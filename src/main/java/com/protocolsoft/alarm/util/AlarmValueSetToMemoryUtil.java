/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmValueSetToMemoryUtil
 *创建人:chensq    创建时间:2018年4月3日
 */
package com.protocolsoft.alarm.util;

import java.util.List;

import com.protocolsoft.alarm.bean.AlarmSetBean;
 
/**
 * @ClassName: AlarmValueSetToMemoryUtil
 * @Description:  设置告警使用的值入内存
 * @author chensq
 *
 */
public class AlarmValueSetToMemoryUtil {    
    /**
     * @Title: setAlarmInfoToThreadList
     * @Description: 设置值入内存，告警线程使用(高低阈值使用)
     * @param tempfinalbean 含部分参数的对象
     * @param levelList 不同类型的告警集合list
     * @param type 告警阈值类型
     * @return AlarmSetBean
     * @author chensq
     */
    public static AlarmSetBean setAlarmInfoToThreadList(AlarmSetBean tempfinalbean, List<AlarmSetBean> levelList, int type){
        StringBuffer idBuf=null;
        StringBuffer levelIdBuf=null;
        StringBuffer alarmvalueBuf=null;
        for (int l=0; l<levelList.size(); l++) {
            if (idBuf == null){
                idBuf=new StringBuffer();
            }
            if (levelIdBuf == null) {
                levelIdBuf=new StringBuffer();
            }
            if (alarmvalueBuf == null) {
                alarmvalueBuf=new StringBuffer();
            }
            AlarmSetBean itemBean=levelList.get(l);
            
            long id=itemBean.getId();
            long levelId=itemBean.getLevelId();

            idBuf.append(id);
            levelIdBuf.append(levelId);
            
            Double alarmValue=null; //高低阈值使用
            if(type==0){//高阈值的值
                alarmValue=itemBean.getHighthresh();
                if(alarmValue==null){
                    continue;
                }
                alarmvalueBuf.append(alarmValue);

            }else if(type==1){//低阈值的值
                alarmValue=itemBean.getLowthresh();
                if(alarmValue==null){
                    continue;
                }
                alarmvalueBuf.append(alarmValue);
            }
           
            if (l!=levelList.size()-1) {
                idBuf.append(",");
                levelIdBuf.append(",");
                alarmvalueBuf.append(",");
            }
          
        }
        if (idBuf!=null) {
            tempfinalbean.setIdList(idBuf.toString());
        }
        if (levelIdBuf!=null) {
            tempfinalbean.setLevelList(levelIdBuf.toString());
        }
        if (alarmvalueBuf!=null) {
            tempfinalbean.setAlarmValueList(alarmvalueBuf.toString());
        }

        tempfinalbean.setHighLowBaselineFlag(type);
        
        return tempfinalbean;
    }
   
    /**
     * @Title: setBaseLineAlarmInfoToThreadList
     * @Description: 设置值入内存，告警线程使用(基线阈值使用)
     * @param tempfinalbean  含部分参数的对象
     * @param levelList 不同类型的告警集合list
     * @param type 告警阈值类型
     * @return AlarmSetBean
     * @author chensq
     */
    public static AlarmSetBean setBaseLineAlarmInfoToThreadList(AlarmSetBean tempfinalbean, List<AlarmSetBean> levelList, int type){
        
        StringBuffer idBuf=null;
        StringBuffer levelIdBuf=null;
        StringBuffer alarmvalueBuf=null;
        
        StringBuffer baseLineValueBuf=new StringBuffer();

        for (int l=0; l<levelList.size(); l++) {
            if (idBuf == null){
                idBuf=new StringBuffer();
            }
            if (levelIdBuf == null) {
                levelIdBuf=new StringBuffer();
            }
            if (alarmvalueBuf == null) {
                alarmvalueBuf=new StringBuffer();
            }
            AlarmSetBean itemBean=levelList.get(l);
            
            long id=itemBean.getId();
            long levelId=itemBean.getLevelId();
            int triggerflag=itemBean.getTriggerflag();
            
            if(l==0){
                idBuf.append(id);
                levelIdBuf.append(levelId);
            }
           
            Double baseLineValue=triggerflag==0?itemBean.getHighthresh():itemBean.getLowthresh();
            if(baseLineValue==null){
                continue;
            }
            baseLineValueBuf.append(triggerflag);
            baseLineValueBuf.append("-");
            baseLineValueBuf.append(baseLineValue);
            baseLineValueBuf.append("|");
                     
        }
        
        if(!"".endsWith(baseLineValueBuf.toString())){
            if (idBuf!=null) {
                tempfinalbean.setIdList(idBuf.toString());
            }
            if (levelIdBuf!=null) {
                tempfinalbean.setLevelList(levelIdBuf.toString());
            }
            if (alarmvalueBuf!=null) {
                tempfinalbean.setAlarmValueList(getNewString(baseLineValueBuf.toString()));
            }
            
            tempfinalbean.setHighLowBaselineFlag(type);
            
            return tempfinalbean;
        }else{
            return null;
        }
       
    }
     
