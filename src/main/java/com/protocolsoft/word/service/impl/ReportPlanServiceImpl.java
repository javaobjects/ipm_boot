/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service.impl;


import java.util.List;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.bean.ReportModalBean;
import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.dao.ReportBusinessDao;
import com.protocolsoft.word.dao.ReportEmailDao;
import com.protocolsoft.word.dao.ReportModalDao;
import com.protocolsoft.word.dao.ReportPlanDao;
import com.protocolsoft.word.service.ReportPlanService;
import com.protocolsoft.word.task.QuartzManager;

/**
 * 
 * @ClassName: ReportPlanServiceImpl
 * @Description: 
 * @author 刘斌
 *
 */
@Service
public class ReportPlanServiceImpl implements ReportPlanService{
    
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;
    
    /**
     * 
     */
    @Autowired
    ReportBusinessDao reportBusinessDao;
    
    /**
     * 
     */
    @Autowired
    ReportEmailDao reportEmailDao;
    
    /**
     * 
     */
    @Autowired
    ReportPlanDao reportPlanDao;
    
    /**
     * 
     */
    @Autowired
    private ReportModalDao reportModalDao;

    /**
     * @return boolean
     */
    @Override
    @Transactional
    public boolean insertReportPlan(ReportPlanBean bean){
        reportPlanDao.insertReportPlan(bean);
        List<ReportBusinessBean> listBus = bean.getListBus();
        if(null!=listBus&&listBus.size()>0){
            for(ReportBusinessBean beanEmail :listBus){
                beanEmail.setPlanId(bean.getId());
            }
            int dd = reportBusinessDao.insertAll(listBus);
            if(dd<0){
                throw new RuntimeException("业务信息插入失败！");
            }
        }else{
            return false;
        }
        List<ReportEmailBean> list = bean.getList();
        if(null!=list&&list.size()>0){
            for(ReportEmailBean beanEmail :list){
                beanEmail.setPlanId(bean.getId());
            }
            int dd = reportEmailDao.insertAll(list);
            if(dd<0){
                throw new RuntimeException("邮件发送信息插入失败！");
            }
        }
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(bean.getUserId());
        logsBean.setModuleId(19);
        SystemUserBean userBean = systemUserService.getUserBeanById(bean.getUserId());
        logsBean.setMsg(userBean.getUserName()+"添加计划");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean);
        
