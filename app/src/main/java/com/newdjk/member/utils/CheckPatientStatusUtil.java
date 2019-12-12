package com.newdjk.member.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.DefaultPatientEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;

import java.util.HashMap;
import java.util.Map;

public class CheckPatientStatusUtil {
    /**
     * 获取该账号下的默认就诊人信息
     */
    public static void getDefaultPatientStatus(Context context) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("accountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
        paramsMap.put("patientId", String.valueOf(SpUtils.getInt(Contants.Id,0)));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.HaveBind).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(context).enqueue(new GsonResponseHandler<HaveBindEntity>() {
            @Override
            public void onSuccess(int statusCode, HaveBindEntity response) {//(Code说明:0设备已绑定,(1未登记用户,)2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)*/
               if (response.getData() != null){
                   SpUtils.put(Contants.DefaultPatientStatus, response.getCode());
                   Gson gson = new Gson();
                   String patientJson = gson.toJson(response.getData().getPatient());
                   SpUtils.put(Contants.DefaultPatientJson, patientJson);
               }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    public static void getDefaultPatient(Context context) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.DefaultPatient).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(context).enqueue(new GsonResponseHandler<DefaultPatientEntity>() {
            @Override
            public void onSuccess(int statusCode, DefaultPatientEntity response) {
                if (response.getCode() == 0) {
                    if (response.getData() == null) {
                        return;
                    }
                    Gson gson = new Gson();
                    SpUtils.put(Contants.DefaultPatientAccountJson, gson.toJson(response.getData()));
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
