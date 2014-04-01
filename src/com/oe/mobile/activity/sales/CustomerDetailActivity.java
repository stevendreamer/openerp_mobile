/*
 * Copyright (C) 2013  stevendreamer (in github)
 * Project Location: https://github.com/stevendreamer/openerp_mobile

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * Addition: any copy of this program should keep the author name info.
 * any copy without the author info will be a pirate

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.oe.mobile.activity.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.R;
import com.oe.mobile.R.id;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.retired.Attribute;
import com.oe.mobile.retired.ItemDetailThread;
import com.oe.mobile.retired.Model;
import com.oe.mobile.service.Stock;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerDetailActivity extends Activity {

	ListView detaillist;
	ProgressDialog dialog;
	Handler handler;
	int customerId;

	Button callPhoneBtn;
	Button callMobileBtn;
	TextView phone;
	TextView mobile;
	MyTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_detail);
		customerId = (Integer) getIntent().getExtras().getInt("ct_id");

		// detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		callPhoneBtn = (Button) findViewById(R.id.customerDetailCallPhone);
		callMobileBtn = (Button) findViewById(R.id.customerDetailCallMobile);
		phone = (TextView) findViewById(R.id.customerDetailPhone);
		mobile = (TextView) findViewById(R.id.customerDetailMobile);
		callPhoneBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 取得输入的电话号码串
				String inputStr = phone.getText().toString();
				// 如果输入不为空创建打电话的Intent
				if (inputStr.trim().length() != 0) {
					Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + inputStr));
					// 启动
					startActivity(phoneIntent);
				}
				// 否则Toast提示一下
				else {
					Toast.makeText(CustomerDetailActivity.this, "电话号码为空",
							Toast.LENGTH_LONG).show();
				}

			}

		});
		
		callMobileBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 取得输入的电话号码串
				String inputStr = mobile.getText().toString();
				// 如果输入不为空创建打电话的Intent
				if (inputStr.trim().length() != 0) {
					Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + inputStr));
					// 启动
					startActivity(phoneIntent);
				}
				// 否则Toast提示一下
				else {
					Toast.makeText(CustomerDetailActivity.this, "电话号码为空",
							Toast.LENGTH_LONG).show();
				}

			}

		});

		task = new MyTask();
		task.execute(customerId);
	}

	private class MyTask extends AsyncTask<Integer, Integer, Row> {

		@Override
		protected void onPreExecute() {
			Log.i("ItemListPage", "onPreExecute() called");
			// dialog.show();
		}

		@Override
		protected Row doInBackground(Integer... params) {
			Row result = null;
			try {
				result = Stock.getCustomerById(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {

		}

		@Override
		protected void onPostExecute(Row rc) {

			setPageView(rc);
			dialog.dismiss();

		}

	}

	public void setPageView(Row r) {
		TextView customerDetailName = (TextView) findViewById(R.id.customerDetailName);
		TextView customerDetailPhone = (TextView) findViewById(R.id.customerDetailPhone);
		TextView customerDetailEmail = (TextView) findViewById(R.id.customerDetailEmail);
		TextView customerDetailMobile = (TextView) findViewById(R.id.customerDetailMobile);

		if (r.get("name") != null)
			customerDetailName.setText(r.get("name").toString());
		if (r.get("phone") != null)
			customerDetailPhone.setText(r.get("phone").toString());
		if (r.get("mobile") != null)
			customerDetailMobile.setText(r.get("mobile").toString());
		if (r.get("email") != null)
			customerDetailEmail.setText(r.get("email").toString());

	}
}
