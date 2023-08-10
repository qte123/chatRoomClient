package com.tafleo.view.menu;

import com.tafleo.client.action.ActionClient;
import com.tafleo.pojo.User;

import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Register extends ViewImpl {
    private JFrame frame;
    private JTextField textField;//用户名
    private JPasswordField passwordField;//密码
    private JTextArea textArea;//签名
    private JComboBox comboBox;//性别
    private JTextField textField_1;//电话号码
    private JTextField textField_5;//生日
    private JTextField textField_2;//故乡
    private JTextField textField_3;//电子邮箱
    private JTextField textField_4;//星座
    private JLabel lblNewLabel;
    private JTextField text;
    private JFrame frmTt;
    private User user;

    public Register(JTextField text, JFrame frmTt) {
        this.text = text;
        this.frmTt = frmTt;
    }

    public void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    user = new User();
                    register();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean registerAccount() {
        try {
            String username = textField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String autograph = textArea.getText();
            int gender = comboBox.getSelectedIndex();
            String phoneNumber = textField_1.getText();
            String b = textField_5.getText();
            String home = textField_2.getText();
            String email = textField_3.getText();
            String constellation = textField_4.getText();
            if (!username.isEmpty() && !password.isEmpty() && !(phoneNumber.isEmpty() | b.isEmpty()) && !email.isEmpty()) {
                if (isValidPhoneNumber(phoneNumber) && isValidEmail(email) && isValidDatetime(b)) {
                    Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(b);
                    //String d = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
                    new Thread(new ActionClient("*ReGiStEr%")).start();
                    user.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAutograph(autograph);
                    user.setGender(gender);
                    user.setPhoneNumber(phoneNumber);
                    user.setBirthday(birthday);
                    user.setHome(home);
                    user.setEmail(email);
                    user.setConstellation(constellation);
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    user.setAddDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
                    String chatNumber = user.getChatNumber();
                    text.setText(chatNumber);
                    System.out.println(user.toString());
                    new Thread(new ActionClient("save", JSONUtil.entityToJSON(user))).start();
                    return chatNumber != null;
                } else {
                    String no = "输入的格式不正确";
                    if (!isValidPhoneNumber(phoneNumber))
                        textField_1.setText(no);
                    if (!isValidDatetime(b))
                        textField_5.setText(no);
                    if (!isValidEmail(email))
                        textField_3.setText(no);
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void register() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setTitle("注册页面");
        frame.setBounds(100, 100, 440, 490);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lbltt = new JLabel("欢迎注册TT号");
        lbltt.setFont(new Font("宋体", Font.PLAIN, 25));
        lbltt.setBounds(133, 10, 159, 30);
        frame.getContentPane().add(lbltt);

        lblNewLabel = new JLabel("");
        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                new Head(user,lblNewLabel);
            }
        });
        lblNewLabel.setIcon(pic(DEFAULT, 80, 80));
        lblNewLabel.setBounds(170, 50, 80, 80);
        frame.getContentPane().add(lblNewLabel);
        user.setHeadPortraitURL(DEFAULT);

        JLabel lblNewLabel_1 = new JLabel("用户名：");
        lblNewLabel_1.setBounds(103, 162, 54, 15);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("性别：");
        lblNewLabel_2.setBounds(103, 218, 54, 15);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("签名：");
        lblNewLabel_3.setBounds(103, 273, 54, 15);
        frame.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("电话号码：");
        lblNewLabel_4.setBounds(103, 300, 70, 15);
        frame.getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("故乡：");
        lblNewLabel_5.setBounds(103, 325, 54, 15);
        frame.getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("电子邮箱：");
        lblNewLabel_6.setBounds(103, 350, 70, 15);
        frame.getContentPane().add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("星座：");
        lblNewLabel_7.setBounds(103, 375, 54, 15);
        frame.getContentPane().add(lblNewLabel_7);

        JButton btnNewButton = new JButton("注册账号");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean flag = registerAccount();
                if (flag) {
                    frame.dispose();
                    new Dialog(user.getChatNumber(), frmTt);
                } else {
                    new Error(REGISTER_FAIL);
                }
            }
        });

        JLabel lblNewLabel_8 = new JLabel("密码：");
        lblNewLabel_8.setBounds(103, 190, 54, 15);
        frame.getContentPane().add(lblNewLabel_8);

        JLabel lblNewLabel_9 = new JLabel("生日：");
        lblNewLabel_9.setBounds(103, 243, 54, 15);
        frame.getContentPane().add(lblNewLabel_9);
        btnNewButton.setBounds(133, 413, 159, 23);
        frame.getContentPane().add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(160, 159, 159, 21);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"男", "女", "隐藏"}));
        comboBox.setBounds(160, 215, 159, 21);
        frame.getContentPane().add(comboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(160, 268, 159, 24);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        textField_1 = new JTextField();
        textField_1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("输入的格式不正确".equals(textField_1.getText())) textField_1.setText("");
            }
        });
        textField_1.setBounds(160, 297, 159, 21);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(160, 322, 159, 21);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("输入的格式不正确".equals(textField_3.getText())) textField_3.setText("");
            }
        });
        textField_3.setBounds(160, 347, 159, 21);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(160, 372, 159, 21);
        frame.getContentPane().add(textField_4);

        textField_4.setColumns(10);
        textField_4.setEditable(false);
        passwordField = new JPasswordField();
        passwordField.setBounds(160, 187, 159, 21);
        frame.getContentPane().add(passwordField);

        textField_5 = new JTextField("yyyy-MM-dd");
        textField_5.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("yyyy-MM-dd".equals(textField_5.getText()) || "输入的格式不正确".equals(textField_5.getText()))
                    textField_5.setText("");
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                if ("".equals(textField_5.getText())) textField_5.setText("yyyy-MM-dd");
                if (textField_5.getText() != null && !textField_5.getText().isEmpty() && !textField_5.getText().equals("yyyy-MM-dd")) {
                    textField_4.setText(getConstellation(textField_5.getText()));
                }
            }
        });
        textField_5.setBounds(160, 240, 159, 21);
        frame.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        JLabel lblNewLabel_10 = new JLabel("点击头像选择头像");
        lblNewLabel_10.setBounds(160, 134, 105, 15);
        frame.getContentPane().add(lblNewLabel_10);
        frame.setVisible(true);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
