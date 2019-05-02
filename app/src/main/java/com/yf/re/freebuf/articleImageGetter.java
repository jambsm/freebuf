package com.yf.re.freebuf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by lenovo on 2017/3/24.
 */

public class articleImageGetter implements Html.ImageGetter {
    Context c;
    TextView container;
    int width;
    public articleImageGetter(TextView t,Context c) {
        this.c=c;
        this.container=t;
        width=c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
       final UrlDrable urlDrable=new UrlDrable();
        ImageLoader.getInstance().loadImage(source,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                float scaleWidth=(float)width/loadedImage.getWidth();
                Matrix matrix=new Matrix();
                matrix.postScale(scaleWidth,scaleWidth);
                loadedImage=Bitmap.createBitmap(loadedImage,0,0,loadedImage.getWidth(),loadedImage.getHeight(),matrix,true);
                urlDrable.bitmap=loadedImage;
                urlDrable.setBounds(0,0,loadedImage.getWidth(),loadedImage.getHeight());
                container.invalidate();
                container.setText(container.getText());
            }
        });
        return urlDrable;
    }

    public class UrlDrable extends BitmapDrawable{
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {

            if(bitmap!=null){
                canvas.drawBitmap(bitmap,0,0,getPaint());
            }
        }
    }
}
