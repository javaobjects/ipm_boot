/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.daoprovider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.word.bean.ReportBusinessBean;
import com.protocolsoft.word.bean.ReportEmailBean;
import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.dao.ReportBusinessDao;
import com.protocolsoft.word.dao.ReportEmailDao;
import com.protocolsoft.word.dao.ReportPlanDao;
import com.protocolsoft.word.service.BookService;
import com.protocolsoft.word.service.ReportPlanService;
import com.protocolsoft.word.task.JobBean;
import com.protocolsoft.word.task.MyJob;
import com.protocolsoft.word.task.QuartzManager;

/**
 * 
 * @ClassName: TimerByReportProvider
 * @Description: 
 * @author 刘斌
 *
 */
@Service("phoneBlacklistCache")
public class TimerByReportProvider {
    
    /**
     * Email数据
     */
    @Autowired
    private ReportEmailDao reportEmailDao;
    
    /**
     * 与计划关联业务数据
     */
    @Autowired
    private ReportBusinessDao reportBusinessDao;
    
    /**
     * 自定义生成报表工具
     */
    @Autowired
    private BookService bookService;
    
    /**
     * 报表计划数据Dao
     */
    @Autowired
    private ReportPlanDao reportPlanDao;
    
    /**
     * 报表计划数据Service
     */
    @Autowired
    private ReportPlanService reportPlanService;
    

    /**
     * 
     * @Title: init
     * @Description: 
     * @author 刘斌
     */
    @PostConstruct
    public void init(){
        List<ReportPlanBean> list = reportPlanDao.selectAllReportPlanByMySelf();
        if(null!=list&&list.size()>0){
            long nowTime = System.currentTimeMillis();
            for(ReportPlanBean bean : list){
                String name = bean.getName();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd~HH:mm:ss");
                name += df.format(new Date());
                bean.setName(name);
                List<ReportBusinessBean>  listBus = reportBusinessDao.selectReportBusiness(bean.getId());
                List<ReportEmailBean> listEmail = reportEmailDao.selectReportEmails(bean.getId());
                if(null!=listBus){
                    bean.setListBus(listBus);
                }
                if(null!=listEmail){
                    bean.setList(listEmail);
                }
                Long sendTime = bean.getNextSendTime();
                String idStr = bean.getId()+"";
                Date dates;
                if(null!=sendTime&&sendTime*1000>nowTime){
                    dates = new Date(sendTime*1000);
                    QuartzManager   quartzManager = new QuartzManager();
                    String date = quartzManager.getCron(dates);
                    quartzManager.addJob(new JobBean(idStr, idStr, idStr, idStr), MyJob.class, date, bean, bookService, reportPlanService);
                }else{
                    dates = new Date(nowTime+10000);
                    try {
                        bookService.userModalProductReport(bean);
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
               
            }
        }
    }
    
    /**
     * 
     * @Title: destroy
     * @Description: 
     * @author 刘斌
     */
    @PreDestroy
    public void destroy() {
        QuartzManager.removeScheduleF();
    }
}