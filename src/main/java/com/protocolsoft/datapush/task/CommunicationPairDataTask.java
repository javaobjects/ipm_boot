/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommunicationPairDataTask.java
 *创建人: wjm   创建时间: 2018年9月26日
 */
package com.protocolsoft.datapush.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.service.CommpairService;
import com.protocolsoft.protoplan.service.PublicProtoPlanService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.utils.PropertiesUtil;

/**
 * @ClassName: CommunicationPairDataTask
 * @Description: 定时执行推送通讯对数据类
 * @author wjm
 *
 */
@Component
public class CommunicationPairDataTask {
	
	/**
     * @Fields commpairService : 服务端管理业务对象
     */
    @Autowired
    private CommpairService commpairService;
    
    /**
     * @Fields publicprotoPlanService : 协议业务对象
     */
    @Autowired
    private PublicProtoPlanService publicProtoPlanService;
    
    /**
     * @Fields serverManagementService : 服务端管理业务对象
     */
    @Autowired
    private ServerManagementService serverManagementService;
    
    /**
     * 
     * @Title: dataPush
     * @Description: 推送通讯对数据
     * @author wjm
     */
    @Scheduled(cron = "0 0 2 * * ?")//每天2点执行一次 
    public void dataPush(){
        //获取默认配置信息
        PropertiesUtil propertiesUtil = new PropertiesUtil("defaultConfig.properties");
        Date d = null;
        try {
            //时间转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //创建文件
            File file = new File("/var/lib/mysql/ipm/commpair/commpair.txt");
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
            	fileParent.mkdirs();
            }
            //判断文件是否存在
            if(file.exists()){
                file.delete();  //如果存在，先删除
                file.createNewFile(); //在创建
            }else{
                file.createNewFile(); //不存在，直接创建
            }
            long start  = 0;
            long end = 0;
            //获取开始时间
            String starttime = propertiesUtil.readProperty("starttime");
            if(starttime.equals("")) {
            	start = todayFirstDate();
            }else {
            	d = sdf.parse(starttime);
                start = d.getTime() /1000;
            }
            //获取开始时间
            String endtime = propertiesUtil.readProperty("endtime");
            if(endtime.equals("")) {
            	end = todayLastDate();
            }else {
            	d = sdf.parse(endtime);
                end = d.getTime() /1000;
            }
            //获取默认状态
            String def = propertiesUtil.readProperty("default");
            if(def.equals("true")){
                String kpis = null;
                String ethernetTraffic = propertiesUtil.readProperty("ethernetTraffic");
            	String qos = propertiesUtil.readProperty("qos");
            	String netPktLostRatio = propertiesUtil.readProperty("netPktLostRatio");
            	String synPkts = propertiesUtil.readProperty("synPkts");
            	String rtt = propertiesUtil.readProperty("rtt");
            	String avgPktsLen = propertiesUtil.readProperty("avgPktsLen");
            	if(!ethernetTraffic.equals("")) {
            		kpis=kpis+ethernetTraffic+",";
            	}
            	if(!qos.equals("")) {
            		kpis=kpis+qos+",";
            	}
            	if(!netPktLostRatio.equals("")) {
            		kpis=kpis+netPktLostRatio+",";
            	}
            	if(!synPkts.equals("")) {
            		kpis=kpis+synPkts+",";
            	}
            	if(!rtt.equals("")) {
            		kpis=kpis+rtt+",";
            	}
            	if(!avgPktsLen.equals("")) {
            		kpis=kpis+avgPktsLen+",";
            	}
            	String kpi = kpis.substring(4, kpis.length()-1);
            	CommpairBean commpairBean = new CommpairBean();
            	commpairBean.setStarttime(start);
            	commpairBean.setEndtime(end);
            	commpairBean.setGroupType(3);
            	commpairBean.setWatchpointId(0);
            	commpairBean.setIpmCenterId(1);
            	commpairBean.setOpenIpLocationFlag(0);
            	commpairBean.setKpis(kpi);
//            	commpairBean.setPropelling(1);
            	List<CommpairBean> commpairList = commpairService.toCommpairMergeData(commpairBean, 
            			publicProtoPlanService, commpairService, serverManagementService); 
            	Map<String, Object> map = new HashMap<String, Object>();
            	for (CommpairBean commpairBean2 : commpairList) {
            		map = new HashMap<String, Object>();
            		map.put("id", commpairBean2.getId());
            		map.put("starttime", commpairBean2.getStarttime());
            		map.put("endtime", commpairBean2.getEndtime());
            		map.put("watchpointName", commpairBean2.getWatchpointName());
            		map.put("ethernetTraffic", commpairBean2.getEthernetTraffic());
            		map.put("qos", commpairBean2.getQos()+"");
            		map.put("netPktLostRatio", commpairBean2.getNetPktLostRatio());
            		map.put("synPkts", commpairBean2.getSynPkts());
            		map.put("rtt", commpairBean2.getRtt());
            		map.put("avgPktsLen", commpairBean2.getAvgPktsLen());
            		map.put("serverip", commpairBean2.getServerip());
            		map.put("serverport", commpairBean2.getServerport());
            		map.put("clientip", commpairBean2.getClientip());
            		JSONObject json = new JSONObject(map);
            		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
            				new FileOutputStream(file, true)));
            		out.write(json.toString()+"\n");
            		out.close();
				}
            	Runtime.getRuntime().exec("/usr/local/var/rrdsync.sh commpair");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    //获取前一天开始时间
    public  long todayFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis()/1000;
    }
    //获取前一天结束时间
    public  long todayLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis()/1000;
    }
}
