/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UsabilityService
 *创建人:wjm    创建时间:2018年3月16日
 */
package com.protocolsoft.usability.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.usability.bean.UsabilityBean;

/**
 * 
 * @ClassName: UsabilityService
 * @Description: 应用可用性Service层
 * @author wangjianmin
 *
 */
public interface UsabilityService {
    
    /**
     * 
     * @Title: getUsabilityAll
     * @Description: 查询所有应用可用性设置
     * @return List<UsabilityBean>
     * @author wangjianmin
     */
    List<UsabilityBean> getUsabilityAll();

    /**
     * 
     * @Title: addUsability
     * @Description: 添加应用可用性设置
     * @param request 请求
     * @param bean    接收参数
     * @return int
     * @author wangjianmin
     */
    int addUsability(HttpServletRequest request, UsabilityBean bean);

    /**
     * 
     * @Title: getByName
     * @Description: 验证名称有没有重复
     * @param name  业务名称
     * @param id    业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean getByName(String name, int id);

    /**
     * 
     * @Title: getByPort
     * @Description: 验证端口是否重复
     * @param port  业务端口
     * @param id   业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean getByPort(String port, int id);

    /**
     * 
     * @Title: delUsabilityId
     * @Description: 删除一条记录
     * @param request 请求
     * @param id  业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean delUsabilityId(HttpServletRequest request, int id);

    /**
     * 
     * @Title: updateUsability
     * @Description: 修改一条记录
     * @param request 请求
     * @param bean  接收参数
     * @return int
     * @author wangjianmin
     */
    int updateUsability(HttpServletRequest request, UsabilityBean bean);
    
}
