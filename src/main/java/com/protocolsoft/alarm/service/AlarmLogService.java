/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogService
 *创建人:chensq    创建时间:2017年11月20日
 */
package com.protocolsoft.alarm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.alarm.bean.AlarmColumnBean;
import com.protocolsoft.alarm.bean.AlarmColumnDataBean;
import com.protocolsoft.alarm.bean.AlarmColumnDetailBean;
import com.protocolsoft.alarm.bean.AlarmColumnTimeLineBean;
import com.protocolsoft.alarm.bean.AlarmKpiBean;
import com.protocolsoft.alarm.bean.AlarmLevelBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmLogFindParamBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLevelDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.dao.AlarmSetDao;
import com.protocolsoft.alarm.util.AlarmLogUtil;
import com.protocolsoft.common.bean.AlarmHeatmapBean;
import com.protocolsoft.common.bean.CenterIpBean;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.user.bean.AuthorizeModuleBean;
import com.protocolsoft.user.dao.AuthorizeModuleDao;
import com.protocolsoft.utils.ArrayCutUtils;
import com.protocolsoft.utils.BeanUtils;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * @ClassName： AlarmLogService
 * @Description: 告警log service
 * @author chensq
 * 
 */
@Service
public class AlarmLogService {
   
    /**
     * @Fields alarmLevelDao : 告警级别  DAO
     */
    @Autowired
    private AlarmLevelDao alarmLevelDao;
    
    /**
     * @Fields alarmKpiDao : 告警kpi  DAO
     */
    @Autowired
    private AlarmKpiDao alarmKpiDao;
   
    /**
     * @Fields alarmLogDao : 告警日志表  DAO
     */
    @Autowired
    private AlarmLogDao alarmLogDao;
  
    /**
     * @Fields alarmSetDao : 告警阈值设置  DAO
     */
    @Autowired
    private AlarmSetDao alarmSetDao;
   
    /**
     * @Fields authorizeModuleDao : 注入 AuthorizeModuleDao
     */
    @Autowired
    AuthorizeModuleDao authorizeModuleDao;
 
    /**
     * @Fields centerService : Center
     */
    @Autowired
    private CenterIpService centerService;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService wpService;
    
    /**
     * @Title: getAlarmLogCount
     * @Description: 根据条件查询告警总数，开始结束时间map
     * @param alarmLogBean
     * @return map
     * @exception 
     * @author chensq
     * 使用方法：
     * AlarmLogBean alarmLogBean =new AlarmLogBean();
     * alarmLogBean.setWatchpointId();  观察点id  (必须)
     * alarmLogBean.setModuleId();      模块id   (必须)
     * alarmLogBean.setBusinessId();    业务id   (必须)
     * alarmLogBean.setStarttime();     开始时间       (可为空)
     * alarmLogBean.setEndtime();       结束时间       (可为空)
     * alarmLogBean.setHandledflag();   'N' 或  'Y' 或     空
     * alarmLogBean.setAlarmLevelIds();  n1,n2,n3 或     空 
     * alarmLogBean.kpisId              kpi的id    或     空
     * alarmLogBean.kpitype             kpitype   或    空
     */
    public Map<String, Long> getAlarmLogCount(AlarmLogBean alarmLogBean) {
        //返回类型
        Map<String, Long> map=new HashMap<String, Long>();
        
        //watchpointId
        if (alarmLogBean.getModuleId()==10){//观察点
            alarmLogBean.setWatchpointId(0);
        }
        //handledflagSql
        StringBuffer handledflagSql =new StringBuffer();
        if (StringUtils.isNotEmpty(alarmLogBean.getHandledflag())) {
            if ("N".equalsIgnoreCase(alarmLogBean.getHandledflag())) {
                handledflagSql.append(" and l.handledflag ='N' ");
            } else if ("Y".equalsIgnoreCase(alarmLogBean.getHandledflag())) {
                handledflagSql.append(" and l.handledflag ='Y' ");
            }
        }
        //levelIdsSql
        StringBuffer levelIdsSql =new StringBuffer();
        if (StringUtils.isNotEmpty(alarmLogBean.getAlarmLevelIds())) {
            levelIdsSql.append(" and s.level_id in ("+alarmLogBean.getAlarmLevelIds()+") ");
        }
        //kpitypeSql
        StringBuffer kpitypeSql =new StringBuffer();
        if (alarmLogBean.getKpitype()!=0) {
            kpitypeSql.append(" and s.kpitype ="+alarmLogBean.getKpitype()+" ");
        }
        //kpis
        StringBuffer kpiIdSql=new StringBuffer();
        if (alarmLogBean.getKpisId()!=0) {
            kpiIdSql.append(" and s.kpi_id ="+alarmLogBean.getKpisId()+" ");
        }
        //kId
        StringBuffer kIdSql=new StringBuffer();
        if (alarmLogBean.getkId()!=0) {
            kIdSql.append(" and k.id ="+alarmLogBean.getkId()+" ");
        }
        //开始时间
        StringBuffer starttimeSql =new StringBuffer();
        if (alarmLogBean.getStarttime()!=0) {
            starttimeSql.append(" and l.starttime >="+alarmLogBean.getStarttime()+" ");
        }
        //结束时间
        StringBuffer endtimeSql =new StringBuffer();
        if (alarmLogBean.getEndtime()!=0) {
            endtimeSql.append(" and l.starttime <"+alarmLogBean.getEndtime()+" ");
        }        
        
        AlarmLogFindParamBean alarmLogFindParamBean =new AlarmLogFindParamBean();
        alarmLogFindParamBean.setWatchpointsql(String.valueOf(alarmLogBean.getWatchpointId()));
        alarmLogFindParamBean.setBusinesssql(String.valueOf(alarmLogBean.getBusinessId()));
        alarmLogFindParamBean.setModulesql(String.valueOf(alarmLogBean.getModuleId()));
        alarmLogFindParamBean.setHandledflagsql(handledflagSql.toString());
        alarmLogFindParamBean.setAlarmLevelsql(levelIdsSql.toString());
        alarmLogFindParamBean.setKpitypesql(kpitypeSql.toString());
        alarmLogFindParamBean.setKpisql(kpiIdSql.toString());
        alarmLogFindParamBean.setStarttimesql(starttimeSql.toString());
        alarmLogFindParamBean.setEndtimesql(endtimeSql.toString());
        alarmLogFindParamBean.setkIdSql(kIdSql.toString());

        //获取返回时间
        if (alarmLogBean.getStarttime()==0 || alarmLogBean.getEndtime()==0) {
            AlarmLogBean alarmLogBeantimeBean=alarmLogDao.getAlarmLogMinMaxTimeByParam(alarmLogFindParamBean);
            if (alarmLogBeantimeBean==null) {
                map.put("starttime", 0L);
                map.put("endtime", 0L);
            } else {
                if(alarmLogBeantimeBean.getStarttime()==alarmLogBeantimeBean.getEndtime()){// 应对一条数据的情况
                    map.put("starttime", alarmLogBeantimeBean.getStarttime()-1);
                    map.put("endtime", alarmLogBeantimeBean.getEndtime());
                }else{
                    map.put("starttime", alarmLogBeantimeBean.getStarttime());
                    map.put("endtime", alarmLogBeantimeBean.getEndtime());
                }
            }
            
        } else {//回溯时间情况
            map.put("starttime", alarmLogBean.getStarttime());
            map.put("endtime", alarmLogBean.getEndtime());
        }
                
        AlarmLogBean alarmLogBeanReturn=alarmLogDao.getAlarmLogCountByParam(alarmLogFindParamBean);
        
        map.put("count", alarmLogBeanReturn.getCount());
        map.put("alarmLevelId", alarmLogBeanReturn.getAlarmLevelId());

        //count
        return map;
    }
    
