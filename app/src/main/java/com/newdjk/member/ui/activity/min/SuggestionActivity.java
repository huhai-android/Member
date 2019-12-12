package com.newdjk.member.ui.activity.min;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.MyBoxingActivity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.PicturePathEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.ui.entity.SuggesttionEntity;
import com.newdjk.member.utils.DisplayUtil;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.ImageBase64;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.StrUtil;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.MultiImageUploadView;
import com.newdjk.member.views.RoundImageUploadView;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;

public class SuggestionActivity extends BasicActivity {


    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.upload_view)
    RoundImageUploadView uploadView;
    @BindView(R.id.tv_pic_number)
    TextView tvPicNumber;
    @BindView(R.id.mSuggestionContent)
    EditText mSuggestionContent;
    @BindView(R.id.mEmail)
    EditText mEmail;


    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList = new ArrayList<>(); //上传的图片凭证的数据源
    private Gson mGson;
    private static final int IMG_REQUEST_CODE = 0x010;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_suggestion;
    }

    @Override
    protected void initView() {
        initTitle("意见反馈").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitContent();
            }
        });

        setUploadView();
    }

    private void setUploadView() {
        uploadView.setAddHandlerImage(R.mipmap.image_add);
        uploadView.setMax(6);
        uploadView.setNumCol(4);
        uploadView.setImgMargin(DisplayUtil.dp2px(this, 10));
        uploadView.setCloseHandlerImage(R.drawable.ic_delete_photo);
        uploadView.setOnImageChangeListener(new MultiImageUploadView.OnImageChangeListener() {
            @Override
            public void onImageChage(List<File> imgFiles, int maxSize) {
                tvPicNumber.setText(String.format("(%d/%d)", imgFiles.size(), maxSize));
            }
        });
        uploadView.setPadding(0, 0, 0, 0);
        uploadView.setAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectImage();
            }
        });

        uploadView.setOnDelPicListener(new MultiImageUploadView.OnDelPicListener() {
            @Override
            public void onDelPicListener(int pos) {
                mSavePicList.remove(pos);
            }
        });
    }


    private void startSelectImage() {
        BoxingConfig mulitImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG)
                .needCamera(R.drawable.ic_camera)
                .needGif()
                .withMaxCount(uploadView.getMax() - uploadView.getFiles().size());
        Boxing.of(mulitImgConfig).
                withIntent(this, MyBoxingActivity.class).
                start(this, IMG_REQUEST_CODE);
    }

    /**
     * 提交意见反馈的内容
     */
    private void commitContent() {
        if (!StrUtil.isNotEmpty(mSuggestionContent, true)) {
            toast("请输入建议内容");
            return;
        }
        if (!StrUtil.isNotEmpty(mEmail, true)) {
            toast("请输入联系方式");
            return;
        }
//        if (mSavePicList == null || mSavePicList.size() == 0) {
//            toast("请上传图片");
//            return;
//        }

        loading(true);
        String mSourceId = String.valueOf(SpUtils.getInt(Contants.Id,0));
        String mSourceName = (String) SpUtils.get(Contants.Name, "");
        SuggesttionEntity mSuggesttionEntity = new SuggesttionEntity();
        mSuggesttionEntity.setSourceId(mSourceId);
        mSuggesttionEntity.setSourceName(mSourceName);
        mSuggesttionEntity.setSourceType(0);
        mSuggesttionEntity.setContact(mEmail.getText().toString());
        mSuggesttionEntity.setAdvice(mSuggestionContent.getText().toString());
        if (mSavePicList != null) {
            mSuggesttionEntity.setImgPaths(mSavePicList);
        }
        String json = mGson.toJson(mSuggesttionEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i("zdp", "json=" + json);
        mMyOkhttp.post().url(HttpUrl.AddFeedBack).headers(HeadUitl.instance.getHeads()).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    toast("提交成功");
                    finish();
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGson = new Gson();
    }

    @Override
    protected void otherViewClick(View view) {

    }



    // 处理选择的照片的地址
    private void refreshAdapter(List<BaseMedia> picList) {
        mPicList.clear();
        for (BaseMedia localMedia : picList) {
            //被压缩后的图片路径
            String compressPath = localMedia.getPath(); //压缩后的图片路径
            mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
        }

        for (String path : mPicList) {
            uploadPicture(path);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMG_REQUEST_CODE:
                    ArrayList<BaseMedia> images = Boxing.getResult(data);
                    if (images != null) {
                        for (BaseMedia image : images) {
                            uploadView.addFile(new File(image.getPath()));
                        }
                    }
                    refreshAdapter(images);

                    break;
            }
        }

    }

    private void uploadPicture(String path) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.FeedbackImgLoad).headers(HeadUitl.instance.getHeads()).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        if (entituy.getCode() == 0) {
                            mSavePicList.add(entituy.getData().getSavePath());
                            Log.d("", mSavePicList.size() + "");
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }



}
