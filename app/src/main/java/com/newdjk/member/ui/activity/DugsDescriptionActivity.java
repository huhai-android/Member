package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.views.ItemView;

import butterknife.BindView;

public class DugsDescriptionActivity extends BasicActivity {


    @BindView(R.id.mContent)
    TextView mContent;
    private String content;
    private String title;

    @Override
    protected int initViewResId() {
        return R.layout.activity_dugs_description;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            content = intent.getStringExtra("content");
            title = intent.getStringExtra("title");
            mContent.setText(content);
        }

        initTitle(title).setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }
}
