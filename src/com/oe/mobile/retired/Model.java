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

import java.util.ArrayList;
import java.util.HashMap;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.Field.FieldType;
import com.debortoliwines.openerp.api.FieldCollection;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;

import android.view.View;

// 每个model包括一个model名字
// 以及所有field里面的list
public class Model {

	private String modelName;
	private ArrayList<Attribute> modelAtt;
	private HashMap<String, Object> attributes;
	private HashMap<String, Object> values;
	private FieldCollection fields;

	public Model() {
		modelAtt = new ArrayList<Attribute>();
		attributes = new HashMap<String, Object>();
		values = new HashMap<String, Object>();
	}

	public HashMap<String, Object> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Object> values) {
		this.values = values;
	}

	public FieldCollection getFields() {
		return fields;
	}

	public void setFields(FieldCollection fields) {
		this.fields = fields;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public ArrayList<Attribute> getModelAtt() {
		return modelAtt;
	}

	public void setModelAtt(ArrayList<Attribute> modelAtt) {
		this.modelAtt = modelAtt;
	}

	public static ArrayList<Model> parseRow(String modelName,
			FieldCollection fc, RowCollection rc) {
		ArrayList<Model> modelList = new ArrayList<Model>();
		// parse each rowcollection, to get the object of it
		System.out.println("------- " + rc.size());
		for (Row r : rc) {
			Model m = new Model();
			m.getAttributes().put("id", r.get("id")); // set the id column, this
														// is the only column
														// that's not include in
														// the fields;
			System.out.println(m.getAttributes().get("id"));
			for (Field f : fc) {
				if (f.getType() == FieldType.ONE2MANY
						|| f.getType() == FieldType.MANY2ONE
						|| f.getType() == FieldType.MANY2MANY) {
					m.getAttributes().put(f.getName(), "TEST");
				} else {
					if (r.get(f) != null)
						m.getAttributes().put(f.getName(), r.get(f));
					else
						m.getAttributes().put(f.getName(), "");
				}
			}
			System.out.println("-------------------XXXXXXXXXX");
			modelList.add(m);
		}
		return modelList;
	}
}
