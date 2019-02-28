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

import com.protocolsoft.word.bean.TimerReportBean;


/**
 * @ClassName: TimerReportDao
 * @Description: 操作数据库中定时报表对象的接口
 * @author 刘斌
 *
 */
@Repository
public interface TimerReportDao {

    /**
     * @Title: insertTimerReport
     * @Description: 新增
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Insert("INSERT INTO ipm_timer_report(type_id,user_id,name,createtime,state)"
            + " SELECT #{typeId},#{userId},#{name},#{createtime},#{state} "
            + "FROM DUAL WHERE NOT EXISTS (SELECT name FROM ipm_timer_report WHERE name = #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTimerReport(TimerReportBean bean);

    /**
     * @Title: selectTimerReport
     * @Description: 通过id查询
     * @param id
     * @return TimerReportBean
     * @author 刘斌
     */
    @Select("select " + "id as id," + "type_id as typeId," + "name as name,"
            + "user_id as userId," + "state as state,"
            + "createtime as createtime "
            + "from ipm_timer_report where id = #{id}")
    TimerReportBean selectTimerReport(Integer id);

    /**
     * @Title: updateTimerReport
     * @Description: 修改
     * @param bean
     * @return int
     * @author 刘斌
     */
    @Update("update ipm_timer_report set name = #{name},type_id = #{typeId},"
            + " user_id = #{userId},createtime = #{createtime} where id = #{id}")
    int updateTimerReport(TimerReportBean bean);

    /**
     * @Title: selectAllTimerReport
     * @Description: 查询全部
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    @Select("SELECT b.id as id,b.name as name,b.type_id as typeId,b.user_id as userId,"
            + "b.state as state,a.username as userName,c.name as typeName,b.createtime as createtime  "
            + " from  ipm_system_user AS a INNER JOIN "
            + "ipm_timer_report AS b ON b.user_id = a.id LEFT JOIN ipm_report_type AS c ON b.type_id = c.id "
            + "where a.id = #{id} order by createtime desc")
    List<TimerReportBean> selectAllTimerReport(Integer id);

    /**
     * @Title: deleteTimerReport
     * @Description: 删除
     * @param id void
     * @author 刘斌
     */
    @Delete("delete from ipm_timer_report where id = #{id}")
    void deleteTimerReport(Integer id);


    /**
     * 通过id 获取未被冻结的定时报表
     * @return List<TimerReportBean>
     * 
     */
    @Select("select " + "id as id," + "type_id as typeId," + "name as name,"
            + "user_id as userId," + "state as state,"
            + "createtime as createtime "
            + "from ipm_timer_report where type_id = #{id} and state = 1")
    List<TimerReportBean> selectNeedTimerReport(Integer id);
    
    /**
     * 修改 2018年3月19日 下午2:06:40
     * 
     * @param AppBusinessBean
     * @return int
     * @exception
     * @see
     */
    @Update("update ipm_timer_report set state = #{state} where id = #{id}")
    int updateTimerReportState(@Param("id")int id, @Param("state")int state);

}
