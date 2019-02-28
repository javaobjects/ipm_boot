/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppService.java
 *创建人: www    创建时间: 2018年1月10日
 */
package com.protocolsoft.app.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.app.bean.AlarmDataBean;
import com.protocolsoft.app.bean.AppSessionBean;
import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;
import com.protocolsoft.app.bean.DBSessionBean;
import com.protocolsoft.app.bean.HeatmapDataBean;
import com.protocolsoft.app.bean.HttpSessionBean;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.app.bean.PointDataBean;
import com.protocolsoft.app.bean.SessionParamsBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.app.bean.TableParamBean;
import com.protocolsoft.app.dao.AppDao;
import com.protocolsoft.app.enumeration.AppTableType;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.HeatSimpleBean;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * @ClassName: AppService
 * @Description: 应用
 * @author www
 *
 */
@Service
public class AppService {
    
    /**
     * 应用DAO
     */
    @Autowired
    private AppDao appDao;
    
    /**
     * TOP业务
     */
    @Autowired
    private AppTopService topService;

    /**
     * 服务端业务
     */
    @Autowired
    private ServerManagementService serverService;
    
    /**
     * 绘图service
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * 告警log
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 用户配置列表字段
     */
    @Autowired
    private UserListColumnService userListColumnService;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
    /**
     * 权限
     */
    @Autowired
    private AuthorizeJurisService jurisService;
    
    /**
     * Center
     */
    @Autowired
    private CenterIpService centerService;
    
    /**
     * 排名绘图编号
     */
    private final static Map<Integer, Character> TOP_PLOT_ID = new HashMap<Integer, Character>() { 
        private static final long serialVersionUID = 1L;
        {
            put(237, '1');      put(238, '1');      put(239, '1');      put(240, '1');
            put(241, '1');      put(242, '1');      put(243, '1');      put(244, '1');
            put(245, '1');      put(246, '1');
        }
    };
    
    /**
     * 健康图绘图编号
     */
    private final static Map<Integer, Character> HEATMAP_PLOT_ID = new HashMap<Integer, Character>() { 
        private static final long serialVersionUID = 1L;
        {
            put(311, '1');      put(312, '1');      put(313, '1');      put(314, '1');
        }
    };
    
    /**
     * 
     * @Title: addApp
     * @Description: 添加
     * @param request
     * @param bean
     * @return Map<String, Integer>
     * @author www
     */
    public Map<String, Integer> addApp(HttpServletRequest request, AppBusinessBean bean) {
        Map<String, Integer> map = serverService.addServerSide(bean, request);
        
        return map;
    }
    
    /**
     * 
     * @Title: deleteApp
     * @Description: 删除业务
     * @param request
     * @param bean
     * @return Map<String, Integer>
     * @author www
     */
    public Map<String, Integer> deleteApp(HttpServletRequest request, AppBusinessBean bean) {
        Map<String, Integer> map = serverService.deleteServerSide(bean.getModuleId(), bean.getId(), request);
        
        return map;
    }
    
    /**
     * 
     * @Title: updateApp
     * @Description: 更新业务
     * @param request
     * @param bean
     * @return Map<String, Integer>
     * @author www
     */
    public Map<String, Integer> updateApp(HttpServletRequest request, AppBusinessBean bean) {
        Map<String, Integer> map = serverService.updateServerSide(request, bean);
        
        return map;
    }
    
    /**
     * 
     * @Title: getAllAppByModuleId
     * @Description: 获取某一应用所有业务
     * @param request 请求
     * @param moduleId 模块编号
     * @return List<AppBusinessBean>
     * @author www
     */
    public List<AppBusinessBean> getAllAppByModuleId(HttpServletRequest request, int moduleId) {
        List<AppBusinessBean> beans = null;
        String ids = null;
        String remoteId = request.getParameter("remoteId");
        if (remoteId == null) { // 本地请求
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            if (userBean.getRoleId() == 1) {
                beans = serverService.getAllServerSide(moduleId);
            } else {
                ids = jurisService.selectModuleRole(userBean.getId(), moduleId, 1);
                beans = serverService.getAllServerSide(moduleId, ids);
            }
        } else if(CenterIpService.REMOTEID.equals(remoteId)) {
            ids = request.getParameter("remoteBusiIds"); 
            if (ids == null || ids.equals("")) {
                beans = serverService.getAllServerSide(moduleId);
            } else {
                beans = serverService.getAllServerSide(moduleId, ids);
            }
        }
        
        return beans;
    }

