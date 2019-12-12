package com.newdjk.member.ui.entity;

/*
 *  @项目名：  BdMember
 *  @包名：    com.newdjk.bdmember.bean
 *  @文件名:   VipPushEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/7/30 14:11
 *  @描述：
 */
public class VipPushEntity {

    /**
     * data : {"ApRecordId":"201"}
     * endStauts : 0
     * id : 4114
     * moduleType : 14
     * msgType : 2
     * serviceCode : 0
     * time : 2019-07-29 15:25:55
     * title : VIP预约进度提醒
     * type : 235
     * userId : 79
     */

    private DataBean data;
    private int endStauts;
    private int id;
    private int moduleType;
    private int msgType;
    private int serviceCode;
    private String time;
    private String title;
    private int type;
    private String userId;

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
         * ApRecordId : 201
         */

        private String ApRecordId;

        public String getApRecordId() {
            return ApRecordId;
        }

        public void setApRecordId(String ApRecordId) {
            this.ApRecordId = ApRecordId;
        }
    }
}
