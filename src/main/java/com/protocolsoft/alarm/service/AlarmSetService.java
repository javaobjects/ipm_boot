/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetService
 *创建人:chensq    创建时间:2017年11月2日
 */
package com.protocolsoft.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmBaseLineBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;
import com.protocolsoft.alarm.bean.AlarmGlobalSetBean;
import com.protocolsoft.alarm.bean.AlarmGlobalValueBean;
import com.protocolsoft.alarm.bean.AlarmKpiBean;
import com.protocolsoft.alarm.bean.AlarmLevelBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmSetDataBean;
import com.protocolsoft.alarm.bean.AlarmSetTitleBean;
import com.protocolsoft.alarm.bean.AlarmThreshBean;
import com.protocolsoft.alarm.bean.AlarmThreshSetBean;
import com.protocolsoft.alarm.bean.AlarmTriggerBean;
import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmCustomUnionKpiDao;
import com.protocolsoft.alarm.dao.AlarmCustomkpiDao;
import com.protocolsoft.alarm.dao.AlarmGlobalSetDao;
import com.protocolsoft.alarm.dao.AlarmGlobalValueDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLevelDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.dao.AlarmSetDao;
import com.protocolsoft.alarm.dao.AlarmSetThreadDao;
import com.protocolsoft.alarm.dao.AlarmTriggerDao;
import com.protocolsoft.alarm.dao.AlarmTypeDao;
import com.protocolsoft.alarm.util.AlarmSetThreadUtil;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.service.AlarmRrdService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.UnitUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;


/**
 * @ClassName： AlarmSetService
 * @Description: 告警设置service
 * @author chensq
 * 
 */
@Service
public class AlarmSetService {
    
    /**
     * @Fields alarmSetThreadMap : 线程使用的告警设置map，程序启动初始化，以后更改阈值，修改业务，维护该map.
     */
    public static Map<String, AlarmSetBean> alarmSetThreadMap=null; 
     
    /**
     * @Fields alarmSetDao : 告警阈值设置  DAO
     */
    @Autowired
    private AlarmSetDao alarmSetDao;
    
    /**
     * @Fields alarmTypeDao : 告警类型  DAO
     */
    @Autowired
    private AlarmTypeDao alarmTypeDao;
    
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
     * @Fields alarmCustomkpiDao : 告警Customkpi  DAO
     */
    @Autowired
    private AlarmCustomkpiDao alarmCustomkpiDao;
    
    /**
     * @Fields appBusinessDao : 业务对应类型  DAO
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * @Fields alarmTriggerDao : 自定义阈值历史表  DAO
     */
    @Autowired
    private AlarmTriggerDao alarmTriggerDao;
     
    /**
     * @Fields alarmLogDao : 告警日志表  DAO
     */
    @Autowired
    private AlarmLogDao alarmLogDao;
   
    /**
     * @Fields kpisDao : kpi DAO
     */
    @Autowired
    private KpisDao kpisDao;
    
    /**
     * @Fields alarmSetThreadDao : alarmSetThread DAO
     */
    @Autowired
    private AlarmSetThreadDao alarmSetThreadDao;
  
    /**
     * @Fields alarmGlobalSetDao : alarmGlobalSet DAO
     */
    @Autowired
    private AlarmGlobalSetDao alarmGlobalSetDao;
    
    /**
     * @Fields alarmGlobalValueDao : alarmGlobalValue DAO
     */
    @Autowired
    private AlarmGlobalValueDao alarmGlobalValueDao;
    
    /**
     * @Fields watchpointDao : Watchpoint Dao
     */
    @Autowired
    private WatchpointDao watchpointDao;
      
    /**
     * @Fields alarmCustomUnionKpiDao : 组合kpi告警dao
     */
    @Autowired
    private AlarmCustomUnionKpiDao alarmCustomUnionKpiDao;
   
    /**
     * @Fields alarmBaseLineDao : 智能告警设置dao
     */
    @Autowired
    private AlarmBaseLineDao alarmBaseLineDao;
    
    /**
     * 
     * @Title: addAppBusinessAfter
     * @Description: 添加完应用之后，执行此方法。(添加阈值基础设置)
     * @param watchpointId 目前相关设计规划中，该值为0,表示阈值不区分观察点,日后规划传了其他值则阈值设置区分观察点
     * @param moduleId 模块id
     * @param businessId 业务id
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean addAppBusinessAfter(Integer watchpointId, Integer moduleId, Integer businessId) {
        //设置观察点id为0
        watchpointId=0;
        
        boolean flag=false;
        //查询ipm_alarm_kpi中模块对应的kpi
        List<AlarmKpiBean> alarmKpiList=moduleId==1
                ?alarmKpiDao.getAlarmKpiListByModuleId1(moduleId)
                        :alarmKpiDao.getAlarmKpiListByModuleId(moduleId, "");
        //查询ipm_alarm_customkpi中模块对应的kpi
        //List<AlarmCustomkpiBean> alarmCustomkpiList=alarmCustomkpiDao.getAlarmCustomKpiListByModuleId(moduleId);
                
        //获取所有告警级别
        List<AlarmLevelBean> alarmLevelList=alarmLevelDao.getAlarmLevelList();
        
        //添加alarm_set 
        List<AlarmSetBean> alarmSetList= null; 
        //迭代原生kpi
        if (alarmKpiList!=null && alarmKpiList.size()>0) {
            for (int x=0; x<alarmKpiList.size(); x++) {
                long alarmKpiId = alarmKpiList.get(x).getId();
                
                //增加特殊情况处理,当moduleId为1时，表示应用可用性设置      默认其为紧急
                if(moduleId==1){ //应用可用性情况                       
                    if (alarmSetList == null) {
                        alarmSetList = new ArrayList<AlarmSetBean>();
                    }
                    AlarmSetBean alarmSetInsertBean= new AlarmSetBean();
                    alarmSetInsertBean.setWatchpointId(watchpointId);
                    alarmSetInsertBean.setBusinessId(businessId);
                    alarmSetInsertBean.setKpitype(1); //原生kpi
                    alarmSetInsertBean.setKpiId(alarmKpiId);
                    alarmSetInsertBean.setLevelId(4);
                    alarmSetInsertBean.setUpdateflag("N");
                    alarmSetInsertBean.setLowthresh(null);
                    alarmSetInsertBean.setHighthresh(null);
                    alarmSetList.add(alarmSetInsertBean);
                }else{// 其他情况
                    for (int y=0; y<alarmLevelList.size(); y++) {
                        long alarmLevelId = alarmLevelList.get(y).getId();                    
                        //特殊处理
                        if (alarmLevelId==5) {//应用不可用告警对应的级别，不继续处理
                            continue;
                        }
                        if (alarmSetList == null) {
                            alarmSetList = new ArrayList<AlarmSetBean>();
                        }
                        AlarmSetBean alarmSetInsertBean= new AlarmSetBean();
                        alarmSetInsertBean.setWatchpointId(watchpointId);
                        alarmSetInsertBean.setBusinessId(businessId);
                        alarmSetInsertBean.setKpitype(1); //原生kpi
                        alarmSetInsertBean.setKpiId(alarmKpiId);
                        alarmSetInsertBean.setLevelId(alarmLevelId);
                        alarmSetInsertBean.setUpdateflag("N");
                        alarmSetInsertBean.setLowthresh(null);
                        alarmSetInsertBean.setHighthresh(null);
                        alarmSetList.add(alarmSetInsertBean);
                    }
                }
            }
        }

        flag= this.doInsertAlarmSet(alarmSetList);
        
        //添加后执行(无),初始时,阈值未变动
        AlarmSetThreadUtil.setAlarmSetThreadAdd();
        
        return flag;
    }
        
    /**
     * 
     * @Title: addCenterBusinessAfter
     * @Description: center添加远端时调用的方法
     * @param name
     * @return boolean
     * @author chensq
     */
    public boolean addCenterBusinessAfter(String name, String displayip){
        //return 
        boolean flag=false;
        int count=0;
        //add to ipm_app_business
        AppBusinessBean busBean=new AppBusinessBean();
        busBean.setModuleId(1);
        busBean.setName(name);
        busBean.setDisplayIp(displayip);
        busBean.setBandwidth(0);
        busBean.setDescrption(null);
        appBusinessDao.insertAppBusiness(busBean);
        
        //add to ipm_alarm_set
        AlarmSetBean alarmSetBean301=new AlarmSetBean();
        alarmSetBean301.setWatchpointId(0);
        alarmSetBean301.setBusinessId(busBean.getId());
        alarmSetBean301.setKpitype(1);
        alarmSetBean301.setKpiId(301);
        alarmSetBean301.setLevelId(4);
        alarmSetBean301.setUpdateflag("N");
        alarmSetBean301.setLowthresh(null);
        alarmSetBean301.setHighthresh(null);
        count=alarmSetDao.addAlarmSet(alarmSetBean301);
        
        AlarmSetBean alarmSetBean302=new AlarmSetBean();
        alarmSetBean302.setWatchpointId(0);
        alarmSetBean302.setBusinessId(busBean.getId());
        alarmSetBean302.setKpitype(1);
        alarmSetBean302.setKpiId(302);
        alarmSetBean302.setLevelId(4);
        alarmSetBean302.setUpdateflag("N");
        alarmSetBean302.setLowthresh(null);
        alarmSetBean302.setHighthresh(null);
        count=alarmSetDao.addAlarmSet(alarmSetBean302);
        
        if(count>0){
            flag=true;
        }
        
        return flag;
    }
    
