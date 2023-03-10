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

@Component
public class CommunicationPairDataPush {
	
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
