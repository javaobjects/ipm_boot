/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ReportAlarmServiceIpml.java
 *创建人: wangjianmin    创建时间: 2018年7月11日
 */
package com.protocolsoft.word.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.alarm.bean.AlarmColumnDataBean;
import com.protocolsoft.alarm.bean.AlarmColumnTimeLineBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.word.bean.ReportAlarmBean;
import com.protocolsoft.word.service.ReportAlarmService;

/**
 * @ClassName: ReportAlarmServiceIpml
 * @Description: 非环比告警图形类
 * @author wangjianmin
 *
 */
@Service
public class ReportAlarmServiceImpl implements ReportAlarmService{
    /**
     * 注入AlarmLogService
     */
    @Autowired(required = false)
    AlarmLogService alarmLog;
    
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;

    @Override
    public String getReportAlarmData(ReportAlarmBean bean) {
        //最终返回的数据格式
        JSONObject dataMap = new JSONObject();
        //告警数据参数
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        alarmLogBean.setStarttime(bean.getStarttime()); 
        alarmLogBean.setEndtime(bean.getEndtime());
        alarmLogBean.setBusinessId(bean.getBusinessId());
        alarmLogBean.setWatchpointId(bean.getWatchpointId());
        alarmLogBean.setModuleId(bean.getModuleId());       
        
        //返回的 x轴 数据格式
        JSONObject data = new JSONObject();
        //返回的 数据格式
        JSONObject data1 = new JSONObject();
        JSONObject data3 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        //用于分隔图形x轴
        int  spot =0 ;
        if(bean.getSpot() == 0){
            spot = 24;
        }else{
            spot = bean.getSpot();
        }
       
        //时间切分
        List<String> dataList = DateAppsUtils.timeSection(bean.getStarttime(), bean.getEndtime(), spot);
        //存放分隔好的时间
        List<String> timeList = new ArrayList<String>();
        List<AlarmColumnTimeLineBean> alarmColumnTimeLineBeanList = new ArrayList<AlarmColumnTimeLineBean>();
        AlarmColumnTimeLineBean alarmColumnTimeLineBean = null; // 时间对象
        Date date =null;
        SimpleDateFormat sdf = null;  
       //设置时间对象
        for (int i = 0; i < dataList.size(); i++) {
            alarmColumnTimeLineBean = new AlarmColumnTimeLineBean();
            String[] timeArray = dataList.get(i).split("-");
            alarmColumnTimeLineBean.setColumnstime(Long.parseLong(timeArray[0]));
            alarmColumnTimeLineBean.setColumnetime(Long.parseLong(timeArray[1]));
            if(bean.getType() == 2){
                date = new Date(Long.parseLong(timeArray[1])*1000);
                sdf = new SimpleDateFormat("HH:mm:ss");  
                timeList.add(sdf.format(date.getTime()));
            }else if(bean.getType() == 3){
                date = new Date(Long.parseLong(timeArray[1])*1000);
                timeList.add(dateToWeek(date.getTime()));
            }else if(bean.getType() == 1){
                Long dateLong = bean.getEndtime() - bean.getStarttime();
                date = new Date(Long.parseLong(timeArray[1])*1000);
                if(dateLong < 24 * 60 * 60){
                    if(dateLong < 60 * 60){
                        sdf = new SimpleDateFormat("HH:mm"); 
                    }else{
                        sdf = new SimpleDateFormat("HH:mm:ss"); 
                    }
                }else if(dateLong > 30 * 24 * 60 * 60){
                    sdf = new SimpleDateFormat("MM-dd");
                }else{
                    sdf = new SimpleDateFormat("MM-dd HH");
                }
                timeList.add(sdf.format(date.getTime()));
            }else{
                date = new Date(Long.parseLong(timeArray[1])*1000);
                sdf = new SimpleDateFormat("dd");
                timeList.add(sdf.format(date.getTime()));
            }
           
            // 设置入对象
            alarmColumnTimeLineBeanList.add(alarmColumnTimeLineBean);
        }
        //获取告警数据
        List<AlarmColumnDataBean> listAlarm = alarmLog.alarmNum(alarmLogBean, dataList);
        for (AlarmColumnDataBean alarmColumnDataBean : listAlarm) {
            jsonArray = new JSONArray();
            data1 = new JSONObject();
            data1.put("id", alarmColumnDataBean.getAlarmLevelId());
            data1.put("name", alarmColumnDataBean.getAlarmLevelName());
            int size = alarmColumnDataBean.getAlarmColumnDetailBeanList().size();
            for (int i = 0; i < size; i++) {
                data3 = new JSONObject();
                data3.put("starttime", alarmColumnTimeLineBeanList.get(i).getColumnstime());
                data3.put("endtime", alarmColumnTimeLineBeanList.get(i).getColumnetime());
                data3.put("y",  alarmColumnDataBean.getAlarmColumnDetailBeanList().get(i).getValue());
                data3.put("alarmLevel", alarmColumnDataBean.getAlarmLevelId());
                jsonArray.add(data3);
            }
            data1.put("data", jsonArray);
            
            if(alarmColumnDataBean.getAlarmLevelId() == 2){ //普通
                data1.put("color", "#F4EA2A");
            }else if(alarmColumnDataBean.getAlarmLevelId() == 3){//重要
                data1.put("color", "#FF862D");
            }else{//紧急
                data1.put("color", "#D22408");
            }
            list.add(data1);
        }
        
        
        //y轴
        JSONObject jsonYAxis = new JSONObject();
        JSONObject jsonTitle2 = new JSONObject();
        jsonTitle2.put("text", "");
        JSONObject jsonStyle4 = new JSONObject();
        jsonStyle4.put("color", "#dedddd");
        jsonTitle2.put("style", jsonStyle4);
        jsonYAxis.put("title", jsonTitle2);
        
        JSONObject jsonLabels2 = new JSONObject();
        JSONObject jsonStyle5 = new JSONObject();
        jsonStyle5.put("color", "#434348");
        jsonLabels2.put("style", jsonStyle5);
        
        
        jsonYAxis.put("labels", jsonLabels2);
        jsonYAxis.put("min", 0);
        jsonYAxis.put("minRange", 1);
        jsonYAxis.put("minTickInterval", 1);
        dataMap.put("yAxis", jsonYAxis);
        
        //chart
        JSONObject chartJson = new JSONObject();
        chartJson.put("renderTo", "draw_charts");
        chartJson.put("type", "column");
        chartJson.put("backgroundColor", "rgba(0,0,0,0)");
        dataMap.put("chart", chartJson);
        
        //右上图标
        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        dataMap.put("credits", jsonCredits);
        
        //底部图标
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        dataMap.put("exporting", jsonExporting);
        
        //title
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        dataMap.put("title", jsonTitle);
        
        //plotOptions
        JSONObject jsonStr = new JSONObject();
        JSONObject columnJson = new JSONObject();
        jsonStr.put("pointPadding", 0.2);
        jsonStr.put("stacking", "normal");
        jsonStr.put("borderWidth", 0);
        jsonStr.put("cursor", "pointer");
        columnJson.put("column", jsonStr);
        JSONArray plotArray = new JSONArray();
        plotArray.add(columnJson);
        for (Object str : plotArray) {
            dataMap.put("plotOptions", str);
        }
        
        data.put("categories", timeList);
        dataMap.put("series", list);
        dataMap.put("xAxis", data);
        dataMap.put("colorByPoint", true);
        
        //legend
        JSONObject legendtJson = new JSONObject();
        dataMap.put("legend", legendtJson);
        
        String jsonString = dataMap.toJSONString();
        String  json = jsonString.replaceAll("\"chart\"", "chart")
                .replaceAll("\"categories\"", "categories")
                .replaceAll("\"labels\"", "labels")
                .replaceAll("\"series\"", "series")
                .replaceAll("\"xAxis\"", "xAxis")
                .replaceAll("\"colorByPoint\"", "colorByPoint")
                .replaceAll("\"renderTo\"", "renderTo")
                .replaceAll("\"type\"", "type")
                .replaceAll("\"allowDecimals\"", "allowDecimals")
                .replaceAll("\"min\"", "min")
                .replaceAll("\"minRange\"", "minRange")
                .replaceAll("\"minTickInterval\"", "minTickInterval")
                .replaceAll("\"id\"", "id")
                .replaceAll("\"name\"", "name")
                .replaceAll("\"data\"", "data")
                .replaceAll("\"yAxis\"", "yAxis")
                .replaceAll("\"starttime\"", "starttime")
                .replaceAll("\"color\"", "color")
                .replaceAll("\"endtime\"", "endtime")
                .replaceAll("\"alarmLevel\"", "alarmLevel")
                .replaceAll("\"y\"", "y")
                .replaceAll("\"style\"", "style")
                .replaceAll("\"enabled\"", "enabled")
                .replaceAll("\"plotOptions\"", "plotOptions")
                .replaceAll("\"title\"", "title")
                .replaceAll("\"text\"", "text")
                .replaceAll("\"backgroundColor\"", "backgroundColor");
        return json;
    }

