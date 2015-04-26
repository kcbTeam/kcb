package com.kcb.student.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * @className: PieChartView
 * @description: 
 * @author: Ding
 * @date: 2015年4月26日 下午3:49:33
 */
public class PieChartView{
    
    private int[] colors={Color.RED,Color.GREEN};
    
    public Intent execute(Context context){
        DefaultRenderer renderer=buildCategoryRenderer(colors);
        CategorySeries categorySeries=new CategorySeries(null);
        categorySeries.add("check in",70);
        categorySeries.add("not check in",30);
        return ChartFactory.getPieChartIntent(context, categorySeries, renderer, null);
    }
    
    protected DefaultRenderer buildCategoryRenderer(int[] colors){
        DefaultRenderer renderer=new DefaultRenderer();
        for(int color:colors){
            SimpleSeriesRenderer r=new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
}
