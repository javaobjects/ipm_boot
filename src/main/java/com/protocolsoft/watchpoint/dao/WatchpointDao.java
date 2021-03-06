/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:WatchpointBeanMapper
 *创建人:wjm    创建时间:2017年9月1日
 */
package com.protocolsoft.watchpoint.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.watchpoint.bean.NetworkBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;

/**
 * 
 * @ClassName: WatchpointDao
 * @Description: 观察点Dao层
 * @author wangjianmin
 *
 */
@Repository
public interface WatchpointDao {
    
  
    /**
     * 
     * @Title: getWatchpointAll
     * @Description: 查询观察点信息
     * @return List<WatchpointBean>
     * @author wangjianmin
     */
    @Select("select a.id , a.name, b.name as nicName, a.vid, a.lid from "
            + "ipm_watchpoint a , ipm_nic b where a.did = b.id ") 
    List<WatchpointBean> getWatchpointAll();
    
    
    
    /**
     * 
     * @Title: getFindAll
     * @Description: 查询所有观察点信息
     * @return List<WatchpointBean>
     * @author wangjianmin
     */
    @Select("select id,name,did,vid,vxid,lid1,lid2,lid3,lid4,lid5,bandwidth,"
    		+ "ip,`port`,contacts,telephone,email,validterm,isLocal "
    		+ "from ipm_watchpoint")
    List<WatchpointBean> getFindAll();
    
   
    /**
     * 
     * @Title: getFindByIds
     * @Description: 查询单个 did 
     * @param ids   网卡ID
     * @return List<WatchpointBean>
     * @author wangjianmin
     */
    @Select("select id,name,did,vid,vxid,lid1,lid2,lid3,lid4,lid5,bandwidth,"
    		+ "ip,`port`,contacts,telephone,email,validterm,isLocal "
    		+ "from ipm_watchpoint where id in (${ids})")
    List<WatchpointBean> getFindByIds(@Param("ids") String ids);
    
    /**
     * 
     * @Title: getWatchpointById
     * @Description: 查询单个观察点
     * @param id  观察点业务ID
     * @return WatchpointBean
     * @author wangjianmin
     */
    @Select("select id,name,did,vid,vxid,lid1,lid2,lid3,lid4,lid5,bandwidth,"
    		+ "ip,`port`,contacts,telephone,email,validterm,isLocal "
    		+ "from ipm_watchpoint WHERE id=#{id} ")
    WatchpointBean getWatchpointById(@Param("id")int id);
    
    /**
     * 
     * @Title: getNetworkAll
     * @Description: 查询所有网卡信息
     * @return List<NetworkBean>
     * @author wangjianmin
     */
    @Select("select id,name,vid,vxid,lid1,lid2,lid3,lid4,lid5 from ipm_nic")
    List<NetworkBean> getNetworkAll();
    
    /**
     * 
     * @Title: getNetworkById
     * @Description: 查询一个网卡
     * @param id  网卡ID
     * @return List<NetworkBean>
     * @author wangjianmin
     */
    @Select("select id,name,vid,vxid,lid1,lid2,lid3,lid4,lid5 from ipm_nic where id =#{id}")
    List<NetworkBean> getNetworkById(@Param("id")String id);
   
    /**
     * 
     * @Title: delWatchpoin
     * @Description: 删除观察点
     * @param id   观察点业务Id
     * @return int
     * @author wangjianmin
     */
    @Delete("delete from ipm_watchpoint where id =#{id}")
    int delWatchpoin(@Param("id")int id);
    
    /**
     * 
     * @Title: addWatchpoint
     * @Description: 添加观察点
     * @param bean 观察点参数实体
     * @return int
     * @author wangjianmin
     */
    @Insert("insert into ipm_watchpoint(name,did,vid,vxid,lid1,lid2,lid3,lid4,lid5,bandwidth,ip,port,"
    		+ "contacts,telephone,email,validterm,isLocal) "
            + "values(#{name},#{did},#{vid},#{vxid},#{lid1},#{lid2},#{lid3},"
            + "#{lid4},#{lid5},#{bandwidth},#{ip},#{port},#{contacts},#{telephone},"
            + "#{email},#{validterm},#{isLocal})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addWatchpoint(WatchpointBean bean);
    
    
    /**
     * 
     * @Title: updWatchpoint
     * @Description: 修改一条观察点信息
     * @param bean 观察点参数实体
     * @return int
     * @author wangjianmin
     */
    @Update("update ipm_watchpoint set name=#{name},did=#{did},vid=#{vid},vxid=#{vxid},"
            + "lid1=#{lid1},lid2=#{lid2},lid3=#{lid3},"
            + "lid4=#{lid4},lid5=#{lid5},bandwidth=#{bandwidth},ip=#{ip},"
            + " port=#{port},contacts=#{contacts},telephone=#{telephone},"
            + " email=#{email}, validterm=#{validterm} where id=#{id}")
    int updWatchpoint(WatchpointBean bean);
    
    /**
     * 
     * @Title: addConfigure
     * @Description: 添加背景 
     * @param id   用户id
     * @param key   背景色的键
     * @param value 背景色的值
     * @return int
     * @author wangjianmin
     */
    @Insert("insert into ipm_user_configure (user_id,`key`,`value`) values(#{user_id},#{key},#{value})")
    int addConfigure (@Param("user_id") int id, @Param("key") String key, @Param("value") String value);
    
    /**
     * 
     * @Title: getWatchpointByName
     * @Description: 验证名称是否重复添加
     * @param name   观察点名称
     * @return boolean
     * @author wangjianmin
     */
    @Select("select count(1) from ipm_watchpoint where name = #{name}")
    boolean getWatchpointByName(@Param("name") String name);
    
    /**
     * 
     * @Title: getWatchpointInfo
     * @Description: 验证观察点除名称，id，带宽以外的是否重复
     * @param bean   接收添加观察点信息
     * @return boolean
     * @author wangjianmin
     */
    @Select("select count(1) from ipm_watchpoint where "
            + "did = #{did} AND vid =#{vid} AND vxid =#{vxid} "
            + "AND lid1 =#{lid1} AND lid2 =#{lid2} AND lid3 =#{lid3} AND lid4 =#{lid4} AND lid5 =#{lid5}")
    boolean getWatchpointInfo(WatchpointBean bean);
    
    /**
     * 
     * @Title: updateByName
     * @Description: 修改观察点名称
     * @param name 名称
     * @param id  id
     * @return boolean
     * @author wangjianmin
     */
    @Update("update ipm_watchpoint SET `name` =#{name} WHERE id=#{id}")
    boolean  updateByName(@Param("name") String name, @Param("id")int id);
    
    /**
     * 
     * @Title: updateByName
     * @Description: 修改观察点有效期
     * @param validterm 有效期
     * @param id  id
     * @return void
     * @author wangjianmin
     */
    @Update("update ipm_watchpoint SET validterm =#{validterm} WHERE id=#{id}")
    void updateValidterm(@Param("validterm") Integer validterm, @Param("id") int id);
    

    /**
     * 
     * @Title: getFindByIds
     * @Description: 查询本地授权
     * @return List<WatchpointBean>
     * @author wangjianmin
     */
    @Select("select id,validterm from ipm_watchpoint WHERE isLocal = 1")
    List<WatchpointBean> selectValidterm();
    
}
