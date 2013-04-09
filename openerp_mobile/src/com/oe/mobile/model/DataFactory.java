package com.oe.mobile.model;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.AppGlobal;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public class DataFactory {
	public static RowCollection getRowsById(int id, String modelName,
			String[] fields) throws Exception {

		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, modelName);

		FilterCollection filters = new FilterCollection();
		filters.add("id", "=", id);

		RowCollection rc = oa.searchAndReadObject(filters, fields);
		return rc;
	}
}
