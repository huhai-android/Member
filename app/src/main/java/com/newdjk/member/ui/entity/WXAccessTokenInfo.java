package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2018/10/23.
 */

public class WXAccessTokenInfo {

    /**
     * access_token : 14__y5VPHOiz3oVxsjN13pYDLyGzsgMx2_L31ShrOcg5yjQYAs1pZQObzOkEvztriFi_1r-GA3Rt7L7937OhiA-gJkbUM3EhbLLCUUNVF4OIno
     * expires_in : 7200
     * refresh_token : 14_MlirZuvOyDnkOjHwZJ4h9SteI9BsiVnPQbQcKLhohvw6Wn5hVP6L6MU8uDUncaDmCSQe-rnPahbTXF_3W4I8pBhbOx3DSwJ_xLUvAIp2fhQ
     * openid : oXTs15_192QUC-PbrI6SLEBSkFBY
     * scope : snsapi_userinfo
     * unionid : oU2Fe1Xp8VHsg1c6UKa3WcuV0PAI
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
