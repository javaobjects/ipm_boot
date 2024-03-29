/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlServicef.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmPieBean;
import com.protocolsoft.alarm.bean.AlarmPieColumnBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.app.bean.HeatmapDataBean;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.app.bean.PointDataBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.HeatSimpleBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.enumeration.ServiceRuleType;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.common.service.impl.AppBusinessService;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.bean.UrlRrdParamBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.kpi.service.UrlRrdService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.url.bean.SimpleUrlBean;
import com.protocolsoft.url.bean.UrlKpiParamBean;
import com.protocolsoft.url.bean.UrlSetBean;
import com.protocolsoft.url.bean.UrlStateListBean;
import com.protocolsoft.url.bean.UrlStateParamBean;
import com.protocolsoft.url.dao.UrlDao;
import com.protocolsoft.url.thread.UrlRrdThread;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;
import com.protocolsoft.word.service.TimerReportDetailService;

/**
 * @ClassName: UrlService
 * @Description: 业务
 * @author www
 *
 */
@Service
public class UrlService {
    
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(UrlService.class);
    
    /**
     * DAO
     */
    @Autowired
    private UrlDao dao;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService wpService;
    
    /**
     * 应用
     */
    @Autowired
    private AppService appService;
    
    /**
     * 业务DAO
     */
    @Autowired
    private AppBusinessDao appDao;
    
    /**
     * 告警阈值设置  service
     */
    @Autowired
    private AlarmSetService alarmSetService;
    
    /**
     * 告警
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 绘图service
     */
    @Autowired
    private PlotService plotService;

    /**
     * 公共业务
     */
    @Autowired
    private CommonService commonService;
    
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
    
    /**
     * 业务信息表
     */
    @Autowired
    private AppBusinessService appBusinessService;
    
    /**
     * 
     * @Title: start
     * @Description: RRD数据
     * @author www
     */
    @Scheduled(fixedRate = 60000)
    public void start() {
        long starttime = runtime - 60;
        List<WatchpointBean> wpList = wpService.getWatchpointInfo();
        if (wpList != null && wpList.size() > 0) {
            List<UrlSetBean> urlList = dao.selectUrl();
            if (urlList != null && urlList.size() > 0) {
                List<String> tables = appService.getAppTableNameByModuleId(
                        starttime, runtime, ModuleType.HTTP.getModuleId());
                if (tables != null && tables.size() > 0) {
                    int watchpointId = 0;
                    UrlKpiParamBean paramBean = null;
                    for (int i = 0, ilen = wpList.size(); i < ilen; i ++) {
                        watchpointId = wpList.get(i).getId();
                        for (int j = 0, jlen = urlList.size(); j < jlen; j ++) {
                            paramBean = new UrlKpiParamBean();
                            paramBean.setStarttime(starttime);
                            paramBean.setEndtime(runtime);
                            paramBean.setWatchpointId(watchpointId);
                            paramBean.setBusiId(urlList.get(j).getId());
                            paramBean.setTables(tables);
                            threads.execute(new UrlRrdThread(paramBean, urlList.get(j), dao));
                        }
                    }
                }
            }
        }
        runtime += 60;
    }
    
    /**
     * 
     * @Title: selectUrl
     * @Description: 获取所有的URL信息
     * @return List<UrlSetBean>
     * @author www
     */
    public List<UrlSetBean> selectUrl() {
        
        return dao.selectUrl();
    }
    
    /**
     * 
     * @Title: selectUrl
     * @Description: 获取所有的URL信息
     * @param request 请求
     * @return List<UrlSetBean>
     * @author www
     */
    public List<UrlSetBean> selectUrl(HttpServletRequest request) {
        List<UrlSetBean> beans = null;
        int moduleId = 8;
        String ids = null;
        String remoteId = request.getParameter("remoteId");
        if (remoteId == null) { // 本地请求
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            if (userBean.getRoleId() == 1) {
                beans = this.selectUrl();
            } else {
                ids = jurisService.selectModuleRole(userBean.getId(), moduleId, 1);
                beans = this.selectUrlByIds(ids);
            }
        } else if(CenterIpService.REMOTEID.equals(remoteId)) {
            ids = request.getParameter("remoteBusiIds"); 
            if (ids == null || ids.equals("")) {
                beans = this.selectUrl();
            } else {
                beans = this.selectUrlByIds(ids);
            }
        }
        
        return beans;
    }
    
