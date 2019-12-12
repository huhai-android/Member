package com.newdjk.member.ui.activity.min;


import android.os.Bundle;
import android.view.View;


/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui
 *  @文件名:   ReviewDocumentActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/27 19:08
 *  @描述：
 */
public class ReviewDocumentActivity extends DocumentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (tvRigth!=null){
            tvRigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* Intent systemSettingIntent = new Intent(ReviewDocumentActivity.this, SecondDiagnosisReportActivity.class);
                systemSettingIntent.putExtra("MedicalRecordId", mMedicalRecordId);
                systemSettingIntent.putExtra("type", 1);
                startActivity(systemSettingIntent);*/
                }
            });
            tvRigth.setVisibility(View.GONE);
        }

        if (topleft!=null){
            topleft.setOnClickListener(v -> this.finish());
        }


    }
}
