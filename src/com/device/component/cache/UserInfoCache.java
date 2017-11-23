package com.device.component.cache;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.core.cache.Cache;

public class UserInfoCache {
	 /**
     * url请求路径
     */
    private static final String CACHE_PATH = "/dm/urlInfo/";
    
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
    public synchronized Object addUserInfo(String userId, UserInfoCatcheDto dto) {
        return cache.attach(CACHE_PATH + userId, dto);
    }

    /**
     * 删除 urlType 对应缓存
     * <功能详细描述>
     * @param urlType
     * @return
     * @see [类、类#方法、类#成员]
     */
    public synchronized Object deleteUserInfo(String userId) {
        return cache.dettach(CACHE_PATH + userId);
    }

    /**
     * 获取 urlType 对应缓存
     * <功能详细描述>
     * @param urlType
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
	public synchronized UserInfoCatcheDto getUserInfo(String userId) {
        return (UserInfoCatcheDto) cache.getObject(CACHE_PATH + userId);
    }
}
