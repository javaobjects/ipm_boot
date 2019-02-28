/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewDao
 *创建人:long    创建时间:2017年9月7日
 */
package com.protocolsoft.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.common.bean.DrawingOptionsBean;
/**
 * @ClassName: GetBookBeanFromMySQLDao
 * @Description: 从数据库中获取有用的定时报表对象的集合
 * @author 刘斌
 *
 */
@Repository
public interface GetBookBeanFromMySQLDao {
    /**
     * @Title: getDrawingOptionsBeanList
     * @Description: 按归档类型获取列表
     * @param typeId
     * @return List<DrawingOptionsBean>
     * @author 刘斌
     */
    @Select("select module_id as modleId,watchpoint_id as watchpointId,app_id as clientId,"
            + " plot_id as plotId,plot_type_id as plotTypeId "
            + " from ipm_timer_report_detail where report_id = #{typeId}")
    List<DrawingOptionsBean> getDrawingOptionsBeanList(int typeId);
    
    /**
     * @Title: getTrueDrawingOptionsBeanList
     * @Description: 按归档类型获取当前未被冻结的报表列表
     * @param typeId
     * @return List<DrawingOptionsBean>
     * @author 刘斌
     */
    @Select("select module_id as modleId,watchpoint_id as clientId,app_id as watchpointId,"
            + " plot_id as plotId,plot_type_id as plotTypeId "
            + " from ipm_timer_report_detail where report_id = #{typeId} and state = 1")
    List<DrawingOptionsBean> getTrueDrawingOptionsBeanList(int typeId);

}
