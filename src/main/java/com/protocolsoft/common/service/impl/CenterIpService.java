/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CenterIpService.java
 *创建人: www    创建时间: 2018年3月26日
 */
package com.protocolsoft.common.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.common.bean.CenterIpBean;
import com.protocolsoft.common.bean.CenterParamBean;
import com.protocolsoft.common.bean.SaasUserBean;
import com.protocolsoft.common.dao.CenterIpDao;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.BeanUtils;
import com.protocolsoft.utils.DateUtils;

/**
 * @ClassName: CenterIpService
 * @Description: 接入服务器业务
 * @author www
 *
 */
@Service
public class CenterIpService {
    
    /**
     * 远程标识
     */
    public final static String REMOTEID = "AEA0BB86-F69E-4D11-B618-3541C6C3A991";
    
    /**
     * 接入服务器的最大时延
     */
    public final static int MAX_DELAY = 2000;

    /**
     * DAO
     */
    @Autowired
    private CenterIpDao dao;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 权限
     */
    @Autowired
    private AuthorizeJurisService jurisService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 用户信息
     */
    @Autowired
    private SaasUserService saasUserService;
    
    /**
     * 
     * @Title: getAllAccessInfo
     * @Description: 获取所有的IP接入信息
     * @return List<CenterIpBean>
     * @author www
     */
    public List<CenterIpBean> getAllAccessInfo() {
        
        return dao.getAllAccessInfo();
    }
    
    /**
     * 
     * @Title: getCenterById
     * @Description: 或企业Center信息
     * @param id 编号
     * @return CenterIpBean
     * @author www
     */
    public CenterIpBean getCenterById(int id) {
        
        return dao.getAllAccessInfoById(id);
    }

