package com.newdjk.member.ui.entity;

import java.util.List;

public class GetReadDoctorListEntity {

    /**
     * Code : 0
     * Message :
     * Data : [{"DrId":1,"DrName":"欧阳云生","DrSex":1,"HospitalName":"人民医院","DepartmentName":"内科1","PositionName":"","PicturePath":"http://userimage.newstarthealth.cn/doc/0/1.jpg?dt=201811075308023","InquiryCount":0,"ServiceItems":["名医续方"]},{"DrId":130,"DrName":"张飞","DrSex":3,"HospitalName":"龙华人民医院","DepartmentName":"外科","PositionName":"","PicturePath":"http://userimage.newstarthealth.cn/doc/0/130.jpg?dt=201811075308032","InquiryCount":3,"ServiceItems":["图文问诊","视频问诊","名医续方"]}]
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
         * DrId : 1
         * DrName : 欧阳云生
         * DrSex : 1
         * HospitalName : 人民医院
         * DepartmentName : 内科1
         * PositionName :
         * PicturePath : http://userimage.newstarthealth.cn/doc/0/1.jpg?dt=201811075308023
         * InquiryCount : 0
         * ServiceItems : ["名医续方"]
         */

        private int DrId;
        private String DrName;
        private int DrSex;
        private String HospitalName;
        private String DepartmentName;
        private String PositionName;
        private String PicturePath;
        private int InquiryCount;
        private List<String> ServiceItems;

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

        public int getDrSex() {
            return DrSex;
        }

        public void setDrSex(int DrSex) {
            this.DrSex = DrSex;
        }

        public String getHospitalName() {
            return HospitalName;
        }

        public void setHospitalName(String HospitalName) {
            this.HospitalName = HospitalName;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getPositionName() {
            return PositionName;
        }

        public void setPositionName(String PositionName) {
            this.PositionName = PositionName;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String PicturePath) {
            this.PicturePath = PicturePath;
        }

        public int getInquiryCount() {
            return InquiryCount;
        }

        public void setInquiryCount(int InquiryCount) {
            this.InquiryCount = InquiryCount;
        }

        public List<String> getServiceItems() {
            return ServiceItems;
        }

        public void setServiceItems(List<String> ServiceItems) {
            this.ServiceItems = ServiceItems;
        }
    }
}
