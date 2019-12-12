package com.newdjk.member.views.baseitem.factory;


import android.content.Context;
import android.util.Log;

import com.newdjk.member.views.baseitem.Item.AbstractItem;
import com.newdjk.member.views.baseitem.Item.ArrowItem;
import com.newdjk.member.views.baseitem.Item.ButtonItem;
import com.newdjk.member.views.baseitem.Item.NormalItem;
import com.newdjk.member.views.baseitem.Item.RedTextItem;
import com.newdjk.member.views.baseitem.Item.TextItem;
import com.newdjk.member.views.baseitem.config.ConfigAttrs;
import com.newdjk.member.views.baseitem.config.Mode;


/**
 * Created by maimingliang on 2016/12/4.
 */

public class ItemFactory extends AbstractItemFactory {

    public ItemFactory(Context context) {
        super(context);
    }

    /**
     * 创建出ItemView
     * @param mode  通过传入 不同mode 来创建出不同类型的itemview
     * @param attrs
     * @param <T>
     * @return
     */
    @Override
    public  <T extends AbstractItem> T createItem(Mode mode, ConfigAttrs attrs) {
        Log.e("mode",mode.name());


        if(mode == null){
            throw new RuntimeException("please set mode");
        }
        AbstractItem item = null;

        try {


            if(mode == Mode.NORMAL){

                item = new NormalItem(mContext);

            }else if(mode == Mode.ARROW){

                item = new ArrowItem(mContext);

            }else if(mode == Mode.BOTTON){

                item = new ButtonItem(mContext);
            }else if(mode == Mode.TEXT){
                item = new TextItem(mContext);
            }else if(mode == Mode.RED_TEXT){
                item = new RedTextItem(mContext);
            }


            if(item != null){
                item.create(attrs);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


        return (T) item;
    }
}
