package com.tafleo.client.file.small;

import com.tafleo.client.ClientImpl;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//客户端
public class FileClient extends ClientImpl implements Runnable {
    private String msg;//消息
    private String toIP;
    public FileClient(String toIP,String msg) {
        this.toIP = toIP;
        this.msg=msg;
    }

    @Override
    public void run() {
        sendFile();
    }


    @Override
    public void sendFile() {
        Socket socket;//文件信息
        OutputStream os;
        InetAddress localIP;//本机IP地址
        try {
            //1.要知道服务器地址
            localIP = InetAddress.getLocalHost();
            InetAddress serverIP = InetAddress.getByName(ServerIP);
            //2.创建一个socket连接
            socket = new Socket(serverIP, CLIENT_PORT);
            //3.发送消息IO流
            String ipstr = localIP.getHostAddress() + "#" + toIP + "$";
            os = socket.getOutputStream();
            msg=ipstr+msg;
            os.write(msg.getBytes());
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}