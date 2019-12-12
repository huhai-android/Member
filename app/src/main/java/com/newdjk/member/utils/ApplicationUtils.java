package com.newdjk.member.utils;


import com.newdjk.member.R;
import com.newdjk.member.ui.entity.AppLicationEntity;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  BdMember
 *  @包名：    com.newdjk.bdmember.utils
 *  @文件名:   ApplicationUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/7/22 9:46
 *  @描述：
 */
public class ApplicationUtils {
    static List<AppLicationEntity> listall = new ArrayList<>();
    static List<AppLicationEntity> recordListuse = new ArrayList<>();


    public static List<AppLicationEntity> getListuse() {
        listall.clear();
        if (listall.size() == 0) {

            AppLicationEntity appLicationEntity1 = new AppLicationEntity();
            appLicationEntity1.setAppDesc("图文问诊");
            appLicationEntity1.setImageID(R.drawable.home_icon_2);
            appLicationEntity1.setAppID(1);
            listall.add(appLicationEntity1);


            AppLicationEntity appLicationEntity2 = new AppLicationEntity();
            appLicationEntity2.setAppDesc("视频问诊");
            appLicationEntity2.setImageID(R.drawable.home_icon_3);
            appLicationEntity2.setAppID(2);
            listall.add(appLicationEntity2);

            AppLicationEntity appLicationEntity3 = new AppLicationEntity();
            appLicationEntity3.setAppDesc("在线续方");
            appLicationEntity3.setImageID(R.mipmap.ic_home_item_second_7);
            appLicationEntity3.setAppID(3);
            listall.add(appLicationEntity3);

            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
            appLicationEntity4.setAppDesc("面诊预约");
            appLicationEntity4.setImageID(R.mipmap.mianzhenyuyue);
            appLicationEntity4.setAppID(4);
            listall.add(appLicationEntity4);

            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
            appLicationEntity5.setAppDesc("专科医联体");
            appLicationEntity5.setImageID(R.mipmap.zhuankeyilianti);
            appLicationEntity5.setAppID(5);
            listall.add(appLicationEntity5);

            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
            appLicationEntity6.setAppDesc("医生集团");
            appLicationEntity6.setAppID(6);
            appLicationEntity6.setImageID(R.mipmap.yishengjituan);
            listall.add(appLicationEntity6);

            AppLicationEntity appLicationEntity7 = new AppLicationEntity();
            appLicationEntity7.setAppDesc("诊所");
            appLicationEntity7.setAppID(7);
            appLicationEntity7.setImageID(R.mipmap.zhensuo);
            listall.add(appLicationEntity7);


            AppLicationEntity appLicationEntity8 = new AppLicationEntity();
            appLicationEntity8.setAppDesc("健康资讯");
            appLicationEntity8.setImageID(R.mipmap.ic_home_item_second_8);
            appLicationEntity8.setAppID(8);
            listall.add(appLicationEntity8);


            AppLicationEntity appLicationEntity9 = new AppLicationEntity();
            appLicationEntity9.setAppDesc("第二诊疗意见");
            appLicationEntity9.setAppID(9);
            appLicationEntity9.setImageID(R.mipmap.ic_second_diagnosis);
            listall.add(appLicationEntity9);


            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("护理咨询");
            appLicationEntity10.setAppID(10);
            appLicationEntity10.setImageID(R.mipmap.ic_home_item_second_5);
            listall.add(appLicationEntity10);


            AppLicationEntity appLicationEntity11 = new AppLicationEntity();
            appLicationEntity11.setAppDesc("远程护理");
            appLicationEntity11.setAppID(11);
            appLicationEntity11.setImageID(R.mipmap.kangfuzhidao);
            listall.add(appLicationEntity11);


            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
            appLicationEntity12.setAppDesc("VIP预约");
            appLicationEntity12.setAppID(12);
            appLicationEntity12.setImageID(R.mipmap.vipyuyue);
            listall.add(appLicationEntity12);


            AppLicationEntity appLicationEntity13 = new AppLicationEntity();
            appLicationEntity13.setAppDesc("医生服务包");
            appLicationEntity13.setAppID(13);
            appLicationEntity13.setImageID(R.drawable.icon_home_doctor);
            listall.add(appLicationEntity13);


            AppLicationEntity appLicationEntity14 = new AppLicationEntity();
            appLicationEntity14.setAppDesc("机构服务包");
            appLicationEntity14.setAppID(14);
            appLicationEntity14.setImageID(R.drawable.icon_home_org);
            listall.add(appLicationEntity14);


//            AppLicationEntity appLicationEntity15 = new AppLicationEntity();
//            appLicationEntity15.setAppDesc("远程护理");
//            appLicationEntity15.setAppID(15);
//            appLicationEntity15.setImageID(R.drawable.icon_home_org);
//            listall.add(appLicationEntity15);

            return listall;

        } else {
            return listall;
        }


    }

