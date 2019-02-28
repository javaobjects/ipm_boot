/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ServerManagementServiceImpl
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.servers.service.impl;


import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.app.bean.ParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.enumeration.ServiceRuleType;
import com.protocolsoft.common.service.impl.AppBusinessService;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;
import com.protocolsoft.word.service.TimerReportDetailService;

/**
 * 服务端管理业务逻辑接口实现类
 * 2017年9月1日 上午11:29:04
 * @author yan
 * @version
 * @see
 */
@Service
public class ServerManagementServiceImpl implements ServerManagementService{

    /**
     * logger
     */
    static Logger logger = Logger.getLogger(ServerManagementServiceImpl.class);
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
    /**
     * 业务信息表
     */
    @Autowired
    private AppBusinessService appBusinessService;
    
    /**
     * 业务信息Dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * ipm_app_ip_port表Dao
     */
    @Autowired
    private AppIpPortDao appIpPortDao;
    
    /**
     * 绘图service
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * 告警阈值设置  service
     */
    @Autowired
    private AlarmSetService alarmSetService;
    
    /**
     * 告警log
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 权限
     */
    @Autowired
    private AuthorizeJurisService jurisService;
    
    /**
     * 报表
     */
    @Autowired(required = false)
    private TimerReportDetailService reportService;
    
    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#addServerSide()
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> addServerSide(AppBusinessBean bean, HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            //新增服务端表ipm_app_business
            appBusinessDao.insertAppBusiness(bean);
            
            int state = appBusinessService.addBusiIpPort(bean, false);
            if (state == 1) { // 成功
                boolean bool = appBusinessService.appBusiCommonProcess(bean, request);
                if (bool) {
                    map.put("state", 1);
                } else {
                    map.put("state", 0);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            } else {
                map.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (IOException e) {
            map.put("state", 0);
            logger.error("新增服务端异常,异常信息:" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
        return map;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getServerSide(java.lang.Integer)
     */
    @Override
    public AppBusinessBean getServerSide(Integer id) {
        return appBusinessDao.selectAppBusiness(id);
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#updateServerSide
     * (com.protocolsoft.serverside.bean.AppBusinessBean)
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> updateServerSide(HttpServletRequest request, AppBusinessBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        //更新
        appBusinessDao.updateAppBusiness(bean);
        
        //通过appId删除
        appIpPortDao.deleteAppIpPort(bean.getId());
        
        // 添加IP端口
        int state = appBusinessService.addBusiIpPort(bean, true);
        if (state == 1) {
            // 添加日志
            appBusinessService.appBusiLogs(bean, request, ServiceRuleType.UPDATE);
            map.put("state", 1);
        } else {
            map.put("state", state);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
        return map;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getAllServerSide()
     */
    @Override
    public List<AppBusinessBean> getAllServerSide(Integer moduleId) {
        List<AppBusinessBean> beans = appBusinessDao.selectAppBusinessesByModuleId(moduleId);
        return beans;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getAllServerSide()
     */
    @Override
    public List<AppBusinessBean> getAllServerSide(HttpServletRequest request, Integer moduleId) {
        List<AppBusinessBean> beans = null;
        String ids = null;
        String remoteId = request.getParameter("remoteId");
        if (remoteId == null) { // 本地请求
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            if (userBean.getRoleId() == 1) {
                beans = this.getAllServerSide(moduleId);
            } else {
                ids = jurisService.selectModuleRole(userBean.getId(), moduleId, 1);
                beans = this.getAllServerSide(moduleId, ids);
            }
        } else if(CenterIpService.REMOTEID.equals(remoteId)) {
            ids = request.getParameter("remoteBusiIds"); 
            if (ids == null || ids.equals("")) {
                beans = this.getAllServerSide(moduleId);
            } else {
                beans = this.getAllServerSide(moduleId, ids);
            }
        }
        
        return beans;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getAllServerSide()
     */
    @Override
    public List<AppBusinessBean> getAllServerSide(Integer moduleId, String ids) {
        List<AppBusinessBean> beans = null;
        if (ids != null) {
            beans = appBusinessDao.selectAppBusinessesByModuleIdByIds(moduleId, ids);
        } else {
            beans = new ArrayList<AppBusinessBean>();
        }
        return beans;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getAppIpPortByAppId()
     */
    @Override
    public List<AppIpPortBean> getAppIpPortByAppId(Integer appId) {
        String appIdsql = "";
        if (appId == null || 0 == appId) {
            appIdsql = "";
        } else {
            appIdsql = " WHERE app_id = " + appId;
        }
        List<AppIpPortBean> beans = appIpPortDao.selectAppIpPortsByAppIdSqlNotDecrypt(appIdsql);
        return beans;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#deleteServerSide(java.lang.Integer)
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> deleteServerSide(int moduleId, Integer id, HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            
            //根据id查询预删除的服务端信息，用于添加日志
            AppBusinessBean appbean = appBusinessDao.selectAppBusiness(id);
            
            //删除主表
            appBusinessDao.deleteAppBusiness(id);
            //删除从表
            appIpPortDao.deleteAppIpPort(id);
            
            //删除服务端设置配置
            JsonFileUtil jfu = JsonFileUtil.getInstance();
            jfu.dleJsonFile(ModuleType.getModuleType(moduleId), id);
            
            boolean flag = alarmSetService.delAppBusinessAfter(0, moduleId, id);
            if (!flag){
                map.put("state", 0);
            } else {
                map.put("state", 1);
            }
            
            // 删除用户权限
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            jurisService.deleteUserAuthorize(userBean.getId(), id, moduleId);
            reportService.deleteTimerDetailByAppId(id, moduleId);
            
            //添加log日志参数bean
            appBusinessService.appBusiLogs(appbean, request, ServiceRuleType.DELETE);
            
        } catch (Exception e) {
            logger.debug("删除服务端异常,异常信息:" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
        return map;
    }
    
    /**
     * @return the appBusinessDao
     */
    public AppBusinessDao getAppBusinessDao() {
        return appBusinessDao;
    }

    /**
     * @param appBusinessDao the appBusinessDao to set
     */
    public void setAppBusinessDao(AppBusinessDao appBusinessDao) {
        this.appBusinessDao = appBusinessDao;
    }

    /**
     * @return the appIpPortDao
     */
    public AppIpPortDao getAppIpPortDao() {
        return appIpPortDao;
    }

    /**
     * @param appIpPortDao the appIpPortDao to set
     */
    public void setAppIpPortDao(AppIpPortDao appIpPortDao) {
        this.appIpPortDao = appIpPortDao;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService#getServerSideGraphical
     * (com.protocolsoft.common.bean.DrawingOptionsBean)
     */
    @Override
    public Map<String, Object> getServerSideGraphical(
            DrawingOptionsBean drawingOptionsBean, Integer serverId) {
        Map<String, Object> mapList = new HashMap<String, Object>();
        long starttime = 0;
        long endtime = 0;
        int plotId = drawingOptionsBean.getPlotId();
        if (drawingOptionsBean.getStarttime() == null) {
            TimeDefaultBean time = DateAppsUtils.getGraphDefaultTime();
            endtime = time.getEndtime();
            if (plotId == 310) { // 健康图默认一个小时
                starttime = endtime - 3600;
            } else {
                starttime = time.getStarttime();
            }
        } else {
            starttime = drawingOptionsBean.getStarttime();
            endtime = drawingOptionsBean.getEndtime();
        }
        int watchpointId = drawingOptionsBean.getWatchpointId();
        WatchpointBean bean = watchpointService.getWatchpointById(watchpointId);
        AppBusinessBean appBean = appBusinessDao.selectAppBusiness(serverId);
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        PlotTypeBean plotTypeBean= plotService.getPlotTypeById(drawingOptionsBean.getPlotTypeId());
        KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
        
        BusiKpiService kpiService = new BusiKpiService(watchpointId, serverId);
        
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        if (plotId == 310) { // 健康图
            kpisBean = new KpisBean();
            plotService.getHeatmap(starttime, endtime, 12, watchpointId, serverId, mapList, kpisBean);
        } else if(plotId == 319) { // 服务质量多线图
            List<SimpleEntry<Long, Double>> tmp = null;
            KpisBean kpi = null;
            int[] qos = new int[]{ 14, 7, 36, 16, 6 };
            for (int i = 0, len = qos.length; i < len; i ++) {
                kpi = plotService.getKpisById(qos[i]);
                map = new HashMap<String, Object>();
                tmp = kpiService.getRrdDataByName(starttime, endtime, 0, kpi.getName(), RrdAlgorithm.AVG);
                map.put("value", tmp);
                map.put("name", kpi.getDisplayName());
                map.put("unit", kpi.getUnit());
                if (qos[i] == 6) { // 链路时延为虚线
                    map.put("type", "spline");
                }
                list.add(map);
            }
            mapList.put("data", list);
        } else {
            List<SimpleEntry<Long, Double>> resultMap = kpiService.getRrdDataByName(starttime, 
                    endtime, drawingOptionsBean.getStep(), kpisBean.getName(), RrdAlgorithm.AVG);
            map = new HashMap<String, Object>();
            map.put("name", kpisBean.getDisplayName());
            map.put("value", resultMap);
            list.add(map);
            mapList.put("data", list);
            
            if ("line".equals(kpisBean.getUnit())) { // 线图展示基线
                ParamBean paramBean = new ParamBean();
                paramBean.setStarttime(starttime);
                paramBean.setEndtime(endtime);
                paramBean.setModuleId(12);
                paramBean.setBusiId(serverId);
                paramBean.setWatchpointId(watchpointId);

                PlotSimpleBean simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.HIGH);
                if (simpleBean != null) {
                    map = new HashMap<String, Object>();
                    map.put("value", simpleBean.getValue());
                    map.put("name", simpleBean.getName());
                    map.put("type", simpleBean.getType());
                    list.add(map);
                }
                
                simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.LOW);
                if (simpleBean != null) {
                    map = new HashMap<String, Object>();
                    map.put("value", simpleBean.getValue());
                    map.put("name", simpleBean.getName());
                    map.put("type", simpleBean.getType());
                    list.add(map);
                }
            }
        }
        
        if (plotId == 319) { // QoS
            mapList.put("kpiName", kpisBean.getName());
        }
        
        mapList.put("starttime", starttime);
        mapList.put("endtime", endtime);
        mapList.put("type", plotTypeBean.getName());
        mapList.put("unit", kpisBean.getUnit());
        mapList.put("plotName", bean.getName() + "-" + appBean.getName() + "-" + plotBean.getName());
        
        return mapList;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.serverside.service.ServerManagementService
     * #getServerSideSingleValueData(com.protocolsoft.common.bean.DrawingOptionsBean)
     */
    @Override
    public Map<String, Object> getServerSideSingleValueData(
            DrawingOptionsBean drawingOptionsBean, Integer serverId, String isCalcul, boolean flag) {
        Map<String, Object> mapList = new HashMap<String, Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        mapList.put("starttime", drawingOptionsBean.getStarttime());
        mapList.put("endtime", drawingOptionsBean.getEndtime());
        mapList.put("watchPointId", drawingOptionsBean.getWatchpointId());
        mapList.put("serverId", serverId);

        if (null != drawingOptionsBean.getPlotIds()
                && !"".equals(drawingOptionsBean.getPlotIds())) {
            String[] plotIdArr = drawingOptionsBean.getPlotIds().split(",");
            RrdAlgorithm rrdAlgorithm = RrdAlgorithm.AVG;
            BusiKpiService kpiService = null;
            for (String optionId : plotIdArr) {
                Map<String, Object> map = new HashMap<String, Object>();
                PlotOptionBean plotBean = plotService.getPlotOptionById(Integer.parseInt(optionId));
                KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
                map.put("plotName", plotBean.getName());
                if (Integer.parseInt(optionId) == 88) {
                    AlarmLogBean alarmLogBean = new AlarmLogBean();
                    if (flag) {
                        alarmLogBean.setStarttime(drawingOptionsBean.getStarttime());
                        alarmLogBean.setEndtime(drawingOptionsBean.getEndtime());
                    }
                    alarmLogBean.setHandledflag("N");
                    // 观察点
                    alarmLogBean.setWatchpointId(drawingOptionsBean.getWatchpointId());
                    // 业务id
                    alarmLogBean.setBusinessId(serverId);
                    // 模块id
                    alarmLogBean.setModuleId(plotBean.getModuleId());
                    map.put("unit", "num");
                    Map<String, Long> alarmLogMap = alarmLogService.getAlarmLogCount(alarmLogBean);
                    // 开始时间
                    map.put("starttime", alarmLogMap.get("starttime"));
                    // 结束时间
                    map.put("endtime", alarmLogMap.get("endtime"));
                    map.put("value", alarmLogMap.get("count"));
                    map.put("alarmLevelId", alarmLogMap.get("alarmLevelId"));
                } else {
                    map.put("unit", kpisBean.getUnit());
                    kpiService = new BusiKpiService(drawingOptionsBean.getWatchpointId(), serverId);
                    String calcul = plotBean.getCalcul();
                    if (flag && "SUM".equals(calcul)) {
                        rrdAlgorithm = RrdAlgorithm.SUM;
                    } else {
                        rrdAlgorithm = RrdAlgorithm.AVG;
                    }
                    Double result = kpiService.getRrdDataPointByName(drawingOptionsBean.getStarttime(),
                            drawingOptionsBean.getEndtime(), kpisBean.getName(), rrdAlgorithm).getValue();
                    map.put("value", result);
                }
                list.add(map);
            }
        }
        mapList.put("data", list);
        
        return mapList;
    }
}
