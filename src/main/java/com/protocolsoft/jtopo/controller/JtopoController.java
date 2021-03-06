/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairController
 *创建人:yan    创建时间:2017-10-16
 */
package com.protocolsoft.jtopo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.jtopo.bean.CommpairQoBean;
import com.protocolsoft.jtopo.bean.CommpairVoBean;
import com.protocolsoft.jtopo.bean.JtopoIpnet;
import com.protocolsoft.jtopo.service.CommpairService;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.utils.DateUtils;

/**
 * 通信对拓扑图Controller
 * 2017-10-16 下午4:01:09
 * @author yan
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/jtopo")
public class JtopoController {

    /**
     * 通信对业务接口
     */
    @Autowired
    private CommpairService commpairService;
    
    /**
     * clientService注入
     */
    @Autowired(required = false)
    private ClientService clientService;
    
    /**
     * 通过时间、粒度获取去重、聚合、平均的通信对
     * 2017-10-17 下午5:35:47
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    @RequestMapping(value="getAllCommpair.do")
    @ResponseBody
    public List<CommpairVoBean> getAllCommpair(CommpairQoBean commpairQoBean){
        if (null == commpairQoBean.getStartTime()) {
            int now = DateUtils.getNowTimeSecond(60);
            commpairQoBean.setStartTime((long)now - 10);
            commpairQoBean.setEndTime((long)now);
        }
        List<CommpairVoBean> commpairVoBeans = commpairService.getAllCommpair(commpairQoBean);
        
        return commpairVoBeans;
    }
    
    /**
     * 通过时间、粒度、IP或者端口获取去重、聚合、平均的通信对
     * 2017-10-18 下午2:34:26
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    @RequestMapping(value="getCommpairByIpPort.do")
    @ResponseBody
    public List<CommpairVoBean> getCommpairByIpPort(CommpairQoBean commpairQoBean){
        if (null == commpairQoBean.getStartTime()) {
            int now = DateUtils.getNowTimeSecond(60);
            commpairQoBean.setStartTime((long)now - 10);
            commpairQoBean.setEndTime((long)now);
        }
        List<CommpairVoBean> commpairVoBeans = commpairService.getCommpairByIpPort(commpairQoBean);
        
        return commpairVoBeans;
    }
    
    /*移植npm————jtopo 方法  begin **************/
    
    /**
     * 带IP及端口的系统查询，返回全子网
     * @return List<JtopoIpnet>
     */
    @RequestMapping(value = "/getJtopoIpnet.do")
    @ResponseBody
    public List<JtopoIpnet> getJtopoIpnet() {
        // 获取所有子网
        List<AppBusinessBean> npmList = clientService.getClient();
        List<JtopoIpnet> list = new ArrayList<JtopoIpnet>(); 
        for (AppBusinessBean np : npmList) {
            JtopoIpnet jtopoIpnet = new JtopoIpnet();
            jtopoIpnet.setIpnet(np.getDisplayIp());
            jtopoIpnet.setName(np.getName());
            list.add(jtopoIpnet);
        }
        
        return list;
    }
    
    /**
     * topo保存
     * @param request
     * @return boolean
     */
    @RequestMapping(value = "/saveJtopo.do")
    @ResponseBody
    public boolean saveJtopo(HttpServletRequest request) {
        String name = request.getParameter("name");
        String nodeJson = request.getParameter("nodeJson");
        String pathName = request.getSession().getServletContext().getRealPath("/") + "/json/jtopo/" + name;
        commpairService.saveJtopo(pathName, nodeJson);
        
        return true;
    }

    /**
     * 获取topo
     * @param request
     * @param name
     * @return Object
     */
    @RequestMapping(value = "/getJtopo.do")
    @ResponseBody
    public Object getJtopo(HttpServletRequest request, String name) {
        String pathName = request.getSession().getServletContext().getRealPath("/") + "/json/jtopo/" + name;
        String json = commpairService.getJtopo(pathName);
        
        return JSON.toJSON(json);
    }

    /**
     * 获取topoNames
     * @param request
     * @return Object
     */
    @RequestMapping(value = "/getJtopoNames.do")
    @ResponseBody
    public Object getJtopoNames(HttpServletRequest request) {
        String pathName = request.getSession().getServletContext().getRealPath("/") + "/json/jtopo/";
        File f = new File(pathName);
        List<String> strList = new ArrayList<String>();
        if (f.exists() && f.isDirectory()) {
            File[] fList = f.listFiles();
            if (fList != null) {
                for (File file : fList) {
                    strList.add(file.getName());
                }
            }
        }
        
        return JSON.toJSON(strList);

    }

    /**
     * 删除topo
     * @param request
     * @param name
     * @return boolean
     */
    @RequestMapping(value = "/delJtopo.do")
    @ResponseBody
    public boolean delJtopo(HttpServletRequest request, String name) {
        boolean bool = true;
        try {
            String pathName = request.getSession().getServletContext().getRealPath("/") + "/json/jtopo/" + name;
            File f = new File(pathName);
            if (!f.exists()) {
            } else {
                f.delete();
            }
        } catch (Exception e){
            bool = false;
            e.printStackTrace();
        }
        
        return bool;
    }
}
