package com.firebugsoft.fmail.smtp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.firebugsoft.fmail.smtp.cmd.SmtpCmd;
import com.firebugsoft.fmail.smtp.factory.IMailboxFactory;
import com.firebugsoft.fmail.smtp.service.api.IMailboxVrfyService;
import com.firebugsoft.fmail.smtp.service.runnable.IVrfyRunnable;
import com.firebugsoft.fmail.smtp.service.runnable.Vrfy163comRunnable;

import biz.ezcom.smail.dao.api.IMailAccountDao;
import biz.ezcom.smail.dao.api.IMailboxDao;
import biz.ezcom.smail.dao.api.IServerDao;
import biz.ezcom.smail.dao.po.MailAccountPo;
import biz.ezcom.smail.dao.po.ServerPo;

@Service
public class MailboxVrfyService implements IMailboxVrfyService {
    @Resource
    private IServerDao      serverDao;
    @Resource
    private IMailAccountDao mailAccountDao;
    @Resource
    private IMailboxDao     mailboxDao;

    @Override
    public void vrfy(String domain, String startUsername) throws Exception {
        ServerPo server = serverDao.findByDomain(domain);
        List<MailAccountPo> mailAccounts = mailAccountDao.findByServerId(server.getId());
        IMailboxFactory factory = this.getFactoryByDomain(domain, startUsername);
        IVrfyRunnable runnable = new Vrfy163comRunnable(server, mailAccounts.get(0),factory, mailboxDao,new Smtp(true));
        runnable.execute();
    }

    private IMailboxFactory getFactoryByDomain(String domain, String startUsername) throws Exception {
        String packageName = IMailboxFactory.class.getPackage().getName();
        String simpleClassName = "MailboxFactory" + domain.replace(".", "").toLowerCase();
        String className = packageName + "." + simpleClassName;
        return (IMailboxFactory) Class.forName(className).getConstructor(String.class).newInstance(startUsername);
    }
}
