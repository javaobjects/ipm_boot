/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmSetDelBaseLineUtil.java
 *创建人: chensq    创建时间: 2018年8月20日
 */
package com.protocolsoft.alarm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;

/**
 * @ClassName: AlarmSetDelBaseLineUtil
 * @Description: 将告警设置按照业务等多个kpi捏为一个kpi(智能告警使用)
 * @author chensq
 *
 */
public class AlarmSetDelBaseLineUtil {

    /**
     * 
     * @Title: delRedundancyAlarmSet
     * @Description: 捏智能告警的多个kpi对象为一个对象
     * @return Map<String,AlarmSetBean>
     * @author chensq
     */
    public static  Map<String, List<AlarmRemoveRedundancyBlBean>> delRedundancyAlarmSet(Map<String, AlarmSetBean> alarmSetThreadMaps){
          
        //捏合智能告警(模块id 观察点id 业务id kpitype)
        Map<String, List<AlarmRemoveRedundancyBlBean>> baselineMapList=null;  
        if(alarmSetThreadMaps!=null && alarmSetThreadMaps.size()>0){
            //init
            baselineMapList=new LinkedHashMap<String, List<AlarmRemoveRedundancyBlBean>>();
            //迭代
            Set<Entry<String, AlarmSetBean>> it = alarmSetThreadMaps.entrySet(); 
            Iterator<Entry<String, AlarmSetBean>> its =it.iterator();
            while (its.hasNext()) {
                Entry<String, AlarmSetBean> entry = its.next();
                String key = entry.getKey();
                AlarmSetBean bean= entry.getValue();
                //key array 
                String []array=key.split(",");
                //智能告警
                if("2".equalsIgnoreCase(array[5])){
                    //模块id 观察点id 业务id kpitype 相等与否
                    String tempKey=array[0]+","+array[1]+","+array[2]+","+array[3];
                    if(baselineMapList.get(tempKey)==null){
                        //组装对象
                        AlarmRemoveRedundancyBlBean temp=new AlarmRemoveRedundancyBlBean(); 
                        temp.setAlarmValueList(bean.getAlarmValueList());
                        temp.setBusinessId(bean.getBusinessId());
                        temp.setHighLowBaselineFlag(bean.getHighLowBaselineFlag());
                        temp.setIdList(bean.getIdList());
                        temp.setKpiId(bean.getKpiId());
                        temp.setModuleId(bean.getModuleId());
                        temp.setWatchpointId(bean.getWatchpointId());      
                        //对象入list
                        List<AlarmRemoveRedundancyBlBean> list=new ArrayList<AlarmRemoveRedundancyBlBean>();
                        list.add(temp);
                        //入map
                        baselineMapList.put(tempKey, list);
                    }else{
                        //获取原有集合
                        List<AlarmRemoveRedundancyBlBean> list=baselineMapList.get(tempKey);
                        //组装对象
                        AlarmRemoveRedundancyBlBean temp=new AlarmRemoveRedundancyBlBean(); 
                        temp.setAlarmValueList(bean.getAlarmValueList());
                        temp.setBusinessId(bean.getBusinessId());
                        temp.setHighLowBaselineFlag(bean.getHighLowBaselineFlag());
                        temp.setIdList(bean.getIdList());
                        temp.setKpiId(bean.getKpiId());
                        temp.setModuleId(bean.getModuleId());
                        temp.setWatchpointId(bean.getWatchpointId());   
                        //add to list
                        list.add(temp);
                    }
                }
            }
        }
        
        //返回除了基线设置以外的告警设置
        return baselineMapList;

    }
    

}
