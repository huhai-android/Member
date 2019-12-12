package com.newdjk.member.utils;

import android.text.TextUtils;

import com.newdjk.member.ui.entity.GetContactInfoEntity;


public class OrderUtil {
    public static String connectStr(GetContactInfoEntity response) {
        StringBuffer sb = new StringBuffer();
        sb.append("领取地址：");
        if (!TextUtils.isEmpty(response.getData().getAddress())) {
            sb.append(response.getData().getAddress());
        }
        sb.append("\n");
        sb.append("联系人：");
        if (!TextUtils.isEmpty(response.getData().getName())) {
            sb.append(response.getData().getName());
        }
        sb.append("\n");
        sb.append("联系电话：");
        if (!TextUtils.isEmpty(response.getData().getMobile())) {
            sb.append(response.getData().getMobile());
        }
        sb.append("\n");
        sb.append("工作日时间：");
        if (!TextUtils.isEmpty(response.getData().getWorkDay())) {
            sb.append(response.getData().getWorkDay());
        }
        sb.append("\n");
        sb.append("非工作日时间：");
        if (!TextUtils.isEmpty(response.getData().getNonWorkDay())) {
            sb.append(response.getData().getNonWorkDay());
        }
        return sb.toString();
    }
}
