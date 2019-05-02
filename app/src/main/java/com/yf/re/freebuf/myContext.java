package com.yf.re.freebuf;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by lenovo on 2016/11/25.
 */

public class myContext extends Application {
    File cache;
    public void onCreate() {
        super.onCreate();
        cache=new File(Environment.getExternalStorageDirectory(),"cache");
        if(!cache.exists()){
            cache.mkdir();
        }
        initImageConfigation();
    }

    public void initImageConfigation(){
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading).build();

        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration
                .Builder(this)
                .diskCache(new UnlimitedDiskCache(cache))
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
