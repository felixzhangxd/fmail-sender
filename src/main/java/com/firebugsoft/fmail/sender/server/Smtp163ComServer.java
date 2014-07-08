package com.firebugsoft.fmail.sender.server;

public class Smtp163ComServer implements ISmtpServer {
    private static final ISmtpServer instance = new Smtp163ComServer();

    private Smtp163ComServer() {}

    public static ISmtpServer getInstance() {
        return instance;
    }

    @Override
    public String getHost() {
        return "smtp.163.com";
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
        // 995
        return 465;
    }
}
