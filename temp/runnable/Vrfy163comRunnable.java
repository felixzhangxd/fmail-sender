package com.firebugsoft.fmail.smtp.service.runnable;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.firebugsoft.fmail.smtp.cmd.SmtpCmd;
import com.firebugsoft.fmail.smtp.factory.IMailboxFactory;

import biz.ezcom.smail.dao.api.IMailboxDao;
import biz.ezcom.smail.dao.po.MailAccountPo;
import biz.ezcom.smail.dao.po.MailboxPo;
import biz.ezcom.smail.dao.po.ServerPo;

/**
 * 验证163.com邮箱线程
 */
public class Vrfy163comRunnable implements IVrfyRunnable {
    private static final int MAX_SOCKET_COUNT = 10;
    private static final int MAX_OK_COUNT     = 40;
    private static final int MAX_NG_COUNT     = 80;
    private ServerPo         server;
    private MailAccountPo    account;
    private IMailboxFactory  factory;
    private IMailboxDao      mailboxDao;
    private Smtp          cmd;

    public Vrfy163comRunnable(ServerPo server, MailAccountPo account, IMailboxFactory factory, IMailboxDao mailboxDao, Smtp cmd) {
        this.server = server;
        this.account = account;
        this.factory = factory;
        this.mailboxDao = mailboxDao;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_SOCKET_COUNT);
        for (int i = 0; i < MAX_SOCKET_COUNT; i++) {
            pool.execute(this);
        }
        pool.shutdown();
    }

    @Override
    public void run() {
        Date vrfyDate = new Date(System.currentTimeMillis());
        Socket socket = null;
        int okCount = 0;
        int ngCount = 0;
        String username = "";
        while (true) {
            try {
                socket = new Socket(server.getHost(), server.getPort());
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (cmd.smtpInit(br, null).getResponseCode() != 220) {
                    break;
                }
                if (cmd.smtpHelo(bos, br, null, account.getUsername()).getResponseCode() != 250) {
                    break;
                }
                if (cmd.smtpAuthLogin(bos, br, null).getResponseCode() != 334) {
                    break;
                }
                if (cmd.smtpUsername(bos, br, null, account.getUsername()).getResponseCode() != 334) {
                    break;
                }
                if (cmd.smtpPassword(bos, br, null, account.getPassword()).getResponseCode() != 235) {
                    break;
                }
                if (cmd.smtpMail(bos, br, null, account.getMailbox()).getResponseCode() != 250) {
                    break;
                }
                do {
                    username = factory.nextUsername();
                    if (username == null) {
                        System.out.println("factory produce is over");
                        return;
                    }
                    String mailbox = factory.getMailbox(username);
                    int rcptCode = cmd.smtpRcpt(bos, br, null, mailbox).getResponseCode();
                    if (rcptCode == 250) {
                        mailboxDao.save(server.getId(), new MailboxPo(null, username, vrfyDate));
                        okCount++;
                    } else {
                        ngCount++;
                    }
                } while (okCount < MAX_OK_COUNT && ngCount < MAX_NG_COUNT);
                cmd.smtpQuit(bos, br, null);
                System.out.println(Thread.currentThread().getName() + "continue: ok=" + okCount + " ng=" + ngCount);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {}
                }
                okCount = 0;
                ngCount = 0;
            }
        }
        System.out.println(Thread.currentThread().getName() + " last username=" + username);
    }
}
