/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpmBusTagDao
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.app.bean.TableParamBean;
import com.protocolsoft.depthanaly.bean.DeptMsgLogBean;
import com.protocolsoft.depthanaly.bean.IpmBusTagBean;
import com.protocolsoft.depthanaly.provider.DepthProvider;
import com.protocolsoft.servers.bean.AppIpPortBean;

/**
 * 
 * @ClassName: IpmBusTagDao
 * @Description: 报文自定义列Dao
 * @author wangjianmin
 *
 */
@Repository
public interface IpmBusTagDao {

    /**
     * 
     * @Title: addBusTag
     * @Description: 添加报文自定义列
     * @param IpmBusTagBean 接收参数
     * @return boolean
     * @author wangjianmin
     */
    @Insert("INSERT INTO ipm_bus_tags(app_id,bus_tag_0,bus_tag_1,bus_tag_2,bus_tag_3,bus_tag_4,bus_tag_5,"
            + " bus_tag_6,bus_tag_7,bus_tag_8,bus_tag_9,bus_tag_10,bus_tag_11,bus_tag_12,bus_tag_13,bus_tag_14,"
            + " bus_tag_15,bus_tag_16,bus_tag_17,bus_tag_18,bus_tag_19) VALUES(#{appId},#{busTag0},#{busTag1},"
            + "#{busTag2},#{busTag3},#{busTag4},#{busTag5},#{busTag6},#{busTag7},#{busTag8},#{busTag9},"
            + "#{busTag10},#{busTag11},#{busTag12},#{busTag13},#{busTag14},#{busTag15},#{busTag16},#{busTag17},"
            + "#{busTag18},#{busTag19})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    boolean  addBusTag(IpmBusTagBean IpmBusTagBean);
 
    /**
     * 
     * @Title: deleteBusTag
     * @Description: 删除报文自定义数据
     * @param appId 业务id
     * @return boolean
     * @author wangjianmin
     */
    @Delete("DELETE FROM ipm_bus_tags WHERE app_id =#{appId}")
    boolean deleteBusTag(int appId);

    /**
     * 
     * @Title: selectAll
     * @Description: 查询所有报文交易业务
     * @return List<IpmBusTagBean>
     * @author wangjianmin
     */
    @Select("SELECT app.id,app.module_id as moduleId,app.`name`,app.displayip,app.bandwidth,app.descrption,"
            + " bus.bus_tag_0 as busTag0 ,bus.bus_tag_1 as busTag1,bus.bus_tag_2 as busTag2,"
            + " bus.bus_tag_3 as busTag3 ,bus.bus_tag_4 as busTag4,bus.bus_tag_5 as busTag5,"
            + " bus.bus_tag_6 as busTag6 ,bus.bus_tag_7 as busTag7,bus.bus_tag_8 as busTag8,"
            + " bus.bus_tag_9 as busTag9 ,bus.bus_tag_10 as busTag10,bus.bus_tag_11 as busTag11,"
            + " bus.bus_tag_12 as busTag12 ,bus.bus_tag_13 as busTag13 ,bus.bus_tag_14 as busTag14,"
            + " bus.bus_tag_15 as busTag15 ,bus.bus_tag_16 as busTag16,bus.bus_tag_17 as busTag17"
            + ",bus.bus_tag_18 as busTag18 ,bus.bus_tag_19 as busTag19,msg.client_ipport as clientIpport ,"
            + " msg.status_code as statusCode,msg.success_code as successCode ,"
            + " msg.failed_code as failedCode"
            + " FROM ipm_app_business app JOIN  ipm_bus_tags  bus ON  bus.app_id =app.id"
            + " JOIN  ipm_msg_fixed msg ON  msg.app_id =app.id WHERE app.module_id =9")
    List<IpmBusTagBean>  selectAll();
    
   /**
    * 
    * @Title: selectAllByIds
    * @Description: 查询一条或多条报文交易
    * @param ids 业务id
    * @return List<IpmBusTagBean>
    * @author wangjianmin
    */
    @Select("SELECT app.id,app.module_id as moduleId,app.`name`,app.displayip,app.bandwidth,app.descrption,"
            + " bus.bus_tag_0 as busTag0 ,bus.bus_tag_1 as busTag1,bus.bus_tag_2 as busTag2,"
            + " bus.bus_tag_3 as busTag3 ,bus.bus_tag_4 as busTag4,bus.bus_tag_5 as busTag5,"
            + " bus.bus_tag_6 as busTag6 ,bus.bus_tag_7 as busTag7,bus.bus_tag_8 as busTag8,"
            + " bus.bus_tag_9 as busTag9 ,bus.bus_tag_10 as busTag10,bus.bus_tag_11 as busTag11,"
            + " bus.bus_tag_12 as busTag12 ,bus.bus_tag_13 as busTag13 ,bus.bus_tag_14 as busTag14,"
            + " bus.bus_tag_15 as busTag15 ,bus.bus_tag_16 as busTag16,bus.bus_tag_17 as busTag17"
            + ",bus.bus_tag_18 as busTag18 ,bus.bus_tag_19 as busTag19,msg.client_ipport as clientIpport,"
            + " msg.status_code as statusCode,msg.success_code as successCode ,"
            + " msg.failed_code as failedCode"
            + " FROM ipm_app_business app JOIN  ipm_bus_tags  bus ON  bus.app_id =app.id"
            + " JOIN  ipm_msg_fixed msg ON  msg.app_id =app.id WHERE app.module_id =9 and app.id in (${ids})")
    List<IpmBusTagBean> selectAllByIds(@Param("ids") String ids);
 
    /**
     * 
     * @Title: selectById
     * @Description: 查询自定义列
     * @param appId 业务ID
     * @return List<IpmBusTagBean>
     * @author wangjianmin
     */
    @Select("SELECT app.id,app.module_id as moduleId ,app.`name`,app.displayip,app.bandwidth,app.descrption,"
            + " bus.bus_tag_0 as busTag0 ,bus.bus_tag_1 as busTag1,bus.bus_tag_2 as busTag2,"
            + " bus.bus_tag_3 as busTag3 ,bus.bus_tag_4 as busTag4,bus.bus_tag_5 as busTag5,"
            + " bus.bus_tag_6 as busTag6 ,bus.bus_tag_7 as busTag7,bus.bus_tag_8 as busTag8,"
            + " bus.bus_tag_9 as busTag9 ,bus.bus_tag_10 as busTag10,bus.bus_tag_11 as busTag11,"
            + " bus.bus_tag_12 as busTag12 ,bus.bus_tag_13 as busTag13 ,bus.bus_tag_14 as busTag14,"
            + " bus.bus_tag_15 as busTag15 ,bus.bus_tag_16 as busTag16,bus.bus_tag_17 as busTag17"
            + ",bus.bus_tag_18 as busTag18 ,bus.bus_tag_19 as busTag19,msg.client_ipport AS clientIpport,"
            + " msg.status_code as statusCode,msg.success_code as successCode ,"
            + " msg.failed_code as failedCode"
            + " FROM ipm_app_business app JOIN  ipm_bus_tags  bus ON  bus.app_id =app.id"
            + " JOIN  ipm_msg_fixed msg ON  msg.app_id =app.id WHERE app.module_id =9 and bus.app_id =#{appId}")
    List<IpmBusTagBean>  selectById(int appId);
    
    /**
     * 
     * @Title: updateBusTag
     * @Description: 修改报文自定义数据
     * @param IpmBusTagBean 接收参数
     * @return boolean
     * @author wangjianmin
     */
    @Update("UPDATE ipm_bus_tags SET bus_tag_0=#{busTag0},bus_tag_1=#{busTag1},bus_tag_2=#{busTag2},"
            + " bus_tag_3=#{busTag3},bus_tag_4=#{busTag4},bus_tag_5=#{busTag5},bus_tag_6=#{busTag6},"
            + " bus_tag_7=#{busTag7},bus_tag_8=#{busTag8},bus_tag_9=#{busTag9}, bus_tag_10=#{busTag10},"
            + " bus_tag_11=#{busTag11},bus_tag_12=#{busTag12},bus_tag_13=#{busTag13},bus_tag_14=#{busTag14},"
            + " bus_tag_15=#{busTag15},bus_tag_16=#{busTag16}, bus_tag_17=#{busTag17},bus_tag_18=#{busTag18},"
            + " bus_tag_19=#{busTag19} WHERE app_id=#{appId}")
    boolean  updateBusTag(IpmBusTagBean IpmBusTagBean);

    /**
     * 
     * @Title: addIpmIpPort
     * @Description: 添加 ipm_app_ip_port
     * @param bean void 接收参数
     * @author wangjianmin
     */
    @Insert("INSERT INTO ipm_app_ip_port (app_id,ip,port) VALUES(#{appId},#{ip},#{port})")
    void addIpmIpPort(AppIpPortBean bean);

    /**
     * 
     * @Title: update
     * @Description: 修改ipm_app_ip_port
     * @param bean void 接收参数
     * @author wangjianmin
     */
    @Update("UPDATE ipm_app_ip_port set ip=#{ip},port=#{port} WHERE app_id=#{appId} ")
    void  update(AppIpPortBean bean);

    /**
     * 
     * @Title: deleteAppIpPort
     * @Description: 删除ipm_app_ip_port
     * @param appId void 业务id
     * @author wangjianmin
     */
    @Delete("delete from ipm_app_ip_port where app_id = #{appId}")
    void deleteAppIpPort(int appId);

    /**
     * 
     * @Title: getAppTableName
     * @Description: 获取 msg_log_tables
     * @param bean 接收参数
     * @return List<String>
     * @author wangjianmin
     */
    @Select("select concat(#{name}, table_id) `name` from msg_log_tables where "
            + "(end >= #{starttime} and end <= #{endtime}) or (start <= #{starttime} "
            + "and end >= #{endtime}) or (start >= #{starttime} and start <= #{endtime})")
    List<String> getAppTableName(TableParamBean bean);

    /**
     * 
     * @Title: getData
     * @Description: 获取Log 表数据
     * @param bean 接收参数
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    @SelectProvider(type = DepthProvider.class, method = "selectLog")
    List<DeptMsgLogBean> getData(DeptMsgLogBean bean);
    
    /**
     * 
     * @Title: getData
     * @Description: 获取Log 表数据
     * @param bean 接收参数
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    @SelectProvider(type = DepthProvider.class, method = "getDataNumSql")
    int getDataNumSql(DeptMsgLogBean bean);

    /**
     * 
     * @Title: selectByLogId
     * @Description: 根据ID 查询一条记录
     * @param id 业务ID
     * @return List<DeptMsgLogBean>
     * @author wangjianmin
     */
    @Select("select  m.id,w.name,`busiId`,`transTime`,inet_ntoa(`server`) as `server`, "
            + "inet_ntoa(`client`) as `client`,`serverPort`,"
            + "`clientPort`,`delay`,`source`,`success`,`resp` from ${tableName} m "
            + "JOIN   ipm_watchpoint  w on   m.`probe` =w.id where  id =#{id}")
    List<DeptMsgLogBean>  selectByLogId(int id);

}