        return true;
    }

    @Override
    @Transactional
    public boolean deleteReportPlan(int id, SystemUserBean userBean) {
        ReportPlanBean bean = reportPlanDao.selectReportPlan(id);
        if(null == userBean){
            userBean =  systemUserService.getUserBeanById(bean.getUserId());
        }
        if(1==bean.getTypeId()){
            try {
                QuartzManager qm = new QuartzManager();
                qm.removeJob(id + "", id + "", id + "", id + "", null);
            } catch (Exception e) {
            }
        }
        reportPlanDao.deleteReportPlan(id);
        reportEmailDao.deleteReportEmails(id);
        reportBusinessDao.deleteReportEBusiness(id);
            
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(19);
        logsBean.setMsg(userBean.getUserName()+"删除计划");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean); 
        return true;
    }

    @Override
    public List<ReportPlanBean> searchAllReportPlan(Integer id) {
        return reportPlanDao.selectAllReportPlan(id);
    }

    @Override
    @Transactional
    public boolean updateReportPlan(ReportPlanBean bean) {
        int ss = reportPlanDao.updateReportPlan(bean);
        if(ss!=0){
            reportBusinessDao.deleteReportEBusiness(bean.getId());
            List<ReportBusinessBean> listBus = bean.getListBus();
            if(null!=listBus&&listBus.size()>0){
                for(ReportBusinessBean beanEmail :listBus){
                    beanEmail.setPlanId(bean.getId());
                }
                int dd = reportBusinessDao.insertAll(listBus);
                if(dd<0){
                    throw new RuntimeException("业务信息插入失败！");
                }
            }
            reportEmailDao.deleteReportEmails(bean.getId());
            List<ReportEmailBean> list = bean.getList();
            if(null!=list&&list.size()>0){
                for(ReportEmailBean beanMail :list){
                    beanMail.setPlanId(bean.getId());
                }
                return reportEmailDao.insertAll(list)>0;
            }else{
                LogsBean logsBean = new LogsBean();
                logsBean.setUserId(bean.getUserId());
                logsBean.setModuleId(19);
                SystemUserBean userBean = systemUserService.getUserBeanById(bean.getUserId());
                logsBean.setMsg(userBean.getUserName()+"删除计划");
                logsBean.setTime(DateUtils.getNowTimeSecond());
                logsDao.insertLogs(logsBean); 
                return true;
            }
        }else{
            return false;  
        }
    }

    @Override
    public boolean updateReportPlanState(Integer id, Integer state) {
        int state2 = (state%2)+1;
        return reportPlanDao.updateReportPlanState(id, state2)>0;
    }

    @Override
    public List<ReportPlanBean> selectNeedReportPlan(int typeId) {
        return reportPlanDao.selectAllNeedReportPlan(typeId);
    }

    @Override
    public ReportPlanBean searchReportPlanBeanById(Integer id) {
        ReportPlanBean bean = reportPlanDao.selectReportPlan(id);
        ReportModalBean modalBean = reportModalDao.selectReportModal(bean.getModalId());
        bean.setModalName(modalBean.getName());
        bean.setModuleIds(modalBean.getModuleIds());
        List<ReportEmailBean> list = reportEmailDao.selectReportEmails(id);
        List<ReportBusinessBean> listBus = reportBusinessDao.selectReportBusiness(id);
        bean.setListBus(listBus);
        bean.setList(list);
        return bean;
    }

    @Override
    public boolean addReportPlanSendtimes(Integer id) {
        return reportPlanDao.addReportPlanSendTimes(id)>0;
    }

    @Override
    public boolean updateReportPlanNextSendTime(Integer id, Long nextTime) {
        return reportPlanDao.updateReportPlanNextSendTime(id, nextTime)>0;
    }

    @Override
    public List<ReportPlanBean> selectAllBusinessByPlanId() {
        return reportPlanDao.selectAllReportPlans();
    }

    @Override
    @Transactional
    public boolean deleteReportPlanByModalId(int id) {
        List<ReportPlanBean> list = reportPlanDao.selectAllReportPlanByModalId(id);
        int ss = reportPlanDao.deleteReportPlanByModalId(id);
        if(ss>0){
            for(ReportPlanBean bean : list){
                reportEmailDao.deleteReportEmails(bean.getId());
                reportBusinessDao.deleteReportEBusiness(bean.getId());
            }
            
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePlanByUserId(Integer userId) {
        List<ReportPlanBean> list = reportPlanDao.selectAllReportPlan(userId);
        for(ReportPlanBean bean : list){
            Integer typeId = bean.getTypeId();
            if(1==typeId){
                QuartzManager qm = new QuartzManager();
                qm.removeJob(bean.getId()+"", bean.getId()+"", bean.getId()+"", bean.getId()+"", null);
            }
            reportEmailDao.deleteReportEmails(bean.getId());
            reportBusinessDao.deleteReportEBusiness(bean.getId());
        }
        reportPlanDao.deleteReportPlanByUserId(userId);
        
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userId);
        logsBean.setModuleId(19);
        SystemUserBean userBean = systemUserService.getUserBeanById(userId);
        logsBean.setMsg(userBean.getUserName()+"删除用户后清空用户所创建的所有计划。");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean); 
        return true;
    }

    @Override
    public List<ReportEmailBean> getAllEmail() {
        return reportEmailDao.selectAllEmails();
    }

    @Override
    public boolean deleteEmailByEmailId(Integer id) {
        reportEmailDao.deleteReportEmails(id);
        return true;
    }

    @Override
    public boolean updateEmailByEmailId(ReportEmailBean bean) {
        int dr = reportEmailDao.updateReportEmail(bean);
        return dr > 0;
    }

    @Override
    public boolean addEmailByEmailId(List<ReportEmailBean> list) {
        int e = 0;
        for(ReportEmailBean bean : list){
            try {
                e = reportEmailDao.insertReportEmails(bean);
                if(e==0){
                    reportEmailDao.updateReportEmails(bean);
                }
                e++;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return e > 0;
    }
    
}
