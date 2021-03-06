/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppIpPortDaoProvider
 *创建人:yan    创建时间:2017年9月5日
 */
package com.protocolsoft.servers.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.servers.bean.AppIpPortBean;

/**
 * ipm_app_ip_port表动态sql处理
 * 2017年9月5日 上午10:46:53
 * @author yan
 * @version
 * @see
 */
public class AppIpPortDaoProvider {
    
    /**
     * 批量新增处理
     * 2017年9月5日 上午10:49:08
     * @param Map<String,List<AppIpPortBean>>
     * @return String
     * @exception 
     * @see
     */
    public String batchInsert(Map<String, List<AppIpPortBean>> map) {  
        List<AppIpPortBean> appIpPorts = map.get("beans");  
        StringBuffer sb = new StringBuffer();  
        sb.append("INSERT INTO ipm_app_ip_port ");  
        sb.append("(app_id,ip,port) ");  
        sb.append("VALUES ");
        Integer port = null;
        Object[] array = null;
        for (int i = 0; i < appIpPorts.size(); i++) {
            port = appIpPorts.get(i).getPort();
            if (port == null) {
                array = new Object[]{String.valueOf(appIpPorts.get(i).getAppId()), appIpPorts.get(i).getIp(), null};        
            } else {
                array = new Object[]{String.valueOf(appIpPorts.get(i).getAppId()), appIpPorts.get(i).getIp(), port.toString()};
            }
            String result = MessageFormat.format("({0}, {1}, {2})", array);
            sb.append(result);
            if (i < appIpPorts.size() - 1) {  
                sb.append(",");  
            }  
        }
        
        return sb.toString();  
    }  
    
}
