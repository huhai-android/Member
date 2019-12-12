package com.newdjk.member.service;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.luckcome.lmtpdecorder.LMTPDecoder;
import com.luckcome.lmtpdecorder.LMTPDecoderListener;
import com.luckcome.lmtpdecorder.data.FhrData;
import com.newdjk.member.R;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 核心后台服务类，负责BLE蓝牙设备连接，蓝牙数据传输。上层UI通过该类来获取要显示的数据。
 */

public class BLEBluetoothService extends BluetoothBaseService {

	// 服务的回调接口
	public Callback mCallback = null;
	// 蓝牙适配器
	public BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
	// 服务当前连接的蓝牙设备
	public BluetoothDevice mBtDevice;
	public BluetoothGatt mBluetoothGatt;
	public PipedInputStream pi;
	public PipedOutputStream po;
	// 是否保存标志
	public boolean isRecord = false;
	/** 终端协议解析器 */
	public LMTPDecoder mLMTPDecoder = null;
	/** 解析器回调接口 */
	public LMTPDListener mLMTPDListener = null;
	// 写特征
	private BluetoothGattCharacteristic write_characteristic;

	@Override
	public void onCreate() {
		mLMTPDecoder = new LMTPDecoder();
		mLMTPDListener = new LMTPDListener();
		mLMTPDecoder.setLMTPDecoderListener(mLMTPDListener);
		mLMTPDecoder.prepare();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		cancel();
		return super.onUnbind(intent);
	}

	public BluetoothBinder mBinder = new BluetoothBinder();
	public class BluetoothBinder extends Binder {
		public BLEBluetoothService getService() {
			return BLEBluetoothService.this;
		}
	}
	
