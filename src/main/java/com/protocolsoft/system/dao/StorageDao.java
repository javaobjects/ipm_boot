/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ClientDao
 *创建人:long    创建时间:2017年9月8日
 */
package com.protocolsoft.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.system.bean.DataStorageBean;

/**
 * StorageDao
 * 2017年12月8日 下午3:15:26
 * @author long
 * @version
 * @see
 */
@Repository
public interface StorageDao {
    /**
     * 添加存储
     * 2017年12月8日 下午3:17:00
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Insert(" insert into ipm_data_storage (id,name,watchpoint_id,starttime,endtime,ip,port,bpf,size,state) "
            + " values (#{id}, #{name},#{watchpointId},#{starttime},#{endtime},#{ip},#{port},#{bpf},#{size}"
            + ",#{state}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addStorage(DataStorageBean dataStorageBean);
    
    /**
     * 删除存储
     * 2017年12月8日 下午3:17:19
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Delete("delete from ipm_data_storage where id=#{id}")
    boolean delStorageById(int id);
    
    /**
     * 根据id删除存储
     * 2017年12月8日 下午3:17:35
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update(" update ipm_data_storage set `name`=#{name},`watchpoint_id`=#{watchpointId},`starttime`=#{starttime}, "
            + " `endtime`=#{endtime},`ip`=#{ip},`port`=#{port},`bpf`=#{bpf},`size`=#{size},`state`=#{state} "
            + " where id=#{id} ")
    int updateStorageById(DataStorageBean dataStorageBean);
    
    /**
     * 查询存储
     * 2017年12月8日 下午3:17:53
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    @Select(" select id,name,watchpoint_id as watchpointId,starttime,endtime,ip as ip,port,bpf,size,state "
            + " from ipm_data_storage   ")
    List<DataStorageBean> getStorage();
    
    /**
     * 根据id查询存储
     * 2017年12月11日 下午4:16:41
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    @Select(" select id,name,watchpoint_id as watchpointId,starttime,endtime,ip as ip,port,bpf,size,state "
            + " from ipm_data_storage  where id=#{id}")
    DataStorageBean getStorageById(int id);
    
    /**
     * 查询高级存储
     * 2017年12月11日 下午4:19:52
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    @Select(" select id,name,watchpoint_id as watchpointId,starttime,endtime,ip,port,bpf,size,state "
            + " from ipm_data_storage  where id>1")
    List<DataStorageBean> getStorageHigh();

}
