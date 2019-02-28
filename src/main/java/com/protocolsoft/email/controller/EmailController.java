/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.email.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.service.EmailService;

/**
 * 
 * @ClassName: EmailController
 * @author 刘斌
 *
 */
@RequestMapping(value = "/Email")
@Controller
public class EmailController {
    
    /**
     * 
     */
    @Autowired
    private EmailService emailService;
    
    /**
     * 
     * @Title: getEmailCtrl
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "/getEmailCtrl.do")
    @ResponseBody
    public String getEmailCtrl(){
        JSONObject json = new JSONObject();
        json.put("result", false); 
        JSONObject jsonBean = new JSONObject();
        try {
            EmailBean email = emailService.getEmail();
            if(null!=email){
                jsonBean.put("id", email.getId());
                jsonBean.put("emailServer", email.getEmailServer());
                jsonBean.put("emailUserName", email.getEmailUserName());
                jsonBean.put("emailPassword", email.getEmailPassword());
                jsonBean.put("userName", email.getUserName());
                jsonBean.put("emailPort", email.getEmailPort());
                jsonBean.put("emailAuthorCode", email.getEmailAuthorCode());
                json.put("emailBean", jsonBean);
                json.put("result", true);
            }else{
                json.put("msg", "获取email设置信息失败！"); 
            }
            return json.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("msg", "获取程序异常！");
            return json.toJSONString();
        }
    }
    
    /**
     * 
     * @Title: updateEmail
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "/updateEmailCtrl.do", method=RequestMethod.POST)
    @ResponseBody
    public String updateEmail(EmailBean bean){
        JSONObject json = new JSONObject();
        int n = emailService.updateEmail(bean);
        if(n == 2){
            json.put("result", 2);
        }else if(n == 0){
            json.put("result", 0);
        }else{
            json.put("result", 1);
        }
        return json.toJSONString();
    }
}