    /**
     * 
     * @Title: getProbeSynchronousAlarmSetIdByIpStr
     * @Description: 根据ip地址信息查询探针的业务的alarmSetId
     * @param displayIp
     * @param type 1:探针同步 2：探针网络
     * @return long
     * @author chensq
     */
    public long getProbeSynchronousAlarmSetIdByIpStr(String displayIp, int type){
        //get bus 
        AppBusinessBean appBusinessBean=appBusinessDao.selectAppBusinessesByModuleIdAndDisplayIp(1, displayIp);
        //get alarmset id
        AlarmSetBean alarmSetBean=new AlarmSetBean();
        alarmSetBean.setWatchpointId(0);
        alarmSetBean.setBusinessId(appBusinessBean.getId());
        alarmSetBean.setKpiId(type==1?301:302);
        alarmSetBean.setLevelId(4);
        AlarmSetBean returnAlarmSetBean=alarmSetDao.getAlarmUsabilitySetByParam(alarmSetBean);
        return returnAlarmSetBean.getId();
    }
    
    /**
     * @Title: addCustomUnionKpi
     * @Description: add alarmset By custom union kpi
     * @param alarmSetBean void
     * @author chensq
     */
    public void addCustomUnionKpi(AlarmSetBean alarmSetBean){
        List<AlarmSetBean> alarmSetList =null;
        List<AlarmLevelBean> alarmLevelList=alarmLevelDao.getAlarmLevelList();
        for (int y=0; y<alarmLevelList.size(); y++) {
            long alarmLevelId = alarmLevelList.get(y).getId();                    
            //特殊处理
            if (alarmLevelId==1) {//基线的级别，不继续处理
                continue;
            }
            if (alarmSetList == null) {
                alarmSetList = new ArrayList<AlarmSetBean>();
            }
            AlarmSetBean alarmSetInsertBean= new AlarmSetBean();
            alarmSetInsertBean.setWatchpointId(0);
            alarmSetInsertBean.setBusinessId(alarmSetBean.getBusinessId());
            alarmSetInsertBean.setKpitype(2); //自定义kpi
            alarmSetInsertBean.setKpiId(alarmSetBean.getKpiId());
            alarmSetInsertBean.setLevelId(alarmLevelId);
            alarmSetInsertBean.setUpdateflag("Y");
            
            alarmSetInsertBean.setLowthresh(null);
            alarmSetInsertBean.setHighthresh(null);
            alarmSetList.add(alarmSetInsertBean);
        }
        this.doInsertAlarmSet(alarmSetList);
    }
    
