/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmAlgorithmUtil
 *创建人:chensq    创建时间:2017年11月14日
 */
package com.protocolsoft.alarm.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;
import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmUseDaoBean;
import com.protocolsoft.alarm.bean.PointEntryByAlarmTypeBean;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.watchpoint.bean.WatchpointBean;

/**
 * @ClassName: AlarmAlgorithmUtil
 * @Description: 告警算法工具类
 * @author chensq
 *
 */
public class AlarmAlgorithmUtil {
   
    /**
     * <p>Title: AlarmAlgorithmUtil</p>
     * <p>Description: 构造方法</p>
     * @param alarmUseDaoBean
     */ 
    public AlarmAlgorithmUtil(AlarmUseDaoBean alarmUseDaoBean) {

    }
     
    /**
     * @Title: getPointEntryByAlarmType
     * @Description: pointEntryByAlarmTypeBean 封装的对象
     * @param pointEntryByAlarmTypeBean
     * @return SimpleEntry<Long,Double>
     * @author chensq
     */
    public static SimpleEntry<Long, Double> getPointEntryByAlarmType(
            PointEntryByAlarmTypeBean pointEntryByAlarmTypeBean) {
        //rrd point
        SimpleEntry<Long, Double> point=null;
        
        //开始时间
        long starttime=pointEntryByAlarmTypeBean.getStarttime();
        //结束时间
        long endtime=pointEntryByAlarmTypeBean.getEndtime();
        //告警设置对象bean
        AlarmSetBean alarmSetBean=pointEntryByAlarmTypeBean.getAlarmSetBean();
        //rrd对象bean
        BusiKpiService busiKpiService=pointEntryByAlarmTypeBean.getBusiKpiService();
        //kpi名称bean
        String kpiName=pointEntryByAlarmTypeBean.getKpiName();
        //算法情况
        AlarmAlgorithmBean alarmAlgorithmBean=pointEntryByAlarmTypeBean.getAlarmAlgorithmBean();
                
        if (alarmSetBean.getKpitype()==1) {//原生kpi
            //算法表中算法一
            if(alarmAlgorithmBean.getId()==1){
                point=busiKpiService.getRrdDataPointByName(//rrd取值
                        starttime,
                        endtime,                     
                        kpiName,
                        RrdAlgorithm.AVG);
            }
            //算法表中算法二
            if(alarmAlgorithmBean.getId()==2){
                point=busiKpiService.getRrdDataPointByName(//rrd取值
                        starttime,
                        endtime,                     
                        kpiName,
                        RrdAlgorithm.AVG);
            }
        } else if (alarmSetBean.getKpitype()==2) {//自定义
            //根据不同的算法情况进行处理
        }
        
        return point;
    }
    
    /**
     * @Title: alarmAlgorithm
     * @Description: 算法分发
     * @param alarmSetBean 告警设置信息
     * @param alarmAlgorithmBean 告警算法信息
     * @param point  告警验证值信息
     * @param alarmCheckCountBean 告警结果标识保存对象
     * @return AlarmCheckCountBean
     * @author chensq
     */
    public static AlarmCheckCountBean alarmAlgorithm(
            AlarmSetBean alarmSetBean,
            AlarmAlgorithmBean alarmAlgorithmBean,
            SimpleEntry<Long, Double> point,
            AlarmCheckCountBean alarmCheckCountBean) {
        //算法id
        int algorithmType =(int)alarmAlgorithmBean.getId();
        
        AlarmCheckCountBean alarmCheckCountReturnBean =null;
        
        if (algorithmType==1) {//n分钟内出现m次   n分钟内超过阈值m次(不区分级别，>=m)，按照最高的级别告警
            alarmCheckCountReturnBean= nMinuteMCount(alarmSetBean, alarmAlgorithmBean, point, alarmCheckCountBean);
        } else if (algorithmType==2) {//n分钟出现n次  n分钟内超过阈值m次(不区分级别，>=m)，按照最高的级别告警  适用于url、报文 告警
            alarmCheckCountReturnBean= nMinuteMCount(alarmSetBean, alarmAlgorithmBean, point, alarmCheckCountBean);
        } else if (algorithmType==3) {
//            return alarmCheckCountReturnBean;
        }
        return alarmCheckCountReturnBean;
    }
  
