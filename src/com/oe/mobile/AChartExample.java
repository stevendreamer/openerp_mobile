package com.oe.mobile;


import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class AChartExample {  
    
    private double[] value = {22,22,22};    
    
    public AChartExample(){
        value[0]= 1;
        value[1]=2;
        value[2] = 3;
    }
    
    public AChartExample(double values[]){
        for (int i=0;i<values.length;i++){
            value[i] = values[i];
        }
    }
    
    public Intent execute(Context context) { 
        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE }; 
        DefaultRenderer renderer = buildCategoryRenderer(colors); 
        CategorySeries categorySeries = new CategorySeries("Vehicles Chart"); 
        categorySeries.add("cars ", value[0]); 
        categorySeries.add("trucks", value[1]); 
        categorySeries.add("bikes ", value[2]); 
        return ChartFactory.getPieChartIntent(context, categorySeries, renderer,"jialia"); 
    } 
      
    protected DefaultRenderer buildCategoryRenderer(int[] colors) { 
        DefaultRenderer renderer = new DefaultRenderer(); 
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setApplyBackgroundColor(true);
        renderer.setLabelsTextSize(20);
        renderer.setChartTitle("pie chart");
        renderer.setChartTitleTextSize(30);
        renderer.setLegendTextSize(30);
        renderer.setLegendHeight(50);
        for (int color : colors) { 
            SimpleSeriesRenderer r = new SimpleSeriesRenderer(); 
            r.setColor(color); 
            renderer.addSeriesRenderer(r); 
        } 
        return renderer; 
    } 
}