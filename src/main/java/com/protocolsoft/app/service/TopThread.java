/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: TopThread.java
 *创建人: www    创建时间: 2018年2月5日
 */
package com.protocolsoft.app.service;

import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;
import com.protocolsoft.app.dao.AppTopDao;

/**
 * @ClassName: TopThread
 * @Description: TOP线程
 * @author www
 *
 */
class TopThread implements Runnable {
    
    /**
     * 参数
     */
    private AppTopParamsBean bean;
    
    /**
     * TOP参数
     */
    private AppTopConfigBean configBean;
    
    /**
     * 业务
     */
    private AppTopService service;

    /**
     * DAO
     */
    private AppTopDao dao;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param bean 参数
     * @param service 业务
     * @param dao DAO
     * @param configBean TOP参数
     */
    TopThread(AppTopParamsBean bean, AppTopService service, 
            AppTopDao dao, AppTopConfigBean configBean) {
        this.bean = bean;
        this.service = service;
        this.dao = dao;
        this.configBean = configBean;
    }

    /** (非 Javadoc)
     * <p>Title: run</p>
     * <p>Description: </p>
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        bean.setBean(this.configBean);
        dao.addTop60Batch(bean);
        service.deleteTopData(bean.getStarttime(), 60);
    }
}
