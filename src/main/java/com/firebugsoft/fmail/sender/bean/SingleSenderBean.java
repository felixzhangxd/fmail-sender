package com.firebugsoft.fmail.sender.bean;

public class SingleSenderBean {
    private String  fromMail;
    private String  username;
    private String  password;
    private String  toMail;
    private byte[]  body;
    private boolean isSuccess;

    public SingleSenderBean() {}

    public SingleSenderBean(String fromMail, String username, String password, String toMail, byte[] body) {
        this.fromMail = fromMail;
        this.username = username;
        this.password = password;
        this.toMail = toMail;
        this.body = body;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
