/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusiKpiService.java
 *创建人: www    创建时间: 2017年11月3日
 */
package com.protocolsoft.kpi.service;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.protocolsoft.kpi.bean.DivideBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.utils.MathUtlis;

/**
 * @ClassName: BusiKpiService
 * @Description: 业务数据
 * @author www
 *
 */
public class BusiKpiService {
    
    /**
     * 标识标量
     */
    private final static char CH = '1';
    
    /**
     * 路径分隔符
     */
    private final static String SC = File.separator;
    
    /**
     * 流量KPI
     */
    private final static Map<String, Character> TRAFFIC_KPI = new HashMap<String, Character>() { 
        private static final long serialVersionUID = 1L;
        {
            put("ethernetTraffic", CH);       put("tcpTraffic", CH);           put("udpTraffic", CH);
            put("unKnowSerTraffic", CH);      put("unKnowCliTraffic", CH);     put("arpTraffic", CH);
            put("unknowUserTraffic", CH);     put("unknowPublicTraffic", CH);  put("bandWidth", CH);
            put("inTraffic", CH);             put("outTraffic", CH);
        }
    };
    
    /**
     * 需要进一法的KPI
     */
    private final static Map<String, Character> NUM_KPI = new HashMap<String, Character>() { 
        private static final long serialVersionUID = 1L;
        {
            put("sessionNum", CH);          put("rstPkts", CH);       put("synPkts", CH);
            put("finPkts", CH);             put("netPktLost", CH);    put("serverPktLost", CH);
            put("serverPkt", CH);           put("clientPktLost", CH); put("clientPkt", CH);
            put("l7SessionCountTotal", CH); put("http400Count", CH);  put("noRespCount", CH);
            put("http500Count", CH);        put("zeroWinCount", CH);  put("transCount", CH);
            put("failCount", CH);           put("synAckPkts", CH);    put("fin1Pkts", CH);
            put("fin2Pkts", CH);            put("url400Count", CH);
        }
    };
    
    /**
     * 需要变大100倍的KPI
     */
    private final static Map<String, Character> RATIO_KPI = new HashMap<String, Character>() { 
        private static final long serialVersionUID = 1L;
        {
            put("netPktLostRatio", CH);  put("serverPktLostRatio", CH);   put("clientPktLostRatio", CH);
            put("connRatio", CH);        put("rstRatio", CH);             put("finRatio", CH);
            put("tinyPktsRatio", CH);    put("bandWidthRatio", CH);       put("failRespRatio", CH);
            put("successRatio", CH);     put("respRatio", CH);            put("noRespRatio", CH);
        } 
    };
    
    /**
     * java二次计算比率类KPI
     */
    private final static Map<String, DivideBean> JAVA_RATIO_KPI = new HashMap<String, DivideBean>() { 
        private static final long serialVersionUID = 1L;
        {
            put("netPktLostRatio", new DivideBean("netPktLost", "tcpPkts"));
            put("serverPktLostRatio", new DivideBean("serverPktLost", "serverPkt"));
            put("clientPktLostRatio", new DivideBean("clientPktLost", "clientPkt"));
            put("tinyPktsRatio", new DivideBean("tinyPkts", "ethernetPkts"));
            put("bandWidthRatio", new DivideBean("ethernetTraffic", "bandWidth"));
            put("avgPktsLen", new DivideBean("ethernetTraffic", "ethernetPkts"));
            // HTTP
            put("failRespRatio", new DivideBean("http400Count", "l7SessionCountTotal"));
            put("noRespRatio", new DivideBean("noRespCount", "l7SessionCountTotal"));
        }
    };

    /**
     * RRD路径
     */
    private String[] path;
    
    /**
     * 粒度
     */
    private int moduleId;
    
    /**
     * 
     * <p>Title: 硬件信息</p>
     * <p>Description: 获取硬件信息RRD初始化</p>
     */
    public BusiKpiService() {
        path = new String[] { "system" };
    }
    
    /**
     * 
     * <p>Title: 远端系统消耗</p>
     * <p>Description: 获取远端系统消耗RRD初始化</p>
     * @param wpath
     */
    public BusiKpiService(String wpath) {
        path = new String[] { SC + wpath + SC + "system"};
    }
    
    /**
     * 
     * <p>Title: 观察点</p>
     * <p>Description: 获取观察点数据，初始化</p>
     * @param watchpointId 观察点ID
     */
    public BusiKpiService(int watchpointId) {
        path = new String[] { "watchPoint" + SC + watchpointId };
    }
    
