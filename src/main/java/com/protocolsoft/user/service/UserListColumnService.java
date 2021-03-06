/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserListColumnService
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.user.service;

import java.util.List;
import java.util.Map;

import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.UserListColumnBean;

/**
 * UserListColumnService
 * 2017年9月15日 上午9:36:21
 * @author long
 * @version
 * @see
 */
public interface UserListColumnService {
    /**
     * 获取源列的字段信息
     * 2017年9月15日 上午9:37:25
     * @param
     * @return List<ListColumn>
     * @exception 
     * @see
     */
    List<ListColumnBean> getListColumnList(int isDefault);
    
    /**
     * 添加用户列字段信息
     * 2017年9月15日 上午9:46:00
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> addUserListColumn(int userId, List<ListColumnBean>listColumnList);
    
    /**
     * 获取所有列表字段
     * 2017年9月15日 下午4:41:36
     * @param typeId
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    List<ListColumnBean> getListColumn(Integer typeId);
    
    /**
     * 获取用户配置对应列表字段
     * 2017年9月15日 下午4:42:13
     * @param userId
     * @param typeId
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    List<ListColumnBean> getUserListColumn(Integer userId, Integer typeId);

    /**
     * 保存用户配置对应列表字段
     * 2017年9月15日 下午6:00:57
     * @param userId
     * @param columnIds
     * @param typeId
     * @return String
     * @exception 
     * @see
     */
/*    String saveUserListColumn(Integer userId, String columnIds, Integer typeId);*/
    
    /**
     * 保存用户配置对应列表字段
     * 2017年9月25日 下午4:01:57
     * @param bean
     * @return String
     * @exception 
     * @see
     */
    String checkedUserListColumn(UserListColumnBean bean);
    
    /**
     * 删除用户配置对应列表字段
     * 2017年9月25日 下午4:04:12
     * @param bean
     * @return String
     * @exception 
     * @see
     */
    String unCheckedUserListColumn(UserListColumnBean bean);
    
    /**
     * 更新列字段信息
     * 2017年9月19日 上午10:13:55
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> updateUserListColumn(int userId, int typeId, String columnIds);
}
