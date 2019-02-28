/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotSimpleBean;

/**
 * @ClassName: CreateJsonModlePieUtil
 * @Description: 饼图json文件生成器
 * @author 刘斌
 *
 */
@SuppressWarnings("unchecked")
public class CreateJsonModlePieUtil {

    /**
     * @Title: getJsonModel
     * @Description: 服务端，观察点，客户端饼图json生成器
     * @param fileName
     * @param map
     * @return String
     * @author 刘斌
     */
    public static Map<String, Object> getJsonModel(String fileName, Map<String, Object> map) {
        Map<String, Object> maps = new HashMap<>();
        JSONObject json = new JSONObject();
        int xf = 0;
        if (map.size() > 0) {
            List<Map<String, Object>> listJson = (List<Map<String, Object>>) map.get("data");
            JSONArray jsonData = new JSONArray();
            for (Map<String, Object> maping : listJson) {
                JSONObject jsonNew = new JSONObject();
                jsonNew.put("id", maping.get("id"));
                jsonNew.put("name", maping.get("name"));
                jsonNew.put("y", maping.get("value"));
                jsonData.add(jsonNew);
                xf++;
            }
            JSONObject jsonSeries = new JSONObject();
            jsonSeries.put("type", map.get("type"));
            jsonSeries.put("data", jsonData);
            JSONArray jsonArraySeries = new JSONArray();
            jsonArraySeries.add(jsonSeries);
            json.put("series", jsonArraySeries);
        } else {
            //JSONObject jsonSubtitle = new JSONObject();
            //jsonSubtitle.put("text", "暂无数据！");
            //json.put("subtitle", jsonSubtitle);
            maps.put("0", "暂无数据！");
        }
        JSONObject jsonChart = new JSONObject();
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        jsonChart.put("plotBorderWidth", null);
        jsonChart.put("plotShadow", false);
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#fff");
        jsonNoData.put("style", jsonStyle);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        JSONObject jsonTooltip = new JSONObject();
        json.put("tooltip", jsonTooltip);
        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonPie = new JSONObject();
        jsonPie.put("allowPointSelect", true);
        jsonPie.put("cursor", "pointer");
        JSONObject jsonDataLabels = new JSONObject();
        if(xf>9){
            jsonDataLabels.put("enabled", true);
        }else{
            jsonDataLabels.put("enabled", false);
        }
        jsonPie.put("dataLabels", jsonDataLabels);
        JSONObject jsonEvents = new JSONObject();
        jsonPie.put("events", jsonEvents);
        jsonPie.put("showInLegend", true);
        
        jsonPie.put("borderWidth", 0);
        
        jsonPlotOptions.put("pie", jsonPie);
        
        JSONObject jsonSeriesPoint = new JSONObject();
        jsonSeriesPoint.put("lineWidth", 1);
        jsonSeriesPoint.put("lineColor", "#fff");
        jsonSeriesPoint.put("fillOpacity", 0.25);
        jsonPlotOptions.put("series", jsonSeriesPoint);
        
        json.put("plotOptions", jsonPlotOptions);
        JSONObject jsonLegend = new JSONObject();
        jsonLegend.put("layout", "vertical");
        jsonLegend.put("align", "right");
        jsonLegend.put("verticalAlign", "top");
        jsonLegend.put("y", 8);
        jsonLegend.put("padding", 0);
        jsonLegend.put("itemMarginTop", 0);
        jsonLegend.put("itemMarginBottom", 5);
        json.put("legend", jsonLegend);
        String[] jsonColors = new String[] { "#058dc7", "#64e572", "#f7a35c",
            "#f15c80", "#90ed7d", "#434348", "#e4d354", "#8085e8",
            "#8d4653", "#91e8e1" };
        json.put("colors", jsonColors);
        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        
        json.put("exporting", jsonExporting);
        String jsonString = json.toJSONString();
        maps.put("1", jsonString);
        return maps;
    }
    
    /**
     * @Title: getJsonModelByBean
     * @Description: 饼图json文件生成器
     * @param fileName
     * @param bean
     * @return String
     * @author 刘斌
     */
    public static Map<String, Object> getJsonModelByBean(String fileName, PlotDataBean bean) {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = new JSONObject();
        int xf = 0;
        List<PlotSimpleBean> listJson = (List<PlotSimpleBean>) bean
                .getData();
        if (listJson.size() > 0) {
            JSONArray jsonData = new JSONArray();
            for (PlotSimpleBean beanNew : listJson) {
                JSONObject jsonNew = new JSONObject();
                jsonNew.put("name", beanNew.getName());
                jsonNew.put("y", beanNew.getValue());
                jsonData.add(jsonNew);
                xf++;
            }
            JSONObject jsonSeries = new JSONObject();
            jsonSeries.put("type", bean.getPlotName());
            jsonSeries.put("data", jsonData);
            JSONArray jsonArraySeries = new JSONArray();
            jsonArraySeries.add(jsonSeries);
            json.put("series", jsonArraySeries);
        }else{
            JSONObject jsonSubtitle = new JSONObject();
            //jsonSubtitle.put("text", "暂无数据！");
            json.put("subtitle", jsonSubtitle);
            map.put("0", "暂无数据");
        }
        JSONObject jsonChart = new JSONObject();
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        jsonChart.put("plotBorderWidth", null);
        jsonChart.put("plotShadow", false);
        json.put("chart", jsonChart);
        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#fff");
        jsonNoData.put("style", jsonStyle);
        json.put("noData", jsonNoData);
        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);
        JSONObject jsonTooltip = new JSONObject();
        json.put("tooltip", jsonTooltip);
        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonPie = new JSONObject();
        jsonPie.put("allowPointSelect", true);
        jsonPie.put("cursor", "pointer");
        
        jsonPie.put("borderWidth", 0);
        
        JSONObject jsonDataLabels = new JSONObject();
        if(xf>9){
            jsonDataLabels.put("enabled", true);
        }else{
            jsonDataLabels.put("enabled", false);
        }
        jsonPie.put("dataLabels", jsonDataLabels);
        JSONObject jsonEvents = new JSONObject();
        jsonPie.put("events", jsonEvents);
        jsonPie.put("showInLegend", true);
        jsonPlotOptions.put("pie", jsonPie);
        
        JSONObject jsonSeriesPoint = new JSONObject();
        jsonSeriesPoint.put("lineWidth", 1);
        jsonSeriesPoint.put("lineColor", "#fff");
        jsonSeriesPoint.put("fillOpacity", 0.25);
        jsonPlotOptions.put("series", jsonSeriesPoint);
        
        json.put("plotOptions", jsonPlotOptions);
        JSONObject jsonLegend = new JSONObject();
        jsonLegend.put("layout", "vertical");
        jsonLegend.put("align", "right");
        jsonLegend.put("verticalAlign", "top");
        jsonLegend.put("y", 8);
        jsonLegend.put("padding", 0);
        jsonLegend.put("itemMarginTop", 0);
        jsonLegend.put("itemMarginBottom", 5);
        json.put("legend", jsonLegend);
        String[] jsonColors = new String[] { "#058dc7", "#64e572", "#f7a35c",
            "#f15c80", "#90ed7d", "#434348", "#e4d354", "#8085e8", "#8d4653", "#91e8e1" };
        json.put("colors", jsonColors);
        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);
        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        
        json.put("exporting", jsonExporting);
        String jsonString = json.toJSONString();
        map.put("1", jsonString);
        return map;
    }
}