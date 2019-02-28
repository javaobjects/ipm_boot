/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UsabilityServiceImpl
 *创建人:wjm    创建时间:2018年3月16日
 */
package com.protocolsoft.usability.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.usability.bean.UsabilityBean;
import com.protocolsoft.usability.dao.UsabilityDao;
import com.protocolsoft.usability.service.UsabilityService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;

/**
 * 
 * @ClassName: UsabilityServiceImpl
 * @Description: 应用可用性实现层
 * @author wangjianmin
 *
 */
@Service
public class UsabilityServiceImpl implements UsabilityService {
    /**
     * UsabilityDao注入
     */
    @Autowired(required=false)
    private  UsabilityDao usabilityDao;
    /**
     * AppBusinessDao注入
     */
    @Autowired(required=false)
    private AppBusinessDao appBusinessDao;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    @Override
    public List<UsabilityBean> getUsabilityAll() {
        List<UsabilityBean> list = usabilityDao.getUsabilityAll();
        return list;
    }

    @Override
    public int addUsability(HttpServletRequest request, UsabilityBean bean) {
        
        //添加统一业务表中
        AppBusinessBean appBusinessBean = new AppBusinessBean();
        appBusinessBean.setModuleId(1);
        appBusinessBean.setName(bean.getName());
        appBusinessDao.insertAppBusiness(appBusinessBean);
        
        if("Y".equals(bean.getStatus())){
            long current = System.currentTimeMillis() / 1000;
            bean.setLastExecTime(current + "");
        }
        bean.setAppId(appBusinessBean.getId());
        
        //添加应用可用性设置
        int id = usabilityDao.addUsability(bean);
        
        if(request != null){
            //得到当前用户信息
            SystemUserBean systemUserBean = userService.getSessionUserBean(request);
            
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(12);
            logsBean.setMsg("添加"+bean.getName()+"应用可用性设置");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            
            //添加系统日志
            logsDao.insertLogs(logsBean);
            
        }
        return id;
    }

   
    @Override
    public boolean getByName(String name, int id) {
        boolean  isName = usabilityDao.getByName(name, id);
        return isName;
    }

    
    @Override
    public boolean getByPort(String port, int id) {
        boolean  isPort = usabilityDao.getByPort(port, id);
        return isPort;
    }

    
    @Override
    public boolean delUsabilityId(HttpServletRequest request, int id) {
        
        //根据id查询应用可用性设置
        UsabilityBean bean = usabilityDao.getSelectById(id);
        
        //删除统一管理业务表中的这个id的应用可用性设置
        appBusinessDao.deleteAppBusiness(bean.getAppId());
        
        //删除这个id的应用可用性设置
        boolean isId = usabilityDao.delUsabilityId(id);
        
        if(request != null){
           //得到当前用户信息
            SystemUserBean systemUserBean = userService.getSessionUserBean(request);
            
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(12);
            logsBean.setMsg("删除"+bean.getName()+"应用可用性设置");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            
            //添加系统日志
            logsDao.insertLogs(logsBean);
            
        }
        return isId;
    }

    
    @Override
    public int updateUsability(HttpServletRequest request, UsabilityBean bean) {
        
        //根据id查询应用可用性设置
        UsabilityBean beans = usabilityDao.getSelectById(bean.getId());
        bean.setAppId(beans.getAppId());
        //统一管理业务中的应用可用性设置
        AppBusinessBean appBusinessBean=new AppBusinessBean();
        appBusinessBean.setId(beans.getAppId());
        appBusinessBean.setName(bean.getName());
        appBusinessDao.updateAppBusiness(appBusinessBean);
        
        if("Y".equals(bean.getStatus())){
            long current = System.currentTimeMillis() / 1000;
            bean.setLastExecTime(current+ "");
        }
        //修改应用可用性设置
        int isUpdate = usabilityDao.updateUsability(bean);
        if(request != null){
            //得到当前用户信息
            SystemUserBean systemUserBean = userService.getSessionUserBean(request);
            
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(12);
            logsBean.setMsg("修改"+beans.getName()+"应用可用性设置");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            
            //添加系统日志
            logsDao.insertLogs(logsBean);
        }
        return isUpdate;
    }
}
