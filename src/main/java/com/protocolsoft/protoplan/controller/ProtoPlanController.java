/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ProtoPlanController
 *创建人:chensq    创建时间:2017年10月23日
 */
package com.protocolsoft.protoplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.service.ProtoPlanService;
/**
 * 协议Controller
 * 2017年10月23日 上午11:34:10
 * @author chensq
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/protoPlan")
public class ProtoPlanController {
    /**
     * 协议管理业务对象
     */
    @Autowired
    private ProtoPlanService protoPlanService;
    
    /**
     * 获取协议列表
     * 2017年10月23日 上午11:34:10
     * @return List<ProtoPlan>
     * @exception 
     * @see
     */
    @RequestMapping(value="getProtoPlanList.do")
    @ResponseBody 
    public List<ProtoPlanBean> getProtoPlanList(){
        return protoPlanService.getProtoPlanList();        
    }
    
}
