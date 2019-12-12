package com.newdjk.member.ui.entity;

import java.util.List;

public class AddressEntity {


    /**
     * Code : 0
     * Message :
     * Data : [{"PatientAddId":12,"PatientId":1,"AreaId":1,"AreaName":"2","StreetId":0,"StreetName":null,"LinkMan":"2","LinkManMobile":"2","Address":"吉林省通化市集安市","PostalCode":"2","IsDefault":0,"CreateTime":"2018-09-22 12:08:40"},{"PatientAddId":10,"PatientId":1,"AreaId":1,"AreaName":"2","StreetId":0,"StreetName":null,"LinkMan":"2","LinkManMobile":"2","Address":"吉林省通化市集安市","PostalCode":"2","IsDefault":0,"CreateTime":"2018-09-22 12:08:06"}]
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * PatientAddId : 12
         * PatientId : 1
         * AreaId : 1
         * AreaName : 2
         * StreetId : 0
         * StreetName : null
         * LinkMan : 2
         * LinkManMobile : 2
         * Address : 吉林省通化市集安市
         * PostalCode : 2
         * IsDefault : 0
         * CreateTime : 2018-09-22 12:08:40
         */

        private int PatientAddId;
        private int PatientId;
        private int AreaId;
        private String AreaName;
        private int StreetId;
        private String StreetName;
        private String LinkMan;
        private String LinkManMobile;
        private String Address;
        private String PostalCode;
        private int IsDefault;
        private String CreateTime;

        public int getPatientAddId() {
            return PatientAddId;
        }

        public void setPatientAddId(int PatientAddId) {
            this.PatientAddId = PatientAddId;
        }

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
        }

        public int getAreaId() {
            return AreaId;
        }

        public void setAreaId(int AreaId) {
            this.AreaId = AreaId;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public int getStreetId() {
            return StreetId;
        }

        public void setStreetId(int StreetId) {
            this.StreetId = StreetId;
        }

        public String getStreetName() {
            return StreetName;
        }

        public void setStreetName(String StreetName) {
            this.StreetName = StreetName;
        }

        public String getLinkMan() {
            return LinkMan;
        }

        public void setLinkMan(String LinkMan) {
            this.LinkMan = LinkMan;
        }

        public String getLinkManMobile() {
            return LinkManMobile;
        }

        public void setLinkManMobile(String LinkManMobile) {
            this.LinkManMobile = LinkManMobile;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPostalCode() {
            return PostalCode;
        }

        public void setPostalCode(String PostalCode) {
            this.PostalCode = PostalCode;
        }

        public int getIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(int IsDefault) {
            this.IsDefault = IsDefault;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
