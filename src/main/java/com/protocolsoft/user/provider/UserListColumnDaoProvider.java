/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppIpPortDaoProvider
 *创建人:yan    创建时间:2017年9月5日
 */
package com.protocolsoft.user.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.user.bean.UserListColumnBean;

/**
 * ipm_user_list_column表动态sql处理
 *
 * 2017年9月18日 下午5:46:38
 * @author yan
 * @version
 * @see
 */
public class UserListColumnDaoProvider {
    
    /**
     * 批量新增处理
     * 2017年9月5日 上午10:49:08
     * @param Map<String,List<AppIpPortBean>>
     * @return String
     * @exception 
     * @see
     */
    public String batchInsert(Map<String, List<UserListColumnBean>> map) {  
        List<UserListColumnBean> userListColumns = map.get("beans");
        UserListColumnBean bean = null;
        Object[] array = null;
        String result = null;
        StringBuffer sb = new StringBuffer();  
        sb.append("INSERT INTO ipm_user_list_column ");  
        sb.append("(user_id,column_id) ");  
        sb.append("VALUES ");
        for (int i = 0; i < userListColumns.size(); i++) {
            bean = userListColumns.get(i);
            array = new Object[] { 
                String.valueOf(bean.getUserId()), 
                String.valueOf(bean.getColumnId()) 
            };
            result = MessageFormat.format("({0}, {1})", array);
            sb.append(result);
            if (i < userListColumns.size() - 1) {
                sb.append(",");  
            }  
        }  
        return sb.toString();  
    }  
}
