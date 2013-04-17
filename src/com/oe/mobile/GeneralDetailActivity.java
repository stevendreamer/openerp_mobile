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
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.model.Attribute;
import com.oe.mobile.model.Model;
import com.oe.mobile.model.ModelView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GeneralDetailActivity extends Activity {

	private String modelName;
	private String[] fields;

	LinearLayout linear;
	ListView detaillist;
	ProgressDialog dialog;
	Handler handler;
	int listItemId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		modelName = getIntent().getExtras().getString("modelName");
		listItemId = (Integer) getIntent().getExtras().getInt("listItemId");
		linear = (LinearLayout) findViewById(R.id.itemDetailLinear);
		// detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x112) {
					// get the search result from the msg
					ModelView mv = (ModelView) msg.obj;

					// construct the arraylist used to show on the page

					for (String k : mv.getViewMap().keySet()) {
						View v = mv.getViewMap().get(k);
						TextView tv = new TextView(getApplicationContext());
						tv.setText(k);
						linear.addView(tv);
						linear.addView(v);
					}
					/*
					 * ArrayList<Attribute> attList = model.getModelAtt(); for
					 * (Attribute att : attList) {
					 * linear.addView(att.getAttName());
					 * linear.addView(att.getAttView()); }
					 */

					dialog.dismiss();
				}
			}
		};

		try {
			new Thread(new ItemDetailThread(handler, getApplicationContext(),
					modelName, listItemId)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item_detail, menu);
		return true;
	}
}
