/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CenterIpController.java
 *创建人: www    创建时间: 2018年3月26日
 */
package com.protocolsoft.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.common.bean.CenterIpBean;
import com.protocolsoft.common.service.impl.CenterIpService;

/**
 * @ClassName: CenterIpController
 * @Description: 服务器接入
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/center")
public class CenterIpController {

    /**
     * 业务
     */
    @Autowired
    private CenterIpService service;
    
    /**
     * 
     * @Title: getCenterIpInfo
     * @Description: 获取接入服务器信息
     * @return List<CenterIpBean>
     * @author www
     */
    @RequestMapping(value = "/getCenterIpInfo.do")
    public @ResponseBody List<CenterIpBean> getCenterIpInfo() {
        List<CenterIpBean> list = service.getAllAccessInfo();
        
        return list;
    }
    
    /**
     * 
     * @Title: getCenterById
     * @Description: 获取Center信息
     * @param id 编号
     * @return CenterIpBean
     * @author www
     */
    @RequestMapping(value = "/getCenterById.do")
    public @ResponseBody CenterIpBean getCenterById(int id) {
        
        return service.getCenterById(id);
    }
    
    /**
     * 
     * @Title: addCenterInfo
     * @Description: 添加接入信息
     * @param bean 接入信息
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/addCenterInfo.do")
    public @ResponseBody  Map<String, Boolean> addCenterInfo(HttpServletRequest request, CenterIpBean bean) {
        boolean bool = service.addUrlInfo(request, bean);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: updCenterById
     * @Description: 修改接入信息
     * @param bean center信息
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/updCenterById.do")
    public @ResponseBody Map<String, Boolean> updCenterById(HttpServletRequest request, CenterIpBean bean) {
        boolean bool = service.updateJoinUrl(request, bean);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: delCenterById
     * @Description: 删除接入信息
     * @param id 编号
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/delCenterById.do")
    public @ResponseBody Map<String, Boolean> delCenterById(HttpServletRequest request, int id) {
        boolean bool = service.dltJoinUrl(request, id);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: remoteMethod
     * @Description: 远端数据
     * @param request 请求
     * @return String 数据
     * @author www
     */
    @RequestMapping(value = "/remoteMethod.do")
    public @ResponseBody String remoteMethod(HttpServletRequest request) {
        
        return service.remoteMethod(request);
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
    @RequestMapping(value = "/getRemoteWatchpointKpiList.do")
    public @ResponseBody String getRemoteWatchpointKpiList(
            HttpServletRequest request, long starttime, long endtime) {
        
        return service.getRemoteWatchpointKpiList(request, starttime, endtime);
    }
    
    /**
     * 
     * @Title: getRemoteDownPlan
     * @Description: 获取所有用户报表计划信息
     * @return List<JSONObject>
     * @author WWW
     */
    @RequestMapping(value = "/getRemoteDownPlan.do")
    public @ResponseBody List<JSONObject> getRemoteDownPlan() {
        
        return service.getRemoteDownPlan();
    }
}
