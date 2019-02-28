/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewDao
 *创建人:long    创建时间:2017年9月7日
 */
package com.protocolsoft.view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.view.bean.MonitorViewBean;

/**
 * MonitorViewDao
 * 2017年9月7日 上午9:55:43
 * @author long
 * @version
 * @see
 */
@Repository
public interface MonitorViewDao {
    /**
     * 駕駛艙信息
     * 2017年9月7日 上午10:00:14
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select m.id,m.name,m.descrption,m.createuserid as createUserId,m.updatetime as updateTime, "
            + " m.lockstatus as lockStatus,IFNULL(u.username,'admin') as userName "
            + " from ipm_monitor_view m left join ipm_system_user u on m.createuserid=u.id ")
    List<MonitorViewBean> getMonitorViewList();
    
    /**
     * 
     * @Title: getSelectById
     * @Description: 查询一个驾驶舱
     * @param id 驾驶舱id
     * @return MonitorViewBean
     * @author wangjianmin
     */
    @Select("select * FROM ipm_monitor_view where id = #{id}")
    MonitorViewBean getSelectById(@Param("id") int id);
    
    /**
     * 根据没有的userId 存在的id获取驾驶舱
     * 2017年9月7日 下午2:30:26
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption,createuserid as createUserId,updatetime as updateTime,"
            + "lockstatus as lockStatus from ipm_monitor_view where createuserid !=#{userId} and id in (#{ids})")
    List<MonitorViewBean> getMonitorViewByIds(@Param("userId") int userId, @Param("ids") String ids);
    
    /**
     * 根据存在的id获取驾驶舱
     * 2017年11月6日 下午3:58:39
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption,createuserid as createUserId,updatetime as updateTime,"
            + "lockstatus as lockStatus from ipm_monitor_view where id in (${ids})")
    List<MonitorViewBean> getMonitorViewByOnlyIds(@Param("ids") String ids);
    
    /**
     * 根据没有的userId 不存在的id获取驾驶舱
     * 2017年9月7日 下午2:58:14
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption,createuserid as createUserId,updatetime as updateTime,"
            + "lockstatus as lockStatus from ipm_monitor_view where createuserid !=#{userId} and id not in (#{ids})")
    List<MonitorViewBean> getMonitorViewByNotIds(@Param("userId") int userId, @Param("ids") String ids);
    
    /**
     * 根据不存在的id获取驾驶舱
     * 2017年11月6日 下午3:59:26
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption,createuserid as createUserId,updatetime as updateTime,"
            + "lockstatus as lockStatus from ipm_monitor_view where id not in (${ids})")
    List<MonitorViewBean> getMonitorViewByOnlyNotIds(@Param("ids") String ids);
    
    /**
     * 根据userId或存在的id获取驾驶舱
     * 2017年9月12日 上午10:40:00
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption,createuserid as createUserId,updatetime as updateTime,"
            + "lockstatus as lockStatus from ipm_monitor_view where createuserid =#{userId} or id in (#{ids})")
    List<MonitorViewBean> getMonitorViewByUserId(@Param("userId") int userId, @Param("ids") String ids);
    
    /**
     * 添加驾驶舱
     * 2017年9月15日 下午1:55:53
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Insert("insert into ipm_monitor_view (name,descrption,createuserid,updatetime,lockstatus) "
            + " values (#{name}, #{descrption},#{createUserId},UNIX_TIMESTAMP(NOW()),#{lockStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addMonitorView(MonitorViewBean monitorViewBean);
    
    /**
     * 驾驶舱名是否存在
     * 2017年9月15日 下午2:01:59
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Select("select count(1) from ipm_monitor_view  where name=#{name} and id!=#{id}")
    boolean getMonitorViewCountByName(@Param("name") String name, @Param("id") int id);
    
    /**
     * 修改驾驶舱
     * 2017年9月15日 下午3:13:21
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update("update ipm_monitor_view set `name`=#{name},`descrption`=#{descrption},`updatetime`=UNIX_TIMESTAMP(NOW()), "
            + " `createuserid`=#{createUserId}, `lockstatus`=#{lockStatus}  where id=#{id} ")
    int updateMonitorViewById(MonitorViewBean monitorViewBean);
    
    /**
     * 删除驾驶舱
     * 2017年9月15日 下午4:08:09
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_monitor_view where id=#{id}")
    boolean delMonitorView(int id);
    
    /**
     *删除驾驶舱权限 
     * 2018年3月29日 下午1:56:21
     * @param
     * @return 
     * @exception 
     * @see
     */
    @Delete("DELETE from ipm_authorize_juris WHERE app_id=#{appId}")
    void delAuthMonitorView(int appId);
    
    /**
     * 通过userId删除驾驶舱
     * 2017年9月20日 下午1:47:26
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_monitor_view where createuserid=#{userId}")
    boolean delMonitorViewByUserId(int userId);
    
    /**
     * 根据id获取数据
     * 2017年11月6日 下午2:00:52
     * @param
     * @return MonitorViewBean
     * @exception 
     * @see
     */
    @Select("select m.id,m.name,m.descrption,m.createuserid as createUserId,m.updatetime as updateTime, "
            + " m.lockstatus as lockStatus,IFNULL(u.username,'admin') as userName "
            + " from ipm_monitor_view m left join ipm_system_user u on m.createuserid=u.id where m.id=#{id}")
    MonitorViewBean getMonitorViewListById(int id);
    
    /**
     * 
     * @Title: updateMonitorViewStatus
     * @Description: 修改状态
     * @param id 编号
     * @param status 状态
     * @return boolean
     * @author www
     */
    @Update("update ipm_monitor_view set lockStatus = #{status} where id = #{id}")
    boolean updateMonitorViewStatus(@Param("id") int id, @Param("status") int status);
    
    /**
     * 查询所有驾驶舱大于一百的
     * 2018年3月21日 下午2:26:46
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @Select("select m.id,m.name,m.descrption,m.createuserid as createUserId,m.updatetime as updateTime,"
            + "m.lockstatus as lockStatus,IFNULL(u.username,'admin') as userName "
            + "from ipm_monitor_view m left join ipm_system_user u on m.createuserid=u.id "
            + "where m.id > 100 ")
    List<MonitorViewBean> getMonitorViewAll();
    
    /**
     * 
     * @Title: getMonitorViewById
     * @Description: 根据用户查询驾驶舱
     * @param id   用户id
     * @return List<MonitorViewBean>
     * @author wangjianmin
     */
    @Select("select m.id,m.name,m.descrption,m.createuserid as createUserId,m.updatetime as updateTime,"
            + "m.lockstatus as lockStatus,IFNULL(u.username,'admin') as userName "
            + "from ipm_monitor_view m left join ipm_system_user u on m.createuserid=u.id "
            + "where m.id > 100 and  createUserId = #{id} ")
    List<MonitorViewBean> getMonitorViewById(int id);
}
