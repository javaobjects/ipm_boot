/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: DownloadFileBean.java
 *创建人: www    创建时间: 2017年12月14日
 */
package com.protocolsoft.system.bean;

/**
 * @ClassName: DownloadFileBean
 * @Description: 下载文件
 * @author www
 *
 */
public class DownloadFileBean {

    /**
     * 文件名称
     */
    private String name;
    
    /**
     * 类型
     */
    private int type;
    
    /**
     * 文件大小
     */
    private long size;
    
    /**
     * 文件
     */
    private long time;

    /**
     * <br />获取 <font color="red"><b>文件名称<b/></font>
     * @return name 文件名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>文件名称</b></font>
     * @param name 文件名称  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>类型<b/></font>
     * @return type 类型
     */
    public int getType() {
        return type;
    }

    /**  
     * <br />设置 <font color='#333399'><b>类型</b></font>
     * @param type 类型  
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * <br />获取 <font color="red"><b>文件大小<b/></font>
     * @return size 文件大小
     */
    public long getSize() {
        return size;
    }

    /**  
     * <br />设置 <font color='#333399'><b>文件大小</b></font>
     * @param size 文件大小  
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * <br />获取 <font color="red"><b>文件<b/></font>
     * @return time 文件
     */
    public long getTime() {
        return time;
    }

    /**  
     * <br />设置 <font color='#333399'><b>文件</b></font>
     * @param time 文件  
     */
    public void setTime(long time) {
        this.time = time;
    }
}