    /**
     * 
     * @Title: getAllAppByModuleId
     * @Description: 根据模块编号获取业务信息
     * @param centerId Center编号
     * @param userId 用户编号
     * @param moduleId 模块编号
     * @return List<AppBusinessBean>
     * @author wangjianmin
     */
    public List<AppBusinessBean> getAllAppByModuleId(int centerId, int userId, int moduleId) {
        List<AppBusinessBean> beans = null;
        SystemUserBean userBean = systemUserService.getUserBeanById(userId);
        if (userBean.getRoleId() == 1) {
            beans = serverService.getAllServerSide(moduleId);
        } else {
            String ids = jurisService.selectModuleRole(userBean.getId(), moduleId, centerId);
            beans = serverService.getAllServerSide(moduleId, ids);
        }
        
        return beans;
    }
    
    /**
     * 
     * @Title: getAllAppByModuleId
     * @Description: 获取某一应用所有业务
     * @param moduleId 模块编号
     * @return List<AppBusinessBean>
     * @author www
     */
    public List<AppBusinessBean> getAllAppByModuleId(int moduleId) {
        List<AppBusinessBean> beans = serverService.getAllServerSide(moduleId);
        
        return beans;
    }
    
    /**
     * 
     * @Title: getPlotData
     * @Description: 绘图
     * @param bean
     * @return PlotDataBean
     * @author www
     */
    public PlotDataBean getPlotData(PlotParamBean bean) {
        int plotId = bean.getPlotId();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean time = DateAppsUtils.getGraphDefaultTime();
            bean.setEndtime(time.getEndtime());
            if (HEATMAP_PLOT_ID.containsKey(plotId)) {
                bean.setStarttime(time.getEndtime() - 3600);
            } else {
                bean.setStarttime(time.getStarttime());
            }
        }
        PlotDataBean plotDataBean = new PlotDataBean();

        WatchpointBean wpBean = watchpointService.getWatchpointById(bean.getWatchpointId());
        AppBusinessBean appBean = serverService.getServerSide(bean.getBusiId());
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        PlotTypeBean plotTypeBean = plotService.getPlotTypeById(bean.getPlotTypeId());

        KpisBean kpisBean = null;
        List<PlotSimpleBean> data = null;
        if (TOP_PLOT_ID.containsKey(plotId)) {
            AppTopParamsBean appTopParamsBean = new AppTopParamsBean();
            appTopParamsBean.setModuleId(bean.getModuleId());
            appTopParamsBean.setStarttime(bean.getStarttime());
            appTopParamsBean.setEndtime(bean.getEndtime());
            appTopParamsBean.setWatchpointId(bean.getWatchpointId());
            appTopParamsBean.setBusiId(bean.getBusiId());
            
            AppTopConfigBean appTopConfigBean = topService.getAppTopConfigById(bean.getModuleId(), plotId);
            appTopParamsBean.setBean(appTopConfigBean);
            
            List<String> tables = this.getAppTableNameByModuleId(
                    bean.getStarttime(), bean.getEndtime(), bean.getModuleId());
            if (tables != null && tables.size() > 0) {
                appTopParamsBean.setTables(tables);
                
                data = topService.selectTop(appTopParamsBean);
            } else {
                data = new ArrayList<PlotSimpleBean>();
            }
            plotDataBean.setData(data);
        } else if (HEATMAP_PLOT_ID.containsKey(plotId)) {
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
            BusiKpiService kpiService = new BusiKpiService(bean.getWatchpointId(), bean.getBusiId());
            List<SimpleEntry<Long, Double>> kpiData = kpiService.getRrdDataByName(bean.getStarttime(),
                    bean.getEndtime(), bean.getStep(), kpisBean.getName(), RrdAlgorithm.AVG);
            
            data = new ArrayList<PlotSimpleBean>();
            PlotSimpleBean plotSimpleBean = new PlotSimpleBean();
            plotSimpleBean.setName(plotBean.getName());
            plotSimpleBean.setValue(kpiData);
            data.add(plotSimpleBean);
            
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
            plotDataBean.setUnit(kpisBean.getUnit());
            plotDataBean.setData(data);        
        }

