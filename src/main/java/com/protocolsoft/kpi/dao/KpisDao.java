/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: KpisDao.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.kpi.bean.KpisBean;

/**
 * @ClassName: KpisDao
 * @Description: KPI DAO
 * @author www
 *
 */
@Repository
public interface KpisDao {
    
    /**
     * 
     * @Title: getAllKpis
     * @Description: 获取所有KPI信息
     * @return List<KpisBean>
     * @author www
     */
    @Select("select id, display_name name from ipm_kpis where id in (select k.id from (select kpi_id "
            + "from ipm_plot_option where module_id in (select id from ipm_authorize_module "
            + "where iscore = 1 and isopen = 1)) o join ipm_kpis k on k.id = o.kpi_id group by k.id)")
    List<KpisBean> getAllKpis();
    
    /**
     * 
     * @Title: getTopoKpis
     * @Description: 获取拓扑KPI数据
     * @return List<KpisBean>
     * @author WWW
     */
    @Select("select id, `name`, display_name displayName, unit from ipm_kpis where id in (1,2,3,4,5,6,9,10,11,12,13,19,20,21,22)")
    List<KpisBean> getTopoKpis();

    /**
     * 
     * @Title: getAllKpisInfo
     * @Description: 获取所有KPI信息(ak.id与name字段)
     * @return List<KpisBean>
     * @author chensq
     */
    @Select("select "
            +"ak.id AS id, "
            +"o.module_id AS moduleId, "
            +"k.name AS NAME  "
            +"FROM ipm_alarm_kpi ak  "
            +"LEFT JOIN ipm_kpis k ON k.id=ak.kpi_id "
            +"LEFT JOIN ipm_plot_option o ON  o.kpi_id =k.id  AND ak.kpi_id = o.kpi_id  "
            +"WHERE  "
            +"o.module_id =ak.module_id and "
            +"(o.number = 1  OR  (o.number = 0 AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325) "
            +"ORDER BY  "
            +"ak.id,o.module_id,k.name ")
    List<KpisBean> getAllKpisInfo();
    
    /**
     * 
     * @Title: getKpisById
     * @Description: 根据编号获取KPI信息
     * @param id 编号
     * @return KpisBean
     * @author www
     */
    @Select("select id, name, display_name displayName, unit from ipm_kpis where id = #{id}")
    KpisBean getKpisById(@Param("id") int id);
    
    /**
     * 
     * @Title: getKpisByEthernetTraffic
     * @Description: 根据kpi英文名获取KPI信息
     * @return KpisBean
     * @author chensq
     */
    @Select("select id, name, display_name displayName, unit from ipm_kpis where name ='ethernetTraffic' ")
    KpisBean getKpisByEthernetTraffic();    
    
    /**
     * 告警阈值设置使用
     * @Title: getKpisByIdModuleId
     * @Description: 根据编号获取KPI信息
     * @param id 编号
     * @param moduleId 模块id
     * @return KpisBean
     * @author www
     */
    @Select("SELECT "    
            + "k.id AS id,  "
            + "k.name AS NAME, "
            + "o.name AS displayName, "
            + "k.unit AS unit "
            + "FROM ipm_kpis k "
            + "LEFT JOIN ipm_plot_option o  ON o.kpi_id =k.id "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.kpi_id = o.kpi_id "
            + "WHERE "
            + "o.module_id = #{moduleId} AND "
            + "ak.module_id = #{moduleId} AND "
            + "k.id=#{id} AND "
            + "(o.number = 1  OR  (o.number = 0 AND ak.kpi_id=2)) AND o.id NOT IN(323,324,325)")
    KpisBean getKpisByIdModuleId(@Param("id") int id, @Param("moduleId") long moduleId);
}
