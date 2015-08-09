package com.kcb.library.view.parallaxview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kcbTeam.kcblibrary.R;

/**
 * 
 * @className: ParallaxScrollView
 * @description:
 * @author: Ding
 * @date: 2015年5月7日 上午10:31:50
 */

public class ParallaxScrollView extends LinearLayout {

	private float mDiffFactor = 0;
	private ParallaxInnerScrollView mForeground;
	private View mBackgournd;
	private LinearLayout mContent;
	private View mParallaxScrollWidget;
	private View mBackgroundContent;
	private int mBackgroundDiff;
	private int mForegroundDiff;
	private float mParallaxFactor;

	public ParallaxScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParallaxScrollView(Context context) {
		super(context);
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.view_parallaxscroll, this);
		mParallaxScrollWidget = findViewById(R.id.parallax_scroll_widget);
		mBackgournd = findViewById(R.id.background);
		mBackgroundContent = findViewById(R.id.background_content);
		mForeground = (ParallaxInnerScrollView) findViewById(R.id.foreground);
		mForeground.setOnScrollListener(onScrollListener);
		mContent = (LinearLayout) findViewById(R.id.content);
		transferChildren();
	}

	private void transferChildren() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(0);
			if (child != mParallaxScrollWidget) {
				detachFromParent(child);
				mContent.addView(child);
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (isInEditMode()) {
			return;
		}
		init();
	}

	public void addChildView(View child) {
		detachFromParent(child);
		mContent.addView(child);
	}

	public void addChildView(View child, int index) {
		detachFromParent(child);
		mContent.addView(child, index);
	}

	public void addChildView(View child, int width, int height) {
		detachFromParent(child);
		mContent.addView(child, width, height);
	}

	private void detachFromParent(View childView) {
		if (null != childView) {
			ViewGroup parent = (ViewGroup) childView.getParent();
			if (null != parent) {
				parent.removeView(childView);
			}
		}
	}

	public void setBackgroundDrawableId(int drawableId) {
		if (null != mBackgroundContent && drawableId >= 0) {
			mBackgroundContent.setBackgroundResource(drawableId);
		}
	}

	@SuppressWarnings("deprecation")
	public void setBackgroundDrawable(Drawable drawable) {
		if (null != drawable && null != mBackgroundContent) {
			mBackgroundContent.setBackgroundDrawable(drawable);
		}
	}

	public void setForegroundDrawableId(int drawableId) {
		if (null != mForeground && drawableId >= 0) {
			mForeground.setBackgroundResource(drawableId);
		}
	}

	@SuppressWarnings("deprecation")
	public void setForegroundDrawable(Drawable drawable) {
		if (null != mForeground && null != drawable) {
			mForeground.setBackgroundDrawable(drawable);
		}
	}

	public void setDiffFactor(float diffFactor) {
		this.mDiffFactor = diffFactor;
		invalidate();
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (isInEditMode()) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int foregroundTotalHeight = getChildScrollViewTotalHeight();
		int scrollDiff = computeScrollDiffDistance(
				mBackgournd.getMeasuredHeight(), foregroundTotalHeight);
		int newBackgroundHeight = mBackgournd.getMeasuredHeight() + scrollDiff;
		measureChild(mBackgournd, widthMeasureSpec,
				MeasureSpec.makeMeasureSpec(newBackgroundHeight,
						MeasureSpec.EXACTLY));
		mBackgroundDiff = mBackgournd.getMeasuredHeight() - getMeasuredHeight();
		mForegroundDiff = foregroundTotalHeight - getMeasuredHeight();
		mParallaxFactor = ((float) mBackgroundDiff) / ((float) mForegroundDiff);
	}

	private int computeScrollDiffDistance(int backgroundHeight,
			int forgroundHeight) {
		if (forgroundHeight <= backgroundHeight) {
			return 0;
		}
		int heightDiff = forgroundHeight - backgroundHeight;
		int scrollDiff = (int) (heightDiff * mDiffFactor);
		return scrollDiff;
	}

	private int getChildScrollViewTotalHeight() {
		if (null == mContent) {
			return 0;
		}
		return mContent.getMeasuredHeight();
	}

	private InnerScrollViewScrollListener onScrollListener = new InnerScrollViewScrollListener() {
		@Override
		public void onScroll() {
			int backgroundScrollY = (int) (mParallaxFactor * mForeground
					.getScrollY());
			mBackgournd.scrollTo(0, backgroundScrollY);
		}
	};

	public static interface InnerScrollViewScrollListener {
		public void onScroll();
	}
}
