/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusinesServiceImpl.java
 *创建人: wangjianmin    创建时间: 2018年6月19日
 */
package com.protocolsoft.datapush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.datapush.bean.BusinesBean;
import com.protocolsoft.datapush.bean.BusinesKpiBean;
import com.protocolsoft.datapush.dao.BusinesDao;
import com.protocolsoft.datapush.service.BusinesService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: BusinesServiceImpl
 * @Description: 数据推送实现
 * @author wangjianmin
 *
 */
@Service
public class BusinesServiceImpl implements  BusinesService{
    /**
     * BusinesDao
     */
    @Autowired
    private BusinesDao businesDao;
    
    /**
     * 服务端管理业务对象
     */
    @Autowired
    private ServerManagementService serverManagementService;
    
    /**
     * WatchpointDao 对象
     */
    @Autowired
    private WatchpointDao dao;
    
    /**
     * appBusinessDao注入
     */
    @Autowired(required=false)
    private AppBusinessDao appBusinessDao;
   
    @Override
    public boolean addDataPush(BusinesBean bean) {
        return businesDao.addDataPush(bean);
    }

    @Override
    public boolean delDataPush(int id) {
        return businesDao.delDataPush(id);
    }


    @Override
    public boolean update(BusinesBean bean) {
        return businesDao.update(bean);
    }

    @Override
    public List<BusinesBean> getAll() {
        List<BusinesBean> list = businesDao.getAll();
        List<WatchpointBean> gList  = dao.getFindAll(); //所有观察点业务数据
        //所有服务端业务数据
        List<AppBusinessBean> fList = serverManagementService.getAllServerSide(12);
        //所有客户端业务数据
        List<AppBusinessBean> klist = appBusinessDao.selectAppBusinessesByModuleId(11);
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getModuleType()) {
                case 10:
                    for (WatchpointBean watchpointBean : gList) {
                        if(watchpointBean.getId() == list.get(i).getBusiId()){
                            list.get(i).setBusiName(watchpointBean.getName());
                        }
                    }
                    break;
                case 11:
                    for (WatchpointBean watchpointBean : gList) {
                        if(watchpointBean.getId() == list.get(i).getWatchpointId()){
                            list.get(i).setWatchpointName(watchpointBean.getName());
                        }
                    }
                    for (AppBusinessBean appBusinessBean : klist) {
                        if(appBusinessBean.getId() == list.get(i).getBusiId()){
                            list.get(i).setBusiName(appBusinessBean.getName());
                        }
                    }
                    break;
                case 12:
                    for (WatchpointBean watchpointBean : gList) {
                        if(watchpointBean.getId() == list.get(i).getWatchpointId()){
                            list.get(i).setWatchpointName(watchpointBean.getName());
                        }
                    }
                    for (AppBusinessBean appBusinessBean : fList) {
                        if(appBusinessBean.getId() == list.get(i).getBusiId()){
                            list.get(i).setBusiName(appBusinessBean.getName());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public List<BusinesKpiBean> getKpis(Integer moduleId) {
        
        return businesDao.getKpis(moduleId);
    }

    @Override
    public BusinesBean getSelectById(Integer id) {
        return businesDao.getSelectById(id);
    }

}
