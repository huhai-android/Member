package com.newdjk.member.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.utils.Listener;
import com.newdjk.member.views.baseitem.utils.DensityUtil;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * 绘制曲线
 * */
public class FhrView extends View {

	private Context context;
	/**
	 * 一共划分多少份
	 */
	private final int num = 17;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * 屏幕高度
	 */
	private int screenHeight;
	/**
	 * 宽9格
	 */
	private int wNum = 9;
	/**
	 * 一份宽度
	 */
//	private int oneWidth;
	private int oneWidth = DensityUtil.dip2px(MyApplication.getContext(),58);
	// fhr 曲线断线阈值
	private final int breakValue = 30;
	// 以下是监护数据的最值
	private final int fhrMax = 210;
	private final int fhrMin = 50;
	private final int tocoMax = 100;
	private final int tocoMin = 0;
	// 以下是网格标志在屏幕位置的值
	private float fhrTop;
	private float fhrBottom;
	private float fhrVer;
	private float tocoTop;
	private float tocoBottom;
	private float tocoVer;
	// 画笔
	private Paint mFhr1Paint;
	private Paint mThinPaint;
	private Paint mBoldPaint;
	private Paint areaPaint;
	private Paint timePaint;
	private int dataListRemoveCount = 0;
	private LinkedList<Listener.TimeData> dataList;
	private Listener.TimeData currData;
	// 背景图片
	private Bitmap bgBitmap;
	private Bitmap beatZdbmp;
	private Bitmap tocoResetBmp;
	/**
	 * 多少个像素显示一个数据
	 */
	private float wide = 2;

	/**
	 * 一屏曲线的数据总长度 ，一屏有三分钟的数据，共 360 个数据
	 */
	private int maxSize = 360;

	// 屏幕宽像素
	private int widthPixels;

	public FhrView(Context context) {
		this(context, null, 0);
		this.context = context;
		initBitmap();
	}

