/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service;

import java.util.List;

import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.bean.ReportPlanBean;

/**
 * 
 * @ClassName: ReportPlanService
 * @Description: 
 * @author 刘斌
 *
 */
public interface ReportPlanService {

    /**
     * @Title: insertTimerReort
     * @Description: 插入定时报表信息接口
     * @param bean
     * @return int
     * @author 刘斌
     */
    boolean insertReportPlan(ReportPlanBean bean);
    /**
     * @Title: deleteTimerReort
     * @Description: 按id杀出定时报表基础信息接口
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteReportPlan(int id, SystemUserBean userBean);
    /**
     * @Title: searchAllTimerReorty
     * @Description: 获取素有定时报表基础信息列表
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    List<ReportPlanBean> searchAllReportPlan(Integer id);
    
    /**
     * @Title: updateTimerReort
     * @Description: 修改定时报表基础信息接口
     * @param bean
     * @param beanq
     * @return JSONObject
     * @author 刘斌
     */
    boolean updateReportPlan(ReportPlanBean bean);
    
    /**
     * @Title: updateTimerReort
     * @Description: 修改定时报表基础信息接口
     * @param bean
     * @param beanq
     * @return JSONObject
     * @author 刘斌
     */
    boolean updateReportPlanState(Integer id, Integer state);
    
    /**
     * @Title: selectNeedTimerReort
     * @Description: 按归档类型获取定时报表基础信息列表接口
     * @param typeId
     * @return List<TimerReportBean>
     * @author 刘斌
     */
    List<ReportPlanBean> selectNeedReportPlan(int typeId);
    
    /**
     * 
     * @Title: searchReportPlanBeanById
     * @Description: 
     * @param id
     * @return ReportPlanBean
     * @author 刘斌
     */
    ReportPlanBean searchReportPlanBeanById(Integer id);
    
    /**
     * 
     * @Title: addReportPlanSendtimes
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean addReportPlanSendtimes(Integer id);
    
    /**
     * 
     * @Title: updateReportPlanNextSendTime
     * @Description: 
     * @param id
     * @param nextTime
     * @return boolean
     * @author 刘斌
     */
    boolean updateReportPlanNextSendTime(Integer id, Long nextTime);
    
    /**
     * 
     * @Title: selectAllBusinessByPlanId
     * @Description: 
     * @return List<ReportBusinessBean>
     * @author 刘斌
     */
    List<ReportPlanBean> selectAllBusinessByPlanId();
    
    /**
     * 
     * @Title: deleteReportPlanByModalId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteReportPlanByModalId(int id);
    
    /**
     * 
     * @Title: deletePlanByUserId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deletePlanByUserId(Integer id);
    
    /**
     * 
     * @Title: getAllEmail
     * @Description: 
     * @return List<ReportEmailBean>
     * @author 刘斌
     */
    List<ReportEmailBean> getAllEmail();
    
    /**
     * 
     * @Title: deleteEmailByEmailId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean deleteEmailByEmailId(Integer id);
    
    /**
     * 
     * @Title: deleteEmailByEmailId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean updateEmailByEmailId(ReportEmailBean bean);
    
    /**
     * 
     * @Title: deleteEmailByEmailId
     * @Description: 
     * @param id
     * @return boolean
     * @author 刘斌
     */
    boolean addEmailByEmailId(List<ReportEmailBean> bean);
}
