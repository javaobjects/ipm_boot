/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogController
 *创建人:chensq    创建时间:2017年11月21日
 */
package com.protocolsoft.alarm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.protocolsoft.alarm.bean.AlarmColumnDataBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.log.annotation.Log;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;


/**
 * @ClassName: AlarmLogController
 * @Description: 告警log控制器层
 * @author chensq
 *
 */
@Controller
@RequestMapping(value = "/alarmLog")
public class AlarmLogController {
   
    /**
     * @Fields alarmLogService : 告警阈值设置业务逻辑层对象
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * @Fields userService : userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * @Title: getAlarmSetTitle
     * @Description: 告警logs数量
     * @param alarmLogBean
     * @return AlarmLogBean
     * @author chensq
     */
    @RequestMapping(value="getAlarmLogCount.do")
    @ResponseBody 
    public AlarmLogBean getAlarmSetTitle(AlarmLogBean alarmLogBean){
        Map<String, Long> map = alarmLogService.getAlarmLogCount(alarmLogBean);
        alarmLogBean.setStarttime(map.get("starttime"));
        alarmLogBean.setEndtime(map.get("endtime"));
        alarmLogBean.setCount(map.get("count"));
        alarmLogBean.setAlarmLevelId(map.get("alarmLevelId"));
        return alarmLogBean;
    }     
    
    /**
     * @Title: getAlarmLogData
     * @Description: 告警logs列表
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    @RequestMapping(value="getAlarmLogData.do")
    @ResponseBody 
    public List<AlarmLogBean> getAlarmLogData(AlarmLogBean alarmLogBean){
        List<AlarmLogBean> list = alarmLogService.getAlarmLogData(alarmLogBean);
        
        return list;
    }
        
    /**
     * @Title: getAlarmLogData4Win
     * @Description: 告警logs列表 win使用
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    @RequestMapping(value="getAlarmLogData4Win.do")
    @ResponseBody 
    public List<AlarmLogBean> getAlarmLogData4Win(AlarmLogBean alarmLogBean){
        List<AlarmLogBean> list = alarmLogService.getAlarmLogData4Win(alarmLogBean);
        
        return list;
    }
    
    /**
     * @Title: getRemoteAlarmLogData
     * @Description: 获取远程数据
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    @RequestMapping(value = "/getRemoteAlarmLogData.do")
    @ResponseBody 
    public List<AlarmLogBean> getRemoteAlarmLogData(AlarmLogBean alarmLogBean){
        List<AlarmLogBean> list = alarmLogService.getRemoteAlarmLog(alarmLogBean);
        
        return list;
    }
    
    /**
     * @Title: getRemoteAlarmLogData4Win
     * @Description: 获取远程数据
     * @param alarmLogBean
     * @return List<Object>
     * @author chensq
     */
    @RequestMapping(value = "/getRemoteAlarmLogData4Win.do")
    @ResponseBody 
    public List<Object> getRemoteAlarmLogData4Win(AlarmLogBean alarmLogBean){
        List<Object> list = alarmLogService.getRemoteAlarmLog4Win(alarmLogBean);
        
        return list;
    }
    
