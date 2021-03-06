/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AuthorizeModuleDao
 *创建人:wjm    创建时间:2017年9月8日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.user.bean.AuthorizeModuleBean;
import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.bean.SamllModuleBean;

/**
 * ipm_authorize_module表 Dao 层
 * 2017年9月8日 下午3:34:35
 * @author wjm
 * @version
 * @see
 */
@Repository
public interface AuthorizeModuleDao {
    
    /**
     * 查询
     * 2017年9月8日 下午3:32:58
     * @param
     * @return List<AuthorizeModuleBean>
     * @exception 
     * @see
     */
    @Select("select id,nameen,namezh from ipm_authorize_module where iscore = true and isopen = true order by `order`")
    List<AuthorizeModuleBean>  selectAllAuthorizeModule();
    
    /**
     * 
     * @Title: selectAllFilterAuthorizeModule
     * @Description: 过滤掉系统资源消耗
     * @return List<AuthorizeModuleBean>
     * @author wangjianmin
     */
    @Select("select id,nameen,namezh from ipm_authorize_module where iscore = true and isopen = true and id != 2 order by `order`")
    List<AuthorizeModuleBean>  selectAllFilterAuthorizeModule();

    /**
     * 查询业务类型(流量下载页面使用),不包含观察点、url、应用可用性
     * 2018年3月26日 下午13:32:58
     * @param
     * @return List<AuthorizeModuleBean>
     * @exception 
     * @see
     */
    @Select("select id,nameen,namezh from ipm_authorize_module where iscore = true and isopen = true and id not in (10,8,1,2,3,13) order by `order`")
    List<AuthorizeModuleBean>  selectAuthorizeModuleForFlowCtl();
    
    /**
     * 
     * @Title: getIsopen
     * @Description: 是否支持多个
     * @return List<AuthorizeModuleBean>
     * @author wangjianmin
     */
    @Select("select id,nameen, isopen from ipm_authorize_module where iscore = false")
    List<AuthorizeModuleBean> getIsopen();
    
    /**
     * 
     * @Title: getSelectModule
     * @Description: 获取所有授权模块（生成产品授权）
     * @return List<AuthorizeModuleBean>
     * @author wjm
     */
    @Select("SELECT id,nameen,namezh,iscore,isopen,`order` FROM ipm_authorize_module")
    List<AuthorizeModuleBean> getSelectModule();
    
    /**
     * 
     * @Title: updateByIdModule
     * @Description: 根据模块ID，修改isopen
     * @param moduleId   模块ID
     * @param isopen     isopen
     * @return boolean
     * @author wjm
     */
    @Update("update ipm_authorize_module set isopen=#{isopen} where id = #{moduleId}")
    boolean updateByIdModule(@Param("moduleId")int moduleId, @Param("isopen")boolean isopen);
    
    /**
     * 
     * @Title: selectByIsopen
     * @Description: 根据模块名称查询 Isopen
     * @param name   模块名称
     * @return boolean
     * @author wjm
     */
    @Select("SELECT isopen FROM ipm_authorize_module WHERE namezh=#{name}")
    String  selectByIsopen(@Param("name")String name);
    
    /**
     * 
     * @Title: getSamllModuleByModuleId
     * @Description: 获取小模块名称
     * @param moduleId 模块编号
     * @return SamllModuleBean
     * @author WWW
     */
    @Select("select id, module_id moduleId, `name` from ipm_small_module where module_id = #{moduleId}")
    SamllModuleBean getSamllModuleByModuleId(@Param("moduleId") int moduleId);

    /**
     * 
     * @Title: getAuthorizeAppModule
     * @Description: 获取应用模块信息
     * @return List<JurisModuleBean>
     * @author WWW
     */
    @Select("select id, nameen, namezh from ipm_authorize_module where iscore = 1 and id in (4,5,6,7,8,9,10,11,12) order by `order`")
    List<JurisModuleBean> getAuthorizeAppModule();
}
