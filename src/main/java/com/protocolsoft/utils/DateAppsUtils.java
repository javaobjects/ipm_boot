/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DateAppsUtils
 *创建人:chensq    创建时间:2017年5月23日
 */
package com.protocolsoft.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.protocolsoft.alarm.util.AlarmConstantUtil;
import com.protocolsoft.utils.bean.TimeDefaultBean;

/**
 * @ClassName: DateAppsUtils
 * @Description: DateAppsUtils日期时间类型处理公共工具类(含有具体业务的方法)
 * @author chensq
 *
 */
public class DateAppsUtils {
      
    /**
     * <p>Title:DateAppsUtils </p>
     * <p>Description:无参构造器，使用默认国别 </p>
     */ 
    public DateAppsUtils() {
        
    }

    /**
     * @Fields DEFAULTINTERVAL_TEN : 10
     */
    public static final int DEFAULTINTERVAL_TEN = 10;

    /**
     * @Fields DEFAULTINTERVAL_ONEMINUTES : 一分钟秒数 60*1
     */
    public static final int DEFAULTINTERVAL_ONEMINUTES = 60;

    /**
     * @Fields RADIX100 : 一百进制100
     */
    public static final long RADIX100 = 100;
 
    /**
     * @Fields DEFAULTINTERVAL_TWOMINUTES : 二分钟秒数 60*2
     */
    public static final int DEFAULTINTERVAL_TWOMINUTES = 120;
  
    /**
     * @Fields DEFAULTINTERVAL_TENMINUTES : 十分钟秒数 60*10
     */
    public static final int DEFAULTINTERVAL_TENMINUTES = 600;    
    
    /**
     * @Fields RADIX1000 : 一千进制1000
     */
    public static final long RADIX1000 = 1000;
    
    /**
     * @Fields DEFAULTINTERVAL_HALFHOUR : 半小时秒数 60*30
     */
    public static final int DEFAULTINTERVAL_HALFHOUR = 1800;
   
    /**
     * @Fields DEFAULTINTERVAL_ONEHOUR : 一小时秒数 60*60
     */
    public static final int DEFAULTINTERVAL_ONEHOUR = 3600;
    
    /**
     * @Fields DEFAULTINTERVAL_TWOHOUR : 两小时秒数 60*60*2
     */
    public static final int DEFAULTINTERVAL_TWOHOUR = 7200;

    /**
     * @Fields DEFAULTINTERVAL_TENHOUR : 十小时秒数 60*60*10
     */
    public static final int DEFAULTINTERVAL_TENHOUR = 36000;
    
    /**
     * @Fields DEFAULTINTERVAL_ONEDAY : 24小时秒数 60*60*24
     */
    public static final int DEFAULTINTERVAL_ONEDAY = 86400;
    
    /**
     * @Fields DEFAULTINTERVAL_THREEDAY : 三天秒数 60*60*24*3
     */
    public static final int DEFAULTINTERVAL_THREEDAY = 259200;
     
    /**
     * @Fields DEFAULTINTERVAL_ONEWEEK : 一周秒数 60*60*24*7
     */
    public static final int DEFAULTINTERVAL_ONEWEEK = 604800;
    
    /**
     * @Fields DEFAULTINTERVAL_ONEMONTH : 一个月秒数 60*60*24*30
     */
    public static final int DEFAULTINTERVAL_ONEMONTH = 2592000;
    
    /**
     * @Fields DEFAULTINTERVAL_ONEQUARTER : 一季度秒数 60*60*24*30*3
     */
    public static final int DEFAULTINTERVAL_ONEQUARTER = 7776000;
    
    /**
     * @Title: getDefaultNowTimeBeforeTime
     * @Description: 获取系统时间 以及 指定多少秒之前的时间
     * @param backZoom 0:默认10分钟
     * @return List<Long> list.get(0):开始时间 ,list.get(1):结束时间
     * @author chensq
     */
    public static List<Long> getDefaultNowTimeBeforeTime(long backZoom) {
        List<Long> list = new ArrayList<Long>();
        long startTime = 0;
        long endTime = System.currentTimeMillis() / RADIX1000;
        if (backZoom == 0) {
            startTime = endTime - DEFAULTINTERVAL_TENMINUTES;
        } else {
            startTime = endTime - backZoom;
        }
        list.add(startTime);
        list.add(endTime);
        
        return list;
    }

