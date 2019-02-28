/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.word.bean.ReportTimerDetailBean;
import com.protocolsoft.word.dao.ReportDetailDao;
import com.protocolsoft.word.service.TimerReportDetailService;


/**
 * @ClassName: TimerReportDetailServiceImpl
 * @Description: 图表信息控制器
 * @author 刘斌
 *
 */
@Service
public class TimerReportDetailServiceImpl implements TimerReportDetailService {
    /**
     * @Fields dao : 图表Dao
     */
    @Autowired
    private ReportDetailDao dao;
    /* (非 Javadoc)
     * <p>Title: insertReportTimerDetail</p>
     * <p>Description: 插入图表信息</p>
     * @param bean
     * @return
     * @see com.protocolsoft.word.service.TimerReportDetailService#insertReportTimerDetail(com.protocolsoft.word.bean.ReportTimerDetailBean)
     */
    @Override
    public boolean insertReportTimerDetail(ReportTimerDetailBean bean) {
        try {
            dao.insertReportTimerDetails(bean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* (非 Javadoc)
     * <p>Title: deleteReportTimerDetail</p>
     * <p>Description: 删除图表信息</p>
     * @param id
     * @return
     * @see com.protocolsoft.word.service.TimerReportDetailService#deleteReportTimerDetail(int)
     */
    @Override
    public boolean deleteReportTimerDetail(int id) {
        try {
            dao.deleteTimerReport(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* (非 Javadoc)
     * <p>Title: searchReportTimerDetail</p>
     * <p>Description: 通过id查找图表基础信息对象</p>
     * @param id
     * @return
     * @see com.protocolsoft.word.service.TimerReportDetailService#searchReportTimerDetail(int)
     */
    @Override
    public ReportTimerDetailBean searchReportTimerDetail(int id) {
        try {
            return dao.selectReportTimerDetail(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* (非 Javadoc)
     * <p>Title: searchReportTimersDetailById</p>
     * <p>Description: 通过报表id 获取报表所含图表的基础信息列表</p>
     * @param id
     * @return
     * @see com.protocolsoft.word.service.TimerReportDetailService#searchReportTimersDetailById(int)
     */
    @Override
    public List<ReportTimerDetailBean> searchReportTimersDetailById(int id) {
        try {
            return dao.selectReportTimerDetailsById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* (非 Javadoc)
     * <p>Title: deleteTimerDetailByWatchPointId</p>
     * <p>Description: 删除已经失效的观察点 在定时报表里对应的图表信息</p>
     * @param id
     * @see com.protocolsoft.word.service.TimerReportDetailService#deleteTimerDetailByWatchPointId(int)
     */
    @Override
    public void deleteTimerDetailByWatchPointId(int id) {
    }

    /* (非 Javadoc)
     * <p>Title: deleteTimerDetailByAppId</p>
     * <p>Description: 删除已经失效的业务 在定时报表里对应的图表信息</p>
     * @param id
     * @param modleId
     * @see com.protocolsoft.word.service.TimerReportDetailService#deleteTimerDetailByAppId(int, int)
     */
    @Override
    public void deleteTimerDetailByAppId(int id, int modleId) {
        try {
            dao.deleteTimerReportAppId(id, modleId);
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
    }

}
