/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.word.bean.ReportHistoryBean;


/**
 * @ClassName: ReportHistoryService
 * @Description: 报表历史记录接口
 * @author 刘斌
 *
 */
public interface ReportHistoryService {
    /**
     * @Title: insertReportHistory
     * @Description: 插入报表历史记录接口
     * @param bean
     * @return Integer
     * @author 刘斌
     */
    Integer insertReportHistory(ReportHistoryBean bean);
    /**
     * @Title: deleteReportHistory
     * @Description: 删除报表历史记录接口
     * @param id
     * @param path
     * @param name
     * @return boolean
     * @author 刘斌
     */
    boolean deleteReportHistory(int id, String path, String name, SystemUserBean bean);
    /**
     * @Title: searchReportHistory
     * @Description: 查找历史记录（按Id）接口
     * @param id
     * @return ReportHistoryBean
     * @author 刘斌
     */
    ReportHistoryBean searchReportHistory(int id);
    /**
     * @Title: searchAllReportHistory
     * @Description: 通过时间段获取历史报表信息列表接口
     * @param stattime
     * @param endtime
     * @return List<ReportHistoryBean>
     * @author 刘斌
     */
    List<ReportHistoryBean> searchAllReportHistory(Long stattime, Long endtime);
    
    /**
     * @Title: downLoadBookFromMyServer
     * @Description: 在固定路径通过文件路径下载文件
     * @param request
     * @param response
     * @param name void
     * @author 刘斌
     */
    void downLoadBookFromMyServer(HttpServletRequest request,
            HttpServletResponse response, String name);

    /**
     * 
     * @Title: selectReportHistoryByUserId
     * @Description: 
     * @param id
     * @return List<ReportHistoryBean>
     * @author 刘斌
     */
    List<ReportHistoryBean> selectReportHistoryByUserId(Integer id);
    
    /**
     * 
     * @Title: selectReportHistoryByUserId
     * @Description: 
     * @param id
     * @return 
     * @author 刘斌
     */
    void deleteReportHistoryByCount();
    
    /**
     * 
     * @Title: updateHistoryBeanBySend
     * @Description: 
     * @param sendTime
     * @param sendNames
     * @param recriveNames
     * @param id
     * @return int
     * @author 刘斌
     */
    int updateHistoryBeanBySend(Long sendTime, Integer id);
}
