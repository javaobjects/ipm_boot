/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserConfigureServiceImpl
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.user.bean.UserConfigureBean;
import com.protocolsoft.user.dao.UserConfigureDao;
import com.protocolsoft.user.service.UserConfigureService;

/**
 * 配置信息
 * 2017年9月4日 上午9:57:03
 * @author long
 * @version
 * @see
 */
@Service
public class UserConfigureServiceImpl implements UserConfigureService {
    /**
     * userConfigureDao注入
     */
    @Autowired(required=false)
    private UserConfigureDao userConfigureDao;
    
    @Override
    public List<UserConfigureBean> getUserConfigureBean(int userId) {
        return userConfigureDao.getUserConfigureBean(userId);
    }

    @Override
    public List<UserConfigureBean> getSessionUserConfigureBean(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<UserConfigureBean> userConfigureList=(List<UserConfigureBean>) session.getAttribute("userConfigureList");
        return userConfigureList;
    }

    @Override
    public Map<String, String> delUserConfigureByUserId(int userId) {
        Map<String, String> map =new HashMap<String, String>();
        boolean isSuccess = userConfigureDao.delUserConfigureByUserId(userId);
        if (isSuccess){
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        return map;
    }

}
