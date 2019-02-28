/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ClientServiceImpl
 *创建人:long    创建时间:2017年9月8日
 */
package com.protocolsoft.subnet.service;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.service.ProtoPlanService;
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
 * ClientServiceImpl
 * 2017年9月8日 上午11:26:59
 * @author long
 * @version
 * @see
 */
@Service
public class ClientService {
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
    /**
     * 服务端业务
     */
    @Autowired
    private ServerManagementService serverService;
    
    /**
     * appBusinessDao注入
     */
    @Autowired(required=false)
    private AppBusinessDao appBusinessDao;
    
    /**
     * plotService注入
     */
    @Autowired(required=false)
    private PlotService plotService;
    
    /**
     * alarmSetService注入
     */
    @Autowired(required=false)
    private AlarmSetService alarmSetService;
    
    /**
     * alarmLogService注入
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 协议表
     */
    @Autowired(required = false)
    private ProtoPlanService protoPlanService;
    
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
     * ipm_app_ip_port表Dao
     */
    @Autowired
    private AppIpPortDao appIpPortDao;
    
    /**
     * 
     * @Title: addClient
     * @Description: 添加客户端
     * @param request 请求 
     * @param appBusinessBean 接收添加客户端参数
     * @return Map<String, Integer>
     * @author www
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> addClient(HttpServletRequest request, AppBusinessBean appBusinessBean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        //添加到统一管理业务表中
        appBusinessDao.insertAppBusiness(appBusinessBean);
        
        int state = appBusinessService.addBusiIpPort(appBusinessBean, false);
        if (state == 1) {
            try {
                boolean bool = appBusinessService.appBusiCommonProcess(appBusinessBean, request);
                if (bool) {
                    map.put("state", 1);
                } else {
                    map.put("state", 0);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            } catch (IOException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            map.put("state", state);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return map;
    }

    /**
     * 
     * @Title: delClientByAppId
     * @Description: 删除客户端
     * @param request 请求
     * @param id 业务id
     * @return Map<String, Integer>
     * @author www
     */
    public Map<String, Integer> delClientByAppId(HttpServletRequest request, int id) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        
        //删除告警中的这个id的客户端信息
        alarmSetService.delAppBusinessAfter(0, 11, id);
        
        //根据id查询预删除的客户端信息，用于添加日志
        AppBusinessBean bean = appBusinessDao.selectAppBusiness(id);
        
        //删除统一管理表中的这个id的客户端信息
        appBusinessDao.deleteAppBusiness(id);
        
        //删除从表
        appIpPortDao.deleteAppIpPort(id);
        
        //删除这个id的客户端配置
        JsonFileUtil.getInstance().dleJsonFile(ModuleType.CLIENT, id);

        // 删除用户权限
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        jurisService.deleteUserAuthorize(userBean.getId(), id, 11);
        reportService.deleteTimerDetailByAppId(id, 11);
        
        appBusinessService.appBusiLogs(bean, request, ServiceRuleType.DELETE);
        map.put("state", 1);
        