	public FhrView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
		initBitmap();
	}

	public FhrView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initBitmap();
	}

	private void initBitmap() {
		float width = context.getResources().getDimension(R.dimen.line_hight);
		// 粗直线
		mBoldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBoldPaint.setColor(context.getResources().getColor(R.color.fetalheart_monitor_line_color));
		mBoldPaint.setStrokeWidth(2f);
		// 细直线
		mThinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mThinPaint.setColor(context.getResources().getColor(R.color.fetalheart_monitor_line_color));
		mThinPaint.setStrokeWidth(2f);
		// 胎心宫缩曲线
		mFhr1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFhr1Paint.setColor(context.getResources().getColor(R.color.black));
		mFhr1Paint.setAntiAlias(true);
		mFhr1Paint.setStrokeWidth(width);
		// 手动胎动、宫缩标识图标
		beatZdbmp = BitmapFactory.decodeResource(getResources(), R.mipmap.quickening_click_icon);
		tocoResetBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.toco_reset_mark);
		/** 110-160区域背景*/
		areaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		areaPaint.setColor(context.getResources().getColor(R.color.fetalheart_monitor_area_bg_color));
		/** 时间坐标*/
		DisplayMetrics dm = getResources().getDisplayMetrics();//获取显示屏属性
		widthPixels = dm.widthPixels;
		timePaint = new Paint();
		if (widthPixels < 800) {
			timePaint.setTextSize(18);
		} else {
			timePaint.setTextSize(28);
		}
		timePaint.setColor(context.getResources().getColor(R.color.font_gray_5));
		timePaint.setTextAlign(Paint.Align.CENTER);
		timePaint.setAntiAlias(true);
		timePaint.setStrokeWidth(2);
	}

	public void setDataList(LinkedList<Listener.TimeData> dataList) {
		this.dataList = dataList;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			this.screenHeight = bottom - top;
			this.screenWidth = right - left;
			wNum = screenWidth / oneWidth;
			maxSize = wNum * 40;
			int remain = screenWidth % oneWidth;
			int remainSize = (int) Math.floor( remain*1.0f / oneWidth* 40);
			maxSize = maxSize + remainSize;
			wide = (right - left)*1.0f / maxSize;
			this.screenWidth = oneWidth * 9;
			fhrTop = this.screenHeight * 18 / 760;
			fhrBottom = this.screenHeight * 488 / 760;
			fhrVer = (fhrBottom - fhrTop) / (fhrMax - fhrMin);
			tocoTop = this.screenHeight * 530 / 760;
			tocoBottom = this.screenHeight * 743 / 760;
			tocoVer = (tocoBottom - tocoTop) / (tocoMax - tocoMin);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.translate(60,0);
		/** 110-160区域背景*/
		canvas.drawRect(0, fhrToValue(160), screenWidth, fhrToValue(110), areaPaint);

		int offX = (int) (dataListRemoveCount *  wide);
		int startIndex = offX / (oneWidth*3);
		float minStep = oneWidth* 3.0f; // 一个屏幕宽度代表3分钟，计算一分钟的宽度

		int num = (int) Math.ceil(screenWidth * 200 / minStep);
		for (int i = startIndex; i < startIndex + num; i++) {
			// 垂直竖线（3厘米/分钟，一格大约1厘米）
			if (i == 0) {
				mBoldPaint.setStrokeWidth(6f);
				canvas.drawLine(oneWidth / 4 * i - offX, fhrToValue(215), oneWidth / 4 * i - offX, fhrToValue(50), mBoldPaint);
				canvas.drawLine(oneWidth / 4 * i - offX, tocoToValue(105), oneWidth / 4 * i - offX, tocoToValue(0), mBoldPaint);
			} else {
				mBoldPaint.setStrokeWidth(2f);
				canvas.drawLine(oneWidth / 4 * i - offX, fhrToValue(215), oneWidth / 4 * i - offX, fhrToValue(50), mBoldPaint);
				canvas.drawLine(oneWidth / 4 * i - offX, tocoToValue(105), oneWidth / 4 * i - offX, tocoToValue(0), mBoldPaint);
			}
			// 时间坐标（3厘米/分钟，一格大约1厘米）
			canvas.drawText(String.valueOf(i/2) + "min", minStep * i - offX, (tocoToValue(98) + fhrToValue(46)) / 2, timePaint);
		}

		// 胎心区域水平直线
		canvas.drawLine(0, fhrToValue(50), screenWidth, fhrToValue(50), mThinPaint);
		canvas.drawLine(0, fhrToValue(60), screenWidth, fhrToValue(60), mBoldPaint);
		canvas.drawLine(0, fhrToValue(70), screenWidth, fhrToValue(70), mThinPaint);
		canvas.drawLine(0, fhrToValue(80), screenWidth, fhrToValue(80), mThinPaint);
		canvas.drawLine(0, fhrToValue(90), screenWidth, fhrToValue(90), mBoldPaint);
		canvas.drawLine(0, fhrToValue(100), screenWidth, fhrToValue(100), mThinPaint);
		canvas.drawLine(0, fhrToValue(110), screenWidth, fhrToValue(110), mThinPaint);
		canvas.drawLine(0, fhrToValue(120), screenWidth, fhrToValue(120), mBoldPaint);
		canvas.drawLine(0, fhrToValue(130), screenWidth, fhrToValue(130), mThinPaint);
		canvas.drawLine(0, fhrToValue(140), screenWidth, fhrToValue(140), mThinPaint);
		canvas.drawLine(0, fhrToValue(150), screenWidth, fhrToValue(150), mBoldPaint);
		canvas.drawLine(0, fhrToValue(160), screenWidth, fhrToValue(160), mThinPaint);
		canvas.drawLine(0, fhrToValue(170), screenWidth, fhrToValue(170), mThinPaint);
		canvas.drawLine(0, fhrToValue(180), screenWidth, fhrToValue(180), mBoldPaint);
		canvas.drawLine(0, fhrToValue(190), screenWidth, fhrToValue(190), mThinPaint);
		canvas.drawLine(0, fhrToValue(200), screenWidth, fhrToValue(200), mThinPaint);
		canvas.drawLine(0, fhrToValue(210), screenWidth, fhrToValue(210), mBoldPaint);

		// 宫缩区域水平直线
		canvas.drawLine(0, tocoToValue(0), screenWidth, tocoToValue(0), mBoldPaint);
		canvas.drawLine(0, tocoToValue(10), screenWidth, tocoToValue(10), mThinPaint);
		canvas.drawLine(0, tocoToValue(20), screenWidth, tocoToValue(20), mBoldPaint);
		canvas.drawLine(0, tocoToValue(30), screenWidth, tocoToValue(30), mThinPaint);
		canvas.drawLine(0, tocoToValue(40), screenWidth, tocoToValue(40), mBoldPaint);
		canvas.drawLine(0, tocoToValue(50), screenWidth, tocoToValue(50), mThinPaint);
		canvas.drawLine(0, tocoToValue(60), screenWidth, tocoToValue(60), mBoldPaint);
		canvas.drawLine(0, tocoToValue(70), screenWidth, tocoToValue(70), mThinPaint);
		canvas.drawLine(0, tocoToValue(80), screenWidth, tocoToValue(80), mBoldPaint);
		canvas.drawLine(0, tocoToValue(90), screenWidth, tocoToValue(90), mThinPaint);
		canvas.drawLine(0, tocoToValue(100), screenWidth, tocoToValue(100), mBoldPaint);

		// 胎心区域纵坐标
		canvas.drawText("60", -30, fhrToValue(57), timePaint);
		canvas.drawText("90", -30, fhrToValue(87), timePaint);
		canvas.drawText("120", -30, fhrToValue(117), timePaint);
		canvas.drawText("150", -30, fhrToValue(147), timePaint);
		canvas.drawText("180", -30, fhrToValue(177), timePaint);
		canvas.drawText("210", -30, fhrToValue(207), timePaint);

		// 宫缩区域纵坐标
		canvas.drawText("0", -30, tocoToValue(-4), timePaint);
		canvas.drawText("20", -30, tocoToValue(16), timePaint);
		canvas.drawText("40", -30, tocoToValue(36), timePaint);
		canvas.drawText("60", -30, tocoToValue(56), timePaint);
		canvas.drawText("80", -30, tocoToValue(76), timePaint);
		canvas.drawText("100", -30, tocoToValue(96), timePaint);
		if (dataList != null && dataList.size() > 1) {
			for (int i = 1; i < dataList.size(); i++) {
				if (dataList == null) return;
				Listener.TimeData timeData = dataList.get(i);
				Listener.TimeData lasttimeData = dataList.get(i - 1);
				if (timeData == null || lasttimeData == null) return;
				int lastRate = lasttimeData.heartRate;
				int currRate = timeData.heartRate;
				int lastToco = lasttimeData.tocoWave;
				int currToco = timeData.tocoWave;
				int statu1 = timeData.status1;

				float startX = (i - 1) * wide;
				float stopX = i * wide;
				// 胎心率值
				float fhrStartY = fhrToValue(lastRate);
				float fhrStopY = fhrToValue(currRate);
				// 宫缩值
				float tocoStartY = tocoToValue(lastToco);
				float tocoStopY = tocoToValue(currToco);
				// 判断是否断线
				boolean breakflag = (new BigDecimal(lastRate - currRate).abs().intValue()) <= breakValue;
				if (lastRate < 50 || lastRate > 210 || currRate < 50 || currRate > 210) {
				} else {
					if (breakflag) {
						canvas.drawLine(startX, fhrStartY, stopX, fhrStopY, mFhr1Paint);
					} else {
						canvas.drawPoint(stopX, fhrStopY, mFhr1Paint);
					}
				}
				canvas.drawLine(startX, tocoStartY, stopX, tocoStopY, mFhr1Paint);

				// 自动胎动
				boolean afmFlag = (statu1 & 0x04) == 0 ? false : true;
				if (afmFlag) {
					canvas.drawRect(stopX - wide / 2, fhrToValue(200), stopX + wide / 2, fhrToValue(190), mFhr1Paint);
				}

				// 手动胎动
				boolean fmFlag = (statu1 & 0x08) == 0 ? false : true;
				if (fmFlag) {
					canvas.drawBitmap(beatZdbmp, stopX - wide / 2, fhrToValue(210), null);
				}
//            // 宫缩复位
//            if ((statu1 & 0x10) != 0) {
//                canvas.drawBitmap(tocoResetBmp, stopX - wide / 2, fhrToValue(200), null);
//            }

			}
		}

	}

	/**
	 * 将胎心率逻辑坐标位置转换为物理坐标
	 *
	 * @param fhr
	 * @return
	 */
	private float fhrToValue(int fhr) {
		float v = fhrTop + fhrVer * (fhrMax - fhr);
		return v;
	}

	/**
	 * 将宫缩值逻辑坐标转换为物理坐标
	 *
	 * @param toco
	 * @return
	 */
	private float tocoToValue(int toco) {
		float v = tocoTop + tocoVer * (tocoMax - toco);
		return v;
	}

	/**
	 * 添加一个心率跳动
	 */
	public void addBeat(Listener.TimeData currData) {

		this.currData = currData;
		try {
			if (dataList.size() != 0 && dataList.size() >= maxSize / 2) {
				dataList.pollFirst();
				dataListRemoveCount++;
			}
			dataList.add(currData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		postInvalidate();
		/*if ((currData.status1 & 0x04) == 0) {
			currData.status2 = 0;
		} else {
			currData.status1 = 0;
			currData.status2 = 0;
		}*/
		currData.status1 = 0;
		currData.status2 = 0;
	}

	/**
	 * 添加
	 */
	public void addSelfBeat() {
		if (currData != null) {
			currData.beatZd = Listener.BEAT;
			currData.status1 |= 0x08;
		}
	}

	public void addTocoReset() {
		if (currData != null) {
			currData.status1 |= 0x10;
		}
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		dataList.clear();
		invalidate();
		dataListRemoveCount = 0;
	}
}
