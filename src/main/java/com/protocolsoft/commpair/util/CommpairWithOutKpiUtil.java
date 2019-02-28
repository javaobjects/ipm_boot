/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommpairWithOutKpiUtil.java
 *创建人: chensq    创建时间: 2018年7月24日
 */
package com.protocolsoft.commpair.util;

/**
 * @ClassName: CommpairWithOutKpiUtil
 * @Description: 通信对某些kpi排除排序的选项
 * @author chensq
 *
 */
public class CommpairWithOutKpiUtil {
    /**
     * @Fields withOutKpiArray :  除去的kpi
     */
    String [] withOutKpiArray =new String[]{
        "bandWidthRatio",
        "arpTraffic",
        "arpPkts", 
        "unKnowSerTraffic", 
        "unKnowCliTraffic"};
    
    /**
     * @Title: withOutKpiTypeId
     * @Description: 返回是否找到类型
     * @param kpiInfo
     * @return String
     * @author chensq
     */
    public int withOutKpiTypeId(String kpiInfo){
        int typeId=0;
        for(int x=0; x<withOutKpiArray.length; x++){
            String tempStr= withOutKpiArray[x];
            if (kpiInfo.toUpperCase().indexOf(tempStr.toUpperCase()) >= 0){
                typeId =1;
            }
        }
        return typeId;
    }
    
}
