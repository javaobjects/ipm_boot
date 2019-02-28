/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotController.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotBean;
import com.protocolsoft.kpi.service.PlotService;

/**
 * @ClassName: PlotController
 * @Description: 绘图控制
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/plot")
public class PlotController {

    /**
     * 绘图
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * 
     * @Title: getPlotByModuleId
     * @Description: 获取绘图的映射关系
     * @param moduleId 模块编号
     * @return List<PlotBean>
     * @author www
     */
    @RequestMapping(value = "/getPlotByModuleId.do")
    @ResponseBody
    public List<PlotBean> getPlotByModuleId(int moduleId) {
        
        return plotService.getPlotByModuleId(moduleId);
    }

    /**
     * 
     * @Title: getPlotByModuleKpiId
     * @Description: 获取绘图选项信息
     * @param moduleId 模块编号
     * @param kpiId KPI编号
     * @return PlotBean
     * @author www
     */
    @RequestMapping(value = "/getPlotByModuleKpiId.do")
    @ResponseBody
    public PlotBean getPlotByModuleKpiId(int moduleId, int kpiId) {
        
        return plotService.getPlotByModuleKpiId(moduleId, kpiId);
    }
    
    /**
     * 
     * @Title: getPlotByModuleKpiName
     * @Description: 获取绘图映射关系
     * @param moduleId 模块编号
     * @param kpiName KPI名称
     * @return PlotBaseBean
     * @author www
     */
    @RequestMapping(value = "/getPlotByModuleKpiName.do")
    @ResponseBody
    public PlotBean getPlotByModuleKpiName(int moduleId, String kpiName) {
        
        return plotService.getPlotByModuleKpiName(moduleId, kpiName);
    }
    
    /**
     * 
     * @Title: getAllKpis
     * @Description: 获取所有KPI信息
     * @return List<KpisBean>
     * @author www
     */
    @RequestMapping(value = "/getAllKpis.do")
    @ResponseBody
    public List<KpisBean> getAllKpis() {
        
        return plotService.getAllKpis();
    }
    
    /**
     * 
     * @Title: getTopoKpis
     * @Description: 获取拓扑图KPI
     * @return List<KpisBean>
     * @author WWW
     */
    @RequestMapping(value = "/getTopoKpis.do")
    @ResponseBody
    public List<KpisBean> getTopoKpis() {
        
        return plotService.getTopoKpis();
    }
    
    /**
     * 
     * @Title: getKpisByPlotId
     * @Description: 获取KPI信息
     * @param plotId 编号
     * @return KpisBean
     * @author WWW
     */
    @RequestMapping(value = "/getKpisByPlotId.do")
    @ResponseBody
    public KpisBean getKpisByPlotId(int plotId) {
        
        return plotService.getKpisByPlotId(plotId);
    }
    

    /**
     * 
     * @Title: getPlotData
     * @Description: 硬件信息绘图
     * @param bean 参数
     * @return PlotDataBean
     * @author WWW
     */
    @RequestMapping(value = "/getPlotData.do")
    @ResponseBody
    public PlotDataBean getPlotData(PlotParamBean bean) {
        
        return plotService.getPlotData(bean);
    }
}
