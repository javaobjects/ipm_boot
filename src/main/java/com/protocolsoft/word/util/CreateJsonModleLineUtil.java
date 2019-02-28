/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.utils.UnitUtils;

/**
 * @ClassName: CreateJsonModleLineUtil
 * @Description: 线性图json生成器
 * @author 刘斌
 *
 */
public class CreateJsonModleLineUtil {

    /**
     * @Title: getJsonModel
     * @Description: 客户端，服务端，观察点线图json文件生成器
     * @param fileName
     * @param map
     * @return String
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonModel(String fileName, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        Map<String, Object> maps = new HashMap<>();
        String[] jsonColors = new String[]{"#058DC7", "#64E572", "#ED561B",
            "#6AF9C4", "#DDDF00", "#50B432", "#FF9655", "#FFF363", "#016191"};
        json.put("colors", jsonColors);
        String dawValue = (String) map.get("unit");
        List<Map<String, Object>> listJson = (List<Map<String, Object>>) map.get("data");
        List<Map<String, Object>> jsonSeries = new ArrayList<Map<String, Object>>();
        double max = 0;
        double min = 0;
        double eve = 0;
        int x = 0;
        int e = 0;
        int number = 0;
        if (null != listJson) {
            String duo = "";
            for (Map<String, Object> maping : listJson) {
                JSONObject jsonNew = new JSONObject();
                jsonNew.put("id", maping.get("id"));
                jsonNew.put("name", maping.get("name"));
                if("".equals(duo)){
                    duo = (String) maping.get("name");
                }else if(!duo.equals((String) maping.get("name"))){
                    e ++;
                }
                List<SimpleEntry<Double, Double>> listMap = (List<SimpleEntry<Double, Double>>) maping.get("value");
                if(null!=listMap){
                    number++;
                    if(number > 1){
                        return linesJsonAndMap(fileName, map);
                    }
                }
                List<Object[]> listString = new ArrayList<Object[]>();
                if (null != listMap) {
                    for (SimpleEntry<Double, Double> entry : listMap) {
                        String dd = entry.getKey() + "";
                        Object[] strings = new Object[] {
                                Long.parseLong(dd + "000") + 8 * 60 * 60000, entry.getValue() };
                        if (entry.getValue() > max && e==0) {
                            max = entry.getValue();
                        }
                        if (0 == x) {
                            min = entry.getValue();
                        }
                        if (entry.getValue() < min || entry.getValue() == 0) {
                            if(e==0){
                                min = entry.getValue();
                            }
                        }
                        listString.add(strings);
                        if(e==0){
                            eve += entry.getValue();
                            x++;
                        }
                    }
                }
                jsonNew.put("data", listString);
                jsonSeries.add(jsonNew);
            }
        } else {
            maps.put("0", "暂无数据！");
        }
        String subTitle;
        SimpleEntry<Double, String> simpleEntryMax = UnitUtils.numFormat(max, dawValue);
        SimpleEntry<Double, String> simpleEntryMin = UnitUtils.numFormat(min, dawValue);

        if (x == 0) {
            subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() + " 最小值："
                    + simpleEntryMin.getKey() + simpleEntryMin.getValue();
        } else {
            SimpleEntry<Double, String> simpleEntryEve = UnitUtils.numFormat(eve / x, dawValue);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            String deleteEveLastTwo = df.format(simpleEntryEve.getKey());
            if(!"%".equals(simpleEntryMax.getValue())){
                subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() 
                        + " 平均值：" + deleteEveLastTwo + simpleEntryEve.getValue() + " 最小值：" + simpleEntryMin.getKey() + simpleEntryMin.getValue();
            }else{
                subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue()
                        + " 最小值：" + simpleEntryMin.getKey() + simpleEntryMin.getValue();
            }
        }
        json.put("series", jsonSeries);

        JSONObject jsonGlobal = new JSONObject();
        jsonGlobal.put("timezoneOffset", -8 * 60);
        json.put("global", jsonGlobal);

        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        json.put("exporting", jsonExporting);

        JSONObject jsonChart = new JSONObject();
        jsonChart.put("type", "area");
        jsonChart.put("zoomType", "x");
        JSONObject jsonResetZoomButton = new JSONObject();
        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("align", "right");
        jsonPosition.put("verticalAlign", "top");
        jsonResetZoomButton.put("position", jsonPosition);
        JSONObject jsonTheme = new JSONObject();
        jsonTheme.put("fill", "#f7f7f7");
        jsonTheme.put("stroke", "silver");
        jsonTheme.put("r", 1);
        JSONObject jsonStates = new JSONObject();
        JSONObject jsonHover = new JSONObject();
        jsonHover.put("fill", "#e6e6e6");
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#333");
        jsonHover.put("style", jsonStyle);
        jsonStates.put("hover", jsonHover);
        jsonTheme.put("states", jsonStates);
        jsonResetZoomButton.put("position", jsonPosition);
        jsonResetZoomButton.put("theme", jsonTheme);
        jsonChart.put("resetZoomButton", jsonResetZoomButton);
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#fff");
        jsonNoData.put("style", jsonStyle2);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        
        if (null == json.get("subtitle")) {
            JSONObject jsonSubtitle = new JSONObject();
            //jsonSubtitle.put("text", subTitle);
            json.put("subtitle", jsonSubtitle);
            maps.put("0", subTitle);
        }
        JSONObject jsonXAxis = new JSONObject();
        jsonXAxis.put("type", "datetime");
        
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "#434348");
        jsonLabels.put("style", jsonStyle3);
        jsonXAxis.put("labels", jsonLabels);
        
        JSONObject jsonDateTimeLabelFormats = new JSONObject();
        jsonDateTimeLabelFormats.put("minute", "%H:%M");
        jsonXAxis.put("dateTimeLabelFormats", jsonDateTimeLabelFormats);
        json.put("xAxis", jsonXAxis);
        
        
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
        json.put("yAxis", jsonYAxis);
        
        
        JSONObject jsonTooltip = new JSONObject();
        JSONObject jsonDateTimeLabelFormats2 = new JSONObject();
        jsonDateTimeLabelFormats2.put("second", "%H:%M:%S");
        jsonTooltip.put("dateTimeLabelFormats", jsonDateTimeLabelFormats2);
        json.put("tooltip", jsonTooltip);
        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonArea = new JSONObject();
        JSONObject jsonDataLabels = new JSONObject();
        jsonArea.put("dataLabels", jsonDataLabels);
        JSONObject jsonMarker = new JSONObject();
        jsonMarker.put("enabled", false);
        jsonMarker.put("symbol", "circle");
        jsonMarker.put("radius", 2);
        JSONObject jsonStates2 = new JSONObject();
        JSONObject jsonHover2 = new JSONObject();
        jsonHover2.put("enabled", true);
        jsonStates2.put("hover", jsonHover2);
        jsonMarker.put("marker", jsonStates2);
        jsonArea.put("marker", jsonMarker);
        
        JSONObject jsonSeriesPoint = new JSONObject();
        jsonSeriesPoint.put("lineWidth", 1);
        jsonSeriesPoint.put("lineColor", "#058DC7");
        jsonSeriesPoint.put("fillOpacity", 0.25);
        jsonPlotOptions.put("series", jsonSeriesPoint);
        
        jsonPlotOptions.put("area", jsonArea);
        jsonPlotOptions.put("colorByPoint", true);
        json.put("plotOptions", jsonPlotOptions);
        JSONObject jsonResponsive = new JSONObject();
        JSONArray jsonRules = new JSONArray();
        JSONObject jsonCondition = new JSONObject();
        jsonCondition.put("maxWidth", 500);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("condition", jsonCondition);
        jsonRules.add(jsonObject);
        jsonResponsive.put("rules", jsonRules);
        String jsonString = json.toJSONString();
        String sgf = jsonString.replace("\"chart\"", "chart");
        sgf = sgf.replace("\"position\"", "position");
        sgf = sgf.replace("\"align\"", "align");
        sgf = sgf.replace("\"verticalAlign\"", "verticalAlign");
        sgf = sgf.replace("\"theme\"", "theme");
        sgf = sgf.replaceAll("\"fill\"", "fill");
        sgf = sgf.replace("\"r\"", "r");
        sgf = sgf.replace("\"states\"", "states");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replaceAll("\"style\"", "style");
        sgf = sgf.replaceAll("\"color\"", "color");
        sgf = sgf.replace("\"stroke\"", "stroke");
        sgf = sgf.replaceAll("\"type\"", "type");
        sgf = sgf.replace("\"plotOptions\"", "plotOptions");
        sgf = sgf.replace("\"area\":", "area:");
        sgf = sgf.replace("\"dataLabels\"", "dataLabels");
        sgf = sgf.replaceAll("\"marker\"", "marker");
        sgf = sgf.replaceAll("\"enabled\"", "enabled");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replace("\"radius\"", "radius");
        sgf = sgf.replace("\"symbol\"", "symbol");
        sgf = sgf.replace("\"colorByPoint\"", "colorByPoint");
        sgf = sgf.replace("\"subtitle\"", "subtitle");
        sgf = sgf.replace("\"title\"", "title");
        sgf = sgf.replaceAll("\"text\"", "text");
        sgf = sgf.replace("\"tooltip\"", "tooltip");
        sgf = sgf
                .replaceAll("\"dateTimeLabelFormats\"", "dateTimeLabelFormats");
        sgf = sgf.replaceAll("\"second\"", "second");
        sgf = sgf.replace("\"xAxis\"", "xAxis");
        sgf = sgf.replaceAll("\"labels\"", "labels");
        sgf = sgf.replace("\"yAxis\"", "yAxis");
        sgf = sgf.replace("\"min\"", "min");
        sgf = sgf.replace("\"minTickInterval\"", "minTickInterval");
        sgf = sgf.replace("\"minRange\"", "minRange");
        sgf = sgf.replace("\"resetZoomButton\"", "resetZoomButton");
        sgf = sgf.replace("\"zoomType\"", "zoomType");
        sgf = sgf.replace("\"noData\"", "noData");
        sgf = sgf.replace("\"id\"", "id");
        sgf = sgf.replace("\"name\"", "name");
        sgf = sgf.replace("\"data\"", "data");
        sgf = sgf.replace("\"global\"", "global");
        sgf = sgf.replace("\"timezoneOffset\"", "timezoneOffset");
        sgf = sgf.replace("\"lineWidth\"", "lineWidth");
        sgf = sgf.replace("\"lineColor\"", "lineColor");
        sgf = sgf.replace("\"fillOpacity\"", "fillOpacity");
        sgf = sgf.replace("\"colors\"", "colors");
        sgf = sgf.replace("\"plotBackgroundColor\"", "plotBackgroundColor");
        sgf = sgf.replace("\"backgroundColor\"", "backgroundColor");
        maps.put("1", sgf);
        return maps;
    }

    /**
     * @Title: getJsonModelByBean
     * @Description: 图文件生成器
     * @param fileName
     * @param plotDataBean
     * @return String
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonModelByBean(String fileName,
            PlotDataBean plotDataBean) {
        Map<String, Object> newMap = new HashMap<>();
        
        JSONObject json = new JSONObject();
        
        String[] jsonColors = new String[]{"#058DC7", "#64E572", "#ED561B",
            "#6AF9C4", "#DDDF00", "#50B432", "#FF9655", "#FFF363", "#016191"};
        json.put("colors", jsonColors);
        String dawValue = plotDataBean.getUnit();
        List<PlotSimpleBean> listJson = (List<PlotSimpleBean>) plotDataBean.getData();
        List<Map<String, Object>> jsonSeries = new ArrayList<Map<String, Object>>();

        double max = 0;
        double min = 0;
        double eve = 0;
        int x = 0;
        if (null != listJson) {
            for (PlotSimpleBean bean : listJson) {
                JSONObject jsonNew = new JSONObject();
                jsonNew.put("name", bean.getName());
                List<SimpleEntry<Double, Double>> listMap = (List<SimpleEntry<Double, Double>>) bean
                        .getValue();

                List<Object[]> listString = new ArrayList<Object[]>();
                if (null != listMap) {
                    for (SimpleEntry<Double, Double> entry : listMap) {
                        String dd = entry.getKey() + "";
                        Object[] strings = new Object[] {
                                Long.parseLong(dd + "000") + 8 * 60 * 60000, entry.getValue() };
                        if (entry.getValue() > max) {
                            max = entry.getValue();
                        }
                        if (0 == x) {
                            min = entry.getValue();
                        }
                        if (entry.getValue() < min || entry.getValue() == 0) {
                            min = entry.getValue();
                        }
                        eve += entry.getValue();
                        listString.add(strings);
                        x++;
                    }
                }
                jsonNew.put("data", listString);
                jsonSeries.add(jsonNew);
            }
        }else{
            JSONObject jsonSubtitle = new JSONObject();
            //jsonSubtitle.put("text", "暂无数据！");
            json.put("subtitle", jsonSubtitle);
            newMap.put("0", "暂无数据！");
        }
        String subTitle;
        SimpleEntry<Double, String> simpleEntryMax = UnitUtils.numFormat(max, dawValue);
        SimpleEntry<Double, String> simpleEntryMin = UnitUtils.numFormat(min, dawValue);

        if (x == 0) {
            subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() + "-最小值："
                    + simpleEntryMin.getKey() + simpleEntryMin.getValue();
        } else {
            SimpleEntry<Double, String> simpleEntryEve = UnitUtils.numFormat(eve / x, dawValue);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            String deleteEveLastTwo = df.format(simpleEntryEve.getKey());
            subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() + " 平均值：" + deleteEveLastTwo + simpleEntryEve.getValue()
                    + " 最小值：" + simpleEntryMin.getKey() + simpleEntryMin.getValue();
        }
        json.put("series", jsonSeries);

        JSONObject jsonGlobal = new JSONObject();
        jsonGlobal.put("timezoneOffset", -8 * 60);
        json.put("global", jsonGlobal);

        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        json.put("exporting", jsonExporting);

        JSONObject jsonChart = new JSONObject();
        jsonChart.put("type", "");
        jsonChart.put("zoomType", "x");
        JSONObject jsonResetZoomButton = new JSONObject();
        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("align", "right");
        jsonPosition.put("verticalAlign", "top");
        jsonResetZoomButton.put("position", jsonPosition);
        JSONObject jsonTheme = new JSONObject();
        jsonTheme.put("fill", "#f7f7f7");
        jsonTheme.put("stroke", "silver");
        jsonTheme.put("r", 1);
        JSONObject jsonStates = new JSONObject();
        JSONObject jsonHover = new JSONObject();
        jsonHover.put("fill", "#e6e6e6");
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#333");
        jsonHover.put("style", jsonStyle);
        jsonStates.put("hover", jsonHover);
        jsonTheme.put("states", jsonStates);
        jsonResetZoomButton.put("position", jsonPosition);
        jsonResetZoomButton.put("theme", jsonTheme);
        jsonChart.put("resetZoomButton", jsonResetZoomButton);
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#fff");
        jsonNoData.put("style", jsonStyle2);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        if(null==json.get("subtitle")){
            newMap.put("0", subTitle);
        }
        JSONObject jsonXAxis = new JSONObject();
        jsonXAxis.put("type", "datetime");
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "#434348");
        jsonLabels.put("style", jsonStyle3);
        jsonXAxis.put("labels", jsonLabels);
        JSONObject jsonDateTimeLabelFormats = new JSONObject();
        jsonDateTimeLabelFormats.put("minute", "%H:%M");
        jsonXAxis.put("dateTimeLabelFormats", jsonDateTimeLabelFormats);
        json.put("xAxis", jsonXAxis);
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
        json.put("yAxis", jsonYAxis);
        JSONObject jsonTooltip = new JSONObject();
        JSONObject jsonDateTimeLabelFormats2 = new JSONObject();
        jsonDateTimeLabelFormats2.put("second", "%H:%M:%S");
        jsonTooltip.put("dateTimeLabelFormats", jsonDateTimeLabelFormats2);
        json.put("tooltip", jsonTooltip);
        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonArea = new JSONObject();
        JSONObject jsonDataLabels = new JSONObject();
        jsonArea.put("dataLabels", jsonDataLabels);
        JSONObject jsonMarker = new JSONObject();
        jsonMarker.put("enabled", false);
        jsonMarker.put("symbol", "circle");
        jsonMarker.put("radius", 2);
        JSONObject jsonStates2 = new JSONObject();
        JSONObject jsonHover2 = new JSONObject();
        jsonHover2.put("enabled", true);
        jsonStates2.put("hover", jsonHover2);
        jsonMarker.put("marker", jsonStates2);
        jsonArea.put("marker", jsonMarker);
        
        JSONObject jsonSeriesPoint = new JSONObject();
        jsonSeriesPoint.put("lineWidth", 1);
        //jsonSeriesPoint.put("lineColor", "#fff");
        jsonSeriesPoint.put("fillOpacity", 0.25);
        jsonPlotOptions.put("series", jsonSeriesPoint);
        
        jsonPlotOptions.put("area", jsonArea);
        jsonPlotOptions.put("colorByPoint", true);
        json.put("plotOptions", jsonPlotOptions);
        JSONObject jsonResponsive = new JSONObject();
        JSONArray jsonRules = new JSONArray();
        JSONObject jsonCondition = new JSONObject();
        jsonCondition.put("maxWidth", 500);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("condition", jsonCondition);
        jsonRules.add(jsonObject);
        jsonResponsive.put("rules", jsonRules);
        String jsonString = json.toJSONString();
        String sgf = jsonString.replace("\"chart\"", "chart");
        sgf = sgf.replace("\"position\"", "position");
        sgf = sgf.replace("\"align\"", "align");
        sgf = sgf.replace("\"verticalAlign\"", "verticalAlign");
        sgf = sgf.replace("\"theme\"", "theme");
        sgf = sgf.replaceAll("\"fill\"", "fill");
        sgf = sgf.replace("\"r\"", "r");
        sgf = sgf.replace("\"states\"", "states");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replaceAll("\"style\"", "style");
        sgf = sgf.replaceAll("\"color\"", "color");
        sgf = sgf.replace("\"stroke\"", "stroke");
        sgf = sgf.replaceAll("\"type\"", "type");
        sgf = sgf.replace("\"plotOptions\"", "plotOptions");
        sgf = sgf.replace("\"area\":", "area:");
        sgf = sgf.replace("\"dataLabels\"", "dataLabels");
        sgf = sgf.replaceAll("\"marker\"", "marker");
        sgf = sgf.replaceAll("\"enabled\"", "enabled");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replace("\"radius\"", "radius");
        sgf = sgf.replace("\"symbol\"", "symbol");
        sgf = sgf.replace("\"colorByPoint\"", "colorByPoint");
        sgf = sgf.replace("\"subtitle\"", "subtitle");
        sgf = sgf.replace("\"title\"", "title");
        sgf = sgf.replaceAll("\"text\"", "text");
        sgf = sgf.replace("\"tooltip\"", "tooltip");
        sgf = sgf
                .replaceAll("\"dateTimeLabelFormats\"", "dateTimeLabelFormats");
        sgf = sgf.replaceAll("\"second\"", "second");
        sgf = sgf.replace("\"xAxis\"", "xAxis");
        sgf = sgf.replaceAll("\"labels\"", "labels");
        sgf = sgf.replace("\"yAxis\"", "yAxis");
        sgf = sgf.replace("\"min\"", "min");
        sgf = sgf.replace("\"minRange\"", "minRange");
        sgf = sgf.replace("\"minTickInterval\"", "minTickInterval");
        sgf = sgf.replace("\"resetZoomButton\"", "resetZoomButton");
        sgf = sgf.replace("\"zoomType\"", "zoomType");
        sgf = sgf.replace("\"noData\"", "noData");
        sgf = sgf.replace("\"id\"", "id");
        sgf = sgf.replace("\"name\"", "name");
        sgf = sgf.replace("\"data\"", "data");
        sgf = sgf.replace("\"global\"", "global");
        sgf = sgf.replace("\"timezoneOffset\"", "timezoneOffset");
        sgf = sgf.replace("\"lineWidth\"", "lineWidth");
        sgf = sgf.replace("\"lineColor\"", "lineColor");
        sgf = sgf.replace("\"fillOpacity\"", "fillOpacity");
        sgf = sgf.replace("\"colors\"", "colors");
        sgf = sgf.replace("\"plotBackgroundColor\"", "plotBackgroundColor");
        sgf = sgf.replace("\"backgroundColor\"", "backgroundColor");
        newMap.put("1", sgf);
        return newMap;

    }

    /**
     * 
     * @Title: linesJsonAndMap
     * @Description: 
     * @param fileName
     * @param map
     * @return Map<String,Object>
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> linesJsonAndMap(String fileName, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String typeOfPicture = "area";
        Map<String, Object> maps = new HashMap<>();
        String[] jsonColors = new String[]{"#058DC7", "#ED561B", "#64E572",
            "#6AF9C4", "#DDDF00", "#50B432", "#FF9655", "#FFF363", "#016191"};
        json.put("colors", jsonColors);
        String dawValue = (String) map.get("unit");
        List<Map<String, Object>> listJson = (List<Map<String, Object>>) map.get("data");
        List<Map<String, Object>> jsonSeries = new ArrayList<Map<String, Object>>();
        double max = 0;
        double min = 0;
        double eve = 0;
        int x = 0;
        int e = 0;
        if (null != listJson) {
            String duo = "";
            
            for (Map<String, Object> maping : listJson) {
                JSONObject jsonNew = new JSONObject();
                jsonNew.put("id", maping.get("id"));
                jsonNew.put("name", maping.get("name"));
                if("".equals(duo)){
                    duo = (String) maping.get("name");
                }else if(!duo.equals((String) maping.get("name"))){
                    e ++;
                }
                List<SimpleEntry<Double, Double>> listMap = (List<SimpleEntry<Double, Double>>) maping.get("value");
                List<Object[]> listString = new ArrayList<Object[]>();
                if (null != listMap) {
                    for (SimpleEntry<Double, Double> entry : listMap) {
                        String dd = entry.getKey() + "";
                        Object[] strings = new Object[] {
                                Long.parseLong(dd + "000") + 8 * 60 * 60000, entry.getValue() };
                        if (entry.getValue() > max && e==0) {
                            max = entry.getValue();
                        }
                        if (0 == x) {
                            min = entry.getValue();
                        }
                        if (entry.getValue() < min || entry.getValue() == 0) {
                            if(e==0){
                                min = entry.getValue();
                            }
                        }
                        listString.add(strings);
                        if(e==0){
                            eve += entry.getValue();
                            x++;
                        }
                        if(entry.getValue() != 0){
                            typeOfPicture = "";
                        }
                    }
                }
                jsonNew.put("data", listString);
                jsonSeries.add(jsonNew);
            }
        } else {
            maps.put("0", "暂无数据！");
        }
        String subTitle;
        SimpleEntry<Double, String> simpleEntryMax = UnitUtils.numFormat(max, dawValue);
        SimpleEntry<Double, String> simpleEntryMin = UnitUtils.numFormat(min, dawValue);

        if (x == 0) {
            subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() + " 最小值："
                    + simpleEntryMin.getKey() + simpleEntryMin.getValue();
        } else {
            SimpleEntry<Double, String> simpleEntryEve = UnitUtils.numFormat(eve / x, dawValue);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            String deleteEveLastTwo = df.format(simpleEntryEve.getKey());
            if(!"%".equals(simpleEntryMax.getValue())){
                subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue() 
                        + " 平均值：" + deleteEveLastTwo + simpleEntryEve.getValue() + " 最小值：" + simpleEntryMin.getKey() + simpleEntryMin.getValue();
            }else{
                subTitle = "最大值：" + simpleEntryMax.getKey() + simpleEntryMax.getValue()
                         + " 最小值：" + simpleEntryMin.getKey() + simpleEntryMin.getValue();
            }
        }
        json.put("series", jsonSeries);

        JSONObject jsonGlobal = new JSONObject();
        jsonGlobal.put("timezoneOffset", -8 * 60);
        json.put("global", jsonGlobal);

        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        json.put("exporting", jsonExporting);

        JSONObject jsonChart = new JSONObject();
        jsonChart.put("type", typeOfPicture);
        jsonChart.put("zoomType", "x");
        JSONObject jsonResetZoomButton = new JSONObject();
        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("align", "right");
        jsonPosition.put("verticalAlign", "top");
        jsonResetZoomButton.put("position", jsonPosition);
        JSONObject jsonTheme = new JSONObject();
        jsonTheme.put("fill", "#f7f7f7");
        jsonTheme.put("stroke", "silver");
        jsonTheme.put("r", 1);
        JSONObject jsonStates = new JSONObject();
        JSONObject jsonHover = new JSONObject();
        jsonHover.put("fill", "#e6e6e6");
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#333");
        jsonHover.put("style", jsonStyle);
        jsonStates.put("hover", jsonHover);
        jsonTheme.put("states", jsonStates);
        jsonResetZoomButton.put("position", jsonPosition);
        jsonResetZoomButton.put("theme", jsonTheme);
        jsonChart.put("resetZoomButton", jsonResetZoomButton);
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#fff");
        jsonNoData.put("style", jsonStyle2);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        
        if (null == json.get("subtitle")) {
            JSONObject jsonSubtitle = new JSONObject();
            //jsonSubtitle.put("text", subTitle);
            json.put("subtitle", jsonSubtitle);
            maps.put("0", subTitle);
        }
        JSONObject jsonXAxis = new JSONObject();
        jsonXAxis.put("type", "datetime");
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "#434348");
        jsonLabels.put("style", jsonStyle3);
        jsonXAxis.put("labels", jsonLabels);
        JSONObject jsonDateTimeLabelFormats = new JSONObject();
        jsonDateTimeLabelFormats.put("minute", "%H:%M");
        jsonXAxis.put("dateTimeLabelFormats", jsonDateTimeLabelFormats);
        json.put("xAxis", jsonXAxis);
        
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
        json.put("yAxis", jsonYAxis);
        
        
        JSONObject jsonTooltip = new JSONObject();
        JSONObject jsonDateTimeLabelFormats2 = new JSONObject();
        jsonDateTimeLabelFormats2.put("second", "%H:%M:%S");
        jsonTooltip.put("dateTimeLabelFormats", jsonDateTimeLabelFormats2);
        json.put("tooltip", jsonTooltip);
        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonArea = new JSONObject();
        JSONObject jsonDataLabels = new JSONObject();
        jsonArea.put("dataLabels", jsonDataLabels);
        JSONObject jsonMarker = new JSONObject();
        jsonMarker.put("enabled", false);
        jsonMarker.put("symbol", "circle");
        jsonMarker.put("radius", 2);
        JSONObject jsonStates2 = new JSONObject();
        JSONObject jsonHover2 = new JSONObject();
        jsonHover2.put("enabled", true);
        jsonStates2.put("hover", jsonHover2);
        jsonMarker.put("marker", jsonStates2);
        jsonArea.put("marker", jsonMarker);
        
        JSONObject jsonSeriesPoint = new JSONObject();
        jsonSeriesPoint.put("lineWidth", 1);
        //jsonSeriesPoint.put("lineColor", "#fff");
        jsonSeriesPoint.put("fillOpacity", 0.25);
        JSONObject jsonMarkerTwo = new JSONObject();
        jsonMarkerTwo.put("enabled", false);
        jsonSeriesPoint.put("marker", jsonMarkerTwo);
        jsonPlotOptions.put("series", jsonSeriesPoint);
        
        jsonPlotOptions.put("area", jsonArea);
        jsonPlotOptions.put("colorByPoint", true);
        
        json.put("plotOptions", jsonPlotOptions);
        JSONObject jsonResponsive = new JSONObject();
        JSONArray jsonRules = new JSONArray();
        JSONObject jsonCondition = new JSONObject();
        jsonCondition.put("maxWidth", 500);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("condition", jsonCondition);
        jsonRules.add(jsonObject);
        jsonResponsive.put("rules", jsonRules);
        String jsonString = json.toJSONString();
        String sgf = jsonString.replace("\"chart\"", "chart");
        sgf = sgf.replace("\"position\"", "position");
        sgf = sgf.replace("\"align\"", "align");
        sgf = sgf.replace("\"verticalAlign\"", "verticalAlign");
        sgf = sgf.replace("\"theme\"", "theme");
        sgf = sgf.replaceAll("\"fill\"", "fill");
        sgf = sgf.replace("\"r\"", "r");
        sgf = sgf.replace("\"states\"", "states");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replaceAll("\"style\"", "style");
        sgf = sgf.replaceAll("\"color\"", "color");
        sgf = sgf.replace("\"stroke\"", "stroke");
        sgf = sgf.replaceAll("\"type\"", "type");
        sgf = sgf.replace("\"plotOptions\"", "plotOptions");
        sgf = sgf.replace("\"area\":", "area:");
        sgf = sgf.replace("\"dataLabels\"", "dataLabels");
        sgf = sgf.replaceAll("\"marker\"", "marker");
        sgf = sgf.replaceAll("\"enabled\"", "enabled");
        sgf = sgf.replaceAll("\"marker\"", "marker");
        sgf = sgf.replace("\"hover\"", "hover");
        sgf = sgf.replace("\"radius\"", "radius");
        sgf = sgf.replace("\"symbol\"", "symbol");
        sgf = sgf.replace("\"colorByPoint\"", "colorByPoint");
        sgf = sgf.replace("\"subtitle\"", "subtitle");
        sgf = sgf.replace("\"title\"", "title");
        sgf = sgf.replaceAll("\"text\"", "text");
        sgf = sgf.replace("\"tooltip\"", "tooltip");
        sgf = sgf.replaceAll("\"dateTimeLabelFormats\"", "dateTimeLabelFormats");
        sgf = sgf.replaceAll("\"second\"", "second");
        sgf = sgf.replace("\"xAxis\"", "xAxis");
        sgf = sgf.replaceAll("\"labels\"", "labels");
        sgf = sgf.replace("\"yAxis\"", "yAxis");
        sgf = sgf.replace("\"min\"", "min");
        sgf = sgf.replace("\"minTickInterval\"", "minTickInterval");
        sgf = sgf.replace("\"minRange\"", "minRange");
        sgf = sgf.replace("\"resetZoomButton\"", "resetZoomButton");
        sgf = sgf.replace("\"zoomType\"", "zoomType");
        sgf = sgf.replace("\"noData\"", "noData");
        sgf = sgf.replace("\"id\"", "id");
        sgf = sgf.replace("\"name\"", "name");
        sgf = sgf.replace("\"data\"", "data");
        sgf = sgf.replace("\"global\"", "global");
        sgf = sgf.replace("\"timezoneOffset\"", "timezoneOffset");
        sgf = sgf.replace("\"lineWidth\"", "lineWidth");
        sgf = sgf.replace("\"lineColor\"", "lineColor");
        sgf = sgf.replace("\"fillOpacity\"", "fillOpacity");
        sgf = sgf.replace("\"colors\"", "colors");
        sgf = sgf.replace("\"plotBackgroundColor\"", "plotBackgroundColor");
        sgf = sgf.replace("\"backgroundColor\"", "backgroundColor");
        maps.put("1", sgf);
        return maps;
    }
}
