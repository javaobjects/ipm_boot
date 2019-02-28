/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserAuthorizeDao
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.JurisModuleBean;

/**
 * AuthorizeJurisDao
 * 2017年9月1日 下午5:05:41
 * @author long
 * @version
 * @see
 */
@Repository
public interface AuthorizeJurisDao {
  
    /**
     * 
     * @Title: getUserAuthorizeBean
     * @Description: 根据用户id查询当前用户的权限
     * @param userId 用户id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @Select("select id,user_id,module_id,center_id,app_id from ipm_authorize_juris "
            + " where user_id=#{userId}")
    List<AuthorizeJurisBean> getUserAuthorizeBean(@Param("userId") int userId);
 
    /**
     * 
     * @Title: getUserAuthorizeByModuleId
     * @Description:根据userId moduleId获得授权
     * @param userId  用户id
     * @param moduleId 模块id
     * @return AuthorizeJurisBean
     * @author wangjianmin
     */
    @Select("select id,user_id as userId,module_id as moduleId,stauts as status from ipm_authorize_juris "
            + " where user_id=#{userId} and module_id=#{moduleId} ")
    AuthorizeJurisBean getUserAuthorizeByModuleId(@Param("userId") int userId, @Param("moduleId") int moduleId);

    /**
     * 
     * @Title: addUserAuthorize
     * @Description: 添加授权
     * @param userAuthorizeBean 接收添加参数
     * @return int
     * @author wangjianmin
     */
    @Insert("insert into ipm_authorize_juris (user_id,module_id,center_id,app_id) "
            + "values (#{userId}, #{moduleId},#{centerId},#{appId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addUserAuthorize(AuthorizeJurisBean userAuthorizeBean);

    /**
     * 
     * @Title: deleteUserAuthorize
     * @Description: 根据业务ID 删除
     * @param userAuthorizeBean void  接收删除参数
     * @author wangjianmin
     */
    @Delete("delete from ipm_authorize_juris where app_id=#{appId} and user_id=#{userId} "
            + "and center_id=#{centerId} and module_id=#{moduleId}")
    void deleteUserAuthorize(AuthorizeJurisBean userAuthorizeBean);

    /**
     * 
     * @Title: deleteCenter
     * @Description: 根据远端删除，删掉权限
     * @param userAuthorizeBean void 接收删除远端并删除权限参数
     * @author wangjianmin
     */
    @Delete("delete from ipm_authorize_juris where  center_id=#{centerId}")
    void deleteCenter(AuthorizeJurisBean userAuthorizeBean);
    /**
     * 
     * @Title: selectModuleId
     * @Description: 获取授权模块编号
     * @param user_id  用户id
     * @param module_id 模块id
     * @return List<AuthorizeJurisBean>
     * @author www
     */
    @Select("select * from ipm_authorize_juris  where  user_id =#{user_id} and module_id=#{module_id}")
    List<AuthorizeJurisBean> selectModuleId(@Param("user_id") int user_id, @Param("module_id") int module_id);
  
    /**
     * 
     * @Title: getJurisModuleList
     * @Description: 查询源授权信息
     * @return List<JurisModuleBean>
     * @author wangjianmin
     */
    @Select("select j.id, j.nameen, j.namezh,j.`order` from ipm_juris_module j where id > 100"
            + " union ALL SELECT j.id, j.nameen, j.namezh,j.`order` FROM ipm_authorize_module m"
            + " JOIN  ipm_juris_module j ON j.id=m.id where iscore = true and isopen = true order by `order`")
    List<JurisModuleBean> getJurisModuleList();
   
    /**
     * 
     * @Title: delUserAuthorizeByUserId
     * @Description: 根据userId删除授权信息
     * @param userId 用户id
     * @return boolean
     * @author wangjianmin
     */
    @Delete("delete from ipm_authorize_juris where user_id=#{userId}")
    boolean delUserAuthorizeByUserId(int userId);

    /**
     * 
     * @Title: updateUserAuthorize
     * @Description:更新授权信息排序
     * @param userAuthorizeBean 接收修改排序参数
     * @return int
     * @author wangjianmin
     */
    @Update("update ipm_authorize_juris set stauts=#{status} where id= #{id}")
    int updateUserAuthorize(AuthorizeJurisBean userAuthorizeBean);
 
    /**
     * 
     * @Title: updateUserAuthorizeByID
     * @Description: 更新授权信息
     * @param userAuthorizeBean 接收修改参数
     * @return int
     * @author wangjianmin
     */
    @Update("UPDATE ipm_authorize_juris SET  `order`=#{order} where app_id=#{appId} "
            + " AND module_id=#{moduleId} and user_id =#{userId}")
    int updateUserAuthorizeByID(AuthorizeJurisBean userAuthorizeBean);
    
    /**
     * 
     * @Title: updateUserAdminAuthorizeByID
     * @Description: 系统管理员排序
     * @param userAuthorizeBean void
     * @author wangjianmin
     */
    @Update("UPDATE ipm_authorize_juris SET  `order`=#{order} where app_id=#{appId}"
            + " AND module_id=#{moduleId} ")
    void updateUserAdminAuthorizeByID(AuthorizeJurisBean userAuthorizeBean);

    /**
     * 
     * @Title: getUserAuthorizeByModuleById
     * @Description: 根据用户ID，模块id,centerID 返回业务ID 
     * @param userId 用户id
     * @param moduleId 模块id
     * @param centerId 远端id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @Select("SELECT id,user_id AS userId,module_id AS moduleId,center_id AS centerId,app_id AS appId "
            + " FROM   ipm_authorize_juris  WHERE user_id=#{userId}  and module_id=#{moduleId}"
            + " and center_id=#{centerId}")
    List<AuthorizeJurisBean> getUserAuthorizeByModuleById(@Param("userId") int userId, @Param("moduleId") int moduleId,
            @Param("centerId") int centerId);

    /**
     * 
     * @Title: getUserAuthorizeByModuleList
     * @Description: 根据userId moduleId获得授权
     * @param userId   用户id
     * @param moduleId 模块id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @Select("select id,user_id AS userId,module_id AS moduleId,center_id AS centerId,app_id AS appId "
            + ",`order` as orderId from ipm_authorize_juris  where user_id=#{userId} and module_id=#{moduleId} "
            + "order by orderId asc")
    List<AuthorizeJurisBean> getUserAuthorizeByModuleList(@Param("userId") int userId, @Param("moduleId") int moduleId);

    
    /**
     * 
     * @Title: getUserAdminAuthorizeByModuleList
     * @Description: 根据驾驶舱id和模块id 查询排序信息
     * @param appId   驾驶舱id
     * @param moduleId 模块id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @Select("select id,user_id AS userId,module_id AS moduleId,center_id AS centerId,app_id AS appId "
            + ",`order` as orderId from ipm_authorize_juris  where app_id=#{appId} and module_id=#{moduleId} "
            + "order by orderId asc")
    List<AuthorizeJurisBean> getUserAdminAuthorizeByModuleList(@Param("appId") int appId, @Param("moduleId") int moduleId);
    
    /**
     * 
     * @Title: getUserAdminAuthorizeModuleList
     * @Description: 根据模块id 查询排序信息
     * @param moduleId 模块id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @Select("select id,user_id AS userId,module_id AS moduleId,center_id AS centerId,app_id AS appId "
            + ",`order` as orderId from ipm_authorize_juris  where  module_id=107 and app_id !=1 "
            + "order by orderId asc")
    List<AuthorizeJurisBean> getUserAdminAuthorizeModuleList(@Param("moduleId") int moduleId);

}
