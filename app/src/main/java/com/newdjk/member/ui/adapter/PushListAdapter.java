package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.greendao.gen.PushDataDaoEntityDao;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.ChatActivity;
import com.newdjk.member.ui.activity.ReadingReportActivity;
import com.newdjk.member.ui.activity.min.ReviewDiagnosisReportActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.CheckTableEntity;
import com.newdjk.member.ui.entity.ConsultMessageEntity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.ExtraPushData;
import com.newdjk.member.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.member.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.ui.entity.PushDataDaoEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.ui.entity.VipPushEntity;
import com.newdjk.member.ui.entity.ZhuanzhenEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SQLiteUtils;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.SingleButtonDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushListAdapter extends RecyclerView.Adapter<PushListAdapter.ViewHolder> {


    private Context mActivity;
    //  private ViewHolder viewHolder;
    private List<PushDataDaoEntity> mTIMMessageList;
    private Gson mGson;
    protected MyOkHttp mMyOkhttp;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String mAction;
    private SingleButtonDialog mDialog;

    public PushListAdapter(List<PushDataDaoEntity> list, Context activity, String action) {
        mActivity = activity;
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        mTIMMessageList = list;
        this.mAction = action;
        mGson = new Gson();
        mDialog = new SingleButtonDialog(activity);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.
                from(MyApplication.getContext()).inflate(R.layout.push_list_item, parent, false));
        //   this.viewHolder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PushDataDaoEntity jpushDataEntity = mTIMMessageList.get(position);
        String title = jpushDataEntity.getTitle();
        boolean isRead = jpushDataEntity.getIsRead();
        int moduleType = jpushDataEntity.getModuleType();
        switch (moduleType) {
            case 1:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon1)
                        .into(holder.avatar);
                break;
            case 2:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon2)
                        .into(holder.avatar);
                break;
            case 3:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon3)
                        .into(holder.avatar);
                break;
            case 4:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon4)
                        .into(holder.avatar);
                break;

            case 5:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon5)
                        .into(holder.avatar);
                break;

            case 6:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon6)
                        .into(holder.avatar);
                break;

            case 7:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon7)
                        .into(holder.avatar);
                break;
            case 8:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon8)
                        .into(holder.avatar);
                break;
            case 9:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message7)
                        .into(holder.avatar);
                break;
            case 10:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon10)
                        .into(holder.avatar);
                break;
            case 11:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon11)
                        .into(holder.avatar);
                break;
            case 12:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.message_inner_icon12)
                        .into(holder.avatar);
                break;
            case 13:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.ic_service_second_diagnosis)
                        .into(holder.avatar);
                break;


            case 14:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.icon_vip)
                        .into(holder.avatar);
                break;
            case 15:
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.icon_yuanchengmenzhen)
                        .into(holder.avatar);
                break;

        }
        if (isRead) {
            holder.systemUnreadNumLayout.setVisibility(View.GONE);
        } else {
            holder.systemUnreadNumLayout.setVisibility(View.VISIBLE);
        }

        if (title != null) {
            holder.name.setText(title);
        }
        holder.last_message.setText(jpushDataEntity.getContent());
        Date time = jpushDataEntity.getTime();
        holder.message_time.setText(mFormatter.format(time));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTIMMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView last_message;
        TextView message_time;
        View systemUnreadNumLayout;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            last_message = (TextView) itemView.findViewById(R.id.last_message);
            message_time = (TextView) itemView.findViewById(R.id.message_time);
            systemUnreadNumLayout = itemView.findViewById(R.id.system_unread_num_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PushDataDaoEntity mPushDataDaoEntity = mTIMMessageList.get(getAdapterPosition());
                    String extras = mPushDataDaoEntity.getContent();
                    int msgId = mPushDataDaoEntity.getMsgId();
                    Log.i("MessageAdapter", mPushDataDaoEntity.toString());
                    if (extras != null) {
                        mPushDataDaoEntity.setIsRead(true);
                        PushDataDaoEntity PushDataDaoEntity = MyApplication.getInstance().getDaoSession().getPushDataDaoEntityDao().queryBuilder().where(PushDataDaoEntityDao.Properties.Id.eq(mPushDataDaoEntity.getId())).build().unique();
                       /* SQLiteUtils.getInstance().addPushData(new PushDataDaoEntity(JpushDataEntity.getId(), JpushDataEntity.getTitle(), JpushDataEntity.getMessage(), extras, JpushDataEntity.getSendTime(),JpushDataEntity.isRead()));
                        EventBus.getDefault().post(new UpdatePushView(true));*/
                        if (PushDataDaoEntity != null) {
                            PushDataDaoEntity.setIsRead(true);
                            setServicePushMessageRead(String.valueOf(msgId));
                            SQLiteUtils.getInstance().updatePushData(PushDataDaoEntity);
                            MessageEvent messageEvent = new MessageEvent();
                            messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                            EventBus.getDefault().post(messageEvent);
                            notifyItemChanged(getAdapterPosition());
                            //EventBus.getDefault().post(new UpdatePushView(1));
                        }
                        int modulType = mPushDataDaoEntity.getModuleType();
//                        String recordId = extrasDataEntity.getData().getRecordId();
                        //1图文问诊 2 视频问诊 3 在线续方 4 胎心监护 5 处方（处方订单） 6 处方（处方配送）
                        // 7 系统消息（评价提醒）8 系统消息（患者报道） 9 系统消息（系统消息） 10 随访（问卷调查）
                        //11 随访（健康宣教） 12 随访（康复提醒）
                        String data = mPushDataDaoEntity.getData();
                        Log.i("MessageAdapter", data);
                        ExtraPushData mExtraPushData = mGson.fromJson(data, ExtraPushData.class);
                        int type = mExtraPushData.getType();
                        switch (type) {
                            case 20://患者报道 不做处理
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 21://处方单  【处方详情】
                                Intent preIntent = new Intent(mActivity, WebViewActivity.class);
                                preIntent.putExtra("type", WebUtil.prePay);
                                preIntent.putExtra("PrescriptionId", Integer.valueOf(mExtraPushData.getData().getPrescriptionId()));
                                mActivity.startActivity(preIntent);
                                break;
                            case 202://处方驳回   【处方详情】
                            case 205:  //处方驳回   【处方详情】
                                Intent intent1 = new Intent(mActivity, WebViewActivity.class);
                                intent1.putExtra("type", 33);
                                intent1.putExtra("PrescriptionId", Integer.valueOf(mExtraPushData.getData().getPrescriptionId()));
                                mActivity.startActivity(intent1);
                                break;
                            case 22://处方支付成功提醒  【处方订单详情】
                                Intent intent = new Intent(mActivity, WebViewActivity.class);
                                intent.putExtra("type", 32);
                                intent.putExtra("PrescriptionId", Integer.valueOf(mExtraPushData.getData().getPrescriptionId()));
                                mActivity.startActivity(intent);
                                break;
                            case 23: //问诊结束提醒
                                int moduleType = mExtraPushData.getModuleType();
                                switch (moduleType) {
                                    //图文问诊
                                    case 1:
                                        QueryConsultDoctorAppMessageByPage(mExtraPushData.getData().getRecordId());
                                        break;
                                    //视频问诊
                                    case 2:
                                        QueryvideoDoctorAppMessageByPage(mExtraPushData.getData().getRecordId());
                                        break;
                                    //在线续方
                                    case 3:
                                        QueryRenewalDoctorAppMessageByPage(mExtraPushData.getData().getRecordId());
                                        break;
                                }
                                break;
                            case 24://视频问诊预约成功提醒
                                QueryvideoDoctorAppMessageByPage(mExtraPushData.getData().getRecordId());
                                break;
                            case 25://评价提醒
                                Intent recommendIntent = new Intent(mActivity, WebViewActivity.class);
                                recommendIntent.putExtra("type", WebUtil.createEvaluate);
                                recommendIntent.putExtra("docId", mExtraPushData.getData().getDoctorId());
                                recommendIntent.putExtra("patientId", mExtraPushData.getData().getPatientId());
                                recommendIntent.putExtra("serviceType", mExtraPushData.getData().getServiceType());
                                recommendIntent.putExtra("relationId", mExtraPushData.getData().getRecordId());
                                mActivity.startActivity(recommendIntent);
                                break;
                            case 26://医生主动提醒用户上传病例
                                Intent caseIntent = new Intent(mActivity, WebViewActivity.class);
                                caseIntent.putExtra("type", WebUtil.caseTip);
                                mActivity.startActivity(caseIntent);
                                break;
                            case 27: //用户主动上传病例，提醒医生查看
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 28://加号凭证
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 29://空着
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 200://监护报告提醒
                                if (!TextUtils.isEmpty(mExtraPushData.getData().getAskId())) {
                                    int askId = Integer.valueOf(mExtraPushData.getData().getAskId());
                                    Intent intentRecord = new Intent(mActivity, ReadingReportActivity.class);
                                    intentRecord.putExtra("id", askId);
                                    intentRecord.putExtra("status", 1);
                                    mActivity.startActivity(intentRecord);
                                }
                                break;
                            case 201://母胎监测服务结算驳回   不处理
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 203://已发货通知 【处方订单详情】
                            case 204://已收货通知 【处方订单详情】
                            case 206://处方下单成功  【处方订单详情】
                                Intent intent32 = new Intent(mActivity, WebViewActivity.class);
                                intent32.putExtra("type", 32);
                                intent32.putExtra("PrescriptionId", Integer.valueOf(mExtraPushData.getData().getPrescriptionId()));
                                mActivity.startActivity(intent32);
                                break;

                            case 207://健康宣教 Url。跳转至Url
                            case 211://随访发送问卷量表任务时 Url。跳转至Url
                            case 213: //随访发送康复指导任务时  Url。跳转至Url
                                Intent mLinkIntent = new Intent(mActivity, WebViewActivity.class);
                                mLinkIntent.putExtra("type", 21);
                                mLinkIntent.putExtra("url", mExtraPushData.getData().getUrl());
                                mActivity.startActivity(mLinkIntent);
                                break;
                            case 208://随访发送复诊提醒任务时  DrId。跳转至医生主页
                                Intent doctorFirstPageIntent = new Intent(mActivity, WebViewActivity.class);
                                doctorFirstPageIntent.putExtra("type", 24);
                                doctorFirstPageIntent.putExtra("doctorId", mExtraPushData.getData().getDoctorId());
                                break;
                            case 209://随访发送复查提醒任务时  不做跳转
                                showdialog(mPushDataDaoEntity);
                                break;
                            case 210://随访发送用药提醒任务时   跳转在线续方页面
                                Intent onrealPageIntent = new Intent(mActivity, WebViewActivity.class);
                                onrealPageIntent.putExtra("type", 19);
                                onrealPageIntent.putExtra("doctorId", mExtraPushData.getData().getDoctorId());
                                break;

                            case 212://随访发送上传病历/上传检查检验报告任务时  跳转至上传病历页面
                                Intent medicalRecordsIntent = new Intent(mActivity, WebViewActivity.class);
                                medicalRecordsIntent.putExtra("type", 26);
                                medicalRecordsIntent.putExtra("medicalTempId", mExtraPushData.getData().getMedicalTempId());
                                mActivity.startActivity(medicalRecordsIntent);
                                break;
                            case 215://第二诊疗页面跳转至病例补充界面
                                Intent secondRecordsIntent = new Intent(mActivity, WebViewActivity.class);
                                secondRecordsIntent.putExtra("type", WebUtil.perfectInformation);
                                secondRecordsIntent.putExtra("id", mExtraPushData.getData().getMedicalRecordId());
                                mActivity.startActivity(secondRecordsIntent);
                                break;
                            case 216://查看第二诊疗pdf报告
                                Intent pdfReportIntent = new Intent(mActivity, ReviewDiagnosisReportActivity.class);
                                pdfReportIntent.putExtra("pdfUrl", mExtraPushData.getData().getMedicalReportPath());
                                mActivity.startActivity(pdfReportIntent);
                                break;
                            case 217://页面跳转至【填写主要问题】页面
                                Intent secondWriteIntent = new Intent(mActivity, WebViewActivity.class);
                                secondWriteIntent.putExtra("id", mExtraPushData.getData().getMedicalRecordId());
                                secondWriteIntent.putExtra("type", WebUtil.secondWriteQuestion);
                                mActivity.startActivity(secondWriteIntent);
                                break;
                            case 218://页面跳转至【我的订单】页面
                                Intent secondOrderIntent = new Intent(mActivity, WebViewActivity.class);
                                secondOrderIntent.putExtra("type", WebUtil.secondDiagnosisOrder);
                                mActivity.startActivity(secondOrderIntent);
                                break;
                            case 224://支付完成后提醒

                                Intent check = new Intent(mActivity, WebViewActivity.class);
                                check.putExtra("type", WebUtil.checklist);
                                mActivity.startActivity(check);
                                break;

                            case 225://页面跳转确认界面

                                CheckTableEntity checkTableEntity = mGson.fromJson(data, CheckTableEntity.class);
                                Intent intentcheck = new Intent(mActivity, WebViewActivity.class);
                                intentcheck.putExtra("type", WebUtil.checktable);
                                intentcheck.putExtra("CensorateRecordId", checkTableEntity.getData().getCensorateRecordId() + "");
                                mActivity.startActivity(intentcheck);
                                break;


                            case 226://页面跳转确认界面
                                ZhuanzhenEntity zhuanzhenEntity = mGson.fromJson(data, ZhuanzhenEntity.class);
                                Intent intentzhuanzhen = new Intent(mActivity, WebViewActivity.class);
                                intentzhuanzhen.putExtra("type", WebUtil.zhuanzhen);
                                intentzhuanzhen.putExtra("zhuanzhenid", zhuanzhenEntity.getData().getReferralRecordId() + "");
                                mActivity.startActivity(intentzhuanzhen);
                                break;


                            case 227:  //中药开方
                                Intent prescriptionTCMIntent = new Intent(mActivity, WebViewActivity.class);
                                prescriptionTCMIntent.putExtra("action", String.valueOf(mExtraPushData.getData().getPrescriptionId()));
                                prescriptionTCMIntent.putExtra("type", WebUtil.ZhongYaoChuFangDetail);
                                mActivity.startActivity(prescriptionTCMIntent);
                                break;
                            case 234:
                                // ToastUtil.setToast("已经支付预约后提醒");
                                VipPushEntity vipEntity = mGson.fromJson(data, VipPushEntity.class);
                                Intent VipList = new Intent(mActivity, WebViewActivity.class);
                                VipList.putExtra("type", WebUtil.VIPList);
                                VipList.putExtra("ApRecordId", vipEntity.getData().getApRecordId());
                                mActivity.startActivity(VipList);
                                break;
                            case 235:
                                //  ToastUtil.setToast("分配医生，待支付提醒");
                                VipPushEntity vipPushEntity = mGson.fromJson(data, VipPushEntity.class);
                                Intent VipPay = new Intent(mActivity, WebViewActivity.class);
                                VipPay.putExtra("type", WebUtil.VIPPay);
                                VipPay.putExtra("ApRecordId", vipPushEntity.getData().getApRecordId());
                                mActivity.startActivity(VipPay);

                                break;

                            case 236:
                                // ToastUtil.setToast("Vip预约成功提醒");
                                VipPushEntity vipEntity2 = mGson.fromJson(data, VipPushEntity.class);
                                Intent VipList2 = new Intent(mActivity, WebViewActivity.class);
                                VipList2.putExtra("type", WebUtil.VIPList);
                                VipList2.putExtra("ApRecordId", vipEntity2.getData().getApRecordId());
                                mActivity.startActivity(VipList2);
                                break;

                            default:
                                showdialog(mPushDataDaoEntity);

                                break;
                        }

                    }
                }
            });
        }
    }

    private void showdialog(PushDataDaoEntity mPushDataDaoEntity) {
        mDialog.show("温馨提示", mPushDataDaoEntity.content, new SingleButtonDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {

            }
        });
    }

    public void resetData(List<PushDataDaoEntity> timMessageList) {
        mTIMMessageList = timMessageList;
        notifyDataSetChanged();
    }

    private void QueryConsultDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultMessageEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ConsultMessageEntity consultMessageEntity = entity.getData();
                    Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);
                    Intent consultIntentTalk = new Intent(mActivity, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, consultMessageEntity.getDoctorName());
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, consultMessageEntity.getDoctorIMId());
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("action", "pictureConsult");
                    consultIntentTalk.putExtra("prescriptionMessage", json);
                    mActivity.startActivity(consultIntentTalk);
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
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
                if (entity.getCode() == 0) {

                    OnlineRenewalDataEntity onlineRenewalDataEntity = entity.getData();
                    if (onlineRenewalDataEntity != null) {
                        Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                        }.getType();
                        //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                        PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                        renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                        String renewalJson = mGson.toJson(renewalMessageEntity);
                        Intent renewalIntentTalk = new Intent(mActivity, ChatActivity.class);
                        renewalIntentTalk.putExtra(Contants.FRIEND_NAME, onlineRenewalDataEntity.getPatientName());
                        renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                        renewalIntentTalk.putExtra("action", "onlineRenewal");
                        renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, onlineRenewalDataEntity.getDoctorIMId());
                        renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                        mActivity.startActivity(renewalIntentTalk);
                    }

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
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
                if (entity.getCode() == 0) {


                    InquiryRecordListDataEntity inquiryRecordListDataEntity = entity.getData();
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    Intent videoIntentTalk = new Intent(mActivity, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, inquiryRecordListDataEntity.getDoctorName());
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getDoctorIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    mActivity.startActivity(videoIntentTalk);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    /**
     * 向服务器写已读记录
     *
     * @param msgId
     */
    private void setServicePushMessageRead(String msgId) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("MsgId", msgId);
        requestMap.put("UserId", SpUtils.getInt(Contants.AccountId,0)+"");
        requestMap.put("AppId", BuildConfig.JPUSH_APPKEY);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.Read).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    LoadDialog.clear();
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
