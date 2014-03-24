package com.oe.mobile.service;

import java.util.ArrayList;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.AppGlobal;

public class Inventory {
	 public static RowCollection getItems() throws Exception {
		ArrayList<String> list = new ArrayList<String>();

		Session s = AppGlobal.getOesession();

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "product.product");

		String[] fields = { "name_template", "qty_available", "lst_price" };

		FilterCollection filters = new FilterCollection();
		// filters.add("id", "<", 10);
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}
	
	public static RowCollection getJobs() throws Exception {
		Session s = AppGlobal.getOesession();

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "mrp.production");
		String[] fields = { "name", "state", "product" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "<", 10);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getPOs() throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order");

		String[] fields = { "name", "state", "origin" };

		FilterCollection filters = new FilterCollection();

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
}
