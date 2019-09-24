/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.temp.cam.camerasdk.library.filter.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.temp.cam.camerasdk.library.filter.GPUImage3x3ConvolutionFilter;
import com.temp.cam.camerasdk.library.filter.GPUImage3x3TextureSamplingFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageAddBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageAlphaBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageBrightnessFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageChromaKeyBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageColorBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageColorBurnBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageColorDodgeBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageColorInvertFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageContrastFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageDarkenBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageDifferenceBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageDirectionalSobelEdgeDetectionFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageDissolveBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageDivideBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageEmbossFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageExclusionBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageExposureFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageFilterGroup;
import com.temp.cam.camerasdk.library.filter.GPUImageGammaFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageGrayscaleFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageHardLightBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageHighlightShadowFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageHueBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageHueFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageLightenBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageLinearBurnBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageLookupFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageLuminosityBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageMonochromeFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageMultiplyBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageNormalBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageOpacityFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageOverlayBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImagePixelationFilter;
import com.temp.cam.camerasdk.library.filter.GPUImagePosterizeFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageRGBFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSaturationBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSaturationFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageScreenBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSepiaFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSharpenFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSmoothToonFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSobelEdgeDetection;
import com.temp.cam.camerasdk.library.filter.GPUImageSobelEdgeDetectionFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSoftLightBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSourceOverBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageSubtractBlendFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageToneCurveFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageTwoInputFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageVignetteFilter;
import com.temp.cam.camerasdk.library.filter.GPUImageWhiteBalanceFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IF1977Filter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFAmaroFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFBrannanFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFEarlybirdFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFHefeFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFHudsonFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFInkwellFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFLomoFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFLordKelvinFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFNashvilleFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFRiseFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFSierraFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFSutroFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFToasterFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFValenciaFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFWaldenFilter;
import com.temp.cam.camerasdk.library.filter.ifilter.IFXprollFilter;
import com.temp.cam.camerasdk.library.utils.MResource;

import java.util.LinkedList;
import java.util.List;

public class ImageFilterTools {

