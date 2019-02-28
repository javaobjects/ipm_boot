/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewDao
 *创建人:long    创建时间:2017年9月7日
 */
package com.protocolsoft.word.dao;

import java.util.List;




import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.daoprovider.ReportEmailDaoProvider;
/**
 * 
 * @ClassName: ReportEmailDao
 * @Description: 
 * @author 刘斌
 *
 */
@Repository
public interface ReportEmailDao {

    /**
     * @Title: insertTimerReport
     * @Description: 新增
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Insert("INSERT INTO ipm_report_email(plan_id,sender,reciver,email)"
            + " values(#{planId},#{sender},#{recriver},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReportEmail(ReportEmailBean bean);
    
    @Insert("INSERT INTO ipm_report_email(plan_id,sender,reciver,email)"
            + " SELECT #{planId},#{sender},#{recriver},#{email} FROM"
            + " DUAL WHERE NOT EXISTS (SELECT id FROM ipm_report_email WHERE email= #{email} AND plan_id =#{planId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReportEmails(ReportEmailBean bean);
    
    
    /**
     * @Title: insertTimerReport
     * @Description: 新增
     * @param bean
     * @return int
     * @author 刘斌
     */
    @InsertProvider(type = ReportEmailDaoProvider.class, method = "insertAll")
    int insertAll(@Param("list") List<ReportEmailBean> list);
    
    /**
     * @Title: deleteTimerReport
     * @Description: 删除通过ID
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_report_email where plan_id = #{id}")
    void deleteReportEmails(Integer id);
    
    /**
     * @Title: selectReportTimerDetailsById
     * @Description: 关联查找
     * @param id
     * @return List<ReportTimerDetailBean>
     * @author 刘斌
     */
    @Select("SELECT id as id,plan_id as planId,sender as "
            + "sender,reciver as recriver,email as email "
            + " from ipm_report_email WHERE plan_id = #{id}")
    List<ReportEmailBean> selectReportEmails(Integer id);
    
    /**
     * @Title: selectReportTimerDetailsById
     * @Description: 关联查找
     * @param id
     * @return List<ReportTimerDetailBean>
     * @author 刘斌
     */
    @Select("SELECT id as id,plan_id as planId,sender as "
            + "sender,reciver as recriver,email as email "
            + " from ipm_report_email")
    List<ReportEmailBean> selectAllEmails();
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_email set plan_id = #{planId},"
            + " sender = #{sender},reciver = #{recriver},email = #{email} where id = #{id}")
    int updateReportEmail(ReportEmailBean bean);
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_email set "
            + " sender = #{sender},reciver = #{recriver} where plan_id = #{planId} AND  email = #{email}")
    int updateReportEmails(ReportEmailBean bean);
    
    /**
     * @Title: deleteTimerReport
     * @Description: 删除通过ID
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_report_email where id = #{id}")
    void deleteReportEmail(Integer id);
}
