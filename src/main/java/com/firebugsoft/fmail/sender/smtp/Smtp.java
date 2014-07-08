package com.firebugsoft.fmail.sender.smtp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class Smtp {
    private static final String CRLF = "\r\n";
    private final boolean       isLog;

    public Smtp() {
        this(false);
    }

    public Smtp(boolean isLog) {
        this.isLog = isLog;
    }

    /**
     * socket连上后初始化
     * 成功返回状态:220
     */
    public int init(BufferedReader br, StringBuilder log) throws IOException {
        return receive(br, log);
    }

    /**
     * smtp: HELO
     * 成功返回状态: 250
     */
    public int helo(BufferedOutputStream bos, BufferedReader br, String domain, StringBuilder log) throws IOException {
        return sendAndReceive(bos, br, "HELO " + domain, log);
    }

    /**
     * smtp: AUTH LOGIN
     * 成功返回状态: 334
     */
    public int authLogin(BufferedOutputStream bos, BufferedReader br, StringBuilder log) throws IOException {
        return sendAndReceive(bos, br, "AUTH LOGIN", log);
    }

    /**
     * smtp: base64's username
     * 成功返回状态: 334
     */
    public int username(BufferedOutputStream bos, BufferedReader br, String username, StringBuilder log) throws IOException {
        String cmd = Base64.encodeBase64String(username.getBytes());
        return sendAndReceive(bos, br, cmd, log);
    }

    /**
     * smtp: base64's password
     * 成功返回状态: 235
     */
    public int password(BufferedOutputStream bos, BufferedReader br, String password, StringBuilder log) throws IOException {
        String cmd = Base64.encodeBase64String(password.getBytes());
        return sendAndReceive(bos, br, cmd, log);
    }

    /**
     * smtp: MAIL FROM
     * 成功返回状态: 250
     */
    public int mailFrom(BufferedOutputStream bos, BufferedReader br, String fromMail, StringBuilder log) throws IOException {
        String cmd = "MAIL FROM:<" + fromMail + ">";
        return sendAndReceive(bos, br, cmd, log);
    }

    /**
     * smtp: RCPT TO
     * 成功返回状态: 250
     */
    public int rcptTo(BufferedOutputStream bos, BufferedReader br, String toMail, StringBuilder log) throws IOException {
        String cmd = "RCPT TO:<" + toMail + ">";
        return sendAndReceive(bos, br, cmd, log);
    }

    /**
     * smtp: DATA
     * 成功返回状态: 354
     */
    public int data(BufferedOutputStream bos, BufferedReader br, StringBuilder log) throws IOException {
        return sendAndReceive(bos, br, "DATA", log);
    }

    /**
     * smtp: mail's body
     * 成功返回状态: 250
     */
    public int body(BufferedOutputStream bos, BufferedReader br, byte[] body, StringBuilder log) throws IOException {
        bos.write(body);
        bos.write(CRLF.getBytes());
        bos.write(".".getBytes());
        bos.write(CRLF.getBytes());
        bos.flush();
        if (isLog) {
            log.append("body").append(CRLF);
        }
        return receive(br, log);
    }

    /**
     * smtp: RSET
     * 成功返回状态: 250
     */
    public int rset(BufferedOutputStream bos, BufferedReader br, StringBuilder log) throws IOException {
        return sendAndReceive(bos, br, "RSET", log);
    }

    /**
     * smtp: QUIT
     * 成功返回状态: 221
     */
    public int quit(BufferedOutputStream bos, BufferedReader br, StringBuilder log) throws IOException {
        return sendAndReceive(bos, br, "QUIT", log);
    }

    private int sendAndReceive(BufferedOutputStream bos, BufferedReader br, String cmd, StringBuilder log) throws IOException {
        send(bos, cmd, log);
        return receive(br, log);
    }

    /**
     * 发送smtp命令
     */
    private void send(BufferedOutputStream bos, String cmd, StringBuilder log) throws IOException {
        bos.write(cmd.getBytes());
        bos.write(CRLF.getBytes());
        if (isLog) {
            log.append(cmd).append(CRLF);
        }
        bos.flush();
    }

    /**
     * 接收smtp返回
     */
    private int receive(BufferedReader br, StringBuilder log) throws IOException {
        String response = "";
        do {
            response = br.readLine();
            if (isLog) {
                log.append(response).append(CRLF);
            }
        } while (br.ready());
        return Integer.parseInt(response.trim().substring(0, 3));
    }
}
