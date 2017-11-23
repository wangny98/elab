package com.device.component.listener;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.filter.URLFilterHelper;




public class InitServerListener {

    @Autowired
    URLFilterHelper urlFilterHelper;

    /**
     * 初始化函数
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see [类、类#方法、类#成员]
     */
    public void init() throws IllegalAccessException, InvocationTargetException {


        //增加URL缓存
        urlFilterHelper.pushAllUrl();


    }


}
