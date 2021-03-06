/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapProvider
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.map.bean.CommpairTablesBean;
import com.protocolsoft.map.bean.MapQoBean;

/**
 * 
 * @ClassName: MapDaoProvider
 * @Description: 地图动态SQL处理
 * @author wangjianmin
 *
 */
public class MapDaoProvider {

    
   /**
    * 
    * @Title: selectCommpairDataToSql
    * @Description: 返回查询通信对的sql
    * @param map 集合
    * @return String
    * @author wangjianmin
    */
    public String selectCommpairDataToSql(Map<String, Object> map){
        @SuppressWarnings("unchecked")
        List<CommpairTablesBean> commpairTablesBeans = (List<CommpairTablesBean>) map.get("beans");  
        
        MapQoBean mapQoBean = (MapQoBean) map.get("mapQoBean");
        
        StringBuffer sb = new StringBuffer();  
        for (CommpairTablesBean commpairTablesBean : commpairTablesBeans) {
            if (commpairTablesBean.getWpid() == mapQoBean.getWatchpointId()) {
                if (mapQoBean.getColumnName().equals("ethernetTraffic value")){
                     
                    if (mapQoBean.getTempColumnName().equals("tempT.regionCn")){
                         
                        sb.append(" SELECT tempT.regionCn,sum(tempT.`value`) `value`,are.id FROM ");
                        
                    } else if (mapQoBean.getTempColumnName().equals("tempT.cityCn")){
                         
                        sb.append(" SELECT tempT.cityCn,sum(tempT.`value`) `value`,are.id FROM ");
                        
                    } else if (mapQoBean.getTempColumnName().equals("tempT.district")){
                         
                        sb.append(" SELECT tempT.district,sum(tempT.`value`) `value`,are.id FROM ");
                    }
                     
                } else if (mapQoBean.getColumnName().equals("sessionNum value")){
                     
                    if (mapQoBean.getTempColumnName().equals("tempT.regionCn")){
                         
                        sb.append(" SELECT tempT.regionCn " 
                                + ",count(tempT.regionCn) `value`,are.id FROM ");
                         
                    } else if (mapQoBean.getTempColumnName().equals("tempT.cityCn")){
                          
                        sb.append(" SELECT tempT.cityCn" 
                                 + ",count(tempT.cityCn) `value` FROM ");
                         
                    } else if (mapQoBean.getTempColumnName().equals("tempT.district")){
                          
                        sb.append(" SELECT tempT.district" 
                                  + ",count(tempT.district) `value` FROM ");
                    }
                                         
                     
                } else {
                    
                    if (mapQoBean.getTempColumnName().equals("tempT.regionCn")){
                      
                        sb.append(" SELECT tempT.regionCn " 
                                + ",sum(tempT.`value`) / count(tempT.regionCn) `value` FROM ");
                         
                    } else if (mapQoBean.getTempColumnName().equals("tempT.cityCn")){
                          
                        sb.append(" SELECT tempT.cityCn " 
                                + ",sum(tempT.`value`) / count(tempT.cityCn) `value` FROM ");
                         
                    } else if (mapQoBean.getTempColumnName().equals("tempT.district")){
                          
                        sb.append(" SELECT tempT.district " 
                                + ",sum(tempT.`value`) / count(tempT.district) `value` FROM ");
                    }
                }
                sb.append(" ( ");
                sb.append("SELECT clientLocId");
                Object [] array = new Object[5];
                array[0] = mapQoBean.getColumnName();
                array[1] = mapQoBean.getStartTime().toString();
                array[2] = mapQoBean.getEndTime().toString();
                if(mapQoBean.getIpStr() != null) {
                	 array[3] = mapQoBean.getIpStr();
                }
                array[4] = mapQoBean.getRegionCn();
                if (null != mapQoBean){
                    if (null != mapQoBean.getColumnName() && !"".equals(mapQoBean.getColumnName())) {
                        sb.append(",");
                        String result1 = MessageFormat.format("{0}", array);
                        sb.append(result1);
                    }
                }
                sb.append(",loc_id_cn , region_cn regionCn , city_cn cityCn, district district ,"
                         + "ip_start_num,ip_end_num from commpair");
                sb.append("_" + commpairTablesBean.getWpid());
                sb.append("_" + commpairTablesBean.getGranularity());
                sb.append("_log");
                sb.append("_" + commpairTablesBean.getTableId()+" com ");
                sb.append(" LEFT JOIN  ipm_ip_location_cn on com.clientLocId = loc_id_cn");
                String result2 = MessageFormat.format(" WHERE snaptime >= {1} and snaptime <= {2}", array);
                sb.append(result2);
                if(mapQoBean.getIpStr() != null) {
                	 String result3 = MessageFormat.format(" AND ({3}) AND region_cn != '''' ", array);
                     sb.append(result3);
                }else {
                	String result3 = MessageFormat.format(" AND region_cn != '''' ", array);
                    sb.append(result3);
                }
               
                 
                if (null != mapQoBean.getRegionCn() && !"".equals(mapQoBean.getRegionCn())) {
                    String result4 = MessageFormat.format(" AND region_cn = ''{4}''", array);
                    sb.append(result4);
                }
                 
                if ("tempT.district".equals(mapQoBean.getTempColumnName())) {
                    sb.append(" AND district != ''");
                } else if ("tempT.cityCn".equals(mapQoBean.getTempColumnName())) {
                    sb.append(" AND city_cn != ''");
                }
                 
                sb.append("  ) tempT "); 
                
                if (mapQoBean.getTempColumnName().equals("tempT.regionCn")){
                    sb.append(" LEFT JOIN ipm_area_dict  are ON  tempT.regionCn = are.name_cn ");
                    sb.append(" GROUP BY tempT.regionCn ");
                     
                } else if (mapQoBean.getTempColumnName().equals("tempT.cityCn")){
                    sb.append(" LEFT JOIN ipm_area_dict  are ON  tempT.cityCn = are.name_cn "); 
                    sb.append(" GROUP BY tempT.cityCn ");
                     
                } else if (mapQoBean.getTempColumnName().equals("tempT.district")){
                    sb.append(" LEFT JOIN ipm_area_dict  are ON  tempT.district = are.name_cn ");
                    sb.append(" GROUP BY tempT.district ");
                }
               
                sb.append(" UNION ALL");
            }             
        }
        String result = sb.toString().substring(0, sb.toString().lastIndexOf("UNION ALL"));
        return result;
    }
}
