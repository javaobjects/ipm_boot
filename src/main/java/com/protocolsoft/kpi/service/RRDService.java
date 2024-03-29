/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: RRDService.java
 *创建人: www    创建时间: 2017年9月6日
 */
package com.protocolsoft.kpi.service;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.protocolsoft.kpi.RrdTool;
import com.protocolsoft.kpi.bean.RetainTimeBean;
import com.protocolsoft.kpi.bean.RrdData;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.utils.MathUtlis;

/**
 * @ClassName: RRDService
 * @Description: RRD
 * @author www
 *
 */
class RRDService {

    /**
     * RRD 根目录
     */
    private final static String RRD_PATH = "/data/kpi/ipm/rrd/interfaces/device.2/";
    
    /**
     * RRD 工具类
     */
    private final static RrdTool RRD_TOOL = RrdTool.getInstance();
    
    /**
     * RRD 默认名称
     */
    private final static String RRD_NAME = "data.rrd";
    
    /**
     * RRD实体
     */
    private RrdData data;
    
    /**
     * RRD 名称
     */
    private String rrdName;
    
    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * RRD路径
     */
    private String path;
    
    /**
     * 间隔步长
     */
    private int step;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: 手动创建RRD使用 </p>
     */
    RRDService() { }
    
    /**
     * 
     * <p>Title: 多列rrd</p>
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param path RRD目录
     * @param step 间隔点
     */
    RRDService(long starttime, long endtime, String path, int step) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.path = path;
        this.rrdName = RRD_NAME;
        if (step == 0) {
            this.step = RrdStepService.getStep(starttime);
        } else {
            this.step = step;
        }
        this.init();
    }
    
    /**
     * 
     * <p>Title: 单列rrd(<font color='red'>协议分布功能使用</font>)</p>
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param path RRD路径
     * @param rrdName RRD名称
     */
    RRDService(long starttime, long endtime, String path, String rrdName) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.path = path;
        this.rrdName = rrdName + "_ethernetTraffic.rrd";
        this.step = RrdStepService.getStep(starttime);
        this.init();
    }
    
    /**
     * 
     * <p>Title: 单列rrd(<font color='red'>包大小分布功能使用</font>)</p>
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param path RRD路径
     * @param rrdName RRD名称
     * @param c 无用
     */
    RRDService(long starttime, long endtime, String path, String rrdName, char c) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.path = path;
        this.rrdName = rrdName;
        this.step = RrdStepService.getStep(starttime);
        this.init();
    }
    
    /**
     * 
     * @Title: getAllColumnName
     * @Description: 获取当前文件所有列
     * @return List<String>
     * @author www
     */
    List<String> getAllColumnName() {
        
        return data.getColumns();
    }
    
    /**
     * 
     * @Title: getLastPointByNames
     * @Description: 获取多个kpi最新数据
     * @param kpiNames kpi名称集合
     * @return Map<String,SimpleEntry<Long,Double>> <pre>
     *          String : kpi名称
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    Map<String, SimpleEntry<Long, Double>> getLastPointByNames(List<String> kpiNames) {
        Map<String, SimpleEntry<Long, Double>> map = null;
        List<String> list = data.getColumns();
        if (!list.isEmpty()) {
            map = new HashMap<String, SimpleEntry<Long, Double>>();
            SimpleEntry<Long, Double> entry = null;
            for (int i = 0, len = list.size(); i < len; i++) {
                entry = data.getLastRrdDataByColumnName(list.get(i));
                map.put(list.get(i), entry);
            }
        }

        return map;
    }
    
    /**
     * 
     * @Title: getLastPointByName
     * @Description: 获取单个kpi最新数据
     * @param kpiName kpi名称
     * @return SimpleEntry<Long,Double> <pre>
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    SimpleEntry<Long, Double> getLastPointByName(String kpiName) {
        
        return data.getLastRrdDataByColumnName(kpiName);
    }
    
    /**
     * 
     * @Title: getRrdDataPointByNames
     * @Description: 获取一组KPI值
     * @param kpiNames <pre>
     *      key(kpi名称)       value(对应算法)
     *      ethernetTraffic     SUM  (流量求和)
     *      ethernetTraffic     AVG  (流量求平均) </pre>   
     * @return Map<String,SimpleEntry<Long,Double>> <pre>
     *          String : kpi名称
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    Map<String, SimpleEntry<Long, Double>> getRrdDataPointByNames(Map<String, RrdAlgorithm> kpiNames) {
        Map<String, SimpleEntry<Long, Double>> map = null;
        if (!kpiNames.isEmpty()) {
            map = new HashMap<String, SimpleEntry<Long, Double>>();
            Iterator<String> iter = kpiNames.keySet().iterator();
            SimpleEntry<Long, Double> entry = null;
            String key = null;
            while (iter.hasNext()) {
                key = iter.next();
                entry = this.getRrdDataPointByName(key, kpiNames.get(key));
                map.put(key, entry);
            }
        }
        
        return map;
    }
    
    /**
     * 
     * @Title: getRrdDataPointByName
     * @Description: 时间段内数据
     * @param kpiName KPI名称
     * @param alg 算法
     * @return SimpleEntry<Long,Double> <pre>
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    SimpleEntry<Long, Double> getRrdDataPointByName(String kpiName, RrdAlgorithm alg) {
        SimpleEntry<Long, Double> entry = new SimpleEntry<Long, Double>(endtime, 0D);
        List<SimpleEntry<Long, Double>> list = this.getRrdDataByName(kpiName, alg);
        if (list != null && list.size() != 0) {
            double val = 0;
            int i = 0; 
            int len = list.size();
            for (; i < len; i ++) {
                val += list.get(i).getValue();
            }
            if (alg == RrdAlgorithm.AVG) {
                val = MathUtlis.getDoubleFormatPercent2Places(val / len);
            }
            entry.setValue(val);
        }
        
        return entry;
    }
    
    /**
     * 
     * @Title: getRrdData
     * @Description: 获取RRD数据
     * @param kpiName KPI名称
     * @param alg 算法
     * @return List<SimpleEntry<Long,Double>>  <pre>
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    List<SimpleEntry<Long, Double>> getRrdDataByName(String kpiName, RrdAlgorithm alg) {
        List<SimpleEntry<Long, Double>> list = null;
        if (data != null) {
            if (alg == RrdAlgorithm.SUM && step > 0) {
                list = data.getRrdDataByColumnName(kpiName, step);
            } else {
                list = data.getRrdDataByColumnName(kpiName);
            }
        }

        return list;
    }
    
    /**
     * 
     * @Title: init
     * @Description: RRD实体初始化
     * @author www
     */
    private void init() {
        starttime = starttime - starttime % step;
        long tmp = 0;
        if ((tmp = endtime % step) != 0) {
            endtime = endtime + (step - tmp);
        }
        // 解决最后一个点为零的情况
        if (step > 10) {
            tmp = System.currentTimeMillis() / 1000;
            if (tmp - endtime < step) {
                endtime -= step;
            }
        }
        endtime -= 1;
        data = RRD_TOOL.fetch(starttime, endtime, RRD_PATH + path, rrdName, step);
    }
    
    /**
     * 
     * @Title: createDir
     * @Description: 检测创建文件目录
     * @param path 路径
     * @param step 粒度
     * @param columns 列名
     * @param time 保留时间
     * @throws Exception void
     * @return boolean 是否成功
     * @author www
     */
    boolean createDir(String path, int step, List<String> columns, 
            List<RetainTimeBean> time) throws Exception {
        boolean bool = false;
        char ch = File.separatorChar;
        path = RRD_PATH + path;
        new File(path).mkdirs();
        File file = new File(path + ch + RRD_NAME);
        if (!file.exists()) {
            bool = RRD_TOOL.create(path, RRD_NAME, step, columns, time);
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: updateRrd
     * @Description: 更新数据
     * @param path 目录
     * @param time 更新时间
     * @param values 更新数据
     * @return boolean 是否成功
     * @author www
     */
    boolean updateRrd(String path, long time, List<Number> values) {
        boolean bool = false;
        bool = RRD_TOOL.update(time, RRD_PATH + path, RRD_NAME, values);
        
        return bool;
    }
    
    /**
     * 
     * @Title: dleDir
     * @Description: 删除
     * @param path 目录
     * @return boolean
     * @author www
     */
    boolean dleDir(String path) {
        boolean bool = true;
        new File(RRD_PATH + path).delete();
        
        return bool;
    }
    
    /**
     * 
     * @Title: getProtoRrdNames
     * @Description: 获取某路径下协议rrd名称
     * @param path 路径
     * @return List<String>
     * @author www
     */
    static List<String> getProtoRrdNames(String path) {
        List<String> list = new ArrayList<String>();
        File[] files = new File(RRD_PATH + path).listFiles();
        if (files != null) {
            String filename = null;
            for (int k = 0; k < files.length; k++) {
                filename = files[k].getName();
                if (filename.contains("_ethernetTraffic")) {
                    filename = filename.substring(0, filename.indexOf("_ether"));
                    if (!filename.startsWith("USER")) {
                        list.add(filename);
                    }
                }
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getPktsSizeNames
     * @Description: 获取路径下的包大小名称
     * @param path 路径
     * @return List<String>
     * @author www
     */
    static List<String> getPktsSizeNames(String path) {
        List<String> list = new ArrayList<String>();
        File[] files = new File(RRD_PATH + path).listFiles();
        if (files != null) {
            String filename = null;
            for (int k = 0; k < files.length; k++) {
                filename = files[k].getName();
                if (filename.endsWith("Pkts.rrd")) {
                    list.add(filename);
                }
            }
        }
        
        return list;
    }
}
