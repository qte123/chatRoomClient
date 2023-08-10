package com.tafleo.common;

public class ClientBase extends Common {

    //服务器端发送文件端口号
    public static final int FILE_TO_PORT = 9997;
    //服务器端接收文件端口号
    public static final int FILE_FROM_PORT = 9998;
    //本地服务端文字端口号，远程服务输出端口号
    public static final int SERVER_PORT = 9999;
    //本地客户端端口号，也是远程服务器输入端口号
    public static final int CLIENT_PORT = 8888;
    //本地客户端用户信息发送端口号
    public static final int MESSAGE_SEND_PORT = 8877;
    //本地服务端用户信息接收端口号
    public static final int MESSAGE_ACCEPT_PORT = 8878;
    //大文件接收端口
    public static final int BIG_FILE_PORT = 6056;
    //服务器的IP地址
    public static String ServerIP;
    //注册信息发送端口
    public static final int SEND_PORT = 9995;
    //注册信息接收端口
    public static final int ACCEPT_PORT = 9996;
    //手机号码正则表达式
    public static final String REGEX_MOBILE = "^1(3\\d|4[5-8]|5[0-35-9]|6[567]|7[01345-8]|8\\d|9[025-9])\\d{8}$";
    //电子邮箱正则表达式
    public static final String REGEX_MAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    //日期的正则表达式
    public static final String REGEX_DATETIME = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
}