        return map;
    }

    /**
     * 
     * @Title: updateClient
     * @Description: 更新客户端
     * @param request 请求
     * @param appBusinessBean 接收修改客户端参数
     * @return Map<String, Integer>
     * @author www
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> updateClient(HttpServletRequest request, AppBusinessBean appBusinessBean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        //修改统一管理表中的客户端信息
        appBusinessDao.updateAppBusiness(appBusinessBean);
        
        // 添加IP端口
        int state = appBusinessService.addBusiIpPort(appBusinessBean, true);
        if (state == 1) {
            // 添加日志
            appBusinessService.appBusiLogs(appBusinessBean, request, ServiceRuleType.UPDATE);
            map.put("state", 1);
        } else {
            map.put("state", state);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
        return map;
    }

    /**
     * 
     * @Title: getClient
     * @Description: 获取所有客户端
     * @return List<AppBusinessBean>
     * @author www
     */
    public List<AppBusinessBean> getClient() {
        List<AppBusinessBean> list = appBusinessDao.selectAppBusinessesByModuleId(11);

        return list;
    }

    /**
     * 
     * @Title: getClientGraphical
     * @Description: 绘图
     * @param drawingOptionsBean
     * @return Map<String,Object>
     * @author www
     */
    public Map<String, Object> getClientGraphical(DrawingOptionsBean drawingOptionsBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        long start = 0;
        long end = 0;
        int plotId = drawingOptionsBean.getPlotId();
        if (drawingOptionsBean.getStarttime() == null) {
            TimeDefaultBean time = DateAppsUtils.getGraphDefaultTime();
            end = time.getEndtime();
            if (plotId == 309) { // 健康图默认一个小时
                start = end - 3600;
            } else {
                start = time.getStarttime();
            }
        } else {
            start = drawingOptionsBean.getStarttime();
            end = drawingOptionsBean.getEndtime();
        }
        int watchpointId = drawingOptionsBean.getWatchpointId();
        int busiId = drawingOptionsBean.getClientId();
        WatchpointBean bean = watchpointService.getWatchpointById(watchpointId);
        AppBusinessBean appBean = appBusinessDao.selectAppBusiness(busiId);
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
        PlotTypeBean plotTypeBean = plotService.getPlotTypeById(drawingOptionsBean.getPlotTypeId());
        BusiKpiService busiKpiService = null;
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = null;
        busiKpiService = new BusiKpiService(watchpointId, busiId);
        if (plotId == 89) { // 公有协议分布
            List<SimpleEntry<String, Double>> list = busiKpiService.
                    getPublicProtoRrdData(start, end, kpisBean.getName());
            SimpleEntry<String, Double> entry = null;
            ProtoPlanBean protoPlanBean = new ProtoPlanBean();
            int protoPlanId = 0;
            for (int i = 0, j = list.size(); i < j; i++) {
                entry = list.get(i);
                protoPlanBean.setName(entry.getKey());
                protoPlanId = protoPlanService.getProtoPlanBeanByName(protoPlanBean).getId();
                dataMap = new HashMap<String, Object>();
                dataMap.put("id", protoPlanId);
                dataMap.put("name", entry.getKey());
                dataMap.put("value", entry.getValue());
                dataList.add(dataMap);
            }
            dataList = sortList(dataList);
            
            // 未识别共有协议
            int unknowId = 11106;
            int unknowKpiId = 34;
            kpisBean = plotService.getKpisById(unknowKpiId);
            double unknowPublic = busiKpiService.getRrdDataPointByName(start, 
                    end, kpisBean.getName(), RrdAlgorithm.SUM).getValue();
            dataMap = new HashMap<String, Object>();
            dataMap.put("id", unknowId);
            dataMap.put("name", kpisBean.getDisplayName());
            dataMap.put("value", unknowPublic);
            dataList.add(dataMap);
        } else if (plotId == 59) { // 用户协议分布(所有定义的服务端)
            List<AppBusinessBean> list = appBusinessDao.selectAppBusinessesByModuleId(12);
            SimpleEntry<String, Double> entry = null;
            for (AppBusinessBean serverBean : list) {
                entry = busiKpiService.getUserProtoRrdData(start, end, 
                    "USER" + serverBean.getId(), kpisBean.getName());
                if (entry != null) {
                    dataMap = new HashMap<String, Object>();
                    dataMap.put("id", serverBean.getId());
                    dataMap.put("name", serverBean.getName() + "流量");
                    dataMap.put("value", entry.getValue());
                    dataList.add(dataMap);
                }
            }
            
            // 未识别用户协议
            int unknowId = 0; 
            int unknowKpiId = 33;
            kpisBean = plotService.getKpisById(unknowKpiId);
            double unknowUser = busiKpiService.getRrdDataPointByName(start, 
                    end, kpisBean.getName(), RrdAlgorithm.SUM).getValue();
            dataMap = new HashMap<String, Object>();
            dataMap.put("id", unknowId);
            dataMap.put("name", kpisBean.getDisplayName());
            dataMap.put("value", unknowUser);
            dataList.add(dataMap);
        } else if (plotId == 302) { // 包大小分布
            Map<String, String> namezh = PlotService.pktsNamezh;
            List<SimpleEntry<String, Double>> list = busiKpiService.getPktsSizeDistri(start, end, kpisBean.getName());
            SimpleEntry<String, Double> entry = null;
            ProtoPlanBean protoPlanBean = new ProtoPlanBean();
            for (int i = 0, j = list.size(); i < j; i++) {
                entry = list.get(i);
                protoPlanBean.setName(entry.getKey());
                dataMap = new HashMap<String, Object>();
                dataMap.put("type", entry.getKey());
                dataMap.put("name", namezh.get(entry.getKey()));
                dataMap.put("value", entry.getValue());
                dataList.add(dataMap);
            }
        } else if (plotId == 309) { // 健康图
            kpisBean = new KpisBean();
            plotService.getHeatmap(start, end, 11, watchpointId, busiId, map, kpisBean);
        } else {
            List<SimpleEntry<Long, Double>> avg = null;
            avg = busiKpiService.getRrdDataByName(start, end, 0, kpisBean.getName(), RrdAlgorithm.AVG);
            if (avg != null) {
                dataMap = new HashMap<String, Object>();
                dataMap.put("name", plotBean.getName());
                dataMap.put("value", avg);
                dataList.add(dataMap);
            }
        }
        map.put("starttime", start);
        map.put("endtime", end);
        map.put("type", plotTypeBean.getName());
        map.put("plotName", bean.getName() + "-" + appBean.getName() + "-" + plotBean.getName());
        map.put("unit", kpisBean.getUnit());
        if (!map.containsKey("data")) {
            map.put("data", dataList);
        }
        
        if ("line".equals(kpisBean.getUnit())) { // 线图展示基线
            ParamBean paramBean = new ParamBean();
            paramBean.setStarttime(start);
            paramBean.setEndtime(end);
            paramBean.setModuleId(11);
            paramBean.setBusiId(busiId);
            paramBean.setWatchpointId(watchpointId);

            PlotSimpleBean simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.HIGH);
            if (simpleBean != null) {
                dataMap = new HashMap<String, Object>();
                dataMap.put("value", simpleBean.getValue());
                dataMap.put("name", simpleBean.getName());
                dataMap.put("type", simpleBean.getType());
                dataList.add(dataMap);
            }
            
            simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.LOW);
            if (simpleBean != null) {    
                dataMap = new HashMap<String, Object>();
                dataMap.put("value", simpleBean.getValue());
                dataMap.put("name", simpleBean.getName());
                dataMap.put("type", simpleBean.getType());
                dataList.add(dataMap);
            }
        }
        
        return map;
    }
    
    /**
     * 
     * @Title: sortList
     * @Description: 排序
     * @param dataList
     * @return List<Map<String,Object>>
     * @author www
     */
    public List<Map<String, Object>> sortList(List<Map<String, Object>> dataList) { 
        Collections.sort(dataList, new Comparator<Map<String, Object>>() {  
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
                int i = (int) (Double.parseDouble(o2.get("value").toString())
                        - Double.parseDouble(o1.get("value").toString()));  
                return i;  
            }  
        });
        int dataSize = dataList.size() > 10 ? 10 : dataList.size();
        List<Map<String, Object>> data = dataList.subList(0, dataSize); 
        // 其他
        if (dataList.size() > 10) {
            StringBuilder ids = new StringBuilder(dataList.get(0).get("id").toString());
            double val = 0;
            for (int i = 1, len = dataList.size(); i < len; i ++) {
                if (i < 10) {
                    ids.append(",");
                    ids.append(dataList.get(i).get("id").toString());
                } else {
                    val += Double.parseDouble(dataList.get(i).get("value").toString());
                }
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("moduleId", 12);
            dataMap.put("id", ids.toString());
            dataMap.put("name", "其他");
            dataMap.put("value", val);
            data.add(dataMap);
        }
        
        return data;
    }

    /**
     * 
     * @Title: getClientSingleValueData
     * @Description: 小列表
     * @param drawingOptionsBean
     * @return Map<String,Object>
     * @author www
     */
    public Map<String, Object> getClientSingleValueData(DrawingOptionsBean drawingOptionsBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        TimeDefaultBean timeBean=DateAppsUtils.getRrdDefaultTime();
        long end=timeBean.getEndtime();
        long start=timeBean.getStarttime();
        boolean isCalcul=false;
        if (drawingOptionsBean.getStarttime()!=null && drawingOptionsBean.getStarttime()>0){
            start=drawingOptionsBean.getStarttime();
            end=drawingOptionsBean.getEndtime();
            isCalcul=true;
        }
        BusiKpiService busiKpiService=null;
        SimpleEntry<Long, Double> data=null;
        List<Map<String, Object>>dataList=new ArrayList<Map<String, Object>>();
        Map<String, Object>dataMap=null;
        String plotIds=drawingOptionsBean.getPlotIds();
        if (plotIds!=null && !"".equals(plotIds.trim())){
            String[]plotList=plotIds.split(",");
            busiKpiService = new BusiKpiService(drawingOptionsBean.getWatchpointId(), drawingOptionsBean.getClientId());
            for (String plot:plotList){
                PlotOptionBean plotBean = plotService.getPlotOptionById(Integer.parseInt(plot));
                if (Integer.parseInt(plot)==87){
                    AlarmLogBean alarmLogBean=new AlarmLogBean();
                    alarmLogBean.setWatchpointId(drawingOptionsBean.getWatchpointId());
                    alarmLogBean.setModuleId(11);
                    alarmLogBean.setBusinessId(drawingOptionsBean.getClientId());
                    alarmLogBean.setHandledflag("N");
                    if (isCalcul){
                        alarmLogBean.setStarttime(start);
                        alarmLogBean.setEndtime(end);
                    }
                    Map<String, Long> countMap=alarmLogService.getAlarmLogCount(alarmLogBean);
                    dataMap=new HashMap<String, Object>();
                    dataMap.put("plotName", plotBean.getName());
                    dataMap.put("unit", "num");
                    dataMap.put("value", countMap.get("count"));
                    dataMap.put("starttime", countMap.get("starttime"));
                    dataMap.put("endtime", countMap.get("endtime"));
                    dataMap.put("alarmLevelId", countMap.get("alarmLevelId"));
                    dataList.add(dataMap);
                } else {
                    KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
                    RrdAlgorithm rrdAlgorithm=RrdAlgorithm.AVG;
                    if (isCalcul){
                        String calcul=plotBean.getCalcul();
                        if (calcul!=null && !"".equals(calcul.trim()) && "SUM".equals(calcul)){
                            rrdAlgorithm=RrdAlgorithm.SUM;
                        }
                    }
                    data=busiKpiService.getRrdDataPointByName(start, end, kpisBean.getName(), rrdAlgorithm);
                    if (data!=null){
                        dataMap=new HashMap<String, Object>();
                        dataMap.put("plotName", plotBean.getName());
                        dataMap.put("unit", kpisBean.getUnit());
                        dataMap.put("value", data.getValue());
                        dataList.add(dataMap);
                    }
                }
            }
        }
        map.put("starttime", start);
        map.put("endtime", end);
        map.put("watchPointId", drawingOptionsBean.getWatchpointId());
        map.put("clientId", drawingOptionsBean.getClientId());
        map.put("data", dataList);
        return map;
    }

    /**
     * @Title: getClient
     * @Description: 获取用户端信息
     * @param request
     * @return List<AppBusinessBean>
     * @author www
     */
    public List<AppBusinessBean> getClient(HttpServletRequest request) {
        List<AppBusinessBean> beans = null;
        int moduleId = 11;
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

}
