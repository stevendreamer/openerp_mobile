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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

public class MainActivity extends Activity {

	private static final String TAG = "==CrazyIt.org==";

	// this is the images used in the main page.
	int[] imageIds = new int[] { R.drawable.message, R.drawable.crm,
			R.drawable.inventory, R.drawable.po, R.drawable.sales,
			R.drawable.mo, R.drawable.reports, R.drawable.reports2,
			R.drawable.barcode_scanner, R.drawable.nopic };
	// this is the descriptions used in the main page, under the descriptions
	String message = "Message", CRM = "CRM", inventory = "库存", purchase = "采购",
			sales = "销售", manufacture = "生产", reports = "报表",
			chartTest = "ChartTest", barcodeTest = "BarcodeTest",
			bitmapTest = "测试图片";

	String[] descs = new String[] { message, CRM, inventory, purchase, sales,
			manufacture, reports, chartTest, barcodeTest, bitmapTest };

	// String[] descs = new String[] { "库存", "销售", "采购", "生产", "报表", "设置",
	// "AChart", "Barcode" };
	// this is the activity names, when user click on the image,
	// based on the following activity name, we'l goto the according activity
	// page.
	// String[] activityNames = new String[] { "A", "B", "C", "D", "E", "F",
	// "G",
	// "H" };

	ArrayList<HashMap<String, Object>> lstImageItems = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// have 9 activity icons
		for (int i = 0; i < descs.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageIds[i]);
			map.put("itemText", descs[i]);
			// map.put("nextActivityName", activityNames[i]);

			lstImageItems.add(map);
		}

		SimpleAdapter sa = new SimpleAdapter(this, lstImageItems,
				R.layout.grid_items, new String[] { "itemImage", "itemText" },
				new int[] { R.id.itemImage, R.id.itemText });

		GridView grid = (GridView) findViewById(R.id.main_grid);
		grid.setAdapter(sa);
		grid.setOnItemClickListener(new ItemClickListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Log.i("ZZYAN", "INSIDE ONITEMCLICK");
			// TODO Auto-generated method stub
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			String itemText = (String) item.get("itemText");
			Log.i("ZZYAN", "itemText:" + itemText);
			Intent intent = null;

			if (itemText.equals(message)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "message");
			}
			if (itemText.equals(CRM)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "crm");
			}
			if (itemText.equals(inventory)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "inventory");
			}
			if (itemText.equals(sales)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "sale");
			}
			if (itemText.equals(purchase)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "purchase");
			}
			if (itemText.equals(manufacture)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "manufacture");
			}
			if (itemText.equals(reports)) {
				intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("menuname", "reports");
			}
			if (itemText.equals(chartTest)) {
				intent = new Intent(MainActivity.this, AChartActivity.class);
				intent.putExtra("menuname", "setup");
			}
			if (itemText.equals(barcodeTest)) {
				// intent = new Intent(MainActivity.this,
				// CaptureActivity.class);
				intent = new Intent(MainActivity.this,
						BarcodeTestActivity.class);
				intent.putExtra("menuname", "setup");
			}
			if (itemText.equals(bitmapTest)) {
				intent = new Intent(MainActivity.this, BitmapActivity.class);
			}
			if (intent != null) {
				startActivity(intent);
			}
		}
	}
}
