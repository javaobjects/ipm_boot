/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: CreateJsonModleErrorUtil
 * @Description: 异常提示图片json生成器
 * @author 刘斌
 *
 */
public class CreateJsonModleErrorUtil {
    
    /**
     * @Title: getJsonModel
     * @Description: 业务和观察点无法正常获取图表数据的错误图面页表
     * @param fileName
     * @return String
     * @author 刘斌
     */
    public static Map<String, Object> getJsonModel(String fileName) {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = new JSONObject();
        
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
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
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
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#fff");
        jsonNoData.put("style", jsonStyle2);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        map.put("0", "图形显示出现问题，请检查本图对应的观察点和业务还是否存在！");
        JSONObject jsonXAxis = new JSONObject();
        jsonXAxis.put("type", "datetime");
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "#434348");
        jsonLabels.put("style", jsonStyle3);
        jsonXAxis.put("labels", jsonLabels);
        JSONObject jsonDateTimeLabelFormats = new JSONObject();
        jsonDateTimeLabelFormats.put("minute", "'%H:%M'");
        jsonXAxis.put("dateTimeLabelFormats", jsonDateTimeLabelFormats);
        json.put("xAxis", jsonXAxis);
        JSONObject jsonYAxis = new JSONObject();
        JSONObject jsonTitle2 = new JSONObject();
        jsonTitle2.put("text", fileName);
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
        jsonYAxis.put("minRang", 1);
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
        sgf = sgf.replace("\"resetZoomButton\"", "resetZoomButton");
        sgf = sgf.replace("\"zoomType\"", "zoomType");
        sgf = sgf.replace("\"noData\"", "noData");
        sgf = sgf.replace("\"id\"", "id");
        sgf = sgf.replace("\"name\"", "name");
        sgf = sgf.replace("\"data\"", "data");
        sgf = sgf.replace("\"global\"", "global");
        sgf = sgf.replace("\"timezoneOffset\"", "timezoneOffset");
        sgf = sgf.replace("\"plotBackgroundColor\"", "plotBackgroundColor");
        sgf = sgf.replace("\"backgroundColor\"", "backgroundColor");
        map.put("1", sgf);
        return map;
    }
}
