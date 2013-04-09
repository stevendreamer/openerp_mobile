package com.oe.mobile.model;

import com.debortoliwines.openerp.api.Field.FieldType;

import android.view.View;

// ÿ��attributeΪһ����model��Ӧ��field���ɵ�view��ʽ
// ����һ��textview���Լ�һ�����ݲ�ͬ�������ɵ�view����
// ��Ҫ���ɵ�view����������¼��֣�
// 1. editview: ���� char, int, float
// 2. date: ʱ������
// 3. button: many2one, many2many, one2one�������ť�Ժ�activity���Զ���������һ����ϸҳ�棬���û�ѡ��ͬ�Ķ���ֵ��
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