    /**
     * @Title: delAppBusinessAfter
     * @Description: 删除完应用之后，执行此方法。(删除阈值、告警等基础设置)
     * @param watchpointId 目前相关设计规划中，该值为0,表示阈值不区分观察点,日后规划传了其他值则阈值设置区分观察点
     * @param moduleId 模块id
     * @param businessId 业务id
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean delAppBusinessAfter(Integer watchpointId, Integer moduleId, Integer businessId) {
        //设置观察点id为0
        watchpointId=0;
        
        //需要增加删除 ipm_alarm_baselineset表
        
        //最终返回
        boolean flag=false;
        //-------------------------------查询alarmset list-----------原生kpi start----------------------------------
        List<AlarmSetBean> alarmSetList=alarmSetDao.getAlarmSetListType1(moduleId, watchpointId, businessId, 1);
        if(moduleId==1){//应用可用性
            if (alarmSetList!=null && alarmSetList.size()>0) {
                for (int x=0; x<alarmSetList.size(); x++) {
                    long alarmSetId=alarmSetList.get(x).getId();
                    //ipm_alarm_log表中进行删除
                    alarmLogDao.delAlarmLogByAlarmsetId(alarmSetId);
                }
            }
        }else{//除了应用可用性的其他类型
            //循环删除 1.ipm_alarm_trigger 2.ipm_alarm_X_baselinetrigger 3.ipm_alarm_log
            if (alarmSetList!=null && alarmSetList.size()>0) {
                for (int x=0; x<alarmSetList.size(); x++) {
                    long alarmSetId=alarmSetList.get(x).getId();
                    //ipm_alarm_trigger表中进行删除
                    alarmTriggerDao.delAlarmTriggerByAlarmsetId(alarmSetId);
                    //删除基线历史阈值
                    
                    //ipm_alarm_log表中进行删除
                    alarmLogDao.delAlarmLogByAlarmsetId(alarmSetId);
                }
            }
        }
        //-------------------------------查询alarmset list-----------原生kpi end----------------------------------

        //-------------------------------查询alarmset list-----------customkpi start----------------------------------
        List<AlarmSetBean> alarmSetListCustom=alarmSetDao.getAlarmSetListType2(moduleId, watchpointId, businessId, 2);
        if(moduleId==1){//应用可用性
            if (alarmSetListCustom!=null && alarmSetListCustom.size()>0) {
                for (int x=0; x<alarmSetListCustom.size(); x++) {
                    long alarmSetId=alarmSetListCustom.get(x).getId();
                    //ipm_alarm_log表中进行删除
                    alarmLogDao.delAlarmLogByAlarmsetId(alarmSetId);
                }
            }
        }else{//除了应用可用性的其他类型
            //循环删除 1.ipm_alarm_trigger 2.ipm_alarm_X_baselinetrigger 3.ipm_alarm_log
            if (alarmSetListCustom!=null && alarmSetListCustom.size()>0) {
                for (int x=0; x<alarmSetListCustom.size(); x++) {
                    long alarmSetId=alarmSetListCustom.get(x).getId();
                    //ipm_alarm_trigger表中进行删除
                    alarmTriggerDao.delAlarmTriggerByAlarmsetId(alarmSetId);
                    //删除基线历史阈值
                    
                    //ipm_alarm_log表中进行删除
                    alarmLogDao.delAlarmLogByAlarmsetId(alarmSetId);
                }
            }
            
            //删除ipm_alarm_customkpi
            AlarmCustomkpiBean alarmCustomkpiBean =alarmCustomkpiDao.getAlarmCustomKpiByMoBus(moduleId, businessId);
            if(alarmCustomkpiBean!=null){
                alarmCustomUnionKpiDao.delAlarmCustomKpsByCusId(alarmCustomkpiBean.getId());
                alarmCustomkpiDao.delAlarmCustomkpiBusMoid(moduleId, businessId);
            }
            
        }
        //-------------------------------查询alarmset list-----------customkpi end----------------------------------
        
        //删除告警阈值设置ipm_alarm_set(通过原生kpi)
        alarmSetDao.delAlarmSetByBusWPid(moduleId, watchpointId, businessId);        
        //删除告警阈值设置ipm_alarm_set(通过自定义组合kpi)
        alarmSetDao.delCustomUnAlarmSetByBusWPid(moduleId, watchpointId, businessId);
        
        //-------------------------------删除业务时，智能告警进行的操作(考虑多个观察点的情况)----------------------------------
        if(moduleId==10){//观察点情况
            //清空智能rrd目录        
            AlarmRrdService alarmRrdServiceH=new AlarmRrdService(moduleId, businessId, watchpointId, AlarmBaseType.HIGH, false);
            alarmRrdServiceH.deleteFile();
            
            AlarmRrdService alarmRrdServiceL=new AlarmRrdService(moduleId, businessId, watchpointId, AlarmBaseType.LOW, false);
            alarmRrdServiceL.deleteFile();
            
            //清空智能告警设置表数据
            AlarmBaseLineBean alarmBaseLineBean =new AlarmBaseLineBean();
            alarmBaseLineBean.setWatchpointId(watchpointId);
            alarmBaseLineBean.setBusinessId(businessId);
            alarmBaseLineBean.setModuleId(moduleId);
            alarmBaseLineDao.delAlarmBaseLine(alarmBaseLineBean);
        }else{//非观察点情况
            List<WatchpointBean> watchpointBeanList =watchpointDao.getFindAll();
            if(watchpointBeanList!=null && watchpointBeanList.size()>0){
                for(int x=0; x<watchpointBeanList.size(); x++){
                    WatchpointBean tempBean= watchpointBeanList.get(x);
                    //清空智能rrd目录        
                    AlarmRrdService alarmRrdServiceH=new AlarmRrdService(moduleId, businessId, tempBean.getId(), AlarmBaseType.HIGH, false);
                    alarmRrdServiceH.deleteFile();
                    
                    AlarmRrdService alarmRrdServiceL=new AlarmRrdService(moduleId, businessId, tempBean.getId(), AlarmBaseType.LOW, false);
                    alarmRrdServiceL.deleteFile();
                    
                    //清空智能告警设置表数据
                    AlarmBaseLineBean alarmBaseLineBean =new AlarmBaseLineBean();
                    alarmBaseLineBean.setWatchpointId(tempBean.getId());
                    alarmBaseLineBean.setBusinessId(businessId);
                    alarmBaseLineBean.setModuleId(moduleId);
                    alarmBaseLineDao.delAlarmBaseLine(alarmBaseLineBean);
                }
            }
        }
        //-------------------------------删除业务时，智能告警进行的操作(考虑多个观察点的情况)----------------------------------        
        
        flag=true;
        
        //删除后执行
        AlarmSetBean updateBean=new AlarmSetBean();
        updateBean.setWatchpointId(watchpointId);
        updateBean.setModuleId(moduleId);
        updateBean.setBusinessId(businessId);
        AlarmSetThreadUtil.setAlarmSetThreadDel(updateBean);
       
        return flag;
    }
     
    /**
     * @Title: getAlarmSetData
     * @Description: 查询业务对应的告警设置的title
     * @return List<AlarmSetDataBean>
     * @author chensq
     */
    public List<AlarmSetDataBean> getAlarmSetData(long watchpointId, long moduleId, long businessId, long kpiId){
        //返回内容
        List<AlarmSetDataBean> returnList=null;
        //watchpointId 设置成0(目前不区分观察点下应用阈值不一样的情况)
        watchpointId=0;
        //获取kpis的所有单位map
        Map<String, String> kpisUnitMap=UnitUtils.changeKpiUnitMap();        
        //查询ipm_alarm_kpi中模块对应的id
        String kpiIdSql="";
        if(kpiId!=0){
            kpiIdSql="  AND k.kpi_id ="+kpiId +" ";
        }
        List<AlarmKpiBean> alarmKpiList=alarmKpiDao.getAlarmKpiListByModuleId(moduleId, kpiIdSql);
        //迭代获取ipm_alarm_kpi的id
        if (alarmKpiList!=null && alarmKpiList.size()>0){
            returnList=new ArrayList<AlarmSetDataBean>();
            for (int x=0; x<alarmKpiList.size(); x++){
                long alarmKpiId = alarmKpiList.get(x).getId();
                long kpiIdTemp = alarmKpiList.get(x).getKpiId();
                String displayName = alarmKpiList.get(x).getDisplayName();

                //获取kpi的其他属性
                KpisBean kpisBean= kpisDao.getKpisByIdModuleId((int)kpiIdTemp, moduleId);
                
                //增加全局告警阈值内容判断
                AlarmGlobalSetBean alarmGlobalSetBean=new AlarmGlobalSetBean();
                alarmGlobalSetBean.setModuleId((int)moduleId);
                alarmGlobalSetBean.setWatchpointId(watchpointId);
                alarmGlobalSetBean.setKpitype(1);
                alarmGlobalSetBean.setKpiId(alarmKpiId);
                Map<String, List<AlarmGlobalValueBean>> globalSetmap=this.getGlobalAlarmSet(alarmGlobalSetBean);
               
                //获取其对应的设置(原生)
                List<AlarmSetBean> alarmSetList= alarmSetDao.getAlarmSetListByKpiId(watchpointId,
                        businessId, moduleId, 1, alarmKpiId);
                //该告警kpi对应的设置
                AlarmSetDataBean alarmSetDataBean =new AlarmSetDataBean();
                alarmSetDataBean.setId(kpiIdTemp);
                alarmSetDataBean.setKpiName(kpisBean.getName());
                alarmSetDataBean.setNameValue(displayName);
                alarmSetDataBean.setKpiUnit(kpisUnitMap.get(kpisBean.getUnit()));
                if (alarmSetList!=null && alarmSetList.size()>0) {
                    
                    int  shighStatusCount=0;  //单独高值计数器
                    int  slowStatusCount=0;  //单独低值计数器
                    
                    for (int y=0; y<alarmSetList.size(); y++) {
                        AlarmSetBean alarmSetBean= alarmSetList.get(y);
                        long alarmSetId=alarmSetBean.getId();
                        long alarmSetLevel=alarmSetBean.getLevelId();
                        Double alarmSetLowthreshValue=alarmSetBean.getLowthresh(); //告警设置的低阈值
                        Double alarmSetHighthreshValue=alarmSetBean.getHighthresh(); //告警设置的高阈值

                        if(kpiId!=0){//单独修改阈值情况
                            List<AlarmTriggerBean> triggerlist= alarmTriggerDao.getAsetGroupByAlarmSetIdAlarmTrigger(alarmSetId);
                            if(triggerlist!=null && triggerlist.size()>0){
                                for(int t=0; t<triggerlist.size(); t++){
                                    AlarmTriggerBean alarmTriggerBeanTemp=triggerlist.get(t);
                                    int sTriggerflag=alarmTriggerBeanTemp.getTriggerflag();
                                    int sTriggerusestatus=alarmTriggerBeanTemp.getTriggerusestatus();
                                    if(sTriggerflag==0 && sTriggerusestatus==0){//高阈值
                                        shighStatusCount++;
                                    }
                                    if(sTriggerflag==1 && sTriggerusestatus==0){//低阈值
                                        slowStatusCount++;
                                    }
                                }
                            }
                        }
                        
                        AlarmThreshBean alarmThreshBean=new AlarmThreshBean();
                        AlarmThreshSetBean alarmThreshSetBeanSingle=new AlarmThreshSetBean();
                        if (alarmSetLevel==1) {//偏离
                            alarmSetDataBean.setDeviateId(alarmSetId);
                            alarmThreshSetBeanSingle.setLowThresh(alarmSetLowthreshValue);
                            alarmThreshSetBeanSingle.setHighThresh(alarmSetHighthreshValue);
                            alarmThreshBean.setSingle(alarmThreshSetBeanSingle);
                            alarmSetDataBean.setDeviateValue(alarmThreshBean);
                        } else if (alarmSetLevel==2) {//普通
                            alarmSetDataBean.setNormalId(alarmSetId);
                            alarmThreshSetBeanSingle.setLowThresh(alarmSetLowthreshValue);
                            alarmThreshSetBeanSingle.setHighThresh(alarmSetHighthreshValue);
                            alarmThreshBean.setSingle(alarmThreshSetBeanSingle);
                            alarmSetDataBean.setNormalValue(alarmThreshBean);
                        } else if (alarmSetLevel==3) {//重要
                            alarmSetDataBean.setImportantId(alarmSetId);
                            alarmThreshSetBeanSingle.setLowThresh(alarmSetLowthreshValue);
                            alarmThreshSetBeanSingle.setHighThresh(alarmSetHighthreshValue);
                            alarmThreshBean.setSingle(alarmThreshSetBeanSingle);
                            alarmSetDataBean.setImportantValue(alarmThreshBean);
                        } else if (alarmSetLevel==4) {//紧急
                            alarmSetDataBean.setUrgentId(alarmSetId);
                            alarmThreshSetBeanSingle.setLowThresh(alarmSetLowthreshValue);
                            alarmThreshSetBeanSingle.setHighThresh(alarmSetHighthreshValue);
                            alarmThreshBean.setSingle(alarmThreshSetBeanSingle);
                            alarmSetDataBean.setUrgentValue(alarmThreshBean);
                        }
                    }
                    
                    alarmSetDataBean.setSingleHighThreshUsedFlag(shighStatusCount>0?0:1);
                    alarmSetDataBean.setSingleLowThreshUsedFlag(slowStatusCount>0?0:1); 
                }
                
                //判断全局的值与设置的值是否相同
                boolean aloneHaveValue=false; //单独是否有值(默认无)
                boolean globalHaveValue=false; //全局是否有值(默认无)
                boolean aloneAndGlobalIsSame=true; //全局与单独值是否相同(默认相同)

                //判断单独是否有值
                if(alarmSetDataBean.getDeviateValue()!=null  
                        ||   (alarmSetDataBean.getNormalValue()!=null 
                        && alarmSetDataBean.getImportantValue()!=null 
                        && alarmSetDataBean.getUrgentValue()!=null
                        )){
                    aloneHaveValue=true;
                }
                //判断全局是否有值
                if(globalSetmap!=null && globalSetmap.size()!=0){
                    globalHaveValue=true;
                }
                if(kpiId!=0){
                  //判断两者均有值得情况下，值是否相同
                    if(aloneHaveValue && globalHaveValue){
                        for (Map.Entry<String, List<AlarmGlobalValueBean>> entry : globalSetmap.entrySet()) {
                            String levelStr=entry.getKey();
                            List<AlarmGlobalValueBean> alarmValueList=entry.getValue();
                            if("1".endsWith(levelStr)){
                                this.setGlobalInfoToAlarmSetDataBean(alarmValueList, alarmSetDataBean.getDeviateValue());
                                
                                aloneAndGlobalIsSame=this.getGlobalSingleCompareFlag(alarmValueList, 
                                        alarmSetDataBean.getDeviateValue(), aloneAndGlobalIsSame);                            
                            }else if("2".endsWith(levelStr)){
                                this.setGlobalInfoToAlarmSetDataBean(alarmValueList, alarmSetDataBean.getNormalValue());
                                
                                aloneAndGlobalIsSame=this.getGlobalSingleCompareFlag(alarmValueList, 
                                        alarmSetDataBean.getNormalValue(), aloneAndGlobalIsSame);                            
                            }else if("3".endsWith(levelStr)){
                                this.setGlobalInfoToAlarmSetDataBean(alarmValueList, alarmSetDataBean.getImportantValue());
                                
                                aloneAndGlobalIsSame=this.getGlobalSingleCompareFlag(alarmValueList, 
                                        alarmSetDataBean.getImportantValue(), aloneAndGlobalIsSame);        
                            }else if("4".endsWith(levelStr)){
                                this.setGlobalInfoToAlarmSetDataBean(alarmValueList, alarmSetDataBean.getUrgentValue());
                                
                                aloneAndGlobalIsSame=this.getGlobalSingleCompareFlag(alarmValueList, 
                                        alarmSetDataBean.getUrgentValue(), aloneAndGlobalIsSame);                               
                            }
                        }
                    }                
                    
                    //获取全局"阈值开启状态"
                    if(globalHaveValue){//有全局
                        int  ghighStatusCount=0;  //全局高值计数器
                        int  glowStatusCount=0;  //全局低值计数器
                        for (Map.Entry<String, List<AlarmGlobalValueBean>> entry : globalSetmap.entrySet()) {
                            List<AlarmGlobalValueBean> alarmValueList=entry.getValue();
                            for(int x1=0; x1<alarmValueList.size(); x1++){
                                int gTriggerflag=alarmValueList.get(x1).getTriggerflag();
                                int gTriggerusestatus=alarmValueList.get(x1).getTriggerusestatus();
                                if(gTriggerflag==0){//高阈值
                                    if(gTriggerusestatus==0){//开启
                                        ghighStatusCount++;
                                    } 
                                }
                                if(gTriggerflag==1){//低阈值
                                    if(gTriggerusestatus==0){//开启
                                        glowStatusCount++;
                                    } 
                                }
                            }
                        }
                        alarmSetDataBean.setGlobalHighThreshUsedFlag(ghighStatusCount>0?0:1);
                        alarmSetDataBean.setGloballowThreshUsedFlag(glowStatusCount>0?0:1); 
                    }
                    
                    //单独有，全局无的情况，全局的值为null
                    if(aloneHaveValue && !globalHaveValue){
                        AlarmThreshSetBean alarmThreshSetBeanGlobal=new AlarmThreshSetBean();
                        alarmThreshSetBeanGlobal.setLowThresh(null);
                        alarmThreshSetBeanGlobal.setHighThresh(null);
                        
                        alarmSetDataBean.getDeviateValue().setGlobal(alarmThreshSetBeanGlobal);
                        alarmSetDataBean.getNormalValue().setGlobal(alarmThreshSetBeanGlobal);
                        alarmSetDataBean.getImportantValue().setGlobal(alarmThreshSetBeanGlobal);
                        alarmSetDataBean.getUrgentValue().setGlobal(alarmThreshSetBeanGlobal);
                    } 
                }
                
                //情形：一：单独有，全局无     0
                //    二：单独有，全局有     值相同 1 值不同0
                //    三：单独无，全局有     1
                int sameCompareFlag=0; //默认单独
                //情形一                
                if(aloneHaveValue && !globalHaveValue){
                    sameCompareFlag=0;
                }
                //情形二               
                if(aloneHaveValue && globalHaveValue){
                    if(aloneAndGlobalIsSame){//相同
                        sameCompareFlag=1;
                    }else{//不同
                        sameCompareFlag=0;
                    }
                }
                //情形三
                if(!aloneHaveValue && globalHaveValue){
                    sameCompareFlag=1;
                }
                
                //全局是否与单独相同的标识
                if((alarmSetDataBean.getGlobalHighThreshUsedFlag()!=alarmSetDataBean.getSingleHighThreshUsedFlag()
                        || alarmSetDataBean.getGloballowThreshUsedFlag()!=alarmSetDataBean.getSingleLowThreshUsedFlag()
                        )){
                    alarmSetDataBean.setAlarmValueCompareFlag(0);
                }else{
                    alarmSetDataBean.setAlarmValueCompareFlag(sameCompareFlag);
                }
                
                returnList.add(alarmSetDataBean);
            }
        }
        
        return returnList;
    }
    /////////////////////////获取告警设置使用///////////////////////////
     
