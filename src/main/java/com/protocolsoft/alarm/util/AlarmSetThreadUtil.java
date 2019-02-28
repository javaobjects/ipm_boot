/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetThreadUtil
 *创建人:chensq    创建时间:2018年4月11日
 */
package com.protocolsoft.alarm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.protocolsoft.alarm.bean.AlarmCustomkpigroupBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.dao.AlarmSetThreadDao;
import com.protocolsoft.alarm.service.AlarmSetService;
 
/**
 * @ClassName: AlarmSetThreadUtil
 * @Description: 告警使用的阈值信息更新处理类
 * @author chensq
 *
 */
public class AlarmSetThreadUtil {
    
    /**
     * <p>Title: AlarmSetThreadUtil</p>
     * <p>Description: 构造方法 </p>
     */ 
    public AlarmSetThreadUtil(){
    }
    
    /**
     * @Title: setAlarmSetThreadEmpty
     * @Description: 设置线程使用的map为null
     * @author chensq
     */
    public static void setAlarmSetThreadEmpty(){
        AlarmSetService.alarmSetThreadMap=null;
    }
        
    /**
     * @Title: setAlarmSetThreadInit
     * @Description: 初始化告警阈值信息,容器启动时调用，其他时候更新
     * @param alarmSetThreadDao void
     * @author chensq
     */
    public static void setAlarmSetThreadInit(AlarmSetThreadDao alarmSetThreadDao){
        //初始化
        if(AlarmSetService.alarmSetThreadMap==null){
            AlarmSetService.alarmSetThreadMap=new ConcurrentHashMap<String, AlarmSetBean>();
        }
        
        //告警设置的观察点数组
        long []wpIdArray=null;
        List<AlarmSetBean> wpList=alarmSetThreadDao.getAsetGroupByWpId();
        if (wpList !=null && wpList.size() > 0) {
            wpIdArray=new long[wpList.size()];
            for (int w=0; w<wpList.size() ; w++) {
                wpIdArray[w]=wpList.get(w).getWatchpointId();
            }
        }
        
        //迭代观察点，取业务，返回 map key:watchpoint_id,  value:业务idList
        Map<String, List<AlarmSetBean>> wpBusMap=null;
        if (wpIdArray!=null && wpIdArray.length >0){
            wpBusMap = new HashMap<String, List<AlarmSetBean>>();
            for (int array=0; array<wpIdArray.length ; array++) {
                long tempWpId= wpIdArray[array];
                List<AlarmSetBean> wpBusList=alarmSetThreadDao.getAsetGroupByBusId(tempWpId);
                if (wpBusList!=null && wpBusList.size() >0) {
                    List<AlarmSetBean> tempList=new ArrayList<AlarmSetBean>();
                    for (int wb=0 ; wb<wpBusList.size() ; wb++){
                        tempList.add(wpBusList.get(wb));
                    }
                    wpBusMap.put(String.valueOf(tempWpId), tempList);
                }
            }
        }
        
        //根据 观察点id 业务id 告警类型 查询告警kpi_id
        List<AlarmSetBean> groupBykpiIdList=null;
        if (wpBusMap != null && wpBusMap.size() >0) {
            groupBykpiIdList= new ArrayList<AlarmSetBean>();
            Iterator<Map.Entry<String, List<AlarmSetBean>>> it = wpBusMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<AlarmSetBean>> entry = it.next();
                String key =entry.getKey(); //观察点id
                List<AlarmSetBean> value =entry.getValue(); //观察点下业务集合
                for (int b=0; b<value.size(); b++) {
                    AlarmSetBean busBean=value.get(b);
                    List<AlarmSetBean> tempList=alarmSetThreadDao.getAsetGroupBykpiId(
                            Long.parseLong(key), 
                            busBean.getBusinessId(),
                            busBean.getModuleId());
                    //为对象设置观察点id 业务id
                    if (tempList!=null && tempList.size()>0) {
                        for (int h=0; h<tempList.size(); h++) {
                            groupBykpiIdList.add(tempList.get(h));
                        }
                    }          
                }
            }
        }
        
