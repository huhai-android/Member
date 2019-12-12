package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/6.
 */

public class SearchDoctorEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":1,"ReturnData":[{"SpecialistHosGroupId":0,"DrId":306,"DrName":"艾美","Mobile":"13410328229","DrType":1,"PharmacistType":0,"DrSex":1,"DrStatus":1,"HospitalId":3379,"HospitalName":"保定市第一中心医院","DepartmentId":10,"DepartmentName":"中医科","Position":1,"PositionName":"主任医师","DoctorGroup":"","PicturePath":"http://userimage.newstarthealth.cn/doc/0/306.jpg?dt=201908162339471","TreatMent":null,"DoctorSkill":"哮喘","Description":"123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地","IsOnline":1,"CreateTime":"2018-11-29 10:54:38","PraiseRate":76,"ConsultCount":0,"ResponseRate":67,"ConsultVolume":283,"OnPrescription":167,"VisitCount":0,"Telenursing":0,"VolumeVideo":37,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|100.00|0.00,1102|视频问诊|60.00|0.00,1103|名医续方|120.00|0.00,1110|第二诊疗意见|100.00|0.00,1120|面诊预约|0.00|0.01","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":"60.00","Price":"0.00"},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":"120.00","Price":"0.00"},{"SericeItemCode":"1110","SericeItemName":"第二诊疗意见","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1120","SericeItemName":"面诊预约","OriginalPrice":"0.00","Price":"0.01"}],"IsPatientMain":false,"IsAttantDr":false,"IsHasPack":1,"PatientAttentDrNum":321,"MedicalRecord":22,"MedicalPrice":"0.00","InterVolume":522,"FaceAppointment":13,"PrescriptionNum":0,"InspectionNum":0,"ManuPatientAttentDrNum":412,"ManuInterVolume":618,"IsOpenPresc":1,"OpratePrescDrId":0,"OrgId":79,"OrgName":"艾美组织","IsClinic":1,"IsReferral":1,"ProfessionalLevel":4,"AreaId":3487,"AreaName":"莲池区","AreaPath":",0,3,42,3487,","IsSpecialReferral":1,"SpecialReferralAmount":"0.10","IsHead":0,"DrTitle":null}]}
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
         * Total : 1
         * ReturnData : [{"SpecialistHosGroupId":0,"DrId":306,"DrName":"艾美","Mobile":"13410328229","DrType":1,"PharmacistType":0,"DrSex":1,"DrStatus":1,"HospitalId":3379,"HospitalName":"保定市第一中心医院","DepartmentId":10,"DepartmentName":"中医科","Position":1,"PositionName":"主任医师","DoctorGroup":"","PicturePath":"http://userimage.newstarthealth.cn/doc/0/306.jpg?dt=201908162339471","TreatMent":null,"DoctorSkill":"哮喘","Description":"123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地","IsOnline":1,"CreateTime":"2018-11-29 10:54:38","PraiseRate":76,"ConsultCount":0,"ResponseRate":67,"ConsultVolume":283,"OnPrescription":167,"VisitCount":0,"Telenursing":0,"VolumeVideo":37,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|100.00|0.00,1102|视频问诊|60.00|0.00,1103|名医续方|120.00|0.00,1110|第二诊疗意见|100.00|0.00,1120|面诊预约|0.00|0.01","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":"60.00","Price":"0.00"},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":"120.00","Price":"0.00"},{"SericeItemCode":"1110","SericeItemName":"第二诊疗意见","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1120","SericeItemName":"面诊预约","OriginalPrice":"0.00","Price":"0.01"}],"IsPatientMain":false,"IsAttantDr":false,"IsHasPack":1,"PatientAttentDrNum":321,"MedicalRecord":22,"MedicalPrice":"0.00","InterVolume":522,"FaceAppointment":13,"PrescriptionNum":0,"InspectionNum":0,"ManuPatientAttentDrNum":412,"ManuInterVolume":618,"IsOpenPresc":1,"OpratePrescDrId":0,"OrgId":79,"OrgName":"艾美组织","IsClinic":1,"IsReferral":1,"ProfessionalLevel":4,"AreaId":3487,"AreaName":"莲池区","AreaPath":",0,3,42,3487,","IsSpecialReferral":1,"SpecialReferralAmount":"0.10","IsHead":0,"DrTitle":null}]
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
             * SpecialistHosGroupId : 0
             * DrId : 306
             * DrName : 艾美
             * Mobile : 13410328229
             * DrType : 1
             * PharmacistType : 0
             * DrSex : 1
             * DrStatus : 1
             * HospitalId : 3379
             * HospitalName : 保定市第一中心医院
             * DepartmentId : 10
             * DepartmentName : 中医科
             * Position : 1
             * PositionName : 主任医师
             * DoctorGroup :
             * PicturePath : http://userimage.newstarthealth.cn/doc/0/306.jpg?dt=201908162339471
             * TreatMent : null
             * DoctorSkill : 哮喘
             * Description : 123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地123顺大吃大喝在嘎嘎右 大吃大喝地
             * IsOnline : 1
             * CreateTime : 2018-11-29 10:54:38
             * PraiseRate : 76
             * ConsultCount : 0
             * ResponseRate : 67
             * ConsultVolume : 283
             * OnPrescription : 167
             * VisitCount : 0
             * Telenursing : 0
             * VolumeVideo : 37
             * FetalHeart : 0
             * SericeItemCodes : 1101|图文问诊|100.00|0.00,1102|视频问诊|60.00|0.00,1103|名医续方|120.00|0.00,1110|第二诊疗意见|100.00|0.00,1120|面诊预约|0.00|0.01
             * DrServiceItems : [{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":"60.00","Price":"0.00"},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":"120.00","Price":"0.00"},{"SericeItemCode":"1110","SericeItemName":"第二诊疗意见","OriginalPrice":"100.00","Price":"0.00"},{"SericeItemCode":"1120","SericeItemName":"面诊预约","OriginalPrice":"0.00","Price":"0.01"}]
             * IsPatientMain : false
             * IsAttantDr : false
             * IsHasPack : 1
             * PatientAttentDrNum : 321
             * MedicalRecord : 22
             * MedicalPrice : 0.00
             * InterVolume : 522
             * FaceAppointment : 13
             * PrescriptionNum : 0
             * InspectionNum : 0
             * ManuPatientAttentDrNum : 412
             * ManuInterVolume : 618
             * IsOpenPresc : 1
             * OpratePrescDrId : 0
             * OrgId : 79
             * OrgName : 艾美组织
             * IsClinic : 1
             * IsReferral : 1
             * ProfessionalLevel : 4
             * AreaId : 3487
             * AreaName : 莲池区
             * AreaPath : ,0,3,42,3487,
             * IsSpecialReferral : 1
             * SpecialReferralAmount : 0.10
             * IsHead : 0
             * DrTitle : null
             */

            private int SpecialistHosGroupId;
            private int DrId;
            private String DrName;
            private String Mobile;
            private int DrType;
            private int PharmacistType;
            private int DrSex;
            private int DrStatus;
            private int HospitalId;
            private String HospitalName;
            private int DepartmentId;
            private String DepartmentName;
            private int Position;
            private String PositionName;
            private String DoctorGroup;
            private String PicturePath;
            private Object TreatMent;
            private String DoctorSkill;
            private String Description;
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
            private int MedicalRecord;
            private String MedicalPrice;
            private int InterVolume;
            private int FaceAppointment;
            private int PrescriptionNum;
            private int InspectionNum;
            private int ManuPatientAttentDrNum;
            private int ManuInterVolume;
            private int IsOpenPresc;
            private int OpratePrescDrId;
            private int OrgId;
            private String OrgName;
            private int IsClinic;
            private int IsReferral;
            private int ProfessionalLevel;
            private int AreaId;
            private String AreaName;
            private String AreaPath;
            private int IsSpecialReferral;
            private String SpecialReferralAmount;
            private int IsHead;
            private Object DrTitle;
            private List<DrServiceItemsBean> DrServiceItems;

            public int getSpecialistHosGroupId() {
                return SpecialistHosGroupId;
            }

            public void setSpecialistHosGroupId(int SpecialistHosGroupId) {
                this.SpecialistHosGroupId = SpecialistHosGroupId;
            }

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

            public int getPharmacistType() {
                return PharmacistType;
            }

            public void setPharmacistType(int PharmacistType) {
                this.PharmacistType = PharmacistType;
            }

            public int getDrSex() {
                return DrSex;
            }

            public void setDrSex(int DrSex) {
                this.DrSex = DrSex;
            }

            public int getDrStatus() {
                return DrStatus;
            }

            public void setDrStatus(int DrStatus) {
                this.DrStatus = DrStatus;
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

            public String getDoctorGroup() {
                return DoctorGroup;
            }

            public void setDoctorGroup(String DoctorGroup) {
                this.DoctorGroup = DoctorGroup;
            }

            public String getPicturePath() {
                return PicturePath;
            }

            public void setPicturePath(String PicturePath) {
                this.PicturePath = PicturePath;
            }

            public Object getTreatMent() {
                return TreatMent;
            }

            public void setTreatMent(Object TreatMent) {
                this.TreatMent = TreatMent;
            }

            public String getDoctorSkill() {
                return DoctorSkill;
            }

            public void setDoctorSkill(String DoctorSkill) {
                this.DoctorSkill = DoctorSkill;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
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

            public int getMedicalRecord() {
                return MedicalRecord;
            }

            public void setMedicalRecord(int MedicalRecord) {
                this.MedicalRecord = MedicalRecord;
            }

            public String getMedicalPrice() {
                return MedicalPrice;
            }

            public void setMedicalPrice(String MedicalPrice) {
                this.MedicalPrice = MedicalPrice;
            }

            public int getInterVolume() {
                return InterVolume;
            }

            public void setInterVolume(int InterVolume) {
                this.InterVolume = InterVolume;
            }

            public int getFaceAppointment() {
                return FaceAppointment;
            }

            public void setFaceAppointment(int FaceAppointment) {
                this.FaceAppointment = FaceAppointment;
            }

            public int getPrescriptionNum() {
                return PrescriptionNum;
            }

            public void setPrescriptionNum(int PrescriptionNum) {
                this.PrescriptionNum = PrescriptionNum;
            }

            public int getInspectionNum() {
                return InspectionNum;
            }

            public void setInspectionNum(int InspectionNum) {
                this.InspectionNum = InspectionNum;
            }

            public int getManuPatientAttentDrNum() {
                return ManuPatientAttentDrNum;
            }

            public void setManuPatientAttentDrNum(int ManuPatientAttentDrNum) {
                this.ManuPatientAttentDrNum = ManuPatientAttentDrNum;
            }

            public int getManuInterVolume() {
                return ManuInterVolume;
            }

            public void setManuInterVolume(int ManuInterVolume) {
                this.ManuInterVolume = ManuInterVolume;
            }

            public int getIsOpenPresc() {
                return IsOpenPresc;
            }

            public void setIsOpenPresc(int IsOpenPresc) {
                this.IsOpenPresc = IsOpenPresc;
            }

            public int getOpratePrescDrId() {
                return OpratePrescDrId;
            }

            public void setOpratePrescDrId(int OpratePrescDrId) {
                this.OpratePrescDrId = OpratePrescDrId;
            }

            public int getOrgId() {
                return OrgId;
            }

            public void setOrgId(int OrgId) {
                this.OrgId = OrgId;
            }

            public String getOrgName() {
                return OrgName;
            }

            public void setOrgName(String OrgName) {
                this.OrgName = OrgName;
            }

            public int getIsClinic() {
                return IsClinic;
            }

            public void setIsClinic(int IsClinic) {
                this.IsClinic = IsClinic;
            }

            public int getIsReferral() {
                return IsReferral;
            }

            public void setIsReferral(int IsReferral) {
                this.IsReferral = IsReferral;
            }

            public int getProfessionalLevel() {
                return ProfessionalLevel;
            }

            public void setProfessionalLevel(int ProfessionalLevel) {
                this.ProfessionalLevel = ProfessionalLevel;
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

            public String getAreaPath() {
                return AreaPath;
            }

            public void setAreaPath(String AreaPath) {
                this.AreaPath = AreaPath;
            }

            public int getIsSpecialReferral() {
                return IsSpecialReferral;
            }

            public void setIsSpecialReferral(int IsSpecialReferral) {
                this.IsSpecialReferral = IsSpecialReferral;
            }

            public String getSpecialReferralAmount() {
                return SpecialReferralAmount;
            }

            public void setSpecialReferralAmount(String SpecialReferralAmount) {
                this.SpecialReferralAmount = SpecialReferralAmount;
            }

            public int getIsHead() {
                return IsHead;
            }

            public void setIsHead(int IsHead) {
                this.IsHead = IsHead;
            }

            public Object getDrTitle() {
                return DrTitle;
            }

            public void setDrTitle(Object DrTitle) {
                this.DrTitle = DrTitle;
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
                 * OriginalPrice : 100.00
                 * Price : 0.00
                 */

                private String SericeItemCode;
                private String SericeItemName;
                private String OriginalPrice;
                private String Price;

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

                public String getOriginalPrice() {
                    return OriginalPrice;
                }

                public void setOriginalPrice(String OriginalPrice) {
                    this.OriginalPrice = OriginalPrice;
                }

                public String getPrice() {
                    return Price;
                }

                public void setPrice(String Price) {
                    this.Price = Price;
                }
            }
        }
    }
}
