package com.temp.cam.camerasdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.temp.cam.R;
import com.temp.cam.camerasdk.library.filter.GPUImageBrightnessFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageContrastFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilterGroup;
import com.temp.cam.camerasdk.library.filter.GPUImageHighlightShadowFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSaturationFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSharpenFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageView;
import com.temp.cam.camerasdk.model.Constants;
import com.temp.cam.camerasdk.ui.fragment.EfectFragment;

/**
 * 调节界面
 * 亮度、对比度、饱和度、锐度、暗部、亮部
 */

public class PhotoEnhanceActivity extends BaseActivity implements OnSeekBarChangeListener {

    private GPUImageView mCropView;
    private TextView btn_done;

    private SeekBar seekbar_brightness;

    private RadioButton button_brightness;
    private RadioGroup layout_tab;
    private int mCurrentRadioInt;
    private TextView mProgressDegree;

    private Bitmap sourceMap;

    public static float enhance_brightness = 0f;
    public static float enhance_contrast = 100f;
    public static float enhance_saturation = 100f;
    public static float enhance_sharpness = 0f;
    public static float enhance_exposure = 0f;
    public static float enhance_shadow = 200f;

    public static void initStaticValue(){
        enhance_brightness = 0f;
        enhance_contrast = 100f;
        enhance_saturation = 100f;
        enhance_sharpness = 0f;
        enhance_exposure = 0f;
        enhance_shadow = 200f;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_enhance);
        showLeftIcon();
        setActionBarTitle("调整");

        mCropView = (GPUImageView) findViewById(R.id.gpu_image_view);
        btn_done = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");


        seekbar_brightness = (SeekBar) findViewById(R.id.seekBar);
        mProgressDegree = (TextView) findViewById(R.id.progress_degree);

        button_brightness = (RadioButton) findViewById(R.id.fragment_radio_bright);

        layout_tab = (RadioGroup) findViewById(R.id.fragment_adjust_radiogroup);

        sourceMap = Constants.bitmap;
        float width = sourceMap.getWidth();
        float height = sourceMap.getHeight();
        float ratio = width / height;

        mCropView.setRatio(ratio);
        mCropView.setImage(sourceMap);
        initEvent();
        button_brightness.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onProgressChanged(seekbar_brightness, (int)enhance_brightness + 100, false);
    }

    private void initEvent() {
        seekbar_brightness.setOnSeekBarChangeListener(this);

        layout_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                mCurrentRadioInt = arg1;
                int tempProgressValue = 0;
                seekbar_brightness.setOnSeekBarChangeListener(null);
                if (arg1 == R.id.fragment_radio_bright) {
                    tempProgressValue = (int)enhance_brightness + 100;
                } else if (arg1 == R.id.fragment_radio_contrast) {
                    tempProgressValue = (int)enhance_contrast;
                } else if (arg1 == R.id.fragment_radio_saturation) {
                    tempProgressValue = (int)enhance_saturation;
                } else if (arg1 == R.id.fragment_radio_sharpness) {
                    tempProgressValue = (int)enhance_sharpness + 100;
                } else if (arg1 == R.id.fragment_radio_exposure) {
                    tempProgressValue = (int)enhance_exposure + 100;
                } else if (arg1 == R.id.fragment_radio_hue) {
                    tempProgressValue = (int)enhance_shadow - 100;
                }
                seekbar_brightness.setProgress(tempProgressValue);
                seekbar_brightness.setOnSeekBarChangeListener(PhotoEnhanceActivity.this);
                mProgressDegree.setText((tempProgressValue - 100) + "");
            }
        });

        btn_done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    //拖动值监听
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        mProgressDegree.setText((progress - 100) + "");
        switch (mCurrentRadioInt) {
            case R.id.fragment_radio_bright:
                enhance_brightness = progress - 100;
                break;
            case R.id.fragment_radio_contrast:
                enhance_contrast = progress;
                break;
            case R.id.fragment_radio_saturation:
                enhance_saturation = progress;
                break;
            case R.id.fragment_radio_sharpness:
                enhance_sharpness = progress - 100;
                break;
            case R.id.fragment_radio_exposure:
                enhance_exposure = progress - 100;
                break;
            case R.id.fragment_radio_hue:
                enhance_shadow = progress + 100;
                break;
        }

        applyFilter(this);
    }

    /**
     * 应用滤镜
     */
    public void applyFilter(Context context){
        GPUImageFilterGroup gpuImageFilterGroup = getFilterGroup(context);
        mCropView.setFilter(gpuImageFilterGroup);
        mCropView.requestRender();
    }

    /**
     * 获取滤镜效果集合，供外部调用
     */
    public static GPUImageFilterGroup getFilterGroup(Context context) {
        GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();
        GPUImageBrightnessFilter gpuImageBrightnessFilter = new GPUImageBrightnessFilter(enhance_brightness / 250);
        GPUImageContrastFilter gpuImageContrastFilter = new GPUImageContrastFilter(enhance_contrast / 100);
        GPUImageSaturationFilter gpuImageSaturationFilter = new GPUImageSaturationFilter(enhance_saturation / 100);
        GPUImageSharpenFilter gpuImageSharpenFilter = new GPUImageSharpenFilter(enhance_sharpness / 100);
        GPUImageHighlightShadowFilter gpuImageHighlightShadowFilter = new GPUImageHighlightShadowFilter(enhance_exposure / 200, enhance_shadow / 200);
        gpuImageFilterGroup.addFilter(gpuImageBrightnessFilter);
        gpuImageFilterGroup.addFilter(gpuImageContrastFilter);
        gpuImageFilterGroup.addFilter(gpuImageSaturationFilter);
        gpuImageFilterGroup.addFilter(gpuImageSharpenFilter);
        gpuImageFilterGroup.addFilter(gpuImageHighlightShadowFilter);
        if(EfectFragment.getsCurrentFilter(context) != null){
            gpuImageFilterGroup.addFilter(EfectFragment.getsCurrentFilter(context));
        }
        return gpuImageFilterGroup;
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    //SeekBar 停止拖动
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void done() {
        //        try {
//            Constants.bitmap = mCropView.capture();//sourceMap;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        setResult(Constants.RequestCode_Ebhance);
        finish();
    }

}
