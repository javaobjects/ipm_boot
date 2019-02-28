/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmMsgFixedDao
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.dao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.depthanaly.bean.IpmMsgFixedBean;

/**
 * 
 * @ClassName: IpmMsgFixedDao
 * @Description: 添加报文交易类
 * @author wangjianmin
 *
 */
@Repository
public interface IpmMsgFixedDao {
  
    /**
     * 
     * @Title: addMsgFixed
     * @Description:  添加报文设置
     * @param bean void 接收参数
     * @author wangjianmin
     */
    @Insert("INSERT INTO ipm_msg_fixed (app_id,client_ipport,status_code,success_code,failed_code)"
            + " VALUES(#{appId},#{clientIpport},#{statusCode},#{successCode},#{failedCode})")
    void  addMsgFixed(IpmMsgFixedBean bean);

    /**
     * 
     * @Title: deleteMsgFixed
     * @Description: 删除报文设置
     * @param appId void 业务id
     * @author wangjianmin
     */
    @Delete("DELETE FROM ipm_msg_fixed WHERE app_id =#{appId}")
    void deleteMsgFixed(int appId);

    /**
     * 
     * @Title: updataMsgFixed
     * @Description: 修改报文设置
     * @param bean void 接收参数
     * @author wangjianmin 
     */
    @Update("UPDATE ipm_msg_fixed set client_ipport=#{clientIpport},status_code=#{statusCode},"
            + "success_code=#{successCode},failed_code=#{failedCode}"
            + " WHERE app_id=#{appId}")
    void updataMsgFixed(IpmMsgFixedBean bean);
    
}
