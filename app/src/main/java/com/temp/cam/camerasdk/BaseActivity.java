package com.temp.cam.camerasdk;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.temp.cam.camerasdk.library.utils.MResource;


public abstract class BaseActivity extends FragmentActivity{
	
	protected Context mContext;
	protected TextView btn_back,mActionBarTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不可横屏幕
	}
	
	public void setActionBarTitle(String s) {
		if(mActionBarTitle==null){
			mActionBarTitle=(TextView)findViewById(MResource.getIdRes(mContext, "camerasdk_actionbar_title"));
		}
		mActionBarTitle.setText(s);
	}
	
	//显示返回按钮
	public void showLeftIcon(){
		if(btn_back==null){
			btn_back=(TextView)findViewById(MResource.getIdRes(mContext, "camerasdk_btn_back"));
		}
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	
}
