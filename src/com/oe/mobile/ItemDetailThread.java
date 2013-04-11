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

import android.os.Handler;
import android.os.Message;
import android.content.Context;

public class ItemDetailThread implements Runnable {
	private Handler handler;
	private Context ctx;
	private int productId;

	public ItemDetailThread(Handler handler, Context context, int productId)
			throws IOException {
		this.handler = handler;
		this.ctx = context;
		this.productId = productId;
	}

	public void run() {
		try {
			Model model = ModelFactory.getModel(ctx, "product.product",
					productId);

			Message msg = new Message();
			msg.what = 0x112;
			msg.obj = model;
			handler.sendMessage(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
