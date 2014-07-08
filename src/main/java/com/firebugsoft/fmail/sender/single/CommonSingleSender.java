package com.firebugsoft.fmail.sender.single;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firebugsoft.fmail.sender.bean.SingleSenderBean;
import com.firebugsoft.fmail.sender.code.ResponseCode;
import com.firebugsoft.fmail.sender.server.ISmtpServer;
import com.firebugsoft.fmail.sender.smtp.Smtp;

public class CommonSingleSender implements Runnable {
    private static Logger    logger = LoggerFactory.getLogger(CommonSingleSender.class);
    private ISmtpServer      smtpServer;
    private Smtp             smtp;
    private SingleSenderBean bean;

    public CommonSingleSender(ISmtpServer smtpServer, Smtp smtp, SingleSenderBean bean) {
        this.smtpServer = smtpServer;
        this.smtp = smtp;
        this.bean = bean;
    }

    @Override
    public void run() {
        StringBuilder log = new StringBuilder();
        Socket socket = null;
        try {
            socket = new Socket(smtpServer.getHost(), smtpServer.getPort());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            boolean isContinue = smtp.init(br, log) == ResponseCode.CODE_220;
            if (isContinue) {
                isContinue = smtp.helo(bos, br, bean.getUsername(), log) == ResponseCode.CODE_250;
            }
            if (isContinue) {
                isContinue = smtp.authLogin(bos, br, log) == ResponseCode.CODE_334;
            }
            if (isContinue) {
                isContinue = smtp.username(bos, br, bean.getUsername(), log) == ResponseCode.CODE_334;
            }
            if (isContinue) {
                isContinue = smtp.password(bos, br, bean.getPassword(), log) == ResponseCode.CODE_235;
            }
            if (isContinue) {
                isContinue = smtp.mailFrom(bos, br, bean.getFromMail(), log) == ResponseCode.CODE_250;
            }
            if (isContinue) {
                isContinue = smtp.rcptTo(bos, br, bean.getToMail(), log) == ResponseCode.CODE_250;
            }
            if (isContinue) {
                isContinue = smtp.data(bos, br, log) == ResponseCode.CODE_354;
            }
            if (isContinue) {
                isContinue = smtp.body(bos, br, bean.getBody(), log) == ResponseCode.CODE_250;
                bean.setIsSuccess(isContinue);
            }
            smtp.quit(bos, br, log);
        } catch (IOException e) {
            logger.error(bean.getToMail(), e);
        } finally {
            logger.info("{}", log);
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
