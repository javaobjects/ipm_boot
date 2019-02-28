/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service;

import java.util.List;
import java.util.Map;

import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.word.bean.ReportModalBean;

/**
 * 
 * @ClassName: ReportModalService
 * @Description: 
 * @author 刘斌
 *
 */
public interface ReportModalService {
    
    /**
     * @Title: insertTimerReort
     * @Description: 插入定时报表信息接口
     * @param bean
     * @return int
     * @author 刘斌
     */
    boolean insertReportModal(ReportModalBean bean);
    /**
     * @Title: deleteTimerReort
     * @Description: 按id杀出定时报表基础信息接口
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteReportModalBean(int id, SystemUserBean bean);
    /**
     * @Title: searchTimerReort
     * @Description: 按id查找定时报表接口
     * @param id
     * @return TimerReportBean
     * @author 刘斌
     */
    Map<String, Object> searchReportModal(int id);
    /**
     * @Title: searchAllTimerReorty
     * @Description: 获取素有定时报表基础信息列表
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    String searchAllReportModal();
    
    /**
     * @Title: updateTimerReort
     * @Description: 修改定时报表基础信息接口
     * @param bean
     * @param beanq
     * @return JSONObject
     * @author 刘斌
     */
    boolean updateReportModal(ReportModalBean bean);
    
    /**
     * @Title: selectNeedTimerReort
     * @Description: 按归档类型获取定时报表基础信息列表接口
     * @param typeId
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    List<ReportModalBean> selectNeedReportModal(int typeId);
    
    /**
     * 
     * @Title: selectModuleIds
     * @Description: 
     * @param id
     * @return String
     * @author 刘斌
     */
    String selectModuleIds(Integer id);
    
    /**
     * 
     * @Title: deleteModalByUserId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteModalByUserId(Integer id, SystemUserBean userBean);
}