    /**
     * @Title: nMinuteMCount
     * @Description: 算法一   n分钟内出现m次超过阈值情况 ,n分钟内超过阈值m次(不区分级别，>=m)，按照最高的级别告警
     * @param alarmSetBean
     * @param alarmAlgorithmBean
     * @param point
     * @param alarmCheckCountBean
     * @return AlarmCheckCountBean
     * @author chensq
     */
    public static AlarmCheckCountBean nMinuteMCount(
            AlarmSetBean alarmSetBean,
            AlarmAlgorithmBean alarmAlgorithmBean,
            SimpleEntry<Long, Double> point,
            AlarmCheckCountBean alarmCheckCountBean){

        //自定义阈值level级别数组
        String []basicLevel =new String[]{"2", "3" , "4"};
        //算法参数
        String []algorithminfoArray=alarmAlgorithmBean.getAlgorithminfo().split(",");
        int minuteParam=Integer.parseInt(algorithminfoArray[0]);
        int countParam=Integer.parseInt(algorithminfoArray[1]);
        //告警设置相关参数
        int highLowBaselineFlag= alarmSetBean.getHighLowBaselineFlag();
        String []idArray=alarmSetBean.getIdList().split(","); //告警设置id
        String []levelArray=alarmSetBean.getLevelList().split(","); //告警级别
        String []alarmValueArray=alarmSetBean.getAlarmValueList().split(","); //告警阈值
         
        //告警结果标识保存对象设置值           
        //阈值级别对应的次数map          
        Map<String, Integer> levelValueMap=alarmCheckCountBean.getLevelValueMap();
        if (levelValueMap==null) {
            levelValueMap=new LinkedHashMap<String, Integer>();
            levelValueMap.put("2", 0);
            levelValueMap.put("3", 0);
            levelValueMap.put("4", 0);
        }
          
        //恒设置
        alarmCheckCountBean.setEndtime(point.getKey());
        alarmCheckCountBean.setTriggerflag(alarmSetBean.getHighLowBaselineFlag());
        
        //详细计算过程    (区分高阈值、低阈值)
        if(highLowBaselineFlag==0){//高阈值
            int index=0;
            if (levelArray.length>0) {
                //普通
                long setId2=0;
                int map2count=0;
                double map2value=0;
                //重要
                long setId3=0;
                int map3count=0;
                double map3value=0;
                //紧急
                long setId4=0;
                int map4count=0;
                double map4value=0;
                  
                for (int x=0; x<levelArray.length; x++) {
                    //level级别数组item 包含在baisc级别中                        
                    if (ArrayUtils.contains(basicLevel, levelArray[index])) {
                        switch (Integer.parseInt(levelArray[index])) {
                            case 2://自定义普通
                                setId2 =Long.parseLong(idArray[index]);
                                map2count= levelValueMap.get(levelArray[index]);
                                map2value=Double.parseDouble(alarmValueArray[index]);
                                break;
                            case 3://自定义重要
                                setId3 =Long.parseLong(idArray[index]);
                                map3count= levelValueMap.get(levelArray[index]);
                                map3value=Double.parseDouble(alarmValueArray[index]);
                                break;    
                            case 4://自定义紧急
                                setId4 =Long.parseLong(idArray[index]);
                                map4count= levelValueMap.get(levelArray[index]);
                                map4value=Double.parseDouble(alarmValueArray[index]);
                                break;  
                            default:
                                break;
                        }
                    }
                    index++;
                }
                
                //时间超过  并且 阈值次数超过  标识值
                int checkResult=0;
                //-----------------------------------------------
                //验证
                if (point.getValue()>=map4value) {
                    map4count++;          
                    levelValueMap.put("4", map4count);
                }
                if (point.getValue()>=map3value) {
                    map3count++;
                    levelValueMap.put("3", map3count);
                }
                if (point.getValue()>=map2value) {
                    map2count++;
                    levelValueMap.put("2", map2count);
                }
                
                //恒重新设置
                alarmCheckCountBean.setLevelValueMap(levelValueMap);
                
                //验证
                if(map4count+map3count+map2count>0){
                    if(alarmCheckCountBean.getFirstCount()==0){
                        alarmCheckCountBean.setStarttime(point.getKey());
                        alarmCheckCountBean.setFirstCount(1);
                    }
                }
                if (map4count + map3count +map2count >= countParam) {
                    checkResult++;
                }
                        
                //阈值时间进行比较(开始时间与结束时间间隔大于设定分钟) 结束条件
                int recalculate=1; // 1 未超时 2 超时
                if (alarmCheckCountBean.getEndtime()-alarmCheckCountBean.getStarttime() >= minuteParam * 60) {
                    recalculate=2;
                    checkResult++;
                }
                //-----------------------------------------------
                
                if (checkResult==2) {//符合告警条件
                    alarmCheckCountBean.setEndFlag(3); 
                    if (map4count>0) {
                        alarmCheckCountBean.setFinalAlarmSetId(setId4);
                    } else if (map3count>0) {
                        alarmCheckCountBean.setFinalAlarmSetId(setId3);
                    } else {
                        alarmCheckCountBean.setFinalAlarmSetId(setId2);
                    }
                } else {
                    alarmCheckCountBean.setEndFlag(recalculate); // 1 可继续计算   2停止计算 
                }
            }
        }else{//低阈值
            int index=0;
            if (levelArray.length>0) {
                //普通
                long setId2=0;
                int map2count=0;
                double map2value=0;
                //重要
                long setId3=0;
                int map3count=0;
                double map3value=0;
                //紧急
                long setId4=0;
                int map4count=0;
                double map4value=0;
                  
                for (int x=0; x<levelArray.length; x++) {
                    //level级别数组item 包含在baisc级别中                        
                    if (ArrayUtils.contains(basicLevel, levelArray[index])) {
                        switch (Integer.parseInt(levelArray[index])) {
                            case 2://自定义普通
                                setId2 =Long.parseLong(idArray[index]);
                                map2count= levelValueMap.get(levelArray[index]);
                                map2value=Double.parseDouble(alarmValueArray[index]);
                                break;
                            case 3://自定义重要
                                setId3 =Long.parseLong(idArray[index]);
                                map3count= levelValueMap.get(levelArray[index]);
                                map3value=Double.parseDouble(alarmValueArray[index]);
                                break;    
                            case 4://自定义紧急
                                setId4 =Long.parseLong(idArray[index]);
                                map4count= levelValueMap.get(levelArray[index]);
                                map4value=Double.parseDouble(alarmValueArray[index]);
                                break;  
                            default:
                                break;
                        }
                    }
                    index++;
                }
                
                //时间超过  并且 阈值次数超过  标识值
                int checkResult=0;
                //-----------------------------------------------
                //验证
                if (point.getValue()<=map4value) {
                    map4count++;          
                    levelValueMap.put("4", map4count);
                }
                if (point.getValue()<=map3value && point.getValue()>map4value){
                    map3count++;
                    levelValueMap.put("3", map3count);
                }
                if (point.getValue()<=map2value && point.getValue()>map3value){
                    map2count++;
                    levelValueMap.put("2", map2count);
                }
                        
                //恒重新设置
                alarmCheckCountBean.setLevelValueMap(levelValueMap);
                //验证
                if(map4count+map3count+map2count>0){
                    if(alarmCheckCountBean.getFirstCount()==0){
                        alarmCheckCountBean.setStarttime(point.getKey());
                        alarmCheckCountBean.setFirstCount(1);
                    }
                }
                if (map4count + map3count +map2count >= countParam) {
                    checkResult++;
                }
                        
                //阈值时间进行比较(开始时间与结束时间间隔大于设定分钟) 结束条件
                int recalculate=1; // 1 未超时 2 超时
                if (alarmCheckCountBean.getEndtime()-alarmCheckCountBean.getStarttime() >= minuteParam * 60) {
                    recalculate=2;
                    checkResult++;
                }
                //-----------------------------------------------
                
                if (checkResult==2) {//符合告警条件
                    alarmCheckCountBean.setEndFlag(3); 
                    if (map4count>0) {
                        alarmCheckCountBean.setFinalAlarmSetId(setId4);
                    } else if (map3count>0) {
                        alarmCheckCountBean.setFinalAlarmSetId(setId3);
                    } else {
                        alarmCheckCountBean.setFinalAlarmSetId(setId2);
                    }
                } else {
                    alarmCheckCountBean.setEndFlag(recalculate); // 1 可继续计算   2停止计算 
                }
            }
        }
     
        return alarmCheckCountBean;
    }
    
