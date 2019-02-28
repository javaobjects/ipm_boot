/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmMsgFixed
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.bean;

/**
 * 
 * @ClassName: IpmMsgFixedBean
 * @Description: 添加报文固有信息
 * @author wangjianmin
 *
 */
public class IpmMsgFixedBean {
    /**
     * 编号
     */
    private int id;
    
    /**
     * HTTP业务ID
     */
    private int appId;
    
    /**
     *客户端ip 端口 
     */
    private String clientIpport;
    
    /**
     * 交易返回码
     */
    private String statusCode;
    
    /**
     * 成功返回码
     */
    private String successCode;
    
    /**
     * 失败放回码
     */
    private String  failedCode;

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
     * @return the appId
     */
    public int getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the failedCode
     */
    public String getFailedCode() {
        return failedCode;
    }

    /**
     * @param failedCode the failedCode to set
     */
    public void setFailedCode(String failedCode) {
        this.failedCode = failedCode;
    }


    /**
     * @return the successCode
     */
    public String getSuccessCode() {
        return successCode;
    }

    /**
     * @param successCode the successCode to set
     */
    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    /**
     * @return the clientIpport
     */
    public String getClientIpport() {
        return clientIpport;
    }

    /**
     * @param clientIpport the clientIpport to set
     */
    public void setClientIpport(String clientIpport) {
        this.clientIpport = clientIpport;
    }

}
