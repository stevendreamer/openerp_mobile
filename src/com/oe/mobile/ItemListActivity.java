package com.oe.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemListActivity extends Activity {
	private int[] imageIds = new int[] { R.drawable.earphone,
			R.drawable.laptop, R.drawable.harddisk, R.drawable.monitor };
	private String[] names = new String[] { "����", "�ʼǱ�", "Ӳ��", "��ʾ��" };
	private String[] infos = new String[] { "��10", "��12", "��14", "��15" };

	MyApp app;
	List<Map<String, Object>> listItems;
	Handler handler;
	ListView list;

	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		list = (ListView) findViewById(R.id.itemlist);

		listItems = new ArrayList<Map<String, Object>>();
		// static list view
		/*
		 * for (int i = 0; i < names.length; i++) { Map<String, Object> listItem
		 * = new HashMap<String, Object>(); listItem.put("header", imageIds[i]);
		 * listItem.put("personName", names[i]); listItem.put("info", infos[i]);
		 * listItems.add(listItem); }
		 * 
		 * setPageView();
		 */
		dialog = ProgressDialog.show(this, "", "�������ݣ����Ե� ��", true, true);
		list.setOnItemClickListener(new ItemClickListener());
		System.out.println("item clicker is set");

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
						//listItem.put("header", r.get("image_medium"));
						listItem.put("personName", r.get("name_template"));
						listItem.put("info", r.get("lst_price"));
						listItem.put("itemListId", r.get("id"));
						listItems.add(listItem);
					}

					setPageView();
					dialog.dismiss();
				}
			}
		};

		try {
			new Thread(new ItemThread(handler, "getItems")).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setPageView() {

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.item_list, new String[] { "personName", "header",
						"info", "itemListId" }, new int[] { R.id.name,
						R.id.header, R.id.info, R.id.itemListId });
		list.setAdapter(simpleAdapter);
		System.out.println("zzyan:finished page setup");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item_list, menu);
		return true;
	}

	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("this is in the clicker");
			// get the item id of the list, and goto the item detail page
			// to show the item detail information.

			System.out.println("zzyan inside list click trigger:" + " name:"
					+ ((HashMap)list.getItemAtPosition(arg2)).get("itemListId"));
			// parse the id of the item
			HashMap h = (HashMap)list.getItemAtPosition(arg2);
			int id = (Integer)h.get("itemListId");
			System.out.println("end of clicker");
			Intent intent = new Intent(ItemListActivity.this,
					ItemDetailActivity.class);
			intent.putExtra("productId", id);
			startActivity(intent);

			// this is the menu action switcher
			// if we have new menu actions, we need to add the function here
			/*
			 * if (lv.getItemAtPosition(arg2).toString().equals(itemSearch)) {
			 * System.out.println(lv.getItemAtPosition(arg2).toString()); intent
			 * = new Intent(MenuActivity.this, ItemListActivity.class); } else
			 * if (lv.getItemAtPosition(arg2).toString().equals(makeOrder)) {
			 * System.out.println(lv.getItemAtPosition(arg2).toString()); intent
			 * = new Intent(MenuActivity.this, JobListActivity.class); }
			 * 
			 * if (!(intent == null)) { startActivity(intent); }
			 */
		}
	}
}
