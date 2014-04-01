package com.oe.mobile.activity.purchase;

import java.util.HashMap;

import com.oe.mobile.MenuActivity;
import com.oe.mobile.R;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.activity.sales.SOListActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class POSearchActivity extends Activity {

	Button btnPoSearch;
	Button btnPoSearchClear;

	EditText po_number;
	EditText supplier;
	EditText origin;
	String status;
	Spinner statusSpin;
	ArrayAdapter<String> statusAdapter;
	static final String[] statusArr = { "draft", "approved", "cancel" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_po_search);
		btnPoSearch = (Button) findViewById(R.id.btn_po_search);
		btnPoSearchClear = (Button) findViewById(R.id.btn_po_search_clear);

		po_number = (EditText) findViewById(R.id.po_search_number);
		supplier = (EditText) findViewById(R.id.po_search_supplier);
		origin = (EditText) findViewById(R.id.po_search_origin);
		statusSpin = (Spinner) findViewById(R.id.po_search_status_spinner);
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

		btnPoSearch.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, String> params = new HashMap<String, String>();

				params.put("poNumber", po_number.getText().toString());
				params.put("supplier", supplier.getText().toString());
				params.put("origin", origin.getText().toString());
				params.put("status", status);

				Intent intent = new Intent(POSearchActivity.this,
						POListActivity.class);
				intent.putExtra("params", params);
				startActivity(intent);
			}
		});

		btnPoSearchClear.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				po_number.setText("");
				supplier.setText("");
				origin.setText("");
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
