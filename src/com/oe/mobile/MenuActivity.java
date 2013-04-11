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

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// this is the whole menu class switcher
// all the functions will go switch here and goto the actual function page
public class MenuActivity extends Activity {

	// menu list
	String[] arr;
	// function of inventory
	String itemSearch = "物料查询", subSearch = "子库存查询", invRcv = "库存接收",
			subInfo = "库存信息";
	String[] invFunc = { itemSearch, subSearch, invRcv, subInfo };

	// function of sales
	String salesOrders = "销售订单", deliveryOrder = "发货单";
	String[] salesFunc = { salesOrders, deliveryOrder };

	// function of po
	String purchaseOrders = "采购订单", quotations = "报价单";
	String[] poFunc = { purchaseOrders, deliveryOrder };

	// function of manufacture
	String makeOrder = "工单", workOrder = "工票", moMaterialReq = "工单物料需求",
			operationTransfer = "工序转移", moInsp = "在制品检验";
	String[] manuFunc = { makeOrder, workOrder, moMaterialReq,
			operationTransfer, moInsp };

	// function of reports
	String moReport = "齐套报表";
	String[] reports = { moReport };

	// function of setup
	String users = "用户", subinventory = "仓库";
	String[] setups = { users, subinventory };

	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		if (getIntent().getExtras().getString("menuname").equals("inventory")) {
			arr = invFunc;
		} else if (getIntent().getExtras().getString("menuname").equals("sale")) {
			arr = salesFunc;
		} else if (getIntent().getExtras().getString("menuname")
				.equals("purchase")) {
			arr = poFunc;
		} else if (getIntent().getExtras().getString("menuname")
				.equals("manufacture")) {
			arr = manuFunc;
		} else if (getIntent().getExtras().getString("menuname")
				.equals("reports")) {
			arr = reports;
		} else if (getIntent().getExtras().getString("menuname")
				.equals("setup")) {
			arr = setups;
		}

		ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
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
			if (lv.getItemAtPosition(arg2).toString().equals(itemSearch)) {
				System.out.println(lv.getItemAtPosition(arg2).toString());
				intent = new Intent(MenuActivity.this, ItemListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString().equals(makeOrder)) {
				System.out.println(lv.getItemAtPosition(arg2).toString());
				intent = new Intent(MenuActivity.this, JobListActivity.class);
			} else if (lv.getItemAtPosition(arg2).toString()
					.equals(purchaseOrders)) { // purchase.order
				// call the general list activity
				intent = new Intent(MenuActivity.this,
						GeneralListActivity.class);
				Bundle data = new Bundle();
				data.putString("modelName", "purchase.order");
				String[] fields = { "name", "state" };
				data.putStringArray("fields", fields);
				intent.putExtras(data);
			} else if (lv.getItemAtPosition(arg2).toString()
					.equals(salesOrders)) { // purchase.order
				// call the general list activity
				intent = new Intent(MenuActivity.this,
						GeneralListActivity.class);
				Bundle data = new Bundle();
				data.putString("modelName", "sale.order");
				String[] fields = { "name", "state" };
				data.putStringArray("fields", fields);
				intent.putExtras(data);
			} else if (lv.getItemAtPosition(arg2).toString().equals(subSearch)) { // purchase.order
				// call the general list activity
				intent = new Intent(MenuActivity.this,
						GeneralListActivity.class);
				Bundle data = new Bundle();
				data.putString("modelName", "stock.inventory");
				String[] fields = { "name", "state" };
				data.putStringArray("fields", fields);
				intent.putExtras(data);
			}

			if (!(intent == null)) {
				startActivity(intent);
			}
		}
	}
}
