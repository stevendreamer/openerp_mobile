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
import android.widget.Button;
import android.widget.EditText;

public class SOSearchActivity extends Activity {

	Button btnSoSearch;
	Button btnSoSearchClear;

	EditText so_number;
	EditText customer;
	EditText origin;
	EditText item_number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_so_search);
		btnSoSearch = (Button) findViewById(R.id.btn_so_search);
		btnSoSearchClear = (Button) findViewById(R.id.btn_so_search_clear);

		so_number = (EditText) findViewById(R.id.so_search_number);
		customer = (EditText) findViewById(R.id.so_search_customer);
		origin = (EditText) findViewById(R.id.so_search_origin);
		item_number = (EditText) findViewById(R.id.so_search_item);

		btnSoSearch.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				HashMap<String, String> params = new HashMap<String, String>();

				params.put("soNumber", so_number.getText().toString());
				params.put("customer", customer.getText().toString());
				params.put("origin", origin.getText().toString());
				params.put("item_number", item_number.getText().toString());

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
				origin.setText("");
				item_number.setText("");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_po_search, menu);
		return true;
	}
}
