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

import com.protocolsoft.word.bean.ReportHistoryBean;

/**
 * @ClassName: ReportHistoryDao
 * @Description: 操作数据库中历史报表信息的接口
 * @author 刘斌
 *
 */
@Repository
public interface ReportHistoryDao {
    
    /**
     * @Title: insertReportHistory
     * @Description: 插入
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Insert("INSERT INTO ipm_report_history(name,type_id,createtime,datastime,dataetime,user_id,send_state,send_names,recive_names)"
            + "SELECT #{name},#{typeId},#{createtime},#{datastime},#{dataetime},#{userId},#{sendStatus},#{sendNames},#{recriveNames}"
            + " FROM DUAL WHERE NOT EXISTS (SELECT id FROM ipm_report_history WHERE name = #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReportHistory(ReportHistoryBean bean);

    /**
     * @Title: selectReportHistory
     * @Description: 通过id查询
     * @param id
     * @return ReportHistoryBean
     * @author 刘斌
     */
    @Select("select " + "id as id," + "name as name," + "type_id as typeId,"
            + "createtime as createtime," + "datastime as datastime,"
            + "dataetime as dataetime "
            + "from ipm_report_history where id = #{id}")
    ReportHistoryBean selectReportHistory(int id);

    /**
     * @Title: selectAllReportHistory
     * @Description: 查询全部 
     * @param starttime
     * @param endtiem
     * @return List<ReportHistoryBean>
     * @author 刘斌
     */
    @Select("SELECT a.id as id,a.type_id as typeId,a.name as name,a.createtime"
            + " as createtime,a.datastime as datastime,a.dataetime as dataetime,send_state as sendStatus,"
            + "a.send_names as sendNames,a.send_time as sendTime,a.recive_names as recriveNames,"
            + " b.name as typeName from ipm_report_history AS a "
            + "LEFT JOIN ipm_report_type AS b ON a.type_id = b.id "
            + "where createtime > #{starttime} and createtime < #{endtiem} order by createtime desc")
    List<ReportHistoryBean> selectAllReportHistory(@Param("starttime")Long starttime, @Param("endtiem")Long endtiem);

    /**
     * @Title: deleteReportHistory
     * @Description: 删除
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_report_history where id = #{id}")
    void deleteReportHistory(Integer id);

    /**
     * @Title: selectReportHistoryName
     * @Description: 通过id获取历史报表的名字
     * @param id
     * @return String
     * @author 刘斌
     */
    @Delete("select name from ipm_report_history where id = #{id}")
    String selectReportHistoryName(Integer id);
    
    /**
     * @Title: selectAllReportHistory
     * @Description: 查询全部 
     * @param starttime
     * @param endtiem
     * @return List<ReportHistoryBean>
     * @author 刘斌
     */
    @Select("SELECT a.id as id,a.type_id as typeId,a.name as name,a.createtime"
            + " as createtime,a.datastime as datastime,a.dataetime as dataetime,send_state as sendStatus,"
            + "a.send_names as sendNames,a.send_time as sendTime,a.recive_names as recriveNames,"
            + " b.name as typeName from ipm_report_history AS a "
            + "LEFT JOIN ipm_report_type AS b ON a.type_id = b.id "
            + "where user_id = #{id} order by createtime desc")
    List<ReportHistoryBean> selectReportHistoryByUserId(@Param("id")int id);
    
    /**
     * 
     * @Title: selectReportHistoryCount
     * @Description: 
     * @return Integer
     * @author 刘斌
     */
    @Select("SELECT COUNT(*) FROM ipm_report_history ")
    Integer selectReportHistoryCount();
    
    /**
     * 
     * @Title: selectReportHistoryCount
     * @Description: 
     * @return Integer
     * @author 刘斌
     */
    @Select("SELECT id FROM ipm_report_history GROUP BY id LIMIT 0,#{count}")
    List<Integer> selectReportHistoryIds(Integer count);
    
    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_report_history set send_state = 2,"
            + " send_time = #{sendTime} where id = #{id}")
    int updateHistoryBeanBySend(@Param("sendTime")Long sendTime, @Param("id")Integer id);
}