    /**
     * @Title: getAlarmLogData
     * @Description: 根据条件查询告警列表
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @exception 
     * @author chensq
     * 使用方法：
     * AlarmLogBean alarmLogBean =new AlarmLogBean();
     * alarmLogBean.watchpointId();  观察点id   可为空   观察点情况为0
     * alarmLogBean.moduleId();      模块id    可为空
     * alarmLogBean.businessId();    业务id    可为空
     * alarmLogBean.starttime();     开始时间          可为空
     * alarmLogBean.endtime();       结束时间          可为空
     * alarmLogBean.handledflag();   响应状态         'N' 或  'Y' 或   空
     * alarmLogBean.alarmLevelIds(); 告警级别           n1,n2,n3 或   空
     * alarmLogBean.kpisId              kpi      kpi的id 或  空  或  自定义kpi的id
     * alarmLogBean.kpisDisplayName     kpis显示名    kpi的DisplayName 或  空  或  自定义kpi的显示名
//     * alarmLogBean.alarmLevelZh        级别显示名    level的DisplayName 或  空  
//     * alarmLogBean.alarmTypeId         告警类型id    type的id 或  空  
//     * alarmLogBean.alarmTypeZh         告警类型显示名      type的DisplayName 或  空  
//     * alarmLogBean.responseuser        响应人id      responseuser的id 或  空  
//     * alarmLogBean.responseuserDisplayName         响应人显示名      responseuser的DisplayName 或  空  
//     * alarmLogBean.moduleName          模块显示名         module的displayName 或  空 
     * alarmLogBean.watchpointName      观察点显示名     watchpoint的displayName 或  空 
     * alarmLogBean.businessName        业务显示名         业务的displayName 或  空 
     * alarmLogBean.sortStr             列表排序标识    空  或 asc 或 desc
     * 特殊说明：
     * ------------------------------------------
     * id查找:
     * 查询观察点则需要moduleId 与 businessId 参数
     * 查询应用则    需要moduleId 与  watchpointId 以及   businessId 参数
     * 模糊查询：
     * 观察点需要moduleId 与 watchpointName 参数
     * 应用    需要moduleId 与 watchpointName 以及  businessName 参数
     * ------------------------------------------
     */
    public List<AlarmLogBean> getAlarmLogData(AlarmLogBean alarmLogBean){
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
        
        //排序情况 默认为开始时间正序
        if (StringUtils.isEmpty(alarmLogBean.getSortStr())) {
            alarmLogFindParamBean.setSortStrsql(" ORDER BY l.starttime desc ");
        } else {
            alarmLogFindParamBean.setSortStrsql(" ORDER BY l.starttime "+alarmLogBean.getSortStr()+" ");
        }
        //limit
        if (alarmLogBean.getLimitNum()!=0) {
            alarmLogFindParamBean.setLimitsql(" limit "+alarmLogBean.getLimitNum());
        }
        
        List<AlarmLogBean> list = alarmLogDao.getAlarmLogDataByParam(alarmLogFindParamBean);
        AlarmLogBean bean = null;
        
        //获取模块名称 
        List<AuthorizeModuleBean> moduleList=authorizeModuleDao.selectAllAuthorizeModule();
        Map<String, String> map=AlarmLogUtil.getAllModuleMap(moduleList);
        
        for (int i = 0, len = list.size(); i < len; i ++) {
            bean = list.get(i);
            bean.setIpmCenterId(1);
            bean.setIpmCenterName("本机");
            bean.setModuleName(map.get(String.valueOf(bean.getModuleId())));
        }
        
        return packageAlarmLogs(list);
    }
        