    public static List<AppLicationEntity> getListChat() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 7; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.mipmap.chat_alb);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.mipmap.icon_camera);
                        break;

                    case 3:
                        appLicationEntity.setAppDesc("补充病历");
                        appLicationEntity.setImageID(R.mipmap.icon_case);
                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("加号");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
//                        break;
//                    case 7:
//                        appLicationEntity.setAppDesc("视频通话");
//                        appLicationEntity.setImageID(R.drawable.videowenzhen);
//                        break;
                    case 4:
                        appLicationEntity.setAppDesc("续方申请");
                        appLicationEntity.setImageID(R.mipmap.ic_chat_more_continuation);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("医生主页");
                        appLicationEntity.setImageID(R.drawable.img_doctor);
                        break;

//                    case 7:
////                        appLicationEntity.setAppDesc("问诊表");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;

//                    case 8:
//                        appLicationEntity.setAppDesc("宣教");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;
                    case 6:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.img_video);
                        break;
                    case 7:

                        appLicationEntity.setAppDesc("结束问诊");
                        appLicationEntity.setImageID(R.mipmap.img_end);
                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }

    public static List<AppLicationEntity> getListChatZixun() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 5; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.mipmap.chat_alb);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.mipmap.icon_camera);
                        break;

//                    case 4:
//                        appLicationEntity.setAppDesc("补充病历");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon4);
//                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("加号");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
//                        break;
//                    case 7:
//                        appLicationEntity.setAppDesc("视频通话");
//                        appLicationEntity.setImageID(R.drawable.videowenzhen);
//                        break;
                    case 3:
                        appLicationEntity.setAppDesc("医生主页");
                        appLicationEntity.setImageID(R.drawable.img_doctor);
                        break;

//                    case 7:
////                        appLicationEntity.setAppDesc("问诊表");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;

//                    case 8:
//                        appLicationEntity.setAppDesc("宣教");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;
                    case 4:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.img_video);
                        break;
                    case 5:

                        appLicationEntity.setAppDesc("结束问诊");
                        appLicationEntity.setImageID(R.mipmap.img_end);
                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }



    public static List<AppLicationEntity> getListChatfenhuizhen() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 4; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.mipmap.chat_alb);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.mipmap.icon_camera);
                        break;

//                    case 4:
//                        appLicationEntity.setAppDesc("补充病历");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon4);
//                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("加号");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
//                        break;
//                    case 7:
//                        appLicationEntity.setAppDesc("视频通话");
//                        appLicationEntity.setImageID(R.drawable.videowenzhen);
//                        break;
                    case 3:
                        appLicationEntity.setAppDesc("医生主页");
                        appLicationEntity.setImageID(R.drawable.img_doctor);
                        break;

//                    case 7:
////                        appLicationEntity.setAppDesc("问诊表");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;

//                    case 8:
//                        appLicationEntity.setAppDesc("宣教");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;
                    case 4:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.img_video);
                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }

}
