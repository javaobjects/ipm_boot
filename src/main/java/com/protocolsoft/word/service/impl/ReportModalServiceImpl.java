/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.word.bean.GroupBean;
import com.protocolsoft.word.bean.ReportModalBean;
import com.protocolsoft.word.bean.ReportModalTableAndWranBean;
import com.protocolsoft.word.bean.ReportTimerDetailBean;
import com.protocolsoft.word.dao.ReportDetailDao;
import com.protocolsoft.word.dao.ReportModalDao;
import com.protocolsoft.word.dao.ReportModalTableAndWarnDao;
import com.protocolsoft.word.service.ReportModalService;
import com.protocolsoft.word.service.ReportPlanService;

/**
 * 
 * @ClassName: ReportModalServiceImpl
 * @Description: 
 * @author 刘斌
 *
 */
@Service
public class ReportModalServiceImpl implements ReportModalService{
    
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
    private ReportPlanService reportPlanService;
    
    /**
     * 
     */
    @Autowired
    private ReportModalTableAndWarnDao reportModalTableAndWarnDao;
    
    /**
     * 
     */
    @Autowired
    private ReportModalDao reportModalDao;
    
    /**
     * @Fields dao2 : 图表Dao
     */
    @Autowired
    private ReportDetailDao reportDetailDao;
    
