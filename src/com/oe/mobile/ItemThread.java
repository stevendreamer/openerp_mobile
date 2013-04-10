package com.oe.mobile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

import android.os.Handler;
import android.os.Message;

public class ItemThread implements Runnable {
	private Handler handler;
	private String method;

	public ItemThread(Handler handler, String method) throws IOException {
		this.handler = handler;
		this.method = method;
	}

	public void run() {
		RowCollection rc = null;
		try {
			if (method.equals("getItems")) {
				rc = getItems();
			} else if (method.equals("getJobs")) {
				rc = getJobs();
			}

			Message msg = new Message();
			msg.what = 0x111;
			msg.obj = rc;
			handler.sendMessage(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public RowCollection getItems() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		/*
		 * Session s = new Session(OpenERPXmlRpcProxy.RPCProtocol.RPC_HTTP,
		 * "192.168.2.100", 8069, "openerp", "admin", "000000");
		 */
		Session s = AppGlobal.getOesession();

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "product.product");

		String[] fields = { "name_template", "qty_available", "lst_price" };

		FilterCollection filters = new FilterCollection();
		// filters.add("id", "<", 10);
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public RowCollection getJobs() throws Exception {
		/*
		 * Session s = new Session(OpenERPXmlRpcProxy.RPCProtocol.RPC_HTTP,
		 * "192.168.2.101", 8069, "openerp", "admin", "000000");
		 */
		Session s = AppGlobal.getOesession();

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "mrp.production");
		String[] fields = { "name", "state", "product" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "<", 10);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public RowCollection getPOs() throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order");
		String[] fields = { "name", "state", "origin" };

		FilterCollection filters = new FilterCollection();

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
	
	// general perpose executer for all models
	// this function is a general search executer for all models
	// @modelName: the name of the model that's going to search
	// @fields: the fields that's going to returned in the page
	// @filters: the filters that will be applied as the search criteria
	public RowCollection getRows(String modelName, String[] fields, FilterCollection filters) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, modelName);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
}
