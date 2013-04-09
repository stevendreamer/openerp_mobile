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
