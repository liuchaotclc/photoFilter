package com.temp.cam.camerasdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.temp.cam.R;
import com.temp.cam.camerasdk.adapter.Filter_Effect_Adapter;
import com.temp.cam.camerasdk.adapter.Filter_Utils_Adapter;
import com.temp.cam.camerasdk.adapter.FragmentViewPagerAdapter;
import com.temp.cam.camerasdk.adapter.SmallThumbAdapter;
import com.temp.cam.camerasdk.library.utils.ScreenUtils;
import com.temp.cam.camerasdk.library.views.HorizontalListView;
import com.temp.cam.camerasdk.model.CameraSdkParameterInfo;
import com.temp.cam.camerasdk.model.Constants;
import com.temp.cam.camerasdk.model.Filter_Effect_Info;
import com.temp.cam.camerasdk.ui.fragment.EfectFragment;
import com.temp.cam.camerasdk.utils.FilterUtils;
import com.temp.cam.camerasdk.view.CustomViewPager;
import com.temp.cam.example.ResultActivity;
import com.temp.cam.example.model.UtilItemBean;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FilterImageActivity extends BaseActivity {

    private CameraSdkParameterInfo mCameraSdkParameterInfo = new CameraSdkParameterInfo();

    private HorizontalListView effect_listview, sticker_listview, images_listview;

    private TextView tab_effect, tab_sticker, txt_cropper, btn_reset;//,txt_enhance;//,txt_graffiti;
    private RelativeLayout loading_layout;// 等待框
//    private SeekBar mSeekBar;


    private SmallThumbAdapter iAdapter;
    private Filter_Effect_Adapter eAdapter;
    private Filter_Utils_Adapter sAdapter;

    private ArrayList<Filter_Effect_Info> effect_list = new ArrayList<Filter_Effect_Info>(); //特效
    //	private ArrayList<Filter_Sticker_Info> stickerList = new ArrayList<Filter_Sticker_Info>();
    private ArrayList<UtilItemBean> utilList = new ArrayList<>();
    private ArrayList<String> imageList;

    private FragmentViewPagerAdapter fAdapter;
    private CustomViewPager mViewPager;
    private ArrayList<Fragment> fragments;
    private int mScreenWidth;
    Handler mHandler;

    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_filter_image);
        showLeftIcon();

        try {
            mCameraSdkParameterInfo = (CameraSdkParameterInfo) getIntent().getSerializableExtra(CameraSdkParameterInfo.EXTRA_PARAMETER);
            imageList = mCameraSdkParameterInfo.getImage_list();
        } catch (Exception e) {
        }

        initView();

        TextView tv_title = (TextView) findViewById(R.id.camerasdk_actionbar_title);
        if (mCameraSdkParameterInfo.isSingle_mode()) {
            setActionBarTitle("编辑图片");
        } else {
            tv_title.setVisibility(View.GONE);
            findViewById(R.id.images_layout).setVisibility(View.VISIBLE);
        }
        mScreenWidth = ScreenUtils.getScreenWidth(this);

        mHandler = new Handler();

        initEvent();
        initData();

    }

    public static void startActivity(Context context, CameraSdkParameterInfo cameraSdkParameterInfo) {

        Intent intent = new Intent();
        intent.setClass(context, FilterImageActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);
        intent.putExtras(b);
        context.startActivity(intent);

    }

    private void initView() {

        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);

        tab_effect = (TextView) findViewById(R.id.txt_effect);
        tab_sticker = (TextView) findViewById(R.id.txt_sticker);
        txt_cropper = (TextView) findViewById(R.id.txt_cropper);
//		txt_enhance = (TextView) findViewById(R.id.txt_enhance);
//		txt_graffiti = (TextView) findViewById(R.id.txt_graffiti);

        btn_reset = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_reset.setText("重置");
        btn_reset.setVisibility(View.VISIBLE);