    /**
     * @Title: setGlobalInfoToAlarmSetDataBean
     * @Description: 将Global的告警信息设置入相应对象
     * @param alarmValueList
     * @param alarmThreshBean void
     * @author chensq
     */
    public void setGlobalInfoToAlarmSetDataBean(List<AlarmGlobalValueBean> alarmValueList, AlarmThreshBean alarmThreshBean){
        AlarmThreshSetBean alarmThreshSetBeanGlobal=new AlarmThreshSetBean();
        for(int x=0; x<alarmValueList.size(); x++){
            int gTriggerflag=alarmValueList.get(x).getTriggerflag();
            Double gTriggerValue=alarmValueList.get(x).getTriggerValue();
            if(gTriggerflag==1){//低阈值
                alarmThreshSetBeanGlobal.setLowThresh(gTriggerValue);
            }
            if(gTriggerflag==0){//高阈值
                alarmThreshSetBeanGlobal.setHighThresh(gTriggerValue);
            }
        }
        alarmThreshBean.setGlobal(alarmThreshSetBeanGlobal);
    }
        
    /**
     * @Title: getGlobalSingleCompareFlag
     * @Description: 比较全局与单独，设置是否与全局一致
     * @param alarmValueList
     * @param alarmThreshBeanSingle
     * @param aloneAndGlobalIsSame
     * @return boolean
     * @author chensq
     */
    public boolean getGlobalSingleCompareFlag(
            List<AlarmGlobalValueBean> alarmValueList, 
            AlarmThreshBean alarmThreshBeanSingle,
            boolean aloneAndGlobalIsSame){
        //单独
        Double low=alarmThreshBeanSingle.getSingle().getLowThresh();
        Double high=alarmThreshBeanSingle.getSingle().getHighThresh();

        for(int x=0; x<alarmValueList.size(); x++){
            int gTriggerflag=alarmValueList.get(x).getTriggerflag();
            Double gTriggerValue=alarmValueList.get(x).getTriggerValue();            
            if(gTriggerflag==1){
                if(low!=null && !low.equals(gTriggerValue)){
                    aloneAndGlobalIsSame=false;
                }
                if(low==null && gTriggerValue!=null){
                    aloneAndGlobalIsSame=false;
                }
            }
            if(gTriggerflag==0){
                if(high!=null && !high.equals(gTriggerValue)){
                    aloneAndGlobalIsSame=false;
                }
                if(high==null && gTriggerValue!=null){
                    aloneAndGlobalIsSame=false;
                }
            }
        }
        return aloneAndGlobalIsSame;
    }
    /////////////////////////获取告警设置使用///////////////////////////

