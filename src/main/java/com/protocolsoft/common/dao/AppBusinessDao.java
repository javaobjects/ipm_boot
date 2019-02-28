/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppBusinessDao
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.SameIpPortParamBean;
import com.protocolsoft.common.provider.AppBusinessProvider;

/**
 * @ClassName: AppBusinessDao
 * @Description: ipm_app_business表Dao
 * @author yan
 *
 */
@Repository
public interface AppBusinessDao {
 
    /**
     * @Title: insertAppBusiness
     * @Description: 新增
     * @param bean void
     * @author yan
     */
    @Insert("insert into ipm_app_business(module_id,name,displayip,bandwidth,descrption) "
            + "values(#{moduleId},#{name},#{displayIp},#{bandwidth},#{descrption})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertAppBusiness(AppBusinessBean bean);
    
    /**
     * @Title: selectAppBusiness
     * @Description: 通过id查询
     * @param id
     * @return AppBusinessBean
     * @author yan
     */
    @Select("select "
            + "id as id,"
            + "module_id as moduleId,"
            + "name as name,"
            + "displayip as displayIp, bandwidth,"
            + "descrption as descrption "
            + "from ipm_app_business where id = #{id}")
    AppBusinessBean selectAppBusiness(long id);
    
    /**
     * @Title: updateAppBusiness
     * @Description:  修改
     * @param bean void
     * @author yan
     */
    @Update("update ipm_app_business set name = #{name},displayip = #{displayIp},"
            + " bandwidth = #{bandwidth},descrption = #{descrption} where id = #{id}")
    void updateAppBusiness(AppBusinessBean bean);
        
    /**
     * @Title: selectAllAppBusiness
     * @Description: 查询全部
     * @return List<AppBusinessBean>
     * @author yan
     */
    @Select("SELECT id,name,displayip,bandwidth,descrption from ipm_app_business")
    List<AppBusinessBean> selectAllAppBusiness();
    
    /**
     * @Title: deleteAppBusiness
     * @Description: 删除
     * @param id void
     * @author yan
     */
    @Delete("delete from ipm_app_business where id = #{id}")  
    void deleteAppBusiness(Integer id);
    
    /**
     * @Title: selectCountByName
     * @Description: 根据name是否有信息
     * @param name
     * @param moduleId
     * @param appId
     * @return boolean
     * @author yan
     */
    @Select("select count(1) from ipm_app_business where `name` = #{name} and module_id = #{moduleId} and id!=#{appId}")
    boolean selectCountByName(@Param("name") String name, @Param("moduleId") int moduleId,  @Param("appId") int appId);
        
    /**
     * @Title: selectAppBusinessesByModuleId
     * @Description: 通过模块id查询所有业务
     * @param moduleId
     * @return List<AppBusinessBean>
     * @author yan
     */
    @Select("select id, module_id moduleId, `name`, displayip, bandwidth, descrption "
            + "from ipm_app_business where module_id = #{moduleId}")
    List<AppBusinessBean> selectAppBusinessesByModuleId(Integer moduleId);
    
    /**
     * @Title: selectAppBusinessesByModuleIdAndBusId
     * @Description:  通过模块id,module_id查询业务
     * @param moduleId
     * @param id
     * @return AppBusinessBean
     * @author yan
     */
    @Select("select id as id, module_id as moduleId, `name` as name, displayip as displayIp, bandwidth as bandwidth, descrption as descrption "
            + "from ipm_app_business where module_id = #{moduleId} and id = #{id} ")
    AppBusinessBean selectAppBusinessesByModuleIdAndBusId(@Param("moduleId") Long moduleId, @Param("id") Long id);
    
    /**
     * 
     * @Title: selectAppBusinessesByModuleIdByIds
     * @Description: 根据ids获取模块信息
     * @param moduleId 模块编号
     * @param ids 业务编号
     * @return List<AppBusinessBean>
     * @author www
     */
    @Select("select id, module_id moduleId, `name`, displayip, bandwidth, descrption "
            + "from ipm_app_business where module_id = #{moduleId} and id in (${ids})")
    List<AppBusinessBean> selectAppBusinessesByModuleIdByIds(
            @Param("moduleId")Integer moduleId, @Param("ids") String ids);
    
    /**
     * @Title: selectAppBusinessesByModuleIdAndDisplayIp
     * @Description:  通过moduleId,displayIp查询业务
     * @param moduleId
     * @param displayIp
     * @return AppBusinessBean
     * @author chensq
     */
    @Select("select id as id, module_id as moduleId, `name` as name, displayip as displayIp, bandwidth as bandwidth, descrption as descrption "
            + "from ipm_app_business where module_id = #{moduleId} and displayip = #{displayIp} ")
    AppBusinessBean selectAppBusinessesByModuleIdAndDisplayIp(@Param("moduleId") int moduleId, @Param("displayIp") String displayIp);
    
    /**
     * 
     * @Title: isExistBusiSameNameOrIp
     * @Description: 是否存在相同的名称或IP业务
     * @param bean
     * @return boolean
     * @author WWW
     */
    @Select("select count(*) from ipm_app_business where module_id = #{moduleId} and (`name` = #{name} or displayip = #{displayIp})")
    boolean isExistBusiSameNameOrIp(AppBusinessBean bean);
    
    /**
     * 
     * @Title: isExistSameIpPort
     * @Description: 是否存在相同的IP或端口
     * @param bean 查询集合
     * @return boolean
     * @author WWW
     */
    @SelectProvider(type = AppBusinessProvider.class, method = "isExistSameIpPort")
    boolean isExistSameIpPort(SameIpPortParamBean bean);
}
