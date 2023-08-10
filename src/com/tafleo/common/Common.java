package com.tafleo.common;

import com.tafleo.pojo.Message;
import com.tafleo.pojo.User;
import com.tafleo.util.JSONUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

//共同类
public class Common {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private String time;

    public Common() {
    }

    //传输最大字数
    public static final int Max = 65536;

    public String time() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        String hours = ifSmall(hour);
        String minutes = ifSmall(minute);
        String seconds = ifSmall(second);
        time = year + "年" + month + "月" + day + "日 " + hours + ":" + minutes + ":" + seconds;
        return time;
    }

    //判断是否小于9
    public String ifSmall(int number) {
        return number <= 9 ? "0" + number : "" + number;
    }

    //判断是否为电子邮箱
    public boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches(ClientBase.REGEX_MAIL, email);
        }
        return false;
    }

    //判断是否为电话号码
    public boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches(ClientBase.REGEX_MOBILE, phoneNumber);
        }
        return false;
    }

    //判断是否为正确日期格式
    public boolean isValidDatetime(String Datetime) {
        if ((Datetime != null) && (!Datetime.isEmpty())) {
            return Pattern.matches(ClientBase.REGEX_DATETIME, Datetime);
        }
        return false;
    }

    //根据生日判断星座
    public String getConstellation(String datetime) {
        try {
            boolean flag = isValidDatetime(datetime);
            if (flag) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(datetime);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DATE);
                String[] constellations = {"魔蝎座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座",
                        "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
                int[] divide = {19, 18, 20, 19, 20, 21, 22, 22, 22, 23, 22, 21};
                if (day <= divide[(month - 1)]) {
                    return constellations[(month - 1)];
                }
                if (month >= 12) {
                    month = 0;
                }
                return constellations[month];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //去除字符串前后面空格
    public String textTrim(String textContent) {
        textContent = textContent.trim();
        while (textContent.startsWith("　")) {//这里判断是不是全角空格
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        while (textContent.endsWith("　")) {
            textContent = textContent.substring(0, textContent.length() - 1).trim();
        }
        return textContent;
    }

    //html转text
    public String getHtmlText(String html) {
        String textContent = html.substring(html.indexOf("<body>") + 6, html.indexOf("</body>"));
        return textTrim(textContent);
    }

    //text转html
    public String getHtml(String text) {
        String start = "<html><head></head><body>";
        String end = "</body></html>";
        return start + text + end;
    }

    //消息打包
    public String messagePack(User me, User user) {
        String htmlText = "";
        try {
            String filePath = "res//messageList//" + me.getChatNumber() + "//" + user.getChatNumber() + ".json";
            File messageFile = new File(filePath);
            InputStream is = new FileInputStream(messageFile);
            byte[] bytes = new byte[Max];
            int len = 0;
            String list = "";
            while ((len = is.read(bytes)) != -1) {
                list = new String(bytes, 0, len);
            }
            List<Message> messageList = JSONUtil.JSONArrayToList(list, Message.class);
            for (int i = 0; i < messageList.size(); i++) {
                Message message = messageList.get(i);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getDate());
                String fromName = message.getFromChatNumber();
                String toName = message.getToChatNumber();
                String messages = "";
                if (message.getType() == 0) {
                    messages = message.getMessage();
                } else {
                    messages = "发送文件--->" + message.getMessage();
                }
                htmlText = htmlText + "<center>" + time + "</center>" + "发送人："+fromName + "<br>接收人：" + toName + "<br>消息内容：" + messages+"<br><br>";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHtml(htmlText);
    }

    public void modifyFriend(User me, List<User> userList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            sb.append(u.getChatNumber() + ",");
        }
        me.setFriendChatNumber(sb.toString());
    }


    public void copyUser(User user, User user1) {
        user.setUid(user1.getUid());
        user.setChatNumber(user1.getChatNumber());
        user.setUsername(user1.getUsername());
        user.setPassword(user1.getPassword());
        user.setAutograph(user1.getAutograph());
        user.setGender(user1.getGender());
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setBirthday(user1.getBirthday());
        user.setRecentIP(user1.getRecentIP());
        user.setRecentTime(user1.getRecentTime());
        user.setChatStatus(user1.getChatStatus());
        user.setFriendChatNumber(user1.getFriendChatNumber());
        user.setHome(user1.getHome());
        user.setEmail(user1.getEmail());
        user.setConstellation(user1.getConstellation());
        user.setHeadPortraitURL(user1.getHeadPortraitURL());
        user.setAddDate(user1.getAddDate());
        user.setModifyDate(user1.getModifyDate());
    }
}
