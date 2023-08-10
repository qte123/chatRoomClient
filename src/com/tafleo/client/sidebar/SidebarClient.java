package com.tafleo.client.sidebar;

import com.tafleo.client.ClientImpl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SidebarClient extends ClientImpl implements Runnable {
    private String msg;
    private String json;

    public SidebarClient(String msg) {
        this.msg = msg;
    }

    public SidebarClient(String msg, String json) {
        this.msg = msg;
        this.json = json;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            String toIP = ServerIP;
            InetAddress localIP = InetAddress.getLocalHost();//本机IP地址
            InetAddress serverIP = InetAddress.getByName(ServerIP);//服务器IP地址
            String ipstr = localIP.getHostAddress() + "#" + toIP + "$";
            msg = ipstr + msg;
            if (json!=null&&!"".equals(json)){
                msg = msg+"*"+json;
            }
            //数据，数据的长度起始，要发送给谁
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, serverIP, MESSAGE_SEND_PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
