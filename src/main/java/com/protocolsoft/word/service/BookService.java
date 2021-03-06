package com.protocolsoft.word.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.common.bean.ReportListBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.depthanaly.service.impl.IpmBusTagServiceImpl;
import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.service.EmailService;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.sendemail.MailSendService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.url.service.UrlService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.AuthorizeJurisDao;
import com.protocolsoft.user.dao.SystemUserDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;
import com.protocolsoft.watchpoint.service.WatchpointService;
import com.protocolsoft.word.bean.ReportAlarmBean;
import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.bean.ReportHistoryBean;
import com.protocolsoft.word.bean.ReportModalTableAndWranBean;
import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.bean.WordBean;
import com.protocolsoft.word.controller.HavingTimeProductNewWordController;
import com.protocolsoft.word.dao.GetBookBeanFromMySQLDao;
import com.protocolsoft.word.dao.ReportModalDao;
import com.protocolsoft.word.dao.ReportModalTableAndWarnDao;
import com.protocolsoft.word.dao.ReportPlanDao;
import com.protocolsoft.word.service.impl.ExportWordServiceUtil;
import com.protocolsoft.word.util.ChangeJsonToPictureUtil;
import com.protocolsoft.word.util.CleanAllTextUtil;
import com.protocolsoft.word.util.CreateNewTextUtil;
import com.protocolsoft.word.util.JsonStringOutputDocUtil;

/**
 * 
 * @ClassName: BookService
 * @author 刘斌
 *z
 */
@Service
public class BookService {
    
    /**
     * 
     */
    static private Integer[] kpiIds = {1, 2, 14};
    
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;
    
    /**
     * 
     */
    @Autowired
    private MailSendService mailSendService;
    
    /**
     * 
     */
    @Autowired
    private ReportAlarmService reportAlarmService;
    
    /**
     * 
     */
    @Autowired
    ReportModalTableAndWarnDao reportModalTableAndWarnDao;
    
    /**
     * 
     */
    @Autowired
    SystemUserDao systemUserDao;
    
    /**
     * 
     */
    @Autowired
    EmailService emailService;
    
    /**
     * 
     */
    @Autowired
    ReportModalDao reportModalDao;
    
    /**
     * 
     */
    @Autowired
    CommonService commonService;
    
    /**
     * @Fields reportPlanDao : reportPlanDao
     */
    @Autowired
    ReportPlanDao reportPlanDao;
    
    /**
     * @Fields getBookBeanFromMySQLDao : 获取定时报表对象 
     */
    @Autowired
    private GetBookBeanFromMySQLDao getBookBeanFromMySQLDao;


    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;

    /**
     * @Fields reportHistoryService : reportHistoryService对象
     */
    @Autowired
    private ReportHistoryService reportHistoryService;

    /**
     * @Fields watchpointServer : WatchpointServer对象
     */
    @Autowired
    private WatchpointService watchpointServer;

    /**
     * @Fields serverManagementService : 服务端对象
     */
    @Autowired
    private ServerManagementService serverManagementService;

    /**
     * @Fields cliemtService : 客户端端对象
     */
    @Autowired
    private ClientService cliemtService;

    /**
     * @Fields urlService : url接口对象
     */
    @Autowired
    private UrlService urlService;

    /**
     * @Fields timerReportDetailService : 图表Service
     */
    @Autowired
    private TimerReportDetailService timerReportDetailService;

    /**
     * @Fields plotService : KPI与模块关联 Service
     */
    @Autowired
    private PlotService plotService;

    /**
     * @Fields appService : 业务模块Service
     */
    @Autowired
    private AppService appService;

    /**
     * @Fields ipmBusTagServiceImpl : 获取报文Service
     */
    @Autowired
    private IpmBusTagServiceImpl ipmBusTagServiceImpl;

    /**
     * @Fields authorizeJurisService : 权限通类获取工具。
     */
    @Autowired
    private AuthorizeJurisService authorizeJurisService;
    
