/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppBusinessProvider.java
 *创建人: WWW    创建时间: 2018年9月26日
 */
package com.protocolsoft.common.provider;

import java.util.List;

import com.protocolsoft.common.bean.SameIpPortParamBean;
import com.protocolsoft.servers.bean.AppIpPortBean;

/**
 * @ClassName: AppBusinessProvider
 * @Description: 业务拼住SQL
 * @author WWW
 *
 */
public class AppBusinessProvider {

    /**
     * 
     * @Title: isExistSameIpPort
     * @Description: 是否存在相同的IP或端口
     * @param bean 参数
     * @return String
     * @author WWW
     */
    public String isExistSameIpPort(SameIpPortParamBean bean) {
        StringBuilder sql = new StringBuilder();

        List<AppIpPortBean> list = bean.getList();
        AppIpPortBean tmp = null;
        String ip = null;
        Integer port = null;
        sql.append("select count(*) > 0 from ipm_app_ip_port where (");
        for (int i = 0, len = list.size(); i < len; i++) {
            tmp = list.get(i);
            ip = tmp.getIp();
            port = tmp.getPort();
            sql.append("(");
            if (ip == null) {
                sql.append("ip is null");
            } else {
                sql.append("ip = " + ip);
            }
            sql.append(" and ");
            if (port == null) {
                sql.append("`port` is null");
            } else {
                sql.append("`port` = " + port);
            }
            sql.append(")");
            if (len != i + 1) {
                sql.append(" or ");
            }
        }
        sql.append(")");
        if (bean.isUpd()) {
            sql.append(" and app_id != #{busiId}");
        }
        
        return sql.toString();
    }
}
