package com.newdjk.member.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * author : struggle
 * e-mail : dexin@163.com
 * date   : 2018/10/22  14:21
 * desc   :
 * version: 1.0
 */
public class MyMapUtil {
    /**
     * 获取定位
     */
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mOptions = null;


    public void getCurrentLocation(int type, AMapLocationListener listener, Context context) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        mOptions = new AMapLocationClientOption();
        mOptions.setLocationMode(type == 0 ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy : AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mOptions.setOnceLocation(true);
        mOptions.setOnceLocationLatest(true);
        mOptions.setNeedAddress(true);
        mOptions.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mOptions);
        //设置定位回调监听
        mLocationClient.setLocationListener(listener);
        //启动定位
        mLocationClient.startLocation();
    }

}
