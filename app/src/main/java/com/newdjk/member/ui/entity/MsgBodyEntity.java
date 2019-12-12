package com.newdjk.member.ui.entity;

public class MsgBodyEntity {
    private String MsgType;
    private MsgContentEntity MsgContent;


    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public MsgContentEntity getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(MsgContentEntity msgContent) {
        MsgContent = msgContent;
    }


}