    public static GPUImageFilter createFilterForType(final Context context, final FilterType type) {
        switch (type) {
            case NORMAL:
                return new GPUImageFilter();
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();
//                GPUImageSoftLightBlendFilter gpuImageSoftLightBlendFilter = new GPUImageSoftLightBlendFilter();//
//                GPUImageBilateralBlurFilter gpuImageSoftLightBlendFilter = new GPUImageBilateralBlurFilter();//双边模糊
//                GPUImageSmoothToonFilter gpuImageSmoothToonFilter = new GPUImageSmoothToonFilter();//以平滑噪声 卡通模糊
//                gpuImageSmoothToonFilter.setBlurSize(0.1f);
                GPUImageSobelEdgeDetectionFilter gPUImageSobelEdgeDetectionFilter = new GPUImageSobelEdgeDetectionFilter();//Sobel边缘检测，边缘以白色突出显示
                GPUImageSaturationFilter gPUImageSaturationFilter = new GPUImageSaturationFilter(1.5f);//饱和度
                GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();//锐化
                sharpness.setSharpness(2.0f);
//                GPUImageSketchFilter gPUImageSketchFilter = new GPUImageSketchFilter();//颜色反转
                GPUImageOverlayBlendFilter gPUImageOverlayBlendFilter = new GPUImageOverlayBlendFilter();//叠加
                gPUImageSobelEdgeDetectionFilter.addFilter(gPUImageSaturationFilter);
                gpuImageFilterGroup.addFilter(gPUImageSobelEdgeDetectionFilter);




                gpuImageFilterGroup.addFilter(sharpness);
                gpuImageFilterGroup.addFilter(gPUImageOverlayBlendFilter);
//                gpuImageFilterGroup.addFilter(fPUImageDirectionalSobelEdgeDetectionFilter);
                return gpuImageFilterGroup;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImage3x3ConvolutionFilter convolution = new GPUImage3x3ConvolutionFilter();
                convolution.setConvolutionKernel(new float[] {
                        -1.0f, 0.0f, 1.0f,
                        -2.0f, 0.0f, 2.0f,
                        -1.0f, 0.0f, 1.0f
                });
                return convolution;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                List<GPUImageFilter> filters = new LinkedList<GPUImageFilter>();
                filters.add(new GPUImageContrastFilter());
                filters.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                filters.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(filters);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
            	return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
            	return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);  
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);  
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);    
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[] {0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                GPUImageToneCurveFilter toneCurveFilter = new GPUImageToneCurveFilter();
                toneCurveFilter.setFromCurveFileInputStream(context.getResources().openRawResource(MResource.getIdByName(context,MResource.raw, "tone_cuver_sample")));
                //toneCurveFilter.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.tone_cuver_sample));
                return toneCurveFilter;
            case BLEND_DIFFERENCE:
                return createBlendFilter(context, GPUImageDifferenceBlendFilter.class);
            case BLEND_SOURCE_OVER:
                return createBlendFilter(context, GPUImageSourceOverBlendFilter.class);
            case BLEND_COLOR_BURN:
                return createBlendFilter(context, GPUImageColorBurnBlendFilter.class);
            case BLEND_COLOR_DODGE:
                return createBlendFilter(context, GPUImageColorDodgeBlendFilter.class);
            case BLEND_DARKEN:
                return createBlendFilter(context, GPUImageDarkenBlendFilter.class);
            case BLEND_DISSOLVE:
                return createBlendFilter(context, GPUImageDissolveBlendFilter.class);
            case BLEND_EXCLUSION:
                return createBlendFilter(context, GPUImageExclusionBlendFilter.class);


            case BLEND_HARD_LIGHT:
                return createBlendFilter(context, GPUImageHardLightBlendFilter.class);
            case BLEND_LIGHTEN:
                return createBlendFilter(context, GPUImageLightenBlendFilter.class);
            case BLEND_ADD:
                return createBlendFilter(context, GPUImageAddBlendFilter.class);
            case BLEND_DIVIDE:
                return createBlendFilter(context, GPUImageDivideBlendFilter.class);
            case BLEND_MULTIPLY:
                return createBlendFilter(context, GPUImageMultiplyBlendFilter.class);
            case BLEND_OVERLAY:
                return createBlendFilter(context, GPUImageOverlayBlendFilter.class);
            case BLEND_SCREEN:
                return createBlendFilter(context, GPUImageScreenBlendFilter.class);
            case BLEND_ALPHA:
                return createBlendFilter(context, GPUImageAlphaBlendFilter.class);
            case BLEND_COLOR:
                return createBlendFilter(context, GPUImageColorBlendFilter.class);
            case BLEND_HUE:
                return createBlendFilter(context, GPUImageHueBlendFilter.class);
            case BLEND_SATURATION:
                return createBlendFilter(context, GPUImageSaturationBlendFilter.class);
            case BLEND_LUMINOSITY:
                return createBlendFilter(context, GPUImageLuminosityBlendFilter.class);
            case BLEND_LINEAR_BURN:
                return createBlendFilter(context, GPUImageLinearBurnBlendFilter.class);
            case BLEND_SOFT_LIGHT:
                return createBlendFilter(context, GPUImageSoftLightBlendFilter.class);
            case BLEND_SUBTRACT:
                return createBlendFilter(context, GPUImageSubtractBlendFilter.class);
            case BLEND_CHROMA_KEY:
                return createBlendFilter(context, GPUImageChromaKeyBlendFilter.class);
            case BLEND_NORMAL:
                return createBlendFilter(context, GPUImageNormalBlendFilter.class);

            case LOOKUP_AMATORKA:
                GPUImageLookupFilter amatorka = new GPUImageLookupFilter();
                amatorka.setBitmap(BitmapFactory.decodeResource(context.getResources(), MResource.getIdByName(context,MResource.drawable, "lookup_amatorka")));
                //amatorka.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.lookup_amatorka));
                return amatorka;

            case I_1977:
                return new IF1977Filter(context);
            case I_AMARO:
                return new IFAmaroFilter(context);
            case I_BRANNAN:
                return new IFBrannanFilter(context);
            case I_EARLYBIRD:
                return new IFEarlybirdFilter(context);
            case I_HEFE:
                return new IFHefeFilter(context);
            case I_HUDSON:
                return new IFHudsonFilter(context);
            case I_INKWELL:
                return new IFInkwellFilter(context);
            case I_LOMO:
                return new IFLomoFilter(context);
            case I_LORDKELVIN:
                return new IFLordKelvinFilter(context);
            case I_NASHVILLE:
                return new IFNashvilleFilter(context);
            case I_RISE:
                return new IFRiseFilter(context);
            case I_SIERRA:
                return new IFSierraFilter(context);
            case I_SUTRO:
                return new IFSutroFilter(context);
            case I_TOASTER:
                return new IFToasterFilter(context);
            case I_VALENCIA:
                return new IFValenciaFilter(context);
            case I_WALDEN:
                return new IFWaldenFilter(context);
            case I_XPROII:
                return new IFXprollFilter(context);
            case TOON:
                return new GPUImageSmoothToonFilter();

            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    private static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> filterClass) {
        try {
            GPUImageTwoInputFilter filter = filterClass.newInstance();
            filter.setBitmap(BitmapFactory.decodeResource(context.getResources(), MResource.getIdByName(context,MResource.drawable, "ic_launcher")));
            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static enum FilterType {
        NORMAL, CONTRAST, GRAYSCALE, SHARPEN, SEPIA, SOBEL_EDGE_DETECTION, THREE_X_THREE_CONVOLUTION, FILTER_GROUP, EMBOSS, POSTERIZE, GAMMA, BRIGHTNESS, INVERT, HUE, PIXELATION,
        SATURATION, EXPOSURE, HIGHLIGHT_SHADOW, MONOCHROME, OPACITY, RGB, WHITE_BALANCE, VIGNETTE, TONE_CURVE, BLEND_COLOR_BURN, BLEND_COLOR_DODGE, BLEND_DARKEN, BLEND_DIFFERENCE,
        BLEND_DISSOLVE, BLEND_EXCLUSION, BLEND_SOURCE_OVER, BLEND_HARD_LIGHT, BLEND_LIGHTEN, BLEND_ADD, BLEND_DIVIDE, BLEND_MULTIPLY, BLEND_OVERLAY, BLEND_SCREEN, BLEND_ALPHA,
        BLEND_COLOR, BLEND_HUE, BLEND_SATURATION, BLEND_LUMINOSITY, BLEND_LINEAR_BURN, BLEND_SOFT_LIGHT, BLEND_SUBTRACT, BLEND_CHROMA_KEY, BLEND_NORMAL, LOOKUP_AMATORKA,
        I_1977, I_AMARO, I_BRANNAN, I_EARLYBIRD, I_HEFE, I_HUDSON, I_INKWELL, I_LOMO, I_LORDKELVIN, I_NASHVILLE, I_RISE, I_SIERRA, I_SUTRO,
        I_TOASTER, I_VALENCIA, I_WALDEN, I_XPROII, TOON
    }


    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        public FilterAdjuster(final GPUImageFilter filter) {
            if (filter instanceof GPUImageSharpenFilter) {
                adjuster = new SharpnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSepiaFilter) {
                adjuster = new SepiaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageContrastFilter) {
                adjuster = new ContrastAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGammaFilter) {
                adjuster = new GammaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBrightnessFilter) {
                adjuster = new BrightnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSobelEdgeDetection) {
                adjuster = new SobelAdjuster().filter(filter);
            } else if (filter instanceof GPUImage3x3TextureSamplingFilter) {
                adjuster = new GPU3x3TextureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageEmbossFilter) {
                adjuster = new EmbossAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHueFilter) {
                adjuster = new HueAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePosterizeFilter) {
                adjuster = new PosterizeAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePixelationFilter) {
                adjuster = new PixelationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSaturationFilter) {
                adjuster = new SaturationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageExposureFilter) {
                adjuster = new ExposureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHighlightShadowFilter) {
                adjuster = new HighlightShadowAdjuster().filter(filter);
            } else if (filter instanceof GPUImageMonochromeFilter) {
                adjuster = new MonochromeAdjuster().filter(filter);
            } else if (filter instanceof GPUImageOpacityFilter) {
                adjuster = new OpacityAdjuster().filter(filter);
            } else if (filter instanceof GPUImageRGBFilter) {
                adjuster = new RGBAdjuster().filter(filter);
            } else if (filter instanceof GPUImageWhiteBalanceFilter) {
                adjuster = new WhiteBalanceAdjuster().filter(filter);
            } else if (filter instanceof GPUImageVignetteFilter) {
                adjuster = new VignetteAdjuster().filter(filter);
            } else if (filter instanceof GPUImageDissolveBlendFilter) {
                adjuster = new DissolveBlendAdjuster().filter(filter);
            } else {
                adjuster = null;
            }
        }

        public void adjust(final int percentage) {
            if (adjuster != null) {
                adjuster.adjust(percentage);
            }
        }

        private abstract class Adjuster<T extends GPUImageFilter> {
            private T filter;

            @SuppressWarnings("unchecked")
            public Adjuster<T> filter(final GPUImageFilter filter) {
                this.filter = (T) filter;
                return this;
            }

            public T getFilter() {
                return filter;
            }

            public abstract void adjust(int percentage);

            protected float range(final int percentage, final float start, final float end) {
                return (end - start) * percentage / 100.0f + start;
            }

            protected int range(final int percentage, final int start, final int end) {
                return (end - start) * percentage / 100 + start;
            }
        }

        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
            }
        }

        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setPixel(range(percentage, 1.0f, 100.0f));
            }
        }

        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setHue(range(percentage, 0.0f, 360.0f));
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setContrast(range(percentage, 0.0f, 2.0f));
            }
        }

        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setGamma(range(percentage, 0.0f, 3.0f));
            }
        }

        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
            }
        }

        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 2.0f));
            }
        }

        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 4.0f));
            }
        }

        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            @Override
            public void adjust(final int percentage) {
                // In theorie to 256, but only first 50 are interesting
                getFilter().setColorLevels(range(percentage, 1, 50));
            }
        }

        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
            }
        }

        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setExposure(range(percentage, -10.0f, 10.0f));
            }
        }

        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setShadows(range(percentage, 0.0f, 1.0f));
                getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
            }
        }

        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 1.0f));
                //getFilter().setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            }
        }

        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setOpacity(range(percentage, 0.0f, 1.0f));
            }
        }

        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setRed(range(percentage, 0.0f, 1.0f));
                //getFilter().setGreen(range(percentage, 0.0f, 1.0f));
                //getFilter().setBlue(range(percentage, 0.0f, 1.0f));
            }
        }

        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
                //getFilter().setTint(range(percentage, -100.0f, 100.0f));
            }
        }

        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setVignetteStart(range(percentage, 0.0f, 1.0f));
            }
        }

        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setMix(range(percentage, 0.0f, 1.0f));
            }
        }
    }

}
