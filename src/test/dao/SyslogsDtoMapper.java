package test.dao;

import test.model.SyslogsDto;

public interface SyslogsDtoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SyslogsDto record);

    int insertSelective(SyslogsDto record);

    SyslogsDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SyslogsDto record);

    int updateByPrimaryKey(SyslogsDto record);
}