package com.newdjk.member.ui.entity;

public class UpdateViewEntity {
    public UpdateViewEntity(Boolean isReflash) {
        this.isReflash = isReflash;
    }

    private Boolean isReflash = false;

    public Boolean getReflash() {
        return isReflash;
    }

    public void setReflash(Boolean reflash) {
        isReflash = reflash;
    }
}
