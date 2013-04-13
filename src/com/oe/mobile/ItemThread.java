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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.FieldCollection;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.model.Model;

import android.os.Handler;
import android.os.Message;

// this is a thread class used to do the searching for model data
// using a thread, because android don't allow us to directly 
// access the internet(fetch data) inside the main UI thread
// this will ends by sending a msg to a handler of the main UI thread
// which will handle the UI re-draw
public class ItemThread implements Runnable {
	private Handler handler;
	private String method;

	// add to test the general fetch function
	private String modelName;
	private String[] fields;
	private FilterCollection filters;

	public ItemThread(Handler handler, String method) throws IOException {
		this.handler = handler;
		this.method = method;
	}

	public ItemThread(Handler handler, String modelName, String[] fields,
			FilterCollection filters) {
		// will delete later after the general test;
		method = "general";

		this.handler = handler;
		this.modelName = modelName;
		this.fields = fields;
		this.filters = filters;
	}

	public void run() {
		RowCollection rc = null;
		ArrayList<Model> modelList = null;
		try {
			/*
			if (method.equals("getItems")) {
				rc = getItems();
			} else if (method.equals("getJobs")) {
				rc = getJobs();
			} else if (method.equals("general")) {
				modelList = getRows(modelName, fields, filters);
			}*/
			
			modelList = getRows(modelName, fields, filters);

			Message msg = new Message();
			msg.what = 0x111;
			/*
			if (method.equals("general"))
				msg.obj = modelList;
			else
				msg.obj = rc;*/
			msg.obj = modelList;
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
	public ArrayList<Model> getRows(String modelName, String[] fields,
			FilterCollection filters) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, modelName);
		FieldCollection fc = oa.getFields(fields);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		ArrayList<Model> mList = Model.parseRow(modelName, fc, rc);
		return mList;
	}
}
