/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DeptMsgLogBean
 *创建人:wjm    创建时间:2018年4月14日
 */
package com.protocolsoft.depthanaly.bean;

import com.protocolsoft.common.bean.CenterParamBean;

/**
 * 
 * @ClassName: DeptMsgLogBean
 * @Description: 报文交易会话列表信息
 * @author wangjianmin
 *
 */
public class DeptMsgLogBean extends CenterParamBean {
    
    /**
     * 编号
     */
    int   id;
    /**
     * 观察点
     */
    int watchpointId;
    /**
     * 获取条数
     */
    int num;
    /**
     * 收索报文内容
     */
    String message;
    /**
     * 名称
     */
    String name;
    /**
     * 业务ID
     */
    long   busiId;
    /**
     * 原始报文
     */
    String  originalMessage;
    /**
     * 数据中的时间
     */
    long transTime;
    /**
     * 数据表名称
     */
    String tableName;
    /**
     * 服务端IP
     */
    String    server;
    /**
     * 开始时间
     */
    long starttime;
    /**
     * 结束时间
     */
    long endtime;
    /**
     * 客户端IP
     */
    String client;
    /**
     * 服务端端口
     */
    long serverPort;
    /**
     * 客户端端口
     */
    long clientPort;
    /**
     * 交易时延
     */
    long delay;
    /**
     * 报文
     */
    String source;
    /**
     * 交易成功
     */
    Integer success;
    /**
     * 响应率
     */
    Integer resp;
    /**
     * 服务器分页
     */
    Integer serverPageNumber;
    /**
     * 拼接SQL
     */
    StringBuffer ipStr;
    
    /**
     * 分页过滤数量
     */
    private int startNum;
    
    public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	/**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }   
    /**
     * @return the busiId
     */
    public long getBusiId() {
        return busiId;
    }
    /**
     * @param busiId the busiId to set
     */
    public void setBusiId(long busiId) {
        this.busiId = busiId;
    }
    /**
     * @return the transTime
     */
    public long getTransTime() {
        return transTime;
    }
    /**
     * @param transTime the transTime to set
     */
    public void setTransTime(long transTime) {
        this.transTime = transTime;
    }
    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }
    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
  
    /**
     * @return the starttime
     */
    public long getStarttime() {
        return starttime;
    }
    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }
    /**
     * @return the endtime
     */
    public long getEndtime() {
        return endtime;
    }
    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
  
    /**
     * @return the serverPort
     */
    public long getServerPort() {
        return serverPort;
    }
    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(long serverPort) {
        this.serverPort = serverPort;
    }
    /**
     * @return the clientPort
     */
    public long getClientPort() {
        return clientPort;
    }
    /**
     * @param clientPort the clientPort to set
     */
    public void setClientPort(long clientPort) {
        this.clientPort = clientPort;
    }
    /**
     * @return the delay
     */
    public long getDelay() {
        return delay;
    }
    /**
     * @param delay the delay to set
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }
    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }
    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }
    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }
    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }
    /**
     * @return the watchpointId
     */
    public int getWatchpointId() {
        return watchpointId;
    }
    /**
     * @param watchpointId the watchpointId to set
     */
    public void setWatchpointId(int watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the ipStr
     */
    public StringBuffer getIpStr() {
        return ipStr;
    }
    /**
     * @param ipStr the ipStr to set
     */
    public void setIpStr(StringBuffer ipStr) {
        this.ipStr = ipStr;
    }
    /**
     * @return the success
     */
    public Integer getSuccess() {
        return success;
    }
    /**
     * @param success the success to set
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }
    /**
     * @return the resp
     */
    public Integer getResp() {
        return resp;
    }
    /**
     * @param resp the resp to set
     */
    public void setResp(Integer resp) {
        this.resp = resp;
    }
    /**
     * @return the originalMessage
     */
    public String getOriginalMessage() {
        return originalMessage;
    }
    /**
     * @param originalMessage the originalMessage to set
     */
    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }
    /**
     * @return the serverPageNumber
     */
    public Integer getServerPageNumber() {
        return serverPageNumber;
    }
    /**
     * @param serverPageNumber the serverPageNumber to set
     */
    public void setServerPageNumber(Integer serverPageNumber) {
        this.serverPageNumber = serverPageNumber;
    }
    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }
    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }
}
