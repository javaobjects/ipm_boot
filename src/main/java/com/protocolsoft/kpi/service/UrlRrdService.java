/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlRrdService.java
 *创建人: www    创建时间: 2018年3月8日
 */
package com.protocolsoft.kpi.service;

import java.util.AbstractMap.SimpleEntry;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.protocolsoft.kpi.bean.RetainTimeBean;
import com.protocolsoft.kpi.bean.UrlRrdParamBean;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.watchpoint.bean.WatchpointBean;

/**
 * @ClassName: UrlRrdService
 * @Description: URL RRD
 * @author www
 *
 */
public class UrlRrdService {
    
    /**
     * URL RRD根
     */
    private final static String PREFIX = "URL";
    
    /**
     * RRD
     */
    private RRDService service;
    
    /**
     * RRD列
     */
    private static List<String> columns;
    
    /**
     * RRD粒度保留时间
     */
    private static List<RetainTimeBean> time;
    
    /**
     * 静态初始化
     */
    static {
        columns = new ArrayList<String>();
        columns.add("ethernetTraffic");
        columns.add("sessionNum");
        columns.add("responseDelay");
        columns.add("pageloadDelay");
        columns.add("rtt");
        columns.add("url400Count");
        columns.add("http500Count");
        columns.add("http400Count");
        columns.add("l7SessionCountTotal");
        
        int[][] tmp = new int[][] {
            {60, 1296000}, {600, 604800}, 
            {3600, 7776000}, {86400, 31536000}
        };
        time = new ArrayList<RetainTimeBean>();
        RetainTimeBean bean = null;
        for (int i = 0, len = tmp.length; i < len; i ++) {
            bean = new RetainTimeBean();
            bean.setStep(tmp[i][0]);
            bean.setTime(tmp[i][1]);
            time.add(bean);
        }
    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public UrlRrdService() {
        service = new RRDService();
    }
    
    /**
     * 
     * @Title: create
     * @Description: 创建RRD
     * @param watchpointId 观察点编号
     * @param busiId 业务编号
     * @param index 下标
     * @return boolean
     * @author www
     */
    public boolean create(int watchpointId, int busiId, int index) {
        boolean bool = false;
        try {
            char ch = File.separatorChar;
            String path = PREFIX + ch + watchpointId + ch + busiId + ch + index;
            bool = service.createDir(path, 60, columns, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: update
     * @Description: 更新
     * @param time 时间
     * @param watchpointId 观察点编号
     * @param busiId 业务编号
     * @param index 下标
     * @param values 数据
     * @return boolean 是否成功
     * @author www
     */
    public boolean update(long time, int watchpointId, 
            int busiId, int index, List<Number> values) {
        char ch = File.separatorChar;
        String path = PREFIX + ch + watchpointId + ch + busiId + ch + index;
        return service.updateRrd(path, time, values);
    }
    
    /**
     * 
     * @Title: deleteFile
     * @Description: 删除RRD文件
     * @param wpList 观察点
     * @param busiId 业务编号
     * @return boolean
     * @author www
     */
    public boolean deleteFile(List<WatchpointBean> wpList, int busiId) {
        char ch = File.separatorChar;
        String path = null;
        for (int i = 0, len = wpList.size(); i < len; i ++) {
            path = PREFIX + ch + wpList.get(i).getId() + ch + busiId;
            service.dleDir(path);
        }
        
        return true;
    }
    
    /**
     * 
     * @Title: getRrdDataByName
     * @Description: 获取RRD数据
     * @param bean 参数
     * @param kpiName KPI名称
     * @return List<SimpleEntry<Long,Double>>
     * @author www
     */
    public List<SimpleEntry<Long, Double>> getRrdDataByName(UrlRrdParamBean bean, String kpiName) {
        BusiKpiService kpiService = new BusiKpiService(PREFIX, 
            bean.getWatchpointId(), bean.getBusiId(), bean.getSubId());
        
        return kpiService.getRrdDataByName(bean.getStarttime(), 
                bean.getEndtime(), bean.getStep(), kpiName, bean.getAlg());
    }
    
    /**
     * 
     * @Title: getRrdDataPointByName
     * @Description: 时间段内求数据
     * @param bean 参数
     * @param kpiName KPI名称
     * @return SimpleEntry<Long,Double>
     * @author www
     */
    public SimpleEntry<Long, Double> getRrdDataPointByName(UrlRrdParamBean bean, String kpiName) {
        BusiKpiService kpiService = new BusiKpiService(PREFIX, 
                bean.getWatchpointId(), bean.getBusiId(), bean.getSubId());
        
        return kpiService.getRrdDataPointByName(bean.getStarttime(), 
                bean.getEndtime(), kpiName, bean.getAlg());
    }
    
    /**
     * 
     * @Title: getUrlKpiService
     * @Description: 时间段内求rrd
     * @param bean 参数
     * @param kpiName KPI名称
     * @return BusiKpiService
     * @author chensq
     */
    public BusiKpiService getUrlKpiService(UrlRrdParamBean bean, String kpiName) {
        BusiKpiService kpiService = new BusiKpiService(PREFIX, 
                bean.getWatchpointId(), bean.getBusiId(), bean.getSubId());
        
        return kpiService;
    }
    
    /**
     * 
     * @Title: getRrdDataPointByNames
     * @Description: 获取一组KPI值
     * @param bean 参数
     * @param kpiNames 
     * @return Map<String, Object>
     * @author www
     */
    public Map<String, Object> getRrdDataPointByNames(
            UrlRrdParamBean bean, Map<String, RrdAlgorithm> kpiNames) {
        BusiKpiService kpiService = new BusiKpiService(PREFIX, 
                bean.getWatchpointId(), bean.getBusiId(), bean.getSubId());
        
        return kpiService.getRrdDataPointByNames(
                bean.getStarttime(), bean.getEndtime(), kpiNames);
    }
}
