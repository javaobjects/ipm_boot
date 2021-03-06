/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmRedundanceDelUtil.java
 *创建人: chensq    创建时间: 2018年8月7日
 */
package com.protocolsoft.alarm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.thread.AlarmSetMinuteRunAble;
import com.protocolsoft.alarm.thread.AlarmSetSecondRunAble;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmRedundanceDelUtil
 * @Description: 告警产生处，告警业务删除，修改等等情况下，更改运算中的告警设置信息
 * @author chensq
 *
 */
public class AlarmRedundanceDelUtil {
    /**
     * 
     * @Title: delAlarmRedundanceSet
     * @Description: 告警设置产生处，对冗余的进行删除
     * @author chensq
     */
    public static void delAlarmRedundanceSet(WatchpointDao watchpointDao, Map<String, AlarmSetBean> alarmSetThreadMaps){
        //标准的告警设置清单
        Map<String, String> standardAlarmSetList=new HashMap<String, String>();
        
        //观察点集合
        List<WatchpointBean> wplist=watchpointDao.getFindAll();    
        
        //统一清除 checkAlarmSecondList中去除checkAlarmSecondList中有，AlarmSetService.alarmSetThreadMap无的数据
        if(alarmSetThreadMaps!=null && alarmSetThreadMaps.size()>0){
            Set<Entry<String, AlarmSetBean>> it = alarmSetThreadMaps.entrySet(); 
            Iterator<Entry<String, AlarmSetBean>> its =it.iterator();
            while (its.hasNext()) {
                Entry<String, AlarmSetBean> entry = its.next();
                AlarmSetBean valueinfo = entry.getValue(); 
                long moduleId=valueinfo.getModuleId();
                if(moduleId==10){
                    StringBuffer tempBuf=new StringBuffer();
                    tempBuf.append(valueinfo.getModuleId());
                    tempBuf.append(",");
                    tempBuf.append(0);
                    tempBuf.append(",");                        
                    tempBuf.append(valueinfo.getBusinessId());
                    tempBuf.append(",");                        
                    tempBuf.append(valueinfo.getKpitype());
                    tempBuf.append(",");                        
                    tempBuf.append(valueinfo.getKpiId());
                    tempBuf.append(",");                        
                    tempBuf.append(valueinfo.getHighLowBaselineFlag());
                    standardAlarmSetList.put(tempBuf.toString(), tempBuf.toString());
                }else{
                    if(wplist!=null && wplist.size()>0){
                        for(int w=0; w<wplist.size(); w++){
                            StringBuffer tempBuf=new StringBuffer();
                            tempBuf.append(valueinfo.getModuleId());
                            tempBuf.append(",");
                            tempBuf.append(wplist.get(w).getId());
                            tempBuf.append(",");                        
                            tempBuf.append(valueinfo.getBusinessId());
                            tempBuf.append(",");                        
                            tempBuf.append(valueinfo.getKpitype());
                            tempBuf.append(",");                        
                            tempBuf.append(valueinfo.getKpiId());
                            tempBuf.append(",");                        
                            tempBuf.append(valueinfo.getHighLowBaselineFlag());
                            standardAlarmSetList.put(tempBuf.toString(), tempBuf.toString());
                        }
                    }
                }
            }
        }
        
        //10秒级别的告警线程(普通)ok
        if (AlarmSetSecondRunAble.checkAlarmSecondList!=null && AlarmSetSecondRunAble.checkAlarmSecondList.size()>0) {
            for (int x=0; x<AlarmSetSecondRunAble.checkAlarmSecondList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=AlarmSetSecondRunAble.checkAlarmSecondList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if(standardAlarmSetList!=null && standardAlarmSetList.size()>0){
                            if(standardAlarmSetList.get(itemKey)==null){
                                AlarmSetSecondRunAble.checkAlarmSecondList.remove(tempItemsMap);
                            }
                        }else{
                            AlarmSetSecondRunAble.checkAlarmSecondList.remove(tempItemsMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //1分级别的告警线程(普通,url+报文)ok
        if (AlarmSetMinuteRunAble.checkAlarmMinuteList!=null && AlarmSetMinuteRunAble.checkAlarmMinuteList.size()>0) {
            for (int x=0; x<AlarmSetMinuteRunAble.checkAlarmMinuteList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=AlarmSetMinuteRunAble.checkAlarmMinuteList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if(standardAlarmSetList!=null && standardAlarmSetList.size()>0){
                            if(standardAlarmSetList.get(itemKey)==null){
                                AlarmSetMinuteRunAble.checkAlarmMinuteList.remove(tempItemsMap);
                            }
                        }else{
                            AlarmSetMinuteRunAble.checkAlarmMinuteList.remove(tempItemsMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //10秒级别的告警线程(智能)
        if (AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList!=null && AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList.size()>0) {
            for (int x=0; x<AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if(standardAlarmSetList!=null && standardAlarmSetList.size()>0){
                            if(standardAlarmSetList.get(itemKey)==null){
                                AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList.remove(tempItemsMap);
                            }
                        }else{
                            AlarmSecondBaseLineUtil.checkBaseLineAlarmSecondList.remove(tempItemsMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //1分级别的告警线程(智能url)
        if (AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList!=null && AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList.size()>0) {
            for (int x=0; x<AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if(standardAlarmSetList!=null && standardAlarmSetList.size()>0){
                            if(standardAlarmSetList.get(itemKey)==null){
                                AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList.remove(tempItemsMap);
                            }
                        }else{
                            AlarmMinuteUrlBaseLineUtil.checkBaseLineAlarmMinuteUrlList.remove(tempItemsMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //1分级别的告警线程(智能bus)
        if (AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList!=null 
                && AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList.size()>0) {
            for (int x=0; x<AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if(standardAlarmSetList!=null && standardAlarmSetList.size()>0){
                            if(standardAlarmSetList.get(itemKey)==null){
                                AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList.remove(tempItemsMap);
                            }
                        }else{
                            AlarmMinuteBusiBaseLineUtil.checkBaseLineAlarmMinuteBusiList.remove(tempItemsMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
