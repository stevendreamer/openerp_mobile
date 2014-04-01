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
package com.oe.mobile.activity.purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Field;
import com.oe.mobile.R;
import com.oe.mobile.R.id;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.retired.Attribute;
import com.oe.mobile.retired.ItemDetailThread;
import com.oe.mobile.retired.Model;
import com.oe.mobile.service.Stock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PODetailActivity extends Activity {

	ListView detaillist;
	ListView lineList;
	ProgressDialog dialog;
	Handler handler;
	int poId;

	ArrayList<Map<String, Object>> listItems;

	MyTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_po_detail);

		listItems = new ArrayList<Map<String, Object>>();

		poId = (Integer) getIntent().getExtras().getInt("po_id");

		// detaillist = (ListView) findViewById(R.id.poDetailList);

		lineList = (ListView) findViewById(R.id.poLinesDetailList);

		// detaillist = (ListView)findViewById(R.id.itemDetaillist);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		task = new MyTask();
		task.execute(poId);
	}

	private class MyTask extends AsyncTask<Integer, Integer, PODetail> {

		@Override
		protected void onPreExecute() {
			Log.i("PODetailPage", "onPreExecute() called");
			// dialog.show();
		}

		@Override
		protected PODetail doInBackground(Integer... params) {
			PODetail result = new PODetail();
			Row poHeader = null;
			RowCollection poLines = null;
			/*
			 * rs.put("PO单据号", r0.get("name")); rs.put("状态", r0.get("state"));
			 * rs.put("来源单据", r0.get("origin"));
			 */
			try {
				poHeader = Stock.getPOById(params[0]);

				poLines = Stock.getPOLines(params[0]);

				for (Row r : poLines) {
					// OK, here when the object is many2one, will return
					// OBJECT[], and the id is in OBJECT[0], the name is in
					// OBJECT[1]
					Log.i("TYPE",
							((Object[]) r.get("product_id"))[0].toString());
					Log.i("TYPE",
							((Object[]) r.get("product_id"))[1].toString());

					Log.i("POLINE",
							r.get("description")
									+ ";"
									+ ((Object[]) r.get("product_id"))[1]
											.toString() + ";"
									+ r.get("product_qty"));
				}

				Log.i("PODETAIL", poLines.size() + ";");
				result.setPoHeader(poHeader);
				result.setPoLines(poLines);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {

		}

		@Override
		protected void onPostExecute(PODetail poDetail) {

			Row poHeader = poDetail.getPoHeader();

			setPageView(poDetail);
			dialog.dismiss();

		}

	}

	public void setPageView(PODetail poDetail) {
		HashMap<String, Object> rc = new HashMap<String, Object>();
		Row poHeader = poDetail.getPoHeader();

		TextView txNumber = (TextView) findViewById(R.id.poDetailNumber);
		TextView txState = (TextView) findViewById(R.id.poDetailState);
		TextView txOrigin = (TextView) findViewById(R.id.poDetailOrigin);

		if (poHeader.get("name") != null)
			txNumber.setText("采购单：  "+poHeader.get("name").toString());
		if (poHeader.get("state") != null)
			txState.setText("状态  ：  "+poHeader.get("state").toString());
		if (poHeader.get("origin") != null)
			txOrigin.setText("来源  ：  "+poHeader.get("origin").toString());

		RowCollection poLines = poDetail.getPoLines();
		// name product_qty price_subtotal
		Log.i("PODETAIL-2", poLines.size() + ";");

		for (Row r : poLines) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			// "name", "state", "origin"
			listItem.put("po_line_name", r.get("name"));
			listItem.put("po_line_product_qty", r.get("product_qty"));
			listItem.put("po_line_price_subtotal", r.get("price_subtotal"));
			listItem.put("po_line_id", r.get("id"));
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.po_line_list, new String[] { "po_line_name",
						"po_line_product_qty", "po_line_price_subtotal",
						"po_line_id" }, new int[] { R.id.po_line_name,
						R.id.po_line_product_qty, R.id.po_line_price_subtotal,
						R.id.po_line_id });
		lineList.setAdapter(simpleAdapter);

	}

	// use to simplify the AsncTask result return
	private class PODetail {
		Row poHeader;
		RowCollection poLines;

		public Row getPoHeader() {
			return poHeader;
		}

		public void setPoHeader(Row poHeader) {
			this.poHeader = poHeader;
		}

		public RowCollection getPoLines() {
			return poLines;
		}

		public void setPoLines(RowCollection poLines) {
			this.poLines = poLines;
		}
	}
}
