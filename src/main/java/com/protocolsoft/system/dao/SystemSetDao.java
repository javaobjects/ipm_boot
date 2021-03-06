/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemSetDao.java
 *创建人: www    创建时间: 2017年12月28日
 */
package com.protocolsoft.system.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SystemSetDao
 * @Description: 系统设置DAO
 * @author www
 *
 */
@Repository
public interface SystemSetDao {

    /**
     * 
     * @Title: trunTableData
     * @Description: 清空表数据
     * @param name
     * @return boolean
     * @author www
     */
    @Delete("delete from ${name}")
    boolean trunTableData(@Param("name") String name);
    
    /**
     * 
     * @Title: selectTablesData
     * @Description: 查询粒度表中的第一张表
     * @param name 粒度表名称
     * @return String
     * @author wangjianmin
     */
    @Select("SELECT start FROM ${name} where table_id <= 1")
    String selectTablesData(@Param("name") String name);
}
