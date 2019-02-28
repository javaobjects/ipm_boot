package com.protocolsoft.word.service;

import java.util.List;

import com.protocolsoft.common.bean.DrawingOptionsBean;

/**
/**
 * @ClassName: GetBookBeanFromMySQLService
 * @Description: 获取需要的定时报表对象的集合的接口
 * @author 刘斌
 *
 */
public interface GetBookBeanFromMySQLService {

    
    /**
     * @Title: getDrawingOptionsBeanFromMySQLService
     * @Description: 获取报表对象列表
     * @param typeId
     * @return List<DrawingOptionsBean>
     * @author 刘斌
     */
    List<DrawingOptionsBean> getDrawingOptionsBeanFromMySQLService(int typeId);
    
    /**
     * @Title: getTrueDrawingOptionsBeanFromMySQLService
     * @Description: 获取报表对象列表
     * @param typeId
     * @return List<DrawingOptionsBean>
     * @author 刘斌
     */
    List<DrawingOptionsBean> getTrueDrawingOptionsBeanFromMySQLService(int typeId);
}
