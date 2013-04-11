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

import com.debortoliwines.openerp.api.Field.FieldType;

import android.view.View;

// 每个attribute为一个该model对应的field生成的view样式
// 包括一个textview，以及一个根据不同类型生成的view对象
// 主要生成的view对象包括以下几种：
// 1. editview: 包括 char, int, float
// 2. date: 时间类型
// 3. button: many2one, many2many, one2one，点击按钮以后，activity会自动导航到下一个详细页面，供用户选择不同的对象值。
public class Attribute {

	private View attName;
	private FieldType attType;
	private View attView;

	public Attribute(View attName, FieldType attType, View attView) {
		this.attName = attName;
		this.attType = attType;
		this.attView = attView;
	}

	public View getAttName() {
		return attName;
	}

	public void setAttName(View attName) {
		this.attName = attName;
	}

	public FieldType getAttType() {
		return attType;
	}

	public void setAttType(FieldType attType) {
		this.attType = attType;
	}

	public View getAttView() {
		return attView;
	}

	public void setAttView(View attView) {
		this.attView = attView;
	}

}
