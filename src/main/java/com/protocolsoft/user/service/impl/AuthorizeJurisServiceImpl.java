/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserAuthorizeServiceImpl
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.SaasUserBean;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.common.service.impl.SaasUserService;
import com.protocolsoft.depthanaly.bean.IpmBusTagBean;
import com.protocolsoft.depthanaly.service.IpmBusTagService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.url.bean.UrlSetBean;
import com.protocolsoft.url.service.UrlService;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.AuthorizeJurisDao;
import com.protocolsoft.user.dao.AuthorizeModuleDao;
import com.protocolsoft.user.dao.SystemUserDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.view.bean.MonitorViewBean;
import com.protocolsoft.view.dao.MonitorViewDao;
import com.protocolsoft.view.service.MonitorViewService;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;


/**
 * UserAuthorizeServiceImpl
 * 2017年9月4日 上午10:01:27
 * @author long
 * @version
 * @see
 */
@Service
public class AuthorizeJurisServiceImpl implements AuthorizeJurisService {

    /**
     * userAuthorizeDao注入
     */
    @Autowired(required=false)
    private AuthorizeJurisDao userAuthorizeDao;
    
    /**
     * 用户业务
     */
    @Autowired
    private SaasUserService saasUserService;
    
    /**
     * monitorViewService注入
     */
    @Autowired(required = false)
    private MonitorViewService monitorViewService;
    
    /**
     * 业务
     */
    @Autowired
    private AppService appService;
    
    /**
     *IpmBusTagService
     */
    @Autowired
    private IpmBusTagService ipmBusTagService;
    
    /**
     * 业务
     */
    @Autowired
    private CenterIpService centerIpService;
    
    /**
     * 服务端管理业务对象
     */
    @Autowired
    private ServerManagementService serverManagementService;
    
    /**
     * monitorViewDao注入
     */
    @Autowired(required=false)
    private MonitorViewDao monitorViewDao;
    
    /**
     * userDao注入
     */
    @Autowired(required=false)
    private SystemUserDao userDao;
    
    /**
     * WatchpointDao 对象
     */
    @Autowired
    WatchpointDao watchpointBeanMapper;
    
    /**
     * clientService注入
     */
    @Autowired(required = false)
    private ClientService clientService;
    
    /**
     * 注入 AuthorizeModuleDao
     */
    @Autowired
    AuthorizeModuleDao authorizeModuleDao;
    
    /**
     * URL业务
     */
    @Autowired
    private UrlService service;

    @Override
    public List<AuthorizeJurisBean> getUserAuthorizeBean(int userId) {
        return userAuthorizeDao.getUserAuthorizeBean(userId);
    }

    @Override
    public List<AuthorizeJurisBean> getSessionUserAuthorizeBean(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<AuthorizeJurisBean> userAuthorizeList=(List<AuthorizeJurisBean>) session.getAttribute("userAuthorizeList");
        return userAuthorizeList;
    }

