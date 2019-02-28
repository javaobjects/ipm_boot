/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairIpLocationUtil
 *创建人:chensq    创建时间:2018年1月23日
 */
package com.protocolsoft.commpair.util;

import org.apache.commons.lang3.StringUtils;

import com.protocolsoft.commpair.bean.CommpairBean;
 
/**
 * @ClassName: CommpairIpLocationUtil
 * @Description: 地址库字段
 * @author chensq
 *
 */
public class CommpairIpLocationUtil {
        
    /**
     * @Title: getMainMergeIpLocationColumn
     * @Description: 获取主sql的地址库列(Merge)
     * @param commpair
     * @return String
     * @author chensq
     */
    public static String getMainMergeIpLocationColumn(CommpairBean commpair){
        StringBuffer buf=new StringBuffer();
        if (commpair.getOpenIpLocationFlag()!=0) {
            buf.append("AVG(serverLocIdColumn) AS serverLocIdColumn, ");
            buf.append("AVG(clientLocIdColumn) AS clientLocIdColumn, ");
            buf.append("serverLocId AS serverLocId, ");
            buf.append("clientLocId AS clientLocId, ");
            buf.append("un.country as countryCn, ");
            buf.append("un.region_cn as regionCn, ");
            buf.append("un.city_cn as cityCn, ");
            buf.append("un.isp_cn as ispCn, ");
        }        
        return buf.toString();
    }
           
    /**
     * @Title: getMainMergeMapMatchCommpair
     * @Description: 主sql的地图匹配地址库(Merge)
     * @param commpair
     * @param alias
     * @return String
     * @author chensq
     */
    public static String getMainMergeMapMatchCommpair(CommpairBean commpair, String alias){
        StringBuffer buf=new StringBuffer();
        if (commpair.getOpenIpLocationFlag()!=0) {
            buf.append("where 1=1 ");
            if (StringUtils.isNotEmpty(commpair.getContinent())) {//洲际
                buf.append("and "+alias+"continent REGEXP '^"+commpair.getContinent()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getCountryCn())) {//国家
                buf.append("and "+alias+"country REGEXP '^"+commpair.getCountryCn()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getRegionCn())) {//省份
                buf.append("and "+alias+"region_cn REGEXP '^"+commpair.getRegionCn()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getCityCn())) {//城市
                buf.append("and "+alias+"city_cn REGEXP '^"+commpair.getCityCn()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getIspCn())) {//运营商
                buf.append("and "+alias+"isp_cn REGEXP '^"+commpair.getIspCn()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getCountryEn())) {//国家英文
                buf.append("and "+alias+"country_english REGEXP '^"+commpair.getCountryEn()+"' ");
            }
            if (StringUtils.isNotEmpty(commpair.getCountryCode())) {//国家简写
                buf.append("and "+alias+"country_code REGEXP '^"+commpair.getCountryCode()+"' ");
            }
        }
        return buf.toString();
    }   
    
    /**
     * @Title: getSubDetailIpLocationColumn
     * @Description: 获取子sql的地址库列(Detail)
     * @param commpair
     * @return String
     * @author chensq
     */
    public static String getSubDetailIpLocationColumn(CommpairBean commpair){
        StringBuffer buf=new StringBuffer();
        if (commpair.getOpenIpLocationFlag()!=0) {
            buf.append("l.serverLocId AS serverLocId, ");
            buf.append("l.clientLocId AS clientLocId, ");
            buf.append("loc.country AS countryCn, ");
            buf.append("loc.region_cn AS regionCn, ");
            buf.append("loc.city_cn AS cityCn, ");
            buf.append("loc.isp_cn AS ispCn, ");            
        }
        return buf.toString();
    }
    
    /**
     * @Title: getMainDetailIpLocationLeftJoin
     * @Description: 获取主sql的地址库left join(Detail)
     * @param commpair
     * @param alias
     * @return String
     * @author chensq
     */
    public static String getMainDetailIpLocationLeftJoin(CommpairBean commpair, String alias){
        StringBuffer buf=new StringBuffer();
        if (commpair.getOpenIpLocationFlag()!=0) {
            buf.append("LEFT JOIN ipm_ip_location_cn loc ON loc.loc_id_cn="+alias+"clientLocId ");
        }
        return buf.toString();
    }
    
}
