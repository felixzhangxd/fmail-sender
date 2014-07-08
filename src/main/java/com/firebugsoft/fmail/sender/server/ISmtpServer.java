package com.firebugsoft.fmail.sender.server;

public interface ISmtpServer {
    String getHost();

    int getPort();

    String getHostSSL();

    int getPortSSL();
}
