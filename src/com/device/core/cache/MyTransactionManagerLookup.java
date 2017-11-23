/*
 * 
 *  @(#)MyTransactionManagerLookup.java Created on 2011-4-11
 * Copyright 2004-2005 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description 
 * 
 * CopyrightVersion 
 *
 */
package com.device.core.cache;

import javax.transaction.TransactionManager;

import org.jboss.cache.transaction.BatchModeTransactionManager;
import org.jboss.cache.transaction.TransactionManagerLookup;

/**
 * 
 * 自定时事务处理类
 * 
 * @author geek
 * @version [版本号, 2012-12-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyTransactionManagerLookup implements TransactionManagerLookup {
    /**
     * 重写父类方法
     * 
     * @return TransactionManager TransactionManager
     */

    public TransactionManager getTransactionManager() {
        return new BatchModeTransactionManager();
    }

}
