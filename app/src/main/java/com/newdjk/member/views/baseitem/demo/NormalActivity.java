package com.newdjk.member.views.baseitem.demo;


import android.support.v7.app.AppCompatActivity;

public class NormalActivity  extends AppCompatActivity {
//    <com.deceen.lxq.shipper.views.baseitem.BaseItemLayout
//    android:id="@+id/layout"
//    android:layout_width="match_parent"
//    android:layout_height="wrap_content"
//    app:icon_margin_left="10"
//    app:icon_text_margin="10"
//    app:item_height="50"
//    app:line_color="@color/line_d6d6d6"
//    app:margin_right="10"
//    app:right_text_color="@color/gray_333333"
//    app:right_text_margin="10"
//    app:right_text_size="@color/line_d6d6d6"
//    app:text_color="@color/gray_333333"
//    app:text_size="16sp" />
//=============================================
//    private BaseItemLayout layout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_item_layout);
//        layout = (BaseItemLayout) findViewById(R.id.layout);
//        initData();
//    }
//
//    private void initData() {
//        List<String> valueList = new ArrayList<>();
//
//        valueList.add("相册");
//        valueList.add("收藏");
//        valueList.add("钱包");
//        valueList.add("卡包");
//        valueList.add("设置");
//        SurfaceView s = new SurfaceView(this);
//
//        List<Integer> resIdList = new ArrayList<>();
//
//        resIdList.add(R.drawable.xc);
//        resIdList.add(R.drawable.sc);
//        resIdList.add(R.drawable.qb);
//        resIdList.add(R.drawable.kb);
//        resIdList.add(R.drawable.sz);
//
//        ConfigAttrs attrs = new ConfigAttrs(); // 把全部参数的配置，委托给ConfigAttrs类处理。
//
//        //参数 使用链式方式配置
//        attrs.setValueList(valueList)  // 文字 list
//                .setResIdList(resIdList) // icon list
//                .setIconWidth(24)  //设置icon 的大小
//                .setIconHeight(24)
//                .setItemMode(Mode.NORMAL);
//        layout.setConfigAttrs(attrs)
//                .create(); //
//
//        layout.setOnBaseItemClick(new BaseItemLayout.OnBaseItemClick() {
//            @Override
//            public void onItemClick(int position) {
//
//            }
//        });
//
//        layout.setOnSwitchClickListener(new BaseItemLayout.OnSwitchClickListener() {
//            @Override
//            public void onClick(int position, boolean isCheck) {
//
//            }
//        });
//
//
//    }
}