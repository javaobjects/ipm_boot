/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: TimerTask.java
 *创建人: wangjianmin    创建时间: 2018年6月20日
 */
package com.protocolsoft.datapush.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.datapush.bean.BusinesBean;
import com.protocolsoft.datapush.bean.BusinesKpiBean;
import com.protocolsoft.datapush.service.BusinesService;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: TimerTask
 * @Description: 定时推送任务
 * @author wangjianmin
 *
 */
@Component
public class TimerDataPushTask {   
    
    /**
     * BusinesService
     */
    @Autowired(required = false)
    private BusinesService  businesService;
    
    /**
     * WatchpointDao 对象
     */
    @Autowired
    private WatchpointDao dao;
    
    /**
     * ipm_app_business表Dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    
    /**
     * 
     * @Title: dataPush
     * @Description: 开始执行推送
     * @author wangjianmin
     */
    @Scheduled(cron = "0 */1 * * * ?")//每隔10秒执行一次 
    public void dataPush(){
        //所有推送设置
        List<BusinesBean> list  =  businesService.getAll();
        for (BusinesBean businesBean : list) {
            businesDataPush(businesBean);
        }   
    }
    
    /**
     * 
     * @Title: businesDataPush
     * @Description: 处理数据推送 kpi的 rrd 数据
     * @param bean 接收没条数据推送设置
     * @author wangjianmin
     */
    public void businesDataPush(BusinesBean bean){
        
        //kafka 配置
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "-1");
        props.put("producer.type", "async");
        props.put("metadata.broker.list", bean.getBrokerIp() + ":" + bean.getPort());  
        final kafka.javaapi.producer.Producer<String, byte[]> producer  
            = new kafka.javaapi.producer.Producer<String, byte[]>(new ProducerConfig(props));
        
        long starttime = 0 ; //开始时间
        long endtime = 0 ; // 结束时间
        if(bean.getGranularity().equals("10")){ //十秒
            TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime();
            starttime =timeDefaultBean.getStarttime(); // 开始时间 
            endtime = timeDefaultBean.getEndtime(); //结束时间
        }
        //所有观察点业务数据
        List<WatchpointBean> gList = dao.getFindAll();
        
        //mapList 作用：最终返回的数据集合
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        
        Map<String, RrdAlgorithm> rrdMap = null; //kpi 名称集合
        Map<String, Object> resultMap = null; //rrd 数据集合
        BusiKpiService kpiService = null;  //rrd 接口Service
        List<AppBusinessBean> busiList = null; //客户端和服务端的业务集合
        switch (bean.getModuleType()) { //判断模块  10 观察点  11 客户端 12 服务端
            case 10://观察点
                if(bean.getBusiId() == 0){ //全部业务
                    for (WatchpointBean watchpointBean : gList) {
                        mapList = new ArrayList<Map<String, Object>>();
                        rrdMap = getRrdMap(10, bean.getKpiIds());
                        kpiService = new BusiKpiService(watchpointBean.getId());
                        resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                        resultMap.put("id", watchpointBean.getId());
                        resultMap.put("name", watchpointBean.getName());
                        mapList.add(resultMap);
                        producer.send(new KeyedMessage<String, byte[]>(bean.getTopic(), mapList.toString().getBytes()));  
                    }
                }else{
                    for (WatchpointBean watchpointBean : gList) { //单个业务
                        if(bean.getBusiId() == watchpointBean.getId()){
                            mapList = new ArrayList<Map<String, Object>>();
                            rrdMap = getRrdMap(10, bean.getKpiIds());
                            kpiService = new BusiKpiService(watchpointBean.getId());
                            resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                            resultMap.put("id", watchpointBean.getId());
                            resultMap.put("name", watchpointBean.getName());
                            mapList.add(resultMap);
                            producer.send(new KeyedMessage<String, byte[]>(bean.getTopic(), mapList.toString().getBytes()));  
                        }
                    }
                }
                break;
            case 11:
            case 12:
                busiList = appBusinessDao.selectAppBusinessesByModuleId(bean.getModuleType());
                rrdMap = getRrdMap(bean.getModuleType(), bean.getKpiIds());
                if(bean.getWatchpointId() == 0){  //全部观察点
                    for (WatchpointBean watchpointBean : gList) {
                        mapList = new ArrayList<Map<String, Object>>();
                        for (AppBusinessBean appBusinessBean : busiList) {
                            if(bean.getBusiId() == 0){ //全部服务端或客户端业务
                                kpiService = new BusiKpiService(watchpointBean.getId(), appBusinessBean.getId());
                                resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                            }else{
                                if(bean.getBusiId() == appBusinessBean.getId()){ //一个服务端或客户端业务
                                    kpiService = new BusiKpiService(watchpointBean.getId(), appBusinessBean.getId());
                                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                                }
                            }
                            resultMap.put("id", appBusinessBean.getId());
                            resultMap.put("name", appBusinessBean.getName());
                            resultMap.put("watchpointName", watchpointBean.getName());
                            mapList.add(resultMap);
                            producer.send(new KeyedMessage<String, byte[]>(bean.getTopic(), mapList.toString().getBytes()));  
                        }
                    }
                }else{
                    for (WatchpointBean watchpointBean : gList) {
                        if(watchpointBean.getId() == bean.getWatchpointId()){//一个观察点
                            for (AppBusinessBean appBusinessBean : busiList) {
                                if(bean.getBusiId() == 0){//全部服务端或客户端业务
                                    kpiService = new BusiKpiService(watchpointBean.getId(), appBusinessBean.getId());
                                    resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                                }else{
                                    if(bean.getBusiId() == appBusinessBean.getId()){  //一个服务端或客户端业务
                                        kpiService = new BusiKpiService(watchpointBean.getId(), appBusinessBean.getId());
                                        resultMap = kpiService.getRrdDataPointByNames(starttime, endtime, rrdMap);
                                    }
                                }
                                resultMap.put("id", appBusinessBean.getId());   //业务id 
                                resultMap.put("name", appBusinessBean.getName()); //业务名称
                                resultMap.put("watchpointName", watchpointBean.getName()); //观察点名称
                                mapList.add(resultMap);
                                producer.send(new KeyedMessage<String, byte[]>(bean.getTopic(), mapList.toString().getBytes()));  
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * 
     * @Title: getRrdMap
     * @Description: 封装kpi rrd数据格式
     * @param moduleId 模块id
     * @return Map<String,RrdAlgorithm>
     * @author wangjianmin
     */
    public Map<String, RrdAlgorithm> getRrdMap(Integer moduleId, String kpi){
        //列表字段和计算方式Map
        Map<String, RrdAlgorithm> rrdMap = new HashMap<String, RrdAlgorithm>();
        
        //所有kpi集合
        List<BusinesKpiBean> listKpis = businesService.getKpis(moduleId);
        
        //封装参数rrdMap 作用：调用getRrdDataPointByNames方法
        for (BusinesKpiBean businesKpiBean : listKpis) {
            if(!kpi.equals("0")){
                String[] kpis = kpi.split(",");
                for (String val : kpis) {
                    if(businesKpiBean.getKpiId().equals(val)){
                        rrdMap.put(businesKpiBean.getName(), RrdAlgorithm.AVG); 
                    }
                }
            }else{
                rrdMap.put(businesKpiBean.getName(), RrdAlgorithm.AVG); 
            } 
        }        
        return rrdMap;
    }
}
