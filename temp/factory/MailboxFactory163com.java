package com.firebugsoft.fmail.smtp.factory;

/**
 * smtp.163.com的邮箱不区分字母大小写,正则如下:
 * [a-z][a-z0-9_]{5,17}@126\.com
 */
public class MailboxFactory163com implements IMailboxFactory {
    private static final char[] LETTER     = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_' };
    private static final int    MAX_LENGTH = 18;
    private char[]              usernameChar;
    private boolean             hasNext;

    /**
     * smtp.163.com的邮箱不区分字母大小写,正则如下:
     * [a-z][a-z0-9_]{5,17}@126\.com
     * username 应该以[a-z]开始,长度小于18
     */
    public MailboxFactory163com(String username) {
        String regexp = "^[a-z][a-z0-9_]{5,17}$";
        if (!username.matches(regexp)) {
            throw new RuntimeException("username master be ^[a-z][a-z0-9_]{5,17}$");
        }
        username = username.toLowerCase();
        this.usernameChar = new char[username.length()];
        username.getChars(0, username.length(), this.usernameChar, 0);
        this.hasNext = true;
    }

    private char nextChar(char c) {
        int index = 0;
        if (c >= 'a' && c <= 'z') {
            index = c - 'a' + 1;
        } else if (c >= '0' && c <= '9') {
            index = c - '0' + 26 + 1;
        } else if (c == '_') {
            index = 0;
        }
        return LETTER[index];
    }

    private void incrementLength() {
        if (usernameChar.length >= MAX_LENGTH) {
            hasNext = false;
            return;
        }
        usernameChar = new char[usernameChar.length + 1];
        for (int i = 0; i < usernameChar.length; i++) {
            usernameChar[i] = LETTER[0];
        }
    }

    private String next() {
        String mailbox = new String(usernameChar);
        for (int i = usernameChar.length - 1; i >= 0; i--) {
            if (i == 0 && usernameChar[i] == 'z') {
                incrementLength();
            } else {
                usernameChar[i] = nextChar(usernameChar[i]);
                if (usernameChar[i] != LETTER[0]) {
                    break;
                }
            }
        }
        return mailbox;
    }

    @Override
    public synchronized String nextUsername() {
        return this.hasNext ? next() : null;
    }

    @Override
    public String getMailbox(String username) {
        return username + "@163.com";
    }

    public static void main(String[] args) {
        IMailboxFactory factory = new MailboxFactory163com("aa");
        String username = null;
        do {
            username = factory.nextUsername();
            System.out.println(username);
        } while (username != null);
    }
}
