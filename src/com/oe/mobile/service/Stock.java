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
		String[] fields = { "name", "date_start", "state", "product_qty",
				"product_uom", "product_id" };

		FilterCollection filters = new FilterCollection();
		Log.i("INV SERVICE", "before doing actual search");
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Log.i("INV_SERVICE", "size of job rc: " + rc.size());
		Log.i("INV SERVICE", "after doing actual search");
		return rc;
	}

	public static RowCollection getWorkOrders() throws Exception {
		Session s = AppGlobal.getOesession();

		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s,
				"mrp.production.workcenter.line");
		String[] fields = { "name", "production_id", "workcenter_id", "state" };

		FilterCollection filters = new FilterCollection();
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getPOs(String poNumber, String supplier,
			String origin, String state) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "purchase.order");

		String[] fields = { "name", "partner_id", "state", "origin",
				"amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("name", "ilike", poNumber);
		filters.add("partner_id", "ilike", supplier);
		filters.add("origin", "ilike", origin);
		filters.add("state", "ilike", state);

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

		String[] fields = { "name", "partner_id", "stage_id", "state", };

		FilterCollection filters = new FilterCollection();
		filters.add("type", "=", "lead");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static RowCollection getOpportunities() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "crm.lead");

		String[] fields = { "name", "partner_id", "active", "description" };

		FilterCollection filters = new FilterCollection();
		filters.add("type", "=", "opportunity");
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

	public static Row getCustomerById(int id) throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "res.partner");

		String[] fields = { "name", "street", "email", "phone", "mobile" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);
		return r0;
	}

	public static RowCollection getStockIn() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking.in");

		String[] fields = { "name", "partner_id", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("type", "=", "in");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static Row getStockInById(int id) throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking.in");

		String[] fields = { "name", "partner_id", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		filters.add("type", "=", "in");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc.get(0);
	}

	public static RowCollection getStockInLines(int id) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.move");

		String[] fields = { "product_id", "product_qty", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("picking_id", "=", id);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getStockOut() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking.out");

		String[] fields = { "name", "partner_id", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("type", "=", "out");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static Row getStockOutById(int id) throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking.out");

		String[] fields = { "name", "partner_id", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		filters.add("type", "=", "out");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc.get(0);
	}

	public static RowCollection getStockOutLines(int id) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.move");

		String[] fields = { "product_id", "product_qty", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("picking_id", "=", id);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static RowCollection getStockMove() throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking");

		String[] fields = { "name", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("type", "=", "internal");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc;
	}

	public static Row getStockMoveById(int id) throws Exception {

		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.picking");

		String[] fields = { "name", "origin", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		filters.add("type", "=", "internal");
		RowCollection rc = oa.searchAndReadObject(filters, fields);

		return rc.get(0);
	}

	public static RowCollection getStockMoveLines(int id) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "stock.move");

		String[] fields = { "product_id", "product_qty", "state" };

		FilterCollection filters = new FilterCollection();
		filters.add("picking_id", "=", id);

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

	public static RowCollection getSOs(String soNumber, String customer,
			String status) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "sale.order");

		String[] fields = { "name", "partner_id", "state", "origin",
				"amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("name", "ilike", soNumber);
		filters.add("partner_id", "ilike", customer);
		filters.add("state", "ilike", status);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}

	public static Row getSOById(int id) throws Exception {

		HashMap<String, Object> rs = new HashMap<String, Object>();
		// start session
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "sale.order");

		String[] fields = { "name", "partner_id", "state", "amount_total" };

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);
		RowCollection rc = oa.searchAndReadObject(filters, fields);
		Row r0 = rc.get(0);
		return r0;
	}

	public static RowCollection getSOLines(int so_id) throws Exception {
		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, "sale.order.line");

		String[] fields = { "name", "product_uom_qty", "price_unit" };

		FilterCollection filters = new FilterCollection();
		filters.add("order_id", "=", so_id);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
}
