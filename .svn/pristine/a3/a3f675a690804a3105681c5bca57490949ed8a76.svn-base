package com.protocolsoft.alarm.util;

import org.apache.commons.lang3.StringUtils;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmLogFindParamBean;

/**
 * @ClassName: AlarmLogFindParamBeanUtil
 * @Description: 告警查询参数工具类
 * @author chensq
 *
 */
public class AlarmLogFindParamBeanUtil {
    /**
     * 组合公共告警查询参数
     * @param alarmLogBean
     * @return AlarmLogFindParamBean
     */
	public static AlarmLogFindParamBean getAlarmLogKpiFindParamBean(AlarmLogBean alarmLogBean){
		//查询对象
        AlarmLogFindParamBean alarmLogFindParamBean =new AlarmLogFindParamBean();
        
        int wheresqlFlag=0;
        //watchpointsql & modulesql & businesssql
        if (alarmLogBean.getModuleId()==10) {//观察点
            alarmLogFindParamBean.setWatchpointsql(" and l.watchpoint_id=0 ");
            alarmLogFindParamBean.setBusinesssql(" and s.business_id="+alarmLogBean.getBusinessId()+" ");
            if (StringUtils.isNotEmpty(alarmLogBean.getWatchpointName())) {
                alarmLogFindParamBean.setWatchpointDisplayNamesql(" and w1.name like %"
                        +alarmLogBean.getWatchpointName()+"% ");
            }
            wheresqlFlag++;
        } else if (alarmLogBean.getModuleId()!=10 && alarmLogBean.getModuleId()!=0) {
            //服务端  客户端 以及 以后的http、oracle、mysql、sqlserver、url
            if (alarmLogBean.getWatchpointId()!=0) {
                alarmLogFindParamBean.setWatchpointsql(" and l.watchpoint_id="+alarmLogBean.getWatchpointId()+" ");
            }
            if(alarmLogBean.getBusinessId()!=0){
                alarmLogFindParamBean.setBusinesssql(" and s.business_id="+alarmLogBean.getBusinessId()+" ");
            }
            if (StringUtils.isNotEmpty(alarmLogBean.getWatchpointName())) {
                alarmLogFindParamBean.setWatchpointDisplayNamesql(" and w.name like %"
                        +alarmLogBean.getWatchpointName()+"% ");
            }
            if (StringUtils.isNotEmpty(alarmLogBean.getBusinessName())) {
                alarmLogFindParamBean.setBusinessNamesql(" and ab.name like %"+alarmLogBean.getBusinessName()+"% ");
            }
            wheresqlFlag++;
        }
        //handledflagsql
        if (StringUtils.isNotEmpty(alarmLogBean.getHandledflag())) {
            if ("N".equalsIgnoreCase(alarmLogBean.getHandledflag())) {
                alarmLogFindParamBean.setHandledflagsql(" and l.handledflag ='N' ");
            } else if ("Y".equalsIgnoreCase(alarmLogBean.getHandledflag())) {
                alarmLogFindParamBean.setHandledflagsql(" and l.handledflag ='Y' ");
            }
            wheresqlFlag++;
        }
        //alarmLevelsql
        if (StringUtils.isNotEmpty(alarmLogBean.getAlarmLevelIds())) {
            alarmLogFindParamBean.setAlarmLevelsql(" and s.level_id in ("+alarmLogBean.getAlarmLevelIds()+") ");
            wheresqlFlag++;
        }
        //starttimesql
        if (alarmLogBean.getStarttime()!=0) {
            alarmLogFindParamBean.setStarttimesql(" and l.starttime>="+alarmLogBean.getStarttime()+" ");
            wheresqlFlag++;
        }
        //endtimesql
        if (alarmLogBean.getEndtime()!=0) {
            alarmLogFindParamBean.setEndtimesql(" and l.starttime<"+alarmLogBean.getEndtime()+" ");
            wheresqlFlag++;
        }
        //kpisql
        if (alarmLogBean.getKpisId()!=0) {
            alarmLogFindParamBean.setKpisql(" and "
                    + " ("
                    + " k.id ="+alarmLogBean.getKpisId()+" "
                    + " or "
                    + " ac.id ="+alarmLogBean.getKpisId()
                    +") ");
            wheresqlFlag++;
        }
        //kpiDisplayNamesql
        if (StringUtils.isNotEmpty(alarmLogBean.getKpisDisplayName())) {
            alarmLogFindParamBean.setKpiDisplayNamesql(" and "
                    + " ("
                    + " k.display_name like %"+alarmLogBean.getKpisDisplayName()+"% "
                    + " or "
                    + " ac.namezh like %"+alarmLogBean.getKpisDisplayName()+"% "
                    +") ");
            wheresqlFlag++;
        }
        //modulesql   
        if (alarmLogBean.getModuleId()>0) {
            alarmLogFindParamBean.setModulesql(" and "
                    + " ("
                    +"ak.module_id="+alarmLogBean.getModuleId()+" "
                    + " or "
                    +"ac.module_id ="+alarmLogBean.getModuleId()+" "
                    +") ");            
            wheresqlFlag++;
        }
        
        //设置where
        if (wheresqlFlag==0) {
            alarmLogFindParamBean.setWheresql(" ");
        } else {
            alarmLogFindParamBean.setWheresql("where 1=1 ");
        }
        
        return alarmLogFindParamBean;
	}
}
