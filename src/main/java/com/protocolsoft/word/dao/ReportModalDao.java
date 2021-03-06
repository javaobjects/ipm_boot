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

import com.protocolsoft.word.bean.ReportModalBean;

/**
 * 
 * @ClassName: ReportModalDao
 * @Description: 
 * @author 刘斌
 *
 */
@Repository
public interface ReportModalDao {
    
    /**
     * @Title: insertTimerReport
     * @Description: 新增
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Insert("INSERT INTO ipm_report_modal(user_id,name,create_time,description)"
            + " SELECT #{userId},#{name},#{createTime},#{description} "
            + "FROM DUAL WHERE NOT EXISTS (SELECT name FROM ipm_report_modal WHERE name = #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReportModal(ReportModalBean bean);

    /**
     * @Title: selectTimerReport
     * @Description: 通过id查询
     * @param id
     * @return TimerReportBean
     * @author 刘斌
     */
    @Select("select " + "id as id," + "user_id as userId," + "name as name,"
            +"description as description,"
            + "create_time as createTime,module_ids as moduleIds  "
            + "from ipm_report_modal where id = #{id}")
    ReportModalBean selectReportModal(Integer id);
    
    /**
     * 
     * @Title: selectReportModalModuleIds
     * @Description: 
     * @param id
     * @return String
     * @author 刘斌
     */
    @Select("select module_ids from ipm_report_modal where id = #{id}")
    String selectReportModalModuleIds(Integer id);


    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_modal set name = #{name},"
            + " description = #{description},module_ids = #{moduleIds} where id = #{id}")
    int updateReportModal(ReportModalBean bean);

    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.user_id as userId,b.description as description,"
            + "a.username as userName,b.create_time as createTime,b.module_ids as moduleIds"
            + "  from  ipm_system_user AS a INNER JOIN "
            + "ipm_report_modal AS b ON b.user_id = a.id "
            + "order by createTime desc")
    List<ReportModalBean> selectAllReportModal();

    /**
     * 
     * @Title: deleteReportModal
     * @Description: 
     * @param id
     * @return int
     * @author 刘斌
     */
    @Delete("delete from ipm_report_modal where id = #{id}")
    int deleteReportModal(Integer id);

    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_modal set module_ids = #{moduleIds}"
            + " where id = #{id}")
    int updateReportModalModules(ReportModalBean bean);
    
    /**
     * 
     * @Title: deleteReportModal
     * @Description: 
     * @param id
     * @return int
     * @author 刘斌
     */
    @Delete("delete from ipm_report_modal where user_id = #{id}")
    int deleteReportModalByUserId(Integer id);
    
    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.user_id as userId,b.description as description,"
            + "a.username as userName,b.create_time as createTime,b.module_ids as moduleIds"
            + "  from  ipm_system_user AS a INNER JOIN "
            + "ipm_report_modal AS b ON b.user_id = a.id "
            + " WHERE b.user_id = #{id} order by createTime desc")
    List<ReportModalBean> selectAllReportModalByUserId(@Param("id")Integer id);
}
