package com.temp.cam.camerasdk;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.temp.cam.R;
import com.temp.cam.camerasdk.library.filter.GPUImageBrightnessFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilterGroup;
import com.temp.cam.camerasdk.library.filter.GPUImageToneCurveFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageView;
import com.temp.cam.camerasdk.model.Constants;

import java.io.IOException;
import java.io.InputStream;

/**
 * 图片扫描页面
 */
public class PhotoScanActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private GPUImageView mCropView;
    private TextView btn_done;
    private SeekBar mSeekbarScan;

    private Bitmap sourceMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_scan);
        showLeftIcon();
        setActionBarTitle("扫描");

        mCropView = (GPUImageView) findViewById(R.id.gpu_image_view);
        btn_done = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");
        mSeekbarScan = (SeekBar) findViewById(R.id.seekBar);


        sourceMap = Constants.bitmap;
        float width = sourceMap.getWidth();
        float height = sourceMap.getHeight();
        float ratio = width / height;

        mCropView.setRatio(ratio);
        mCropView.setImage(sourceMap);


        executeFilter(0.25f);
        mSeekbarScan.setProgress(100);
        initEvent();

    }

    private void executeFilter(float value){
        GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();
        GPUImageBrightnessFilter gpuImageBrightnessFilter = new GPUImageBrightnessFilter(value);
        GPUImageToneCurveFilter gpuImageToneCurveFilter = new GPUImageToneCurveFilter();//曲线

        AssetManager as = getAssets();
        InputStream is = null;
        try {
            is = as.open("scan.acv");
            gpuImageToneCurveFilter.setFromCurveFileInputStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("MainActivity", "Error");
        }
        gpuImageFilterGroup.addFilter(gpuImageBrightnessFilter);
        gpuImageFilterGroup.addFilter(gpuImageToneCurveFilter);
        mCropView.setFilter(gpuImageFilterGroup);
    }


    private void initEvent() {
        btn_done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                done();
            }
        });
        mSeekbarScan.setOnSeekBarChangeListener(this);
    }


    private void done() {
        try {
            Constants.bitmap = mCropView.capture();//sourceMap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setResult(Constants.RequestCode_Croper);
        finish();
    }

    //拖动值监听
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        float value = ((float)progress)/400 ;

        executeFilter(value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
