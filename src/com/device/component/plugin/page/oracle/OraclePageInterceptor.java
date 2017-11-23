package com.device.component.plugin.page.oracle;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.agile.erms.component.plugin.page.mysql.ErmsDialect;

/**
 * 标识：OraclePageInterceptor
 * 主题：Oracle实现Interceptor
 * 描述：Oracle重写基类实现内部拦截接口
 * 作者：geek
 * 修改日志
 * 日期           修改人         版本              修改原因及内容
 * 2012.07.31    geek         @version 1.0        创建
 */
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class OraclePageInterceptor implements Interceptor {

    /**
     * Oracle拦截方法实现，创建statementHandler对象，连接源sql生成新的sql
     */
    public Object intercept(Invocation invocation) throws Throwable {
        //创建OracleDialect对象dialect
        ErmsDialect dialect = new OracleDialect();

        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler
                .getValue("delegate.parameterHandler");
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds
                .getOffset(), rowBounds.getLimit()));
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        BoundSql boundSql = statementHandler.getBoundSql();
        //        System.out.println("生成分页SQL : " + boundSql.getSql());
        return invocation.proceed();
    }

    /**
     * 重写plugin方法
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 重写setProperties方法
     */
    public void setProperties(Properties arg0) {

    }

}