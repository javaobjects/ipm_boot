/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmBusTagServiceImpl
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.service.impl;


import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;
import com.protocolsoft.app.bean.HeatmapDataBean;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.app.bean.PointDataBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.app.bean.TableParamBean;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.app.service.AppTopService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.HeatSimpleBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.enumeration.ServiceRuleType;
import com.protocolsoft.common.service.impl.AppBusinessService;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.depthanaly.bean.BusTagListBean;
import com.protocolsoft.depthanaly.bean.DeptMsgLogBean;
import com.protocolsoft.depthanaly.bean.IpmBusTagBean;
import com.protocolsoft.depthanaly.bean.IpmMsgFixedBean;
import com.protocolsoft.depthanaly.dao.IpmBusTagDao;
import com.protocolsoft.depthanaly.dao.IpmMsgFixedDao;
import com.protocolsoft.depthanaly.service.IpmBusTagService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.AuthorizeJurisDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * 
 * @ClassName: IpmBusTagServiceImpl
 * @Description: 报文交易实现层
 * @author wangjianmin
 *
 */
@Service
public class IpmBusTagServiceImpl implements IpmBusTagService{
    
    /**
     * AppBusinessDao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 告警阈值设置  service
     */
    @Autowired
    private AlarmSetService alarmSetService;
    /**
     * AppService
     */
    @Autowired
    private AppService appService;
    
    /**
     * IpmMsgFixedDao
     */
    @Autowired
    private IpmMsgFixedDao ipmMsgFixedDao;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * IpmBusTagDao
     */
    @Autowired
    private IpmBusTagDao ipmBusTagDao;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
    /**
     * 绘图service
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * TOP业务
     */
    @Autowired
    private AppTopService topService;
    
    /**
     * Center
     */
    @Autowired
    private CenterIpService centerService;
    
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
     * 权限保存
     */
    @Autowired
    private AuthorizeJurisDao authorizeJurisDao;
    
