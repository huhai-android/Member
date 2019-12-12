package com.newdjk.member.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.ChatActivity;
import com.newdjk.member.ui.activity.ShowOriginPictureActivity;
import com.newdjk.member.ui.activity.min.WebHeadViewActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.AdviceGoodDetailEntity;
import com.newdjk.member.ui.entity.CustomMessageEntity;
import com.newdjk.member.ui.entity.DoctorIDinfoEntity;
import com.newdjk.member.ui.entity.ImageInfoArrayEntity;
import com.newdjk.member.ui.entity.MDTOrderMessageEntity;
import com.newdjk.member.ui.entity.MsgContentEntity;
import com.newdjk.member.ui.fragment.MinFragment;
import com.newdjk.member.utils.FileUtil;
import com.newdjk.member.utils.FrescoBitmapCallback;
import com.newdjk.member.utils.FrescoLoadUtil;
import com.newdjk.member.utils.GlideMediaLoader;
import com.newdjk.member.utils.MyTIMMessage;
import com.newdjk.member.utils.PDFviewUtils;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.TimeUtil;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.CircleImageView;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.PlusDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMGroupTipsType;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageStatus;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.TIMVideoElem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gabriel on 2017/3/6.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final int mPatientId;
    private List<MyTIMMessage> mTIMMessageList;
    private MediaPlayer mMediaPlayer;
    private static final String TAG = "ChatAdapter";
    public static AnimationDrawable mLastAnimationDrawable;
    private int mPosition = -1;
    private String mPath;
    private Context mContext;
    private Gson mGson;
    private String mleftImagePath;
    private int mDoctorId;
    private TIMConversation mTIMConversation;

    public ChatAdapter(List<MyTIMMessage> timMessage, int doctorId, int patientId, Context context, String leftImagePath, TIMConversation timConversation) {
        mContext = context;
        mTIMMessageList = timMessage;
        mMediaPlayer = new MediaPlayer();
        mleftImagePath = leftImagePath;
        mDoctorId = doctorId;
        mPatientId = patientId;
        mTIMConversation = timConversation;
        mGson = new Gson();

    }

    public void setLeftImagePath(String leftImagePath) {
        mleftImagePath = leftImagePath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(MyApplication.getContext()).inflate(R.layout.item_message, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //由于sdk返回的list是按时间顺序排列的，所以显示时需要反过来
        position = mTIMMessageList.size() - position - 1;
        final int pposition = position;
        final MyTIMMessage myTIMMessage = mTIMMessageList.get(position);
        final TIMMessage timMessage = mTIMMessageList.get(position).getTimMessage();
        boolean isRevoke = myTIMMessage.isRevoke();
        boolean isLocalMessage = mTIMMessageList.get(position).isLocalMessage();
        long timeStamp = 0;
        long lastimeStamp = 0;
        String sender = null;
        if (isLocalMessage) {
            timeStamp = mTIMMessageList.get(position).getMsgTimestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
            sender = mTIMMessageList.get(position).getSendTarget();
        } else {
            sender = timMessage.getSender();
            timeStamp = timMessage.timestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
        }
        final long timeStamp1 = timeStamp;
        Log.i(TAG, "sender=" + sender + "," + SpUtils.getString(Contants.ImId));
        holder.systemMessage.setVisibility(View.VISIBLE);

        holder.systemMessage.setText(TimeUtil.getChatTimeStr(timeStamp));
        holder.systemMessageLayout.setVisibility(View.GONE);


        holder.name.setText(SpUtils.getString(Contants.Name));
        Log.d("聊天类型",timMessage.getConversation().getType()+"");
        if (timMessage.getConversation().getType()== TIMConversationType.C2C){
            holder.name.setVisibility(View.GONE);
        }else{
            holder.name.setVisibility(View.VISIBLE);
        }


        /*holder.systemMessage1.setBackgroundColor(0);
        holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
        holder.systemMessageLayout.setBackgroundResource(0);*/
        if (position != mTIMMessageList.size() - 1) {
            TIMMessage lastTimMessage = mTIMMessageList.get(position + 1).getTimMessage();
            //如果上一条消息与当前这一条的时间间隔小于300秒，则不显示这一条消息的发送时间
            if (timeStamp - lastimeStamp < 300) {
                holder.systemMessage.setVisibility(View.GONE);
            }

        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 5, 10, 5);
        TIMElem element = timMessage.getElement(0);
        Log.i("pp", "type=" + element.getType());
        if (sender.equals(SpUtils.getString(Contants.ImId))) {     //自己向好友发送的消息
//            GlideMediaLoader.loadPhoto(MyApplication.getContext(), holder.rightAvatar, MinFragment.mSelfAvatarImagePath);
            Glide.with(MyApplication.getContext())
                    .load(MinFragment.mSelfAvatarImagePath)
                    .dontAnimate()
                    .placeholder(R.drawable.icon_patient)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.rightAvatar);
            holder.rightMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long nowTime = System.currentTimeMillis() / 1000;
                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                        showPopupWindow(v, myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    } else {
                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

            });
            holder.leftPanel.setVisibility(View.GONE);
            holder.rightPanel.setVisibility(View.VISIBLE);
            holder.rightMessage.removeAllViews();
            holder.rightMessage.setBackgroundResource(R.drawable.chat_blue_bg);
            holder.rightMessage.setGravity(Gravity.CENTER);
            //显示自己向好友发送的消息的发送状态
            if (isLocalMessage) {
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            } else {
                switch (timMessage.status()) {
                    case Sending:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.VISIBLE);
                        break;
                    case SendSucc:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                    case SendFail:
                        holder.sendError.setVisibility(View.VISIBLE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                }
            }
            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("你撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            Log.i("zdp", "element_type=" + element.getType());
            //文字信息处理
            if (element.getType() == TIMElemType.Text) {
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextSize(Dimension.SP, 14);
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.rightMessage.addView(textView, layoutParams);
                //语音信息处理
            } else if (element.getType() == TIMElemType.Sound) {
                Log.i("ChatAdapter", "right");
                TIMSoundElem elem = (TIMSoundElem) element;
                final ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.left_voice3);
                holder.leftMessage.addView(imageView, layoutParams);
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.leftMessage.addView(textView);

                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        mLastAnimationDrawable.selectDrawable(0);
                        mLastAnimationDrawable.stop();
                        //   animationDrawable.stop();
                    }
                });
                holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (mPosition == pposition) {
                                mLastAnimationDrawable.selectDrawable(0);
                                mLastAnimationDrawable.stop();
                                mMediaPlayer.stop();
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                try {
                                    RelativeLayout relativeLayout = (RelativeLayout) v;
                                    ImageView childImageView = (ImageView) relativeLayout.findViewById(R.id.img_id);
                                    AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                    operatePlaySound(AnimationDrawable);
                                    //   mMediaPlayer = new MediaPlayer();
                                    playSound(mMediaPlayer, timMessage, false, null, null);
                                    mPosition = pposition;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });

            }    else if (element.getType() == TIMElemType.Video){


                holder.rightMessage.setBackgroundResource(0);

                View view = View.inflate(mContext, R.layout.item_video, null);
                final ImageView imageView = view.findViewById(R.id.video_image);
                final ImageView playvideo = view.findViewById(R.id.paly_image);
                final RelativeLayout relativeLayout = view.findViewById(R.id.lv_rv_root);

                final TIMVideoElem videoelem = (TIMVideoElem) element;
                int height = (int) videoelem.getSnapshotInfo().getHeight();
                int width = (int) videoelem.getSnapshotInfo().getWidth();
                if (height > width) {
                    height = 320;
                    width = 240;
                } else {
                    width = 320;
                    height = 240;
                }
                RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                        .LayoutParams(width
                        , height);
                imageView.setLayoutParams(pictureLayoutParams);
                Log.d("zdp", "image type: " + videoelem.getSnapshotInfo().getType() +
                        " image size " + videoelem.getSnapshotInfo().getSize() +
                        " image height " + videoelem.getSnapshotInfo().getHeight() +
                        " image width " + videoelem.getSnapshotInfo().getWidth());
                layoutParams.setMargins(0, 0, 0, 0);
                final String imagepath = FileUtil.sdkpath + videoelem.getSnapshotInfo().getUuid() + ".jpg";
                final String videopath = FileUtil.sdkpath + videoelem.getVideoInfo().getUuid() + ".mp4";
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getSnapshotInfo().getUuid()+".jpg");
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getVideoInfo().getUuid()+".mp4");
                //获取截图
                if (FileUtil.fileIsExists(imagepath)) {
                    Log.d(TAG, "图片文件存在");
                    GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                } else {
                    videoelem.getSnapshotInfo().getImage(imagepath, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.d("zdp", "下载视频截图出错" + i + "  " + s);
                        }

                        @Override
                        public void onSuccess() {
                            GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                        }
                    });
                }

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (FileUtil.fileIsExists(videopath)) {
                            Log.d("zdp", "视频文件存在,直接播放"+pposition+" "+videopath);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            File file = new File(videopath);
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(mContext, "com.newdjk.member.fileprovider", file);
                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                uri = Uri.fromFile(file);
                            }
                            intent.setDataAndType(uri, "video/*");
                            mContext.startActivity(intent);

                        } else {
                            LoadDialog.show(mContext,"");
                            videoelem.getVideoInfo().getVideo(videopath, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    LoadDialog.clear();
                                }

                                @Override
                                public void onSuccess() {
                                    LoadDialog.clear();
                                    Log.d("zdp", "下载完成后,再进行播放"+pposition);
                                    videoelem.setVideoPath(videopath);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String path = videoelem.getVideoPath();//该路径可以自定义
                                    File file = new File(path);
                                    Uri uri;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(mContext, "com.newdjk.member.fileprovider", file);
                                    } else {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        uri = Uri.fromFile(file);
                                    }
                                    intent.setDataAndType(uri, "video/*");
                                    mContext.startActivity(intent);
                                }
                            });
                        }

                    }
                });

                holder.rightMessage.addView(view, pictureLayoutParams);



            //图片信息处理
            }else if (element.getType() == TIMElemType.Image) {
                holder.rightMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;
                for (TIMImage timImage : imageElem.getImageList()) {
                  /*  timImage.getImage(new TIMValueCallBack<byte[]>() {
                        @Override
                        public void onError(int code, String desc) {//获取图片失败
                            //错误码 code 和错误描述 desc，可用于定位请求失败原因
                            //错误码 code 含义请参见错误码表
                            Log.d("zdp", "getImage failed. code: " + code + " errmsg: " + desc);
                        }
                        @Override
                        public void onSuccess(byte[] data) {//成功，参数为图片数据
                            //doSomething
                            Log.d("zdp", "getImage success. data size: " + data.length);
                        }
                    });*/

                    if (timImage.getType().toString().equals("Large")) {
                        final String path = timImage.getUrl();
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                    if (timImage.getType().toString().equals("Thumb")) {

                        View mImgLayout = LayoutInflater.
                                from(MyApplication.getContext()).inflate(R.layout.img_layout, null);
                        LinearLayout.LayoutParams layoutParam = new LinearLayout
                                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                , LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParam.setMargins(10, 5, 10, 5);
                        mImgLayout.setLayoutParams(layoutParam);


                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);
                        SimpleDraweeView mSimpleDraweeView = mImgLayout.findViewById(R.id.mSimpleDraweeView);
                        SimpleDraweeView mDefaltPic = mImgLayout.findViewById(R.id.mDefaltPic);

                        Uri uri = new Uri.Builder()
                                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                                .path(String.valueOf(R.mipmap.img_im_default))
                                .build();
                        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(true)
                                .build();
                        mDefaltPic.setController(controller); //设置加载中图片

                        mSimpleDraweeView.setImageURI(Uri.parse(timImage.getUrl()));
                        FrescoLoadUtil.getInstance().loadImageBitmap(timImage.getUrl(), new FrescoBitmapCallback<Bitmap>() {
                            @Override
                            public void onSuccess(Uri uri, Bitmap result) {
                                mDefaltPic.setVisibility(View.GONE);
                                mSimpleDraweeView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Uri uri, Throwable throwable) {
                                mDefaltPic.setVisibility(View.VISIBLE);
                                mSimpleDraweeView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancel(Uri uri) {

                            }
                        });
                        Log.d("zdp", "image type: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth());

//                        GlideMediaLoader.loadImImg(MyApplication.getContext(), imageView, timImage.getUrl());
                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.rightMessage.addView(mImgLayout, pictureLayoutParams);

                    }
                }
            }
            else if (element.getType() == TIMElemType.GroupTips) {//群组消息
                TIMGroupTipsElem tipsElem = (TIMGroupTipsElem) element;
                String grouptitle = "";
                if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyGroupInfo) {
                    //群组事件通知
                    grouptitle = "[修改群名" + tipsElem.getGroupName() + "]";

                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Join) {
                    grouptitle = "加入群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Quit) {
                    grouptitle = "离开群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.SetAdmin) {
                    grouptitle = "设置新群主";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyMemberInfo) {
                    grouptitle = "修改群成员信息";
                }
                holder.systemMessage1.setText(grouptitle);
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            }

            else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    String s = new String(customElem.getData());
                    Log.i("ChatAdapter", "s=" + s);
                   /* RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                            .LayoutParams(600
                            , RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                    Log.i("ChatAdapter", "ssss");
                    Log.e("ChatAdapter", "s=" + s);
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
//                        final ImageView imageView = new ImageView(MyApplication.getContext());
//                        RelativeLayout.LayoutParams layoutParamsImage = new RelativeLayout
//                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
//                                , 40);
//                        layoutParamsImage.addRule(Gravity.CENTER_VERTICAL);
//                        imageView.setId(R.id.img_id);
//                        imageView.setImageResource(R.mipmap.right_voice3);
//                        imageView.setLayoutParams(layoutParamsImage);
//                        holder.rightMessage.addView(imageView);
//                        //     Log.i("zdp", ((TIMSoundElem) element).getDuration() + "\"");
//                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
//                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
//                                , 40);
//                        TextView textView = new TextView(MyApplication.getContext());
//                        textView.setText(msgContentEntity.getSecond() + "\"");
//                        textView.setTextColor(mContext.getResources().getColor(R.color.white));
//                        textView.setTextSize(Dimension.SP, 14);
//                        textLayoutParams.rightMargin = 50;
//                        textLayoutParams.leftMargin = 30;
//                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
//                        textView.setLayoutParams(textLayoutParams);
//                        textView.setGravity(Gravity.CENTER);
//                        holder.rightMessage.addView(textView);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.drawable.left_voice);
                        holder.leftMessage.addView(imageView, layoutParams);
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "");

                        //  Log.i("ChatAdapter", "left"+((TIMSoundElem) element).getDuration());
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        textView.setTextColor(mContext.getResources().getColor(R.color.black));
                        holder.leftMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        imageView.setImageResource(R.mipmap.right_voice3);
                                        mMediaPlayer.stop();
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.right_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }

                            }

                        });
                    } else if (desc != null && desc.equals("TIMImageElem")) {
                        Log.i("TIMImageElem", "s=" + s);
                        holder.rightMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken<List<ImageInfoArrayEntity>>() {
                        }.getType();
                        List<ImageInfoArrayEntity> imageInfoArray = mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() > 0) {
                            for (int i = 0; i < imageInfoArray.size(); i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                    String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\", "/");
                                    holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else if (type == 3) {
//                                    int height = imageInfoArrayEntity.getHeight();
//                                    int width = imageInfoArrayEntity.getWidth();
//                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
//                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
//                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
//                                    ImageView imageView = new ImageView(MyApplication.getContext());
//                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
//                                            " image size " + timImage.getSize() +
//                                            " image height " + timImage.getHeight() +
//                                            " image width " + timImage.getWidth());*/
//
//                                    String url = imageInfoArrayEntity.getURL();
//                                    String path = url.replace("\\", "/");
//                                    Log.i("TIMImageElem", "URL=" + path);
//                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
//                                    layoutParams.setMargins(0, 0, 0, 0);
//                                    holder.rightMessage.addView(imageView, pictureLayoutParams);


                                    View mImgLayout = LayoutInflater.
                                            from(MyApplication.getContext()).inflate(R.layout.img_layout, null);
                                    LinearLayout.LayoutParams layoutParam = new LinearLayout
                                            .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                            , LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParam.setMargins(10, 5, 10, 5);
                                    mImgLayout.setLayoutParams(layoutParam);


                                    int height = imageInfoArrayEntity.getHeight();
                                    int width = imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(width
                                            , height);
                                    SimpleDraweeView mSimpleDraweeView = mImgLayout.findViewById(R.id.mSimpleDraweeView);
                                    SimpleDraweeView mDefaltPic = mImgLayout.findViewById(R.id.mDefaltPic);

                                    Uri uri = new Uri.Builder()
                                            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                                            .path(String.valueOf(R.mipmap.img_im_default))
                                            .build();
                                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setUri(uri)
                                            .setAutoPlayAnimations(true)
                                            .build();
                                    mDefaltPic.setController(controller); //设置加载中图片

                                    mSimpleDraweeView.setImageURI(Uri.parse(imageInfoArrayEntity.getURL()));
                                    FrescoLoadUtil.getInstance().loadImageBitmap(imageInfoArrayEntity.getURL(), new FrescoBitmapCallback<Bitmap>() {
                                        @Override
                                        public void onSuccess(Uri uri, Bitmap result) {
                                            mDefaltPic.setVisibility(View.GONE);
                                            mSimpleDraweeView.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onFailure(Uri uri, Throwable throwable) {
                                            mDefaltPic.setVisibility(View.VISIBLE);
                                            mSimpleDraweeView.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onCancel(Uri uri) {

                                        }
                                    });

                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.rightMessage.addView(mImgLayout, pictureLayoutParams);


                                }
                            }
                        }
                    } else {
                        holder.rightMessage.setBackgroundResource(0);
                        final CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = CustomMessageEntity.isIsSystem();
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            int showType = CustomMessageEntity.getShowType();

                            if (showType == 0 || showType == 1) {
                                boolean IsShowDividingLine = CustomMessageEntity.isShowDividingLine();
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(CustomMessageEntity.getTitle());
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {

                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package, null);
                            LinearLayout.LayoutParams layoutParam = new LinearLayout
                                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                    , LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParam.setMargins(10, 5, 10, 5);
                            serviceView.setLayoutParams(layoutParam);
                            final TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            RelativeLayout titleLayout = serviceView.findViewById(R.id.title_layout);
                            TextView detailText = serviceView.findViewById(R.id.check_detail_text);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            CustomMessageAdapter adapter = new CustomMessageAdapter(CustomMessageEntity.getContent());
                            adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                                @Override
                                public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(view, myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                                    } else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });
