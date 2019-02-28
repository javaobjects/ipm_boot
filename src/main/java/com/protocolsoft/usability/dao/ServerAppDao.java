/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ServerAppDao.java
 *创建人: wangjianmin    创建时间: 2018年9月13日
 */
package com.protocolsoft.usability.dao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ServerAppDao
 * @Description: server 添加应用可用性
 * @author wangjianmin
 *
 */
@Repository
public interface ServerAppDao {
    
    /**
     * 
     * @Title: getByBusiName
     * @Description:根据服务端业务id，查询对应业务名称
     * @param id  业务id
     * @return String
     * @author wangjianmin
     */
    @Select("SELECT `name` FROM ipm_app_business WHERE id = #{id}")
    String  getByBusiName(@Param("id") int id);
    
}
