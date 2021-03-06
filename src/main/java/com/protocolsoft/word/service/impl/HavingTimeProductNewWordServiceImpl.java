/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.common.bean.ReportListBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.service.EmailService;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.sendemail.MailSendService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.SystemUserDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.UnitUtils;
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
import com.protocolsoft.word.dao.ReportEmailDao;
import com.protocolsoft.word.dao.ReportHistoryDao;
import com.protocolsoft.word.dao.ReportModalDao;
import com.protocolsoft.word.dao.ReportModalTableAndWarnDao;
import com.protocolsoft.word.dao.ReportPlanDao;
import com.protocolsoft.word.service.HavingTimeProductNewWordService;
import com.protocolsoft.word.service.ReportAlarmService;
import com.protocolsoft.word.service.ReportHistoryService;
import com.protocolsoft.word.service.ReportModalService;
import com.protocolsoft.word.util.ChangeJsonToPictureUtil;
import com.protocolsoft.word.util.CleanAllTextUtil;
import com.protocolsoft.word.util.CreateNewTextUtil;
import com.protocolsoft.word.util.GetTimeUtil;
import com.protocolsoft.word.util.JsonStringOutputDocUtil;

/***
 * 
 * @ClassName: HavingTimeProductNewWordServiceImpl
 * @Description: 
 * @author 刘斌
 *
 */
