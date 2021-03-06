/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTopService.java
 *创建人: www    创建时间: 2018年1月26日
 */
package com.protocolsoft.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;
import com.protocolsoft.app.bean.ParamBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.app.dao.AppTopDao;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AppTopService
 * @Description: TOP业务
 * @author www
 *
 */
@Service
public class AppTopService {

    /**
     * DAO
     */
    @Autowired
    private AppTopDao dao;
    
    /**
     * 观察点业务
     */
    @Autowired
    private WatchpointDao wpDao;
    
    /**
     * 应用业务
     */
    @Autowired
    private AppService appService;
    
    /**
     * TOP保留时间
     */
    private Map<Integer, Integer> topHoldTime = new HashMap<Integer, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put(60, 14400); // 1分钟粒度保留4小时TOP数据
            put(600, 86400); // 10分钟粒度保留一天TOP数据
            put(3600, 604800); // 1小时粒度保留一周TOP数据
            put(86400, 15552000); // 1天粒度保留半年TOP数据
        }
    };
    
    /**
     * 
     * @Title: getAppTopConfig
     * @Description: 获取配置信息
     * @param moduleId 模块编号
     * @return List<AppTopConfigBean>
     * @author www
     */
    private List<AppTopConfigBean> getAppTopConfig(int moduleId) {
        
        return dao.getAppTopConfig(moduleId);
    }
    
    /**
     * 
     * @Title: getAppTopConfigById
     * @Description: 获取应用TOP配置信息
     * @param moduleId 模块编号
     * @param plotId 绘图编号
     * @return AppTopConfigBean
     * @author www
     */
    public AppTopConfigBean getAppTopConfigById(int moduleId, int plotId) {
        
        return dao.getAppTopConfigById(moduleId, plotId);
    }
    
    /**
     * 
     * @Title: startTop60
     * @Description: 1分钟粒度
     * @param bean 参数
     * @return boolean
     * @author www
     */
    public boolean startTop60(AppTopParamsBean bean) {
        boolean bool = true;
        int moduleId = bean.getModuleId();
        List<WatchpointBean> list = wpDao.getFindAll();
        if (list != null && list.size() > 0) {
            List<String> tables = appService.getAppTableNameByModuleId(
                    bean.getStarttime(), bean.getEndtime(), moduleId);
            if (tables != null && tables.size() > 0) {
                List<AppBusinessBean> appList = appService.getAllAppByModuleId(moduleId);
                if (appList != null && appList.size() > 0) {
                    bean.setTables(tables);
                    List<AppTopConfigBean> topList = this.getAppTopConfig(moduleId);
                    WatchpointBean wpBean = null;
                    AppBusinessBean appBean = null;
                    for (int i = 0, ilen = list.size(); i < ilen; i ++) {
                        wpBean = list.get(i);
                        for (int k = 0, klen = appList.size(); k < klen; k ++) {
                            appBean = appList.get(k);
                            for (int j = 0, jlen = topList.size(); j < jlen; j ++) {
                                bean.setWatchpointId(wpBean.getId());
                                bean.setBusiId(appBean.getId());
                                bean.setBean(topList.get(j));
                                bool = dao.addTop60Batch(bean);
                            }
                        }
                    }
                }
            }
        }
        this.deleteTopData(bean.getStarttime(), 60);
        
        return bool;
    }
    
    /**
     * 
     * @Title: addTopBacth
     * @Description: TOP排名计算
     * @param bean 参数
     * @param step 粒度
     * @return boolean
     * @author www
     */
    public boolean addTopBacth(ParamBean bean, int step) {
        boolean bool = false;
        switch (step) {
            case 600:
                bool = dao.addTop600Bacth(bean);
                break;
            case 3600:
                bool = dao.addTop3600Bacth(bean);
                break;
            case 86400:
                bool = dao.addTop86400Bacth(bean);
                break;
            default:
                break;
        }
        this.deleteTopData(bean.getStarttime(), step);
        
        return bool;
    }
    
    /**
     * 
     * @Title: deleteTopData
     * @Description: 删除TOP数据
     * @param step 粒度
     * @return boolean
     * @author www
     */
    public boolean deleteTopData(long time, int step) {
        boolean bool = false;
        time = time - topHoldTime.get(step);
        bool = dao.deleteTopData(time, "ipm_app_topn_" + step);
        
        return bool;
    }
    
    /**
     * 
     * @Title: getTopInfo
     * @Description: 获取TOP信息
     * @param bean 参数
     * @return List<PlotSimpleBean>
     * @author www
     */
    public List<PlotSimpleBean> getTopInfo(PlotParamBean bean) {
        
        return dao.getTopInfo(bean);
    }
    
    /**
     * 
     * @Title: selectTop
     * @Description: 获取TOp数据(实时计算)
     * @param bean 参数
     * @return List<PlotSimpleBean>
     * @author www
     */
    public List<PlotSimpleBean> selectTop(AppTopParamsBean bean) {
        
        return dao.selectTop(bean);
    }
}
