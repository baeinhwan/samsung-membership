// : 'All code (c) Samsung Techwin Co,Ltd. all rights reserved.'

package com.example.scontrol;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class SMyView extends View {
	private Global mGlobal;
	int NeedleWidth;
	int NeedleHeight;
	int GaugeWidth;
	int GaugeHeight;

	Paint paint;

	Bitmap Needle;
	Bitmap backGround;
	Bitmap NeedleResize;
	Bitmap GaugeResize;

	private Configuration newConfig = null;

	public SMyView(Context context) {
		super(context);
		mGlobal = (Global) context.getApplicationContext();
		timer.sendEmptyMessageDelayed(0, 10);

		paint = new Paint();
		paint.setColor(Color.parseColor("#00ff0000"));
		paint.setStyle(Paint.Style.STROKE);

		Needle = BitmapFactory
				.decodeResource(getResources(), R.drawable.needle);

		NeedleResize = Bitmap.createScaledBitmap(Needle, 40, 82, true);
		NeedleWidth = NeedleResize.getWidth() / 2;
		NeedleHeight = NeedleResize.getHeight();
	}

	Handler timer = new Handler() {
		public void handleMessage(Message msg) {
			invalidate();
			timer.sendEmptyMessageDelayed(0, 100);
		}
	}; // Handler

}

class Battery extends View {
	private Global mGlobal;

	int CenterX;
	int CenterY;
	int NeedleWidth;
	int NeedleHeight;

	Paint paint;
	Bitmap velocityNeedle;
	Bitmap NeedleResize;

	public Battery(Context context) {
		super(context);
		mGlobal = (Global) context.getApplicationContext();

		paint = new Paint();
		paint.setColor(Color.parseColor("#00ff0000"));
		paint.setStyle(Paint.Style.STROKE);

		velocityNeedle = BitmapFactory.decodeResource(getResources(),
				R.drawable.needle);
		NeedleResize = Bitmap.createScaledBitmap(velocityNeedle, 20, 25, true);

		NeedleWidth = NeedleResize.getWidth() / 2;
		NeedleHeight = NeedleResize.getHeight() / 2;

	}

	public void onDraw(Canvas canvas) {

		CenterX = mGlobal.getBatteryX();
		CenterY = mGlobal.getBatteryY();
		
		canvas.drawCircle(CenterX, CenterY, 44, paint);
		canvas.rotate(mGlobal.getbatteryAngle(), CenterX, CenterY);
		canvas.drawBitmap(NeedleResize, CenterX - NeedleWidth, CenterY
				- (NeedleHeight + 130), null);
	}

}

class fuel extends View {
	private Global mGlobal;
	int CenterX;
	int CenterY;
	int NeedleWidth;
	int NeedleHeight;

	Paint paint;
	Bitmap velocityNeedle;
	Bitmap NeedleResize;

	public fuel(Context context) {
		super(context);
		mGlobal = (Global) context.getApplicationContext();

		paint = new Paint();
		paint.setColor(Color.parseColor("#00ffffff"));
		paint.setStyle(Paint.Style.STROKE);

		velocityNeedle = BitmapFactory.decodeResource(getResources(),
				R.drawable.needle);
		NeedleResize = Bitmap.createScaledBitmap(velocityNeedle, 20, 25, true);

		NeedleWidth = NeedleResize.getWidth() / 2;
		NeedleHeight = NeedleResize.getHeight() / 2;

	}

	public void onDraw(Canvas canvas) {

		CenterX = mGlobal.getBatteryX();
		CenterY = mGlobal.getBatteryY();
		
		canvas.rotate(mGlobal.getfuelAngle(), CenterX, CenterY);
		canvas.drawBitmap(NeedleResize, CenterX - NeedleWidth, CenterY
				- (NeedleHeight + 130), null);
	}

}// class

class coolant extends View {
	private Global mGlobal;
	int CenterX;
	int CenterY;
	int NeedleWidth;
	int NeedleHeight;

	Paint paint;
	Bitmap velocityNeedle;
	Bitmap NeedleResize;

	public coolant(Context context) {
		super(context);
		mGlobal = (Global) context.getApplicationContext();

		paint = new Paint();
		paint.setColor(Color.parseColor("#00ff0000"));
		paint.setStyle(Paint.Style.STROKE);

		velocityNeedle = BitmapFactory.decodeResource(getResources(),
				R.drawable.needle);
		NeedleResize = Bitmap.createScaledBitmap(velocityNeedle, 20, 25, true);

		NeedleWidth = NeedleResize.getWidth() / 2;
		NeedleHeight = NeedleResize.getHeight() / 2;
	}

	public void onDraw(Canvas canvas) {

		CenterX = mGlobal.getBatteryX();
		CenterY = mGlobal.getBatteryY();
		
		canvas.drawCircle(CenterX, CenterY, 44, paint);
		canvas.rotate(mGlobal.getcoolantAngle(), CenterX, CenterY);
		canvas.drawBitmap(NeedleResize, CenterX - NeedleWidth, CenterY
				- (NeedleHeight + 130), null);
	}

}// class

