/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommonServiceImpl
 *创建人:yan    创建时间:2017年9月19日
 */
package com.protocolsoft.common.service.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.Heatmap;
import com.protocolsoft.common.bean.HeatmapBlock;
import com.protocolsoft.common.bean.ReportListBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.dao.UserListColumnDao;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * 公共业务逻辑接口
 * 2017年9月19日 上午11:48:32
 * @author yan
 * @version
 * @see
 */
@Service
public class CommonServiceImpl implements CommonService{

    /**
     * ipm_app_business表Dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * 业务
     */
    @Autowired
    private AppService appService;
    
    /**
     * userListColumnDao
     */
    @Autowired
    private UserListColumnDao userListColumnDao;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
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
     * 服务端业务
     */
    @Autowired
    private ServerManagementService serverService;
    
    /**
     * 封装调用getRrdDataPointByNames所需的Map
     * 2017年9月21日 上午10:27:17
     * @param moduleId
     * @return Map<String, RrdAlgorithm>
     * @exception 
     * @see
     */
    public Map<String, RrdAlgorithm> getRrdMap(Integer moduleId, boolean isDefault) {

        // 列表字段和计算方式Map
        Map<String, RrdAlgorithm> rrdMap = new HashMap<String, RrdAlgorithm>();

        // 列表字段集合
        List<ListColumnBean> listColumnBeans = null;
        if (isDefault) {
            listColumnBeans = userListColumnDao.selectDefListColumnByTypeId(moduleId);
        } else {
            listColumnBeans = userListColumnDao.selectListColumnByTypeId(moduleId);
        }

        // 封装参数rrdMap 作用：调用getRrdDataPointByNames方法
        String calcul = null;
        for (ListColumnBean listColumnBean : listColumnBeans) {
            calcul = listColumnBean.getCalcul();
            if (null != calcul && !"".equals(calcul)) {
                if (calcul.equals("SUM")) {
                    rrdMap.put(listColumnBean.getColumnen(), RrdAlgorithm.SUM);
                } else {
                    rrdMap.put(listColumnBean.getColumnen(), RrdAlgorithm.AVG);
                }
            }
        }
        
        return rrdMap;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.common.service.CommonService#getNpmListRrdData(java.lang.Integer)
     */
    @Override
    public List<Map<String, Object>> getNpmListRrdData(HttpServletRequest request, Integer moduleId,
            Long starttime, Long endtime, Integer watchPointId) {
        //mapList 作用：存储最终返回的数据集合
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        
        // mapList 作用：存储返回的rrd Map集合
        Map<String, RrdAlgorithm> rrdMap = getRrdMap(moduleId, false);
        
        Map<String, Object> resultMap = null;
        BusiKpiService kpiService = null;
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        alarmLogBean.setModuleId(moduleId);
        alarmLogBean.setStarttime(starttime);
        alarmLogBean.setEndtime(endtime);
        alarmLogBean.setHandledflag("N");
        
        //观察点
        if (moduleId == ModuleType.WATCHPOINT.getModuleId()) {
            List<WatchpointBean> watchpoints = watchpointService.getWatchpointInfo(request);
            for (WatchpointBean watchpointBean : watchpoints) {
                alarmLogBean.setBusinessId(watchpointBean.getId());
                kpiService = new BusiKpiService(watchpointBean.getId());
                resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                resultMap.put("ipmCenterId", 1);
                resultMap.put("id", watchpointBean.getId());
                resultMap.put("name", watchpointBean.getName());
                resultMap.put("ip", watchpointBean.getIp());
                resultMap.put("port", watchpointBean.getPort());
                resultMap.put("contacts", watchpointBean.getContacts());
                resultMap.put("telephone", watchpointBean.getTelephone());
                resultMap.put("email", watchpointBean.getEmail());
                resultMap.put("alarmLevel", alarmLogService.getAlarmLogCount(alarmLogBean).get("count"));
                mapList.add(resultMap);
            }
        } else if (null != watchPointId) {
            alarmLogBean.setWatchpointId(watchPointId);
            //业务集合
            List<AppBusinessBean> appBusinesses = appService.getAllAppByModuleId(request, moduleId);
            //遍历业务
            for (AppBusinessBean appBusinessBean : appBusinesses) {
                alarmLogBean.setBusinessId(appBusinessBean.getId());
                if (moduleId == ModuleType.MESSAGE.getModuleId()) {
                    kpiService = new BusiKpiService(watchPointId, appBusinessBean.getId(), true);
                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                } else {
                    kpiService = new BusiKpiService(watchPointId, appBusinessBean.getId());
                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                }
                resultMap.put("id", appBusinessBean.getId());
                resultMap.put("name", appBusinessBean.getName());
                resultMap.put("alarmLevel", alarmLogService.getAlarmLogCount(alarmLogBean).get("count"));
                mapList.add(resultMap);
            }
        }
        
        return mapList;
    }
    
    /**
     * 
     * @Title: getReportWatchpointKpiList
     * @Description: 根据用户编号获取报表KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param userId 用户编号
     * @return List<ReportListBean>
     * @author WWW
     */
    public List<ReportListBean> getReportWatchpointKpiList(long starttime, long endtime, int userId) {
        List<WatchpointBean> list = watchpointService.getWatchpointInfo(1, userId);
        
        return this.getReportWatchpointKpiList(starttime, endtime, list);
    }
    
    /**
     * 
     * @Title: getReportWatchpointKpiList
     * @Description: 根据观察点信息获取报表KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param ids 编号
     * @return List<ReportListBean>
     * @author WWW
     */
    public List<ReportListBean> getReportWatchpointKpiList(long starttime, long endtime, String ids) {
        List<WatchpointBean> list = watchpointService.getWatchpointInfo(ids);
        
        return this.getReportWatchpointKpiList(starttime, endtime, list);
    }
    
    /**
     * 
     * @Title: getOtherModuleKpiList
     * @Description: 根据用户获取其他模块KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param userId 用户编号
     * @return List<ReportListBean>
     * @author WWW
     */
    public List<ReportListBean> getOtherModuleKpiList(long starttime, 
            long endtime, int moduleId, int watchpointId, int userId) {
        List<AppBusinessBean> list = appService.getAllAppByModuleId(1, userId, moduleId);
        
        return this.getOtherModuleKpiList(starttime, endtime, moduleId, watchpointId, list);
    }
    
    /**
     * 
     * @Title: getOtherModuleKpiList
     * @Description: 根据业务编号获取其他模块KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param ids 业务编号
     * @return List<ReportListBean>
     * @author WWW
     */
    public List<ReportListBean> getOtherModuleKpiList(long starttime, 
            long endtime, int moduleId, int watchpointId, String ids) {
        List<AppBusinessBean> list = serverService.getAllServerSide(moduleId, ids);
        
        return this.getOtherModuleKpiList(starttime, endtime, moduleId, watchpointId, list);
    }
    
    /**
     * 
     * @Title: getOtherModuleKpiList
     * @Description: 获取除观察点模块KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param list 业务信息
     * @return List<ReportListBean>
     * @author WWW
     */
    private List<ReportListBean> getOtherModuleKpiList(long starttime, long endtime, 
            int moduleId, int watchpointId, List<AppBusinessBean> list) {
        List<ReportListBean> data = new ArrayList<ReportListBean>();
        if (list != null && list.size() > 0) {
            Map<String, RrdAlgorithm> rrdMap = getRrdMap(moduleId, true);
            BusiKpiService kpiService = null;
            Map<String, Object> resultMap = null;
            AlarmLogBean alarmLogBean = new AlarmLogBean();
            alarmLogBean.setModuleId(moduleId);
            alarmLogBean.setStarttime(starttime);
            alarmLogBean.setEndtime(endtime);
            alarmLogBean.setWatchpointId(watchpointId);
            
            ReportListBean bean = null;
            Map<String, Long> alarmMap = null;
            for (AppBusinessBean appBean : list) {
                bean = new ReportListBean();
                alarmLogBean.setBusinessId(appBean.getId());
                alarmMap = alarmLogService.getAlarmLogCount(alarmLogBean);
                if (moduleId == ModuleType.MESSAGE.getModuleId()) {
                    kpiService = new BusiKpiService(watchpointId, appBean.getId(), true);
                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                } else {
                    kpiService = new BusiKpiService(watchpointId, appBean.getId());
                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                }
                bean.setId(appBean.getId());
                bean.setName(appBean.getName());
                bean.setAlarmNum(alarmMap.get("count"));
                if (resultMap != null) {
                    if (moduleId == ModuleType.CLIENT.getModuleId()) {
                        bean.setQosOrDelay((double) resultMap.get("serverDurDelay"));
                    } else {
                        bean.setQosOrDelay((double) resultMap.get("qos"));
                    }
                    bean.setEthernetTraffic((double) resultMap.get("ethernetTraffic"));
                    bean.setSynPkts((double) resultMap.get("sessionNum"));
                    bean.setNetPktLostRatio((double) resultMap.get("netPktLostRatio"));
                    bean.setBandWidthRatio((double) resultMap.get("bandWidthRatio"));
                }
                data.add(bean);
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getReportWatchpointKpiList
     * @Description: 获取观察点报点KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param list 观察点 信息
     * @return List<ReportListBean>
     * @author WWW
     */
    private List<ReportListBean> getReportWatchpointKpiList(long starttime, long endtime, List<WatchpointBean> list) {
        List<ReportListBean> data = new ArrayList<ReportListBean>();
        if (list != null && list.size() > 0) {
            int moduleId = 10;
            Map<String, RrdAlgorithm> rrdMap = getRrdMap(moduleId, true);
            BusiKpiService kpiService = null;
            Map<String, Object> resultMap = null;
            AlarmLogBean alarmLogBean = new AlarmLogBean();
            alarmLogBean.setModuleId(moduleId);
            alarmLogBean.setStarttime(starttime);
            alarmLogBean.setEndtime(endtime);
            
            ReportListBean bean = null;
            Map<String, Long> alarmMap = null;
            for (WatchpointBean watchpointBean : list) {
                bean = new ReportListBean();
                alarmLogBean.setBusinessId(watchpointBean.getId());
                alarmMap = alarmLogService.getAlarmLogCount(alarmLogBean);
                kpiService = new BusiKpiService(watchpointBean.getId());
                resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                bean.setId(watchpointBean.getId());
                bean.setName(watchpointBean.getName());
                bean.setAlarmNum(alarmMap.get("count"));
                if (resultMap != null) {
                    bean.setQosOrDelay((double) resultMap.get("qos"));
                    bean.setEthernetTraffic((double) resultMap.get("ethernetTraffic"));
                    bean.setSynPkts((double) resultMap.get("sessionNum"));
                    bean.setNetPktLostRatio((double) resultMap.get("netPktLostRatio"));
                    bean.setBandWidthRatio((double) resultMap.get("bandWidthRatio"));
                }
                data.add(bean);
            }
        }
        
        return data;
    } 
    
    /* (non-Javadoc)
     * @see com.protocolsoft.common.service.CommonService#getNpmListRrdDataBySubPath(java.lang.Integer)
     */
    @Override
    public List<Map<String, Object>> getNpmListRrdDataBySubPath(HttpServletRequest request, Integer moduleId,
            Long starttime, Long endtime, Integer watchPointId, Integer appBusinessId) {
        if (moduleId == ModuleType.SERVER.getModuleId()){
            moduleId = ModuleType.CLIENT.getModuleId();
        } else {
            moduleId = ModuleType.SERVER.getModuleId();
        }
        
        //mapList 作用：存储返回的rrd Map集合
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        
        // mapList 作用：存储返回的rrd Map集合
        Map<String, RrdAlgorithm> rrdMap = getRrdMap(moduleId, false);
        
        //查询当前id对应的客户端或者服务端
        AppBusinessBean appBusinessBean = appBusinessDao.selectAppBusiness(appBusinessId);

        Map<String, Object> resultMap = null;
        BusiKpiService kpiService = null;
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        alarmLogBean.setWatchpointId(watchPointId);
        alarmLogBean.setModuleId(moduleId);
        alarmLogBean.setStarttime(starttime);
        alarmLogBean.setEndtime(endtime);
        alarmLogBean.setHandledflag("N");
        
        List<AppBusinessBean> appBusinessServerSides = appService.getAllAppByModuleId(request, moduleId);
        for (AppBusinessBean appBusinessServerSide : appBusinessServerSides) {
            alarmLogBean.setBusinessId(appBusinessServerSide.getId());
            kpiService = new BusiKpiService(watchPointId, appBusinessBean.getId(), appBusinessServerSide.getId());
            resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
            //封装结果
            resultMap.put("id", appBusinessServerSide.getId());
            resultMap.put("name", appBusinessServerSide.getName());
            resultMap.put("alarmLevel", alarmLogService.getAlarmLogCount(alarmLogBean).get("count"));
            mapList.add(resultMap);
        }
        
        return mapList;
    }
    
    /**
     * 
     * @Title: getHeatmap
     * @Description: 获取热度图数据 
     * @param request 请求
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param watchpointId 观察点
     * @param plotId 绘图编号
     * @return Heatmap 
     * @author www
     */
    @Override
    public Heatmap getHeatmap(HttpServletRequest request, long starttime, 
            long endtime, int watchpointId, int plotId) {
        Heatmap heatmap = new Heatmap();
        if (0 == starttime && 0 == endtime) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime();
            starttime = timeDefaultBean.getStarttime();
            endtime = timeDefaultBean.getEndtime();
        }
        List<AppBusinessBean> clientList =  appService.getAllAppByModuleId(request, 
                ModuleType.CLIENT.getModuleId());
        List<AppBusinessBean> serverList = appService.getAllAppByModuleId(request, 
                ModuleType.SERVER.getModuleId());
        Collections.reverse(serverList);
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
        RrdAlgorithm alg = RrdAlgorithm.AVG;
        HeatmapBlock block = null;
        BusiKpiService kpiService = null;
        SimpleEntry<Long, Double> entry = null;
        int clientId = 0;
        int serverId = 0;
        int ilen = clientList.size();
        int jlen = serverList.size();
        List<HeatmapBlock> list = new ArrayList<HeatmapBlock>();
        String[] xLabel = new String[ilen];
        String[] yLabel = new String[jlen];
        if ("SUM".equals(plotBean.getCalcul())) {
            alg = RrdAlgorithm.SUM;
        }
        for (int i = 0; i < ilen; i ++) {
            clientId = clientList.get(i).getId();
            xLabel[i] = clientList.get(i).getName();
            for (int j = 0; j < jlen; j ++) {
                if (i == 0) {
                    yLabel[j] = serverList.get(j).getName();
                }
                serverId = serverList.get(j).getId();
                kpiService = new BusiKpiService(watchpointId, clientId, serverId);
                entry = kpiService.getRrdDataPointByName(starttime, endtime, kpisBean.getName(), alg);
                block = new HeatmapBlock();
                block.setX(i);
                block.setY(j);
                block.setValue(entry.getValue());
                block.setClientId(clientId);
                block.setServerId(serverId);
                list.add(block);
            }
        }
        
        heatmap.setStarttime(starttime);
        heatmap.setEndtime(endtime);
        heatmap.setType("heatmap");
        heatmap.setPlotName("全局热力图");
        heatmap.setUnit(kpisBean.getUnit());
        heatmap.setxLabel(xLabel);
        heatmap.setyLabel(yLabel);
        heatmap.setData(list);
        heatmap.setGlobal(true);
        
        return heatmap;
    }
}
