/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmKpiAlgorithmDao
 *创建人:chensq    创建时间:2017年10月31日
 */
package com.protocolsoft.alarm.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmKpiAlgorithmBean;
/**
 * @ClassName: AlarmKpiAlgorithmDao
 * @Description: AlarmKpiAlgorithm DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmKpiAlgorithmDao {
 
    /**
     * 
     * @Title: addAlarmKpiAlgorithm
     * @Description: 添加告警kpi与算法关系表
     * @param alarmKpiAlgorithmBean 算法关系表bean
     * @return int
     * @author chensq
     */    
    @Insert("insert into ipm_alarm_kpialgorithm (kpitype,kpi_id,algorithm_id) "
            + " values (#{kpitype},#{kpiId},#{algorithmId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addAlarmKpiAlgorithm(AlarmKpiAlgorithmBean alarmKpiAlgorithmBean);
    
    /**
     * 
     * @Title: updateAlarmKpiAlgorithm
     * @param alarmKpiAlgorithmBean kpi算法关系表对象
     * @Description: 根据id修改kpi算法关系表对象
     * @return int
     * @author chensq
     */    
    @Update("update ipm_alarm_kpialgorithm set "
            + "algorithm_id=#{algorithmId} "
            + "where kpitype=#{kpitype} and kpi_id=#{kpiId} ")
    int updateAlarmKpiAlgorithm(AlarmKpiAlgorithmBean alarmKpiAlgorithmBean);
    
    /**
     * 
     * @Title: getAlarmKpiAlgorithmByKpiId
     * @Description: 查询ipm_alarm_kpialgorithm根据 kpiId
     * @param kpiId 告警设置的kpi id
     * @return AlarmKpiAlgorithmBean
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "kpitype as kpitype, " 
            + "kpi_id as kpiId, "
            + "algorithm_id as algorithmId "
            + "from " 
            + "ipm_alarm_kpialgorithm "
            + "where "
            + "kpi_id = ${kpiId} and "
            + "kpitype = ${kpitype} ")
    AlarmKpiAlgorithmBean getAlarmKpiAlgorithmByKpiId(
            @Param("kpiId") long  kpiId,
            @Param("kpitype") long  kpitype);
    
}
