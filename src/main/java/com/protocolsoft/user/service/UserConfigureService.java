/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserConfigureService
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.user.bean.UserConfigureBean;

/**
 * UserConfigureService
 * 2017年9月4日 上午9:55:56
 * @author long
 * @version
 * @see
 */
public interface UserConfigureService {
    
    /**
     * 配置信息
     * 2017年9月4日 上午9:56:09
     * @param
     * @return List<UserConfigureBean>
     * @exception 
     * @see
     */
    List<UserConfigureBean> getUserConfigureBean(int userId);
    
    /**
     * 获取session配置信息
     * 2017年9月4日 上午11:23:29
     * @param
     * @return List<UserConfigureBean>
     * @exception 
     * @see
     */
    List<UserConfigureBean> getSessionUserConfigureBean(HttpServletRequest request);
    
    /**
     * 根据userId删除配置信息
     * 2017年9月5日 下午4:01:43
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String>  delUserConfigureByUserId(int userId);
}
