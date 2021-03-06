/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppProvider.java
 *创建人: www    创建时间: 2018年1月24日
 */
package com.protocolsoft.app.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import com.protocolsoft.app.bean.SessionParamsBean;

/**
 * @ClassName: AppProvider
 * @Description: 应用拼接SQL
 * @author www
 *
 */
public class AppProvider {

    /**
     * 
     * @Title: sessionDataSql
     * @Description: 获取列表
     * @param bean 参数
     * @return String
     * @author www
     */
    public String sessionDataSql(SessionParamsBean bean) {
        String serverColumn = "server";
        String ousColumn = "sqlquery";
        String codeColumn = "responsecode";
        int moduleId = bean.getModuleId();
        if (moduleId == 4 || moduleId == 8) { // HTTP URL
            serverColumn = "serverip";
            ousColumn = "CONCAT(`server`, url)";
            codeColumn = "httpreturncode";
        }
        BEGIN();
        SELECT(bean.getColumn());
        FROM(bean.getTable() + " l");
        // URL
        if(moduleId == 8 && bean.getBusiId() != null) {
            INNER_JOIN("(select replace(url, '***', '%') burl from ipm_url_set"
                + " where app_id = #{busiId}) u on concat(server, url) like burl");
        }
        INNER_JOIN("ipm_watchpoint w on w.id = l.probe");
        WHERE("begintime >= #{starttime}");
        WHERE("begintime <= #{endtime}");
        if (bean.getWatchpointId() != null) {
            WHERE("probe = #{watchpointId}");
        }
        if (bean.getClientip() != null) {
            WHERE("client = INET_ATON(#{clientip})");
        }
        if(bean.getClientport() != null) {
            WHERE("clientport = #{clientport}");
        }
        if (bean.getServerip() != null) {
            WHERE(serverColumn + " = INET_ATON(#{serverip})");
        } else if (moduleId != 8 && bean.getBusiId() != null) {
            WHERE("CONCAT(`" + serverColumn + "`, l.`port`) in (select ipport from "
                    + "(select CONCAT(`ip`, `port`) ipport from ipm_app_ip_port "
                    + "where app_id = #{busiId}) ipport_tmp)");
        }
        if(bean.getServerport() != null) {
            WHERE("l.`port` = #{serverport}");
        }
        if (bean.getCode() != null) {
            WHERE(codeColumn + " like '%" + bean.getCode() + "%'");
        } else {
            switch(bean.getPlotId()) {
                case 239:
                    WHERE(codeColumn + " like '4%'");
                    break;
                case 240:
                    WHERE(codeColumn + " like '5%'");
                    break;
                case 242: case 244: case 246:
                    WHERE(codeColumn + " != 0");
                    break;
                default:
                    break;
            }
        }
        if (bean.getUos() != null) {
            WHERE(ousColumn + " like '%" + bean.getUos().replaceAll("\\'", "\\\\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%'");
        }
        if(bean.getMessage() != null) {
            WHERE("req like '%" + bean.getMessage().replaceAll("\\'", "\\\\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%' or "
                + "res like '%" + bean.getMessage().replaceAll("'", "\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%'");
        }
        ORDER_BY("l.id desc limit #{startNum}, #{num}");
        
        return SQL();
    }
    
    /**
     * 
     * @Title: sessionDataNumSql
     * @Description: 获取数量语句
     * @param bean 参数
     * @return String
     * @author www
     */
    public String sessionDataNumSql(SessionParamsBean bean) {
        String serverColumn = "server";
        String ousColumn = "sqlquery";
        String codeColumn = "responsecode";
        int moduleId = bean.getModuleId();
        if (moduleId == 4 || moduleId == 8) { // HTTP URL
            serverColumn = "serverip";
            ousColumn = "CONCAT(`server`, url)";
            codeColumn = "httpreturncode";
        }
        BEGIN();
        SELECT("count(*) num");
        FROM(bean.getTable());
        // URL
        if(moduleId == 8 && bean.getBusiId() != null) {
            INNER_JOIN("(select replace(url, '***', '%') burl from ipm_url_set"
                + " where app_id = #{busiId}) u on concat(server, url) like burl");
        }
        WHERE("begintime >= #{starttime}");
        WHERE("begintime <= #{endtime}");
        if (bean.getWatchpointId() != null) {
            WHERE("probe = #{watchpointId}");
        }
        if (bean.getClientip() != null) {
            WHERE("client = INET_ATON(#{clientip})");
        }
        if(bean.getClientport() != null) {
            WHERE("clientport = #{clientport}");
        }
        if (bean.getServerip() != null) {
            WHERE(serverColumn + " = INET_ATON(#{serverip})");
        } else if (moduleId != 8 && bean.getBusiId() != null) {
            WHERE("CONCAT(`" + serverColumn + "`, `port`) in (select ipport from "
                    + "(select CONCAT(`ip`, `port`) ipport from ipm_app_ip_port "
                    + "where app_id = #{busiId}) ipport_tmp)");
        }
        if(bean.getServerport() != null) {
            WHERE("`port` = #{serverport}");
        }
        if (bean.getCode() != null) {
            WHERE(codeColumn + " like '%" + bean.getCode() + "%'");
        } else {
            switch(bean.getPlotId()) {
                case 239:
                    WHERE(codeColumn + " like '4%'");
                    break;
                case 240:
                    WHERE(codeColumn + " like '5%'");
                    break;
                case 242: case 244: case 246:
                    WHERE(codeColumn + " != 0");
                    break;
                default:
                    break;
            }
        }
        if (bean.getUos() != null) {
            WHERE(ousColumn + " like '%" + bean.getUos().replaceAll("\\'", "\\\\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%'");
        }
        if(bean.getMessage() != null) {
            WHERE("req like '%" + bean.getMessage().replaceAll("\\'", "\\\\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%' or "
                + "res like '%" + bean.getMessage().replaceAll("'", "\'")
                .replaceAll("\\%", "\\\\%").replaceAll("\\[", "\\\\[") + "%'");
        }
        
        return SQL();
    }
}
