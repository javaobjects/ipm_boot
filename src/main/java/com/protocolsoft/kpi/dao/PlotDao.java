/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotOptionDao.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.kpi.bean.PlotBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;

/**
 * @ClassName: PlotOptionDao
 * @Description: 绘图
 * @author www
 *
 */
@Repository
public interface PlotDao {

    /**
     * 
     * @Title: getPlotByModuleIds
     * @Description: 获取绘图映射关系
     * @param moduleId 模块编号
     * @return List<PlotBaseBean>
     * @author www
     */
    @Select("select o.id, module_id moduleId, kpi_id kpiId, group_id groupId, g.`name` groupName, "
            + "o.`name`, graph, number, calcul from ipm_plot_option o join ipm_plot_group_type g "
            + "on o.group_id = g.id where module_id = #{moduleId} order by group_id, `order`")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "types", many = @Many(select = "getPlotTypeByPlotId"))
    })
    List<PlotBean> getPlotByModuleId(@Param("moduleId") int moduleId);
    
    /**
     * 
     * @Title: getPlotByModuleKpiId
     * @Description: 获取绘图映射关系
     * @param moduleId 模块编号
     * @param kpiId KPI编号
     * @return PlotBaseBean
     * @author www
     */
    @Select("select id, module_id moduleId, kpi_id kpiId, `name`, graph, number, calcul from ipm_plot_option "
            + "where module_id = #{moduleId} and kpi_id = #{kpiId} and number = 1")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "types", many = @Many(select = "getPlotTypeByPlotId"))
    })
    PlotBean getPlotByModuleKpiId(@Param("moduleId") int moduleId, @Param("kpiId") int kpiId);
    
    /**
     * 
     * @Title: getPlotByModuleKpiName
     * @Description: 获取绘图映射关系
     * @param moduleId 模块编号
     * @param kpiName KPI名称
     * @return PlotBaseBean
     * @author www
     */
    @Select("select o.id, module_id moduleId, kpi_id kpiId, o.`name`, graph, number, calcul from ipm_plot_option o "
            + "join ipm_kpis k on o.kpi_id = k.id where module_id = #{moduleId} and k.name = #{kpiName} and number = 1")
    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "id", property = "types", many = @Many(select = "getPlotTypeByPlotId"))
    })
    PlotBean getPlotByModuleKpiName(@Param("moduleId") int moduleId, @Param("kpiName") String kpiName);
    
    /**
     * 
     * @Title: getPlotOptionById
     * @Description: 获取绘图选项信息
     * @param option_id 绘图选项编号
     * @return PlotOptionBean
     * @author www
     */
    @Select("select id, module_id moduleId, kpi_id kpiId, `name`, graph, number, calcul "
            + "from ipm_plot_option where id = #{option_id}")
    PlotOptionBean getPlotOptionById(@Param("option_id") int option_id);
    
    /**
     * 
     * @Title: getPlotTypeByPlotId
     * @Description: 根据绘图选项编号获取类型
     * @param plot_id 绘图选项编号
     * @return List<PlotTypeBean>
     * @author www
     */
    @Select("select t.id id, `name`, type from ipm_plot_option_type ot join "
            + "ipm_plot_type t on ot.type_id = t.id where option_id = #{plot_id}")
    List<PlotTypeBean> getPlotTypeByPlotId(@Param("plot_id") int plot_id);
    
    /**
     * 
     * @Title: getPlotTypeById
     * @Description: 获取绘图类型信息
     * @param plot_type_id 绘图类型编号
     * @return PlotTypeBean
     * @author www
     */
    @Select("select id, `name`, type from ipm_plot_type where id = #{plot_type_id}")
    PlotTypeBean getPlotTypeById(@Param("plot_type_id") int plot_type_id);
}
