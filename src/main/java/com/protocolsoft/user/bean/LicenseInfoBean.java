/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: LicenseInfoBean.java
 *创建人: WWW    创建时间: 2018年9月17日
 */
package com.protocolsoft.user.bean;

/**
 * @ClassName: LicenseInfoBean
 * @Description: 授权信息
 * @author WWW
 *
 */
public class LicenseInfoBean {

    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 联系人
     */
    private String contacts;
    
    /**
     * 电话
     */
    private String telephone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 系统版本号
     */
    private String productNo;
    
    /**
     * 最大分析流量(Mb)
     */
    private int maxFlow;
    
    /**
     * 是否支持多观察点
     */
    private boolean manyWatchpoint;
    
    /**
     * 是否开启服务端
     */
    private boolean server;
    
    /**
     * 是否开启客户端
     */
    private boolean client;
    
    /**
     * 是否开启HTTP
     */
    private boolean http;
    
    /**
     * 是否开启MYSQL
     */
    private boolean mysql;
    
    /**
     * 是否开启ORACLE
     */
    private boolean oracle;
    
    /**
     * 是否开启SQLSERVER
     */
    private boolean sqlserver;
    
    /**
     * 是否开启URL
     */
    private boolean url;
    
    /**
     * 是否开启报文
     */
    private boolean message;
    
    /**
     * 是否开启流量储存
     */
    private boolean flowStorage;
    
    /**
     * 是否开启地图
     */
    private boolean map;
    
    /**
     * 是否开启拓扑图
     */
    private boolean topo;
    
    /**
     * 是否开启通信队
     */
    private boolean trafficPair;
    
    /**
     * 是否开启digger
     */
    private boolean digger;
    
    /**
     * 多观察点有效期
     */
    private String manyTerm;
    
    /**
     * 服务端有效期
     */
    private String serverTerm;
    
    /**
     * 客户端有效期
     */
    private String clientTerm;
    
    /**
     * HTTP有效期
     */
    private String httpTerm;
    
    /**
     * MYSQL有效期
     */
    private String mysqlTerm;
    
    /**
     * ORACLE有效期
     */
    private String oracleTerm;
    
    /**
     * SQLSERVER有效期
     */
    private String sqlserverTerm;
    
    /**
     * URL有效期
     */
    private String urlTerm;
    
    /**
     * 报文有效期
     */
    private String messageTerm;
    
    /**
     * 流量存储有效期
     */
    private String storeTerm;
    
    /**
     * 地图有效期
     */
    private String mapTerm;
    
    /**
     * 拓扑图有效期
     */
    private String topoTerm;
    
    /**
     * 通信队有效期
     */
    private String pairTerm;
    
    /**
     * digger有限期
     */
    private String diggerTerm;

