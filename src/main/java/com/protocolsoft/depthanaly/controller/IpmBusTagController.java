/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmBusTagController
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.depthanaly.bean.BusTagListBean;
import com.protocolsoft.depthanaly.bean.DeptMsgLogBean;
import com.protocolsoft.depthanaly.bean.IpmBusTagBean;
import com.protocolsoft.depthanaly.service.IpmBusTagService;


/**
 * 
 * @ClassName: IpmBusTagController
 * @Description: 报文交易Controller层
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/depthanaly")
public class IpmBusTagController {
    
    /**
     *IpmBusTagService
     */
    @Autowired
    private IpmBusTagService ipmBusTagService;

    /**
     * 
     * @Title: addBusTag
     * @Description: 添加报文业务
     * @param bean 接收参数
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/addBusTag.do")
    @ResponseBody
    public Map<String, Integer> addBusTag(HttpServletRequest request, BusTagListBean bean){
        return ipmBusTagService.addBusTag(request, bean);
    }

    /**
     * 
     * @Title: deleteBusTag
     * @Description: 删除自定义报文数据
     * @param appId 业务id
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/deleteBusTag.do")
    @ResponseBody
    public Map<String, Integer> deleteBusTag(HttpServletRequest request, int appId){
        return ipmBusTagService.deleteBusTag(request, appId);
    }
  
    /**
     * 
     * @Title: selectAll
     * @Description: 查询所有 报文交易
     * @param request 请求
     * @return List<IpmBusTagBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/selectAll.do")
    @ResponseBody
    public List<IpmBusTagBean>  selectAll(HttpServletRequest request){
        List<IpmBusTagBean> list = ipmBusTagService.selectAll(request);
        return list;
    }
    
    /**
     * 
     * @Title: updateBusTag
     * @Description: 修改报文自定义数据
     * @param bean 接收参数
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateBusTag.do")
    @ResponseBody
    public Map<String, Integer> updateBusTag(HttpServletRequest request, BusTagListBean bean){
        return ipmBusTagService.updateBusTag(request, bean);
    }

    /**
     * 
     * @Title: getPlotData
     * @Description: 报文绘图
     * @param bean 接收绘参数
     * @return PlotDataBean
     * @author wangjianmin
     */
    @RequestMapping(value = "/depthanalyGraphical.do")
    @ResponseBody
    public PlotDataBean getPlotData(PlotParamBean bean) {
        return ipmBusTagService.getPlotData(bean);
    }
    
    /**
     * 
     * @Title: getSimpleData
     * @Description: 获取十字格数据
     * @param bean 接收十字格数据参数
     * @return SimpleDataBean
     * @author www
     */
    @RequestMapping(value = "/getSimpleData.do")
    @ResponseBody
    public  SimpleDataBean getSimpleData(SimpleParamBean bean) {
        return ipmBusTagService.getSimpleData(bean);
    }

    /**
     * 
     * @Title: getData
     * @Description:  查询LOG 表数据
     * @param bean 接收查询参数
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getData.do")
    @ResponseBody
    public List<DeptMsgLogBean> getData(DeptMsgLogBean bean){
        
        return ipmBusTagService.getData(bean);
    }

    /**
     * 
     * @Title: getRemoteData
     * @Description: 查询LOG 表数据
     * @param bean 接收参数
     * @return List<Object>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getRemoteData.do")
    @ResponseBody
    public List<Object> getRemoteData(DeptMsgLogBean bean){
        
        return ipmBusTagService.getRemoteData(bean);
    }
    
    /**
     * 
     * @Title: selectByLogId
     * @Description: 查询一条记录
     * @param id  业务id
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/selectByLogId.do")
    @ResponseBody
    public List<DeptMsgLogBean> selectByLogId(int id){
        
        return ipmBusTagService.selectByLogId(id);
    }
}
