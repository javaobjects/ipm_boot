/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UsabilityDao
 *创建人:wjm    创建时间:2018年3月16日
 */
package com.protocolsoft.usability.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.protocolsoft.usability.bean.UsabilityBean;

/**
 * 
 * @ClassName: UsabilityDao
 * @Description: 应用可用性Dao层
 * @author wangjianmin
 *
 */
@Repository
public interface UsabilityDao {
    
    /**
     * 
     * @Title: getUsabilityAll
     * @Description: 查询所有应用可用性设置
     * @return List<UsabilityBean>
     * @author wangjianmin
     */
    @Select("select id,name,ip,port,`interval`,`lastExecTime`,`status`,`des`,app_id as appId from ipm_usability_set")
    List<UsabilityBean> getUsabilityAll();
    
    /**
     * 
     * @Title: getSelectById
     * @Description: 根据id查询一条应用可用性设置
     * @param id 业务id
     * @return UsabilityBean
     * @author wangjianmin
     */
    @Select("select id,name,ip,port,`interval`,`lastExecTime`,`status`,`des`,app_id as appId "
            + "from ipm_usability_set where  id =#{id}")
    UsabilityBean getSelectById(@Param("id") int id);
    
    
    /**
     * 
     * @Title: getSelectById
     * @Description: 根据业务id查询一条应用可用性设置
     * @param id 业务id
     * @return UsabilityBean
     * @author wangjianmin
     */
    @Select("select id,name,ip,port,`interval`,`lastExecTime`,`status`,`des`,app_id as appId,busiId "
            + "from ipm_usability_set where  busiId =#{id}")
    List<UsabilityBean> getSelectByAppId(@Param("id") int id);
    
    /**
     * 
     * @Title: addUsability
     * @Description: 添加应用可用性设置
     * @param bean  接收参数
     * @return int
     * @author wangjianmin
     */
    @Insert("insert into ipm_usability_set(id,name, ip, port,`interval`,`lastExecTime`,`status`,`des`,app_id,busiId) "
            + "values(#{id},#{name},#{ip},#{port},#{interval},#{lastExecTime},#{status},#{des},#{appId},#{busiId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addUsability(UsabilityBean bean);
    
    /**
     * 
     * @Title: getByName
     * @Description: 验证名称有没有重复
     * @param name   业务名称
     * @param id     业务ID
     * @return boolean
     * @author wangjianmin
     */
    @Select("select count(1) from ipm_usability_set  where name=#{name} and id!=#{id}")
    boolean getByName(@Param("name") String name, @Param("id") int id);
    
    /**
     * 
     * @Title: getByPort
     * @Description: 验证端口是否重复
     * @param port   业务端口
     * @param id     业务ID
     * @return boolean
     * @author wangjianmin
     */
    @Select("select count(1) from ipm_usability_set  where port=#{port} and id!=#{id}")
    boolean getByPort(@Param("port") String port, @Param("id") int id);
    
    /**
     * 
     * @Title: delUsabilityId
     * @Description: 删除一条记录
     * @param id  业务ID
     * @return boolean
     * @author wangjianmin
     */
    @Delete("delete from ipm_usability_set where id=#{id}")
    boolean delUsabilityId(@Param("id") int id);
    
    /**
     * 
     * @Title: updateUsability
     * @Description: 修改一条记录
     * @param bean  接收参数
     * @return int
     * @author wangjianmin
     */
    @Update("update ipm_usability_set set name=#{name},ip=#{ip},port=#{port},`interval`=#{interval},`lastExecTime`=#{lastExecTime},"
            + "`status`=#{status},`des`=#{des},app_id=#{appId} where id=#{id}")
    int updateUsability(UsabilityBean bean);
    
    /**
     * 
     * @Title: updateTime
     * @Description: 修改上次执行时间
     * @param bean 接收参数
     * @return int
     * @author wangjianmin
     */
    @Update("update ipm_usability_set set `lastExecTime`=#{lastExecTime}  where id=#{id}")
    int updateTime(UsabilityBean bean);
}
