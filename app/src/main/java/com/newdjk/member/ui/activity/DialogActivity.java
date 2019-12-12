package com.newdjk.member.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.activity.IM.RoomActivity;
import com.newdjk.member.ui.entity.HangUpTipEntity;
import com.newdjk.member.ui.entity.RejectCallTip;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.GlideMediaLoader;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.NetWorkUtil;
import com.newdjk.member.views.CircleImageView1;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends BasicActivity implements SurfaceHolder.Callback {

    @BindView(R.id.civImg)
    CircleImageView1 civImg;
    @BindView(R.id.tv_title_custom_dialog)
    TextView tvTitleCustomDialog;
    @BindView(R.id.tv_message_custom_dialog)
    TextView tvMessageCustomDialog;
    @BindView(R.id.btn_negative_custom_dialog)
    TextView btnNegativeCustomDialog;
    @BindView(R.id.btn_positive_custom_dialog)
    TextView btnPositiveCustomDialog;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    private SoundPool mSoundPool;

    private String mIdentifier;
    private String mSender;
    private int mCallId = 0;
    private String userSig = "";
    private String TAG = "DialogActivity";
    private int cameraId;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private int mSoundId;
    private String mAction;

    @Override
    protected int initViewResId() {
        return R.layout.video_dialog;
    }

    @Override
    protected void initView() {

        mSender = getIntent().getStringExtra("sender");
        mCallId = getIntent().getIntExtra("callId", 0);
        userSig = getIntent().getStringExtra("userSig");
        mIdentifier = getIntent().getStringExtra("identifier");
        mAction = getIntent().getStringExtra("action");
        tvTitleCustomDialog.setText("视频邀请");
        tvMessageCustomDialog.setText("来自" + mSender + "的视频邀请");
        btnNegativeCustomDialog.setText("拒绝");
        btnPositiveCustomDialog.setText("接受");
        List<String> list = new ArrayList<>();
                  /*  if (StringUtil.isNumeric(id)) {
                        id = "86-" + id;
                    }*/
        list.add(mIdentifier + "");
        TIMFriendshipManager.getInstance().getUsersProfile(list, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {

                Log.d(TAG, timUserProfiles.toString());
                if (timUserProfiles != null) {

                    GlideMediaLoader.loadPhoto(mContext, civImg, timUserProfiles.get(0).getFaceUrl());
                    tvTitleCustomDialog.setText(timUserProfiles.get(0).getNickName());
                    tvMessageCustomDialog.setText(timUserProfiles.get(0).getNickName() + "邀请你进行视频通话");
                }
            }
        });

        opencamera();
    }

    private void opencamera() {

    }

    /**
     * 初始化相机对象
     *
     * @return
     */
    private Camera initCamera() {

        Camera camera;
        try {
            camera = Camera.open(1);
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }

        return camera;
    }

    /**
     * 开始预览相机内容
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            //将Camera预览角度进行调整
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initListener() {
        btnNegativeCustomDialog.setOnClickListener(this);
        btnPositiveCustomDialog.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mHolder = surfaceview.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_negative_custom_dialog:
                if (mAction != null && mAction.equals("dialogActivity")) {
                    MessageEvent event = new MessageEvent();
                    event.setmType(MainConstant.CANCELVIDEO);
                    event.setAction(133);
                    event.setTime(-1);
                    event.setIdentifity(mIdentifier);
                    EventBus.getDefault().post(event);
                } else {
                    EventBus.getDefault().post(new HangUpTipEntity(133, -1));
                }
                finish();
                break;
            case R.id.btn_positive_custom_dialog:
                //acceptCall(callId, notification.getSponsorId(), callType);
                if (NetWorkUtil.isNetworkAvailable(this)) {
                    if (mAction != null && mAction.equals("dialogActivity")) {
                        MessageEvent event1 = new MessageEvent();
                        event1.setmType(MainConstant.CANCELVIDEO);
                        event1.setAction(130);
                        event1.setTime(-1);
                        event1.setIdentifity(mIdentifier);
                        EventBus.getDefault().post(event1);
                    } else {
                        EventBus.getDefault().post(new HangUpTipEntity(130, -1));
                    }
                    Intent roomIntent = new Intent(DialogActivity.this, RoomActivity.class);
                    roomIntent.putExtra("callId", mCallId);
                    roomIntent.putExtra("target", "other");
                    roomIntent.putExtra("userSig", userSig);
                    roomIntent.putExtra("identifier", mIdentifier);
                    roomIntent.putExtra("action", mAction);
                    startActivity(roomIntent);
                    finish();
                } else {
                    toast("网络连接异常，请检查网络");
                    finish();
                }

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initSoundPool();
        EventBus.getDefault().register(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.transparent), 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RejectCallTip rejectCallTip) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mSoundPool.release();
    }


    //相机操作

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, holder);
        setCameraDisplayOrientation(this, 1, mCamera);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //先关闭,再开启
        mCamera.stopPreview();
        startPreview(mCamera, holder);
        setCameraDisplayOrientation(this, 1, mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera == null) {
            mCamera = initCamera();
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
                setCameraDisplayOrientation(this, 1, mCamera);
            }
        }


    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();

            mCamera.release();
            mCamera = null;
        }
    }

    public void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result = 0;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setRotation(90);
        mCamera.setParameters(parameters);
    }


    //打开音乐
    private void initSoundPool() {
        if (Build.VERSION.SDK_INT >= 21) {
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build())
                    .build();
        } else {
            // This old constructor is deprecated, but we need it for
            // compatibility.
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        mSoundId = mSoundPool.load(this, R.raw.weixin2, 1);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                android.util.Log.d(TAG, "load完毕" + status);
                mSoundPool.play(mSoundId, 1.0f, 1.0f, 1, -1, 1.0f);

            }
        });

    }


}
