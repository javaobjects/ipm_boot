/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.controller;


import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.watchpoint.service.WatchpointService;
import com.protocolsoft.word.bean.AddEmailBean;
import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.bean.ReportTimerDetailBean;
import com.protocolsoft.word.dao.ReportBusinessDao;
import com.protocolsoft.word.dao.ReportEmailDao;
import com.protocolsoft.word.dao.ReportPlanDao;
import com.protocolsoft.word.service.BookService;
import com.protocolsoft.word.service.ReportModalService;
import com.protocolsoft.word.service.ReportPlanService;
import com.protocolsoft.word.service.TimerReportDetailService;
import com.protocolsoft.word.task.JobBean;
import com.protocolsoft.word.task.MyJob;
import com.protocolsoft.word.task.QuartzManager;
import com.protocolsoft.word.util.GetTimeUtil;


/**
 * @ClassName: ReportPlanController
 * @Description: 报表计划控制类
 * @author 刘斌
 *
 */
@Controller
@RequestMapping(value="/ReportPlanController")
public class ReportPlanController {
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    TimerReportDetailService timerReportDetailService;
    
    /**
     * @Fields authorizeJurisService : 权限通类获取工具。
     */
    @Autowired
    private AuthorizeJurisService authorizeJurisService;
    
    /**
     * @Fields reportModalService : 模版Service
     */
    @Autowired
    ReportModalService reportModalService;
    
    /**
     * @Fields reportModalService : commonServiced
     */
    @Autowired
    private CommonService commonService;
    
    /**
     * @Fields reportModalService : reportEmailDao
     */
    @Autowired
    private ReportEmailDao reportEmailDao;
    
    /**
     * @Fields reportModalService : reportBusinessDao
     */
    @Autowired
    private ReportBusinessDao reportBusinessDao;
    /**
     * @Fields watchpointServer : WatchpointServer对象
     */
    @Autowired
    private WatchpointService watchpointServer;

    /**
     * @Fields serverManagementService : 服务端对象
     */
    @Autowired
    private ServerManagementService serverManagementService;

    /**
     * @Fields cliemtService : 客户端端对象
     */
    @Autowired
    private ClientService cliemtService;

    /**
     * @Fields reportPlanService : 计划Service
     */
    @Autowired
    ReportPlanService reportPlanService;
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * @Fields reportHistoryService : 报表生成函数宗
     */
    @Autowired
    private BookService bookService;
    
    /**
     * @Fields reportPlanDao : 访问数据库报表计划
     */
    @Autowired
    ReportPlanDao reportPlanDao;
    
