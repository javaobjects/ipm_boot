/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: LoginFilter.java
 *创建人: www    创建时间: 2017年9月18日
 */
package com.protocolsoft.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginFilter
 * @Description: 登录
 * @author www
 *
 */
public class LoginFilter implements Filter {
    
    /**
     * 默认URL
     */
    private final static String DEFAULT_URL = "/";
    
    /**
     * 登录URL
     */
    private final static String LOGIN_URL = "/login.html";
    
    /** (非 Javadoc)
     * <p>Title: init</p>
     * <p>Description: </p>
     * @param filterConfig
     * @throws ServletException Exception if has error
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /** (非 Javadoc)
     * <p>Title: doFilter</p>
     * <p>Description: </p>
     * @param request
     * @param response
     * @param chain
     * @throws IOException Exception if has error
     * @throws ServletException Exception if has error
     * @see javax.servlet.Filter#doFilter(
     * javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String url = httpReq.getRequestURI();
        if (!url.equals(DEFAULT_URL) && !url.equals(LOGIN_URL)) {
            if (httpReq.getSession().getAttribute("userBean") == null) {
                ((HttpServletResponse) response).sendRedirect("/");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /** (非 Javadoc)
     * <p>Title: destroy</p>
     * <p>Description:</p>
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

    }
}
