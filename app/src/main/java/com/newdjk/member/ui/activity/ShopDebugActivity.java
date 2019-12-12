package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.activity.min.ShopWebViewActivity;
import com.newdjk.member.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopDebugActivity extends BasicActivity {


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
    @BindView(R.id.mSuggestionContent)
    EditText mSuggestionContent;
    @BindView(R.id.btn_save)
    AppCompatButton btnSave;
    @BindView(R.id.btn_todebug)
    AppCompatButton btnTodebug;
    @BindView(R.id.copy)
    AppCompatButton copy;

    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList = new ArrayList<>(); //上传的图片凭证的数据源
    private Gson mGson;
    private static final int IMG_REQUEST_CODE = 0x010;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme), 0);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_shopdebug;
    }

    @Override
    protected void initView() {
        String data = SpUtils.getString("debugH5");
        mSuggestionContent.setText(data);
        initTitle("输入调试H5界面地址").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
    }


    @Override
    protected void initListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSuggestionContent.getText().toString())) {
                    toast("H5调试地址为空");
                }
                SpUtils.put("debugH5", mSuggestionContent.getText().toString());
                toast("保存地址成功");
            }
        });
        btnTodebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent debugindent = new Intent(ShopDebugActivity.this, ShopWebViewActivity.class);
                startActivity(debugindent);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSuggestionContent.setText("file:///android_asset/index.html#/doctor-index?DrId=147");
                SpUtils.put("debugH5", "file:///android_asset/index.html#/doctor-index?DrId=147");

            }
        });
    }

    @Override
    protected void initData() {
        mGson = new Gson();
    }

    @Override
    protected void otherViewClick(View view) {

    }


}
