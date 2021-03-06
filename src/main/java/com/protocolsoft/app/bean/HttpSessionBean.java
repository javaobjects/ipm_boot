/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: HttpSessionBean.java
 *创建人: www    创建时间: 2018年1月12日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: HttpSessionBean
 * @Description: HTTP会话列表
 * @author www
 *
 */
public class HttpSessionBean extends AppSessionBean {

    /**
     * 结束时间
     */
    private long endtimewithpayload;
    
    /**
     * 服务端IP
     */
    private String serverip;
    
    /**
     * URL
     */
    private String url;
    
    /**
     * 协议
     */
    private String protocol;
    
    /**
     * 命令类型
     */
    private String method;
    
    /**
     * HTTP返回码
     */
    private int httpreturncode;
    
    /**
     * 加载类型
     */
    private String contenttype;
    
    /**
     * URL加载时延
     */
    private float pageload;
    
    /**
     * 响应时延
     */
    private float appllatency;
    
    /**
     * 源客户端
     */
    private String forward;
    
    /**
     * VLAN ID
     */
    private int vlanId;
    
    /**
     * 请求报文
     */
    private String req;
    
    /**
     * 响应报文
     */
    private String res;

    /**
     * <br />获取 <font color="red"><b>结束时间<b/></font>
     * @return endtimewithpayload 结束时间
     */
    public long getEndtimewithpayload() {
        return endtimewithpayload;
    }

    /**  
     * <br />设置 <font color='#333399'><b>结束时间</b></font>
     * @param endtimewithpayload 结束时间  
     */
    public void setEndtimewithpayload(long endtimewithpayload) {
        this.endtimewithpayload = endtimewithpayload;
    }

    /**
     * <br />获取 <font color="red"><b>服务端IP<b/></font>
     * @return serverip 服务端IP
     */
    public String getServerip() {
        return serverip;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端IP</b></font>
     * @param serverip 服务端IP  
     */
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    /**
     * <br />获取 <font color="red"><b>URL<b/></font>
     * @return url URL
     */
    public String getUrl() {
        return url;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URL</b></font>
     * @param url URL  
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <br />获取 <font color="red"><b>协议<b/></font>
     * @return protocol 协议
     */
    public String getProtocol() {
        return protocol;
    }

    /**  
     * <br />设置 <font color='#333399'><b>协议</b></font>
     * @param protocol 协议  
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * <br />获取 <font color="red"><b>命令类型<b/></font>
     * @return method 命令类型
     */
    public String getMethod() {
        return method;
    }

    /**  
     * <br />设置 <font color='#333399'><b>命令类型</b></font>
     * @param method 命令类型  
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * <br />获取 <font color="red"><b>HTTP返回码<b/></font>
     * @return httpreturncode HTTP返回码
     */
    public int getHttpreturncode() {
        return httpreturncode;
    }

    /**  
     * <br />设置 <font color='#333399'><b>HTTP返回码</b></font>
     * @param httpreturncode HTTP返回码  
     */
    public void setHttpreturncode(int httpreturncode) {
        this.httpreturncode = httpreturncode;
    }

    /**
     * <br />获取 <font color="red"><b>加载类型<b/></font>
     * @return contenttype 加载类型
     */
    public String getContenttype() {
        return contenttype;
    }

    /**  
     * <br />设置 <font color='#333399'><b>加载类型</b></font>
     * @param contenttype 加载类型  
     */
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    /**
     * <br />获取 <font color="red"><b>URL加载时延<b/></font>
     * @return pageload URL加载时延
     */
    public float getPageload() {
        return pageload;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URL加载时延</b></font>
     * @param pageload URL加载时延  
     */
    public void setPageload(float pageload) {
        this.pageload = pageload;
    }

    /**
     * <br />获取 <font color="red"><b>响应时延<b/></font>
     * @return appllatency 响应时延
     */
    public float getAppllatency() {
        return appllatency;
    }

    /**  
     * <br />设置 <font color='#333399'><b>响应时延</b></font>
     * @param appllatency 响应时延  
     */
    public void setAppllatency(float appllatency) {
        this.appllatency = appllatency;
    }

    /**
     * <br />获取 <font color="red"><b>源客户端<b/></font>
     * @return forward 源客户端
     */
    public String getForward() {
        return forward;
    }

    /**  
     * <br />设置 <font color='#333399'><b>源客户端</b></font>
     * @param forward 源客户端  
     */
    public void setForward(String forward) {
        this.forward = forward;
    }

    /**
     * <br />获取 <font color="red"><b>VLANID<b/></font>
     * @return vlanId VLANID
     */
    public int getVlanId() {
        return vlanId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>VLANID</b></font>
     * @param vlanId VLANID  
     */
    public void setVlanId(int vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * <br />获取 <font color="red"><b>请求报文<b/></font>
     * @return req 请求报文
     */
    public String getReq() {
        return req;
    }

    /**  
     * <br />设置 <font color='#333399'><b>请求报文</b></font>
     * @param req 请求报文  
     */
    public void setReq(String req) {
        this.req = req;
    }

    /**
     * <br />获取 <font color="red"><b>响应报文<b/></font>
     * @return res 响应报文
     */
    public String getRes() {
        return res;
    }

    /**  
     * <br />设置 <font color='#333399'><b>响应报文</b></font>
     * @param res 响应报文  
     */
    public void setRes(String res) {
        this.res = res;
    }
}
