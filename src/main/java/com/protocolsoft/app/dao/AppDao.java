/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppDao.java
 *创建人: www    创建时间: 2018年1月12日
 */
package com.protocolsoft.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.protocolsoft.app.bean.DBSessionBean;
import com.protocolsoft.app.bean.HttpSessionBean;
import com.protocolsoft.app.bean.SessionParamsBean;
import com.protocolsoft.app.bean.TableParamBean;
import com.protocolsoft.app.provider.AppProvider;

/**
 * @ClassName: AppDao
 * @Description: 应用DAO
 * @author www
 *
 */
@Repository
public interface AppDao {

    /**
     * 
     * @Title: getAppTableName
     * @Description: 获取应用会话列表表名
     * @param bean 数参数
     * @return List<String>
     * @author www
     */
    @Select("select concat(#{name}, table_id) `name` from ${tableName} where "
            + "(end >= #{starttime} and end <= #{endtime}) or (start <= #{starttime} "
            + "and end >= #{endtime}) or (start >= #{starttime} and start <= #{endtime})"
            + "order by `end` desc")
    List<String> getAppTableName(TableParamBean bean);
    
    /**
     * 
     * @Title: getHttpSessionData
     * @Description: 获取HTTP会话列表
     * @param bean 参数
     * @return List<HttpSessionBean>
     * @author www
     */
    @SelectProvider(type = AppProvider.class, method = "sessionDataSql")
    List<HttpSessionBean> getHttpSessionData(SessionParamsBean bean);
    
    /**
     * 
     * @Title: getDBSessionData
     * @Description: 获取DB会话
     * @param bean 参数
     * @return List<DBSessionBean>
     * @author www
     */
    @SelectProvider(type = AppProvider.class, method = "sessionDataSql")
    List<DBSessionBean> getDBSessionData(SessionParamsBean bean);
    
    /**
     * 
     * @Title: getSessionDataNum
     * @Description: 获取会话数量
     * @param bean 参数
     * @return int 数量
     * @author WWW
     */
    @SelectProvider(type = AppProvider.class, method = "sessionDataNumSql")
    int getSessionDataNum(SessionParamsBean bean);
}
