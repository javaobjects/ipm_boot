/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairServiceImpl
 *创建人:yan    创建时间:2017年10月16日
 */
package com.protocolsoft.jtopo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.jtopo.bean.CommpairQoBean;
import com.protocolsoft.jtopo.bean.CommpairTablesBean;
import com.protocolsoft.jtopo.bean.CommpairVoBean;
import com.protocolsoft.jtopo.dao.CommpairJtopoDao;
import com.protocolsoft.jtopo.service.CommpairService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.utils.FileUtil;

/**
 * 通信对接口实现类
 * 2017-10-16 下午2:38:15
 * @author yan
 * @version
 * @see
 */
@Service
public class CommpairServiceImpl implements CommpairService{

    /**
     * 通信对Dao
     */
    @Autowired
    private CommpairJtopoDao commpairJtopoDao;
    
    /**
     * 绘图
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * 通过开始时间和结束时间计算得出粒度
     * 2017-10-18 下午2:21:39
     * @param starttime
     * @param endtime
     * @return long
     * @exception 
     * @see
     */
    public long getLidu(long starttime, long endtime){
        long lidu=0;
        if ((endtime-starttime)<3600){
            lidu=10;
        } else if ((endtime-starttime)>=3600 && (endtime-starttime)<36000){
            lidu=60;
        } else if ((endtime-starttime)>=36000 && (endtime-starttime)<259200){
            lidu=3600;
        } else if ((endtime-starttime)>=259200){
            lidu=86400;
        }
        return lidu;
    }
    
    /**
     * 拼SQL列
     * 2017-10-18 上午11:41:55
     * @param
     * @return String
     * @exception 
     * @see
     */
    public String getColumnName(Long granularity, String columnName) {
        String tmp = "(";
        switch(columnName) {
            case "ethernetPkts":
            case "tinyPkts":
                tmp += (columnName + " / " + granularity);
                break;
            case "netPktLostRatio":
                tmp += "netPktLost / ethernetPkts";
                break;
            case "serverPktLostRatio":
                tmp += "serverPktLost / serverPkt";
                break;
            case "clientPktLostRatio":
                tmp += "clientPktLost / clientPkt";
                break;
            case "tinyPktsRatio":
                tmp += "tinyPkts / ethernetPkts";
                break;
            default:
                tmp += columnName;
        }
        tmp += ") value";
        
        return tmp;
    }
    
    /**
     * 
     * 2017-10-18 上午11:41:55
     * @param
     * @return String
     * @exception 
     * @see
     */
    public String getColumnCalcul(String unit){
        String cal = null;
        switch(unit) {
            case "flow":
            case "flowB":
            case "num":
                cal = "SUM";
                break;
            case "ms":
            case "pps":
            case "ratio":
                cal = "AVG";
                break;
            default:
                cal = "AVG";
        }
        
        return cal;
    }
    
