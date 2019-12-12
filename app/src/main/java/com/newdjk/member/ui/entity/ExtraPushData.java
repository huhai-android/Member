package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2018/12/3.
 */

public class ExtraPushData {


    /**
     * type : 23
     * data : {"RecordId":"1564","ServiceType":"1"}
     * serviceCode : 0
     * endStauts : 0
     * time : 2018-12-03 16:27:22
     * userId : 34
     * title : 图文问诊结束提醒
     * msgType : 2
     * moduleType : 1
     * id : 23890
     */

    private int type;
    private DataBean data;
    private int serviceCode;
    private int endStauts;
    private String time;
    private String userId;
    private String title;
    private int msgType;
    private int moduleType;
    private int id;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public int getEndStauts() {
        return endStauts;
    }

    public void setEndStauts(int endStauts) {
        this.endStauts = endStauts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class DataBean {
        /**
         * RecordId : 1564
         * ServiceType : 1
         */

        private String RecordId;
        private String ServiceType;
        private String PrescriptionId;
        private String DoctorId;
        private String DoctorName;
        private int PatientId;
        private String LinkURL;
        private int  MedicalTempId;
        private int MedicalRecordId;
        private String MedicalReportPath;
        private int DrId;
        private int MedicalUnitId;
        private String AskId;
        private String Url;

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }

        public String getAskId() {
            return AskId;
        }

        public void setAskId(String askId) {
            AskId = askId;
        }

        public int getMedicalRecordId() {
            return MedicalRecordId;
        }

        public void setMedicalRecordId(int medicalRecordId) {
            MedicalRecordId = medicalRecordId;
        }

        public String getMedicalReportPath() {
            return MedicalReportPath;
        }

        public void setMedicalReportPath(String medicalReportPath) {
            MedicalReportPath = medicalReportPath;
        }

        public int getDrId() {
            return DrId;
        }

        public void setDrId(int drId) {
            DrId = drId;
        }

        public int getMedicalUnitId() {
            return MedicalUnitId;
        }

        public void setMedicalUnitId(int medicalUnitId) {
            MedicalUnitId = medicalUnitId;
        }

        public int getMedicalTempId() {
            return MedicalTempId;
        }

        public void setMedicalTempId(int medicalTempId) {
            MedicalTempId = medicalTempId;
        }

        public String getLinkURL() {
            return LinkURL;
        }

        public void setLinkURL(String linkURL) {
            LinkURL = linkURL;
        }

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int patientId) {
            PatientId = patientId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public String getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(String doctorId) {
            DoctorId = doctorId;
        }

        public String getPrescriptionId() {
            return PrescriptionId;
        }

        public void setPrescriptionId(String prescriptionId) {
            PrescriptionId = prescriptionId;
        }

        public String getRecordId() {
            return RecordId;
        }

        public void setRecordId(String RecordId) {
            this.RecordId = RecordId;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String ServiceType) {
            this.ServiceType = ServiceType;
        }
    }
}
