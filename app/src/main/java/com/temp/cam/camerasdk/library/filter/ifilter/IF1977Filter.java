package com.temp.cam.camerasdk.library.filter.ifilter;

import android.content.Context;

import com.temp.cam.camerasdk.library.utils.MResource;


public class IF1977Filter extends com.temp.cam.camerasdk.library.filter.ifilter.IFImageFilter {
    private static final String SHADER = "precision lowp float;\n" +
            " \n" +
            " varying highp vec2 textureCoordinate;\n" +
            " \n" +
            " uniform sampler2D inputImageTexture;\n" +
            " uniform sampler2D inputImageTexture2;\n" +
            " \n" +
            " void main()\n" +
            " {\n" +
            "     \n" +
            "     vec3 texel = texture2D(inputImageTexture, textureCoordinate).rgb;\n" +
            "     \n" +
            "     texel = vec3(\n" +
            "                  texture2D(inputImageTexture2, vec2(texel.r, .16666)).r,\n" +
            "                  texture2D(inputImageTexture2, vec2(texel.g, .5)).g,\n" +
            "                  texture2D(inputImageTexture2, vec2(texel.b, .83333)).b);\n" +
            "     \n" +
            "     gl_FragColor = vec4(texel, 1.0);\n" +
            " }\n";

    public IF1977Filter(Context paramContext) {
        super(paramContext, SHADER);
        setRes(paramContext);
    }

    private void setRes(Context context) {
    	
    	int resId1=MResource.getIdByName(context,MResource.drawable, "nmap");
    	int resId2=MResource.getIdByName(context,MResource.drawable, "nblowout");
    	addInputTexture(resId1);
        addInputTexture(resId2);
        
        //addInputTexture(R.drawable.nmap);
        //addInputTexture(R.drawable.nblowout);
        
        
    }
}
