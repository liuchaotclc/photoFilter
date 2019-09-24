package com.temp.cam.camerasdk.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.temp.cam.R;
import com.temp.cam.camerasdk.PhotoEnhanceActivity;
import com.temp.cam.camerasdk.library.filter.GPUImageFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilterGroup;
import com.temp.cam.camerasdk.library.filter.GPUImageView;
import com.temp.cam.camerasdk.library.filter.util.ImageFilterTools;
import com.temp.cam.camerasdk.library.utils.PhotoUtils;
import com.temp.cam.camerasdk.library.views.HSuperImageView;
import com.temp.cam.camerasdk.model.Constants;

import java.io.File;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


public class EfectFragment extends Fragment {



    private Context mContext;
    private View mView;
    private String mPath;
    private GPUImageView effect_main; // 需要修改的图像
    private PhotoView mPhotoView, mPhotoBack;
    private TextView mCompareBtn;
    private int mPosition;

    private static ImageFilterTools.FilterType sCurrentFilterType;
    //public Bitmap sourceBitmap;	//当前的图片
    public static ArrayList<HSuperImageView> sticklist; // 保存贴纸图片的集合
    private int sticknum = -1;// 贴纸添加的序号


    public static EfectFragment newInstance(String path) {
        EfectFragment f = new EfectFragment();
        Bundle b = new Bundle();
        b.putString("path", path);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = getArguments().getString("path");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mView = inflater.inflate(R.layout.camerasdk_item_viewpage, container, false);
        effect_main = (GPUImageView) mView.findViewById(R.id.effect_main);
        mPhotoView = (PhotoView) mView.findViewById(R.id.iv_photo);
        mPhotoBack = (PhotoView) mView.findViewById(R.id.iv_photo_back);
        mCompareBtn = (TextView) mView.findViewById(R.id.filter_compare);
        sticklist = new ArrayList<HSuperImageView>();
        mPhotoView.setMinimumScale(1.0f);
        mPhotoBack.setMinimumScale(1.0f);

        mPhotoView.mDispatchTouchEventCallback = new PhotoView.DispatchTouchEventCallback() {
            @Override
            public void onEvent(MotionEvent event) {
                mPhotoBack.dispatchTouchEvent(event);
            }
        };

        mCompareBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPhotoView.setVisibility(View.INVISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mPhotoView.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        if (mPath.startsWith("http://") || mPath.startsWith("https://")) {
            loadUrlImage();
        } else {
            effect_main.setImage(mPath);
            Glide.with(getContext()).load(Uri.fromFile(new File(mPath))).into(mPhotoBack);
        }
        effect_main.setVisibility(View.INVISIBLE);

    }

    //加载网络图片
    private void loadUrlImage() {

        Glide.with(mContext).load(mPath).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setSourceBitmap(resource);
            }
        });
    }


    /**
     * 改变图片(截图)
     */
    public void setBitMap() {
        setSourceBitmap(Constants.bitmap);
    }

    /**
     * 刷新PhotoView的Attacher
     */
    public void refreshAttacher(){
        mPhotoView.refreshAttacher();
        mPhotoBack.refreshAttacher();
        mPhotoView.setMinimumScale(1.0f);
        mPhotoBack.setMinimumScale(1.0f);
    }

    /**
     * 重置bitmap
     */
    public void resetBitMap() {

        Constants.bitmap = Constants.sourceBitmap;
        setSourceBitmap(Constants.bitmap);
        mPhotoView.setVisibility(View.INVISIBLE);
        mPhotoBack.setVisibility(View.INVISIBLE);
        mPhotoView.setMinimumScale(1.0f);
        mPhotoBack.setMinimumScale(1.0f);
        refreshAttacher();
        effect_main.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取当前图片
     *
     * @return
     */
    public Bitmap getCurrentBitMap() {
        return effect_main.getCurrentBitMap();
    }

    /**
     *  加特效
     */
    public void addEffect(ImageFilterTools.FilterType filterType) {
        addEffect(filterType, -1);

    }

    /**
     *  刷新特效
     */
    public void refreshEffect(){
        addEffect(sCurrentFilterType);
    }

    //加特效
    public void addEffect(ImageFilterTools.FilterType filterType, int position) {
        sCurrentFilterType = filterType;
        GPUImageFilterGroup gpuImageFilterGroup = PhotoEnhanceActivity.getFilterGroup(getContext());
//        gpuImageFilterGroup.addFilter(filter);
        effect_main.setFilter(gpuImageFilterGroup);
        effect_main.requestRender();
        mPhotoBack.setScale(mPhotoBack.getPhotoViewAttacher().getMinScale());
        try {
            Bitmap fBitmap = effect_main.capture();
            mPhotoView.setImageBitmap(fBitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (position == 0) {
            mCompareBtn.setVisibility(View.GONE);
        } else {
            if(position != -1){
                mCompareBtn.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     *  获取当前特效
     */
    public static GPUImageFilter getsCurrentFilter(Context context) {
        if(sCurrentFilterType == null){
            return new GPUImageFilter();
        }
        return  ImageFilterTools.createFilterForType(context, sCurrentFilterType);
    }

    //获取最终的图片的路径
    public String getFilterImage() {

        effect_main.setDrawingCacheEnabled(true);
        Bitmap editbmp = Bitmap.createBitmap(effect_main.getDrawingCache());

        try {
            Bitmap fBitmap = effect_main.capture();
            Bitmap bitmap = Bitmap.createBitmap(fBitmap.getWidth(), fBitmap.getHeight(), Config.ARGB_8888);
            Canvas cv = new Canvas(bitmap);
            cv.drawBitmap(fBitmap, 0, 0, null);
            cv.drawBitmap(editbmp, 0, 0, null);

            //最终合并生成图片
            String path = PhotoUtils.saveAsBitmap(mContext, bitmap);
            bitmap.recycle();
            return path;

        } catch (Exception e) {
            return "";
        }

    }

    //获取最终的图片的Bitmap
    public Bitmap getFilterBitmap() {

        effect_main.setDrawingCacheEnabled(true);
        Bitmap editbmp = Bitmap.createBitmap(effect_main.getDrawingCache());

        try {
            Bitmap fBitmap = effect_main.capture();
            Bitmap bitmap = Bitmap.createBitmap(fBitmap.getWidth(), fBitmap.getHeight(), Config.ARGB_8888);
            Canvas cv = new Canvas(bitmap);
            cv.drawBitmap(fBitmap, 0, 0, null);
            cv.drawBitmap(editbmp, 0, 0, null);

            fBitmap.recycle();
            editbmp.recycle();

            return bitmap;

        } catch (Exception e) {
            return null;
        }
    }

    private void setSourceBitmap(Bitmap sourceBitmap) {
        float width = sourceBitmap.getWidth();
        float height = sourceBitmap.getHeight();
        float ratio = width / height;

        effect_main.setVisibility(View.VISIBLE);
        effect_main.setRatio(ratio);
        effect_main.setImage(sourceBitmap);
        mPhotoBack.setImageBitmap(sourceBitmap);
        mPhotoView.setImageBitmap(sourceBitmap);
//        mPhotoBack.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    /**
     *  加载出特效到PhotoView
     */
    public void refectFilter(){
        Bitmap fBitmap = null;
        try {
            fBitmap = effect_main.capture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPhotoView.setImageBitmap(fBitmap);
        mPhotoView.setVisibility(View.VISIBLE);
        mPhotoBack.setVisibility(View.VISIBLE);
        mPhotoBack.setImageBitmap(Constants.bitmap);
        mPhotoView.setMinimumScale(1.0f);
        mPhotoBack.setMinimumScale(1.0f);
//        mPhotoBack.setVisibility(View.INVISIBLE);
//        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        effect_main.setVisibility(View.INVISIBLE);

    }

}
