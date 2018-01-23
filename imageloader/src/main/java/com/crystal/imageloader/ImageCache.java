package com.crystal.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by GanJinjin on 2018/1/23.
 */

public class ImageCache {

    //使用V4包的LruCache，兼容3.1之前的Android版本
    private LruCache<String, Bitmap> mLruCache;

    public ImageCache() {
        initImageCache();
    }

    private void initImageCache() {
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 1024;//单位转换为KB
        int cacheSize = maxSize / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //计算Bitmap对象的大小
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                //移除缓存时调用此方法，可进行相应的资源回收操作
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }

    public void put(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        return mLruCache.get(url);
    }
}