    /**
     * @Title: getKey
     * @Description: 获取告警业务的所有key
     * @param keyList key的集合
     * @param alarmSetBean 告警设置的对象
     * @param wpList 观察点集合
     * @param type 类型 0：不需要迭代观察点,1:需要迭代观察点
     * @return List<String>
     * @author chensq
     */
    public List<String> getKey(List<String> keyList, AlarmSetBean alarmSetBean, List<WatchpointBean> wpList, int type){
        //设置临时变量
        StringBuffer itemKeyBuf=null;
        //根据情况拼接key
        if (type==0){//单个情况
            itemKeyBuf=new StringBuffer();
            itemKeyBuf.append(alarmSetBean.getModuleId());
            itemKeyBuf.append(",");
            itemKeyBuf.append(alarmSetBean.getWatchpointId());
            itemKeyBuf.append(",");
            itemKeyBuf.append(alarmSetBean.getBusinessId());
            itemKeyBuf.append(",");
            itemKeyBuf.append(alarmSetBean.getKpitype());
            itemKeyBuf.append(",");
            itemKeyBuf.append(alarmSetBean.getKpiId());
            itemKeyBuf.append(",");
            itemKeyBuf.append(alarmSetBean.getHighLowBaselineFlag());

            keyList.add(itemKeyBuf.toString());
        } else {//多个情况
            if (wpList!=null && wpList.size()>0) {
                for (int x=0; x<wpList.size(); x++) {
                    itemKeyBuf=new StringBuffer();
                    itemKeyBuf.append(wpList.get(x).getId());
                    itemKeyBuf.append(",");
                    itemKeyBuf.append(alarmSetBean.getWatchpointId());
                    itemKeyBuf.append(",");
                    itemKeyBuf.append(alarmSetBean.getBusinessId());
                    itemKeyBuf.append(",");
                    itemKeyBuf.append(alarmSetBean.getKpitype());
                    itemKeyBuf.append(",");
                    itemKeyBuf.append(alarmSetBean.getKpiId());     
                    itemKeyBuf.append(",");
                    itemKeyBuf.append(alarmSetBean.getHighLowBaselineFlag());
                    keyList.add(itemKeyBuf.toString());
                }
            }
        }
        return keyList;
    }

}
