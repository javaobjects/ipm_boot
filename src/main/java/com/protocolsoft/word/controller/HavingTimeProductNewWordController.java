/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.protocolsoft.word.service.HavingTimeProductNewWordService;
import com.protocolsoft.word.service.ReportHistoryService;

 /**
 * @ClassName: HavingTimeProductNewWordController
 * @Description: 定时生成报表类
 * @author 刘斌
 *
 */
@Controller
public class HavingTimeProductNewWordController {
    
    /**
     * @Fields reportHistoryService : 报表历史记录
     */
    @Autowired
    private ReportHistoryService reportHistoryService;
    
    /**
     * @Fields havingTimeProductNewWordService : 定时生成报表
     */
    @Autowired
    private HavingTimeProductNewWordService havingTimeProductNewWordService;
    
    /**
     * @Title: productWordByWeek
     * @Description: 每周一晚上1点定时生成周报 99
     * @author 刘斌
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void productWordByDate() {
        havingTimeProductNewWordService.productWordByDate();
    }
    
    /**
     * @Title: productWordByWeek
     * @Description: 每周一晚上1点定时生成周报
     * @author 刘斌
     */
    @Scheduled(cron = "0 0 1 ? * MON")
    public void productWordByWeek() {
        havingTimeProductNewWordService.productWordByWeek();
    }

    /**
     * @Title: productWordByMonth
     * @Description: 每个月一号晚上0点20分生成上一个月的月报
     * @author 刘斌
     */
    @Scheduled(cron = "0 20 0 1 * *")
    public void productWordByMonth() {
        havingTimeProductNewWordService.productWordByMonth();
    }
    
    /**
     * @Title: productWordByMonth
     * @Description: 每个月一号晚上0点20分生成上一个月的月报
     * @author 刘斌
     */
    @Scheduled(cron = "0 10 0 2 * *")
    public void deleteWordByMonth() {
        reportHistoryService.deleteReportHistoryByCount();
    }
    
    /**
     * @Title: productWordByWeek
     * @Description: 每周一晚上1点定时生成周报 99
     * @author 刘斌
     */
    @Scheduled(cron = "0 */20 * * * ?")
    public void productWordByDates() {
        havingTimeProductNewWordService.productWordByDate();
        havingTimeProductNewWordService.productWordByMonth();
        havingTimeProductNewWordService.productWordByWeek();
        reportHistoryService.deleteReportHistoryByCount();
    }
}
