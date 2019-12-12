package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2018/11/14.
 */

public class Position {

    private float lat;
    private float lon;

    public Position(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
