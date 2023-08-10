package com.tafleo.view.menu;

import com.tafleo.client.action.ActionClient;
import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.User;
import com.tafleo.server.message.MessageServer;
import com.tafleo.server.sidebar.SidebarServer;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Sidebar extends ViewImpl {

    private JPanel panel;
    private User user1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;//头像
    private JLabel lblNewLabel_4;//用户名
    private JTextField textField_1;//签名
    private List<ChatBox> chatBoxList;//聊天框列表
    private List<User> userList;
    private JButton btnNewButton_3;
    private People people;

    public Sidebar(User user1, List<User> userList) {
        this.user1 = user1;
        chatBoxList = new ArrayList<>();
        this.userList = userList;
        people=new People(user1,userList);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    sidebar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void list(User user, int num) {
        JPanel panel1 = new JPanel();
        if (num < chatBoxList.size()) {
            panel1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    ChatBox chatBox = chatBoxList.get(num);
                    chatBox.getFrame().setVisible(true);
                    chatBox.getFrame().setState(Frame.NORMAL);
                }
            });
        }
        panel.add(panel1);
        panel1.setLayout(null);
        if (user != null) {
            JLabel lblNewLabel = new JLabel("");
            lblNewLabel.setIcon(pic(user.getHeadPortraitURL(), 48, 48));
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
                    String s1 = JSONUtil.entityToJSON(user1);
                    new Thread(new SidebarClient("delete", s + "%" + s1)).start();

                }
            });
            btnNewButton_3.setBounds(200, 30, 70, 23);
            panel1.add(btnNewButton_3);
        }
    }

    public void sidebar() {
        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                String s = JSONUtil.entityToJSON(user1);
                new Thread(new ActionClient("exit", s)).start();
                System.exit(0);
            }
        });
        frame.setResizable(false);
        frame.setTitle("TT聊天室");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 295, 618);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 130, 289, 416);
        frame.getContentPane().add(scrollPane);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new GridLayout(0, 1));

        JScrollBar scrollBar = new JScrollBar();
        panel.add(scrollBar);

        JButton btnNewButton = new JButton("退出");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = JSONUtil.entityToJSON(user1);
                new Thread(new ActionClient("exit", s)).start();
                System.exit(0);
            }
        });
        btnNewButton.setBounds(211, 556, 68, 23);
        frame.getContentPane().add(btnNewButton);

        lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Information(true, user1, lblNewLabel_3, lblNewLabel_4, textField_1, null);
            }
        });
        lblNewLabel_3.setIcon(pic(user1.getHeadPortraitURL(), 60, 60));
        lblNewLabel_3.setBounds(15, 29, 60, 60);
        frame.getContentPane().add(lblNewLabel_3);

        lblNewLabel_4 = new JLabel(user1.getUsername());
        lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel_4.setBounds(85, 43, 80, 19);
        frame.getContentPane().add(lblNewLabel_4);

        JTextField textField = new JTextField();
        textField.setText("搜索");
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if ("".equals(textField.getText())) {
                    textField.setText("搜索");
                }
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                if ("搜索".equals(textField.getText())) {
                    textField.setText("");
                }
            }
        });
        textField.setBounds(44, 99, 157, 21);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("在线");
        lblNewLabel_5.setBounds(233, 74, 46, 15);
        frame.getContentPane().add(lblNewLabel_5);

        JButton btnNewButton_1 = new JButton("搜索");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                String chatNumber = textField.getText();
                if (chatNumber != null && !chatNumber.isEmpty() && !chatNumber.equals(user1.getChatNumber())) {
                    for (int i = 0; i < userList.size(); i++) {
                        User u = userList.get(i);
                        if (chatNumber.equals(u.getChatNumber())) {
                            new Information(true, u, null, null, null, null);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        new Thread(new SidebarClient("select", chatNumber)).start();
                    }
                }
            }
        });
        btnNewButton_1.setBounds(211, 97, 68, 23);
        frame.getContentPane().add(btnNewButton_1);

        JLabel lblNewLabel_6 = new JLabel("");
        lblNewLabel_6.setIcon(pic(QUERY_ICON_FILE, 21, 21));
        lblNewLabel_6.setBounds(15, 100, 21, 21);
        frame.getContentPane().add(lblNewLabel_6);

        textField_1 = new JTextField();
        textField_1.setEditable(false);
        textField_1.setText(user1.getAutograph());
        textField_1.setBounds(85, 68, 116, 21);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        int size = 0;
        User u = null;
        if (userList != null) {
            size = userList.size();
            for (int i = 0; i < 100; i++) {
                if (i < size) {
                    u = userList.get(i);
                    chatBoxList.add(new ChatBox(user1, u));
                } else {
                    u = null;
                }
                list(u, i);
            }
        } else {
            for (int i = 0; i < 100; i++) {
                list(null, i);
            }
        }
        JButton btnNewButton_4 = new JButton("群聊室");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                people.getFrame().setVisible(true);
                people.getFrame().setState(Frame.NORMAL);
            }
        });
        btnNewButton_4.setBounds(10, 556, 80, 23);
        frame.getContentPane().add(btnNewButton_4);
        new Thread(new SidebarServer(user1, panel, lblNewLabel_2, chatBoxList, userList)).start();
        new Thread(new MessageServer(chatBoxList, user1, userList,people)).start();
        frame.setVisible(true);
    }
}

