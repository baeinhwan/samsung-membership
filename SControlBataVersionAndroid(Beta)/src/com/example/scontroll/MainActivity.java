package com.example.scontroll;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VerticalSeekBar;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class MainActivity extends Activity implements SensorEventListener,
		OnClickListener {

	private static final int ROTATE = 0;
	private SeekBar seekBar, seekBar_coolant, seekBar_battery, seekBar_fuel;
	private TextView txtVolume, txtVolume_coolant, txtVolume_battery,
			txtVolume_fuel;

	private SensorManager sensorManager = null;

	private TextView outputX2;
	private TextView outputY2;

	// 수신받은 정보를 전시해주기 위한 변수

	Timer timer;

	private int Roll, Velocity, X, Y;

	SensorManager mSensorManager;

	protected int Sensor;

	private RelativeLayout statelay;
	private LinearLayout slinear, linear, seekBarlinear;
	private AbsoluteLayout scontrol;

	private Button neutral, reverse, driving, pivot;
	private Button emergency, parking, monned, unmonned;
	private TextView kmh;
	private byte emergency_select = 0x00;
	private byte operation_select = 0x01;
	private byte driving_select = 0x01;

	private TextView speed, speedX, speedY;

	private VerticalSeekBar verticalSeekBar = null;

	private TextView vsProgress = null;

	// 주행제어명령 전송 쓰레드
	private Handler mHandler;
	private NumberThread mNumberThread;

	public String SERVER_IP = "210.118.75.174";
	public int SERVER_PORT = 30001;
	String name;

	boolean lbCheck = false;

	// statelay textveiw
	TextView[] leftWheel = new TextView[3];
	TextView[] rightWheel = new TextView[3];
	TextView[] moduleStatus = new TextView[5]; // vcu,pcu,mcu,tcu,fcu

	@SuppressLint("ParserError")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
		SERVER_IP = prefs.getString("IP", "");

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		if (getLastNonConfigurationInstance() != null) {
			mNumberThread = (NumberThread) getLastNonConfigurationInstance();

		} else {
			mNumberThread = new NumberThread(true);
			mNumberThread.start();

		}
		mHandler = new Handler();

		Roll = 0;
		Velocity = 0;
		X = 0;
		Y = 0;

		speed = (TextView) findViewById(R.id.speedtext);
		speedX = (TextView) findViewById(R.id.speedtextX);
		speedY = (TextView) findViewById(R.id.speedtextY);
		verticalSeekBar = (VerticalSeekBar) findViewById(R.id.vertical_Seekbar);
		vsProgress = (TextView) findViewById(R.id.vertical_sb_progresstext);
		outputX2 = (TextView) findViewById(R.id.speedtextX);
		outputY2 = (TextView) findViewById(R.id.speedtextY);
		neutral = (Button) findViewById(R.id.neutral);
		reverse = (Button) findViewById(R.id.reverse);
		driving = (Button) findViewById(R.id.driving);
		pivot = (Button) findViewById(R.id.pivot);
		emergency = (Button) findViewById(R.id.emergency);
		parking = (Button) findViewById(R.id.parking);
		monned = (Button) findViewById(R.id.monned);
		unmonned = (Button) findViewById(R.id.unmonned);
		statelay = (RelativeLayout) findViewById(R.id.statelay);
		slinear = (LinearLayout) findViewById(R.id.slinear);
		linear = (LinearLayout) findViewById(R.id.linear);
		seekBarlinear = (LinearLayout) findViewById(R.id.seekBarlinear);
		kmh = (TextView) findViewById(R.id.kmh);
		scontrol = (AbsoluteLayout) findViewById(R.id.scontrol);

		leftWheel[0] = (TextView) findViewById(R.id.l1_wheel);
		leftWheel[1] = (TextView) findViewById(R.id.l2_wheel);
		leftWheel[2] = (TextView) findViewById(R.id.l3_wheel);

		rightWheel[0] = (TextView) findViewById(R.id.r1_wheel);
		rightWheel[1] = (TextView) findViewById(R.id.r2_wheel);
		rightWheel[2] = (TextView) findViewById(R.id.r3_wheel);

		moduleStatus[0] = (TextView) findViewById(R.id.vcu);
		moduleStatus[1] = (TextView) findViewById(R.id.pcu);
		moduleStatus[2] = (TextView) findViewById(R.id.mcu);
		moduleStatus[4] = (TextView) findViewById(R.id.tcu);
		moduleStatus[3] = (TextView) findViewById(R.id.fcu);

		seekBar = (SeekBar) findViewById(R.id.seekbar);
		txtVolume = (TextView) findViewById(R.id.volume);
		seekBar_coolant = (SeekBar) findViewById(R.id.seekbar_coolant);
		txtVolume_coolant = (TextView) findViewById(R.id.volume_coolant);
		seekBar_battery = (SeekBar) findViewById(R.id.seekbar_battery);
		txtVolume_battery = (TextView) findViewById(R.id.volume_battery);
		seekBar_fuel = (SeekBar) findViewById(R.id.seekbar_fuel);
		txtVolume_fuel = (TextView) findViewById(R.id.volume_fuel);

		speed.setText(Velocity + "");
		speedX.setText(X + "");
		speedY.setText(Y + "");

		// 시크바의 값이 변경될 때의 이벤트 처리
		verticalSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					// 트래킹 종료 시크바를 놓았을때
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					// 트래킹 시작 시크바를 잡았을때
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					// 시크바의 값이 변경될때
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						vsProgress.setText(progress + "");
						mNumberThread.accelator = (short) progress;
					}
				});

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			// 트래킹 종료 시크바를 놓았을때
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			// 트래킹 시작 시크바를 잡았을때
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			// 시크바의 값이 변경될때
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				txtVolume.setText("" + progress);
				mNumberThread.direction = (short) progress;
			}
		});

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar_coolant.setEnabled(false);
		seekBar_coolant
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					// 트래킹 종료 시크바를 놓았을때
					public void onStopTrackingTouch(SeekBar seekBar_coolant) {
					}

					// 트래킹 시작 시크바를 잡았을때
					public void onStartTrackingTouch(SeekBar seekBar_coolant) {
					}

					// 시크바의 값이 변경될때
					public void onProgressChanged(SeekBar seekBar_coolant,
							int progress, boolean fromUser) {
						txtVolume_coolant.setText("Coolant : " + progress);
					}
				});

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar_battery.setEnabled(false);
		seekBar_battery
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					// 트래킹 종료 시크바를 놓았을때
					public void onStopTrackingTouch(SeekBar seekBar_battery) {
					}

					// 트래킹 시작 시크바를 잡았을때
					public void onStartTrackingTouch(SeekBar seekBar_battery) {
					}

					// 시크바의 값이 변경될때
					public void onProgressChanged(SeekBar seekBar_battery,
							int progress, boolean fromUser) {
						txtVolume_battery.setText("Battery  : " + progress);
					}
				});

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar_fuel.setEnabled(false);
		seekBar_fuel.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			// 트래킹 종료 시크바를 놓았을때7
			public void onStopTrackingTouch(SeekBar seekBar_fuel) {
			}

			// 트래킹 시작 시크바를 잡았을때
			public void onStartTrackingTouch(SeekBar seekBar_fuel) {
			}

			// 시크바의 값이 변경될때
			public void onProgressChanged(SeekBar seekBar_fuel, int progress,
					boolean fromUser) {
				txtVolume_fuel.setText("F u e l　: " + progress);
			}
		});

		neutral.setOnClickListener(this);
		reverse.setOnClickListener(this);
		driving.setOnClickListener(this);
		pivot.setOnClickListener(this);
		emergency.setOnClickListener(this);
		parking.setOnClickListener(this);
		monned.setOnClickListener(this);
		unmonned.setOnClickListener(this);

		timer = new Timer();
		timer.schedule(new UpdateReportData(), 300, 300);

		Resources r = Resources.getSystem();
		Configuration config = r.getConfiguration();
		onConfigurationChanged(config);

	}

	class UpdateReportData extends TimerTask {

		public void run() {
			runOnUiThread(new Runnable() {
				public void run() {
					speed.setText(mNumberThread.mRecvSocket.mSpeed + "");
					seekBar_battery.setProgress(mNumberThread.mRecvSocket.mSOC);
					seekBar_coolant
							.setProgress(mNumberThread.mRecvSocket.mCoolant);
					seekBar_fuel.setProgress(mNumberThread.mRecvSocket.mFuel);
					for (int i = 0; i < 5; i++) {
						switch (mNumberThread.mRecvSocket.faultArray[i]) {
						case 0:
							moduleStatus[i].setText("Normal");
							break;
						case 1:
							moduleStatus[i].setText("Stop ");
							break;
						case 2:
							moduleStatus[i].setText("Warning");
							break;
						case 3:
							moduleStatus[i].setText("Fault");
							break;
						}
					}
					for(int i=0;i<3;i++)
					{
						leftWheel[i].setText(mNumberThread.mRecvSocket.wheelRpm[0]+"rpm");
						rightWheel[i].setText(mNumberThread.mRecvSocket.wheelRpm[1]+"rpm");
					}
				}
			});

		}
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
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 세로
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			break;
		}
		return true;
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// 세로전환 되었을때
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mNumberThread.Motion = true;
			Toast.makeText(MainActivity.this, "세로모드", Toast.LENGTH_SHORT)
					.show();
			txtVolume_coolant.setX(800);
			txtVolume_battery.setX(800);
			txtVolume_fuel.setX(800);
			txtVolume.setX(800);

			seekBar_coolant.setX(300);
			seekBar_coolant.setY(-50);
			seekBar_battery.setX(300);
			seekBar_battery.setY(-70);
			seekBar_fuel.setX(300);
			seekBar_fuel.setY(-90);
			seekBar.setX(800);

			statelay.setScaleX(1.4f);
			statelay.setScaleY(1.4f);
			statelay.setPivotX(0);
			statelay.setPivotY(0);
			statelay.setX(-750);
			statelay.setY(200);

			slinear.setScaleX(1.4f);
			slinear.setScaleY(1.4f);
			slinear.setPivotX(0);
			slinear.setPivotY(0);
			slinear.setX(0);
			slinear.setY(300);

			linear.setScaleX(1.4f);
			linear.setScaleY(1.4f);
			linear.setPivotX(0);
			linear.setPivotY(0);
			linear.setX(-250);
			linear.setY(300);
			seekBarlinear.setX(1080);
			seekBarlinear.setY(440);

			speed.setScaleX(2f);
			speed.setScaleY(2f);
			speed.setPivotX(0);
			speed.setPivotY(0);
			speed.setX(-500);
			speed.setY(-200);

			kmh.setScaleX(2f);
			kmh.setScaleY(2f);
			kmh.setPivotX(0);
			kmh.setPivotY(0);
			kmh.setX(-400);
			kmh.setY(-200);

			outputX2.setX(-500);
			outputX2.setY(650);
			outputY2.setX(-500);
			outputY2.setY(650);

			scontrol.setBackgroundColor(Color.BLACK);
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mNumberThread.Motion = false;
			Toast.makeText(MainActivity.this, "가로모드", Toast.LENGTH_SHORT)
					.show();
			txtVolume_coolant.setX(0);
			txtVolume_battery.setX(0);
			txtVolume_fuel.setX(0);
			txtVolume.setX(0);

			seekBar_coolant.setX(0);
			seekBar_coolant.setY(0);
			seekBar_battery.setX(0);
			seekBar_battery.setY(0);
			seekBar_fuel.setX(0);
			seekBar_fuel.setY(0);
			seekBar.setX(0);

			statelay.setX(0);
			statelay.setY(0);

			slinear.setX(0);
			slinear.setY(0);

			linear.setX(0);
			linear.setY(0);
			seekBarlinear.setX(0);
			seekBarlinear.setY(0);

			speed.setX(0);
			speed.setY(0);

			kmh.setX(0);
			kmh.setY(0);
		}
	}

	public void onClick(View v) {

		byte flag;
		byte data;
		ByteBuffer buff1 = ByteBuffer.allocate(9);
		byte buf1[] = buff1.array();
		switch (v.getId()) {

		// 비상정지
		case R.id.emergency:
			if (emergency_select == 0) {
				emergency.setBackgroundColor(0xffff0000);
				emergency_select = 1;
				flag = 0x11;
				data = (byte) 0xff;

				buff1.put(flag);
				buff1.put(data);
				buf1 = buff1.array();
				try {
					mNumberThread.output.write(buf1);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else if (emergency_select == 1) {
				emergency.setBackgroundColor(0xff50c8FF);
				emergency_select = 0;
				flag = 0x11;
				data = 0x00;
				buff1.put(flag);
				buff1.put(data);
				buf1 = buff1.array();
				try {
					mNumberThread.output.write(buf1);

				} catch (IOException e) {

					e.printStackTrace();
				}
			} else if (emergency_select == 0xFF) {
				emergency.setBackgroundColor(0xff50c8FF);
				emergency_select = 0x00;
				// 비상정지 명령 전송
				flag = 0x11;
				data = (byte) emergency_select;
				buff1.put(flag);
				buff1.put(data);
				buf1 = buff1.array();
				try {
					mNumberThread.output.write(buf1);

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			break;

		// 기동파킹
		case R.id.parking:
			parking.setBackgroundColor(0xff0064ff);
			monned.setBackgroundColor(0xff50c8FF);
			unmonned.setBackgroundColor(0xff50c8FF);
			operation_select = 0x01;
			// 기동모드 명령 전송
			flag = 0x21;
			data = (byte) operation_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 유인기동
		case R.id.monned:
			parking.setBackgroundColor(0xff50c8FF);
			monned.setBackgroundColor(0xff0064ff);
			unmonned.setBackgroundColor(0xff50c8FF);
			operation_select = 0x02;
			// 기동모드 명령 전송
			flag = 0x21;
			data = (byte) operation_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 무인기동
		case R.id.unmonned:
			parking.setBackgroundColor(0xff50c8FF);
			monned.setBackgroundColor(0xff50c8FF);
			unmonned.setBackgroundColor(0xff0064ff);
			operation_select = 0x03;
			// 기동모드 명령 전송
			flag = 0x21;
			data = (byte) operation_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 중립기어
		case R.id.neutral:
			neutral.setBackgroundColor(0xff288C28);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff66CDAA);
			driving_select = 0x01;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 후진기어
		case R.id.reverse:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff288C28);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff66CDAA);
			driving_select = 0x02;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 전진기어
		case R.id.driving:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff288C28);
			pivot.setBackgroundColor(0xff66CDAA);
			driving_select = 0x03;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		// 선회
		case R.id.pivot:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff288C28);
			driving_select = 0x04;
			// 주행모드 명령 전송
			flag = 0x31;
			data = (byte) driving_select;
			buff1.put(flag);
			buff1.put(data);
			buf1 = buff1.array();
			try {
				mNumberThread.output.write(buf1);

			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	public Object onRetainNonConfigurationInstance() {

		return mNumberThread;
	}

	// 요기까지
	@Override
	public synchronized void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ORIENTATION),
				sensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onStop() {
		super.onStop();
		sensorManager.unregisterListener(this, sensorManager
				.getDefaultSensor(sensorManager.SENSOR_ORIENTATION));
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case SensorManager.SENSOR_ORIENTATION:
			outputX2.setText("좌우:" + (int) ((float) event.values[0] * 10 * -1));
			outputY2.setText("전후:" + (int) ((float) event.values[1] * 10 * -1));
			mNumberThread.motionDirection = (short) (event.values[0] * 10 * -1);
			mNumberThread.motionAccelator = (short) (event.values[1] * 10 * -1);
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

			mSpeed = buf[1];
			mFuel = buf[2];
			mSOC = buf[3];
			mCoolant = buf[4];
			ByteBuffer buff1 = ByteBuffer.allocate(9);
			buff1.put(buf[6]);
			buff1.put(buf[5]);
			faultStatus = buff1.getShort(0);
			short num = 3;
			for (int i = 0; i < 5; i++) {
				faultArray[i] = (faultStatus & num) >> (i * 2);
				num = (short) (num << 2);
			}
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
			buff1.put(buf[3]);
		}
	}

	class NumberThread extends Thread {

		private boolean isPlay = false;
		private boolean serverStatus = false;
		Socket socket;

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
				mRecvSocket = new recvSocket();
				mRecvSocket.start();
				connect();

			} else {

			}

			while (isPlay) {
				try {
					Thread.sleep(300);
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
							STR_CMD = motionToValue(motionDirection);
							accel = (short) (motionToValue(motionAccelator) * 2);
						}

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
