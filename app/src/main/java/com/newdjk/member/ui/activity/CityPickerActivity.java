package com.newdjk.member.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.adapter.ProvenceAdapter;
import com.newdjk.member.ui.entity.CityEntity;
import com.newdjk.member.utils.HeadUitl;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class CityPickerActivity extends BasicActivity {
    @BindView(R.id.ib_left_back)
    ImageButton ibLeftBack;
    @BindView(R.id.lv_provence)
    ListView lvProvence;
    @BindView(R.id.lv_city)
    ListView lvCity;

    private List<CityEntity.DataBean> mProvenceList;
    private ProvenceAdapter mProvenceAdapter;
    private ProvenceAdapter mSecondLevelAdapter;
    private List<CityEntity.DataBean> mSecondLevelList;

    @Override
    protected int initViewResId() {
        return R.layout.activity_city_picker;
    }

    @Override
    protected void initView() {
        mProvenceList = new LinkedList<>();
        mProvenceAdapter = new ProvenceAdapter(mProvenceList, this, true);
        lvProvence.setAdapter(mProvenceAdapter);

        mSecondLevelList = new ArrayList<>();
        mSecondLevelAdapter = new ProvenceAdapter(mSecondLevelList, this, false);
        lvCity.setAdapter(mSecondLevelAdapter);
    }

    @Override
    protected void initListener() {
        lvProvence.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProvenceAdapter.selectItem(position);
                View v = mProvenceAdapter.getView(position, view, parent);
                v.setBackgroundColor(CityPickerActivity.this.getResources().getColor(R.color.white));
                lvProvence.smoothScrollToPosition(position);
                obtainSecondLevelCity(position);

            }
        });

      lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              EventBus.getDefault().postSticky(mSecondLevelList.get(position));
              finish();
          }
      });

      ibLeftBack.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
    }

    private void obtainSecondLevelCity(int i) {
        int parentId;
        if (i == 0) {
            parentId = 1;
        } else {
            parentId = mProvenceList.get(i).getAreaId();

        }
        String url = HttpUrl.QueryAreaByParentId + "?" + "ParentId=" + parentId;
        mMyOkhttp.get().url(url).tag(this).enqueue(new GsonResponseHandler<CityEntity>() {
            @Override
            public void onSuccess(int statusCode, CityEntity response) {
                if (response != null) {
                    if (response.getCode() == 0) {
                        mSecondLevelList.clear();
                        mSecondLevelList.addAll(response.getData());
                        mSecondLevelAdapter.notifyDataSetChanged();
                    } else {
                        toast(response.getMessage() == null ? getString(R.string.networkError) : response.getMessage());
                    }
                } else {
                    toast(getString(R.string.networkError));
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    protected void initData() {
        obtainProvence();
    }


    private void obtainProvence() {
        String url = new StringBuffer(HttpUrl.QueryAreaByParentId).append("?").append("ParentId=0").toString();
        mMyOkhttp.get().url(url).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<CityEntity>() {
            @Override
            public void onSuccess(int statusCode, CityEntity response) {
                if (response != null) {
                    if (response.getCode() == 0) {
                        mProvenceList.addAll(response.getData());
                        mProvenceAdapter.notifyDataSetChanged();
                        CityEntity.DataBean bean = new CityEntity.DataBean();
                        bean.setAreaId(1);
                        bean.setAreaName(getString(R.string.hot_area));
                        mProvenceList.add(0, bean);
                        obtainSecondLevelCity(0);
                    } else {
                        toast(response.getMessage() == null ? getString(R.string.networkError) : response.getMessage());
                    }
                } else {
                    toast(getString(R.string.networkError));
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSecondLevelList != null) {
            mSecondLevelList = null;
        }

        if (mSecondLevelAdapter != null) {
            mSecondLevelAdapter = null;
        }

        if (mProvenceAdapter != null) {
            mProvenceAdapter = null;
        }

        if (mProvenceList != null) {
            mProvenceList = null;
        }
    }
}