    /**
     * 
     * <p>Title: 客户端、服务端</p>
     * <p>Description: 获取观察点下客户端、服务端数据使用</p>
     * @param watchpointId 观察点ID
     * @param id 客户端ID 或 服务端ID
     */
    public BusiKpiService(int watchpointId, int id) {
        path = new String[] { "watchPoint" + SC + watchpointId + SC + "entity" + id };
    }
    
    /**
     * 
     * <p>Title: 客户端下服务端、服务端下客户端</p>
     * <p>Description: 获取观察点下客户端下服务端、服务端下客户端数据使用</p>
     * @param watchpointId 观察点ID
     * @param firstId 客户端ID 或 服务端ID
     * @param secondId 客户端ID 或 服务端ID
     */
    public BusiKpiService(int watchpointId, int firstId, int secondId) {
        path = new String [] { "watchPoint" + SC + watchpointId + SC 
                + "entity" + firstId + SC + "entity" + secondId };
    }
    
    /**
     * 
     * <p>Title: BSS端 </p>
     * <p>Description: 获取BSS端下RRD数据使用</p>
     * @param watchpointId 观察点ID
     * @param ips IP、端口集合
     */
    public BusiKpiService(int watchpointId, List<AppIpPortBean> ips) {
        int size = ips.size();
        path = new String[size];
        AppIpPortBean bean = null;
        String tmp = "watchPoint" + SC + watchpointId + SC;
        for (int i = 0; i < size; i ++) {
            bean = ips.get(i);
            if (bean.getIp() == null) {
                path[i] = tmp + "ports" + SC + bean.getPort();
            } else {
                path[i] =  tmp + "hosts" + SC + bean.getIp().replace(".", SC);
                if (bean.getPort() != null) {
                    path[i] += (SC + bean.getPort());
                }
            }
        }
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: 只限URL使用</p>
     * @param prefix 前缀
     * @param watchpointId 观察点编号
     * @param busiId 业务编号
     * @param index url编号
     */
    public BusiKpiService(String prefix, int watchpointId, int busiId, int index) {
        this.moduleId = 8;
        path = new String [] { prefix + SC + watchpointId + SC + busiId + SC + index };
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: 只限报文使用</p>
     * @param watchpointId 观察点
     * @param msgId 报文
     * @param ismsg 无用参数，写死就好
     */
    public BusiKpiService(int watchpointId, int msgId, boolean ismsg) {
        this.moduleId = 9;
        path = new String[] { "watchPoint" + SC + watchpointId + SC + "msg" + msgId };
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: 只限告警基线使用</p>
     * @param moduleId 模块编号
     * @param busiId 业务编号
     * @param watchpointId 观察点编号
     * @param type 基线类型
     * @param isFinBase 是否为最终基线
     * 
     */
    public BusiKpiService(int moduleId, int busiId, int watchpointId, AlarmBaseType type, boolean isFinBase) {
        this.moduleId = moduleId;
        path = new String[] { "ALARM" + SC + moduleId + SC + busiId + SC + watchpointId + SC + type.ordinal() + SC + (isFinBase ? 1 : 0) };
    }
    
    /**
     * 
     * @Title: getRrdDataByName
     * @Description: 获取RRD数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param step 粒度
     * @param kpiName KPI名称
     * @param alg 算法
     * @return List<SimpleEntry<Long,Double>>
     * @author www
     */
    public List<SimpleEntry<Long, Double>> getRrdDataByName(long starttime, 
            long endtime, int step, String kpiName, RrdAlgorithm alg) {
        step = RrdStepService.getStep(moduleId, starttime, step);
        List<RRDService> list = this.getRrdList(starttime, endtime, step);
        
        return this.getDataByRrds(list, kpiName, alg);
    }
    
    /**
     * 
     * @Title: getRrdDataPointByName
     * @Description: 时间段内求数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param kpiName KPI名称
     * @param alg 算法
     * @return SimpleEntry<Long,Double> <pre>
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    public SimpleEntry<Long, Double> getRrdDataPointByName(long starttime, 
            long endtime, String kpiName, RrdAlgorithm alg) {
        int step = RrdStepService.getStep(moduleId, starttime, 0);
        List<RRDService> list = this.getRrdList(starttime, endtime, step);
        
        return this.getPointDataByRrds(endtime, list, kpiName, alg);
    }
    
    /**
     * 
     * @Title: getRrdDataPointByName
     * @Description: 时间段内求数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param step 粒度
     * @param kpiName KPI名称
     * @param alg 算法
     * @return SimpleEntry<Long,Double> <pre>
     *          Long   : 秒级时间戳
     *          Double : 对应数据 </pre>
     * @author www
     */
    public SimpleEntry<Long, Double> getRrdDataPointByName(long starttime, 
            long endtime, int step, String kpiName, RrdAlgorithm alg) {
        List<RRDService> list = this.getRrdList(starttime, endtime, step);
        
        return this.getPointDataByRrds(endtime, list, kpiName, alg);
    }
    
    /**
     * 
     * @Title: getRrdDataPointByNames
     * @Description: 获取一组KPI值
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param kpiNames <pre>
     *      key(kpi名称)       value(对应算法)
     *      ethernetTraffic     SUM  (流量求和)
     *      ethernetTraffic     AVG  (流量求平均) </pre>   
     * @return Map<String, Double> <pre>
     *          String : kpi名称
     *          Double : 对应数据 </pre>
     * @author www
     */
    public Map<String, Object> getRrdDataPointByNames(
            long starttime, long endtime, Map<String, RrdAlgorithm> kpiNames) {
        Map<String, Object> map = null;
        if (!kpiNames.isEmpty()) {
            int step = RrdStepService.getStep(moduleId, starttime, 0);
            List<RRDService> list = this.getRrdList(starttime, endtime, step);
            map = new HashMap<String, Object>();
            Iterator<String> iter = kpiNames.keySet().iterator();
            SimpleEntry<Long, Double> entry = null;
            RrdAlgorithm alg = null;
            String key = null;
            while (iter.hasNext()) {
                key = iter.next();
                alg = kpiNames.get(key);
                if ("load_5".equals(key)) {
                    BusiKpiService service = new BusiKpiService(this.path[0]);
                    entry = service.getRrdDataPointByName(starttime, endtime, key, alg);
                } else {
                    entry = this.getPointDataByRrds(endtime, list, key, alg);
                }
                map.put(key, entry.getValue());
            }
        }
        
        return map;
    }
    
    /**
     * 
     * @Title: getPublicProtoRrdData
     * @Description: 获取观察点下所有共有协议rrd文件数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @return List<String>
     * @author www
     */
    public List<SimpleEntry<String, Double>> getPublicProtoRrdData(long starttime, long endtime, String kpiName) {
        List<SimpleEntry<String, Double>> list = new ArrayList<SimpleEntry<String, Double>>();
        String rrdPath = path[0];
        List<String> names = RRDService.getProtoRrdNames(rrdPath);
        RRDService rrd = null;
        double sum = 0;
        SimpleEntry<String, Double> entry = null;
        for (int i = 0, j = names.size(); i < j; i++) {
            rrd = new RRDService(starttime, endtime, rrdPath, names.get(i));
            sum = rrd.getRrdDataPointByName(kpiName, RrdAlgorithm.SUM).getValue();
            if (sum != 0) {
                entry = new SimpleEntry<String, Double>(names.get(i), sum);
                list.add(entry);
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getUserProtoRrdData
     * @Description: 获取观察点下所有自定义协议rrd文件数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param appProtoName 自定义名称('USER' + id)
     * @param kpiName KPI名称
     * @return SimpleEntry<String,Double>
     * @author www
     */
    public SimpleEntry<String, Double> getUserProtoRrdData(long starttime, 
            long endtime, String appProtoName, String kpiName) {
        SimpleEntry<String, Double> entry = null;
        RRDService rrd = new RRDService(starttime, endtime, path[0], appProtoName);
        double sum = rrd.getRrdDataPointByName(kpiName, RrdAlgorithm.SUM).getValue();
        if (sum != 0) {
            entry = new SimpleEntry<String, Double>(appProtoName, sum);
        }
        
        return entry;
    }
    
    /**
     * 
     * @Title: getPktsSizeDistri
     * @Description: 获取包大小分布
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param kpiName KPI名称
     * @return List<SimpleEntry<String,Double>>
     * @author www
     */
    public List<SimpleEntry<String, Double>> getPktsSizeDistri(long starttime, long endtime, String kpiName) {
        List<SimpleEntry<String, Double>> list = new ArrayList<SimpleEntry<String, Double>>();
        String rrdPath = path[0];
        List<String> names = RRDService.getPktsSizeNames(rrdPath);
        RRDService rrd = null;
        double sum = 0;
        SimpleEntry<String, Double> entry = null;
        for (int i = 0, j = names.size(); i < j; i++) {
            rrd = new RRDService(starttime, endtime, rrdPath, names.get(i), 'A');
            sum = rrd.getRrdDataPointByName(kpiName, RrdAlgorithm.SUM).getValue();
            entry = new SimpleEntry<String, Double>(names.get(i), sum);
            list.add(entry);
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getRrdList
     * @Description: 获取所有PATH对应的RRD
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param step 粒度
     * @return List<RRDService>
     * @author www
     */
    private List<RRDService> getRrdList(long starttime, long endtime, int step) {
        // 存放RRD集合
        List<RRDService> list = new ArrayList<RRDService>();
        // RRD业务临时变量
        RRDService rrd = null;
        // 循环获取RRD数据，如果PATH为多个
        for (int i = 0, len = path.length; i < len; i ++) {
            rrd = new RRDService(starttime, endtime, path[i], step);
            list.add(rrd);
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getPointDataByRrds
     * @Description: 获取单点数据
     * @param time 结束时间
     * @param rrds 数据源
     * @param kpiName KPI名称
     * @param alg 算法
     * @return SimpleEntry<Long,Double>
     * @author WWW
     */
    private SimpleEntry<Long, Double> getPointDataByRrds(long time, 
            List<RRDService> rrds, String kpiName, RrdAlgorithm alg) {
        // 最终返回结果
        SimpleEntry<Long, Double> entry = new SimpleEntry<Long, Double>(time, 0D);
        // 最终数值容器
        double val = 0;
        // 多点数据最终算法
        RrdAlgorithm finAlg = RrdAlgorithm.SUM;
        if (JAVA_RATIO_KPI.containsKey(kpiName)) { // 比率类二次计算
            DivideBean bean = JAVA_RATIO_KPI.get(kpiName);
            double valdend = this.manyPointToSimple(rrds, bean.getDividend(), RrdAlgorithm.SUM, finAlg);
            double valsor = this.manyPointToSimple(rrds, bean.getDivisor(), RrdAlgorithm.SUM, finAlg);
            if (valsor != 0) {
                val = valdend / valsor;
                if ("avgPktsLen".equals(kpiName)) { // 平均包长单位为B
                    val /= 8;
                }
                val = this.kpiCalcul(kpiName, val);
            }
        } else {
            if (alg == RrdAlgorithm.AVG) {
                // 如果RRD是平均值状态下，流量类或者数量类是时间段内累加数据。
                boolean isSum = TRAFFIC_KPI.containsKey(kpiName) || NUM_KPI.containsKey(kpiName);
                finAlg = isSum ? RrdAlgorithm.SUM : RrdAlgorithm.AVG;
            }
            if (moduleId != 8 && "sessionNum".equals(kpiName)) { // 列表中会话数量拿最大，最大会话数量
                finAlg = RrdAlgorithm.MAX;
            }
            val = this.manyPointToSimple(rrds, kpiName, alg, finAlg);
            // 累计时，需要特殊处理。
            if (alg == RrdAlgorithm.SUM) {
                if (moduleId == 8) { // URL
                    val = Math.round(val);
                } else {
                    val = Math.ceil(val);
                }
            } else {
                val = this.kpiCalcul(kpiName, val);
            }
        }
        
        // 对象赋值
        entry.setValue(val);
        
        return entry;
    }
    
    /**
     * 
     * @Title: getDataByRrds
     * @Description: 获取RRD数据
     * @param rrds RRD集合
     * @param kpiName KPI名称
     * @param alg 算法
     * @return List<SimpleEntry<Long,Double>>
     * @author WWW
     */
    private List<SimpleEntry<Long, Double>> getDataByRrds(List<RRDService> rrds, String kpiName, RrdAlgorithm alg) {
        List<SimpleEntry<Long, Double>> data = null;
        SimpleEntry<Long, Double> entry = null;
        if (JAVA_RATIO_KPI.containsKey(kpiName)) { // 二次计算KPI
            DivideBean bean = JAVA_RATIO_KPI.get(kpiName);
            data = this.getSimpleKipDataByRrds(rrds, bean.getDividend(), RrdAlgorithm.SUM);
            if (data != null) {
                List<SimpleEntry<Long, Double>> divisor = this.getSimpleKipDataByRrds(rrds, bean.getDivisor(), RrdAlgorithm.SUM);
                double val = 0;
                for (int i = 0, len = data.size(); i < len; i ++) {
                    entry = data.get(i);
                    val = divisor.get(i).getValue();
                    if (val == 0) {
                        entry.setValue(val);
                    } else {
                        val = entry.getValue() / divisor.get(i).getValue();
                        if ("avgPktsLen".equals(kpiName)) { // 平均包长单位为B
                            val /= 8;
                        }
                        entry.setValue(kpiCalcul(kpiName, val));
                    }
                }
            }
        } else {
            data = this.getSimpleKipDataByRrds(rrds, kpiName, alg);
            if (data != null) {
                for (int i = 0, len = data.size(); i < len; i ++) {
                    entry = data.get(i);
                    entry.setValue(kpiCalcul(kpiName, entry.getValue()));
                }
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getSimpleKipDataByRrds
     * @Description: 获取单个KPI RRD数据(数量类的KPI小数进一)
     * @param rrds RRD集合
     * @param kpiName KPI名称
     * @param alg 算法
     * @return List<SimpleEntry<Long,Double>>
     * @author www
     */
    private List<SimpleEntry<Long, Double>> getSimpleKipDataByRrds(List<RRDService> rrds, String kpiName, RrdAlgorithm alg) {
        // 返回值以及第一条数据值
        List<SimpleEntry<Long, Double>> list = null;
        // 所有RRD数据值
        List<List<SimpleEntry<Long, Double>>> data = new ArrayList<List<SimpleEntry<Long, Double>>>();
        // RRD业务临时变量
        RRDService rrd = null;
        // 循环获取RRD数据，如果PATH为多个 
        int rrdSize = rrds.size();
        for (int i = 0; i < rrdSize; i ++) {
            rrd = rrds.get(i);
            list = rrd.getRrdDataByName(kpiName, alg);
            if (list != null) {
                data.add(list);
            }
        }
        // 有多少条RRD数据
        int dataSize = data.size();
        if (dataSize > 0) {
            // 是否为平均值
            boolean isAvg = (alg == RrdAlgorithm.AVG);
            // 第一条数据值
            list = data.get(0);
            // RRD一条数据临时变量
            List<SimpleEntry<Long, Double>> tmp = null;
            // 每条数据中单个点临时变量
            SimpleEntry<Long, Double> entryTmp = null;
            // 多条RRD数据，进行数据业务
            for (int i = 1; i < dataSize; i ++) {
                tmp = data.get(i);
                if (tmp != null && list != null) {
                    for (int j = 0, jlen = list.size(); j < jlen; j ++) {
                        entryTmp = list.get(j);
                        // 每个条数据对应时间点进行相加
                        entryTmp.setValue(entryTmp.getValue() + tmp.get(j).getValue());
                    }
                }
            }
            // 多条数据并且为平均算法
            if (isAvg && list != null) {
                boolean isSum = TRAFFIC_KPI.containsKey(kpiName) || NUM_KPI.containsKey(kpiName);
                for (int i = 0, len = list.size(); i < len; i ++) {
                    entryTmp = list.get(i);
                    if (isSum) {
                        entryTmp.setValue(entryTmp.getValue());
                    } else {
                        entryTmp.setValue(entryTmp.getValue() / rrdSize);
                    }
                }
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: manyPointToSimple
     * @Description: 多点数据计算为单点数据
     * @param rrds RRD集合
     * @param kpiName KPI名称
     * @param rrdAlg RRD算法
     * @param finAlg 返回数据算法
     * @return double
     * @author WWW
     */
    private double manyPointToSimple(List<RRDService> rrds, String kpiName, RrdAlgorithm rrdAlg, RrdAlgorithm finAlg) {
        double val = 0;
        List<SimpleEntry<Long, Double>> data = this.getSimpleKipDataByRrds(rrds, kpiName, rrdAlg);
        if (data != null) {
            int count = data.size();
            if (count != 0) {
                if (finAlg == RrdAlgorithm.MAX) {
                    double tmp = 0;
                    for (int i = 0; i < count; i ++) {
                        tmp = data.get(i).getValue();
                        if (val < tmp) {
                            val = tmp;
                        }
                    }
                } else {
                    for (int i = 0; i < count; i ++) {
                        val += data.get(i).getValue();
                    }
                    if (finAlg == RrdAlgorithm.AVG) {
                        val = val / count;
                    }
                }
            }
        }
        
        return val;
    }
    
    /**
     * 
     * @Title: kpiCalcul
     * @Description: 根据KPI名称进行不同计算
     * @param kpiName KPI名称
     * @param val 值
     * @return double 计算后的值
     * @author www
     */
    private double kpiCalcul(String kpiName, double val) {
        if (NUM_KPI.containsKey(kpiName)) { // 单位为个数KPI
            val = Math.ceil(val);
        } else if (RATIO_KPI.containsKey(kpiName)) { // 单位百分比KPI
            val = val * 100;
        }
        val = MathUtlis.getDoubleFormatPercent2Places(val);
        
        return val;
    }
}
