package com.tafleo.view.menu;

import com.tafleo.client.action.ActionClient;
import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.User;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Information extends ViewImpl {

    private JFrame frame;
    private JTextField textField;//用户名
    private JComboBox comboBox_1;//性别
    private JTextArea textArea;//签名
    private JTextField textField_1;//出生年月
    private JTextField textField_2;//电话号码
    private JLabel lblNewLabel_9;//状态
    private JTextField textField_3;//故乡
    private JTextField textField_4;//电子邮箱
    private JTextField textField_7;//星座
    private JButton btnNewButton;
    private boolean flag;//是否已经成为好友
    private User user;
    private JLabel lbl3;//头像
    private JLabel lbl4;//用户名
    private JTextField textField_;//签名
    private JLabel lblNewLabel_7;//头像
    private String chatNumber;
    private User test;

    public Information(boolean flag, User user, JLabel lbl3, JLabel lbl4, JTextField textField_, String chatNumber) {
        this.flag = flag;
        this.user = user;
        this.lbl3 = lbl3;
        this.lbl4 = lbl4;
        this.textField_ = textField_;
        this.chatNumber = chatNumber;
        test=new User();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    information();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void information() {
        frame = new JFrame();
        frame.setTitle("信息界面");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 488, 298);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("TT号：");
        lblNewLabel.setBounds(211, 63, 54, 15);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("用户名：");
        lblNewLabel_1.setBounds(211, 38, 54, 15);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("性别：");
        lblNewLabel_2.setBounds(211, 88, 42, 15);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel label = new JLabel("电话号码：");
        label.setBounds(211, 138, 70, 15);
        frame.getContentPane().add(label);

        JLabel lblNewLabel_3 = new JLabel("状态：");
        lblNewLabel_3.setBounds(46, 148, 54, 15);
        frame.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("故乡：");
        lblNewLabel_4.setBounds(211, 191, 54, 15);
        frame.getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("电子邮箱：");
        lblNewLabel_5.setBounds(211, 216, 72, 15);
        frame.getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("星座：");
        lblNewLabel_6.setBounds(211, 241, 54, 15);
        frame.getContentPane().add(lblNewLabel_6);

        JLabel label_1 = new JLabel("签名：");
        label_1.setBounds(211, 113, 54, 15);
        frame.getContentPane().add(label_1);
        test.setHeadPortraitURL(user.getHeadPortraitURL());
        lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Head(test,lblNewLabel_7);
            }
        });
        lblNewLabel_7.setBounds(46, 38, 100, 100);
        lblNewLabel_7.setIcon(pic(user.getHeadPortraitURL(), 100, 100));
        frame.getContentPane().add(lblNewLabel_7);

        textField = new JTextField(user.getUsername());
        textField.setBounds(275, 35, 147, 21);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_2 = new JTextField(user.getPhoneNumber());
        textField_2.setBounds(275, 138, 147, 21);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField(user.getHome());
        textField_3.setBounds(275, 188, 147, 21);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField(user.getEmail());
        textField_4.setBounds(275, 213, 147, 21);
        frame.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        textField_7 = new JTextField(user.getConstellation());
        textField_7.setBounds(275, 238, 147, 21);
        frame.getContentPane().add(textField_7);
        textField_7.setColumns(10);
        textField_7.setEditable(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(275, 109, 147, 24);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea(user.getAutograph());
        scrollPane.setViewportView(textArea);

        lblNewLabel_9 = new JLabel("在线");
        lblNewLabel_9.setFont(new Font("宋体", Font.BOLD, 14));
        lblNewLabel_9.setBounds(81, 147, 54, 16);
        frame.getContentPane().add(lblNewLabel_9);

        comboBox_1 = new JComboBox();
        comboBox_1.setModel(new DefaultComboBoxModel(new String[]{"男", "女", "隐藏"}));
        comboBox_1.setSelectedIndex(user.getGender());
        comboBox_1.setBounds(275, 85, 147, 21);
        frame.getContentPane().add(comboBox_1);
        textField_1 = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()));

        textField.setEditable(false);
        comboBox_1.setEnabled(false);
        textArea.setEditable(false);
        textField_1.setEditable(false);//出生年月
        textField_2.setEditable(false);//电话号码
        textField_3.setEditable(false);//故乡
        textField_4.setEditable(false);//电子邮箱
        textField_7.setEditable(false);//星座
        if (flag && lbl3 == null && lbl4 == null && textField_ == null) {
            if (user.getChatStatus() == 1) {
                lblNewLabel_9.setText("在线");
            } else {
                lblNewLabel_9.setText("离线");
            }
        }
        if (flag && lbl3 != null && lbl4 != null && textField_ != null) {
            btnNewButton = new JButton("保存信息");
            textField.setEditable(true);
            comboBox_1.setEnabled(true);
            textArea.setEditable(true);
            textField_1.setEditable(true);//出生年月
            textField_2.setEditable(true);//电话号码
            textField_3.setEditable(true);//故乡
            textField_4.setEditable(true);//电子邮箱
            textField_7.setEditable(true);//星座
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (isModify()) {
                        String s = JSONUtil.entityToJSON(user);
                        new Thread(new SidebarClient("modify", s)).start();
                    }
                }
            });
            btnNewButton.setBounds(46, 188, 100, 24);
            frame.getContentPane().add(btnNewButton);
        } else if (!flag && chatNumber != null && !chatNumber.isEmpty()) {
            btnNewButton = new JButton("添加好友");
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int sum = 0;
                    String json = chatNumber + "&" + JSONUtil.entityToJSON(user);
                    new Thread(new SidebarClient("addFriend", json)).start();
                    sum++;
                    if (sum==1){
                        btnNewButton.setEnabled(false);
                        btnNewButton.setText("请求已发出");
                    }
                }
            });
            btnNewButton.setBounds(46, 188, 100, 24);
            frame.getContentPane().add(btnNewButton);
        }


        JLabel lblNewLabel_8 = new JLabel(user.getChatNumber());
        lblNewLabel_8.setBounds(275, 63, 147, 15);
        frame.getContentPane().add(lblNewLabel_8);

        JLabel lblNewLabel_10 = new JLabel("出生年月：");
        lblNewLabel_10.setBounds(211, 166, 70, 15);
        frame.getContentPane().add(lblNewLabel_10);

        textField_1.setBounds(275, 163, 147, 21);
        frame.getContentPane().add(textField_1);
        textField_1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("输入的格式不正确".equals(textField_1.getText()))
                    textField_1.setText("");
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                if ("".equals(textField_1.getText())) textField_1.setText(textField_1.getText());
                if (textField_1.getText() != null && !textField_1.getText().isEmpty()) {
                    textField_7.setText(getConstellation(textField_1.getText()));
                }
                if (!isValidDatetime(textField_1.getText())) {
                    textField_1.setText("输入的格式不正确");
                }
            }
        });
        textField_1.setColumns(10);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public boolean isModify() {
        try {
            boolean flag;
            String username = textField.getText();
            int gender = comboBox_1.getSelectedIndex();
            String autograph = textArea.getText();
            String date = textField_1.getText();
            flag = !date.isEmpty() && isValidDatetime(date);
            String phoneNumber = textField_2.getText();
            flag = !phoneNumber.isEmpty() && isValidPhoneNumber(phoneNumber);
            String home = textField_3.getText();
            String email = textField_4.getText();
            flag = !email.isEmpty() && isValidEmail(email);
            String constellation = textField_7.getText();
            user.setHeadPortraitURL(test.getHeadPortraitURL());
            if (flag) {
                lbl3.setIcon(pic(user.getHeadPortraitURL(), 60, 60));
                user.setUsername(username); //用户名
                textField.setText(username);
                lbl4.setText(username);
                user.setGender(gender);//性别
                comboBox_1.setSelectedIndex(gender);
                textArea.setText(autograph);
                user.setAutograph(autograph);//签名
                textField_.setText(autograph);
                textField_3.setText(home);
                textField_1.setText(date);
                user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                textField_2.setText(phoneNumber);
                user.setPhoneNumber(phoneNumber);//电话号码
                user.setHome(home);//故乡
                textField_4.setText(email);
                user.setEmail(email);//电子邮箱
                textField_7.setText(constellation);
                user.setConstellation(constellation);//星座
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                user.setModifyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}