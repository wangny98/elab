package com.device.component.plugin.page.oracle;

import com.agile.erms.component.plugin.page.mysql.ErmsDialect;

/**
 * 标识：OracleDialect
 * 主题：Oracle数据库分页实现
 * 描述：生成Oracle分页的sql语句
 * 作者：geek
 * 修改日志
 * 日期           修改人         版本              修改原因及内容
 * 2012.07.31    geek         @version 1.0        创建
 */
public class OracleDialect implements ErmsDialect {

    /**
     * 根据传入的参数生成新的Oracle分页的sql语句
     * @param sql 原始sql语句
     * @param offset 开始条数
     * @param limit 每页显示数
     * @return 新的sql语句
     */
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        pagingSelect.append(sql);

        pagingSelect.append(" ) row_ ) where rownum_ > ").append(offset).append(" and rownum_ <= ").append(
                offset + limit);

        return pagingSelect.toString();
    }

    /**
     * 如果有offset值，则形成新的sql
     * @return null
     */
    public String getLimitString(String sql, boolean hasOffset) {
        return null;
    }

    /**
     * 判断是否限定每页显示数
     * @return true
     */
    public boolean supportsLimit() {
        return true;
    }

    /**
     * 判断是否有分页起始值和每页显示数
     * @return true
     */
    public boolean supportsLimitOffset() {
        return true;
    }

}