        plotDataBean.setStarttime(bean.getStarttime());
        plotDataBean.setEndtime(bean.getEndtime());
        plotDataBean.setPlotName(wpBean.getName() + "-" + appBean.getName() + "-" + plotBean.getName());
        plotDataBean.setType(plotTypeBean.getName());

        return plotDataBean;
    }
    
    /**
     * 
     * @Title: getTopTable
     * @Description: 获取TOP表名
     * @param time 时间
     * @return String 表名
     * @author www
     */
    public String getTopTable(long time) {
        String table = null;
        long diff = DateUtils.getNowTimeSecond() - time;
        if (diff <= 14400) {
            table = "ipm_app_topn_60";
        } else if (diff <= 86400) {
            table = "ipm_app_topn_600";
        } else if (diff <= 604800) {
            table = "ipm_app_topn_3600";
        } else {
            table = "ipm_app_topn_86400";
        }
        
        return table;
    }
    
    /**
     * 
     * @Title: getSimpleData
     * @Description: 获取十字格数据
     * @param bean
     * @return SimpleDataBean
     * @author www
     */
    public SimpleDataBean getSimpleData(SimpleParamBean bean) {
        boolean flag = true; // 标志是否为回溯
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime();
            bean.setStarttime(timeDefaultBean.getStarttime());
            bean.setEndtime(timeDefaultBean.getEndtime());
            flag = false;
        }
        SimpleDataBean simpleDataBean = new SimpleDataBean();
        List<PointDataBean> data = new ArrayList<PointDataBean>();
        if (bean.getPlotIds() != null) {
            String[] ids = bean.getPlotIds().split(",");
            PointDataBean pointDataBean = null;
            BusiKpiService kpiService = null;
            PlotOptionBean plotBean = null;
            RrdAlgorithm rrdAlg = null;
            KpisBean kpisBean = null;
            double val = 0;
            int plotId = 0;
            for (int i = 0, len = ids.length; i < len; i ++) {
                plotId = Integer.parseInt(ids[i]);
                plotBean = plotService.getPlotOptionById(plotId);
                kpisBean = plotService.getKpisById(plotBean.getKpiId());
                if (plotId == 101 || plotId == 135 || plotId == 169 || plotId == 203) { // 未响应告警数量
                    pointDataBean = this.getAlarmSimpleData(bean, plotBean, flag);
                } else {
                    pointDataBean = new PointDataBean();
                    kpiService = new BusiKpiService(bean.getWatchpointId(), bean.getBusiId());
                    if (flag && "SUM".equals(plotBean.getCalcul())) {
                        rrdAlg = RrdAlgorithm.SUM;
                    } else {
                        rrdAlg = RrdAlgorithm.AVG;
                    }
                    val = kpiService.getRrdDataPointByName(bean.getStarttime(),
                        bean.getEndtime(), kpisBean.getName(), rrdAlg).getValue();
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
     * @Title: getAlarmSimpleData
     * @Description: 获取告警数据
     * @param bean 参数
     * @param plotBean 绘图Bean
     * @param flag 是否为回溯
     * @return PointDataBean
     * @author www
     */
    public PointDataBean getAlarmSimpleData(SimpleParamBean bean, 
            PlotOptionBean plotBean, boolean flag) {
        AlarmDataBean alarmDataBean = new AlarmDataBean();
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        if (flag) {
            alarmLogBean.setStarttime(bean.getStarttime());
            alarmLogBean.setEndtime(bean.getEndtime());
        }
        alarmLogBean.setHandledflag("N");
        alarmLogBean.setModuleId(bean.getModuleId()); // 模块id
        alarmLogBean.setWatchpointId(bean.getWatchpointId()); // 观察点
        alarmLogBean.setBusinessId(bean.getBusiId()); // 业务id
        Map<String, Long> alarmLogInfo = alarmLogService.getAlarmLogCount(alarmLogBean);
        alarmDataBean.setStarttime(alarmLogInfo.get("starttime"));
        alarmDataBean.setEndtime(alarmLogInfo.get("endtime"));
        alarmDataBean.setValue(alarmLogInfo.get("count"));
        alarmDataBean.setAlarmLevelId(alarmLogInfo.get("alarmLevelId"));
        alarmDataBean.setPlotName(plotBean.getName());
        alarmDataBean.setUnit("num");
        
        return alarmDataBean;
    }
    
    /**
     * 
     * @Title: getAppListColumnByTypeId
     * @Description: 获取列表列
     * @param request 请求
     * @param moduleId 模块ID
     * @param typeId 类型ID
     * @return List<ListColumnBean>
     * @author www
     */
    public List<ListColumnBean> getAppListColumnByTypeId(
            HttpServletRequest request, int moduleId, int typeId) {
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        List<ListColumnBean> columns = userListColumnService.getUserListColumn(user.getId(), typeId);
        
        return columns;
    }
    
    /**
     * 
     * @Title: getAppTableNameByModuleId
     * @Description: 获取应用一段时间内表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @return List<String> 表名集合
     * @author www
     */
    public List<String> getAppTableNameByModuleId(long starttime, long endtime, int moduleId) {
        AppTableType type = AppTableType.getTypeByModuleId(moduleId);
        TableParamBean bean = new TableParamBean();
        bean.setStarttime(starttime);
        bean.setEndtime(endtime);
        bean.setTableName(type.getTableName());
        bean.setName(type.getTablePrefix());
        List<String> tables = appDao.getAppTableName(bean);
        
        return tables;
    }
    
    /**
     * 
     * @Title: getAppStateList
     * @Description: 获取会话列表
     * @param bean 参数
     * @return List<AppSessionBean>
     * @author www
     */
    public List<Object> getAppStateList(SessionParamsBean bean) {
        List<Object> data = new ArrayList<Object>();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime();
            bean.setStarttime(timeDefaultBean.getStarttime());
            bean.setEndtime(timeDefaultBean.getEndtime());
        }
        int moduleId = bean.getModuleId();
        // URL与HTTP表一致
        if (moduleId == 8) {
            moduleId = 4;
        }
        List<String> tables = this.getAppTableNameByModuleId(
                bean.getStarttime(), bean.getEndtime(), moduleId);
        if (tables != null && tables.size() > 0) {
            int count = 1000; // 每次至多N条
            int filterNum = 0;
            if (bean.getServerPageNumber() != null) {
                filterNum = (bean.getServerPageNumber() - 1) * count;
            }
            List<ListColumnBean> columns = userListColumnService.getListColumn(moduleId + 10);
            StringBuilder column = new StringBuilder();
            column.append(columns.get(0).getColumnen());
            String tmp = null;
            for (int i = 1, len = columns.size(); i < len; i ++) {
                tmp = columns.get(i).getColumnen();
                if ("ipmCenterName".equals(tmp)) {
                    continue;
                }
                column.append(", ");
                if ("serverip".equals(tmp) || "server".equals(tmp) 
                        || "client".equals(tmp) || "forward".equals(tmp)) { // IP进行解码
                    column.append("INET_NTOA(");
                    column.append(tmp);
                    column.append(") ");
                } else if ("url".equals(tmp)) { // URL进行组合
                    column.append("CONCAT(server, url) ");
                } else if ("port".equals(tmp)) {
                    column.append("l.port ");
                }
                column.append(tmp);
            }
            bean.setColumn(column.toString());
            
            List<? extends AppSessionBean> tmpList = null;
            for (int i = 0, len = tables.size(); i < len; i ++) {
                bean.setTable(tables.get(i));
                bean.setStartNum(filterNum);
                bean.setNum(count);
                if (moduleId == 4) { // HTTP会话列表
                    tmpList = appDao.getHttpSessionData(bean);
                } else { // DB会话列表
                    tmpList = appDao.getDBSessionData(bean);
                }
                if (filterNum != 0 && tmpList.size() == 0) {
                    filterNum -= appDao.getSessionDataNum(bean);
                } else {
                    filterNum = 0;
                }
                data.addAll(tmpList);
                count -= data.size();
                if (count <= 0) {
                    break;
                }
            }
        }
        
        return data;
    }

    /**
     * @Title: getRemoteAppStateList
     * @Description: 获取会话列表
     * @param bean 参数
     * @return List<AppSessionBean>
     * @author www
     */
    public List<Object> getRemoteAppStateList(SessionParamsBean bean) {
        List<Object> dataBean = null;
        String url = "/appController/getAppStateList.do";
        Integer centerId = bean.getIpmCenterId();
        Class<? extends AppSessionBean> cla = null;
        int moduleId = bean.getModuleId();
        if (moduleId == 4 || moduleId == 8) {
            cla = HttpSessionBean.class;
        } else {
            cla = DBSessionBean.class;
        }
        dataBean = centerService.getRemoteSessionList(url, bean, cla, centerId);
        
        return dataBean;
    }
}
