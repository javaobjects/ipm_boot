/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetController
 *创建人:chensq    创建时间:2017年11月3日
 */
package com.protocolsoft.alarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmSetDataBean;
import com.protocolsoft.alarm.bean.AlarmSetTitleBean;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.common.bean.AppBusinessBean;

/**
 * @ClassName: AlarmSetController
 * @Description: 告警阈值设置控制器层
 * @author chensq
 *
 */
@Controller
@RequestMapping(value = "/alarmSet")
public class AlarmSetController {
   
    /**
     * @Fields alarmSetService : 告警阈值设置业务逻辑层对象
     */
    @Autowired
    private AlarmSetService alarmSetService;
        
    /**
     * @Title: getAlarmSetTitle
     * @Description: 告警阈值设置title
     * @return List<AlarmSetTitleBean>
     * @author chensq
     */
    @RequestMapping(value="getAlarmSetTitle.do")
    @ResponseBody 
    public List<AlarmSetTitleBean> getAlarmSetTitle(){
        List<AlarmSetTitleBean> alarmSetTitleBean = alarmSetService.getAlarmSetTitle();
        return alarmSetTitleBean;
    }
   
    /**
     * @Title: getAlarmSetData
     * @Description: 告警阈值设置Data
     * @param alarmSetBean
     * @return List<AlarmSetDataBean>
     * @author chensq
     */
    @RequestMapping(value="getAlarmSetData.do")
    @ResponseBody 
    public List<AlarmSetDataBean> getAlarmSetData(AlarmSetBean alarmSetBean){
        return alarmSetService.getAlarmSetData(
                alarmSetBean.getWatchpointId(),
                alarmSetBean.getModuleId(),
                alarmSetBean.getBusinessId(),
                alarmSetBean.getKpiId());
    }
     
    /**
     * @Title: updateAlarmSet
     * @Description: 修改告警阈值设置c
     * @param alarmSetBean
     * @return boolean
     * @author chensq
     */
    @RequestMapping(value="updateAlarmSet.do")
    @ResponseBody 
    public boolean updateAlarmSet(AlarmSetBean alarmSetBean){
        return alarmSetService.updateAlarmSet(alarmSetBean);
    }
    
    /**
     * 
     * @Title: addCenterBusinessAfter
     * @Description: 谈价探针对象
     * @param appBusinessBean
     * @return boolean
     * @author chensq
     */
    @RequestMapping(value="addCenterBusinessAfter.do")
    @ResponseBody 
    public boolean addCenterBusinessAfter(AppBusinessBean appBusinessBean){
        return alarmSetService.addCenterBusinessAfter(appBusinessBean.getName(), appBusinessBean.getDisplayIp());
    }
    
    /**
     * 
     * @Title: getProbeSynchronousAlarmSetIdByIpStr
     * @Description: 根据ip查询对应的alarmsetId
     * @param appBusinessBean
     * @return Long
     * @author chensq
     */
    @RequestMapping(value="getProbeSynchronousAlarmSetIdByIpStr.do")
    @ResponseBody 
    public Long getProbeSynchronousAlarmSetIdByIpStr(AppBusinessBean appBusinessBean){
        return alarmSetService.getProbeSynchronousAlarmSetIdByIpStr(appBusinessBean.getDisplayIp(), appBusinessBean.getProbeAlarmType());
    }
    
}
