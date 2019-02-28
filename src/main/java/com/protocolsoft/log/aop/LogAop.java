/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:LogAop
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.protocolsoft.log.annotation.Log;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.service.LogsService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.utils.DateUtils;

/**
 * AOP切面织入记录操作日志
 * 2017年9月4日 上午10:38:50
 * @author yan
 * @version
 * @see
 */
@Aspect
@Component
public class LogAop {
    
    /**
     * 日志管理业务对象
     */
    @Autowired
    private LogsService logsService;
    
    /**
     * 切入点
     * 2017年9月4日 下午2:30:56
     * @param
     * @exception 
     * @see
     */
    @Pointcut("@annotation(com.protocolsoft.log.annotation.Log)")
    public void log(){
    }
     
    /**
     * 前置通知
     * @param joinPoint
     */
/*    @Before("log()")
    public void beforeExec(JoinPoint joinPoint){
        MethodSignature ms=(MethodSignature)joinPoint.getSignature();
        Method method=ms.getMethod();
    }*/
     
    /**
     * 后置通知
     * 2017年9月4日 下午2:30:14
     * @param
     * @exception 
     * @see
     */
    @After("log()")
    public void afterExec(JoinPoint joinPoint){
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();
        //小模块id
        int smallModuleId = method.getAnnotation(Log.class).smallModuleId();
        
        //日志描述
        String description = method.getAnnotation(Log.class).description();
        
        LogsBean bean = new LogsBean();

        HttpServletRequest request =((ServletRequestAttributes) 
                RequestContextHolder.getRequestAttributes()).getRequest(); 
        HttpSession session =request.getSession(); 
        SystemUserBean user = (SystemUserBean) session.getAttribute("userBean");
        if (user != null) {
            bean.setUserId(user.getId());
            bean.setModuleId(smallModuleId);
            bean.setMsg(description);
            bean.setTime(DateUtils.getNowTimeSecond());
            logsService.addLogs(bean);
        }
    }
    
    /**
     * 环绕通知
     * 2017年9月4日 下午2:28:49
     * @param ProceedingJoinPoint
     * @return Object
     * @throws Throwable 
     * @exception 
     * @see
     */
    @Around("log()")
    public Object aroundExec(ProceedingJoinPoint jp) throws Throwable {
        Object result = null;
        result = jp.proceed();
        return result;
    }

    /**
     * @return the logsService
     */
    public LogsService getLogsService() {
        return logsService;
    }

    /**
     * @param logsService the logsService to set
     */
    public void setLogsService(LogsService logsService) {
        this.logsService = logsService;
    }
}