    @Override
    @Transactional
    public boolean insertReportModal(ReportModalBean bean) {
        reportModalDao.insertReportModal(bean);
        if(null == bean.getId()){
            return false;
        }
        List<GroupBean> listGroup = bean.getGroup();
        List<ReportTimerDetailBean> listReportTim = new ArrayList<>();
        List<ReportModalTableAndWranBean> listyTableWarning =new ArrayList<>();
        Map<Integer, Integer> map =new  HashMap<>();
        if(null!=listGroup&&listGroup.size()>0){
            for (GroupBean group : listGroup){
                ReportTimerDetailBean detailBean = new ReportTimerDetailBean();
                if(1==group.getType()){
                    detailBean.setModuleId(group.getModuleId());
                    detailBean.setPlotId(group.getPlotId());
                    detailBean.setPlotTypeId(group.getPlotTypeId());
                    detailBean.setTableHaving(group.getTableHaving());
                    detailBean.setWarningHaving(group.getWarningHaving());
                    map.put(group.getModuleId(), 1);
                    if (10 == group.getModuleId()) {
                        detailBean.setWatchpointId(group.getBusiId());
                    } else {
                        detailBean.setWatchpointId(group.getWatchpointId());
                    }
                    detailBean.setReportId(bean.getId());
                    detailBean.setAppId(group.getBusiId());
                    listReportTim.add(detailBean);
                }else{
                    ReportModalTableAndWranBean tableAndWarnbean = new ReportModalTableAndWranBean();
                    tableAndWarnbean.setModalId(bean.getId());
                    tableAndWarnbean.setModultId(group.getModuleId());
                    tableAndWarnbean.setTableHaving(group.getTableHaving());
                    tableAndWarnbean.setWarningHaving(group.getWarningHaving());
                    map.put(group.getModuleId(), 1);
                    listyTableWarning.add(tableAndWarnbean);
                }
            }
        }
        Set<Map.Entry<Integer, Integer>> entry = map.entrySet();
        Iterator<Map.Entry<Integer, Integer>>  ite = entry.iterator();
        String ds ="";
        while(ite.hasNext()){
            Map.Entry<Integer, Integer> en = ite.next();
            ds += (en.getKey()+",");
        }
        ds = ds.substring(0, ds.length()-1);
        bean.setModuleIds(ds);
        reportModalDao.updateReportModalModules(bean);
        if(null!=listReportTim&&listReportTim.size()>0){
            reportDetailDao.insertAll(listReportTim);
        }
        if(null!=listyTableWarning&&listyTableWarning.size()>0){
            reportModalTableAndWarnDao.insertAll(listyTableWarning);
        }
        
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(bean.getUserId());
        logsBean.setModuleId(19);
        SystemUserBean userBean = systemUserService.getUserBeanById(bean.getUserId());
        logsBean.setMsg(userBean.getUserName()+"添加模板");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteReportModalBean(int id, SystemUserBean bean) {
        int ss = reportModalDao.deleteReportModal(id);
        if(ss>0){
            reportDetailDao.deleteTimerReport(id);
            reportModalTableAndWarnDao.deleteReportModalTableAndWranBeans(id);
            
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(bean.getId());
            logsBean.setModuleId(19);
            logsBean.setMsg(bean.getUserName()+"删除模板，并删除所有使用该模板的计划。");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsDao.insertLogs(logsBean);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Map<String, Object> searchReportModal(int id) {
        Map<String, Object> map = new HashMap<>();
        ReportModalBean bean = reportModalDao.selectReportModal(id);
        List<ReportTimerDetailBean> list = reportDetailDao.selectReportTimerDetailsById(id);
        List<ReportModalTableAndWranBean> listyTableWarning = reportModalTableAndWarnDao.selectReportModalTableAndWranBeans(id);
        map.put("bean", bean);
        map.put("list", list);
        map.put("listTableWarning", listyTableWarning);
        return map;
    }

    @Override
    public String searchAllReportModal() {
        List<ReportModalBean> list = reportModalDao.selectAllReportModal();
        JSONArray jsonArray = new JSONArray();
        for (ReportModalBean bean : list) {
            String containModle = bean.getModuleIds();
            Integer watchpointKpiNum = 0;
            Integer clientKpiNum = 0;
            Integer serverKpiNum = 0;
            List<ReportTimerDetailBean> listReport = reportDetailDao.selectReportTimerDetailsById(bean.getId());
            if(null!= listReport && listReport.size()>0){
                for(ReportTimerDetailBean le : listReport){
                    switch(le.getModuleId()){
                        case 10 :
                            watchpointKpiNum ++;
                            break;
                        case 11 :
                            clientKpiNum ++;
                            break;
                        case 12 :
                            serverKpiNum ++;
                            break;
                        default:
                            break;
                    }
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", bean.getId());
            jsonObject.put("modulesOf", bean.getModuleIds());
            jsonObject.put("moduleIds", bean.getModuleIds());
            jsonObject.put("watchpointKpiNum", watchpointKpiNum);
            jsonObject.put("clientKpiNum", clientKpiNum);
            jsonObject.put("serverKpiNum", serverKpiNum);
            jsonObject.put("name", bean.getName());
            jsonObject.put("description", bean.getDescription());
            jsonObject.put("userId", bean.getUserName());
            jsonObject.put("createTime", bean.getCreateTime());
            containModle = containModle.replace("10", "观察点");
            containModle = containModle.replace("11", "客户端");
            containModle = containModle.replace("12", "服务端");
            jsonObject.put("moduleIdstwo", containModle);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @Override
    @Transactional
    public boolean updateReportModal(ReportModalBean bean) {
        reportDetailDao.deleteTimerReport(bean.getId());
        reportModalTableAndWarnDao.deleteReportModalTableAndWranBeans(bean.getId());
        List<GroupBean> listGroup = bean.getGroup();
        Map<Integer, Integer> map =new  HashMap<>();
        if(null!=listGroup&&listGroup.size()>0){
            List<ReportTimerDetailBean> listReportTim = new ArrayList<>();
            List<ReportModalTableAndWranBean> listyTableWarning =new ArrayList<>();
            for (GroupBean group : listGroup) {
                if(1==group.getType()){
                    ReportTimerDetailBean detailBean = new ReportTimerDetailBean();
                    detailBean.setModuleId(group.getModuleId());
                    detailBean.setPlotId(group.getPlotId());
                    detailBean.setPlotTypeId(group.getPlotTypeId());
                    map.put(group.getModuleId(), 1);
                    if (10 == group.getModuleId()) {
                        detailBean.setWatchpointId(group.getBusiId());
                    } else {
                        detailBean.setWatchpointId(group.getWatchpointId());
                    }
                    detailBean.setReportId(bean.getId());
                    detailBean.setAppId(group.getBusiId());
                    listReportTim.add(detailBean);
                } else {
                    ReportModalTableAndWranBean tableAndWarnbean = new ReportModalTableAndWranBean();
                    tableAndWarnbean.setModalId(bean.getId());
                    tableAndWarnbean.setModultId(group.getModuleId());
                    tableAndWarnbean.setTableHaving(group.getTableHaving());
                    tableAndWarnbean.setWarningHaving(group.getWarningHaving());
                    map.put(group.getModuleId(), 1);
                    listyTableWarning.add(tableAndWarnbean);
                }
            }
            Set<Map.Entry<Integer, Integer>> entry = map.entrySet();
            Iterator<Map.Entry<Integer, Integer>>  ite = entry.iterator();
            String ds ="";
            while(ite.hasNext()){
                Map.Entry<Integer, Integer> en = ite.next();
                ds += (en.getKey()+",");
            }
            ds = ds.substring(0, ds.length()-1);
            bean.setModuleIds(ds);
            reportModalDao.updateReportModal(bean);
            if(null!=listReportTim&&listReportTim.size()>0){
                reportDetailDao.insertAll(listReportTim);
            }
            if(null!=listyTableWarning&&listyTableWarning.size()>0){
                reportModalTableAndWarnDao.insertAll(listyTableWarning);
            }
            
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(bean.getUserId());
            logsBean.setModuleId(19);
            logsBean.setMsg(bean.getUserName()+"修改模板");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsDao.insertLogs(logsBean);
        }
        return true;
    }

    @Override
    public List<ReportModalBean> selectNeedReportModal(int id) {
        return null;
    }

    @Override
    public String selectModuleIds(Integer id) {
        return reportModalDao.selectReportModalModuleIds(id);
    }

    @Override
    @Transactional
    public boolean deleteModalByUserId(Integer userId, SystemUserBean userBean) {
        List<ReportModalBean> list = reportModalDao.selectAllReportModalByUserId(userId);
        for(ReportModalBean bean : list){
            reportDetailDao.deleteTimerReport(bean.getId());
            reportModalTableAndWarnDao.deleteReportModalTableAndWranBeans(bean.getId());
            reportPlanService.deleteReportPlanByModalId(bean.getId());
        }
        reportModalDao.deleteReportModalByUserId(userId);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(19);
        logsBean.setMsg(userBean.getUserName()+"删除用户时删除与用户之前创建的所有模板，和使用这些模板的所有计划。");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean);
        return true;
    }

}
