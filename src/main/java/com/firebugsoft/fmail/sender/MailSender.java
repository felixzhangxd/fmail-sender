package com.firebugsoft.fmail.sender;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailSender {
    private ExecutorService service;

    public MailSender(int threadCount) {
        service = Executors.newFixedThreadPool(threadCount);
        service.shutdown();
    }

    public void send(Collection<Runnable> rs) {
        for (Runnable r : rs) {
            service.execute(r);
        }
    }
}
