/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreadInsertUtil
 *创建人:chensq    创建时间:2018年4月9日
 */
package com.protocolsoft.alarm.util;

import java.util.Map;

import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiThreadBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmThreadInsertBean;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.system.service.impl.SyslogService;

/**
 * @ClassName: AlarmThreadInsertUtil
 * @Description: 线程插入告警的方法
 * @author chensq
 *
 */
public class AlarmThreadInsertUtil {
   
    /**
     * @Title: doInsertAlarm
     * @Description: 线程执行告警插入
     * @param insertBeanClass
     * @return boolean
     * @author chensq
     */
    public static boolean doInsertAlarm(AlarmThreadInsertBean insertBeanClass){
        //get
        String key= insertBeanClass.getKey();
        AlarmCheckCountBean checkCountBean=insertBeanClass.getCheckCountBean();
        AlarmLogDao alarmLogDao =insertBeanClass.getAlarmLogDao();
        SyslogService syslogService=insertBeanClass.getSyslogService();
        
        //成功与否
        boolean flag=false;
        
        //通过key获取参数
        String []alarmParam=key.split(",");
        int moduleId=Integer.parseInt(alarmParam[0]);
        long watchpointId=Long.parseLong(alarmParam[1]);
        AlarmLogBean insertBean=setFullInsertBean(moduleId, watchpointId, checkCountBean);
             
        //执行添加
        if (alarmLogDao.addAlarmLog(insertBean)>0) {
            flag=true;
            //add syslog
            AlarmSyslogUtil alarmSyslogUtil=new AlarmSyslogUtil(syslogService, insertBean, alarmLogDao);
            alarmSyslogUtil.toSendSysLog();
        }
        
        return flag;
    }
   
