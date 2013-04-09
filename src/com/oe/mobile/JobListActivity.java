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
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JobListActivity extends Activity {
	private int[] imageIds = new int[] { R.drawable.earphone,
			R.drawable.laptop, R.drawable.harddisk, R.drawable.monitor };
	private String[] names = new String[] { "耳机", "笔记本", "硬盘", "显示器" };
	private String[] infos = new String[] { "￥10", "￥12", "￥14", "￥15" };

	List<Map<String, Object>> listItems;
	Handler handler;

	ProgressDialog dialog;
	
	LinearLayout linear;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
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
			new Thread(new ItemThread(handler, "getJobs")).start();
		} catch (IOException e) {
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
