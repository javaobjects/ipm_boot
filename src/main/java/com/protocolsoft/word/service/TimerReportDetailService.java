/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service;

import java.util.List;

import com.protocolsoft.word.bean.ReportTimerDetailBean;

/**
 * @ClassName: TimerReportDetailService
 * @Description: 图表信息接口
 * @author 刘斌
 *
 */
public interface TimerReportDetailService {
    
    /**
     * @Title: insertReportTimerDetail
     * @Description: 插入图表信息接口
     * @param bean
     * @return boolean
     * @author 刘斌
     */
    boolean insertReportTimerDetail(ReportTimerDetailBean bean);

    /**
     * @Title: deleteReportTimerDetail
     * @Description: 删除图表信息接口
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteReportTimerDetail(int id);
    /**
     * @Title: searchReportTimerDetail
     * @Description: 按id查找图表信息接口
     * @param id
     * @return ReportTimerDetailBean
     * @author 刘斌
     */
    ReportTimerDetailBean searchReportTimerDetail(int id);
    /**
     * @Title: searchReportTimersDetailById
     * @Description: 按定时报表信息获取信图表信息接口
     * @param id
     * @return List<ReportTimerDetailBean>
     * @author 刘斌
     */
    List<ReportTimerDetailBean> searchReportTimersDetailById(int id);
    
    /**
     * @Title: deleteTimerDetailByWatchPointId
     * @Description:  删除失效观察点相关的信息接口
     * @param id void
     * @author 刘斌
     */
    void deleteTimerDetailByWatchPointId(int id);
    
    /**
     * @Title: deleteTimerDetailByAppId
     * @Description: 删除失效业务相关信息接口
     * @param id
     * @param modleId void
     * @author 刘斌
     */
    void deleteTimerDetailByAppId(int id, int modleId);
    
}
