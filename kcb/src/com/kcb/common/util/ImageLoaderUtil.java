package com.kcb.common.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.kcb.common.application.KApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 
 * @className: ImageLoaderUtil
 * @description: 加载网络图片使用的是UIL框架，启动app的时候，在KApplication中初始化此框架；
 * @author: wanghang
 * @date: 2015-7-11 下午6:31:34
 */
public class ImageLoaderUtil {

    /**
     * UIL的初始化，在KApplication的onCreate()中调用；
     */
    public static void init() {
        // 创建ImageLoader的默认配置参数
        // ImageLoaderConfiguration configuration =
        // ImageLoaderConfiguration.createDefault(KApplication.getInstance());
        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration.Builder(KApplication.getInstance())
                // 缓存图片的最大宽度、最大高度，默认是手机屏幕的宽度、高度，建议使用默认值；
                // .memoryCacheExtraOptions(480, 800)
                // 磁盘存储图片的最大宽度、最大高度、存储之前可以对图片进行处理，如果为null表示不做处理；
                // .diskCacheExtraOptions(480, 800, null)
                // 设置加载、显示图片的线程池，如果设置了此项，那么后面的设置线程池大小、优先级、处理顺序方法与此线程池无关；
                // .taskExecutor(Executors.newCachedThreadPool())
                // 设置显示磁盘存储的图片的线程池，可以和.taskExecutor()设置成同一个线程池,
                // 如果设置了此项，那么后面的设置线程池大小、优先级、处理顺序方法与此线程池无关；
                // .taskExecutorForCachedImages(Executors.newCachedThreadPool())
                // UIL的线程池大小，默认为3；
                // .threadPoolSize(3)
                // UIL的线程池中线程优先级，默认为Thread.NORM_PRIORITY-1
                // .threadPriority(Thread.NORM_PRIORITY - 1)
                // UIL的线程池处理任务顺序，默认为FIFO；
                // .tasksProcessingOrder(QueueProcessingType.FIFO)
                // 禁止缓存同一个图片的不同大小
                // .denyCacheImageMultipleSizesInMemory()
                // 缓存管理，如果设置了此项，那么后面的.memoryCacheSize()将无效
                // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                // 设置缓存大小
                // .memoryCacheSize(2 * 1024 * 1024)
                // 缓存大小占可以内存的百分比，默认是13
                // .memoryCacheSizePercentage(13)
                // 设置磁盘缓存，cacheDir为磁盘缓存路径
                // .diskCache(new UnlimitedDiskCache(cacheDir))
                // 设置磁盘缓存大小，默认没有限制
                // .diskCacheSize(50 * 1024 * 1024)
                // 磁盘缓存能够存储的最多文件数量，默认没有限制
                // .diskCacheFileCount(100)
                // 磁盘缓存的文件名字管理者，默认是HashCodeFileNameGenerator
                // .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // 图片下载器，默认为BaseImageDownloader
                // .imageDownloader(new BaseImageDownloader(KApplication.getInstance()))
                // 图片解码器，解析图片流，构造方法的参数——是否允许将调试日志写入LogCat
                // .imageDecoder(new BaseImageDecoder(false))
                // 图片显示选项，用于displayImage()方法中的参数，默认是DisplayImageOptions.createSimple()
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // 允许细节日志工作
                // .writeDebugLogs()
                        .build();
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * UIL的用法实例1
     */
    private ImageView imageView;
    private String url;

    private void useUIL1() {
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {}

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {}
        });
    }

    private void useUIL2() {
        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageView.setImageBitmap(loadedImage);
            }
        });
    }
}
