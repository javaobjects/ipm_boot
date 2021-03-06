/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommonController
 *创建人:yan    创建时间:2017年9月20日
 */
package com.protocolsoft.common.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.common.bean.Heatmap;
import com.protocolsoft.common.service.CommonService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.bean.TimeDefaultBean;

/**
 * 公共Controller
 * 2017年9月20日 上午10:29:47
 * @author yan
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/commonController")
public class CommonController {

    /**
     * 公共业务
     */
    @Autowired
    private CommonService commonService;
    
    /**
     * 暂时获取观察点、服务端、客户端的rrd数据
     * 2017年9月20日 下午2:39:58
     * @param moduleId
     * @param starttime
     * @param endtime
     * @param watchPointId
     * @return List<Map<String, Object>>
     * @exception 
     * @see
     */
    @RequestMapping(value="getNpmListRrdData.do")
    @ResponseBody
    public List<Map<String, Object>> getNpmListRrdData(HttpServletRequest request, 
            Integer moduleId, Long starttime, Long endtime, Integer watchpointId){
        
        TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime();
        
        Long tempStarttime = null;
        Long tempEndtime = null;
        if (null != starttime && null != endtime){
            tempStarttime = starttime;
            tempEndtime = endtime;
        } else {
            tempStarttime = timeDefaultBean.getStarttime();
            tempEndtime = timeDefaultBean.getEndtime();
        }
        List<Map<String, Object>> resultList = null;
        if (null != moduleId){
            resultList = commonService.getNpmListRrdData(request, moduleId, 
                    tempStarttime, tempEndtime, watchpointId);
        }
        return resultList;
    }
    
    /**
     * 从服务端查看客户端、从客户端查看服务端的rrd数据
     * 2017年9月21日 下午4:46:54
     * @param moduleId
     * @param starttime
     * @param endtime
     * @param watchPointId
     * @param appBusinessId
     * @return List<Map<String, Object>>
     * @exception 
     * @see
     */
    @RequestMapping(value="getNpmListRrdDataBySubPath.do")
    @ResponseBody
    public List<Map<String, Object>> getNpmListRrdDataBySubPath(HttpServletRequest request, Integer moduleId, 
            Long starttime, Long endtime, Integer watchpointId, Integer appBusinessId){
        
        TimeDefaultBean timeDefaultBean = DateAppsUtils.getGraphDefaultTime();
        
        Long tempStarttime = null;
        Long tempEndtime = null;
        if (null != starttime && null != endtime){
            tempStarttime = starttime;
            tempEndtime = endtime;
        } else {
            tempStarttime = timeDefaultBean.getStarttime();
            tempEndtime = timeDefaultBean.getEndtime();
        }
        List<Map<String, Object>> resultList = null;
        if (null != moduleId && null != watchpointId && null != appBusinessId){
            resultList = commonService.getNpmListRrdDataBySubPath(request, moduleId, 
                    tempStarttime, tempEndtime, watchpointId, appBusinessId);
        }
        return resultList;
    }
    
    /**
     * 
     * @Title: getHeatmap
     * @Description: 获取热度图数据 
     * @param request 请求
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param watchpointId 观察点
     * @param plotId 绘图编号
     * @return Heatmap 
     * @author www
     */
    @RequestMapping(value="/getHeatmap.do")
    @ResponseBody
    public Heatmap getHeatmap(HttpServletRequest request, long starttime, 
            long endtime, int watchpointId, int plotId) {
        
        return commonService.getHeatmap(request, starttime, endtime, watchpointId, plotId);
    }
}
