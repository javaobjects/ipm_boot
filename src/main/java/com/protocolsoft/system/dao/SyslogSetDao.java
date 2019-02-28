/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SyslogSetDao.java
 *创建人: www    创建时间: 2018年3月31日
 */
package com.protocolsoft.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.system.bean.SyslogBean;

/**
 * @ClassName: SyslogSetDao
 * @Description: SYSLOG
 * @author www
 *
 */
@Repository
public interface SyslogSetDao {

    /**
     * 
     * @Title: getSyslogInfo
     * @Description: 获取服务器信息
     * @return List<SyslogBean>
     * @author www
     */
    @Select("select `id`, `ip`, `port`, `name`, `descrption` from `ipm_syslog_set`")
    List<SyslogBean> getSyslogInfo();

    /**
     * 
     * @Title: getSyslogInfoById
     * @Description: 通过编号获取服务器信息
     * @param id 编号
     * @return SyslogBean
     * @author www
     */
    @Select("select `id`, `ip`, `port`, `name`, `descrption` from `ipm_syslog_set` where id = #{id}")
    SyslogBean getSyslogInfoById(@Param("id") int id);
    
    /**
     * 
     * @Title: addSyslog
     * @Description: 添加
     * @param bean void
     * @author www
     */
    @Select("insert into `ipm_syslog_set` (`ip`, `port`, `name`, `descrption`) values (#{ip}, #{port}, #{name}, #{descrption})")
    void addSyslog(SyslogBean bean);

    /**
     * 
     * @Title: delSyslog
     * @Description: 删除
     * @param id 编号
     * @return int
     * @author www
     */
    @Delete("delete from `ipm_syslog_set` where id = #{id}")
    boolean delSyslog(@Param("id") int id);

    /**
     * 
     * @Title: updSyslog
     * @Description: 修改服务器信息
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @Update("update `ipm_syslog_set` set `ip`=#{ip}, `port`=#{port}, `name`=#{name}, `descrption`=#{descrption} where `id`=#{id}")
    boolean updSyslog(SyslogBean bean);
}
