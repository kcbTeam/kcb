package com.kcb.common.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.kcb.common.application.KApplication;
import com.kcbTeam.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
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
                // UIL的线程池大小，默认为3，为了避免OOM推荐1-5
                // .threadPoolSize(3)
                // UIL的线程池中线程优先级，默认为Thread.NORM_PRIORITY-1
                // .threadPriority(Thread.NORM_PRIORITY - 1)
                // UIL的线程池处理任务顺序，默认为FIFO；
                // .tasksProcessingOrder(QueueProcessingType.FIFO)
                // 禁止缓存同一个图片的不同大小
                // .denyCacheImageMultipleSizesInMemory()
                // 缓存管理，如果设置了此项，那么后面的.memoryCacheSize()将无效
                        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                        // 为了避免OOM，推荐使用WeakMemoryCache或者不使用缓存
                        .memoryCache(new WeakMemoryCache())
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
                        // 允许显示具体日志
                        .writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);

        // 创建加载图片的选项
        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        // 设置加载过程中显示的图片，resId或者drawable，对于loadImage()无效
                        .showImageOnLoading(R.drawable.ic_launcher)
                        // 设置空url时候的图片，对于loadImage()无效
                        .showImageForEmptyUri(R.drawable.ic_launcher)
                        // 设置加载失败的图片，对于loadImage()无效
                        .showImageOnFail(R.drawable.ic_launcher)
                        // 加载之前重置imageaware，默认为false
                        .resetViewBeforeLoading(false)
                        // 加载之前延时，默认没有延时
                        .delayBeforeLoading(1000)
                        // 是否缓存加载的图片
                        .cacheInMemory(false)
                        // 是否存储加载的图片到磁盘
                        .cacheOnDisk(false)
                        // 图片在缓存之前会被预处理，即时不允许缓存
                        .preProcessor(null)
                        // 在缓存图片之后，显示图片之前，处理图片
                        .postProcessor(null)
                        //
                        // .extraForDownloader(extra)
                        // 是否考虑图片的exif信息
                        .considerExifParams(false)
                        // 当定义了解码图片的大小时，需要使用type，默认为ImageScaleType.IN_SAMPLE_POWER_OF_2
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        // 为了避免OOM，推荐使用下面的设置
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        // 图片解码配置，默认为Bitmap.Config.ARGB_8888，推荐Bitmap.Config.RGB_565，比默认的少消耗两倍内存
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        // 图片解码选项，会覆盖上方的.imageScaleType()设置
                        // .decodingOptions(decodingOptions)
                        // 设置图片显示器
                        .displayer(DefaultConfigurationFactory.createBitmapDisplayer())
                        // 设置显示图片和监听ImageLoadingListener接口的handler，默认为new Handler()
                        .handler(new Handler()).build();
    }

    /**
     * UIL的用法实例1
     */
    private ImageView imageView;
    private String url;

    /**
     * loadImage，监听所有过程；
     */
    private void useLoadImage1() {
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

    /**
     * loadImage，选择监听的过程；
     */
    private void useLoadImage2() {
        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageView.setImageBitmap(loadedImage);
            }
        });
    }

    /**
     * loadImage，指定图片大小；
     */
    private void useLoadImage3() {
        ImageSize imageSize = new ImageSize(480, 800);
        ImageLoader.getInstance().loadImage(url, imageSize, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageView.setImageBitmap(loadedImage);
            }
        });
    }

    /**
     * loadImage，指定图片显示选项
     */
    private void useLoadImage4() {
        ImageSize imageSize = new ImageSize(100, 100);
        DisplayImageOptions options =
                new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().loadImage(url, imageSize, options,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        imageView.setImageBitmap(loadedImage);
                    }
                });
    }

    /**
     * displayImage，指定图片选项
     */
    private void useDisplayImage1() {
        DisplayImageOptions options =
                new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
                        .showImageForEmptyUri(R.drawable.ic_launcher)
                        .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    /**
     * displayImage，指定图片选项，监听图片下载过程，显示图片下载进度
     */
    private void useDisplayImage2() {
        DisplayImageOptions options =
                new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
                        .showImageForEmptyUri(R.drawable.ic_launcher)
                        .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(url, imageView, options,
                new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {

                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        // 计算百分比
                        int percent = (int) ((float) current / (float) total);
                    }
                });
    }

    /**
     * displayImage，指定图片选项，加载不同来源的图片
     */
    private void useDisplayImage3() {
        DisplayImageOptions options =
                new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
                        .showImageForEmptyUri(R.drawable.ic_launcher)
                        .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        // 本地sd卡中的图片
        String imagePath = "/mnt/sdcard/image.png";
        String imageUrl1 = Scheme.FILE.wrap(imagePath);
        ImageLoader.getInstance().displayImage(imageUrl1, imageView, options);

        // ContentProvider中的图片
        String imageUrl2 = "content://media/external/audio/albumart/13";
        ImageLoader.getInstance().displayImage(imageUrl2, imageView, options);

        // assets中的图片

        String imageUrl3 = Scheme.ASSETS.wrap("image.png");
        ImageLoader.getInstance().displayImage(imageUrl3, imageView, options);

        // drawable中的图片
        String imageUrl4 = Scheme.DRAWABLE.wrap("R.drawable.ic_launcher");
        ImageLoader.getInstance().displayImage(imageUrl4, imageView, options);
    }

    /**
     * listview、gridview加载图片设置; 第一个参数ImageLoader，第二个参数列表滑动的过程中是否加载图片，手指在列表上的时候是否加载；
     */
    private void useListView() {
        ListView listView = new ListView(KApplication.getInstance());
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true,
                true));
    }
}
