package com.newdjk.member.ui.entity;

/*
 *  @项目名：  Member
 *  @包名：    com.newdjk.member.ui.entity
 *  @文件名:   CheckTable
 *  @创建者:   huhai
 *  @创建时间:  2019/4/2 17:26
 *  @描述：
 */
public class ZhuanzhenEntity {


    /**
     * type : 225
     * data : {"ReferralRecordId":"163","ReferralTime":"2019-04-03 10:48:31"}
     * serviceCode : 0
     * endStauts : 0
     * time : 2019-04-03 10:48:31
     * userId : 788
     * title : 转诊提醒
     * msgType : 5
     * moduleType : 9
     * id : 25087
     */

    private int type;
    private DataBean data;
    private int serviceCode;
    private int endStauts;
    private String time;
    private String userId;
    private String title;
    private int msgType;
    private int moduleType;
    private int id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public int getEndStauts() {
        return endStauts;
    }

    public void setEndStauts(int endStauts) {
        this.endStauts = endStauts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class DataBean {
        /**
         * ReferralRecordId : 163
         * ReferralTime : 2019-04-03 10:48:31
         */

        private String ReferralRecordId;
        private String ReferralTime;

        public String getReferralRecordId() {
            return ReferralRecordId;
        }

        public void setReferralRecordId(String ReferralRecordId) {
            this.ReferralRecordId = ReferralRecordId;
        }

        public String getReferralTime() {
            return ReferralTime;
        }

        public void setReferralTime(String ReferralTime) {
            this.ReferralTime = ReferralTime;
        }
    }
}
