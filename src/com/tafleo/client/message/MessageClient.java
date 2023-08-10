package com.tafleo.client.message;

import com.tafleo.client.ClientImpl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MessageClient extends ClientImpl implements Runnable {
    private String toIP;
    private String message;

    public MessageClient(String toIP, String message) {
        this.toIP = toIP;
        this.message = message;
    }

    @Override
    public void run() {
        sendMessage();
    }

    @Override
    public void sendMessage() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress localIP = InetAddress.getLocalHost();//本机IP地址
            InetAddress serverIP = InetAddress.getByName(ServerIP);//服务器IP地址
            String ipstr = localIP.getHostAddress() + "#" + toIP + "$";
            //2.建个包
            String msg = message;
            msg = ipstr + msg;
            //数据，数据的长度起始，要发送给谁
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, serverIP, CLIENT_PORT);
            //3.发送包
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
