/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmAlgorithmBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;
 
/**
 * @ClassName: AlarmAlgorithmBean
 * @Description: 告警算法实体类
 * @author chensq
 *
 */
public class AlarmAlgorithmBean {
    
    /**
     * @Fields id : id
     */
    private long id;
    /**
     * @Fields nameen : nameen
     */
    private String nameen;
    /**
     * @Fields namezh : 中文名称
     */
    private String namezh;
    /**
     * @Fields algorithm : 算法id
     */
    private int algorithm;
    /**
     * @Fields algorithminfo : 算法内容
     */
    private String algorithminfo;
    /**
     * @Fields descrption : 算法描述
     */
    private String descrption;
    
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
    /**
     * @return the algorithm
     */
    public int getAlgorithm() {
        return algorithm;
    }
    /**
     * @param algorithm the algorithm to set
     */
    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }
    /**
     * @return the algorithminfo
     */
    public String getAlgorithminfo() {
        return algorithminfo;
    }
    /**
     * @param algorithminfo the algorithminfo to set
     */
    public void setAlgorithminfo(String algorithminfo) {
        this.algorithminfo = algorithminfo;
    }
    /**
     * @return the descrption
     */
    public String getDescrption() {
        return descrption;
    }
    /**
     * @param descrption the descrption to set
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
      
}
