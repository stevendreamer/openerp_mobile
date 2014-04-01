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
package com.oe.mobile.activity.mrp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.MyApp;
import com.oe.mobile.R;
import com.oe.mobile.R.id;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.retired.ItemThread;
import com.oe.mobile.service.Stock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

// try to change the joblist activity to use the general data fetch method in ItemThread.java
public class WOListActivity extends Activity {

	MyApp app;
	List<Map<String, Object>> listItems;
	Handler handler;
	ListView list;
	MyTask mTask;

	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wo_list);

		// listitems is used to setup the filter
		listItems = new ArrayList<Map<String, Object>>();
		list = (ListView) findViewById(R.id.wolist);

		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		// call the asynchronized task
		mTask = new MyTask();
		mTask.execute();

	}

	public void setPageView(RowCollection rc) {

		// construct the arraylist used to show on the page
		for (Row r : rc) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			// "name", "production_id", "workcenter_id", "state"
			listItem.put("name", r.get("name"));

			if (r.get("production_id") != null)
				listItem.put("production_id",
						((Object[]) r.get("production_id"))[1].toString());
			else
				listItem.put("production_id", "");

			if (r.get("workcenter_id") != null)
				listItem.put("workcenter_id",
						((Object[]) r.get("workcenter_id"))[1].toString());
			else
				listItem.put("workcenter_id", "");

			listItem.put("state", r.get("state"));

			listItem.put("woId", r.get("id"));
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.wo_list, new String[] { "name", "production_id",
						"workcenter_id", "state", "woId" }, new int[] {
						R.id.wo_name, R.id.wo_production_id,
						R.id.wo_workcenter_id, R.id.wo_state, R.id.wo_Id });
		list.setAdapter(simpleAdapter);
	}

	private class MyTask extends AsyncTask<String, Integer, RowCollection> {

		@Override
		protected void onPreExecute() {
			Log.i("WOListPage", "onPreExecute() called");

		}

		@Override
		protected RowCollection doInBackground(String... params) {
			RowCollection result = null;
			try {
				result = Stock.getWorkOrders();
				Log.i("JOB", "after getting jobs from server");
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
		protected void onPostExecute(RowCollection rc) {

			setPageView(rc);
			dialog.dismiss();

		}
	}
}
