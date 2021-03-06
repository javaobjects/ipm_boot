/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SyslogService.java
 *创建人: www    创建时间: 2018年3月31日
 */
package com.protocolsoft.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.system.bean.SyslogBean;
import com.protocolsoft.system.dao.SyslogSetDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;

/**
 * @ClassName: SyslogService
 * @Description: SYSLOG
 * @author www
 *
 */
@Service
public class SyslogService {
    
    /**
     * DAO
     */
    @Autowired
    private SyslogSetDao dao;
    
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
     * 
     * @Title: getSyslogInfo
     * @Description: 获取服务器信息
     * @return List<SyslogBean>
     * @author www
     */
    public List<SyslogBean> getSyslogInfo() {
        
        return dao.getSyslogInfo();
    }

    /**
     * 
     * @Title: getSyslogInfoById
     * @Description: 通过编号获取服务器信息
     * @param id 编号
     * @return SyslogBean
     * @author www
     */
    public SyslogBean getSyslogInfoById(int id) {
        
        return dao.getSyslogInfoById(id);
    }
    
    /**
     * 
     * @Title: addSyslog
     * @Description: 添加
     * @param bean void
     * @return Map<String, Boolean>
     * @author www
     */
    public Map<String, Boolean> addSyslog(HttpServletRequest request, SyslogBean bean) {
        dao.addSyslog(bean);
        
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("添加"+bean.getName()+"SYSLOG服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", true);
        
        return map;
    }

    /**
     * 
     * @Title: delSyslog
     * @Description: 删除
     * @param id 编号
     * @return Map<String, Boolean>
     * @author www
     */
    public Map<String, Boolean> delSyslog(HttpServletRequest request, int id) {
        SyslogBean  bean = dao.getSyslogInfoById(id);
        
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("删除"+bean.getName()+"SYSLOG服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        
        boolean bool = dao.delSyslog(id);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }

    /**
     * 
     * @Title: updSyslog
     * @Description: 修改服务器信息
     * @param bean 参数
     * @return boolean
     * @author www
     */
    public Map<String, Boolean> updSyslog(HttpServletRequest request, SyslogBean bean) {
        boolean bool = dao.updSyslog(bean);
        
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("修改"+bean.getName()+"SYSLOG服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: logSend
     * @Description: SYSLOG发送
     * @param message 信息
     * @param level 级别  <br>
     *      Syslog.LEVEL_WARN  普通 <br>
     *      Syslog.LEVEL_CRITICAL 重要 <br>
     *      Syslog.LEVEL_EMERGENCY 紧急 <br>
     * @author www
     */
    public void logSend(String message, int level) {
        List<SyslogBean> list = this.getSyslogInfo();
        if (list != null && list.size() > 0) {
            SyslogIF syslog = null;
            SyslogBean bean = null;
            try {
                for (int i = 0, len = list.size(); i < len; i ++) {
                    bean = list.get(i);
                    syslog = Syslog.getInstance(Syslog.UDP);
                    syslog.getConfig().setHost(bean.getIp());
                    syslog.getConfig().setPort(bean.getPort());
                    syslog.log(level, message);
                }
            } catch (SyslogRuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