    @Override
    public String getReportRingRatioData(ReportAlarmBean bean) {
        //最终返回的数据格式
        JSONObject dataMap = new JSONObject();
        //返回的 x轴 数据格式
        JSONObject data = new JSONObject();
        //告警数据参数
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        alarmLogBean.setStarttime(bean.getStarttime()); 
        alarmLogBean.setEndtime(bean.getEndtime());
        alarmLogBean.setBusinessId(bean.getBusinessId());
        alarmLogBean.setWatchpointId(bean.getWatchpointId());
        alarmLogBean.setModuleId(bean.getModuleId()); 
        
        //返回的  series数据格式
        JSONObject data1 = new JSONObject();
        JSONObject data2 = new JSONObject();
        JSONObject data3 = new JSONObject();
        JSONObject data4 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //用于分隔图形x轴
        int  spot =0 ;
        if(bean.getSpot() == 0){
            spot = 24;
        }else{
            spot = bean.getSpot();
        }
        
        //时间切分
        List<String> dataList = DateAppsUtils.timeSection(bean.getStarttime(), bean.getEndtime(), spot);
        //存放分隔好的时间
        List<String> timeList = new ArrayList<String>();
        List<AlarmColumnTimeLineBean> alarmColumnTimeLineBeanList = new ArrayList<AlarmColumnTimeLineBean>();
        AlarmColumnTimeLineBean alarmColumnTimeLineBean = null; // 时间对象
        SimpleDateFormat sdf = null;  
        Date date =null;
        //设置时间对象
        for (int i = 0; i < dataList.size(); i++) {
            alarmColumnTimeLineBean = new AlarmColumnTimeLineBean();
            String[] timeArray = dataList.get(i).split("-");
            alarmColumnTimeLineBean.setColumnstime(Long.parseLong(timeArray[0]));
            alarmColumnTimeLineBean.setColumnetime(Long.parseLong(timeArray[1]));
            date = new Date(Long.parseLong(timeArray[1])*1000);
            if(bean.getType() == 2){
                sdf = new SimpleDateFormat("HH:mm:ss");  
                timeList.add(sdf.format(date.getTime()));
            }else if(bean.getType() == 3){
                timeList.add(dateToWeek(date.getTime()));
            }else{
                sdf = new SimpleDateFormat("dd");
                timeList.add(sdf.format(date.getTime()));
            }
            // 设置入对象
            alarmColumnTimeLineBeanList.add(alarmColumnTimeLineBean);
        }
        
        //获取告警原始数据
        List<AlarmColumnDataBean> listAlarm = alarmLog.alarmNum(alarmLogBean, dataList);
        int zhuzi=listAlarm.get(0).getAlarmColumnDetailBeanList().size();
        int typeSize= listAlarm.size();
        for (int i = 0; i < zhuzi; i++) {
            data1 = new JSONObject();
            data1.put("starttime", alarmColumnTimeLineBeanList.get(i).getColumnstime());
            data1.put("endtime", alarmColumnTimeLineBeanList.get(i).getColumnetime());
            long count=0;
            for (int j = 0; j < typeSize; j++) {
                count+=listAlarm.get(j).getAlarmColumnDetailBeanList().get(i).getValue();
                data1.put("y",  count);
            }
            jsonArray.add(data1);
            data2.put("name", bean.getName());
            data2.put("data", jsonArray);
            data2.put("stack", "male");
        }
        
        list.add(data2);
        
        //获取环比数据
        if(bean.getRingRatioStart() != 0){
            alarmLogBean = new AlarmLogBean();
            alarmLogBean.setStarttime(bean.getRingRatioStart());
            alarmLogBean.setEndtime(bean.getRingRatioEnd());
            dataList = DateAppsUtils.timeSection(bean.getRingRatioStart(), bean.getRingRatioEnd(), spot);
            listAlarm = alarmLog.alarmNum(alarmLogBean, dataList);
            zhuzi=listAlarm.get(0).getAlarmColumnDetailBeanList().size();
            typeSize= listAlarm.size();
            for (int i = 0; i < zhuzi; i++) {
                data3 = new JSONObject();
                data3.put("starttime", alarmColumnTimeLineBeanList.get(i).getColumnstime());
                data3.put("endtime", alarmColumnTimeLineBeanList.get(i).getColumnetime());
                long count=0;
                for (int j = 0; j < typeSize; j++) {
                    count+=listAlarm.get(j).getAlarmColumnDetailBeanList().get(i).getValue();
                    data3.put("y",  count);
                }
                jsonArray1.add(data3);
            }
            data4.put("name", bean.getRingRatioName());
            data4.put("data", jsonArray1);
            data4.put("stack", "female");
            list.add(data4);
        }
       
        //y轴
        JSONObject jsonYAxis = new JSONObject();
        JSONObject jsonTitle2 = new JSONObject();
        jsonTitle2.put("text", "");
        JSONObject jsonStyle4 = new JSONObject();
        jsonStyle4.put("color", "#dedddd");
        jsonTitle2.put("style", jsonStyle4);
        jsonYAxis.put("title", jsonTitle2);
        
        JSONObject jsonLabels2 = new JSONObject();
        JSONObject jsonStyle5 = new JSONObject();
        jsonStyle5.put("color", "#434348");
        jsonLabels2.put("style", jsonStyle5);
        
        
        jsonYAxis.put("labels", jsonLabels2);
        jsonYAxis.put("min", 0);
        jsonYAxis.put("minRange", 1);
        jsonYAxis.put("minTickInterval", 1);
        dataMap.put("yAxis", jsonYAxis);
        
        //chart
        JSONObject chartJson = new JSONObject();
        chartJson.put("renderTo", "draw_charts");
        chartJson.put("type", "column");
        chartJson.put("backgroundColor", "rgba(0,0,0,0)");
        dataMap.put("chart", chartJson);
        
        //右上图标
        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        dataMap.put("credits", jsonCredits);
        
        //底部图标
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        dataMap.put("exporting", jsonExporting);
        
        //title
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        dataMap.put("title", jsonTitle);
        
        //plotOptions
        JSONObject jsonStr = new JSONObject();
        JSONObject columnJson = new JSONObject();
        jsonStr.put("pointPadding", 0.2);
        jsonStr.put("stacking", "normal");
        jsonStr.put("borderWidth", 0);
        jsonStr.put("cursor", "pointer");
        columnJson.put("column", jsonStr);
        JSONArray plotArray = new JSONArray();
        plotArray.add(columnJson);
        for (Object str : plotArray) {
            dataMap.put("plotOptions", str);
        }
        
        data.put("categories", timeList);
        dataMap.put("series", list);
        dataMap.put("xAxis", data);
        dataMap.put("colorByPoint", true);
        
        //legend
        JSONObject legendtJson = new JSONObject();
        dataMap.put("legend", legendtJson);
        
        String jsonString = dataMap.toJSONString();
        String  json = jsonString.replaceAll("\"chart\"", "chart")
                .replaceAll("\"categories\"", "categories")
                .replaceAll("\"labels\"", "labels")
                .replaceAll("\"series\"", "series")
                .replaceAll("\"xAxis\"", "xAxis")
                .replaceAll("\"colorByPoint\"", "colorByPoint")
                .replaceAll("\"renderTo\"", "renderTo")
                .replaceAll("\"type\"", "type")
                .replaceAll("\"allowDecimals\"", "allowDecimals")
                .replaceAll("\"min\"", "min")
                .replaceAll("\"minRange\"", "minRange")
                .replaceAll("\"minTickInterval\"", "minTickInterval")
                .replaceAll("\"id\"", "id")
                .replaceAll("\"name\"", "name")
                .replaceAll("\"data\"", "data")
                .replaceAll("\"yAxis\"", "yAxis")
                .replaceAll("\"starttime\"", "starttime")
                .replaceAll("\"color\"", "color")
                .replaceAll("\"endtime\"", "endtime")
                .replaceAll("\"alarmLevel\"", "alarmLevel")
                .replaceAll("\"y\"", "y")
                .replaceAll("\"style\"", "style")
                .replaceAll("\"enabled\"", "enabled")
                .replaceAll("\"plotOptions\"", "plotOptions")
                .replaceAll("\"title\"", "title")
                .replaceAll("\"text\"", "text")
                .replaceAll("\"backgroundColor\"", "backgroundColor");
    
        return json;
    }
    
    /**
     * 
     * @Title: dateToWeek
     * @Description: 根据日期获取星期
     * @param datetime
     * @return String
     * @author wangjianmin
     */
    public static String dateToWeek(long datetime) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = new Date(datetime) ;
            cal.setTime(datet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

}
