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
	String itemSearch = "���ϲ�ѯ", subSearch = "�ӿ���ѯ", invRcv = "������",
			subInfo = "�����Ϣ";
	String[] invFunc = { itemSearch, subSearch, invRcv, subInfo };

	// function of sales
	String salesOrders = "���۶���", deliveryOrder = "������";
	String[] salesFunc = { salesOrders, deliveryOrder };

	// function of po
	String purchaseOrders = "�ɹ�����", quotations = "���۵�";
	String[] poFunc = { purchaseOrders, deliveryOrder };

	// function of manufacture
	String makeOrder = "����", workOrder = "��Ʊ", moMaterialReq = "������������",
			operationTransfer = "����ת��", moInsp = "����Ʒ����";
	String[] manuFunc = { makeOrder, workOrder, moMaterialReq,
			operationTransfer, moInsp };

	// function of reports
	String moReport = "���ױ���";
	String[] reports = { moReport };

	// function of setup
	String users = "�û�", subinventory = "�ֿ�";
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
			}

			if (!(intent == null)) {
				startActivity(intent);
			}
		}
	}
}
