/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlProvider.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.JOIN;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.text.MessageFormat;
import java.util.List;

import com.protocolsoft.url.bean.UrlKpiParamBean;
import com.protocolsoft.url.bean.UrlSetBean;

/**
 * @ClassName: UrlProvider
 * @Description: URL业务SQL拼装
 * @author www
 *
 */
public class UrlProvider {
    
    /**
     * 
     * @Title: selectKpi
     * @Description: RRD数据
     * @param bean 参数bean
     * @return String
     * @author www
     */
    public String selectKpi(UrlKpiParamBean bean) {
        List<String> tables = bean.getTables();
        StringBuilder sql = new StringBuilder();
        sql.append("select sum(pv) sessionNum, sum(bytes) ethernetTraffic, ");
        sql.append("avg(appllatency) responseDelay, avg(pageLoad) pageloadDelay, ");
        sql.append("avg(netDelay) rttDurDelay, sum(code400) http400Count, ");
        sql.append("sum(code500) http500Count, sum(notres) unresponsiveNum from (");
        for (int i = 0, len = tables.size(); i < len; i ++) {
            if (i != 0) {
                sql.append(" union all "); 
            }
            BEGIN();
            SELECT("count(1) pv, sum(bytes) bytes, avg(appllatency) appllatency");
            SELECT("pageload pageLoad");
            SELECT("avg(clientlatency+serverlatency) netDelay");
            SELECT("sum(case when httpreturncode like '4%' then 1 else 0 end) code400");
            SELECT("sum(case when httpreturncode like '5%' then 1 else 0 end) code500");
            SELECT("sum(case when httpreturncode = '0' then 1 else 0 end) notres");
            FROM(tables.get(i) + " h");
            JOIN("ipm_url_set u on concat(h.server, h.url) like replace(u.url, '***', '%')");
            WHERE("begintime >= #{starttime}");
            WHERE("begintime < #{endtime}");
            WHERE("h.probe = #{watchpointId}");
            WHERE("u.app_id = #{busiId}");
            sql.append(SQL());
        }
        sql.append(") http_log");
        
        return sql.toString();
    }
    
    /**
     * 
     * @Title: selectKpiInSub
     * @Description: RRD数据
     * @param bean 参数bean
     * @return String
     * @author www
     */
    public String selectKpiInSub(UrlKpiParamBean bean) {
        List<String> tables = bean.getTables();
        StringBuilder sql = new StringBuilder();
        sql.append("select u.id id, (case when bytes is null then 0 else count(1) end) sessionNum, ");
        sql.append("sum(bytes) ethernetTraffic, avg(appllatency) responseDelay, ");
        sql.append("avg(endtimewithpayload * 1000 - begintime * 1000) pageloadDelay, ");
        sql.append("avg(clientlatency+serverlatency) rttDurDelay, ");
        sql.append("sum(case when httpreturncode like '4%' then 1 else 0 end) http400Count,");
        sql.append("sum(case when httpreturncode like '5%' then 1 else 0 end) http500Count,");
        sql.append("sum(case when httpreturncode = '0' then 1 else 0 end) unresponsiveNum from (");
        for (int i = 0, len = tables.size(); i < len; i ++) {
            if (i != 0) {
                sql.append(" union all "); 
            }
            BEGIN();
            SELECT("`server`, url, bytes, appllatency, endtimewithpayload");
            SELECT("begintime, clientlatency, serverlatency, httpreturncode");
            FROM(tables.get(i));
            WHERE("begintime >= #{starttime}");
            WHERE("begintime < #{endtime}");
            WHERE("probe = #{watchpointId}");
            sql.append(SQL());
        }
        sql.append(") h right join (select id, url from ipm_url_set where app_id = #{busiId}) u on ");
        sql.append("concat(h.server, h.url) like replace(u.url, '***', '%') GROUP BY u.id");
        
        return sql.toString();
    }
    
    /**
     * 
     * @Title: urlInsertBatch
     * @Description: 批量添加URL
     * @param bean 业务信息
     * @return String SQL
     * @author www
     */
    public String urlInsertBatch(UrlSetBean bean) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into `ipm_url_set` (`app_id`, `num`, `name`, `url`, `isstored`) values");
        String pattern = "(#'{'id'}', #'{'set[{0}].num'}', #'{'set[{0}].name'}', #'{'set[{0}].url'}', #'{'set[{0}].isStored'}')";
        for (int i = 0, len = bean.getSet().size(); i < len; i ++) {
            if(i != 0) {
                sql.append(",");
            }
            sql.append(MessageFormat.format(pattern, String.valueOf(i)));
        }
        
        return sql.toString();
    }
}
