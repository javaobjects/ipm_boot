/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmTypeDao
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmTypeBean;

/**
 * @ClassName: AlarmTypeDao
 * @Description: AlarmType DAO
 * @author chensq
 *
 */
@Repository
public interface  AlarmTypeDao {

   /**
    * @Title: getAlarmTypeList
    * @Description: 返回告警类型集合
    * @return List<AlarmTypeBean>
    * @author chensq
    */
    @Select("select id as id, " 
                +" nameen as nameen, " 
                +" namezh as namezh " 
           +" from ipm_alarm_type ")
    List<AlarmTypeBean> getAlarmTypeList();
    
    
}
