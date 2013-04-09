package com.oe.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.oe.mobile.model.Attribute;
import com.oe.mobile.model.Model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;


public class ItemDetailActivity extends Activity {

	LinearLayout linear;
	ListView detaillist;
	ProgressDialog dialog;
	Handler handler;
	int productId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		productId = (Integer)getIntent().getExtras().getInt("productId");
		linear = (LinearLayout) findViewById(R.id.itemDetailLinear);
		//detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x112) {
					// get the search result from the msg
					Model model = (Model) msg.obj;

					// construct the arraylist used to show on the page

					ArrayList<Attribute> attList = model.getModelAtt();
					for (Attribute att : attList) {
						linear.addView(att.getAttName());
						linear.addView(att.getAttView());
					}

					dialog.dismiss();
				}
			}
		};

		try {
			new Thread(new ItemDetailThread(handler, getApplicationContext(),productId))
					.start();
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
