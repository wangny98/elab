/*
 * 
 *  @(#)Cache.java Created on 2011-4-1
 *
 * Copyright 2004-2011 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description：用于存储POJO对象的Cache操作 
 *
 * Version：          1.0
 *
 */
package com.device.core.cache;

import java.util.Map;

import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.pojo.PojoCache;
import org.jboss.cache.pojo.PojoCacheFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.device.util.CreateRandom;

/** 
 * 
 * 缓存中对象的增加、删除、获取
 * 初始化缓存，实现增加对象到缓存中，删除缓存中的对象、获取缓存中的单个对象
 * 和制定路径下的所有对象
 * 
 * @author  geek
 * @version  [版本号, 2012-12-12] 
 * @see  [相关类/方法] 
 * @since  version 1.0
 */
@Component
@Scope("singleton")
public class Cache {
    private PojoCache pojoCache;

    /**
     * 初始化，用于创建PojoCache
     */
    @SuppressWarnings("unchecked")
    public Cache() {

        pojoCache = PojoCacheFactory.createCache("pojoCacheConfig.xml", false);
        final org.jboss.cache.Cache jbossCache = pojoCache.getCache();
        final Configuration con = jbossCache.getConfiguration();

        final int intIndex = CreateRandom.getRandomInt();
        con.setClusterName("JBossCache-Cluster_ErmsServer_" + intIndex);
        //        con.setClusterName("JBossCache-Cluster_Erms");
        pojoCache.start();

    }

    /**
     * 向Cache中添加指定的对象
     * @param id   添加对象的路径，一般采用"/ecc/registeruser/huangfeihong"类似的格式
     * @param pojo 需要添加到Cache中的对象
     * @return     返回在id这个路径下面的原先对象，如果原来没有这个对象，则返回为null
     */
    public Object attach(String id, Object pojo) {
        return pojoCache.attach(id, pojo);
    }

    /**
     * 删除缓存中指定路径下的对象
     * @param id 对象的路径，一般采用"/ecc/registeruser/huangfeihong"类似的格式
     * @return Cache中的对象
     */
    public Object dettach(String id) {
        return pojoCache.detach(id);
    }

    /**
     * 获取缓存中指定路径下的对象
     * @param id 对象的路径，一般采用"/ecc/registeruser/huangfeihong"类似的格式
     * @return Cache中的对象
     */
    public Object getObject(String id) {
        return pojoCache.find(id);
    }

    /**
     * 获取缓存中指定路径下的所有对象
     * @param id 对象的路径 ,一般采用"/ecc/registeruser/huangfeihong"类似的格式
     * @return Cache中的对象集
     */
    public Map<Fqn<?>, Object> getAllObject(String id) {
        return pojoCache.findAll(id);
    }
}
