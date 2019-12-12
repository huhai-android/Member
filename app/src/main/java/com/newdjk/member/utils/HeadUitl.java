package com.newdjk.member.utils;

import com.newdjk.member.tools.Contants;

import java.util.HashMap;

public enum HeadUitl {

    instance;

    public HashMap<String, String> getHeads() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", SpUtils.getString(Contants.Token));
        return params;
    }
}
