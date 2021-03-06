/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairTableNameUtil
 *创建人:chensq    创建时间:2018年1月17日
 */
package com.protocolsoft.commpair.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.dao.CommpairDao;


/**
 * @ClassName: CommpairTableNameUtil
 * @Description:  获取通信对名称的工具类
 * @author chensq
 *
 */
public class CommpairTableNameUtil {
   
    /**
     * @Title: commpairTables4WP
     * @Description: 通信对对象
     * @param commpair
     * @param watchpointId
     * @param commpairDao
     * @return List<String>
     * @author chensq
     */
    public static List<String> commpairTables4WP(CommpairBean commpair,
            int watchpointId,
            CommpairDao commpairDao){
        //记录表
        StringBuffer timeBucketTable=new StringBuffer();
        timeBucketTable.append("commpair");
        timeBucketTable.append("_");
        timeBucketTable.append(commpair.getLidu());
        timeBucketTable.append("_");
        timeBucketTable.append("log");
        timeBucketTable.append("_");
        timeBucketTable.append("tables");
        //前缀
        StringBuffer tablePrefix=new StringBuffer();
        tablePrefix.append("commpair");
        tablePrefix.append("_");
        tablePrefix.append(watchpointId);
        tablePrefix.append("_");
        tablePrefix.append(commpair.getLidu());
        tablePrefix.append("_");
        tablePrefix.append("log");
        tablePrefix.append("_");

        //时间map
        Map<String, Long> timeMap=new HashMap<String, Long>();
        timeMap.put("starttime", commpair.getStarttime());
        timeMap.put("endtime", commpair.getEndtime());

        //所有符合条件的表名的后缀
        List<String> commpairTables=getCommpairTables(
                tablePrefix.toString(),
                timeBucketTable.toString(), 
                timeMap,
                watchpointId,
                commpairDao);
        
        return commpairTables;
    }
    
    /**
     * 
     * @Title: getCommpairTables
     * @Description: 获取通信对中间表
     * @param tablePrefix 中间表前缀
     * @param timeBucketTable 中间表名称
     * @param timeMap key:starttime/endtime
     * @param watchpointId 观察点id
     * @param commpairDao dao
     * @return List<String>
     * @author chensq
     */
    public static List<String> getCommpairTables(String  tablePrefix,
                                          String timeBucketTable,
                                          Map<String, Long> timeMap,
                                          int watchpointId,
                                          CommpairDao commpairDao){
        List<String> ret = null;
        try { 
            ret = commpairDao.getCommpairTables(tablePrefix,  
                    timeBucketTable,  
                    timeMap.get("starttime"),  
                    timeMap.get("endtime"), 
                    watchpointId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
}
