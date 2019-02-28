/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AuthorizeModuleServer
 *创建人:wjm    创建时间:2017年9月8日
 */
package com.protocolsoft.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.user.bean.AuthorizeModuleBean;
import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.bean.SamllModuleBean;
import com.protocolsoft.user.dao.AuthorizeModuleDao;

/**
 * ipm_authorize_module表 server 层
 * 2017年9月8日 下午3:39:48
 * @author wjm
 * @version
 * @see
 */
@Service
public class AuthorizeModuleServer {
    
    /**
     * 注入 AuthorizeModuleDao
     */
    @Autowired
    AuthorizeModuleDao authorizeModuleDao;
    
    /**
     * 查询所有
     * 2017年9月8日 下午3:45:40
     * @param
     * @return List<AuthorizeModuleBean>
     * @exception 
     * @see
     */
    public List<Map<String, String>> selectAllAuthorizeModule(){
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> m = new HashMap<String, String>();
        List<AuthorizeModuleBean> list =authorizeModuleDao.selectAllAuthorizeModule();
        
        for (AuthorizeModuleBean authorizeModuleBean : list) {
            m = new HashMap<String, String>();
            m.put("id", String.valueOf(authorizeModuleBean.getId()));
            m.put("nameen", authorizeModuleBean.getNameen());
            m.put("namezh", authorizeModuleBean.getNamezh());
            dataList.add(m);
        }
        
        return dataList;
    }
    
    /**
     * 查询所有(流量下载使用,不含观察点，url，应用可用性)
     * 2018年3月28日 下午3:45:40
     * @param
     * @return List<AuthorizeModuleBean>
     * @exception 
     * @see
     */
    public List<Map<String, String>> selectAuthorizeModuleForFlowCtl(){
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> m = new HashMap<String, String>();
        List<AuthorizeModuleBean> list =authorizeModuleDao.selectAuthorizeModuleForFlowCtl();
        
        for (AuthorizeModuleBean authorizeModuleBean : list) {
            m = new HashMap<String, String>();
            m.put("id", String.valueOf(authorizeModuleBean.getId()));
            m.put("nameen", authorizeModuleBean.getNameen());
            m.put("namezh", authorizeModuleBean.getNamezh());
            dataList.add(m);
        }
        
        return dataList;
    }
    
    /**
     * 
     * @Title: selectAllFilterAuthorizeModule
     * @Description: 查询过滤掉系统资源消耗模块
     * @return List<AuthorizeModuleBean>
     * @author wangjianmin
     */
    public List<Map<String, String>> selectAllFilterAuthorizeModule(){
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> m = new HashMap<String, String>();
        List<AuthorizeModuleBean> list =authorizeModuleDao.selectAllFilterAuthorizeModule();
        for (AuthorizeModuleBean authorizeModuleBean : list) {
            m = new HashMap<String, String>();
            m.put("id", String.valueOf(authorizeModuleBean.getId()));
            m.put("nameen", authorizeModuleBean.getNameen());
            m.put("namezh", authorizeModuleBean.getNamezh());
            dataList.add(m);
        }
        
        return dataList;
    }
    
    /**
     * 
     * @Title: selectIsopen
     * @Description: 是否支持多个
     * @return Map<String, Boolean>
     * @author wangjianmin
     */
    public Map<String, Boolean> selectIsopen(){
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        List<AuthorizeModuleBean> list = authorizeModuleDao.getIsopen();
        for (AuthorizeModuleBean authorizeModuleBean : list) {
            if(authorizeModuleBean.getIsopen() == 1){
                m.put(authorizeModuleBean.getNameen().toLowerCase(), true);
            }else{
                m.put(authorizeModuleBean.getNameen().toLowerCase(), false);
            }
        }
        return m;
    }
    
    /**
     * 
     * @Title: selectAllModule
     * @Description: 获取所有授权模块（生成产品授权）
     * @return List<AuthorizeModuleBean>
     * @author wjm
     */
    public List<AuthorizeModuleBean> selectAllModule(){
        return authorizeModuleDao.getSelectModule();
    }
    
    /**
     * 
     * @Title: updateByIdModule
     * @Description: 根据模块ID，修改isopen
     * @param moduleId   模块ID
     * @param isopen     是否开启多个
     * @return boolean
     * @author wjm
     */
    public boolean updateByIdModule(int moduleId, boolean isopen){
        return authorizeModuleDao.updateByIdModule(moduleId, isopen);
    }
    
    /**
     * 
     * @Title: getSamllModuleByModuleId
     * @Description: 获取小模块名称
     * @param moduleId 模块编号
     * @return SamllModuleBean
     * @author WWW
     */
    public SamllModuleBean getSamllModuleByModuleId(int moduleId) {
        
        return authorizeModuleDao.getSamllModuleByModuleId(moduleId);
    }
    
    /**
     * 
     * @Title: getAuthorizeAppModule
     * @Description: 获取应用模块信息
     * @return List<JurisModuleBean>
     * @author WWW
     */
    public List<JurisModuleBean> getAuthorizeAppModule() {
        
        return authorizeModuleDao.getAuthorizeAppModule();
    }
}
