package com.kcb.student.chart;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

/**
 * @className: PieChart
 * @description: 
 * @author: Ding
 * @date: 2015年4月27日 下午2:32:49
 */
public class PieChart{
    
    private int[] colors=new int[]{Color.RED,Color.GREEN};
    public View execute(Context context){
        DefaultRenderer renderer=buildCategoryRenderer(colors);
        CategorySeries categorySeries=new CategorySeries(null);
        categorySeries.add("Sign in",8);
        categorySeries.add("Not sign in",2);
        return ChartFactory.getPieChartView(context, categorySeries, renderer);
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
