package com.temp.cam.camerasdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.temp.cam.R;
import com.temp.cam.camerasdk.library.utils.PhotoUtils;
import com.temp.cam.camerasdk.model.CameraSdkParameterInfo;
import com.temp.cam.camerasdk.model.Constants;

/**
 * 旋转图片处理页面
 */

public class RotateActivity extends BaseActivity {
  
    private ImageView mCropView;
    private TextView btn_done;

    private Bitmap sourceMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_rotate);
        showLeftIcon();
        setActionBarTitle("旋转");
        findViews();
        
        sourceMap=Constants.bitmap;
        mCropView.setImageBitmap(sourceMap);		
    }

	public static void startActivity(Context context, CameraSdkParameterInfo cameraSdkParameterInfo){
		Intent intent = new Intent();
		intent.setClass(context, FilterImageActivity.class);
		Bundle b=new Bundle();
		b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);
		intent.putExtras(b);
		context.startActivity(intent);
	}

    private void findViews() {
        mCropView = (ImageView) findViewById(R.id.cropImageView);
        btn_done=(TextView)findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");
        
        initEvent();
    }
	
	private void initEvent() {
		btn_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				done();
			}
		});
		/*findViewById(R.id.buttonFree).setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_FREE); //自由裁剪
			}
		});*/
		

		findViewById(R.id.ratation_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sourceMap = PhotoUtils.rotateImage(sourceMap, -90);
				mCropView.setImageBitmap(sourceMap);
			}
		});
		findViewById(R.id.ratation_right).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sourceMap = PhotoUtils.rotateImage(sourceMap, 90);
				mCropView.setImageBitmap(sourceMap);
			}
		});
		findViewById(R.id.ratation_vertical).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sourceMap = PhotoUtils.reverseImage(sourceMap, -1, 1);
				mCropView.setImageBitmap(sourceMap);
			}
		});
		findViewById(R.id.ratation_updown).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sourceMap = PhotoUtils.reverseImage(sourceMap, 1, -1);
				mCropView.setImageBitmap(sourceMap);
			}
		});
		
		
	}
    
    private void done(){
//		mCropView.setDrawingCacheEnabled(true);
//    	Constants.bitmap=Bitmap.createBitmap(mCropView.getDrawingCache());
		Constants.bitmap =((BitmapDrawable)mCropView.getDrawable()).getBitmap();
		setResult(Constants.RequestCode_Croper);
        finish();
    }
    

}
