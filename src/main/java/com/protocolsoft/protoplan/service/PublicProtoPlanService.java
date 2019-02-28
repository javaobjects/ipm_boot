/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:PublicProtoPlanService
 *创建人:chensq    创建时间:2018年7月16日
 */
package com.protocolsoft.protoplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.dao.PublicProtoPlanDao;

/**
 * @ClassName： PublicProtoPlanService
 * @Description: public协议service
 * @author chensq
 * 
 */
@Service
public class PublicProtoPlanService {

    /**
     * 协议DAO
     */
    @Autowired
    private PublicProtoPlanDao publicprotoPlanDao;
    
    /**
     * 
     * @Title: getProtoPlanList
     * @Description: 返回符合协议集合
     * @return List<ProtoPlanBean>
     * @author chensq
     */
    public List<ProtoPlanBean> getProtoPlanList(){
        return publicprotoPlanDao.getProtoPlanList();
    }
    
    /**
     * 
     * @Title: getProtoPlanBeanByName
     * @Description: 根据名称获取协议
     * @param bean 协议对象
     * @return ProtoPlanBean
     * @author chensq
     */
    public ProtoPlanBean getProtoPlanBeanByName(ProtoPlanBean bean){
        return publicprotoPlanDao.getProtoPlanBeanByName(bean);
    }
   
    /**
     * 
     * @Title: getProtoPlanBeanById
     * @Description: 根据id获取协议
     * @param bean 协议对象
     * @return ProtoPlanBean
     * @author chensq
     */
    public ProtoPlanBean getProtoPlanBeanById(ProtoPlanBean bean){
        return publicprotoPlanDao.getProtoPlanBeanById(bean);
    }
}
