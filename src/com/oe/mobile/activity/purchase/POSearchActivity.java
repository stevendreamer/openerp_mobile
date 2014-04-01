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
import android.widget.Button;
import android.widget.EditText;

public class POSearchActivity extends Activity {

	Button btnPoSearch;
	Button btnPoSearchClear;

	EditText po_number;
	EditText customer;
	EditText origin;
	EditText item_number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_po_search);
		btnPoSearch = (Button) findViewById(R.id.btn_po_search);
		btnPoSearchClear = (Button) findViewById(R.id.btn_po_search_clear);

		po_number = (EditText) findViewById(R.id.po_search_number);
		customer = (EditText) findViewById(R.id.po_search_customer);
		origin = (EditText) findViewById(R.id.po_search_origin);
		item_number = (EditText) findViewById(R.id.po_search_item);

		btnPoSearch.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, String> params = new HashMap<String, String>();

				params.put("poNumber", po_number.getText().toString());
				params.put("customer", customer.getText().toString());
				params.put("origin", origin.getText().toString());
				params.put("item_number", item_number.getText().toString());

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