    /**
     * @Title: getAlarmLogData4Win
     * @Description: 告警小列表提速，先取出条数，在关联其他表
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    public List<AlarmLogBean> getAlarmLogData4Win(AlarmLogBean alarmLogBean){
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
        
        //排序情况 默认为开始时间正序
        if (StringUtils.isEmpty(alarmLogBean.getSortStr())) {
            alarmLogFindParamBean.setSortStrsql(" ORDER BY l.starttime desc ");
        } else {
            alarmLogFindParamBean.setSortStrsql(" ORDER BY l.starttime "+alarmLogBean.getSortStr()+" ");
        }
        //limit
        if (alarmLogBean.getLimitNum()!=0) {
            alarmLogFindParamBean.setLimitsql(" limit "+alarmLogBean.getLimitNum());
        }
        
        List<AlarmLogBean> list = alarmLogDao.getAlarmLogDataByParam4Win(alarmLogFindParamBean);
        AlarmLogBean bean = null;
        
        //获取模块名称 
        List<AuthorizeModuleBean> moduleList=authorizeModuleDao.selectAllAuthorizeModule();
        Map<String, String> map=AlarmLogUtil.getAllModuleMap(moduleList);
        
        for (int i = 0, len = list.size(); i < len; i ++) {
            bean = list.get(i);
            bean.setIpmCenterId(1);
            bean.setIpmCenterName("本机");
            bean.setModuleName(map.get(String.valueOf(bean.getModuleId())));
        }
        
        return packageAlarmLogs(list);
    }
    
    /**
     * @Title: getAlarmLogColumnCount
     * @Description: 根据条件查询告警柱图对象
     * @param dataList
     * @param alarmLogBean
     * @return Map<String, Map<String, String>>
     * @exception 
     * @author chensq
     * 使用方法：
     * AlarmLogBean alarmLogBean =new AlarmLogBean();
     * alarmLogBean.watchpointId();  观察点id   可为空   观察点情况为0
     * alarmLogBean.moduleId();      模块id    可为空
     * alarmLogBean.businessId();    业务id    可为空
     * alarmLogBean.starttime();     开始时间          可为空
     * alarmLogBean.endtime();       结束时间          可为空
     * alarmLogBean.handledflag();   响应状态         'N' 或  'Y' 或   空
     * alarmLogBean.alarmLevelIds(); 告警级别           n1,n2,n3 或   空
     * alarmLogBean.kpisId              kpi      kpi的id 或  空  或  自定义kpi的id
     * alarmLogBean.kpisDisplayName     kpis显示名    kpi的DisplayName 或  空  或  自定义kpi的显示名
//     * alarmLogBean.alarmLevelZh        级别显示名    level的DisplayName 或  空  
//     * alarmLogBean.alarmTypeId         告警类型id    type的id 或  空  
//     * alarmLogBean.alarmTypeZh         告警类型显示名      type的DisplayName 或  空  
//     * alarmLogBean.responseuser        响应人id      responseuser的id 或  空  
//     * alarmLogBean.responseuserDisplayName         响应人显示名      responseuser的DisplayName 或  空  
//     * alarmLogBean.moduleName          模块显示名         module的displayName 或  空 
     * alarmLogBean.watchpointName      观察点显示名     watchpoint的displayName 或  空 
     * alarmLogBean.businessName        业务显示名         业务的displayName 或  空 
     * 特殊说明：
     * ------------------------------------------
     * id查找:
     * 查询观察点则需要moduleId 与 businessId 参数
     * 查询应用则    需要moduleId 与  watchpointId 以及   businessId 参数
     * 模糊查询：
     * 观察点需要moduleId 与 watchpointName 参数
     * 应用    需要moduleId 与 watchpointName 以及  businessName 参数
     * ------------------------------------------
     */
    public Map<String, Map<String, String>> getAlarmLogColumnCount(List<String> dataList, AlarmLogBean alarmLogBean){
        //返回告警列表
        List<AlarmLogBean> ret = null;

        //-----------------------------数量start-----------------------------------
        //时间map
        Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
        //拼接sql条件
        StringBuffer  paramsql1=new StringBuffer();
        StringBuffer  paramsql2=new StringBuffer();
        StringBuffer  paramsql3=new StringBuffer();
        //temp
        String stime=null;
        String etime=null;
        //切分时间
        for (int i = 0; i < dataList.size(); i++) {
            stime = dataList.get(i).split("-")[0];
            etime = dataList.get(i).split("-")[1];

            //拼接paramsql1
            if (i!=dataList.size()-1) {
                paramsql1.append("l.starttime >= cast('");
                paramsql1.append(stime);
                paramsql1.append("' as signed) and l.starttime < cast('");
                paramsql1.append(etime);
                paramsql1.append("' as signed) as tempColumn"+i+",");
            } else {
                paramsql1.append("l.starttime >= cast('");
                paramsql1.append(stime);
                paramsql1.append("' as signed) and l.starttime <= cast('");
                paramsql1.append(etime);
                paramsql1.append("' as signed) as tempColumn"+i+",");
            }
         

            //拼接paramsql2
            if (i!=dataList.size()-1) {
                paramsql2.append("l.starttime >= cast('");
                paramsql2.append(stime);
                paramsql2.append("' as signed) and l.starttime < cast('");
                paramsql2.append(etime);
                paramsql2.append("' as signed) ");
                if (i!=dataList.size()-1){
                    paramsql2.append(" or ");
                }
            } else {
                paramsql2.append("l.starttime >= cast('");
                paramsql2.append(stime);
                paramsql2.append("' as signed) and l.starttime <= cast('");
                paramsql2.append(etime);
                paramsql2.append("' as signed) ");
                if (i!=dataList.size()-1){
                    paramsql2.append(" or ");
                }
            }
            
            //拼接paramsql3
            paramsql3.append("tempColumn"+i);
            if (i!=dataList.size()-1){
                paramsql3.append(",");
            }
        }
        //-----------------------------数量end-----------------------------------

        //-----------------------------查询start-----------------------------
        //查询对象
        AlarmLogFindParamBean alarmLogFindParamBean =new AlarmLogFindParamBean();
        
        int wheresqlFlag=0;
        //watchpointsql & modulesql & businesssql
        if (alarmLogBean.getModuleId()==10) {//观察点
            alarmLogFindParamBean.setWatchpointsql(" and l.watchpoint_id=0 ");
            if(alarmLogBean.getBusinessId()!=0){
                alarmLogFindParamBean.setBusinesssql(" and s.business_id="+alarmLogBean.getBusinessId()+" ");
            }
            if (StringUtils.isNotEmpty(alarmLogBean.getWatchpointName())) {
                alarmLogFindParamBean.setWatchpointDisplayNamesql(" and w1.name like %"
                        +alarmLogBean.getWatchpointName()+"% ");
            }
            wheresqlFlag++;
        } else if (alarmLogBean.getModuleId()!=10 && alarmLogBean.getModuleId()!=0) {
          //服务端  客户端 以及 以后的http、oracle、mysql、sqlserver
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
            alarmLogFindParamBean.setEndtimesql(" and l.starttime<="+alarmLogBean.getEndtime()+" ");
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
        //-----------------------------查询end-----------------------------
        alarmLogFindParamBean.setParamsql1(paramsql1.toString());
        alarmLogFindParamBean.setParamsql2(paramsql2.toString());
        alarmLogFindParamBean.setParamsql3(paramsql3.toString());
        
        //告警级别
        List<AlarmLevelBean> alarmLevelBeanList= alarmLevelDao.getAlarmLevelList();
        
        //请求
        ret=alarmLogDao.getAlarmLogColumnByParam(alarmLogFindParamBean);
        
        if (ret!=null && ret.size()>0) {
            for (int x=0; x<ret.size(); x++) {
                AlarmLogBean tempBean= ret.get(x);
                //确定结果集属于哪个区间 并对区间内的多个数据进行合并
                map=AlarmLogUtil.getStartAndEndTime(
                        alarmLevelBeanList,
                        dataList,
                        tempBean.getStarttime(),
                        map, 
                        tempBean);
            }

            //时间段没有查询出数据的情况
            map=AlarmLogUtil.setDefaultMap(alarmLevelBeanList, dataList, map);

            //对map进行排序 start时间正序
            if (map.size() != 0) {
                List<Long> sortMapStartlist=new ArrayList<Long>();
                List<Long> sortMapEndlist=new ArrayList<Long>();
                long startKey=0;
                long endKey=0;
                for (String date : map.keySet()) {
                    startKey=Long.parseLong(date.split("-")[0]);
                    endKey=Long.parseLong(date.split("-")[1]);
                    sortMapStartlist.add(startKey);
                    sortMapEndlist.add(endKey);
                }

                Collections.sort(sortMapStartlist);
                Collections.sort(sortMapEndlist);

                //定义新的map进行排序后的返回
                Map<String, Map<String, String>> sortMap= new LinkedHashMap<String, Map<String, String>>();
                for (int x=0; x< sortMapStartlist.size(); x++) {
                    String temp1=String.valueOf(sortMapStartlist.get(x));
                    String temp2=String.valueOf(sortMapEndlist.get(x));
                    String temp=temp1+"-"+temp2;
                    Map<String, String> tempMap=map.get(temp);
                    sortMap.put(temp, tempMap);
                }
                return sortMap;
            }

        }
        
        return map;
    }
    
    /**
    *
    * @Title: getMonLogInfo
    * @Description: 告警柱图数据
    * @param alarmLogBean
    * @return Map<String,Object> 返回类型
    * @author chensq
    */
    public Map<String, Object> getMonLogInfo(AlarmLogBean alarmLogBean) {
        //返回
        Map<String, Object> data = new HashMap<String, Object>();
        //返回规定内容
        data.put("type", "stack_column");
        data.put("unit", "个");
        data.put("starttime", alarmLogBean.getStarttime());
        data.put("endtime", alarmLogBean.getEndtime());
        data.put("plotName", "告警");
        //时间切分
        List<String> dataList = DateAppsUtils.timeSection(alarmLogBean.getStarttime(), alarmLogBean.getEndtime(), 
                alarmLogBean.getColumnNum()==null||alarmLogBean.getColumnNum()==0?24:alarmLogBean.getColumnNum());
        //整体对象
        AlarmColumnBean alarmColumnBean=new AlarmColumnBean();
        List<AlarmColumnDataBean> alarmColumnDataBeanList = this.getRemoteAlarmNum(alarmLogBean, dataList);
        List<AlarmColumnTimeLineBean> alarmColumnTimeLineBeanList = new ArrayList<AlarmColumnTimeLineBean>();
        AlarmColumnTimeLineBean alarmColumnTimeLineBean = null; // 时间对象
        //设置时间对象
        for (int i = 0; i < dataList.size(); i++) {
            alarmColumnTimeLineBean = new AlarmColumnTimeLineBean();
            String[] timeArray = dataList.get(i).split("-");
            alarmColumnTimeLineBean.setColumnstime(Long.parseLong(timeArray[0]));
            alarmColumnTimeLineBean.setColumnetime(Long.parseLong(timeArray[1]));
            alarmColumnTimeLineBean.setLongTime(Long.parseLong(timeArray[1]));
            // 设置入对象
            alarmColumnTimeLineBeanList.add(alarmColumnTimeLineBean);
        }

        //时间集合设置入data集合
        alarmColumnBean.setAlarmColumnTimeLineBeanList(alarmColumnTimeLineBeanList);
        //时间集合设置入data集合
        alarmColumnBean.setAlarmColumnDataBeanList(alarmColumnDataBeanList);
        //最终存放
        data.put("data", alarmColumnBean);

        return data;
    }
       
    /**
     * @Title: getMonLogHealthInfo
     * @Description: 健康图使用的servic方法,告警柱图数据
     * @param alarmLogBean
     * @return List<AlarmHeatmapBean>
     * @author chensq
     */
    public List<AlarmHeatmapBean> getMonLogHealthInfo(AlarmLogBean alarmLogBean) {
        
        //返回存放的list
        List<AlarmHeatmapBean> alarmHeatmapList=new ArrayList<AlarmHeatmapBean>();
        
        //时间切分
        List<String> dataList = DateAppsUtils.timeSection(
                alarmLogBean.getStarttime(), 
                alarmLogBean.getEndtime(), 
                alarmLogBean.getHealthColumn()==0?6:alarmLogBean.getHealthColumn());
      
        //查询内容
        Map<String, Map<String, String>> map = this.getAlarmLogColumnCount(dataList, alarmLogBean);
        
        //list里存放的对象
        AlarmHeatmapBean tempItem=null;
        
        //map为空的情况
        if (map.size() == 0) {            
            
            //迭代时间分割
            for (int i = 0; i < dataList.size(); i++) {
                tempItem =new AlarmHeatmapBean();

                String []timeArray=dataList.get(i).split("-");

                tempItem.setStarttime(Long.parseLong(timeArray[0]));
                tempItem.setEndtime(Long.parseLong(timeArray[1]));
                tempItem.setNum(0);
                tempItem.setLevel(2);
                
                alarmHeatmapList.add(tempItem);
            }
            
        } else {//有内容的情况
            //处理返回结果
            Map<String, String> newMap= AlarmLogUtil.getAlarmLogColumnHealthMap(map);
            
            //组合结果
            for (Map.Entry<String, String> levelValueEntry : newMap.entrySet()) {
                //返回
                String levelKey= levelValueEntry.getKey();
                String levelValues= levelValueEntry.getValue();

                String []timeArray=levelKey.split("-");
                String []valuesArray=levelValues.split("-");
                
                tempItem =new AlarmHeatmapBean();

                tempItem.setStarttime(Long.parseLong(timeArray[0]));
                tempItem.setEndtime(Long.parseLong(timeArray[1]));
                tempItem.setLevel(Integer.parseInt(valuesArray[0]));
                tempItem.setNum(Integer.parseInt(valuesArray[1]));
                
                alarmHeatmapList.add(tempItem);
                                      
            }
        }

        return alarmHeatmapList;
    }
    
    /**
     * 
     * @Title: alarmNum
     * @Description: 获取告警柱图数据
     * @param alarmLogBean 参数
     * @param dataList 时间分割
     * @return List<AlarmColumnDataBean>
     * @author www
     */
    public List<AlarmColumnDataBean> alarmNum(AlarmLogBean alarmLogBean, List<String> dataList) {
        //数据
        List<AlarmColumnDataBean> alarmColumnDataBeanList = new ArrayList<AlarmColumnDataBean>();
        //level list
        List<AlarmLevelBean> alarmLevelBeanList=alarmLevelDao.getAlarmLevelList();
        //存放单个数据集合
        List<AlarmColumnDetailBean> dataDetailList = null;
        //返回的map
        Map<String, Map<String, String>> map = null;
        // 对象内容
        AlarmColumnDataBean alarmColumnDataBean = null; // 数据对象
        AlarmColumnDetailBean alarmColumnDetailBean = null; // 详细

        //查询内容
        map = this.getAlarmLogColumnCount(dataList, alarmLogBean);
        // map是否为空
        boolean isNull = (map.size() == 0);
        // 级别id
        long alarmLevelId = 0;
        // 告警数
        String alarmVal = null;
        // 告警级别
        String alarmLevelIdStr = null;
        //迭代告警级别
        for (int l = 0; l < alarmLevelBeanList.size(); l++) {
            //存放一组级别的数据
            alarmColumnDataBean = new AlarmColumnDataBean();
            //级别id
            alarmLevelId = alarmLevelBeanList.get(l).getId();
            if (!AlarmLogUtil.outLevel(alarmLevelId)) {
                continue;
            }
            //存放单个数据集合
            dataDetailList = new ArrayList<AlarmColumnDetailBean>();
            // 迭代时间分割
            if (isNull) {
                for (int i = 0; i < dataList.size(); i++) {
                    // 设置结果的数据
                    alarmColumnDetailBean = new AlarmColumnDetailBean();
                    alarmColumnDetailBean.setValue(0);
                    dataDetailList.add(alarmColumnDetailBean);
                }
            } else {
                for (String date : map.keySet()) {
                    // 设置结果的数据
                    alarmLevelIdStr = String.valueOf(alarmLevelId);
                    alarmVal = map.get(date).get(alarmLevelIdStr);
                    String levelCount = alarmVal == null ? "0" : alarmVal;
                    alarmColumnDetailBean = new AlarmColumnDetailBean();
                    alarmColumnDetailBean.setValue(Integer.parseInt(levelCount));
                    dataDetailList.add(alarmColumnDetailBean);
                }
            }
            //存放入data对象
            alarmColumnDataBean.setAlarmLevelId(alarmLevelId);
            alarmColumnDataBean.setAlarmLevelName(alarmLevelBeanList.get(l).getNamezh());                
            alarmColumnDataBean.setAlarmColumnDetailBeanList(dataDetailList);
            alarmColumnDataBeanList.add(alarmColumnDataBean);
        }
        
        return alarmColumnDataBeanList;
    }
     
    /**
     * @Title: updateAlarmLogs
     * @Description: 根据ids修改告警(告警响应)
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    public boolean updateAlarmLogs(AlarmLogBean alarmLogBean){
        
        //insql String list
        List<String> insqlStrList=new ArrayList<String>();
        //防止in长度超过限制
        String idsStr=alarmLogBean.getIds();
        String []idsArray=idsStr.split(",");
        //对数组进行切分
        Object[] subAry =ArrayCutUtils.splitAry(idsArray, 5);
        //array to list
        for (Object obj: subAry) {//打印输出结果  
            String[] aryItem = (String[]) obj;  
            StringBuffer buf=new StringBuffer();
            buf.append(" in (");
            for (int i = 0; i < aryItem.length; i++) {
                buf.append(aryItem[i]);
                if (i!=aryItem.length-1) {
                    buf.append(",");
                }
            }  
            buf.append(") ");
            insqlStrList.add(buf.toString());
        }
        //拼接完整in sql
        StringBuffer fullBuf=new StringBuffer();
        if (insqlStrList.size()>0) {
            for (int x=0; x<insqlStrList.size(); x++) {
                fullBuf.append(" id ");
                fullBuf.append(insqlStrList.get(x));
                if (x!=insqlStrList.size()-1) {
                    fullBuf.append(" or ");
                }
            }
        }
        //返回
        boolean flag=false;
        int count=alarmLogDao.updateAlarmSetByIds(
                alarmLogBean.getHandledflag(),
                alarmLogBean.getResponseuser(),
                alarmLogBean.getResponsetime(),
                fullBuf.toString());
        
        if (count>0) {
            flag=true;
        }
        return flag;
    }
   
    /**
     * @Title: updateAlarmLog
     * @Description: 根据id修改告警(告警响应)
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    public boolean updateAlarmLog(AlarmLogBean alarmLogBean){
        //响应的id
        long id=alarmLogBean.getId();
        //返回
        boolean flag=false;
        int count=alarmLogDao.updateAlarmSetById(
                alarmLogBean.getHandledflag(),
                alarmLogBean.getResponseuser(),
                alarmLogBean.getResponsetime(),
                id);
        
        if (count>0) {
            flag=true;
        }
        return flag;
    }
     
    /**
     * @Title: deleteAlarmLog
     * @Description: 根据id修改告警(告警响应)
     * @param alarmLogBean
     * @return boolean
     * @author chensq
     */
    public boolean deleteAlarmLog(AlarmLogBean alarmLogBean){
        //响应的id
        long id=alarmLogBean.getId();
        //返回
        boolean flag=false;
        int count=alarmLogDao.deleteAlarmLogById(id);
        
        if (count>0) {
            flag=true;
        }
        return flag;
    }
    
    /**
     * @Title: getAlarmKpiIdsByModuleId
     * @Description: 根据moduleId查询AlarmKpi的id(,链接)
     * @param moduleId
     * @param kpiIdSql
     * @return String
     * @author chensq
     */
    public String getAlarmKpiIdsByModuleId(long moduleId, String kpiIdSql){
        List<AlarmKpiBean> list=alarmKpiDao.getAlarmKpiListByModuleId(moduleId, kpiIdSql);
        StringBuffer buf=new StringBuffer();
        if (list!=null && list.size()>0) {
            for (int x=0; x<list.size(); x++) {
                buf.append(list.get(x).getId());
                if (x!=list.size()-1) {
                    buf.append(",");
                }
            }
        }
        return buf.toString();
    }
   
    /**
     * @Title: getAlarmSetIdsByAlarmKpis
     * @Description: 根据kpis查询告警设置
     * @param watchpointId  (告警设置的观察点id，目前需求情况下为0)
     * @param businessId 
     * @param alarmKpiIds
     * @param levelIdsSql
     * @param kpitypeSql
     * @return String
     * @author chensq
     */
    public String getAlarmSetIdsByAlarmKpis(long watchpointId, long businessId, 
            String alarmKpiIds, String levelIdsSql, String kpitypeSql){
        List<AlarmSetBean> alarmSetList= alarmSetDao.getAlarmSetListByAlarmKpiIds(watchpointId, 
                businessId, 
                alarmKpiIds,
                levelIdsSql,
                kpitypeSql);
        StringBuffer buf=new StringBuffer();
        if (alarmSetList!=null && alarmSetList.size() >0) {
            for (int x=0; x<alarmSetList.size(); x++) {
                buf.append(alarmSetList.get(x).getId());
                if (x!=alarmSetList.size()-1) {
                    buf.append(",");
                }
            }
        }
        return buf.toString();
    }
     
    /**
     * @Title: loopDelAlertLog
     * @Description: 告警定时清空使用的方法
     * @param endTime void
     * @author chensq
     */
    public  void  loopDelAlertLog (long endTime){
        try {
            Long startTime= alarmLogDao.getMinStartTime(endTime);
            if (startTime!=null && startTime!=0) {
                alarmLogDao.loopDelAlertLog(startTime, endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: loopDelAlertLogByCount
     * @Description: 循环删除告警日志,根据最大保留上线条数删除
     * @param maxCount
     * @return long
     * @author chensq
     */
    public  long  loopDelAlertLogByCount(long maxCount){
        long time=0;
        try {
            Long startTime= alarmLogDao.getMinStartTimeByCount(maxCount);
            if (startTime!=null && startTime!=0) {
                time=startTime;
                alarmLogDao.loopDelAlertLogByCount(startTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    
    //------------------应用可用性使用方法 start-------------------
    
    /**
     * @Title: insertUsabilityAlarmlog
     * @Description: 应用可用性添加告警
     * @param alarmLogBean
     * @return AlarmLogBean
     * @author chensq
     */
    public AlarmLogBean insertUsabilityAlarmlog(AlarmLogBean alarmLogBean){
                
        AlarmSetBean alarmSetBean =new AlarmSetBean();
        alarmSetBean.setWatchpointId(0);
        alarmSetBean.setBusinessId(alarmLogBean.getBusinessId());
        alarmSetBean.setKpiId(300);
        alarmSetBean.setLevelId(4);
        
        alarmSetBean=alarmSetDao.getAlarmUsabilitySetByParam(alarmSetBean);
        
        //设置告警日志的观察点id为0
        alarmLogBean.setWatchpointId(0);
        alarmLogBean.setAlarmsetId(alarmSetBean.getId());
        alarmLogBean.setHandledflag("N");
        alarmLogBean.setFinishflag("Y");
        alarmLogBean.setTriggerflag(3);
        //执行添加
        alarmLogDao.addAlarmLog(alarmLogBean);

        return alarmLogBean;
    }
     
    /**
     * @Title: insertProbeSynchronousAlarmlog
     * @Description: 探针同步添加添加告警
     * @param alarmLogBean
     * @return AlarmLogBean
     * @author chensq
     */
    public AlarmLogBean insertProbeSynchronousAlarmlog(long alarmsetId, long starttime, long endtime){
        AlarmLogBean alarmLogBean=new AlarmLogBean();
        alarmLogBean.setAlarmsetId(alarmsetId);
        alarmLogBean.setStarttime(starttime);
        alarmLogBean.setEndtime(endtime);

        //设置告警日志的观察点id为0
        alarmLogBean.setWatchpointId(0);
        alarmLogBean.setHandledflag("N");
        alarmLogBean.setFinishflag("Y");
        alarmLogBean.setTriggerflag(5);
        //执行添加
        alarmLogDao.addAlarmLog(alarmLogBean);

        return alarmLogBean;
    }
    
    /**
     * @Title: updateUsabilityAlarmLogEndTimeById
     * @Description: 更新应用可用性的时间
     * @param endtime
     * @param id void
     * @author chensq
     */
    public void updateUsabilityAlarmLogEndTimeById(long endtime, long id){
        alarmLogDao.updateAlarmSetEndTimeById(endtime, id);
    }

    /**
     * @Title: getMaxUsabilityAlarmLongEndTime
     * @Description: 获取应用可用性的告警列表的最大时间
     * @param ip
     * @param port
     * @return AlarmLogBean
     * @author chensq
     */
    public AlarmLogBean getMaxUsabilityAlarmLongEndTime(String ip, String port){
        return alarmLogDao.getMaxUsabilityAlarmLongEndTime(ip, port);
    }
    //------------------应用可用性使用方法 end-------------------
    
    /**
     * @Title: packageAlarmLogs
     * @Description: 对结果集进行封装
     * @param list
     * @return List<AlarmLogBean>
     * @author chensq
     */
    public List<AlarmLogBean> packageAlarmLogs(List<AlarmLogBean> list){
        
        return list;
    }
    
    /**
     * 
     * @Title: getRemoteAlarmLog
     * @Description: 获取远程告警数据
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author www
     */
    public List<AlarmLogBean> getRemoteAlarmLog(AlarmLogBean alarmLogBean) {
        List<AlarmLogBean> list = this.getAlarmLogData(alarmLogBean);
        if (list != null && list.size() > 0) {
            Map<Integer, WatchpointBean> map = new HashMap<Integer, WatchpointBean>();
            List<WatchpointBean> wplist = wpService.getWatchpointInfo();
            WatchpointBean wpbean = null;
            for ( int i = 0, len = wplist.size(); i < len; i ++) {
                wpbean = wplist.get(i);
                map.put(wpbean.getId(), wpbean);
            }
            AlarmLogBean bean = null;
            for (int i = 0, len = list.size(); i < len; i ++) {
                bean = list.get(i);
                wpbean = map.get((int) bean.getBusinessId());
                if (wpbean != null) {
                    bean.setContacts(wpbean.getContacts());
                    bean.setTelephone(wpbean.getTelephone());
                    bean.setEmail(wpbean.getEmail());
                }
            }
        }
        
        return list;
    }
    
    
    /**
     * 
     * @Title: getRemoteAlarmLog4Win
     * @Description: 获取远程告警数据
     * @param alarmLogBean
     * @return List<CenterParamBean>
     * @author www
     */
    public List<Object> getRemoteAlarmLog4Win(AlarmLogBean alarmLogBean) {
        List<Object> list = null;
        String url = "/alarmLog/getAlarmLogData4Win.do";
        Integer centerId = alarmLogBean.getIpmCenterId();
        list = centerService.getRemoteSessionList(url, alarmLogBean, AlarmLogBean.class, centerId);
        
        return list;
    }
    
    /**
     * 
     * @Title: getRemoteAlarmNum
     * @Description: 获取告警数量
     * @param alarmLogBean 参数
     * @param dataList 时间分割
     * @return List<AlarmColumnDataBean>
     * @author www
     */
    private List<AlarmColumnDataBean> getRemoteAlarmNum(AlarmLogBean alarmLogBean, List<String> dataList) {
        List<AlarmColumnDataBean> list = null;
        Map<String, Object> params = BeanUtils.beanToMap(alarmLogBean);
        String url = "/alarmLog/alarmNum.do";
        String data = null;
        Integer centerId = alarmLogBean.getIpmCenterId();
        CenterIpBean bean = null;
        if (centerId != null) {
            if (centerId == 1) {
                list = this.alarmNum(alarmLogBean, dataList);
            } else {
                bean = centerService.getCenterById(centerId);
                data = centerService.getRemoteData(params, centerId, url);
                if (data != null) {
                    list = this.alarmNumStrToList(data, bean);
                }
            }
        } else {
            list = this.alarmNum(alarmLogBean, dataList);
            List<CenterIpBean> center = centerService.getAllAccessInfo();
            List<AlarmColumnDataBean> tmpList = null;
            for (int i = 1, len = center.size(); i < len; i ++) {
                bean = center.get(i);
                data = centerService.getRemoteData(params, bean.getId(), url);
                if (data != null) {
                    tmpList = this.alarmNumStrToList(data, bean);
                    this.alarmNumSum(list, tmpList);
                }
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: alarmNumSum
     * @Description: 告警数量累计
     * @param list 最终数据
     * @param data 告警累计数据
     * @author www
     */
    private void alarmNumSum(List<AlarmColumnDataBean> list, List<AlarmColumnDataBean> data) {
        List<AlarmColumnDetailBean> dbList = null;
        List<AlarmColumnDetailBean> dbData = null;
        AlarmColumnDetailBean bean = null;
        for (int j = 0, jlen = data.size(); j < jlen; j ++) {
            dbList = list.get(j).getAlarmColumnDetailBeanList();
            dbData = data.get(j).getAlarmColumnDetailBeanList();
            for (int i = 0, ilen = dbList.size(); i < ilen; i ++) {
                bean = dbList.get(i);
                bean.setValue(bean.getValue() + dbData.get(i).getValue());
            }
        }
    }
    
    /**
     * 
     * @Title: alarmNumStrToList
     * @Description: 字符串转为List
     * @param data JSON字符串
     * @param ciBean ciBean
     * @return List<AlarmLogBean>
     * @author www
     */
    private List<AlarmColumnDataBean> alarmNumStrToList(String data, CenterIpBean ciBean) {
        List<AlarmColumnDataBean> list = null;
        JSONArray arr = JSONArray.parseArray(data);
        AlarmColumnDataBean bean = null;
        JSONObject obj = null;
        if (arr != null) {
            list = new ArrayList<AlarmColumnDataBean>();
            for (int i = 0, len = arr.size(); i < len; i ++) {
                obj = arr.getJSONObject(i);
                bean = JSONObject.toJavaObject(obj, AlarmColumnDataBean.class);
                list.add(bean);
            }
        }
        
        return list;
    }
}
