/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlController.java
 *创建人: www    创建时间: 2018年3月14日
 */
package com.protocolsoft.url.controller;

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
import com.protocolsoft.url.bean.UrlSetBean;
import com.protocolsoft.url.bean.UrlStateListBean;
import com.protocolsoft.url.bean.UrlStateParamBean;
import com.protocolsoft.url.service.UrlService;

/**
 * @ClassName: UrlController
 * @Description: URL
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/url")
public class UrlController {

    /**
     * URL业务
     */
    @Autowired
    private UrlService service;
    
    /**
     * 
     * @Title: getUrl
     * @Description: 获取URL信息
     * @param id 编号
     * @return Object
     * @author www
     */
    @RequestMapping(value = "/get.do")
    public @ResponseBody Object getUrl(HttpServletRequest request, Integer id) {
        Object obj = null;
        if (id == null) {
            obj = service.selectUrl(request);
        } else {
            obj = service.selectUrl(id);
        }
        
        return obj;
    }
    
    /**
     * 
     * @Title: add
     * @Description: 添加
     * @param bean URL信息
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/add.do")
    public @ResponseBody Map<String, Integer> add(HttpServletRequest request, UrlSetBean bean) {
        
        return service.add(request, bean);
    }

    /**
     * 
     * @Title: update
     * @Description: 修改
     * @param request 请求
     * @param bean URL信息
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map<String, Integer> update(HttpServletRequest request, UrlSetBean bean) {
        
        return service.update(request, bean);
    }
    
    /**
     * 
     * @Title: delete
     * @Description: 删除
     * @param id 编号
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/delete.do")
    public @ResponseBody Map<String, Integer> delete(HttpServletRequest request, int id) {
        
        return service.delete(request, id);
    }
    
    /**
     * 
     * @Title: getPlotData
     * @Description: 获取绘图数据
     * @param bean
     * @return PlotDataBean
     * @author www
     */
    @RequestMapping(value = "/getPlotData.do")
    public @ResponseBody PlotDataBean getPlotData(PlotParamBean bean) {
        
        return service.getPlotData(bean);
    }
    
    /**
     * 
     * @Title: getSimpleData
     * @Description: 获取十字格数据
     * @param bean
     * @return SimpleDataBean
     * @author www
     */
    @RequestMapping(value = "/getSimpleData.do")
    public @ResponseBody SimpleDataBean getSimpleData(SimpleParamBean bean) {
        
        return service.getSimpleData(bean);
    }
    
    /**
     * 
     * @Title: getUrlStateList
     * @Description: 所有URL业务信息
     * @param request 请求
     * @param bean 参数
     * @return List<UrlStateListBean>
     * @author www
     */
    @RequestMapping(value = "/getUrlStateList.do")
    public @ResponseBody List<UrlStateListBean> getUrlStateList(
            HttpServletRequest request, UrlStateParamBean bean) {
        
        return service.getUrlStateList(request, bean);
    }
    
    /**
     * 
     * @Title: getUrlStateList
     * @Description: 某个URL业务信息
     * @param bean 参数
     * @return List<UrlStateListBean>
     * @author www
     */
    @RequestMapping(value = "/getSimpleUrlStateList.do")
    public @ResponseBody List<UrlStateListBean> getSimpleUrlStateList(UrlStateParamBean bean) {
        
        return service.getUrlStateList(bean);
    }
}
