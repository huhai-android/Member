package com.newdjk.member.ui.entity;

import com.tencent.TIMMessage;

import java.util.List;

public class ConversationEntity {
   private TIMMessage timMessage;
   private long unReadNum = 0;
   private  long timestamp = 0;
   private String lastMsg;

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public void setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
    }

    public long getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }
}
