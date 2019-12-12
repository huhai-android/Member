package com.newdjk.member.ui.entity;

public class JPushDataMessageEntity {


    /**
     * data : {"RecordId":"1439"}
     * endStauts : 0
     * id : 23377
     * moduleType : 1
     * msgType : 2
     * serviceCode : 0
     * time : 2018-11-28 11:11:46
     * title : 图文问诊结束提醒
     * type : 23
     * userId : 35
     */

    private DataBean data;
    private int endStauts;
    private int id;
    private int moduleType;  //1图文问诊 2 视频问诊 3 在线续方 4 胎心监护 5 处方（处方订单） 6 处方（处方配送）
                            // 7 系统消息（评价提醒）8 系统消息（患者报道） 9 系统消息（系统消息） 10 随访（问卷调查）
                            //11 随访（健康宣教） 12 随访（康复提醒）

    private int msgType;   //1：订单提醒 2：服务提醒 3：处方提醒 4 随访提醒 5 系统消息
    private int serviceCode;
    private String time;
    private String title;
    private int type;
    private String userId;
    private boolean isRead;


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getEndStauts() {
        return endStauts;
    }

    public void setEndStauts(int endStauts) {
        this.endStauts = endStauts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class DataBean {
        /**
         * RecordId : 1439
         */

        private String RecordId;

        public String getRecordId() {
            return RecordId;
        }

        public void setRecordId(String RecordId) {
            this.RecordId = RecordId;
        }
    }
}
