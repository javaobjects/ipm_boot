/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserListColumn
 *创建人:long    创建时间:2017年9月14日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.UserListColumnBean;
import com.protocolsoft.user.provider.UserListColumnDaoProvider;

/**
 * UserListColumnDao
 * 2017年9月14日 下午6:04:50
 * @author long
 * @version
 * @see
 */
@Repository
public interface UserListColumnDao {
    
    /**
     * 获取源列字段信息
     * 2017年9月14日 下午6:13:14
     * @param
     * @return List<ListColumn>
     * @exception 
     * @see
     */
    @Select(" select id,type_id as typeId,columnen,columnzh,calcul,isDefault from ipm_list_column "
            + " where isdefault=#{isDefault} ")
    List<ListColumnBean> getListColumnList(@Param("isDefault") int isDefault);
    
    /**
     * 添加用户列字段信息
     * 2017年9月15日 上午9:59:32
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Insert("insert into ipm_user_list_column (user_id,column_id) values (#{userId}, #{columnId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUserListColumn(UserListColumnBean userListColumnBean);
    
    /**
     * 按照列表类型id查询全部列表字段
     * 2017年9月6日 下午3:51:58
     * @param typeId
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    @Select("SELECT t1.id,t1.type_id as typeId,t1.columnen,t1.columnzh,t1.calcul,t1.isdefault "
            + "from ipm_list_column t1 where type_id = #{typeId}")
    List<ListColumnBean> selectListColumnByTypeId(Integer typeId);
    
    /**
     * 按照列表类型id查询全部列表字段
     * 2017年9月6日 下午3:51:58
     * @param typeId
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    @Select("SELECT t1.id,t1.type_id as typeId,t1.columnen,t1.columnzh,t1.calcul,t1.isdefault "
            + "from ipm_list_column t1 where type_id = #{typeId} and isdefault = 1")
    List<ListColumnBean> selectDefListColumnByTypeId(Integer typeId);
    
    /**
     * 查询用户配置对应的列表字段
     * 2017年9月15日 下午4:35:34
     * @param userId
     * @param typeId
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    @Select("select t1.id,t1.type_id as typeId,t1.columnen,t1.columnzh,t1.calcul,t1.isdefault from "
            + "ipm_list_column t1,ipm_user_list_column t2 where "
            + "t2.user_id = #{userId} and t1.id = t2.column_id and t1.type_id = #{typeId}")
    List<ListColumnBean> selectUserListColumnByUserIdAndTypeId(@Param("userId") int userId , 
            @Param("typeId") int typeId);
    
    /**
     * 批量新增用户配置对应的列表字段
     * 2017年9月18日 下午5:49:51
     * @param List<UserListColumnBean>
     * @exception 
     * @see
     */
    @InsertProvider(type = UserListColumnDaoProvider.class, method = "batchInsert") 
    void batchInsertUserListColumn(@Param("beans") List<UserListColumnBean> beans);
    
    /**
     * 通过userId和columnId删除
     * 2017年9月15日 下午6:11:28
     * @param bean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_user_list_column where user_id= #{userId} and column_id = #{columnId}")
    void deleteUserListByUserIdAndColumnId(UserListColumnBean bean);
    
    /**
     * 通过userId与typeId删除列字段
     * 2017年9月19日 上午10:33:10
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Delete("delete from ipm_user_list_column  where user_id= #{userId} "
            + "and column_id in (select id from ipm_list_column  where type_id= #{typeId}) ")
    int delUserListByUserIdColumnId(@Param("userId") int userId , @Param("typeId") int typeId);
    
    /**
     * 通过userId和columnId判断是否存在column字段
     * 2017年9月20日 上午9:48:07
     * @param
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    @Select("select count(1) from ipm_user_list_column  where user_id = #{userId} and column_id = #{columnId}")
    int getUserListColumnByUserColumnId(@Param("userId") int userId, @Param("columnId") int columnId);
    
    /**
     * 通过userId删除column数据
     * 2017年9月20日 下午1:42:21
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Delete("delete from ipm_user_list_column  where user_id= #{userId}  ")
    int delUserListByUserId(@Param("userId") int userId);
}
