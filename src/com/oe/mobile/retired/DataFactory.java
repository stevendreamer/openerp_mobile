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
package com.oe.mobile.retired;

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
