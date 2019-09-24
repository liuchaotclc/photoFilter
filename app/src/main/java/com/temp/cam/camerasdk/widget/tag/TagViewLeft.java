package com.temp.cam.camerasdk.widget.tag;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.temp.cam.R;

public class TagViewLeft extends TagView {
	public TagViewLeft(Context paramContext) {
		this(paramContext, null);
	}

	public TagViewLeft(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		LayoutInflater.from(paramContext).inflate(R.layout.camerasdk_view_tag_left, this);
		this.textview = ((TextView) findViewById(R.id.text));
		this.textview.getBackground().setAlpha(178);
		this.textview.setVisibility(View.VISIBLE);
		this.blackIcon1 = ((ImageView) findViewById(R.id.blackIcon1));
		this.blackIcon2 = ((ImageView) findViewById(R.id.blackIcon2));
		this.brandIcon = ((ImageView) findViewById(R.id.brandIcon));
		this.geoIcon = ((ImageView) findViewById(R.id.geoIcon));
		// this.brandIcon.setVisibility(View.GONE);
		this.viewPointer = brandIcon;
		setVisible();
	}
}