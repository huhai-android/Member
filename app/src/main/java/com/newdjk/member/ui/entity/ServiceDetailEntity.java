package com.newdjk.member.ui.entity;

public class ServiceDetailEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"PatientName":"巢","PicturePath":"http://userimage.newstarthealth.cn/pat/0/45.jpg?dt=201811123546693","GestationWeeks":"孕40周+0天","ServiceWeeks":"90天","DeviceNo":"LCeFM6018051010","HospitalName":"保定市人民医院","RentExpires":null,"BeginTime":"2018-11-08 18:36:30","EndTime":"2019-02-08 17:47:17","UsedNum":0,"TotalNum":50,"UseDays":3,"Status":0}
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
         * PatientName : 巢
         * PicturePath : http://userimage.newstarthealth.cn/pat/0/45.jpg?dt=201811123546693
         * GestationWeeks : 孕40周+0天
         * ServiceWeeks : 90天
         * DeviceNo : LCeFM6018051010
         * HospitalName : 保定市人民医院
         * RentExpires : null
         * BeginTime : 2018-11-08 18:36:30
         * EndTime : 2019-02-08 17:47:17
         * UsedNum : 0
         * TotalNum : 50
         * UseDays : 3
         * Status : 0
         */

        private String PatientName;
        private String PicturePath;
        private String GestationWeeks;
        private String ServiceWeeks;
        private String DeviceNo;
        private String HospitalName;
        private Object RentExpires;
        private String BeginTime;
        private String EndTime;
        private int UsedNum;
        private int TotalNum;
        private int UseDays;
        private int Status;

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String PicturePath) {
            this.PicturePath = PicturePath;
        }

        public String getGestationWeeks() {
            return GestationWeeks;
        }

        public void setGestationWeeks(String GestationWeeks) {
            this.GestationWeeks = GestationWeeks;
        }

        public String getServiceWeeks() {
            return ServiceWeeks;
        }

        public void setServiceWeeks(String ServiceWeeks) {
            this.ServiceWeeks = ServiceWeeks;
        }

        public String getDeviceNo() {
            return DeviceNo;
        }

        public void setDeviceNo(String DeviceNo) {
            this.DeviceNo = DeviceNo;
        }

        public String getHospitalName() {
            return HospitalName;
        }

        public void setHospitalName(String HospitalName) {
            this.HospitalName = HospitalName;
        }

        public Object getRentExpires() {
            return RentExpires;
        }

        public void setRentExpires(Object RentExpires) {
            this.RentExpires = RentExpires;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String BeginTime) {
            this.BeginTime = BeginTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public int getUsedNum() {
            return UsedNum;
        }

        public void setUsedNum(int UsedNum) {
            this.UsedNum = UsedNum;
        }

        public int getTotalNum() {
            return TotalNum;
        }

        public void setTotalNum(int TotalNum) {
            this.TotalNum = TotalNum;
        }

        public int getUseDays() {
            return UseDays;
        }

        public void setUseDays(int UseDays) {
            this.UseDays = UseDays;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