    /**
     * 
     * @Title: insertPlan
     * @Description: 计划添加
     * @param request
     * @param response
     * @param bean
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value="/savePlan.do", method=RequestMethod.POST)
    @ResponseBody
    public String insertPlan(HttpServletRequest request, HttpServletResponse response,
            ReportPlanBean bean) {
        bean.setSendTimes(0);
        bean.setState(2);
        if(null==bean.getCompareToLastOne()){
            bean.setCompareToLastOne(1);
        }
        
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result", false);
            jsonObject.put("msg", "添加失败！请稍后再试！");
            long nowTime = System.currentTimeMillis();
            
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            
            if(null == userBean){
                jsonObject.put("result", false);
                jsonObject.put("msg", "登录失效！请刷新页面或重新登录！");
                return jsonObject.toJSONString();
            }
            
            bean.setUserId(userBean.getId());
            if(null!=bean.getTypeId()&&bean.getTypeId()==1){
                Long endTime = bean.getEndTime();
                if(endTime.compareTo(nowTime/1000)>0){
                    bean.setCreateTime(nowTime/1000);
                    bean.setNextSendTime(endTime);
                    bean.setSendTimes(0);
                    try {
                        result = reportPlanService.insertReportPlan(bean);
                    } catch (Exception e) {
                        return jsonObject.toJSONString();
                    }
                    String idStr = bean.getId()+"";
                    QuartzManager  quartzManager = new QuartzManager();
                    String date = quartzManager.getCron(new Date(endTime*1000));
                    quartzManager.addJob(new JobBean(idStr, idStr, idStr, idStr), MyJob.class, date, bean, bookService, reportPlanService);
                    jsonObject.put("result", result);
                    return jsonObject.toJSONString();
                }else{
                    try {
                        return bookService.userModalProductReport(bean);
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                bean.setCreateTime(nowTime/1000);
                Long nextTime = GetTimeUtil.getNextSendTimeByType(bean.getTypeId());
                bean.setNextSendTime(nextTime/1000);
                try {
                    result = reportPlanService.insertReportPlan(bean);
                } catch (Exception e) {
                    return jsonObject.toJSONString();
                }
                jsonObject.put("result", result);
                return jsonObject.toJSONString();
            }
        } catch (Exception e) {
            jsonObject.put("msg", "操作失败！");
            return jsonObject.toJSONString();
        }
        return jsonObject.toJSONString();
    }
    
    /**
     * 
     * @Title: deletePlan
     * @Description: 计划删除
     * @param request
     * @param response
     * @param id
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/deletePlan.do")
    public String deletePlan(HttpServletRequest request, HttpServletResponse response, Integer id){
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        jsonObject.put("msg", "删除失败！请稍后再试！");
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        try {
            result = reportPlanService.deleteReportPlan(id, userBean);
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
     * @Title: selectAllPlan
     * @Description: 
     * @param request
     * @param response
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/allPlan.do", method=RequestMethod.POST)
    public String selectAllPlan(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        try {
            SystemUserBean userbean = systemUserService.getSessionUserBean(request);
            List<ReportPlanBean> list = null;
            try {
                if (2 == userbean.getRoleId()) {
                    list = reportPlanService.searchAllReportPlan(userbean.getId());
                } else {
                    list = reportPlanService.selectAllBusinessByPlanId();
                }
            } catch (Exception e) {
                json.put("result", false);
                json.put("msg", "登录失效！请刷新页面或重新登录！");
                return json.toJSONString();
            }
            JSONArray jsonArray = new JSONArray();
            for (ReportPlanBean bean : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", bean.getId());
                String name = bean.getName();
                if(name.contains(":")){
                    char[] chars = name.toCharArray();
                    name = name.substring(0, chars.length-19);
                }
                jsonObject.put("name", name);
                jsonObject.put("modalId", bean.getModalId());
                jsonObject.put("nextSendTime", bean.getNextSendTime());
                jsonObject.put("sendTimes", bean.getSendTimes());
                jsonObject.put("createtime", bean.getCreateTime());
                jsonObject.put("typeId", bean.getTypeId());
                jsonObject.put("userName", bean.getUserName());
                jsonObject.put("state", bean.getState());
                jsonObject.put("compareToLastOne", bean.getCompareToLastOne());
                jsonObject.put("modalName", bean.getModalName());
                jsonArray.add(jsonObject);
            }
            return jsonArray.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", false);
            json.put("msg", "操作失败！");
            return json.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: searchPlanById
     * @Description: 
     * @param request
     * @param response
     * @param id
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/searchPlanById.do")
    public String searchPlanById(HttpServletRequest request, HttpServletResponse response, Integer id){
        JSONObject jsonObject = new JSONObject();
        try {
            ReportPlanBean bean = reportPlanService.searchReportPlanBeanById(id);
            jsonObject.put("id", bean.getId());
            String name = bean.getName();
            if(name.contains(":")){
                char[] chars = name.toCharArray();
                name = name.substring(0, chars.length-19);
            }
            jsonObject.put("name", name);
            
            jsonObject.put("modalId", bean.getModalId());
            List<ReportTimerDetailBean> listReport = timerReportDetailService.searchReportTimersDetailById(bean.getModalId());
            Integer watchpointKpiNum = 0;
            Integer clientKpiNum = 0;
            Integer serverKpiNum = 0;
            if(null!= listReport && listReport.size()>0){
                for(ReportTimerDetailBean le : listReport){
                    switch(le.getModuleId()){
                        case 10 :
                            watchpointKpiNum ++;
                            break;
                        case 11 :
                            clientKpiNum ++;
                            break;
                        case 12 :
                            serverKpiNum ++;
                            break;
                        default:
                            break;
                    }
                }
            }
            jsonObject.put("watchpointKpiNum", watchpointKpiNum);
            jsonObject.put("clientKpiNum", clientKpiNum);
            jsonObject.put("serverKpiNum", serverKpiNum);
            jsonObject.put("nextSendTime", bean.getNextSendTime());
            jsonObject.put("sendTimes", bean.getSendTimes());
            jsonObject.put("createtime", bean.getCreateTime());
            jsonObject.put("typeId", bean.getTypeId());
            jsonObject.put("state", bean.getState());
            jsonObject.put("watchpointIds", bean.getWatchpointIds());
            jsonObject.put("startTime", bean.getStartTime());
            jsonObject.put("endtime", bean.getEndTime());
            jsonObject.put("modalName", bean.getModalName());
            jsonObject.put("moduleIds", bean.getModuleIds());
            jsonObject.put("compareToLastOne", bean.getCompareToLastOne());
            List<ReportEmailBean> list = bean.getList();
            if (null != list && list.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (ReportEmailBean beanEmail : list) {
                    JSONObject jsonObjectEmail = new JSONObject();
                    jsonObjectEmail.put("id", beanEmail.getId());
                    jsonObjectEmail.put("sender", beanEmail.getSender());
                    jsonObjectEmail.put("recriver", beanEmail.getRecriver());
                    jsonObjectEmail.put("email", beanEmail.getEmail());
                    jsonArray.add(jsonObjectEmail);
                }
                jsonObject.put("list", jsonArray);
            }
            List<ReportBusinessBean> listBus = bean.getListBus();
            if (null != listBus && listBus.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject11 = new JSONObject();
                jsonObject11.put("moduleId", 11);
                String business11String = "";
                JSONObject jsonObject12 = new JSONObject();
                jsonObject12.put("moduleId", 12);
                String business12String = "";
                for (ReportBusinessBean beanBusiness : listBus) {
                    JSONObject jsonObjectBusiness = new JSONObject();
                    if(0==beanBusiness.getBussinessId()){
                        jsonObject.put("businessIds", 0);
                    }else{
                        jsonObjectBusiness.put("modulId", beanBusiness.getModulId());
                        if(11==beanBusiness.getModulId()){
                            business11String += beanBusiness.getBussinessId() + ",";
                        }else if(12==beanBusiness.getModulId()){
                            business12String += beanBusiness.getBussinessId() + ",";
                        }
                    }
                }
                if(!"".equals(business11String)){
                    business11String = business11String.substring(0, business11String.length()-1);
                    jsonObject11.put("businessIds", business11String);
                    jsonArray.add(jsonObject11);
                }
                if(!"".equals(business12String)){
                    business12String = business12String.substring(0, business12String.length()-1);
                    jsonObject12.put("businessIds", business12String);
                    jsonArray.add(jsonObject12);
                }
                
                jsonObject.put("listBus", jsonArray);
            }
            jsonObject.put("result", true);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            jsonObject.put("result", false);
            jsonObject.put("rmsg", "操作失败！");
            return jsonObject.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: updateStateOfPlan
     * @Description: 
     * @param request
     * @param response
     * @param id
     * @param state
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/updateStateOfPlan.do")
    public String updateStateOfPlan(HttpServletRequest request, HttpServletResponse response, Integer id, Integer state){
        JSONObject json = new JSONObject();
        try {
            boolean result = reportPlanService.updateReportPlanState(id, state);
            json.put("result", result);
            if (!result) {
                json.put("msg", "修改失败！请稍后重试");
            }
            return json.toJSONString();
        } catch (Exception e) {
            json.put("result", false);
            json.put("msg", "操作失败！");
            return json.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: updatePlan
     * @Description: 
     * @param request
     * @param response
     * @param bean
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/updatePlan.do", method=RequestMethod.POST)
    public String updatePlan(HttpServletRequest request, HttpServletResponse response, ReportPlanBean bean){
        if(null==bean.getCompareToLastOne()){
            bean.setCompareToLastOne(1);
        }
        JSONObject json = new JSONObject();
        try {
            long nowTime = System.currentTimeMillis();
            
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            bean.setUserId(userBean.getId());
            
            Integer er = reportPlanDao.searchTypeIdOfPlan(bean.getId());
            if(1==er){
                String name = bean.getName();
                if(name.contains(":")){
                    char[] chars = name.toCharArray();
                    name = name.substring(0, chars.length-19);
                    bean.setName(name);
                }
                try {
                    QuartzManager qm = new QuartzManager();
                    qm.removeJob(bean.getId() + "", bean.getId() + "",
                            bean.getId() + "", bean.getId() + "", 1);
                } catch (Exception e) {
                }
            }
            if(null!=bean.getTypeId()&&bean.getTypeId()==1){
                Long endTime = bean.getEndTime();
                String name = bean.getName();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd~HH:mm:ss");
                name += df.format(new Date());
                bean.setName(name);
                if(endTime.compareTo(nowTime/1000)>0){
                    bean.setCreateTime(nowTime/1000);
                    bean.setNextSendTime(endTime);
                    bean.setSendTimes(0);
                    boolean result = reportPlanService.updateReportPlan(bean);
                    String idStr = bean.getId()+"";
                    QuartzManager   quartzManager = new QuartzManager();
                    String date = quartzManager.getCron(new Date(endTime*1000));
                    quartzManager.addJob(new JobBean(idStr, idStr, idStr, idStr), MyJob.class, date, bean, bookService, reportPlanService);
                    json.put("result", result);
                    json.put("msg", "修改失败！请稍后再尝试。");
                    return json.toJSONString();
                }else{
                    try {
                        String result = bookService.userModalProductReport(bean);
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        if((boolean) jsonObject.get("result")){
                            reportPlanService.deleteReportPlan(bean.getId(), userBean);
                        }
                        return result;
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                bean.setCreateTime(nowTime/1000);
                Long nextTime = GetTimeUtil.getNextSendTimeByType(bean.getTypeId());
                bean.setNextSendTime(nextTime/1000);
                boolean result = reportPlanService.updateReportPlan(bean);
                json.put("result", result);
                json.put("msg", "修改失败！请稍后再尝试。");
                return json.toJSONString();
            }
        } catch (Exception e) {
            json.put("result", false);
            json.put("msg", "操作失败！");
            return json.toJSONString();
        }
        return null;
    }
    
    /**
     * 
     * @Title: updatePlan
     * @Description: 
     * @param request
     * @param response
     * @param bean
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/downPlan.do")
    public String getAllPlanInfo(){
        JSONArray jsonArray = new JSONArray();
        try {
            List<ReportPlanBean> list = reportPlanService.selectAllBusinessByPlanId();
            
            for (ReportPlanBean bean : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", bean.getId());
                String name = bean.getName();
                if(name.contains(":")){
                    char[] chars = name.toCharArray();
                    name = name.substring(0, chars.length-19);
                }
                jsonObject.put("name", name);
                jsonObject.put("modalId", bean.getModalId());
                jsonObject.put("nextSendTime", bean.getNextSendTime());
                jsonObject.put("sendTimes", bean.getSendTimes());
                jsonObject.put("createtime", bean.getCreateTime());
                jsonObject.put("typeId", bean.getTypeId());
                jsonObject.put("userName", bean.getUserName());
                jsonObject.put("state", bean.getState());
                jsonObject.put("compareToLastOne", bean.getCompareToLastOne());
                jsonObject.put("modalName", bean.getModalName());
                jsonArray.add(jsonObject);
            }
            return jsonArray.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonArray.toJSONString();
        }
    }
    
    /**
     * @Title: updatePlan
     * @Description: 
     * @param request
     * @param response
     * @param bean
     * @return String
     * @author 刘斌
     */
    @ResponseBody
    @RequestMapping(value="/addEmail.do", method=RequestMethod.POST)
    public String addEmailInfo(AddEmailBean bean){
        List<ReportEmailBean> list = bean.getList();
        JSONObject json = new JSONObject();
        try {
            boolean math = reportPlanService.addEmailByEmailId(list);
            json.put("result", math);
            return json.toJSONString();
        } catch (Exception e) {
            json.put("result", false);
            return json.toJSONString();
        }
    }
    
}
