/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>

 */
package com.protocolsoft.word.dao;

import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.word.bean.ReportTimerDetailBean;
import com.protocolsoft.word.daoprovider.ReportDetailDaoProvider;

/**
 * @ClassName: ReportDetailDao
 * @Description: 操作数据库中图表的接口
 * @author 刘斌
 *
 */
@Repository
public interface ReportDetailDao {

    /**
     * @Title: insertReportTimerDetails
     * @Description: 插入报表
     * @param bean void
     * @author 刘斌
     */
    @Insert("insert into ipm_timer_report_detail(report_id,module_id,watchpoint_id,app_id,plot_id,plot_type_id) "
            + "values(#{reportId},#{moduleId},#{watchpointId},#{appId},#{plotId},#{plotTypeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertReportTimerDetails(ReportTimerDetailBean bean);
    
    /**
     * 
     * @Title: insertAll
     * @Description: 
     * @param list
     * @return int
     * @author 刘斌
     */
    @InsertProvider(type = ReportDetailDaoProvider.class, method = "insertAll")
    int insertAll(@Param("list") List<ReportTimerDetailBean> list);

    /**
     * @Title: selectReportTimerDetail
     * @Description: 通过id查询 
     * @param id
     * @return ReportTimerDetailBean
     * @author 刘斌
     */
    @Select("select " + "id as id," + "type_id as typeId,"
            + "module_id as moduleId," + "watchpoint_id as watchpointId,"
            + "app_id as appId," + "plot_id as plotId "
            + "plot_type_id as plotTypeId "
            + "from ipm_timer_report_detail where id = #{id}")
    ReportTimerDetailBean selectReportTimerDetail(Integer id);

    /**
     * @Title: deleteTimerReport
     * @Description: 删除通过ID
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_timer_report_detail where report_id = #{id}")
    void deleteTimerReport(Integer id);

    /**
     * @Title: selectReportTimerDetailsById
     * @Description: 关联查找
     * @param id
     * @return List<ReportTimerDetailBean>
     * @author 刘斌
     */
    @Select("SELECT id as id,report_id as reportId,module_id as "
            + "moduleId,watchpoint_id as watchpointId,app_id as appId, plot_id "
            + "as plotId,plot_type_id as plotTypeId"
            + " from ipm_timer_report_detail WHERE report_id = #{id}")
    List<ReportTimerDetailBean> selectReportTimerDetailsById(Integer id);
    
    /**
     * @Title: deleteTimerReportByWatchPointId
     * @Description: 删除已经被删除的观察点在列表中的相关信息
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_timer_report_detail where watchpoint_id = #{id}")
    void deleteTimerReportByWatchPointId(Integer id);
    
    /**
     * @Title: deleteTimerReportAppId
     * @Description: 删除已经被删除的业务在列表中的相关信息
     * @param id
     * @param modleId void
     * @author 刘斌
     */
    @Delete("delete from ipm_report_business where business_id = #{id} and module_id = #{modleId}")
    void deleteTimerReportAppId(@Param("id")Integer id, @Param("modleId")Integer modleId);
}
