package com.newdjk.member.views.baseitem.factory;


import android.content.Context;

import com.newdjk.member.views.baseitem.Item.AbstractItem;
import com.newdjk.member.views.baseitem.config.ConfigAttrs;
import com.newdjk.member.views.baseitem.config.Mode;


/**
 * Created by maimingliang on 2016/12/4.
 *
 * 创建Item 的工厂类
 */

public abstract class AbstractItemFactory {

    protected Context mContext;

    public AbstractItemFactory(Context context){
        this.mContext = context;
    }


    public abstract  <T extends AbstractItem> T createItem(Mode mode, ConfigAttrs attrs);
}
