package com.newdjk.member.ui.entity;

import java.util.List;

public class GetMonitorPageEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"Total":5,"ReturnData":[{"Id":103,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:41:37","AskTime":null},{"Id":102,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:39:59","AskTime":null},{"Id":101,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:39:31","AskTime":null},{"Id":100,"TLong":1202,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:54:52","Status":-1,"CreateTime":"2018-11-16 10:39:06","AskTime":null},{"Id":99,"TLong":1202,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:54:52","Status":-1,"CreateTime":"2018-11-16 10:39:03","AskTime":null}]}
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
         * Total : 5
         * ReturnData : [{"Id":103,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:41:37","AskTime":null},{"Id":102,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:39:59","AskTime":null},{"Id":101,"TLong":1234,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:55:24","Status":-1,"CreateTime":"2018-11-16 10:39:31","AskTime":null},{"Id":100,"TLong":1202,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:54:52","Status":-1,"CreateTime":"2018-11-16 10:39:06","AskTime":null},{"Id":99,"TLong":1202,"Weeks":"孕40周+0天","StartTime":"2018-11-05 13:34:50","EndTime":"2018-11-05 13:54:52","Status":-1,"CreateTime":"2018-11-16 10:39:03","AskTime":null}]
         */

        private int Total;
        private List<ReturnDataBean> ReturnData;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public List<ReturnDataBean> getReturnData() {
            return ReturnData;
        }

        public void setReturnData(List<ReturnDataBean> ReturnData) {
            this.ReturnData = ReturnData;
        }

        public static class ReturnDataBean {
            /**
             * Id : 103
             * TLong : 1234
             * Weeks : 孕40周+0天
             * StartTime : 2018-11-05 13:34:50
             * EndTime : 2018-11-05 13:55:24
             * Status : -1
             * CreateTime : 2018-11-16 10:41:37
             * AskTime : null
             */

            private int Id;
            private int TLong;
            private String Weeks;
            private String StartTime;
            private String EndTime;
            private int Status;
            private String CreateTime;
            private String AskTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getTLong() {
                return TLong;
            }

            public void setTLong(int TLong) {
                this.TLong = TLong;
            }

            public String getWeeks() {
                return Weeks;
            }

            public void setWeeks(String Weeks) {
                this.Weeks = Weeks;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getAskTime() {
                return AskTime;
            }

            public void setAskTime(String AskTime) {
                this.AskTime = AskTime;
            }
        }
    }
}
