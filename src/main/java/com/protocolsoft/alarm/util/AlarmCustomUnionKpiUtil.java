/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiUtil
 *创建人:chensq    创建时间:2018年4月16日
 */
package com.protocolsoft.alarm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiThreadBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmCustomUnionKpiUtil
 * @Description: 组合告警线程使用的工具类
 * @author chensq
 *
 */
public class AlarmCustomUnionKpiUtil {
   
    /**
     * @Title: getAllThreadToCustomKpiMap
     * @Description: 将告警线程中的内容筛选出组合告警信息封装好,放入map
     * @param alarmSetThreadMaps
     * @param watchpointDao
     * @return Map<String,AlarmCustomUnionKpiThreadBean>
     * @author chensq
     */
    public Map<String, AlarmCustomUnionKpiThreadBean> getAllThreadToCustomKpiMap(
            Map<String, AlarmSetBean> alarmSetThreadMaps,
            WatchpointDao watchpointDao){
        
        //所有观察点id
        List<WatchpointBean> wpList=watchpointDao.getFindAll();
        //最终返回map
        Map<String, AlarmCustomUnionKpiThreadBean> customMap=new HashMap<String, AlarmCustomUnionKpiThreadBean>();
        
        //迭代
        if(alarmSetThreadMaps!=null && alarmSetThreadMaps.size()>0){
            Set<Entry<String, AlarmSetBean>> it = alarmSetThreadMaps.entrySet(); 
            Iterator<Entry<String, AlarmSetBean>> its =it.iterator();
            while (its.hasNext()) {
                Entry<String, AlarmSetBean> entry = its.next();
                AlarmSetBean valueinfo = entry.getValue(); 
                //属性
                int highLowBaselineFlag=valueinfo.getHighLowBaselineFlag();
                
                //组合告警情况
                if(highLowBaselineFlag==4){
                    this.setToMap(customMap, valueinfo, wpList);
                }
            }
        }
        
        return customMap;
    }
    
    /**
     * @Title: setToMap
     * @Description: 设置入map
     * @param customMap
     * @param valueinfo
     * @param wpList void
     * @author chensq
     */
    public void setToMap(Map<String, AlarmCustomUnionKpiThreadBean> customMap,
            AlarmSetBean valueinfo,
            List<WatchpointBean> wpList){
        if(wpList!=null && wpList.size()>0){
            //临时对象
            StringBuffer keyBuf=null;
            AlarmCustomUnionKpiThreadBean customKpiThreadBean=null;
            if(valueinfo.getModuleId()==10){
              //组装key
                keyBuf=new StringBuffer();
                keyBuf.append(valueinfo.getModuleId());
                keyBuf.append(",");
                keyBuf.append(0);
                keyBuf.append(",");
                keyBuf.append(valueinfo.getBusinessId());
                keyBuf.append(",");
                //组装bean
                String[] alarmValueList =valueinfo.getAlarmValueList().split("\\|");                

                customKpiThreadBean=new AlarmCustomUnionKpiThreadBean();
                customKpiThreadBean.setWatchpointId(valueinfo.getBusinessId());
                customKpiThreadBean.setModuleId(valueinfo.getModuleId());
                customKpiThreadBean.setBusinessId(valueinfo.getBusinessId());
                customKpiThreadBean.setKpitype(valueinfo.getKpitype());
                customKpiThreadBean.setKpiId(valueinfo.getKpiId());
                customKpiThreadBean.setIdList(valueinfo.getIdList());
                customKpiThreadBean.setLevelList(valueinfo.getLevelList());
                customKpiThreadBean.setAlarmValueList(valueinfo.getAlarmValueList());
                
                customKpiThreadBean.setMaxLevel(0);
                customKpiThreadBean.setBeforeCount(alarmValueList.length);
                Map<String, Integer> kpiIdCountMap=new HashMap<String, Integer>();
                for(int k=0; k<alarmValueList.length; k++){
                    kpiIdCountMap.put(String.valueOf(alarmValueList[k].split(",")[0]), 0);
                }
                customKpiThreadBean.setKpiIdCountMap(kpiIdCountMap);
                
                customMap.put(keyBuf.toString(), customKpiThreadBean); 
            }else{
                for(int x=0; x<wpList.size(); x++){
                    //组装key
                    keyBuf=new StringBuffer();
                    keyBuf.append(valueinfo.getModuleId());
                    keyBuf.append(",");
                    keyBuf.append(wpList.get(x).getId());
                    keyBuf.append(",");
                    keyBuf.append(valueinfo.getBusinessId());
                    keyBuf.append(",");
                    //组装bean
                    String[] alarmValueList =valueinfo.getAlarmValueList().split("\\|");                

                    customKpiThreadBean=new AlarmCustomUnionKpiThreadBean();
                    customKpiThreadBean.setWatchpointId(wpList.get(x).getId());
                    customKpiThreadBean.setModuleId(valueinfo.getModuleId());
                    customKpiThreadBean.setBusinessId(valueinfo.getBusinessId());
                    customKpiThreadBean.setKpitype(valueinfo.getKpitype());
                    customKpiThreadBean.setKpiId(valueinfo.getKpiId());
                    customKpiThreadBean.setIdList(valueinfo.getIdList());
                    customKpiThreadBean.setLevelList(valueinfo.getLevelList());
                    customKpiThreadBean.setAlarmValueList(valueinfo.getAlarmValueList());
                    
                    customKpiThreadBean.setMaxLevel(0);
                    customKpiThreadBean.setBeforeCount(alarmValueList.length);
                    Map<String, Integer> kpiIdCountMap=new HashMap<String, Integer>();
                    for(int k=0; k<alarmValueList.length; k++){
                        kpiIdCountMap.put(String.valueOf(alarmValueList[k].split(",")[0]), 0);
                    }
                    customKpiThreadBean.setKpiIdCountMap(kpiIdCountMap);
                    
                    customMap.put(keyBuf.toString(), customKpiThreadBean); 
                }
            }
            
        }
    }
    
}
