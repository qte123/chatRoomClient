package com.tafleo.server.sidebar;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.view.menu.ChatBox;
import com.tafleo.view.menu.Register;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class SidebarServer extends ServerImpl implements Runnable {
    private Register register;
    private String message;
    private User user;
    private JPanel panel;
    private JLabel lblNewLabel_;
    private List<ChatBox> chatBoxList;
    private List<User> userList;
    public SidebarServer(User user, JPanel panel, JLabel lblNewLabel_, List<ChatBox> chatBoxList,List<User> userList) {
        this.user = user;
        this.panel = panel;
        this.lblNewLabel_ = lblNewLabel_;
        this.chatBoxList = chatBoxList;
        this.userList=userList;
    }

    @Override
    public void run() {
        //开放端口
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(MESSAGE_ACCEPT_PORT);
            while (true) {
                //接收数据包
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                //阻塞式接收包裹
                socket.receive(packet);
                new Thread(new SidebarRunnable(packet, user, panel, lblNewLabel_, chatBoxList,userList)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
