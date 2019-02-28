/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DepthProvider
 *创建人:wjm    创建时间:2018年4月17日
 */
package com.protocolsoft.depthanaly.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import com.protocolsoft.depthanaly.bean.DeptMsgLogBean;
/**
 * 
 * @ClassName: DepthProvider
 * @Description: 防止sql注入
 * @author wangjianmin
 *
 */
public class DepthProvider {
    
    /**
     * 查询报文会话列表
     * 2018年4月17日 下午9:43:42
     * @param
     * @return String
     * @exception 
     * @see
     */
    public String  selectLog(DeptMsgLogBean bean){
        BEGIN();
        SELECT("m.id,w.name,`busiId`,`transTime`,endTime as endtime,inet_ntoa(`server`) as `server`,"
                + "inet_ntoa(`client`) as `client`,`serverPort`,`clientPort`,"
                + "`delay`,`source`,`success`,`resp`");
        FROM(bean.getTableName() +" m");
        JOIN("ipm_watchpoint  w on   m.`probe` =w.id ");
        WHERE("transTime >= #{starttime}");
        WHERE("endTime <= #{endtime}");
        
        //交易成功率
        if(bean.getSuccess() !=null){
            WHERE("`success` = #{success}");
        }
        //交易响应率
        if(bean.getResp() !=null){
            WHERE("`resp` = #{resp}");
        }
        //交易时延
        if(bean.getDelay() !=0){
            WHERE("`delay` = #{delay}");
        }
        //业务ID
        if(bean.getBusiId() !=0){
            WHERE("busiId = #{busiId}");
        }
        //客户端IP 不为空 
        if(bean.getClient()!= null && !bean.getClient().equals("")){
            WHERE("`client` =#{client} ");
        }
        //客户端端端口 不为空 
        if(bean.getClientPort() !=0){
            WHERE("clientPort = #{clientPort}");
        }
        //服务端IP 不为空 
        if(bean.getServer() !=null && !bean.getServer().equals("")){
            WHERE("`server` = #{server}");
        }
        //服务端端端口 不为空 
        if(bean.getServerPort() !=0){
            WHERE("serverPort=#{serverPort} ");
        }
        //观察点不为空
        if(bean.getWatchpointId() !=0){
            WHERE(" m.`probe` =#{watchpointId}");
        }
        //报文内容
        if(bean.getMessage() !=null && !bean.getMessage().equals("")){
            WHERE(" source like '%"+bean.getMessage()+"%'"); 
        }
        ORDER_BY("id desc limit #{startNum}, #{num}");
        return SQL();
    }
    
    /**
     * 
     * @Title: getDataNumSql
     * @Description: 获取数量语句
     * @param bean 参数
     * @return String
     * @author wjm
     */
    public String getDataNumSql(DeptMsgLogBean bean) {
    	 BEGIN();
         SELECT(" count(*) num ");
         FROM(bean.getTableName());
         WHERE("transTime >= #{starttime}");
         WHERE("endTime <= #{endtime}");
         
         //交易成功率
         if(bean.getSuccess() !=null){
             WHERE("`success` = #{success}");
         }
         //交易响应率
         if(bean.getResp() !=null){
             WHERE("`resp` = #{resp}");
         }
         //交易时延
         if(bean.getDelay() !=0){
             WHERE("`delay` = #{delay}");
         }
         //业务ID
         if(bean.getBusiId() !=0){
             WHERE("busiId = #{busiId}");
         }
         //客户端IP 不为空 
         if(bean.getClient()!= null && !bean.getClient().equals("")){
             WHERE("`client` =#{client} ");
         }
         //客户端端端口 不为空 
         if(bean.getClientPort() !=0){
             WHERE("clientPort = #{clientPort}");
         }
         //服务端IP 不为空 
         if(bean.getServer() !=null && !bean.getServer().equals("")){
             WHERE("`server` = #{server}");
         }
         //服务端端端口 不为空 
         if(bean.getServerPort() !=0){
             WHERE("serverPort=#{serverPort} ");
         }
         //观察点不为空
         if(bean.getWatchpointId() !=0){
             WHERE(" `probe` =#{watchpointId}");
         }
         //报文内容
         if(bean.getMessage() !=null && !bean.getMessage().equals("")){
             WHERE(" source like '%"+bean.getMessage()+"%'"); 
         }
    	return SQL();
    }

}
