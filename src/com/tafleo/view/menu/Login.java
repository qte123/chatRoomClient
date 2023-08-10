package com.tafleo.view.menu;

import java.awt.EventQueue;

import com.tafleo.client.action.ActionClient;
import com.tafleo.pojo.User;
import com.tafleo.server.action.ActionServer;
import com.tafleo.server.file.samll.FileServer;
import com.tafleo.server.file.big.ReceiveFile;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Login extends ViewImpl {

    private JFrame frmTt;
    private JTextField textField;
    private JPasswordField passwordField;
    private User user;
    private JLabel lblNewLabel_2;
    private Sidebar sidebar;
    private List<User> userList = new ArrayList<>();
    ;

    public Login() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    user = new User();
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean identification() {
        String chatNumber = textField.getText();
        String password = String.valueOf(passwordField.getPassword());
        passwordField.setText("");
        if ("".equals(chatNumber)) {
            textField.setText("输入的账号不能为空");
            return false;
        } else if ("".equals(password)) {
            return false;
        } else {
            user.setChatNumber(chatNumber);
            user.setPassword(password);
            String json = JSONUtil.entityToJSON(user);
            new Thread(new ActionClient("login", json)).start();
            //判断列表中是否有重复的对象
            for (int i = 0; i < userList.size() - 1; i++) {
                for (int j = userList.size() - 1; j > i; j--) {
                    if (userList.get(j).equals(userList.get(i))) {
                        userList.remove(j);
                    }
                }
            }
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user.getChatStatus() == 0) {
                new Error(1);
                return false;
            }
            new Thread(new ActionClient("successful", user.getChatNumber())).start();
            return true;
        }
    }


    private void login() {
        frmTt = new JFrame();
        frmTt.setResizable(false);
        frmTt.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frmTt.setTitle("TT聊天室");
        frmTt.setBounds(100, 100, 450, 300);
        frmTt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTt.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("TT号：");
        lblNewLabel.setBounds(117, 148, 54, 15);
        frmTt.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("密码：");
        lblNewLabel_1.setBounds(117, 176, 54, 15);
        frmTt.getContentPane().add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(165, 145, 168, 21);
        frmTt.getContentPane().add(textField);
        textField.setColumns(10);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("输入的账号不能为空".equals(textField.getText())) textField.setText("");
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                if (!Objects.equals("", textField.getText()) && !"输入的账号不能为空".equals(textField.getText())) {
                    new Thread(new ActionClient("start", textField.getText())).start();
                }
            }
        });
        passwordField = new JPasswordField();
        passwordField.setBounds(165, 173, 168, 21);
        frmTt.getContentPane().add(passwordField);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                passwordField.setText("");
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    if (identification()) {
                        frmTt.dispose();
                        sidebar = new Sidebar(user, userList);
                    }
                }
            }
        });
        JButton btnNewButton = new JButton("登录");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (identification()) {
                    frmTt.dispose();
                    sidebar = new Sidebar(user, userList);
                }
            }
        });
        btnNewButton.setBounds(117, 204, 216, 30);
        frmTt.getContentPane().add(btnNewButton);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(174, 24, 100, 100);
        lblNewLabel_2.setIcon(pic(WINDOW_ICON_FILE, 100, 100));
        frmTt.getContentPane().add(lblNewLabel_2);
        Register register = new Register(textField, frmTt);
        new Thread(new ActionServer(register, user, lblNewLabel_2, userList)).start();
        new Thread(new FileServer()).start();
        new Thread(new ReceiveFile(user)).start();
        JLabel lblNewLabel_3 = new JLabel("注册账号");
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                register.start();
                frmTt.setVisible(false);
            }
        });
        lblNewLabel_3.setBounds(10, 246, 54, 15);
        frmTt.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("修改密码");
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                new Modify();
            }
        });
        lblNewLabel_4.setBounds(380, 246, 54, 15);
        frmTt.getContentPane().add(lblNewLabel_4);
        frmTt.setVisible(true);
    }
}
