/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommonService
 *创建人:yan    创建时间:2017年9月19日
 */
package com.protocolsoft.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.common.bean.Heatmap;
import com.protocolsoft.common.bean.ReportListBean;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;

/**
 * 公共业务逻辑接口
 * 2017年9月19日 上午11:33:52
 * @author yan
 * @version
 * @see
 */
public interface CommonService {
    
    /**
     * 暂时获取观察点、服务端、客户端的rrd数据
     * 2017年9月19日 上午11:47:16
     * @param moduleId
     * @param starttime
     * @param endtime
     * @param watchPointId
     * @return List<Map<String, SimpleEntry<Long, Double>>>
     * @exception 
     * @see
     */
    List<Map<String, Object>> getNpmListRrdData(HttpServletRequest request, Integer moduleId,
            Long starttime, Long endtime, Integer watchPointId);
    
    /**
     * 从服务端查看客户端、从客户端查看服务端的rrd数据
     * 2017年9月21日 下午4:42:32
     * @param moduleId
     * @param starttime
     * @param endtime
     * @param watchPointId
     * @param appBusinessId
     * @return List<Map<String,Object>>
     * @exception 
     * @see
     */
    List<Map<String, Object>> getNpmListRrdDataBySubPath(HttpServletRequest request, Integer moduleId,
            Long starttime, Long endtime, Integer watchPointId, Integer appBusinessId);

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
    Heatmap getHeatmap(HttpServletRequest request, long starttime,
            long endtime, int watchpointId, int plotId);
    
    /**
     * 
     * @Title: getRrdMap
     * @Description: RRD
     * @param moduleId 模块编号
     * @param isDefault 是否为默认
     * @return Map<String,RrdAlgorithm>
     * @author www
     */
    Map<String, RrdAlgorithm> getRrdMap(Integer moduleId, boolean isDefault);
    
    /**
     * 
     * @Title: getReportWatchpointKpiList
     * @Description: 根据用户编号获取报表KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param userId 用户编号
     * @return List<ReportListBean>
     * @author WWW
     */
    List<ReportListBean> getReportWatchpointKpiList(long starttime, long endtime, int userId);
    
    /**
     * 
     * @Title: getReportWatchpointKpiList
     * @Description: 根据观察点信息获取报表KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param ids 编号
     * @return List<ReportListBean>
     * @author WWW
     */
    List<ReportListBean> getReportWatchpointKpiList(long starttime, long endtime, String ids);
    
    /**
     * 
     * @Title: getOtherModuleKpiList
     * @Description: 根据用户获取其他模块KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param userId 用户编号
     * @return List<ReportListBean>
     * @author WWW
     */
    List<ReportListBean> getOtherModuleKpiList(long starttime, long endtime, int moduleId, int watchpointId, int userId);
    
    /**
     * 
     * @Title: getOtherModuleKpiList
     * @Description: 根据业务编号获取其他模块KPI列表信息
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param moduleId 模块编号
     * @param watchpointId 观察点编号
     * @param ids 业务编号
     * @return List<ReportListBean>
     * @author WWW
     */
    List<ReportListBean> getOtherModuleKpiList(long starttime, long endtime, int moduleId, int watchpointId, String ids);
}
