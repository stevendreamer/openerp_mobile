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

import com.oe.mobile.activity.mrp.JobListActivity;
import com.oe.mobile.activity.stock.ItemListActivity;
import com.oe.mobile.activity.purchase.POListActivity;
import com.oe.mobile.activity.purchase.POSearchActivity;
import com.oe.mobile.activity.sales.LeadListActivity;
import com.oe.mobile.activity.sales.CustomerListActivity;
import com.oe.mobile.activity.stock.StockInListActivity;
import com.oe.mobile.activity.sales.SOListActivity;
import com.oe.mobile.activity.sales.SOSearchActivity;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// this is the whole menu class switcher
// all the functions will go switch here and goto the actual function page
public class MenuActivity extends Activity {

	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		// construct the menuArray
		String menuName = getIntent().getExtras().getString("menuName");
		ArrayList<String> menuList = AppGlobal.getMenu()
				.constructMenu(menuName);
		String[] menuArray = trans(menuList);

		ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuArray);
		lv = (ListView) findViewById(R.id.menuList);
		lv.setAdapter(adap);
		lv.setOnItemClickListener(new ItemClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("zzyan index:" + arg2 + " name:"
					+ (String) lv.getItemAtPosition(arg2));
			Intent intent = null;

			// this is the menu action switcher
			// if we have new menu actions, we need to add the function here
			if (lv.getItemAtPosition(arg2).toString().equals("物料查询")) {
				intent = new Intent(MenuActivity.this, ItemListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("工单")) {
				intent = new Intent(MenuActivity.this, JobListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("采购订单")) {
				intent = new Intent(MenuActivity.this, POSearchActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("线索")) {
				intent = new Intent(MenuActivity.this, LeadListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("客户信息")) {
				intent = new Intent(MenuActivity.this,
						CustomerListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("入库单")) {
				intent = new Intent(MenuActivity.this,
						StockInListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("销售订单")) {
				intent = new Intent(MenuActivity.this, SOSearchActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals("ScanTest")) {
				intent = new Intent(MenuActivity.this, CaptureActivity.class);
			}

			if (!(intent == null)) {
				startActivity(intent);
			}
		}
	}

	String[] trans(ArrayList<String> als) {
		String[] sa = new String[als.size()];
		als.toArray(sa);
		return sa;
	}
}
