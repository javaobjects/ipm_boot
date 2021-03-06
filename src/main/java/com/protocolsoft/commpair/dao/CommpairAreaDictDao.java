/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommpairAreaDictDao.java
 *创建人: chensq    创建时间: 2018年6月29日
 */
package com.protocolsoft.commpair.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.commpair.bean.CommpairAreaDictBean;

/**
 * @ClassName: CommpairAreaDictDao
 * @Description: ipm_area_dict地址名称表
 * @author chensq
 */
@Repository
public interface CommpairAreaDictDao {
    
    /**
     * 
     * @Title: getCommpairAreaDictById
     * @Description: 根据id查询地址名称
     * @param id 
     * @return CommpairAreaDictBean
     * @author chensq
     */
    @Select("SELECT "
            + "id as id ,"
            + "level_id as levelId ,"
            + "level_name as levelName ,"
            + "parent_id as parentId ,"
            + "name_cn as nameCn "
            + "FROM "
            + "ipm_area_dict "
            + "WHERE "
            + "id =#{id} ")
    CommpairAreaDictBean getCommpairAreaDictById(long id);
    
}
