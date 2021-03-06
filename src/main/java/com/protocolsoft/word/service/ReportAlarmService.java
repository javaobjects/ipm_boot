/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ReportAlarmService.java
 *创建人: wangjianmin    创建时间: 2018年7月11日
 */
package com.protocolsoft.word.service;

import com.protocolsoft.word.bean.ReportAlarmBean;


/**
 * @ClassName: ReportAlarmService
 * @Description: 报表告警类
 * @author wangjianmin
 *
 */
public interface ReportAlarmService {
    
   /**
    * 
    * @Title: getReportAlarmData
    * @Description: 自定义，非环比告警报表图形
    * @param bean  接收参数
    * @return String
    * @author wangjianmin
    */
    String getReportAlarmData(ReportAlarmBean bean);
    
     /**
      * 
      * @Title: getReportRingRatioData
      * @Description: 非环比告警报表图形
      * @param bean  接收参数
      * @return String
      * @author wangjianmin
      */
    String  getReportRingRatioData(ReportAlarmBean bean);

}
