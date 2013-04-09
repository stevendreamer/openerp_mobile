package com.oe.mobile;

import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.MyApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Intent intent;

	EditText loginEdHost;
	EditText loginEdPort;
	EditText loginEdDb;
	EditText loginEdUsr;
	EditText loginEdPwd;

	Button loginBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginEdHost = (EditText) findViewById(R.id.loginEdHost);
		loginEdPort = (EditText) findViewById(R.id.loginEdPort);
		loginEdDb = (EditText) findViewById(R.id.loginEdDb);
		loginEdUsr = (EditText) findViewById(R.id.loginEdUsr);
		loginEdPwd = (EditText) findViewById(R.id.loginEdPwd);
		loginBtn = (Button) findViewById(R.id.loginBtnLogin);

		preferences = getSharedPreferences("oe_mobile", MODE_PRIVATE);
		editor = preferences.edit();
		loginEdHost.setText(preferences.getString("loginHost", "demo.openerp.cn"));
		loginEdPort.setText(preferences.getString("loginPort", "80"));
		loginEdDb.setText(preferences.getString("loginDb", "demo1"));
		loginEdUsr.setText(preferences.getString("loginUsr", "admin"));
		loginEdPwd.setText(preferences.getString("loginPwd", "admin"));

		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// save the last input data
				editor.putString("loginHost", loginEdHost.getText().toString());
				editor.putString("loginPort", loginEdPort.getText().toString());
				editor.putString("loginDb", loginEdDb.getText().toString());
				editor.putString("loginUsr", loginEdUsr.getText().toString());
				editor.putString("loginPwd", loginEdPwd.getText().toString());
				editor.commit();

				// save the login info into the MyApp global template
				AppGlobal.setHostname(loginEdHost.getText().toString());
				AppGlobal.setPort(Integer.parseInt(loginEdPort.getText()
						.toString()));
				AppGlobal.setDbname(loginEdDb.getText().toString());
				AppGlobal.setUsername(loginEdUsr.getText().toString());
				AppGlobal.setPassword(loginEdPwd.getText().toString());

				System.out.println(AppGlobal.getHostname());
				System.out.println(AppGlobal.getPort());
				System.out.println(AppGlobal.getDbname());
				System.out.println(AppGlobal.getUsername());
				System.out.println(AppGlobal.getPassword());

				// setup the session instance in the login page
				// this session instance will be used in all pages
				// NOTICE!!! don't use session.startSession here
				// this will cause an exception since all network connections should be used in a standalone thread.
				Session s = new Session(
						OpenERPXmlRpcProxy.RPCProtocol.RPC_HTTP, AppGlobal
								.getHostname(), AppGlobal.getPort(), AppGlobal
								.getDbname(), AppGlobal.getUsername(),
						AppGlobal.getPassword());
				// set the session in the global static instance.
				AppGlobal.setOesession(s);
				
				try {
					// s.startSession();
					intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				} catch (Exception ex) {
					ex.printStackTrace();

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
}
