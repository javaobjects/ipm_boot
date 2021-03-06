/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLevelDao
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmLevelBean;

/**
 * @ClassName: AlarmLevelDao
 * @Description: AlarmLevel DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmLevelDao {

    /**
     * 
     * @Title: getAlarmLevelListByTypeId
     * @Description: 根据typeId返回告警级别集合
     * @param typeId 告警类型id
     * @return List<AlarmLevelBean>
     * @author chensq
     */
    @Select("select id as id, " 
            +" type_id as typeId, " 
            +" nameen as nameen, " 
            +" namezh as namezh " 
            +" from " 
            +" ipm_alarm_level " 
            +" where " 
            +" type_id = ${typeId} ")
    List<AlarmLevelBean> getAlarmLevelListByTypeId(@Param("typeId") String  typeId);
    
    /**
     * 
     * @Title: getAlarmLevelList
     * @Description: 告警级别集合
     * @return List<AlarmLevelBean>
     * @author chensq
     */
    @Select("select id as id, " 
            +" type_id as typeId, " 
            +" nameen as nameen, " 
            +" namezh as namezh " 
            +" from " 
            +" ipm_alarm_level "
            +" ORDER BY FIELD(id,2,3,4,1) ASC  "
            + "")
    List<AlarmLevelBean> getAlarmLevelList();
    
}
