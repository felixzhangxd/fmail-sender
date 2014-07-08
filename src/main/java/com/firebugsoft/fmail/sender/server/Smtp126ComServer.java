package com.firebugsoft.fmail.sender.server;

public class Smtp126ComServer implements ISmtpServer {
    private static final ISmtpServer instance = new Smtp126ComServer();

    private Smtp126ComServer() {}

    public static ISmtpServer getInstance() {
        return instance;
    }

    @Override
    public String getHost() {
        return "smtp.126.com";
    }

    @Override
    public int getPort() {
        return 25;
    }

    @Override
    public String getHostSSL() {
        return this.getHost();
    }

    @Override
    public int getPortSSL() {
        // 994
        return 465;
    }
}
