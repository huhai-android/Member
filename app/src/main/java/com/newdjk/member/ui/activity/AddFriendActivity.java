package com.newdjk.member.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BasicActivity {


    @BindView(R.id.identifier)
    EditText identifier;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.add_user)
    LinearLayout addUser;
    @BindView(R.id.no_user)
    RelativeLayout noUser;
    private String mIdentifier;


    @Override
    protected int initViewResId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void initView() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = identifier.getText().toString();
                if (!TextUtils.isEmpty(id)) {
                    identifier.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(identifier.getWindowToken(), 0);
                    List<String> list = new ArrayList<>();
                  /*  if (StringUtil.isNumeric(id)) {
                        id = "86-" + id;
                    }*/
                    list.add(id);
                    TIMFriendshipManager.getInstance().getUsersProfile(list, new TIMValueCallBack<List<TIMUserProfile>>() {
                        @Override
                        public void onError(int i, String s) {
                            noUser.setVisibility(View.VISIBLE);
                            addUser.setVisibility(View.GONE);
                        }

                        @Override
                        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                            if (timUserProfiles.size() == 0) {
                                noUser.setVisibility(View.VISIBLE);
                                addUser.setVisibility(View.GONE);
                            } else {
                                TIMUserProfile timUserProfile = timUserProfiles.get(0);
                                mIdentifier = timUserProfile.getIdentifier();
                                //  nick_name.setText(timUserProfile.getNickName());
                                nickName.setText(mIdentifier);
                                noUser.setVisibility(View.GONE);
                                addUser.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TIMAddFriendRequest> list = new ArrayList<>();
                TIMAddFriendRequest timAddFriendRequest = new TIMAddFriendRequest();
                timAddFriendRequest.setIdentifier(mIdentifier);
                list.add(timAddFriendRequest);
                TIMFriendshipManager.getInstance().addFriend(list, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        Toast.makeText(AddFriendActivity.this, "请求已发送", Toast.LENGTH_SHORT).show();
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
                    }
                });
            }
        });
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
