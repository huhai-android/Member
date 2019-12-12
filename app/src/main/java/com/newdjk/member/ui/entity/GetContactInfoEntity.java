package com.newdjk.member.ui.entity;

import java.io.Serializable;

public class GetContactInfoEntity implements Serializable {

    /**
     * Code : 0
     * Message :
     * Data : {"Name":"zhangsan","Mobile":"17773930653","Address":"河北保定市","WorkDay":"09:00-18:00","NonWorkDay":"10:00-17:00"}
     */

    private int Code;
    private String Message;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * Name : zhangsan
         * Mobile : 17773930653
         * Address : 河北保定市
         * WorkDay : 09:00-18:00
         * NonWorkDay : 10:00-17:00
         */

        private String Name;
        private String Mobile;
        private String Address;
        private String WorkDay;
        private String NonWorkDay;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getWorkDay() {
            return WorkDay;
        }

        public void setWorkDay(String WorkDay) {
            this.WorkDay = WorkDay;
        }

        public String getNonWorkDay() {
            return NonWorkDay;
        }

        public void setNonWorkDay(String NonWorkDay) {
            this.NonWorkDay = NonWorkDay;
        }
    }
}
