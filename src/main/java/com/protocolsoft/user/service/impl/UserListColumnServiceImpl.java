/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserListColumnServiceImpl
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.UserListColumnBean;
import com.protocolsoft.user.dao.UserListColumnDao;
import com.protocolsoft.user.service.UserListColumnService;

/**
 * UserListColumnServiceImpl
 * 2017年9月15日 上午9:38:35
 * @author long
 * @version
 * @see
 */
@Service
public class UserListColumnServiceImpl implements UserListColumnService {
    
    /**
     * logger
     */
    static Logger logger = Logger.getLogger(UserListColumnServiceImpl.class);
    
    /**
     * userConfigureDao注入
     */
    @Autowired(required=false)
    private UserListColumnDao userListColumnDao;

    @Override
    public List<ListColumnBean> getListColumnList(int isDefault) {
        return userListColumnDao.getListColumnList(isDefault);
    }

    @Override
    public Map<String, String> addUserListColumn(int userId,
            List<ListColumnBean> listColumnList) {
        Map<String, String> map=new HashMap<String, String>();
        for (ListColumnBean listColumnBean:listColumnList){
            UserListColumnBean userListColumnBean=new UserListColumnBean();
            userListColumnBean.setUserId(userId);
            userListColumnBean.setColumnId(listColumnBean.getId());
            userListColumnDao.addUserListColumn(userListColumnBean);
        }
        return map;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.user.service.UserListColumnService#getListColumn(java.lang.Integer)
     */
    @Override
    public List<ListColumnBean> getListColumn(Integer typeId) {
        List<ListColumnBean> listColumnList = null;
        try {
            listColumnList = userListColumnDao.selectListColumnByTypeId(typeId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return listColumnList;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.user.service.UserListColumnService#getUserListColumn(java.lang.Integer)
     */
    @Override
    public List<ListColumnBean> getUserListColumn(Integer userId, Integer typeId) {
        List<ListColumnBean> listColumnList = null;
        try {
            listColumnList=getListColumn(typeId);
            for (int i=0; i<listColumnList.size(); i++){
                int count=userListColumnDao.getUserListColumnByUserColumnId(userId, listColumnList.get(i).getId());
                if (count>0){
                    listColumnList.get(i).setChecked(1);
                } else {
                    listColumnList.get(i).setChecked(0);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listColumnList;
    }

    /* (non-Javadoc)
     * @see com.protocolsoft.user.service.UserListColumnService#addUserListColumn(java.lang.Integer, java.lang.Integer)
     */
   /*@Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveUserListColumn(Integer userId, String columnIds, Integer typeId) {
        List<UserListColumnBean> beans = new ArrayList<UserListColumnBean>();
        String result = "fail";
        try {
            StringBuffer columnIdStr = new StringBuffer();
            List<ListColumnBean> listColumns = userListColumnDao.selectListColumnByTypeId(typeId);
            for (ListColumnBean listColumnBean : listColumns) {
                columnIdStr.append(",");
                columnIdStr.append(listColumnBean.getId());
            }
            userListColumnDao.deleteUserListColumnInColumnIds(userId, columnIdStr.toString().substring(1));
            
            userListColumnDao.delUserListByUserIdColumnId(userId, typeId);
            
            if (null != columnIds && !"".equals(columnIds)) {
                String [] arr = columnIds.split(",");
                for (int i = 0; i < arr.length; i++) {
                    if (null != arr[i]) {
                        UserListColumnBean bean = new UserListColumnBean();
                        bean.setUserId(userId);
                        bean.setColumnId(Integer.parseInt(arr[i]));
                        beans.add(bean);
                    }
                }
            }
            userListColumnDao.batchInsertUserListColumn(beans);
            result = "success";
        } catch (Exception e){
            logger.debug("保存用户配置列表字段异常,异常信息:" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }*/

    /* (non-Javadoc)
     * @see com.protocolsoft.user.service.UserListColumnService#checkedUserListColumn(com.protocolsoft.user.bean.
     * UserListColumnBean)
     */
    @Override
    public String checkedUserListColumn(UserListColumnBean bean) {
        String result = "fail";
        try {
            userListColumnDao.addUserListColumn(bean);
            result = "success";
        } catch (Exception e){
            logger.debug("保存用户配置列表字段异常,异常信息:" + e);
        }
        return result;
    }
    
    /* (non-Javadoc)
     * @see com.protocolsoft.user.service.UserListColumnService#unCheckedUserListColumn(com.protocolsoft.user.bean.
     * UserListColumnBean)
     */
    @Override
    public String unCheckedUserListColumn(UserListColumnBean bean) {
        String result = "fail";
        try {
            userListColumnDao.deleteUserListByUserIdAndColumnId(bean);
            result = "success";
        } catch (Exception e){
            logger.debug("删除用户配置列表字段异常,异常信息:" + e);
        }
        return result;
    }
    
    @Override
    public Map<String, String> updateUserListColumn(int userId, int typeId, String columnIds) {
        Map<String, String>map=new HashMap<String, String>();
        List<UserListColumnBean> userListColumnList = new ArrayList<UserListColumnBean>();
        userListColumnDao.delUserListByUserIdColumnId(userId, typeId);
        if (null != columnIds && !"".equals(columnIds)) {
            String [] arr = columnIds.split(",");
            for (int i = 0; i < arr.length; i++) {
                if (null != arr[i]) {
                    UserListColumnBean userListColumnBean = new UserListColumnBean();
                    userListColumnBean.setUserId(userId);
                    userListColumnBean.setColumnId(Integer.parseInt(arr[i]));
                    userListColumnList.add(userListColumnBean);
                }
            }
        }
        userListColumnDao.batchInsertUserListColumn(userListColumnList);
        map.put("success", "1");
        return map;
    }
}
