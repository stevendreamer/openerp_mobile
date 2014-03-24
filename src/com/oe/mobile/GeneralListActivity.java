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

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.ItemListActivity.ItemClickListener;
import com.oe.mobile.model.Model;
import com.oe.mobile.retired.ItemThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

// try to change the joblist activity to use the general data fetch method in ItemThread.java
public class GeneralListActivity extends Activity {

	private String modelName;
	private String[] fields;
	private FilterCollection filters;
	private TextView col1;
	private TextView col2;
	private TextView col3;
	private TextView col4;

	List<Map<String, Object>> listItems;
	Handler handler;

	ProgressDialog dialog;

	LinearLayout linear;
	ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		// setup the model and search filters in hardcode to test
		// will delete later
		Bundle bundle = getIntent().getExtras();
		modelName = bundle.getString("modelName");
		fields = bundle.getStringArray("fields");
		// set the textview name of the fields
		if (fields.length == 3) {
			col1 = (TextView) findViewById(R.id.col3_1);
			col2 = (TextView) findViewById(R.id.col3_2);
			col3 = (TextView) findViewById(R.id.col3_3);
			col1.setText(fields[0]);
			col2.setText(fields[1]);
			col3.setText(fields[2]);
		} else if (fields.length == 4) {
			col1 = (TextView) findViewById(R.id.col4_1);
			col2 = (TextView) findViewById(R.id.col4_2);
			col3 = (TextView) findViewById(R.id.col4_3);
			col4 = (TextView) findViewById(R.id.col4_4);
			col1.setText(fields[0]);
			col2.setText(fields[1]);
			col3.setText(fields[2]);
			col4.setText(fields[3]);
		}
		filters = new FilterCollection();

		list = (ListView) findViewById(R.id.itemlist);

		// listitems is used to setup the filter
		listItems = new ArrayList<Map<String, Object>>();
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		// setup the list clicker
		list.setOnItemClickListener(new ListItemClickListener());

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x111) {
					// get the search result from the msg
					// RowCollection rc = (RowCollection) msg.obj;
					ArrayList<Model> modelList = (ArrayList<Model>) msg.obj;

					if (modelList == null) {
						System.out.println("this is null");
					}

					for (Model m : modelList) {
						Map<String, Object> listItem = new HashMap<String, Object>();

						;
						if (fields.length == 3) {
							listItem.put("listItemId",
									m.getAttributes().get("id").toString());
							listItem.put("col1",
									m.getAttributes().get(fields[0]).toString());
							listItem.put("col2",
									m.getAttributes().get(fields[1]).toString());
							listItem.put("col3",
									m.getAttributes().get(fields[2]).toString());
						} else if (fields.length == 4) {
							listItem.put("col1",
									m.getAttributes().get(fields[0]).toString());
							listItem.put("col2",
									m.getAttributes().get(fields[1]).toString());
							listItem.put("col3",
									m.getAttributes().get(fields[2]).toString());
							listItem.put("col4",
									m.getAttributes().get(fields[3]).toString());
						}

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
		SimpleAdapter simpleAdapter = null;
		if (fields.length == 3) {
			// this listview contains 3 columns
			simpleAdapter = new SimpleAdapter(this, listItems,
					R.layout.col3_list, new String[] { "listItemId", "col1",
							"col2", "col3" }, new int[] { R.id.listItemId,
							R.id.col3_1, R.id.col3_2, R.id.col3_3 });
		} else if (fields.length == 4) {
			// this listview contains 4 columns
			simpleAdapter = new SimpleAdapter(this, listItems,
					R.layout.col4_list, new String[] { "col1", "col2", "col3",
							"col4" }, new int[] { R.id.col4_1, R.id.col4_2,
							R.id.col4_3, R.id.col4_4 });
		}

		list.setAdapter(simpleAdapter);
		System.out.println("zzyan:finished page setup");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item_list, menu);
		return true;
	}

	class ListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("this is in the clicker");
			// get the item id of the list, and goto the item detail page
			// to show the item detail information.

			System.out.println("zzyan inside list click trigger:"
					+ " name:"
					+ ((HashMap) list.getItemAtPosition(arg2))
							.get("listItemId"));
			// parse the id of the item
			HashMap h = (HashMap) list.getItemAtPosition(arg2);
			int id = Integer.parseInt((String) h.get("listItemId"));
			System.out.println("end of clicker");
			Intent intent = new Intent(GeneralListActivity.this,
					GeneralDetailActivity.class);
			intent.putExtra("modelName", modelName);
			intent.putExtra("listItemId", id);
			startActivity(intent);

		}
	}
	
	
	
}
