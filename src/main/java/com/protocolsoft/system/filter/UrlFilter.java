/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlFilter.java
 *创建人: www    创建时间: 2018年3月26日
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

/**
 * @ClassName: UrlFilter
 * @Description: URL过滤器
 * @author www
 *
 */
public class UrlFilter implements Filter {
    
    /**
     * 过滤URL
     */
    private final static String FILTER_URL = "/center/remoteMethod.do";

    /** (非 Javadoc)
     * <p>Title: init</p>
     * <p>Description: </p>
     * @param filterConfig
     * @throws ServletException exc
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    /** (非 Javadoc)
     * <p>Title: doFilter</p>
     * <p>Description: </p>
     * @param request
     * @param response
     * @param chain
     * @throws IOException IO
     * @throws ServletException SE
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String url = ((HttpServletRequest) request).getRequestURI();
        String ipmCenterId = request.getParameter("ipmCenterId");
        if (!FILTER_URL.equals(url) && !url.contains("getRemote")  
                && ipmCenterId != null && !"1".equals(ipmCenterId)) {
            request.setAttribute("remoteUrl", url);
            request.getRequestDispatcher(FILTER_URL).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    /** (非 Javadoc)
     * <p>Title: destroy</p>
     * <p>Description: </p>
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        
    }

}
