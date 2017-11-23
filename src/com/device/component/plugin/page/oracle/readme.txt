<plugins>  
       	<plugin interceptor="org.mybatis.mysql.page.OffsetLimitInterceptor"/> 
</plugins>
Please confige file config.xml like above, if you use database Mysql.

=======================================================================================

<plugins> 
	<plugin interceptor="org.mybatis.orcl.page.OraclePageInterceptor" />   	 
</plugins>
Please confige file config.xml like above, if you use database Oracle.