    /**
     * @Title: updateAlarmLogs
     * @Description: 更改告警信息
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    @Log(smallModuleId = 10, description = "告警响应")
    @RequestMapping(value="updateAlarmLogs.do")
    @ResponseBody 
    public boolean updateAlarmLogs(AlarmLogBean alarmLogBean){
        alarmLogBean.setHandledflag("Y");
        //获取request
        ServletRequestAttributes servletRequestAttributes  = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        int userId=0;
        if (systemUserBean!=null) {
            userId=systemUserBean.getId();
        }
        alarmLogBean.setResponseuser(userId);
        alarmLogBean.setResponsetime(DateUtils.getNowTimeSecond());
        boolean flag= alarmLogService.updateAlarmLogs(alarmLogBean);
        return flag;
    }
    
    /**
     * @Title: updateAlarmLog
     * @Description: 更改告警信息，单条
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    @Log(smallModuleId = 10, description = "告警响应")
    @RequestMapping(value="updateAlarmLog.do")
    @ResponseBody 
    public boolean updateAlarmLog(AlarmLogBean alarmLogBean){
        alarmLogBean.setHandledflag("Y");
        //获取request
        ServletRequestAttributes servletRequestAttributes  = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        int userId = 1;
        if (systemUserBean != null) {
            userId = systemUserBean.getId();
        }
        alarmLogBean.setResponseuser(userId);
        alarmLogBean.setResponsetime(DateUtils.getNowTimeSecond());
        boolean flag= alarmLogService.updateAlarmLog(alarmLogBean);
        return flag;
    }
     
    /**
     * @Title: deleteAlarmLog
     * @Description: 删除告警信息，单条
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    @Log(smallModuleId = 10, description = "告警删除")
    @RequestMapping(value="deleteAlarmLog.do")
    @ResponseBody 
    public boolean deleteAlarmLog(AlarmLogBean alarmLogBean){
        boolean flag= alarmLogService.deleteAlarmLog(alarmLogBean);
        return flag;
    }
    
    /**
     * @Title: getRemoteMonLogInfo
     * @Description: 告警logs柱图   (1.默认两小时 2.对时间进行了取整) 
     * @param alarmLogBean
     * @return Map<String,Object>
     * @author chensq
     */
    @RequestMapping(value = "/getRemoteMonLogInfo.do")
    @ResponseBody 
    public Map<String, Object> getRemoteMonLogInfo(AlarmLogBean alarmLogBean) {
        long start = 0;
        long end = 0;
        if (alarmLogBean.getStarttime() != 0 && alarmLogBean.getEndtime() !=0){
            start = alarmLogBean.getStarttime();
            end = alarmLogBean.getEndtime();
        } else {
            end = System.currentTimeMillis() / 1000 ;
            end = DateUtils.normalize(end, 60);
            start = end - 7200;
        }
        
        alarmLogBean.setStarttime(start);
        alarmLogBean.setEndtime(end);
        
        return alarmLogService.getMonLogInfo(alarmLogBean);
    }
    
    /**
     * @Title: getMonLogInfo
     * @Description: 告警logs柱图   (1.默认两小时 2.对时间进行了取整) 
     * @param alarmLogBean
     * @return Map<String,Object>
     * @author chensq
     */
    @RequestMapping(value = "/getMonLogInfo.do")
    @ResponseBody 
    public Map<String, Object> getMonLogInfo(AlarmLogBean alarmLogBean) {
        long start = 0;
        long end = 0;
        if (alarmLogBean.getStarttime() != 0 && alarmLogBean.getEndtime() !=0){
            start = alarmLogBean.getStarttime();
            end = alarmLogBean.getEndtime();
        } else {
            end = System.currentTimeMillis() / 1000 ;
            end = DateUtils.normalize(end, 60);
            start = end - 7200;
        }
        
        alarmLogBean.setStarttime(start);
        alarmLogBean.setEndtime(end);
        
        // 本机数据
        alarmLogBean.setIpmCenterId(1);
        
        return alarmLogService.getMonLogInfo(alarmLogBean);
    }
    
    /**
     * @Title: alarmNum
     * @Description: 获取告警数量
     * @param alarmLogBean
     * @return List<AlarmColumnDataBean>
     * @author chensq
     */
    @RequestMapping(value = "/alarmNum.do")
    @ResponseBody 
    public List<AlarmColumnDataBean> alarmNum(AlarmLogBean alarmLogBean) {
        if(alarmLogBean.getColumnNum()==null || alarmLogBean.getColumnNum()==0){
            alarmLogBean.setColumnNum(24);
        }
        List<String> dataList = DateAppsUtils.timeSection(alarmLogBean.getStarttime(), alarmLogBean.getEndtime(), alarmLogBean.getColumnNum());
        
        return alarmLogService.alarmNum(alarmLogBean, dataList);
    }
    
    /**
     * 
     * @Title: insertProbeSynchronousAlarmlog
     * @Description: 添加探针同步告警
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    @RequestMapping(value = "/insertProbeSynchronousAlarmlog.do")
    @ResponseBody 
    public boolean insertProbeSynchronousAlarmlog(AlarmLogBean alarmLogBean){
        alarmLogBean=alarmLogService.insertProbeSynchronousAlarmlog(alarmLogBean.getAlarmsetId(), 
                alarmLogBean.getStarttime(), 
                alarmLogBean.getEndtime());
        if(alarmLogBean.getId()<=0){
            return false;
        }else{
            return true;
        }
    }
    
}
