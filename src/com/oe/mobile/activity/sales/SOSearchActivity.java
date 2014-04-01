package com.oe.mobile.activity.sales;

import java.util.HashMap;

import com.oe.mobile.MenuActivity;
import com.oe.mobile.R;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.activity.purchase.POListActivity;
import com.oe.mobile.activity.purchase.POSearchActivity;
import com.oe.mobile.activity.sales.SOListActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SOSearchActivity extends Activity {

	Button btnSoSearch;
	Button btnSoSearchClear;

	EditText so_number;
	EditText customer;
	String status = "";
	Spinner statusSpin;
	ArrayAdapter<String> statusAdapter;
	static final String[] statusArr = { "draft", "progress", "cancel" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_so_search);
		btnSoSearch = (Button) findViewById(R.id.btn_so_search);
		btnSoSearchClear = (Button) findViewById(R.id.btn_so_search_clear);

		so_number = (EditText) findViewById(R.id.so_search_number);
		customer = (EditText) findViewById(R.id.so_search_customer);

		statusSpin = (Spinner) findViewById(R.id.so_search_status_spinner);
		statusAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, statusArr);
		statusAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将适配器添加到spinner中去
		statusSpin.setAdapter(statusAdapter);

		statusSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				status = ((TextView) arg1).getText().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnSoSearch.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				HashMap<String, String> params = new HashMap<String, String>();

				params.put("soNumber", so_number.getText().toString());
				params.put("customer", customer.getText().toString());
				params.put("status", status);

				Intent intent = new Intent(SOSearchActivity.this,
						SOListActivity.class);
				intent.putExtra("params", params);
				startActivity(intent);
			}
		});

		btnSoSearchClear.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				so_number.setText("");
				customer.setText("");
				statusSpin.setSelection(0);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_po_search, menu);
		return true;
	}
}
