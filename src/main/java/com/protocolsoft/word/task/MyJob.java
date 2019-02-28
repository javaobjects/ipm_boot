/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: MyJob.java
 *创建人: wangjianmin    创建时间: 2018年7月9日
 */
package com.protocolsoft.word.task;

import java.io.IOException;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.word.bean.ReportPlanBean;
import com.protocolsoft.word.service.BookService;
import com.protocolsoft.word.service.ReportPlanService;


/**
 * @ClassName: MyJob
 * @Description: 定时任务类
 * @author wangjianmin
 *
 */
public class MyJob  implements Job{
   
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        ReportPlanBean bean = (ReportPlanBean) data.get("planBean");
        BookService bookService = (BookService) data.get("bookService");
        ReportPlanService reportPlanService = (ReportPlanService) data.get("reportPlanService");
        try {
            String result = bookService.userModalProductReport(bean);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if((boolean) jsonObject.get("result")){
                reportPlanService.deleteReportPlan(bean.getId(), null);
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //执行任务完成，删除定时任务
        JobBean jobBean = (JobBean) data.get("jobBean");
        QuartzManager qm = new QuartzManager();
        qm.removeJob(jobBean.getJobName(), jobBean.getJobGroupName(), 
                jobBean.getTriggerName(), jobBean.getTriggerGroupName(), null);
        
    }   
}
