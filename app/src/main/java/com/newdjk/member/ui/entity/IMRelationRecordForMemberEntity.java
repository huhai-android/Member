package com.newdjk.member.ui.entity;

public class IMRelationRecordForMemberEntity {

    /**
     * ApplicantId : 1
     * ApplicantIMId : sample string 2
     * ApplicantName : sample string 3
     * ApplicantHeadImgUrl : sample string 4
     * DoctorId : 5
     * DoctorIMId : sample string 6
     * DoctorName : sample string 7
     * ServiceType : 64
     * ServiceItemCode :
     * RecordId : 9
     * Status : 64
     */

    private int ApplicantId;
    private String ApplicantIMId;
    private String ApplicantName;
    private String ApplicantHeadImgUrl;
    private int DoctorId;
    private String DoctorIMId;
    private String DoctorName;
    private int ServiceType;
    private String ServiceItemCode;
    private int RecordId;
    private int Status;

    public int getApplicantId() {
        return ApplicantId;
    }

    public void setApplicantId(int ApplicantId) {
        this.ApplicantId = ApplicantId;
    }

    public String getApplicantIMId() {
        return ApplicantIMId;
    }

    public void setApplicantIMId(String ApplicantIMId) {
        this.ApplicantIMId = ApplicantIMId;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String ApplicantName) {
        this.ApplicantName = ApplicantName;
    }

    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String ApplicantHeadImgUrl) {
        this.ApplicantHeadImgUrl = ApplicantHeadImgUrl;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String DoctorIMId) {
        this.DoctorIMId = DoctorIMId;
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

    public String getServiceItemCode() {
        return ServiceItemCode;
    }

    public void setServiceItemCode(String ServiceItemCode) {
        this.ServiceItemCode = ServiceItemCode;
    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
}
