package com.device.component.cache;

import com.device.core.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlFilterCache {
    /**
     * url请求路径
     */
    private static final String CACHE_PATH = "/dm/urlFilter/";

    @Autowired
    private Cache cache;

    /**
     * 添加 urlType 缓存记录
     * <功能详细描述>
     * @param urlType
     * @param cacheDto
     * @return
     * @see [类、类#方法、类#成员]
     */
    public synchronized Object addUrl(String urlType, UrlFilterCacheDto cacheDto) {
        return cache.attach(CACHE_PATH + urlType, cacheDto);
    }

    /**
     * 删除 urlType 对应缓存
     * <功能详细描述>
     * @param urlType
     * @return
     * @see [类、类#方法、类#成员]
     */
    public synchronized Object deleteUrl(String urlType) {
        return cache.dettach(CACHE_PATH + urlType);
    }

    /**
     * 获取 urlType 对应缓存
     * <功能详细描述>
     * @param urlType
     * @return
     * @see [类、类#方法、类#成员]
     */
    public synchronized UrlFilterCacheDto getUrl(String urlType) {
        return (UrlFilterCacheDto) cache.getObject(CACHE_PATH + urlType);
    }
}
