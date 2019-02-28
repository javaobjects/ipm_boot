/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewServiceImpl
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.view.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.view.bean.MonitorViewBean;
import com.protocolsoft.view.dao.MonitorViewDao;
import com.protocolsoft.view.service.MonitorViewService;

/**
 * MonitorViewServiceImpl
 * 2017年9月15日 上午10:49:34
 * @author long
 * @version
 * @see
 */
@Service
public class MonitorViewServiceImpl implements MonitorViewService {
    /**
     * userConfigureDao注入
     */
    @Autowired(required=false)
    private MonitorViewDao monitorViewDao;
    /**
     * authorizeJurisService注入
     */
    @Autowired(required=false)
    private AuthorizeJurisService authorizeJurisService;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    @Override
    public List<MonitorViewBean> getMonitorViewList() {
        List<MonitorViewBean>list=monitorViewDao.getMonitorViewList();
        return list;
    }

    @Override
    public MonitorViewBean addMonitorView(HttpServletRequest request, MonitorViewBean monitorViewBean) {
        boolean isExist=monitorViewDao.getMonitorViewCountByName(monitorViewBean.getName(), monitorViewBean.getId());
        if (!isExist){
            int isSuccess=monitorViewDao.addMonitorView(monitorViewBean);
            if (isSuccess>0){
                authorizeJurisService.addAuthorizeJurisByMonitorViewBean(monitorViewBean);
                JsonFileUtil jsonFileUtil = JsonFileUtil.getInstance();
                try {
                    jsonFileUtil.addJsonFile(ModuleType.VIEW, monitorViewBean.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                monitorViewBean.setSuccess("1");
            } else {
                monitorViewBean.setSuccess("0");
            }
        } else {
            monitorViewBean.setSuccess("2");
        }
        
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(8);
        logsBean.setMsg("添加"+monitorViewBean.getName()+"驾驶舱");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        return monitorViewBean;
    }

    @Override
    public MonitorViewBean updateMonitorViewById(HttpServletRequest request, MonitorViewBean monitorViewBean) {
        boolean isExist=monitorViewDao.getMonitorViewCountByName(monitorViewBean.getName(), monitorViewBean.getId());
        if (!isExist){
            int isSuccess=monitorViewDao.updateMonitorViewById(monitorViewBean);
            if (isSuccess>0){
                monitorViewBean.setSuccess("1");
            } else {
                monitorViewBean.setSuccess("0");
            }
        } else {
            monitorViewBean.setSuccess("2");
        }
        
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(8);
        logsBean.setMsg("修改"+monitorViewBean.getName()+"驾驶舱");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        return monitorViewBean;
    }

    @Override
    public Map<String, String> delMonitorView(HttpServletRequest request, int id) {
        Map<String, String> map=new HashMap<String, String>();
        //根据id查询预删除的驾驶舱，用于添加日志
        MonitorViewBean bean = monitorViewDao.getSelectById(id);
        
        boolean isSuccess=monitorViewDao.delMonitorView(id);
        
        monitorViewDao.delAuthMonitorView(id);
        if (isSuccess){
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(8);
        logsBean.setMsg("删除"+bean.getName()+"驾驶舱");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        return map;
    }

    @Override
    public List<MonitorViewBean> getMonitorViewList(int userId, int roleId) {
        List<MonitorViewBean> monitorViewBeanList = new ArrayList<MonitorViewBean>();
        MonitorViewBean monitorViewBean = null;
        if(roleId == 0 || roleId == 1){
            List<AuthorizeJurisBean> beanSet = authorizeJurisService.getUserAdminAuthorizeModuleList(107);
            Set<Integer> set = new  HashSet<Integer>(); 
            List<AuthorizeJurisBean> beanList = new ArrayList<AuthorizeJurisBean>();
            for (AuthorizeJurisBean aJurisBean : beanSet) {
                if(set.add(aJurisBean.getAppId())){
                    beanList.add(aJurisBean);
                }
            }
            for (int i = 0; i < beanList.size(); i++) {
                monitorViewBean = monitorViewDao.getMonitorViewListById(beanList.get(i).getAppId());
                if(monitorViewBean != null && monitorViewBean.getId() >= 100){
                    monitorViewBeanList.add(monitorViewBean);  
                }
            }
        }else{
            List<AuthorizeJurisBean> beanList = authorizeJurisService.getUserAuthorizeByModuleList(userId, 107);
            if(beanList.size() > 0){
                for(int i = 0; i < beanList.size(); i++){
                    if(beanList.get(i).getAppId() != 1){
                        monitorViewBean = monitorViewDao.getMonitorViewListById(beanList.get(i).getAppId());
                        if(monitorViewBean != null && monitorViewBean.getId() >= 100){
                            monitorViewBeanList.add(monitorViewBean);  
                        }
                    }
                } 
            }
        }
        return monitorViewBeanList;
    }

    /**
     * 
     * @Title: getViewById
     * @Description: 驾驶舱信息
     * @param id 
     * @return MonitorViewBean
     * @author www
     */
    public MonitorViewBean getViewById(int id) {
        
        return monitorViewDao.getMonitorViewListById(id);
    }
    
    /**
     * 
     * @Title: updateMonitorViewStatus
     * @Description: 更改状态
     * @param id 编号
     * @param status 状态
     * @return boolean
     * @author www
     */
    public boolean updateMonitorViewStatus(int id, int status) {
        
        return monitorViewDao.updateMonitorViewStatus(id, status);
    }
}
