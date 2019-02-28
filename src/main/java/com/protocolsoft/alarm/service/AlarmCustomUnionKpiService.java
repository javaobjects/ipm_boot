/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiService
 *创建人:chensq    创建时间:2018年4月2日
 */
package com.protocolsoft.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpigroupBean;
import com.protocolsoft.alarm.bean.AlarmKpiBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.dao.AlarmCustomUnionKpiDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.dao.AlarmSetThreadDao;
import com.protocolsoft.alarm.util.AlarmLogUtil;
import com.protocolsoft.alarm.util.AlarmSetThreadUtil;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.user.bean.AuthorizeModuleBean;
import com.protocolsoft.user.dao.AuthorizeModuleDao;
import com.protocolsoft.utils.ArrayCutUtils;
import com.protocolsoft.utils.UnitUtils;

/**
 * @ClassName：AlarmCustomUnionKpiService 
 * @Description: 自定义组合kpi使用的service
 * @author chensq  
 * @see
 */

@Service
public class AlarmCustomUnionKpiService {
 
    /**
     * @Fields alarmCustomUnionKpiDao : 告警基线阈值
     */
    @Autowired
    private AlarmCustomUnionKpiDao alarmCustomUnionKpiDao;
    
    /**
     * @Fields alarmKpiDao : 告警kpi  DAO
     */
    @Autowired
    private AlarmKpiDao alarmKpiDao;
     
    /**
     * @Fields kpisDao : kpi dao
     */
    @Autowired
    private KpisDao kpisDao;
    
    /**
     * @Fields alarmSetService : AlarmSet service
     */
    @Autowired
    private AlarmSetService alarmSetService;
    
    /**
     * @Fields alarmLogDao : alarmLog Dao
     */
    @Autowired
    private AlarmLogDao alarmLogDao;
     
    /**
     * @Fields centerService : Center
     */
    @Autowired
    private CenterIpService centerService;
    
    /**
     * @Fields alarmSetThreadDao : alarmSetThread Dao
     */
    @Autowired
    private AlarmSetThreadDao alarmSetThreadDao;
  
    /**
     * @Fields authorizeModuleDao : 注入 authorizeModule Dao
     */
    @Autowired
    AuthorizeModuleDao authorizeModuleDao;
    
    /**
     * @Title: getCustomUnionKpisList
     * @Description: 获取设置kpis的列表
     * @param alarmCustomkpiBean
     * @return List<AlarmCustomUnionKpiBean>
     * @author chensq
     */
    public List<AlarmCustomUnionKpiBean> getCustomUnionKpisList(AlarmCustomkpiBean alarmCustomkpiBean){
        //单位转换
        Map<String, String> kpisUnitMap=UnitUtils.changeKpiUnitMap();        
        
        //返回
        List<AlarmCustomUnionKpiBean> list=new ArrayList<AlarmCustomUnionKpiBean>();
        
        //迭代模块下所有的告警kpi
        AlarmCustomUnionKpiBean alarmCustomUnionKpiBean=null;
        List<AlarmKpiBean> alarmKpiList=alarmKpiDao.getAlarmKpiListByModuleId(alarmCustomkpiBean.getModuleId(), "");
        if(alarmKpiList!=null && alarmKpiList.size()>0){
            for(int x=0; x<alarmKpiList.size(); x++){
                AlarmKpiBean alarmKpiBean=alarmKpiList.get(x);
                alarmCustomUnionKpiBean=new AlarmCustomUnionKpiBean();
                alarmCustomUnionKpiBean.setId(alarmKpiBean.getKpiId());
                list.add(alarmCustomUnionKpiBean);
            }
        }
        
        //查询有效的(可设置的)
        Map<String, String> canSelMap=new HashMap<String, String>();
        
        AlarmSetBean alarmSetBean=new AlarmSetBean();
        alarmSetBean.setWatchpointId(0);
        alarmSetBean.setModuleId(alarmCustomkpiBean.getModuleId());
        alarmSetBean.setBusinessId(alarmCustomkpiBean.getBusinessId());        
        
        //insql String list
        List<String> insqlStrList=new ArrayList<String>();
        
        String idList=alarmCustomUnionKpiDao.getAlarmCanUsedSetIdsList(alarmSetBean);
        //切分
        Object[] subAry =null;
        if(StringUtils.isNotEmpty(idList)){
            subAry= ArrayCutUtils.splitAry(idList.split(","), 5); 
        }
   
        //array to list
        if(subAry!=null && subAry.length>0){
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
                    fullBuf.append(" a.alarmset_id ");
                    fullBuf.append(insqlStrList.get(x));
                    if (x!=insqlStrList.size()-1) {
                        fullBuf.append(" or ");
                    }
                }
            }
            
