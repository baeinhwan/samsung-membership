package com.example.scontroll;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LoginActivity extends Activity {
	private EditText edittextpassword, edittextip;
	private Button login;
	private String stringpassword, stringip;
	private String password;
	private TextView comment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		password = "dgssm";
		edittextpassword = (EditText) findViewById(R.id.edittextpassword);
		edittextip = (EditText) findViewById(R.id.edittextip);
		login = (Button) findViewById(R.id.login);
		comment = (TextView) findViewById(R.id.comment);

		login.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
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
					comment.setText("로그인 성공!");
				} else {
					comment.setText("로그인실패");
				}
			}
		});

	}

}

