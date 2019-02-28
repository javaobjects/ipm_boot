/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusiProcessingController.java
 *创建人: wangjianmin    创建时间: 2018年6月15日
 */
package com.protocolsoft.datapush.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.protocolsoft.datapush.bean.BusinesBean;
import com.protocolsoft.datapush.service.BusinesService;

/**
 * @ClassName: BusiProcessingController
 * @Description: 数据导出(RRD)
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/dataPush")
public class BusiProcessingController {
    
    /**
     * BusinesService
     */
    @Autowired(required = false)
    private BusinesService  businesService;
  
    /**
     * 
     * @Title: addDataPush
     * @Description: 添加数据推送设置
     * @param bean 接收添加参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @RequestMapping(value="addDataPush.do")
    @ResponseBody 
    public boolean  addDataPush(BusinesBean bean){
        return businesService.addDataPush(bean);
    }
    
    /**
     * 
     * @Title: delDataPush
     * @Description: 删除推送设置
     * @param id   业务id 
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @RequestMapping(value="delDataPush.do")
    @ResponseBody 
    public boolean  delDataPush(int id){
        return businesService.delDataPush(id);
    }
    
    /**
     * 
     * @Title: update
     * @Description: 修改推送设置
     * @param bean 接收修改推送设置参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @RequestMapping(value="updateDataPush.do")
    @ResponseBody 
    public boolean  updateDataPush(BusinesBean bean){
        return businesService.update(bean);
    }
    
    /**
     * 
     * @Title: getAll
     * @Description: 查询所有推送数据设置
     * @return List<BusinesBean>
     * @author wangjianmin
     */
    @RequestMapping(value="getAll.do")
    @ResponseBody 
    public List<BusinesBean>  getAll(HttpServletRequest request) {
        return businesService.getAll();
    }
    /**
     * 
     * @Title: getSelectById
     * @Description: 查询一条业务信息
     * @param id  业务id 
     * @return BusinesKpiBean
     * @author wangjianmin
     */
    @RequestMapping(value="getSelectById.do")
    @ResponseBody 
    public BusinesBean getSelectById(Integer id) {
        
        return businesService.getSelectById(id);
    }
}