            if(StringUtils.isNotEmpty(fullBuf.toString())){
                alarmSetBean.setIdsInSql("where " + fullBuf.toString());
            }
        }
        
        List<AlarmSetBean> canSelectedSetList= alarmCustomUnionKpiDao.getAlarmCanUsedKpisList(alarmSetBean);
        if(canSelectedSetList!=null && canSelectedSetList.size()>0){
            for(int x=0; x<canSelectedSetList.size(); x++){
                canSelMap.put(String.valueOf(canSelectedSetList.get(x).getKpiId()), String.valueOf(canSelectedSetList.get(x).getKpiId()));
            }
        }
        
        //已设置的
        Map<String, String> selMap=new HashMap<String, String>();

        List<AlarmCustomkpigroupBean> selectedSetList=alarmCustomUnionKpiDao.getAlarmUsedKpisList(alarmCustomkpiBean);
        if(selectedSetList!=null && selectedSetList.size()>0){
            for(int x=0; x<selectedSetList.size(); x++){
                selMap.put(String.valueOf(selectedSetList.get(x).getKpiId()), String.valueOf(selectedSetList.get(x).getKpiId()));
            }
        }
        
        //组装最终返回
        if(list!=null && list.size()>0){
            for(int x=0; x<list.size(); x++){
                AlarmCustomUnionKpiBean  returnBean=list.get(x);
                int id=(int) list.get(x).getId();
                KpisBean kpiBean=  kpisDao.getKpisByIdModuleId(id, alarmCustomkpiBean.getModuleId());
                //设置值 kpi基础信息
                returnBean.setKpiName(kpiBean.getName());
                returnBean.setNameValue(kpiBean.getDisplayName()); 
                returnBean.setKpiUnit(kpisUnitMap.get(kpiBean.getUnit()));

                //设置值 kpi是否可设置
                if(canSelMap.get(String.valueOf(id))!=null){
                    returnBean.setGroupKpiCanUsed(1);
                }else{
                    returnBean.setGroupKpiCanUsed(0);
                }
                
                //设置值 kpi是否已经设置
                if(selMap.get(String.valueOf(id))!=null){
                    returnBean.setGroupKpiSelected(1);
                }else{
                    returnBean.setGroupKpiSelected(0);
                }
                
                //特殊情况，不可设置，但已经设置
                if(canSelMap.get(String.valueOf(id))==null){
                    if(selMap.get(String.valueOf(id))!=null){
                        returnBean.setGroupKpiCanUsed(0);
                        returnBean.setGroupKpiSelected(0);
                    }
                }
                
                //设置其他信息
                returnBean.setModuleId(alarmCustomkpiBean.getModuleId());
                returnBean.setBusinessId(alarmCustomkpiBean.getBusinessId());
            }
        }
        
        return list;
        
    }
   
    /**
     * @Title: getCustomkpiInfo
     * @Description: 获取告警组合设置基础信息
     * @param alarmCustomkpiBean
     * @return AlarmCustomkpiBean
     * @author chensq
     */
    public AlarmCustomkpiBean getCustomkpiInfo(AlarmCustomkpiBean alarmCustomkpiBean){
        List<AlarmCustomkpiBean> listCustomKpi=alarmCustomUnionKpiDao.getAlarmUnKpisByParam(alarmCustomkpiBean);
        if(listCustomKpi!=null && listCustomKpi.size()>0){
            return listCustomKpi.get(0);
        }else{
            AlarmCustomkpiBean alarmCustomkpiBeanNullBean=new AlarmCustomkpiBean();
            alarmCustomkpiBeanNullBean.setGroupKpiName("");
            return alarmCustomkpiBeanNullBean;
        }
    }
   
    /**
     * @Title: addAlarmCustomUnionKpis
     * @Description: 添加组合告警
     * @param alarmCustomkpiBean
     * @return boolean
     * @author chensq
     */
    public boolean addAlarmCustomUnionKpis(AlarmCustomkpiBean alarmCustomkpiBean){
        //ipm_alarm_customkpi  ipm_alarm_customkpigroup
        boolean flag=false;
        List<AlarmCustomkpiBean>  alarmCustomkpiBeanList=alarmCustomUnionKpiDao.getAlarmUnKpisByParam(alarmCustomkpiBean);
        if(alarmCustomkpiBeanList!=null && alarmCustomkpiBeanList.size()>0){//修改情况
            for(int x=0; x<alarmCustomkpiBeanList.size(); x++){
                long id= alarmCustomkpiBeanList.get(x).getId();
                //设置id
                alarmCustomkpiBean.setId(id);
                this.insertOrUpdateCustomUnKpi(alarmCustomkpiBean);
            }
        }else{//添加情况
            alarmCustomkpiBean.setNameen("customUnionKpi"+alarmCustomkpiBean.getModuleId()
                    +"-"+alarmCustomkpiBean.getBusinessId());
            alarmCustomUnionKpiDao.addCustomkpi(alarmCustomkpiBean);
            //添加新的groups
            this.toAddCustomkpigroup(alarmCustomkpiBean);
            
            //alarmSet 表中增加数据
            AlarmSetBean alarmSetBeanInsertTemp=new AlarmSetBean();
            alarmSetBeanInsertTemp.setBusinessId(alarmCustomkpiBean.getBusinessId());
            alarmSetBeanInsertTemp.setKpiId(alarmCustomkpiBean.getId());
            alarmSetService.addCustomUnionKpi(alarmSetBeanInsertTemp);
        }
                
        //重新调用，将告警信息设置入内存        
        AlarmSetBean tempAlarmSetBean=new AlarmSetBean();
        tempAlarmSetBean.setWatchpointId(0);
        tempAlarmSetBean.setModuleId(alarmCustomkpiBean.getModuleId());
        tempAlarmSetBean.setBusinessId(alarmCustomkpiBean.getBusinessId());
        tempAlarmSetBean.setKpitype(2);
        tempAlarmSetBean.setKpiId(alarmCustomkpiBean.getId());
        AlarmSetThreadUtil.setAlarmSetThreadGrp(tempAlarmSetBean, alarmSetThreadDao);
        
        flag=true;
        return flag;
    }
    
    /**
     * @Title: insertOrUpdateCustomUnKpi
     * @Description: 执行修改或者添加
     * @param alarmCustomkpiBean void
     * @author chensq
     */
    public void insertOrUpdateCustomUnKpi(AlarmCustomkpiBean alarmCustomkpiBean){
        if(alarmCustomkpiBean.getId()>0){//有 需要修改
            //修改 ipm_alarm_customkpi
            alarmCustomUnionKpiDao.updateAlarmSetByIds(alarmCustomkpiBean);
            //删除原有
            alarmCustomUnionKpiDao.delAlarmCustomKpsByCusId(alarmCustomkpiBean.getId());
            //添加新的groups
            this.toAddCustomkpigroup(alarmCustomkpiBean);
        }
    }
     
    /**
     * @Title: toAddCustomkpigroup
     * @Description: 执行添加
     * @param alarmCustomkpiBean void
     * @author chensq
     */
    public void toAddCustomkpigroup(AlarmCustomkpiBean alarmCustomkpiBean){
        if(!"".endsWith(alarmCustomkpiBean.getGroupKpis())){
            String []kpis= alarmCustomkpiBean.getGroupKpis().split(",");
            if(kpis!=null && kpis.length>0){
                AlarmCustomkpigroupBean addItem=null;
                for(int x=0; x<kpis.length; x++){
                    addItem=new AlarmCustomkpigroupBean();
                    addItem.setCustomkpiId(alarmCustomkpiBean.getId());
                    addItem.setKpiId(Long.parseLong(kpis[x]));
                    alarmCustomUnionKpiDao.addCustomkpigroup(addItem);
                }
            }
        }
    }
    
    /**
     * @Title: getAlarmLogsByUnionkpi
     * @Description: 组合kpi钻取使用
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    public List<AlarmLogBean> getAlarmLogsByUnionkpi(AlarmLogBean alarmLogBean){
        //返回 log list
        List<AlarmLogBean> returnLogBeanList=null;
        //根据组合告警的id查询告警的其他信息
        List<AlarmSetBean> alarmSetList=alarmCustomUnionKpiDao.getAlarmUnKpiByLogId(alarmLogBean);
        
        //获取模块名称 
        List<AuthorizeModuleBean> moduleList=authorizeModuleDao.selectAllAuthorizeModule();
        Map<String, String> map=AlarmLogUtil.getAllModuleMap(moduleList);
        
        if(alarmSetList!=null && alarmSetList.size()>0){
            returnLogBeanList=new ArrayList<AlarmLogBean>();
            for(int x=0; x<alarmSetList.size(); x++){
                AlarmSetBean alarmSetTemp= alarmSetList.get(x);
                alarmSetTemp.setStarttime(alarmLogBean.getStarttime());
                alarmSetTemp.setEndtime(alarmLogBean.getEndtime());
                long alarmSetId=alarmCustomUnionKpiDao.getAlarmUnionEdSid(alarmSetTemp);
                
                AlarmLogBean searchLogBean=new AlarmLogBean();
                searchLogBean.setAlarmsetId(alarmSetId);
                searchLogBean.setStarttime(alarmLogBean.getStarttime());
                searchLogBean.setEndtime(alarmLogBean.getEndtime());
                
                AlarmLogBean  getBean11=alarmLogDao.getAlarmLogBySetIdParam(searchLogBean);
                
                getBean11.setModuleName(map.get(String.valueOf(getBean11.getModuleId())));
                
                returnLogBeanList.add(getBean11);
                
            }
            
        }
        return returnLogBeanList;
    }

    /**
     * @Title: getRemoteUnionKpiLog
     * @Description: 组合告警
     * @param alarmLogBean
     * @return List<AlarmLogBean>
     * @author www
     */
    public List<Object> getRemoteUnionKpiLog(AlarmLogBean alarmLogBean) {
        List<Object> list = null;
        String url = "/customUnionKpi/getUnionKpiLog.do";
        Integer centerId = alarmLogBean.getIpmCenterId();
        list = centerService.getRemoteSessionList(url, alarmLogBean, AlarmLogBean.class, centerId);
        
        return list;
    }
}
