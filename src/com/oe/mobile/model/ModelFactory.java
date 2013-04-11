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
package com.oe.mobile.model;

import java.util.ArrayList;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.Field.FieldType;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.AppGlobal;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.Context;
import android.view.View;

// 这个是产生model view 的工厂类，用来生成一个model的view
// 该类会生成所有的字段的view
public class ModelFactory {

	public static Model getModel(Context ctx, String modelName, int id)
			throws Exception {
		Model model = new Model();
		model.setModelName(modelName);

		Session s = AppGlobal.getOesession();
		s.startSession();

		ObjectAdapter oa = new ObjectAdapter(s, modelName);

		// get the fieldnames
		String[] fieldNames;
		FilterCollection filters;
		RowCollection rc = null;
		if (id != 0) {
			fieldNames = oa.getFieldNames();
			filters = new FilterCollection();
			filters.add("id", "=", id);
			rc = oa.searchAndReadObject(filters, fieldNames);
		}

		System.out.println(rc.size());
		for (Field f : oa.getFields()) {
			System.out.println(f.getName());
			TextView tx = new TextView(ctx);
			tx.setText(f.getName());
			EditText et = new EditText(ctx);
			if (id != 0 && rc.get(0).get(f) != null) {
				et.setText(rc.get(0).get(f).toString());
			}
			et.setWidth(300);
			et.setEnabled(false);
			/*
			 * if (f.getType() == FieldType.BINARY || f.getType() ==
			 * FieldType.BOOLEAN || f.getType() == FieldType.CHAR || f.getType()
			 * == FieldType.FLOAT || f.getType() == FieldType.INTEGER) { et =
			 * new EditText(ctx); }
			 */

			Attribute att = new Attribute(tx, f.getType(), et);
			model.getModelAtt().add(att);

		}

		return model;
	}

}
