/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotService.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.service;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.app.bean.ParamBean;
import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.common.bean.AlarmHeatmapBean;
import com.protocolsoft.common.bean.HeatSimpleBean;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.dao.PlotDao;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.utils.bean.TimeDefaultBean;

/**
 * @ClassName: PlotService
 * @Description: 绘图
 * @author www
 *
 */
@Service
public class PlotService {
    
    /**
     * 告警基线是否展示
     */
    public static boolean alarmBaselineShow;
    
    /**
     * 绘图DAO
     */
    @Autowired
    private PlotDao plotDao;
    
    /**
     * KPI DAO
     */
    @Autowired
    private KpisDao kpisDao;
    
    /**
     * 告警日志
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 报分布名称
     */
    public static Map<String, String> pktsNamezh;
    
    /**
     * 初始化
     */
    static {
        pktsNamezh = new HashMap<String, String>();
        pktsNamezh.put("upTo64Pkts.rrd", "小于64");
        pktsNamezh.put("upTo128Pkts.rrd", "64-128");
        pktsNamezh.put("upTo256Pkts.rrd", "128-256");
        pktsNamezh.put("upTo512Pkts.rrd", "256-512");
        pktsNamezh.put("upTo1024Pkts.rrd", "512-1024");
        pktsNamezh.put("upTo1514Pkts.rrd", "1024-1514");
        pktsNamezh.put("largePkts.rrd", "大于1514");
        
        PropertiesUtil util = new PropertiesUtil("sysset.properties");
        try {
            alarmBaselineShow = "1".equals(util.readProperty("alarm_baseline_show").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: getPlotByModuleId
     * @Description: 获取绘图的映射关系
     * @param moduleId 模块编号
     * @return List<PlotBean>
     * @author www
     */
    public List<PlotBean> getPlotByModuleId(int moduleId) {
        
        return plotDao.getPlotByModuleId(moduleId);
    }
    
    /**
     * 
     * @Title: getPlotByModuleKpiId
     * @Description: 获取绘图选项信息
     * @param moduleId 模块编号
     * @param kpiId KPI编号
     * @return PlotBean
     * @author www
     */
    public PlotBean getPlotByModuleKpiId(int moduleId, int kpiId) {
        
        return plotDao.getPlotByModuleKpiId(moduleId, kpiId);
    }
    
    /**
     * 
     * @Title: getPlotByModuleKpiName
     * @Description: 获取绘图映射关系
     * @param moduleId 模块编号
     * @param kpiName KPI名称
     * @return PlotBaseBean
     * @author www
     */
    public PlotBean getPlotByModuleKpiName(int moduleId, String kpiName) {
        
        return plotDao.getPlotByModuleKpiName(moduleId, kpiName);
    }
    
    /**
     * 
     * @Title: getPlotOptionById
     * @Description: 获取绘图选项信息
     * @param option_id 绘图选项编号
     * @return PlotOptionBean
     * @author www
     */
    public PlotOptionBean getPlotOptionById(int option_id) {
        
        return plotDao.getPlotOptionById(option_id);
    }
    
    /**
     * 
     * @Title: getPlotTypeById
     * @Description: 获取绘图类型信息
     * @param plot_type_id 绘图类型编号
     * @return PlotTypeBean
     * @author www
     */
    public PlotTypeBean getPlotTypeById(int plot_type_id) {
        
        return plotDao.getPlotTypeById(plot_type_id);
    }
    
    /**
     * 
     * @Title: getAllKpis
     * @Description: 获取所有KPI信息
     * @return List<KpisBean>
     * @author www
     */
    public List<KpisBean> getAllKpis() {
        
        return kpisDao.getAllKpis();
    }
    
    /**
     * 
     * @Title: getTopoKpis
     * @Description: 获取拓扑KPI
     * @return List<KpisBean>
     * @author WWW
     */
    public List<KpisBean> getTopoKpis() {
        
        return kpisDao.getTopoKpis();
    }
    
    /**
     * 
     * @Title: getKpisById
     * @Description: 获取KPI信息
     * @param kpiId KPI编号
     * @return KpisBean
     * @author www
     */
    public KpisBean getKpisById(int kpiId) {
        
        return kpisDao.getKpisById(kpiId);
    }
    
    /**
     * 
     * @Title: getKpisByEthernetTraffic
     * @Description: 根据kpi英文名获取KPI信息
     * @return KpisBean
     * @author chensq
     */
    public KpisBean getKpisByEthernetTraffic(){
        
        return kpisDao.getKpisByEthernetTraffic();
    }
    
    /**
     * 
     * @Title: getKpisByPlotId
     * @Description: 根据绘图编号获取kpi信息
     * @param plotId 编号
     * @return KpisBean
     * @author WWW
     */
    public KpisBean getKpisByPlotId(int plotId) {
        PlotOptionBean bean = this.getPlotOptionById(plotId);
        
        return kpisDao.getKpisById(bean.getKpiId());
    }
    
    /**
     * 
     * @Title: getHeatmap
     * @Description: 获取健康图
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param busiId 业务编号
     * @param map 数据容器
     * @param kpisBean KPI信息
     * @author WWW
     */
    public void getHeatmap(long starttime, long endtime, int moduleId, 
        int watchpointId, int busiId, Map<String, Object> map, KpisBean kpisBean) {
        kpisBean = new KpisBean();
        kpisBean.setUnit("num");
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        alarmLogBean.setStarttime(starttime);
        alarmLogBean.setEndtime(endtime);
        alarmLogBean.setModuleId(moduleId);
        if (moduleId == 10) { // 观察点情况
            alarmLogBean.setBusinessId(watchpointId);
        } else {
            alarmLogBean.setWatchpointId(watchpointId);
            alarmLogBean.setBusinessId(busiId);
        }
        List<HeatSimpleBean> heatList = this.getHeatmap(alarmLogBean);
        int size = heatList.size();
        long[] xLabel = new long[size];
        for (int i = 0; i < size; i ++) {
            xLabel[i] = heatList.get(i).getStarttime();
        }
        map.put("data", heatList);
        map.put("yLabel", new int[] {1});
        map.put("xLabel", xLabel);
    }
    
    /**
     * 
     * @Title: getHeatmap
     * @Description: 获取健康图信息
     * @param alarmLogBean
     * @return List<HeatSimpleBean>
     * @author 小王
     */
    public List<HeatSimpleBean> getHeatmap(AlarmLogBean alarmLogBean) {
        List<HeatSimpleBean> data = new ArrayList<HeatSimpleBean>();
        alarmLogBean.setHealthColumn(12);
        List<AlarmHeatmapBean> list = alarmLogService.getMonLogHealthInfo(alarmLogBean);
        if (list != null) {
            AlarmHeatmapBean bean = null;
            HeatSimpleBean sBean = null;
            for (int i = 0, len = list.size(); i < len; i++) {
                bean = list.get(i);
                sBean = new HeatSimpleBean();
                sBean.setStarttime(bean.getStarttime());
                sBean.setEndtime(bean.getEndtime());
                sBean.setModuleId((int) alarmLogBean.getModuleId());
                sBean.setWatchpointId((int) alarmLogBean.getWatchpointId());
                sBean.setBusiId((int) alarmLogBean.getBusinessId());
                sBean.setX(i);
                sBean.setY(0);
                sBean.setLevel(bean.getNum());
                if (bean.getNum() == 0) { // 告警数为0时，告警等级为1，代表健康
                    sBean.setValue(1);
                } else {
                    sBean.setValue(bean.getLevel());
                }
                data.add(sBean);
            }
        }
        
        return data;
    }

    /**
     * @Title: getPlotData
     * @Description: 硬件信息绘图
     * @param bean 参数
     * @return PlotDataBean
     * @author WWW
     */
    public PlotDataBean getPlotData(PlotParamBean bean) {
        PlotDataBean plotDataBean = new PlotDataBean();
        int plotId = bean.getPlotId();
        if (0 == bean.getStarttime() && 0 == bean.getEndtime()) {
            TimeDefaultBean time = DateAppsUtils.getGraphDefaultTime();
            bean.setEndtime(time.getEndtime());
            bean.setStarttime(time.getStarttime());
        }
        PlotOptionBean plotBean = this.getPlotOptionById(plotId);
        PlotTypeBean plotTypeBean = this.getPlotTypeById(bean.getPlotTypeId());
        
        KpisBean kpisBean = this.getKpisById(plotBean.getKpiId());
        BusiKpiService kpiService = new BusiKpiService();
        List<SimpleEntry<Long, Double>> kpiData = kpiService.getRrdDataByName(bean.getStarttime(),
                bean.getEndtime(), bean.getStep(), kpisBean.getName(), RrdAlgorithm.AVG);
        
        List<PlotSimpleBean> data = new ArrayList<PlotSimpleBean>();
        PlotSimpleBean plotSimpleBean = new PlotSimpleBean();
        plotSimpleBean.setName(kpisBean.getDisplayName());
        plotSimpleBean.setValue(kpiData);
        data.add(plotSimpleBean);
        
        plotDataBean.setStarttime(bean.getStarttime());
        plotDataBean.setEndtime(bean.getEndtime());
        plotDataBean.setPlotName(plotBean.getName());
        plotDataBean.setType(plotTypeBean.getName());
        plotDataBean.setUnit(kpisBean.getUnit());
        plotDataBean.setData(data);

        return plotDataBean;
    }
    
    /**
     * 
     * @Title: getAlarmBaseLine
     * @Description: 获取告警基线值
     * @param bean 参数
     * @param kpisBean KPI
     * @param type 高,低阈值
     * @return PlotSimpleBean
     * @author WWW
     */
    public PlotSimpleBean getAlarmBaseLine(ParamBean bean, KpisBean kpisBean, AlarmBaseType type) {
        PlotSimpleBean plotSimpleBean = null;
        if (alarmBaselineShow) {
            plotSimpleBean = new PlotSimpleBean();
            BusiKpiService kpiService = new BusiKpiService(bean.getModuleId(),
                    bean.getBusiId(), bean.getWatchpointId(), type, false);
            List<SimpleEntry<Long, Double>> kpiData = kpiService.getRrdDataByName(bean.getStarttime(),
                    bean.getEndtime(), 0, kpisBean.getName(), RrdAlgorithm.AVG);
            String str = null;
            if (type == AlarmBaseType.HIGH) {
                str = "高阈值";
            } else {
                str = "低阈值";
            }
            plotSimpleBean.setName(str + "基线");
            plotSimpleBean.setValue(kpiData);
            plotSimpleBean.setType("spline");
        }
        
        return plotSimpleBean;
    }
}
