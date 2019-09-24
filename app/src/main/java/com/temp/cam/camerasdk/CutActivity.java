package com.temp.cam.camerasdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.temp.cam.R;
import com.temp.cam.camerasdk.model.CameraSdkParameterInfo;
import com.temp.cam.camerasdk.model.Constants;
import com.temp.cam.camerasdk.view.CropImageView;

/**
 * 裁剪界面
 */
public class CutActivity extends BaseActivity {
  
    private CropImageView mCropView;
    private TextView btn_done;

    private Bitmap sourceMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_cut);
        showLeftIcon();
        setActionBarTitle("裁剪");
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
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        btn_done=(TextView)findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");
        
        initEvent();
    }
	
	private void initEvent() {
		findViewById(R.id.button1_1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
			}
		});
		findViewById(R.id.button3_4).setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
			}
		});
		findViewById(R.id.button4_3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
			}
		});
		findViewById(R.id.button9_16).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
			}
		});
		findViewById(R.id.button16_9).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
			}
		});
		findViewById(R.id.buttonfree).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCropView.setCropMode(CropImageView.CropMode.RATIO_FREE);
			}
		});
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
		

		
		
	}
    
    private void done(){
    	Constants.bitmap=mCropView.getCroppedBitmap();
    	setResult(Constants.RequestCode_Croper);
        finish();
    }

}
