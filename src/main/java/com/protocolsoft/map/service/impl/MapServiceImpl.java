/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapServiceImpl
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.protocolsoft.map.bean.CommpairTablesBean;
import com.protocolsoft.map.bean.MapQoBean;
import com.protocolsoft.map.bean.MapVoBean;
import com.protocolsoft.map.dao.MapDao;
import com.protocolsoft.map.service.MapService;


/**
 * 
 * @ClassName: MapServiceImpl
 * @Description: 地图业务处理接口实现类
 * @author wangjianmin
 *
 */
@Service
public class MapServiceImpl implements MapService {

   
    
    /**
     * 地图Dao
     */
    @Autowired
    private MapDao mapDao;
    
    /**
     * 
     * @Title: assembleResultMap
     * @Description: 组装Map
     * @param mapQoBean 接收Qo参数
     * @param regionCnMapVoBeans 接收Vo参数
     * @return Map<String,MapVoBean>
     * @author wangjianmin
     */
    public Map<String, MapVoBean> assembleResultMap(MapQoBean mapQoBean, List<MapVoBean> regionCnMapVoBeans){
        
        Map<String, MapVoBean> map = new HashMap<String, MapVoBean>();
        
        MapVoBean tempRegionCnMapVoBean = null;
        
        if (null != mapQoBean.getRegionCn() && !"".equals(mapQoBean.getRegionCn())){
            if ("北京市".equals(mapQoBean.getRegionCn()) || "上海市".equals(mapQoBean.getRegionCn()) 
                    || "重庆市".equals(mapQoBean.getRegionCn()) || "天津市".equals(mapQoBean.getRegionCn())){
                for (MapVoBean mapVoBean : regionCnMapVoBeans) {
                    tempRegionCnMapVoBean = map.get(mapVoBean.getDistrict());
                    if (tempRegionCnMapVoBean != null) {
                        tempRegionCnMapVoBean.setValue(tempRegionCnMapVoBean.getValue() + mapVoBean.getValue());
                    } else {
                        map.put(mapVoBean.getDistrict(), mapVoBean);
                    }
                }
            } else {
                for (MapVoBean mapVoBean : regionCnMapVoBeans) {
                    tempRegionCnMapVoBean = map.get(mapVoBean.getCityCn());
                    if (tempRegionCnMapVoBean != null) {
                        tempRegionCnMapVoBean.setValue(tempRegionCnMapVoBean.getValue() + mapVoBean.getValue());
                    } else {
                        map.put(mapVoBean.getCityCn(), mapVoBean);
                    }
                }
            }
        } else {
            for (MapVoBean mapVoBean : regionCnMapVoBeans) {
                tempRegionCnMapVoBean = map.get(mapVoBean.getRegionCn());
                if (tempRegionCnMapVoBean != null) {
                    tempRegionCnMapVoBean.setValue(tempRegionCnMapVoBean.getValue() + mapVoBean.getValue());
                } else {
                    map.put(mapVoBean.getRegionCn(), mapVoBean);
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, MapVoBean> getMapData(MapQoBean mapQoBean) {
        StringBuffer ipStr = new StringBuffer();
        if (mapQoBean.getAppBusinessId() != 4001){
        	ipStr.append(" (");
            ipStr.append(" serverUideId like '%."+mapQoBean.getAppBusinessId()+".%'");
            ipStr.append(") ");
            ipStr.append("OR");
        } 
        if (ipStr.toString().length() > 0){
            mapQoBean.setIpStr(ipStr.toString().substring(0, ipStr.toString().lastIndexOf("OR")));
        }
        
        Map<String, MapVoBean> map = new HashMap<String, MapVoBean>();
        
        //获取粒度
        Long granularity = getLidu(mapQoBean.getStartTime(), mapQoBean.getEndTime());
        //封装query参数
        mapQoBean.setGranularity(granularity);
        //拼SQL列
        String columnName = getColumnName(granularity, mapQoBean.getKpiName());
        mapQoBean.setColumnName(columnName);
        String tableName = "commpair_" + granularity + "_log_tables";
        //查询该粒度下是否有表
        String resultTableName = mapDao.selectTableIsExist(tableName);
        if (null != resultTableName && !"".equals(resultTableName)){
          //查询表名
            List<CommpairTablesBean> commpairTablesBeans = mapDao.selectCommpairTableId(mapQoBean);
            if (null != commpairTablesBeans && commpairTablesBeans.size() > 0){
                for (CommpairTablesBean commpairTablesBean : commpairTablesBeans) {
                    if (commpairTablesBean.getWpid() == mapQoBean.getWatchpointId()) {
                    //查询所有对应通信对数据 对应省份
                        List<MapVoBean> regionCnMapVoBeans = mapDao.
                                selectCommpairDataToMapData(commpairTablesBeans, mapQoBean);
                        map = assembleResultMap(mapQoBean, regionCnMapVoBeans);
                    }
                }
            }
        }
        
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        List<Map<String, String>> listMap =  new ArrayList<Map<String, String>>();
        List<Map<String, String>> resultMap =  new ArrayList<Map<String, String>>();
        Map<String, String> newMap = new HashMap<String, String>();
        while (iter.hasNext()) {
            newMap = new HashMap<String, String>();
            String key = iter.next();
            newMap.put("key", key);
            BigDecimal bd=new BigDecimal(map.get(key).getValue());
            newMap.put("value", bd.doubleValue()+"");
            listMap.add(newMap);
        }
        
        Collections.sort(listMap, new Comparator<Map<String, String>>() {  
            @Override  
            public int compare(Map<String, String> o1, Map<String, String> o2) {  
                int i = (int) (Double.parseDouble(o2.get("value"))
                        - Double.parseDouble(o1.get("value")));  
                return i;
            }  
        });
        MapVoBean bean = new MapVoBean();
        for (int i = 0; i < listMap.size(); i++) {
            if(i < 10){
                resultMap.add(listMap.get(i));
            }
        }
        bean.setResultMap(resultMap);
        map.put("sort", bean);
        return map;
    }
    
    /**
     * 
     * @Title: getColumnName
     * @Description: 拼SQL列
     * @param granularity 粒度
     * @param columnName  kpi名称
     * @return String
     * @author wangjianmin
     */
    public String getColumnName(Long granularity, String columnName){
        //拼sql列
        if ("flow".equals(columnName)) {// 流量
            columnName = "ethernetTraffic value";
        } else if ("netPktLost".equals(columnName)) {// 网络传输丢包率
            columnName = "(netPktLost/ethernetPkts) * 100 value";
        } else if ("NumberSessions".equals(columnName)) {// 会话数量
            columnName = "sessionNum value";
        }else if("qos".equals(columnName)){
            columnName = "(serverConDelay+responseDelay+loadDelay) value";
        }
        return columnName;
    }
    
    /**
     * 
     * @Title: getLidu
     * @Description: 通过开始时间和结束时间计算得出粒度
     * @param starttime  开始时间
     * @param endtime    结束时间
     * @return long
     * @author wangjianmin
     */
    public long getLidu(long starttime, long endtime){
        long lidu=0;
        long diff = endtime - starttime;
        if (diff < 3600) { //小于一小时
            lidu = 10;
        } else if (diff >= 3600 && diff < 36000) { //大于等于1小时    小于10小时
            lidu = 60;
        } else if (diff >= 36000 && diff < 345600) { //大于等于10小时    小于4天(345600)
            lidu = 600;
        } else if (diff >= 345600) { //大于等于4天
            lidu = 86400;
        }
        return lidu;
    }
}
