/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: KpiController.java
 *创建人: WWW    创建时间: 2019年4月30日
 */
package com.protocolsoft.kpi.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.kpi.service.KpiService;

/**
 * @ClassName: KpiController
 * @Description: KPI
 * @author WWW
 *
 */
@Controller
@RequestMapping("/kpiSource")
public class KpiController {
    
    /**
     * 数据推送
     */
    @Autowired
    private KpiService service;
    
    /**
     * 
     * @Title: getAllKPIData
     * @Description: 获取所有观察点数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param kpi KPI
     * @return Map<Integer, Map<String, LinkedHashMap<Long, Double>>>
     * @author WWW
     */
    @RequestMapping("/AllKPIDataNewDB.do")
    @ResponseBody
    public Map<Integer, Map<String, LinkedHashMap<Long, Double>>> getAllKPIData(
            long starttime, long endtime, String kpi) {
        
        return service.getAllKPIData(starttime, endtime, kpi);
    }
    
    /**
     * 
     * @Title: getKPIData
     * @Description: 获取单观察点数据
     * @param starttime 开始时间
     * @param endtime 解释时间
     * @param userId 观察点
     * @param kpi KPI
     * @return Map<String, LinkedHashMap<Long, Double>>
     * @author WWW
     */
    @RequestMapping("/KPIDataNewDB.do")
    @ResponseBody
    public Map<String, LinkedHashMap<Long, Double>> getKPIData(
            long starttime, long endtime, int userId, String kpi) {
        
        return service.getKPIData(starttime, endtime, userId, kpi);
    }
}
