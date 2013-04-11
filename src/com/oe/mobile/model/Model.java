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

import android.view.View;

// 每个model包括一个model名字
// 以及所有field里面的list
public class Model {

	private String modelName;
	private ArrayList<Attribute> modelAtt;
	public Model(){
		modelAtt = new ArrayList<Attribute>();
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
	
	
}
