/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SaasUserService.java
 *创建人: WWW    创建时间: 2018年8月9日
 */
package com.protocolsoft.common.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.protocolsoft.common.bean.CenterIpBean;
import com.protocolsoft.common.bean.SaasUserBean;
import com.protocolsoft.common.dao.SaasUserDao;

/**
 * @ClassName: SaasUserService
 * @Description: 用户信息
 * @author WWW
 *
 */
@Service
public class SaasUserService {

    /**
     * 用户DAO
     */
    @Autowired
    private SaasUserDao dao;
    
    /**
     * 远端管理
     */
    @Autowired
    private CenterIpService centerService;
    
    /**
     * 
     * @Title: addSaasUser
     * @Description: 添加用户信息
     * @param request 请求
     * @param bean 参数
     * @return boolean
     * @author WWW
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean addSaasUser(HttpServletRequest request, SaasUserBean bean) {
        
        CenterIpBean centerBean = new CenterIpBean();
        centerBean.setName(bean.getName());
        centerBean.setIp(bean.getIp());
        centerBean.setPort(bean.getPort());
        centerService.addUrlInfo(request, centerBean);
        
        bean.setId(centerBean.getId());
        dao.addSaasUser(bean);
        
        return true;
    }
    
    /**
     * 
     * @Title: updSaasUser
     * @Description: 更新用户信息
     * @param request 请求
     * @param bean 参数
     * @return boolean
     * @author WWW
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updSaasUser(HttpServletRequest request, SaasUserBean bean) {
        CenterIpBean centerBean = new CenterIpBean();
        centerBean.setId(bean.getId());
        centerBean.setName(bean.getName());
        centerBean.setIp(bean.getIp());
        centerBean.setPort(bean.getPort());
        centerService.updateJoinUrl(request, centerBean);
        
        dao.updSaasUser(bean);
        
        return true;
    }
    
    /**
     * 
     * @Title: delSaasUser
     * @Description: 删除用户信息
     * @param request 请求
     * @param id 编号
     * @return boolean
     * @author WWW
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean delSaasUser(HttpServletRequest request, int id) {
        if (id != 1) {
            centerService.dltJoinUrl(request, id);
            
            dao.delSaasUser(id);
        }
        
        return true;
    }
    
    /**
     * 
     * @Title: getAllUser
     * @Description: 获取所有用户信息
     * @return List<SaasUserBean>
     * @author WWW
     */
    public List<SaasUserBean> getAllUser() {
        
        return dao.getAllUser();
    }
    
    /**
     * 
     * @Title: getAccessUser
     * @Description: 获取接入用户信息除本机
     * @return List<SaasUserBean>
     * @author WWW
     */
    public List<SaasUserBean> getAccessUser() {
        
        return dao.getAccessUser();
    }
    
    /**
     * 
     * @Title: getUserByDelay
     * @Description: 获取服务器通信时延小于某个时延的用户信息
     * @return List<SaasUserBean>
     * @author WWW
     */
    public List<SaasUserBean> getUserByDelay(int delay) {
        
        return dao.getUserByDelay(delay);
    }
    
    /**
     * 
     * @Title: getUserById
     * @Description: 通过编号获取用户信息
     * @param id 编号
     * @return SaasUserBean
     * @author WWW
     */
    public SaasUserBean getUserById(int id) {
        
        return dao.getUserById(id);
    }
}
