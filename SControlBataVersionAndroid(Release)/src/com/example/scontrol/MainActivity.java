//  : 'All code (c) Samsung Techwin Co,Ltd. all rights reserved.'

package com.example.scontrol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VerticalSeekBar;

@SuppressLint("ParserError" )
public class MainActivity extends Activity implements OnClickListener,
		SensorEventListener {

	private static final int ROTATE = 0;
	@SuppressLint("ParserError")
	private int motionActive = 1;

	private SeekBar seekBar;
	private TextView txtVolume;
	private VerticalSeekBar verticalSeekBar = null;
	private TextView vsProgress = null;
	private ImageView strActive;
	private ImageView drvActive;

	private SensorManager sensorManager = null;

	private TextView outputX;
	private TextView outputY;

	protected int Sensor;
	SensorManager mSensorManager;
	private int nowAccZ;
	private int nowGyroX;
	private int nowGyroY;
	private int standardAccZ;
	private int standardGyroX;
	private int standardGyroY;
	private int compareStandardNow;
	private int completedY;
	private int motionButtonCheck;

	AbsoluteLayout scontrol;
	RelativeLayout statelayout;
	LinearLayout drivelayout;
	LinearLayout unmannedlayout;
	TextView[] rightwheel = new TextView[3];
	TextView[] leftwheel = new TextView[3];
	TextView[] state = new TextView[5];
	ImageButton[] drive = new ImageButton[3];
	ImageButton[] unmanned = new ImageButton[3];
	ImageButton motionbutton;
	private TextView ipText;

	TextView motionspeed;
	TextView kmh;

	public String SERVER_IP = "210.118.75.174";

	SeekBar testbar1, testbar2, testbar3, testbar4;
	private Global mGlobal;

	// 주행제어명령 전송 쓰레드
	private Handler mHandler;
	private NumberThread mNumberThread;
	public int SERVER_PORT = 30001;
	String name;
	boolean lbCheck = false;

	private byte emergency_select = 0x00;
	private byte operation_select = 0x01;
	private byte driving_select = 0x01;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		SMyView vw = new SMyView(this);
		fuel fuelGauge = new fuel(this);
		Battery batteryGauge = new Battery(this);
		coolant coolantGauge = new coolant(this);

		addContentView(vw, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addContentView(batteryGauge, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addContentView(fuelGauge, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addContentView(coolantGauge, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		mGlobal = (Global) this.getApplicationContext();

		// ////////////////socket initialize/////////////
		mNumberThread = new NumberThread(true);
		mNumberThread.start();
		mHandler = new Handler();
		// //////////////////////////////////////////////

		SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
		SERVER_IP = prefs.getString("IP", "");

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		scontrol = (AbsoluteLayout) findViewById(R.id.scontrol);
		statelayout = (RelativeLayout) findViewById(R.id.statelayout);
		drivelayout = (LinearLayout) findViewById(R.id.drivelayout);
		unmannedlayout = (LinearLayout) findViewById(R.id.unmannedlayout);
		rightwheel[0] = (TextView) findViewById(R.id.rightwheel1);
		rightwheel[1] = (TextView) findViewById(R.id.rightwheel2);
		rightwheel[2] = (TextView) findViewById(R.id.rightwheel3);
		leftwheel[0] = (TextView) findViewById(R.id.leftwheel1);
		leftwheel[1] = (TextView) findViewById(R.id.leftwheel2);
		leftwheel[2] = (TextView) findViewById(R.id.leftwheel3);
		state[0] = (TextView) findViewById(R.id.vcu);
		state[1] = (TextView) findViewById(R.id.pcu);
		state[2] = (TextView) findViewById(R.id.mcu);
		state[3] = (TextView) findViewById(R.id.tcu);
		state[4] = (TextView) findViewById(R.id.fcu);
		drive[0] = (ImageButton) findViewById(R.id.neutral);
		drive[1] = (ImageButton) findViewById(R.id.reverse);
		drive[2] = (ImageButton) findViewById(R.id.driving);
		unmanned[0] = (ImageButton) findViewById(R.id.emergency);
		unmanned[1] = (ImageButton) findViewById(R.id.parking);
		unmanned[2] = (ImageButton) findViewById(R.id.unmanned);
		motionbutton = (ImageButton) findViewById(R.id.motionbutton);
		motionspeed = (TextView) findViewById(R.id.motionspeed);
		kmh = (TextView) findViewById(R.id.kmh);
		strActive = (ImageView) findViewById(R.id.streering_ctrl);
		drvActive = (ImageView) findViewById(R.id.driving_ctrl);

		seekBar = (SeekBar) findViewById(R.id.seekbar);
		txtVolume = (TextView) findViewById(R.id.volume);
		verticalSeekBar = (VerticalSeekBar) findViewById(R.id.vertical_Seekbar);
		vsProgress = (TextView) findViewById(R.id.vertical_sb_progresstext);
		outputX = (TextView) findViewById(R.id.speedtextX);
		outputY = (TextView) findViewById(R.id.speedtextY);

		motionbutton.setVisibility(View.INVISIBLE);
		outputX.setVisibility(View.INVISIBLE);
		outputY.setVisibility(View.INVISIBLE);

		// ��젣�좉쾬
		ipText = (TextView) findViewById(R.id.iptext);
		ipText.setText(SERVER_IP);
		// ��젣�좉쾬

		Typeface face = Typeface.createFromAsset(getAssets(),
				"font/malgunbd.ttf");// �고듃 �ㅼ젙(留묒�怨좊뵓)
		motionspeed.setTypeface(face);
		kmh.setTypeface(face);
		txtVolume.setTypeface(face);
		vsProgress.setTypeface(face);
		outputX.setTypeface(face);
		outputY.setTypeface(face);

		for (int i = 0; i < 5; i++) // �곹깭�덉씠�꾩썐 �고듃 �곸슜
		{
			if (i < 3) {
				rightwheel[i].setTypeface(face);
				leftwheel[i].setTypeface(face);
			}
			state[i].setTypeface(face);
		}
		

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			// 트래킹 종료 시크바를 놓았을때
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekBar.setProgress(50);
				strActive.setBackgroundResource(R.drawable.str_ctrl);
			}

			// 트래킹 시작 시크바를 잡았을때
			public void onStartTrackingTouch(SeekBar seekBar) {
				strActive.setBackgroundResource(R.drawable.str_ctrl_active);
			}

			// 시크바의 값이 변경될때
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				txtVolume.setText("조향: " + ((progress - 50) * 2) + "%");
				mNumberThread.direction = (short) progress;
			}
		});
		verticalSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					// 트래킹 종료 시크바를 놓았을때
					public void onStopTrackingTouch(SeekBar seekBar) {
						drvActive.setBackgroundResource(R.drawable.drv_ctrl);
					}
					// 트래킹 시작 시크바를 잡았을때
					public void onStartTrackingTouch(SeekBar seekBar) {
						drvActive.setBackgroundResource(R.drawable.drv_ctrl_active);
					}
					// 시크바의 값이 변경될때
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						vsProgress.setText("구동: " + (progress - 100) + " %");
						mNumberThread.accelator = (short) progress;
					}
				});

		drive[0].setOnClickListener(this);
		drive[1].setOnClickListener(this);
		drive[2].setOnClickListener(this);
		unmanned[0].setOnClickListener(this);
		unmanned[1].setOnClickListener(this);
		unmanned[2].setOnClickListener(this);
		motionbutton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(0, ROTATE, 0, "Rotate");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ROTATE:
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // �몃줈
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			break;
		}
		return true;
	}

	public void onClick(View v) {

		byte flag;
		byte data;

		switch (v.getId()) {
		case R.id.neutral:
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			drive[0].setBackgroundResource(R.drawable.neutral_sel);
			drive[1].setBackgroundResource(R.drawable.reverse);
			drive[2].setBackgroundResource(R.drawable.driving);
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			driving_select = 0x01;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			sendButtonCommand(flag, data);
			break;

		case R.id.reverse:
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			drive[0].setBackgroundResource(R.drawable.neutral);
			drive[1].setBackgroundResource(R.drawable.reverse_sel);
			drive[2].setBackgroundResource(R.drawable.driving);
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			driving_select = 0x02;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			sendButtonCommand(flag, data);
			break;

		case R.id.driving:
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			drive[0].setBackgroundResource(R.drawable.neutral);
			drive[1].setBackgroundResource(R.drawable.reverse);
			drive[2].setBackgroundResource(R.drawable.driving_sel);
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			driving_select = 0x03;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			sendButtonCommand(flag, data);
			break;

		case R.id.emergency:

			if (emergency_select == 1) {
				unmanned[0].setBackgroundResource(R.drawable.emergency);
				emergency_select = 0;
				flag = 0x11;
				data = 0x00;
				sendButtonCommand(flag, data);
			} else {
				unmanned[0].setBackgroundResource(R.drawable.emergency_sel);
				emergency_select = 1;
				flag = 0x11;
				data = (byte) 0xff;
				sendButtonCommand(flag, data);
			}


			break;

		case R.id.parking:
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			unmanned[1].setBackgroundResource(R.drawable.parking_sel);
			unmanned[2].setBackgroundResource(R.drawable.unmanned);
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			operation_select = 0x01;
			// 기동모드 명령 전송
			flag = 0x21;
			data = (byte) operation_select;
			sendButtonCommand(flag, data);
			break;

		case R.id.unmanned:

			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			unmanned[1].setBackgroundResource(R.drawable.parking);
			unmanned[2].setBackgroundResource(R.drawable.unmanned_sel);
			operation_select = 0x03;
			// 기동모드 명령 전송
			flag = 0x21;
			data = (byte) operation_select;
			sendButtonCommand(flag, data);
			// �듭떊遺�텇�먯꽌 �ㅼ떆�섏젙
			break;

		case R.id.motionbutton:

			standardGyroX = nowGyroX;
			standardGyroY = nowGyroY;
			standardAccZ = nowAccZ;

			if (motionActive == 1) {
				motionbutton
						.setBackgroundResource(R.drawable.motion_ctr_activel);
				motionButtonCheck = 1;
				motionActive = 0;
			} else {
				motionbutton.setBackgroundResource(R.drawable.motion_ctrl);
				motionButtonCheck = 0;
				motionActive = 1;
			}
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("onKeyDown",""+keyCode);
		if (keyCode == 4) {
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				return false;
			} 
			setDialog();
			return false;
		} else
	return super.onKeyDown(keyCode, event);
	}
	void setDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("프로그램을 종료하시겠습니까?").setCancelable(false) .setPositiveButton("예", new DialogInterface.OnClickListener() { private Activity activity;
	public void onClick(DialogInterface dialog, int id){
		activity.finish(); 
		}
	})
	.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int id) {
	} 
	}); 
	builder.create().show(); 
	}



