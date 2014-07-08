package com.firebugsoft.fmail.smtp.factory;

/**
 * smtp.126.com的邮箱不区分字母大小写,正则如下:
 * [a-z][a-z0-9_]{5,17}@126\.com
 * 126与163的邮箱的username部分规则一样,这里使用组合
 */
public class MailboxFactory126com implements IMailboxFactory {
    private IMailboxFactory factory;

    public MailboxFactory126com(String username) {
        this.factory = new MailboxFactory163com(username);
    }

    @Override
    public String nextUsername() {
        return factory.nextUsername();
    }

    @Override
    public String getMailbox(String username) {
        return username + "@163.com";
    }
}
