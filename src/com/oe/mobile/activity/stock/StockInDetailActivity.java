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
package com.oe.mobile.activity.stock;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StockInDetailActivity extends Activity {

	ListView detaillist;

	ProgressDialog dialog;
	Handler handler;
	int stockInId;

	MyTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stockin_detail);
		stockInId = (Integer) getIntent().getExtras().getInt("stockinId");

		detaillist = (ListView) findViewById(R.id.stockInDetailList);

		// detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		task = new MyTask();
		task.execute(stockInId);
	}

	private class MyTask extends AsyncTask<Integer, Integer, HashMap> {

		@Override
		protected void onPreExecute() {
			Log.i("StockInDetailPage", "onPreExecute() called");
			// dialog.show();
		}

		@Override
		protected HashMap doInBackground(Integer... params) {
			HashMap<String, Object> result = new HashMap<String, Object>();
			Row header = null;
			RowCollection lines = null;
			try {
				header = Stock.getStockInById(params[0]);
				lines = Stock.getStockInLines(params[0]);
				result.put("header", header);
				result.put("lines", lines);
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
		protected void onPostExecute(HashMap result) {

			setPageView(result);
			dialog.dismiss();

		}

	}

	public void setPageView(HashMap result) {

		Row header = (Row) result.get("header");
		RowCollection lines = (RowCollection) result.get("lines");

		TextView detailNumber = (TextView) findViewById(R.id.stockInDetailNumber);
		TextView detailCustomer = (TextView) findViewById(R.id.stockInDetailCustomer);
		TextView detailOrigin = (TextView) findViewById(R.id.stockInDetailOrigin);
		TextView detailStatus = (TextView) findViewById(R.id.stockInDetailStatus);

		if (header.get("name") != null)
			detailNumber.setText(header.get("name").toString());
		if (header.get("partner_id") != null)
			detailCustomer.setText(((Object[]) header.get("partner_id"))[1].toString());
		if (header.get("origin") != null)
			detailOrigin.setText(header.get("origin").toString());
		if (header.get("state") != null)
			detailStatus.setText(header.get("state").toString());

		ArrayList<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (Row r : lines) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			// "name", "state", "origin"
			if (r.get("product_id") != null)
				listItem.put("product_id",
						((Object[]) r.get("product_id"))[1].toString());
			listItem.put("product_qty", r.get("product_qty"));
			listItem.put("state", r.get("state"));
			listItem.put("id", r.get("id"));
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.stockin_line_list, new String[] { "product_id",
						"product_qty", "state", "id" }, new int[] {
						R.id.stockin_line_product_id,
						R.id.stockin_line_product_qty, R.id.stockin_line_state,
						R.id.stockin_line_id });
		detaillist.setAdapter(simpleAdapter);

	}

}
