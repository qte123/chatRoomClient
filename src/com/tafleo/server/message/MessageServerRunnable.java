package com.tafleo.server.message;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;
import com.tafleo.common.Common;
import com.tafleo.view.menu.ChatBox;
import com.tafleo.view.menu.People;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.DatagramPacket;
import java.util.List;

public class MessageServerRunnable extends ServerImpl implements Runnable {
    private DatagramPacket packet;//建立数据包
    private String IM;//存放的IP和消息
    private String message;//存放的消息
    private String chatNumber;
    private String fromIP;//发送方电脑的IP地址
    private List<ChatBox> chatBoxList;
    private User user;
    private List<User> userList;
    private People people;
    private boolean flag;

    public MessageServerRunnable(DatagramPacket packet, List<ChatBox> chatBoxList, User user, List<User> userList, People people) {
        this.packet = packet;
        this.chatBoxList = chatBoxList;
        this.user = user;
        this.userList = userList;
        this.people = people;
    }

    @Override
    public void acceptMessage() {
        IM = new String(packet.getData(), 0, packet.getLength());
        fromIP = IM.substring(0, IM.indexOf("#"));
        flag = Boolean.parseBoolean(IM.substring(IM.indexOf("#") + 1, IM.indexOf("@")));
        message = IM.substring(IM.indexOf("@") + 1, IM.indexOf("*^%"));
        chatNumber = IM.substring(IM.indexOf("*^%") + 3);
        System.out.println(flag);
        if (!flag) {
            User u = null;
            if (!user.getChatNumber().equals(chatNumber)) {
                for (int i = 0; i < userList.size(); i++) {
                    u = userList.get(i);
                    if (chatNumber.equals(u.getChatNumber())) {
                        break;
                    }
                }
                people.getFrame().setVisible(true);
                people.getFrame().setState(Frame.NORMAL);
                JEditorPane editorPane = people.getEditorPane();
                String lastHtml = editorPane.getText();
                String htmlText = getHtmlText(lastHtml);
                String time = time();
                String img = "<img src=\"file:" + u.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + "昵称：" + u.getUsername() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                String text1 = "<center>" + time + "</center>" + img + message;
                String newText = htmlText + text1;
                String newHtml = getHtml(newText);
                editorPane.setText(newHtml);
                InputStream is = null;
                try {
                    is = new FileInputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int len;
                String last = "";
                byte[] bytes = new byte[Max];
                try {
                    while (((len = is.read(bytes)) != -1)) {
                        last = new String(bytes, 0, len);
                    }
                    is.close();
                    String lastText123 = getHtmlText(last);
                    String and = getHtml(lastText123 + newText);
                    System.out.println(and);
                    OutputStream peopleMessage = new FileOutputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
                    peopleMessage.write(and.getBytes());
                    peopleMessage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editorPane.setSelectionStart(editorPane.getText().length());
                JScrollPane scrollPane = people.getScrollPane();
                JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
            }
        } else {
            for (int i = 0; i < userList.size(); i++) {
                ChatBox chatBox = chatBoxList.get(i);
                JEditorPane editorPane = chatBox.getEditorPane();
                User u = chatBox.getUser1();
                if (chatNumber.equals(u.getChatNumber())) {
                    chatBox.getFrame().setVisible(true);
                    chatBox.getFrame().setState(Frame.NORMAL);
                    String lastHtml = editorPane.getText();
                    String htmlText = getHtmlText(lastHtml);
                    String time = time();
                    String img = "<img src=\"file:" + u.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + "昵称：" + u.getUsername() + "：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    String text1 = "<center>" + time + "</center>" + img + message;
                    String newText = htmlText + text1;
                    String newHtml = getHtml(newText);
                    editorPane.setText(newHtml);
                    InputStream is = null;
                    try {
                        is = new FileInputStream("res//messageList//" + user.getChatNumber() + "//" + u.getChatNumber() + ".txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int len;
                    String last = "";
                    byte[] bytes = new byte[Max];
                    try {
                        while (((len = is.read(bytes)) != -1)) {
                            last = new String(bytes, 0, len);
                        }
                        is.close();
                        String lastText123 = getHtmlText(last);
                        String and = getHtml(lastText123 + newText);
                        System.out.println(and);
                        OutputStream messageList = new FileOutputStream("res//messageList//" + user.getChatNumber() + "//" + u.getChatNumber() + ".txt");
                        messageList.write(and.getBytes());
                        messageList.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editorPane.setSelectionStart(editorPane.getText().length());
                    JScrollPane scrollPane = chatBox.getScrollPane();
                    JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                    scrollBar.setValue(scrollBar.getMaximum());
                }
            }
        }
        time();
        System.out.print(fromIP + "：" + message);
    }


    @Override
    public void run() {
        acceptMessage();
    }
}