    /**
     * @Title: getDefaultBeginTimeBackTime
     * @Description: 根据给定时间、向前推的时间 获取起止时间
     * @param endTime 结束时间
     * @param backZoom 0:默认10分钟
     * @return List<Long> list.get(0):开始时间 ,list.get(1):结束时间
     * @author chensq
     */
    public static List<Long> getDefaultBeginTimeBackTime(long endTime, long backZoom) {
        List<Long> list = new ArrayList<Long>();
        long startTime = 0;
        if (backZoom == 0) {
            startTime = endTime - DEFAULTINTERVAL_TENMINUTES;
        } else {
            startTime = endTime - backZoom;
        }
        list.add(startTime);
        list.add(endTime);
        
        return list;
    }

    /**
     * @Title: getIntervalAuto
     * @Description: 根据开始时间 结束时间 获取粒度 (可根据实际情况完善或者调整)
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return int
     * @author chensq
     */
    public static int getIntervalAuto(long startTime, long endTime){
        int gran = 0;
        long diff = endTime - startTime;
        if (diff <= DEFAULTINTERVAL_ONEDAY) {  // 一天内粒度
            gran = DEFAULTINTERVAL_TEN;
        } else if (diff > DEFAULTINTERVAL_ONEDAY && diff <= DEFAULTINTERVAL_ONEWEEK) { // 一周内粒度
            gran = DEFAULTINTERVAL_ONEMINUTES;
        } else if (diff > DEFAULTINTERVAL_ONEWEEK && diff <= DEFAULTINTERVAL_ONEMONTH) { // 一个月内粒度
            gran = DEFAULTINTERVAL_TENMINUTES;
        } else if (diff > DEFAULTINTERVAL_ONEMONTH && diff <= DEFAULTINTERVAL_ONEQUARTER) { // 一个季度内粒度
            gran = DEFAULTINTERVAL_ONEHOUR;
        } else { // 其他时间粒度
            gran = DEFAULTINTERVAL_ONEDAY;
        }
        return gran;
    }
       
    /**
     * @Title: getCommpairIntervalAuto
     * @Description: 根据开始时间 结束时间 获取粒度  通信对使用(可根据实际情况完善或者调整)
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return int 粒度
     * @author chensq
     */
    public static int getCommpairIntervalAuto(long startTime, long endTime){
        int gran = DEFAULTINTERVAL_TEN;
        long diff = endTime - startTime;
        if (diff < DEFAULTINTERVAL_ONEHOUR) {
            gran = DEFAULTINTERVAL_TEN;
        } else if (diff >= DEFAULTINTERVAL_ONEHOUR && diff < DEFAULTINTERVAL_TENHOUR) { 
            gran = DEFAULTINTERVAL_ONEMINUTES;
        } else if (diff >= DEFAULTINTERVAL_TENHOUR && diff < DEFAULTINTERVAL_THREEDAY) {
            gran = DEFAULTINTERVAL_ONEHOUR;
        } else if (diff >= DEFAULTINTERVAL_THREEDAY) {
            gran = DEFAULTINTERVAL_ONEDAY;
        }
        return gran;
    }
    
    /**
     * @Title: getCommpairNewIntervalAuto
     * @Description: 根据开始时间 结束时间 获取粒度  通信对使用(可根据实际情况完善或者调整)
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return int 粒度  10秒粒度 ..1分钟粒度(60s)..一小时粒度(600)..一天粒度(86400)..
     * @author chensq
     */
    public static int getCommpairNewIntervalAuto(long startTime, long endTime){
        int gran = 10;
        long diff = endTime - startTime;
        if (diff < 3600) { //小于一小时
            gran = 10;
        } else if (diff >= 3600 && diff < 36000) { //大于等于1小时    小于10小时
            gran = 60;
        } else if (diff >= 36000 && diff < 345600) { //大于等于10小时    小于4天(345600)
            gran = 600;
        } else if (diff >= 345600) { //大于等于4天
            gran = 86400;
        }
        return gran;
    }
   

    /**
     * @Title: getCommpairRowTime
     * @Description: 2018年1月9日 下午4:59:41
     * @param startTime
     * @param endTime
     * @param intval
     * @return String
     * @author chensq
     */
    public static String getCommpairRowTime(long startTime, long endTime, long intval){
        String stime=getFormatedDateByIntval(startTime, intval);
        String etime=getFormatedDateByIntval(endTime, intval);
        return stime+" ~ "+etime;
    }
    
