/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiController
 *创建人:chensq    创建时间:2018年4月4日
 */
package com.protocolsoft.alarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmCustomUnionKpiService;

/**
 * @ClassName: AlarmCustomUnionKpiController
 * @Description: 告警log控制器层
 * @author chensq
 *
 */
@Controller
@RequestMapping(value = "/customUnionKpi")
public class AlarmCustomUnionKpiController {
  
    /**
     * @Fields alarmCustomUnionKpiService : 组合kpi设置业务逻辑层对象
     */
    @Autowired
    private AlarmCustomUnionKpiService alarmCustomUnionKpiService;
    
    /**
     * @Title: getCustomUnionKpisList
     * @Description: 组合kpi list
     * @param alarmCustomkpiBean
     * @return List<AlarmCustomUnionKpiBean>
     * @author chensq
     */
    @RequestMapping(value="getAlarmCustomUnionKpiList.do")
    @ResponseBody 
    public List<AlarmCustomUnionKpiBean> getCustomUnionKpisList(AlarmCustomkpiBean alarmCustomkpiBean){
        return alarmCustomUnionKpiService.getCustomUnionKpisList(alarmCustomkpiBean);
    }
    
    
    /**
     * @Title: getCustomkpiInfo
     * @Description: 获取组合kpi设置info
     * @param alarmCustomkpiBean
     * @return AlarmCustomkpiBean
     * @author chensq
     */
    @RequestMapping(value="getCustomkpiInfo.do")
    @ResponseBody 
    public AlarmCustomkpiBean getCustomkpiInfo(AlarmCustomkpiBean alarmCustomkpiBean){
        return alarmCustomUnionKpiService.getCustomkpiInfo(alarmCustomkpiBean);
    }
    
    /**
     * @Title: addAlarmCustomUnionKpis
     * @Description: 组合kpi add
     * @param alarmCustomkpiBean
     * @return boolean
     * @author chensq
     */
    @RequestMapping(value="addAlarmCustomUnionKpis.do")
    @ResponseBody 
    public boolean addAlarmCustomUnionKpis(AlarmCustomkpiBean alarmCustomkpiBean){
        return alarmCustomUnionKpiService.addAlarmCustomUnionKpis(alarmCustomkpiBean);
    }
    
    /**
     * @Title: getAlarmLogsByUnionkpi
     * @Description: 查询聚合的告警log
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    @RequestMapping(value="getUnionKpiLog.do")
    @ResponseBody 
    public List<AlarmLogBean> getAlarmLogsByUnionkpi(AlarmLogBean alarmLogBean){
        return alarmCustomUnionKpiService.getAlarmLogsByUnionkpi(alarmLogBean);
    }
    
    /**
     * 
     * @Title: getRemoteUnionKpiLog
     * @Description: 组合KPI数据
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author www
     */
    @RequestMapping(value = "/getRemoteUnionKpiLog.do")
    @ResponseBody 
    public List<Object> getRemoteUnionKpiLog(AlarmLogBean alarmLogBean){
        
        return alarmCustomUnionKpiService.getRemoteUnionKpiLog(alarmLogBean);
    }
}
