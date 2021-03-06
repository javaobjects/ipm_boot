/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmBusTagService
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.depthanaly.bean.BusTagListBean;
import com.protocolsoft.depthanaly.bean.DeptMsgLogBean;
import com.protocolsoft.depthanaly.bean.IpmBusTagBean;

/**
 * 
 * @ClassName: IpmBusTagService
 * @Description: 报文交易Service层
 * @author wangjianmin
 *
 */
public interface IpmBusTagService {

    /**
     * 
     * @Title: addBusTag
     * @Description:添加报文自定义列
     * @param bean 接收参数
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, Integer>  addBusTag(HttpServletRequest request, BusTagListBean bean);

    /**
     * 
     * @Title: deleteBusTag
     * @Description: 删除报文自定义数据
     * @param appId 业务ID
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, Integer> deleteBusTag(HttpServletRequest request, int appId);

    /**
     * 
     * @Title: selectAll
     * @Description: 查询所有
     * @return List<IpmBusTagBean>
     * @author wangjianmin
     */
    List<IpmBusTagBean>  selectAll();
    
    /**
     * 
     * @Title: updateBusTag
     * @Description: 修改自定义报文数据
     * @param bean 接收参数
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, Integer>  updateBusTag(HttpServletRequest request, BusTagListBean bean);
   
    /**
     * 
     * @Title: getPlotData
     * @Description: 报文交易绘图
     * @param bean 绘图参数
     * @return PlotDataBean
     * @author wangjianmin
     */
    PlotDataBean getPlotData(PlotParamBean bean);
  
    /**
     * 
     * @Title: getSimpleData
     * @Description: 报文交易十字格数据
     * @param bean 十字格数据 参数
     * @return SimpleDataBean
     * @author wangjianmin
     */
    SimpleDataBean getSimpleData(SimpleParamBean bean);
  
    /**
     * 
     * @Title: getData
     * @Description: 获取报文数据
     * @param bean 接收参数
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    List<DeptMsgLogBean> getData(DeptMsgLogBean bean);
    
    /**
     * 
     * @Title: selectByLogId
     * @Description: 根据ID查询数据
     * @param id 业务ID
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    List<DeptMsgLogBean>  selectByLogId(int id);


    /**
     * @Title: getRemoteData
     * @Description: 获取远程数据
     * @param bean 接收参数
     * @return List<DeptMsgLogBean>
     * @author www
     */
    List<Object> getRemoteData(DeptMsgLogBean bean);


    /**
     * @Title: selectAllByIds
     * @Description: 获取数据
     * @param ids 编号
     * @return List<IpmBusTagBean>
     * @author www
     */
    List<IpmBusTagBean> selectAllByIds(String ids);


    /**
     * @Title: selectAll
     * @Description: 获取数据
     * @param request 请求
     * @return List<IpmBusTagBean>
     * @author www
     */
    List<IpmBusTagBean> selectAll(HttpServletRequest request);
}
