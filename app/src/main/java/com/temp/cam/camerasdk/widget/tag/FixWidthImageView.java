package com.temp.cam.camerasdk.widget.tag;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FixWidthImageView extends ImageView {

	public FixWidthImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FixWidthImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixWidthImageView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthModel = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightModel = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = 0;
		int height = 0;

		if (widthModel == MeasureSpec.EXACTLY || heightModel == MeasureSpec.EXACTLY) {
			if (widthModel == MeasureSpec.EXACTLY && heightModel == MeasureSpec.EXACTLY) {
				//取较大的长度
				if (widthSize > heightSize) {
					width = height = widthSize;
				} else {
					width = height = heightSize;
				}
			} else if (widthModel == MeasureSpec.EXACTLY) {
				width = height = widthSize;
			} else {
				width = height = heightSize;
			}
		} else {
			//指定默认的大小
			width = height = widthSize;
		}
		setMeasuredDimension(width,height);
	}
}
