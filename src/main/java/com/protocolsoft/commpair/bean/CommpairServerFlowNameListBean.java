/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairServerFlowNameListBean
 *创建人:chensq    创建时间:2018年4月26日
 */
package com.protocolsoft.commpair.bean;

import java.util.List;
 
/**
 * @ClassName: CommpairServerFlowNameListBean
 * @Description: 获取的服务器端指定文件夹下的文件名对象
 * @author chensq
 *
 */
public class CommpairServerFlowNameListBean {
     
    /**
     * @Fields fileName : 名称集合
     */
    private List<CommpairServerFlowNameItemBean> fileName;
 
    /**
     * @Fields finishFlag : 结束标识
     */
    private int finishFlag=0; //0:未结束  1：结束
    
    /**
     * @Fields nodataFlag : 有无数据标识
     */
    private int nodataFlag=0; //0:有数据  1：无数据
    
    /**
     * @return the fileName
     */
    public List<CommpairServerFlowNameItemBean> getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(List<CommpairServerFlowNameItemBean> fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the finishFlag
     */
    public int getFinishFlag() {
        return finishFlag;
    }
    /**
     * @param finishFlag the finishFlag to set
     */
    public void setFinishFlag(int finishFlag) {
        this.finishFlag = finishFlag;
    }
    /**
     * @return the nodataFlag
     */
    public int getNodataFlag() {
        return nodataFlag;
    }
    /**
     * @param nodataFlag the nodataFlag to set
     */
    public void setNodataFlag(int nodataFlag) {
        this.nodataFlag = nodataFlag;
    }
    
}