	/** 消息编号，连接完成 */
	public static final int MSG_CONNECT_FINISHED = 10;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what) {
			case MSG_CONNECT_FINISHED:
				isReading = true;
				mReadThread = new ReadThread();
				mReadThread.start();
				break;
			}
		}
	};
	
	/**
	 * 设置要连接的蓝牙设备
	 * @param device
	 */
	public void setBluetoothDevice(BluetoothDevice device) {
		mBtDevice = device;
	}
	/**
	 * 启动连接线程
	 */
	@SuppressLint("NewApi")
	public void start() {
		if (mReadThread != null) {
			if (mReadThread.isAlive())
				mReadThread.cancel();
			mReadThread = null;
		}
		if (mBtDevice==null) {
			mBtDevice = mAdapter.getRemoteDevice(String.valueOf(mBtDevice));
		}
		mBluetoothGatt = mBtDevice.connectGatt(this, false, mGattCallback);
		pi = new PipedInputStream();
		po = new PipedOutputStream();
		try {
			pi.connect(po);
		} catch (IOException e) {
			e.printStackTrace();
		}
        mHandler.sendEmptyMessage(MSG_CONNECT_FINISHED);
		mLMTPDecoder.startWork();
	}
	
	/**
	 * 停止数据读取和解析
	 */
	public void cancel() {
		isReading = false;
        if (mReadThread != null) {
            mReadThread.cancel();
            mReadThread = null;
        }
        if (mLMTPDecoder != null) {
			mLMTPDecoder.stopWork();
		}
	}
	
	/**
	 * 读数据线程
	 */
	private boolean isReading = false;
	
	ReadThread mReadThread = null;
	private class ReadThread extends Thread {

		@Override
		public void run() {

			int len;
			byte[] buffer = new byte[107];
			while(isReading) {
				try {
					len = pi.read(buffer);
					if (mLMTPDecoder != null) {
						mLMTPDecoder.putData(buffer, 0, len);
					}
				} catch (IOException e) {
					isReading = false;

				} 
			}
		}
		@SuppressLint("NewApi")
		public void cancel() {
			try {
				if(mBluetoothGatt != null){
					mBluetoothGatt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取工作状态
	 * @return
	 */
	public boolean getReadingStatus() {
		return isReading;
	}
	
	/**
	 * 启动记录功能
	 */
	public void recordStart() {
		/*File path = Utils.getRecordFilePath();
		String fname = "" + System.currentTimeMillis();
		mLMTPDecoder.beginRecordWave(path, fname);*/
		isRecord = true;
		if(mCallback != null)
			mCallback.dispServiceStatus(getResources().getString(R.string.fetalheart_monitor_recording_string));
	}
	
	/**
	 * 结束记录
	 */
	public void recordFinished() {
		isRecord = false;
		/*if (mLMTPDecoder != null) {
			mLMTPDecoder.finishRecordWave();
		}*/
		if(mCallback != null)
			mCallback.dispServiceStatus(getResources().getString(R.string.fetalheart_monitor_record_finished_string));
	}
	
	/**
	 * 获取记录状态
	 * @return
	 */
	public boolean getRecordStatus() {
		return isRecord;
	}

	/**
	 * 设置宫缩复位
	 * @param value 宫缩复位的值
	 */
	public void setTocoResetBLE(byte[] value) {
		writeCharacteristic(value);
	}

	/**
	 * 设置一次手动胎动
	 */
	public void setFM() {
		mLMTPDecoder.setFM();
	}

	/**
	 * 设置胎心音量
	 * @param value		胎心音量大小
	 */
	public void setFhrVolume(int value) {
		mLMTPDecoder.sendFhrVolue(value);
	}

	/**
	 * 设置回调接口
	 * @param cb
	 */
	public void setCallback(Callback cb) {
		mCallback = cb;
	}

	/**
	 * 协议解析器回调接口
	 * @author Administrator
	 *
	 */
	class LMTPDListener implements LMTPDecoderListener {

		/**
		 * 有新数据产生的时候回调
		 */
		@Override
		public void fhrDataChanged(FhrData fhrData) {

			String infor = String.format(
					"FHR1: %-3d\nFHR2: %-3d\nTOCO: %-3d\nAFM: %-3d\nSIGN: %-3d\nafmFlag: %-3d\nBATT: %-3d\n"
					+ "isFHR1: %-3d\nisTOCO: %-3d\nisAFM: %-3d\n",
					fhrData.fhr1,fhrData.fhr2, fhrData.toco, fhrData.afm, fhrData.fhrSignal, fhrData.afmFlag, fhrData.devicePower,
					fhrData.isHaveFhr1, fhrData.isHaveToco, fhrData.isHaveAfm);
			
			if(fhrData.fmFlag != 0)
				Log.e("LMTPD", "LMTPD...1...fm");
			
			if(fhrData.tocoFlag != 0)
				Log.e("LMTPD", "LMTPD...2...toco");

			if(mCallback != null)
				mCallback.dispInfor(infor);

			if(mCallback != null)
				mCallback.dispData(fhrData);
		}

		/**
		 * 有命令产生的时候回调
		 */
		@Override
		public void sendCommand(byte[] cmd) {
			// 这里添加从蓝牙发送数据的代码
			if(po != null) {
				try {
					po.write(cmd);
					po.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";

	@SuppressLint("NewApi")
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

		//连接状态改变的回调
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				Log.e("TAG", "onConnectionStateChange:" + mBluetoothGatt.discoverServices());
				if(mCallback != null) {
					mCallback.dispServiceStatus("0");
				}
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				Log.e("TAG", "onConnectionStateChange()->1");
                if(mCallback != null) {
                    mCallback.dispServiceStatus(getResources().getString(R.string.fetalheart_monitor_service_read_data_fail_string));
                }
                // 连接不成功时重连
                gatt.connect();
			}
		}

		//发现服务的回调
		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			//成功发现服务后可以调用相应方法得到该BLE设备的所有服务，并且打印每一个服务的UUID和每个服务下各个特征的UUID
			if (status == BluetoothGatt.GATT_SUCCESS) {
				connectCharacter(mBluetoothGatt.getServices());
			} else {
				Log.e("TAG", "onServicesDiscovered received: " + status);
			}
		}

		//读操作的回调
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				Log.e("TAG", "onCharacteristicRead：" +characteristic.getValue());
			}
		}

		//写操作的回调
		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				Log.e("TAG", "onCharacteristicWrite：" +characteristic.getValue());
			}
		}

		//数据返回的回调（此处接收BLE设备返回数据）
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

			byte[] data = characteristic.getValue();

			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
		}
	};

	@SuppressLint("NewApi")
	private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {

		final byte[] data = characteristic.getValue();
		final int len = data.length;
//			Log.e("ReceiveData","len: "+len);
		try {
			po.write(data);
			po.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	private void connectCharacter(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;
		for (BluetoothGattService gattService : gattServices) { // 遍历出gattServices里面的所有服务
			List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();

			// Loops through available Characteristics.
			for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) { // 遍历每条服务里的所有Characteristic
				String uuid = null;
				uuid = gattCharacteristic.getUuid().toString();
//				Log.e("UUID: ", uuid);
				if ((BluetoothGattCharacteristic.PROPERTY_READ & gattCharacteristic.getProperties()) > 0) {
					// READ set one
					uuid = gattCharacteristic.getUuid().toString();
//					Log.e("UUID_READ: ", uuid);
				}
				if ((BluetoothGattCharacteristic.PROPERTY_WRITE & gattCharacteristic.getProperties()) > 0) {
					// write set one
					uuid = gattCharacteristic.getUuid().toString();
//					Log.e("UUID_WRITE: ", uuid);
					write_characteristic = gattService.getCharacteristic(UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb"));
				}
				if (uuid.equals("0000fed6-0000-1000-8000-00805f9b34fb")) {
					setCharacteristicNotification(gattCharacteristic, true);
					return;
				}
			}
		}
	}

	// 可接收通知的UUID，设置其可以接收通知（notification）
	@SuppressLint("NewApi")
	public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mAdapter == null || mBluetoothGatt == null) {
//			Log.e(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
		BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
		if (descriptor != null) {
			descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			mBluetoothGatt.writeDescriptor(descriptor);
		}
	}

	/**
	 * 写入指令的方法
	 *
	 * @param bytes 写入指令
	 */
	@SuppressLint("NewApi")
	public void writeCharacteristic(byte[] bytes) {
		if (mBluetoothGatt == null || write_characteristic == null) {
			Log.e("TAG", "BluetoothAdapter not initialized");
			return;
		}
		write_characteristic.setValue(bytes);
		Log.e("writeCharacteristic", "writeCharacteristic:" + bytes);
		mBluetoothGatt.writeCharacteristic(write_characteristic);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mLMTPDecoder.release();
		mLMTPDecoder = null;
		mLMTPDListener = null;
	}
	
}
