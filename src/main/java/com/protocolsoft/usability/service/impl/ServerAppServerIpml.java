/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ServerAppServerIpml.java
 *创建人: wangjianmin    创建时间: 2018年9月14日
 */
package com.protocolsoft.usability.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.usability.bean.UsabilityBean;
import com.protocolsoft.usability.dao.ServerAppDao;
import com.protocolsoft.usability.dao.UsabilityDao;
import com.protocolsoft.usability.service.ServerAppServer;
import com.protocolsoft.usability.service.UsabilityService;

/**
 * @ClassName: ServerAppServerIpml
 * @Description: 服务端添加到应用可用性处理类
 * @author wangjianmin
 *
 */
@Service
public class ServerAppServerIpml implements ServerAppServer {

    /**
     * UsabilityService 注入
     */
    @Autowired(required = false)
    private UsabilityService usabilityService;
    
    /**
     * 注入
     */
    @Autowired(required=false)
    private ServerAppDao serverAppDao;
    
    /**
     * ipm_app_ip_port表Dao
     */
    @Autowired
    private AppIpPortDao appIpPortDao;
    
    /**
     * UsabilityDao注入
     */
    @Autowired(required=false)
    private  UsabilityDao usabilityDao;
    
    
    @Override
    public boolean addServerUsab(int id) {
        boolean flag = true;
        try {
            deleteServerUsab(id);
            //根据业务id。得到业务名称
            String name = serverAppDao.getByBusiName(id);
            //根据id得到 ip 和端口集合
            List<AppIpPortBean> list =  appIpPortDao.selectAppIpPortsByAppId(id);
            //创建应用可用性对象
            UsabilityBean bean = new UsabilityBean();
            //得到执行时间
            long current = System.currentTimeMillis() / 1000;
            //循环ip 和端口集合
            for (AppIpPortBean appIpPortBean : list) {
                bean = new UsabilityBean();
                bean.setName(name);
                bean.setIp(appIpPortBean.getIp());
                bean.setPort(appIpPortBean.getPort()+"");
                bean.setInterval(5+"");
                bean.setBusiId(id);
                bean.setStatus("Y");
                bean.setDes("");
                bean.setLastExecTime(current + "");
                usabilityService.addUsability(null, bean);
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

   
    @Override
    public boolean updateServerUsab(int id) {
       
        return true;
    }

   
    @Override
    public boolean deleteServerUsab(int id) {
        List<UsabilityBean> list = usabilityDao.getSelectByAppId(id);
        boolean flag = true;
        try {
            for (UsabilityBean usabilityBean : list) {
                usabilityService.delUsabilityId(null, usabilityBean.getId());
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
