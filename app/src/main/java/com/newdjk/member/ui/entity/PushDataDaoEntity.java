package com.newdjk.member.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class PushDataDaoEntity {
    @Id(autoincrement = true)
    private Long id;
    public String title;
    public String content;
    public String data;
    public Date time ;
    public boolean isRead;
    public String accountId;
    private int msgType;
    private int moduleType;
    private int msgId;
    public int getMsgId() {
        return this.msgId;
    }
    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
    public int getModuleType() {
        return this.moduleType;
    }
    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }
    public int getMsgType() {
        return this.msgType;
    }
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public boolean getIsRead() {
        return this.isRead;
    }
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 976671102)
    public PushDataDaoEntity(Long id, String title, String content, String data,
            Date time, boolean isRead, String accountId, int msgType,
            int moduleType, int msgId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.data = data;
        this.time = time;
        this.isRead = isRead;
        this.accountId = accountId;
        this.msgType = msgType;
        this.moduleType = moduleType;
        this.msgId = msgId;
    }
    @Generated(hash = 1914469244)
    public PushDataDaoEntity() {
    }




}
