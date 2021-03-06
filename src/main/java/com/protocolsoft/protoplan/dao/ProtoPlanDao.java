/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ProtoPlanDao
 *创建人:chensq    创建时间:2017年10月18日
 */
package com.protocolsoft.protoplan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.protoplan.bean.ProtoPlanBean;

/**
 * @ClassName: ProtoPlanDao
 * @Description: ProtoPlan DAO
 * @author chensq
 *
 */
@Repository
public interface ProtoPlanDao {

    /**
     * 
     * @Title: getProtoPlanList
     * @Description: 返回符合协议集合
     * @return List<ProtoPlanBean>
     * @author chensq
     */
    @Select("SELECT "
            + "id as id,"
            + "appid as appId,"
            + "ip as ip,"
            + "`port` as port,"
            + "proto as proto,"
            + "`name` as name,"
            + "display_name as displayName "
            + "FROM ipm_proto_plan "
            + "GROUP BY `name` "
            + "ORDER BY id ")
    List<ProtoPlanBean> getProtoPlanList();

    /**
     * 
     * @Title: getProtoPlanNotInList
     * @param ids
     * @Description: 返回符合协议集合(not in)
     * @return List<ProtoPlanBean>
     * @author chensq
     */
    @Select("SELECT "
            + "id as id,"
            + "appid as appId,"
            + "ip as ip,"
            + "`port` as port,"
            + "proto as proto,"
            + "`name` as name,"
            + "display_name as displayName "
            + "FROM ipm_proto_plan "
            + "WHERE id NOT IN(${ids}) "
            + "GROUP BY `name` "
            + "ORDER BY id ")
    List<ProtoPlanBean> getProtoPlanNotInList(@Param("ids") String  ids);
    
    /**
     * 
     * @Title: getProtoPlanBeanByName
     * @Description: 返回符合协议bean
     * @return ProtoPlanBean
     * @author chensq
     */
    @Select("select id,"
            + "`port`, "
            + "`name` "
            + "from "
            + "ipm_proto_plan "
            + "where "
            + "`name` = #{name}  group by `name` order by id")
    ProtoPlanBean getProtoPlanBeanByName(ProtoPlanBean bean);
    
    /**
     * 
     * @Title: getProtoPlanBeanById
     * @Description: 返回符合协议bean
     * @return ProtoPlanBean
     * @author chensq
     */
    @Select("select id,"
            + "`port`, "
            + "`name` "
            + "from "
            + "ipm_proto_plan "
            + "where "
            + "`id` = #{id}  group by `name` order by id")
    ProtoPlanBean getProtoPlanBeanById(ProtoPlanBean bean);
    
    
}
