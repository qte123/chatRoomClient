package com.tafleo.server.sidebar;

import com.tafleo.client.action.ActionClient;
import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;
import com.tafleo.view.menu.ChatBox;
import com.tafleo.view.menu.Error;
import com.tafleo.view.menu.Information;
import com.tafleo.view.menu.IsFriend;
import com.tafleo.view.menu.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.DatagramPacket;
import java.util.List;

public class SidebarRunnable extends ServerImpl implements Runnable {
    private DatagramPacket packet;//建立数据包
    private String IM;//存放的IP和消息
    private String message;//存放的消息
    private User me;
    private JPanel panel;
    private JLabel lblNewLabel_;
    private List<ChatBox> chatBoxList;
    private List<User> userList;

    public SidebarRunnable(DatagramPacket packet, User me, JPanel panel, JLabel lblNewLabel_, List<ChatBox> chatBoxList, List<User> userList) {
        this.packet = packet;
        this.me = me;
        this.panel = panel;
        this.lblNewLabel_ = lblNewLabel_;
        this.chatBoxList = chatBoxList;
        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            IM = new String(packet.getData(), 0, packet.getLength());
            String fromIP = IM.substring(0, IM.indexOf("#"));
            message = IM.substring(IM.indexOf("#") + 1);
            if (message.startsWith("modify*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User u = JSONUtil.JSONToEntity(jsonUser, User.class);
                modifyUserList(userList, u, panel);
                time();
                panel.validate();
                System.out.println("服务器：" + message);
            }
            if (message.startsWith("mod*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User u = JSONUtil.JSONToEntity(jsonUser, User.class);
                modifyUserList(userList, u, panel);
                time();
                panel.validate();
                System.out.println("服务器：" + message);
            }
            if (message.startsWith("select*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User u = JSONUtil.JSONToEntity(jsonUser, User.class);
                if (u != null) {
                    new Information(false, u, null, null, null, me.getChatNumber());
                }
            }
            if (message.startsWith("addFriend*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User u = JSONUtil.JSONToEntity(jsonUser, User.class);
                if (u != null) {
                    new IsFriend(u, me, panel, lblNewLabel_, chatBoxList, userList);
                }
            }
            if (message.startsWith("on*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User user = JSONUtil.JSONToEntity(jsonUser, User.class);
                modifyUserList(userList, user, panel);
                panel.validate();
            }
            if (message.startsWith("exit*")) {
                String jsonUser = message.substring(message.indexOf("*") + 1);
                User user = JSONUtil.JSONToEntity(jsonUser, User.class);
                modifyUserList(userList, user, panel);
                panel.validate();
            }
            if (message.startsWith("addYes*")) {
                String s = message.substring(message.indexOf("*") + 1);
                User user = JSONUtil.JSONToEntity(s, User.class);
                new Window(1);
                addUserList(userList, user, panel);
                modifyFriend(me, userList);
                panel.validate();
            }
            if (message.startsWith("addNo*")) {
                new Window(0);
            }
            if (message.startsWith("delete*")) {
                String s = message.substring(message.indexOf("*") + 1);
                System.out.println(s);
                User user = JSONUtil.JSONToEntity(s, User.class);
                System.out.println(userList);
                System.out.println(user);
                for (int i = 0; i < userList.size(); i++) {
                    User u = userList.get(i);
                    if (user.getChatNumber().equals(u.getChatNumber())) {
                        userList.remove(i);
                        break;
                    }
                }
                System.out.println(userList);
                for (int i = 0; i < chatBoxList.size(); i++) {
                    ChatBox chatBox = chatBoxList.get(i);
                    if (user.getChatNumber().equals(chatBox.getUser1())) {
                        chatBoxList.remove(chatBox);
                        break;
                    }
                }
                panel.removeAll();
                JScrollBar scrollBar = new JScrollBar();
                panel.add(scrollBar);
                int size = 0;
                User u = null;
                if (userList != null) {
                    size = userList.size();
                    for (int i = 0; i < 100; i++) {
                        if (i < size) {
                            u = userList.get(i);
                        } else {
                            u = null;
                        }
                        list(u, lblNewLabel_, i);
                    }
                } else {
                    for (int i = 0; i < 100; i++) {
                        list(null, lblNewLabel_, i);
                    }
                }
                modifyFriend(me, userList);
                panel.validate();
            }
            if (message.startsWith("down*")) {
                String sign = message.substring(message.indexOf("*") + 1);
                if ("line".equals(sign)) {
                    new Error(4);
                }
                if ("delete".equals(sign)) {
                    new Error(5);
                }
                String s = JSONUtil.entityToJSON(me);
                new Thread(new ActionClient("exit", s)).start();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserList(List<User> list, User user, JPanel panel) {
        System.out.println(list);
        boolean flag = true;
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (user.getChatNumber().equals(u.getChatNumber())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            chatBoxList.add(list.size(), new ChatBox(me, user));
            list.add(user);
            panel.removeAll();
            JScrollBar scrollBar = new JScrollBar();
            panel.add(scrollBar);
            int size = 0;
            User u = null;
            if (list != null) {
                size = list.size();
                for (int i = 0; i < 100; i++) {
                    if (i < size) {
                        u = list.get(i);
                    } else {
                        u = null;
                    }
                    list(u, lblNewLabel_, i);
                }
            } else {
                for (int i = 0; i < 100; i++) {
                    list(null, lblNewLabel_, i);
                }
            }
        }

    }

    public void modifyUserList(List<User> list, User use, JPanel panel) {
        int num = -1;
        for (int i = 0; i < list.size(); i++) {
            if (use.getChatNumber().equals(list.get(i).getChatNumber()) && !me.getChatNumber().equals(use.getChatNumber())) {
                User u = list.get(i);
                copyUser(u, use);
                //list.remove(i);
                // list.add(i, use);
                num = i;
                break;
            }
        }
        if (use.getChatNumber().equals(me.getChatNumber())) {
            for (int i = 0; i < chatBoxList.size(); i++) {
                User user1 = chatBoxList.get(i).getUser1();
                ChatBox chatBox = chatBoxList.get(i);
                chatBox.setMe(me);
                chatBox.setUser1(user1);

                chatBox.getLblNewLabel().setIcon(pic(user1.getHeadPortraitURL(), 48, 48));
                chatBox.getLblNewLabel_1().setText(user1.getUsername());
                chatBox.setChatStatus(user1.getChatStatus());
                String s = user1.getChatStatus() == 1 ? "在线" : "离线";
                chatBox.getLblNewLabel_2().setText(s);

                chatBox.getLblNewLabel1().setIcon(pic(me.getHeadPortraitURL(), 48, 48));
                chatBox.getLblNewLabel_11().setText(me.getUsername());
                chatBox.setChatStatus1(me.getChatStatus());
                String s1 = me.getChatStatus() == 1 ? "在线" : "离线";
                chatBox.getLblNewLabel_22().setText(s1);

                //ChatBox remove = chatBoxList.remove(i);
                //ChatBox chatBox = new ChatBox(me, remove.getUser1());
                //chatBoxList.add(i, chatBox);
            }
        }
        if (num != -1) {
            //chatBoxList.remove(num);
            // ChatBox newChatBox = new ChatBox(me, use);
            //chatBoxList.add(num, newChatBox);
            ChatBox modify = chatBoxList.get(num);
            modify.setMe(me);
            modify.setUser1(use);

            modify.getLblNewLabel().setIcon(pic(use.getHeadPortraitURL(), 48, 48));
            modify.getLblNewLabel_1().setText(use.getUsername());
            modify.setChatStatus(use.getChatStatus());
            String s = use.getChatStatus() == 1 ? "在线" : "离线";
            modify.getLblNewLabel_2().setText(s);

            modify.getLblNewLabel1().setIcon(pic(me.getHeadPortraitURL(), 48, 48));
            modify.getLblNewLabel_11().setText(me.getUsername());
            modify.setChatStatus1(me.getChatStatus());
            String s1 = me.getChatStatus() == 1 ? "在线" : "离线";
            modify.getLblNewLabel_22().setText(s1);

            panel.removeAll();
            JScrollBar scrollBar = new JScrollBar();
            panel.add(scrollBar);
            int size = 0;
            User u = null;
            if (list != null) {
                size = list.size();
                for (int i = 0; i < 100; i++) {
                    if (i < size) {
                        u = list.get(i);
                    } else {
                        u = null;
                    }
                    list(u, lblNewLabel_, i);
                }
            } else {
                for (int i = 0; i < 100; i++) {
                    list(null, lblNewLabel_, i);
                }
            }
        }
    }

    public void list(User user, JLabel lblNewLabel_2, int num) {
        ViewImpl view = new ViewImpl();
        JPanel panel1 = new JPanel();
        if (num < chatBoxList.size()) {
            panel1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    ChatBox chatBox = chatBoxList.get(num);
                    chatBox.getFrame().setVisible(true);
                }
            });
        }
        panel.add(panel1);
        panel1.setLayout(null);
        if (user != null) {
            JLabel lblNewLabel = new JLabel("");
            lblNewLabel.setIcon(view.pic(user.getHeadPortraitURL(), 48, 48));
            lblNewLabel.setBounds(10, 0, 48, 48);
            panel1.add(lblNewLabel);

            JLabel lblNewLabel_1 = new JLabel(user.getUsername());
            lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
            lblNewLabel_1.setBounds(68, 10, 54, 16);
            panel1.add(lblNewLabel_1);


            lblNewLabel_2 = new JLabel("");
            if (user.getChatStatus() == 1) {
                lblNewLabel_2.setText("在线");
            } else {
                lblNewLabel_2.setText("离线");
            }
            lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 10));
            lblNewLabel_2.setBounds(68, 33, 54, 15);
            panel1.add(lblNewLabel_2);

            JButton btnNewButton_2 = new JButton("查看");
            btnNewButton_2.setBounds(200, 0, 70, 23);
            btnNewButton_2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Information(true, user, null, null, null, null);
                }
            });
            panel1.add(btnNewButton_2);

            JButton btnNewButton_3 = new JButton("删除");
            btnNewButton_3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    panel.remove(panel1);
                    for (int i = 0; i < userList.size(); i++) {
                        User u = userList.get(i);
                        if (user.getChatNumber().equals(u.getChatNumber())) {
                            userList.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < chatBoxList.size(); i++) {
                        ChatBox chatBox = chatBoxList.get(i);
                        if (user.getChatNumber().equals(chatBox.getUser1())) {
                            chatBoxList.remove(i);
                            break;
                        }
                    }
                    String s = JSONUtil.entityToJSON(user);
                    String s1 = JSONUtil.entityToJSON(me);
                    new Thread(new SidebarClient("delete", s + "%" + s1)).start();
                    panel.validate();
                }
            });
            btnNewButton_3.setBounds(200, 30, 70, 23);
            panel1.add(btnNewButton_3);
        }
    }

    public ImageIcon pic(String file, int width, int height) {
        ImageIcon oldIcon = new ImageIcon(file);
        Image img = oldIcon.getImage();
        Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(newImg);
        return icon;
    }
}
