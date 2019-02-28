/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairProvider
 *创建人:yan    创建时间:2017-10-16
 */
package com.protocolsoft.jtopo.provider;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.protocolsoft.jtopo.bean.CommpairQoBean;
import com.protocolsoft.jtopo.bean.CommpairTablesBean;
import com.protocolsoft.utils.IpUtils;

/**
 * 通信对表 动态sql处理
 * 2017-10-16 下午3:23:23
 * @author yan
 * @version
 * @see
 */
public class CommpairDaoProvider {
    
    /**
     * 动态拼接查询SQL
     * 2017-10-18 下午2:31:27
     * @param map
     * @return String
     * @exception 
     * @see
     */
    public String selectCommpairData(Map<String, Object> map){
        @SuppressWarnings("unchecked")
        List<CommpairTablesBean> commpairTablesBeans = (List<CommpairTablesBean>) map.get("beans");  
        
        CommpairQoBean commpairQoBean = (CommpairQoBean) map.get("commpairQoBean");
        StringBuffer sb = new StringBuffer("select fromIp, toInfo, ");
        sb.append(commpairQoBean.getCalcul());
        sb.append("(value) value from (");
        for (CommpairTablesBean commpairTablesBean : commpairTablesBeans) {
            sb.append(" select INET_NTOA(serverip) fromIp,INET_NTOA(clientip) toInfo");
            Object [] array = new Object[5];
            array[0] = commpairQoBean.getColumnName();
            array[1] = commpairQoBean.getStartTime().toString();
            array[2] = commpairQoBean.getEndTime().toString();
            array[3] = commpairQoBean.getIp();
            if (null != commpairQoBean.getPort()){
                array[4] = commpairQoBean.getPort().toString();
            }
            if (null != commpairQoBean){
                if (null != commpairQoBean.getColumnName() && !"".equals(commpairQoBean.getColumnName())) {
                    sb.append(",");
                    String result1 = MessageFormat.format("{0}", array);
                    sb.append(commpairQoBean.getCalcul());
                    sb.append(result1);
                }
            }
            sb.append(", count(1) count");
            //commpair_观察点Id_粒度_log_000000001
            sb.append(" from commpair");
            sb.append("_" + commpairTablesBean.getWpid());
            sb.append("_" + commpairTablesBean.getGranularity());
            sb.append("_log");
            sb.append("_" + commpairTablesBean.getTableId());
            sb.append(MessageFormat.format(" where snaptime >= {1} and snaptime <= {2}", array));
            if (commpairQoBean.getSegment() != null) {
                sb.append(" and ");
                sb.append(this.getSelectSegmentSql(commpairQoBean.getSegment()));
            }
            sb.append(" group by fromIp, toInfo");
            sb.append(" UNION ALL");
        }
        String result = sb.toString().substring(0, sb.toString().lastIndexOf("UNION ALL"));
        result += ") addr group by fromIp, toInfo limit " + commpairQoBean.getNum();
        
        return result;
    }
    
    /**
     * 动态拼接查询SQL
     * 2017-10-18 下午2:31:27
     * @param map
     * @return String
     * @exception 
     * @see
     */
    public String selectCommpairDataByIpPort(Map<String, Object> map){
        @SuppressWarnings("unchecked")
        List<CommpairTablesBean> commpairTablesBeans = (List<CommpairTablesBean>) map.get("beans");  
        
        CommpairQoBean commpairQoBean = (CommpairQoBean) map.get("commpairQoBean");
        StringBuffer sb = new StringBuffer("select fromIp, toInfo, port, ");
        sb.append(commpairQoBean.getCalcul());
        sb.append("(value) value from (");  
        for (CommpairTablesBean commpairTablesBean : commpairTablesBeans) {
            sb.append(" select INET_NTOA(serverip) fromIp,INET_NTOA(clientip) toInfo,serverport port");
            Object [] array = new Object[5];
            array[0] = commpairQoBean.getColumnName();
            array[1] = commpairQoBean.getStartTime().toString();
            array[2] = commpairQoBean.getEndTime().toString();
            array[3] = commpairQoBean.getIp();
            if (null != commpairQoBean.getPort()){
                array[4] = commpairQoBean.getPort().toString();
            }
            if (null != commpairQoBean){
                if (null != commpairQoBean.getColumnName() && !"".equals(commpairQoBean.getColumnName())) {
                    sb.append(",");
                    sb.append(commpairQoBean.getCalcul());
                    sb.append(MessageFormat.format("{0}", array));
                }
            }
            //commpair_观察点Id_粒度_log_000000001
            sb.append(" from commpair");
            sb.append("_" + commpairTablesBean.getWpid());
            sb.append("_" + commpairTablesBean.getGranularity());
            sb.append("_log");
            sb.append("_" + commpairTablesBean.getTableId());
            sb.append(MessageFormat.format(" where snaptime >= {1} and snaptime <= {2}", array));
            if (null != commpairQoBean){
                if (null != commpairQoBean.getIp() && !"".equals(commpairQoBean.getIp())){
                    String[] ips = commpairQoBean.getIp().split(",");
                    sb.append(" and serverip in (INET_ATON('");
                    sb.append(ips[0]);
                    sb.append("')");
                    for (int i = 1, len = ips.length; i < len; i ++) {
                        sb.append(", INET_ATON('");
                        sb.append(ips[i]);
                        sb.append("')");
                    }
                    sb.append(")");
                }
                if (null != commpairQoBean.getPort()){
                    sb.append(MessageFormat.format(" and serverport = {4}", array));
                }
                if (commpairQoBean.getSegment() != null) {
                    sb.append(" and ");
                    sb.append(this.getSelectSegmentSql(commpairQoBean.getSegment()));
                }
            }
            sb.append(" group by fromIp, toInfo, port");
            sb.append(" UNION ALL");
        }
        String result = sb.toString().substring(0, sb.toString().lastIndexOf("UNION ALL"));
        result += ") addr where port != 0 group by fromIp, toInfo, port order by port limit " + commpairQoBean.getNum();
        
        return result;
    }
    
    /**
     * 
     * @Title: getSelectSegmentSql
     * @Description: 获取网段查询SQL
     * @param segment 网段(可混合，逗号隔开)
     *              例:192.168.1.0/24 子网形式
     *                192.168.1.100-192.168.1.200 网段形式
     * @return String
     * @author www
     */
    private String getSelectSegmentSql(String segment) {
        String[] ips = segment.split(",");
        StringBuilder sql = new StringBuilder(" (");
        List<Long> ipnet = null;
        String tmp = null;
        String[] stmp = null;
        for (int i = 0, len = ips.length; i < len; i ++) {
            tmp = ips[i];
            if (tmp.contains("/")) {
                ipnet = IpUtils.getMaxMinIpByIpnet(tmp);
            } else if (tmp.contains("-")) {
                stmp = tmp.split("-");
                ipnet = new ArrayList<Long>();
                ipnet.add(IpUtils.ipFromStringToLong(stmp[0]));
                ipnet.add(IpUtils.ipFromStringToLong(stmp[1]));
            }
            sql.append("(serverip >= ");
            sql.append(ipnet.get(0));
            sql.append(" and serverip <= ");
            sql.append(ipnet.get(1));
            sql.append(") or (clientip >= ");
            sql.append(ipnet.get(0));
            sql.append(" and clientip <= ");
            sql.append(ipnet.get(1));
            sql.append(")");
            if (i + 1 != len) {
                sql.append(" or ");
            }
        }
        sql.append(") ");
        
        return sql.toString();
    }
}
