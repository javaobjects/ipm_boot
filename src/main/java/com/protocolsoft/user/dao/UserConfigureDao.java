/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserConfigure
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.user.bean.UserConfigureBean;

/**
 * 配置dao
 * 2017年9月1日 下午4:48:52
 * @author long
 * @version
 * @see
 */
@Repository
public interface UserConfigureDao {
    
    /**
     * 配置信息
     * 2017年9月1日 下午4:54:22
     * @param
     * @return List<UserConfigureBean>
     * @exception 
     * @see
     */
    @Select("select id,user_id as userId,`key`,`value` from ipm_user_configure where user_id=#{userId}")
    List<UserConfigureBean> getUserConfigureBean(@Param("userId") int userId);
    
    /**
     * 根据用户userId和key获取配置信息
     * 2017年9月5日 上午11:41:49
     * @param
     * @return UserConfigureBean
     * @exception 
     * @see
     */
    @Select("select id,user_id as userId,`key`,`value` from ipm_user_configure "
            + " where user_id=#{userId} and `key`=#{key} ")
    UserConfigureBean getUserConfigureBeanByKey(@Param("userId") int userId, @Param("key") String key);
    
    /**
     * 根据id获取配置信息
     * 2017年9月5日 上午11:42:33
     * @param
     * @return UserConfigureBean
     * @exception 
     * @see
     */
    @Select("select id,user_id as userId,`key`,`value` from ipm_user_configure where id=#{id}")
    UserConfigureBean getUserConfigureBeanById(@Param("id") int id);
    
    /**
     * 添加配置信息
     * 2017年9月5日 上午11:03:40
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Insert("insert into ipm_user_configure (user_id,`key`,`value`) values (#{userId}, #{key},#{value})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addUserConfigure(UserConfigureBean userConfigureBean);
    
    /**
     * 根据id更新配置信息
     * 2017年9月5日 下午2:23:35
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update("update ipm_user_configure set `key`=#{key},`value`=#{value} where id=#{id}")
    int updateUserConfigureById(UserConfigureBean userConfigureBean);
    
    /**
     * 根据用户userId和key更新配置信息
     * 2017年9月5日 下午2:23:46
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update("update ipm_user_configure set `key`=#{key},`value`=#{value} where `key`=#{key} and user_id=#{userId} ")
    int updateUserConfigureByKey(UserConfigureBean userConfigureBean);
    
    /**
     * 根据userId删除配置信息
     * 2017年9月5日 下午3:54:15
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_user_configure where user_id=#{userId}")
    boolean delUserConfigureByUserId(int userId);

}
