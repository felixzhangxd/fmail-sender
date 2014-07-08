package com.firebugsoft.fmail.smtp.factory;

public interface IMailboxFactory {
    String nextUsername();
    String getMailbox(String username);
}
