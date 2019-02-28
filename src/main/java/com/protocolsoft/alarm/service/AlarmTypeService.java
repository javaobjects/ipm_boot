/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmTypeService
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.protocolsoft.alarm.dao.AlarmLevelDao;
import com.protocolsoft.alarm.dao.AlarmTypeDao;

/**
 * @ClassName： AlarmTypeService
 * @Description: 告警类型service
 * @author chensq
 */
@Service
public class AlarmTypeService {
     
    /**
     * @Fields alarmTypeDao : 告警类型DAO
     */
    @Autowired
    private AlarmTypeDao alarmTypeDao;
   
    /**
     * @Fields alarmLevelDao : 告警级别DAO
     */
    @Autowired
    private AlarmLevelDao alarmLevelDao;
        
}
