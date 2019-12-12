package com.newdjk.member.ui.activity.min;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.views.LoadDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class ReviewDiagnosisReportActivity extends BasicActivity  {


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
    @BindView(R.id.remote_pdf_root)
    LinearLayout root;
    private String url = "";



    @Override
    protected int initViewResId() {
        return R.layout.activity_remote_pdf;
    }

    @Override
    protected void initView() {
        loading(true, "正在加载");
        url = getIntent().getStringExtra("pdfUrl");
        initBackTitle("报告详情");
        liearTitlebar.setBackgroundColor(getResources().getColor(R.color.white));
        if (url != null) {
            downFile(url, PDFfile());
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @NonNull
    private File PDFfile() {
        File file = new File(Environment.getExternalStorageDirectory(), "memberreport.pdf");
        return file;
    }




    public static void open(Context context) {
        Intent i = new Intent(context, ReviewDiagnosisReportActivity.class);
        context.startActivity(i);
    }







    public void downFile(final String path, final File file) {
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
                    //    notifyDataSetChanged();
                    inputStream.close();

                    // 调用系统自带的文件选择器
                    Uri uri = Uri.fromFile( PDFfile());
                    Intent intent = new Intent(mContext, ReviewDocumentActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                    finish();
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    LoadDialog.clear();
                }
            }
        });
    }

}
