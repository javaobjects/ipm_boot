/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommpairProvider.java
 *创建人: chensq    创建时间: 2018年7月2日
 */
package com.protocolsoft.commpair.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import org.apache.commons.lang3.StringUtils;

import com.protocolsoft.commpair.bean.CommpairBean;

/**
 * @ClassName: CommpairProvider
 * @Description: 通信对不注入sql方式
 * @author chensq
 *
 */
public class CommpairProvider {
    
    /**
     * 
     * @Title: commpairMergeListProviderSql
     * @Description: 获取获取列表(通信对聚合无地址库)
     * @param bean
     * @param watchPointId
     * @param tableName
     * @return String
     * @author chensq
     */
    public static String commpairMergeListProviderSql(CommpairBean bean, long watchPointId, String tableName) {
        
        //-------------sub start--------------------
        StringBuffer subSqlBuf=new StringBuffer();
        subSqlBuf.append(watchPointId+" AS watchpointId, ");
        subSqlBuf.append("l.clientUideId AS clientUideIds, ");
        subSqlBuf.append("l.serverUideId AS serverUideIds, ");
        subSqlBuf.append("l.snaptime AS snaptime, ");
        subSqlBuf.append("min(l.snaptime) AS starttime, ");
        subSqlBuf.append("max(l.snaptime) AS endtime, ");
        subSqlBuf.append("INET_NTOA(l.serverip) AS serverip, ");
        subSqlBuf.append("INET_NTOA(l.clientip) AS clientip, ");
        subSqlBuf.append("l.serverport AS serverport, ");
        // 具体指标
        //-----展示项目start-----
        subSqlBuf.append("SUM(ethernetTraffic) AS ethernetTraffic, "); // 网络流量
        subSqlBuf.append("SUM(synPkts) AS synPkts, "); // SYN包数
        subSqlBuf.append("SUM(synAckPkts) AS synAckPkts, "); // 连接应答数量
        subSqlBuf.append("AVG(serverConDelay)+AVG(responseDelay)+AVG(loadDelay) AS qos, "); // 服务质量
        subSqlBuf.append("AVG(serverDurDelay) AS serverDurDelay, "); // 服务端通信时延
        subSqlBuf.append("AVG(clientDurDelay) AS clientDurDelay, "); // 客户端通信时延
        
        subSqlBuf.append("AVG(rtt) AS rtt, "); // 链路时延RTT
        subSqlBuf.append("AVG(serverConDelay) AS serverConDelay, "); // 服务端握手时延
        subSqlBuf.append("AVG(clientConDelay) AS clientConDelay, "); // 客户端握手时延
        subSqlBuf.append("AVG(responseDelay) AS responseDelay, "); // 应用处理时延
        subSqlBuf.append("AVG(loadDelay) AS loadDelay, "); // 负载传输时延

        subSqlBuf.append("AVG(serverRetransDelay) AS serverRetransDelay, "); // 服务端重传时延
        subSqlBuf.append("AVG(clientRetransDelay) AS clientRetransDelay, "); // 客户端重传时延
        subSqlBuf.append("SUM(tcpTraffic) AS tcpTraffic, "); // TCP流量
        subSqlBuf.append("SUM(udpTraffic) AS udpTraffic, "); // UDP流量
        subSqlBuf.append("SUM(inTraffic) AS inTraffic, "); // 入流量
        subSqlBuf.append("SUM(outTraffic) AS outTraffic, "); // 出流量
        subSqlBuf.append("SUM(rstPkts) AS rstPkts, "); // rst包数
        
        subSqlBuf.append("SUM(finPkts) AS finPkts, "); // fin包数
        subSqlBuf.append("SUM(fin1Pkts) AS fin1Pkts, "); // 主动关闭包数
        subSqlBuf.append("SUM(fin2Pkts) AS fin2Pkts, "); // 被动关闭包数
        subSqlBuf.append("SUM(ethernetPkts) AS ethernetPkts, "); // 数据包个数(与rrd名称不一致)
        subSqlBuf.append("SUM(tinyPkts) AS tinyPkts, "); // 小包个数(与rrd名称不一致)
        subSqlBuf.append("SUM(zeroWinCount) AS zeroWinCount, "); // 零窗口包数
        //-----展示项目start-----
        
        //-----数据库列start-----
        subSqlBuf.append("SUM(netPktLost) AS netPktLost, "); // 网络传输丢包率
        subSqlBuf.append("SUM(serverPktLost) AS serverPktLost, "); // 服务端丢包率
        subSqlBuf.append("SUM(clientPktLost) AS clientPktLost, "); // 客户端丢包率
        subSqlBuf.append("SUM(serverPkt) AS serverPkt, "); // 服务端包数
        subSqlBuf.append("SUM(clientPkt) AS clientPkt, "); // 客户端包数
        //-----数据库列end-----
        
        //new add
        subSqlBuf.append("AVG(l.serverip) AS serverIpColumn, ");
        subSqlBuf.append("AVG(l.clientip) AS clientIpColumn, ");
        subSqlBuf.append("AVG(l.serverport) AS serverPortColumn, ");
         
        subSqlBuf.append("GROUP_CONCAT(DISTINCT serverport)  serverportConcat ");
        //-------------sub end--------------------

        //-------------条件 start--------------------
        //子网/
        String clientUideSql="";
        if (bean.getClientId()>0) {
            clientUideSql="  l.clientUideId REGEXP  '\\\\."+bean.getClientId()+"\\\\.' ";
        }
        //应用/服务端
        String serverUideSql="";
        if (bean.getServerId()>0) {
            serverUideSql="  l.serverUideId REGEXP  '\\\\."+bean.getServerId()+"\\\\.' ";
        }
        //端口
        String serverportSql="";
        if (bean.getServerport()>0) {
            serverportSql="  l.serverport = "+bean.getServerport()+" ";
        }
        //特殊情况(协议分布)  需要使用or
        StringBuffer serCliIpSQLBuf=new StringBuffer();
        //服务端ip
        String serveripSql="";
        //客户端ip
        String clientipSql="";
        if (bean.getNeedSerCli()==1){
            if (StringUtils.isNotEmpty(bean.getServerip()) && StringUtils.isNotEmpty(bean.getClientip())){
                serCliIpSQLBuf.append("  (");
                serCliIpSQLBuf.append(" l.serverip = inet_aton('"+bean.getServerip()+"')");
                serCliIpSQLBuf.append(" or ");
                serCliIpSQLBuf.append(" l.clientip = inet_aton('"+bean.getClientip()+"')");
                serCliIpSQLBuf.append(" ) ");
            }
        } else {
          //服务端ip
            if (StringUtils.isNotEmpty(bean.getServerip())){
                serveripSql="  l.serverip = inet_aton('"+bean.getServerip()+"')";
            }
          //客户端ip
            if (StringUtils.isNotEmpty(bean.getClientip())){
                clientipSql="  l.clientip = inet_aton('"+bean.getClientip()+"')";
            }
        }

        //聚合类型(子sql用)
        String groupBySql="";
        if (bean.getGroupType()>=0) {
            switch (bean.getGroupType()){
                case 1:
                    groupBySql=" l.serverip ";
                    break;
                case 2:
                    groupBySql=" l.clientip ";
                    break;
                case 3:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
                case 4:
                    groupBySql=" l.serverip ,l.serverport ";
                    break;
                default:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
            }
        }
        //-------------条件 end--------------------

        //迭代多个表
        BEGIN();
        
        //sub--迭代所有符合条件的表       
        SELECT(subSqlBuf.toString());
        FROM(tableName + " l");
        if (StringUtils.isNotEmpty(serveripSql)){
            WHERE(serveripSql);
        }
        if (StringUtils.isNotEmpty(clientipSql)){
            WHERE(clientipSql);
        }
        if (StringUtils.isNotEmpty(serverportSql)){
            WHERE(serverportSql);
        }
        WHERE("l.snaptime > "+bean.getStarttime());
        WHERE("l.snaptime <= "+bean.getEndtime());
        if (StringUtils.isNotEmpty(bean.getServerportNotInSql())) {
            WHERE(bean.getServerportNotInSql());
        }
        if (StringUtils.isNotEmpty(bean.getServerIpPortSql())) {
            WHERE(bean.getServerIpPortSql());
        }
        if (StringUtils.isNotEmpty(clientUideSql)){
            WHERE(clientUideSql);
        }
        if(StringUtils.isNotEmpty(serverUideSql)){
            WHERE(serverUideSql);
        }
        if(StringUtils.isNotEmpty(serCliIpSQLBuf)){
            WHERE(serCliIpSQLBuf.toString());
        }        
        GROUP_BY(groupBySql);
       
        return SQL();
    }

