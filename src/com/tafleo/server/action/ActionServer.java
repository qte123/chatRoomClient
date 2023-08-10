package com.tafleo.server.action;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.view.menu.Register;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class ActionServer extends ServerImpl implements Runnable {
    private Register register;
    private String message;
    private User user;
    private JLabel lblNewLabel_2;
    private List<User> userList;
    public ActionServer(Register register, User user, JLabel lblNewLabel_2,List<User> userList) {
        this.register=register;
        this.user=user;
        this.lblNewLabel_2=lblNewLabel_2;
        this.userList=userList;
    }

    @Override
    public void run() {
        //开放端口
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(ACCEPT_PORT);
            while (true) {
                //接收数据包
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                //阻塞式接收包裹
                socket.receive(packet);
                new Thread(new ActionRunnable(packet,register.getUser(),user,lblNewLabel_2,userList)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
