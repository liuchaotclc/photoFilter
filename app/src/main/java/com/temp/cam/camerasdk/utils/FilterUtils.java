package com.temp.cam.camerasdk.utils;

import com.temp.cam.R;
import com.temp.cam.camerasdk.library.filter.util.ImageFilterTools.FilterType;
import com.temp.cam.camerasdk.model.Filter_Effect_Info;
import com.temp.cam.example.model.UtilItemBean;

import java.util.ArrayList;


/**
 * 特效文件
 */
public class FilterUtils {

	
	
	/**
	 * 获取特效列表
	 * @return
	 */
	public static ArrayList<Filter_Effect_Info> getEffectList(){
		
		ArrayList<Filter_Effect_Info> effect_list = new ArrayList<Filter_Effect_Info>();
		
		effect_list.add(new Filter_Effect_Info("原图", R.drawable.camerasdk_filter_normal,FilterType.NORMAL));
		effect_list.add(new Filter_Effect_Info("黑白", R.drawable.camerasdk_filter_inkwell,FilterType.I_INKWELL));
//		effect_list.add(new Filter_Effect_Info("清晰", R.drawable.camerasdk_filter_inkwell,FilterType.SHARPEN));
		effect_list.add(new Filter_Effect_Info("创新", R.drawable.camerasdk_filter_in1977,FilterType.I_1977));
		effect_list.add(new Filter_Effect_Info("流年", R.drawable.camerasdk_filter_amaro,FilterType.I_AMARO));
		effect_list.add(new Filter_Effect_Info("淡雅", R.drawable.camerasdk_filter_brannan,FilterType.I_BRANNAN));
		effect_list.add(new Filter_Effect_Info("怡尚", R.drawable.camerasdk_filter_early_bird,FilterType.I_EARLYBIRD));
		effect_list.add(new Filter_Effect_Info("优格", R.drawable.camerasdk_filter_hefe,FilterType.I_HEFE));
		effect_list.add(new Filter_Effect_Info("胶片", R.drawable.camerasdk_filter_hudson,FilterType.I_HUDSON));
		effect_list.add(new Filter_Effect_Info("个性", R.drawable.camerasdk_filter_lomo,FilterType.I_LOMO));
		effect_list.add(new Filter_Effect_Info("回忆", R.drawable.camerasdk_filter_lord_kelvin,FilterType.I_LORDKELVIN));
		effect_list.add(new Filter_Effect_Info("不羁", R.drawable.camerasdk_filter_nashville,FilterType.I_NASHVILLE));

		return effect_list;
		
	}


	/**
	 * 获取特效列表
	 * @return
	 */
	public static ArrayList<UtilItemBean> getUtilList(){

		ArrayList<UtilItemBean> effect_list = new ArrayList<UtilItemBean>();

		effect_list.add(new UtilItemBean("裁剪", R.drawable.camerasdk_filter_normal));
		effect_list.add(new UtilItemBean("旋转", R.drawable.camerasdk_filter_normal));
		effect_list.add(new UtilItemBean("调节", R.drawable.camerasdk_filter_normal));
		effect_list.add(new UtilItemBean("提取", R.drawable.camerasdk_filter_normal));
		effect_list.add(new UtilItemBean("扫描", R.drawable.camerasdk_filter_normal));
//		effect_list.add(new UtilItemBean("涂鸦", R.drawable.camerasdk_filter_normal));


		return effect_list;

	}

    
    
}
