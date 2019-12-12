package com.newdjk.member.ui.entity;

public class PrescriptionMessageEntity<T> {
    private LoginEntity doctor;
    private T patient;
    private T position;
    private T AppInfo;
    public T getPosition() {
        return position;
    }

    public void setPosition(T position) {
        this.position = position;
    }

    public LoginEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(LoginEntity doctor) {
        this.doctor = doctor;
    }

    public T getPatient() {
        return patient;
    }

    public void setPatient(T patient) {
        this.patient = patient;
    }

    public T getAppInfo() {
        return AppInfo;
    }

    public void setAppInfo(T appInfo) {
        AppInfo = appInfo;
    }
}
