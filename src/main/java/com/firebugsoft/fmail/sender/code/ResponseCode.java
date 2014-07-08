package com.firebugsoft.fmail.sender.code;

public class ResponseCode {
    /** 系统状态或系统帮助响应 */
    public static final int CODE_211 = 211;
    /** 帮助信息 */
    public static final int CODE_214 = 214;
    /** <domain> 服务就绪 */
    public static final int CODE_220 = 220;
    /** <domain> 服务关闭传输信道 */
    public static final int CODE_221 = 221;
    /** AUTH LOGIN 成功后 或 密码输入成功后返回 */
    public static final int CODE_235 = 235;
    /** 要求的邮件操作完成 */
    public static final int CODE_250 = 250;
    /** 用户非本地，将转发向<forward-path> */
    public static final int CODE_251 = 251;
    /** AUTH LOGIN 成功后返回 与 用户名输入成功后返回 */
    public static final int CODE_334 = 334;
    /** 开始邮件输入，以<CRLF>.<CRLF>结束 */
    public static final int CODE_354 = 354;
    /** <domain> 服务未就绪，关闭传输信道（当必须关闭时，此应答可以作为对任何命令的响应） */
    public static final int CODE_421 = 421;
    /** 要求的邮件操作未完成，邮箱不可用（例如，邮箱忙） */
    public static final int CODE_450 = 450;
    /** 放弃要求的操作；处理过程中出错 */
    public static final int CODE_451 = 451;
    /** 系统存储不足，要求的操作未执行 */
    public static final int CODE_452 = 452;
    /** 格式错误，命令不可识别（此错误也包括命令行过长） */
    public static final int CODE_500 = 500;
    /** 参数格式错误 */
    public static final int CODE_501 = 501;
    /** 命令不可实现 */
    public static final int CODE_502 = 502;
    /** 错误的命令序列 */
    public static final int CODE_503 = 503;
    /** 命令参数不可实现 */
    public static final int CODE_504 = 504;
    /** RCPT TO时该邮件不存在 */
    public static final int CODE_511 = 511;
    /** 用户不存在 */
    public static final int CODE_550 = 550;
    /** 用户非本地，请尝试<forward-path> */
    public static final int CODE_551 = 551;
    /** 过量的存储分配，要求的操作未执行 */
    public static final int CODE_552 = 552;
    /** 邮箱名不可用，要求的操作未执行（例如邮箱格式错误） */
    public static final int CODE_553 = 553;
    /** 操作失败 */
    public static final int CODE_554 = 554;
}
