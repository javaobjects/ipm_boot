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
 * @ClassName: CreateJsonModleColumnUtil
 * @Description: 柱星图json文件生成器
 * @author 刘斌
 *
 */
public class CreateJsonModleColumnUtil {

    /**
     * @Title: getJsonModel
     * @Description: 生成竖柱图json文件生成器
     * @param fileName
     * @param plotDataBean
     * @param typeOfPicture
     * @return String
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonModel(String fileName,
            PlotDataBean plotDataBean, String typeOfPicture) {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = new JSONObject();
        List<PlotSimpleBean> listJson = (List<PlotSimpleBean>) plotDataBean.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (listJson.size() > 0) {
            for (PlotSimpleBean bean : listJson) {
                String snew = bean.getName();
                Long time = Long.parseLong(snew + "000");
                JSONArray jsonAray = new JSONArray();
                jsonAray.add(time);
                jsonAray.add(bean.getValue());
                jsonArray.add(jsonAray);
                
            }
        } else {
//            JSONObject jsonSubtitle = new JSONObject();
//            jsonSubtitle.put("text", "暂无数据");
//            json.put("subtitle", jsonSubtitle);
            map.put("0", "暂无数据");
        }
        jsonObject.put("data", jsonArray);
        jsonObject.put("_colorIndex", 0);

        JSONArray jsonSeries = new JSONArray();
        jsonSeries.add(jsonObject);
        json.put("series", jsonSeries);

        JSONObject jsonxAxis = new JSONObject();
        JSONObject jsonLabels2 = new JSONObject();
        JSONObject jsonStyle4 = new JSONObject();
        jsonLabels2.put("style", jsonStyle4);
        jsonLabels2.put("reserveSpace", false);
        jsonxAxis.put("labels", jsonLabels2);
        jsonxAxis.put("type", "datetime");
        json.put("xAxis", jsonxAxis);

        JSONObject jsonChart = new JSONObject();
        jsonChart.put("type", typeOfPicture);
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        json.put("chart", jsonChart);

        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#fff");
        jsonNoData.put("style", jsonStyle);
        json.put("noData", jsonNoData);

        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);

        if (null == json.get("subtitle")) {
            JSONObject jsonSubtitle = new JSONObject();
            jsonSubtitle.put("text", "");
            json.put("subtitle", jsonSubtitle);
        }

        JSONObject jsonyAxis = new JSONObject();
        jsonyAxis.put("min", 0);
        JSONObject jsonTitle2 = new JSONObject();
        jsonTitle2.put("text", "");
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#dedddd");
        jsonTitle2.put("style", jsonStyle2);
        jsonyAxis.put("title", jsonTitle2);
        json.put("yAxis", jsonyAxis);

        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "f3f2f2");
        jsonLabels.put("style", jsonStyle3);
        json.put("labels", jsonLabels);

        JSONObject jsonLegend = new JSONObject();
        jsonLegend.put("enabled", false);
        json.put("legend", jsonLegend);

        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonBar = new JSONObject();
        jsonBar.put("pointPadding", 0.2);
        jsonBar.put("borderWidth", 0);
        jsonBar.put("events", new JSONObject());
        jsonPlotOptions.put("bar", jsonBar);
        json.put("plotOptions", jsonPlotOptions);

        JSONObject jsonColumn = new JSONObject();
        jsonColumn.put("events", new JSONObject());
        json.put("column", jsonColumn);

        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);

        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        map.put("1", json.toJSONString());
        return map;
    }

    
    /**
     * @Title: getJsonModelX
     * @Description: 生成横柱图json文件生成器
     * @param id
     * @param fileName
     * @param plotDataBean
     * @param typeOfPicture
     * @param newPath
     * @return String
     * @author 刘斌
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonModelX(String id, String fileName,
            PlotDataBean plotDataBean, String typeOfPicture, String newPath) {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = new JSONObject();
        List<PlotSimpleBean> listJson = (List<PlotSimpleBean>) plotDataBean
                .getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonCategories = new JSONArray();
        int lengh = 1;
        String news = "";
        if (listJson.size() > 0) {
            for (PlotSimpleBean bean : listJson) {
                jsonCategories.add(lengh);
                String snew = bean.getName();
                news += ("(" + lengh + "):" + snew + "\r");
                JSONObject jsonObjectMin = new JSONObject();
                jsonObjectMin.put("starttime", plotDataBean.getStarttime());
                jsonObjectMin.put("endtime", plotDataBean.getEndtime());
                jsonObjectMin.put("y", bean.getValue());
                jsonArray.add(jsonObjectMin);
                lengh++;
            }
        } else {
            news = "还没有用于统计的数据";
        }
        CreateNewTextUtil.createJsonFileByBeanOther(id, newPath, news);

        jsonObject.put("data", jsonArray);
        jsonObject.put("_colorIndex", 0);

        JSONArray jsonSeries = new JSONArray();
        jsonSeries.add(jsonObject);
        json.put("series", jsonSeries);

        JSONObject jsonxAxis = new JSONObject();
        jsonxAxis.put("categories", jsonCategories);
        jsonxAxis.put("useHTML", true);
        JSONObject jsonLabels2 = new JSONObject();
        JSONObject jsonStyle4 = new JSONObject();
        jsonStyle4.put("textOverflow", "none");
        jsonStyle4.put("color", "#000000");
        jsonLabels2.put("style", jsonStyle4);
        jsonLabels2.put("reserveSpace", false);
        jsonLabels2.put("align", "left");
        jsonxAxis.put("labels", jsonLabels2);
        json.put("xAxis", jsonxAxis);

        JSONObject jsonChart = new JSONObject();
        jsonChart.put("renderTo", "draw_ober_oracle_plot_plotype4");
        jsonChart.put("type", typeOfPicture);
        jsonChart.put("backgroundColor", "rgba(0,0,0,0)");
        jsonChart.put("plotBackgroundColor", null);
        json.put("chart", jsonChart);

        JSONObject jsonNoData = new JSONObject();
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("color", "#fff");
        jsonNoData.put("style", jsonStyle);
        json.put("noData", jsonNoData);

        JSONObject jsonTitle = new JSONObject();
        jsonTitle.put("text", "");
        json.put("title", jsonTitle);

        if (null == json.get("subtitle")) {
            JSONObject jsonSubtitle = new JSONObject();
            jsonSubtitle.put("text", "");
            json.put("subtitle", jsonSubtitle);
        }

        JSONObject jsonyAxis = new JSONObject();
        jsonyAxis.put("min", 0);
        JSONObject jsonTitle2 = new JSONObject();
        jsonTitle2.put("text", "");
        JSONObject jsonStyle2 = new JSONObject();
        jsonStyle2.put("color", "#dedddd");
        jsonTitle2.put("style", jsonStyle2);
        jsonyAxis.put("title", jsonTitle2);
        json.put("yAxis", jsonyAxis);

        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonStyle3 = new JSONObject();
        jsonStyle3.put("color", "f3f2f2");
        jsonLabels.put("style", jsonStyle3);
        json.put("labels", jsonLabels);

        JSONObject jsonkTooltip = new JSONObject();
        jsonkTooltip.put("headerFormat", "");
        jsonkTooltip.put("pointFormat", "");
        jsonkTooltip.put("tooltip", jsonkTooltip);

        JSONObject jsonLegend = new JSONObject();
        jsonLegend.put("enabled", false);
        json.put("legend", jsonLegend);

        JSONObject jsonPlotOptions = new JSONObject();
        JSONObject jsonBar = new JSONObject();
        jsonBar.put("pointPadding", 0.2);
        jsonBar.put("borderWidth", 0);
        jsonBar.put("events", new JSONObject());
        jsonPlotOptions.put("bar", jsonBar);
        json.put("plotOptions", jsonPlotOptions);

        JSONObject jsonColumn = new JSONObject();
        jsonColumn.put("events", new JSONObject());
        json.put("column", jsonColumn);

        JSONObject jsonCredits = new JSONObject();
        jsonCredits.put("enabled", false);
        json.put("credits", jsonCredits);

        JSONObject jsonExporting = new JSONObject();
        jsonExporting.put("enabled", false);
        map.put("1", json.toJSONString());
        return map;
    }

}
