package com.tafleo.view.menu;

import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.User;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class IsFriend extends ViewImpl {
    private JFrame frmHa;
    private User user;
    private User me;
    private JPanel panel;
    private JLabel lblNewLabel_;
    private List<ChatBox> chatBoxList;
    private List<User> userList;
    private JButton btnNewButton_3;

    public IsFriend(User user, User me, JPanel panel, JLabel lblNewLabel_, List<ChatBox> chatBoxList, List<User> userList) {
        this.user = user;
        this.me = me;
        this.panel = panel;
        this.lblNewLabel_ = lblNewLabel_;
        this.chatBoxList = chatBoxList;
        this.userList = userList;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    isFriend();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void isFriend() {
        frmHa = new JFrame();
        frmHa.setTitle("好友同意");
        frmHa.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frmHa.setResizable(false);
        frmHa.setBounds(100, 100, 308, 207);
        frmHa.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmHa.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("请求加为好友");
        lblNewLabel.setBounds(116, 93, 83, 15);
        frmHa.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel(user.getChatNumber());
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(123, 37, 60, 21);
        frmHa.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("拒绝");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String s = JSONUtil.entityToJSON(me);
                new Thread(new SidebarClient("addNo", s)).start();
                frmHa.dispose();
            }
        });
        btnNewButton.setBounds(55, 129, 73, 23);
        frmHa.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("同意");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserList(userList, user, panel);
                modifyFriend(me,userList);
                String s = JSONUtil.entityToJSON(user);
                String ME = JSONUtil.entityToJSON(me);
                new Thread(new SidebarClient("addYes", s + "%" + ME)).start();
                panel.validate();
                frmHa.dispose();
            }
        });
        btnNewButton_1.setBounds(174, 129, 73, 23);
        frmHa.getContentPane().add(btnNewButton_1);

        JLabel lblNewLabel_2 = new JLabel(user.getUsername());
        lblNewLabel_2.setBounds(116, 68, 67, 15);
        frmHa.getContentPane().add(lblNewLabel_2);
        frmHa.setVisible(true);
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

            btnNewButton_3 = new JButton("删除");
            btnNewButton_3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    panel.remove(panel1);
                    panel.validate();
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
                }
            });
            btnNewButton_3.setBounds(200, 30, 70, 23);
            panel1.add(btnNewButton_3);
        }
    }
}