    /**
     * 
     * @Title: commpairMergeListProviderSql
     * @Description: 获取获取列表(通信对聚合地址库)
     * @param bean
     * @param watchPointId
     * @param tableName
     * @return String
     * @author chensq
     */
    public static String commpairMergeListLocProviderSql(CommpairBean bean, long watchPointId, String tableName) {
        
        //-------------sub start--------------------
        StringBuffer subSqlBuf=new StringBuffer();
        subSqlBuf.append(watchPointId+" AS watchpointId, ");
        subSqlBuf.append("l.clientUideId AS clientUideIds, ");
        subSqlBuf.append("l.serverUideId AS serverUideIds, ");
        subSqlBuf.append("l.snaptime AS snaptime, ");
        subSqlBuf.append("min(l.snaptime) AS starttime, ");
        subSqlBuf.append("max(l.snaptime) AS endtime, ");
        subSqlBuf.append("INET_NTOA(l.serverip) AS serverip, ");
        subSqlBuf.append("INET_NTOA(l.clientip) AS clientip, ");
        subSqlBuf.append("l.serverport AS serverport, ");
        // 具体指标
        //-----展示项目start-----
        subSqlBuf.append("SUM(ethernetTraffic) AS ethernetTraffic, "); // 网络流量
        subSqlBuf.append("SUM(synPkts) AS synPkts, "); // SYN包数
        subSqlBuf.append("SUM(synAckPkts) AS synAckPkts, "); // 连接应答数量
        subSqlBuf.append("AVG(serverConDelay)+AVG(responseDelay)+AVG(loadDelay) AS qos, "); // 服务质量
        subSqlBuf.append("AVG(serverDurDelay) AS serverDurDelay, "); // 服务端通信时延
        subSqlBuf.append("AVG(clientDurDelay) AS clientDurDelay, "); // 客户端通信时延
        
        subSqlBuf.append("AVG(rtt) AS rtt, "); // 链路时延RTT
        subSqlBuf.append("AVG(serverConDelay) AS serverConDelay, "); // 服务端握手时延
        subSqlBuf.append("AVG(clientConDelay) AS clientConDelay, "); // 客户端握手时延
        subSqlBuf.append("AVG(responseDelay) AS responseDelay, "); // 应用处理时延
        subSqlBuf.append("AVG(loadDelay) AS loadDelay, "); // 负载传输时延

        subSqlBuf.append("AVG(serverRetransDelay) AS serverRetransDelay, "); // 服务端重传时延
        subSqlBuf.append("AVG(clientRetransDelay) AS clientRetransDelay, "); // 客户端重传时延
        subSqlBuf.append("SUM(tcpTraffic) AS tcpTraffic, "); // TCP流量
        subSqlBuf.append("SUM(udpTraffic) AS udpTraffic, "); // UDP流量
        subSqlBuf.append("SUM(inTraffic) AS inTraffic, "); // 入流量
        subSqlBuf.append("SUM(outTraffic) AS outTraffic, "); // 出流量
        subSqlBuf.append("SUM(rstPkts) AS rstPkts, "); // rst包数
        
        subSqlBuf.append("SUM(finPkts) AS finPkts, "); // fin包数
        subSqlBuf.append("SUM(fin1Pkts) AS fin1Pkts, "); // 主动关闭包数
        subSqlBuf.append("SUM(fin2Pkts) AS fin2Pkts, "); // 被动关闭包数
        subSqlBuf.append("SUM(ethernetPkts) AS ethernetPkts, "); // 数据包个数(与rrd名称不一致)
        subSqlBuf.append("SUM(tinyPkts) AS tinyPkts, "); // 小包个数(与rrd名称不一致)
        subSqlBuf.append("SUM(zeroWinCount) AS zeroWinCount, "); // 零窗口包数
        //-----展示项目start-----
        
        //-----数据库列start-----
        subSqlBuf.append("SUM(netPktLost) AS netPktLost, "); // 网络传输丢包率
        subSqlBuf.append("SUM(serverPktLost) AS serverPktLost, "); // 服务端丢包率
        subSqlBuf.append("SUM(clientPktLost) AS clientPktLost, "); // 客户端丢包率
        subSqlBuf.append("SUM(serverPkt) AS serverPkt, "); // 服务端包数
        subSqlBuf.append("SUM(clientPkt) AS clientPkt, "); // 客户端包数
        //-----数据库列end-----        
        
        //new add
        subSqlBuf.append("AVG(l.serverip) AS serverIpColumn, ");
        subSqlBuf.append("AVG(l.clientip) AS clientIpColumn, ");
        subSqlBuf.append("AVG(l.serverport) AS serverPortColumn, ");
         
        //地址库id信息
        subSqlBuf.append("l.serverLocId AS serverLocIdColumn, ");
        subSqlBuf.append("l.clientLocId AS clientLocIdColumn, ");
        subSqlBuf.append("l.serverLocId AS serverLocId, ");
        subSqlBuf.append("l.clientLocId AS clientLocId, ");
        
        //地址库内容信息
        subSqlBuf.append("loc.continent AS continent, "); //洲际
        subSqlBuf.append("loc.country AS country, "); //国家
        subSqlBuf.append("loc.region_cn AS region_cn, "); //省份
        subSqlBuf.append("loc.city_cn AS city_cn, "); //城市
        subSqlBuf.append("loc.isp_cn AS isp_cn, "); //运营商
        subSqlBuf.append("loc.country_english  AS country_english, "); //国家英文
        subSqlBuf.append("loc.country_code  AS country_code, "); //国家简写
        
        subSqlBuf.append("GROUP_CONCAT(DISTINCT serverport)  serverportConcat ");
        //-------------sub end--------------------

        //-------------条件 start--------------------
        //子网/
        String clientUideSql="";
        if (bean.getClientId()>0) {
            clientUideSql="  l.clientUideId REGEXP  '\\\\."+bean.getClientId()+"\\\\.' ";
        }
        //应用/服务端
        String serverUideSql="";
        if (bean.getServerId()>0) {
            serverUideSql="  l.serverUideId REGEXP  '\\\\."+bean.getServerId()+"\\\\.' ";
        }
        //端口
        String serverportSql="";
        if (bean.getServerport()>0) {
            serverportSql="  l.serverport = "+bean.getServerport()+" ";
        }
        //特殊情况(协议分布)  需要使用or
        StringBuffer serCliIpSQLBuf=new StringBuffer();
        //服务端ip
        String serveripSql="";
        //客户端ip
        String clientipSql="";
        if (bean.getNeedSerCli()==1){
            if (StringUtils.isNotEmpty(bean.getServerip()) && StringUtils.isNotEmpty(bean.getClientip())){
                serCliIpSQLBuf.append("  (");
                serCliIpSQLBuf.append(" l.serverip = inet_aton('"+bean.getServerip()+"')");
                serCliIpSQLBuf.append(" or ");
                serCliIpSQLBuf.append(" l.clientip = inet_aton('"+bean.getClientip()+"')");
                serCliIpSQLBuf.append(" ) ");
            }
        } else {
          //服务端ip
            if (StringUtils.isNotEmpty(bean.getServerip())){
                serveripSql="  l.serverip = inet_aton('"+bean.getServerip()+"')";
            }
          //客户端ip
            if (StringUtils.isNotEmpty(bean.getClientip())){
                clientipSql="  l.clientip = inet_aton('"+bean.getClientip()+"')";
            }
        }

        //聚合类型(子sql用)
        String groupBySql="";
        if (bean.getGroupType()>=0) {
            switch (bean.getGroupType()){
                case 1:
                    groupBySql=" l.serverip ";
                    break;
                case 2:
                    groupBySql=" l.clientip ";
                    break;
                case 3:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
                case 4:
                    groupBySql=" l.serverip ,l.serverport ";
                    break;
                default:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
            }
        }
        //-------------条件 end--------------------

        //迭代多个表
        BEGIN();
        
        //sub--迭代所有符合条件的表       
        SELECT(subSqlBuf.toString());
        FROM(tableName + " l");
 
        LEFT_OUTER_JOIN(" ipm_ip_location_cn loc ON loc.loc_id_cn = l.clientLocId ");
        if(StringUtils.isNotEmpty(serveripSql)){
            WHERE(serveripSql);
        }
        if(StringUtils.isNotEmpty(clientipSql)){
            WHERE(clientipSql);
        }
        if(StringUtils.isNotEmpty(serverportSql)){
            WHERE(serverportSql);
        }
        WHERE("l.snaptime > "+bean.getStarttime());
        WHERE("l.snaptime <= "+bean.getEndtime());
        if (StringUtils.isNotEmpty(bean.getServerportNotInSql())) {
            WHERE(bean.getServerportNotInSql());
        }
        if (StringUtils.isNotEmpty(bean.getServerIpPortSql())) {
            WHERE(bean.getServerIpPortSql());
        }
        if (StringUtils.isNotEmpty(clientUideSql)){
            WHERE(clientUideSql);
        }
        if (StringUtils.isNotEmpty(serverUideSql)){
            WHERE(serverUideSql);
        }
        if (StringUtils.isNotEmpty(serCliIpSQLBuf)){
            WHERE(serCliIpSQLBuf.toString());
        }
        if (StringUtils.isNotEmpty(bean.getContinent())) {//洲际
            WHERE(" loc.continent REGEXP '^"+bean.getContinent()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryCn())) {//国家
            WHERE(" loc.country REGEXP '^"+bean.getCountryCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getRegionCn())) {//省份
            WHERE(" loc.region_cn REGEXP '^"+bean.getRegionCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCityCn())) {//城市
            WHERE(" loc.city_cn REGEXP '^"+bean.getCityCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getIspCn())) {//运营商
            WHERE(" loc.isp_cn REGEXP '^"+bean.getIspCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryEn())) {//国家英文
            WHERE(" loc.country_english REGEXP '^"+bean.getCountryEn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryCode())) {//国家简写
            WHERE(" loc.country_code REGEXP '^"+bean.getCountryCode()+"' ");
        }
        if(bean.getOtherCountry()!=0){//国外地址
            WHERE(" loc.country_code REGEXP '^[^CN]' ");
        }
        
        GROUP_BY(groupBySql);
       
        return SQL();
    }
    
