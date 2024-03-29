/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.word.bean.ReportModalBean;
import com.protocolsoft.word.bean.ReportModalTableAndWranBean;
import com.protocolsoft.word.bean.ReportTimerDetailBean;
import com.protocolsoft.word.service.ReportModalService;
import com.protocolsoft.word.service.ReportPlanService;

/**
 * @ClassName: ReportModalController
 * @Description: 报表模板的控制接口类
 * @author 刘斌
 *
 */
@Controller
@RequestMapping(value="/ReportModalController")
public class ReportModalController {
    
    /**
     * clientService注入
     */
    @Autowired
    ReportPlanService reportPlanService;
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * @Fields reportModalService : 模版Service
     */
    @Autowired
    ReportModalService reportModalService;
    
    /**
     * 
     * @Title: insertReportModal
     * @Description: 添加模板
     * @param bean
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/saveModal.do", method = RequestMethod.POST)
    @ResponseBody
    public String insertReportModal(ReportModalBean bean, HttpServletRequest request, HttpServletResponse response){
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result", false);
            jsonObject.put("msg", "添加失败！请稍后再试！");
            long nowTime = System.currentTimeMillis();
        
            SystemUserBean userbean = systemUserService.getSessionUserBean(request);
            
            if(null == userbean){
                jsonObject.put("result", false);
                jsonObject.put("msg", "登录失效！请刷新页面或重新登录！");
                return jsonObject.toJSONString();
            }
            bean.setUserId(userbean.getId());
            bean.setCreateTime(nowTime/1000);
            result = reportModalService.insertReportModal(bean);
            jsonObject.put("result", result);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            jsonObject.put("msg", "添加失败，可能是模板名称重复导致！");
            return jsonObject.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: deleteReportModal
     * @Description: 删除模板
     * @param id
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/deleteModal.do", method = RequestMethod.POST)
    @ResponseBody
    public String deleteReportModal(Integer id, HttpServletRequest request, HttpServletResponse response){
        
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        
        if(1==id || 2==id){
            jsonObject.put("msg", "删除失败！系统模板不允许删除！");
            return jsonObject.toJSONString();
        }
        Map<String, Object> map = reportModalService.searchReportModal(id);
        ReportModalBean bean = (ReportModalBean) map.get("bean");
        try {
            SystemUserBean userbean = systemUserService.getSessionUserBean(request);
            if(null == userbean){
                jsonObject.put("result", false);
                jsonObject.put("msg", "登录失效！请刷新页面或重新登录！");
                return jsonObject.toJSONString();
            }
            if(userbean.getRoleId()==1||userbean.getId() == bean.getUserId()){
            }else{
                jsonObject.put("msg", "操作失败，用户权限验证未通过！");
                return jsonObject.toJSONString();
            }
            result = reportModalService.deleteReportModalBean(id, userbean);
            if(result){
                reportPlanService.deleteReportPlanByModalId(id);
            }
            if(result){
                jsonObject.put("msg", "删除成功！"); 
            }
            jsonObject.put("result", result);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            jsonObject.put("msg", "操作失败！");
            return jsonObject.toJSONString();
        }
         
    }
    
    /**
     * 
     * @Title: updateReportModal
     * @Description: 修改模版
     * @param bean
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/updateModal.do", method = RequestMethod.POST)
    @ResponseBody
    public String updateReportModal(ReportModalBean bean, HttpServletRequest request, HttpServletResponse response){
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        jsonObject.put("msg", "修改失败！请稍后再试！");
        if(1==bean.getId() || 2==bean.getId()){
            jsonObject.put("msg", "修改失败！系统模板不允许修改！");
            return jsonObject.toJSONString();
        }
        Map<String, Object> map = reportModalService.searchReportModal(bean.getId());
        
        try {
            SystemUserBean userbean = systemUserService.getSessionUserBean(request);
            if(null == userbean){
                jsonObject.put("result", false);
                jsonObject.put("msg", "登录失效！请刷新页面或重新登录！");
                return jsonObject.toJSONString();
            }
            ReportModalBean beanHistory = (ReportModalBean) map.get("bean");
            bean.setUserName(userbean.getUserName());
            if(userbean.getRoleId() ==1 || userbean.getId() == beanHistory.getUserId()){
            }else{
                jsonObject.put("msg", "操作失败，用户权限验证未通过！");
                return jsonObject.toJSONString();
            }
            bean.setUserId(userbean.getId());
            result = reportModalService.updateReportModal(bean);
            jsonObject.put("result", result);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            jsonObject.put("msg", "操作失败！");
            return jsonObject.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: allReportModal
     * @Description: 模版列表
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/allModal.do")
    @ResponseBody
    public String allReportModal(HttpServletRequest request, HttpServletResponse response){
        return reportModalService.searchAllReportModal();
    }
    
    /**
     * 
     * @Title: searchReportModalById
     * @Description: 通过Id详细查找
     * @param id
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/searchModalById.do", method = RequestMethod.POST)
    @ResponseBody
    public String searchReportModalById(Integer id, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> map = reportModalService.searchReportModal(id);
        @SuppressWarnings("unchecked")
        List<ReportTimerDetailBean> list = (List<ReportTimerDetailBean>) map.get("list");
        ReportModalBean bean = (ReportModalBean) map.get("bean");
        jsonObject.put("id", bean.getId());
        jsonObject.put("name", bean.getName());
        jsonObject.put("description", bean.getDescription());
        JSONArray jsonArray4 = new JSONArray();
        JSONArray jsonArray5 = new JSONArray();
        JSONArray jsonArray6 = new JSONArray();
        for (ReportTimerDetailBean beanQ : list) {
            JSONObject json = new JSONObject();
            json.put("id", beanQ.getId());
            json.put("moduleId", beanQ.getModuleId());
            json.put("plotId", beanQ.getPlotId());
            json.put("plotTypeId", beanQ.getPlotTypeId());
            json.put("watchpointId", beanQ.getWatchpointId());
            if(10==beanQ.getModuleId()){
                jsonArray4.add(json);
            }else if(11==beanQ.getModuleId()){
                jsonArray5.add(json);   
            }else if(12==beanQ.getModuleId()){
                jsonArray6.add(json);
            }
        }
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        jsonObject1.put("KPI", jsonArray4);
        jsonObject2.put("KPI", jsonArray5);
        jsonObject3.put("KPI", jsonArray6);
        @SuppressWarnings("unchecked")
        List<ReportModalTableAndWranBean> listyTableWarning = (List<ReportModalTableAndWranBean>) map.get("listTableWarning");
        for (ReportModalTableAndWranBean beanQ : listyTableWarning) {
            JSONObject json = new JSONObject();
            json.put("id", beanQ.getId());
            json.put("moduleId", beanQ.getModultId());
            json.put("modalId", beanQ.getModalId());
            json.put("tableHaving", beanQ.getTableHaving());
            json.put("warningHaving", beanQ.getWarningHaving());
            if(10==beanQ.getModultId()){
                if(null == beanQ.getTableHaving()){
                    jsonObject1.put("warningHaving", json);
                }else if(null == beanQ.getWarningHaving()){
                    jsonObject1.put("tableHaving", json);
                }
            }else if(11==beanQ.getModultId()){
                if(null == beanQ.getTableHaving()){
                    jsonObject2.put("warningHaving", json);
                }else if(null == beanQ.getWarningHaving()){
                    jsonObject2.put("tableHaving", json);
                }   
            }else if(12==beanQ.getModultId()){
                if(null == beanQ.getTableHaving()){
                    jsonObject3.put("warningHaving", json);
                }else if(null == beanQ.getWarningHaving()){
                    jsonObject3.put("tableHaving", json);
                }
            }
        }
        
        jsonObject.put("10", jsonObject1);
        jsonObject.put("11", jsonObject2);
        jsonObject.put("12", jsonObject3);
        
        return jsonObject.toJSONString();
    }
    
    
}
