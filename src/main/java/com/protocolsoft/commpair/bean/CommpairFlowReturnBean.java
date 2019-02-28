/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFlowReturnBean
 *创建人:chensq    创建时间:2017年12月8日
 */
package com.protocolsoft.commpair.bean;
 
/**
 * @ClassName: CommpairFlowReturnBean
 * @Description: 流量下载返回
 * @author chensq
 *
 */
public class CommpairFlowReturnBean {
    
    /**
     * @Fields typeId : 返回类型
     */
    private String typeId;
    
    /**
     * @Fields fileName : 返回下载名称
     */
    private String fileName;
    
    /**
     * @Fields folderName : 文件夹名称
     */
    private String folderName;
    
    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }
    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the folderName
     */
    public String getFolderName() {
        return folderName;
    }
    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    
}
