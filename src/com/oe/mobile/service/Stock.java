package com.oe.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.AppGlobal;

public class Stock {
	public static RowCollection getItems() throws Exception {

		// start session
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
		String[] fields = { "date_start", "product_qty", "product_uom" };

		FilterCollection filters = new FilterCollection();
		Log.i("INV SERVICE", "before doing actual search");
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Log.i("INV_SERVICE", "size of job rc: " + rc.size());
		Log.i("INV SERVICE", "after doing actual search");
		return rc;
	}

	public static RowCollection getPOs(String poNumber) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order");

		String[] fields = { "name", "partner_id", "state", "origin",
				"amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("name", "ilike", poNumber);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getPOLines(int po_id) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order.line");

		String[] fields = { "name", "product_id", "product_qty", "price_unit",
				"price_subtotal" };

		FilterCollection filters = new FilterCollection();
		filters.add("order_id", "=", po_id);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getLeads() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "crm.lead");

		String[] fields = { "name", "partner_id", "active", "description" };

		FilterCollection filters = new FilterCollection();
		// filters.add("id", "<", 10);
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static RowCollection getCustomers() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "res.partner");

		String[] fields = { "name", "street", "email", "phone" };

		FilterCollection filters = new FilterCollection();
		filters.add("customer", "=", "t");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static HashMap<String, Object> getItemById(int id) throws Exception {

		HashMap<String, Object> rs = new HashMap<String, Object>();
		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "product.product");

		String[] fields = { "name_template", "qty_available", "lst_price" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);

		rs.put("料号", r0.get("name_template"));
		rs.put("可用量", r0.get("qty_available"));
		rs.put("价格", r0.get("lst_price"));

		return rs;
	}

	public static HashMap<String, Object> getCustomerById(int id)
			throws Exception {

		HashMap<String, Object> rs = new HashMap<String, Object>();
		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "res.partner");

		String[] fields = { "name", "street", "email", "phone" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);

		rs.put("客户名", r0.get("name"));
		rs.put("街道", r0.get("street"));
		rs.put("邮箱", r0.get("email"));
		rs.put("电话", r0.get("phone"));

		return rs;
	}

	public static RowCollection getStockIn() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking.in");

		String[] fields = { "name", "partner_id", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static Row getPOById(int id) throws Exception {

		HashMap<String, Object> rs = new HashMap<String, Object>();
		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order");

		String[] fields = { "name", "state", "origin" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);

		return r0;

	}

	public static RowCollection getSOs(String soNumber) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "sale.order");

		String[] fields = { "name", "state", "origin", "amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("name", "ilike", soNumber);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static HashMap<String, Object> getSOById(int id) throws Exception {

		HashMap<String, Object> rs = new HashMap<String, Object>();
		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "sale.order");

		String[] fields = { "name", "state", "amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);

		rs.put("SO单据号", r0.get("name"));
		rs.put("状态", r0.get("state"));
		rs.put("金额", r0.get("amount_total"));

		return rs;
	}
}
