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
import com.protocolsoft.email.bean.QywxBean;
import com.protocolsoft.email.service.QywxService;

/**
 * 
 * @ClassName: QywxController
 * @author 刘斌
 *
 */
@RequestMapping(value = "/Qywx")
@Controller
public class QywxController {
    
    @Autowired
    private QywxService qywxService;
    
    /**
     * 
     * @Title: getQywxCtrl
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "/getQywxCtrl.do")
    @ResponseBody
    public String getQywxCtrl(){
        JSONObject json = new JSONObject();
        json.put("result", false); 
        JSONObject jsonBean = new JSONObject();
        try {
            QywxBean qywx = qywxService.getQywx();
            if (null != qywx) {
                jsonBean.put("qywxId", qywx.getQywxId());
                jsonBean.put("qywxAppAgentId", qywx.getQywxAppAgentId());
                jsonBean.put("qywxAppSecret", qywx.getQywxAppSecret());
                jsonBean.put("qywxDepId", qywx.getQywxDepId());
                jsonBean.put("qywxUsers", qywx.getQywxUsers());
                json.put("qywxBean", jsonBean);
                json.put("result", true);
            }else{
                json.put("msg", "获取企业微信设置信息失败！"); 
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
     * @Title: updateQywx
     * @return String
     * @author 刘斌
     */
    @RequestMapping(value = "/updateQywxCtrl.do", method=RequestMethod.POST)
    @ResponseBody
    public String updateQywx(QywxBean bean){
        JSONObject json = new JSONObject();
        int n = qywxService.updateQywx(bean);
        if(n == 0){
            json.put("result", 0);
        }else{
            json.put("result", 1);
        }
        return json.toJSONString();
    }
}
