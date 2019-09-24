# 源码导读

## <span id="工程概述">工程概述</span>
使用自定义相机(CameraActivity非系统相机)拍照，或者相册中选取图片(PhotoPickActivity)。选取图片后可进行滤镜(FilterImageActivity),调整(PhotoEnhanceActivity)等操作


## <span id="总体流程">总体流程</span>

图片滤镜的总体流程如下：
* 首页MainActivity(获取相机，存储权限)，在调用模块之前需要先获取相应权限
* 拍照 CameraActivity 支持前后摄像头切换，闪光灯操作，撤销
* 图片确定页 ImgEnsureActivity 支持重新拍照和重新从相册选取图片
* 相册页面 PhotoPickActivity 图片Grid展示，相册文件夹选取
* 滤镜页面 FilterImageActivity 底部三个tab，工具箱、一键美化、完成。
* 裁剪页面(工具箱)CutActivity 多种比例裁剪模式和自由裁剪模式。
* 旋转页面(工具箱)RotateActivity
* 调节页面(工具箱)FilterImageActivity 操作包含亮度、对比度、饱和度、锐化、高光、阴影
* 提取页面(工具箱)PhotoExtractActivity 使用黑白-查找边缘-反色 实现
* 提取页面(工具箱)PhotoScanActivity 使用亮度 曲线 实现
* 完成界面 ResultActivity

## <span id="主要第三方库">主要第三方库</span>

* 图片缩放 PhotoView
* 图片滤镜框架 GPUImage

所有第三方框架都需要定制修改，直接源码导入工程

### 工程结构说明

源码主要分成四个 package ：cameralibrary、camerasdk、photoview 和 example。
- cameralibrary：自定义相机。
- camerasdk：滤镜相关逻辑，界面全部在这里。
- photoview：图片预览框架。
- example：调用sdk事例。

下面具体介绍 camerasdk 包下的子包结构：
- 一级目录：所有 Activity。
- adapter：适配器。
- library: 滤镜算法。
- model：数据模型。
- fragment: 滤镜页面GPUImage容器
- utils: 相关工具。
- view：自定义view。
- ui: 直播间界面 ui 控件。

### 重点类说明

- FilterImageActivity : 滤镜操作,所有滤镜效果缓存于EfectFragment的sCurrentFilterType变量中,获取滤镜算法通过PhotoEnhanceActivity的静态方法getFilterGroup。
- PhotoEnhanceActivity：调节页面 缓存了调节的数值。
- EfectFragment: 呈现滤镜特效,具体方法文档中有详细注释