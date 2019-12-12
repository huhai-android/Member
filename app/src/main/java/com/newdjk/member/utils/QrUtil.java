package com.newdjk.member.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.DoctorIdEntity;

import java.util.HashMap;
import java.util.Map;

public class QrUtil {
    public static void toDoQrResult(String result, Context context) {
        if (!TextUtils.isEmpty(result)) {
            String accountId = String.valueOf(SpUtils.getInt(Contants.AccountId,0));
            Map<String, String> params = new HashMap<>();
            params.put("QRCodeUrl", result);
            params.put("AccountId", accountId);
            MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.QueryDoctorIdByQRCodeUrl).headers(HeadUitl.instance.getHeads()).params(params).tag(context).enqueue(new GsonResponseHandler<DoctorIdEntity>() {
                @Override
                public void onSuccess(int statusCode, DoctorIdEntity entituy) {
                    if (entituy.getCode() == 0) {
                        if (entituy.getData() == null) {
                            return;
                        }
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra("type", 29);
                        intent.putExtra("doctorId", entituy.getData().getDrId());
                        intent.putExtra("doctorName", entituy.getData().getDrName());
                        intent.putExtra("doctorType", entituy.getData().getDrType());
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "请选择正确的二维码", Toast.LENGTH_LONG).show();
                    }

                 }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    Toast.makeText(context, "请选择正确的二维码", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public static class AlbumMessage {
        public String result;
    }
}
