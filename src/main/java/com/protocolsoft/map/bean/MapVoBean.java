/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapVoBean
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: MapVoBean
 * @Description: 地图Vo
 * @author wangjianmin
 *
 */
public class MapVoBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 对应省份
     */
    private String regionCn;
    
    /**
     * 对应地市
     */
    private String cityCn;
    
    /**
     * 对应区县
     */
    private String district;
    
    /**
     * 对应省份或者市级的值
     */
    private Double value;
    
    /**
     *对应 地区 id 
     */
    private Integer id;
    
    /**
     * 排序的结果
     */
    private List<Map<String, String>> resultMap;

    /**
     * @return the regionCn
     */
    public String getRegionCn() {
        return regionCn;
    }

    /**
     * @param regionCn the regionCn to set
     */
    public void setRegionCn(String regionCn) {
        this.regionCn = regionCn;
    }

    /**
     * @return the cityCn
     */
    public String getCityCn() {
        return cityCn;
    }

    /**
     * @param cityCn the cityCn to set
     */
    public void setCityCn(String cityCn) {
        this.cityCn = cityCn;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>serialversionuid<b/></font>
     * @return serialversionuid serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * <br />获取 <font color="red"><b>resultMap<b/></font>
     * @return resultMap resultMap
     */
    public List<Map<String, String>> getResultMap() {
        return resultMap;
    }

    /**  
     * <br />设置 <font color='#333399'><b>resultMap</b></font>
     * @param resultMap resultMap  
     */
    public void setResultMap(List<Map<String, String>> resultMap) {
        this.resultMap = resultMap;
    }
}
