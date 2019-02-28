/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewDao
 *创建人:long    创建时间:2017年9月7日
 */
package com.protocolsoft.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.daoprovider.ReportBussinessDaoProvider;

/**
 * 
 * @ClassName: ReportBusinessDao
 * @Description: 
 * @author 刘斌
 *
 */
@Repository
public interface ReportBusinessDao {

    /**
     * 
     * @Title: insertAll
     * @Description: 
     * @param list
     * @return int
     * @author 刘斌
     */
    @InsertProvider(type = ReportBussinessDaoProvider.class, method = "insertAll")
    int insertAll(@Param("list") List<ReportBusinessBean> list);
    
    /**
     * @Title: deleteTimerReport
     * @Description: 删除通过ID
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_report_business where plan_id = #{id}")
    void deleteReportEBusiness(Integer id);
    
    /**
     * @Title: selectReportTimerDetailsById
     * @Description: 关联查找
     * @param id
     * @return List<ReportTimerDetailBean>
     * @author 刘斌
     */
    @Select("SELECT id as id,plan_id as planId,module_id as "
            + "modulId,name,business_id as bussinessId "
            + " from ipm_report_business WHERE plan_id = #{id}")
    List<ReportBusinessBean> selectReportBusiness(Integer id);
}