    /**
     * @Title: addUrlInfo
     * @Description: 添加ip
     * @param bean    设定文件
     * @return void    返回类型
     * @author 小王
     */
    public boolean addUrlInfo(HttpServletRequest request, CenterIpBean bean) {
        dao.addUrlInfo(bean);
        
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("添加"+bean.getName()+"XPM服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        return true;
    }

    /**
     * @Title: dltjoinUrl
     * @Description: 删除
     * @param id
     * @return boolean    返回类型
     * @author 小王
     */
    public boolean dltJoinUrl(HttpServletRequest request, int id) {
        CenterIpBean bean  = dao.getAllAccessInfoById(id);
        boolean bool = false;
        if (id != 1) {
            bool = dao.dltJoinUrl(id);
            jurisService.deleteCenter(id);
        }
        
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("删除"+bean.getName()+"XPM服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        return bool;
    }

    /**
     * @Title: updateJoinUrl
     * @Description: 修改
     * @param bean
     * @return boolean    返回类型
     * @author 小王
     */
    public boolean updateJoinUrl(HttpServletRequest request, CenterIpBean bean) {
        //得到当前用户信息
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("修改"+bean.getName()+"XPM服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        return dao.updateJoinUrl(bean);
    }
    
    /**
     * 
     * @Title: remoteMethod
     * @Description: 获取远程数据方法
     * @param request
     * @return String
     * @author www
     */
    public String remoteMethod(HttpServletRequest request) {
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        String centerId = request.getParameter("ipmCenterId");
        String url = request.getAttribute("remoteUrl").toString();
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<?> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if ("ipmCenterId".equals(paraName)) {
                continue;
            }
            params.put(paraName, request.getParameter(paraName));
        }
        int cid = Integer.parseInt(centerId);
        if (userBean != null) {
            if (userBean.getRoleId() != 1) {
                String moduleTmp = request.getParameter("moduleId");
                int moduleId = Integer.parseInt(moduleTmp);
                String ids = jurisService.selectModuleRole(userBean.getId(), moduleId, cid);
                params.put("remoteBusiIds", ids);
            }
        }
        String result = this.getRemoteData(params, cid, url);
        
        return result;
    }
    
    /**
     * 
     * @Title: getRemoteData
     * @Description: 获取远程数据
     * @param params 参数
     * @param centerId 远程编号
     * @param url 访问URL
     * @return String
     * @author www
     */
    public String getRemoteData(Map<String, Object> params, int centerId, String url) {
        String data = null;
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        // 远程标识
        params.put("remoteId", REMOTEID);
        StringBuilder result = null;
        CenterIpBean bean = dao.getAllAccessInfoById(centerId);
        url = "http://" + bean.getIp() + ":" + bean.getPort() + url;
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        BufferedReader bufRead = null;
        String parameters = getParamString(params);
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("contentType", "UTF-8");
            httpURLConnection.getOutputStream().write(parameters.getBytes());  
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();
            is = httpURLConnection.getInputStream();
            bufRead = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String lineStr = null;
            result = new StringBuilder();
            while ((lineStr = bufRead.readLine()) != null) {
                result.append(lineStr);
            }
            data = result.toString();
        } catch (IOException e) { } finally {
            try {
                if (bufRead != null) {
                    bufRead.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) { } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getRemoteSessionList
     * @Description: 获取会话数据
     * @param url URL
     * @param bean 参数Bean
     * @param cla 实体Bean
     * @param centerId Center编号
     * @return List<? super CenterParamBean>
     * @author www
     */
    public <T> List<Object> getRemoteSessionList(String url, T bean, 
            Class<? extends CenterParamBean> cla, Integer centerId) {
        List<Object> dataBean = new ArrayList<Object>();
        Map<String, Object> params = BeanUtils.beanToMap(bean);
        String data = null;
        SaasUserBean cbean = null;
        if (centerId != null) {
            cbean = saasUserService.getUserById(centerId);
            data = this.getRemoteData(params, centerId, url);
            if (data != null) {
                this.beanStrToList(data, cbean, dataBean, cla);
            }
        } else {
            List<SaasUserBean> center = saasUserService.getAllUser();
            for (int i = 0, len = center.size(); i < len; i ++) {
                cbean = center.get(i);
                data = this.getRemoteData(params, cbean.getId(), url);
                if (data != null) {
                    this.beanStrToList(data, cbean, dataBean, cla);
                }
            }
        }
        
        return dataBean;
    }
    
    /**
     * 
     * @Title: getRemoteWatchpointKpiList
     * @Description: 获取接入IP所有观察点KPI数据列表
     * @param request 请求
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @return String
     * @author WWW
     */
    public String getRemoteWatchpointKpiList(
            HttpServletRequest request, long starttime, long endtime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("moduleId", 10);
        params.put("starttime", starttime);
        params.put("endtime", endtime);
        params.put("ipmCenterId", 1);
        String url = "/commonController/getNpmListRrdData.do";

        String data = this.getRemoteData(params, 1, url);
        
        return data;
    }
    
    /**
     * 
     * @Title: getRemoteDownPlan
     * @Description: 获取所有用户报表计划信息
     * @return List<JSONObject>
     * @author WWW
     */
    public List<JSONObject> getRemoteDownPlan() {
        List<JSONObject> list = new ArrayList<JSONObject>();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ipmCenterId", 1);
        String url = "/ReportPlanController/downPlan.do";
        
        this.strToJson(url, params, list);
        
        return list;
    }
    
    /**
     * 
     * @Title: getParamString
     * @Description: 访问参数整理
     * @param params 参数
     * @return String
     * @author www
     */
    private String getParamString(Map<String, Object> params) {
        String parameters = null;
        StringBuffer bufStr = new StringBuffer();
        for (String str : params.keySet()) {
            bufStr.append(str);
            bufStr.append("=");
            bufStr.append(params.get(str));
            bufStr.append("&");
        }
        parameters = bufStr.substring(0, bufStr.length() - 1);
        
        return parameters;
    }
    
    /**
     * 
     * @Title: beanStrToList
     * @Description: 字符串转为List
     * @param data JSON字符串
     * @param ciBean 用户数据
     * @param dataBean 数据容器
     * @param cla 实体Class
     * @author www
     */
    private void beanStrToList(String data, SaasUserBean ciBean, 
            List<Object> dataBean, Class<? extends CenterParamBean> cla) {
        JSONArray arr = JSONArray.parseArray(data);
        CenterParamBean bean = null;
        JSONObject obj = null;
        if (arr != null) {
            for (int i = 0, len = arr.size(); i < len; i ++) {
                obj = arr.getJSONObject(i);
                bean = JSONObject.toJavaObject(obj, cla);
                bean.setIpmCenterId(ciBean.getId());
                bean.setIpmCenterName(ciBean.getName());
                bean.setContacts(ciBean.getContacts());
                bean.setTelephone(ciBean.getTelephone());
                bean.setEmail(ciBean.getEmail());
                dataBean.add(bean);
            }
        }
    }
    
    /**
     * 
     * @Title: strToJson
     * @Description: 接入用户数据字符串转为JSON
     * @param url 反问路径
     * @param params 参数
     * @param list 数据容器
     * @author WWW
     */
    private void strToJson(String url, Map<String, Object> params, List<JSONObject> list) {
        List<SaasUserBean> center = saasUserService.getUserByDelay(MAX_DELAY);
        SaasUserBean cbean = null;
        JSONObject jsonObj = null;
        JSONArray jsonArr = null;
        String data = null;
        for (int i = 0, len = center.size(); i < len; i ++) {
            cbean = center.get(i);
            data = this.getRemoteData(params, cbean.getId(), url);
            if (data != null) {
                jsonArr = JSONArray.parseArray(data);
                for (int j = 0, jlen = jsonArr.size(); j < jlen; j ++) {
                    jsonObj = jsonArr.getJSONObject(j);
                    jsonObj.put("ipmCenterId", cbean.getId());
                    jsonObj.put("ipmCenterName", cbean.getName());
                    jsonObj.put("ip", cbean.getIp());
                    jsonObj.put("port", cbean.getPort());
                    jsonObj.put("contacts", cbean.getContacts());
                    jsonObj.put("telephone", cbean.getTelephone());
                    jsonObj.put("email", cbean.getEmail());
                    list.add(jsonObj);
                }
            }
        }
    }
}
