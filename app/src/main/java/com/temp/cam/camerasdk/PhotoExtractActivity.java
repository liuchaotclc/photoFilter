package com.temp.cam.camerasdk;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.temp.cam.R;
import com.temp.cam.camerasdk.library.filter.GPUImageColorInvertFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilterGroup;
import com.temp.cam.camerasdk.library.filter.GPUImageSobelDetectionFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageView;
import com.temp.cam.camerasdk.library.filter.ifilter.IFInkwellFilter;
import com.temp.cam.camerasdk.model.Constants;

/**
 * 照片提取界面
 */
public class PhotoExtractActivity extends BaseActivity {

    private GPUImageView mCropView;
    private TextView btn_done;


    private Bitmap sourceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_extract);
        showLeftIcon();
        setActionBarTitle("提取");

        mCropView = (GPUImageView) findViewById(R.id.gpu_image_view);
        btn_done = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");


        sourceMap = Constants.bitmap;
        float width = sourceMap.getWidth();
        float height = sourceMap.getHeight();
        float ratio = width / height;

        mCropView.setRatio(ratio);
        mCropView.setImage(sourceMap);
        initEvent();
        GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();

        IFInkwellFilter ifInkwellFilter = new IFInkwellFilter(PhotoExtractActivity.this);//黑白
        GPUImageSobelDetectionFilter gpuImageSobelEdgeDetectionFilter = new GPUImageSobelDetectionFilter();//查找边缘
        GPUImageColorInvertFilter gpuImageColorInvertFilter = new GPUImageColorInvertFilter();//反色
        if(ifInkwellFilter != null){
            gpuImageFilterGroup.addFilter(ifInkwellFilter);
        }
        if(gpuImageSobelEdgeDetectionFilter != null){
            gpuImageFilterGroup.addFilter(gpuImageSobelEdgeDetectionFilter);
        }
        gpuImageFilterGroup.addFilter(gpuImageColorInvertFilter);
        mCropView.setFilter(gpuImageFilterGroup);
    }


    private void initEvent() {
        btn_done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                done();
            }
        });
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

}
