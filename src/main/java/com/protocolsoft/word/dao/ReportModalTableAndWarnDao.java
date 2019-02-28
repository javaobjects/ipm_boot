/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.word.bean.ReportModalTableAndWranBean;
import com.protocolsoft.word.daoprovider.ReportModalTableAndWraningProvider;

/**
 * 
 * @ClassName: ReportModalTableAndWarnDao
 * @Description: 
 * @author 刘斌
 *
 */
@Repository
public interface ReportModalTableAndWarnDao {

        
        /**
         * @Title: insertTimerReport
         * @Description: 新增
         * @param bean
         * @return int
         * @author 刘斌
         */
    @InsertProvider(type = ReportModalTableAndWraningProvider.class, method = "insertAll")
    int insertAll(@Param("list") List<ReportModalTableAndWranBean> list);
        
        /**
         * @Title: deleteTimerReport
         * @Description: 删除通过ID
         * @param id void
         * @author 刘斌
         */
    @Delete("delete from ipm_modal_tableandalarm where modal_id = #{id}")
    void deleteReportModalTableAndWranBeans(Integer id);
        
        /**
         * @Title: selectReportTimerDetailsById
         * @Description: 关联查找
         * @param id
         * @return List<ReportTimerDetailBean>
         * @author 刘斌
         */
    @Select("SELECT id as id,modal_id as modalId,module_id as "
                + "modultId,table_having as tableHaving,warning_having as warningHaving "
                + " from ipm_modal_tableandalarm WHERE modal_id = #{id}")
    List<ReportModalTableAndWranBean> selectReportModalTableAndWranBeans(Integer id);
        
        /**
         * 
         * @Title: selectReportModalTableBeanByTwo
         * @Description: 
         * @return List<ReportModalTableAndWranBean>
         * @author 刘斌
         */
    @Select("SELECT id as id,modal_id as modalId,module_id as "
                + "modultId,table_having as tableHaving,warning_having as warningHaving "
                + " from ipm_modal_tableandalarm WHERE module_id = 10 and table_having = 1 and modal_id = #{id}")
    ReportModalTableAndWranBean selectReportModalTableBeanByTwo(Integer id);
        
        /**
         * 
         * @Title: selectReportModalTableBeanByTwo
         * @Description: 
         * @param id
         * @return ReportModalTableAndWranBean
         * @author 刘斌
         */
    @Select("SELECT id as id,modal_id as modalId,module_id as "
                + "modultId,table_having as tableHaving,warning_having as warningHaving "
                + " from ipm_modal_tableandalarm WHERE module_id = 10 and warning_having = 1 and modal_id = #{id}")
    ReportModalTableAndWranBean selectReportModalWranBeanByTwo(Integer id);
        
}