    /**
     * 业务信息表
     */
    @Autowired
    private AppBusinessService appBusinessService;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Integer> addBusTag(HttpServletRequest request, BusTagListBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        
        //ipm_app_business 实体
        AppBusinessBean appBusBean = new  AppBusinessBean();
        //ipm_msg_fixed 实体
        IpmMsgFixedBean msgBean = new IpmMsgFixedBean();
        //ipm_bus_tags 实体
        IpmBusTagBean busBean = new IpmBusTagBean();
        
        for(int i = 0; i < bean.getSet().size(); i++){
            appBusBean = new  AppBusinessBean();
            msgBean = new IpmMsgFixedBean();
            busBean = new IpmBusTagBean();
           
            appBusBean.setModuleId(9);
            appBusBean.setName(bean.getSet().get(i).getName());
            appBusBean.setDisplayIp(bean.getSet().get(i).getDisplayIp());
            //添加到 ipm_app_business
            appBusinessDao.insertAppBusiness(appBusBean);
                       
            appBusinessService.addBusiIpPort(appBusBean, false);
            
            msgBean.setAppId(appBusBean.getId());
            msgBean.setClientIpport(bean.getSet().get(i).getClientIpport());
            msgBean.setStatusCode(bean.getSet().get(i).getStatusCode());
            msgBean.setSuccessCode(bean.getSet().get(i).getSuccessCode());
            msgBean.setFailedCode(bean.getSet().get(i).getFailedCode());
            
            //添加到 ipm_msg_fixed
            ipmMsgFixedDao.addMsgFixed(msgBean);
            
            busBean.setAppId(appBusBean.getId());
            busBean.setBusTag0(bean.getSet().get(i).getBusTag0());
            busBean.setBusTag1(bean.getSet().get(i).getBusTag1());
            busBean.setBusTag2(bean.getSet().get(i).getBusTag2());
            busBean.setBusTag3(bean.getSet().get(i).getBusTag3());
            busBean.setBusTag4(bean.getSet().get(i).getBusTag4());
            busBean.setBusTag5(bean.getSet().get(i).getBusTag5());
            busBean.setBusTag6(bean.getSet().get(i).getBusTag6());
            busBean.setBusTag7(bean.getSet().get(i).getBusTag7());
            busBean.setBusTag8(bean.getSet().get(i).getBusTag8());
            busBean.setBusTag9(bean.getSet().get(i).getBusTag9());
            busBean.setBusTag10(bean.getSet().get(i).getBusTag10());
            busBean.setBusTag11(bean.getSet().get(i).getBusTag11());
            busBean.setBusTag12(bean.getSet().get(i).getBusTag12());
            busBean.setBusTag13(bean.getSet().get(i).getBusTag13());
            busBean.setBusTag14(bean.getSet().get(i).getBusTag14());
            busBean.setBusTag15(bean.getSet().get(i).getBusTag15());
            busBean.setBusTag16(bean.getSet().get(i).getBusTag16());
            busBean.setBusTag17(bean.getSet().get(i).getBusTag17());
            busBean.setBusTag18(bean.getSet().get(i).getBusTag18());
            busBean.setBusTag19(bean.getSet().get(i).getBusTag19());
            //添加到 ipm_bus_tags
            boolean bool = ipmBusTagDao.addBusTag(busBean);
            
            try {
                bool = appBusinessService.appBusiCommonProcess(appBusBean, request);
                if (bool) {
                    map.put("state", 1);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            } catch (IOException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        
        return map;
    }

    @Override
    public Map<String, Integer> deleteBusTag(HttpServletRequest request, int appId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        //根据id 得到预删除的 报文业务，用于添加日志
        AppBusinessBean bean = appBusinessDao.selectAppBusiness(appId);
        
        appBusinessDao.deleteAppBusiness(appId);
        ipmMsgFixedDao.deleteMsgFixed(appId);
        ipmBusTagDao.deleteAppIpPort(appId);
        alarmSetService.delAppBusinessAfter(0, 9, appId);
        AuthorizeJurisBean userAuthorizeBean =new AuthorizeJurisBean();
        userAuthorizeBean.setAppId(appId);
        authorizeJurisDao.deleteUserAuthorize(userAuthorizeBean);
        ipmBusTagDao.deleteBusTag(appId);
        //删除观察点设置
        JsonFileUtil jfu = JsonFileUtil.getInstance();
        try {
            jfu.dleJsonFile(ModuleType.MESSAGE, appId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(14);
        logsBean.setMsg("删除"+bean.getName()+"报文服务");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        map.put("state", 1);
        
        return map;
    }

    @Override
    public List<IpmBusTagBean> selectAll() {
        return ipmBusTagDao.selectAll();
    }
    
    @Override
    public List<IpmBusTagBean> selectAllByIds(String ids) {
        List<IpmBusTagBean> list = null;
        if (ids != null) {
            list = ipmBusTagDao.selectAllByIds(ids);
        } else {
            list = new ArrayList<IpmBusTagBean>();
        }
        
        return list;
    }
    
    @Override
    public List<IpmBusTagBean> selectAll(HttpServletRequest request) {
        List<IpmBusTagBean> beans = null;
        int moduleId = 9;
        String ids = null;
        String remoteId = request.getParameter("remoteId");
        if (remoteId == null) { // 本地请求
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            if (userBean.getRoleId() == 1) {
                beans = this.selectAll();
            } else {
                ids = jurisService.selectModuleRole(userBean.getId(), moduleId, 1);
                beans = this.selectAllByIds(ids);
            }
        } else if(CenterIpService.REMOTEID.equals(remoteId)) {
            ids = request.getParameter("remoteBusiIds"); 
            if (ids == null || ids.equals("")) {
                beans = this.selectAll();
            } else {
                beans = this.selectAllByIds(ids);
            }
        }
        
        return beans;
    }

    @Override
    public Map<String, Integer> updateBusTag(HttpServletRequest request, BusTagListBean bean) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("state", 0);
        //ipm_app_business 实体
        AppBusinessBean appBusBean = new  AppBusinessBean();
        //ipm_msg_fixed 实体
        IpmMsgFixedBean msgBean = new IpmMsgFixedBean();
        //ipm_bus_tags 实体
        IpmBusTagBean busBean = new IpmBusTagBean();
      
        for(int i = 0; i < bean.getSet().size(); i++){
            msgBean = new IpmMsgFixedBean();
            busBean = new IpmBusTagBean();
            
            appBusBean = new  AppBusinessBean();
            appBusBean.setName(bean.getSet().get(i).getName());
            appBusBean.setId(bean.getSet().get(i).getAppId());
            appBusBean.setDisplayIp(bean.getSet().get(i).getDisplayIp());
            //修改 ipm_app_business
            appBusinessDao.updateAppBusiness(appBusBean);
            
            appBusinessService.addBusiIpPort(appBusBean, true);
            
            msgBean.setAppId(bean.getSet().get(i).getAppId());
            msgBean.setClientIpport(bean.getSet().get(i).getClientIpport());
            msgBean.setStatusCode(bean.getSet().get(i).getStatusCode());
            msgBean.setSuccessCode(bean.getSet().get(i).getSuccessCode());
            msgBean.setFailedCode(bean.getSet().get(i).getFailedCode());
            //修改  ipm_msg_fixed
            ipmMsgFixedDao.updataMsgFixed(msgBean);
            
            busBean.setAppId(bean.getSet().get(i).getAppId());
            busBean.setBusTag0(bean.getSet().get(i).getBusTag0());
            busBean.setBusTag1(bean.getSet().get(i).getBusTag1());
            busBean.setBusTag2(bean.getSet().get(i).getBusTag2());
            busBean.setBusTag3(bean.getSet().get(i).getBusTag3());
            busBean.setBusTag4(bean.getSet().get(i).getBusTag4());
            busBean.setBusTag5(bean.getSet().get(i).getBusTag5());
            busBean.setBusTag6(bean.getSet().get(i).getBusTag6());
            busBean.setBusTag7(bean.getSet().get(i).getBusTag7());
            busBean.setBusTag8(bean.getSet().get(i).getBusTag8());
            busBean.setBusTag9(bean.getSet().get(i).getBusTag9());
            busBean.setBusTag10(bean.getSet().get(i).getBusTag10());
            busBean.setBusTag11(bean.getSet().get(i).getBusTag11());
            busBean.setBusTag12(bean.getSet().get(i).getBusTag12());
            busBean.setBusTag13(bean.getSet().get(i).getBusTag13());
            busBean.setBusTag14(bean.getSet().get(i).getBusTag14());
            busBean.setBusTag15(bean.getSet().get(i).getBusTag15());
            busBean.setBusTag16(bean.getSet().get(i).getBusTag16());
            busBean.setBusTag17(bean.getSet().get(i).getBusTag17());
            busBean.setBusTag18(bean.getSet().get(i).getBusTag18());
            busBean.setBusTag19(bean.getSet().get(i).getBusTag19());
            //添加到 ipm_bus_tags
            ipmBusTagDao.updateBusTag(busBean);
            // 添加日志
            appBusinessService.appBusiLogs(appBusBean, request, ServiceRuleType.UPDATE);
        }
        map.put("state", 1);
        
        return map;
    }

    @Override
    public PlotDataBean getPlotData(PlotParamBean bean) {
    	int plotId = bean.getPlotId();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime60();
            if(plotId == 316) {
            	 bean.setStarttime(timeDefaultBean.getEndtime() - 3600);
            }else {
            	 bean.setStarttime(timeDefaultBean.getStarttime());
            }
            bean.setEndtime(timeDefaultBean.getEndtime());
        }
        
        PlotDataBean plotDataBean = new PlotDataBean();
        WatchpointBean wpBean = watchpointService.getWatchpointById(bean.getWatchpointId()); 
        AppBusinessBean appBean = appBusinessDao.selectAppBusiness(bean.getBusiId());
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        PlotTypeBean plotTypeBean = plotService.getPlotTypeById(bean.getPlotTypeId());
        List<PlotSimpleBean> data = null;
        if (plotId == 306 || plotId == 307){   //判断柱图 307 = 交易响应率      306= 交易成功率
            AppTopParamsBean appTopParamsBean = new AppTopParamsBean();
            appTopParamsBean.setModuleId(bean.getModuleId());
            appTopParamsBean.setStarttime(bean.getStarttime());
            appTopParamsBean.setEndtime(bean.getEndtime());
            appTopParamsBean.setWatchpointId(bean.getWatchpointId());
            appTopParamsBean.setBusiId(bean.getBusiId());
            
            AppTopConfigBean appTopConfigBean = topService.getAppTopConfigById(bean.getModuleId(), plotId);
            appTopParamsBean.setBean(appTopConfigBean);
            KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
            BusiKpiService kpiService = new BusiKpiService(bean.getWatchpointId(), bean.getBusiId(), true);
            List<SimpleEntry<Long, Double>> kpiData = kpiService.getRrdDataByName(bean.getStarttime(),
                    bean.getEndtime(), 60, kpisBean.getName(), RrdAlgorithm.AVG);
            data = new ArrayList<PlotSimpleBean>();
            if(kpiData != null){
                for(int i = 0; i < kpiData.size(); i++){
                    PlotSimpleBean plotSimpleBean = new PlotSimpleBean();
                    plotSimpleBean.setName(kpiData.get(i).getKey().toString());
                    plotSimpleBean.setValue(kpiData.get(i).getValue());
                    data.add(plotSimpleBean);
                }   
            }
            plotDataBean.setUnit(kpisBean.getUnit());
            plotDataBean.setData(data);
        } else if(plotId == 316) {
        	HeatmapDataBean hmbean = new HeatmapDataBean();
        	KpisBean kpisBean = new KpisBean();
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
            KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
            BusiKpiService kpiService = new BusiKpiService(bean.getWatchpointId(), bean.getBusiId(), true);
            List<SimpleEntry<Long, Double>> kpiData = kpiService.getRrdDataByName(bean.getStarttime(),
                    bean.getEndtime(), 60, kpisBean.getName(), RrdAlgorithm.AVG);
            
            data = new ArrayList<PlotSimpleBean>();
            PlotSimpleBean plotSimpleBean = new PlotSimpleBean();
            plotSimpleBean.setName(kpisBean.getDisplayName());
            plotSimpleBean.setValue(kpiData);
            data.add(plotSimpleBean);
            plotDataBean.setUnit(kpisBean.getUnit());
            
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
            plotDataBean.setData(data);
        }
        
        plotDataBean.setStarttime(bean.getStarttime());
        plotDataBean.setEndtime(bean.getEndtime());
        plotDataBean.setPlotName(wpBean.getName() + "-" + appBean.getName() + "-" + plotBean.getName());
        plotDataBean.setType(plotTypeBean.getName());

        return plotDataBean;
    }
    
    @Override
    public SimpleDataBean getSimpleData(SimpleParamBean bean) {
        boolean flag = true; // 标志是否为回溯
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime60();
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
                if (plotId ==303) { // 未响应告警数量
                    pointDataBean=appService.getAlarmSimpleData(bean, plotBean, flag);
                } else {
                    pointDataBean = new PointDataBean();
                    kpiService = new BusiKpiService(bean.getWatchpointId(), bean.getBusiId(), true);
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

    @Override
    public List<DeptMsgLogBean> getData(DeptMsgLogBean bean) {
        TableParamBean tableBean =new TableParamBean();
        List<DeptMsgLogBean> listBean =new ArrayList<DeptMsgLogBean>();
        DeptMsgLogBean  logbean = new DeptMsgLogBean();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime60();
            tableBean.setStarttime(timeDefaultBean.getStarttime());
            tableBean.setEndtime(timeDefaultBean.getEndtime());
        }else{
            tableBean.setStarttime(bean.getStarttime());
            tableBean.setEndtime(bean.getEndtime());
        }
        tableBean.setName("msg_log_");
        List<String> tables = ipmBusTagDao.getAppTableName(tableBean);
        if(tables != null && tables.size() > 0){
        	int count = 1000; // 每次至多N条
            int filterNum = 0;
            if (bean.getServerPageNumber() != null) {
                filterNum = (bean.getServerPageNumber() - 1) * count;
            }
            for (int i = 0, len = tables.size(); i < len; i ++) {
            	bean.setTableName(tables.get(i));
                bean.setStarttime(tableBean.getStarttime());
                bean.setEndtime(tableBean.getEndtime());
                bean.setStartNum(filterNum);
                bean.setNum(count);
                List<DeptMsgLogBean> logList= ipmBusTagDao.getData(bean);
                for(int j =0; j <logList.size(); j++){
                    logbean = new DeptMsgLogBean();
                    logbean.setId(logList.get(j).getId());
                    logbean.setClientPort(logList.get(j).getClientPort());
                    logbean.setDelay(logList.get(j).getDelay());
                    logbean.setResp(logList.get(j).getResp());
                    logbean.setServerPort(logList.get(j).getServerPort());
                    logbean.setSuccess(logList.get(j).getSuccess());
                    logbean.setOriginalMessage(logList.get(j).getSource());
                    logbean.setBusiId(logList.get(j).getBusiId());
                    logbean.setWatchpointId(bean.getWatchpointId());
                    String str =lsitColunm(bean.getBusiId(), logList.get(j).getSource());
                    logbean.setSource(str);
                    logbean.setClient(logList.get(j).getClient());
                    logbean.setServer(logList.get(j).getServer());
                    logbean.setTransTime(logList.get(j).getTransTime());
                    logbean.setEndtime(logList.get(j).getEndtime());
                    logbean.setName(logList.get(j).getName());
                    listBean.add(logbean);
                }
                if (filterNum != 0 && listBean.size() == 0) {
                    filterNum -= ipmBusTagDao.getDataNumSql(bean);
                } else {
                    filterNum = 0;
                }
                count -= listBean.size();
                if (count <= 0) {
                    break;
                }
            }
        }
        return listBean;
    }
    /**
     * 
     * @Title: lsitColunm
     * @Description: 解析报文自定义列
     * @param l   业务id
     * @param source  原始字段
     * @return String
     * @author wangjianmin
     */
    public String  lsitColunm(long l, String source){
        //查询自定义列表
        List<IpmBusTagBean> columList= ipmBusTagDao.selectById((int)l);
        String val=null;
        for(int i =0; i< columList.size(); i++){
            //匹配自定义列
            if(columList.get(i).getStatusCode()!=null && !columList.get(i).getStatusCode().equals("")){
                String value =this.getSource(columList.get(i).getStatusCode(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getSuccessCode()!=null && !columList.get(i).getSuccessCode().equals("")){
                String value =this.getSource(columList.get(i).getSuccessCode(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getFailedCode()!=null && !columList.get(i).getFailedCode().equals("")){
                String value =this.getSource(columList.get(i).getFailedCode(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag0()!=null && !columList.get(i).getBusTag0().equals("")){
                String value =this.getSource(columList.get(i).getBusTag0(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag1()!=null && !columList.get(i).getBusTag1().equals("")){
                String value =this.getSource(columList.get(i).getBusTag1(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag2()!=null && !columList.get(i).getBusTag2().equals("")){
                String value =this.getSource(columList.get(i).getBusTag2(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag3()!=null && !columList.get(i).getBusTag3().equals("")){
                String value =this.getSource(columList.get(i).getBusTag3(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag4()!=null && !columList.get(i).getBusTag4().equals("")){
                String value =this.getSource(columList.get(i).getBusTag4(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag5()!=null && !columList.get(i).getBusTag5().equals("")){
                String value =this.getSource(columList.get(i).getBusTag5(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag6()!=null && !columList.get(i).getBusTag6().equals("")){
                String value =this.getSource(columList.get(i).getBusTag6(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag7()!=null && !columList.get(i).getBusTag7().equals("")){
                String value =this.getSource(columList.get(i).getBusTag7(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag8()!=null && !columList.get(i).getBusTag8().equals("")){
                String value =this.getSource(columList.get(i).getBusTag8(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag9()!=null && !columList.get(i).getBusTag9().equals("")){
                String value =this.getSource(columList.get(i).getBusTag9(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag10()!=null && !columList.get(i).getBusTag10().equals("")){
                String value =this.getSource(columList.get(i).getBusTag10(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag11()!=null && !columList.get(i).getBusTag11().equals("")){
                String value =this.getSource(columList.get(i).getBusTag11(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag12()!=null && !columList.get(i).getBusTag12().equals("")){
                String value =this.getSource(columList.get(i).getBusTag12(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag13()!=null && !columList.get(i).getBusTag13().equals("")){
                String value =this.getSource(columList.get(i).getBusTag13(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag14()!=null && !columList.get(i).getBusTag14().equals("")){
                String value =this.getSource(columList.get(i).getBusTag14(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag15()!=null && !columList.get(i).getBusTag15().equals("")){
                String value =this.getSource(columList.get(i).getBusTag15(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag16()!=null && !columList.get(i).getBusTag16().equals("")){
                String value =this.getSource(columList.get(i).getBusTag16(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag17()!=null && !columList.get(i).getBusTag17().equals("")){
                String value =this.getSource(columList.get(i).getBusTag17(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag18()!=null && !columList.get(i).getBusTag18().equals("")){
                String value =this.getSource(columList.get(i).getBusTag18(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
            if(columList.get(i).getBusTag19()!=null && !columList.get(i).getBusTag19().equals("")){
                String value =this.getSource(columList.get(i).getBusTag19(), source);
                if(value != null){
                    val= val + value + ",";
                }else{
                    if(val == null){
                        val="{}";
                    }
                }
            }
        }
        if(val == null){
            val =source;
        }else if(val.equals("{}")){
            val=null; 
        }else{
            if(val.indexOf("null")>-1){
                val=val.substring(4, val.length() -1);  
            }else{
                val=val.substring(2, val.length() -1);   
            }
        }
        return val;
    }
    
    /**
     * 
     * @Title: getSource
     * @Description: 解析报文
     * @param name  自定义名称
     * @param source 原始数据
     * @return String
     * @author wangjianmin
     */
    public String  getSource(String name, String source){
        String key=null;
        String  val =null;
        String  str=null;
        JSONObject jsonMap=JSONObject.parseObject(source);
        Set<Entry<String, Object>> entry =  jsonMap.entrySet();
        Iterator<Entry<String, Object>> it = entry.iterator();
        while(it.hasNext()){
            Entry<String, Object>  me = it.next();  
            String keys = me.getKey();
            if(keys.equals(name)){
                key= "\"" + keys  + "\"";
                val="\"" +me.getValue() +  "\"";
            }
        }
        if(key == null){
            str=null;
        }else{
            str =key +":" + val;
        }
        return str;
    }

    @Override
    public List<DeptMsgLogBean> selectByLogId(int id) {
       
        return  ipmBusTagDao.selectByLogId(id);
    }
    
    @Override
    public List<Object> getRemoteData(DeptMsgLogBean bean) {
        List<Object> dataBean = null;
        String url = "/depthanaly/getData.do";
        Integer centerId = bean.getIpmCenterId();
        dataBean = centerService.getRemoteSessionList(url, bean, DeptMsgLogBean.class, centerId);
        
        return dataBean;
    }
    
}
