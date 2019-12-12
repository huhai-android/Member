package com.newdjk.member.ui.entity;

public class ImRelationRecode {


    /**
     * PatientIMId : pat_172
     * PatientName : 婶婶
     * DoctorIMId : doc_306
     * DoctorId : 306
     * DoctorName : 艾美
     * ServiceType : 1
     * RecordId : 1883
     */

    private String PatientIMId;
    private String PatientName;
    private String DoctorIMId;
    private int DoctorId;
    private String DoctorName;
    private int ServiceType;
    private int DoctorType;
    private int RecordId;

    public int getDoctorType() {
        return DoctorType;
    }

    public void setDoctorType(int doctorType) {
        DoctorType = doctorType;
    }

    public String getPatientIMId() {
        return PatientIMId;
    }

    public void setPatientIMId(String PatientIMId) {
        this.PatientIMId = PatientIMId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String DoctorIMId) {
        this.DoctorIMId = DoctorIMId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int ServiceType) {
        this.ServiceType = ServiceType;
    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }
}