    /*
     * (non-Javadoc)
     * @see com.protocolsoft.jtopo.service.CommpairService#getAllCommpair
     * (java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<CommpairVoBean> getAllCommpair(CommpairQoBean commpairQoBean) {
        //获取粒度
        Long granularity = commpairQoBean.getGranularity();
        if (granularity == null) {
            granularity = getLidu(commpairQoBean.getStartTime(), commpairQoBean.getEndTime());
        }
        //数量
        if (commpairQoBean.getNum() == null) {
            commpairQoBean.setNum(2000);
        }
        //封装query参数
        commpairQoBean.setGranularity(granularity);
        
        KpisBean kpisBean = plotService.getKpisById(commpairQoBean.getKpiId());
        //拼SQL列
        String columnName = getColumnName(granularity, kpisBean.getName());
        commpairQoBean.setColumnName(columnName);
        
        String calcul = getColumnCalcul(kpisBean.getUnit());
        commpairQoBean.setCalcul(calcul);
        
        String tableName = "commpair_" + granularity + "_log_tables";
        //查询该粒度下是否有表
        String resultTableName = commpairJtopoDao.selectTableIsExist(tableName);
        if (null != resultTableName && !"".equals(resultTableName)){
            //查询表名
            List<CommpairTablesBean> commpairTablesBeans = null;
            if (commpairQoBean.getWatchpointId() == null) {
                commpairTablesBeans = commpairJtopoDao.selectCommpairTableId(commpairQoBean);
            } else {
                commpairTablesBeans = commpairJtopoDao.selectCommpairTableIdByWId(commpairQoBean);
            }
            if (null != commpairTablesBeans && commpairTablesBeans.size() > 0){
                
                List<CommpairVoBean> newCommpairVoBeans = new ArrayList<CommpairVoBean>();
                //查询所有对应通信对数据
                List<CommpairVoBean> commpairVoBeans = commpairJtopoDao.selectCommpairData(commpairTablesBeans, 
                        commpairQoBean);
                //处理数据
                for (CommpairVoBean commpairVoBean : commpairVoBeans) {
                    commpairVoBean.setRealIp(commpairVoBean.getFromIp());
                    commpairVoBean.setFromAlarm(0L);
                    commpairVoBean.setToAlarm(0L);
                    commpairVoBean.setFromType("server");
                    commpairVoBean.setToType("server");
                    commpairVoBean.setStartTime(commpairQoBean.getStartTime());
                    commpairVoBean.setEndTime(commpairQoBean.getEndTime());
                    newCommpairVoBeans.add(commpairVoBean);
                }
                return newCommpairVoBeans;
            }
        }
        return new ArrayList<CommpairVoBean>();
    }

    /*
     * (non-Javadoc)
     * @see com.protocolsoft.jtopo.service.CommpairService#getCommpairByIpPort
     * (com.protocolsoft.jtopo.bean.CommpairQoBean)
     */
    @Override
    public List<CommpairVoBean> getCommpairByIpPort(CommpairQoBean commpairQoBean) {
        //获取粒度
        Long granularity = commpairQoBean.getGranularity();
        if (granularity == null) {
            granularity = getLidu(commpairQoBean.getStartTime(), commpairQoBean.getEndTime());
        }
        //数量
        if (commpairQoBean.getNum() == null) {
            commpairQoBean.setNum(255);
        }
        //封装query参数
        commpairQoBean.setGranularity(granularity);
        
        KpisBean kpisBean = plotService.getKpisById(commpairQoBean.getKpiId());
        //拼SQL列
        String columnName = getColumnName(granularity, kpisBean.getName());
        commpairQoBean.setColumnName(columnName);
        
        String calcul = getColumnCalcul(kpisBean.getUnit());
        commpairQoBean.setCalcul(calcul);
        
        String tableName = "commpair_" + granularity + "_log_tables";
        //查询该粒度下是否有表
        String resultTableName = commpairJtopoDao.selectTableIsExist(tableName);
        if (null != resultTableName && !"".equals(resultTableName)){
            //查询表名
            List<CommpairTablesBean> commpairTablesBeans = null;
            if (commpairQoBean.getWatchpointId() == null) {
                commpairTablesBeans = commpairJtopoDao.selectCommpairTableId(commpairQoBean);
            } else {
                commpairTablesBeans = commpairJtopoDao.selectCommpairTableIdByWId(commpairQoBean);
            }
            
            if (null != commpairTablesBeans && commpairTablesBeans.size() > 0){
                //查询所有对应通信对数据
                List<CommpairVoBean> commpairVoBeans = commpairJtopoDao.selectCommpairDataByIpPort(commpairTablesBeans, 
                        commpairQoBean);
                
                List<CommpairVoBean> newCommpairVoBeans = new ArrayList<CommpairVoBean>();
                
                //只传IP
                if ((null != commpairQoBean.getIp() && !"".equals(commpairQoBean.getIp())) 
                        && null == commpairQoBean.getPort()){
                    Set<String> set = new HashSet<String>();
                    for (CommpairVoBean commpairVoBean : commpairVoBeans) {
                        if (set.add(commpairVoBean.getFromIp() + commpairVoBean.getPort())){
                            commpairVoBean.setIp(commpairVoBean.getFromIp());
                            commpairVoBean.setFromAlarm(0L);
                            commpairVoBean.setToAlarm(0L);
                            commpairVoBean.setFromType("server");
                            commpairVoBean.setToType("server");
                            commpairVoBean.setType("server");
                            commpairVoBean.setStartTime(commpairQoBean.getStartTime());
                            commpairVoBean.setEndTime(commpairQoBean.getEndTime());
                            newCommpairVoBeans.add(commpairVoBean);
                        }
                    }
                } else if ((null != commpairQoBean.getIp() && !"".equals(commpairQoBean.getIp())) 
                        && null != commpairQoBean.getPort()){//传IP和PORT
                    Set<String> set = new HashSet<String>();
                    for (CommpairVoBean commpairVoBean : commpairVoBeans) {
                        if (set.add(commpairVoBean.getToInfo())){
                            commpairVoBean.setIp(commpairVoBean.getToInfo());
                            commpairVoBean.setFromAlarm(0L);
                            commpairVoBean.setToAlarm(0L);
                            commpairVoBean.setFromType("server");
                            commpairVoBean.setToType("server");
                            commpairVoBean.setType("server");
                            commpairVoBean.setStartTime(commpairQoBean.getStartTime());
                            commpairVoBean.setEndTime(commpairQoBean.getEndTime());
                            newCommpairVoBeans.add(commpairVoBean);
                        }
                    }
                } else {
                    for (CommpairVoBean commpairVoBean : commpairVoBeans) {
                        commpairVoBean.setRealIp(commpairVoBean.getFromIp());
                        commpairVoBean.setFromAlarm(0L);
                        commpairVoBean.setToAlarm(0L);
                        commpairVoBean.setFromType("server");
                        commpairVoBean.setToType("server");
                        commpairVoBean.setStartTime(commpairQoBean.getStartTime());
                        commpairVoBean.setEndTime(commpairQoBean.getEndTime());
                        newCommpairVoBeans.add(commpairVoBean);
                    }
                }
                return newCommpairVoBeans;
            }
        }
        return new ArrayList<CommpairVoBean>();
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.jtopo.service.CommpairService#getJtopo(java.lang.String)
     */
    @Override
    public String getJtopo(String name) {
        return FileUtil.getFile(name);
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.jtopo.service.CommpairService#saveJtopo(java.lang.String, java.lang.String)
     */
    @Override
    public void saveJtopo(String name, String nodeJson) {
        // 写文件
        FileUtil.createFile(name, nodeJson);
    }
}