        //根据watchpointId,businessId,kpiId 查询对应的level
        if (groupBykpiIdList!=null && groupBykpiIdList.size()>0) {
            AlarmSetBean temp=null;
            for (int x=0; x<groupBykpiIdList.size(); x++) {
                temp=groupBykpiIdList.get(x);
                long watchpointId=temp.getWatchpointId();
                long businessId=temp.getBusinessId();
                long moduleId=temp.getModuleId();
                int kpitype=temp.getKpitype();
                long kpiId=temp.getKpiId();
                
                if(kpitype==1){//原生kpi
                    
                    String setsStr=alarmSetThreadDao.getAlarmSetStrNative(temp);
                    String setsBaseLineStr=alarmSetThreadDao.getAlarmSetBaseLineStr(temp);
                    
                    //高阈值 低阈值  智能告警
                    //高阈值
                    if(StringUtils.isNotEmpty(setsStr)){
                        //最终放入集合中的bean
                        AlarmSetBean tempfinalbeanH= new AlarmSetBean();
                        tempfinalbeanH.setWatchpointId(watchpointId);
                        tempfinalbeanH.setBusinessId(businessId);
                        tempfinalbeanH.setKpitype(kpitype); 
                        tempfinalbeanH.setKpiId(kpiId);

                        //普通类型告警-高阈值
                        AlarmSetBean alarmSetHigh=new AlarmSetBean();
                        alarmSetHigh.setWatchpointId(watchpointId);
                        alarmSetHigh.setBusinessId(businessId);
                        alarmSetHigh.setModuleId(moduleId);
                        alarmSetHigh.setKpitype(kpitype);
                        alarmSetHigh.setKpiId(kpiId);
                        alarmSetHigh.setTriggerflag(0);
                        alarmSetHigh.setLevelId(1);
                        alarmSetHigh.setTriggerSearchSetIds(setsStr);
                        
                        List<AlarmSetBean> levelListHigh=alarmSetThreadDao.getAsetGroupByBaseHighLowTrigger(alarmSetHigh);
                        if(levelListHigh!=null && levelListHigh.size()>0){
                            boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListHigh, 0);
                            if(flag){
                                AlarmSetBean tempBean= AlarmValueSetToMemoryUtil.setAlarmInfoToThreadList(tempfinalbeanH, levelListHigh, 0);
                                tempBean.setModuleId(moduleId);
                                String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(tempBean);
                                AlarmSetService.alarmSetThreadMap.put(key, tempBean);
                            }
                        }
                    }
                    //低阈值
                    if(StringUtils.isNotEmpty(setsStr)){
                        //最终放入集合中的bean
                        AlarmSetBean tempfinalbeanL= new AlarmSetBean();
                        tempfinalbeanL.setWatchpointId(watchpointId);
                        tempfinalbeanL.setBusinessId(businessId);
                        tempfinalbeanL.setKpitype(kpitype); 
                        tempfinalbeanL.setKpiId(kpiId);
                        
                        //普通类型告警-低阈值
                        AlarmSetBean alarmSetLow=new AlarmSetBean();
                        alarmSetLow.setWatchpointId(watchpointId);
                        alarmSetLow.setBusinessId(businessId);
                        alarmSetLow.setModuleId(moduleId);
                        alarmSetLow.setKpitype(kpitype);
                        alarmSetLow.setKpiId(kpiId);
                        alarmSetLow.setTriggerflag(1);
                        alarmSetLow.setLevelId(1);
                        alarmSetLow.setTriggerSearchSetIds(setsStr);

                        List<AlarmSetBean> levelListLow=alarmSetThreadDao.getAsetGroupByBaseHighLowTrigger(alarmSetLow);
                        if(levelListLow!=null && levelListLow.size()>0){
                            boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListLow, 1);
                            if(flag){
                                AlarmSetBean tempBean= AlarmValueSetToMemoryUtil.setAlarmInfoToThreadList(tempfinalbeanL, levelListLow, 1);
                                tempBean.setModuleId(moduleId);
                                String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(tempBean);
                                AlarmSetService.alarmSetThreadMap.put(key, tempBean);
                            }
                        }
                    }
                    //智能告警
                    if(StringUtils.isNotEmpty(setsBaseLineStr)){
                      //最终放入集合中的bean
                        AlarmSetBean tempfinalbeanT= new AlarmSetBean();
                        tempfinalbeanT.setWatchpointId(watchpointId);
                        tempfinalbeanT.setBusinessId(businessId);
                        tempfinalbeanT.setKpitype(kpitype); 
                        tempfinalbeanT.setKpiId(kpiId);
                        
                        //基线类型告警
                        AlarmSetBean alarmSetBaseLine=new AlarmSetBean();
                        alarmSetBaseLine.setWatchpointId(watchpointId);
                        alarmSetBaseLine.setBusinessId(businessId);
                        alarmSetBaseLine.setModuleId(moduleId);
                        alarmSetBaseLine.setKpitype(kpitype);
                        alarmSetBaseLine.setKpiId(kpiId);
                        alarmSetBaseLine.setLevelId(1);
                        alarmSetBaseLine.setTriggerSearchSetIds(setsBaseLineStr);

                        List<AlarmSetBean> levelListBaseLine=alarmSetThreadDao.getAsetGroupByBaseLineHighLowTrigger(alarmSetBaseLine);
                        if(levelListBaseLine!=null && levelListBaseLine.size()>0){
                            boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListBaseLine, 2);
                            if(flag){
                                AlarmSetBean baselineBean= AlarmValueSetToMemoryUtil.setBaseLineAlarmInfoToThreadList(tempfinalbeanT,
                                        levelListBaseLine, 2);
                                //if超限了，进行分解
                                ifMoreThan4(baselineBean, moduleId);
                            }
                        }
                    }
                }else{//自定义kpi
                    
                    //最终放入集合中的bean
                    AlarmSetBean tempfinalbeanC= new AlarmSetBean();
                    tempfinalbeanC.setWatchpointId(temp.getWatchpointId());
                    tempfinalbeanC.setBusinessId(temp.getBusinessId());
                    tempfinalbeanC.setModuleId(temp.getModuleId());
                    tempfinalbeanC.setKpitype(temp.getKpitype()); 
                    tempfinalbeanC.setKpiId(temp.getKpiId());
                                        
                    StringBuffer idBuf=null;
                    StringBuffer levelIdBuf=null;
                    StringBuffer alarmvalueBuf=null;
                    
                    if (idBuf == null){
                        idBuf=new StringBuffer();
                    }
                    if (levelIdBuf == null) {
                        levelIdBuf=new StringBuffer();
                    }
                    if (alarmvalueBuf == null) {
                        alarmvalueBuf=new StringBuffer();
                    }
                    
                    tempfinalbeanC.setIdList(idBuf.toString());
                    tempfinalbeanC.setLevelList(levelIdBuf.toString());
                    tempfinalbeanC.setAlarmValueList(alarmvalueBuf.toString());
                    
                    tempfinalbeanC.setHighLowBaselineFlag(4);
                    
                    //返回模块下的业务设置的组合告警kpi的id(ipm_kpis)
                    AlarmSetBean customAlarmSetBean=new AlarmSetBean();
                    customAlarmSetBean.setModuleId(moduleId);
                    customAlarmSetBean.setBusinessId(businessId);                    
                    List<AlarmCustomkpigroupBean> alarmCustomkpigroupBeanList=alarmSetThreadDao.getAsetGroupByCustomUnionKpi(customAlarmSetBean);
                    
                    //迭代 获取这个kpi的id对应的alarmSetId
                    if(alarmCustomkpigroupBeanList!=null && alarmCustomkpigroupBeanList.size()>0){
                        AlarmSetBean ctuBean= null;
                        for(int c=0; c<alarmCustomkpigroupBeanList.size(); c++){
                            long kpiIdTemp= alarmCustomkpigroupBeanList.get(c).getKpiId();
                            customAlarmSetBean.setKpiId(kpiIdTemp);
                            List<AlarmSetBean> custumList= alarmSetThreadDao.getAsetGroupByCustomUnionAlarmSetId(customAlarmSetBean);
                            if(custumList!=null && custumList.size()>0){
                                ctuBean= AlarmValueSetToMemoryUtil.setCustomUnionKpiAlarmInfoToThreadList(tempfinalbeanC, custumList);
                            }
                        }
                        
                        String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(ctuBean);
                        AlarmSetService.alarmSetThreadMap.put(key, ctuBean);
                    }
                    
                }
                                      
            }
        }
        
    }
    
    /**
     * 
     * @Title: ifMoreThan4
     * @Description: if超过数量限制，进行拆分
     * @param baselineBean
     * @param moduleId void
     * @author chensq
     */
    public static void ifMoreThan4(AlarmSetBean baselineBean, long moduleId){
        if(baselineBean!=null){
            baselineBean.setModuleId(moduleId);
            String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(baselineBean);
            AlarmSetService.alarmSetThreadMap.put(key, baselineBean);
        }
    }
     
    /**
     * @Title: setAlarmSetThreadToDoNativeAdd
     * @Description: 执行添加-原生
     * @param alarmSetBeanList
     * @param alarmSetThreadDao void
     * @author chensq
     */
    public static void setAlarmSetThreadToDoNativeAdd(List<AlarmSetBean> alarmSetBeanList, AlarmSetThreadDao alarmSetThreadDao){
        if (alarmSetBeanList!=null && alarmSetBeanList.size()>0) {
            AlarmSetBean temp=null;
            for (int x=0; x<alarmSetBeanList.size(); x++) {
                temp=alarmSetBeanList.get(x);
                long watchpointId=temp.getWatchpointId();
                long businessId=temp.getBusinessId();
                long moduleId=temp.getModuleId();
                int kpitype=temp.getKpitype();
                long kpiId=temp.getKpiId();
                
                String setsStr=alarmSetThreadDao.getAlarmSetStrNative(temp);
                String setsBaseLineStr=alarmSetThreadDao.getAlarmSetBaseLineStr(temp);
                
                //高阈值 低阈值  智能告警
                //高阈值
                if(StringUtils.isNotEmpty(setsStr)){
                    //最终放入集合中的bean
                    AlarmSetBean tempfinalbeanH= new AlarmSetBean();
                    tempfinalbeanH.setWatchpointId(watchpointId);
                    tempfinalbeanH.setBusinessId(businessId);
                    tempfinalbeanH.setKpitype(kpitype); 
                    tempfinalbeanH.setKpiId(kpiId);

                    //普通类型告警-高阈值
                    AlarmSetBean alarmSetHigh=new AlarmSetBean();
                    alarmSetHigh.setWatchpointId(watchpointId);
                    alarmSetHigh.setBusinessId(businessId);
                    alarmSetHigh.setModuleId(moduleId);
                    alarmSetHigh.setKpitype(kpitype);
                    alarmSetHigh.setKpiId(kpiId);
                    alarmSetHigh.setTriggerflag(0);
                    alarmSetHigh.setLevelId(1);
                    alarmSetHigh.setTriggerSearchSetIds(setsStr);
                    
                    List<AlarmSetBean> levelListHigh=alarmSetThreadDao.getAsetGroupByBaseHighLowTrigger(alarmSetHigh);
                    if(levelListHigh!=null && levelListHigh.size()>0){
                        boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListHigh, 0);
                        if(flag){
                            AlarmSetBean tempBean= AlarmValueSetToMemoryUtil.setAlarmInfoToThreadList(tempfinalbeanH, levelListHigh, 0);
                            tempBean.setModuleId(moduleId);
                            String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(tempBean);
                            
                            //容错,特殊情况下，map为空指针
                            if(AlarmSetService.alarmSetThreadMap==null){
                                AlarmSetService.alarmSetThreadMap=new ConcurrentHashMap<String, AlarmSetBean>();
                            }

                            AlarmSetService.alarmSetThreadMap.put(key, tempBean);
                        }
                    }
                }
                //低阈值
                if(StringUtils.isNotEmpty(setsStr)){
                    //最终放入集合中的bean
                    AlarmSetBean tempfinalbeanL= new AlarmSetBean();
                    tempfinalbeanL.setWatchpointId(watchpointId);
                    tempfinalbeanL.setBusinessId(businessId);
                    tempfinalbeanL.setKpitype(kpitype); 
                    tempfinalbeanL.setKpiId(kpiId);
                    
                    //普通类型告警-低阈值
                    AlarmSetBean alarmSetLow=new AlarmSetBean();
                    alarmSetLow.setWatchpointId(watchpointId);
                    alarmSetLow.setBusinessId(businessId);
                    alarmSetLow.setModuleId(moduleId);
                    alarmSetLow.setKpitype(kpitype);
                    alarmSetLow.setKpiId(kpiId);
                    alarmSetLow.setTriggerflag(1);
                    alarmSetLow.setLevelId(1);
                    alarmSetLow.setTriggerSearchSetIds(setsStr);

                    List<AlarmSetBean> levelListLow=alarmSetThreadDao.getAsetGroupByBaseHighLowTrigger(alarmSetLow);
                    if(levelListLow!=null && levelListLow.size()>0){
                        boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListLow, 1);
                        if(flag){
                            AlarmSetBean tempBean= AlarmValueSetToMemoryUtil.setAlarmInfoToThreadList(tempfinalbeanL, levelListLow, 1);
                            tempBean.setModuleId(moduleId);
                            String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(tempBean);
                            AlarmSetService.alarmSetThreadMap.put(key, tempBean);
                        }
                    }
                }
                //智能告警
                if(StringUtils.isNotEmpty(setsBaseLineStr)){
                    //最终放入集合中的bean
                    AlarmSetBean tempfinalbeanT= new AlarmSetBean();
                    tempfinalbeanT.setWatchpointId(watchpointId);
                    tempfinalbeanT.setBusinessId(businessId);
                    tempfinalbeanT.setKpitype(kpitype); 
                    tempfinalbeanT.setKpiId(kpiId);
                    
                    //基线类型告警
                    AlarmSetBean alarmSetBaseLine=new AlarmSetBean();
                    alarmSetBaseLine.setWatchpointId(watchpointId);
                    alarmSetBaseLine.setBusinessId(businessId);
                    alarmSetBaseLine.setModuleId(moduleId);
                    alarmSetBaseLine.setKpitype(kpitype);
                    alarmSetBaseLine.setKpiId(kpiId);
                    alarmSetBaseLine.setLevelId(1);
                    alarmSetBaseLine.setTriggerSearchSetIds(setsBaseLineStr);

                    List<AlarmSetBean> levelListBaseLine=alarmSetThreadDao.getAsetGroupByBaseLineHighLowTrigger(alarmSetBaseLine);
                    if(levelListBaseLine!=null && levelListBaseLine.size()>0){
                        boolean flag=AlarmValueSetToMemoryUtil.checkValueStatus(levelListBaseLine, 2);
                        if(flag){
                            AlarmSetBean baselineBean= AlarmValueSetToMemoryUtil.setBaseLineAlarmInfoToThreadList(tempfinalbeanT,
                                    levelListBaseLine, 2);
                            if(baselineBean!=null){
                                baselineBean.setModuleId(moduleId);
                                String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(baselineBean);
                                AlarmSetService.alarmSetThreadMap.put(key, baselineBean);
                            }
                        }
                    }
                }
                
            }
        }
    }
  
    /**
     * @Title: setAlarmSetThreadToDoCustomAdd
     * @Description: 执行添加-原生
     * @param alarmSetBean
     * @param alarmSetThreadDao void
     * @author chensq
     */
    public static void setAlarmSetThreadToDoCustomAdd(AlarmSetBean alarmSetBean, AlarmSetThreadDao alarmSetThreadDao){

        long moduleId=alarmSetBean.getModuleId();
        long businessId=alarmSetBean.getBusinessId();
        
        //最终放入集合中的bean
        AlarmSetBean tempfinalbeanC= new AlarmSetBean();
        tempfinalbeanC.setWatchpointId(alarmSetBean.getWatchpointId());
        tempfinalbeanC.setBusinessId(alarmSetBean.getBusinessId());
        tempfinalbeanC.setModuleId(alarmSetBean.getModuleId());
        tempfinalbeanC.setKpitype(alarmSetBean.getKpitype()); 
        tempfinalbeanC.setKpiId(alarmSetBean.getKpiId()); 

        StringBuffer idBuf=null;
        StringBuffer levelIdBuf=null;
        StringBuffer alarmvalueBuf=null;
        
        if (idBuf == null){
            idBuf=new StringBuffer();
        }
        if (levelIdBuf == null) {
            levelIdBuf=new StringBuffer();
        }
        if (alarmvalueBuf == null) {
            alarmvalueBuf=new StringBuffer();
        }
        
        tempfinalbeanC.setIdList(idBuf.toString());
        tempfinalbeanC.setLevelList(levelIdBuf.toString());
        tempfinalbeanC.setAlarmValueList(alarmvalueBuf.toString());
        
        tempfinalbeanC.setHighLowBaselineFlag(4);
        
        //返回模块下的业务设置的组合告警kpi的id(ipm_kpis)
        AlarmSetBean customAlarmSetBean=new AlarmSetBean();
        customAlarmSetBean.setModuleId(moduleId);
        customAlarmSetBean.setBusinessId(businessId);                    
        List<AlarmCustomkpigroupBean> alarmCustomkpigroupBeanList=alarmSetThreadDao.getAsetGroupByCustomUnionKpi(customAlarmSetBean);
        
        //迭代 获取这个kpi的id对应的alarmSetId
        if(alarmCustomkpigroupBeanList!=null && alarmCustomkpigroupBeanList.size()>0){
            AlarmSetBean ctuBean= null;
            for(int c=0; c<alarmCustomkpigroupBeanList.size(); c++){
                long kpiIdTemp= alarmCustomkpigroupBeanList.get(c).getKpiId();
                customAlarmSetBean.setKpiId(kpiIdTemp);
                List<AlarmSetBean> custumList= alarmSetThreadDao.getAsetGroupByCustomUnionAlarmSetId(customAlarmSetBean);
                if(custumList!=null && custumList.size()>0){
                    ctuBean= AlarmValueSetToMemoryUtil.setCustomUnionKpiAlarmInfoToThreadList(tempfinalbeanC, custumList);
                }
            }
            
            String key=AlarmValueSetToMemoryUtil.getAlarmThreadSetKey(ctuBean);
            AlarmSetService.alarmSetThreadMap.put(key, ctuBean);
        }
    }
    
    /**
     * @Title: setAlarmSetThreadAdd
     * @Description: 添加时调用,目前添加不用更新
     * @author chensq
     */
    public static void setAlarmSetThreadAdd(){
        
    }
    
    /**
     * @Title: setAlarmSetThreadDel
     * @Description: 删除业务时调用, moduleId_watchpointId_businessId_kpitype_kpiId_highLowBaselineFlag
     * @param alarmSetBean void
     * @author chensq
     */
    public static void setAlarmSetThreadDel(AlarmSetBean alarmSetBean){
        StringBuffer delKeyBuf=new StringBuffer();
        
        delKeyBuf.append(alarmSetBean.getModuleId());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getWatchpointId());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getBusinessId());
        delKeyBuf.append(",");
        
        delSetThreadInMap(delKeyBuf.toString());
    }
   
    /**
     * @Title: delSetThreadInMap
     * @Description: 根据条件清空告警设置的map指定内容,moduleId_watchpointId_businessId_kpitype_kpiId_highLowBaselineFlag
     * @param delKey 需要清空的key,并非完整的key，内容皆为清空的内容
     * @author chensq
     */
    public static void delSetThreadInMap(String delKey){
        if(AlarmSetService.alarmSetThreadMap!=null && AlarmSetService.alarmSetThreadMap.size()>0){
            Set<Entry<String, AlarmSetBean>> it = AlarmSetService.alarmSetThreadMap.entrySet(); 
            Iterator<Entry<String, AlarmSetBean>> its =it.iterator();
            while (its.hasNext()) {
                Entry<String, AlarmSetBean> entry = its.next();
                String itemKey = entry.getKey();    
                int cutLength=delKey.length();
                int itemLength=itemKey.length();
                if(itemLength>=cutLength){
                    String cutStr=itemKey.substring(0, cutLength);
                    if (cutStr.equals(delKey)) {
                        its.remove();
                    }
                }
            } 
        }
    }
    
    /**
     * @Title: setAlarmSetThreadModNativeKpi
     * @Description: 修改时调用(Native：单独修改-全局修改),原生kpi的高阈值 低阈值 智能告警
     * moduleId_watchpointId_businessId_kpitype_kpiId_highLowBaselineFlag
     * @param alarmSetBeanList
     * @param alarmSetThreadDao void
     * @author chensq
     */
    public static void setAlarmSetThreadModNativeKpi(List<AlarmSetBean> alarmSetBeanList, AlarmSetThreadDao alarmSetThreadDao){
        //在map中删除
        StringBuffer delKeyBuf=null;
        if(alarmSetBeanList!=null && alarmSetBeanList.size()>0){
            //高阈值
            for(int x=0; x<alarmSetBeanList.size(); x++){
                delKeyBuf=new StringBuffer();
                AlarmSetBean itemBean=alarmSetBeanList.get(x);
                delKeyBuf.append(itemBean.getModuleId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getWatchpointId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getBusinessId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpitype());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpiId());
                delKeyBuf.append(","); 
                delKeyBuf.append(0);

                delSetThreadInMap(delKeyBuf.toString());
            }
            //低阈值
            for(int x=0; x<alarmSetBeanList.size(); x++){
                delKeyBuf=new StringBuffer();
                AlarmSetBean itemBean=alarmSetBeanList.get(x);
                delKeyBuf.append(itemBean.getModuleId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getWatchpointId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getBusinessId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpitype());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpiId());
                delKeyBuf.append(","); 
                delKeyBuf.append(1);

                delSetThreadInMap(delKeyBuf.toString());
            }
            //智能告警
            for(int x=0; x<alarmSetBeanList.size(); x++){
                delKeyBuf=new StringBuffer();
                AlarmSetBean itemBean=alarmSetBeanList.get(x);
                delKeyBuf.append(itemBean.getModuleId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getWatchpointId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getBusinessId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpitype());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpiId());
                delKeyBuf.append(","); 
                delKeyBuf.append(2);

                delSetThreadInMap(delKeyBuf.toString());
            }
            //应用可用性
            for(int x=0; x<alarmSetBeanList.size(); x++){
                delKeyBuf=new StringBuffer();
                AlarmSetBean itemBean=alarmSetBeanList.get(x);
                delKeyBuf.append(itemBean.getModuleId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getWatchpointId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getBusinessId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpitype());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpiId());
                delKeyBuf.append(","); 
                delKeyBuf.append(3);

                delSetThreadInMap(delKeyBuf.toString());
            }
            //组合kpi
            for(int x=0; x<alarmSetBeanList.size(); x++){
                delKeyBuf=new StringBuffer();
                AlarmSetBean itemBean=alarmSetBeanList.get(x);
                delKeyBuf.append(itemBean.getModuleId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getWatchpointId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getBusinessId());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpitype());
                delKeyBuf.append(",");
                delKeyBuf.append(itemBean.getKpiId());
                delKeyBuf.append(","); 
                delKeyBuf.append(4);

                delSetThreadInMap(delKeyBuf.toString());
            }
            
        }
        
        //添加
        setAlarmSetThreadToDoNativeAdd(alarmSetBeanList, alarmSetThreadDao);
        
    }
    
    /**
     * @Title: setAlarmSetThreadGrp
     * @Description: 组合告警调用,moduleId_watchpointId_businessId_kpitype_kpiId_highLowBaselineFlag
     * @param alarmSetBean
     * @param alarmSetThreadDao void
     * @author chensq
     */
    public static void setAlarmSetThreadGrp(AlarmSetBean alarmSetBean, AlarmSetThreadDao alarmSetThreadDao){
        //删除
        StringBuffer delKeyBuf=new StringBuffer();
        
        delKeyBuf.append(alarmSetBean.getModuleId());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getWatchpointId());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getBusinessId());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getKpitype());
        delKeyBuf.append(",");
        delKeyBuf.append(alarmSetBean.getKpiId());
        delKeyBuf.append(",");

        delSetThreadInMap(delKeyBuf.toString());
        
        //添加
        setAlarmSetThreadToDoCustomAdd(alarmSetBean, alarmSetThreadDao);
    }
    
}
