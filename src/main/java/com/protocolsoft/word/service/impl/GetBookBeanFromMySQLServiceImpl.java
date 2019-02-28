/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>

 */
package com.protocolsoft.word.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.word.dao.GetBookBeanFromMySQLDao;
import com.protocolsoft.word.service.GetBookBeanFromMySQLService;

/**
 * @ClassName: GetBookBeanFromMySQLServiceImpl
 * @Description: 获取所需的定时报表对象的集合的接口
 * @author 刘斌
 *
 */
@Service
public class GetBookBeanFromMySQLServiceImpl implements
        GetBookBeanFromMySQLService {
    /**
     * @Fields getBookBeanFromMySQLDao : 获取定时报表对象
     */
    @Autowired
    GetBookBeanFromMySQLDao getBookBeanFromMySQLDao;
    
    /* (非 Javadoc)
     * <p>Title: getDrawingOptionsBeanFromMySQLService</p>
     * <p>Description: 获取定时报表对象集合</p>
     * @param typeId
     * @return
     * @see com.protocolsoft.word.service.GetBookBeanFromMySQLService#getDrawingOptionsBeanFromMySQLService(int)
     */
    @Override
    public List<DrawingOptionsBean> getDrawingOptionsBeanFromMySQLService(
            int typeId) {
        return getBookBeanFromMySQLDao.getDrawingOptionsBeanList(typeId);
    }

    /* (非 Javadoc)
     * <p>Title: getTrueDrawingOptionsBeanFromMySQLService</p>
     * <p>Description: 获取定时报表对象集合</p>
     * @param typeId
     * @return
     * @see com.protocolsoft.word.service.GetBookBeanFromMySQLService#getTrueDrawingOptionsBeanFromMySQLService(int)
     */
    @Override
    public List<DrawingOptionsBean> getTrueDrawingOptionsBeanFromMySQLService(
            int typeId) {
        try {
            return getBookBeanFromMySQLDao.getTrueDrawingOptionsBeanList(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
