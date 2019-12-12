package com.newdjk.member.utils;

import com.tencent.TIMMessage;

public class MyTIMMessage {
    private String sendTarget;
    private long MsgTimestamp;
    private TIMMessage timMessage;
    private boolean isLocalMessage = false;
    private boolean isRevoke = false;

    public boolean isRevoke() {
        return isRevoke;
    }

    public void setRevoke(boolean revoke) {
        isRevoke = revoke;
    }

    public String getSendTarget() {
        return sendTarget;
    }

    public void setSendTarget(String sendTarget) {
        this.sendTarget = sendTarget;
    }

    public long getMsgTimestamp() {
        return MsgTimestamp;
    }

    public void setMsgTimestamp(long msgTimestamp) {
        MsgTimestamp = msgTimestamp;
    }

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public void setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
    }

    public boolean isLocalMessage() {
        return isLocalMessage;
    }

    public void setLocalMessage(boolean localMessage) {
        isLocalMessage = localMessage;
    }
}
