package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2018/10/23.
 */

public class WXLoginInfo {


    /**
     * Code : 0
     * Message :
     * Data : {"AccountId":35,"Mobile":"13823230159","Account":"13823230159","Password":"F63B072D90D0A8ECCC6C2F6948BCDDC4","Source":0,"AppId":null,"Invalid":0,"CreateTime":"2018-09-27 18:01:31","UpdateTime":"2018-10-15 14:03:09"}
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

    public static class DataBean {
        /**
         * AccountId : 35
         * Mobile : 13823230159
         * Account : 13823230159
         * Password : F63B072D90D0A8ECCC6C2F6948BCDDC4
         * Source : 0
         * AppId : null
         * Invalid : 0
         * CreateTime : 2018-09-27 18:01:31
         * UpdateTime : 2018-10-15 14:03:09
         */

        private int AccountId;
        private String Mobile;
        private String Account;
        private String Password;
        private int Source;
        private Object AppId;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String Account) {
            this.Account = Account;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public int getSource() {
            return Source;
        }

        public void setSource(int Source) {
            this.Source = Source;
        }

        public Object getAppId() {
            return AppId;
        }

        public void setAppId(Object AppId) {
            this.AppId = AppId;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
