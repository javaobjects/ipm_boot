/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.bean.ReportPlanBean;

/**
 * 
 * @ClassName: ReportPlanDao
 * @Description: 
 * @author 刘斌
 *
 */
@Repository
public interface ReportPlanDao {

    /**
     * @Title: insertTimerReport
     * @Description: 新增
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Insert("INSERT INTO ipm_report_plan(name,user_id,modal_id,type_id,create_time,send_times,next_sendtime,watchpoint_ids,compare_to_last,"
            + "starttime,endtime)"
            + " SELECT #{name},#{userId},#{modalId},#{typeId},#{createTime},#{sendTimes},#{nextSendTime},#{watchpointIds},#{compareToLastOne},"
            + "#{startTime},#{endTime} "
            + "FROM DUAL WHERE NOT EXISTS (SELECT name FROM ipm_report_plan WHERE name = #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReportPlan(ReportPlanBean bean);

    /**
     * @Title: selectTimerReport
     * @Description: 通过id查询
     * @param id
     * @return TimerReportBean
     * @author 刘斌
     */
    @Select("select " + "id as id," + "modal_id as modalId," + "name as name,"
            + "user_id as userId," +"type_id as typeId,state as state,"
            + "create_time as createTime,send_times as sendTimes,next_sendtime as nextSendTime,"
            + "starttime as startTime,endtime as endTime,compare_to_last as compareToLastOne,"
            + "watchpoint_ids as watchpointIds  from ipm_report_plan where id = #{id}")
    ReportPlanBean selectReportPlan(Integer id);

    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_plan set state = #{state}"
            + " where id = #{id}")
    int updateReportPlanState(@Param("id")Integer id, @Param("state")Integer state);
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_plan set name = #{name},modal_id = #{modalId},"
            + " user_id = #{userId},type_id = #{typeId},next_sendtime = #{nextSendTime},starttime = #{startTime},endtime = #{endTime}"
            + ",state = #{state},compare_to_last=#{compareToLastOne},watchpoint_ids =#{watchpointIds} where id = #{id}")
    int updateReportPlan(ReportPlanBean bean);

    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.user_id as userId,b.modal_id as modalId,c.name as modalName,"
            + "b.type_id as typeId,a.username as userName,b.create_time as createTime,b.compare_to_last as compareToLastOne,"
            + "b.send_times as sendTimes,b.next_sendtime as nextSendTime,b.state as state,b.watchpoint_ids as watchpointIds"
            + "  from  ipm_system_user AS a INNER JOIN "
            + "ipm_report_plan AS b ON b.user_id = a.id INNER JOIN  ipm_report_modal AS c ON b.modal_id = c.id "
            + "where a.id = #{id} order by createTime desc")
    List<ReportPlanBean> selectAllReportPlan(Integer id);
    
    /**
     * 
     * @Title: deleteReportPlan
     * @Description: 
     * @param id
     * @return int
     * @author 刘斌
     */
    @Delete("delete from ipm_report_plan where id = #{id}")
    int deleteReportPlan(Integer id);
    
    /**
     * 
     * @Title: deleteReportPlan
     * @Description: 
     * @param id
     * @return int
     * @author 刘斌
     */
    @Delete("delete from ipm_report_plan where modal_id = #{id}")
    int deleteReportPlanByModalId(Integer id);
    
    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.user_id as userId,b.modal_id as modalId,b.state as state,"
            + "b.type_id as typeId,a.username as userName,b.create_time as createTime,b.compare_to_last as compareToLastOne,"
            + "b.send_times as sendTimes,b.next_sendtime as nextSendTime,b.watchpoint_ids as watchpointIds  from  ipm_system_user AS a INNER JOIN "
            + "ipm_report_plan AS b ON b.user_id = a.id "
            + "where b.state = 1 AND b.type_id = #{tid} order by createTime desc")
    List<ReportPlanBean> selectAllNeedReportPlan(@Param("tid")Integer tid);
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_plan set send_times = send_times + 1"
            + " where id = #{id}")
    int addReportPlanSendTimes(@Param("id")Integer id);
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_plan set next_sendtime = #{nextTime}"
            + " where id = #{id}")
    int updateReportPlanNextSendTime(@Param("id")Integer id, @Param("nextTime")Long nextTime);
    
    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT id,name,module_id as modulId,plan_id as planId,"
            + "business_id as bussinessId FROM "
            + "ipm_report_business "
            + "where plan_id = #{id}")
    List<ReportBusinessBean> selectAllReportBusinessByPlanId(@Param("id")Integer id);
    
    /**
     * 
     * @Title: selectAllReportPlanByModalId
     * @Description: 
     * @param id
     * @return List<ReportPlanBean>
     * @author 刘斌
     */
    @Select("SELECT id as id from "
            + "ipm_report_plan "
            + "where modal_id = #{id}")
    List<ReportPlanBean> selectAllReportPlanByModalId(Integer id);
    
    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("select " + "id as id," + "modal_id as modalId," + "name as name,"
            + "user_id as userId," +"type_id as typeId,state as state,"
            + "create_time as createTime,send_times as sendTimes,next_sendtime as nextSendTime"
            + ",watchpoint_ids as watchpointIds,starttime as startTime,endtime as endTime "
            + "from ipm_report_plan where type_id = 1")
    List<ReportPlanBean> selectAllReportPlanByMySelf();
    
    /**
     * 
     * @Title: getServerSide
     * @Description: 
     * @param id
     * @return String
     * @author 刘斌
     */
    @Select("select name from ipm_app_business where id = #{id}")
    String getServerSide(@Param("id")Integer id);
   
    /**
     * 
     * @Title: getServerSide
     * @Description: 
     * @param id
     * @return String
     * @author 刘斌
     */
    @Select("select type_id from ipm_report_plan where id = #{id}")
    Integer searchTypeIdOfPlan(@Param("id")Integer id);
    
    /**
     * 
     * @Title: deleteReportPlan
     * @Description: 
     * @param id
     * @return int
     * @author 刘斌
     */
    @Delete("delete from ipm_report_plan where user_id = #{id}")
    int deleteReportPlanByUserId(Integer id);
    
    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.user_id as userId,b.modal_id as modalId,c.name as modalName,"
            + "b.type_id as typeId,a.username as userName,b.create_time as createTime,b.compare_to_last as compareToLastOne,"
            + "b.send_times as sendTimes,b.next_sendtime as nextSendTime,b.state as state,b.watchpoint_ids as watchpointIds"
            + "  from  ipm_system_user AS a INNER JOIN "
            + "ipm_report_plan AS b ON b.user_id = a.id INNER JOIN  ipm_report_modal AS c ON b.modal_id = c.id "
            + " order by createTime desc")
    List<ReportPlanBean> selectAllReportPlans();
}