//        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        effect_listview = (HorizontalListView) findViewById(R.id.effect_listview);
        sticker_listview = (HorizontalListView) findViewById(R.id.sticker_listview);
        images_listview = (HorizontalListView) findViewById(R.id.images_listview);
        loading_layout = (RelativeLayout) findViewById(R.id.loading);


        tab_effect.setTextColor(getResources().getColor(R.color.camerasdk_txt_normal));
        tab_sticker.setTextColor(getResources().getColor(R.color.camerasdk_txt_selected));
    }

    private void initEvent() {
        tab_effect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                effect_listview.setVisibility(View.VISIBLE);
//                mSeekBar.setVisibility(View.VISIBLE);
                sticker_listview.setVisibility(View.INVISIBLE);
                tab_effect.setTextColor(getResources().getColor(R.color.camerasdk_txt_selected));
                tab_sticker.setTextColor(getResources().getColor(R.color.camerasdk_txt_normal));
            }
        });
        tab_sticker.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                effect_listview.setVisibility(View.INVISIBLE);
//                mSeekBar.setVisibility(View.INVISIBLE);
                sticker_listview.setVisibility(View.VISIBLE);
                tab_effect.setTextColor(getResources().getColor(R.color.camerasdk_txt_normal));
                tab_sticker.setTextColor(getResources().getColor(R.color.camerasdk_txt_selected));
            }
        });
        txt_cropper.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                complate();
            }
        });
        btn_reset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO 重置
                ((EfectFragment) fragments.get(current)).resetBitMap();
                eAdapter.setSelectItem(0);

                Filter_Effect_Info info = effect_list.get(0);
//                GPUImageFilter filter = ImageFilterTools.createFilterForType(mContext, info.getFilterType());
                ((EfectFragment) fragments.get(current)).addEffect(info.getFilterType(), 0);

                PhotoEnhanceActivity.initStaticValue();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((EfectFragment) fragments.get(current)).refectFilter();
                    }
                }, 200);
            }
        });

        effect_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                eAdapter.setSelectItem(arg2);

                final int tmpint = arg2;
                final int tmpitem = arg1.getWidth();
                final float m = mScreenWidth / ((float) (tmpitem * 2));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        effect_listview.scrollTo(tmpitem * (tmpint) - (int) (tmpitem * (m - 0.5f)));
                    }
                }, 200);

                Filter_Effect_Info info = effect_list.get(arg2);
//                GPUImageFilter filter = ImageFilterTools.createFilterForType(mContext, info.getFilterType());
                ((EfectFragment) fragments.get(current)).addEffect(info.getFilterType(), arg2);
            }
        });

        sticker_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                UtilItemBean utilItemBean = utilList.get(arg2);
                Constants.bitmap = ((EfectFragment) fragments.get(current)).getCurrentBitMap();

                if (arg2 == 0) {
//                     TODO 裁剪图片

                    Intent intent = new Intent();
                    intent.setClassName(getApplication(), "com.temp.cam.camerasdk.CutActivity");
                    startActivityForResult(intent, Constants.RequestCode_Croper);
                } else if (arg2 == 1) {
                    // TODO 旋转
                    Intent intent = new Intent();
                    intent.setClassName(getApplication(), "com.temp.cam.camerasdk.RotateActivity");
                    startActivityForResult(intent, Constants.RequestCode_Croper);
                } else if (arg2 == 2) {
                    // TODO 调节
                    Intent intent = new Intent();
                    intent.setClassName(getApplication(), "com.temp.cam.camerasdk.PhotoEnhanceActivity");
                    startActivityForResult(intent, Constants.RequestCode_Ebhance);
                } else if (arg2 == 3) {
                    // TODO 提取
                    Intent intent = new Intent();
                    intent.setClassName(getApplication(), "com.temp.cam.camerasdk.PhotoExtractActivity");
                    startActivityForResult(intent, Constants.RequestCode_Croper);
                }else if (arg2 == 4) {
                    // TODO 扫描
                    Intent intent = new Intent();
                    intent.setClassName(getApplication(), "com.temp.cam.camerasdk.PhotoScanActivity");
                    startActivityForResult(intent, Constants.RequestCode_Croper);
                }


            }
        });
        images_listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position, false);
                fragments.get(position).onResume();
                fragments.get(current).onPause();
                current = position;

                iAdapter.setSelected(position);
                final int tmpint = position;
                final int tmpitem = view.getWidth();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        images_listview.scrollTo(tmpitem * (tmpint - 1) - tmpitem / 4);
                    }
                }, 200);

            }
        });

    }

    private void initData() {

        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < imageList.size(); i++) {
            EfectFragment ef1 = EfectFragment.newInstance(imageList.get(i));
            fragments.add(ef1);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Constants.sourceBitmap = Glide.with(FilterImageActivity.this)
                            .load(Uri.fromFile(new File(imageList.get(0))))
                            .asBitmap()
                            .centerCrop()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        Glide.with(FilterImageActivity.this).load(Uri.fromFile(new File(imageList.get(0)))).into(Constants.sourceBitmap);

        fAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mViewPager, fragments);
        mViewPager.setAdapter(fAdapter);
        mViewPager.setCurrentItem(0);
        //mViewPager.setOffscreenPageLimit(imageList.size());

        effect_list = FilterUtils.getEffectList();
