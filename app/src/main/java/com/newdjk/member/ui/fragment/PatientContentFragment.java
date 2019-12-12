package com.newdjk.member.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.PaintListEntity;
import com.newdjk.member.utils.GlideMediaLoader;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   ChatFragment1
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 10:57
 *  @描述：
 */
public class PatientContentFragment extends BasicFragment {

    List<PaintListEntity.DataBean> patientlist = new ArrayList<>();
    @BindView(R.id.name)
    TextView name;
    Unbinder unbinder;
    @BindView(R.id.civImg)
    CircleImageView civImg;
    @BindView(R.id.tv_sex_age)
    TextView tvSexAge;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_patient)
    TextView tvPatient;
    private int position = 0;


    @Override
    protected int initViewResId() {
        return R.layout.fragment_patient;
    }

    @Override
    protected void initView() {

        if (position == patientlist.size() || patientlist.size() == 0) {
            tvPatient.setText("添加就诊人");
            name.setVisibility(View.GONE);
            tvSexAge.setText("请添加联系人，进行病例档案管理");
        } else {
            name.setText(patientlist.get(position).getPatientName());
            GlideMediaLoader.loadPhoto(mContext, civImg, patientlist.get(position).getPicturePath());
            String sex = "";
            if (patientlist.get(position).getPatientSex() == 1) {
                sex = "男";
            } else if (patientlist.get(position).getPatientSex() == 2) {
                sex = "女";
            } else {
                sex = "未知";
            }
            if (patientlist.get(position).getDefaultPatient()==1){
                tvDefault.setVisibility(View.VISIBLE);
            }else {
                tvDefault.setVisibility(View.GONE);
            }
            tvSexAge.setText(sex + "  " + patientlist.get(position).getAge());
        }


    }

    @Override
    protected void initListener() {
        tvPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == patientlist.size() || patientlist.size() == 0) {
                    //添加就诊人
                    Intent farorderList = new Intent(getContext(), WebViewActivity.class);
                    farorderList.putExtra("type", WebUtil.addpatient);
                    toActivity(farorderList);


                }  else {
                    //查看病例档案
                    Intent farorderList = new Intent(getContext(), WebViewActivity.class);
                    farorderList.putExtra("type", WebUtil.patientDom);
                    toActivity(farorderList);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }



    public void setdata(List<PaintListEntity.DataBean> patientlist, int position) {
        this.patientlist.clear();
        this.patientlist.addAll(patientlist);
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
