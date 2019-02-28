/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairDao.java
 *创建人:chensq    创建时间:2017年10月17日
 */
package com.protocolsoft.commpair.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.bean.CommpairFindParamBean;

/**
 * @ClassName: CommpairDao
 * @Description: commpair DAO
 * @author chensq
 *
 */
@Repository
public interface CommpairDao {
    
    /**
     * 
     * @Title: getCommpairTables
     * @Description: 返回符合时间段范围的表名集合
     * @param tablePrefix 前缀
     * @param timeBucketTable 对应的时间段表
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param watchpointId 观察点id
     * @return List<String>
     * @author chensq
     */
    @Select("select concat('${tablePrefix}', table_id) `name` " 
            +" from " 
            +" ${timeBucketTable} " 
            +" where " 
            +" ((end  >= ${starttime}  and end   <= ${endtime})  or " 
            +" (start <= ${starttime}  and end   >= ${endtime})  or " 
            +" (start >= ${starttime}  and start <= ${endtime})) and " 
            +" wpid = ${watchpointId} ")
    List<String> getCommpairTables(@Param("tablePrefix") String  tablePrefix,
                                   @Param("timeBucketTable") String timeBucketTable,
                                   @Param("starttime") long starttime,
                                   @Param("endtime") long endtime,
                                   @Param("watchpointId") int watchpointId);
    
    /**
     * 
     * @Title: getCommpairUnionAllMergeLogs
     * @Description: 查询合并的通信对(UNION ALL，多表查询)
     * @param commpairFindParamBean 查询参数bean
     * @return List<CommpairBean>
     * @author chensq
     */
    @Select(
            "SELECT "
                    +"${watchpointSql} "
                    +"clientUideIds AS clientUideIds, "
                    +"serverUideIds AS serverUideIds, "
                    +"snaptime AS snaptime, "
                    +"min(starttime) AS starttime, "
                    +"max(endtime) AS endtime, "
                    +"serverip AS serverip, "
                    +"clientip AS clientip, "
                    +"serverport AS serverport, "
                    
                    //-----展示项目start-----
                    +"SUM(ethernetTraffic) AS ethernetTraffic, " // 网络流量
                    +"SUM(synPkts) AS synPkts, " // SYN包数
                    +"SUM(synAckPkts) AS synAckPkts, " // 连接应答数量
                    +"AVG(qos) AS qos, " // 服务质量
                    +"AVG(serverDurDelay) AS serverDurDelay, " // 服务端通信时延
                    +"AVG(clientDurDelay) AS clientDurDelay, " // 客户端通信时延
                    
                    +"AVG(rtt) AS rtt, " // 链路时延RTT
                    +"AVG(serverConDelay) AS serverConDelay, " // 服务端握手时延
                    +"AVG(clientConDelay) AS clientConDelay, " // 客户端握手时延
                    +"AVG(responseDelay) AS responseDelay, " // 应用处理时延
                    +"AVG(loadDelay) AS loadDelay, " // 负载传输时延

                    +"AVG(serverRetransDelay) AS serverRetransDelay, " // 服务端重传时延
                    +"AVG(clientRetransDelay) AS clientRetransDelay, " // 客户端重传时延
                    +"SUM(tcpTraffic) AS tcpTraffic, " // TCP流量
                    +"SUM(udpTraffic) AS udpTraffic, " // UDP流量
                    +"SUM(inTraffic) AS inTraffic, " // 入流量
                    +"SUM(outTraffic) AS outTraffic, " // 出流量
                    +"SUM(rstPkts) AS rstPkts, "// rst包数
                    
                    +"SUM(finPkts) AS finPkts, " // fin包数
                    +"SUM(fin1Pkts) AS fin1Pkts, " // 主动关闭包数
                    +"SUM(fin2Pkts) AS fin2Pkts, " // 被动关闭包数
                    +"SUM(ethernetPkts) AS ethernetPkts, " // 数据包个数(与rrd名称不一致)
                    +"(netPktLost/ethernetPkts)*100 AS netPktLostRatio, " // 网络丢包率 (netPktLost/ethernetPkts)

                    +"(serverPktLost/serverPkt)*100 AS serverPktLostRatio, " // 服务端丢包率  (serverPktLost/serverPkt)
                    +"(clientPktLost/clientPkt)*100 AS clientPktLostRatio, " // 客户端丢包率  (clientPktLost/clientPkt)
                    +"SUM(tinyPkts) AS tinyPkts, " // 小包个数(与rrd名称不一致)
                    +"(tinyPkts/ethernetPkts)*100 AS tinyPktsRatio, " // 小包比率 (tinyPkts/ethernetPkts)
                    +"ethernetTraffic/8/ethernetPkts AS avgPktsLen, " // 平均包长

                    +"SUM(zeroWinCount) AS zeroWinCount, " // 零窗口包数
                    //-----展示项目start-----
                    
                    //-----数据库列start-----
                    +"SUM(netPktLost) AS netPktLost, " // 网络传输丢包率
                    +"SUM(serverPktLost) AS serverPktLost, " // 服务端丢包率
                    +"SUM(clientPktLost) AS clientPktLost, " // 客户端丢包率
                    +"SUM(serverPkt) AS serverPkt, " // 服务端包数
                    +"SUM(clientPkt) AS clientPkt, " // 客户端包数
                    //-----数据库列end-----
                    
                    //汇合
                    +"AVG(serverIpColumn) AS serverIpColumn, "
                    +"AVG(clientIpColumn) AS clientIpColumn, "
                    +"AVG(serverPortColumn) AS serverPortColumn, "
                    
                    +"GROUP_CONCAT(serverportConcat)   serverportConcat "
                    
                    +"FROM "
                    +"( "
                    +"${unionAllSql}" 
                    +") "
                    +" un "
                    +"${groupBySql}  "              //            GROUP BY l.serverip ,l.clientip
                    +"${orderBySql}  "              //            order BY snaptime
                    +"${pageSql} "                  //            limit
    )
    List<CommpairBean> getCommpairUnionAllMergeLogs(CommpairFindParamBean commpairFindParamBean);
  
