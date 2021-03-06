/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UsabilityController
 *创建人:wjm    创建时间:2018年3月16日
 */
package com.protocolsoft.usability.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.usability.bean.UsabilityBean;
import com.protocolsoft.usability.service.UsabilityService;

/**
 * 
 * @ClassName: UsabilityController
 * @Description: 应用可用性Controller
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/usability")
public class UsabilityController {
    
    /**
     * UsabilityService 注入
     */
    @Autowired(required = false)
    private UsabilityService usabilityService;
    /**
     * AlarmSetService 注入
     */
    @Autowired(required = false)
    private AlarmSetService  alarmSetService; 
    
    /**
     * 
     * @Title: getUsability
     * @Description: 查询所有应用可用性设置
     * @return List<UsabilityBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getUsability.do")
    @ResponseBody
    public List<UsabilityBean> getUsability(){
        List<UsabilityBean> list = usabilityService.getUsabilityAll();
        return list;
    }

    /**
     * 
     * @Title: addUsability
     * @Description: 添加应用可用性设置
     * @param request 请求
     * @param bean  接收参数
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/addUsability.do")
    @ResponseBody
    public int addUsability(HttpServletRequest request, UsabilityBean bean){
        int id = 0;
        boolean  isName = usabilityService.getByName(bean.getName(), bean.getId());
        if(!isName){
            id = usabilityService.addUsability(request, bean);
            alarmSetService.addAppBusinessAfter(0, 1, bean.getAppId());
        }else{
            id = 2;
        }
        return id;
    }

    /**
     * 
     * @Title: delUsabilityId
     * @Description: 删除一条记录
     * @param request 请求
     * @param id 业务id
     * @return boolean
     * @author wangjianmin
     */
    @RequestMapping(value = "/delUsabilityId.do")
    @ResponseBody
    public boolean delUsabilityId(HttpServletRequest request, int id) {
        alarmSetService.delAppBusinessAfter(0, 1, id);
        boolean isId = usabilityService.delUsabilityId(request, id);
        return isId;
    }

    /**
     * 
     * @Title: updateUsability
     * @Description: 修改一条记录
     * @param request 请求
     * @param bean   接收参数
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateUsabilityId.do")
    @ResponseBody
    public int updateUsability(HttpServletRequest request, UsabilityBean bean) {
        int isUpdate = 0;
        boolean  isName = usabilityService.getByName(bean.getName(), bean.getId());
        if(!isName){
            isUpdate = usabilityService.updateUsability(request, bean);
        }else{
            isUpdate = 2;
        }
        return isUpdate;
    }

}
