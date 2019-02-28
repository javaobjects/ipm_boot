/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapController
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.map.bean.MapQoBean;
import com.protocolsoft.map.bean.MapVoBean;
import com.protocolsoft.map.service.MapService;
import com.protocolsoft.utils.DateUtils;

/**
 * 
 * @ClassName: MapController
 * @Description: 地图Controller
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/map")
public class MapController {
    
    /**
     * 地图业务处理接口
     */
    @Autowired
    private MapService mapService;
    
    
    /**
     * 
     * @Title: getMapData
     * @Description:  获取地图数据
     * @param mapQoBean 接收Qo参数
     * @return Map<String,MapVoBean>
     * @author wangjianmin
     */
    @RequestMapping(value="getMapData.do")
    @ResponseBody 
    public Map<String, MapVoBean> getMapData(MapQoBean mapQoBean){
        if (null == mapQoBean.getStartTime()) {
            mapQoBean.setStartTime((long)DateUtils.getNowTimeSecond(60) - 600);
        } 
        if (null == mapQoBean.getEndTime()) {
            mapQoBean.setEndTime((long)DateUtils.getNowTimeSecond(60));
        }
        if (null == mapQoBean.getTempColumnName() || "".equals(mapQoBean.getTempColumnName())){
            mapQoBean.setTempColumnName("tempT.regionCn");
        }
        
        Map<String, MapVoBean> map = mapService.getMapData(mapQoBean);
        
        return map;
    }
}
