package com.newdjk.member.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.ChatActivity;
import com.newdjk.member.ui.activity.min.GroupChatActivity;
import com.newdjk.member.ui.entity.ConsultMessageEntity;
import com.newdjk.member.ui.entity.ImDataEntity;
import com.newdjk.member.ui.entity.ImRelationRecode;
import com.newdjk.member.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.member.ui.entity.MDTDetailEntity;
import com.newdjk.member.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.GlideMediaLoader;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.NetWorkUtil;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.TimeUtil;
import com.newdjk.member.utils.ToastUtil;
import com.newdjk.member.views.CircleImageView;
import com.newdjk.member.views.GlideImageLoader;
import com.newdjk.member.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabriel on 2017/2/28.
 */

public class MessageAdapter extends BaseQuickAdapter<ImDataEntity, BaseViewHolder> {

    protected MyOkHttp mMyOkhttp;
    private Gson mGson;

    public MessageAdapter(@Nullable List<ImDataEntity> data) {
        super(R.layout.item_conversation, data);
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ImDataEntity item) {
        long unReadNum = item.getUnReadNum();
        String identifier = item.getIdentifier();
        if (item.getType()==1){
            List<String> doctorIdList = new ArrayList<>();
            doctorIdList.add(item.getIdentifier());
            TIMFriendshipManager.getInstance().getUsersProfile(doctorIdList, new TIMValueCallBack<List<TIMUserProfile>>() {
                @Override
                public void onError(int i, String s) {
                    helper.setText(R.id.name, identifier);
                }

                @Override
                public void onSuccess(List<TIMUserProfile> timUserProfiles) {

                    TIMUserProfile tIMUserProfile = timUserProfiles.get(0);
                    Log.d(TAG,"打印个人信息单聊"+identifier+tIMUserProfile.getNickName());
                    String name = tIMUserProfile.getNickName();
                    if (!TextUtils.isEmpty(name)) {
                        helper.setText(R.id.name, name);
                    } else {
                        helper.setText(R.id.name, identifier);
                    }

                    Glide.with(MyApplication.getContext())
                            .load(tIMUserProfile.getFaceUrl())
                            .dontAnimate()
                            .into(((CircleImageView) helper.getView(R.id.avatar)));

                }
            });



        }else if (item.getType()==2){
            List<String> doctorIdList = new ArrayList<>();
            doctorIdList.add(item.getIdentifier());
            TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                @Override
                public void onError(int i, String s) {
                    helper.setText(R.id.name, identifier);
                    helper.setImageResource(R.id.avatar,R.drawable.group_icon);
                }

                @Override
                public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {

                    TIMGroupDetailInfo tIMUserProfile = timGroupDetailInfos.get(0);
                    Log.d(TAG,"打印个人信息群聊"+item.getIdentifier()+tIMUserProfile.getGroupName());

                    String name= tIMUserProfile.getGroupName();
                    if (!TextUtils.isEmpty(name)) {

                        helper.setText(R.id.name, name);
                    } else {
                        helper.setText(R.id.name, identifier);
                    }

                    helper.setImageResource(R.id.avatar,R.drawable.group_icon);
                }
            });

        }
        if (unReadNum > 0) {
            helper.setVisible(R.id.unread_num, true);
        } else {
            helper.setVisible(R.id.unread_num, false);
        }



        helper.setText(R.id.message_time, TimeUtil.getChatTimeStr(item.getLastTime()));
        helper.setText(R.id.last_message, TextUtils.isEmpty(item.getLastMsg()) ? "回复一条新消息" : item.getLastMsg());
        helper.setOnClickListener(R.id.itemview, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkUtil.isNetworkAvailable(mContext)) {
                    TIMConversation conversation = null;
                    String faceUrl = item.getFaceUrl();
                    if (item.getType() == 2) {
                        // ToastUtil.setToast("群聊天");
                        conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.Group,    //会话类型：单聊
                                item.getIdentifier());      //会话对方用户帐号
                    } else if (item.getType() == 1) {
                        conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.C2C,    //会话类型：单聊
                                item.getIdentifier());      //会话对方用户帐号
                    } else {
                        return;
                    }


                    //将此会话的所有消息标记为已读
                    List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);
                    LoadDialog.show(mContext);
                    if (lastMsgs.size() > 0) {
                        TIMMessage msg = lastMsgs.get(0);

                        conversation.setReadMessage(msg, new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                LoadDialog.clear();
                                Log.e("setReadMessage", "setReadMessage failed, code: " + code + "|desc: " + desc);
                            }

                            @Override
                            public void onSuccess() {
                                Log.d("setReadMessage", "setReadMessage succ");
                                helper.setVisible(R.id.unread_num, false);
                                String identifier = item.getIdentifier();
                                String imId = SpUtils.getString(Contants.ImId);
                                if (item.getType()==1){
                                    getIMRelationRecord(imId, identifier, faceUrl);
                                }else if (item.getType()==2){
                                    getIMRelationRecordforGroup(item);
                                }

                            }
                        });
                    }

                } else {
                    ToastUtil.setToast("网络连接异常，请检查网络");

                }

            }
        });
    }


    private void getIMRelationRecord(String patientImId, String doctorIMId, String faceUrl) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorIMId", doctorIMId);
        map.put("PatientIMId", patientImId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetIMRelationRecord).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ImRelationRecode>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ImRelationRecode> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());

                if (entity.getCode() == 0) {
                    ImRelationRecode imRelationRecode = entity.getData();
                    if (imRelationRecode != null) {
                        int serviceCode = imRelationRecode.getServiceType();
                        String recordId = String.valueOf(imRelationRecode.getRecordId());
                        //1 图文 2 视频 3 续方  5 护理咨询  6远程护理
                        if (serviceCode == 1 || serviceCode == 5) {
                            QueryConsultDoctorAppMessageByPage(recordId);
                        } else if (serviceCode == 2 || serviceCode == 6) {
                            QueryvideoDoctorAppMessageByPage(recordId);
                        } else if (serviceCode == 3) {
                            QueryRenewalDoctorAppMessageByPage(recordId);
                        } else if (serviceCode == 0) {
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("status", 0);
                            intent.putExtra(Contants.FRIEND_NAME, imRelationRecode.getDoctorName());
                            intent.putExtra(Contants.FRIEND_IDENTIFIER, imRelationRecode.getDoctorIMId());
                            intent.putExtra("drId", imRelationRecode.getDoctorId());
                            intent.putExtra("mLeftAvatorImagePath", faceUrl);
                            //intent.putExtra("head",imRelationRecode.)
                            mContext.startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryConsultDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecord).params(requestMap).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultMessageEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    ConsultMessageEntity consultMessageEntity = entity.getData();
                    Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);
                    String doctorImId = consultMessageEntity.getDoctorIMId();
                    String doctorName = consultMessageEntity.getDoctorName();
                    Intent consultIntentTalk = new Intent(mContext, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("drId", consultMessageEntity.getDoctorId());
                    consultIntentTalk.putExtra("action", "pictureConsult");
                    mContext.startActivity(consultIntentTalk);
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryRenewalDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetPrescriptionApply).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<OnlineRenewalDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    OnlineRenewalDataEntity onlineRenewalDataEntity = entity.getData();
                    Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                    renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                    String renewalJson = mGson.toJson(renewalMessageEntity);
                    String doctorImId = onlineRenewalDataEntity.getDoctorIMId();
                    String doctorName = onlineRenewalDataEntity.getDoctorName();
                    Intent renewalIntentTalk = new Intent(mContext, ChatActivity.class);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                    renewalIntentTalk.putExtra("action", "onlineRenewal");
                    renewalIntentTalk.putExtra("drId", onlineRenewalDataEntity.getDoctorId());
                    renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                    mContext.startActivity(renewalIntentTalk);
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryvideoDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquiryRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<InquiryRecordListDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    InquiryRecordListDataEntity inquiryRecordListDataEntity = entity.getData();
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    String doctorImId = inquiryRecordListDataEntity.getDoctorIMId();
                    String doctorName = inquiryRecordListDataEntity.getDoctorName();
                    Intent videoIntentTalk = new Intent(mContext, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("drId", inquiryRecordListDataEntity.getDoctorId());
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    mContext.startActivity(videoIntentTalk);
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void getIMRelationRecordforGroup(final ImDataEntity imDataEntity) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", imDataEntity.getIdentifier());
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Intent intent = new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("action", "MDT");
                    intent.putExtra("MDTDetailEntity", entity.getData());
                    intent.putExtra(Contants.FRIEND_NAME, imDataEntity.getNickName());
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, imDataEntity.getIdentifier());
                    mContext.startActivity(intent);

                } else {
                    LoadDialog.clear();
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }
}
