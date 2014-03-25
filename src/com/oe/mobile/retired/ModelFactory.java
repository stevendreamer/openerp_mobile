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

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.Field.FieldType;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.oe.mobile.AppGlobal;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

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

	public static ModelView getModelView(Context ctx, String modelName, int id)
			throws Exception {
		Model model = new Model();
		model.setModelName(modelName);

		ModelView mv = new ModelView();

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
			System.out.println("zzyan: before search");			
			
			rc = oa.searchAndReadObject(filters, fieldNames);
			System.out.println("zzyan: after search");
		}

		// this is the model value;
		Row r = rc.get(0);
		// construct the model;
		model.setFields(oa.getFields());
		// construct the model;
		for (Field f : model.getFields()) {
			// create the model based on the field type
			// INTEGER, CHAR, TEXT, BINARY, BOOLEAN, FLOAT, DATETIME, DATE,
			// MANY2ONE, ONE2MANY, MANY2MANY, SELECTION;
			switch (f.getType()) {
			case INTEGER:
				Integer vint = (Integer) r.get(f.getName());
				model.getValues().put(f.getName(), vint);
				break;
			case CHAR:
				String vchar = (String) r.get(f.getName());
				model.getValues().put(f.getName(), vchar);
				break;
			case TEXT:
				String vtext = (String) r.get(f.getName());
				model.getValues().put(f.getName(), vtext);
				break;
			case BINARY:
				// String t = (String) r.get(f.getName());
				model.getValues().put(f.getName(), "didn't implement");
				break;
			case BOOLEAN:
				Boolean vbool = (Boolean) r.get(f.getName());
				model.getValues().put(f.getName(), vbool);
				break;
			case FLOAT:
				Double vdouble = (Double) r.get(f.getName());
				model.getValues().put(f.getName(), vdouble);
				break;
			case DATETIME:
				model.getValues().put(f.getName(), "didn't implement");
				break;
			case DATE:
				model.getValues().put(f.getName(), "didn't implement");
				break;
			case MANY2ONE:
				// Integer vmany2one = Integer
				// .parseInt((String) r.get(f.getName()));
				Integer vmany2one = new Integer(1);
				model.getValues().put(f.getName(), vmany2one);
				break;
			case ONE2MANY:
				ArrayList<Object> vone2many = new ArrayList<Object>();
				model.getValues().put(f.getName(), vone2many);
				break;
			case MANY2MANY:
				model.getValues().put(f.getName(), "didn't implement");
				break;
			case SELECTION:
				ArrayList<String> vselection = new ArrayList<String>();
				model.getValues().put(f.getName(), vselection);
				break;
			default:
				break;
			}

		}

		// construct the view
		for (Field f : model.getFields()) {
			// create the model based on the field type
			// INTEGER, CHAR, TEXT, BINARY, BOOLEAN, FLOAT, DATETIME, DATE,
			// MANY2ONE, ONE2MANY, MANY2MANY, SELECTION;
			switch (f.getType()) {
			case INTEGER:
				Integer vint = (Integer) model.getValues().get(f.getName());
				TextView intview = new TextView(ctx);
				intview.setTextColor(Color.BLACK);
				intview.setText(vint.toString());
				mv.getViewMap().put(f.getName(), intview);
				break;
			case CHAR:
				String vchar = (String) model.getValues().get(f.getName());
				TextView charview = new TextView(ctx);
				charview.setTextColor(Color.BLACK);
				charview.setText(vchar);
				mv.getViewMap().put(f.getName(), charview);
				break;
			case TEXT:
				String vtext = (String) model.getValues().get(f.getName());
				TextView textview = new TextView(ctx);
				textview.setTextColor(Color.BLACK);
				textview.setText(vtext);
				mv.getViewMap().put(f.getName(), textview);
				break;
			case BINARY:
				// String t = (String) r.get(f.getName());
				TextView bv = new TextView(ctx);
				bv.setTextColor(Color.BLACK);
				bv.setText("didn't implement");
				mv.getViewMap().put(f.getName(), bv);
				break;
			case BOOLEAN:
				Boolean vbool = (Boolean) model.getValues().get(f.getName());
				TextView boolview = new TextView(ctx);
				boolview.setTextColor(Color.BLACK);
				boolview.setText(vbool.toString());
				mv.getViewMap().put(f.getName(), boolview);
				break;
			case FLOAT:
				Double vfloat = (Double) model.getValues().get(f.getName());
				TextView floatview = new TextView(ctx);
				floatview.setTextColor(Color.BLACK);
				floatview.setText(vfloat.toString());
				mv.getViewMap().put(f.getName(), floatview);
				break;
			case DATETIME:
				TextView dv2 = new TextView(ctx);
				dv2.setTextColor(Color.BLACK);
				dv2.setText("didn't implement");
				mv.getViewMap().put(f.getName(), dv2);
				break;
			case DATE:
				TextView dateview = new TextView(ctx);
				dateview.setTextColor(Color.BLACK);
				dateview.setText("didn't implement");
				mv.getViewMap().put(f.getName(), dateview);
				break;
			case MANY2ONE:
				Integer many2one = (Integer) model.getValues().get(f.getName());
				Button many2onebtn = new Button(ctx);
				many2onebtn.setTextColor(Color.BLACK);
				many2onebtn.setText("many2one:" + many2one);
				mv.getViewMap().put(f.getName(), many2onebtn);
				break;
			// when this is an ONE2MANY mapping, then we need to pass current record id to the child pages
			case ONE2MANY:
				ArrayList<Object> one2many = new ArrayList<Object>();
				Button one2manybtn = new Button(ctx);
				one2manybtn.setTextColor(Color.BLACK);
				one2manybtn.setText("one2many:size:" + one2many.size());
				//OnClickListener c = new One2ManyClicker(ctx,f);
				one2manybtn.setOnClickListener(new One2ManyClicker(ctx,f));
				
				mv.getViewMap().put(f.getName(), one2manybtn);
				break;
			case MANY2MANY:
				model.getValues().put(f.getName(), "didn't implement");
				TextView many2manyview = new TextView(ctx);
				many2manyview.setTextColor(Color.BLACK);
				many2manyview.setText("many2many,didn't implement");
				mv.getViewMap().put(f.getName(), many2manyview);
				break;
			case SELECTION:
				TextView selectionview = new TextView(ctx);
				selectionview.setTextColor(Color.BLACK);
				selectionview.setText("selection");
				mv.getViewMap().put(f.getName(), selectionview);
				break;
			default:
				break;
			}

		}

		return mv;
	}
	
	static class One2ManyClicker implements OnClickListener{

		private Field f; 
		private Context ctx=null;
		public One2ManyClicker(Context ctx, Field f){
			// f.getRelation(): get the relation ship with the other model
			// f.getFieldProperty("relation_field")) get the relation_feild in the model, 
			// so we can confine the search results
			this.ctx = ctx;
			this.f = f;
			
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ctx,
					GeneralDetailActivity.class);	
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(intent);
		}
		
	}
}
