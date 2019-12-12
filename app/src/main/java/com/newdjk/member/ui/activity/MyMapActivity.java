package com.newdjk.member.ui.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import com.amap.api.maps.model.animation.Animation;
import android.view.animation.LinearInterpolator;
import com.amap.api.maps.model.animation.RotateAnimation;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.HospitalAddressInfo;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.MapNaviUtils;
import com.newdjk.member.utils.MyMapUtil;

import java.util.List;


public class MyMapActivity extends FragmentActivity implements AMapLocationListener {
    private MapView mMap;
    private AMap mAMap;
    CameraUpdate cameraUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        findViews();
        initViews(savedInstanceState);
        MyMapUtil mapUtil = new MyMapUtil();
         mapUtil.getCurrentLocation(0, this, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        HospitalAddressInfo entity = bundle.getParcelable("hospitalAddInfo");
        if (entity == null) {
            Toast.makeText(this, getString(R.string.addressError), Toast.LENGTH_SHORT).show();
            return;
        }
        if (entity.getLongitude() == null || entity.getDimension() == null) {
            return;
        }
        LatLng latLng = new LatLng(Double.valueOf(entity.getDimension()), Double.valueOf(entity.getLongitude()));

        final Marker marker = mAMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(entity.getHospitalName() == null ? "未知" : entity.getHospitalName())
                .snippet(entity.getAddress() == null ? "未知" : entity.getAddress()));
        marker.showInfoWindow();
        Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 360, 0, 0, 0);
        long duration = 2000L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());

        marker.setAnimation(animation);
        marker.startAnimation();

        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 0, 30));
        mAMap.moveCamera(cameraUpdate);//地图移向指定区域

        mAMap.setOnMarkerClickListener(v -> {
            if (isApplicationAvilible(MyMapActivity.this, "com.autonavi.minimap")) {
                MapNaviUtils.openGaoDeNavi(MyMapActivity.this, 0, 0, null, Double.valueOf(entity.getDimension()), Double.valueOf(entity.getLongitude()),entity.getHospitalName());
//                            MapUtils.openMap(mContext,"com.autonavi.minimap",new LatLng(31.33260711060764,121.54777721524306,"CCB"));
            } else {
                Toast.makeText(MyMapActivity.this, "请安装第三方地图方可导航", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void initViews(Bundle savedInstanceState) {
        mMap.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMap.getMap();

        }

        MyMapUtil util = new MyMapUtil();
        util.getCurrentLocation(0, this, this);


    }

    private void findViews() {
        mMap = this.findViewById(R.id.map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMap.onSaveInstanceState(outState);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtils.e(aMapLocation.getLocationDetail());
    }


    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }
}

