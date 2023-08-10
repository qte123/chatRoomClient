package com.tafleo.pojo;

import java.util.Date;

//消息类
public class Message {
    private String uid;//uid
    private String message;//消息
    private String fromChatNumber;//发送方聊天号
    private String toChatNumber;//接收方聊天号
    private Date date;//日期
    private int ifDelete;//消息是否删除 1已删除 0未删除
    private int type;//消息类型，0是消息，1是文件
    private Date addDate;//添加日期

    public Message() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromChatNumber() {
        return fromChatNumber;
    }

    public void setFromChatNumber(String fromChatNumber) {
        this.fromChatNumber = fromChatNumber;
    }

    public String getToChatNumber() {
        return toChatNumber;
    }

    public void setToChatNumber(String toChatNumber) {
        this.toChatNumber = toChatNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIfDelete() {
        return ifDelete;
    }

    public void setIfDelete(int ifDelete) {
        this.ifDelete = ifDelete;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}