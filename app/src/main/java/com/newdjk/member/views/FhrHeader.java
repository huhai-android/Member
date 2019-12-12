package com.newdjk.member.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 胎心数据文件格式头
 * 
 */
public class FhrHeader {
	/** 当前文件版本号 */
	public static final int VERSION = 2;
	/** 各个版本号文件头长度 */
	public static final int[] LEN = {0, 240, 304};
	// 头文件字节
	public static final int LENGTH = LEN[VERSION];
	public static final int BLUETOOTHNAMELENGTH = 100;
	public static final char[] LC = { 'L', 'U', 'C', 'K', 'C', 'O', 'M', 'E' };
	
	public static final int MID_LENGTH = 30;
	
	public int length = 0;
	public char[] luckcome = new char[8];
	public int pregWeek = 0;
	public int pregDay = 0;
	public long startTime = 0;
	public int bluetoothNameLength = 0;
	public char[] bluetoothName = new char[BLUETOOTHNAMELENGTH];
	public int midLength = 0;
	public char[] mid = new char[MID_LENGTH];
	

	public FhrHeader() {
	}

	public FhrHeader(int pregWeek, int pregDay, char[] name, long startTime) {
		this.length = LENGTH;
		for (int i = 0; i < LC.length; i++)
			luckcome[i] = LC[i];
		this.pregWeek = pregWeek;
		this.pregDay = pregDay;
		this.startTime = startTime;
		this.bluetoothNameLength = name.length;
		for (int i = 0; i < this.bluetoothNameLength; i++)
			this.bluetoothName[i] = name[i];
		
		this.midLength = -1;
	}

	public FhrHeader(int pregWeek, int pregDay, String name, long startTime) {
		this.length = LENGTH;
		for (int i = 0; i < LC.length; i++)
			luckcome[i] = LC[i];
		this.pregWeek = pregWeek;
		this.pregDay = pregDay;
		this.startTime = startTime;
		this.bluetoothNameLength = name.length();
		char[] tmp = name.toCharArray();
		for (int i = 0; i < this.bluetoothNameLength; i++)
			this.bluetoothName[i] = tmp[i];
		
		this.midLength = -1;
	}

	/**
	 * 从文件中读取文件头
	 * 
	 * @param f
	 * @return 
	 * @return 
	 * @return 
	 */
	public void readFromFile(File f) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "r");
			this.length = raf.readInt();
			for (int i = 0; i < LC.length; i++)
				this.luckcome[i] = raf.readChar();
			this.pregWeek = raf.readInt();
			this.pregDay = raf.readInt();
			this.startTime = raf.readLong();
			this.bluetoothNameLength = raf.readInt();
			if (this.bluetoothNameLength < BLUETOOTHNAMELENGTH) {
				for (int i = 0; i < this.bluetoothNameLength; i++) {
					this.bluetoothName[i] = raf.readChar();
				}
			}
			raf.skipBytes((BLUETOOTHNAMELENGTH - this.bluetoothNameLength) * 2);
			
			this.midLength = raf.readInt();
			if (this.midLength < MID_LENGTH) {
				for (int i = 0; i < this.midLength; i++) {
					this.mid[i] = raf.readChar();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将文件头写入文件
	 * 
	 * @param f
	 */
	public void writeToFile(File f) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
			raf.seek(0);
			raf.writeInt(this.length);
			for (int i = 0; i < LC.length; i++)
				raf.writeChar(this.luckcome[i]);
			raf.writeInt(this.pregWeek);
			raf.writeInt(this.pregDay);
			raf.writeLong(this.startTime);
			raf.writeInt(this.bluetoothNameLength);
			for (int i = 0; i < BLUETOOTHNAMELENGTH; i++)
				raf.writeChar(this.bluetoothName[i]);
			raf.writeInt(midLength);
			for (int i = 0; i < MID_LENGTH; i++)
				raf.writeChar(this.mid[i]);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 修改文件的mid
	 * @param mid	要写入的mid
	 */
	public static boolean writeMid(File f, String mid) {
		if(mid == null)
			return false;
		
		int len = mid.length();
		if(len == 0)
			return false;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
			raf.skipBytes(240);
			raf.writeInt(len);
			char[] id = mid.toCharArray();
			int length = len;
			if(length > 30)
				length = 30;
			for (int i = 0; i < length; i++)
				raf.writeChar(id[i]);
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	

	/**
	 * 判断该文件是否包含文件头
	 * 
	 * @return
	 */
	public boolean isHaveHead() {
		return String.valueOf(luckcome).equals(String.valueOf(LC));
	}
	
	/**
	 * 检测文件版本号
	 * @param headLeng	测试的文件头长度
	 * @return
	 */
	public static int testVersion(int headLeng) {
		if(headLeng < 0)
			return -1;
		
		int i = 0;
		for(; i < LEN.length; i++) {
			if(LEN[i] == headLeng)
				break;
		}
		
		if(i== LEN.length)
			return -1;
		
		return i;
	}
}