    /**
     * @Title: isInsertCustomBean
     * @Description: 判断当前插入的告警是否有组合告警，是否具备产生组合告警log的条件，如果具备插入告警记录
     * @param insertBean void
     * @author chensq
     */
    public static void isInsertCustomBean(AlarmThreadInsertBean insertBean){
        //get
        String key= insertBean.getKey();
        AlarmCheckCountBean checkCountBean=insertBean.getCheckCountBean();
        AlarmLogDao alarmLogDao =insertBean.getAlarmLogDao();
        SyslogService syslogService=insertBean.getSyslogService();
        AlarmSetService alarmSetService=insertBean.getAlarmSetService();
        Map<String, AlarmCustomUnionKpiThreadBean> customMap=insertBean.getCustomMap();
        
        //通过key获取参数
        StringBuffer checkCustomKeyBuf=new StringBuffer();
        
        String []alarmParam=key.split(",");
        checkCustomKeyBuf.append(Integer.parseInt(alarmParam[0]));
        checkCustomKeyBuf.append(",");
        checkCustomKeyBuf.append(Long.parseLong(alarmParam[1]));
        checkCustomKeyBuf.append(",");
        checkCustomKeyBuf.append(Long.parseLong(alarmParam[2]));
        checkCustomKeyBuf.append(",");

        //该模块下观察点下的业务是否有组合告警的需求
        AlarmCustomUnionKpiThreadBean customBean= customMap.get(checkCustomKeyBuf.toString());
        if(customBean!=null){
            
            //预插入的普通告警的设置
            AlarmSetBean alarmSetBean=alarmSetService.getAlarmSetById(checkCountBean.getFinalAlarmSetId());
            long kpiId=alarmSetBean.getKpiId();
            long levelId=alarmSetBean.getLevelId();
            
            //验证当前kpi_id是否是在组合告警设置之中
            Map<String, Integer> cusKpiIdMap= customBean.getKpiIdCountMap();
            //当前在组合告警设置之中
            if(cusKpiIdMap.get(String.valueOf(kpiId))!=null){
                
                //组合告警的时间
                long cStarttime=customBean.getStarttime();
                long cEndtime=customBean.getEndtime();
                //预插入告警
                long starttime =checkCountBean.getStarttime();
                long endtime= checkCountBean.getEndtime();  
                
                if(cStarttime==0 && cEndtime==0){//初始
                    customBean.setStarttime(starttime);
                    customBean.setEndtime(endtime);
                }else{//后续囊括所有被组合告警的时间
                    if(cStarttime>starttime){
                        customBean.setStarttime(starttime);
                    }
                    if(cEndtime<endtime){
                        customBean.setEndtime(endtime);
                    }
                }
                
                int maxLevel=customBean.getMaxLevel();
                int beforeCount= customBean.getBeforeCount();
                //获取最大级别
                if(levelId>maxLevel){
                    customBean.setMaxLevel((int)levelId);
                }
                //设置每个kpiId的数量
                if(cusKpiIdMap.get(String.valueOf(kpiId))==0){
                    cusKpiIdMap.put(String.valueOf(kpiId), 1);
                }
                //判断当前已经存在的组合告警数量
                int totalCount=0;
                for (Map.Entry<String, Integer> entry : cusKpiIdMap.entrySet()) {
                    totalCount+=entry.getValue();
                }
                //具备告警条件
                if(totalCount == beforeCount){
                    //查询alarmSetId
                    AlarmSetBean tempSearchBean=new AlarmSetBean();
                    tempSearchBean.setBusinessId(customBean.getBusinessId());
                    tempSearchBean.setModuleId(customBean.getModuleId());
                    tempSearchBean.setLevelId(customBean.getMaxLevel());
                    long finalAlarmSetId =alarmSetService.getAlarmSetIdByParam(tempSearchBean);
                    
                     //组合告警插入
                    AlarmCheckCountBean customCheckCountBean=new AlarmCheckCountBean();
                    customCheckCountBean.setStarttime(customBean.getStarttime());
                    customCheckCountBean.setEndtime(customBean.getEndtime());
                    customCheckCountBean.setFinalAlarmSetId(finalAlarmSetId);
                    customCheckCountBean.setTriggerflag(4);

                    AlarmLogBean finalInsertBean=setFullInsertBean((int)customBean.getModuleId(), customBean.getWatchpointId(), customCheckCountBean);
                    
                    //执行添加
                    if (alarmLogDao.addAlarmLog(finalInsertBean)>0) {
                        //add syslog
                        AlarmSyslogUtil alarmSyslogUtil=new AlarmSyslogUtil(syslogService, finalInsertBean, alarmLogDao);
                        alarmSyslogUtil.toSendSysLog();
                    }
                }
                
            }
            
                        
        }
    } 
    
    /**
     * @Title: setFullInsertBean
     * @Description: 完善告警的其他字段
     * @param moduleId
     * @param watchpointId
     * @param checkCountBean
     * @return AlarmLogBean
     * @author chensq
     */
    public static AlarmLogBean setFullInsertBean(int moduleId, long watchpointId, AlarmCheckCountBean checkCountBean){
        AlarmLogBean insertBean=new AlarmLogBean();
        //判断观察点情况还是服务端客户端情况
        if(moduleId ==10) {//观察点
            insertBean.setWatchpointId(0);
        } else if (moduleId ==11) {
            insertBean.setWatchpointId(watchpointId);
        } else if (moduleId ==12) {
            insertBean.setWatchpointId(watchpointId);
        } else if (moduleId ==2){
            insertBean.setWatchpointId(0);
        }else {
            insertBean.setWatchpointId(watchpointId);
        }
        insertBean.setAlarmsetId(checkCountBean.getFinalAlarmSetId());
        insertBean.setStarttime(checkCountBean.getStarttime());
        insertBean.setEndtime(checkCountBean.getEndtime());
        insertBean.setHandledflag("N");
        insertBean.setFinishflag("Y");
        insertBean.setTriggerflag(checkCountBean.getTriggerflag());
        return insertBean;
    } 
}