@Service
public class HavingTimeProductNewWordServiceImpl implements
        HavingTimeProductNewWordService {
    /**
     * 
     */
    static private Integer[] kpiIds = {1, 2, 14};

    /**
     * 
     */
    private static String ints = ",60,97,32,33,276,1,58,61,9,40,68,2,";
    
    /**
     * 
     */
    private static String ints2 = ",317,319,";
    
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
    EmailService emailService;

    /**
     * @Fields reportHistoryDao : 获取历史记录对象
     */
    @Autowired
    private ReportHistoryDao reportHistoryDao;

    /**
     * @Fields systemUserDao : 用户对象
     */
    @Autowired
    private SystemUserDao systemUserDao;

    /**
     * @Fields cliemtService : 客户端对象
     */
    @Autowired
    private ClientService cliemtService;

    /**
     * @Fields serverManagementService : 服务端对象
     */
    @Autowired
    private ServerManagementService serverManagementService;

    /**
     * @Fields watchpointServer : 获取观察点对象
     */
    @Autowired
    private WatchpointService watchpointServer;

    /**
     * @Fields plotService : KPI对象
     */
    @Autowired
    private PlotService plotService;

    /**
     * @Fields logger : logger
     */
    static Logger logger = Logger.getLogger(HavingTimeProductNewWordServiceImpl.class);
    
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;

    /**
     * @Fields reportEmailDao : reportEmailDao
     */
    @Autowired
    ReportEmailDao reportEmailDao;

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
     * @Fields reportHistoryService : reportHistoryService对象
     */
    @Autowired
    private ReportHistoryService reportHistoryService;

    /**
     * 
     */
    @Autowired
    private ReportModalDao reportModalDao;

    /**
     * 
     */
    @Autowired
    private CommonService commonService;

    /**
     * 
     */
    @Autowired
    ReportModalTableAndWarnDao reportModalTableAndWarnDao;
    
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
     * 
     */
    @Autowired
    private AlarmLogService alarmLogService;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public void productWordByDate() {

        try {
            
            long nowTime = System.currentTimeMillis();
            List<ReportPlanBean> listBean = reportPlanDao.selectAllNeedReportPlan(2);
            if (null != listBean && listBean.size() > 0) {
                for (ReportPlanBean beanQ : listBean) {
                    SystemUserBean userBean = systemUserDao.getUserBeanById(beanQ.getUserId());
                    
                    List<ReportBusinessBean> listBus = reportPlanDao.selectAllReportBusinessByPlanId(beanQ.getId());
                    String moduleIds = reportModalService.selectModuleIds(beanQ.getModalId());
                    if(1==listBus.size()&&listBus.get(0).getBussinessId()==0){
                        listBus.clear();
                        if(2==userBean.getRoleId()){
                            if(moduleIds.contains("12")){
                                String  serverStr = authorizeJurisService.selectModuleRole(beanQ.getUserId(), 12, 1);
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
                                String  climentStr= authorizeJurisService.selectModuleRole(beanQ.getUserId(), 11, 1);
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
                        }else if(1==userBean.getRoleId()){
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
                    beanQ.setListBus(listBus);
                    
                    String watchPoint2 = beanQ.getWatchpointIds();
                    String[] watchPoints2 = watchPoint2.split(",");
                    if(watchPoints2.length==1&&watchPoints2[0].equals("0")){
                        watchPoint2 = "";
                        if(1==userBean.getRoleId()){
                            List<WatchpointBean> listWatchpoint = watchpointDao.getFindAll();
                            if(null!=listWatchpoint&&listWatchpoint.size()>0){
                                for(WatchpointBean juris : listWatchpoint){
                                    watchPoint2 += juris.getId() + ",";
                                }
                                watchPoint2 =  watchPoint2.substring(0, watchPoint2.length()-1);
                            }
                        }else if(2==userBean.getRoleId()){
                            watchPoint2= authorizeJurisService.selectModuleRole(userBean.getId(), 10, 1);
                        }
                        beanQ.setWatchpointIds(watchPoint2);
                    }

                    Map<String, String> nameMap = new HashMap<>();
                    List<DrawingOptionsBean> list = getBookBeanFromMySQLDao.getDrawingOptionsBeanList(beanQ.getModalId());
                    List<ReportModalTableAndWranBean> listTableAndWarning = reportModalTableAndWarnDao
                            .selectReportModalTableAndWranBeans(beanQ.getModalId());
                    SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                    String rootPath = this.getClass().getClassLoader().getResource("/").getPath();
                    Calendar cal = Calendar.getInstance();
                    String path = HavingTimeProductNewWordController.class.getClassLoader().getResource("report").getPath();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(sdf.format(cal.getTime()));
                    long longDate = date.getTime();
                    ReportHistoryBean beanHistory = new ReportHistoryBean();
                    beanHistory.setName(beanQ.getName() + "（日报）" + sdf.format(longDate - 24 * 60 * 60000));
                    beanHistory.setTypeId(2);
                    beanHistory.setUserId(beanQ.getUserId());
                    beanHistory.setCreatetime((System.currentTimeMillis() / 1000));
                    beanHistory.setDatastime((longDate / 1000 - 24 * 60 * 60));
                    beanHistory.setDataetime((longDate / 1000));
                    beanHistory.setSendStatus(1);
                    List<ReportEmailBean> listEmail = reportEmailDao.selectReportEmails(beanQ.getId());
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
                    int lo = reportHistoryDao.insertReportHistory(beanHistory);
                    if (0 != lo) {
                        Map<String, List<List<ReportListBean>>> listListMap = new HashMap<>();
                        List<List<ReportListBean>> watchmps = new ArrayList<>();
                        List<List<ReportListBean>> servermaps = new ArrayList<>();
                        List<List<ReportListBean>> clientmaps = new ArrayList<>();
                        int volantenner = beanQ.getCompareToLastOne();
                        try {
                            String watchPoint = beanQ.getWatchpointIds();
                            String[] watchPoints = watchPoint.split(",");
                            Integer modalId = beanQ.getModalId();
                            List<JSONObject> listJson = new ArrayList<>();
                            for (int se = 0; se < watchPoints.length; se++) {
                                WatchpointBean watchpointBean1 = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                if(null!=watchpointBean1){
                                    ReportBusinessBean beanwatchpointBean = new ReportBusinessBean();
                                    beanwatchpointBean.setBussinessId(Integer.parseInt(watchPoints[se]));
                                    beanwatchpointBean.setModulId(10);
                                    beanwatchpointBean.setWatchPointId(Integer.parseInt(watchPoints[se]));
                                    listBus.add(beanwatchpointBean);
                                    ReportModalTableAndWranBean beanTableWatchpoing = reportModalTableAndWarnDao
                                            .selectReportModalTableBeanByTwo(beanQ.getModalId());
                                    if (null != beanTableWatchpoing && null != beanTableWatchpoing.getTableHaving() 
                                            && 1 == beanTableWatchpoing.getTableHaving()) {
                                        List<ReportListBean> listTableBeane = commonService
                                                    .getReportWatchpointKpiList(beanHistory.getDatastime(),
                                                            beanHistory.getDataetime(), watchPoints[se]);
                                        if (2 == volantenner) {
                                            String op = listTableBeane.get(0).getName();
                                            listTableBeane.get(0).setName(op + "（本日）");
                                            List<ReportListBean> listTableBeane2 = commonService.getReportWatchpointKpiList(
                                                            beanHistory.getDatastime() - 24 * 60 * 60, 
                                                            beanHistory.getDataetime() - 24 * 60 * 60, watchPoints[se]);
                                            listTableBeane.addAll(listTableBeane2);
                                            String op2 = listTableBeane.get(1).getName();
                                            listTableBeane.get(1).setName(op2 + "（昨日）");
                                        }
                                        watchmps.add(listTableBeane);
                                    }
                                    ReportModalTableAndWranBean beanWarnWatchpoing = reportModalTableAndWarnDao
                                            .selectReportModalWranBeanByTwo(beanQ.getModalId());
                                    if (null != beanWarnWatchpoing && null != beanWarnWatchpoing.getWarningHaving()
                                            && 1 == beanWarnWatchpoing.getWarningHaving()) {
                                        ReportAlarmBean beanAlarm1 = new ReportAlarmBean();
                                        beanAlarm1.setBusinessId(Integer.parseInt(watchPoints[se]));
                                        beanAlarm1.setEndtime(beanHistory.getDataetime());
                                        beanAlarm1.setModuleId(10);
                                        beanAlarm1.setSpot(24);
                                        beanAlarm1.setType(2);
                                        beanAlarm1.setStarttime(beanHistory.getDatastime());
                                        beanAlarm1.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        String warnJson = "";
                                        if (2 == volantenner) {
                                            beanAlarm1.setRingRatioEnd(beanHistory.getDataetime() - 24 * 60 * 60);
                                            beanAlarm1.setRingRatioStart(beanHistory.getDatastime() - 24 * 60 * 60);
                                            beanAlarm1.setName("今日");
                                            beanAlarm1.setRingRatioName("昨日");
                                            warnJson = reportAlarmService.getReportRingRatioData(beanAlarm1);
                                        } else {
                                            warnJson = reportAlarmService.getReportAlarmData(beanAlarm1);
                                        }
                                        String jsonName2 = "10!" + watchPoints[se] + "!0!" + Integer.parseInt(watchPoints[se]) + "!0";
                                        WatchpointBean watchpointBean = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                        String fileXname2 = "观察点-" + watchpointBean.getName() + "-告警";
                                        CreateNewTextUtil.createJsonOfTable(rootPath + File.separator+ "wordtext"
                                                + File.separator + beanHistory.getId(), jsonName2, warnJson);
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
                                        jsonObject.put("name", watchpointBean1.getName());
                                        for(int xo = 0; xo < kpiIds.length; xo++){
                                            alarmLogBean.setkId(kpiIds[xo]);
                                            Map<String, Long> mapAlarm;
                                            try {
                                                mapAlarm = alarmLogService.getAlarmLogCount(alarmLogBean);
                                                if(null!=mapAlarm){
                                                    jsonObject.put(kpiIds[xo]+"", mapAlarm.get("count"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            
                                        }
                                        listJson.add(jsonObject);
                                    }
                                    for (ReportBusinessBean beanBus : listBus) {
                                        AppBusinessBean appBusinessBean = serverManagementService.getServerSide(beanBus.getBussinessId());
                                        if(modalId <=2 && 10 != beanBus.getModulId()){
                                            AlarmLogBean alarmLogBean = new AlarmLogBean();
                                            alarmLogBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                            alarmLogBean.setBusinessId(beanBus.getBussinessId());
                                            alarmLogBean.setModuleId(beanBus.getModulId());
                                            alarmLogBean.setStarttime(beanHistory.getDatastime());
                                            alarmLogBean.setEndtime(beanHistory.getDataetime());
                                            alarmLogBean.setKpitype(1);
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("name", watchpointBean1.getName()+"-"+appBusinessBean.getName());
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
                                        if (null!=appBusinessBean && null != listTableAndWarning && listTableAndWarning.size() > 0) {
                                            for (ReportModalTableAndWranBean beanTandW : listTableAndWarning) {
                                                if (null!=appBusinessBean && null != beanTandW.getTableHaving() 
                                                        && 11 == beanBus.getModulId()&& 11 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = this.
                                                            checkAndDoDate(volantenner, beanHistory, Integer.parseInt(watchPoints[se]), beanBus);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    clientmaps.add(listTableBeanNew);
                                                } else if (null!=appBusinessBean && null!=appBusinessBean && null != beanTandW.getTableHaving() 
                                                        && 12 == beanBus.getModulId()&& 12 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = this.
                                                            checkAndDoDate(volantenner, beanHistory, Integer.parseInt(watchPoints[se]), beanBus);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    servermaps.add(listTableBeanNew);
                                                } else if (null!=appBusinessBean && null!=appBusinessBean && null != beanTandW.getWarningHaving() 
                                                        && 10!=beanTandW.getModultId() && beanBus.getModulId() == beanTandW.getModultId()) {
                                                    Map<Integer, String> mapStr = checkWarnAndDaoDate(
                                                            beanBus, beanHistory, Integer.parseInt(watchPoints[se]), volantenner, appBusinessBean, 2);
                                                    CreateNewTextUtil.createJsonOfTable(rootPath + File.separator+ "wordtext" 
                                                            + File.separator+ beanHistory.getId(), mapStr.get(2), mapStr.get(1));
                                                    nameMap.put(mapStr.get(2) + ".png", mapStr.get(3));
                                                }
                                            }
                                        }
                                        if (null!=appBusinessBean && null != list && lo > 0 && list.size() > 0) {
                                            for (DrawingOptionsBean bean : list) {
                                                if (beanBus.getModulId() == bean.getModleId() && null!= appBusinessBean) {
                                                    Map<Integer, Object> mapCan = new HashMap<>();
                                                    mapCan.put(1, beanQ);
                                                    mapCan.put(2, appBusinessBean);
                                                    mapCan.put(3, beanHistory);
                                                    mapCan.put(4, rootPath);
                                                    mapCan.put(5, nameMap);
                                                    nameMap = checkMapAndDoDate(bean, beanBus, longDate, 
                                                            Integer.parseInt(watchPoints[se]), watchpointBean1, mapCan);
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            if (null != watchmps) {
                                listListMap.put("10", watchmps);
                            }
                            if (null != servermaps) {
                                listListMap.put("12", servermaps);
                            }
                            if (null != clientmaps) {
                                listListMap.put("11", clientmaps);
                            }
                            ChangeJsonToPictureUtil.translateJsonToPicture(rootPath + File.separator + "wordtext"
                                            + File.separator + beanHistory.getId(), rootPath + File.separator + "wordpicture"
                                            + File.separator + beanHistory.getId());
                            CleanAllTextUtil.cleanFile(rootPath+ File.separator + "wordtext"
                                    + File.separator + beanHistory.getId());
                            WordBean beanWord = new WordBean(beanQ.getName() + sdf.format(longDate - 24 * 60 * 60000),
                                    sdfm.format(nowTime), "定时日报", null,
                                    userBean.getRealName(), sdfm.format(longDate - 24 * 60 * 60000),
                                    sdfm.format(longDate), rootPath + "wordbar" + File.separator + beanHistory.getId());
                            if(null!=listJson && listJson.size()>0){
                                beanWord.setJsonObjects(listJson);
                            }
                            beanWord.setMap(nameMap);
                            String really = new String((beanQ.getName() + sdf.format(longDate - 24 * 60 * 60000) + "（日报）")
                                    .getBytes("iso-8859-1"), "utf-8");
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("picturePath", rootPath + "wordpicture" + File.separator + beanHistory.getId());
                            map.put("modelPath", rootPath + "wordmodle" + File.separator + "joanna.docx");
                            map.put("pathpp", path + beanHistory.getId());
                            ExportWordServiceUtil.getTheWarningWord(beanWord, really, plotService, map, nameMap, listListMap);
                            File file = new File(path + File.separator + beanHistory.getId() + File.separator + really + ".docx");
                            CleanAllTextUtil.cleanFile(rootPath + "wordpicture" + File.separator + beanHistory.getId());
                            if (!file.exists()) {
                                reportHistoryDao.deleteReportHistory(beanHistory.getId());
                            } else {
                                Long nextTime = GetTimeUtil.getNextSendTimeByType(2);
                                boolean resultSend = true;
                                if (null != listEmail && listEmail.size() > 0) {
                                    for (ReportEmailBean emailBena : listEmail) {
                                        try {
                                            EmailBean emailBean = emailService.getEmail();
                                            mailSendService.send(emailBean, emailBena, path + File.separator + beanHistory.getId() 
                                                    + File.separator + really + ".docx", beanQ.getName()+".docx");
                                        } catch (Exception e) {
                                            resultSend = false;
                                        }
                                    }
                                    if(resultSend){
                                        long sendTime = System.currentTimeMillis();
                                        reportHistoryService.updateHistoryBeanBySend(sendTime/1000, beanHistory.getId());
                                        reportPlanDao.addReportPlanSendTimes(beanQ.getId());
                                    }
                                }
                                reportPlanDao.updateReportPlanNextSendTime(beanQ.getId(), nextTime/1000);
                                
                                LogsBean logsBean = new LogsBean();
                                logsBean.setUserId(userBean.getId());
                                logsBean.setModuleId(19);
                                logsBean.setMsg(userBean.getUserName()+"生成定时报表，生成定时报表的历史信息");
                                logsBean.setTime(DateUtils.getNowTimeSecond());
                                logsDao.insertLogs(logsBean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            reportHistoryDao.deleteReportHistory(beanHistory.getId());
                        }
                    } else {
                        logger.error("this name has been used so we didn't product your report!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public void productWordByWeek() {
        try {
            long nowTime = System.currentTimeMillis();
            List<ReportPlanBean> listBean = reportPlanDao.selectAllNeedReportPlan(3);
            for (ReportPlanBean beanQ : listBean) {
                SystemUserBean userBean = systemUserDao.getUserBeanById(beanQ.getUserId());
                
                List<ReportBusinessBean> listBus = reportPlanDao.selectAllReportBusinessByPlanId(beanQ.getId());
                String moduleIds = reportModalService.selectModuleIds(beanQ.getModalId());
                if(1==listBus.size()&&listBus.get(0).getBussinessId()==0){
                    listBus.clear();
                    if(2==userBean.getRoleId()){
                        if(moduleIds.contains("12")){
                            String  serverStr = authorizeJurisService.selectModuleRole(beanQ.getUserId(), 12, 1);
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
                            String  climentStr= authorizeJurisService.selectModuleRole(beanQ.getUserId(), 11, 1);
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
                    }else if(1==userBean.getRoleId()){
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
                beanQ.setListBus(listBus);
                
                String watchPoint2 = beanQ.getWatchpointIds();
                String[] watchPoints2 = watchPoint2.split(",");
                if(watchPoints2.length==1&&watchPoints2[0].equals("0")){
                    watchPoint2 = "";
                    if(1==userBean.getRoleId()){
                        List<WatchpointBean> listWatchpoint = watchpointDao.getFindAll();
                        if(null!=listWatchpoint&&listWatchpoint.size()>0){
                            for(WatchpointBean juris : listWatchpoint){
                                watchPoint2 += juris.getId() + ",";
                            }
                            watchPoint2 =  watchPoint2.substring(0, watchPoint2.length()-1);
                        }
                    }else if(2==userBean.getRoleId()){
                        watchPoint2= authorizeJurisService.selectModuleRole(userBean.getId(), 10, 1);
                    }
                    beanQ.setWatchpointIds(watchPoint2);
                }
                
                Map<String, String> nameMap = new HashMap<>();
                List<DrawingOptionsBean> list = getBookBeanFromMySQLDao.getDrawingOptionsBeanList(beanQ.getModalId());
                List<ReportModalTableAndWranBean> listTableAndWarning = reportModalTableAndWarnDao
                        .selectReportModalTableAndWranBeans(beanQ.getModalId());

                SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                String path = HavingTimeProductNewWordController.class.getClassLoader().getResource("report").getPath();
                String rootPath = this.getClass().getClassLoader().getResource("/").getPath();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                String weekhand = sdf.format(cal.getTime());
                Date date = sdf.parse(weekhand);
                long longDate = date.getTime();
                ReportHistoryBean beanHistory = new ReportHistoryBean();
                beanHistory.setName(beanQ.getName() + "（周报）" + sdf.format(cal.getTime()));
                beanHistory.setTypeId(3);
                beanHistory.setUserId(beanQ.getUserId());
                beanHistory.setCreatetime((System.currentTimeMillis() / 1000));
                beanHistory.setDatastime((longDate / 1000 - 7 * 24 * 60 * 60));
                beanHistory.setDataetime((longDate / 1000));
                beanHistory.setSendStatus(1);
                List<ReportEmailBean> listEmail = reportEmailDao.selectReportEmails(beanQ.getId());
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
                int ds = reportHistoryDao.insertReportHistory(beanHistory);
                if (ds != 0) {
                    Map<String, List<List<ReportListBean>>> listListMap = new HashMap<>();
                    List<List<ReportListBean>> watchmps = new ArrayList<>();
                    List<List<ReportListBean>> servermaps = new ArrayList<>();
                    List<List<ReportListBean>> clientmaps = new ArrayList<>();
                    int volantenner = beanQ.getCompareToLastOne();
                    try {
                        String watchPoint = beanQ.getWatchpointIds();
                        String[] watchPoints = watchPoint.split(",");
                        Integer modalId = beanQ.getModalId();
                        List<JSONObject> listJson = new ArrayList<>();
                        for (int se = 0; se < watchPoints.length; se++) {
                            WatchpointBean watchpointBean1 = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                            if(null!=watchpointBean1){
                                ReportModalTableAndWranBean beanTableWatchpoing = reportModalTableAndWarnDao
                                        .selectReportModalTableBeanByTwo(beanQ.getModalId());
                                ReportBusinessBean beanwatchpointBean = new ReportBusinessBean();
                                beanwatchpointBean.setBussinessId(Integer.parseInt(watchPoints[se]));
                                beanwatchpointBean.setModulId(10);
                                beanwatchpointBean.setWatchPointId(Integer.parseInt(watchPoints[se]));
                                listBus.add(beanwatchpointBean);
                                if (null != beanTableWatchpoing && null != beanTableWatchpoing.getTableHaving() 
                                        && 1 == beanTableWatchpoing.getTableHaving()) {
                                    List<ReportListBean> listTableBeane = commonService.getReportWatchpointKpiList(
                                                    beanHistory.getDatastime(), beanHistory.getDataetime(), watchPoints[se]);
                                    if (2 == volantenner) {
                                        String op = listTableBeane.get(0).getName();
                                        listTableBeane.get(0).setName(op + "（本周）");
                                        List<ReportListBean> listTableBeane2 = commonService.getReportWatchpointKpiList(
                                                        beanHistory.getDatastime() - 7 * 24 * 60 * 60, 
                                                        beanHistory.getDataetime() - 7 * 24 * 60 * 60, watchPoints[se]);
                                        listTableBeane.addAll(listTableBeane2);
                                        if(listTableBeane2.size()>0){
                                            String op2 = listTableBeane.get(1).getName();
                                            listTableBeane.get(1).setName(op2 + "（上周）");
                                        }
                                    }
                                    watchmps.add(listTableBeane);
                                }
                                ReportModalTableAndWranBean beanWarnWatchpoing = reportModalTableAndWarnDao
                                        .selectReportModalWranBeanByTwo(beanQ.getModalId());
                                if (null != beanWarnWatchpoing) {
                                    if (null != beanWarnWatchpoing.getWarningHaving()&& 1 == beanWarnWatchpoing.getWarningHaving()) {
                                        ReportAlarmBean beanAlarm1 = new ReportAlarmBean();
                                        beanAlarm1.setBusinessId(Integer.parseInt(watchPoints[se]));
                                        beanAlarm1.setEndtime(beanHistory.getDataetime()-1);
                                        beanAlarm1.setModuleId(10);
                                        beanAlarm1.setType(3);
                                        beanAlarm1.setSpot(7);
                                        beanAlarm1.setStarttime(beanHistory.getDatastime());
                                        beanAlarm1.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        String warnJson = "";
                                        if (2 == volantenner) {
                                            beanAlarm1.setRingRatioEnd(beanHistory.getDataetime() - 7 * 24 * 60 * 60-1);
                                            beanAlarm1.setRingRatioStart(beanHistory.getDatastime() - 7 * 24 * 60 * 60);
                                            beanAlarm1.setName("本周");
                                            beanAlarm1.setRingRatioName("上周");
                                            warnJson = reportAlarmService.getReportRingRatioData(beanAlarm1);
                                        } else {
                                            warnJson = reportAlarmService.getReportAlarmData(beanAlarm1);
                                        }
                                        String jsonName2 = "10!" + watchPoints[se] + "!0!" + Integer.parseInt(watchPoints[se]) + "!0";
                                        WatchpointBean watchpointBean = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                        String fileXname2 = "观察点-" + watchpointBean.getName() + "-告警";
                                        CreateNewTextUtil.createJsonOfTable(
                                                rootPath + File.separator + "wordtext" + File.separator + beanHistory.getId(), jsonName2, warnJson);
                                        nameMap.put(jsonName2 + ".png", fileXname2);
                                    }
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
                                    jsonObject.put("name", watchpointBean1.getName());
                                    for(int xo = 0; xo < kpiIds.length; xo++){
                                        alarmLogBean.setkId(kpiIds[xo]);
                                        Map<String, Long> mapAlarm;
                                        try {
                                            mapAlarm = alarmLogService.getAlarmLogCount(alarmLogBean);
                                            if(null!=mapAlarm){
                                                jsonObject.put(kpiIds[xo]+"", mapAlarm.get("count"));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                    listJson.add(jsonObject);
                                }
                                for (ReportBusinessBean beanBus : listBus) {
                                    AppBusinessBean appBusinessBean = serverManagementService.getServerSide(beanBus.getBussinessId());
                                    if(modalId <= 2 && 10 != beanBus.getModulId()){
                                        AlarmLogBean alarmLogBean = new AlarmLogBean();
                                        alarmLogBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        alarmLogBean.setBusinessId(beanBus.getBussinessId());
                                        alarmLogBean.setModuleId(beanBus.getModulId());
                                        alarmLogBean.setStarttime(beanHistory.getDatastime());
                                        alarmLogBean.setEndtime(beanHistory.getDataetime());
                                        alarmLogBean.setKpitype(1);
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("name", watchpointBean1.getName()+"-"+appBusinessBean.getName());
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
                                    if (null != listTableAndWarning&& listTableAndWarning.size() > 0) {
                                        if(null!=appBusinessBean){
                                            for (ReportModalTableAndWranBean beanTandW : listTableAndWarning) {
                                                if (null != beanTandW.getTableHaving() && 11 == beanBus.getModulId() 
                                                        && 11 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = checkAndDoWeek(volantenner, beanHistory,
                                                            Integer.parseInt(watchPoints[se]), beanBus);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    clientmaps.add(listTableBeanNew);
                                                } else if (null != beanTandW.getTableHaving() && 12 == beanBus.getModulId()
                                                        && 12 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = checkAndDoWeek(volantenner, beanHistory,
                                                            Integer.parseInt(watchPoints[se]), beanBus);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    servermaps.add(listTableBeanNew);
                                                } else if (null != beanTandW.getWarningHaving() && 10!=beanTandW.getModultId() 
                                                        && beanBus.getModulId() == beanTandW.getModultId()) {
                                                    Map<Integer, String> mapStr = checkWarnAndDaoDate(
                                                            beanBus, beanHistory, Integer.parseInt(watchPoints[se]), volantenner, appBusinessBean, 3);
                                                    CreateNewTextUtil.createJsonOfTable(rootPath + File.separator+ "wordtext" 
                                                            + File.separator+ beanHistory.getId(), mapStr.get(2), mapStr.get(1));
                                                    nameMap.put(mapStr.get(2) + ".png", mapStr.get(3));
                                                }
                                            }
                                        }
                                    }
                                    if (null!=appBusinessBean && null != list && ds > 0 && list.size() > 0) {
                                        for (DrawingOptionsBean bean : list) {
                                            if (beanBus.getModulId() == bean.getModleId() && null!= appBusinessBean) {
                                                Map<Integer, Object> mapCan = new HashMap<>();
                                                mapCan.put(1, beanQ);
                                                mapCan.put(2, appBusinessBean);
                                                mapCan.put(3, beanHistory);
                                                mapCan.put(4, rootPath);
                                                mapCan.put(5, nameMap);
                                                nameMap = checkMapAndDoDate(bean, beanBus, longDate, 
                                                        Integer.parseInt(watchPoints[se]), watchpointBean1, mapCan);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (null != watchmps) {
                            listListMap.put("10", watchmps);
                        }
                        if (null != servermaps) {
                            listListMap.put("12", servermaps);
                        }
                        if (null != clientmaps) {
                            listListMap.put("11", clientmaps);
                        }
                        ChangeJsonToPictureUtil.translateJsonToPicture(rootPath
                                + File.separator + "wordtext" + File.separator + beanHistory.getId(), rootPath
                                + File.separator + "wordpicture" + File.separator + beanHistory.getId());
                        CleanAllTextUtil.cleanFile(rootPath + File.separator
                                + "wordtext" + File.separator + beanHistory.getId());
                        WordBean beanWord = new WordBean(beanQ.getName()
                                + sdf.format(cal.getTime()), sdfm.format(nowTime), "定时周报", null,
                                userBean.getRealName(), sdfm.format(longDate - 7 * 24 * 60 * 60 * 1000),
                                sdfm.format(longDate), rootPath + "wordbar" + File.separator + beanHistory.getId());
                        if(null!=listJson && listJson.size()>0){
                            beanWord.setJsonObjects(listJson);
                        }
                        beanWord.setMap(nameMap);
                        String really = new String((beanQ.getName() + sdf.format(cal.getTime()) + "（周报）").getBytes("iso-8859-1"), "utf-8");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("picturePath", rootPath + File.separator + "wordpicture" + File.separator + beanHistory.getId());
                        map.put("modelPath", rootPath + "wordmodle" + File.separator + "joanna.docx");
                        map.put("pathpp", path + File.separator + beanHistory.getId().toString());
                        ExportWordServiceUtil.getTheWarningWord(beanWord, really, plotService, map, nameMap, listListMap);
                        File file = new File(path + File.separator + beanHistory.getId() + File.separator + really + ".docx");
                        CleanAllTextUtil.cleanFile(rootPath + "wordpicture" + File.separator + beanHistory.getId());
                        if (!file.exists()) {
                            reportHistoryDao.deleteReportHistory(beanHistory.getId());
                        } else {
                            Long nextTime = GetTimeUtil.getNextSendTimeByType(3);
                            boolean resultSend = true;
                            if (null != listEmail && listEmail.size() > 0) {
                                for (ReportEmailBean emailBena : listEmail) {
                                    try {
                                        EmailBean emailBean = emailService.getEmail();
                                        mailSendService.send(emailBean, emailBena, path + File.separator
                                                + beanHistory.getId() + File.separator + really + ".docx", beanQ.getName() + ".docx");
                                    } catch (Exception e) {
                                        resultSend = false;
                                    }
                                }
                                if(resultSend){
                                    long sendTime = System.currentTimeMillis();
                                    reportHistoryService.updateHistoryBeanBySend(sendTime/1000, beanHistory.getId());
                                    reportPlanDao.addReportPlanSendTimes(beanQ.getId());
                                }
                            }
                            reportPlanDao.updateReportPlanNextSendTime(beanQ.getId(), nextTime/1000);
                            LogsBean logsBean = new LogsBean();
                            logsBean.setUserId(userBean.getId());
                            logsBean.setModuleId(19);
                            logsBean.setMsg(userBean.getUserName()+"生成定时报表，生成报表的历史信息");
                            logsBean.setTime(DateUtils.getNowTimeSecond());
                            logsDao.insertLogs(logsBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CleanAllTextUtil.cleanFile(rootPath + File.separator + "wordtext" + File.separator + beanHistory.getId());
                        CleanAllTextUtil.cleanFile(rootPath + "wordpicture" + File.separator + beanHistory.getId());
                        reportHistoryDao.deleteReportHistory(beanHistory.getId());
                    }
                } else {
                    logger.error("this name has been used so we didn't product your report!");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public void productWordByMonth() {
        try {
            long nowTime = System.currentTimeMillis();
            List<ReportPlanBean> listBean = reportPlanDao
                    .selectAllNeedReportPlan(4);
            for (ReportPlanBean beanQ : listBean) {
                SystemUserBean userBean = systemUserDao.getUserBeanById(beanQ.getUserId());
                
                List<ReportBusinessBean> listBus = reportPlanDao.selectAllReportBusinessByPlanId(beanQ.getId());
                String moduleIds = reportModalService.selectModuleIds(beanQ.getModalId());
                if(1==listBus.size()&&listBus.get(0).getBussinessId()==0){
                    listBus.clear();
                    if(2==userBean.getRoleId()){
                        if(moduleIds.contains("12")){
                            String  serverStr = authorizeJurisService.selectModuleRole(beanQ.getUserId(), 12, 1);
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
                            String  climentStr= authorizeJurisService.selectModuleRole(beanQ.getUserId(), 11, 1);
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
                    }else if(1==userBean.getRoleId()){
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
                beanQ.setListBus(listBus);
                
                String watchPoint2 = beanQ.getWatchpointIds();
                String[] watchPoints2 = watchPoint2.split(",");
                if(watchPoints2.length==1&&watchPoints2[0].equals("0")){
                    watchPoint2 = "";
                    if(1==userBean.getRoleId()){
                        List<WatchpointBean> listWatchpoint = watchpointDao.getFindAll();
                        if(null!=listWatchpoint&&listWatchpoint.size()>0){
                            for(WatchpointBean juris : listWatchpoint){
                                watchPoint2 += juris.getId() + ",";
                            }
                            watchPoint2 =  watchPoint2.substring(0, watchPoint2.length()-1);
                        }
                    }else if(2==userBean.getRoleId()){
                        watchPoint2= authorizeJurisService.selectModuleRole(userBean.getId(), 10, 1);
                    }
                    beanQ.setWatchpointIds(watchPoint2);
                }
                
                Map<String, String> nameMap = new HashMap<>();
                List<DrawingOptionsBean> list = getBookBeanFromMySQLDao.getDrawingOptionsBeanList(beanQ.getModalId());
                List<ReportModalTableAndWranBean> listTableAndWarning = reportModalTableAndWarnDao
                        .selectReportModalTableAndWranBeans(beanQ.getModalId());

                String path = HavingTimeProductNewWordController.class.getClassLoader().getResource("report").getPath();
                String rootPath = this.getClass().getClassLoader().getResource("/").getPath();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, -1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                Date date2 = format.parse(format.format(cal.getTime()));
                long longDateq = date2.getTime();
                int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), maxDay, 23, 59, 59);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                Date date = sdf.parse(sdf.format(cal.getTime()));
                long longDate = date.getTime();
                ReportHistoryBean beanHistory = new ReportHistoryBean();
                beanHistory.setName(beanQ.getName() + "（月报）" + format.format(cal.getTime()));
                beanHistory.setTypeId(4);
                beanHistory.setCreatetime((System.currentTimeMillis() / 1000));
                beanHistory.setDatastime((longDateq / 1000));
                beanHistory.setUserId(beanQ.getUserId());
                beanHistory.setDataetime((longDate / 1000));
                beanHistory.setSendStatus(1);
                List<ReportEmailBean> listEmail = reportEmailDao.selectReportEmails(beanQ.getId());
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
                
                int qe = reportHistoryDao.insertReportHistory(beanHistory);
                if (0 != qe) {
                    Map<String, List<List<ReportListBean>>> listListMap = new HashMap<>();
                    List<List<ReportListBean>> watchmps = new ArrayList<>();
                    List<List<ReportListBean>> servermaps = new ArrayList<>();
                    List<List<ReportListBean>> clientmaps = new ArrayList<>();
                    int volantenner = beanQ.getCompareToLastOne();
                    int maxDate = 0;
                    Calendar a = Calendar.getInstance();
                    a.set(Calendar.DATE, 1);
                    a.roll(Calendar.DATE, -1);
                    maxDate = a.get(Calendar.DATE);
                    try {
                        String watchPoint = beanQ.getWatchpointIds();
                        String[] watchPoints = watchPoint.split(",");
                        Integer modalId = beanQ.getModalId();
                        List<JSONObject> listJson = new ArrayList<>();
                        for (int se = 0; se < watchPoints.length; se++) {
                            WatchpointBean watchpointBean1 = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                            if(null!=watchpointBean1){
                                ReportBusinessBean beanwatchpointBean = new ReportBusinessBean();
                                beanwatchpointBean.setBussinessId(Integer.parseInt(watchPoints[se]));
                                beanwatchpointBean.setModulId(10);
                                beanwatchpointBean.setWatchPointId(Integer.parseInt(watchPoints[se]));
                                listBus.add(beanwatchpointBean);
                                ReportModalTableAndWranBean beanTableWatchpoing = reportModalTableAndWarnDao.
                                        selectReportModalTableBeanByTwo(beanQ.getModalId());
                                if (null != beanTableWatchpoing && null != beanTableWatchpoing.getTableHaving() 
                                        && 1 == beanTableWatchpoing.getTableHaving()) {
                                    List<ReportListBean> listTableBeane = commonService.getReportWatchpointKpiList(beanHistory.getDatastime(),
                                                    beanHistory.getDataetime(), watchPoints[se]);
                                    if (2 == volantenner) {
                                        String op = listTableBeane.get(0).getName();
                                        listTableBeane.get(0).setName(op + ("（本月）"));
                                        List<ReportListBean> listTableBeane2 = commonService
                                                .getReportWatchpointKpiList(beanHistory.getDatastime() - 24 * 60 * 60, 
                                                        beanHistory.getDataetime() - maxDate * 24 * 60 * 60, watchPoints[se]);
                                        listTableBeane.addAll(listTableBeane2);
                                        if(listTableBeane2.size()>0){
                                            String op2 = listTableBeane.get(1).getName();
                                            listTableBeane.get(1).setName(op2 + ("（上月）"));
                                        }
                                    }
                                    watchmps.add(listTableBeane);
                                }
                                ReportModalTableAndWranBean beanWarnWatchpoing = reportModalTableAndWarnDao
                                        .selectReportModalWranBeanByTwo(beanQ.getModalId());
                                if (null != beanWarnWatchpoing) {
                                    if (null != beanWarnWatchpoing.getWarningHaving()&& 1 == beanWarnWatchpoing.getWarningHaving()) {
                                        ReportAlarmBean beanAlarm1 = new ReportAlarmBean();
                                        beanAlarm1.setBusinessId(Integer.parseInt(watchPoints[se]));
                                        beanAlarm1.setEndtime(beanHistory.getDataetime());
                                        beanAlarm1.setModuleId(10);
                                        beanAlarm1.setSpot(maxDate);
                                        beanAlarm1.setType(4);
                                        beanAlarm1.setStarttime(beanHistory.getDatastime());
                                        beanAlarm1.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        String warnJson = "";
                                        if (2 == volantenner) {
                                            beanAlarm1.setRingRatioEnd(beanHistory.getDataetime() - maxDate * 24 * 60 * 60);
                                            beanAlarm1.setRingRatioStart(beanHistory.getDatastime() - maxDate * 24 * 60 * 60);
                                            beanAlarm1.setName("本月");
                                            beanAlarm1.setRingRatioName("上月");
                                            warnJson = reportAlarmService.getReportRingRatioData(beanAlarm1);
                                        } else {
                                            warnJson = reportAlarmService.getReportAlarmData(beanAlarm1);
                                        }
                                        String jsonName2 = "10!" + watchPoints[se] + "!0!" + Integer.parseInt(watchPoints[se]) + "!0";
                                        WatchpointBean watchpointBean = watchpointServer.getWatchpointById(Integer.parseInt(watchPoints[se]));
                                        String fileXname2 = "观察点-" + watchpointBean.getName() + "-告警";
                                        CreateNewTextUtil.createJsonOfTable(
                                                rootPath + File.separator + "wordtext" + File.separator + beanHistory.getId(), jsonName2, warnJson);
                                        nameMap.put(jsonName2 + ".png", fileXname2);
                                    }
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
                                    jsonObject.put("name", watchpointBean1.getName());
                                    for(int xo = 0; xo < kpiIds.length; xo++){
                                        alarmLogBean.setkId(kpiIds[xo]);
                                        Map<String, Long> mapAlarm;
                                        try {
                                            mapAlarm = alarmLogService.getAlarmLogCount(alarmLogBean);
                                            if(null!=mapAlarm){
                                                jsonObject.put(kpiIds[xo]+"", mapAlarm.get("count"));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                    listJson.add(jsonObject);
                                }
                                for (ReportBusinessBean beanBus : listBus) {
                                    AppBusinessBean appBusinessBean = serverManagementService.getServerSide(beanBus.getBussinessId());
                                    if(modalId <= 2 && 10 != beanBus.getModulId()){
                                        AlarmLogBean alarmLogBean = new AlarmLogBean();
                                        alarmLogBean.setWatchpointId(Integer.parseInt(watchPoints[se]));
                                        alarmLogBean.setBusinessId(beanBus.getBussinessId());
                                        alarmLogBean.setModuleId(beanBus.getModulId());
                                        alarmLogBean.setStarttime(beanHistory.getDatastime());
                                        alarmLogBean.setEndtime(beanHistory.getDataetime());
                                        alarmLogBean.setKpitype(1);
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("name", watchpointBean1.getName()+"-"+appBusinessBean.getName());
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
                                    if (null != listTableAndWarning&& listTableAndWarning.size() > 0) {
                                        if(null!=appBusinessBean){
                                            for (ReportModalTableAndWranBean beanTandW : listTableAndWarning) {
                                                if (null != beanTandW.getTableHaving() && 11 == beanBus.getModulId() 
                                                        && 11 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = checkAndDoMonth(volantenner, beanHistory,
                                                            Integer.parseInt(watchPoints[se]), beanBus, maxDate);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    clientmaps.add(listTableBeanNew);
                                                } else if (null != beanTandW.getTableHaving() && 12 == beanBus.getModulId() 
                                                        && 12 == beanTandW.getModultId()) {
                                                    List<ReportListBean> listTableBean = checkAndDoMonth(volantenner, beanHistory,
                                                            Integer.parseInt(watchPoints[se]), beanBus, maxDate);
                                                    List<ReportListBean> listTableBeanNew = new ArrayList<>();
                                                    for(ReportListBean beanTable : listTableBean){
                                                        String nameOfTableBean = beanTable.getName();
                                                        beanTable.setName(watchpointBean1.getName()+"-"+nameOfTableBean);
                                                        listTableBeanNew.add(beanTable);
                                                    }
                                                    servermaps.add(listTableBeanNew);
                                                } else if (null != beanTandW.getWarningHaving()&&10!=beanTandW.getModultId() 
                                                        && beanBus.getModulId() == beanTandW.getModultId()) {
                                                    Map<Integer, String> mapStr = checkWarnAndMonth(
                                                            beanBus, beanHistory, Integer.parseInt(watchPoints[se]), 
                                                            volantenner, appBusinessBean, maxDate);
                                                    CreateNewTextUtil.createJsonOfTable(rootPath + File.separator+ "wordtext" 
                                                            + File.separator+ beanHistory.getId(), mapStr.get(2), mapStr.get(1));
                                                    nameMap.put(mapStr.get(2) + ".png", mapStr.get(3));
                                                }
                                            }
                                        }
                                    }
                                    for (DrawingOptionsBean bean : list) {
                                        if (beanBus.getModulId() == bean.getModleId()) {
                                            if(null!=appBusinessBean){
                                                Map<Integer, Object> mapCan = new HashMap<>();
                                                mapCan.put(1, beanQ);
                                                mapCan.put(2, appBusinessBean);
                                                mapCan.put(3, beanHistory);
                                                mapCan.put(4, rootPath);
                                                mapCan.put(5, nameMap);
                                                mapCan.put(6, maxDate);
                                                nameMap = checkMapAndDoDate(bean, beanBus, longDate, 
                                                        Integer.parseInt(watchPoints[se]), watchpointBean1, mapCan);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (null != watchmps) {
                            listListMap.put("10", watchmps);
                        }
                        if (null != servermaps) {
                            listListMap.put("12", servermaps);
                        }
                        if (null != clientmaps) {
                            listListMap.put("11", clientmaps);
                        }
                        ChangeJsonToPictureUtil.translateJsonToPicture(
                                rootPath + "wordtext" + File.separator + beanHistory.getId(), rootPath
                                        + "wordpicture" + File.separator + beanHistory.getId());
                        CleanAllTextUtil.cleanFile(rootPath + "wordtext"
                                + File.separator + beanHistory.getId());
                        WordBean beanWord = new WordBean(beanQ.getName()
                                + format.format(cal.getTime()), sdf.format(nowTime), "定时月报", null,
                                userBean.getRealName(), sdf.format(longDateq),
                                sdf.format(longDate + 1000), rootPath + "wordbar" + File.separator + beanHistory.getId());
                        if(null!=listJson && listJson.size()>0){
                            beanWord.setJsonObjects(listJson);
                        }
                        beanWord.setMap(nameMap);
                        String really = new String(
                                (beanQ.getName() + format.format(cal.getTime()) + "(月报)").getBytes("iso-8859-1"), "utf-8");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("picturePath", rootPath + File.separator + "wordpicture" + File.separator + beanHistory.getId());
                        map.put("modelPath", rootPath + "wordmodle" + File.separator + "joanna.docx");
                        map.put("pathpp", path + File.separator + beanHistory.getId().toString());
                        ExportWordServiceUtil.getTheWarningWord(beanWord, really, plotService, map, nameMap, listListMap);
                        File file = new File(path + File.separator + beanHistory.getId().toString()
                                + File.separator + really + ".docx");
                        CleanAllTextUtil.cleanFile(rootPath + "wordpicture" + File.separator + beanHistory.getId());
                        if (!file.exists()) {
                            reportHistoryDao.deleteReportHistory(beanHistory.getId());
                        } else {
                            Long nextTime = GetTimeUtil.getNextSendTimeByType(4);
                            boolean resultSend = true;
                            if (null != listEmail && listEmail.size() > 0) {
                                for (ReportEmailBean emailBena : listEmail) {
                                    try {
                                        EmailBean emailBean = emailService.getEmail();
                                        mailSendService.send(emailBean, emailBena, path + File.separator
                                                + beanHistory.getId() + File.separator + really + ".docx", beanQ.getName() + ".docx");
                                    } catch (Exception e) {
                                        resultSend = false;
                                    }
                                }
                                if(resultSend){
                                    long sendTime = System.currentTimeMillis();
                                    reportHistoryService.updateHistoryBeanBySend(sendTime/1000, beanHistory.getId());
                                    reportPlanDao.addReportPlanSendTimes(beanQ.getId());
                                }
                            }
                            reportPlanDao.updateReportPlanNextSendTime(beanQ.getId(), nextTime/1000);
                            LogsBean logsBean = new LogsBean();
                            logsBean.setUserId(userBean.getId());
                            logsBean.setModuleId(19);
                            logsBean.setMsg(userBean.getUserName()+"生成定时报表,生成报表的历史记录");
                            logsBean.setTime(DateUtils.getNowTimeSecond());
                            logsDao.insertLogs(logsBean);
                        }
                    } catch (Exception e) {
                        CleanAllTextUtil.cleanFile(rootPath + File.separator + "wordtext" + File.separator + beanHistory.getId());
                        CleanAllTextUtil.cleanFile(rootPath + "wordpicture" + File.separator + beanHistory.getId());
                        reportHistoryDao.deleteReportHistory(beanHistory.getId());
                    }
                } else {
                    logger.error("this name has been used so we didn't product your report!");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 
     * @Title: changeTwoToOne
     * @Description: 
     * @param map1
     * @param map2
     * @param typeId
     * @return Map<String,Object>
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> changeTwoToOne(Map<String, Object> map1,
            Map<String, Object> map2, Integer typeId) {
        String thisone = "";
        String lastone = "";
        try {
            List<Map<String, Object>> list1 = (List<Map<String, Object>>) map1
                    .get("data");
            Map<String, Object> maping1 = null;
            List<SimpleEntry<Double, Double>> listMap1 = null;
            if(list1.size()>0){
                maping1 = list1.get(0);
                maping1.put("id", 1);
            }
            String dawValue1 = (String) map1.get("unit");
            if(null!=maping1){
                listMap1 = (List<SimpleEntry<Double, Double>>) maping1.get("value");
            }
            double max1 = 0;
            double min1 = 0;
            double eve1 = 0;
            int x1 = 0;
            if (null != listMap1) {
                for (SimpleEntry<Double, Double> entry1 : listMap1) {
                    if (entry1.getValue() > max1) {
                        max1 = entry1.getValue();
                    }
                    if (0 == x1) {
                        min1 = entry1.getValue();
                    }
                    if (entry1.getValue() < min1) {
                        min1 = entry1.getValue();
                    }
                    eve1 += entry1.getValue();
                    x1++;
                }
            }
            String subTitle1;
            SimpleEntry<Double, String> simpleEntryMax1 = UnitUtils.numFormat(
                    max1, dawValue1);
            SimpleEntry<Double, String> simpleEntryMin1 = UnitUtils.numFormat(
                    min1, dawValue1);
            if (x1 == 0) {
                subTitle1 = "最大值：" + simpleEntryMax1.getKey()
                        + simpleEntryMax1.getValue() + "-最小值："
                        + simpleEntryMin1.getKey() + simpleEntryMin1.getValue();
            } else {
                SimpleEntry<Double, String> simpleEntryEve1 = UnitUtils
                        .numFormat(eve1 / x1, dawValue1);
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
                String deleteEveLastTwo1 = df.format(simpleEntryEve1.getKey());
                if(!"%".equals(simpleEntryEve1.getValue())){
                    subTitle1 = "最大值：" + simpleEntryMax1.getKey()
                            + simpleEntryMax1.getValue() + " 平均值："
                            + deleteEveLastTwo1 + simpleEntryEve1.getValue()
                            + " 最小值：" + simpleEntryMin1.getKey()
                            + simpleEntryMin1.getValue();
                }else{
                    subTitle1 = "最大值：" + simpleEntryMax1.getKey()
                            + simpleEntryMax1.getValue()
                            + " 最小值：" + simpleEntryMin1.getKey()
                            + simpleEntryMin1.getValue();
                }
            }
            String dawValue2 = (String) map2.get("unit");
            List<Map<String, Object>> list2 = (List<Map<String, Object>>) map2.get("data");
            Map<String, Object> mapingn2 = null;
            List<SimpleEntry<Long, Double>> listMap2 = null;
            if(list2.size()>0){
                mapingn2 = list2.get(0);
            }
            Map<String, Object> maping = new HashMap<>();
            if(null!=mapingn2){
                listMap2 = (List<SimpleEntry<Long, Double>>) mapingn2.get("value");
            }
            List<SimpleEntry<Long, Double>> listMao3 = new ArrayList<>();
            double max2 = 0;
            double min2 = 0;
            double eve2 = 0;
            int x2 = 0;
            if (2 == typeId) {
                thisone = "今日";
                lastone = "昨日"; 
            } else if (3 == typeId) {
                thisone = "本周";
                lastone = "上周";
            } else if (4 == typeId) {
                thisone = "本月";
                lastone = "上月";
            }
            
            if (null != listMap2) {
                for (SimpleEntry<Long, Double> entry2 : listMap2) {
                    SimpleEntry<Long, Double> simple2 = null;
                    if (2 == typeId) {
                        thisone = "今日";
                        lastone = "昨日";
                        Long key = entry2.getKey() + 24 * 60 * 60;
                        simple2 = new SimpleEntry<>(key, entry2.getValue());
                    } else if (3 == typeId) {
                        thisone = "本周";
                        lastone = "上周";
                        Long key = entry2.getKey() + 24 * 60 * 60 * 7;
                        simple2 = new SimpleEntry<>(key, entry2.getValue());
                    } else if (4 == typeId) {
                        thisone = "本月";
                        lastone = "上月";
                        Calendar a = Calendar.getInstance();
                        a.set(Calendar.DATE, 1);
                        a.roll(Calendar.DATE, -1);
                        int maxDate = a.get(Calendar.DATE);
                        Long key = entry2.getKey() + 24 * 60 * 60 * maxDate;
                        simple2 = new SimpleEntry<>(key, entry2.getValue());
                    }

                    if (entry2.getValue() > max2) {
                        max2 = entry2.getValue();
                    }
                    if (0 == x2) {
                        min2 = entry2.getValue();
                    }
                    if (entry2.getValue() < min2) {
                        min2 = entry2.getValue();
                    }
                    eve2 += entry2.getValue();
                    x2++;
                    listMao3.add(simple2);
                }
            }
            maping.put("id", 2);
            maping.put("value", listMao3);
            maping.put("name", lastone);
            String subTitle2;
            SimpleEntry<Double, String> simpleEntryMax2 = UnitUtils.numFormat(
                    max2, dawValue2);
            SimpleEntry<Double, String> simpleEntryMin2 = UnitUtils.numFormat(
                    min2, dawValue2);
            if (x2 == 0) {
                subTitle2 = "最大值：" + simpleEntryMax2.getKey()
                        + simpleEntryMax2.getValue() + "-最小值："
                        + simpleEntryMin2.getKey() + simpleEntryMin2.getValue();
            } else {
                SimpleEntry<Double, String> simpleEntryEve2 = UnitUtils
                        .numFormat(eve2 / x2, dawValue2);
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
                String deleteEveLastTwo2 = df.format(simpleEntryEve2.getKey());
                if(!"%".equals(simpleEntryMax2.getValue())){
                    subTitle2 = "最大值：" + simpleEntryMax2.getKey()
                            + simpleEntryMax2.getValue() + " 平均值："
                            + deleteEveLastTwo2 + simpleEntryEve2.getValue()
                            + " 最小值：" + simpleEntryMin2.getKey()
                            + simpleEntryMin2.getValue();
                }else{
                    subTitle2 = "最大值：" + simpleEntryMax2.getKey()
                            + simpleEntryMax2.getValue()
                            + " 最小值：" + simpleEntryMin2.getKey()
                            + simpleEntryMin2.getValue(); 
                }
            }
            map1.put("this", thisone + subTitle1);
            map1.put("last", lastone + subTitle2);
            list1.clear();
            if(null == maping1){
                maping1 = new HashMap<>();
                maping1.put("id", 1);
                maping1.put("name", thisone);
            }else{
                maping1.put("id", 1);
                maping1.put("name", thisone);
            }
            list1.add(maping1);
            list1.add(maping);
            map1.put("data", list1);
            return map1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * @Title: changeTwoToSimpleMap
     * @Description: 
     * @param map
     * @return Map<String,Object>
     * @author 刘斌
     */
    public Map<String, Object> changeTwoToSimpleMap(Map<String, Object> map){
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list1 = (List<Map<String, Object>>) map.get("data");
        Map<String, Object> map1 = new HashMap<>();
        Set<Map.Entry<String, Object>> entry = map.entrySet();
        Iterator<Map.Entry<String, Object>>  ite = entry.iterator();
        while(ite.hasNext()){
            Map.Entry<String, Object> en = ite.next();
            String key = en.getKey();
            Object value = en.getValue();
            map1.put(key, value);
        }
        
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> maping1 = list1.get(0);
        list.add(maping1);
        map1.put("data", list);
        return map1;
    }
    
    /**
     * 
     * @Title: checkAndDoDate
     * @Description: 
     * @param volantenner
     * @param beanHistory
     * @param wathcBeanId
     * @param beanBus
     * @return List<ReportListBean>
     * @author 刘斌
     */
    public List<ReportListBean> checkAndDoDate(Integer volantenner, ReportHistoryBean beanHistory,
            Integer wathcBeanId, ReportBusinessBean beanBus){
        List<ReportListBean> listTableBean = commonService
                .getOtherModuleKpiList(beanHistory.getDatastime(),
                        beanHistory.getDataetime(), beanBus.getModulId(), wathcBeanId,
                        beanBus.getBussinessId()+ "");
        if (2 == volantenner) {
            String op = listTableBean.get(0).getName();
            listTableBean.get(0).setName(op + "（本日）");
            List<ReportListBean> listTableBeane2 = commonService.getOtherModuleKpiList(
                            beanHistory.getDatastime() - 24 * 60 * 60,
                            beanHistory.getDataetime() - 24 * 60 * 60,
                            beanBus.getModulId(), wathcBeanId,
                            beanBus.getBussinessId() + "");
            listTableBean.addAll(listTableBeane2);
            String op2 = listTableBean.get(1).getName();
            listTableBean.get(1).setName(op2 + "（昨日）");
        }
        return listTableBean;
    }
    
    /**
     * 
     * @Title: checkAndDoWeek
     * @Description: 
     * @param volantenner
     * @param beanHistory
     * @param wathcBeanId
     * @param beanBus
     * @return List<ReportListBean>
     * @author 刘斌
     */
    public List<ReportListBean> checkAndDoWeek(Integer volantenner, ReportHistoryBean beanHistory,
            Integer wathcBeanId, ReportBusinessBean beanBus){
        List<ReportListBean> listTableBean = commonService
                .getOtherModuleKpiList(beanHistory.getDatastime(),
                        beanHistory.getDataetime(), beanBus.getModulId(), wathcBeanId,
                        beanBus.getBussinessId()+ "");
        if (2 == volantenner) {
            String op = listTableBean.get(0).getName();
            listTableBean.get(0).setName(op + "（本周）");
            List<ReportListBean> listTableBeane2 = commonService.getOtherModuleKpiList(
                            beanHistory.getDatastime() - 7 * 24 * 60 * 60,
                            beanHistory.getDataetime() - 7 * 24 * 60 * 60,
                            beanBus.getModulId(), wathcBeanId,
                            beanBus.getBussinessId() + "");
            listTableBean.addAll(listTableBeane2);
            String op2 = listTableBean.get(1).getName();
            listTableBean.get(1).setName(op2 + "（上周）");
        }
        return listTableBean;
    }
    
    /**
     * 
     * @Title: checkAndDoMonth
     * @Description: 
     * @param volantenner
     * @param beanHistory
     * @param wathcBeanId
     * @param beanBus
     * @param mathMax
     * @return List<ReportListBean>
     * @author 刘斌
     */
    public List<ReportListBean> checkAndDoMonth(Integer volantenner, ReportHistoryBean beanHistory,
            Integer wathcBeanId, ReportBusinessBean beanBus, Integer mathMax){
        List<ReportListBean> listTableBean = commonService
                .getOtherModuleKpiList(beanHistory.getDatastime(),
                        beanHistory.getDataetime(), beanBus.getModulId(), wathcBeanId,
                        beanBus.getBussinessId()+ "");
        if (2 == volantenner) {
            String op = listTableBean.get(0).getName();
            listTableBean.get(0).setName(op + "（本月）");
            List<ReportListBean> listTableBeane2 = commonService.getReportWatchpointKpiList(
                    beanHistory.getDatastime() - 24 * 60 * 60, beanHistory.getDataetime() - mathMax * 24 * 60 * 60, wathcBeanId + "");
            listTableBean.addAll(listTableBeane2);
            String op2 = listTableBean.get(1).getName();
            listTableBean.get(1).setName(op2 + "（上月）");
        }
        return listTableBean;
    }
    
    /**
     * 
     * @Title: checkWarnAndDaoDate
     * @Description: 
     * @param beanBus
     * @param beanHistory
     * @param watchpointId
     * @param volantenner
     * @param appBusinessBean
     * @param typeId
     * @return Map<Integer,String>
     * @author 刘斌
     */
    public Map<Integer, String> checkWarnAndDaoDate(
            ReportBusinessBean beanBus, ReportHistoryBean beanHistory, Integer watchpointId, 
            Integer volantenner, AppBusinessBean appBusinessBean, Integer typeId){
        Map<Integer, String> map = new HashMap<>();
        Integer mathDate = 1;
        Integer mathWarnBar = 24;
        String nowStr = "今日";
        String lastStr = "昨日";
        if(3 == typeId){
            mathDate = 7;
            nowStr = "本周";
            lastStr = "上周";
            mathWarnBar = 7;
        }
        
        ReportAlarmBean beanAlarm = new ReportAlarmBean();
        beanAlarm.setBusinessId(beanBus.getBussinessId());
        if(7 == mathDate){
            beanAlarm.setEndtime(beanHistory.getDataetime()-1);
        }else{
            beanAlarm.setEndtime(beanHistory.getDataetime());
        }
        beanAlarm.setModuleId(beanBus.getModulId());
        beanAlarm.setType(typeId);
        beanAlarm.setSpot(mathWarnBar);
        beanAlarm.setStarttime(beanHistory.getDatastime());
        beanAlarm.setWatchpointId(watchpointId);
        String warnJson = "";
        if (2 == volantenner) {
            if(7 == mathDate){
                beanAlarm.setRingRatioEnd(beanHistory.getDataetime() - mathDate * 24 * 60 * 60 - 1);
                beanAlarm.setRingRatioStart(beanHistory.getDatastime() - mathDate * 24 * 60 * 60); 
            }else{
                beanAlarm.setRingRatioEnd(beanHistory.getDataetime() - mathDate * 24 * 60 * 60);
                beanAlarm.setRingRatioStart(beanHistory.getDatastime() - mathDate * 24 * 60 * 60);
            }
            beanAlarm.setName(nowStr);
            beanAlarm.setRingRatioName(lastStr);
            warnJson = reportAlarmService.getReportRingRatioData(beanAlarm);
        } else {
            warnJson = reportAlarmService.getReportAlarmData(beanAlarm);
        }
        String jsonName1 = beanBus.getModulId()+ "!" + watchpointId+ "!0!"
                + beanBus.getBussinessId() + "!0";
        String fileXname1 = "";
        WatchpointBean watchpointBeanno = watchpointServer
                .getWatchpointById(watchpointId);
        if (11 == beanBus.getModulId()) {
            fileXname1 = "客户端-" + watchpointBeanno.getName() 
                    + "-" + appBusinessBean.getName()+ "-告警";
        } else if (12 == beanBus.getModulId()) {
            fileXname1 = "服务端-"+ watchpointBeanno.getName()
                    + "-" + appBusinessBean.getName()+ "-告警";
        }
        map.put(1, warnJson);
        map.put(2, jsonName1);
        map.put(3, fileXname1);
        return map;
        
    }
    
    /**
     * 
     * @Title: checkMapAndDoDate
     * @Description: 
     * @param bean
     * @param beanBus
     * @param longDate
     * @param watchpointId
     * @param watchpointBean1
     * @param mapCan
     * @return Map<String,String>
     * @author 刘斌
     */
    public Map<String, String> checkMapAndDoDate(DrawingOptionsBean bean, 
            ReportBusinessBean beanBus, Long longDate, Integer watchpointId, WatchpointBean watchpointBean1, Map<Integer, Object> mapCan){
        ReportPlanBean beanQ = (ReportPlanBean) mapCan.get(1);
        AppBusinessBean appBusinessBean = (AppBusinessBean) mapCan.get(2);
        ReportHistoryBean beanHistory = (ReportHistoryBean) mapCan.get(3);
        @SuppressWarnings("unchecked")
        Map<String, String> nameMap = (Map<String, String>) mapCan.get(5);
        String rootPath = (String) mapCan.get(4);
        Integer mathDay = 1;
        Integer typeId = beanQ.getTypeId();
        if(3 == typeId){
            mathDay = 7;
        }else if(4 == typeId){
            mathDay = (Integer) mapCan.get(6);
        }
        Map<String, Object> map2 = null;
        bean.setClientId(beanBus.getBussinessId());
        bean.setEndtime(longDate / 1000);
        bean.setStarttime(longDate / 1000 - mathDay * 24 * 60 * 60);
        bean.setWatchpointId(watchpointId);
        Map<String, Object> map = new HashMap<String, Object>();
        PlotOptionBean plotBean = plotService.getPlotOptionById(bean.getPlotId());
        String fileXname = "";
        String appName = "";
        if (10 == bean.getModleId()) {
            bean.setClientId(watchpointId);
            appName = watchpointBean1.getName() + "-";
            fileXname = "网络-" + appName + plotBean.getName();
            try {
                map = watchpointServer.getWatchpointGraphical(bean);
                if (2 == beanQ.getCompareToLastOne() && ints.contains(","+bean.getPlotId()+",")) {
                    DrawingOptionsBean bean2 = bean;
                    bean2.setEndtime(longDate / 1000 - mathDay * 24 * 60 * 60);
                    bean2.setStarttime(longDate / 1000 - mathDay * 48 * 60 * 60);
                    map2 = watchpointServer.getWatchpointGraphical(bean2);
                }
                
                if (2 == beanQ.getCompareToLastOne()&& ints2.contains(","+bean.getPlotId()+",")) {
                    DrawingOptionsBean bean2 = bean;
                    bean2.setEndtime(longDate / 1000 - mathDay * 24 * 60 * 60);
                    bean2.setStarttime(longDate / 1000 - mathDay * 48 * 60 * 60);
                    map2 = watchpointServer.getWatchpointGraphical(bean2);
                    map2 = changeTwoToSimpleMap(map2);
                    map = changeTwoToSimpleMap(map);
                }
            } catch (Exception e) {
                map.put("exeption", "yes");
            }
        } else {
            String watchPointBeanName = watchpointBean1.getName() + "-";
            String headString;
            if (11 == bean.getModleId()) {
                headString = "客户端-";
            } else if (12 == bean.getModleId()) {
                headString = "服务端-";
            } else {
                headString = "";
            }
            fileXname = headString + watchPointBeanName + appBusinessBean.getName() 
                    + "-"+ plotBean.getName();
        }
        if (11 == bean.getModleId()) {
            try {
                if (!map.isEmpty() && null != map.get("exeption") 
                        && "yes".equals(map.get("exeption"))) {
                } else {
                    map = cliemtService.getClientGraphical(bean);
                    if (2 == beanQ.getCompareToLastOne() 
                            && ints.contains(","+bean.getPlotId()+",")) {
                        DrawingOptionsBean bean2 = bean;
                        bean2.setEndtime(longDate / 1000 - mathDay * 24 * 60 * 60);
                        bean2.setStarttime(longDate / 1000 - mathDay * 48 * 60 * 60);
                        map2 = cliemtService.getClientGraphical(bean2);
                    }
                }
            } catch (Exception e) {
                map.put("exeption", "yes");
            }
        } else if (12 == bean.getModleId()) {
            try {
                if (!map.isEmpty()&& null != map.get("exeption") 
                        && "yes".equals(map.get("exeption"))) {
                } else {
                    map = serverManagementService .getServerSideGraphical(bean, bean.getClientId());
                    if (2 == beanQ.getCompareToLastOne()&& ints.contains(","+bean.getPlotId()+",")) {
                        DrawingOptionsBean bean2 = bean;
                        bean2.setEndtime(longDate / 1000 - mathDay * 24 * 60 * 60);
                        bean2.setStarttime(longDate / 1000 - mathDay * 48 * 60 * 60);
                        map2 = serverManagementService.
                                getServerSideGraphical(bean2, bean2.getClientId());
                    }
                    
                    if (2 == beanQ.getCompareToLastOne() 
                            && ints2.contains(","+bean.getPlotId()+",")) {
                        DrawingOptionsBean bean2 = bean;
                        bean2.setEndtime(longDate / 1000 - mathDay * 24 * 60 * 60);
                        bean2.setStarttime(longDate / 1000 - mathDay * 48 * 60 * 60);
                        map2 = serverManagementService.
                                getServerSideGraphical(bean2, bean2.getClientId());
                    }
                }
            } catch (Exception e) {
                map.put("exeption", "yes");
            }
        } else {
        }
        String jsonName = bean.getModleId() + "!" + watchpointId + "!" + bean.getPlotId();
        if (!map.isEmpty()) {
            if (null == map.get("exeption")) {
                map.put("exeption", "no");
            }
            if(10 == bean.getModleId()){
                jsonName += ("!" + watchpointId + "!" + bean.getPlotId());
            }else{
                jsonName += ("!" + bean.getClientId() + "!" + bean.getPlotId());
            }
            if (null == map2) {
                String sr = JsonStringOutputDocUtil.createJsonFile(rootPath
                                        + File.separator + "wordtext" 
                        + File.separator + beanHistory.getId(),
                                jsonName, fileXname, map, bean.getPlotTypeId());
                nameMap.put(jsonName + ".png", fileXname);
                nameMap.put(jsonName + ".pngl", sr);
            } else {
                Map<String, Object> map1 = this.changeTwoToOne(map, map2, typeId);
                JsonStringOutputDocUtil.createJsonFile(
                                rootPath + File.separator + "wordtext" 
                                        + File.separator + beanHistory.getId(),
                                jsonName, fileXname, map1, bean.getPlotTypeId());
                nameMap.put(jsonName + ".png", fileXname);
                nameMap.put(jsonName + ".pngl", map1.get("this").toString());
                nameMap.put(jsonName + ".pngf", map1.get("last").toString());
            }
        }
        return nameMap;
    }
    
    /**
     * 
     * 
     * @Title: checkWarnAndMonth
     * @Description: 
     * @param beanBus
     * @param beanHistory
     * @param watchpointId
     * @param volantenner
     * @param appBusinessBean
     * @param typeId
     * @param mathMax
     * @return Map<Integer,String>
     * @author 刘斌
     */
    public Map<Integer, String> checkWarnAndMonth(
            ReportBusinessBean beanBus, ReportHistoryBean beanHistory, Integer watchpointId, 
            Integer volantenner, AppBusinessBean appBusinessBean, Integer mathMax){
        Map<Integer, String> map = new HashMap<>();
        ReportAlarmBean beanAlarm = new ReportAlarmBean();
        beanAlarm.setBusinessId(beanBus.getBussinessId());
        beanAlarm.setEndtime(beanHistory.getDataetime());
        beanAlarm.setModuleId(beanBus.getModulId());
        beanAlarm.setSpot(mathMax);
        beanAlarm.setType(4);
        beanAlarm.setStarttime(beanHistory.getDatastime());
        beanAlarm.setWatchpointId(watchpointId);
        String warnJson = "";
        Long timeAdd = beanHistory.getDataetime() - beanHistory.getDatastime();
        if (2 == volantenner) {
            beanAlarm.setRingRatioEnd(beanHistory.getDatastime());
            beanAlarm.setRingRatioStart(beanHistory.getDatastime() - timeAdd);
            beanAlarm.setName("本月");
            beanAlarm.setRingRatioName("上月");
            warnJson = reportAlarmService.getReportRingRatioData(beanAlarm);
        } else {
            warnJson = reportAlarmService.getReportAlarmData(beanAlarm);
        }
        String jsonName1 = beanBus.getModulId() + "!" + watchpointId + "!0!" + beanBus.getBussinessId() + "!0";
        String fileXname1 = "";
        WatchpointBean watchpointBeanno = watchpointServer.getWatchpointById(watchpointId);
        if (11 == beanBus.getModulId()) {
            fileXname1 = "客户端-" + watchpointBeanno.getName() + "-" + appBusinessBean.getName() + "-告警";
        } else if (12 == beanBus.getModulId()) {
            fileXname1 = "服务端-" + watchpointBeanno.getName() + "-" + appBusinessBean.getName() + "-告警";
        
        }
        map.put(1, warnJson);
        map.put(2, jsonName1);
        map.put(3, fileXname1);
        return map;
    }
}
