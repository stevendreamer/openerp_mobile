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
