package com.newdjk.member.ui.entity;

public class GetReportDetailEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"AskId":11,"MonitorId":14,"AskNo":"2000011","ScType":2,"ScTypeName":"NST","Score":"7","Result":"","Advice":"测试7","DoctorName":"张飞","ReadTime":"2018-11-09 17:30:54","PatientName":"香囊记","Age":48,"Weeks":"孕40周+0天","Mobile":"15088886661","Sex":1,"Duration":5,"BeginDate":"2018-11-06 17:23:34","EndDate":"2018-11-06 17:23:39","MonitorTime":"2018-11-06 17:23:39"}
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
         * AskId : 11
         * MonitorId : 14
         * AskNo : 2000011
         * ScType : 2
         * ScTypeName : NST
         * Score : 7
         * Result :
         * Advice : 测试7
         * DoctorName : 张飞
         * ReadTime : 2018-11-09 17:30:54
         * PatientName : 香囊记
         * Age : 48
         * Weeks : 孕40周+0天
         * Mobile : 15088886661
         * Sex : 1
         * Duration : 5
         * BeginDate : 2018-11-06 17:23:34
         * EndDate : 2018-11-06 17:23:39
         * MonitorTime : 2018-11-06 17:23:39
         */

        private int AskId;
        private int MonitorId;
        private String AskNo;
        private int ScType;
        private String ScTypeName;
        private String Score;
        private String Result;
        private String Advice;
        private String DoctorName;
        private String ReadTime;
        private String PatientName;
        private int Age;
        private String Weeks;
        private String Mobile;
        private int Sex;
        private int Duration;
        private String BeginDate;
        private String EndDate;
        private String MonitorTime;

        public int getAskId() {
            return AskId;
        }

        public void setAskId(int AskId) {
            this.AskId = AskId;
        }

        public int getMonitorId() {
            return MonitorId;
        }

        public void setMonitorId(int MonitorId) {
            this.MonitorId = MonitorId;
        }

        public String getAskNo() {
            return AskNo;
        }

        public void setAskNo(String AskNo) {
            this.AskNo = AskNo;
        }

        public int getScType() {
            return ScType;
        }

        public void setScType(int ScType) {
            this.ScType = ScType;
        }

        public String getScTypeName() {
            return ScTypeName;
        }

        public void setScTypeName(String ScTypeName) {
            this.ScTypeName = ScTypeName;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }

        public String getAdvice() {
            return Advice;
        }

        public void setAdvice(String Advice) {
            this.Advice = Advice;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String DoctorName) {
            this.DoctorName = DoctorName;
        }

        public String getReadTime() {
            return ReadTime;
        }

        public void setReadTime(String ReadTime) {
            this.ReadTime = ReadTime;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getWeeks() {
            return Weeks;
        }

        public void setWeeks(String Weeks) {
            this.Weeks = Weeks;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getDuration() {
            return Duration;
        }

        public void setDuration(int Duration) {
            this.Duration = Duration;
        }

        public String getBeginDate() {
            return BeginDate;
        }

        public void setBeginDate(String BeginDate) {
            this.BeginDate = BeginDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getMonitorTime() {
            return MonitorTime;
        }

        public void setMonitorTime(String MonitorTime) {
            this.MonitorTime = MonitorTime;
        }
    }
}