    /**
     * @Fields reportModalService : 模版Service
     */
    @Autowired
    ReportModalService reportModalService;
    
    /**
     * userAuthorizeDao注入
     */
    @Autowired(required=false)
    private AuthorizeJurisDao userAuthorizeDao;
    
    /**
     * WatchpointDao 对象
     */
    @Autowired
    private WatchpointDao watchpointDao;
    
    /**
     * ipm_app_business表Dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * 
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 
     * @Title: userModalProductReport
     * @Description: 
     * @param paramFrom
     * @param request
     * @return String
     * @throws InvalidFormatException 
     * @throws IOException String 
     * @author 刘斌
     */
    @Transactional(propagation=Propagation.SUPPORTS)
    public String userModalProductReport(ReportPlanBean paramFrom) throws InvalidFormatException, IOException{
        JSONObject json = new JSONObject();
        String path;
        List<ReportBusinessBean> listBus = paramFrom.getListBus();
        SystemUserBean bean = systemUserService.getUserBeanById(paramFrom.getUserId());
        try {
            String moduleIds = reportModalService.selectModuleIds(paramFrom.getModalId());
            if(null == moduleIds || "".equals(moduleIds)){
                json.put("result", false);
                json.put("msg", "模板不存在，可能已经被删除，请核实后再操作！");
                return json.toJSONString();
            }
            if(1==listBus.size()&&listBus.get(0).getBussinessId()==0){
                listBus.clear();
                if(2==bean.getRoleId()){
                    if(moduleIds.contains("12")){
                        String  serverStr = authorizeJurisService.selectModuleRole(paramFrom.getUserId(), 12, 1);
                        String[] strServer = serverStr.split(",");
                        if(strServer.length>0){
                            for(int i1 = 0; i1 < strServer.length; i1++){
                                ReportBusinessBean appBusBean = new ReportBusinessBean();
                                appBusBean.setBussinessId(Integer.parseInt(strServer[i1]));
                                appBusBean.setModulId(12);
                                listBus.add(appBusBean);
                            }
                        }
                    }
                    if(moduleIds.contains("11")){
                        String  climentStr= authorizeJurisService.selectModuleRole(paramFrom.getUserId(), 11, 1);
                        String[] strCliment = climentStr.split(",");
                        if(strCliment.length>0){
                            for(int i2 = 0; i2 < strCliment.length; i2++){
                                ReportBusinessBean appBusBean = new ReportBusinessBean();
                                appBusBean.setBussinessId(Integer.parseInt(strCliment[i2]));
                                appBusBean.setModulId(11);
                                listBus.add(appBusBean);
                            }
                        }
                    }
                }else if(1==bean.getRoleId()){
                    if(moduleIds.contains("12")){
                        List<AppBusinessBean> listAppBean= appBusinessDao.selectAppBusinessesByModuleId(12);
                        if(listAppBean.size()>0){
                            for(int i1 = 0; i1 < listAppBean.size(); i1++){
                                ReportBusinessBean appBusBean = new ReportBusinessBean();
                                appBusBean.setBussinessId(listAppBean.get(i1).getId());
                                appBusBean.setModulId(12);
                                listBus.add(appBusBean);
                            }
                        }
                    }
                    if(moduleIds.contains("11")){
                        List<AppBusinessBean> listAppBean= appBusinessDao.selectAppBusinessesByModuleId(11);
                        if(listAppBean.size()>0){
                            for(int i1 = 0; i1 < listAppBean.size(); i1++){
                                ReportBusinessBean appBusBean = new ReportBusinessBean();
                                appBusBean.setBussinessId(listAppBean.get(i1).getId());
                                appBusBean.setModulId(11);
                                listBus.add(appBusBean);
                            }
                        }
                    }
                }
            }
            paramFrom.setListBus(listBus);
            
            String watchPoint = paramFrom.getWatchpointIds();
            String[] watchPoints = watchPoint.split(",");
            if(watchPoints.length==1&&watchPoints[0].equals("0")){
                if(1==bean.getRoleId()){
                    List<WatchpointBean> listWatchpoint = watchpointDao.getFindAll();
                    if(null!=listWatchpoint&&listWatchpoint.size()>0){
                        String watchpointIds = "";
                        for(WatchpointBean juris : listWatchpoint){
                            watchpointIds += juris.getId() + ",";
                        }
                        watchpointIds =  watchpointIds.substring(0, watchpointIds.length()-1);
                        paramFrom.setWatchpointIds(watchpointIds);
                    }
                }else if(2==bean.getRoleId()){
                    String  watchpointIds= authorizeJurisService.selectModuleRole(paramFrom.getUserId(), 10, 1);
                    paramFrom.setWatchpointIds(watchpointIds);
                }
            }
            path = HavingTimeProductNewWordController.class.getClassLoader()
                    .getResource("report").getPath();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("msg", "系统文件缺失，请核实report文件是否布置在指定路径");
            return json.toJSONString();
        }
        json.put("result", false);
        ReportHistoryBean beanHistory = new ReportHistoryBean();
        try {
            String deleteEmpty = paramFrom.getName().replace(" ", "");
            paramFrom.setName(deleteEmpty);
            String filePath = this.getClass().getClassLoader().getResource("/")
                    .getPath();
            Map<String, String> nameMap = new HashMap<>();
            List<DrawingOptionsBean> list = getBookBeanFromMySQLDao.getDrawingOptionsBeanList(paramFrom.getModalId());
            
            List<ReportModalTableAndWranBean> listTableAndWarning = reportModalTableAndWarnDao
                        .selectReportModalTableAndWranBeans(paramFrom.getModalId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            long nowTime = System.currentTimeMillis();
            String realNamew = paramFrom.getName();

            beanHistory.setName(realNamew+"（自定义）");
            beanHistory.setTypeId(paramFrom.getTypeId());
            beanHistory.setCreatetime(nowTime / 1000);
            beanHistory.setDatastime(paramFrom.getStartTime());
            beanHistory.setUserId(paramFrom.getUserId());
            beanHistory.setDataetime(paramFrom.getEndTime());
            beanHistory.setSendStatus(1);
            List<ReportEmailBean> listEmail = paramFrom.getList();
            String sendNames ="";
            String reciveNames ="";
            if(null!=listEmail&&listEmail.size()>0){
                for (ReportEmailBean emailBena : listEmail) {
                    sendNames += emailBena.getSender() +";";
                    reciveNames += emailBena.getRecriver() + ";";
                }
            }
            beanHistory.setRecriveNames(reciveNames);
            beanHistory.setSendNames(sendNames);
            
            try {
                int num = reportHistoryService.insertReportHistory(beanHistory);
                if (num <= 0) {
                    json.put("msg", "自定义报表生成失败，请核对报表名称是否重复，是否包含空格！");
                    return json.toJSONString();
                }
            } catch (Exception e) {
                json.put("msg", "自定义报表生成失败!请稍后重试");
                return json.toJSONString();
            }
            Map<String, List<List<ReportListBean>>> listListMap = new  HashMap<>();
            List<List<ReportListBean>> watchmps = new ArrayList<>();
            List<List<ReportListBean>> servermaps = new ArrayList<>();
            List<List<ReportListBean>> clientmaps = new ArrayList<>();
            String watchPoint = paramFrom.getWatchpointIds();
            String[] watchPoints = watchPoint.split(",");
            Integer modalId = paramFrom.getModalId();
            List<JSONObject> listJson = new ArrayList<>();
            for(int se =0; se<watchPoints.length; se++){
                WatchpointBean watchpointBeanq = watchpointServer
                        .getWatchpointById(Integer.parseInt(watchPoints[se]));
                if(watchpointBeanq!=null){
                    ReportBusinessBean beanwatchpointBean = new ReportBusinessBean();
                    beanwatchpointBean.setBussinessId(Integer.parseInt(watchPoints[se]));
                    beanwatchpointBean.setModulId(10);
                    beanwatchpointBean.setWatchPointId(Integer.parseInt(watchPoints[se]));
                    listBus.add(beanwatchpointBean);
                    ReportModalTableAndWranBean beanTableWatchpoing = reportModalTableAndWarnDao
                            .selectReportModalTableBeanByTwo(paramFrom.getModalId());
                    if(null!=beanTableWatchpoing){
                        List<ReportListBean> listTableBeane = commonService.getReportWatchpointKpiList(paramFrom.getStartTime(),
                            paramFrom.getEndTime(), watchPoints[se]);
                        watchmps.add(listTableBeane);
                    }
                    ReportModalTableAndWranBean beanWarnWatchpoing = reportModalTableAndWarnDao
                            .selectReportModalWranBeanByTwo(paramFrom.getModalId());
                    if(null!=beanWarnWatchpoing){
                        ReportAlarmBean beanAlarm1 = new ReportAlarmBean();
                        beanAlarm1.setBusinessId(Integer.parseInt(watchPoints[se]));
                        beanAlarm1.setEndtime(paramFrom.getEndTime());
                        beanAlarm1.setModuleId(10);
                        beanAlarm1.setSpot(24);
                        beanAlarm1.setType(1);
                        beanAlarm1.setStarttime(paramFrom.getStartTime());
                        beanAlarm1.setWatchpointId(Integer.parseInt(watchPoints[se]));
                        String warnJsonw = reportAlarmService.getReportAlarmData(beanAlarm1);
                        String jsonName2 = "10!" + watchPoints[se] + "!0!" + Integer.parseInt(watchPoints[se]) + "!0";
                        WatchpointBean watchpointBean = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                        String fileXname2 = "观察点-" + watchpointBean.getName() + "-告警";
                        CreateNewTextUtil.createJsonOfTable(filePath + File.separator + "wordtext"
                                + File.separator + beanHistory.getId(), jsonName2, warnJsonw);
                        nameMap.put(jsonName2 + ".png", fileXname2);
                    }
                    if(1 == modalId || 2 == modalId){
                        AlarmLogBean alarmLogBean = new AlarmLogBean();
                        alarmLogBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                        alarmLogBean.setBusinessId(Integer.parseInt(watchPoints[se]));
                        alarmLogBean.setModuleId(10);
                        alarmLogBean.setStarttime(beanHistory.getDatastime());
                        alarmLogBean.setEndtime(beanHistory.getDataetime());
                        alarmLogBean.setKpitype(1);
                        JSONObject jsonObject = new JSONObject();
                        Map<String, Long> mapAlarm1 = alarmLogService.getAlarmLogCount(alarmLogBean);
                        if(null!=mapAlarm1){
                            jsonObject.put("0", mapAlarm1.get("count"));
                        }
                        jsonObject.put("name", watchpointBeanq.getName());
                        for(int xo = 0; xo < kpiIds.length; xo++){
                            alarmLogBean.setkId(kpiIds[xo]);
                            Map<String, Long> mapAlarm;
                            try {
                                mapAlarm = alarmLogService
                                        .getAlarmLogCount(alarmLogBean);
                                if(null!=mapAlarm){
                                    jsonObject.put(kpiIds[xo]+"", mapAlarm.get("count"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                        }
                        listJson.add(jsonObject);
                    }
                    if(null!=listBus&&listBus.size()>0){
                        
                        for (ReportBusinessBean  beanBus : listBus) {
                            if(modalId <=2 && 10 != beanBus.getModulId()){
                                AppBusinessBean appBusinessBean = serverManagementService.getServerSide(beanBus.getBussinessId());
                                AlarmLogBean alarmLogBean = new AlarmLogBean();
                                alarmLogBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                alarmLogBean.setBusinessId(beanBus.getBussinessId());
                                alarmLogBean.setModuleId(beanBus.getModulId());
                                alarmLogBean.setStarttime(beanHistory.getDatastime());
                                alarmLogBean.setEndtime(beanHistory.getDataetime());
                                alarmLogBean.setKpitype(1);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("name", watchpointBeanq.getName()+"-"+appBusinessBean.getName());
                                Map<String, Long> mapAlarm1 = alarmLogService.getAlarmLogCount(alarmLogBean);
                                if(null!=mapAlarm1){
                                    jsonObject.put("0", mapAlarm1.get("count"));
                                }
                                for(int xo = 0; xo < kpiIds.length; xo++){
                                    alarmLogBean.setkId(kpiIds[xo]);
                                    Map<String, Long> mapAlarm = alarmLogService.getAlarmLogCount(alarmLogBean);
                                    if(null!=mapAlarm){
                                        jsonObject.put(kpiIds[xo]+"", mapAlarm.get("count"));
                                    }
                                }
                                listJson.add(jsonObject);
                            }
                            for(ReportModalTableAndWranBean beanTandW : listTableAndWarning){
                                if(null!=beanTandW.getTableHaving()){
                                    if(11==beanBus.getModulId()&&11==beanTandW.getModultId()){
                                        List<ReportListBean> listTableBean = commonService.getOtherModuleKpiList(paramFrom.getStartTime(),
                                                paramFrom.getEndTime(), beanBus.getModulId(),
                                                Integer.parseInt(watchPoints[se]), beanBus.getBussinessId()+"");
                                        List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                        for(ReportListBean beanTable : listTableBean){
                                            String nameOfTableBean = beanTable.getName();
                                            beanTable.setName(watchpointBeanq.getName()+"-"+nameOfTableBean);
                                            listTableBeanNew.add(beanTable);
                                        }
                                        
                                        clientmaps.add(listTableBeanNew);
                                    }else if(12==beanBus.getModulId()&&12==beanTandW.getModultId()){
                                        List<ReportListBean> listTableBean = commonService.getOtherModuleKpiList(paramFrom.getStartTime(),
                                                paramFrom.getEndTime(), beanBus.getModulId(), Integer.parseInt(watchPoints[se]),
                                                beanBus.getBussinessId()+"");
                                        List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                        for(ReportListBean beanTable : listTableBean){
                                            String nameOfTableBean = beanTable.getName();
                                            beanTable.setName(watchpointBeanq.getName()+"-"+nameOfTableBean);
                                            listTableBeanNew.add(beanTable);
                                        }
                                        
                                        servermaps.add(listTableBeanNew);
                                        
                                    }
                                }else if(null!=beanTandW.getWarningHaving()&&10!=beanTandW.getModultId()){
                                    if(beanBus.getModulId()==beanTandW.getModultId()){
                                        ReportAlarmBean beanAlarm = new ReportAlarmBean();
                                        beanAlarm.setBusinessId(beanBus.getBussinessId());
                                        beanAlarm.setEndtime(paramFrom.getEndTime());
                                        beanAlarm.setModuleId(beanBus.getModulId());
                                        beanAlarm.setSpot(24);
                                        beanAlarm.setType(1);
                                        beanAlarm.setStarttime(paramFrom.getStartTime());
                                        beanAlarm.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        String warnJson = reportAlarmService.getReportAlarmData(beanAlarm);
                                        String jsonName1 = beanBus.getModulId() + "!" + watchPoints[se]
                                                + "!0!" + beanBus.getBussinessId() + "!0";
                                        String fileXname1 ="";
                                        WatchpointBean watchpointBeanno = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                        String appName = reportPlanDao.getServerSide(beanBus.getBussinessId());
                                        if(11 == beanBus.getModulId()){
                                            fileXname1 = "客户端-" + watchpointBeanno.getName()+"-" + appName + "-告警";
                                        }else if(12 == beanBus.getModulId()){
                                            fileXname1 = "服务端-" + watchpointBeanno.getName()+"-" + appName + "-告警";
                                        }
                                        CreateNewTextUtil.createJsonOfTable(filePath + File.separator + "wordtext"
                                                        + File.separator + beanHistory.getId(), jsonName1, warnJson);
                                        nameMap.put(jsonName1 + ".png", fileXname1);
                                    }
                                }
                            }
                    
                            for (DrawingOptionsBean group : list) {
                                if(beanBus.getModulId()==group.getModleId()){
                                    DrawingOptionsBean drawingOptionsBean = new DrawingOptionsBean();
                                    drawingOptionsBean.setEndtime(paramFrom.getEndTime());
                                    drawingOptionsBean.setStarttime(paramFrom.getStartTime());
                                    drawingOptionsBean.setPlotId(group.getPlotId());
                                    drawingOptionsBean.setPlotTypeId(group.getPlotTypeId());
                                    //服务端Over
                                    drawingOptionsBean.setModleId(group.getModleId());
                                    drawingOptionsBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                    drawingOptionsBean.setClientId(beanBus.getBussinessId());
                                    PlotOptionBean plotBean = plotService.getPlotOptionById(group.getPlotId());
                                    String fileXname = "";
                                    String appName = "";
                                    if (10 == group.getModleId()) {
                                        WatchpointBean watchpointBeanr; 
                                        try {
                                            watchpointBeanr = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                            appName = watchpointBeanr.getName() + "-";
                                        } catch (Exception e) {
                                            appName = beanBus.getBussinessId() + "";
                                        }
                                        drawingOptionsBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        if (null != plotBean) {
                                            fileXname = "观察点-" + appName + plotBean.getName();
                                        } else {
                                            fileXname = "" + group.getModleId() + watchPoints[se] + group.getPlotId();
                                        }
                                    } else {
                                        drawingOptionsBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        AppBusinessBean appBusinessBean = serverManagementService.getServerSide(beanBus.getBussinessId());
                                        if (11 == group.getModleId()) {
                                            fileXname = "客户端-" + watchpointBeanq.getName() 
                                                    + "-" + appBusinessBean.getName() + "-" + plotBean.getName();
                                        } else if (12 == group.getModleId()) {
                                            fileXname = "服务端-" + watchpointBeanq.getName() 
                                                    + "-" + appBusinessBean.getName() + "-" + plotBean.getName();
                                        }
                                    }
                                   
                                    Map<String, Object> map = new HashMap<String, Object>();
                        
                                    Integer watchPointId = 0;
                                    if (null != drawingOptionsBean.getWatchpointId()) {
                                        watchPointId = drawingOptionsBean.getWatchpointId();
                                    }
                                    String jsonName = group.getModleId() + "!" + watchPointId
                                            + "!" + group.getPlotId() + "!" + beanBus.getBussinessId() + "!" + group.getPlotId();
                        
                                    if (10 == group.getModleId()) {
                                        jsonName = group.getModleId() + "!" + watchPoints[se]
                                                + "!" + group.getPlotId() + "!" + watchPoints[se] + "!" + group.getPlotId();
                                        try {
                                            map = watchpointServer.getWatchpointGraphical(drawingOptionsBean);
                                        } catch (Exception e) {
                                        }
                                    } else if (11 == group.getModleId()) {
                                        map = cliemtService.getClientGraphical(drawingOptionsBean);
                                    } else if (12 == group.getModleId()) {
                                        map = serverManagementService.getServerSideGraphical(
                                                drawingOptionsBean, beanBus.getBussinessId());
                                    }
                                    if (!map.isEmpty()) {
                                        String sf = JsonStringOutputDocUtil.createJsonFile(
                                                filePath + File.separator + "wordtext" + File.separator + beanHistory.getId(),
                                                jsonName, fileXname, map, drawingOptionsBean.getPlotTypeId());
                                        nameMap.put(jsonName + ".png", fileXname);
                                        nameMap.put(jsonName + ".pngl", sf);
                                    }
                                }
                                    
                            }
                        }
                    }
                }
            }
            if(null!=watchmps){
                listListMap.put("10", watchmps);
            }
            if(null!=servermaps){
                listListMap.put("12", servermaps);
            }
            if(null!=clientmaps){
                listListMap.put("11", clientmaps);
            }
            ChangeJsonToPictureUtil.translateJsonToPicture(
                            filePath + File.separator + "wordtext" + File.separator + beanHistory.getId(),
                            filePath + File.separator + "wordpicture" + File.separator + beanHistory.getId());
            CleanAllTextUtil.cleanFile(filePath + File.separator
                    + "wordtext" + File.separator + beanHistory.getId());
            String realName = new String((realNamew+"（自定义）").getBytes("iso-8859-1"), "utf-8");
            WordBean beanWord = new WordBean(paramFrom.getName(),
                    sdf.format(nowTime), "自定义报表", null, bean.getRealName(),
                    sdf.format(paramFrom.getStartTime() * 1000),
                    sdf.format(paramFrom.getEndTime() * 1000), filePath + "wordbar" + File.separator + beanHistory.getId());
            if(null!=listJson && listJson.size()>0){
                beanWord.setJsonObjects(listJson);
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("picturePath", filePath + File.separator + "wordpicture" + File.separator
                    + beanHistory.getId());
            map.put("modelPath", filePath + "wordmodle" + File.separator + "joanna.docx");
            map.put("pathpp", path + File.separator + beanHistory.getId().toString());
            ExportWordServiceUtil.getTheWarningWord(beanWord, realName, plotService, map, nameMap, listListMap);
            CleanAllTextUtil.cleanFile(filePath + File.separator
                    + "wordpicture" + File.separator + beanHistory.getId());
            File file = new File(path + File.separator
                    + beanHistory.getId().toString() + File.separator + realName + ".docx");
            if (!file.exists()) {
                reportHistoryService.deleteReportHistory(beanHistory.getId(),
                        beanHistory.getId().toString(), realName, bean);
            } else {
                boolean resultSend = true;
                if(null!=listEmail&&listEmail.size()>0){
                    for (ReportEmailBean emailBena : listEmail) {
                        try {
                            EmailBean emailBean = emailService.getEmail();
                            mailSendService.send(emailBean, emailBena, path + File.separator
                                    + beanHistory.getId().toString() + File.separator + realName + ".docx", realNamew+".docx");
                        } catch (Exception e) {
                            e.printStackTrace();
                            resultSend = false;
                        }
                    }
                    if(resultSend){
                        long sendTime = System.currentTimeMillis();
                        reportHistoryService.updateHistoryBeanBySend(sendTime/1000, beanHistory.getId());
                    }
                }
                
                LogsBean logsBean = new LogsBean();
                logsBean.setUserId(bean.getId());
                logsBean.setModuleId(19);
                logsBean.setMsg(bean.getRealName()+"生成自定义报表，生成自定义报表的历史信息。");
                logsBean.setTime(DateUtils.getNowTimeSecond());
                logsDao.insertLogs(logsBean);
                
                if(!resultSend){
                    json.put("msg", "生成报告成功，但邮件发送失败，请用户核实邮件信息！");
                    json.put("id", beanHistory.getId());
                    return json.toJSONString();
                }
                json.put("result", true);
                json.put("id", beanHistory.getId());
                return json.toJSONString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (null != beanHistory && null != beanHistory.getId()) {
                reportHistoryService.deleteReportHistory(beanHistory.getId(),
                        path + File.separator + beanHistory.getId().toString(),
                        paramFrom + ".docx", bean);
            }
            json.put("result", false);
            json.put("msg", "报表生成失败，请稍后重试.");
            return json.toJSONString();
        }
        json.put("result", false);
        json.put("msg", "报表生成失败，请稍后重试..");
        return json.toJSONString();
    }
}
