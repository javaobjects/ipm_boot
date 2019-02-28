/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: QuartzManager.java
 *创建人: wangjianmin    创建时间: 2018年7月9日
 */
package com.protocolsoft.word.task;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.service.BookService;
import com.protocolsoft.word.service.ReportPlanService;



/**
 * @ClassName: QuartzManager
 * @Description: 定时类
 * @author wangjianmin
 *
 */
public class QuartzManager {
   
    /**
     * 
     */
    static  SchedulerFactory schedulerfactory = new StdSchedulerFactory();
    /**
     * 
     * @Title: addJob
     * @Description: 定时执行任务
     * @param jobClass 任务 类
     * @param cron void  时间设置
     * @param bean  接收任务bean
     * @author wangjianmin
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void  addJob(JobBean jobBean, Class jobClass, 
            String cron, ReportPlanBean bean, BookService bookService, ReportPlanService reportPlanService) {        //通过schedulerFactory获取一个调度器  
        Scheduler scheduler=null;  
        try {  
            scheduler=schedulerfactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobBean.getJobName(), 
                    jobBean.getJobGroupName()).build();
            //设置参数 
            jobDetail.getJobDataMap().put("planBean", bean);
            jobDetail.getJobDataMap().put("jobBean", jobBean);
            jobDetail.getJobDataMap().put("bookService", bookService);
            jobDetail.getJobDataMap().put("reportPlanService", reportPlanService);
            // 触发器 
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(jobBean.getTriggerName(), jobBean.getTriggerGroupName());
            triggerBuilder.startNow();
            // 触发器时间设定  
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);  

            // 启动  
            if (!scheduler.isShutdown()) {  
                scheduler.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    /**
     * 
     * @Title: getCron
     * @Description: 转换cron表达式
     * @param date 时间
     * @return String
     * @author wangjianmin
     */
    public String getCron(Date  date){  
        String dateFormat="ss mm HH dd MM ? yyyy";  
        return  fmtDateToStr(date, dateFormat);  
    }  
     
     /**
      * 
      * @Title: fmtDateToStr
      * @Description: 转换成年，月，日
      * @param date  时间
      * @param dtFormat 日期格式
      * @return String
      * @author wangjianmin
      */
    public String fmtDateToStr(Date date, String dtFormat) {  
        if (date == null) {
            return "";  
        } else{
            try {  
                SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);  
                return dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();  
                return "";  
            }   
        }
    }
    
    /**
     * 
     * @Title: removeJob
     * @Description: 删除 定时执行任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名 
     * @param triggerName  触发器名
     * @param triggerGroupName void 触发器组名 
     * @author wangjianmin
     */
    public void removeJob(String jobName, String jobGroupName,
            String triggerName, String triggerGroupName, Integer code) {
        //通过schedulerFactory获取一个调度器  
        Scheduler scheduler=null;
        try {  
            scheduler=schedulerfactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            if(null!=code){
                scheduler.shutdown(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    /**
     * 
     * @Title: removeScheduleF
     * @Description: 
     * @author 刘斌
     */
    public static void removeScheduleF() {
        //通过schedulerFactory获取一个调度器  
        try {
            Collection<Scheduler> scheduler = schedulerfactory.getAllSchedulers();
            if(null!=scheduler && scheduler.size()>0){
                for(Scheduler schedulers : scheduler){
                    schedulers.shutdown(true);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    } 
}
