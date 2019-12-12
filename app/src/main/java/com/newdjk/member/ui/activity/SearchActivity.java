package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.ui.adapter.TagAdapter;
import com.newdjk.member.ui.db.RecordSQLiteOpenHelper;
import com.newdjk.member.ui.entity.AdBannerInfo;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.HotSearchEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.ImageBase64;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.FlowLayout;
import com.newdjk.member.views.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

public class SearchActivity extends BasicActivity {

    @BindView(R.id.mHistoryContainer)
    RelativeLayout mHistoryContainer;
    @BindView(R.id.mFlowlayout)
    TagFlowLayout mFlowlayout;
    @BindView(R.id.mHotFlowlayout)
    TagFlowLayout mHotFlowlayout;
    @BindView(R.id.mEditText)
    EditText mEditText;
    @BindView(R.id.mIvClear)
    ImageButton mIvClear;
    @BindView(R.id.mSearchTv)
    TextView mSearchTv;
    @BindView(R.id.mCancelTv)
    TextView mCancelTv;

    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);

    private LayoutInflater mInflater;
    private List<HotSearchEntity.DataBean.ReturnDataBean> returnData;
    private SQLiteDatabase db;
    private List<String> data;

    @Override
    protected int initViewResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }

    @Override
    protected void initView() {
        mInflater = LayoutInflater.from(this);
        mFlowlayout.setMaxSelectCount(1);
        mHotFlowlayout.setMaxSelectCount(1);

        mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (data != null && data.size()>0) {
                    Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra(MainConstant.KEYWORDS,data.get(position));
                    startActivity(intent);
                }
                return true;
            }
        });

        mHotFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (returnData != null&&returnData.size()>0) {
                    HotSearchEntity.DataBean.ReturnDataBean returnDataBean = returnData.get(position);
                    Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra(MainConstant.KEYWORDS,returnDataBean.getSearchKey());
                    startActivity(intent);
                }

                return true;
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    String content = mEditText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        toast("请输入关键词,然后进行搜索");
                        return true;
                    }
                    Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra(MainConstant.KEYWORDS,content);
                    startActivity(intent);
                    //插入历史搜索记录
                    boolean hasData = hasData(mEditText.getText().toString());
                    if (!hasData) {
                        insertData(mEditText.getText().toString());
                        queryData("");
                    }
                }
                return false;
            }
        });
        queryData("");
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 设置适配器
        data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        if (data.size() > 0) {
            mFlowlayout.setVisibility(View.VISIBLE);
            mHistoryContainer.setVisibility(View.VISIBLE);
            mFlowlayout.setAdapter(new TagAdapter<String>(data) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.tv_layout, mFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });

        } else {
            mFlowlayout.setVisibility(View.GONE);
            mHistoryContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }



    @Override
    protected void initListener() {
        mIvClear.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getHotSearchData();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.mIvClear:
                new CommomDialog(SearchActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.confirm_clear_search_history), new com.newdjk.member.views.CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.cancel();
                        deleteData();
                        queryData("");
                    }
                }).show();

                break;
            case R.id.mCancelTv:
                finish();
                break;
            case R.id.mSearchTv:
                //搜索
                String content = mEditText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    toast("请输入关键词,然后进行搜索");
                    return;
                }
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra(MainConstant.KEYWORDS,content);
                startActivity(intent);
                //插入历史搜索记录
                boolean hasData = hasData(mEditText.getText().toString());
                if (!hasData) {
                    insertData(mEditText.getText().toString());
                    queryData("");
                }
                break;
        }
    }

    public void getHotSearchData() {
        Map<String,String> params = new HashMap<>();
        params.put("PageSize","15");
        mMyOkhttp.post().url(HttpUrl.GetDrSearchPage).headers(HeadUitl.instance.getHeads()).params(params).tag(this).enqueue(new GsonResponseHandler<HotSearchEntity>() {
            @Override
            public void onSuccess(int statusCode, HotSearchEntity entituy) {
                if (entituy.getCode()==0) {
                    HotSearchEntity.DataBean data = entituy.getData();
                    if (data != null) {
                        returnData = data.getReturnData();
                        mHotFlowlayout.setAdapter(new TagAdapter<HotSearchEntity.DataBean.ReturnDataBean>(returnData) {
                            @Override
                            public View getView(FlowLayout parent, int position, HotSearchEntity.DataBean.ReturnDataBean returnDataBean) {
                                TextView tv = (TextView) mInflater.inflate(R.layout.tv_layout, mFlowlayout, false);
                                tv.setText(returnDataBean.getSearchKey());
                                return tv;
                            }

                        });
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }
}
