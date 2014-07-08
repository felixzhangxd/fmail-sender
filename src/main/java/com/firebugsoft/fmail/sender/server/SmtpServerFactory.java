package com.firebugsoft.fmail.sender.server;

public class SmtpServerFactory {
    public static ISmtpServer getSmtpServer(String fromMail) {
        if (fromMail.endsWith("@126.com")) {
            return Smtp126ComServer.getInstance();
        }
        if (fromMail.endsWith("@163.com")) {
            return Smtp163ComServer.getInstance();
        }
        throw new RuntimeException("No smtp server : " + fromMail);
    }
}
