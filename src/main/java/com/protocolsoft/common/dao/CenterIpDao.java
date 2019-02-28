/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CenterIpDao.java
 *创建人: www    创建时间: 2018年3月26日
 */
package com.protocolsoft.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.common.bean.CenterIpBean;

/**
 * @ClassName: CenterIpDao
 * @Description: 服务器接入
 * @author www
 *
 */
@Repository
public interface CenterIpDao {

    /**
     * 
     * @Title: getAllAccessInfo
     * @Description: 获取所有服务器信息
     * @return List<CenterIpBean>
     * @author www
     */
    @Select("select `id`, `ip`, `port`, `name`, `descrption` from `ipm_center_ip`")
    List<CenterIpBean> getAllAccessInfo();

    /**
     * 
     * @Title: getAllAccessInfoById
     * @Description: 获取接入信息
     * @param id 编号
     * @return CenterIpBean
     * @author www
     */
    @Select("select `id`, `ip`, `port`, `name`, `descrption` from `ipm_center_ip` where id = #{id}")
    CenterIpBean getAllAccessInfoById(@Param("id") int id);
    
    /**
     * 
     * @Title: addUrlInfo
     * @Description: 添加URl
     * @param bean void
     * @author www
     */
    @Insert("insert into `ipm_center_ip` (`ip`, `port`, `name`, `descrption`) values (#{ip}, #{port}, #{name}, #{descrption})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUrlInfo(CenterIpBean bean);

    /**
     * 
     * @Title: dltJoinUrl
     * @Description: 删除接入服务器信息
     * @param id 编号
     * @return int
     * @author www
     */
    @Delete("delete from `ipm_center_ip` where id = #{id}")
    boolean dltJoinUrl(@Param("id") int id);

    /**
     * 
     * @Title: updateJoinUrl
     * @Description: 修改服务器信息
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @Update("update `ipm_center_ip` set `ip`=#{ip}, `port`=#{port}, `name`=#{name}, `descrption`=#{descrption} where `id`=#{id}")
    boolean updateJoinUrl(CenterIpBean bean);
}
