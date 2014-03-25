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
import com.oe.mobile.retired.Model;

import android.os.Handler;
import android.os.Message;

// this is the datathread mainly used for row collection return
public class DataThread implements Runnable {
	private Handler handler;
	private String method;

	// add to test the general fetch function
	private String modelName;
	private String[] fields;
	private FilterCollection filters;

	public DataThread(Handler handler, String modelName, String[] fields,
			FilterCollection filters) throws IOException {

		method = "general";

		this.handler = handler;
		this.modelName = modelName;
		this.fields = fields;
		this.filters = filters;
	}

	public void run() {
		RowCollection rc = null;
		try {

			rc = getRowCollection(modelName, fields, filters);

			// create a msg and send back to handler
			Message msg = new Message();
			msg.what = Lookup.MsgItemList;
			msg.obj = rc;
			handler.sendMessage(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// define a general select function
	public RowCollection getRowCollection(String modelName, String[] fields,
			FilterCollection filters) throws Exception {

		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, modelName);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
}
