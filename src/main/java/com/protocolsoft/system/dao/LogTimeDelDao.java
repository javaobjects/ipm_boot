/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: LogTimeDelDao.java
 *创建人: wangjianmin    创建时间: 2018年7月19日
 */
package com.protocolsoft.system.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: LogTimeDelDao
 * @Description: 定时删除系统日志类
 * @author wangjianmin
 *
 */
@Repository
public interface LogTimeDelDao {
    
    /**
     * 
     * @Title: logNum
     * @Description: 查询系统日志记录数
     * @return int
     * @author wangjianmin
     */
    @Select("SELECT COUNT(*) FROM  ipm_logs  ORDER BY id DESC")
    int selectLogNum();
    
    /**
     * 
     * @Title: byId
     * @Description: 查询最近10000条记录
     * @return int
     * @author wangjianmin
     */
    @Select("SELECT MIN(l.id) FROM (SELECT * FROM ipm_logs ORDER BY id DESC limit 10000) l")
    int selectById();
    
    /**
     * 
     * @Title: deleteLog
     * @Description: 删除小于 num 的记录
     * @param num void  返回 最小的记录数id
     * @author wangjianmin
     */
    @Delete("delete from ipm_logs where id < #{num}")
    void deleteLog(int num);

}
