/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreadPoolListener
 *创建人:chensq    创建时间:2017年12月1日
 */
package com.protocolsoft.common.listener;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.protocolsoft.alarm.thread.AlarmThreadPool;
import com.protocolsoft.url.service.UrlService;


/**
 * @ClassName: AlarmThreadPoolListener
 * @Description: 告警线程监听,服务停止前,停止线程
 * @author chensq
 *
 */
public class ThreadPoolListener implements ServletContextListener  {
 
    /** (非 Javadoc)
     * <p>Title: contextDestroyed</p>
     * <p>Description: </p>
     * @param sce
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override  
    public void contextDestroyed(ServletContextEvent sce) {
        
        // 告警线程停止
        try {
            if (AlarmThreadPool.fixedThreadPool!=null){
                AlarmThreadPool.fixedThreadPool.shutdown();
                if (!AlarmThreadPool.fixedThreadPool.awaitTermination(10, TimeUnit.MILLISECONDS)){  
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。  
                    AlarmThreadPool.fixedThreadPool.shutdownNow();  
                }
            } 
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。  
            AlarmThreadPool.fixedThreadPool.shutdownNow();  
        }
        
        // URL线程停止
        try {
            if (UrlService.threads != null) {
                UrlService.threads.shutdown();
                if (!UrlService.threads.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    UrlService.threads.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            UrlService.threads.shutdownNow();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) { }
}