    /**
     * @Title: getFormatedDateByIntval
     * @Description: 根据 给定时间与粒度 格式化时间 (可根据实际情况完善或者调整)
     * @param time 需要格式化的时间
     * @param intval 粒度
     * @return String str time 当前语言下的相应的默认时间格式
     * @author chensq
     */
    public static String getFormatedDateByIntval(long time, long intval){
        String formatStyle = "yyyy-MM-dd HH:mm:ss";
        if (intval >= DEFAULTINTERVAL_ONEDAY  && intval < DEFAULTINTERVAL_ONEMONTH) {
            //天
            formatStyle = "MM-dd";
        } else if (intval >= DEFAULTINTERVAL_ONEHOUR && intval < DEFAULTINTERVAL_ONEDAY) {
            //小时
            formatStyle = "MM-dd HH";
        } else if (intval >= DEFAULTINTERVAL_ONEMINUTES  && intval < DEFAULTINTERVAL_ONEHOUR) {
            //1分钟
            formatStyle = "HH:mm";
        } else if (intval >= DEFAULTINTERVAL_TEN  && intval < DEFAULTINTERVAL_ONEMINUTES) {
            //10秒
            formatStyle = "HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        String date = sdf.format(new Date(time));
        return date;
    }
    
    /**
     * @Title: getFormatedDateByCommpair
     * @Description: 通信对详细列表使用的时间
     * @param time  需要格式化的时间
     * @return String str time 当前语言下的相应的默认时间格式
     * @author chensq
     */
    public static String getFormatedDateByCommpair(long time){
        String formatStyle = "MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        String date = sdf.format(new Date(time));
        return date;
    }
    
    /**
     * 
     * @Title: getGraphDefaultTime
     * @Description: 获取图形默认时间
     * @return TimeDefaultBean
     * @author www
     */
    public static TimeDefaultBean getGraphDefaultTime() {
        long endtime = getEndDefaultTime();
        long starttime = endtime - 600;
        TimeDefaultBean bean = new TimeDefaultBean(starttime, endtime);
        
        return bean;
    }
    
    /**
     * 
     * @Title: getGraphDefaultTime
     * @Description: 获取图形默认时间
     * @return TimeDefaultBean
     * @author www
     */
    public static TimeDefaultBean getGraphDefaultTime60() {
        long endtime = getEndDefaultTime60();
        long starttime = endtime - 600;
        TimeDefaultBean bean = new TimeDefaultBean(starttime, endtime);
        
        return bean;
    }
    
    /**
     * 
     * @Title: getRrdDefaultTime
     * @Description: 获取十字格、告警数据默认时间
     * @return TimeDefaultBean
     * @author www
     */
    public static TimeDefaultBean getRrdDefaultTime() {
        long endtime = getEndDefaultTime();
        long starttime = endtime - 10;
        TimeDefaultBean bean = new TimeDefaultBean(starttime, endtime);
        
        return bean;
    }
    
    /**
     * 
     * @Title: getRrdDefaultTime
     * @Description: 获取十字格、告警数据默认时间
     * @return TimeDefaultBean
     * @author www
     */
    public static TimeDefaultBean getRrdDefaultTime60() {
        long endtime = getEndDefaultTime60();
        long starttime = endtime - 60;
        TimeDefaultBean bean = new TimeDefaultBean(starttime, endtime);
        
        return bean;
    }
    
    /**
     * 
     * @Title: getEndDefaultTime
     * @Description: 获取默认结束时间
     * @return long
     * @author www
     */
    private static long getEndDefaultTime() {
        long endtime = 0;
        endtime = System.currentTimeMillis() / 1000;
        endtime = endtime - endtime % 10 - 140;
        
        return endtime;
    }
    
    /**
     * 
     * @Title: getEndDefaultTime
     * @Description: 获取默认结束时间
     * @return long
     * @author www
     */
    private static long getEndDefaultTime60() {
        long endtime = System.currentTimeMillis() / 1000 - 140;
        endtime = endtime - endtime % 60;
        
        return endtime;
    }
  
    /**
     * @Title: timeSection
     * @Description: 现在十字格柱状图图形的count段切分
     * @param starttime
     * @param endtime
     * @param count
     * @return List<String> 例如：time1-time2
     * @author chensq
     */
    public static List<String> timeSection(long starttime, long endtime, int count){
        long time = (endtime - starttime) / count;
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            if (i!=count-1) {
                list.add(starttime + i * time + "-" + (starttime + (i + 1) * time));
            } else {
                list.add(starttime + i * time + "-" +  endtime);                
            }
        }
        return list;
    }
        
    /**
     * 
     * @Title: getManyDayBeforeTimes
     * @Description: 根据指定时间查询其前n天的时间，有顺序，由远至近
     * @param time long
     * @return List<Long>
     * @author chensq
     */
    public static List<Long> getManyDayBeforeTimes(Long time){
        List<Long> list=new ArrayList<Long>();
        for(int x=1; x<=AlarmConstantUtil.CHECK_BASELINE_DAY; x++){
            long differenceTime = x * AlarmConstantUtil.ONE_DAY_TIME;
            long thisDayTime=time-differenceTime;
            list.add(thisDayTime);
        }
        Collections.reverse(list);
        return list;
    }

     
}
