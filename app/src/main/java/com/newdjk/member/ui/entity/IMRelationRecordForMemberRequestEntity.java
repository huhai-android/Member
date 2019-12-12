package com.newdjk.member.ui.entity;

import java.util.List;

public class IMRelationRecordForMemberRequestEntity {
    private String ApplicantIM;
    private List<String> DoctorIMIdList;

    public String getApplicantIM() {
        return ApplicantIM;
    }

    public void setApplicantIM(String applicantIM) {
        ApplicantIM = applicantIM;
    }

    public List<String> getDoctorIMIdList() {
        return DoctorIMIdList;
    }

    public void setDoctorIMIdList(List<String> doctorIMIdList) {
        DoctorIMIdList = doctorIMIdList;
    }
}