    /**
     * <br />获取 <font color="red"><b>用户名称<b/></font>
     * @return userName 用户名称
     */
    public String getUserName() {
        return userName;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>用户名称</b></font>
     * @param userName 用户名称  
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    /**
     * <br />获取 <font color="red"><b>联系人<b/></font>
     * @return contacts 联系人
     */
    public String getContacts() {
        return contacts;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>联系人</b></font>
     * @param contacts 联系人  
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    

    /**
     * <br />获取 <font color="red"><b>电话<b/></font>
     * @return telephone 电话
     */
    public String getTelephone() {
        return telephone;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>电话</b></font>
     * @param telephone 电话  
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    

    /**
     * <br />获取 <font color="red"><b>邮箱<b/></font>
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>邮箱</b></font>
     * @param email 邮箱  
     */
    public void setEmail(String email) {
        this.email = email;
    }
    

    /**
     * <br />获取 <font color="red"><b>系统版本号<b/></font>
     * @return productNo 系统版本号
     */
    public String getProductNo() {
        return productNo;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>系统版本号</b></font>
     * @param productNo 系统版本号  
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    


    /**
     * <br />获取 <font color="red"><b>最大分析流量(Mb)<b/></font>
     * @return maxFlow 最大分析流量(Mb)
     */
    public int getMaxFlow() {
        return maxFlow;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>最大分析流量(Mb)</b></font>
     * @param maxFlow 最大分析流量(Mb)  
     */
    public void setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否支持多观察点<b/></font>
     * @return manyWatchpoint 是否支持多观察点
     */
    public boolean isManyWatchpoint() {
        return manyWatchpoint;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否支持多观察点</b></font>
     * @param manyWatchpoint 是否支持多观察点  
     */
    public void setManyWatchpoint(boolean manyWatchpoint) {
        this.manyWatchpoint = manyWatchpoint;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启服务端<b/></font>
     * @return server 是否开启服务端
     */
    public boolean isServer() {
        return server;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启服务端</b></font>
     * @param server 是否开启服务端  
     */
    public void setServer(boolean server) {
        this.server = server;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启客户端<b/></font>
     * @return client 是否开启客户端
     */
    public boolean isClient() {
        return client;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启客户端</b></font>
     * @param client 是否开启客户端  
     */
    public void setClient(boolean client) {
        this.client = client;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启HTTP<b/></font>
     * @return http 是否开启HTTP
     */
    public boolean isHttp() {
        return http;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启HTTP</b></font>
     * @param http 是否开启HTTP  
     */
    public void setHttp(boolean http) {
        this.http = http;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启MYSQL<b/></font>
     * @return mysql 是否开启MYSQL
     */
    public boolean isMysql() {
        return mysql;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启MYSQL</b></font>
     * @param mysql 是否开启MYSQL  
     */
    public void setMysql(boolean mysql) {
        this.mysql = mysql;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启ORACLE<b/></font>
     * @return oracle 是否开启ORACLE
     */
    public boolean isOracle() {
        return oracle;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启ORACLE</b></font>
     * @param oracle 是否开启ORACLE  
     */
    public void setOracle(boolean oracle) {
        this.oracle = oracle;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启SQLSERVER<b/></font>
     * @return sqlserver 是否开启SQLSERVER
     */
    public boolean isSqlserver() {
        return sqlserver;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启SQLSERVER</b></font>
     * @param sqlserver 是否开启SQLSERVER  
     */
    public void setSqlserver(boolean sqlserver) {
        this.sqlserver = sqlserver;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启URL<b/></font>
     * @return url 是否开启URL
     */
    public boolean isUrl() {
        return url;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启URL</b></font>
     * @param url 是否开启URL  
     */
    public void setUrl(boolean url) {
        this.url = url;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启报文<b/></font>
     * @return message 是否开启报文
     */
    public boolean isMessage() {
        return message;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启报文</b></font>
     * @param message 是否开启报文  
     */
    public void setMessage(boolean message) {
        this.message = message;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启流量储存<b/></font>
     * @return flowStorage 是否开启流量储存
     */
    public boolean isFlowStorage() {
        return flowStorage;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启流量储存</b></font>
     * @param flowStorage 是否开启流量储存  
     */
    public void setFlowStorage(boolean flowStorage) {
        this.flowStorage = flowStorage;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启地图<b/></font>
     * @return map 是否开启地图
     */
    public boolean isMap() {
        return map;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启地图</b></font>
     * @param map 是否开启地图  
     */
    public void setMap(boolean map) {
        this.map = map;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启拓扑图<b/></font>
     * @return topo 是否开启拓扑图
     */
    public boolean isTopo() {
        return topo;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启拓扑图</b></font>
     * @param topo 是否开启拓扑图  
     */
    public void setTopo(boolean topo) {
        this.topo = topo;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否开启通信队<b/></font>
     * @return trafficPair 是否开启通信队
     */
    public boolean isTrafficPair() {
        return trafficPair;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否开启通信队</b></font>
     * @param trafficPair 是否开启通信队  
     */
    public void setTrafficPair(boolean trafficPair) {
        this.trafficPair = trafficPair;
    }
    

    /**
     * <br />获取 <font color="red"><b>多观察点有效期<b/></font>
     * @return manyTerm 多观察点有效期
     */
    public String getManyTerm() {
        return manyTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>多观察点有效期</b></font>
     * @param manyTerm 多观察点有效期  
     */
    public void setManyTerm(String manyTerm) {
        this.manyTerm = manyTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>服务端有效期<b/></font>
     * @return serverTerm 服务端有效期
     */
    public String getServerTerm() {
        return serverTerm;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>服务端有效期</b></font>
     * @param serverTerm 服务端有效期  
     */
    public void setServerTerm(String serverTerm) {
        this.serverTerm = serverTerm;
    }
    

    /**
     * <br />获取 <font color="red"><b>客户端有效期<b/></font>
     * @return clientTerm 客户端有效期
     */
    public String getClientTerm() {
        return clientTerm;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>客户端有效期</b></font>
     * @param clientTerm 客户端有效期  
     */
    public void setClientTerm(String clientTerm) {
        this.clientTerm = clientTerm;
    }


    /**
     * <br />获取 <font color="red"><b>HTTP有效期<b/></font>
     * @return httpTerm HTTP有效期
     */
    public String getHttpTerm() {
        return httpTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>HTTP有效期</b></font>
     * @param httpTerm HTTP有效期  
     */
    public void setHttpTerm(String httpTerm) {
        this.httpTerm = httpTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>MYSQL有效期<b/></font>
     * @return mysqlTerm MYSQL有效期
     */
    public String getMysqlTerm() {
        return mysqlTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>MYSQL有效期</b></font>
     * @param mysqlTerm MYSQL有效期  
     */
    public void setMysqlTerm(String mysqlTerm) {
        this.mysqlTerm = mysqlTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>ORACLE有效期<b/></font>
     * @return oracleTerm ORACLE有效期
     */
    public String getOracleTerm() {
        return oracleTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>ORACLE有效期</b></font>
     * @param oracleTerm ORACLE有效期  
     */
    public void setOracleTerm(String oracleTerm) {
        this.oracleTerm = oracleTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>SQLSERVER有效期<b/></font>
     * @return sqlserverTerm SQLSERVER有效期
     */
    public String getSqlserverTerm() {
        return sqlserverTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>SQLSERVER有效期</b></font>
     * @param sqlserverTerm SQLSERVER有效期  
     */
    public void setSqlserverTerm(String sqlserverTerm) {
        this.sqlserverTerm = sqlserverTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>URL有效期<b/></font>
     * @return urlTerm URL有效期
     */
    public String getUrlTerm() {
        return urlTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>URL有效期</b></font>
     * @param urlTerm URL有效期  
     */
    public void setUrlTerm(String urlTerm) {
        this.urlTerm = urlTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>报文有效期<b/></font>
     * @return messageTerm 报文有效期
     */
    public String getMessageTerm() {
        return messageTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>报文有效期</b></font>
     * @param messageTerm 报文有效期  
     */
    public void setMessageTerm(String messageTerm) {
        this.messageTerm = messageTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>流量存储有效期<b/></font>
     * @return storeTerm 流量存储有效期
     */
    public String getStoreTerm() {
        return storeTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>流量存储有效期</b></font>
     * @param storeTerm 流量存储有效期  
     */
    public void setStoreTerm(String storeTerm) {
        this.storeTerm = storeTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>地图有效期<b/></font>
     * @return mapTerm 地图有效期
     */
    public String getMapTerm() {
        return mapTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>地图有效期</b></font>
     * @param mapTerm 地图有效期  
     */
    public void setMapTerm(String mapTerm) {
        this.mapTerm = mapTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>拓扑图有效期<b/></font>
     * @return topoTerm 拓扑图有效期
     */
    public String getTopoTerm() {
        return topoTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>拓扑图有效期</b></font>
     * @param topoTerm 拓扑图有效期  
     */
    public void setTopoTerm(String topoTerm) {
        this.topoTerm = topoTerm;
    }
    


    /**
     * <br />获取 <font color="red"><b>通信队有效期<b/></font>
     * @return pairTerm 通信队有效期
     */
    public String getPairTerm() {
        return pairTerm;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>通信队有效期</b></font>
     * @param pairTerm 通信队有效期  
     */
    public void setPairTerm(String pairTerm) {
        this.pairTerm = pairTerm;
    }


    /**
     * <br />获取 <font color="red"><b>是否开启digger<b/></font>
     * @return digger 是否开启digger
     */
    public boolean isDigger() {
        return digger;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>是否开启digger</b></font>
     * @param digger 是否开启digger  
     */
    public void setDigger(boolean digger) {
        this.digger = digger;
    }
    

    /**
     * <br />获取 <font color="red"><b>digger有限期<b/></font>
     * @return diggerTerm digger有限期
     */
    public String getDiggerTerm() {
        return diggerTerm;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>digger有限期</b></font>
     * @param diggerTerm digger有限期  
     */
    public void setDiggerTerm(String diggerTerm) {
        this.diggerTerm = diggerTerm;
    }
    
}
