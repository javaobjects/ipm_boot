/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetTitleBean
 *创建人:chensq    创建时间:2017年11月9日
 */
package com.protocolsoft.alarm.bean;
 
/**
 * @ClassName: AlarmSetTitleBean
 * @Description: 告警设置title,该内容为动态。根据告警级别循环
 * @author chensq
 *
 */
public class AlarmSetTitleBean {
    /**
     * @Fields id : id
     */
    private long  id;
    /**
     * @Fields nameen : 标题英文名
     */
    private String nameen;    
    /**
     * @Fields namezh : 标题中文名
     */
    private String namezh;
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the nameen
     */
    public String getNameen() {
        return nameen;
    }
    /**
     * @param nameen the nameen to set
     */
    public void setNameen(String nameen) {
        this.nameen = nameen;
    }
    /**
     * @return the namezh
     */
    public String getNamezh() {
        return namezh;
    }
    /**
     * @param namezh the namezh to set
     */
    public void setNamezh(String namezh) {
        this.namezh = namezh;
    }

    
}
