/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTopProvider.java
 *创建人: www    创建时间: 2018年1月26日
 */
package com.protocolsoft.app.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import java.util.List;

import com.protocolsoft.app.bean.AppTopConfigBean;
import com.protocolsoft.app.bean.AppTopParamsBean;

/**
 * @ClassName: AppTopProvider
 * @Description: TOP拼接
 * @author www
 *
 */
public class AppTopProvider {
    
    /**
     * 
     * @Title: addTop60Batch
     * @Description: 一分钟级TOP
     * @param bean 参数
     * @return String
     * @author www
     */
    public String addTop60Batch(AppTopParamsBean bean) {
        AppTopConfigBean config = bean.getBean();
        List<String> tables = bean.getTables();
        String serverColumn = "server";
        if (bean.getModuleId() == 4) {
            serverColumn = "serverip";
        }
        StringBuilder sql = new StringBuilder("insert into ipm_app_topn_60 select ");
        sql.append(bean.getStarttime());
        sql.append(",");
        sql.append(bean.getEndtime());
        sql.append(",");
        sql.append(bean.getWatchpointId());
        sql.append(",");
        sql.append(bean.getBusiId());
        sql.append(",");
        sql.append(bean.getBean().getPlotId());
        sql.append(",");
        sql.append(config.getSummary());
        sql.append(" from (");
        for (int i = 0, len = tables.size(); i < len; i ++) {
            if (i != 0) {
                sql.append(" union all ");
            }
            BEGIN();
            SELECT(config.getColumn());
            FROM(tables.get(i));
            WHERE("begintime >= #{starttime}");
            WHERE("begintime <= #{endtime}");
            WHERE("probe = #{watchpointId}");
            WHERE("CONCAT(`" + serverColumn + "`, `port`) in (select CONCAT(`ip`, `port`) "
                    + "from ipm_app_ip_port where app_id = #{busiId})");
            if(config.getWhere() != null) {
                WHERE(config.getWhere());
            }
            if(config.getGroup() != null) {
                GROUP_BY(config.getGroup());
            }
            sql.append(SQL());
        }
        sql.append(") tmp");
        if(config.getGroup() != null) {
            sql.append(" group by ");
            sql.append(config.getGroup());
        }
        if(config.getOrder() != null) {
            sql.append(" order by ");
            sql.append(config.getOrder());
        }
        sql.append(" limit ");
        sql.append(config.getLimit());
        
        return sql.toString();
    }
    
    /**
     * 
     * @Title: selectTopSql
     * @Description: 查询TOP数据
     * @param bean 参数
     * @return String
     * @author www
     */
    public String selectTopSql(AppTopParamsBean bean) {
        AppTopConfigBean config = bean.getBean();
        List<String> tables = bean.getTables();
        String serverColumn = "server";
        if(bean.getModuleId() == 4) {
            serverColumn = "serverip";
        }
        StringBuilder sql = new StringBuilder("select ");
        sql.append(config.getSummary());
        sql.append(" from (");
        for(int i = 0, len = tables.size(); i < len; i ++) {
            if(i != 0) {
                sql.append(" union all ");
            }
            BEGIN();
            SELECT(config.getColumn());
            FROM(tables.get(i));
            WHERE("begintime >= #{starttime}");
            WHERE("begintime <= #{endtime}");
            WHERE("probe = #{watchpointId}");
            WHERE("CONCAT(`" + serverColumn + "`, `port`) in (select CONCAT(`ip`, `port`) "
                    + "from ipm_app_ip_port where app_id = #{busiId})");
            if(config.getWhere() != null) {
                WHERE(config.getWhere());
            }
            if(config.getGroup() != null) {
                GROUP_BY(config.getGroup());
            }
            sql.append(SQL());
        }
        sql.append(") tmp");
        if(config.getGroup() != null) {
            sql.append(" group by ");
            sql.append(config.getGroup());
        }
        if(config.getOrder() != null) {
            sql.append(" order by ");
            sql.append(config.getOrder());
        }
        sql.append(" limit ");
        sql.append(config.getLimit());
        
        return sql.toString();
    }
}
