/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogUtil
 *创建人:chensq    创建时间:2017年12月13日
 */
package com.protocolsoft.alarm.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.protocolsoft.alarm.bean.AlarmLevelBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.user.bean.AuthorizeModuleBean;
 
/**
 * @ClassName: AlarmLogUtil
 * @Description: 告警日志工具类
 * @author chensq
 *
 */
public class AlarmLogUtil {
     
    /**
     * 
     * @Title: getStartAndEndTime
     * @Description: 根据结果集中返回的start字段确定其属于哪个切分的区间段，并合并同一时间段的数据
     * @param alarmLevelBeanList 告警级别
     * @param list 时间切分段的集合
     * @param starttime 时间切分段的集合
     * @param map 时间切分段的集合
     * @param itemLog 记录
     * @return Map<String,Map<String,String>>
     * @author chensq
     */
    public static Map<String, Map<String, String>> getStartAndEndTime(
           List<AlarmLevelBean> alarmLevelBeanList,
           List<String> list, 
           long starttime, 
           Map<String, Map<String, String>> map, 
           AlarmLogBean itemLog){
        Iterator<String> it = list.iterator();
        String stime=null;
        String etime=null;

        while (it.hasNext()){
            {
                String str = it.next();
                stime=str.split("-")[0];
                etime=str.split("-")[1];
                long stimeL=Long.parseLong(stime);
                long etimeL=Long.parseLong(etime);
                if (starttime>=stimeL && starttime<etimeL){

                    String mapKey=stimeL+"-"+etimeL;

                    if (map.get(mapKey)==null){
                        if (AlarmLogUtil.outLevel(itemLog.getAlarmLevelId())) {
                            Map<String, String> sin = new HashMap<String, String>();
                            sin.put(String.valueOf(itemLog.getAlarmLevelId()), String.valueOf(itemLog.getCount()));
                            sin.put("starttime", String.valueOf(itemLog.getStarttime()));
                            sin.put("starttimeStr", String.valueOf(itemLog.getStarttimeStr()));
                            map.put(mapKey, sin);
                        }
                    } else {
                        for (int x=0; x<alarmLevelBeanList.size(); x++) {
                            long alarmLevelId= alarmLevelBeanList.get(x).getId();
                            if (!AlarmLogUtil.outLevel(alarmLevelId)) {
                                continue;
                            }
                            Map<String, String> tempMap= map.get(mapKey);
                            if (String.valueOf(alarmLevelId).equals(String.valueOf(itemLog.getAlarmLevelId()))){
                                int count=0;
                                if (tempMap.get(String.valueOf(alarmLevelId)) != null){
                                    count=Integer.parseInt(tempMap.get(String.valueOf(alarmLevelId)));
                                    tempMap.put(String.valueOf(alarmLevelId), String.valueOf(count+itemLog.getCount()));
                                } else {
                                    tempMap.put(String.valueOf(alarmLevelId), String.valueOf(itemLog.getCount()));
                                }
                            }
                        }
                    }
                    
                }
            }
        }
        return map;
    }
    
    /**
     * @Title: setDefaultMap
     * @Description: 设置入map
     * @param alarmLevelBeanList
     * @param list
     * @param map
     * @return Map<String,Map<String,String>>
     * @author chensq
     */
    public static Map<String, Map<String, String>> setDefaultMap(
            List<AlarmLevelBean> alarmLevelBeanList, 
            List<String> list, 
            Map<String, Map<String, String>> map){
        Iterator<String> it = list.iterator();
        String stime=null;
        String etime=null;
        //sql会将切分时间段内没有数据的会过滤掉
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        while (it.hasNext()){
            String str = it.next();
            stime=str.split("-")[0];
            etime=str.split("-")[1];
            String mapKey=stime+"-"+etime;
            if (map.get(mapKey)==null){
                Map<String, String> sin = new HashMap<String, String>();
                for (int x=0; x<alarmLevelBeanList.size(); x++) {
                    long alarmLevelId= alarmLevelBeanList.get(x).getId();
                    if (!AlarmLogUtil.outLevel(alarmLevelId)) {
                        continue;
                    }
                    sin.put(String.valueOf(alarmLevelId), "0");
                }
                sin.put("starttime", stime);
                sin.put("starttimeStr", formatter.format(Long.parseLong(stime) * 1000));
                map.put(mapKey, sin);
            }
        }
        return map;
    }
    
    /**
     * @Title: outLevel
     * @Description: 统一判断级别
     * @param levelId
     * @return boolean
     * @author chensq
     */
    public static boolean outLevel(long levelId) {
        boolean flag=true;
        if (levelId==1) {
            flag=false;
        }
        if (levelId==5) {
            flag=false;
        }
        return flag;
    } 
    
    /**
     * @Title: changeSQL
     * @Description: 更改sql and中插入别名
     * @param oldSQL
     * @param bieming
     * @return String
     * @author chensq
     */
    public static String changeSQL(String oldSQL, String bieming) {
        StringBuffer newSQLBuf=new StringBuffer();
        int andIndex=oldSQL.indexOf("and");
        newSQLBuf.append(oldSQL.substring(0, andIndex+4));
        newSQLBuf.append(bieming);
        newSQLBuf.append(".");
        newSQLBuf.append(oldSQL.substring(andIndex+4, oldSQL.length()));
        return newSQLBuf.toString();
    }
       
    /**
     * @Title: getAllModuleMap
     * @Description: 模块map
     * @param moduleList 模块名称list
     * @return Map<String,String>
     * @author chensq
     */
    public static Map<String, String> getAllModuleMap(List<AuthorizeModuleBean> moduleList){
        Map<String, String> map=null;
        if(moduleList!=null && moduleList.size()>0){
            map=new HashMap<String, String>();
            for(int x=0; x<moduleList.size(); x++){
                map.put(String.valueOf(moduleList.get(x).getId()), moduleList.get(x).getNamezh());
            }
        }
        return map;
    }
     
    /**
     * @Title: getAlarmLogColumnHealthMap
     * @Description:  health健康图使用的方法，取该段柱图的最高级别
     * @param map
     * @return Map<String,String>
     * @author chensq
     */
    public static Map<String, String> getAlarmLogColumnHealthMap(Map<String, Map<String, String>> map){
        //返回
        Map<String, String> returnMap=new LinkedHashMap<String, String>();
        //处理
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            String key=entry.getKey();
            Map<String, String> levelValueMap=entry.getValue();
            int maxLevel=0;
            long totalValue=0;
            for(Map.Entry<String, String> levelValueEntry : levelValueMap.entrySet()){
                String levelKey= levelValueEntry.getKey();
                String levelValue= levelValueEntry.getValue();
                //跳过情况
                if(!levelKey.equalsIgnoreCase("starttimeStr") && !levelKey.equalsIgnoreCase("starttime")){
                    //合计数量
                    totalValue+=Long.parseLong(levelValue);
                    
                    //最大的级别
                    if(Integer.parseInt(levelKey)>maxLevel){
                        maxLevel=Integer.parseInt(levelKey);
                    }
                }
            }
            //组建新的Str
            String valueStr=String.valueOf(maxLevel)+"-"+ String.valueOf(totalValue);
         
            //替换新的map
            returnMap.put(key, valueStr);
        }
        
        return returnMap;
    }
    
}