    /**
     * 
     * @Title: getCommpairUnionAllMergeLogsCount
     * @Description: 查询合并的通信对数量(UNION ALL，多表查询)
     * @param commpairFindParamBean 查询参数bean
     * @return long
     * @author chensq
     */
    @Select(
            "SELECT COUNT(*) as count  FROM "
                    +"("
                    +"SELECT "
                    +"count(watchpointId) "
                    +"FROM "
                    +"( "
                    +"${unionAllSql}"
                    +") "
                    +" un "
                    +"${groupBySql}  "
                    +")"
                    + "AS a"
    )
    long getCommpairUnionAllMergeLogsCount(CommpairFindParamBean commpairFindParamBean);
     
    /**
     * 
     * @Title: getCommpairUnionAllMergeLocationLogs
     * @Description: 查询合并的通信对(UNION ALL，多表查询),关联地址库
     * @param commpairFindParamBean 查询参数bean
     * @return List<CommpairBean>
     * @author chensq
     */
    @Select(
            "SELECT "
                    +"${watchpointSql} "
                    +"clientUideIds AS clientUideIds, "
                    +"serverUideIds AS serverUideIds, "
                    +"snaptime AS snaptime, "
                    +"min(starttime) AS starttime, "
                    +"max(endtime) AS endtime, "
                    +"serverip AS serverip, "
                    +"clientip AS clientip, "
                    +"serverport AS serverport, "
                    // 具体指标
                    //-----展示项目start-----
                    +"SUM(ethernetTraffic) AS ethernetTraffic, " // 网络流量
                    +"SUM(synPkts) AS synPkts, " // SYN包数
                    +"SUM(synAckPkts) AS synAckPkts, " // 连接应答数量
                    +"AVG(qos) AS qos, " // 服务质量
                    +"AVG(serverDurDelay) AS serverDurDelay, " // 服务端通信时延
                    +"AVG(clientDurDelay) AS clientDurDelay, " // 客户端通信时延
                    
                    +"AVG(rtt) AS rtt, " // 链路时延RTT
                    +"AVG(serverConDelay) AS serverConDelay, " // 服务端握手时延
                    +"AVG(clientConDelay) AS clientConDelay, " // 客户端握手时延
                    +"AVG(responseDelay) AS responseDelay, " // 应用处理时延
                    +"AVG(loadDelay) AS loadDelay, " // 负载传输时延

                    +"AVG(serverRetransDelay) AS serverRetransDelay, " // 服务端重传时延
                    +"AVG(clientRetransDelay) AS clientRetransDelay, " // 客户端重传时延
                    +"SUM(tcpTraffic) AS tcpTraffic, " // TCP流量
                    +"SUM(udpTraffic) AS udpTraffic, " // UDP流量
                    +"SUM(inTraffic) AS inTraffic, "// 入流量
                    +"SUM(outTraffic) AS outTraffic, "// 出流量
                    +"SUM(rstPkts) AS rstPkts, "// rst包数
                    
                    +"SUM(finPkts) AS finPkts, " // fin包数
                    +"SUM(fin1Pkts) AS fin1Pkts, "// 主动关闭包数
                    +"SUM(fin2Pkts) AS fin2Pkts, "// 被动关闭包数                  
                    +"SUM(ethernetPkts) AS ethernetPkts, " // 数据包个数(与rrd名称不一致)
                    +"(netPktLost/ethernetPkts)*100 AS netPktLostRatio, " // 网络丢包率 (netPktLost/ethernetPkts)

                    +"(serverPktLost/serverPkt)*100 AS serverPktLostRatio, " // 服务端丢包率  (serverPktLost/serverPkt)
                    +"(clientPktLost/clientPkt)*100 AS clientPktLostRatio, " // 客户端丢包率  (clientPktLost/clientPkt)
                    +"SUM(tinyPkts) AS tinyPkts, " // 小包个数(与rrd名称不一致)
                    +"(tinyPkts/ethernetPkts)*100 AS tinyPktsRatio, " // 小包比率 (tinyPkts/ethernetPkts)
                    +"ethernetTraffic/8/ethernetPkts AS avgPktsLen, " // 平均包长

                    +"SUM(zeroWinCount) AS zeroWinCount, " // 零窗口包数
                    //-----展示项目start-----

                    //-----数据库列start-----
                    +"SUM(netPktLost) AS netPktLost, " // 网络传输丢包率
                    +"SUM(serverPktLost) AS serverPktLost, " // 服务端丢包率
                    +"SUM(clientPktLost) AS clientPktLost, " // 客户端丢包率
                    +"SUM(serverPkt) AS serverPkt, " // 服务端包数
                    +"SUM(clientPkt) AS clientPkt, " // 客户端包数
                    //-----数据库列end-----
                    
                    //汇合
                    +"AVG(serverIpColumn) AS serverIpColumn, "
                    +"AVG(clientIpColumn) AS clientIpColumn, "
                    +"AVG(serverPortColumn) AS serverPortColumn, "
                    
                    +"${openIpLocationFlagColumnSql} "

                    +"GROUP_CONCAT(serverportConcat)   serverportConcat "
                    
                    +"FROM "
                    +"( "
                    +"${unionAllSql}" 
                    +") "
                    +" un "
                    +"${groupBySql}  "              //            GROUP BY l.serverip ,l.clientip
                    +"${orderBySql}  "              //            order BY snaptime
                    +"${pageSql} "                  //            limit
    )
    List<CommpairBean> getCommpairUnionAllMergeLocationLogs(CommpairFindParamBean commpairFindParamBean);
        
    /**
     * 
     * @Title: getCommpairUnionAllDetailLogs
     * @Description: 查询未合并的通信对列表(合并列表点击)
     * @param unionAllSql 多张表un的sql
     * @return List<CommpairBean>
     * @author chensq
     */
    @Select("${unionAllSql}")
    List<CommpairBean> getCommpairUnionAllDetailLogs(@Param("unionAllSql") String unionAllSql);
    
}
