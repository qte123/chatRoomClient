package com.tafleo.server.message;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.view.menu.ChatBox;
import com.tafleo.view.menu.People;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class MessageServer extends ServerImpl implements Runnable {
    private List<ChatBox> chatBoxList;
    private User user;
    private List<User> userList;
    private People people;


    public MessageServer(List<ChatBox> chatBoxList, User user, List<User> userList, People people) {
        this.chatBoxList = chatBoxList;
        this.user = user;
        this.userList = userList;
        this.people = people;
    }

    @Override
    public void run() {
        try {//开放端口
            DatagramSocket socket = new DatagramSocket(SERVER_PORT);
            while (true) {
                //接收数据包
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                //阻塞式接收包裹
                socket.receive(packet);
                new Thread(new MessageServerRunnable(packet, chatBoxList, user, userList,people)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
