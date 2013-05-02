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
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.model.Model;
import com.oe.mobile.model.ModelFactory;
import com.oe.mobile.model.ModelView;

import android.os.Handler;
import android.os.Message;
import android.content.Context;

public class ImageThread implements Runnable {
	private Handler handler;
	private Context ctx;
	private String modelName;
	private int listItemId;

	public ImageThread(Handler handler) throws IOException {
		this.handler = handler;

	}

	public void run() {
		try {

			Message msg = new Message();
			msg.what = 0x113;
			msg.obj = getImage();
			handler.sendMessage(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getImage() throws Exception {
		String rs = null;
		Session s = new Session(OpenERPXmlRpcProxy.RPCProtocol.RPC_HTTP,
				"192.168.1.100", 8069, "openerp", "admin", "admin");

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "product.product");

		String[] fields = { "image_small", "qty_available", "lst_price" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", 27);
		// filters.add("x_name", "=", "a");
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		for (Row r : rc) {
			rs = (String) r.get("image_small");
			System.out.println(r.get("image_small") + ":"
					+ r.get("qty_available") + ":" + r.get("lst_price"));
		}
		return rs;
	}

}