    /**
     * @Title: getGlobalAlarmSet
     * @Description: 获取全局告警设置的值,查询业务对应的告警设置的title
     * @param alarmGlobalSetBean
     * @return Map<String,List<AlarmGlobalValueBean>>
     * @author chensq
     */
    public Map<String, List<AlarmGlobalValueBean>> getGlobalAlarmSet(AlarmGlobalSetBean alarmGlobalSetBean){        
        Map<String, List<AlarmGlobalValueBean>> map=null;
        List<AlarmGlobalSetBean> globalAlarmSetList=alarmGlobalSetDao.getAlarmGlobalSetByParam(alarmGlobalSetBean);
        if(globalAlarmSetList!=null && globalAlarmSetList.size()>0){
            for(int x=0; x<globalAlarmSetList.size(); x++){
                AlarmGlobalSetBean tempBean= globalAlarmSetList.get(x);
                long globalAlarmSetId= tempBean.getId();
                List<AlarmGlobalValueBean> alarmGlobalValueList= alarmGlobalValueDao.getAlarmGlobalValueByParam(globalAlarmSetId);
                if(alarmGlobalValueList!=null && alarmGlobalValueList.size()>0){
                    map=new HashMap<String, List<AlarmGlobalValueBean>>();
                    for(int y=0; y<alarmGlobalValueList.size(); y++){
                        AlarmGlobalValueBean alarmGlobalValueBean=alarmGlobalValueList.get(y);
                        int levelId=alarmGlobalValueBean.getLevelId(); //级别
                        String levelIdStr=String.valueOf(levelId); //级别str
                        if(map.get(levelIdStr)!=null){//该级别已经存现map
                            List<AlarmGlobalValueBean> keyMap= map.get(levelIdStr);
                            keyMap.add(alarmGlobalValueBean);
                        }else{//该级别map未存在
                            List<AlarmGlobalValueBean> alarmGlobalValueBeanTempList =new ArrayList<AlarmGlobalValueBean>();
                            alarmGlobalValueBeanTempList.add(alarmGlobalValueBean);
                            map.put(levelIdStr, alarmGlobalValueBeanTempList);
                        }
                    }
                }
            }
        }
        return map;
    }
    
    /**
     * @Title: getAlarmSetTitle
     * @Description: 查询业务对应的告警设置的title
     * @return List<AlarmSetTitleBean>
     * @author chensq
     */
    public List<AlarmSetTitleBean> getAlarmSetTitle(){
        List<AlarmSetTitleBean> alarmSetTitleList=new ArrayList<AlarmSetTitleBean>(); 
        AlarmSetTitleBean alarmSetTitleBean1=new AlarmSetTitleBean();
        AlarmSetTitleBean alarmSetTitleBean2=new AlarmSetTitleBean();

        alarmSetTitleBean1.setId(-1);
        alarmSetTitleBean1.setNameen("id");
        alarmSetTitleBean1.setNamezh("编号");
        
        alarmSetTitleBean2.setId(0);
        alarmSetTitleBean2.setNameen("name");
        alarmSetTitleBean2.setNamezh("KPI名称");
        
        alarmSetTitleList.add(alarmSetTitleBean1);
        alarmSetTitleList.add(alarmSetTitleBean2);

        //获取告警级别
        List<AlarmLevelBean> alarmLevelList=alarmLevelDao.getAlarmLevelList();
        if (alarmLevelList!=null && alarmLevelList.size()>0) {        
            AlarmSetTitleBean alarmSetTitleTemp=null;
            for (int x=0; x<alarmLevelList.size(); x++){
                alarmSetTitleTemp =new AlarmSetTitleBean();
                AlarmLevelBean alarmLevelBean=alarmLevelList.get(x);
                if (alarmLevelBean.getId() == 5) { 
                    continue;
                }
                alarmSetTitleTemp.setId(alarmLevelBean.getId());
                alarmSetTitleTemp.setNameen(alarmLevelBean.getNameen().toLowerCase());
                alarmSetTitleTemp.setNamezh(alarmLevelBean.getNamezh());
             
                alarmSetTitleList.add(alarmSetTitleTemp);
            }
        }
        return alarmSetTitleList;
    }
       
