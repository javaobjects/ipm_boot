/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SessionListener.java
 *创建人: www    创建时间: 2017年9月15日
 */
package com.protocolsoft.system.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.controller.SystemUserController;
import com.protocolsoft.user.dao.SystemUserDao;
import com.protocolsoft.utils.DateUtils;

/**
 * @ClassName: SessionListener
 * @Description: 会话监听
 * @author www
 *
 */
public class SessionListener implements ServletContextListener, HttpSessionListener {
    
    /**
     * 日志管理业务对象
     */
    private LogsDao logsDao;
    
    /**
     * 用户业务
     */
    private SystemUserDao userDao;
    
    /**
     * spring上下文
     */
    private WebApplicationContext context;

    /** (非 Javadoc)
     * <p>Title: sessionCreated</p>
     * <p>Description: </p>
     * @param se
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se) {
        
    }

    /** (非 Javadoc)
     * <p>Title: sessionDestroyed</p>
     * <p>Description: </p>
     * @param se
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        SystemUserBean user = (SystemUserBean) se.getSession().getAttribute("userBean");
        if (user != null) {
            if (user.getLoginState() == 1) {
                LogsBean bean = new LogsBean();
                bean.setUserId(user.getId());
                bean.setModuleId(4);
                bean.setMsg(user.getUserName() + "用户登录超时，强制退出。");
                bean.setTime(DateUtils.getNowTimeSecond());
                logsDao.insertLogs(bean);
                SystemUserController.setCount(SystemUserController.getCount() - 1);
                List<Map<String, Object>> list = SystemUserController.getListMap();
                for (int i = 0; i < list.size(); i++) {
                    HttpSession session = (HttpSession)list.get(i).get("userSession");
                    SystemUserBean userBean = (SystemUserBean) session.getAttribute("userBean");
                    if(userBean.getUserName().equals(user.getUserName())){
                        list.remove(i);
                    }
                }
                SystemUserController.setListMap(list);
            }
            userDao.getUserLoginState(user.getId(), 0, 1);
        }
    }

    /** (非 Javadoc)
     * <p>Title: contextInitialized</p>
     * <p>Description: </p>
     * @param sce
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        logsDao = context.getBean(LogsDao.class);
        userDao = context.getBean(SystemUserDao.class);
    }

    /** (非 Javadoc)
     * <p>Title: contextDestroyed</p>
     * <p>Description: </p>
     * @param sce
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
