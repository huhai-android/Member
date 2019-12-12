package com.newdjk.member.ui.entity;

/*
 *  @项目名：  Member
 *  @包名：    com.newdjk.member.ui.entity
 *  @文件名:   CheckTable
 *  @创建者:   huhai
 *  @创建时间:  2019/4/2 17:26
 *  @描述：
 */
public class CheckTableEntity {

    /**
     * data : {"CensorateRecordId":"104"}
     * endStauts : 0
     * id : 25069
     * moduleType : 9
     * msgType : 1
     * serviceCode : 0
     * time : 2019-04-02 17:13:05
     * title : 检查单提醒
     * type : 225
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
         * CensorateRecordId : 104
         */

        private String CensorateRecordId;

        public String getCensorateRecordId() {
            return CensorateRecordId;
        }

        public void setCensorateRecordId(String CensorateRecordId) {
            this.CensorateRecordId = CensorateRecordId;
        }
    }
}
