/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:LogsDao
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import com.protocolsoft.log.bean.LogsBean;

/**
 * ipm_logs表Dao
 * 2017年9月4日 下午3:03:43
 * @author yan
 * @version
 * @see
 */
@Repository
public interface LogsDao {

    /**
     * 新增
     * 2017年9月4日 下午3:05:49
     * @param LogsBean
     * @exception 
     * @see
     */
    @Insert("insert into ipm_logs(user_id,time,module_id,msg) values(#{userId},#{time},#{moduleId},#{msg})")
    void insertLogs(LogsBean bean);
    
    /**
     * 通过userId删除log
     * 2017年9月20日 下午4:09:28
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_logs where user_id=#{userId}")
    boolean delLogsByUserId(int userId);
}
