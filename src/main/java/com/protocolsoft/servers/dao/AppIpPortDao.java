/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppIpPortDao
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.servers.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.servers.provider.AppIpPortDaoProvider;

/**
 * ipm_app_ip_port表Dao
 * 2017年9月1日 上午11:54:32
 * @author yan
 * @version
 * @see
 */
public interface AppIpPortDao {
    
    /**
     * 批量新增
     * 2017年9月1日 上午11:56:07
     * @param AppIpPortBean
     * @exception 
     * @see
     */
    @InsertProvider(type = AppIpPortDaoProvider.class, method = "batchInsert") 
    void batchInsertAppIpPort(@Param("beans") List<AppIpPortBean> beans);
    
    /**
     * 删除
     * 2017年9月1日 上午11:56:16
     * @param id
     * @exception 
     * @see
     */
    @Delete("delete from ipm_app_ip_port where app_id = #{id}")
    void deleteAppIpPort(Integer id);
    
    /**
     * 查询
     * 2017年9月19日 下午2:58:17
     * @param appId
     * @return List<AppIpPortBean>
     * @exception 
     * @see
     */
    @Select("select INET_NTOA(ip) ip,port from ipm_app_ip_port where app_id = #{appId}")
    List<AppIpPortBean> selectAppIpPortsByAppId(Integer appId);
    
    /**
     * 查询 ip不解密
     * 2017年9月19日 下午2:58:17
     * @param appId
     * @return List<AppIpPortBean>
     * @exception 
     * @see
     */
    @Select("select ip,port from ipm_app_ip_port where app_id = #{appId}")
    List<AppIpPortBean> selectAppIpPortsByAppIdNotDecrypt(Integer appId);
    
    /**
     * 查询所有服务端业务
     * 2018年1月12日 上午10:10:55
     * @param
     * @return List<AppIpPortBean>
     * @exception 
     * @see
     */
    @Select("select * from ipm_app_business  where  module_id=12")
    List<AppBusinessBean> selectAll();
    
    /**
     * 查询 ip不解密，appId存在有无情况
     * 2017年9月19日 下午2:58:17
     * @param appIdsql
     * @return List<AppIpPortBean>
     * @exception 
     * @see
     */
    @Select("select ip,port from ipm_app_ip_port ${appIdsql} ")
    List<AppIpPortBean> selectAppIpPortsByAppIdSqlNotDecrypt(@Param("appIdsql") String appIdsql);
}
