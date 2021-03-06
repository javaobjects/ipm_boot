/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmAlgorithmService
 *创建人:chensq    创建时间:2017年11月14日
 */
package com.protocolsoft.alarm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;
import com.protocolsoft.alarm.dao.AlarmAlgorithmDao;

/**
 * @ClassName： AlarmAlgorithmService
 * @Description: 告警算法service
 * @author chensq
 * 
 */
@Service
public class AlarmAlgorithmService {

    /**
     * @Fields alarmAlgorithmDao : 告警算法表
     */
    @Autowired
    private AlarmAlgorithmDao alarmAlgorithmDao;
    
    /**
     * @Fields alarmAlgorithmMap : 告警设置所有告警算法的map
     */
    public static Map<String, AlarmAlgorithmBean> alarmAlgorithmMap = null;
    
    /**
     * @Title: setAllAlarmAlgorithmToMap
     * @Description: 将所有告警算法，放入map(改方法在告警算法表有变动时重新调用该方法)
     * @author chensq
     */
    public void setAllAlarmAlgorithmToMap(){
        Map<String, AlarmAlgorithmBean> map=null;
        List<AlarmAlgorithmBean> alarmAlgorithmList= alarmAlgorithmDao.getAlarmAlgorithmList();
        if (alarmAlgorithmList!=null && alarmAlgorithmList.size()>0) {
            map=new HashMap<String, AlarmAlgorithmBean>();
            for (int x=0; x<alarmAlgorithmList.size(); x++) {
                int algorithm =alarmAlgorithmList.get(x).getAlgorithm();
                map.put(String.valueOf(algorithm), alarmAlgorithmList.get(x));
            }
        }
        alarmAlgorithmMap=map;
    }
    
}
