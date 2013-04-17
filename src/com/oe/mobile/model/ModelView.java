package com.oe.mobile.model;

import java.util.HashMap;

import android.view.View;
// this class is used for hold the view of the model
// we can use ModelFactory.getModelView to get the view

public class ModelView {

	private Model model;
	private HashMap<String, View> viewMap;

	public ModelView() {
		viewMap = new HashMap<String, View>();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public HashMap<String, View> getViewMap() {
		return viewMap;
	}

	public void setViewMap(HashMap<String, View> viewMap) {
		this.viewMap = viewMap;
	}

}
