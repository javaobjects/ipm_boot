/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemSetController
 *创建人:yan    创建时间:2017年7月18日
 */
package com.protocolsoft.system.controller;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.service.LogsService;
import com.protocolsoft.system.service.SystemSetService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;

/**
 * 系统设置控制器层
 * 2017年7月18日 下午5:51:47
 * @author yan
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/systemSet")
public class SystemSetController {
    
    /**
     * 系统设置业务逻辑层对象
     */
    @Autowired
    private SystemSetService systemSetService;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * 日志管理业务对象
     */
    @Autowired
    private LogsService logsService;

    /**
     * 读取日期和时间设置
     * 2017年7月18日 下午6:00:53
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    @RequestMapping(value="readDateTimeSet.do")
    @ResponseBody 
    public Map<String, String> readDateTimeSet(){
        Map<String, String> resultMap = systemSetService.getDataTimeSet();
        return resultMap;
    }
    
    /**
     * 操作日期和时间设置
     * 2017年7月20日 下午5:40:02
     * @param
     * @return String
     * @exception 
     * @see
     */
    @RequestMapping(value="operateDateTimeSet.do")
    @ResponseBody 
    public String operationDateTimeSet(HttpServletRequest request, String sameServer, String nowTime){
        String result = systemSetService.operateDateTimeSet(request, sameServer, nowTime);
        return result;
    }
    
    
    /**
     * 读取产品授权设置
     * 2017年7月18日 下午6:02:04
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    @RequestMapping(value="readProductLicensSet.do")
    @ResponseBody 
    public List<Map<String, String>> readProductLicensSet(){
        List<Map<String, String>> resultMap = systemSetService.getProductLicensSet();
        return resultMap;
    }
    
    /**
     * 读取数据存储设置
     * 2017年7月18日 下午6:03:30
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    @RequestMapping(value="readDataStorageSet.do")
    @ResponseBody 
    public Map<String, String> readDataStorageSet(){
        Map<String, String> resultMap = systemSetService.getDataStorageSet();
        return resultMap;
    }

    /**
     * 清空传入的目标目录
     * 2017年7月24日 上午10:21:53
     * @param targetDir 目标目录
     * @return String
     * @exception 
     * @see
     */
    @RequestMapping(value="clearTargetDir.do")
    @ResponseBody 
    public String clearTargetDir(HttpServletRequest request, String targetDir){
        String result = systemSetService.clearTargetDir(request, targetDir);
        return result;
    }
    
    /**
     * 操作数据存储设置
     * 2017年7月24日 上午11:19:19
     * @param fileSize 文件块存储大小
     * @param diskUsage 磁盘利用率百分比上限
     * @return String
     * @exception 
     * @see
     */
    @RequestMapping(value="operationDataStorageSet.do")
    @ResponseBody
    public String operationDataStorageSet(HttpServletRequest request, String fileSize, String diskUsage){
        String result = systemSetService.operationDataStorageSet(request, fileSize, diskUsage);
        return result;
    }
    
    /**
     * 
     * @Title: systemConfigExport
     * @Description: 配置导出
     * @return ResponseEntity<byte[]>
     * @author www
     */
    @RequestMapping(value="/systemConfigExport.do")
    public ResponseEntity<byte[]> systemConfigExport(HttpServletRequest request) {
        
        return systemSetService.systemConfigExport(request);
    }
    
    /**
     * 
     * @Title: systemConfigImport
     * @Description: 配置导入
     * @param name 名称
     * @param file 配置文件
     * @return SimpleEntry<String,Integer>
     * @author www
     */
    @RequestMapping(value="/systemConfigImport.do")
    @ResponseBody
    public SimpleEntry<String, Integer> systemConfigImport(HttpServletRequest request, String name, MultipartFile file) {
        
        return systemSetService.systemConfigImport(request, name, file);
    }
    
    /**
     * 
     * @Title: rebootServer
     * @Description: 重启机器
     * @author www
     */
    @RequestMapping(value="/rebootServer.do")
    @ResponseBody
    public void rebootServer(HttpServletRequest request) {
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("重启服务器");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        try {
            Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "/usr/local/var/chkxpm.sh kill"}).waitFor();
            
            Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "/sbin/reboot"});
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: rebootServer
     * @Description: 重启机器
     * @author www
     */
    @RequestMapping(value="/haltServer.do")
    @ResponseBody
    public void haltServer(HttpServletRequest request) {
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("关闭服务器");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        try {
            Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "/usr/local/var/chkxpm.sh kill"}).waitFor();
            Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "/sbin/halt"});
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: appConfigImport
     * @Description: 应用导入
     * @param name 名称
     * @param file 文件
     * @return Map<String,Object>
     * @author www
     */
    @RequestMapping(value="/appConfigImport.do")
    @ResponseBody
    public Map<String, Object> appConfigImport(HttpServletRequest request, String name, MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        String info = systemSetService.appConfigImport(request, name, file);
        map.put("stauts", info.equals(""));
        map.put("info", info);
        
        return map;
    }
    
    /**
     * 
     * @Title: tempDowload
     * @Description: 模版下载
     * @return ResponseEntity<byte[]>
     * @author www
     */
    @RequestMapping(value="/tempDowload.do")
    @ResponseBody
    public ResponseEntity<byte[]> tempDowload(HttpServletRequest request) {
        
        return systemSetService.appConfigExport(request);
    }
    
    /**
     * @return the systemSetService
     */
    public SystemSetService getSystemSetService() {
        return systemSetService;
    }

    /**
     * @param systemSetService the systemSetService to set
     */
    public void setSystemSetService(SystemSetService systemSetService) {
        this.systemSetService = systemSetService;
    }
    
    /**
     * 
     * @Title: selectTableName
     * @Description: 查询粒度表
     * @param request 请求
     * @return List<Map<String,Object>>
     * @author wangjianmin
     */
    @RequestMapping(value="/selectTableName.do")
    @ResponseBody
    public List<Map<String, Object>> selectTableName(HttpServletRequest request) {
        
        return systemSetService.selectTablesData();
    }
    
    /**
     * 
     * @Title: getValidterm
     * @Description: 获取系统授权时间
     * @return String
     * @author wangjianmin
     */
    @RequestMapping(value="getValidterm.do")
    @ResponseBody 
    public String getValidterm(){
        String result = systemSetService.getValidterm();
        return result;
    }
    
}
