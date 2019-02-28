/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapService
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.service;

import java.util.Map;

import com.protocolsoft.map.bean.MapQoBean;
import com.protocolsoft.map.bean.MapVoBean;

/**
 * 
 * @ClassName: MapService
 * @Description: 地图业务处理接口
 * @author wangjianmin
 *
 */
public interface MapService {

    /**
     * 
     * @Title: getMapData
     * @Description: 获取地图数据
     * @param mapQoBean 接收Qo参数
     * @return Map<String,MapVoBean>
     * @author wangjianmin
     */
    Map<String, MapVoBean> getMapData(MapQoBean mapQoBean);
}
