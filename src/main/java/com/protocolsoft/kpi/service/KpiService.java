/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: KpiService.java
 *创建人: WWW    创建时间: 2019年4月30日
 */
package com.protocolsoft.kpi.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * @ClassName: KpiService
 * @Description: KPI
 * @author WWW
 *
 */
@Service
public class KpiService {

    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
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
    public Map<Integer, Map<String, LinkedHashMap<Long, Double>>> getAllKPIData(
            long starttime, long endtime, String kpi) {
        Map<Integer, Map<String, LinkedHashMap<Long, Double>>> data = null;
        List<WatchpointBean> watchpoints = watchpointService.getWatchpointInfo();
        if (watchpoints != null) {
            int size = watchpoints.size();
            if (size > 0) {
                data = new HashMap<>();
                int watchpointId = 0;
                List<String> kpis = this.getRrdKpiName(kpi);
                Map<String, LinkedHashMap<Long, Double>> tmp = null;
                for (WatchpointBean watchpointBean : watchpoints) {
                    watchpointId = watchpointBean.getId();
                    tmp = this.getKPIData(starttime, endtime, watchpointId, kpis);
                    data.put(watchpointId, tmp);
                }
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getKPIData
     * @Description: 获取单观察点数据
     * @param starttime 开始时间
     * @param endtime 解释时间
     * @param watchpointId 观察点
     * @param kpi KPI
     * @return Map<String, LinkedHashMap<Long, Double>>
     * @author WWW
     */
    public Map<String, LinkedHashMap<Long, Double>> getKPIData(
            long starttime, long endtime, int watchpointId, String kpi) {
        List<String> kpis = this.getRrdKpiName(kpi);
        
        return this.getKPIData(starttime, endtime, watchpointId, kpis);
    }
    
    /**
     * 
     * @Title: getKPIData
     * @Description: 获取单观察点数据
     * @param starttime 开始时间
     * @param endtime 解释时间
     * @param watchpointId 观察点
     * @param kpi KPI
     * @return Map<String, LinkedHashMap<Long, Double>>
     * @author WWW
     */
    private Map<String, LinkedHashMap<Long, Double>> getKPIData(
            long starttime, long endtime, int watchpointId, List<String> kpis) {
        Map<String, LinkedHashMap<Long, Double>> data = null;
        if (kpis != null) {
            data = new HashMap<>();
            LinkedHashMap<Long, Double> simpleData = null;
            String name = null;
            for (int i = 0, len = kpis.size(); i < len; i ++) {
                simpleData = null;
                name = kpis.get(i);
                if (!StringUtils.isEmpty(name.trim())) {
                    simpleData = this.getKpiData(starttime, endtime, watchpointId, name);
                }
                data.put(name, simpleData);
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getKpiData
     * @Description: 获取KPI数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param watchpointId 观察点
     * @param rrdMap RRD KPI信息
     * @return LinkedHashMap<Long, Double> 
     * @author WWW
     */
    private LinkedHashMap<Long, Double> getKpiData(long starttime, long endtime, 
            int watchpointId, String kpiName) {
        LinkedHashMap<Long, Double> linkMap = new LinkedHashMap<>();
        BusiKpiService kpiService = new BusiKpiService(watchpointId);
        List<SimpleEntry<Long, Double>> data = kpiService.getRrdDataByName(starttime, endtime, 10, kpiName, RrdAlgorithm.AVG);
        if (data != null) {
            SimpleEntry<Long, Double> entry = null;
            for (int i = 0, len = data.size(); i < len; i ++) {
                entry = data.get(i);
                linkMap.put(entry.getKey(), entry.getValue());
            }
        }
        
        return linkMap;
    }
    
    /**
     * 
     * @Title: getRrdKpiName
     * @Description: 解析kpi名称
     * @param kpis  获取KPI名称
     * @return List<String>
     * @author WWW
     */
    private List<String> getRrdKpiName(String kpis) {
        List<String> data = null;
        if (!StringUtils.isEmpty(kpis)) {
            data = new ArrayList<>();
            String[] strs = kpis.split(",");
            for (int i = 0, len = strs.length; i < len; i ++ ) {
                data.add(strs[i]);
            }
        }
        
        return data;
    }
}