/*	public void onBackPressed(){
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
		} 
		else {
			return true;
		}
	}*/


	// 현재 선택된 버튼의 명령을 전송
	public void sendButtonCommand(byte flag, byte data) {
		ByteBuffer buff1 = ByteBuffer.allocate(9);
		byte buf1[] = buff1.array();
		buff1.put(flag);
		buff1.put(data);
		buf1 = buff1.array();
		try {
			mNumberThread.output.write(buf1);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// �몃줈�꾪솚 �ъ쓣��
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mNumberThread.Motion = true;
			Toast.makeText(MainActivity.this, "세로모드", Toast.LENGTH_SHORT)
					.show();

			scontrol.setBackgroundResource(R.drawable.motion_bgr);

			statelayout.setX(350);
			statelayout.setY(200);

			drivelayout.setX(380);
			drivelayout.setY(850);

			unmannedlayout.setX(580);
			unmannedlayout.setY(850);

			motionbutton.setX(25);
			motionbutton.setY(820);

			motionspeed.setScaleX(1.4f);
			motionspeed.setScaleY(1.4f);
			motionspeed.setX(80);
			motionspeed.setY(605);
			motionspeed.setGravity(Gravity.RIGHT);
			kmh.setX(450);
			kmh.setY(630);

			outputX.setX(335);
			outputX.setY(670);

			outputY.setX(335);
			outputY.setY(705);

			
			txtVolume.setVisibility(View.INVISIBLE);
			vsProgress.setVisibility(View.INVISIBLE);
			outputX.setVisibility(View.VISIBLE);
			outputY.setVisibility(View.VISIBLE);
			motionbutton.setVisibility(View.VISIBLE);
			seekBar.setVisibility(View.INVISIBLE);
			verticalSeekBar.setVisibility(View.INVISIBLE);
			strActive.setVisibility(View.INVISIBLE);
			
			mGlobal.setBatteryX(210);
			mGlobal.setBatteryY(290);

		}
		
		else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mNumberThread.Motion = false;
			Toast.makeText(MainActivity.this, "가로모드", Toast.LENGTH_SHORT)
					.show();
			scontrol.setBackgroundResource(R.drawable.back);

			statelayout.setX(803);
			statelayout.setY(172);

			drivelayout.setX(300);
			drivelayout.setY(500);

			unmannedlayout.setX(780);
			unmannedlayout.setY(500);

			motionspeed.setScaleX(1f);
			motionspeed.setScaleY(1f);
			motionspeed.setX(490);
			motionspeed.setY(230);
			motionspeed.setGravity(Gravity.CENTER);
			kmh.setX(690);
			kmh.setY(390);

						
			motionActive=0;
			motionButtonCheck=0;
			motionbutton.setBackgroundResource(R.drawable.motion_ctrl);
			
			txtVolume.setVisibility(View.VISIBLE);
			vsProgress.setVisibility(View.VISIBLE);
			outputX.setVisibility(View.INVISIBLE);
			outputY.setVisibility(View.INVISIBLE);
			motionbutton.setVisibility(View.INVISIBLE);
			seekBar.setVisibility(View.VISIBLE);
			verticalSeekBar.setVisibility(View.VISIBLE);
			strActive.setVisibility(View.VISIBLE);
			
			mGlobal.setBatteryX(258);
			mGlobal.setBatteryY(256);
			

		}
	}

	// 요기까지
	@Override
	public synchronized void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ORIENTATION),
				sensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onStop() {
		super.onStop();
		sensorManager.unregisterListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ORIENTATION));
		sensorManager.unregisterListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ACCELEROMETER));
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case SensorManager.SENSOR_ACCELEROMETER:
			nowAccZ = (int) event.values[2];

			if (nowAccZ < 1)
				compareStandardNow = 0;
			else if (nowAccZ >= 1)
				compareStandardNow = 1;
			break;

		case SensorManager.SENSOR_ORIENTATION:
			nowGyroX = (int) ((float) event.values[0] * 10 * -1);
			nowGyroY = (int) ((float) event.values[1] * 10 * -1);

			if (motionButtonCheck == 1) {
				if (compareStandardNow == 0) {
					completedY = nowGyroY - standardGyroY;
				} else if (compareStandardNow == 1) {
					if (standardGyroY > nowGyroY) {
						completedY = ((nowGyroY - standardGyroY) + ((-96 - nowGyroY) + (-96 - nowGyroY)));
					}
					if (standardGyroY < nowGyroY) {
						Toast.makeText(MainActivity.this, "성공",
								Toast.LENGTH_LONG).show();
						completedY = ((nowGyroY - standardGyroY) + ((-96 - nowGyroY) + (-96 - nowGyroY)));
					}
				}
				if(nowGyroX >= 100)
					nowGyroX = 100;
				else if(nowGyroX <= -100)
					nowGyroX = -100;
				else if(completedY >= 100)
					completedY = 100;
				else if(completedY <= -100)
					completedY = -100;

				outputX.setText("  조향:" + (int) nowGyroX + "%");
				outputY.setText("  구동:" + ((int) completedY) + "%");
			} else {
				outputX.setText("  조향:" + 0 + "%");
				outputY.setText("  구동:" + 0 + "%");
				//출력과 스티어링이 0~200사이 이므로 중간값인 100을 default로 선언
				nowGyroX = 0;
				completedY = 0;
			}

			// mNumberThread.motionDirection = (short) nowGyroX;
			//mNumberThread.motionAccelator = (short) completedY;

			break;
		}
	}
	
	

	

	public class recvSocket extends Thread {

		DataInputStream input;
		int mSpeed; // 시스템 모듈에서 수신받은 차량의 속도
		int mSOC;
		int mCoolant;
		int mFuel;
		int[] faultArray = new int[5];

		short[] wheelRpm = new short[2]; // 수신 받게되는 rpm정보가 저장 0은 죄측 1은 우측

		public void run() {
			super.run();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (true) {
				ByteBuffer buff1 = ByteBuffer.allocate(9);
				byte buf1[] = buff1.array();
				try {
					input.read(buf1);
					switch (buf1[0]) {
					case (byte) 0x85:
						recvStatus(buf1);
						break;
					case (byte) 0x96:
						recvRpm(buf1);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// 1.2.8
		// 현재의 차량상태 보고를 수신
		private void recvStatus(byte[] buf) {
			short faultStatus = 0;

			mSpeed = buf[1]; //0~60
			if(mSpeed < 0)
			{
				mSpeed *= -1;
			}
			mFuel = buf[2];  //0~100
			mSOC = buf[3]; 	//0~100
			mCoolant = (0xff & buf[4]);	//0~130
				
			
			
			ByteBuffer buff1 = ByteBuffer.allocate(9);
			buff1.put(buf[6]);
			buff1.put(buf[5]);
			faultStatus = buff1.getShort(0);
			short num = 3;
			for (int i = 0; i < 5; i++) {
				faultArray[i] = (faultStatus & num) >> (i * 2);
				num = (short) (num << 2);
			}
			mHandler.post(new Runnable() {
				public void run() {
			
					motionspeed.setText(mSpeed+"");
					mGlobal.setbatteryAngle(((mSOC*90)/100)-120); //range -120~-30
					mGlobal.setfuelAngle((((mFuel*95)/100)-120)*-1); //25~120
					mGlobal.setcoolantAngle(-145-((mCoolant*70)/130)); //-145~-215
					for (int i = 0; i < 5; i++) {
						switch (faultArray[i]) {
						case 0:
							state[i].setTextColor(Color.parseColor("#ffffff"));//normal
							break;
						case 1:
							state[i].setTextColor(Color.parseColor("#ff0000"));//stop
							break;
						case 2:
							state[i].setTextColor(Color.parseColor("#ffff00"));//warning
							break;
						case 3:
							state[i].setTextColor(Color.parseColor("#00ff00"));//fault
							break;
						}
					}
				}
			});
		}

		// 1.2.9 차량의 바퀴 회전수를 수신
		private void recvRpm(byte[] buf) {
			ByteBuffer buff1 = ByteBuffer.allocate(9);
			buff1.put(buf[2]);
			buff1.put(buf[1]);
			buff1.put(buf[4]);
			buff1.put(buf[3]);
			// 손신 측에서 -방지를 위해 10000을 더해서 보내왔기 때문에 빼야함

			wheelRpm[0] = (short) (buff1.getShort(0) - 10000);
			wheelRpm[1] = (short) (buff1.getShort(2) - 10000);
			mHandler.post(new Runnable() {
				public void run() {
					for (int i = 0; i < 3; i++) {
						leftwheel[i]
								.setText(mNumberThread.mRecvSocket.wheelRpm[0]
										+ "");
						rightwheel[i]
								.setText(mNumberThread.mRecvSocket.wheelRpm[1]
										+ "");
					}
				}
			});
			buff1.put(buf[3]);
		}
	}

	

	
	class NumberThread extends Thread {

		private boolean isPlay = false;
		private boolean serverStatus = false;
		Socket socket;
		
		//직전에 송신한 엑셀과 조향값
		short prevSTR_CMD = 0;
		short prevAccel = 0;
		short count = 0;
		
		
		DataOutputStream output;
		private recvSocket mRecvSocket;

		public short direction = 50;
		public short accelator = 100;

		public Boolean Motion = false; // 모션 동작모드를 선택
		public short motionDirection = 0;
		public short motionAccelator = 0;

		private short motionMin = -70;
		private short motionMax = 70;

		public void connect() {
			try {
				socket = null;
				Log.d("Thead", "connect1" + socket);

				socket = new Socket(SERVER_IP, SERVER_PORT);
				Log.d("Thead", "connect2" + socket);
				mRecvSocket = new recvSocket();			
				mRecvSocket.start();
				output = new DataOutputStream(socket.getOutputStream());
				mRecvSocket.input = new DataInputStream(socket.getInputStream());
				serverStatus = true;
				while (socket != null) {
					if (socket.isConnected()) {
						output.flush();
						break;
					}
				}
			} catch (Exception e) {
				//서버 접속이 불가능 하면 프로그램 종료
				finish();
			}
		}

		

		
		
		
		public NumberThread(boolean isPlay) {
			this.isPlay = isPlay;
		}

		public void stopThread() {
			isPlay = !isPlay;
		}

		// 송신한 6칸의 배열의 바이트 오더를 맞추어준다.
		public void swap(byte buf[]) {
			byte tmp;
			tmp = buf[1];
			buf[1] = buf[2];
			buf[2] = tmp;

			tmp = buf[3];
			buf[3] = buf[4];
			buf[4] = tmp;

			tmp = buf[5];
			buf[5] = buf[6];
			buf[6] = tmp;
		}

		private short motionToValue(short motionValue) {
			short CMD = 50;
			if ((motionValue >= motionMin) && (motionValue <= -20)) {
				CMD = (short) (motionValue + 50);
			} else if ((motionValue <= motionMax) && (motionValue >= 20)) {
				CMD = (short) ((motionValue - 20) + 50);
			} else if (motionValue > motionMax) {
				CMD = 100;
			} else if (motionValue < motionMin) {
				CMD = 0;
			}

			return CMD;
		}

		
		
		@Override
		public void run() {
			super.run();
			if (serverStatus == false) {

				connect();
				
			} else {

			}


			
			while (isPlay) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				mHandler.post(new Runnable() {
					public void run() {
						// 주행제어 명령 전송
						// short STR_CMD = (short) seekBar.getProgress(); //
						// 현재방향값을 꺼내옴
						// short accel = (short) verticalSeekBar.getProgress();
						short STR_CMD = direction;
						short accel = accelator;

						if (Motion == true) {
							// STR_CMD = motionToValue(motionDirection);
							// accel = (short) (motionToValue(motionAccelator) *
							// 2);
							STR_CMD = (short)(nowGyroX+50);
							accel = (short)(completedY+100);

						}

				
						if((prevSTR_CMD == STR_CMD) &&(prevAccel == accel) && (count <= 3))
						{
							count++;
							return;
						}
						count = 0;
						prevSTR_CMD = STR_CMD;
						prevAccel = accel;
						short DRV_CMD = 0;
						short BRK_CMD = 0;

						if (accel > 100) {
							DRV_CMD = (short) ((accel - 100));
						} else {
							BRK_CMD = (short) ((accel - 100) * (-1));
						}
				
						byte flag = 0x46;
						ByteBuffer buff1 = ByteBuffer.allocate(9);
						buff1.put(flag);
						buff1.putShort(DRV_CMD);
						buff1.putShort(STR_CMD);
						buff1.putShort(BRK_CMD);
						byte buf1[] = buff1.array();
						swap(buf1);
						try {

							output.write(buf1);
							Log.d("Thead", "send3" + BRK_CMD);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
			}
		}
	}

}
