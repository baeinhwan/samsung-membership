// : 'All code (c) Samsung Techwin Co,Ltd. all rights reserved.'

package com.example.scontrol;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.*;
import android.view.*;
import android.widget.*;

@SuppressLint("ParserError")
public class LoginActivity extends Activity {
	public static final int REQUEST_CODE_ANOTHER = 1001;
	private EditText edittextpassword, edittextip;
	private Button login;
	private String stringpassword, stringip;
	private String password;
	private TextView comment;
	private TextView passwordText, ipText;
	private TextView department;

	protected boolean checkWifi() {
		// 안드로이드 네트워크 연결상태 확인 (Mobile/Wifi)
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// Wifi 네트워크 연결 상태
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		return isWifi;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		password = "dgssm";
		edittextpassword = (EditText) findViewById(R.id.edittextpassword);
		edittextip = (EditText) findViewById(R.id.edittextip);
		login = (Button) findViewById(R.id.login);
		comment = (TextView) findViewById(R.id.comment);
		passwordText = (TextView) findViewById(R.id.password);
		ipText = (TextView) findViewById(R.id.ip);
		department = (TextView) findViewById(R.id.department);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"font/malgunbd.ttf");// 맑은고딕
		edittextpassword.setTypeface(face);
		edittextip.setTypeface(face);
		login.setTypeface(face);
		comment.setTypeface(face);
		passwordText.setTypeface(face);
		ipText.setTypeface(face);
		department.setTypeface(face);
		department.setText("특수사업부 M&S 그룹");

		SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
		stringip = prefs.getString("IP", "");
		edittextip.setText(stringip); // IP 저장

		login.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (!checkWifi()) {
					Toast toast = null;
					toast.makeText(getBaseContext(), "WIFI 미연결",
							Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				stringpassword = edittextpassword.getText().toString();
				stringip = edittextip.getText().toString();
				if (password.equals(stringpassword)) {
					SharedPreferences prefs = getSharedPreferences("PrefName",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("IP", stringip);
					editor.commit();

					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					comment.setText("로그인성공!");
				} else {
					comment.setText("로그인실패");
				}
			}
		});

	}
	
	public void onBackPressed(){
		onDestroy();
	}
	

}
