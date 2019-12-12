package com.newdjk.member.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * author : struggle
 * e-mail : dexin@163.com
 * date   : 2018/10/20  11:07
 * desc   :
 * version: 1.0
 */
public enum PermissionUtil {
    INSTANCE;
    public static final int REQUEST_PERMISSION = 0xfffff001;
    public static final int REQUEST_CODE = 100;
    private Context mContext;

    public void setContext(Context context) {
        mContext = context.getApplicationContext();
    }


    public boolean checkPermissionStorage(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)) {
                permissions.add(Manifest.permission.CALL_PHONE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN)) {
                permissions.add(Manifest.permission.BLUETOOTH_ADMIN);
            }
            int len = permissions.size();
            if (len != 0) {
                String[] per = new String[len];
                for (int i = 0; i < len; i++) {
                    per[i] = permissions.get(i);
                }
                ActivityCompat.requestPermissions(context,
                        per,
                        REQUEST_CODE);
                return false;
            }
        }

        return true;
    }

    public boolean phoneCall(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)) {
                permissions.add(Manifest.permission.CALL_PHONE);
            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(context,
                        (String[]) permissions.toArray(new String[0]),
                        REQUEST_CODE);
                return false;
            }
        }

        return true;
    }

    public static String[] arrayPermission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 获取定位权限
     */
    public boolean checkLocationPermission(String permission) {
        if (mContext == null) {
            LogUtils.exception("没有上下文对象");
        }
        if (EasyPermissions.hasPermissions(this.mContext, permission)) {
            return true;
        } else {
            requestPermission(permission);
            return false;
        }
    }

    public void requestPermission(String permission) {
        EasyPermissions.requestPermissions(this, "需要开相关权限",
                REQUEST_PERMISSION, permission);
    }

}
