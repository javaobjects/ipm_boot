/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: LogTimeDel.java
 *创建人: wangjianmin    创建时间: 2018年7月19日
 */
package com.protocolsoft.system.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.protocolsoft.system.dao.LogTimeDelDao;

/**
 * @ClassName: LogTimeDel
 * @Description: 定时删除系统日志类
 * @author wangjianmin
 *
 */
@Component
public class LogTimeDel {
    
    /**
     * 注入
     */
    @Autowired(required = false)
    private LogTimeDelDao dao;
    
   
    /**
     * 
     * @Title: selectLogNum
     * @Description: 定时删除系统日志超过一万条的记录
     * @author wangjianmin
     */
    @Scheduled(cron = "0 0 1 * * ?")//每隔1分钟执行一次 
    public void selectLogNum() {
        int num = dao.selectLogNum(); //总记录数
        if(num > 10000){
            int id =  dao.selectById(); //最近记录的最小id
            dao.deleteLog(id);  //删除小于 最近记录的最小id 的记录
        }
    }
}
