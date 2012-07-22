package com.example.scontroll;

import android.annotation.SuppressLint;
import android.app.*;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
/*import android.widget.VerticalSeekBar_Reverse;*/
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	SeekBar seekBar, seekBar_coolant, seekBar_battery, seekBar_fuel;
	TextView txtVolume, txtVolume_coolant, txtVolume_battery, txtVolume_fuel;
	// 주용이가 한부분
	private Button neutral, reverse, driving, pivot;
	private Button emergency, parking, monned, unmonned;
	private TextView text; // 요까지]
	private int emergency_select=0;
	
	// 정호형 한부분
	TextView speed;// 요까지
	
	VerticalSeekBar verticalSeekBar = null;
	/* VerticalSeekBar_Reverse verticalSeekBar_Reverse=null; */
	TextView vsProgress, vs_reverseProgress = null;
	
	// ㅈ닫러자ㅣㅓㅈ라ㅣ젇리ㅏ저다ㅣㄹㅈ더ㅣㅈㄷㄹ

	@SuppressLint("ParserError")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//정호형 부분
	    int a=100;
	    speed =(TextView)findViewById(R.id.speedtext);
	    speed.setText(a+""); // 여기까지		
		
		verticalSeekBar = (VerticalSeekBar) findViewById(R.id.vertical_Seekbar);
		/*
		 * verticalSeekBar_Reverse=(VerticalSeekBar_Reverse)findViewById(R.id.
		 * seekbar_reverse);
		 */
		vsProgress = (TextView) findViewById(R.id.vertical_sb_progresstext);
		/*
		 * vs_reverseProgress=(TextView)findViewById(R.id.reverse_sb_progresstext
		 * );
		 */

		// 주용
		neutral = (Button) findViewById(R.id.neutral);
		reverse = (Button) findViewById(R.id.reverse);
		driving = (Button) findViewById(R.id.driving);
		pivot = (Button) findViewById(R.id.pivot);
		emergency = (Button) findViewById(R.id.emergency);
		parking = (Button) findViewById(R.id.parking);
		monned = (Button) findViewById(R.id.monned);
		unmonned = (Button) findViewById(R.id.unmonned);
		// 요기까지

		verticalSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						vsProgress.setText(progress + "");
					}
				});

		/*
		 * verticalSeekBar_Reverse.setOnSeekBarChangeListener(new
		 * OnSeekBarChangeListener() {
		 * 
		 * public void onStopTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub }
		 * 
		 * public void onStartTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub }
		 * 
		 * public void onProgressChanged(SeekBar seekBar, int progress, boolean
		 * fromUser) { vs_reverseProgress.setText(progress+""); } });
		 */

		seekBar = (SeekBar) findViewById(R.id.seekbar);
		txtVolume = (TextView) findViewById(R.id.volume);

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar.
		    setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
			}
		});

		seekBar_coolant = (SeekBar) findViewById(R.id.seekbar_coolant);
		seekBar_coolant.setEnabled(false);
		txtVolume_coolant = (TextView) findViewById(R.id.volume_coolant);

		// 시크바의 값이 변경될 때의 이벤트 처리
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

		seekBar_battery = (SeekBar) findViewById(R.id.seekbar_battery);
		seekBar_battery.setEnabled(false);		
		txtVolume_battery = (TextView) findViewById(R.id.volume_battery);

		// 시크바의 값이 변경될 때의 이벤트 처리
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

		seekBar_fuel = (SeekBar) findViewById(R.id.seekbar_fuel);
		seekBar_fuel.setEnabled(false);
		txtVolume_fuel = (TextView) findViewById(R.id.volume_fuel);

		// 시크바의 값이 변경될 때의 이벤트 처리
		seekBar_fuel.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			// 트래킹 종료 시크바를 놓았을때
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
		//주용이가 한부분
		neutral.setOnClickListener(this);
		reverse.setOnClickListener(this);
		driving.setOnClickListener(this);
		pivot.setOnClickListener(this);
		emergency.setOnClickListener(this);
		parking.setOnClickListener(this);
		monned.setOnClickListener(this);
		unmonned.setOnClickListener(this);
        //요기까지
	}
    //주용이가 한부분
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.emergency:
			if(emergency_select == 0)
			{
				emergency.setBackgroundColor(0xffff0000);
				emergency_select = 1;
			}
			else if(emergency_select == 1)
			{
				emergency.setBackgroundColor(0xff50c8FF);
				emergency_select = 0;
			}
			
			break;

		case R.id.parking:
			parking.setBackgroundColor(0xff0064ff);
			monned.setBackgroundColor(0xff50c8FF);
			unmonned.setBackgroundColor(0xff50c8FF);
			break;

		case R.id.monned:
			parking.setBackgroundColor(0xff50c8FF);
			monned.setBackgroundColor(0xff0064ff);
			unmonned.setBackgroundColor(0xff50c8FF);
			break;

		case R.id.unmonned:
			parking.setBackgroundColor(0xff50c8FF);
			monned.setBackgroundColor(0xff50c8FF);
			unmonned.setBackgroundColor(0xff0064ff);
			break;

		case R.id.neutral:
			neutral.setBackgroundColor(0xff288C28);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff66CDAA);

			break;

		case R.id.reverse:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff288C28);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff66CDAA);
			break;

		case R.id.driving:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff288C28);
			pivot.setBackgroundColor(0xff66CDAA);
			break;

		case R.id.pivot:
			neutral.setBackgroundColor(0xff66CDAA);
			reverse.setBackgroundColor(0xff66CDAA);
			driving.setBackgroundColor(0xff66CDAA);
			pivot.setBackgroundColor(0xff288C28);
			break;

		default:
			break;

		}

	}
	//요기까지
}

