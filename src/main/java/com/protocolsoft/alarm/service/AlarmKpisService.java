/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmKpisService.java
 *创建人: chensq    创建时间: 2018年5月28日
 */
package com.protocolsoft.alarm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.dao.KpisDao;

/**
 * @ClassName: AlarmKpisService
 * @Description: ipm_kpis表的数据
 * @author chensq
 *
 */
@Service
public class AlarmKpisService {
   
    /**
     * @Fields kpisDao : kpis表
     */
    @Autowired
    private KpisDao kpisDao;
    
    /**
     * @Fields alarmKpisMap : 告警设置所有kpis的map,<moduleId,<id,kpiName>> 
     */
    public static Map<String, Map<String, String>> alarmKpisMap = null;
    
    /**
     * @Title: setAllKpisToMap
     * @Description: 将所有kpis，放入map(改方法在表有变动时重新调用该方法)
     * @author chensq
     */
    public void setAllKpisToMap(){
        Map<String, Map<String, String>> map=null;
        List<KpisBean> kpisList= kpisDao.getAllKpisInfo();
        if (kpisList!=null && kpisList.size()>0) {
            map=new LinkedHashMap<String, Map<String, String>>();
            for (int x=0; x<kpisList.size(); x++) {
                long id =kpisList.get(x).getId();
                String name =kpisList.get(x).getName();
                long moduleId =kpisList.get(x).getModuleId();
                if(map.get(String.valueOf(moduleId))==null){
                    Map<String, String> subMap=new LinkedHashMap<String, String>();
                    subMap.put(String.valueOf(id), name);
                    map.put(String.valueOf(moduleId), subMap);
                }else{
                    Map<String, String> subMap= map.get(String.valueOf(moduleId));
                    subMap.put(String.valueOf(id), name);
                }
            }
        }
        alarmKpisMap=map;
    }
    
}
