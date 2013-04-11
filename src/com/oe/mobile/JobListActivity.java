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
package com.oe.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

// try to change the joblist activity to use the general data fetch method in ItemThread.java
public class JobListActivity extends Activity {
	private int[] imageIds = new int[] { R.drawable.earphone,
			R.drawable.laptop, R.drawable.harddisk, R.drawable.monitor };
	private String[] names = new String[] { "耳机", "笔记本", "硬盘", "显示器" };
	private String[] infos = new String[] { "￥10", "￥12", "￥14", "￥15" };

	private String modelName;
	private String[] fields;
	private FilterCollection filters;

	List<Map<String, Object>> listItems;
	Handler handler;

	ProgressDialog dialog;

	LinearLayout linear;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		// setup the model and search filters in hardcode to test
		// will delete later
		modelName = "mrp.production";
		String[] fields = { "name", "state", "product" };
		filters = new FilterCollection();

		// listitems is used to setup the filter
		listItems = new ArrayList<Map<String, Object>>();
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x111) {
					// get the search result from the msg
					RowCollection rc = (RowCollection) msg.obj;

					// construct the arraylist used to show on the page

					for (Row r : rc) {
						Map<String, Object> listItem = new HashMap<String, Object>();

						listItem.put("header", R.drawable.nopic);
						listItem.put("personName", r.get("name"));
						listItem.put("info", r.get("state"));
						listItems.add(listItem);
					}

					setPageView();
					dialog.dismiss();
				}
			}
		};

		try {
			// test the new general method
			new Thread(new ItemThread(handler, modelName, fields, filters))
					.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setPageView() {

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.item_list, new String[] { "personName", "header",
						"info" },
				new int[] { R.id.name, R.id.header, R.id.info });
		ListView list = (ListView) findViewById(R.id.itemlist);
		list.setAdapter(simpleAdapter);
		System.out.println("zzyan:finished page setup");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item_list, menu);
		return true;
	}
}
