/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.word.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.word.bean.ReportHistoryBean;
import com.protocolsoft.word.service.ReportHistoryService;

/**
 * @ClassName: ReportHistoryController
 * @Description: 操作历史报表接口
 * @author 刘斌
 *
 */
@Controller
@RequestMapping(value = "/reporthistoryController")
public class ReportHistoryController {

    /**
     * @Fields reportHistoryService : 报表历史记录
     */
    @Autowired
    private ReportHistoryService reportHistoryService;
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * @Title: getAllHistoryBean
     * @Description: 报表历史记录报表
     * @param request
     * @param response
     * @param starttime
     * @param endtime
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "getAllReportHistory.do")
    @ResponseBody
    public String getAllHistoryBean(HttpServletRequest request, HttpServletResponse response, Long starttime, Long endtime) {
        try {
            if(null ==starttime){
                starttime = 0l;
            }
            if(null == endtime){
                endtime = 99999999999l;
            }
            SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
            JSONArray jsonArray = new JSONArray();
            if(null == systemUserBean){
                return jsonArray.toJSONString();
            }
            List<ReportHistoryBean> listReport = null;
            if(1==systemUserBean.getRoleId()){
                listReport = reportHistoryService.searchAllReportHistory(starttime, endtime);
            }else{
                listReport = reportHistoryService.selectReportHistoryByUserId(systemUserBean.getId());
            }
            if(null!=listReport && listReport.size()>0){
                for (ReportHistoryBean bean : listReport) {
                    JSONObject json = new JSONObject();
                    json.put("id", bean.getId());
                    json.put("name", bean.getName());
                    json.put("createtime", bean.getCreatetime());
                    json.put("sendStatus", bean.getSendStatus());
                    if(null!=bean.getSendTime()){
                        json.put("sendTime", bean.getSendTime());
                    }else{
                        json.put("sendTime", 0);
                    }
                    if(null!=bean.getSendNames() && !"".equals(bean.getSendNames())){
                        json.put("sendNames", bean.getSendNames().substring(0, bean.getSendNames().length()-1));
                    }else{
                        json.put("sendNames", bean.getSendNames());
                    }
                    if(null!=bean.getRecriveNames() && !"".equals(bean.getRecriveNames())){
                        json.put("recriveNames", bean.getRecriveNames().substring(0, bean.getRecriveNames().length()-1));
                    }else{
                        json.put("recriveNames", bean.getRecriveNames());
                    }
                    json.put("typeId", bean.getTypeName());
                    jsonArray.add(json);
                }
            }
            return jsonArray.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Title: deleteReportHistoryById
     * @Description: 删除历史报表
     * @param request
     * @param id
     * @param name
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "deleteReportHistoryById.do")
    @ResponseBody
    public String deleteReportHistoryById(HttpServletRequest request, int id, String name, HttpServletResponse response) {
        String path = HavingTimeProductNewWordController.class.getClassLoader().getResource("report").getPath();
        JSONObject json = new JSONObject();
        SystemUserBean systemUserBean = systemUserService.getSessionUserBean(request);
        if(null == systemUserBean){
            json.put("result", false);
            json.put("msg", "登录失效！请刷新页面或重新登录！");
            return json.toJSONString();
        }
        if(reportHistoryService.deleteReportHistory(id, path, name, systemUserBean)){
            json.put("result", true);
        }else{
            json.put("result", false);
            json.put("msg", "删除报表失败！");
        }
        return json.toJSONString();
    }
}
