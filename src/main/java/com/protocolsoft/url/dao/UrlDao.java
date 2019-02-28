/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlDao.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.protocolsoft.url.bean.SimpleUrlBean;
import com.protocolsoft.url.bean.UrlKpiDataBean;
import com.protocolsoft.url.bean.UrlKpiParamBean;
import com.protocolsoft.url.bean.UrlSetBean;
import com.protocolsoft.url.provider.UrlProvider;

/**
 * @ClassName: UrlDao
 * @Description: URLDAO
 * @author www
 *
 */
public interface UrlDao {
    
    /**
     * 
     * @Title: getUrlKpiData
     * @Description: RRD数据
     * @param bean 参数
     * @return List<UrlKpiDataBean>
     * @author www
     */
    @SelectProvider(type = UrlProvider.class, method = "selectKpi")
    List<UrlKpiDataBean> getUrlKpiData(UrlKpiParamBean bean);

    /**
     * 
     * @Title: selectKpiInSub
     * @Description: RRD数据
     * @param bean 参数
     * @return List<UrlKpiDataBean>
     * @author www
     */
    @SelectProvider(type = UrlProvider.class, method = "selectKpiInSub")
    List<UrlKpiDataBean> selectKpiInSub(UrlKpiParamBean bean);
    
    /**
     * 
     * @Title: selectUrlByIds
     * @Description: 获取单个URL信息
     * @return UrlSetBean
     * @author www
     */
    @Select("select id, `name`, descrption from ipm_app_business where module_id = 8 and id in (${ids})")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "set", many = @Many(select = "selectSimpleUserByAppId"))
    })
    List<UrlSetBean> selectUrlByIds(@Param("ids") String ids);
    
    /**
     * 
     * @Title: selectUrlById
     * @Description: 获取单个URL信息
     * @return UrlSetBean
     * @author www
     */
    @Select("select id, `name`, descrption from ipm_app_business where module_id = 8 and id = #{id}")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "set", many = @Many(select = "selectSimpleUserByAppId"))
    })
    UrlSetBean selectUrlById(int id);

    /**
     * 
     * @Title: selectUrl
     * @Description: 获取URL信息
     * @return List<UrlSetBean>
     * @author www
     */
    @Select("select id, `name`, descrption from ipm_app_business where module_id = 8")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "set", many = @Many(select = "selectSimpleUserByAppId"))
    })
    List<UrlSetBean> selectUrl();
    
    /**
     * 
     * @Title: selectSimpleUserByAppId
     * @Description: 获取业务URL信息
     * @param id 业务编号
     * @return List<SimpleUrlBean>
     * @author www
     */
    @Select("select id, app_id, num, `name`, url, isStored from ipm_url_set where app_id = #{id}")
    List<SimpleUrlBean> selectSimpleUserByAppId(int id);
    
    /**
     * 
     * @Title: urlInsertBatch
     * @Description: 批量添加URL
     * @param bean 参数
     * @return boolean 是否成功
     * @author www
     */
    @InsertProvider(type = UrlProvider.class, method = "urlInsertBatch")
    boolean urlInsertBatch(UrlSetBean bean);
    
    /**
     * 
     * @Title: urlInsert
     * @Description: 添加单条URL信息
     * @param bean 数据
     * @return boolean
     * @author www
     */
    @Insert("insert into `ipm_url_set` (`app_id`, `num`, `name`, `url`, `isStored`)"
            + " values (#{appId}, #{num}, #{name}, #{url}, #{isStored})")
    boolean urlInsert(SimpleUrlBean bean);
    
    /**
     * 
     * @Title: deleteUrlByAppId
     * @Description: 删除整个业务的URL信息
     * @param appId 业务编号
     * @return boolean
     * @author www
     */
    @Delete("delete from `ipm_url_set` where app_id = #{appId}")
    boolean deleteUrlByAppId(int appId);
    
    /**
     * 
     * @Title: deleteUrlById
     * @Description: 删除单条URL
     * @param id 编号
     * @return boolean
     * @author www
     */
    @Delete("delete from `ipm_url_set` where id = #{id}")
    boolean deleteUrlById(int id);
    
    /**
     * 
     * @Title: updateUrlById
     * @Description: 更新URL信息
     * @param bean URL信息
     * @return boolean
     * @author www
     */
    @Update("update `ipm_url_set` set `num`= ${num}, `name`= #{name}, "
            + "`url`= #{url}, `isStored`= #{isStored} where id = #{id}")
    boolean updateUrlById(SimpleUrlBean bean);
}
