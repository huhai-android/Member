package com.newdjk.member.update;

/**
 * Created by Struggle on 2018/10/19.
 */

public class AppUpdateInfo {


    /**
     * Code : 0
     * Message :
     * Data : {"AppCode":null,"AppName":null,"AppVersion":1,"AppPath":null,"Remark":null,"MustUpdate":0}
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
         * AppCode : null
         * AppName : null
         * AppVersion : 1
         * AppPath : null
         * Remark : null
         * MustUpdate : 0
         */

        private String AppCode;
        private String AppName;
        private int AppVersion;
        private String AppPath;
        private String Remark;
        private int MustUpdate;

        public String getAppCode() {
            return AppCode;
        }

        public void setAppCode(String AppCode) {
            this.AppCode = AppCode;
        }

        public String getAppName() {
            return AppName;
        }

        public void setAppName(String AppName) {
            this.AppName = AppName;
        }

        public int getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(int AppVersion) {
            this.AppVersion = AppVersion;
        }

        public String getAppPath() {
            return AppPath;
        }

        public void setAppPath(String AppPath) {
            this.AppPath = AppPath;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public int getMustUpdate() {
            return MustUpdate;
        }

        public void setMustUpdate(int MustUpdate) {
            this.MustUpdate = MustUpdate;
        }
    }
}
