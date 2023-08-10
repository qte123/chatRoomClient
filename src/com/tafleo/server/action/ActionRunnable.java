package com.tafleo.server.action;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;
import com.tafleo.view.menu.Error;
import com.tafleo.view.menu.Information;
import com.tafleo.view.menu.Successful;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class ActionRunnable extends ServerImpl implements Runnable {

    private DatagramPacket packet;//建立数据包
    private String IM;//存放的IP和消息
    private String message;//存放的消息
    private User user;
    private User newUser;
    private JLabel lblNewLabel_2;
    private List<User> list;

    public ActionRunnable(DatagramPacket packet, User user, User newUser, JLabel lblNewLabel_2, List<User> list) {
        this.packet = packet;
        this.user = user;
        this.newUser = newUser;
        this.lblNewLabel_2 = lblNewLabel_2;
        this.list = list;
    }

    @Override
    public void run() {
        IM = new String(packet.getData(), 0, packet.getLength());
        String fromIP = IM.substring(0, IM.indexOf("#"));
        message = IM.substring(IM.indexOf("#") + 1);
        if (message.startsWith("json*")) {
            String jsonUser = message.substring(message.indexOf("*") + 1);
            if ("already".equals(jsonUser)) {
                new Error(6);
            } else {
                User user1 = JSONUtil.JSONToEntity(jsonUser, User.class);
                if (newUser != null) {
                    copyUser(newUser, user1);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    File file = new File("res//userList//" + newUser.getChatNumber() + ".json");
                    InputStream is = null;
                    try {
                        is = new FileInputStream(file);
                        byte[] bytes = new byte[Max];
                        int len = 0;
                        String listJson = "";
                        while ((len = is.read(bytes)) != -1) {
                            listJson = new String(bytes, 0, len);
                        }
                        is.close();
                        if (!"[]".equals(listJson)) {
                            List<User> list1 = JSONUtil.JSONArrayToList(listJson, User.class);
                            for (int i = 0; i < list1.size(); i++) {
                                list.add(list1.get(i));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (message.startsWith("start*")) {
            String jsonUser = message.substring(message.indexOf("*") + 1);
            User newUser = JSONUtil.JSONToEntity(jsonUser, User.class);
            if (newUser != null) {
                ViewImpl view = new ViewImpl();
                lblNewLabel_2.setIcon(view.pic(newUser.getHeadPortraitURL(), 100, 100));
            }
        }
        if (message.startsWith("modify*")) {
            String jsonUser = message.substring(message.indexOf("*") + 1);
            if ("Failed".equals(jsonUser)){
                new Error(7);
            }
            if ("Successful".equals(jsonUser)){
                new Successful();
            }
        } else {
            if (user != null) {
                user.setChatNumber(message);
            }
        }

        time();
        System.out.println("服务器：" + message);
    }
}
