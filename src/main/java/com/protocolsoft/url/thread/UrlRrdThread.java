/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlRrdThread.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.thread;

import java.util.ArrayList;
import java.util.List;

import com.protocolsoft.kpi.service.UrlRrdService;
import com.protocolsoft.url.bean.UrlKpiDataBean;
import com.protocolsoft.url.bean.UrlKpiParamBean;
import com.protocolsoft.url.bean.UrlSetBean;
import com.protocolsoft.url.dao.UrlDao;

/**
 * @ClassName: UrlRrdThread
 * @Description: RRD写入线程
 * @author www
 *
 */
public class UrlRrdThread implements Runnable {
    
    /**
     * 参数
     */
    private UrlKpiParamBean paramBean;
    
    /**
     * RRD
     */
    private UrlRrdService service;
    
    /**
     * URL设置
     */
    private UrlSetBean setBean;
    
    /**
     * DAO
     */
    private UrlDao dao;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param paramBean 参数
     * @param setBean 业务信息
     * @param dao DAO
     */
    public UrlRrdThread(UrlKpiParamBean paramBean, UrlSetBean setBean, UrlDao dao) {
        service = new UrlRrdService();
        this.paramBean = paramBean;
        this.setBean = setBean;
        this.dao = dao;
    }

    /** (非 Javadoc)
     * <p>Title: run</p>
     * <p>Description: </p>
     * @see java.lang.Runnable#run()
     */
    public void run() {
        List<UrlKpiDataBean> data = dao.getUrlKpiData(paramBean);
        data.addAll(dao.selectKpiInSub(paramBean));
        UrlKpiDataBean dataBean = null;
        List<Number> values = new ArrayList<Number>();
        float errNum = 0;
        for (int i = 0, len = data.size(); i < len; i ++) {
            values.clear();
            dataBean = data.get(i);
            service.create(paramBean.getWatchpointId(), setBean.getId(), dataBean.getId());
            values.add(dataBean.getEthernetTraffic() / 60);
            errNum = dataBean.getSessionNum();
            values.add(errNum / 60);
            values.add(dataBean.getResponseDelay());
            values.add(dataBean.getPageloadDelay());
            values.add(dataBean.getRttDurDelay());
            errNum = dataBean.getHttp400Count();
            values.add(errNum / 60);
            errNum = dataBean.getHttp500Count();
            values.add(errNum / 60);
            errNum = dataBean.getHttp400Count() + dataBean.getHttp500Count();
            values.add(errNum / 60);
            values.add(values.get(1));
            service.update(paramBean.getEndtime(), paramBean.getWatchpointId(), setBean.getId(), dataBean.getId(), values);
        }
    }
}
