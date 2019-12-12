package com.newdjk.member.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.luckcome.lmtpdecorder.data.FhrData;

/**
 * 核心后台服务基类，兼容不同蓝牙设备连接，上层UI通过该类来获取要显示的数据。
 */

public class BluetoothBaseService extends Service {

    public BluetoothDevice mDevice;
    // 是否保存标志
    public boolean isRecord = false;
    private boolean isReading = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class BluetoothBinder extends Binder {

    }

    private final IBinder mBinder = new BluetoothBinder();

    public synchronized void start() {

    }

    /**
     * 设置要连接的蓝牙设备
     *
     * @param device
     */
    public void setBluetoothDevice(BluetoothDevice device) {
        mDevice = device;
    }

    /**
     * 服务的回调接口定义
     */
    public interface Callback {
        /**
         * 主要显示监护数据信息
         *
         * @param infor
         */
        public void dispInfor(String infor);

        /**
         * 主要显示记录状态
         *
         * @param sta
         */
        public void dispServiceStatus(String sta);

        /**
         * 返回的数据
         *
         * @param fhrData
         */
        public void dispData(FhrData fhrData);
    }

    /**
     * 设置回调接口
     *
     * @param cb
     */
    public void setCallback(Callback cb) {

    }

    /**
     * 停止数据读取和解析
     */
    public void cancel() {

    }

    /**
     * 获取记录状态
     *
     * @return
     */
    public boolean getRecordStatus() {
        return isRecord;
    }

    /**
     * 设置一次手动胎动
     */
    public void setFM() {

    }

    /**
     * 设置宫缩复位SPP
     *
     * @param value 宫缩复位的值
     */
    public void setTocoResetSpp(int value) {

    }

    /**
     * 设置宫缩复位BLE
     *
     * @param bytes 数据
     */
    public void setTocoResetBLE(byte[] bytes) {

    }

    /**
     * 获取工作状态
     *
     * @return
     */
    public boolean getReadingStatus() {
        return isReading;
    }

    /**
     * 启动记录功能
     */
    public void recordStart() {
        isRecord = true;
    }

    /**
     * 结束记录
     */
    public void recordFinished() {
        isRecord = false;
    }

    /**
     * 设置胎心音量
     * @param value		胎心音量大小
     */
    public void setFhrVolume(int value) {
    }

}