    /**
     * 
     * @Title: selectUrlByIds
     * @Description: 通过编号获取信息
     * @param ids 
     * @return List<UrlSetBean>
     * @author www
     */
    public List<UrlSetBean> selectUrlByIds(String ids) {
        List<UrlSetBean> list = null;
        if (ids != null) {
            list = dao.selectUrlByIds(ids);
        } else {
            list = new ArrayList<UrlSetBean>();
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: selectUrl
     * @Description: 获取某一组信息
     * @param id 编号
     * @return UrlSetBean
     * @author www
     */
    public UrlSetBean selectUrl(int id) {
        
        return dao.selectUrlById(id);
    }
    
    /**
     * 
     * @Title: add
     * @Description: 添加
     * @param bean URL信息
     * @return Map<String,Integer>
     * @author www
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Integer> add(HttpServletRequest request, UrlSetBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        int moduleId = ModuleType.URL.getModuleId();
        
        AppBusinessBean appBean = new AppBusinessBean();
        appBean.setModuleId(moduleId);
        appBean.setName(bean.getName());
        appBean.setDescrption(bean.getDescrption());
        
        // 添加URL业务设置
        appDao.insertAppBusiness(appBean);
        
        // 添加具体URL
        bean.setId(appBean.getId());
        boolean bool = dao.urlInsertBatch(bean);
        
        try {
            bool = appBusinessService.appBusiCommonProcess(appBean, request);
            if (!bool) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (IOException e) {
            logger.error("URL添加错误：" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        map.put("state", 1);
        
        return map;
    }
    
    /**
     * 
     * @Title: update
     * @Description: URL修改
     * @param bean 参数
     * @return Map<String,Integer>
     * @author www
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Integer> update(HttpServletRequest request, UrlSetBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        
        int moduleId = ModuleType.URL.getModuleId();
        AppBusinessBean appBean = new AppBusinessBean();
        appBean.setModuleId(moduleId);
        appBean.setId(bean.getId());
        appBean.setName(bean.getName());
        appBean.setDescrption(bean.getDescrption());
        // 修改基础信息
        appDao.updateAppBusiness(appBean);
        
        // 具体URL操作
        if (bean != null) {
            List<SimpleUrlBean> set = bean.getSet();
            SimpleUrlBean urlBean = null;
            for (int i = 0, len = set.size(); i < len; i ++) {
                urlBean = set.get(i);
                if (urlBean.getStauts() == 1) { //添加
                    urlBean.setAppId(appBean.getId());
                    dao.urlInsert(urlBean);
                } else if (urlBean.getStauts() == 2) { // 修改
                    dao.updateUrlById(urlBean);
                } else if (urlBean.getStauts() == 3) { // 删除
                    dao.deleteUrlById(urlBean.getId());
                }
            }
        }
        
        // 添加日志
        appBusinessService.appBusiLogs(appBean, request, ServiceRuleType.UPDATE);
        
        map.put("state", 1);
        
        return map;
    }
    
    /**
     * 
     * @Title: delete
     * @Description: 删除
     * @param id void
     * @return Map<String, Integer>
     * @author www
     */
    public Map<String, Integer> delete(HttpServletRequest request, int id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        int moduleId = ModuleType.URL.getModuleId();
        
        //根据id查询预删除的服务端信息，用于添加日志
        AppBusinessBean appbean = appDao.selectAppBusiness(id);
        
        // 删除业务数据
        appDao.deleteAppBusiness(id);
        // 删除URL数据
        dao.deleteUrlByAppId(id);
        // 删除JSON文件
        try {
            JsonFileUtil.getInstance().dleJsonFile(ModuleType.URL, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 删除告警信息
        alarmSetService.delAppBusinessAfter(0, moduleId, id);
        // 删除用户权限
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        jurisService.deleteUserAuthorize(userBean.getId(), id, moduleId);
        //删除报表信息
        reportService.deleteTimerDetailByAppId(id, moduleId);
        // 删除RRD文件
        UrlRrdService rrdService = new UrlRrdService();
        List<WatchpointBean> wpList = wpService.getWatchpointInfo();
        rrdService.deleteFile(wpList, id);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(13);
        logsBean.setMsg("删除"+appbean.getName()+"URL服务");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        map.put("state", 1);
        
        return map;
    }
    
    /**
     * 
     * @Title: getPlotData
     * @Description: 绘图
     * @param bean 参数
     * @return PlotDataBean
     * @author www
     */
    public PlotDataBean getPlotData(PlotParamBean bean, HttpServletRequest request) {
        int plotId = bean.getPlotId();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime60();
            if (plotId == 315) { // 健康图默认一个小时
                bean.setStarttime(timeDefaultBean.getEndtime() - 3600 - 180);
            } else {
                bean.setStarttime(timeDefaultBean.getStarttime() - 180);
            }
            bean.setEndtime(timeDefaultBean.getEndtime() - 180);
        }
        int watchpointId = bean.getWatchpointId();
        int busiId = bean.getBusiId();
        PlotDataBean plotDataBean = new PlotDataBean();
 
        WatchpointBean wpBean = wpService.getWatchpointById(watchpointId);
        UrlSetBean appBean = dao.selectUrlById(busiId);
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        PlotTypeBean plotTypeBean = plotService.getPlotTypeById(bean.getPlotTypeId());
        KpisBean kpisBean = null;
        if (plotId == 354) {
            List<PlotSimpleBean> data = this.getBusinessPie(request, bean);
            plotDataBean.setData(data);
        } else if (plotId == 352) {
            List<PlotSimpleBean> data = new ArrayList<PlotSimpleBean>();
            AlarmLogBean alarmLogBean = new AlarmLogBean();
            alarmLogBean.setWatchpointId(bean.getWatchpointId());
            alarmLogBean.setModuleId(bean.getModuleId());
            alarmLogBean.setBusinessId(bean.getBusiId());
            alarmLogBean.setStarttime(bean.getStarttime());
            alarmLogBean.setEndtime(bean.getEndtime());
            AlarmPieColumnBean alarmPieColumnBean = alarmLogService.getMonLogKpisPieInfo(alarmLogBean);
            PlotSimpleBean plotSimpleBean = null;
            List<AlarmPieBean> listData = alarmPieColumnBean.getData();
            for (AlarmPieBean alarmPieBean : listData) {
                plotSimpleBean = new PlotSimpleBean();
                plotSimpleBean.setName(alarmPieBean.getName());
                plotSimpleBean.setValue(alarmPieBean.getValue());
                data.add(plotSimpleBean);
            }
            plotDataBean.setData(data);
            kpisBean = plotService.getKpisById(plotBean.getKpiId());
            plotDataBean.setUnit(kpisBean.getUnit());
        } else if (plotId == 315) { // 健康图
            HeatmapDataBean hmbean = new HeatmapDataBean();
            kpisBean = new KpisBean();
            kpisBean.setUnit("num");
            AlarmLogBean alarmLogBean = new AlarmLogBean();
            alarmLogBean.setStarttime(bean.getStarttime());
            alarmLogBean.setEndtime(bean.getEndtime());
            alarmLogBean.setModuleId(bean.getModuleId());
            alarmLogBean.setWatchpointId(bean.getWatchpointId());
            alarmLogBean.setBusinessId(bean.getBusiId());
            List<HeatSimpleBean> heatList = plotService.getHeatmap(alarmLogBean);
            int size = heatList.size();
            long[] xLabel = new long[size];
            for (int i = 0; i < size; i ++) {
                xLabel[i] = heatList.get(i).getStarttime();
            }
            hmbean.setData(heatList);
            hmbean.setyLabel(new int[] {1});
            hmbean.setxLabel(xLabel);
            plotDataBean = hmbean;
        } else {
            kpisBean = plotService.getKpisById(plotBean.getKpiId());
            
            UrlRrdService rrdService = new UrlRrdService();
            UrlRrdParamBean paramBean = new UrlRrdParamBean();
            paramBean.setStarttime(bean.getStarttime());
            paramBean.setEndtime(bean.getEndtime());
            paramBean.setWatchpointId(watchpointId);
            paramBean.setBusiId(busiId);
            paramBean.setAlg(RrdAlgorithm.AVG);
            List<SimpleEntry<Long, Double>> kpiData = rrdService.getRrdDataByName(paramBean, kpisBean.getName());
            
            List<PlotSimpleBean> data = new ArrayList<PlotSimpleBean>();
            PlotSimpleBean plotSimpleBean = new PlotSimpleBean();
            plotSimpleBean.setName(plotBean.getName());
            plotSimpleBean.setValue(kpiData);
            data.add(plotSimpleBean);
            plotDataBean.setUnit(kpisBean.getUnit());
            plotDataBean.setData(data);
            
            if ("line".equals(kpisBean.getUnit())) { // 线图展示基线
                plotSimpleBean = plotService.getAlarmBaseLine(bean, kpisBean, AlarmBaseType.HIGH);
                if (plotSimpleBean != null) {
                    data.add(plotSimpleBean);
                }
                
                plotSimpleBean = plotService.getAlarmBaseLine(bean, kpisBean, AlarmBaseType.LOW);
                if (plotSimpleBean != null) {
                    data.add(plotSimpleBean);
                }
            }
        }
        
        plotDataBean.setStarttime(bean.getStarttime());
        plotDataBean.setEndtime(bean.getEndtime());
        plotDataBean.setPlotName(wpBean.getName() + "-" + appBean.getName() + "-" + plotBean.getName());
        plotDataBean.setType(plotTypeBean.getName());

        if (plotId == 354) {
            plotDataBean.setPlotName(plotBean.getName());
        }

        return plotDataBean;
    }
    
    /**
     * 
     * @Title: getSimpleData
     * @Description: 获取十字格数据
     * @param bean 参数
     * @return SimpleDataBean
     * @author www
     */
    public SimpleDataBean getSimpleData(SimpleParamBean bean) {
        boolean flag = true; // 标志是否为回溯
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime60();
            bean.setStarttime(timeDefaultBean.getStarttime() - 180);
            bean.setEndtime(timeDefaultBean.getEndtime() - 180);
            flag = false;
        }
        SimpleDataBean simpleDataBean = new SimpleDataBean();
        List<PointDataBean> data = new ArrayList<PointDataBean>();
        if (bean.getPlotIds() != null) {
            UrlRrdService rrdService = new UrlRrdService();
            String[] ids = bean.getPlotIds().split(",");
            PointDataBean pointDataBean = null;
            PlotOptionBean plotBean = null;
            RrdAlgorithm rrdAlg = null;
            KpisBean kpisBean = null;
            double val = 0;
            int plotId = 0;
            UrlRrdParamBean paramBean = new UrlRrdParamBean();
            paramBean.setStarttime(bean.getStarttime());
            paramBean.setEndtime(bean.getEndtime());
            paramBean.setWatchpointId(bean.getWatchpointId());
            paramBean.setBusiId(bean.getBusiId());
            for (int i = 0, len = ids.length; i < len; i ++) {
                plotId = Integer.parseInt(ids[i]);
                plotBean = plotService.getPlotOptionById(plotId);
                kpisBean = plotService.getKpisById(plotBean.getKpiId());
                if (plotId == 286) { // 未响应告警数量
                    pointDataBean = appService.getAlarmSimpleData(bean, plotBean, flag);
                } else {
                    pointDataBean = new PointDataBean();
                    if (flag && "SUM".equals(plotBean.getCalcul())) {
                        rrdAlg = RrdAlgorithm.SUM;
                    } else {
                        rrdAlg = RrdAlgorithm.AVG;
                    }
                    paramBean.setAlg(rrdAlg);
                    val = rrdService.getRrdDataPointByName(paramBean, kpisBean.getName()).getValue();
                    pointDataBean.setPlotName(plotBean.getName());
                    pointDataBean.setUnit(kpisBean.getUnit());
                    pointDataBean.setValue(val);
                }
                data.add(pointDataBean);
            }
        }
        simpleDataBean.setStarttime(bean.getStarttime());
        simpleDataBean.setEndtime(bean.getEndtime());
        simpleDataBean.setModuleId(bean.getModuleId());
        simpleDataBean.setWatchpointId(bean.getWatchpointId());
        simpleDataBean.setBusiId(bean.getBusiId());
        simpleDataBean.setData(data);
        
        return simpleDataBean;
    }
    
    /**
     * 
     * @Title: getUrlStateList
     * @Description: URL状态列表
     * @param request 请求
     * @param bean 参数
     * @return List<UrlStateListBean>
     * @author www
     */
    public List<UrlStateListBean> getUrlStateList(HttpServletRequest request, UrlStateParamBean bean) {
        List<UrlStateListBean> data = new ArrayList<UrlStateListBean>();
        int moduleId = bean.getModuleId();
        List<AppBusinessBean> appList = appService.getAllAppByModuleId(request, moduleId);
        if (appList != null && appList.size() > 0) {
            if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
                TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime60();
                bean.setStarttime(timeDefaultBean.getStarttime() - 180);
                bean.setEndtime(timeDefaultBean.getEndtime() - 180);
            }
            UrlRrdService rrdService = new UrlRrdService();
            Map<String, RrdAlgorithm> kpiNames = commonService.getRrdMap(moduleId, false);
            
            UrlRrdParamBean rrdBean = new UrlRrdParamBean();
            rrdBean.setStarttime(bean.getStarttime());
            rrdBean.setEndtime(bean.getEndtime());
            rrdBean.setWatchpointId(bean.getWatchpointId());
            
            AlarmLogBean alarmLogBean = new AlarmLogBean();
            alarmLogBean.setModuleId(moduleId);
            alarmLogBean.setWatchpointId(bean.getWatchpointId());
            alarmLogBean.setStarttime(bean.getStarttime());
            alarmLogBean.setEndtime(bean.getEndtime());
            alarmLogBean.setHandledflag("N");
            
            Map<String, Object> simpleData = null;
            UrlStateListBean listBean = null;
            for (int i = 0, len = appList.size(); i < len; i ++) {
                rrdBean.setBusiId(appList.get(i).getId());
                alarmLogBean.setBusinessId(rrdBean.getBusiId());
                simpleData = rrdService.getRrdDataPointByNames(rrdBean, kpiNames);
                
                listBean = new UrlStateListBean();  
                listBean.setId(rrdBean.getBusiId());
                listBean.setName(appList.get(i).getName());
                listBean.setAlarmLevel(alarmLogService.getAlarmLogCount(alarmLogBean).get("count"));
                try {
                    this.setClassValues(listBean, simpleData);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                data.add(listBean);
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getUrlStateList
     * @Description: 获取详细URL信息
     * @param bean 参数
     * @return List<UrlStateListBean>
     * @author www
     */
    public List<UrlStateListBean> getUrlStateList(UrlStateParamBean bean) {
        List<UrlStateListBean> data = new ArrayList<UrlStateListBean>();
        int moduleId = bean.getModuleId();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime60();
            bean.setStarttime(timeDefaultBean.getStarttime() - 180);
            bean.setEndtime(timeDefaultBean.getEndtime() - 180);
        }
        UrlRrdService rrdService = new UrlRrdService();
        Map<String, RrdAlgorithm> kpiNames = commonService.getRrdMap(moduleId, false);
        
        UrlRrdParamBean rrdBean = new UrlRrdParamBean();
        rrdBean.setStarttime(bean.getStarttime());
        rrdBean.setEndtime(bean.getEndtime());
        rrdBean.setWatchpointId(bean.getWatchpointId());
        rrdBean.setBusiId(bean.getBusiId());
        
        UrlSetBean appBean = dao.selectUrlById(bean.getBusiId());
        List<SimpleUrlBean> urlList = appBean.getSet();
        if (urlList != null) {
            Map<String, Object> simpleData = null;
            UrlStateListBean listBean = null;
            for (int i = 0, len = urlList.size(); i < len; i ++) {
                rrdBean.setSubId(urlList.get(i).getId());
                simpleData = rrdService.getRrdDataPointByNames(rrdBean, kpiNames);
                
                listBean = new UrlStateListBean();
                listBean.setId(urlList.get(i).getId());
                listBean.setName(urlList.get(i).getName());
                listBean.setUrl(urlList.get(i).getUrl());
                try {
                    this.setClassValues(listBean, simpleData);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                data.add(listBean);
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: setClassValues
     * @Description: 设置value
     * @param classes 实体
     * @param map 值
     * @throws IllegalAccessException void
     * @author www
     */
    public void setClassValues(UrlStateListBean classes, 
        Map<String, Object> map) throws IllegalAccessException {
        String fieldName = null;
        Field[] fields = classes.getClass().getDeclaredFields();
        for (Field f : fields) {
            fieldName = f.getName();
            if (map.containsKey(fieldName)) {
                f.setAccessible(true);
                f.set(classes, map.get(fieldName));
            }
        }
    }
    
    /**
     * 
     * @Title: getBusinessPie
     * @Description: 获取业务饼图分布
     * @param request 请求
     * @param bean 参数
     * @return List<PlotSimpleBean>
     * @author WWW
     */
    private List<PlotSimpleBean> getBusinessPie(HttpServletRequest request, PlotParamBean bean) {
        List<PlotSimpleBean> data = new ArrayList<PlotSimpleBean>();
        int moduleId = bean.getModuleId();
        List<AppBusinessBean> appList = appService.getAllAppByModuleId(request, moduleId);
        if (appList != null && appList.size() > 0) {
            if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
                TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime60();
                bean.setStarttime(timeDefaultBean.getStarttime() - 180);
                bean.setEndtime(timeDefaultBean.getEndtime() - 180);
            }
            UrlRrdService rrdService = new UrlRrdService();
            Map<String, RrdAlgorithm> kpiNames = new HashMap<>();
            kpiNames.put("sessionNum", RrdAlgorithm.SUM);
            
            UrlRrdParamBean rrdBean = new UrlRrdParamBean();
            rrdBean.setStarttime(bean.getStarttime());
            rrdBean.setEndtime(bean.getEndtime());
            rrdBean.setWatchpointId(bean.getWatchpointId());
            
            PlotSimpleBean plotSimpleBean = null;
            Map<String, Object> simpleData = null;
            AppBusinessBean appBean = null;
            for (int i = 0, len = appList.size(); i < len; i ++) {
                plotSimpleBean = new PlotSimpleBean();
                appBean = appList.get(i);
                rrdBean.setBusiId(appBean.getId());
                simpleData = rrdService.getRrdDataPointByNames(rrdBean, kpiNames);
                
                plotSimpleBean.setId(appBean.getId());
                plotSimpleBean.setName(appBean.getName());
                plotSimpleBean.setValue(simpleData.get("sessionNum"));
                data.add(plotSimpleBean);
            }
        }
        
        return data;
    }
    
    /**
     * 上次执行时间
     */
    private static long runtime;
    
    /**
     * 线程
     */
    public static ExecutorService threads;
    
    /**
     * 初始化
     */
    static {
        runtime = DateUtils.getNowTimeSecond(60) - 60;
        threads = Executors.newFixedThreadPool(6);
    }
}
