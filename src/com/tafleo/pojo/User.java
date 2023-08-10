package com.tafleo.pojo;

import java.util.Date;
import java.util.List;

//用户类
public class User {
    private String uid;//uid
    private String chatNumber;//聊天号
    private String username;//用户名
    private String password;//密码
    private String autograph;//签名
    private int gender;//性别
    private String phoneNumber;//电话号码
    private Date birthday;//出生年月
    private String recentIP;//最近登录IP
    private Date recentTime;//最近登录时间
    private int chatStatus;//登录状态
    private String friendChatNumber;//好友聊天号
    private String home;//故乡
    private String email;//电子邮箱
    private String constellation;//星座
    private String headPortraitURL;//头像文件地址
    private Date addDate;//添加日期
    private Date modifyDate;//修改日期
    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChatNumber() {
        return chatNumber;
    }

    public void setChatNumber(String chatNumber) {
        this.chatNumber = chatNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRecentIP() {
        return recentIP;
    }

    public void setRecentIP(String recentIP) {
        this.recentIP = recentIP;
    }

    public Date getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(Date recentTime) {
        this.recentTime = recentTime;
    }

    public int getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(int chatStatus) {
        this.chatStatus = chatStatus;
    }

    public String getFriendChatNumber() {
        return friendChatNumber;
    }

    public void setFriendChatNumber(String friendChatNumber) {
        this.friendChatNumber = friendChatNumber;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getHeadPortraitURL() {
        return headPortraitURL;
    }

    public void setHeadPortraitURL(String headPortraitURL) {
        this.headPortraitURL = headPortraitURL;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", chatNumber='" + chatNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", autograph='" + autograph + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", recentIP='" + recentIP + '\'' +
                ", recentTime=" + recentTime +
                ", chatStatus=" + chatStatus +
                ", friendChatNumber='" + friendChatNumber + '\'' +
                ", home='" + home + '\'' +
                ", email='" + email + '\'' +
                ", constellation='" + constellation + '\'' +
                ", headPortraitURL='" + headPortraitURL + '\'' +
                ", addDate=" + addDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}