    /**
     * @Title: setCustomUnionKpiAlarmInfoToThreadList
     * @Description: 设置值入内存，告警线程使用(组合kpi使用)
     * @param asTemp 含部分参数的对象
     * @param levelList 不同类型的告警集合list
     * @return AlarmSetBean
     * @author chensq
     */
    public static AlarmSetBean setCustomUnionKpiAlarmInfoToThreadList(AlarmSetBean asTemp, List<AlarmSetBean> levelList){
        //获取
        StringBuffer idListBuf=new StringBuffer(asTemp.getIdList());
        StringBuffer levelListBuf=new StringBuffer(asTemp.getLevelList());
        StringBuffer alarmValueListBuf=new StringBuffer(asTemp.getAlarmValueList());

        for (int l=0; l<levelList.size(); l++) {
           
            AlarmSetBean itemBean=levelList.get(l);
            
            long id=itemBean.getId();
            long levelId=itemBean.getLevelId();
            long kpiId=itemBean.getKpiId();

            //初始
            if(l==0){
                if(!"".endsWith(idListBuf.toString())){
                    idListBuf.append("|");
                }
                if(!"".endsWith(levelListBuf.toString())){
                    levelListBuf.append("|");
                }
                if(!"".endsWith(alarmValueListBuf.toString())){
                    alarmValueListBuf.append("|");
                }
            }
        
            idListBuf.append(id);
            levelListBuf.append(levelId);
            alarmValueListBuf.append(kpiId);
            
            if (l!=levelList.size()-1) {
                idListBuf.append(",");
                levelListBuf.append(",");
                alarmValueListBuf.append(",");
            }
        }
                
        if (idListBuf!=null) {
            asTemp.setIdList(idListBuf.toString());
        }
        if (levelListBuf!=null) {
            asTemp.setLevelList(levelListBuf.toString());
        }
        if (alarmValueListBuf!=null) {
            asTemp.setAlarmValueList(alarmValueListBuf.toString());
        }

        return asTemp;       
    }
    
    /**
     * @Title: checkValueStatus
     * @Description: 验证阈值,符合条件的才允许跑告警线程
     * @param alarmSetList
     * @param type 0高,1低,2基线,3应用可用性,4组合kpi
     * @return boolean false:不允许  true:允许
     * @author chensq
     */
    public static boolean checkValueStatus(List<AlarmSetBean> alarmSetList, int type){
        boolean flag=true;
        
        //高阈值
        if (type==0){
            Double level2=null;
            Double level3=null;
            Double level4=null;
            for(int x=0; x<alarmSetList.size(); x++){
                AlarmSetBean tempAlarmSetBean=alarmSetList.get(x);
                long levelId=tempAlarmSetBean.getLevelId();
                if(levelId==2){
                    level2=tempAlarmSetBean.getHighthresh();
                }
                if(levelId==3){
                    level3=tempAlarmSetBean.getHighthresh();
                }
                if(levelId==4){
                    level4=tempAlarmSetBean.getHighthresh();
                }
            }
            if(level2==null || level3==null || level4==null){
                flag=false;
            }
        }
        
        //低阈值
        if(type==1){
            Double level2=null;
            Double level3=null;
            Double level4=null;
            for(int x=0; x<alarmSetList.size(); x++){
                AlarmSetBean tempAlarmSetBean=alarmSetList.get(x);
                long levelId=tempAlarmSetBean.getLevelId();
                if(levelId==2){
                    level2=tempAlarmSetBean.getLowthresh();
                }
                if(levelId==3){
                    level3=tempAlarmSetBean.getLowthresh();
                }
                if(levelId==4){
                    level4=tempAlarmSetBean.getLowthresh();
                }
            }
            if(level2==null || level3==null || level4==null){
                flag=false;
            }
        }
        
        //基线
        if(type==2){
            Double highthresh=null;
            Double lowthresh=null;
            for(int x=0; x<alarmSetList.size(); x++){
                AlarmSetBean tempAlarmSetBean=alarmSetList.get(x);
                long triggerflag=tempAlarmSetBean.getTriggerflag();
                if(triggerflag==0){
                    highthresh=tempAlarmSetBean.getHighthresh();
                }
                if(triggerflag==1){
                    lowthresh=tempAlarmSetBean.getLowthresh();
                }
            }
            if(highthresh==null && lowthresh==null){
                flag=false;
            }
        }
        
        return flag;
    }
    
    /**
     * @Title: getAlarmThreadSetKey
     * @Description: 告警线程使用的key
     * @param alarmSetBean
     * @return String moduleId_watchpointId_businessId_kpitype_kpiId_highLowBaselineFlag
     * @author chensq
     */
    public static String getAlarmThreadSetKey(AlarmSetBean alarmSetBean){
        //key
        StringBuffer buf=new StringBuffer();
        buf.append(alarmSetBean.getModuleId());
        buf.append(",");
        buf.append(alarmSetBean.getWatchpointId());
        buf.append(",");
        buf.append(alarmSetBean.getBusinessId());
        buf.append(",");
        buf.append(alarmSetBean.getKpitype());
        buf.append(",");
        buf.append(alarmSetBean.getKpiId());
        buf.append(",");
        buf.append(alarmSetBean.getHighLowBaselineFlag());
        
        return buf.toString();
    }
   
    /**
     * @Title: getNewString
     * @Description: 去掉字符串多余的“|”
     * @param oldStr
     * @return String
     * @author chensq
     */
    public static String getNewString(String oldStr){
        String newStr="";
        String str1 = oldStr.substring(oldStr.length()-1, oldStr.length());
        if(str1.equals("|")){
            newStr=oldStr.substring(0, oldStr.length()-1);
        }else{
            newStr=oldStr;
        }
        return newStr;
    }
    
   
}