//                            list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                            list.setAdapter(adapter);
                            View line = serviceView.findViewById(R.id.line);
                            //  setListViewHeightBasedOnChildren(list);
                            //  adapter.setDatalist(CustomMessageEntity.getContent());
                            final String url = CustomMessageEntity.getLinkUrl();
                            final CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                            // adapter.setDatalist(CustomMessageEntity.getContent());
                            int type1 = 0;
                            if (extraData == null) {
                                if (url != null && !url.equals("")) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                } else {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                }
                            } else {


                                //右边
                                type1 = extraData.getType();


                                if (extraData.getType() == 13 || extraData.getType() == 25 || extraData.getType() == 38|| type1 > 100) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);

                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    if (extraData.getType() == 28) {
                                        servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                                        detailText.setText("查看凭证");
                                    }
                                }
                                if (type1==306){
                                    checkDetail.setVisibility(View.VISIBLE);
                                    line.setVisibility(View.VISIBLE);
                                }



                            }
                            checkDetail.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(v, myTIMMessage);

                                    } else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                }
                            });
                            checkDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                           /* case 31:
                                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(prescriptionIntent);
                                                break;*/
                                            case 13:
                                                Intent prescriptionIntent = new Intent(mContext, WebViewActivity.class);
                                                prescriptionIntent.putExtra("PrescriptionId", extraData.getData().getPrescriptionId());
                                                prescriptionIntent.putExtra("type", 23);
                                                mContext.startActivity(prescriptionIntent);
                                                break;
                                            case 32:
                                                Intent intent = new Intent(mContext, WebViewActivity.class);
                                                intent.putExtra("action", String.valueOf(extraData.getData().getServiceId()));
                                                mContext.startActivity(intent);
                                                break;
                                            case 27:
                                                Log.i("ChatAdapter", "imgId=" + extraData.getData().getImageId() + ",medicalTempId=" + extraData.getData().getMedicalTempId() + ",patientName=" + extraData.getData().getPatientName());
                                                Intent caseDetailIntent = new Intent(mContext, WebViewActivity.class);
                                                caseDetailIntent.putExtra("type", WebUtil.caseDetail);
                                                caseDetailIntent.putExtra("imgId", extraData.getData().getImageId());
                                                caseDetailIntent.putExtra("medicalTempId", extraData.getData().getMedicalTempId());
                                                caseDetailIntent.putExtra("patientName", extraData.getData().getPatientName());
                                                mContext.startActivity(caseDetailIntent);
                                                break;
                                            case 34://服务包详情
                                                Intent serviceIntent = new Intent(mContext, WebViewActivity.class);
                                                serviceIntent.putExtra("type", WebUtil.serviceDetail);
                                                serviceIntent.putExtra("payOrderId", extraData.getData().getPayOrderId());
                                                mContext.startActivity(serviceIntent);
                                                break;
                                            case 39:
                                                Intent CustomLinkintent = new Intent(mContext, WebHeadViewActivity.class);
                                                CustomLinkintent.putExtra("type", WebUtil.link);
                                                CustomLinkintent.putExtra("url", url);
                                                mContext.startActivity(CustomLinkintent);
                                                break;
                                            case 37:
                                                Intent doctorPraiseIntent = new Intent(mContext, WebViewActivity.class);
                                                mContext.startActivity(doctorPraiseIntent);
                                                break;
                                            case 306:

                                                PDFviewUtils.getInstanse().showPDF(mContext,url,"检验检查报告");
                                                break;
                                        }
                                    } else if (url != null && !url.equals("")) {
                                        Intent intent = new Intent(mContext, WebViewActivity.class);
                                        intent.putExtra("type", 21);
                                        intent.putExtra("url", url);
                                        mContext.startActivity(intent);
                                    }

                                }
                            });
                            String title = CustomMessageEntity.getTitle();

                            if (title != null) {
                                if (type1 == 39) {//修复问卷结束之后的消息展示
                                    servicePackageName.setText("问卷提交: " + title);
                                    list.setVisibility(View.GONE);
                                } else {
                                    servicePackageName.setText(title);
                                }
                            } else if (type1 == 134) {
                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time;
                                if (data != null) {
                                    time = data.getTime();
                                } else {
                                    time = null;
                                }
                                if (time != null && !time.equals("")) {
                                    servicePackageName.setText("视频通话时长：" + time);
                                } else {
                                    servicePackageName.setText("已挂断视频通话");
                                }
                            } else if (type1 == 133) {
                                servicePackageName.setText("我拒绝了视频邀请");
                            } else if (type1 == 132) {
                                servicePackageName.setText("呼叫超时");
                            } else if (type1 == 131) {
                                servicePackageName.setText("我取消了视频");
                            } else if (CustomMessageEntity.getFocusTitle() == null) {
                                titleLayout.setVisibility(View.GONE);
                            }
                       /* else {
                            servicePackageName.setVisibility(View.GONE);
                        }
                        if(CustomMessageEntity.getFocusTitle() != null) {
                            servicePackageName.setVisibility(View.VISIBLE);
                            servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                        }*/
                            if (type1 == 130 || type1 == 129) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.rightMessage.addView(serviceView);
                            }
                            Log.i("ChatAdapter", "CustomMessageEntity=" + CustomMessageEntity.getTitle());
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }

              /*  else {
                    TextView textView = new TextView(MyApplication.getContext());
                    textView.setText(s);
                    textView.setTextSize(Dimension.SP, 16);
                    holder.rightMessage.addView(textView, layoutParams);
                }*/
            }
        } else {   //好友向自己发送的消息
            holder.leftPanel.setVisibility(View.VISIBLE);
            holder.rightPanel.setVisibility(View.GONE);
            holder.leftMessage.removeAllViews();
            holder.sender.setVisibility(View.GONE);
            holder.sender.setText(timMessage.getSender());
            holder.leftMessage.setBackgroundResource(R.drawable.chat_white_bg);
            Log.d("聊天类型1",timMessage.getConversation().getType()+"");
            if (timMessage.getConversation().getType()== TIMConversationType.C2C ){
                holder.sender.setVisibility(View.GONE);
            }else {
                holder.sender.setVisibility(View.VISIBLE);
            }


//            GlideMediaLoader.load(MyApplication.getContext(), holder.leftAvatar, mleftImagePath);
            if (TextUtils.isEmpty(mleftImagePath)) {

                ArrayList<String> memberIds = new ArrayList<>();
                memberIds.add(sender);
                TIMFriendshipManager.getInstance().getUsersProfile(memberIds, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, timMessage.getSender() + "获取昵称 " + s);
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles.size() > 0) {
                            Glide.with(MyApplication.getContext())
                                    .load(timUserProfiles.get(0).getFaceUrl())
                                    .dontAnimate()
                                    .placeholder(R.drawable.doctor_default_img)
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.leftAvatar);
                            Log.d("获取昵称", timMessage.getSender() + "   " + timUserProfiles.get(0).getNickName()+"  头像"+timUserProfiles.get(0).getFaceUrl());
                            holder.sender.setText(timUserProfiles.get(0).getNickName());
                        }

                    }
                });

            }else {
                GlideMediaLoader.load(MyApplication.getContext(), holder.leftAvatar, mleftImagePath);
            }
            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("对方撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            } else if (element.getType() == TIMElemType.Text) {
                //文字信息处理
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextColor(Color.DKGRAY);
                textView.setTextSize(Dimension.SP, 14);
                holder.leftMessage.addView(textView, layoutParams);
            } else if (element.getType() == TIMElemType.Sound) {
                //语音信息处理
                Log.i("ChatAdapter", "left");
                ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.left_voice);
                holder.leftMessage.addView(imageView, layoutParams);
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.leftMessage.addView(textView);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        mLastAnimationDrawable.selectDrawable(0);
                        mLastAnimationDrawable.stop();
                        //   animationDrawable.stop();
                    }
                });
                holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (mPosition == pposition) {
                                mLastAnimationDrawable.selectDrawable(0);
                                mLastAnimationDrawable.stop();
                                mMediaPlayer.stop();
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                RelativeLayout relativeLayout = (RelativeLayout) v;
                                ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                operatePlaySound(AnimationDrawable);
                                //   mMediaPlayer = new MediaPlayer();
                                playSound(mMediaPlayer, timMessage, false, null, null);
                                mPosition = pposition;
                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });
                //图片信息处理
            } else if (element.getType() == TIMElemType.Image) {
                holder.leftMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;
                for (TIMImage timImage : imageElem.getImageList()) {
                    if (timImage.getType().toString().equals("Large")) {
                        final String path = timImage.getUrl();
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                    if (timImage.getType().toString().equals("Thumb")) {
                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        Log.d("zdp", "image type: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth());
                        Glide.with(MyApplication.getContext())
                                .load(timImage.getUrl())
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        layoutParams.setMargins(0, 0, 0, 0);

                        holder.leftMessage.addView(imageView, pictureLayoutParams);

                    }
                   /* ImageView imageView = new ImageView(MyApplication.getContext());
                    Glide.with(MyApplication.getContext())
                            .load(timImage.getUrl())
                         //   .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.leftMessage.addView(imageView, layoutParams);*/
                }
            }
            //小视频消息
            else if (element.getType() == TIMElemType.Video){

                holder.leftMessage.setBackgroundResource(0);
                View view = View.inflate(mContext, R.layout.item_video, null);
                final ImageView imageView = view.findViewById(R.id.video_image);
                final ImageView playvideo = view.findViewById(R.id.paly_image);
                final RelativeLayout relativeLayout = view.findViewById(R.id.lv_rv_root);
                final TIMVideoElem videoelem = (TIMVideoElem) element;
                int height = (int) videoelem.getSnapshotInfo().getHeight();
                int width = (int) videoelem.getSnapshotInfo().getWidth();
                if (height > width) {
                    height = 320;
                    width = 240;
                } else {
                    width = 320;
                    height = 240;
                }
                RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                        .LayoutParams(width
                        , height);
                imageView.setLayoutParams(pictureLayoutParams);
                Log.d("zdp", "image type: " + videoelem.getSnapshotInfo().getType() +
                        " image size " + videoelem.getSnapshotInfo().getSize() +
                        " image height " + videoelem.getSnapshotInfo().getHeight() +
                        " image width " + videoelem.getSnapshotInfo().getWidth());
                layoutParams.setMargins(0, 0, 0, 0);
                final String imagepath = FileUtil.sdkpath + videoelem.getSnapshotInfo().getUuid() + ".jpg";
                final String videopath = FileUtil.sdkpath + videoelem.getVideoInfo().getUuid() + ".mp4";
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getSnapshotInfo().getUuid()+".jpg");
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getVideoInfo().getUuid()+".mp4");
                //获取截图
                if (FileUtil.fileIsExists(imagepath)) {
                    Log.d(TAG, "图片文件存在");
                    GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                } else {
                    videoelem.getSnapshotInfo().getImage(imagepath, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.d("zdp", "下载视频截图出错" + i + "  " + s);
                        }

                        @Override
                        public void onSuccess() {
                            GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                        }
                    });
                }

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (FileUtil.fileIsExists(videopath)) {
                            Log.d("zdp", "视频文件存在,直接播放"+pposition+" "+videopath);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            File file = new File(videopath);
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(mContext, "com.newdjk.member.fileprovider", file);
                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                uri = Uri.fromFile(file);
                            }
                            intent.setDataAndType(uri, "video/*");
                            mContext.startActivity(intent);

                        } else {
                            LoadDialog.show(mContext,"");
                            videoelem.getVideoInfo().getVideo(videopath, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    LoadDialog.clear();
                                }

                                @Override
                                public void onSuccess() {
                                    LoadDialog.clear();
                                    Log.d("zdp", "下载完成后,再进行播放"+pposition);
                                    videoelem.setVideoPath(videopath);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String path = videoelem.getVideoPath();//该路径可以自定义
                                    File file = new File(path);
                                    Uri uri;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(mContext, "com.newdjk.member.fileprovider", file);
                                    } else {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        uri = Uri.fromFile(file);
                                    }
                                    intent.setDataAndType(uri, "video/*");
                                    mContext.startActivity(intent);
                                }
                            });
                        }

                    }
                });
                holder.leftMessage.addView(view, layoutParams);

            }
            else if (element.getType() == TIMElemType.GroupTips) {//群组消息
                TIMGroupTipsElem tipsElem = (TIMGroupTipsElem) element;
                String grouptitle = "";
                String opname = tipsElem.getOpUserInfo().getNickName();
                if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyGroupInfo) {
                    //群组事件通知
                    grouptitle = opname + "修改群名为" + tipsElem.getGroupName() + "";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Join) {
                    Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                    Iterator<TIMUserProfile> iterator2 = values.iterator();
                    while (iterator2.hasNext()) {
                        grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                    }
                    grouptitle = grouptitle + "加入群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Quit) {
                    Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                    Iterator<TIMUserProfile> iterator2 = values.iterator();
                    while (iterator2.hasNext()) {
                        grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                    }
                    grouptitle = grouptitle + "离开群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.SetAdmin) {
                    grouptitle = opname + "设置新群主";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyMemberInfo) {
                    grouptitle = opname + "修改群成员信息";
                }
                holder.systemMessage1.setText(grouptitle);
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    String s = new String(customElem.getData());
                    Log.e("ChatAdapter", "s=" + s);
                    //   String s = mGson.toJson(data);
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
                        final ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.mipmap.left_voice3);
                        holder.leftMessage.addView(imageView, layoutParams);
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "\"");
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(Gravity.CENTER);
                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        holder.leftMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                       /* holder.sendError.setVisibility(View.GONE);
                                        holder.sending.setVisibility(View.GONE);*/
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        mMediaPlayer.stop();
                                        imageView.setImageResource(R.mipmap.left_voice3);
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.left_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }
                            }
                        });


                    } else if (desc != null && desc.equals("TIMImageElem")) {
                        holder.leftMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken<List<ImageInfoArrayEntity>>() {
                        }.getType();
                        Log.e("TIMImageElem", "s=" + s);
                        List<ImageInfoArrayEntity> imageInfoArray = mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() > 0) {
                            for (int i = 0; i < imageInfoArray.size(); i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                    String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\", "/");
                                    holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else if (type == 3) {
                                    int height = imageInfoArrayEntity.getHeight();
                                    int width = imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());

                                    String url = imageInfoArrayEntity.getURL();
                                    String path = url.replace("\\", "/");
                                    Log.i("TIMImageElem", "URL=" + path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.leftMessage.addView(imageView, pictureLayoutParams);

                                }
                            }
                        }
                    } else if (s != null && !s.equals("")) {
                        holder.leftMessage.setBackgroundResource(0);
                        RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                                .LayoutParams(600
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        final CustomMessageEntity mCustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = mCustomMessageEntity.isIsSystem();
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            holder.leftPanel.setVisibility(View.GONE);
                            holder.rightPanel.setVisibility(View.GONE);
                            holder.leftMessage.removeAllViews();
                            boolean IsShowDividingLine = mCustomMessageEntity.isShowDividingLine();
                            int showType = mCustomMessageEntity.getShowType();
                            if (showType == 0 || showType == 1) {
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(mCustomMessageEntity.getTitle());
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {
                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package, null, false);
                            TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            TextView detailText = serviceView.findViewById(R.id.check_detail_text);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            TextView titlebar = serviceView.findViewById(R.id.service_title);
                            TextView sendtime = serviceView.findViewById(R.id.service_time);
                            View line = serviceView.findViewById(R.id.line);
                            final String url = mCustomMessageEntity.getLinkUrl();
                            CustomMessageAdapter adapter = new CustomMessageAdapter(mCustomMessageEntity.getContent());
                            list.setAdapter(adapter);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            final CustomMessageEntity.ExtDataBean extraData = mCustomMessageEntity.getExtData();
                            int type1 = 0;
                            if (extraData == null) {
                                if (url != null && !url.equals("")) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                } else {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                }
                            } else {
                                //左边显示或者隐藏
                                type1 = extraData.getType();
                                if (type1 > 100 && type1 != 301 && type1 != 304 && type1 != 303&& type1 != 313 && type1 != 311 && type1 != 317&&type1 != 312&&type1 != 310&&type1 != 315) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);

                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    if (extraData.getType() == 28) {
                                        servicePackageName.setText(mCustomMessageEntity.getFocusTitle());
                                        detailText.setText("查看凭证");
                                    } else if (extraData.getType() == 26) {
                                        servicePackageName.setText(mCustomMessageEntity.getTitle());
                                        detailText.setText("查看详情");
                                    }
                                }
                                if (type1 == 41) {
                                    titlebar.setVisibility(View.GONE);
                                    if (TextUtils.isEmpty(extraData.getData().getOrderTime())) {
                                        sendtime.setVisibility(View.GONE);
                                    } else {
                                        sendtime.setVisibility(View.GONE);
                                        sendtime.setText(extraData.getData().getOrderTime() + "");
                                    }

                                }
                            }
                            checkDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                            case 25:
                                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                                Intent recommendIntent = new Intent(mContext, WebViewActivity.class);
                                                recommendIntent.putExtra("type", WebUtil.createEvaluate);
                                                recommendIntent.putExtra("docId", data.getDoctorId());
                                                recommendIntent.putExtra("patientId", mPatientId);
                                                recommendIntent.putExtra("serviceType", data.getServiceType());
                                                recommendIntent.putExtra("relationId", data.getRecordId());
                                                mContext.startActivity(recommendIntent);
                                                break;
                                            case 26:
                                                Intent medicalRecordsIntent = new Intent(mContext, WebViewActivity.class);
                                                medicalRecordsIntent.putExtra("type", WebUtil.pintBook);
                                                medicalRecordsIntent.putExtra("medicalTempId", extraData.getData().getMedicalTempId());
                                                ((ChatActivity) mContext).startActivityForResult(medicalRecordsIntent, ChatActivity.CASE_CODE);
                                                break;
                                            case 32:
                                                Intent medicalServiceIntent = new Intent(mContext, WebViewActivity.class);
                                                medicalServiceIntent.putExtra("serviceId", extraData.getData().getServiceId());
                                                medicalServiceIntent.putExtra("type", WebUtil.servicePackage);
                                                medicalServiceIntent.putExtra("doctorId", mDoctorId);
                                                mContext.startActivity(medicalServiceIntent);
                                                break;
                                            case 31:
                                                Intent prescriptionIntent = new Intent(mContext, WebViewActivity.class);
                                                prescriptionIntent.putExtra("type", WebUtil.prePay);
                                                prescriptionIntent.putExtra("PrescriptionId", extraData.getData().getPrescriptionId());
                                                mContext.startActivity(prescriptionIntent);
                                                break;
                                            case 33://问卷调查详情
                                                Intent CustomLinkintent = new Intent(mContext, WebHeadViewActivity.class);
                                                CustomLinkintent.putExtra("type", WebUtil.link);
                                                CustomLinkintent.putExtra("url", url);
                                                mContext.startActivity(CustomLinkintent);
                                                break;
                                            case 28:
                                                String title = mCustomMessageEntity.getFocusTitle();
                                                List<CustomMessageEntity.ContentBean> Content = mCustomMessageEntity.getContent();
                                                String number = "";
                                                String content = "";
                                                String stamp = extraData.getData().getStamp();
                                                if (Content != null && Content.size() == 2) {
                                                    number = Content.get(0).getContentElem().getText();
                                                    content = Content.get(1).getContentElem().getText();
                                                }
                                                //showPlusSign(title,content,stamp,number);
                                                PlusDialog PlusDialog = new PlusDialog(mContext);
                                                PlusDialog.show(title, content, stamp, number);
                                                break;
                                            case 301:
                                                //宣教详情
                                                CustomMessageEntity.ExtDataBean.DataBean mDataBean = extraData.getData();
                                                Intent mCustomLinkintent = new Intent(mContext, WebHeadViewActivity.class);
                                                mCustomLinkintent.putExtra("type", WebUtil.link);
                                                mCustomLinkintent.putExtra("url", mDataBean.getLinkURL());
                                                mContext.startActivity(mCustomLinkintent);
                                                break;

                                            case 41:
                                                Log.d(TAG, extraData.getData().toString());
                                                Intent intentcheck = new Intent(mContext, WebViewActivity.class);
                                                intentcheck.putExtra("type", WebUtil.checktable);
                                                intentcheck.putExtra("CensorateRecordId", extraData.getData().getCensorateRecordId() + "");
                                                mContext.startActivity(intentcheck);
                                                break;
                                            case 304:
                                                AdviceGoodDetailEntity adviceGoodEntity = mGson.fromJson(s, AdviceGoodDetailEntity.class);
                                                Intent adviceintent = new Intent(mContext, WebViewActivity.class);
                                                adviceintent.putExtra("type", WebUtil.AdviceGoods);
                                                adviceintent.putExtra("DataSource", adviceGoodEntity.getExtData().getData().getDataSourceOfDoc() + "");
                                                adviceintent.putExtra("DataId", adviceGoodEntity.getExtData().getData().getDataIdOfDoc() + "");
                                                mContext.startActivity(adviceintent);
                                                break;
                                            case 303:
                                                Intent prescriptionTCMIntent = new Intent(mContext, WebViewActivity.class);
                                                prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionTCMIntent.putExtra("type", WebUtil.ZhongYaoChuFangDetail);
                                                mContext.startActivity(prescriptionTCMIntent);
                                                break;
                                            case 313:
                                                DoctorIDinfoEntity doctorIDinfoEntity = mGson.fromJson(s, DoctorIDinfoEntity.class);

                                                Intent doctorinfoIntent = new Intent(mContext, WebViewActivity.class);
                                                doctorinfoIntent.putExtra("doctorId", doctorIDinfoEntity.getExtData().getData().getDrId());
                                                doctorinfoIntent.putExtra("type", WebUtil.doctorinfo);
                                                mContext.startActivity(doctorinfoIntent);
                                                break;
                                            case 311:
                                                MDTOrderMessageEntity mdtOrderMessageEntity = mGson.fromJson(s, MDTOrderMessageEntity.class);
                                                Intent orderfoIntent = new Intent(mContext, WebViewActivity.class);
                                                orderfoIntent.putExtra("mSubjectBuyRecordId", mdtOrderMessageEntity.getExtData().getData().getSubjectBuyRecordId());
                                                MDTOrderMessageEntity.ExtDataBean.DataBean databean = mdtOrderMessageEntity.getExtData().getData();
                                                String jsoninfo = mGson.toJson(databean);
                                                orderfoIntent.putExtra("mdtTriageInfo", jsoninfo);
                                                orderfoIntent.putExtra("type", WebUtil.mdtorderfo);
                                                mContext.startActivity(orderfoIntent);
                                                break;

                                            case 317: //补充病例
                                                MDTOrderMessageEntity mdtOrderMessageEntity2 = mGson.fromJson(s, MDTOrderMessageEntity.class);
                                                Intent orderfoIntent2 = new Intent(mContext, WebViewActivity.class);
                                                orderfoIntent2.putExtra("mSubjectBuyRecordId", mdtOrderMessageEntity2.getExtData().getData().getSubjectBuyRecordId());
                                                orderfoIntent2.putExtra("type", WebUtil.diseaseAdd);
                                                mContext.startActivity(orderfoIntent2);
                                                break;

                                            case 312: //补充病例
                                                MDTOrderMessageEntity mdtOrderMessageEntity3 = mGson.fromJson(s, MDTOrderMessageEntity.class);
                                                Intent orderfoIntent3 = new Intent(mContext, WebViewActivity.class);
                                                orderfoIntent3.putExtra("mSubjectBuyRecordId", mdtOrderMessageEntity3.getExtData().getData().getSubjectBuyRecordId());
                                                orderfoIntent3.putExtra("type", WebUtil.mdtOrderpay);
                                                mContext.startActivity(orderfoIntent3);
                                                break;
                                            case 310: //补充病例
                                                MDTOrderMessageEntity mdtOrderMessageEntity4 = mGson.fromJson(s, MDTOrderMessageEntity.class);
                                                Intent mdtfenzhen = new Intent(mContext, WebViewActivity.class);
                                                mdtfenzhen.putExtra("mSubjectBuyRecordId", mdtOrderMessageEntity4.getExtData().getData().getSubjectBuyRecordId());
                                                mdtfenzhen.putExtra("type", WebUtil.mdtfenzhen);
                                                mContext.startActivity(mdtfenzhen);
                                                break;
                                            case 315: //补充病例
                                                MDTOrderMessageEntity mdtOrderMessageEntity5 = mGson.fromJson(s, MDTOrderMessageEntity.class);
                                                Intent mdtreport = new Intent(mContext, WebViewActivity.class);
                                                mdtreport.putExtra("mSubjectBuyRecordId", mdtOrderMessageEntity5.getExtData().getData().getSubjectBuyRecordId());
                                                mdtreport.putExtra("type", WebUtil.mdtreport);
                                                mContext.startActivity(mdtreport);
                                                break;


                                        }
                                    } else if (url != null && !url.equals("")) {
                                        Intent intent = new Intent(mContext, WebViewActivity.class);
                                        intent.putExtra("type", 21);
                                        intent.putExtra("url", url);
                                        mContext.startActivity(intent);
                                    }
                                }
                            });

                            String title = mCustomMessageEntity.getTitle();
                            if (title != null) {
                                servicePackageName.setText(title);
                            }
                            if (type1 == 129) {
                                servicePackageName.setText("对方开启了视频邀请");
                            } else if (type1 == 133) {
                                servicePackageName.setText("对方拒绝了视频邀请");
                            } else if (type1 == 131) {
                                servicePackageName.setText("对方已取消视频通话");
                            } else if (type1 == 134) {
                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time = null;
                                if (data != null) {
                                    time = data.getTime();
                                }
                                if (time != null && !time.equals("")) {
                                    servicePackageName.setText("视频通话时长：" + time);
                                } else {
                                    servicePackageName.setText("对方挂断了视频");
                                }
                            } else if (type1 == 41) {
                                servicePackageName.setText(mCustomMessageEntity.getPushDesc() + "");

                            }
                       /*   else {
                                servicePackageName.setVisibility(View.GONE);
                            }*/

                            if (type1 == 130) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.rightMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.leftMessage.addView(serviceView, layoutParams);
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }
               /* TIMCustomElem customElem = (TIMCustomElem) element;
                String s = new String(customElem.getData());
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(s);
                textView.setTextSize(Dimension.SP, 16);
                holder.leftMessage.addView(textView, layoutParams);*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTIMMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.system_message_layout)
        FrameLayout systemMessageLayout;

        @BindView(R.id.systemMessage)
        TextView systemMessage;

        @BindView(R.id.leftPanel)
        RelativeLayout leftPanel;

        @BindView(R.id.rightPanel)
        RelativeLayout rightPanel;

        @BindView(R.id.leftAvatar)
        CircleImageView leftAvatar;

        @BindView(R.id.rightAvatar)
        CircleImageView rightAvatar;

        @BindView(R.id.sender)
        TextView sender;

        @BindView(R.id.leftMessage)
        RelativeLayout leftMessage;

        @BindView(R.id.rightMessage)
        RelativeLayout rightMessage;

        @BindView(R.id.sendStatus)
        RelativeLayout sendStatus;

        @BindView(R.id.rightDesc)
        TextView rightDesc;

        @BindView(R.id.sending)
        ProgressBar sending;

        @BindView(R.id.sendError)
        ImageView sendError;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.systemMessage1)
        TextView systemMessage1;


        @BindView(R.id.name)
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /* public static void playSound(final MediaPlayer mediaPlayer, TIMMessage timMessage) {
         mediaPlayer.reset();
         // mediaPlayer = new MediaPlayer();
        *//* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                animationDrawable .selectDrawable(0);
                animationDrawable.stop();
            }
        });*//*
        TIMElem element = timMessage.getElement(0);
        if (element.getType() == TIMElemType.Sound) {
            final TIMSoundElem soundElem = (TIMSoundElem) element;
            final String path = MyApplication.getContext().getFilesDir()
                    + File.separator + ((TIMSoundElem) element).getUuid();
            File soundFile = new File(path);
            Log.i("ChatAdapter", "path=" + path);
            if (soundFile.exists()) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                final MediaPlayer finalMediaPlayer = mediaPlayer;
                soundElem.getSoundToFile(path, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        soundElem.setPath(path);
                        try {
                            finalMediaPlayer.setDataSource(path);
                            finalMediaPlayer.prepare();
                            finalMediaPlayer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }


        }
    }

    public void operatePlaySound(AnimationDrawable animationDrawable) {
        if (mLastAnimationDrawable == null) {
            mLastAnimationDrawable = animationDrawable;
            mLastAnimationDrawable.start();
        } else {
            try {
                mLastAnimationDrawable.selectDrawable(0);
                mLastAnimationDrawable.stop();
                mLastAnimationDrawable = animationDrawable;
                mLastAnimationDrawable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
*/
    public void playSound(final MediaPlayer mediaPlayer, TIMMessage timMessage, boolean isLocalMessage, String uuid, final String urlPath) {
        mediaPlayer.reset();
        // mediaPlayer = new MediaPlayer();
       /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                animationDrawable .selectDrawable(0);
                animationDrawable.stop();
            }
        });*/
        if (isLocalMessage) {
            final String path = MyApplication.getContext().getFilesDir()
                    + File.separator + uuid;
            File soundFile = new File(path);
            Log.i("ChatAdapter", "path=" + path);
            if (soundFile.exists()) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                final MediaPlayer finalMediaPlayer = mediaPlayer;
                downFile(urlPath, soundFile, mediaPlayer);
            }
        } else {
            TIMElem element = timMessage.getElement(0);
            if (element.getType() == TIMElemType.Sound) {
                final TIMSoundElem soundElem = (TIMSoundElem) element;
                final String path = MyApplication.getContext().getFilesDir()
                        + File.separator + ((TIMSoundElem) element).getUuid();
                File soundFile = new File(path);
                Log.i("ChatAdapter", "path=" + path);
                if (soundFile.exists()) {
                    try {
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    final MediaPlayer finalMediaPlayer = mediaPlayer;
                    soundElem.getSoundToFile(path, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onSuccess() {
                            soundElem.setPath(path);
                            try {
                                finalMediaPlayer.setDataSource(path);
                                finalMediaPlayer.prepare();
                                finalMediaPlayer.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        }
    }

    public void operatePlaySound(AnimationDrawable animationDrawable) {
        if (mLastAnimationDrawable == null) {
            mLastAnimationDrawable = animationDrawable;
            mLastAnimationDrawable.start();
        } else {
            try {
                mLastAnimationDrawable.selectDrawable(0);
                mLastAnimationDrawable.stop();
                mLastAnimationDrawable = animationDrawable;
                mLastAnimationDrawable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showPlusSign(final String title, String content, String stamp, String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.plus_sign_detail, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);


        TextView titleTx = (TextView) view.findViewById(R.id.title);
        TextView numberTx = (TextView) view.findViewById(R.id.number);
        ImageView stampIv = (ImageView) view.findViewById(R.id.stamp);
        TextView signContentTx = (TextView) view.findViewById(R.id.sign_content);
        titleTx.setText(title);
        numberTx.setText(number);
        signContentTx.setText(content);
        Glide.with(MyApplication.getContext())
                .load(stamp)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(stampIv);
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.show();
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void downFile(final String path, final File file, final MediaPlayer mediaPlayer) {
        LoadDialog.show(mContext);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .get()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    InputStream inputStream = response.body().byteStream();
                 /*   Log.i("ChatActivity","path1="+path+",uuid1="+uuid);
                    //将图片保存到本地存储卡中
                    File file = new File( MyApplication.getContext().getFilesDir()
                            + File.separator + uuid);*/
                    //  File file = new File(Environment.getExternalStorageDirectory(), uuid);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] temp = new byte[2048];
                    int length;
                    while ((length = inputStream.read(temp)) != -1) {
                        fileOutputStream.write(temp, 0, length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    //    notifyDataSetChanged();
                    inputStream.close();
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showPopupWindow(View view, final MyTIMMessage myTIMMessage) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.revoke_window, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 设置按钮的点击事件
        TextView button = (TextView) contentView.findViewById(R.id.revoke);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), true);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TIMMessage timMessage = myTIMMessage.getTimMessage();
                mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        popupWindow.dismiss();
                        Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        popupWindow.dismiss();
                        myTIMMessage.setRevoke(true);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        popupWindow.setTouchable(true);

      /*  popupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));*/

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
}
