package com.newdjk.member.views.baseitem.Item;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.newdjk.member.views.baseitem.SwitchImageView;
import com.newdjk.member.views.baseitem.utils.DensityUtil;


/**
 * Created by maimingliang on 2016/12/4.
 */

public class ButtonItem extends AbstractItem {


    private SwitchImageView switchImageView;

    private RelativeLayout.LayoutParams switchCblp;


    public ButtonItem(Context context) {
        super(context);
    }

    public ButtonItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void createWidget() {
        switchImageView = new SwitchImageView(mContext);
    }

    @Override
    public void createWidgetLayoutParams() {

        switchCblp = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switchCblp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        switchCblp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
    }


    @Override
    public void addWidget() {
        super.addWidget();
        addView(switchImageView,switchCblp);
        setSwitchImageViewStyle();
    }


    public void setSwitchImageViewStyle(){


        if(configAttrs == null){
            throw new RuntimeException("config attrs is not set");
        }
        if(configAttrs.getTrunResId() <= 0 || configAttrs.getUpResId() <= 0){
            throw new RuntimeException("button res is not set");
        }

        switchCblp.rightMargin = DensityUtil.dip2px(mContext,configAttrs.getMarginRight());

        switchImageView.setImageResource(configAttrs.getTrunResId());



    }


    public SwitchImageView getSwitchImageView(){
        return switchImageView;
    }
}