    @Override
    public List<JurisModuleBean> getJurisModuleList(int userId, String requestType, String changeUser) {
        List<JurisModuleBean> jurisModuleList = userAuthorizeDao.getJurisModuleList();
        List<JurisModuleBean> jurisModuleListReturn = new ArrayList<JurisModuleBean>();
        JurisModuleBean jurisModuleBean = null;
        List<Object> dataList = new ArrayList<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        SystemUserBean systemUserBean = userDao.getUserBeanById(userId);
        if (requestType != null && "add".equals(requestType)){   //添加用户权限
            for (int i = 0; i < jurisModuleList.size(); i++){
                int id=jurisModuleList.get(i).getId();
                if (id == 107){ //驾驶舱
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<MonitorViewBean> monitorViewBeanList = monitorViewDao.getMonitorViewAll();
                    jurisModuleList.get(i).setMonitorViewBeanList(monitorViewBeanList);
                    
                } else if (id == 10) { //观察点
                    dataList = new ArrayList<Object>();
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(2);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<WatchpointBean>   list = watchpointBeanMapper.getFindAll(); //查询所有观察点
                    for(int g = 0; g < list.size(); g++){ 
                        dataList.add(list.get(g));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    List<Map<String, String>> center = centerList(null, listCentrtIp, "/watchpointController/getFindAll.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                    
                } else if (id == 11) { //客户端
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(3);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean> client = clientService.getClient();   //查询所有客户端
                    for(int j = 0; j < client.size(); j++){
                        dataList.add(client.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    params.put("moduleId", "11");
                    List<Map<String, String>> center =centerList(params, listCentrtIp, "/client/getClient.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                } else if (id == 12) { //服务端
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    dataList = new ArrayList<Object>();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(4);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean>  server = serverManagementService.getAllServerSide(12); //查询所有服务端
                    for(int j = 0; j < server.size(); j++){
                        dataList.add(server.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    List<Map<String, String>> center = centerList(null, listCentrtIp, "/serverManagement/getAllServerSide.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                } else if (id == 4) { //http
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(5);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean>  http = appService.getAllAppByModuleId(4);  // 查询所有http
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    for(int j = 0; j < http.size(); j++){
                        dataList.add(http.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    params.put("moduleId", "4");
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                } else if (id == 5) { //oracle
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(6);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean>  oracle = appService.getAllAppByModuleId(5);  // 查询所有oracle
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    for(int j = 0; j < oracle.size(); j++){
                        dataList.add(oracle.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    params.put("moduleId", "5");
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                } else if (id == 6) { //mysql
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(7);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean> mysql = appService.getAllAppByModuleId(6);  // 查询所有mysql
                    
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    for(int j = 0; j < mysql.size(); j++){
                        dataList.add(mysql.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    params.put("moduleId", "6");
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                } else if (id == 7) { //sql server
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(8);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<AppBusinessBean> sqlserver = appService.getAllAppByModuleId(7);  // 查询所有sqlserver
                    
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    for(int j = 0; j < sqlserver.size(); j++){
                        dataList.add(sqlserver.get(j));
                    }
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    params.put("moduleId", "7");
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                     
                } else if (id == 8) {  //url
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(9);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<UrlSetBean>  urlList = service.selectUrl();
                    params =new HashMap<String, Object>();
                    dataList = new ArrayList<Object>();
                    for(int j = 0; j < urlList.size(); j++){
                        dataList.add(urlList.get(j));
                    }
                    params.put("moduleId", "8");
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/url/get.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                    
                } else if (id == 9) { //报文
                    AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                    authorizeJurisBean.setChecked(0);
                    jurisModuleList.get(i).setMonitorId(10);
                    jurisModuleListReturn.add(jurisModuleList.get(i));
                    List<IpmBusTagBean> list = ipmBusTagService.selectAll();
                    dataList = new ArrayList<Object>();
                    params =new HashMap<String, Object>();
                    for(int j = 0; j < list.size(); j++){
                        dataList.add(list.get(j));
                    }
                    params.put("moduleId", "9");
                    List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                    List<Map<String, String>> center = centerList(params, listCentrtIp, "/depthanaly/selectAll.do");
                    for(int c = 0; c < center.size(); c++){
                        dataList.add(center.get(c));
                    }
                    jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                    
                }else if (id > 100 && id < 107) {
                    //过滤 流量存储  拓扑图
                    String moduleName = jurisModuleList.get(i).getNamezh();
                    String isopen =null;
                    if(moduleName.equals("流量存储")){
                        moduleName = "流量储存";
                        isopen = authorizeModuleDao.selectByIsopen(moduleName);
                        if(isopen.equals("1")){
                            AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                            authorizeJurisBean.setChecked(0);
                            jurisModuleListReturn.add(jurisModuleList.get(i));
                        }
                    }else if(moduleName.equals("拓扑图")){
                        moduleName = "拓扑图";
                        isopen = authorizeModuleDao.selectByIsopen(moduleName);
                        if(isopen.equals("1")){
                            AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                            authorizeJurisBean.setChecked(0);
                            jurisModuleListReturn.add(jurisModuleList.get(i));
                        }
                    }else{
                        AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                    }
                }
            }
        } else if (requestType != null && "update".equals(requestType)){//修改用户权限
            for (int i = 0; i < jurisModuleList.size(); i++){
                int id=jurisModuleList.get(i).getId();
                List<AuthorizeJurisBean> authorizeJurisBeanList = null;
                if (changeUser == null) {
                    authorizeJurisBeanList= userAuthorizeDao.getUserAuthorizeByModuleList(userId, id);
                } 
                AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
                if (authorizeJurisBeanList == null || authorizeJurisBeanList.size() == 0){ //当前用户没有权限
                    if (id == 107){ //驾驶舱
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<MonitorViewBean> monitorViewBeanList = monitorViewDao.getMonitorViewAll();
                        jurisModuleList.get(i).setMonitorViewBeanList(monitorViewBeanList);
                        
                    } else if (id == 10){ //观察点
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(2);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<WatchpointBean> list = watchpointBeanMapper.getFindAll(); //查询所有观察点
                        for(int g = 0; g < list.size(); g++){ 
                            dataList.add(list.get(g));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        List<Map<String, String>> center = centerList(null, listCentrtIp, "/watchpointController/getFindAll.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 11){//客户端
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(3);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        dataList = new ArrayList<Object>();
                        params = new HashMap<String, Object>();
                        List<AppBusinessBean> client= clientService.getClient();   //查询所有客户端
                        for(int g = 0; g < client.size(); g++){ 
                            dataList.add(client.get(g));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        params.put("moduleId", "11");
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/client/getClient.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 12){//服务端
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(4);
                        dataList = new ArrayList<Object>();
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<AppBusinessBean> server = serverManagementService.getAllServerSide(12); //查询所有服务端
                        for(int g = 0; g < server.size(); g++){ 
                            dataList.add(server.get(g));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        List<Map<String, String>> center = centerList(null, listCentrtIp, "/serverManagement/getAllServerSide.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 4){ // http
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(5);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<AppBusinessBean> http = appService.getAllAppByModuleId(4);  // 查询所有http
                        params = new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        for(int j = 0; j < http.size(); j++){
                            dataList.add(http.get(j));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        params.put("moduleId", "4");
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 5){ //oracle
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(6);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<AppBusinessBean>  oracle = appService.getAllAppByModuleId(5);  // 查询所有oracle
                        params = new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        for(int j = 0; j < oracle.size(); j++){
                            dataList.add(oracle.get(j));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        params.put("moduleId", "5");
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 6){//mysql
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(7);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<AppBusinessBean> mysql = appService.getAllAppByModuleId(6);  // 查询所有mysql
                        params = new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        for(int j = 0; j < mysql.size(); j++){
                            dataList.add(mysql.get(j));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        params.put("moduleId", "6");
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 7){ //sql server
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(8);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<AppBusinessBean> sqlserver = appService.getAllAppByModuleId(7);  // 查询所有sqlserver
                        params = new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        for(int j = 0; j < sqlserver.size(); j++){
                            dataList.add(sqlserver.get(j));
                        }
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        params.put("moduleId", "7");
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    } else if (id == 8) {// url
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(9);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<UrlSetBean>  urlList = service.selectUrl();
                        dataList = new ArrayList<Object>();
                        params = new HashMap<String, Object>();
                        for(int j = 0; j < urlList.size(); j++){
                            dataList.add(urlList.get(j));
                        }
                        params.put("moduleId", "8");
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/url/get.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    }else if (id == 9) {//报文
                        authorizeJurisBean = new AuthorizeJurisBean();
                        authorizeJurisBean.setChecked(0);
                        jurisModuleList.get(i).setMonitorId(10);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<IpmBusTagBean> list = ipmBusTagService.selectAll();
                        dataList = new ArrayList<Object>();
                        params = new HashMap<String, Object>();
                        for(int j = 0; j < list.size(); j++){
                            dataList.add(list.get(j));
                        }
                        params.put("moduleId", "9");
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/depthanaly/selectAll.do");
                        for(int c = 0; c < center.size(); c++){
                            dataList.add(center.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    }else if (id > 100 && id < 107) {
                        //过滤 流量存储  拓扑图
                        String moduleName = jurisModuleList.get(i).getNamezh();
                        String isopen =null;
                        if(moduleName.equals("流量存储")){
                            moduleName = "流量储存";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                authorizeJurisBean=new AuthorizeJurisBean();
                                authorizeJurisBean.setChecked(0);
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else if(moduleName.equals("拓扑图")){
                            moduleName = "拓扑图";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                authorizeJurisBean=new AuthorizeJurisBean();
                                authorizeJurisBean.setChecked(0);
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else{
                            authorizeJurisBean=new AuthorizeJurisBean();
                            authorizeJurisBean.setChecked(0);
                            jurisModuleListReturn.add(jurisModuleList.get(i));
                        }
                    }
                } else {
                    if (id == 107){ //驾驶舱
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        List<MonitorViewBean> monitorViewList=monitorViewDao.getMonitorViewAll();
                        //选中
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            for (int m = 0; m < monitorViewList.size(); m++) {
                                if (authorizeJurisBeanList.get(j).getAppId() == monitorViewList.get(m).getId()) {
                                    monitorViewList.get(m).setChecked(1);
                                }
                            }
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(monitorViewList);
                        
                    } else if (id == 10) { //观察点
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(2);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        dataList = new ArrayList<Object>();
                        //查询所有观察点
                        List<WatchpointBean>   list=watchpointBeanMapper.getFindAll(); 
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //查询所有远端放回的观察点
                        List<Map<String, String>> center =centerList(null, listCentrtIp, "/watchpointController/getFindAll.do");
                        //获取所有选中的远端观察点
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //本地观察点选中
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<list.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == list.get(g).getId()){
                                        list.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地观察点，放到一个统一集合
                        for(int k = 0; k < list.size(); k++){
                            dataList.add(list.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                        
                    } else if (id == 11) { //客户端
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(3);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        dataList = new ArrayList<Object>();
                        params =new HashMap<String, Object>();
                        //查询所有客户端
                        List<AppBusinessBean>  client=clientService.getClient();   
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //查询远端参数
                        params.put("moduleId", "11");
                        //查询远端客户端
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/client/getClient.do");
                        //获取所有选中的远端客户端
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地客户端
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<client.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == client.get(g).getId()){
                                        client.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                       //循环远端和本地客户端，放到一个统一集合
                        for(int k = 0; k < client.size(); k++){
                            dataList.add(client.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                        
                    } else if (id == 12) { //服务端
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(4);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        dataList = new ArrayList<Object>();
                        //查询所有服务端
                        List<AppBusinessBean>  server=serverManagementService.getAllServerSide(12); 
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端 
                        //查询所有远端服务端
                        List<Map<String, String>> center =centerList(null, listCentrtIp, "/serverManagement/getAllServerSide.do");
                        //获取所有选中的远端服务端
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地服务端
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<server.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == server.get(g).getId()){
                                        server.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地服务端，放到一个统一集合
                        for(int k = 0; k < server.size(); k++){
                            dataList.add(server.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                        
                    } else if (id == 4) { //http
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(5);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        params =new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        // 查询所有http
                        List<AppBusinessBean>  http=appService.getAllAppByModuleId(4);  
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //获取远端http 参数
                        params.put("moduleId", "4");
                        //查询所有远端HTTP
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        //获取所有选中的远端http
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地HTTP
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<http.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == http.get(g).getId()){
                                        http.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地HTTP，放到一个统一集合
                        for(int k =0; k < http.size(); k++){
                            dataList.add(http.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                      
                    } else if (id == 5) { //oracle
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(6);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        params =new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        // 查询所有oracle
                        List<AppBusinessBean>  oracle=appService.getAllAppByModuleId(5);  
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //获取远端oracle 参数
                        params.put("moduleId", "5");
                        //查询所有远端oracle
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                       //获取所有选中的远端oracle
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地oracle
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<oracle.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == oracle.get(g).getId()){
                                        oracle.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地oracle，放到一个统一集合
                        for(int k = 0; k < oracle.size(); k++){
                            dataList.add(oracle.get(k));
                        }
                        for(int c = 0; c< centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                       
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                      
                    } else if (id == 6) {//mysql
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(7);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        params =new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        // 查询所有mysql
                        List<AppBusinessBean> mysql= appService.getAllAppByModuleId(6);  
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //获取远端 mysql
                        params.put("moduleId", "6");
                        //查询所有远端mysql
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        //获取所有选中的远端mysql
                        List<Map<String, String>> centerList = centerListBusit(authorizeJurisBeanList, center);
                        //选中本地mysql
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<mysql.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == mysql.get(g).getId()){
                                        mysql.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地mysql，放到一个统一集合
                        for(int k = 0; k < mysql.size(); k++){
                            dataList.add(mysql.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                       
                    } else if (id == 7) { //sql server
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(8);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        params =new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        // 查询所有sqlserver
                        List<AppBusinessBean>  sqlserver=appService.getAllAppByModuleId(7); 
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //获取远端 sqlserver
                        params.put("moduleId", "7");
                        //查询所有远端sqlserver
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/appController/getAllAppByModuleId.do");
                        //获取所有选中的远端sqlserver
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地sqlserver
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<sqlserver.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == sqlserver.get(g).getId()){
                                        sqlserver.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地sqlserver，放到一个统一集合
                        for(int k =0; k<sqlserver.size(); k++){
                            dataList.add(sqlserver.get(k));
                        }
                        for(int c = 0; c<centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                        
                    } else if (id == 8) { //url
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(9);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        params =new HashMap<String, Object>();
                        dataList = new ArrayList<Object>();
                        //查询所有URL
                        List<UrlSetBean>  urlList = service.selectUrl();
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //查询远端URL参数
                        params.put("moduleId", "8");
                        //查询远端URL
                        List<Map<String, String>> center =centerList(params, listCentrtIp, "/url/get.do");
                       //获取所有选中的远端url
                        List<Map<String, String>> centerList =centerListBusit(authorizeJurisBeanList, center);
                        //选中本地url
                        for(int j = 0; j < authorizeJurisBeanList.size(); j++){
                            if(authorizeJurisBeanList.get(j).getCenterId() == 1){
                                for (int g=0; g<urlList.size(); g++) {
                                    if(authorizeJurisBeanList.get(j).getAppId() == urlList.get(g).getId()){
                                        urlList.get(g).setChecked(1);
                                    }
                                }
                            }
                        } 
                        //循环远端和本地url，放到一个统一集合
                        for(int k = 0; k < urlList.size(); k++){
                            dataList.add(urlList.get(k));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList); 
                        
                    } else if (id == 9) { //报文
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleList.get(i).setMonitorId(10);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                        dataList = new ArrayList<Object>();
                        //查询所有报文业务
                        List<IpmBusTagBean> list = ipmBusTagService.selectAll();
                        //查询所有远端
                        List<SaasUserBean> listCentrtIp = saasUserService.getAccessUser(); //查询所有远端
                        //查询远端URL参数
                        params.put("moduleId", "9");
                        //查询远端报文
                        List<Map<String, String>> center = centerList(params, listCentrtIp, "/depthanaly/selectAll.do");
                       //获取所有选中的远端报文
                        List<Map<String, String>> centerList = centerListBusit(authorizeJurisBeanList, center);
                        //选中本地url
                        for(int jl = 0; jl < authorizeJurisBeanList.size(); jl++){
                            for(int k= 0; k < list.size(); k++){
                                if(authorizeJurisBeanList.get(jl).getAppId() == list.get(k).getId()){
                                    list.get(k).setChecked(1);
                                }
                            }
                        }
                        
                        for(int j = 0; j < list.size(); j++){
                            dataList.add(list.get(j));
                        }
                        for(int c = 0; c < centerList.size(); c++){
                            dataList.add(centerList.get(c));
                        }
                        jurisModuleList.get(i).setMonitorViewBeanList(dataList);
                        
                    }else {
                        authorizeJurisBean.setChecked(1);
                        jurisModuleList.get(i).setChecked(1);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                    }
                }
            }
        } else if (requestType != null && "get".equals(requestType.trim())){ //查询用户权限
            if (systemUserBean != null && systemUserBean.getRoleId() == 1){  //管理员查询
                for (int i = 0; i < jurisModuleList.size(); i++){
                    int id=jurisModuleList.get(i).getId();
                    if (id == 107) { //驾驶舱
                        List<MonitorViewBean>  monitorViewBean = monitorViewService.getMonitorViewList(userId, 1);
                        for (int j = 0; j < monitorViewBean.size(); j++) {
                            jurisModuleBean=new JurisModuleBean();
                            jurisModuleBean.setChecked(1);
                            jurisModuleBean.setId(id);
                            jurisModuleBean.setNameen(jurisModuleList.get(i).getNameen());
                            jurisModuleBean.setNamezh(monitorViewBean.get(j).getName());
                            jurisModuleBean.setMonitorId(monitorViewBean.get(j).getId());
                            jurisModuleBean.setUserName(monitorViewBean.get(j).getUserName());
                            jurisModuleListReturn.add(jurisModuleBean);
                        }
                    } else {
                        jurisModuleList.get(i).setChecked(1);
                        switch (jurisModuleList.get(i).getId()) { //默认驾驶舱ID写入monitorId
                            case 13:
                                jurisModuleList.get(i).setMonitorId(1);
                                break;
                            case 10:
                                jurisModuleList.get(i).setMonitorId(2);
                                break;
                            case 11:
                                jurisModuleList.get(i).setMonitorId(3);
                                break;
                            case 12:
                                jurisModuleList.get(i).setMonitorId(4);
                                break;
                            case 4:
                                jurisModuleList.get(i).setMonitorId(5);
                                break;
                            case 5:  
                                jurisModuleList.get(i).setMonitorId(6);
                                break;
                            case 6:
                                jurisModuleList.get(i).setMonitorId(7);
                                break;
                            case 7:
                                jurisModuleList.get(i).setMonitorId(8);
                                break;
                            case 8:
                                jurisModuleList.get(i).setMonitorId(9);
                                break;
                            case 9:
                                jurisModuleList.get(i).setMonitorId(10);
                                break;    
                            default:
                                break;
                        }
                        //过滤 流量存储  拓扑图
                        String moduleName = jurisModuleList.get(i).getNamezh();
                        String isopen =null;
                        if(moduleName.equals("流量存储")){
                            moduleName = "流量储存";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else if(moduleName.equals("拓扑图")){
                            moduleName = "拓扑图";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else{
                            jurisModuleListReturn.add(jurisModuleList.get(i));
                        }
                    }
                }
                return jurisModuleListReturn;
            }
            for (int i = 0; i < jurisModuleList.size(); i++){   //普通用户
                int id=jurisModuleList.get(i).getId();
                List<AuthorizeJurisBean> authorizeJurisBean = userAuthorizeDao.getUserAuthorizeByModuleList(userId, id);
                if (authorizeJurisBean.size() > 0){
                    if (id == 107){ //驾驶舱
                        for(int j = 0; j < authorizeJurisBean.size(); j++){
                            MonitorViewBean monitorViewBean=monitorViewDao.
                                     getMonitorViewListById(authorizeJurisBean.get(j).getAppId());
                            if (monitorViewBean!=null){
                                jurisModuleBean=new JurisModuleBean();
                                jurisModuleBean.setChecked(1);
                                jurisModuleBean.setId(id);
                                jurisModuleBean.setNameen(jurisModuleList.get(i).getNameen());
                                jurisModuleBean.setNamezh(monitorViewBean.getName());
                                jurisModuleBean.setMonitorId(monitorViewBean.getId());
                                jurisModuleBean.setUserName(monitorViewBean.getUserName());
                                jurisModuleListReturn.add(jurisModuleBean);
                            }
                        }                        
                    } else {
                        jurisModuleList.get(i).setChecked(1);
                        switch (jurisModuleList.get(i).getId()) {   //默认驾驶舱ID写入monitorId
                            case 10:
                                jurisModuleList.get(i).setMonitorId(2);
                                break;
                            case 11:
                                jurisModuleList.get(i).setMonitorId(3);
                                break;
                            case 12:
                                jurisModuleList.get(i).setMonitorId(4);
                                break;
                            case 4:
                                jurisModuleList.get(i).setMonitorId(5);
                                break;
                            case 5:  
                                jurisModuleList.get(i).setMonitorId(6);
                                break;
                            case 6:
                                jurisModuleList.get(i).setMonitorId(7);
                                break;
                            case 7:
                                jurisModuleList.get(i).setMonitorId(8);
                                break;
                            case 8:
                                jurisModuleList.get(i).setMonitorId(9);
                                break;
                            case 9:
                                jurisModuleList.get(i).setMonitorId(10);
                                break;    
                            default:
                                break;
                        }
                      //过滤 流量存储  拓扑图
                        String moduleName = jurisModuleList.get(i).getNamezh();
                        String isopen =null;
                        if(moduleName.equals("流量存储")){
                            moduleName = "流量储存";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else if(moduleName.equals("拓扑图")){
                            moduleName = "拓扑图";
                            isopen = authorizeModuleDao.selectByIsopen(moduleName);
                            if(isopen.equals("1")){
                                jurisModuleListReturn.add(jurisModuleList.get(i));
                            }
                        }else{
                            jurisModuleListReturn.add(jurisModuleList.get(i));
                        }
                    }
                } else {
                    if(id != 107){
                        jurisModuleList.get(i).setChecked(0);
                        jurisModuleListReturn.add(jurisModuleList.get(i));
                    }
                }
                if (userId<=0 && id==7){
                    List<MonitorViewBean> monitorViewIdsList = monitorViewDao.getMonitorViewList();
                    if (monitorViewIdsList != null && monitorViewIdsList.size() > 0){
                        jurisModuleList.get(i).setChecked(1);
                        int monitorSize=monitorViewIdsList.size();
                        if (monitorSize>2){
                            monitorSize=3;
                        }
                        for (int j = 0; j < monitorSize; j++){
                            monitorViewIdsList.get(j).setChecked(1);
                            jurisModuleBean=new JurisModuleBean();
                            jurisModuleBean.setChecked(1);
                            jurisModuleBean.setId(id);
                            jurisModuleBean.setNameen(jurisModuleList.get(i).getNameen());
                            jurisModuleBean.setNamezh(monitorViewIdsList.get(j).getName());
                            jurisModuleBean.setMonitorId(monitorViewIdsList.get(j).getId());
                            jurisModuleListReturn.add(jurisModuleBean);
                        }
                    }
                }
            }
        }
        
        return jurisModuleListReturn;
    }

    @Override
    public Map<String, String> delUserAuthorizeByUserId(int userId) {
        Map<String, String> map =new HashMap<String, String>();
        boolean isSuccess = userAuthorizeDao.delUserAuthorizeByUserId(userId);
        if (isSuccess){
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        return map;
    }

    @Override
    public SystemUserBean addAuthorizeJurisBySystemUserBean(SystemUserBean systemUserBean) {
        if (systemUserBean.getModuleIds()!=null &&!"".equals(systemUserBean.getModuleIds().trim())){
            String[] moduleIds= systemUserBean.getModuleIds().split(",");
            Arrays.sort(moduleIds);
            AuthorizeJurisBean userAuthorizeBean=null;
            int j=0;
            for (String module:moduleIds){
                if (module!=null && !"".equals(module.trim())){
                    String[] modules= module.split(":"); //分隔出远端 id 和 业务id
                    if(modules.length > 2){
                        j++;
                        userAuthorizeBean =new AuthorizeJurisBean();
                        userAuthorizeBean.setUserId(systemUserBean.getId());
                        userAuthorizeBean.setModuleId(Integer.parseInt(modules[0]));
                        userAuthorizeBean.setAppId(Integer.parseInt(modules[2]));
                        userAuthorizeBean.setCenterId(Integer.parseInt(modules[1]));
                        userAuthorizeDao.addUserAuthorize(userAuthorizeBean);
                        
                        userAuthorizeBean.setOrder(j);
                        userAuthorizeDao.updateUserAuthorizeByID(userAuthorizeBean);
                    }else{
                        if(Integer.parseInt(modules[0]) < 107 && Integer.parseInt(modules[0]) > 100){
                            userAuthorizeBean =new AuthorizeJurisBean();
                            userAuthorizeBean.setUserId(systemUserBean.getId());
                            userAuthorizeBean.setModuleId(Integer.parseInt(modules[0]));
                            userAuthorizeBean.setCenterId(1);
                            userAuthorizeBean.setAppId(1);
                            userAuthorizeDao.addUserAuthorize(userAuthorizeBean);
                        }else if(Integer.parseInt(modules[0]) != 107){
                            userAuthorizeBean =new AuthorizeJurisBean();
                            userAuthorizeBean.setUserId(systemUserBean.getId());
                            userAuthorizeBean.setModuleId(Integer.parseInt(modules[0]));
                            userAuthorizeBean.setCenterId(1);
                            userAuthorizeBean.setAppId(0);
                            userAuthorizeDao.addUserAuthorize(userAuthorizeBean);
                        }
                    }
                }
            }
        }
        return systemUserBean;
    }
    
    /**
     * 根据模块ID，返回这个模块的权限
     * 2018年2月8日 上午10:24:05
     * @param
     * @return String
     * @exception 
     * @see
     */
    public String selectModuleRole(int userId, int moduleId, int centerId) {
        List<AuthorizeJurisBean> list =userAuthorizeDao.getUserAuthorizeByModuleById(userId, moduleId, centerId);
        String moduleRole=null;
        for(int i =0 ; i<list.size(); i++){
            moduleRole=moduleRole + list.get(i).getAppId()+",";
        }
        if(moduleRole==null){
            moduleRole=0+"";
        }else{
            moduleRole= moduleRole.substring(4, moduleRole.length()-1);
        }
        return moduleRole;
    }
    
    @Override
    public Map<String, String> addAuthorizeJurisByList(List<SystemUserBean> list) {
        Map<String, String> map=new HashMap<String, String>();
        AuthorizeJurisBean authorizeJurisBean =new AuthorizeJurisBean();
        for (SystemUserBean systemUserBean:list){
            authorizeJurisBean=new AuthorizeJurisBean();
            authorizeJurisBean.setUserId(systemUserBean.getId());
            authorizeJurisBean.setModuleId(107);
            authorizeJurisBean.setCenterId(1);
            authorizeJurisBean.setAppId(Integer.parseInt(systemUserBean.getModuleIds()));
            userAuthorizeDao.addUserAuthorize(authorizeJurisBean);
            authorizeJurisBean.setOrder(authorizeJurisBean.getId());
            userAuthorizeDao.updateUserAuthorizeByID(authorizeJurisBean);
        }
        map.put("success", "1");
        return map;
    }
    
    @Override
    public Map<String, String> addAuthorizeJurisByMonitorViewBean(MonitorViewBean monitorViewBean){
        Map<String, String> map=new HashMap<String, String>();
        List<SystemUserBean> list=getSystemUserBean(monitorViewBean);
        addAuthorizeJurisByList(list);
        map.put("success", "1");
        return map;
    }

    @Override
    public List<SystemUserBean> getSystemUserBean(MonitorViewBean monitorViewBean) {
        List<SystemUserBean>list=userDao.getUserListByRoleId(monitorViewBean.getCreateUserId(), monitorViewBean.getRoleId());
        for (int i=0; i<list.size(); i++){
            list.get(i).setModuleIds(monitorViewBean.getId()+"");
        }
        return list;
    }

    @Override
    public AuthorizeJurisBean getUserAuthorizeByModuleId(int userId,
            int moduleId) {
        return userAuthorizeDao.getUserAuthorizeByModuleId(userId, moduleId);
    }
    
    @Override
    public List<AuthorizeJurisBean> getUserAuthorizeByModuleList(int userId,
            int moduleId) {
        return userAuthorizeDao.getUserAuthorizeByModuleList(userId, moduleId);
    }

    @Override
    public AuthorizeJurisBean updateUserAuthorize(AuthorizeJurisBean authorizeJurisBean) {
        int isSuccess=userAuthorizeDao.updateUserAuthorize(authorizeJurisBean);
        if (isSuccess>0){
            authorizeJurisBean.setSuccess("1");
        } else {
            authorizeJurisBean.setSuccess("0");
        }
        return authorizeJurisBean;
    }

    @Override
    public AuthorizeJurisBean updateUserAuthorizeSort(int userId, String status) {
        AuthorizeJurisBean authorizeJurisBean=getUserAuthorizeByModuleId(userId, 7);
        if (authorizeJurisBean!=null){
            authorizeJurisBean.setStatus(status);
            authorizeJurisBean=updateUserAuthorize(authorizeJurisBean);
        } else {
            authorizeJurisBean=new AuthorizeJurisBean();
            authorizeJurisBean.setSuccess("0");
        }
        return authorizeJurisBean;
    }

    /**
     * 
     * @Title: getDefaultView
     * @Description: 默认驾驶舱
     * @return Map<Integer,JurisModuleBean>
     * @author www
     */
    public Map<Integer, JurisModuleBean> getDefaultView() {
        Map<Integer, JurisModuleBean> defBean = new HashMap<Integer, JurisModuleBean>();
        
        JurisModuleBean jurisModuleBean = new JurisModuleBean(); // 观察点
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("观察点");
        jurisModuleBean.setMonitorId(3000000);
        defBean.put(3000000, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // 客户端
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("客户端");
        jurisModuleBean.setMonitorId(3000001);
        defBean.put(3000001, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // 服务端
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("服务端");
        jurisModuleBean.setMonitorId(3000002);
        defBean.put(3000002, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // HTTP
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("HTTP服务");
        jurisModuleBean.setMonitorId(3000003);
        defBean.put(3000003, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // ORACLE
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("ORACLE服务");
        jurisModuleBean.setMonitorId(3000004);
        defBean.put(3000004, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // MYSQL
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("MYSQL服务");
        jurisModuleBean.setMonitorId(3000005);
        defBean.put(3000005, jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // SQLSERVER
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("SQLSERVER服务");
        jurisModuleBean.setMonitorId(3000006);
        defBean.put(3000006, jurisModuleBean);
        
        return defBean;
    }
    
    /**
     * 
     * @Title: addDefaultView
     * @Description: 添加默认驾驶舱
     * @param jurisModuleListReturn void
     * @author www
     */
    public void addDefaultView(List<JurisModuleBean> jurisModuleListReturn) {
        JurisModuleBean jurisModuleBean = new JurisModuleBean(); // 观察点
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("观察点");
        jurisModuleBean.setMonitorId(3000000);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // 客户端
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("客户端");
        jurisModuleBean.setMonitorId(3000001);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // 服务端
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("服务端");
        jurisModuleBean.setMonitorId(3000002);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // HTTP
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("HTTP服务");
        jurisModuleBean.setMonitorId(3000003);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // ORACLE
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("ORACLE服务");
        jurisModuleBean.setMonitorId(3000004);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // MYSQL
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("MYSQL服务");
        jurisModuleBean.setMonitorId(3000005);
        jurisModuleListReturn.add(jurisModuleBean);
        
        jurisModuleBean = new JurisModuleBean(); // SQLSERVER
        jurisModuleBean.setChecked(1);
        jurisModuleBean.setId(7);
        jurisModuleBean.setNameen("view");
        jurisModuleBean.setNamezh("SQLSERVER服务");
        jurisModuleBean.setMonitorId(3000006);
        jurisModuleListReturn.add(jurisModuleBean);
    }
    
   /**
    * 获取远端数据
    * 2018年3月28日 下午3:03:31
    * @param
    * @return List<Map<String,String>>
    * @exception 
    * @see
    */
    public  List<Map<String, String>> centerList (Map<String, Object> params, List<SaasUserBean> listCentrtIp, String url){
        JSONArray jsonArray=null;
        Map<String, String> mList = new HashMap<String, String>();
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        if(listCentrtIp.size() > 0){
            for(int j =0; j<listCentrtIp.size(); j++){
                int centerId= listCentrtIp.get(j).getId();
                if(centerId == 1){
                    
                }else{
                    String centerPush=centerIpService.getRemoteData(params, centerId, url);
                    if(centerPush!=null && !centerPush.equals("")){
                        jsonArray =JSONArray.parseArray(centerPush); 
                        for(int k =0; k<jsonArray.size(); k++){
                            mList = new HashMap<String, String>();
                            mList.put("centerId", centerId+"");
                            JSONObject jsonStr = JSONObject.parseObject(jsonArray.get(k).toString());
                            Set<Entry<String, Object>> entry = jsonStr.entrySet();
                            Iterator<Entry<String, Object>> it = entry.iterator();
                            while(it.hasNext()){
                                Entry<String, Object>  me = it.next();  
                                String keys = me.getKey();
                                if(keys.equals("name")){
                                    String val = me.getValue().toString() +"("+listCentrtIp.get(j).getName()+")"; 
                                    mList.put(keys, val);
                                }else{
                                    mList.put(keys, me.getValue().toString()); 
                                }
                            }
                            dataList.add(mList);
                        } 
                    
                    }
                }
            } 
        }
        return dataList;
    }
    
    /**
     * 修改时远端选中
     * 2018年4月4日 上午10:22:05
     * @param
     * @return List<Map<String,String>>
     * @exception 
     * @see
     */
    public List<Map<String, String>> centerListBusit(List<AuthorizeJurisBean> authorizeJurisBeanList, 
                 List<Map<String, String>> center){
        for(int i =0; i<authorizeJurisBeanList.size(); i++){
            if(authorizeJurisBeanList.get(i).getCenterId()!=1){
                for(int j=0; j<center.size(); j++){
                    if(authorizeJurisBeanList.get(i).getAppId() == Integer.parseInt(center.get(j).get("id"))){
                        if(center.get(j).containsKey("checked")){
                            center.get(j).put("checked", "1");
                        }
                    }
                } 
            }
            
        }
        return center;
    }
    
    /**
     * 远端权限选中
     * 2018年3月29日 下午6:41:31
     * @param
     * @return List<Map<String,String>>
     * @exception 
     * @see
     */
    public  List<Map<String, String>> checkedCenterList (List<Map<String, String>> listCentrtIp, int centrtId, int appId){
        Map<String, String> mList = new HashMap<String, String>();
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        for(int i =0; i<listCentrtIp.size(); i++){
            mList = new HashMap<String, String>();
            Set<Entry<String, String>> entry = listCentrtIp.get(i).entrySet();
            Iterator<Entry<String, String>> it = entry.iterator();
            while(it.hasNext()){
                Entry<String, String>  me = it.next();  
                String keys = me.getKey();
                if(Integer.parseInt(listCentrtIp.get(i).get("centerId")) == centrtId){
                    if(appId == Integer.parseInt(listCentrtIp.get(i).get("id"))){
                        if(keys.equals("checked")){
                            mList.put(keys, "1");
                        }else{
                            mList.put(keys, me.getValue().toString()); 
                        }
                    }else{
                        mList.put(keys, me.getValue().toString()); 
                    }
                }else {
                    mList.put(keys, me.getValue().toString());
                }
            }
            dataList.add(mList);
        }
        return dataList;
    }
    
    
    @Override
    public List<AuthorizeJurisBean> updateUserAuthorizeSortList(int userId, String status, int roleId) {
        List<AuthorizeJurisBean> beanList = new ArrayList<AuthorizeJurisBean>();
        Set<Integer> set = new  HashSet<Integer>(); 
        List<AuthorizeJurisBean> beanSet = new ArrayList<AuthorizeJurisBean>();
        if(roleId == 1 || roleId == 0){
            beanList = new ArrayList<AuthorizeJurisBean>();
        }else{
            beanList = getUserAuthorizeByModuleList(userId, 107); 
        }
        //修改排序参数
        AuthorizeJurisBean authorizeJurisBean = new AuthorizeJurisBean();
        String[]  order = status.split(",");
        int j=0; //排序编号
        for (String orders : order) {
            j++;
            if(roleId == 1 || roleId == 0){
                List<AuthorizeJurisBean> bean = userAuthorizeDao.getUserAdminAuthorizeByModuleList(Integer.parseInt(orders), 107);
                for(int i= 0; i < bean.size(); i++){
                    authorizeJurisBean =new AuthorizeJurisBean();
                    authorizeJurisBean = bean.get(i);
                    authorizeJurisBean.setAppId(Integer.parseInt(orders));
                    authorizeJurisBean.setOrder(j);
                    authorizeJurisBean.setModuleId(authorizeJurisBean.getModuleId());
                    userAuthorizeDao.updateUserAdminAuthorizeByID(authorizeJurisBean);
                    beanSet.add(authorizeJurisBean);
                }
            }else{
                beanList=getUserAuthorizeByModuleList(userId, 107);
                for(int i= 0; i<beanList.size(); i++){
                    authorizeJurisBean =new AuthorizeJurisBean();
                    int appId=beanList.get(i).getAppId();
                    if(appId == Integer.parseInt(orders)){
                        authorizeJurisBean.setAppId(appId);
                        authorizeJurisBean.setOrder(j);
                        authorizeJurisBean.setModuleId(beanList.get(i).getModuleId());
                        authorizeJurisBean.setUserId(userId);
                        userAuthorizeDao.updateUserAuthorizeByID(authorizeJurisBean);
                    }
                }
            }
        }
        for (AuthorizeJurisBean aJurisBean : beanSet) {
            if(set.add(aJurisBean.getAppId())){
                beanList.add(aJurisBean);
            }
        }
        return beanList;
    }
    
    /**
     * 修改MonitorId
     * 2018年3月29日 下午3:10:07
     * @param
     * @return int
     * @exception 
     * @see
     */
    public int getmonitorViewBeanList (String names){
        List<MonitorViewBean> monitorViewBeanList=monitorViewDao.getMonitorViewList();
        int ids=0;
        for(int j=0; j < monitorViewBeanList.size(); j++){
            String name=monitorViewBeanList.get(j).getName();
            if(name.equals(names)){
                ids =monitorViewBeanList.get(j).getId();
            }
        }  
        return ids;
    }

    
    @Override
    public void addUserAuthorize(int userId, int id, int moduleId) {
        AuthorizeJurisBean userAuthorizeBean =new AuthorizeJurisBean();
        userAuthorizeBean.setUserId(userId);
        userAuthorizeBean.setCenterId(1);
        userAuthorizeBean.setAppId(id);
        userAuthorizeBean.setModuleId(moduleId);
        userAuthorizeDao.addUserAuthorize(userAuthorizeBean);
    }

    
    @Override
    public void deleteUserAuthorize(int userId, int id, int moduleId) {
        AuthorizeJurisBean userAuthorizeBean =new AuthorizeJurisBean();
        userAuthorizeBean.setUserId(userId);
        userAuthorizeBean.setCenterId(1);
        userAuthorizeBean.setAppId(id);
        userAuthorizeBean.setModuleId(moduleId);
        userAuthorizeDao.deleteUserAuthorize(userAuthorizeBean);
    }

    @Override
    public void deleteCenter(int centerId) {
        AuthorizeJurisBean userAuthorizeBean =new AuthorizeJurisBean();
        userAuthorizeBean.setCenterId(centerId);
        userAuthorizeDao.deleteCenter(userAuthorizeBean);
    }

    @Override
    public List<AuthorizeJurisBean> getUserAdminAuthorizeModuleList(int moduleId) {
       
        return userAuthorizeDao.getUserAdminAuthorizeModuleList(moduleId);
    }
}
