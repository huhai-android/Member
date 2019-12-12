package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2019/2/14.
 */

public class WXResp {


    /**
     * errcode : 40029
     * errmsg : invalid code, hints: [ req_id: HHGAm44ce-fQq2BA ]
     */

    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
