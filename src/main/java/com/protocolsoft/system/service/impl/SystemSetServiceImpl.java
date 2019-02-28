/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemSetServiceImpl
 *创建人:yan    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.protocolsoft.alarm.dao.AlarmSetThreadDao;
import com.protocolsoft.alarm.util.AlarmSetThreadUtil;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.system.dao.SystemSetDao;
import com.protocolsoft.system.service.SystemNetworkSetService;
import com.protocolsoft.system.service.SystemSetService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.utils.RuntimeUtil;

/**
 * 系统设置业务逻辑层接口实现类
 * 2017年7月18日 下午5:57:27
 * @author yan
 * @version
 * @see
 */
@Service
public class SystemSetServiceImpl implements SystemSetService {
    
    /**
     * 系统设置DAO
     */
    @Autowired
    private SystemSetDao dao;
    
    /**
     * 服务端
     */
    @Autowired
    private ServerManagementService serverService;
    
    /**
     * 客户端
     */
    @Autowired
    private ClientService clientService;
    
    /**
     * 告警DAO
     */
    @Autowired
    private AlarmSetThreadDao alarmSetThreadDao;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * systemNetworkSetService注入
     */
    @Autowired(required = false)
    private SystemNetworkSetService systemNetworkSetService;
    
    /**
     * 业务DAO
     */
    @Autowired
    private AppBusinessDao busiDao;
    
    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#getDataTimeSet()
     */
    @Override
    public Map<String, String> getDataTimeSet() {
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
            String sameServer = propertiesUtil.readProperty("time_server");
            String nowTime = DateUtils.getServerTime();
            resultMap.put("sameServer", sameServer);
            resultMap.put("nowTime", nowTime);
            return resultMap;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#operationDateTimeSet(java.lang.String, java.lang.String)
     */
    @Override
    public String operateDateTimeSet(HttpServletRequest request, String sameServer, String nowTime) {
        
       //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(2);
        logsBean.setMsg("更新系统时间设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        String result = "success";
        try {
            if (!"".equals(sameServer)){
                PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
                propertiesUtil.writeProperty("time_server", sameServer);
            }
            if (!"".equals(sameServer) && !"".equals(nowTime)){
                StringBuffer cmdSb = new StringBuffer();
                cmdSb.append("ntpdate");
                cmdSb.append(" "+sameServer);
                String [] cmdArr = new String []{"/bin/sh", "-c", cmdSb.toString()};
                Map<String, String> map = RuntimeUtil.exec(cmdArr);
                //String input = map.get("brInput");
                String error = map.get(RuntimeUtil.BR_ERROR);
                if (null != error && !"".equals(error)){
                    if (error.indexOf("no server") != -1){
                        setServerTime(nowTime);
                    }
                }
            } else if (!"".equals(sameServer)){
                StringBuffer cmdSb = new StringBuffer();
                cmdSb.append("ntpdate");
                cmdSb.append(" "+sameServer);
                String [] cmdArr = new String []{"/bin/sh", "-c", cmdSb.toString()};
                Map<String, String> map = RuntimeUtil.exec(cmdArr);
                //String input = map.get("brInput");
                String error = map.get(RuntimeUtil.BR_ERROR);
                if (null != error && !"".equals(error)){
                    if (error.indexOf("no server") != -1){
                        result = "nonExistent";
                    }
                }
            } else if (!"".equals(nowTime)){
                setServerTime(nowTime);
                result = "success";
            }
        } catch (Exception e){
            e.printStackTrace();
            result = "fail";
        }
        return result;
    }
    
    /**
     * 设置服务器时间
     * 2017年7月21日 下午3:20:03
     * @param 
     * @exception 
     * @see
     */
    public void setServerTime(String time) {
        String cmd = "date -s \"" + time + "\"";
        try {
            RuntimeUtil.exec(new String []{"/bin/sh", "-c", cmd});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#getProductLicensSet()
     */
    @Override
    public List<Map<String, String>> getProductLicensSet() {
        try {
            List<Map<String, String>> resultListMap = new ArrayList< Map<String, String>>();
            PropertiesUtil propertiesUtil = new PropertiesUtil("sysinfo.properties");
            //用户名称
            String userName = propertiesUtil.readProperty("userName");
            //联系人
            String contacts = propertiesUtil.readProperty("contacts");
            //电话
            String telephone = propertiesUtil.readProperty("telephone");
            //邮箱
            String email = propertiesUtil.readProperty("email");
            //产品型号
            String productNo = propertiesUtil.readProperty("productNo");
            //有效期
            String validterm = propertiesUtil.readProperty("validterm");
            //最大分析流量(Mb)
            String maxFlow = propertiesUtil.readProperty("maxFlow");
            
            //是否支持多观察点
            String manyWatchpoint = propertiesUtil.readProperty("manyWatchpoint");
            //多观察点有效期
            String manyTerm = propertiesUtil.readProperty("manyTerm");
            
            //是否开启服务端
            String server = propertiesUtil.readProperty("server");
            String serverTerm = propertiesUtil.readProperty("serverTerm");
            //是否开启客户端
            String client = propertiesUtil.readProperty("client");
            String clientTerm = propertiesUtil.readProperty("clientTerm");
            //是否开启HTTP
            String http = propertiesUtil.readProperty("http");
            String httpTerm = propertiesUtil.readProperty("httpTerm");
            //是否开启MYSQL
            String mysql = propertiesUtil.readProperty("mysql");
            String mysqlTerm = propertiesUtil.readProperty("mysqlTerm");
            //是否开启ORACLE
            String oracle = propertiesUtil.readProperty("oracle");
            String oracleTerm = propertiesUtil.readProperty("oracleTerm");
            //是否开启SQLServer
            String sqlserver = propertiesUtil.readProperty("sqlserver");
            String sqlserverTerm = propertiesUtil.readProperty("sqlserverTerm");
            //是否开启URL
            String url = propertiesUtil.readProperty("url");
            String urlTerm = propertiesUtil.readProperty("urlTerm");
            //是否开启报文
            String message = propertiesUtil.readProperty("message");
            String messageTerm = propertiesUtil.readProperty("messageTerm");
            //是否开启流量储存
            String flowStorage = propertiesUtil.readProperty("flowStorage");
            String storeTerm = propertiesUtil.readProperty("storeTerm");
            //是否开启地图
            String map = propertiesUtil.readProperty("map");
            String mapTerm = propertiesUtil.readProperty("mapTerm");
            //是否开启拓扑图
            String topo = propertiesUtil.readProperty("topo");
            String topoTerm = propertiesUtil.readProperty("topoTerm");
            //是否储存通信队
            String trafficPair = propertiesUtil.readProperty("trafficPair");
            String pairTerm = propertiesUtil.readProperty("pairTerm");
            //iDigger
            String digger = propertiesUtil.readProperty("digger");
            String diggerTerm = propertiesUtil.readProperty("diggerTerm");
            
            Map<String, String> mapList = new HashMap<String, String>();
            if(!userName.equals("")) {
            	mapList.put("userName", userName);
            }
            if(!contacts.equals("")) {
            	mapList.put("contacts", contacts);
            }
            if(!telephone.equals("")) {
            	mapList.put("telephone", telephone);
            }
            if(!email.equals("")) {
            	mapList.put("email", email);
            }
            if(!productNo.equals("")) {
            	mapList.put("productNo", productNo);
            }
            if(!validterm.equals("")) {
            	mapList.put("validterm", validterm);
            }
            if(!maxFlow.equals("")) {
            	mapList.put("maxFlow", maxFlow);
            }
            if(!manyWatchpoint.equals("")) {
            	if(manyWatchpoint.equals("1")) {
            		mapList.put("manyWatchpoint", "开启 (有效期至"+manyTerm+")");
            	}else {
            		mapList.put("manyWatchpoint", "未开启");
            	}
            }
            if(!server.equals("")) {
            	if(server.equals("1")) {
            		mapList.put("server", "开启 (有效期至"+serverTerm+")");
            	}else {
            		mapList.put("server", "未开启");
            	}
            }
            if(!client.equals("")) {
            	if(client.equals("1")) {
            		mapList.put("client", "开启 (有效期至"+clientTerm+")");
            	}else {
            		mapList.put("client", "未开启");
            	}
            }
            if(!http.equals("")) {
            	if(http.equals("1")) {
            		mapList.put("http", "开启 (有效期至"+httpTerm+")");
            	}else {
            		mapList.put("http", "未开启");
            	}
            }
            if(!mysql.equals("")) {
            	if(mysql.equals("1")) {
            		mapList.put("mysql", "开启 (有效期至"+mysqlTerm+")");
            	}else {
            		mapList.put("mysql", "未开启");
            	}
            }
            if(!oracle.equals("")) {
            	if(oracle.equals("1")) {
            		mapList.put("oracle", "开启 (有效期至"+oracleTerm+")");
            	}else {
            		mapList.put("oracle", "未开启");
            	}
            }
            if(!sqlserver.equals("")) {
            	if(sqlserver.equals("1")) {
            		mapList.put("sqlserver", "开启 (有效期至"+sqlserverTerm+")");
            	}else {
            		mapList.put("sqlserver", "未开启");
            	}
            }
            if(!url.equals("")) {
            	if(url.equals("1")) {
            		mapList.put("url", "开启 (有效期至"+urlTerm+")");
            	}else {
            		mapList.put("url", "未开启");
            	}
            }
            if(!message.equals("")) {
            	if(message.equals("1")) {
            		mapList.put("message", "开启 (有效期至"+messageTerm+")");
            	}else {
            		mapList.put("message", "未开启");
            	}
            }
            if(!flowStorage.equals("")) {
            	if(flowStorage.equals("1")) {
            		mapList.put("flowStorage", "开启 (有效期至"+storeTerm+")");
            	}else {
            		mapList.put("flowStorage", "未开启");
            	}
            }
            if(!map.equals("")) {
            	if(map.equals("1")) {
            		mapList.put("map", "开启 (有效期至"+mapTerm+")");
            	}else {
            		mapList.put("map", "未开启");
            	}
            }
            if(!topo.equals("")) {
            	if(topo.equals("1")) {
            		mapList.put("topo", "开启 (有效期至"+topoTerm+")");
            	}else {
            		mapList.put("topo", "未开启");
            	}
            }
            if(!trafficPair.equals("")) {
            	if(trafficPair.equals("1")) {
            		mapList.put("trafficPair", "开启 (有效期至"+pairTerm+")");
            	}else {
            		mapList.put("trafficPair", "未开启");
            	}
            }
            if(!digger.equals("")) {
            	if(digger.equals("1")) {
            		mapList.put("digger", "开启 (有效期至"+diggerTerm+")");
            	}else {
            		mapList.put("digger", "未开启");
            	}
            }
            resultListMap.add(mapList);
            return resultListMap;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#getDataStorageSet()
     */
    @Override
    public Map<String, String> getDataStorageSet() {
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
            String fileSize = propertiesUtil.readProperty("file_size");
            String hisDataDir = propertiesUtil.readProperty("file_dictionary");
            String dataSaveDir = propertiesUtil.readProperty("root_dictionary");
            String anaDataDir = propertiesUtil.readProperty("system_analysis_path");
            String diskUsage = propertiesUtil.readProperty("disk_usage");
            
            resultMap.put("fileSize", fileSize);
            resultMap.put("hisDataDir", hisDataDir);
            resultMap.put("dataSaveDir", dataSaveDir);
            resultMap.put("anaDataDir", anaDataDir);
            resultMap.put("diskUsage", diskUsage);
            return resultMap;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#clearTargetDir(java.lang.String)
     */
    @Override
    public String clearTargetDir(HttpServletRequest request, String targetDir) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(3);
        logsBean.setMsg("清空"+targetDir+"目录");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        StringBuffer cmdSb = new StringBuffer();
        //判断传过来的目标目录是否为空
        if (null != targetDir && !"".equals(targetDir)){
            if (targetDir.endsWith("dsas")) {
                cmdSb.append("/usr/local/bin/rmdsas");
            } else {
                //创建拼接字符串对象targetDirSb
                StringBuffer targetDirSb = new StringBuffer(targetDir);
                //如果末尾不包含/就拼接一个/
                if (!targetDirSb.toString().endsWith("/")){
                    targetDirSb.append("/");
                }
                //拼命令
                cmdSb.append("rm -rf ");    
                cmdSb.append(targetDirSb);
                cmdSb.append("*");
            }
        }
        try {
            if (!"".equals(cmdSb.toString())){
                RuntimeUtil.exec(new String []{"/bin/sh", "-c", cmdSb.toString()});
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.system.service.SystemSetService#operationDataStorageSet(java.lang.String, java.lang.String)
     */
    @Override
    public String operationDataStorageSet(HttpServletRequest request, String fileSize, String diskUsage) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(3);
        logsBean.setMsg("更新数据存储设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        try {
            if ((null != fileSize && !"".equals(fileSize)) && (null != diskUsage && !"".equals(diskUsage))){
                PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
                propertiesUtil.writeProperty("file_size", fileSize);
                propertiesUtil.writeProperty("disk_usage", diskUsage);
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    
    /**
     * 配置导出的表名
     */
    private final static String[] EXPORTTABLES = new String[] { 
        "ipm_authorize_juris", "ipm_email_control", "ipm_data_storage", "ipm_monitor_view", 
        "ipm_system_user", "ipm_syslog_set", "ipm_watchpoint",
        "ipm_app_business", "ipm_app_ip_port", "ipm_app_column", 
        "ipm_user_configure", "ipm_user_list_column", "ipm_logs", 
        "ipm_usability_set", "ipm_url_set", "ipm_bus_tags", "ipm_msg_fixed", 
        // 报表
        "ipm_modal_tableandalarm", "ipm_report_modal", "ipm_report_type", "ipm_timer_report_detail",
        // Center
        "ipm_center_user", "ipm_center_ip", 
        // 告警
        "ipm_alarm_baseline", "ipm_alarm_customkpi", "ipm_alarm_customkpigroup", "ipm_alarm_globalset ", 
        "ipm_alarm_globalvalue", "ipm_alarm_log", "ipm_alarm_set", "ipm_alarm_trigger" };
    
    /**
     * (非 Javadoc)
     * <p>Title: systemConfigExport</p>
     * <p>Description: </p>
     * @return ResponseEntity<byte[]>
     * @see com.protocolsoft.system.service.SystemSetService#systemConfigExport()
     */
    public ResponseEntity<byte[]> systemConfigExport(HttpServletRequest request) {
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("配置导出");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        ResponseEntity<byte[]> entity = null;
        String fileName = "IPM.CONFIG";
        String cfgPath = this.getClass().getClassLoader().getResource("").getPath();
        // 数据库配置导出
        StringBuilder cmd = new StringBuilder();
        // 保存目录结构 
        cmd.append("/usr/local/var/backupwp.sh");
        // 文件移动
        cmd.append("&& cp -f /data/kpi/backupwp.tar.gz ");
        cmd.append("/opt/apache-tomcat-8.0.45/webapps/ROOT/WEB-INF/classes");
        // 基础配置
        cmd.append("&& mysqldump -uroot -p123456 -t -c --compact --skip-comments ipm --tables");
        for (int i = 0, len = EXPORTTABLES.length; i < len; i ++) {
            cmd.append(" ");
            cmd.append(EXPORTTABLES[i]);
        }
        cmd.append(" > ");
        cmd.append(cfgPath);
        cmd.append("ipmfile");
        // 进入目录
        cmd.append("&& cd ");
        cmd.append(cfgPath);
        // 打包
        cmd.append("&& tar czf ");
        cmd.append(fileName);
        cmd.append(" ipmfile viewConf sysset.properties backupwp.tar.gz");
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{ "/bin/sh", "-c", cmd.toString() });
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        entity = this.fileDowload(fileName, cfgPath);
        
        return entity;
    }
    
    /**
     * (非 Javadoc)
     * <p>Title: systemConfigImport</p>
     * <p>Description: </p>
     * @param name
     * @param file
     * @return SimpleEntry<String, Integer>
     * @see com.protocolsoft.system.service.SystemSetService#systemConfigImport
     * (java.lang.String, org.springframework.web.multipart.MultipartFile)
     */
    public SimpleEntry<String, Integer> systemConfigImport(HttpServletRequest request, String name, MultipartFile file) {
        
        SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>("sccuess", 0);
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File newFile = new File(path + "IPM.CONFIG");
        try {
            file.transferTo(newFile);
            for (int i = 0, len = EXPORTTABLES.length; i < len; i ++) {
                dao.trunTableData(EXPORTTABLES[i]);
            }
            StringBuilder cmd = new StringBuilder();
            // 进入目录
            cmd.append("cd ");
            cmd.append(path);
            // 解压
            cmd.append("&& tar -xzf IPM.CONFIG");
            // 导入数据库文件
            cmd.append("&& mysql -uroot -p123456 --default-character-set=utf8 ipm < ipmfile");
            // 恢复目录结构
            cmd.append("&& tar -xvf backupwp.tar.gz -C ");
            cmd.append("/data/kpi/ipm/rrd/interfaces/device.2/watchPoint");
            
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{ "/bin/sh", "-c", cmd.toString() });
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
            // 删除文件
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile = new File(path + "ipmfile");
            if (newFile.exists()) {
                newFile.delete();
            }
            entry.setValue(1);
            
            //得到当前用户信息
            SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
           
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(11);
            logsBean.setMsg("配置导入");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            
            //添加系统日志
            logsDao.insertLogs(logsBean);
            
            AlarmSetThreadUtil.setAlarmSetThreadEmpty();
            AlarmSetThreadUtil.setAlarmSetThreadInit(alarmSetThreadDao);
            systemNetworkSetService.startStatistic(request);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return entry;
    }

    /** (非 Javadoc)
     * <p>Title: appConfigImport</p>
     * <p>Description: </p>
     * @param name
     * @param file
     * @return String
     * @see com.protocolsoft.system.service.SystemSetService#appConfigImport(java.lang.String, org.springframework.web.multipart.MultipartFile)
     */
    public String appConfigImport(HttpServletRequest request, String name, MultipartFile file) {
       
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("应用配置导入");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        StringBuilder info = new StringBuilder();
        InputStream is = null;
        Workbook wb = null;
        try {
            is = file.getInputStream();
            wb = new XSSFWorkbook(is);
            // 工作表个数
            int sheetNum = wb.getNumberOfSheets();
            Sheet sheet = null;
            for (int i = 0; i < sheetNum; i ++) {
                sheet = wb.getSheetAt(i);
                this.appAutoImport(sheet.getSheetName(), sheet, info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return info.toString();
    }

    /** (非 Javadoc)
     * <p>Title: appConfigExport</p>
     * <p>Description: 应用配置导出</p>
     * @return ResponseEntity<byte[]>
     * @see com.protocolsoft.system.service.SystemSetService#appConfigExport()
     */
    public ResponseEntity<byte[]> appConfigExport(HttpServletRequest request) {
       //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("应用配置导出");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        String fileName = "Template.xlsx";
        String path = this.getClass().getClassLoader().getResource("").getPath();
        ResponseEntity<byte[]> entity = this.fileDowload(fileName, path);
        
        return entity;
    }
    
    /**
     * 
     * @Title: fileDowload
     * @Description: 文件下载
     * @param fileName 文件名称
     * @param path 路径
     * @return ResponseEntity<byte[]>
     * @author www
     */
    public ResponseEntity<byte[]> fileDowload(String fileName, String path) {
        ResponseEntity<byte[]> entity = null;
        File file = new File(path + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        try {
            byte[] fileBytes = FileUtils.readFileToByteArray(file);
            entity = new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return entity;
    }
    
    /**
     * 
     * @Title: appAutoImport
     * @Description: 添加应用信息
     * @param sheetName 工作薄名称
     * @param sheet 工作薄实例
     * @param info 信息
     * @return boolean 是否成功
     * @author www
     */
    private boolean appAutoImport(String sheetName, Sheet sheet, StringBuilder info) {
        boolean bool = false;
        if (sheet != null) {
            int moduleId = this.getModuleIdByName(sheetName);
            // 工作表行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            // 行信息
            Row row = null;
            for (int i = 1; i < rowNum; i ++) {
                row = sheet.getRow(i);
                bool = this.addAppBusi(moduleId, row);
                if (!bool) {
                    info.append(sheetName + "工作薄中第" + (i + 1) + "行添加失败.");
                }
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: addAppBusi
     * @Description: 添加业务
     * @param moduleId 模块编号
     * @param row 信息
     * @return boolean
     * @author www
     */
    private boolean addAppBusi(int moduleId, Row row) {
        boolean bool = false;
        AppBusinessBean bean = new AppBusinessBean();
        bean.setModuleId(moduleId);
        if (row.getCell(0) != null) {
            if (row.getCell(1) != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                bean.setName(row.getCell(0).getStringCellValue());
                bean.setDisplayIp(row.getCell(1).getStringCellValue());
                if (moduleId == 11 || moduleId == 12) {
                    bean.setBandwidth((int) row.getCell(2).getNumericCellValue());
                    if (row.getCell(3) != null) {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        bean.setDescrption(row.getCell(3).getStringCellValue());
                    }
                } else {
                    if (row.getCell(2) != null) {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        bean.setDescrption(row.getCell(2).getStringCellValue());
                    }
                }
                bool = busiDao.isExistBusiSameNameOrIp(bean);
                if (!bool) {
                    if (moduleId == 11) { // 客户端
                        bool = clientService.addClient(null, bean).get("state") == 1;
                    } else {
                        bool = serverService.addServerSide(bean, null).get("state") == 1;
                    }
                }
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: getModuleIdByName
     * @Description: 根据名称获取模块编号
     * @param name 模块名称
     * @return int 模块编号
     * @author www
     */
    private int getModuleIdByName(String name) {
        int moduleId = 0;
        switch (name) {
            case "Server": // 服务端
                moduleId = 12;
                break;
            case "Client": // 客户端
                moduleId = 11;
                break;
            default:
                break;
        }
        
        return moduleId;
    }

    @Override
    public List<Map<String, Object>> selectTablesData() {
        //存储：数据拼接格式
        Map<String, Object> m = new HashMap<String, Object>();
        //存储：最终数据格式
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        //存放：所有粒度表名
        String[] tableName={"commpair_10_log_tables", "commpair_600_log_tables", "commpair_60_log_tables", "commpair_86400_log_tables"};
        //循环得到每张粒度表表名
        for (int i = 0; i < tableName.length; i++) {
            //每次重新创建对象
            m = new HashMap<String, Object>();
            //分隔粒度表表名，取到粒度（10，600，60，86400）
            String[] granularity = tableName[i].split("_");
            //得到第一张表的第一条数据
            String start = dao.selectTablesData(tableName[i]);
            //拼接数据格式
            m.put(granularity[1], start);
            //保存拼接的数据格式
            dataList.add(m);
        }
        return dataList;
    }

	@Override
	public String getValidterm() {
		PropertiesUtil propertiesUtil = new PropertiesUtil("sysinfo.properties");
		String time = null;
		try {
			String validterm = propertiesUtil.readProperty("validterm");
			if(!validterm.equals("")) {
				time = validterm;
			}else {
				time = "0";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return time;
	}
}
