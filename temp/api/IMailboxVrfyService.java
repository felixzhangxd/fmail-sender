package com.firebugsoft.fmail.smtp.service.api;

public interface IMailboxVrfyService {
    void vrfy(String domain, String startUsername) throws Exception;
}
