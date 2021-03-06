/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmConstantUtil.java
 *创建人: chensq    创建时间: 2018年6月1日
 */
package com.protocolsoft.alarm.util;

/**
 * @ClassName: AlarmConstantUtil
 * @Description: 告警使用的常量工具类
 * @author chensq
 *
 */
public class AlarmConstantUtil {
    
    /**
     * @Fields URL_RRD_GET_BEFORE : url获取rrd的时间向前推得时间，现定4分钟
     */
    public static final int URL_RRD_GET_BEFORE=240; 

    /**
     * @Fields URL_BUSI_RRD_GET_BETWEEN : url、报文获取rrd的时间间隔时间，现定1分钟
     */
    public static final int URL_BUSI_RRD_GET_BETWEEN=60;
    
    /**
     * @Fields ONE_DAY_TIME : 一天的秒数
     */
    public static final long ONE_DAY_TIME =86400; //  该值必须可以被分钟整除
    
    /**
     * @Fields CHECK_BASELINE_DAY : 定义常量,智能告警学习天数
     */
    public static final long CHECK_BASELINE_DAY=7;
    
    /**
     * @Fields CHECK_BASELINE_TIME : 定义常量,智能告警学习秒数
     */
    public static final long CHECK_BASELINE_TIME=ONE_DAY_TIME * CHECK_BASELINE_DAY;
    
    /**
     * @Fields THESAMEWAYMODULEID :  相同的处理(模块id)
     */
    public static final String THESAMEWAYMODULEID="11,12,4,5,6,7,8,9,1,2"; 
    
    /**
     * @Fields SAMESPECIALMANAGEKPINAME :  相同特殊处理的字段(应用可用性,1分钟负载)
     */
    public static final String SAMESPECIALMANAGEKPINAME="'usability','load_1','probeSynchronous','probeNetwork'";
    
    /**
     * @Fields LOOP_DEL_COUNT : 告警删除清除指定数量以外的历史数据数量
     */
    public static final long LOOP_DEL_COUNT=100000;
 
}
