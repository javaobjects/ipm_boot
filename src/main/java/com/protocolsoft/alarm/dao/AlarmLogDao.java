/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogDao
 *创建人:chensq    创建时间:2017年11月6日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmLogFindParamBean;
import com.protocolsoft.alarm.util.AlarmConstantUtil;

/**
 * @ClassName: AlarmLogDao
 * @Description: AlarmLog DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmLogDao {
    /**
     * 
     * @Title: delAlarmLogByAlarmsetId
     * @Description: 根据阈值设置id删除告警日志记录(告警日志表ipm_alarm_log)
     * @param alarmsetId
     * @return boolean
     * @author chensq
     */
    @Delete("delete from ipm_alarm_log where alarmset_id=${alarmsetId}")
    boolean delAlarmLogByAlarmsetId(@Param("alarmsetId") long  alarmsetId);
        
    /**
     * @Title: addAlarmLog
     * @Description: 添加告警阈值设置
     * @param alarmLogBean
     * @return int
     * @author chensq
     */
    @Insert("insert into ipm_alarm_log "
            + "(watchpoint_id,alarmset_id,starttime,endtime,handledflag,finishflag,triggerflag) "
            + " values "
            + "(#{watchpointId},#{alarmsetId},#{starttime},#{endtime},#{handledflag},#{finishflag},#{triggerflag})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addAlarmLog(AlarmLogBean alarmLogBean);
    
    //-------------------
    
    /**
     * @Title: getAlarmLogMinMaxTimeByParam
     * @Description: 根据条件查新告警log的最大最小时间
     * @param alarmLogFindParamBean 拼接sql参数信息
     * @return AlarmLogBean
     * @author chensq
     */
    @Select("SELECT "
            + "MIN(l.starttime) AS starttime,"
            + "MAX(l.starttime) AS endtime "
            + "FROM "
            + "ipm_alarm_log l "
            + "LEFT JOIN ipm_alarm_set  s ON s.id=l.alarmset_id "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id AND s.kpitype=1 "
            + "LEFT JOIN ipm_alarm_customkpi ac ON ac.id=s.kpi_id AND s.kpitype=2 "
            + "LEFT JOIN ipm_kpis k ON ak.kpi_id=k.id AND s.kpitype=1 "
            + "WHERE "
            + "l.watchpoint_id =${watchpointsql} and "
            + "(ak.module_id=${modulesql} OR ac.module_id=${modulesql}) and "
            + "s.business_id=${businesssql} "
            + "${handledflagsql} "
            + "${alarmLevelsql} "
            + "${starttimesql} "            
            + "${endtimesql} "
            + "${kpitypesql} "
            + "${kpisql} "
            + "${kIdSql} ")
    AlarmLogBean getAlarmLogMinMaxTimeByParam(AlarmLogFindParamBean alarmLogFindParamBean);
        
    /**
     * @Title: getAlarmLogCountByParam
     * @Description: 根据条件查新告警log的最严重级别与数量
     * @param alarmLogFindParamBean 拼接sql参数信息
     * @return AlarmLogBean
     * @author chensq
     */
    @Select("SELECT "
            + "COUNT(l.id) AS COUNT, "
            + "MAX(CASE l.handledflag WHEN 'N' THEN s.level_id ELSE 1 END) AS alarmLevelId "
            + "FROM "
            + "ipm_alarm_log l "
            + "LEFT JOIN ipm_alarm_set  s ON s.id=l.alarmset_id "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id AND s.kpitype=1 "
            + "LEFT JOIN ipm_alarm_customkpi ac ON ac.id=s.kpi_id AND s.kpitype=2 "
            + "LEFT JOIN ipm_kpis k ON ak.kpi_id=k.id AND s.kpitype=1 "
            + "WHERE "
            + "l.watchpoint_id =${watchpointsql} and "
            + "(ak.module_id=${modulesql} OR ac.module_id=${modulesql}) and "
            + "s.business_id=${businesssql} "
            + "${handledflagsql} "
            + "${alarmLevelsql} "
            + "${starttimesql} "            
            + "${endtimesql} "
            + "${kpitypesql} "
            + "${kpisql} "
            + "${kIdSql} ")
    AlarmLogBean getAlarmLogCountByParam(AlarmLogFindParamBean alarmLogFindParamBean);
    //-------------------
    
    /**
     * @Title: getAlarmLogDataByParam
     * @Description: 根据条件获取告警集合
     * @param alarmLogFindParamBean
     * @return list<AlarmLogBean>
     * @author chensq
     */
    @Select(
            "SELECT "
            
            //alarmLog固有字段 start
            +"l.id AS id, "
            +"IF(w.id IS NULL,0,w.id) AS watchpointId, "
            +"l.alarmset_id AS alarmsetId, "
            +"l.starttime AS starttime, "
            +"l.endtime AS endtime, "
            +"l.handledflag AS handledflag, "
            +"l.finishflag AS finishflag, "
            +"l.responseuser AS responseuser, "
            +"l.responsetime AS responsetime, "
            +"l.triggerflag AS triggerflag, "
            //阈值类型
            +"( CASE WHEN l.triggerflag=0 THEN '高阈值' "
            +"WHEN l.triggerflag=1 THEN '低阈值' "
            +"WHEN l.triggerflag=2 THEN '智能告警阈值' "
            +"WHEN l.triggerflag=3 THEN '波测超时' "
            +"WHEN l.triggerflag=4 THEN '组合告警' "
            +"WHEN l.triggerflag=5 THEN '探针同步' END  "
            +") AS triggerflagStr, "           
            //alarmLog固有字段 end
            
            //alarmset字段 start
            +"IF(w.name IS NULL ,'',w.name) AS watchpointName, "
            +"IF(ac.module_id IS NULL,ak.module_id ,ac.module_id) AS moduleId, "
            +"IF(w1.id IS NULL,ab.id,w1.id) AS businessId, "
            +"IF(w1.name IS NULL,ab.name,w1.name) AS businessName, "
            +"s.kpitype AS kpitype, "
            +"s.kpi_id AS alarmKpiId, "
            //alarmset字段 end
            
            //告警级别
            +"s.level_id AS alarmLevelId, "            

            //kpis的字段 start
            +"IF(ac.id IS NULL,k.id,ac.id) AS kpisId, "
            +"IF(ac.nameen IS NULL,k.name,ac.nameen) AS kpisName, "
            +"IF(ac.namezh IS NULL,IF(k.name in("+AlarmConstantUtil.SAMESPECIALMANAGEKPINAME+"),"
            +"k.display_name,o.name),ac.namezh) AS kpisDisplayName, "
            +"IF(k.unit IS NULL,'',k.unit) AS kpisUnit, "
            +"IF(k.cause IS NULL,'',k.cause) AS cause, "
            //kpis的字段 end
            
            //-----------------其他字段-start------------------
            +"u.realname AS responseuserDisplayName "
            //-----------------其他字段-end------------------
            +"FROM "
            +"ipm_alarm_log l "
            +"LEFT JOIN ipm_alarm_set  s         ON   s.id=l.alarmset_id "
            +"LEFT JOIN ipm_alarm_kpi  ak        ON   ak.id=s.kpi_id AND s.kpitype=1 "
            //-- k.display_name(显示) k.display_name like '%流量%'  or k.id =  
            +"LEFT JOIN ipm_kpis  k              ON   k.id=ak.kpi_id AND s.kpitype=1 "
            //新的方式，获取kpi名称
            +"LEFT JOIN ipm_plot_option     o    ON   o.kpi_id = ak.kpi_id AND s.kpitype=1 AND o.module_id=ak.module_id AND "
            + "(o.number = 1 or (o.number = 0 AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325) "  
            //-- ac.namezh(显示)    ac.namezh like '%服务%'  or  ac.id =      
            +"LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "  
             //-- l.responseuser=1  or  u.realname like '%Jcts%'
            +"LEFT JOIN ipm_system_user   u      ON   u.id=l.responseuser "         
            //-- l.watchpoint_id=1   w.name like '观察点'   log的观察点
            +"LEFT JOIN ipm_watchpoint w         ON   w.id = l.watchpoint_id AND l.watchpoint_id!=0 " 
            //-- s.business_id=  or ab.name like   模块id   
            //************************************************这里根据项目进展情况维护***********************
            +"LEFT JOIN ipm_app_business ab      ON   ab.id = s.business_id   AND "
            +"ab.module_id IN ("+AlarmConstantUtil.THESAMEWAYMODULEID+") "
            +"AND (l.watchpoint_id!=0  OR  (l.watchpoint_id=0 AND ak.module_id=1)  OR  (l.watchpoint_id=0 AND ak.module_id=2)) "  
            //-- s.business_id=  or ab.name like   模块id
            +"LEFT JOIN ipm_watchpoint w1        ON   w1.id = s.business_id  AND   l.watchpoint_id=0 and  ak.module_id=10 "  
            
            //条件   *****************这里根据项目进展情况维护***********************
            + "${wheresql} "
            + "${watchpointsql} "
            + "${modulesql} "
            + "${businesssql} "
            + "${alarmLevelsql} "
            + "${handledflagsql} "
            + "${starttimesql} "
            + "${endtimesql} "
            + "${kpisql} "       
            + "${kpiDisplayNamesql} " 
            + "${watchpointDisplayNamesql} "
            + "${businessNamesql} "
            + "${sortStrsql} "
            + "${limitsql} "
 )
    List<AlarmLogBean> getAlarmLogDataByParam(AlarmLogFindParamBean alarmLogFindParamBean);
        
    /**
     * @Title: getAlarmLogDataByParam4Win
     * @Description: 告警小窗使用,根据条件获取告警集合
     * @param alarmLogFindParamBean
     * @return List<AlarmLogBean>
     * @author chensq
     */
    @Select(
            "SELECT "
            
            //alarmLog固有字段 start
            +"l.id AS id, "
            +"IF(w.id IS NULL,0,w.id) AS watchpointId, "
            +"l.alarmset_id AS alarmsetId, "
            +"l.starttime AS starttime, "
            +"l.endtime AS endtime, "
            +"l.handledflag AS handledflag, "
            +"l.finishflag AS finishflag, "
            +"l.responseuser AS responseuser, "
            +"l.responsetime AS responsetime, "
            +"l.triggerflag AS triggerflag, "
            //阈值类型
            +"( CASE WHEN l.triggerflag=0 THEN '高阈值' "
            +"WHEN l.triggerflag=1 THEN '低阈值' "
            +"WHEN l.triggerflag=2 THEN '智能告警阈值' "
            +"WHEN l.triggerflag=3 THEN '波测超时' "
            +"WHEN l.triggerflag=4 THEN '组合告警' "
            +"WHEN l.triggerflag=5 THEN '探针同步' END  "
            +") AS triggerflagStr, "                   
            //alarmLog固有字段 end
            
            //alarmset字段 start
            +"IF(w.name IS NULL ,'',w.name) AS watchpointName, "
            +"IF(ac.module_id IS NULL,ak.module_id ,ac.module_id) AS moduleId, "
            +"IF(w1.id IS NULL,ab.id,w1.id) AS businessId, "
            +"IF(w1.name IS NULL,ab.name,w1.name) AS businessName, "
            +"s.kpitype AS kpitype, "
            +"s.kpi_id AS alarmKpiId, "
            //alarmset字段 end
            
            //告警级别
            +"s.level_id AS alarmLevelId, "            

            //kpis的字段 start
            +"IF(ac.id IS NULL,k.id,ac.id) AS kpisId, "
            +"IF(ac.nameen IS NULL,k.name,ac.nameen) AS kpisName, "
            +"IF(ac.namezh IS NULL,IF(k.name in("+AlarmConstantUtil.SAMESPECIALMANAGEKPINAME+"),"
                    + "k.display_name,o.name),ac.namezh) AS kpisDisplayName, "
            +"IF(k.unit IS NULL,'',k.unit) AS kpisUnit, "
            //kpis的字段 end
            
            //-----------------其他字段-start------------------
            +"u.realname AS responseuserDisplayName "
            //-----------------其他字段-end------------------
            +"FROM "
            +" (SELECT l1.* FROM ipm_alarm_log l1 ORDER BY l1.starttime DESC ${limitsql} ) l "
            +"LEFT JOIN ipm_alarm_set  s         ON   s.id=l.alarmset_id "
            +"LEFT JOIN ipm_alarm_kpi  ak        ON   ak.id=s.kpi_id AND s.kpitype=1 "
            //-- k.display_name(显示) k.display_name like '%流量%'  or k.id =  
            +"LEFT JOIN ipm_kpis  k              ON   k.id=ak.kpi_id AND s.kpitype=1 "
            //新的方式，获取kpi名称
            +"LEFT JOIN ipm_plot_option     o    ON   o.kpi_id = ak.kpi_id AND s.kpitype=1 AND o.module_id=ak.module_id AND "
            + "(o.number = 1 or (o.number = 0 AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325) "  
            //-- ac.namezh(显示)    ac.namezh like '%服务%'  or  ac.id =      
            +"LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "  
             //-- l.responseuser=1  or  u.realname like '%Jcts%'
            +"LEFT JOIN ipm_system_user   u      ON   u.id=l.responseuser "         
            //-- l.watchpoint_id=1   w.name like '观察点'   log的观察点
            +"LEFT JOIN ipm_watchpoint w         ON   w.id = l.watchpoint_id AND l.watchpoint_id!=0 " 
            //-- s.business_id=  or ab.name like   模块id   
            //************************************************这里根据项目进展情况维护***********************
            +"LEFT JOIN ipm_app_business ab      ON   ab.id = s.business_id   AND "
            +"ab.module_id IN ("+AlarmConstantUtil.THESAMEWAYMODULEID+") "
            +"AND (l.watchpoint_id!=0  OR (l.watchpoint_id=0 AND ak.module_id=1)  OR  (l.watchpoint_id=0 AND ak.module_id=2) ) "  
            //-- s.business_id=  or ab.name like   模块id
            +"LEFT JOIN ipm_watchpoint w1        ON   w1.id = s.business_id  AND   l.watchpoint_id=0 and  ak.module_id=10 "
            
            //条件   *****************这里根据项目进展情况维护***********************
            + "${wheresql} "
            + "${watchpointsql} "
            + "${modulesql} "
            + "${businesssql} "
            + "${alarmLevelsql} "
            + "${handledflagsql} "
            + "${starttimesql} "
            + "${endtimesql} "
            + "${kpisql} "       
            + "${kpiDisplayNamesql} " 
            + "${watchpointDisplayNamesql} "
            + "${businessNamesql} "
            + "${sortStrsql} "
            + "${limitsql} "
 )
    List<AlarmLogBean> getAlarmLogDataByParam4Win(AlarmLogFindParamBean alarmLogFindParamBean);
    
    /**
     * @Title: getAlarmLogDataById
     * @Description: Syslog 使用,根据id获取告警集合
     * @param id
     * @return AlarmLogBean
     * @author chensq
     */
    @Select(
            "SELECT "
            //alarmLog固有字段 start
            +"l.id AS id, "
            +"IF(w.id IS NULL,0,w.id) AS watchpointId, "
            +"l.alarmset_id AS alarmsetId, "
            +"l.starttime AS starttime, "
            +"l.endtime AS endtime, "
            +"RIGHT(DATE_FORMAT(FROM_UNIXTIME(l.starttime), '%Y-%m-%d %H:%i:%s'),19) starttimeStr, "
            +"RIGHT(DATE_FORMAT(FROM_UNIXTIME(l.endtime), '%Y-%m-%d %H:%i:%s'),19) endtimeStr, "
            +"l.handledflag AS handledflag, "
            +"l.finishflag AS finishflag, "
            +"l.responseuser AS responseuser, "
            +"l.responsetime AS responsetime, "
            +"l.triggerflag AS triggerflag, "            
            //alarmLog固有字段 end
            
            //alarmset字段 start
            +"IF(w.name IS NULL ,'',w.name) AS watchpointName, "
            +"IF(ac.module_id IS NULL,ak.module_id ,ac.module_id) AS moduleId, "
//            +"IF(am.namezh IS NULL,am1.namezh,am.namezh) AS moduleName, "
            +"IF(w1.id IS NULL,ab.id,w1.id) AS businessId, "
            +"IF(w1.name IS NULL,ab.name,w1.name) AS businessName, "
            +"s.kpitype AS kpitype, "
            +"s.kpi_id AS alarmKpiId, "
            //alarmset字段 end
            
            //告警级别
            +"s.level_id AS alarmLevelId, "            

            //kpis的字段 start
            +"IF(ac.id IS NULL,k.id,ac.id) AS kpisId, "
            +"IF(ac.nameen IS NULL,k.name,ac.nameen) AS kpisName, "
            +"IF(ac.namezh IS NULL,IF(k.name in("+AlarmConstantUtil.SAMESPECIALMANAGEKPINAME+"),"
            + "k.display_name,o.name),ac.namezh) AS kpisDisplayName, "
            +"IF(k.unit IS NULL,'',k.unit) AS kpisUnit, "
            //kpis的字段 end

            //-----------------alarm-type-level-start----------------
//            +"art.id AS alarmTypeId, "
//            +"art.nameen AS alarmTypeEn, "
//            +"art.namezh AS alarmTypeZh, "
            +"arl.id AS alarmLevelId, "
            +"arl.nameen AS alarmLevelEn, "
            +"arl.namezh AS alarmLevelZh "
            //-----------------alarm-type-level-end----------------
            
            //-----------------其他字段-start------------------
//            +"u.realname AS responseuserDisplayName "
            //-----------------其他字段-end------------------
            +"FROM "
            +" (SELECT l1.* FROM ipm_alarm_log l1 where l1.id=#{id} ) l "
            +"LEFT JOIN ipm_alarm_set  s         ON   s.id=l.alarmset_id "
            +"LEFT JOIN ipm_alarm_kpi  ak        ON   ak.id=s.kpi_id AND s.kpitype=1 "
            //-- k.display_name(显示) k.display_name like '%流量%'  or k.id =  
            +"LEFT JOIN ipm_kpis  k              ON   k.id=ak.kpi_id AND s.kpitype=1 "
            //新的方式，获取kpi名称
            +"LEFT JOIN ipm_plot_option     o    ON   o.kpi_id = ak.kpi_id AND s.kpitype=1 AND o.module_id=ak.module_id AND "
            + "(o.number = 1 or (o.number = 0 AND ak.kpi_id=2))  AND o.id NOT IN(323,324,325) " 
            //-- ac.namezh(显示)    ac.namezh like '%服务%'  or  ac.id =      
            +"LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "  
            //-- s.level_id in (x,x,x) or arl.namezh like '%告警%'
            +"LEFT JOIN ipm_alarm_level  arl     ON   arl.id=s.level_id "     
//            //-- art.id=1 or  art.namezh like '%自定义%'
//            +"LEFT JOIN ipm_alarm_type   art     ON   art.id=arl.type_id "    
//            //-- l.responseuser=1  or  u.realname like '%Jcts%'
//            +"LEFT JOIN ipm_system_user   u      ON   u.id=l.responseuser "    
//            //-- ak.module_id in (x,x,x) or am.namezh like '%服务%'
//            +"LEFT JOIN ipm_authorize_module am  ON   am.id=ak.module_id  AND s.kpitype=1 " 
//            //-- ac.module_id in (x,x,x) or am.namezh like '%服务%'
//            +"LEFT JOIN ipm_authorize_module am1 ON   am1.id=ac.module_id AND s.kpitype=2 " 
            
            //-- l.watchpoint_id=1   w.name like '观察点'   log的观察点
            +"LEFT JOIN ipm_watchpoint w         ON   w.id = l.watchpoint_id AND l.watchpoint_id!=0 " 
            //-- s.business_id=  or ab.name like   模块id   
            //************************************************这里根据项目进展情况维护***********************
            +"LEFT JOIN ipm_app_business ab      ON   ab.id = s.business_id   AND "
            +"ab.module_id IN ("+AlarmConstantUtil.THESAMEWAYMODULEID+") "
            +"AND (l.watchpoint_id!=0  "
            +"OR (l.watchpoint_id=0 AND ak.module_id=1)  "
            +"OR (l.watchpoint_id=0 AND ak.module_id=2) ) "                                                            
            //-- s.business_id=  or ab.name like   模块id
            +"LEFT JOIN ipm_watchpoint w1        ON   w1.id = s.business_id  AND   l.watchpoint_id=0 and  ak.module_id=10 "
            + "where l.id=#{id} ")
    AlarmLogBean getAlarmLogDataById(@Param("id") long  id);
    
    //////////////////////////-----------
 
    /**
     * @Title: getAlarmLogBySetIdParam
     * @Description: 获取被聚合的告警kpi,根据条件获取告警集合
     * @param alarmLogBean
     * @return AlarmLogBean
     * @author chensq
     */
    @Select(
            "SELECT "
            
            //alarmLog固有字段 start
            +"l.id AS id, "
            +"IF(w.id IS NULL,0,w.id) AS watchpointId, "
            +"l.alarmset_id AS alarmsetId, "
            +"l.starttime AS starttime, "
            +"l.endtime AS endtime, "
            +"l.handledflag AS handledflag, "
            +"l.finishflag AS finishflag, "
            +"l.responseuser AS responseuser, "
            +"l.responsetime AS responsetime, "
            +"l.triggerflag AS triggerflag, "
            //阈值类型
            +"( CASE WHEN l.triggerflag=0 THEN '高阈值' "
            +"WHEN l.triggerflag=1 THEN '低阈值' "
            +"WHEN l.triggerflag=2 THEN '智能告警阈值' "
            +"WHEN l.triggerflag=3 THEN '波测超时' "
            +"WHEN l.triggerflag=4 THEN '组合告警' "
            +"WHEN l.triggerflag=5 THEN '探针同步' END  "
            +") AS triggerflagStr, "              
            //alarmLog固有字段 end
            
            //alarmset字段 start
            +"IF(w.name IS NULL ,'',w.name) AS watchpointName, "
            +"IF(ac.module_id IS NULL,ak.module_id ,ac.module_id) AS moduleId, "
            +"IF(w1.id IS NULL,ab.id,w1.id) AS businessId, "
            +"IF(w1.name IS NULL,ab.name,w1.name) AS businessName, "
            +"s.kpitype AS kpitype, "
            +"s.kpi_id AS alarmKpiId, "
            //alarmset字段 end
            
            //告警级别
            +"s.level_id AS alarmLevelId, "            

            //kpis的字段 start
            +"IF(ac.id IS NULL,k.id,ac.id) AS kpisId, "
            +"IF(ac.nameen IS NULL,k.name,ac.nameen) AS kpisName, "
            +"IF(ac.namezh IS NULL,IF(k.name in("+AlarmConstantUtil.SAMESPECIALMANAGEKPINAME+"),"
            + "k.display_name,o.name),ac.namezh) AS kpisDisplayName, "
            +"IF(k.unit IS NULL,'',k.unit) AS kpisUnit, "
            //kpis的字段 end

            //-----------------其他字段-start------------------
            +"u.realname AS responseuserDisplayName "
            //-----------------其他字段-end------------------
            +"FROM "
            +"ipm_alarm_log l "
            +"LEFT JOIN ipm_alarm_set  s         ON   s.id=l.alarmset_id "
            +"LEFT JOIN ipm_alarm_kpi  ak        ON   ak.id=s.kpi_id AND s.kpitype=1 "
            +"LEFT JOIN ipm_kpis  k              ON   k.id=ak.kpi_id AND s.kpitype=1 "
            //新的方式，获取kpi名称
            +"LEFT JOIN ipm_plot_option     o    ON   o.kpi_id = ak.kpi_id AND s.kpitype=1 AND o.module_id=ak.module_id AND "
            + "(o.number = 1 or (o.number = 0 AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325) " 
            +"LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "  
            +"LEFT JOIN ipm_system_user   u      ON   u.id=l.responseuser "            
            +"LEFT JOIN ipm_watchpoint w         ON   w.id = l.watchpoint_id AND l.watchpoint_id!=0 " 
            +"LEFT JOIN ipm_app_business ab      ON   ab.id = s.business_id   AND "
            +"ab.module_id IN ("+AlarmConstantUtil.THESAMEWAYMODULEID+") "
            +"AND (l.watchpoint_id!=0  OR (l.watchpoint_id=0 AND ak.module_id=1)  OR  (l.watchpoint_id=0 AND ak.module_id=2)) "  
            +"LEFT JOIN ipm_watchpoint w1        ON   w1.id = s.business_id  AND   l.watchpoint_id=0 and  ak.module_id=10 "
            +"where "  
            +"l.alarmset_id=#{alarmsetId} and "  
            +"l.starttime>=#{starttime} and " 
            +"l.endtime<=#{endtime} limit 1 "
            //条件   *****************这里根据项目进展情况维护***********************
 )
    AlarmLogBean getAlarmLogBySetIdParam(AlarmLogBean alarmLogBean);
    //////////////////////////-----------
    

    /**
     * @Title: getAlarmMaxLevelIdByParam
     * @Description: 查询
     * @param starttime 
     * @param endtime 
     * @param alarmSetIdStr 
     * @return List<AlarmLevelBean>
     * @author chensq
     */
    @Select("select "
            + "MAX(s.level_id) as levelId " 
            +" FROM ipm_alarm_log l  " 
            +" LEFT JOIN ipm_alarm_set s ON s.id=l.alarmset_id  " 
            +" where " 
            +" l.alarmset_id IN (${alarmSetIdStr}) AND  "
            +" l.starttime>=${starttime} AND  "
            +" l.endtime<=${endtime}  ")
    Integer getAlarmMaxLevelIdByParam(
            @Param("starttime") long  starttime, 
            @Param("endtime") long  endtime, 
            @Param("alarmSetIdStr") String  alarmSetIdStr);
      
    /**
     * @Title: getAlarmLogColumnByParam
     * @Description: 根据条件获取告警柱图集合数量
     * @param alarmLogFindParamBean
     * @return long
     * @author chensq
     */
    @Select("select "
          + "right(date_format(FROM_UNIXTIME(l.starttime), '%Y-%m-%d %H:%i:%s'),19) starttimeStr," 
          +  "${paramsql1} " 
          +  "s.level_id as alarmLevelId, " 
          +  "l.starttime as starttime, " 
          +  "count(*) count " 
          +  "from ipm_alarm_log l " 
          
          +"LEFT JOIN ipm_alarm_set  s         ON   s.id=l.alarmset_id "
          +"LEFT JOIN ipm_alarm_kpi  ak        ON   ak.id=s.kpi_id AND s.kpitype=1 "
          //-- k.display_name(显示) k.display_name like '%流量%'  or k.id =  
          +"LEFT JOIN ipm_kpis  k              ON   k.id=ak.kpi_id AND s.kpitype=1 "
          //-- 与告警列表的统一
          +"LEFT JOIN ipm_plot_option o        ON   o.kpi_id = ak.kpi_id  AND s.kpitype=1 "
          + "AND o.module_id=ak.module_id  "
          + "AND (o.number = 1 OR (o.number = 0  AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325) "
          //-- ac.namezh(显示)    ac.namezh like '%服务%'  or  ac.id =      
          +"LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "  
          //-- l.watchpoint_id=1   w.name like '观察点'   log的观察点
          +"LEFT JOIN ipm_watchpoint w         ON   w.id = l.watchpoint_id AND l.watchpoint_id!=0 " 
          //-- s.business_id=  or ab.name like   模块id   
          //************************************************这里根据项目进展情况维护***********************
          +"LEFT JOIN ipm_app_business ab      ON   ab.id = s.business_id   "
          + "AND (l.watchpoint_id!=0  OR (l.watchpoint_id=0 AND ak.module_id=1) OR (l.watchpoint_id=0 AND ak.module_id=2)) "
          + "AND ab.module_id IN ("+AlarmConstantUtil.THESAMEWAYMODULEID+") "          
          //-- s.business_id=  or ab.name like   模块id
          +"LEFT JOIN ipm_watchpoint w1        ON   w1.id = s.business_id  AND   l.watchpoint_id=0  AND  ak.module_id=10 "
             //条件   *****************这里根据项目进展情况维护***********************
            + "${wheresql} "
            + "and (${paramsql2}) " 
            + "${watchpointsql} "
            + "${modulesql} "
            + "${businesssql} "
            + "${alarmLevelsql} "
            + "${handledflagsql} "
            + "${starttimesql} "
            + "${endtimesql} "
            + "${kpisql} "       
            + "${kpiDisplayNamesql} "      
            + "${watchpointDisplayNamesql} "
            + "${businessNamesql} "
            +  "group by "
            + "s.level_id,"
            + "${paramsql3} "
            + " ORDER BY "
            + "CAST(l.starttime AS SIGNED) ASC  ")
    List<AlarmLogBean> getAlarmLogColumnByParam(AlarmLogFindParamBean alarmLogFindParamBean);
    
    /**
     * @Title: updateAlarmSetByIds
     * @Description: 根据ids修改告警log(告警响应)
     * @param handledflag
     * @param responseuser
     * @param responsetime
     * @param idInsql
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_log  set "
            + "handledflag='${handledflag}',"
            + "responseuser=${responseuser},"
            + "responsetime=${responsetime} where ${idInsql} ")
    int updateAlarmSetByIds(
            @Param("handledflag") String  handledflag,
            @Param("responseuser") long  responseuser,
            @Param("responsetime") long  responsetime,
            @Param("idInsql") String  idInsql);
    
    /**
     * @Title: updateAlarmSetById
     * @Description: 根据id修改告警log(告警响应，单条)
     * @param handledflag
     * @param responseuser
     * @param responsetime
     * @param id
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_log  set "
            + "handledflag='${handledflag}',"
            + "responseuser=${responseuser},"
            + "responsetime=${responsetime} where id=${id} ")
    int updateAlarmSetById(
            @Param("handledflag") String  handledflag,
            @Param("responseuser") long  responseuser,
            @Param("responsetime") long  responsetime,
            @Param("id") long  id);
    
    /**
     * 
     * @Title: deleteAlarmLogById
     * @Description: 删除告警记录
     * @param id
     * @return int
     * @author chensq
     */
    @Delete("delete from ipm_alarm_log where id=${id}")
    int deleteAlarmLogById(@Param("id") long  id);
    
    //------------------定时删除告警日志使用 start-------------------
    /**
     * @Title: getMinStartTime
     * @Description: 获取最小时间
     * @param endTime 结束时间
     * @return Long
     * @author chensq
     */
    @Select("select min(starttime) as starttime from ipm_alarm_log where starttime <= #{endTime}")
    Long getMinStartTime (@Param("endTime") long endTime);
    
    /**
     * 
     * @Title: loopDelAlertLog
     * @Description: 定时删除告警记录使用
     * @param startTime
     * @param endTime
     * @author chensq
     */
    @Delete("delete from ipm_alarm_log where starttime>=#{startTime} AND starttime <= #{endTime}")
    void  loopDelAlertLog (@Param("startTime") long startTime, @Param("endTime") long endTime);    
    //------------------定时删除告警日志使用 end-------------------
    
    //------------------保留指定个数告警使用 start-------------------
    /**
     * 
     * @Title: getMinStartTimeByCount
     * @Description: 指定个数的告警的最小时间
     * @param maxCount
     * @return Long
     * @author chensq
     */
    @Select("SELECT MIN(l.starttime) FROM (SELECT starttime AS starttime FROM ipm_alarm_log  ORDER BY starttime DESC LIMIT #{maxCount}) l")
    Long getMinStartTimeByCount (@Param("maxCount") long maxCount);
    
    /**
     * 
     * @Title: loopDelAlertLogByCount
     * @Description: 删除指定时间以前的数据
     * @param startTime void
     * @author chensq
     */
    @Delete("delete from ipm_alarm_log where starttime < #{startTime} ")
    void  loopDelAlertLogByCount (@Param("startTime") long startTime); 
    //------------------保留指定个数告警使用 end-------------------
    
    //------------------应用可用性使用方法 start-------------------
    
    /**
     * @Title: getMaxUsabilityAlarmLongEndTime
     * @Description: 应用可用性使用，查询告警数据的最大时间
     * @param ip
     * @param port
     * @return AlarmLogBean
     * @author chensq
     */
    @Select("select max(l.endtime) as endtime,l.id as id from ipm_alarm_log l "
            +"LEFT JOIN ipm_alarm_set s ON l.alarmset_id=s.id "
            +"LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id "
            +"LEFT JOIN ipm_usability_set us ON us.id=s.business_id "
            + "where "
            + "s.kpitype=1 AND "
            + "ak.module_id=1 AND "
            + "us.ip=#{ip} AND "
            + "us.PORT=#{port} ")
    AlarmLogBean getMaxUsabilityAlarmLongEndTime(@Param("ip") String ip, @Param("port") String port);
         
    /**
     * @Title: updateAlarmSetEndTimeById
     * @Description: 根据id修改告警log(告警结束时间，单条)
     * @param endtime
     * @param id
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_log  set "
            + "endtime=#{endtime} "
            + "where id=#{id} ")
    int updateAlarmSetEndTimeById(
            @Param("endtime") long  endtime,
            @Param("id") long  id);
    
    //------------------应用可用性使用方法 end-------------------

}
