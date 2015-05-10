package com.kcb.library.view.parallaxview;

import com.kcb.library.view.parallaxview.ParallaxScrollView.InnerScrollViewScrollListener;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @className: ParallaxInnerScrollView
 * @description: 
 * @author: Ding
 * @date: 2015年5月7日 上午10:02:23
 */
public class ParallaxInnerScrollView extends ScrollView{
    
    private InnerScrollViewScrollListener mScrollListener;
    
    public void setOnScrollListener(InnerScrollViewScrollListener listener){
        mScrollListener=listener;
    }
    
    public ParallaxInnerScrollView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public ParallaxInnerScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public ParallaxInnerScrollView(Context context) {  
        super(context);  
    }
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {  
        super.onScrollChanged(l, t, oldl, oldt);  
        if (null != mScrollListener) {  
            mScrollListener.onScroll();  
        }
    }
}