    /**
     * 
     * @Title: commpairMergeCountProviderSql
     * @Description: 获取获取列表(通信对聚合无地址库)数量
     * @param bean
     * @param watchPointId
     * @param tableName
     * @return String
     * @author chensq
     */
    public static String commpairMergeCountProviderSql(CommpairBean bean, long watchPointId, String tableName) {
        //-------------sub start--------------------
        StringBuffer subSqlBuf=new StringBuffer();
        subSqlBuf.append(watchPointId+" AS watchpointId, ");
        subSqlBuf.append("l.serverip AS serverip, ");
        subSqlBuf.append("l.clientip AS clientip, ");
        subSqlBuf.append("l.serverport AS serverport ");
        //-------------条件 start--------------------
        //子网/
        String clientUideSql="";
        if (bean.getClientId()>0) {
            clientUideSql="  l.clientUideId REGEXP  '\\\\."+bean.getClientId()+"\\\\.' ";
        }
        //应用/服务端
        String serverUideSql="";
        if (bean.getServerId()>0) {
            serverUideSql="  l.serverUideId REGEXP  '\\\\."+bean.getServerId()+"\\\\.' ";
        }
        //端口
        String serverportSql="";
        if (bean.getServerport()>0) {
            serverportSql="  l.serverport = "+bean.getServerport()+" ";
        }
        //特殊情况(协议分布)  需要使用or
        StringBuffer serCliIpSQLBuf=new StringBuffer();
        //服务端ip
        String serveripSql="";
        //客户端ip
        String clientipSql="";
        if (bean.getNeedSerCli()==1){
            if (StringUtils.isNotEmpty(bean.getServerip()) && StringUtils.isNotEmpty(bean.getClientip())){
                serCliIpSQLBuf.append("  (");
                serCliIpSQLBuf.append(" l.serverip = inet_aton('"+bean.getServerip()+"')");
                serCliIpSQLBuf.append(" or ");
                serCliIpSQLBuf.append(" l.clientip = inet_aton('"+bean.getClientip()+"')");
                serCliIpSQLBuf.append(" ) ");
            }
        } else {
          //服务端ip
            if (StringUtils.isNotEmpty(bean.getServerip())){
                serveripSql="  l.serverip = inet_aton('"+bean.getServerip()+"')";
            }
          //客户端ip
            if (StringUtils.isNotEmpty(bean.getClientip())){
                clientipSql="  l.clientip = inet_aton('"+bean.getClientip()+"')";
            }
        }

        //聚合类型(子sql用)
        String groupBySql="";
        if (bean.getGroupType()>=0) {
            switch (bean.getGroupType()){
                case 1:
                    groupBySql=" l.serverip ";
                    break;
                case 2:
                    groupBySql=" l.clientip ";
                    break;
                case 3:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
                case 4:
                    groupBySql=" l.serverip ,l.serverport ";
                    break;
                default:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
            }
        }
        //-------------条件 end--------------------

        //迭代多个表
        BEGIN();
        
        //sub--迭代所有符合条件的表       
        SELECT(subSqlBuf.toString());
        FROM(tableName + " l");
        if (StringUtils.isNotEmpty(serveripSql)){
            WHERE(serveripSql);
        }
        if (StringUtils.isNotEmpty(clientipSql)){
            WHERE(clientipSql);
        }
        if (StringUtils.isNotEmpty(serverportSql)){
            WHERE(serverportSql);
        }
        WHERE("l.snaptime > "+bean.getStarttime());
        WHERE("l.snaptime <= "+bean.getEndtime());
        if (StringUtils.isNotEmpty(bean.getServerportNotInSql())) {
            WHERE(bean.getServerportNotInSql());
        }
        if (StringUtils.isNotEmpty(bean.getServerIpPortSql())) {
            WHERE(bean.getServerIpPortSql());
        }
        if (StringUtils.isNotEmpty(clientUideSql)){
            WHERE(clientUideSql);
        }
        if(StringUtils.isNotEmpty(serverUideSql)){
            WHERE(serverUideSql);
        }
        if(StringUtils.isNotEmpty(serCliIpSQLBuf)){
            WHERE(serCliIpSQLBuf.toString());
        }        
        GROUP_BY(groupBySql);
       
        return SQL();
    }
    
    
    /**
     * 
     * @Title: commpairMergeCountLocProviderSql
     * @Description: 获取获取列表(通信对聚合地址库)数量
     * @param bean
     * @param watchPointId
     * @param tableName
     * @return String
     * @author chensq
     */
    public static String commpairMergeCountLocProviderSql(CommpairBean bean, long watchPointId, String tableName) {
        
        //-------------sub start--------------------
        StringBuffer subSqlBuf=new StringBuffer();
        subSqlBuf.append(watchPointId+" AS watchpointId, ");
        subSqlBuf.append("l.serverip AS serverip, ");
        subSqlBuf.append("l.clientip AS clientip, ");
        subSqlBuf.append("l.serverport AS serverport ");
        
        //-------------条件 start--------------------
        //子网/
        String clientUideSql="";
        if (bean.getClientId()>0) {
            clientUideSql="  l.clientUideId REGEXP  '\\\\."+bean.getClientId()+"\\\\.' ";
        }
        //应用/服务端
        String serverUideSql="";
        if (bean.getServerId()>0) {
            serverUideSql="  l.serverUideId REGEXP  '\\\\."+bean.getServerId()+"\\\\.' ";
        }
        //端口
        String serverportSql="";
        if (bean.getServerport()>0) {
            serverportSql="  l.serverport = "+bean.getServerport()+" ";
        }
        //特殊情况(协议分布)  需要使用or
        StringBuffer serCliIpSQLBuf=new StringBuffer();
        //服务端ip
        String serveripSql="";
        //客户端ip
        String clientipSql="";
        if (bean.getNeedSerCli()==1){
            if (StringUtils.isNotEmpty(bean.getServerip()) && StringUtils.isNotEmpty(bean.getClientip())){
                serCliIpSQLBuf.append("  (");
                serCliIpSQLBuf.append(" l.serverip = inet_aton('"+bean.getServerip()+"')");
                serCliIpSQLBuf.append(" or ");
                serCliIpSQLBuf.append(" l.clientip = inet_aton('"+bean.getClientip()+"')");
                serCliIpSQLBuf.append(" ) ");
            }
        } else {
          //服务端ip
            if (StringUtils.isNotEmpty(bean.getServerip())){
                serveripSql="  l.serverip = inet_aton('"+bean.getServerip()+"')";
            }
          //客户端ip
            if (StringUtils.isNotEmpty(bean.getClientip())){
                clientipSql="  l.clientip = inet_aton('"+bean.getClientip()+"')";
            }
        }

        //聚合类型(子sql用)
        String groupBySql="";
        if (bean.getGroupType()>=0) {
            switch (bean.getGroupType()){
                case 1:
                    groupBySql=" l.serverip ";
                    break;
                case 2:
                    groupBySql=" l.clientip ";
                    break;
                case 3:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
                case 4:
                    groupBySql=" l.serverip ,l.serverport ";
                    break;
                default:
                    groupBySql=" l.serverip ,l.clientip ";
                    break;
            }
        }
        //-------------条件 end--------------------

        //迭代多个表
        BEGIN();
        
        //sub--迭代所有符合条件的表       
        SELECT(subSqlBuf.toString());
        FROM(tableName + " l");
 
        LEFT_OUTER_JOIN(" ipm_ip_location_cn loc ON loc.loc_id_cn = l.clientLocId ");
        if(StringUtils.isNotEmpty(serveripSql)){
            WHERE(serveripSql);
        }
        if(StringUtils.isNotEmpty(clientipSql)){
            WHERE(clientipSql);
        }
        if(StringUtils.isNotEmpty(serverportSql)){
            WHERE(serverportSql);
        }
        WHERE("l.snaptime > "+bean.getStarttime());
        WHERE("l.snaptime <= "+bean.getEndtime());
        if (StringUtils.isNotEmpty(bean.getServerportNotInSql())) {
            WHERE(bean.getServerportNotInSql());
        }
        if (StringUtils.isNotEmpty(bean.getServerIpPortSql())) {
            WHERE(bean.getServerIpPortSql());
        }
        if (StringUtils.isNotEmpty(clientUideSql)){
            WHERE(clientUideSql);
        }
        if (StringUtils.isNotEmpty(serverUideSql)){
            WHERE(serverUideSql);
        }
        if (StringUtils.isNotEmpty(serCliIpSQLBuf)){
            WHERE(serCliIpSQLBuf.toString());
        }
        if (StringUtils.isNotEmpty(bean.getContinent())) {//洲际
            WHERE(" loc.continent REGEXP '^"+bean.getContinent()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryCn())) {//国家
            WHERE(" loc.country REGEXP '^"+bean.getCountryCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getRegionCn())) {//省份
            WHERE(" loc.region_cn REGEXP '^"+bean.getRegionCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCityCn())) {//城市
            WHERE(" loc.city_cn REGEXP '^"+bean.getCityCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getIspCn())) {//运营商
            WHERE(" loc.isp_cn REGEXP '^"+bean.getIspCn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryEn())) {//国家英文
            WHERE(" loc.country_english REGEXP '^"+bean.getCountryEn()+"' ");
        }
        if (StringUtils.isNotEmpty(bean.getCountryCode())) {//国家简写
            WHERE(" loc.country_code REGEXP '^"+bean.getCountryCode()+"' ");
        }
        if(bean.getOtherCountry()!=0){//国外地址
            WHERE(" loc.country_code REGEXP '^[^CN]' ");
        }
        
        GROUP_BY(groupBySql);
       
        return SQL();
    }
    
}
