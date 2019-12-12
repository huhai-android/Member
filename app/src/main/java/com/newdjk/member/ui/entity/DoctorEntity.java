package com.newdjk.member.ui.entity;

import java.util.List;

public class DoctorEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"DrId":221,"DrName":"李童","Mobile":"17324219646","DrType":1,"DrSex":3,"HospitalId":257,"HospitalName":"东城区第一人民医院","DepartmentId":27,"DepartmentName":"神经内科","Position":1,"PositionName":"主任医师","DoctorGroup":null,"PicturePath":null,"TreatMent":null,"DoctorSkill":null,"Description":null,"IsOnline":1,"CreateTime":"2018-10-19 16:18:16","PraiseRate":0,"ConsultCount":0,"ResponseRate":79,"ConsultVolume":15,"OnPrescription":0,"VisitCount":0,"Telenursing":0,"VolumeVideo":2,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|100.00|0.00,1102|视频问诊|0.00|0.00","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":100,"Price":0},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":0,"Price":0}],"IsPatientMain":true,"IsAttantDr":false,"IsHasPack":0,"PatientAttentDrNum":1}
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
         * DrId : 221
         * DrName : 李童
         * Mobile : 17324219646
         * DrType : 1
         * DrSex : 3
         * HospitalId : 257
         * HospitalName : 东城区第一人民医院
         * DepartmentId : 27
         * DepartmentName : 神经内科
         * Position : 1
         * PositionName : 主任医师
         * DoctorGroup : null
         * PicturePath : null
         * TreatMent : null
         * DoctorSkill : null
         * Description : null
         * IsOnline : 1
         * CreateTime : 2018-10-19 16:18:16
         * PraiseRate : 0
         * ConsultCount : 0
         * ResponseRate : 79
         * ConsultVolume : 15
         * OnPrescription : 0
         * VisitCount : 0
         * Telenursing : 0
         * VolumeVideo : 2
         * FetalHeart : 0
         * SericeItemCodes : 1101|图文问诊|100.00|0.00,1102|视频问诊|0.00|0.00
         * DrServiceItems : [{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":100,"Price":0},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":0,"Price":0}]
         * IsPatientMain : true
         * IsAttantDr : false
         * IsHasPack : 0
         * PatientAttentDrNum : 1
         */

        private int DrId;
        private String DrName;
        private String Mobile;
        private int DrType;
        private int DrSex;
        private int HospitalId;
        private String HospitalName;
        private int DepartmentId;
        private String DepartmentName;
        private int Position;
        private String PositionName;
        private Object DoctorGroup;
        private Object PicturePath;
        private Object TreatMent;
        private Object DoctorSkill;
        private Object Description;
        private int IsOnline;
        private String CreateTime;
        private int PraiseRate;
        private int ConsultCount;
        private int ResponseRate;
        private int ConsultVolume;
        private int OnPrescription;
        private int VisitCount;
        private int Telenursing;
        private int VolumeVideo;
        private int FetalHeart;
        private String SericeItemCodes;
        private boolean IsPatientMain;
        private boolean IsAttantDr;
        private int IsHasPack;
        private int PatientAttentDrNum;
        private List<DrServiceItemsBean> DrServiceItems;

        public int getDrId() {
            return DrId;
        }

        public void setDrId(int DrId) {
            this.DrId = DrId;
        }

        public String getDrName() {
            return DrName;
        }

        public void setDrName(String DrName) {
            this.DrName = DrName;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getDrType() {
            return DrType;
        }

        public void setDrType(int DrType) {
            this.DrType = DrType;
        }

        public int getDrSex() {
            return DrSex;
        }

        public void setDrSex(int DrSex) {
            this.DrSex = DrSex;
        }

        public int getHospitalId() {
            return HospitalId;
        }

        public void setHospitalId(int HospitalId) {
            this.HospitalId = HospitalId;
        }

        public String getHospitalName() {
            return HospitalName;
        }

        public void setHospitalName(String HospitalName) {
            this.HospitalName = HospitalName;
        }

        public int getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(int DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public int getPosition() {
            return Position;
        }

        public void setPosition(int Position) {
            this.Position = Position;
        }

        public String getPositionName() {
            return PositionName;
        }

        public void setPositionName(String PositionName) {
            this.PositionName = PositionName;
        }

        public Object getDoctorGroup() {
            return DoctorGroup;
        }

        public void setDoctorGroup(Object DoctorGroup) {
            this.DoctorGroup = DoctorGroup;
        }

        public Object getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(Object PicturePath) {
            this.PicturePath = PicturePath;
        }

        public Object getTreatMent() {
            return TreatMent;
        }

        public void setTreatMent(Object TreatMent) {
            this.TreatMent = TreatMent;
        }

        public Object getDoctorSkill() {
            return DoctorSkill;
        }

        public void setDoctorSkill(Object DoctorSkill) {
            this.DoctorSkill = DoctorSkill;
        }

        public Object getDescription() {
            return Description;
        }

        public void setDescription(Object Description) {
            this.Description = Description;
        }

        public int getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(int IsOnline) {
            this.IsOnline = IsOnline;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getPraiseRate() {
            return PraiseRate;
        }

        public void setPraiseRate(int PraiseRate) {
            this.PraiseRate = PraiseRate;
        }

        public int getConsultCount() {
            return ConsultCount;
        }

        public void setConsultCount(int ConsultCount) {
            this.ConsultCount = ConsultCount;
        }

        public int getResponseRate() {
            return ResponseRate;
        }

        public void setResponseRate(int ResponseRate) {
            this.ResponseRate = ResponseRate;
        }

        public int getConsultVolume() {
            return ConsultVolume;
        }

        public void setConsultVolume(int ConsultVolume) {
            this.ConsultVolume = ConsultVolume;
        }

        public int getOnPrescription() {
            return OnPrescription;
        }

        public void setOnPrescription(int OnPrescription) {
            this.OnPrescription = OnPrescription;
        }

        public int getVisitCount() {
            return VisitCount;
        }

        public void setVisitCount(int VisitCount) {
            this.VisitCount = VisitCount;
        }

        public int getTelenursing() {
            return Telenursing;
        }

        public void setTelenursing(int Telenursing) {
            this.Telenursing = Telenursing;
        }

        public int getVolumeVideo() {
            return VolumeVideo;
        }

        public void setVolumeVideo(int VolumeVideo) {
            this.VolumeVideo = VolumeVideo;
        }

        public int getFetalHeart() {
            return FetalHeart;
        }

        public void setFetalHeart(int FetalHeart) {
            this.FetalHeart = FetalHeart;
        }

        public String getSericeItemCodes() {
            return SericeItemCodes;
        }

        public void setSericeItemCodes(String SericeItemCodes) {
            this.SericeItemCodes = SericeItemCodes;
        }

        public boolean isIsPatientMain() {
            return IsPatientMain;
        }

        public void setIsPatientMain(boolean IsPatientMain) {
            this.IsPatientMain = IsPatientMain;
        }

        public boolean isIsAttantDr() {
            return IsAttantDr;
        }

        public void setIsAttantDr(boolean IsAttantDr) {
            this.IsAttantDr = IsAttantDr;
        }

        public int getIsHasPack() {
            return IsHasPack;
        }

        public void setIsHasPack(int IsHasPack) {
            this.IsHasPack = IsHasPack;
        }

        public int getPatientAttentDrNum() {
            return PatientAttentDrNum;
        }

        public void setPatientAttentDrNum(int PatientAttentDrNum) {
            this.PatientAttentDrNum = PatientAttentDrNum;
        }

        public List<DrServiceItemsBean> getDrServiceItems() {
            return DrServiceItems;
        }

        public void setDrServiceItems(List<DrServiceItemsBean> DrServiceItems) {
            this.DrServiceItems = DrServiceItems;
        }

        public static class DrServiceItemsBean {
            /**
             * SericeItemCode : 1101
             * SericeItemName : 图文问诊
             * OriginalPrice : 100.0
             * Price : 0.0
             */

            private String SericeItemCode;
            private String SericeItemName;
            private double OriginalPrice;
            private double Price;

            public String getSericeItemCode() {
                return SericeItemCode;
            }

            public void setSericeItemCode(String SericeItemCode) {
                this.SericeItemCode = SericeItemCode;
            }

            public String getSericeItemName() {
                return SericeItemName;
            }

            public void setSericeItemName(String SericeItemName) {
                this.SericeItemName = SericeItemName;
            }

            public double getOriginalPrice() {
                return OriginalPrice;
            }

            public void setOriginalPrice(double OriginalPrice) {
                this.OriginalPrice = OriginalPrice;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }
        }
    }
}
