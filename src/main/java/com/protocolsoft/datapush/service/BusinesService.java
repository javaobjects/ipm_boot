/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusinesService.java
 *创建人: wangjianmin    创建时间: 2018年6月19日
 */
package com.protocolsoft.datapush.service;

import java.util.List;

import com.protocolsoft.datapush.bean.BusinesBean;
import com.protocolsoft.datapush.bean.BusinesKpiBean;

/**
 * @ClassName: BusinesService
 * @Description: 数据推送Service
 * @author wangjianmin
 *
 */

public interface BusinesService {
    
    /**
     * 
     * @Title: addDataPush
     * @Description: 添加数据推送
     * @param bean  接收添加参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    boolean  addDataPush(BusinesBean bean);
    
    /**
     * 
     * @Title: delDataPush
     * @Description: 删除推送设置
     * @param id   业务id 
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    boolean  delDataPush(int id);
    
    /**
     * 
     * @Title: update
     * @Description: 修改推送设置
     * @param bean 接收修改推送设置参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    boolean update(BusinesBean bean);
    
    /**
     * 
     * @Title: getAll
     * @Description: 查询所有推送数据设置
     * @return List<BusinesBean>
     * @author wangjianmin
     */
    List<BusinesBean>  getAll();
    
    /**
     * 
     * @Title: getKpis
     * @Description: 查询某模块的kpi信息
     * @return List<BusinesKpiBean>
     * @author wangjianmin
     */
    List<BusinesKpiBean> getKpis(Integer moduleId);
    
    /**
     * 
     * @Title: getSelectById
     * @Description: 查询一条业务信息
     * @param id 业务id
     * @return BusinesKpiBean
     * @author wangjianmin
     */
    BusinesBean   getSelectById(Integer id);

}