    /**
     * @Title: updateAlarmSet
     * @Description: 2018/4/11该代码增加“为修改线程使用值”内容, 修改告警设置
     * @param alarmSetBean
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean updateAlarmSet(AlarmSetBean alarmSetBean){
        
        //为修改线程使用值
        List<AlarmSetBean> alarmSetBeanList= new ArrayList<AlarmSetBean>();
        
        //获取观察点集合
        List<WatchpointBean> watchpointBeanList =watchpointDao.getFindAll();
        
        //成功与否标识
        boolean flag=false;
        
        if(alarmSetBean.getPageSetIsGlobalId()==0){//单个情况
            //设置观察点id为0
            alarmSetBean.setWatchpointId(0);
            
            //为修改线程使用值 
            AlarmSetBean updateSetBean=new AlarmSetBean();
            updateSetBean.setWatchpointId(alarmSetBean.getWatchpointId());
            
            //需要修改的alarmSet集合
            List<AlarmSetBean> alarmSetList=null;
            //需要添加的阈值历史记录集合
            List<AlarmTriggerBean> alarmTriggerList=null;
            //对象中组合list集合
            String []idList= alarmSetBean.getIdList().split(",");
            String []highthreshList= alarmSetBean.getHighthreshList().split(",");
            String []lowthreshList= alarmSetBean.getLowthreshList().split(",");
            
            int singleLowThreshUsedFlag=  alarmSetBean.getSingleLowThreshUsedFlag(); //0为启用，1为不启用
            int singleHighThreshUsedFlag=  alarmSetBean.getSingleHighThreshUsedFlag(); //0为启用，1为不启用
                      
            if ((idList.length>0 && highthreshList.length>0 && lowthreshList.length>0)) {
                alarmSetList=new ArrayList<AlarmSetBean>();
                alarmTriggerList=new ArrayList<AlarmTriggerBean>();
                AlarmSetBean tempAlarmSet=null;
                AlarmTriggerBean tempAlarmTrigger=null;
                for (int x=0; x<idList.length; x++) {
                    String highthreshTempValue=highthreshList[x];
                    String lowthreshTempValue=lowthreshList[x];

                    Double highthreshValue=null;
                    Double lowthreshValue=null;
                    //值,前台传递字符串null,转为null
                    if (!"null".equalsIgnoreCase(highthreshTempValue)) {
                        highthreshValue=Double.parseDouble(highthreshTempValue);
                    }
                    if (!"null".equalsIgnoreCase(lowthreshTempValue)) {
                        lowthreshValue=Double.parseDouble(lowthreshTempValue);
                    }
                    //键,前台传递字符串null，跳出当前循环
                    if("null".equalsIgnoreCase(idList[x])){
                        continue;
                    }
                    //alarmSet
                    tempAlarmSet =new AlarmSetBean();
                    tempAlarmSet.setId(Long.parseLong(idList[x]));
                    tempAlarmSet.setUpdateflag("Y");
                    tempAlarmSet.setLowthresh(lowthreshValue);
                    tempAlarmSet.setHighthresh(highthreshValue);
                    alarmSetList.add(tempAlarmSet);
                    //alarmTrigger
                    AlarmSetBean tempAlarmSetBean= alarmSetDao.getAlarmSetById(Long.parseLong(idList[x]));
                    int  tempKpitype=tempAlarmSetBean.getKpitype();
                    long tempKpiId=tempAlarmSetBean.getKpiId();
                    
                    //为修改线程使用值 
                    updateSetBean.setModuleId(tempAlarmSetBean.getModuleId());
                    updateSetBean.setBusinessId(tempAlarmSetBean.getBusinessId());
                    updateSetBean.setKpitype(tempKpitype);
                    updateSetBean.setKpiId(tempKpiId);
                    
                    long nowTime=DateUtils.getNowTimeSecond(); //当前时间
                    
                    if (tempKpitype==1) {//标准情况
                        KpisBean kpisBeanTemp=useAlarmKpiIdGetKpisInfo(tempKpiId);
                        
                        tempAlarmTrigger =new AlarmTriggerBean();
                        tempAlarmTrigger.setAlarmsetId(Long.parseLong(idList[x]));
                        tempAlarmTrigger.setTriggerflag(0);
                        tempAlarmTrigger.setTriggerusestatus(singleHighThreshUsedFlag);
                        tempAlarmTrigger.setTriggervalue(highthreshValue);
                        tempAlarmTrigger.setTriggerunit(kpisBeanTemp.getUnit());
                        tempAlarmTrigger.setTriggertime(nowTime);
                        alarmTriggerList.add(tempAlarmTrigger);
                        
                        tempAlarmTrigger =new AlarmTriggerBean();
                        tempAlarmTrigger.setAlarmsetId(Long.parseLong(idList[x]));
                        tempAlarmTrigger.setTriggerflag(1);
                        tempAlarmTrigger.setTriggerusestatus(singleLowThreshUsedFlag);
                        tempAlarmTrigger.setTriggervalue(lowthreshValue);
                        tempAlarmTrigger.setTriggerunit(kpisBeanTemp.getUnit());
                        tempAlarmTrigger.setTriggertime(nowTime);
                        alarmTriggerList.add(tempAlarmTrigger);
                        
                    }                
                }
                this.doUpdateAlarmSet(alarmSetList);
                this.doInsertAlarmTrigger(alarmTriggerList);
                flag=true;
            }
            
            //为修改线程使用值 
            alarmSetBeanList.add(updateSetBean);       
            
        }else{//全局情况
            //设置观察点id为0
            alarmSetBean.setWatchpointId(0);
            
            //step1
            String []idList= alarmSetBean.getIdList().split(",");
            String []highthreshList= alarmSetBean.getHighthreshList().split(",");
            String []lowthreshList= alarmSetBean.getLowthreshList().split(",");
            
            //01
            StringBuffer alarmsetIdsBuf=new StringBuffer();
            if (idList!=null && idList.length>0) {
                for(int x=0; x<idList.length; x++){
                    alarmsetIdsBuf.append(idList[x]);
                    if(x!=idList.length-1){
                        alarmsetIdsBuf.append(",");
                    }
                }
            }
            AlarmSetBean alarmSetBeanStep1= alarmSetDao.getAlarmGlobalSetByParam(alarmsetIdsBuf.toString());
            //02
            Map<String, Long> standardIdLevelMap=new LinkedHashMap<String, Long>(); //id level
            List<AlarmSetBean> alarmSetBeanStandard =alarmSetDao.getAlarmGlobalStandardSetByParam(alarmsetIdsBuf.toString());
            if(alarmSetBeanStandard!=null && alarmSetBeanStandard.size()>0){
                for(int x=0; x<alarmSetBeanStandard.size(); x++){
                    standardIdLevelMap.put(String.valueOf(alarmSetBeanStandard.get(x).getId()+"|"+"0"), alarmSetBeanStandard.get(x).getLevelId());
                    standardIdLevelMap.put(String.valueOf(alarmSetBeanStandard.get(x).getId()+"|"+"1"), alarmSetBeanStandard.get(x).getLevelId());
                }
            }
            //03
            Map<String, Double> standardIdValueMap=new LinkedHashMap<String, Double>(); //id value
            if (idList!=null && idList.length>0) {
                for(int x=0; x<idList.length; x++){
                   
                    String highthreshTempValue=highthreshList[x];
                    String lowthreshTempValue=lowthreshList[x];

                    Double highthreshValue=null;
                    Double lowthreshValue=null;
                    //值,前台传递字符串null,转为null
                    if (!"null".equalsIgnoreCase(highthreshTempValue)) {
                        highthreshValue=Double.parseDouble(highthreshTempValue);
                    } 
                    if (!"null".equalsIgnoreCase(lowthreshTempValue)) {
                        lowthreshValue=Double.parseDouble(lowthreshTempValue);
                    }
                    
                    standardIdValueMap.put(idList[x]+"|"+"0", highthreshValue);     
                    standardIdValueMap.put(idList[x]+"|"+"1", lowthreshValue);     
                }
            }
            
            //04
            Map<String, Double> standardLevelValueMap=new LinkedHashMap<String, Double>(); //level value
            for (Map.Entry<String, Long> entry : standardIdLevelMap.entrySet()) {
                String key=entry.getKey();
                String type=key.split("\\|")[1];
                Long value= entry.getValue();
                Double standardValue=standardIdValueMap.get(key);
                standardLevelValueMap.put(value+"|"+type, standardValue);
            }
                        
            //获取所有该模块下的业务,以及其对应的其他告警信息
            Map<String, Map<Long, AlarmSetBean>> alarmSetInfoMap =this.getBusIdAndAlarmSetInfoByModuleId(alarmSetBeanStep1);
            //迭代添加或修改
            if(alarmSetInfoMap!=null && alarmSetInfoMap.size()>0){
                
                //为修改线程使用值
                AlarmSetBean updateTempBean=null;
                
                //为了调试与代码清晰就不写在一个for循环中了
                //修改ipm_alarm_set中的记录
                for (Map.Entry<String, Map<Long, AlarmSetBean>> entry : alarmSetInfoMap.entrySet()) {
                    String busIdItem= entry.getKey();
                    
                    //为修改线程使用值
                    updateTempBean=new AlarmSetBean();
                    updateTempBean.setWatchpointId(0);
                    updateTempBean.setBusinessId(Long.parseLong(busIdItem));
                    updateTempBean.setModuleId(alarmSetBeanStep1.getModuleId());
                    updateTempBean.setKpitype(alarmSetBeanStep1.getKpitype());
                    updateTempBean.setKpiId(alarmSetBeanStep1.getKpiId());

                    alarmSetBeanList.add(updateTempBean);             
                    
                    Map<Long, AlarmSetBean> mapItem=entry.getValue();
                    if(mapItem==null){
                        continue;
                    }
                    AlarmSetBean updateAlarmSetBean =new AlarmSetBean();
                    updateAlarmSetBean.setWatchpointId(alarmSetBean.getWatchpointId());
                    updateAlarmSetBean.setBusinessId(Long.parseLong(busIdItem));
                    updateAlarmSetBean.setKpitype(alarmSetBeanStep1.getKpitype());
                    updateAlarmSetBean.setKpiId(alarmSetBeanStep1.getKpiId());
                    updateAlarmSetBean.setUpdateflag("Y");
                    for(Map.Entry<Long, AlarmSetBean> entrySub : mapItem.entrySet()){
                        Long levelIdItem= entrySub.getKey();
                        Double valueItemHigh= standardLevelValueMap.get(levelIdItem+"|"+"0");
                        Double valueItemLow= standardLevelValueMap.get(levelIdItem+"|"+"1");
                        updateAlarmSetBean.setLowthresh(valueItemLow);
                        updateAlarmSetBean.setHighthresh(valueItemHigh);
                        updateAlarmSetBean.setLevelId(levelIdItem);
                        this.updateAlarmSetForGlobal(updateAlarmSetBean);
                    }
                }
                //添加ipm_alarm_trigger
                for (Map.Entry<String, Map<Long, AlarmSetBean>> entry : alarmSetInfoMap.entrySet()) {
                    Map<Long, AlarmSetBean> mapItem=entry.getValue();
                    if(mapItem==null){
                        continue;
                    }
                    
                    long nowTime=DateUtils.getNowTimeSecond(); //当前时间

                    for(Map.Entry<Long, AlarmSetBean> entrySub : mapItem.entrySet()){
                        int[] intArray = {0, 1};
                        for(int i=0; i<intArray.length; i++){
                            AlarmTriggerBean addAlarmTriggerBean =new AlarmTriggerBean();
                            Long levelIdItem= entrySub.getKey();
                            AlarmSetBean alarmSetBeanTempForAdd=entrySub.getValue();
                            Double valueItem= standardLevelValueMap.get(levelIdItem+"|"+intArray[i]);
                            addAlarmTriggerBean.setAlarmsetId(alarmSetBeanTempForAdd.getId());
                            addAlarmTriggerBean.setTriggerflag(intArray[i]);
                            addAlarmTriggerBean.setTriggerusestatus(intArray[i]==0
                                    ?alarmSetBean.getSingleHighThreshUsedFlag()
                                    :alarmSetBean.getSingleLowThreshUsedFlag());
                            addAlarmTriggerBean.setTriggervalue(valueItem);
                            //获取单位
                            KpisBean kpisBeanTemp=useAlarmKpiIdGetKpisInfo(alarmSetBeanTempForAdd.getKpiId());
                            addAlarmTriggerBean.setTriggerunit(kpisBeanTemp.getUnit());
                            addAlarmTriggerBean.setTriggertime(nowTime);
                            this.addAlarmTriggerForGlobal(addAlarmTriggerBean);
                        }
                    }
                    
                }
                //添加或者修改ipm_alarm_globalset ipm_alarm_globalvalue
                
                AlarmGlobalSetBean alarmGlobalSetBean =new AlarmGlobalSetBean();
                alarmGlobalSetBean.setModuleId((int)alarmSetBeanStep1.getModuleId());
                alarmGlobalSetBean.setWatchpointId(alarmSetBeanStep1.getWatchpointId());
                alarmGlobalSetBean.setKpitype(alarmSetBeanStep1.getKpitype());
                alarmGlobalSetBean.setKpiId(alarmSetBeanStep1.getKpiId());
                
                List<AlarmGlobalValueBean> alarmGlobalList=new ArrayList<AlarmGlobalValueBean>();
                AlarmGlobalValueBean alarmGlobalValueBean=null;
                for (Map.Entry<String, Map<Long, AlarmSetBean>> entry : alarmSetInfoMap.entrySet()) {
                    String busIdItem= entry.getKey();                    
                    Map<Long, AlarmSetBean> mapItem=entry.getValue();
                    if(mapItem==null){
                        continue;
                    }
                    if(Long.parseLong(busIdItem)==alarmSetBeanStep1.getBusinessId()){
                        int[] intArray = {0, 1};
                        for(Map.Entry<Long, AlarmSetBean> entrySub : mapItem.entrySet()){
                            Long levelIdItem= entrySub.getKey();
                            for(int i=0; i<intArray.length; i++){
                                Double valueItem= standardLevelValueMap.get(levelIdItem+"|"+intArray[i]);
                                alarmGlobalValueBean=new AlarmGlobalValueBean();
                                alarmGlobalValueBean.setLevelId(Integer.parseInt(String.valueOf(levelIdItem)));
                                alarmGlobalValueBean.setTriggerflag(intArray[i]);
                                alarmGlobalValueBean.setTriggerusestatus(intArray[i]==0
                                        ?alarmSetBean.getSingleHighThreshUsedFlag()
                                        :alarmSetBean.getSingleLowThreshUsedFlag());
                                alarmGlobalValueBean.setTriggerValue(valueItem);
                                alarmGlobalList.add(alarmGlobalValueBean); 
                            }
                          
                        }
                    }
                }
                
                this.insertOrUpdateAlarmGlobalsetGlobalvalue(alarmGlobalSetBean, alarmGlobalList);

                flag=true;
            }
        }
        
        //修改后执行
        AlarmSetThreadUtil.setAlarmSetThreadModNativeKpi(alarmSetBeanList, alarmSetThreadDao);
        
        //情况智能告警设置表的响应kpi数据
        delAlarmBaseLineByKid(watchpointBeanList, alarmSetBeanList);
        
        
        return flag;
    }
    
    /**
     * 
     * @Title: delAlarmBaseLineByKid
     * @Description: 告警阈值修改时，清空其智能告警设置(根据当前业务的kpi清空)只要这个业务的这个KPI改了，不管是否其改了智能告警告警与否，都清空，即为重新开始计算
     * @param watchpointBeanList
     * @param alarmSetBeanList void
     * @author chensq
     */
    public void delAlarmBaseLineByKid(List<WatchpointBean> watchpointBeanList, List<AlarmSetBean> alarmSetBeanList){
        //迭代清空
        if(alarmSetBeanList!=null && alarmSetBeanList.size()>0){
            //临时对象
            AlarmBaseLineBean alarmBaseLineBean=null;
            for (int i = 0; i < alarmSetBeanList.size(); i++) {
                //初始化
                alarmBaseLineBean=new AlarmBaseLineBean();
                //组合参数前获取值
                AlarmSetBean alarmSetBean=alarmSetBeanList.get(i);                
                AlarmKpiBean alarmKpiBean=alarmKpiDao.getAlarmKpiById(alarmSetBean.getKpiId());
                //组合参数
                if(alarmSetBean.getModuleId()==10){//观察点情况
                    alarmBaseLineBean.setWatchpointId(alarmSetBean.getWatchpointId());
                    alarmBaseLineBean.setModuleId(alarmSetBean.getModuleId());
                    alarmBaseLineBean.setBusinessId(alarmSetBean.getBusinessId());
                    alarmBaseLineBean.setKpiId(alarmKpiBean.getKpiId());
                    //清空
                    alarmBaseLineDao.delAlarmBaseLineByKpiId(alarmBaseLineBean);
                }else{//非观察点情况
                    if(watchpointBeanList!=null && watchpointBeanList.size()>0){
                        for(int x=0; x<watchpointBeanList.size(); x++){
                            WatchpointBean tempWpBean=watchpointBeanList.get(x);
                            alarmBaseLineBean.setWatchpointId(tempWpBean.getId());
                            alarmBaseLineBean.setModuleId(alarmSetBean.getModuleId());
                            alarmBaseLineBean.setBusinessId(alarmSetBean.getBusinessId());
                            alarmBaseLineBean.setKpiId(alarmKpiBean.getKpiId());
                            //清空
                            alarmBaseLineDao.delAlarmBaseLineByKpiId(alarmBaseLineBean);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * @Title: doInsertAlarmSet
     * @Description: 执行alarmSet添加
     * @param alarmSetList 
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean doInsertAlarmSet(List<AlarmSetBean> alarmSetList){
        boolean flag=false;
        if (alarmSetList!=null && alarmSetList.size()>0) {
            for (int x=0; x<alarmSetList.size(); x++) {
                alarmSetDao.addAlarmSet(alarmSetList.get(x));
            }
            flag=true;
        }
        return flag;
    }
    
    /**
     * @Title: doInsertAlarmTrigger
     * @Description: 执行alarmSet修改
     * @param tempAlarmTriggerList 
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean doInsertAlarmTrigger(List<AlarmTriggerBean> tempAlarmTriggerList){
        boolean flag=false;
        if (tempAlarmTriggerList!=null && tempAlarmTriggerList.size()>0) {
            for (int x=0; x<tempAlarmTriggerList.size(); x++) {
                alarmTriggerDao.addAlarmTrigger(tempAlarmTriggerList.get(x));
            }
            flag=true;
        }
        return flag;
    }
    
    /**
     * @Title: doUpdateAlarmSet
     * @Description: 执行alarmSet修改
     * @param alarmSetList 
     * @return boolean true：成功,false:失败
     * @author chensq
     */
    public boolean doUpdateAlarmSet(List<AlarmSetBean> alarmSetList){
        boolean flag=false;
        if (alarmSetList!=null && alarmSetList.size()>0) {
            for (int x=0; x<alarmSetList.size(); x++) {
                alarmSetDao.updateAlarmSetById(alarmSetList.get(x));
            }
            flag=true;
        }
        return flag;
    }
    
    /**
     * @Title: getAlarmLevelMap
     * @Description: 将AlarmLevel的list集合转为map集合
     * @param alarmLevelList 
     * @return map<Stirng,Object>
     * @author chensq
     */
    public Map<String, AlarmLevelBean> getAlarmLevelMap(List<AlarmLevelBean> alarmLevelList) {
        Map<String, AlarmLevelBean> alarmLevelMap=null;
        if (alarmLevelList!=null && alarmLevelList.size()>0) {
            alarmLevelMap=new HashMap<String, AlarmLevelBean>();
            for (int x=0; x<alarmLevelList.size(); x++) {
                long alarmLevelId=alarmLevelList.get(x).getId();
                alarmLevelMap.put(String.valueOf(alarmLevelId), alarmLevelList.get(x));
            }
        }
        return alarmLevelMap;
    }
        
    /**
     * @Title: useAlarmKpiIdGetKpisInfo
     * @Description: 根据告警设置中的kpiid查询kpis info
     * @param alarmKpiId
     * @return KpisBean
     * @author chensq
     */
    public KpisBean useAlarmKpiIdGetKpisInfo(long alarmKpiId){
         //获取AlarmKpi对象
        AlarmKpiBean alarmKpiBean= alarmKpiDao.getAlarmKpiById(alarmKpiId);
         //根据kpi查询kpis
        return kpisDao.getKpisById((int)alarmKpiBean.getKpiId());
    }
    
    /**
     * @Title: getAlarmSetById
     * @Description: 根据告警设置id获取告警设置
     * @param id
     * @return AlarmSetBean
     * @author chensq
     */
    public  AlarmSetBean getAlarmSetById(long id){
        AlarmSetBean tempAlarmSetBean= alarmSetDao.getAlarmSetById(id);
        return tempAlarmSetBean;
    }                   
  
    /**
     * @Title: getAlarmSetIdByParam
     * @Description: 组合告警插入使用
     * @param alarmSetBean
     * @return Long
     * @author chensq
     */
    public Long getAlarmSetIdByParam(AlarmSetBean alarmSetBean){
        long id= alarmSetDao.getAlarmSetIdByParam(alarmSetBean);
        return id;
    }
    
    //-------------------------全局告警使用-----start-------------------------
   
    /**
     * @Title: getBusIdAndAlarmSetInfoByModuleId
     * @Description: 根据模块id查询其下的所有业务id,以及其对应的告警设置信息
     * @param alarmSetBean
     * @return Map<String,Map<Long,AlarmSetBean>>
     * @author chensq
     */
    public Map<String, Map<Long, AlarmSetBean>> getBusIdAndAlarmSetInfoByModuleId(AlarmSetBean alarmSetBean){
        //目前告警阈值设置统一不区分观察点
        alarmSetBean.setWatchpointId(0);
        
        Map<String, Map<Long, AlarmSetBean>> map=null ;
        if(alarmSetBean.getModuleId()==10){//观察点
            map=new HashMap<String, Map<Long, AlarmSetBean>>();
            List<WatchpointBean> watchpointBeanList =watchpointDao.getFindAll();
            if(watchpointBeanList!=null && watchpointBeanList.size()>0){
                for(int b=0; b<watchpointBeanList.size(); b++){
                    map.put(String.valueOf(watchpointBeanList.get(b).getId()), null);
                    List<AlarmSetBean> alarmSetBeanList=alarmSetDao.getAlarmSetListByKpiId(
                            alarmSetBean.getWatchpointId(),
                            watchpointBeanList.get(b).getId(),
                            alarmSetBean.getModuleId(),
                            alarmSetBean.getKpitype(),
                            alarmSetBean.getKpiId());
                    if(alarmSetBeanList!=null && alarmSetBeanList.size()>0){
                        Map<Long, AlarmSetBean> innerMap=new HashMap<Long, AlarmSetBean>();
                        for(int s=0; s<alarmSetBeanList.size(); s++){                            
                            innerMap.put(alarmSetBeanList.get(s).getLevelId(), alarmSetBeanList.get(s));
                        }
                        map.put(String.valueOf(watchpointBeanList.get(b).getId()), innerMap);
                    }
                }
            }
            
        }else{//其他业务
            map=new HashMap<String, Map<Long, AlarmSetBean>>();
            List<AppBusinessBean> appBusinessBeanList = appBusinessDao.selectAppBusinessesByModuleId((
                    Integer.parseInt(String.valueOf(alarmSetBean.getModuleId()))));
            if(appBusinessBeanList!=null && appBusinessBeanList.size()>0){
                for(int b=0; b<appBusinessBeanList.size(); b++){
                    map.put(String.valueOf(appBusinessBeanList.get(b).getId()), null);
                    List<AlarmSetBean> alarmSetBeanList=alarmSetDao.getAlarmSetListByKpiId(
                            alarmSetBean.getWatchpointId(),
                            appBusinessBeanList.get(b).getId(),
                            alarmSetBean.getModuleId(),
                            alarmSetBean.getKpitype(),
                            alarmSetBean.getKpiId());
                    if(alarmSetBeanList!=null && alarmSetBeanList.size()>0){
                        Map<Long, AlarmSetBean> innerMap=new HashMap<Long, AlarmSetBean>();
                        for(int s=0; s<alarmSetBeanList.size(); s++){                            
                            innerMap.put(alarmSetBeanList.get(s).getLevelId(), alarmSetBeanList.get(s));
                        }
                        map.put(String.valueOf(appBusinessBeanList.get(b).getId()), innerMap);
                    }
                }
            }
        }
        return map;
    }
        
    /**
     * @Title: updateAlarmSetForGlobal
     * @Description: 执行全局告警修改的方法(AlarmSet)
     * @param alarmSetBean void
     * @author chensq
     */
    public void updateAlarmSetForGlobal(AlarmSetBean alarmSetBean){
        alarmSetDao.updateAlarmGlobalSet(alarmSetBean);
    }
        
    /**
     * @Title: addAlarmTriggerForGlobal
     * @Description: 执行全局告警历史阈值添加的方法(AlarmTrigger)
     * @param alarmTriggerBean void
     * @author chensq
     */
    public void addAlarmTriggerForGlobal(AlarmTriggerBean alarmTriggerBean){
        alarmTriggerDao.addAlarmTrigger(alarmTriggerBean);
    }
     
    /**
     * @Title: insertOrUpdateAlarmGlobalsetGlobalvalue
     * @Description: 告警全局设置或告警全局设置值表添加或修改
     * @param alarmGlobalSetBean
     * @param alarmGlobalValueBeanList void
     * @author chensq
     */
    public void insertOrUpdateAlarmGlobalsetGlobalvalue(
            AlarmGlobalSetBean alarmGlobalSetBean,
            List<AlarmGlobalValueBean> alarmGlobalValueBeanList){
        long alarmGlobalSetId=0;
        List<AlarmGlobalSetBean> gsetList=alarmGlobalSetDao.getAlarmGlobalSetByParam(alarmGlobalSetBean);
        if(gsetList!=null && gsetList.size()>0){//已经存在
            alarmGlobalSetId =gsetList.get(0).getId();
            //首先删除以前的全局值
            alarmGlobalValueDao.deleteAlarmGlobalValue(alarmGlobalSetId);
            //再次再添加
            if(alarmGlobalValueBeanList!=null && alarmGlobalValueBeanList.size()>0){
                for(int x=0; x<alarmGlobalValueBeanList.size(); x++){
                    alarmGlobalValueBeanList.get(x).setGlobalsetId(alarmGlobalSetId);
                    alarmGlobalValueDao.insertAlarmGlobalValue(alarmGlobalValueBeanList.get(x));
                }
            }
        }else{//未存在
            alarmGlobalSetDao.insertAlarmGlobalSet(alarmGlobalSetBean); 
            alarmGlobalSetId= alarmGlobalSetBean.getId();
            if(alarmGlobalValueBeanList!=null && alarmGlobalValueBeanList.size()>0){
                for(int x=0; x<alarmGlobalValueBeanList.size(); x++){
                    alarmGlobalValueBeanList.get(x).setGlobalsetId(alarmGlobalSetId);
                    alarmGlobalValueDao.insertAlarmGlobalValue(alarmGlobalValueBeanList.get(x));
                }
            }
        }
    }
    
    //-------------------------全局告警使用-----end-------------------------

}
