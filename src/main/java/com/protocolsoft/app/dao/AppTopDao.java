/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTopDao.java
 *创建人: www    创建时间: 2018年1月26日
 */
package com.protocolsoft.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;
import com.protocolsoft.app.bean.ParamBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.app.provider.AppTopProvider;

/**
 * @ClassName: AppTopDao
 * @Description: TOP
 * @author www
 *
 */
@Component
public interface AppTopDao {

    /**
     * 
     * @Title: getAppTopConfig
     * @Description: 获取应用TOP配置信息
     * @param moduleId 模块编号
     * @return List<AppTopConfigBean>
     * @author www
     */
    @Select("select c.`id`, `module_id` moduleId, `plot_id` plotId, `summary`, "
            + "`column`, `where`, `group`, c.`order`, `limit` from ipm_app_topn_config c" 
            + " join ipm_plot_option p on c.plot_id = p.id where module_id = #{moduleId}")
    List<AppTopConfigBean> getAppTopConfig(@Param("moduleId") int moduleId);
    
    /**
     * 
     * @Title: getAppTopConfigById
     * @Description: 获取应用TOP配置信息
     * @param moduleId 模块编号
     * @param plotId 绘图编号
     * @return AppTopConfigBean
     * @author www
     */
    @Select("select c.`id`, `module_id` moduleId, `plot_id` plotId, `summary`, `column`, `where`, "
            + "`group`, c.`order`, `limit` from ipm_app_topn_config c join ipm_plot_option p on "
            + "c.plot_id = p.id where module_id = #{moduleId} and plot_id = #{plotId}")
    AppTopConfigBean getAppTopConfigById(@Param("moduleId") int moduleId, @Param("plotId") int plotId);
    
    /**
     * 
     * @Title: getTopInfo
     * @Description: 获取TOP信息
     * @param bean 参数
     * @return List<PlotSimpleBean>
     * @author www
     */
    @Select("select `key` `name`, ${calcul}(`value`) `value` from ${table} "
            + "where starttime >= #{starttime} and endtime <= #{endtime} and "
            + "watchpoint_id = #{watchpointId} and app_id = #{busiId} and "
            + "plot_id = #{plotId} group by `key` order by `value` desc limit 10")
    List<PlotSimpleBean> getTopInfo(PlotParamBean bean);
    
    /**
     * 
     * @Title: selectTop
     * @Description: 获取TOP数据(实时计算)
     * @param bean 参数
     * @return List<PlotSimpleBean>
     * @author www
     */
    @SelectProvider(type = AppTopProvider.class, method = "selectTopSql")
    List<PlotSimpleBean> selectTop(AppTopParamsBean bean);
    
    /**
     * 
     * @Title: addTop60Batch
     * @Description: 一分钟TOP排名
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @InsertProvider(type = AppTopProvider.class, method = "addTop60Batch")
    boolean addTop60Batch(AppTopParamsBean bean);
    
    /**
     * 
     * @Title: addTop600Bacth
     * @Description: 十分钟TOP排名
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @Insert("insert into ipm_app_topn_600 select ${starttime}, ${endtime}, watchpoint_id, app_id, "
            + "plot_id,`key`, `value` from (select watchpoint_id, app_id, plot_id, `key`, `value`, "
            + "case when @gid = id then @rownum:=@rownum+1 else @rownum:=1 end rownum, @gid:=id "
            + "from (select concat(watchpoint_id, app_id, plot_id) id, watchpoint_id, "
            + "app_id, plot_id, `key`, case p.calcul when 'SUM' then SUM(`value`) when 'AVG' "
            + "then AVG(`value`) end `value` from ipm_app_topn_60 t join ipm_plot_option p on "
            + "t.plot_id = p.id where starttime >= #{starttime} and endtime <= #{endtime} "
            + "group by watchpoint_id, app_id, plot_id, `key` order by plot_id, "
            + "`value` desc) tmp, (select @rownum:=0, @gid:=null) var group by id, `key` "
            + "having rownum <= 100 order by plot_id, `value` desc) tmp")
    boolean addTop600Bacth(ParamBean bean);
    
    
    /**
     * 
     * @Title: addTop3600Bacth
     * @Description: 一小时TOP排名
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @Insert("insert into ipm_app_topn_3600 select ${starttime}, ${endtime}, watchpoint_id, app_id, "
            + "plot_id,`key`, `value` from (select watchpoint_id, app_id, plot_id, `key`, `value`, "
            + "case when @gid = id then @rownum:=@rownum+1 else @rownum:=1 end rownum, @gid:=id "
            + "from (select concat(watchpoint_id, app_id, plot_id) id, watchpoint_id, "
            + "app_id, plot_id, `key`, case p.calcul when 'SUM' then SUM(`value`) when 'AVG' "
            + "then AVG(`value`) end `value` from ipm_app_topn_600 t join ipm_plot_option p on "
            + "t.plot_id = p.id where starttime >= #{starttime} and endtime <= #{endtime} "
            + "group by watchpoint_id, app_id, plot_id, `key` order by plot_id, "
            + "`value` desc) tmp, (select @rownum:=0, @gid:=null) var group by id, `key` "
            + "having rownum <= 100 order by plot_id, `value` desc) tmp")
    boolean addTop3600Bacth(ParamBean bean);
    
    /**
     * 
     * @Title: addTop86400Bacth
     * @Description: 一天TOP排名
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @Insert("insert into ipm_app_topn_86400 select ${starttime}, ${endtime}, watchpoint_id, app_id, "
            + "plot_id,`key`, `value` from (select watchpoint_id, app_id, plot_id, `key`, `value`, "
            + "case when @gid = id then @rownum:=@rownum+1 else @rownum:=1 end rownum, @gid:=id "
            + "from (select concat(watchpoint_id, app_id, plot_id) id, watchpoint_id, "
            + "app_id, plot_id, `key`, case p.calcul when 'SUM' then SUM(`value`) when 'AVG' "
            + "then AVG(`value`) end `value` from ipm_app_topn_3600 t join ipm_plot_option p on "
            + "t.plot_id = p.id where starttime >= #{starttime} and endtime <= #{endtime} "
            + "group by watchpoint_id, app_id, plot_id, `key` order by plot_id, "
            + "`value` desc) tmp, (select @rownum:=0, @gid:=null) var group by id, `key` "
            + "having rownum <= 100 order by plot_id, `value` desc) tmp")
    boolean addTop86400Bacth(ParamBean bean);
    
    /**
     * 
     * @Title: deleteTopData
     * @Description: 删除TOP数据
     * @param time 保留time之后的TOP数据
     * @param table 删除哪张表的数据
     * @return boolean 是否成功
     * @author www
     */
    @Delete("delete from ${table} where starttime < #{time}")
    boolean deleteTopData(@Param("time") long time,  @Param("table") String table);
}
