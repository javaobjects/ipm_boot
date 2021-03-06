/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DateUtils
 *创建人:chensq    创建时间:2017年5月23日
 */
package com.protocolsoft.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DateUtils
 * @Description: Date类型处理公共工具类
 * @author chensq
 *
 */
public class DateUtils {
    
    /**
     * <p>Title:DateUtils </p>
     * <p>Description: 构造方法</p>
     */ 
    public DateUtils() {
        
    }

    /**
     * @Title: getNowDate
     * @Description: 获取当前时间 (Date类型)
     * @return Date
     * @author chensq
     */
    public static Date getNowDate(){
        return new Date();
    }
    
    /**
     * @Title: getNowTimeMillisecond
     * @Description: 获取当前时间戳 毫秒
     * @return long 时间戳 毫秒
     * @author chensq
     */
    public static long getNowTimeMillisecond(){
        return new Date().getTime();
    }

    /**
     * @Title: getNowTimeSecond
     * @Description: 获取当前时间戳 秒
     * @return int 时间戳 秒
     * @author chensq
     */
    public static int getNowTimeSecond(){
        return (int)(new Date().getTime() / 1000);
    }
    
    /**
     * @Title: getNowTimeSecond
     * @Description: 时间按照参数取整
     * @param step
     * @return int
     * @author chensq
     */
    public static int getNowTimeSecond(int step) {
        int now = getNowTimeSecond();
        
        return (now - now % step);
    }
    
    /**
     * @Title: getNowStrDateTime
     * @Description: 获取当前 年月日时分秒
     * @return String 年月日时分秒格式
     * @author chensq
     */
    public static String getNowStrDateTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date().getTime());
    }

    /**
     * @Title: getNowStrDate 
     * @Description: 获取当前 年月日
     * @return String 年月日格式
     * @author chensq
     */
    public static String getNowStrDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date().getTime());
    }
    
    /**
     * @Title: getNowStrTime
     * @Description: 获取当前 时分秒
     * @return String 时分秒格式
     * @author chensq
     */
    public static String getNowStrTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date().getTime());
    }
    
    /**
     * @Title: longToDate
     * @Description: 时间戳-->Date对象
     * @param timeStamp 时间戳
     * @return Date 对应的对象
     * @author chensq
     */
    public static Date longToDate(long timeStamp){
        return new Date(timeStamp);
    }
    
    /**
     * @Title: getDataToString
     * @Description: Date to String  (根据Date与转换格式，当前时间或指定Date时间)
     * @param date Date类型的时间; null：当前时间
     * @param timeStyle 时间转换格式; null:时间格式
     * @return String
     * @author chensq
     */
    public static String getDataToString(Date date, String timeStyle){
        SimpleDateFormat sdf=null;
        if (StringUtils.isEmpty(timeStyle)) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat(timeStyle);
        }
        if (date == null) {
            return sdf.format(new Date());
        } else {
            return sdf.format(date);
        }
    }
 
    /**
     * @Title: getLongToString
     * @Description: long to String  (对long时间根据转换格式进行转换)
     * @param timeL long类型的时间
     * @param timeStyle 时间转换格式; null:时间格式
     * @return String str time
     * @author chensq
     */
    public static String getLongToString(long timeL, String timeStyle){
        SimpleDateFormat sdf=null;
        if (StringUtils.isEmpty(timeStyle)){
            sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf=new SimpleDateFormat(timeStyle);
        }
        return sdf.format(new Date(timeL));
    }

    /**
     * @Title: getLongToStrDateTime
     * @Description: long to Str DateTime  年月日时分秒 (对long时间根据转换格式进行转换)
     * @param now long类型的时间
     * @return String 年月日时分秒格式
     * @author chensq
     */
    public static String getLongToStrDateTime(long now){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(now));
    }

    /**
     * @Title: getLongToStrDate
     * @Description: long to StrDate  年月日 (对long时间根据转换格式进行转换)
     * @param now long类型的时间
     * @return String 年月日格式
     * @author chensq
     */
    public static String getLongToStrDate(long now){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(now));
    }
    
    /**
     * @Title: getLongToStrTime
     * @Description: long to StrTime  时分秒 (对long时间根据转换格式进行转换)
     * @param now long类型的时间
     * @return String 时分秒格式
     * @author chensq
     */
    public static String getLongToStrTime(long now){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(now));
    }
    
    /**
     * @Title: getServerTime
     * @Description: 获取服务器时间
     * @return String
     * @author chensq
     */
    public static String getServerTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String serverTime = sdf.format(Calendar.getInstance().getTime());
        return serverTime;
    }
    
    /**
     * @Title: string2long
     * @Description: String to long
     * @param time
     * @return long
     * @author chensq
     */
    public static long string2long(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    
    /**
     * @Title: normalize
     * @Description: 时间取整
     * @param timestamp
     * @param step
     * @return long
     * @author chensq
     */
    public static long normalize(long timestamp, long step) {
        return timestamp - timestamp % step;
    }
    
}
