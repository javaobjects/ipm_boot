/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineRrdInitCreateUtil.java
 *创建人: chensq    创建时间: 2018年6月8日
 */
package com.protocolsoft.alarm.util;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.protocolsoft.alarm.enumeration.BaselineTriggerGrain;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.service.AlarmRrdService;

/**
 * @ClassName: AlarmBaseLineRrdInitCreateUtil
 * @Description: 初始化智能告警的rrd，并设置入数据
 * @author chensq
 *
 */
public class AlarmBaseLineRrdInitCreateUtil {
    
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    
    /**
     * @Fields busiId : 业务id
     */
    private long busiId;
    
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;   
    
    /**
     * @Fields type : 高低类型
     */
    private AlarmBaseType type;
    
    /**
     * @Fields isFinBase : 是否为基线   true:不是基线   false:基线
     */
    private boolean isFinBase;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param moduleId
     * @param busiId
     * @param watchpointId
     * @param type
     * @param isFinBase
     */
    public AlarmBaseLineRrdInitCreateUtil(long moduleId, long busiId, long watchpointId, AlarmBaseType type, boolean isFinBase){
        this.moduleId=moduleId;
        this.busiId=busiId;
        this.watchpointId=watchpointId;
        this.type=type;
        this.isFinBase=isFinBase;
    }
    
    /**
     * @Title: toCreateUpdate
     * @Description: 创建或者修改智能告警的rrd
     * @param currentThreadtime
     * @param numberList void
     * @author chensq
     */
    public void toCreateUpdate(long currentThreadtime, List<Number> numberList){
        //初始化rrd对象
        AlarmRrdService alarmRrdService=new AlarmRrdService(
                moduleId, 
                busiId, 
                watchpointId, 
                type, 
                isFinBase);
        
        //该业务对应模块下的kpis
        List<String> kpiColumns=AlarmSetBaseLineToMemoryUtil.getColumns(moduleId);

        //初始保存粒度
        int step=10;
        if(moduleId==8){
            step=BaselineTriggerGrain._60S.toInt();
        }else if(moduleId==9){
            step=BaselineTriggerGrain._60S.toInt();
        }else{
            step=BaselineTriggerGrain._10S.toInt();
        }
        
        //初始化
        alarmRrdService.create(step, 
                kpiColumns, 
                AlarmSetBaseLineToMemoryUtil.getRetainTimeList(moduleId));
                
        //修改
        alarmRrdService.update(currentThreadtime, numberList);
        
    }
        
    /**
     * 
     * @Title: getBaseLineSimpleEntryInfo
     * @Description: 获取某个时间的基线数据
     * @param startTime
     * @param endTime
     * @param kpiName
     * @return SimpleEntry<Long,Double>
     * @author chensq
     */
    public SimpleEntry<Long, Double> getBaseLineSimpleEntryInfo(long startTime, long endTime, String kpiName , int lidu){
        //初始化rrd对象
        AlarmRrdService alarmRrdService=new AlarmRrdService(moduleId, busiId, watchpointId, type, isFinBase);
        //临时变量
        SimpleEntry<Long, Double> point=alarmRrdService.getBaseLineRrd(//rrd取值
                startTime,
                endTime,                     
                kpiName,
                lidu);
        //返回
        return point;
    }
    
    
}