//		stickerList=FilterUtils.getStickerList();
        utilList = FilterUtils.getUtilList();

        iAdapter = new SmallThumbAdapter(mContext, imageList);
        eAdapter = new Filter_Effect_Adapter(this, effect_list);
        sAdapter = new Filter_Utils_Adapter(this, utilList);

        images_listview.setAdapter(iAdapter);
        iAdapter.setSelected(0);
        effect_listview.setAdapter(eAdapter);
        sticker_listview.setAdapter(sAdapter);

    }


    // 完成
    private void complate() {

        loading_layout.setVisibility(View.VISIBLE);
        complate_runnable(3 * 1000);

    }

    private void complate_runnable(long delayMillis) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                ArrayList<String> list = new ArrayList<String>();
                if (mCameraSdkParameterInfo.getRet_type() == 0) {
                    //返回一个路径
                    for (int i = 0; i < imageList.size(); i++) {
                        Fragment mFragment = fragments.get(i);
                        if (mFragment.isAdded()) {
                            String path = ((EfectFragment) fragments.get(i)).getFilterImage();
                            list.add(path);
                        } else {
                            list.add(imageList.get(i));
                        }
                    }
                } else {
                    //保存bitmap
                    CameraSdkParameterInfo.bitmap_list.clear();
                    for (int i = 0; i < imageList.size(); i++) {
                        Fragment mFragment = fragments.get(i);
                        if (mFragment.isAdded()) {
                            Bitmap bitmap = ((EfectFragment) fragments.get(i)).getFilterBitmap();
                            CameraSdkParameterInfo.bitmap_list.add(bitmap);
                        }
                    }
                }

                //如果是网络图片则直接返回
                if (mCameraSdkParameterInfo.is_net_path()) {

                    Bundle b = new Bundle();
                    b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);

                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mCameraSdkParameterInfo.setImage_list(list);
                    ResultActivity.startActivity(FilterImageActivity.this, mCameraSdkParameterInfo);
//					PhotoPickActivity.instance.getFilterComplate(list);
                }


                finish();

            }
        };
        mHandler.postDelayed(runnable, delayMillis);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.RequestCode_Croper) {
            //截图返回
            ((EfectFragment) fragments.get(current)).refreshAttacher();
            ((EfectFragment) fragments.get(current)).setBitMap();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((EfectFragment) fragments.get(current)).refectFilter();
                }
            }, 1000);
        }else if(resultCode == Constants.RequestCode_Ebhance){
            //截图返回
            ((EfectFragment) fragments.get(current)).refreshAttacher();
            ((EfectFragment) fragments.get(current)).setBitMap();

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((EfectFragment) fragments.get(current)).refreshEffect();
                    ((EfectFragment) fragments.get(current)).refectFilter();
                }
            }, 1000);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
