/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmAlgorithmDao
 *创建人:chensq    创建时间:2017年11月2日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;

/**
 * @ClassName: AlarmAlgorithmDao
 * @Description: AlarmAlgorithm Dao
 * @author chensq
 *
 */
@Repository
public interface AlarmAlgorithmDao {
    /**
     * 
     * @Title: getAlarmAlgorithmList
     * @Description: 返回算法的数据集合
     * @return List<AlarmAlgorithmBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "nameen as nameen, " 
            + "namezh as namezh, " 
            + "algorithm as algorithm, " 
            + "algorithminfo as algorithminfo, " 
            + "descrption as descrption " 
            + "from " 
            + "ipm_alarm_algorithm ")
    List<AlarmAlgorithmBean> getAlarmAlgorithmList();
    
    /**
     * 
     * @Title: getAlarmAlgorithmById
     * @param id 算法id
     * @Description: 根据id查询算法的数据
     * @return AlarmAlgorithmBean
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "nameen as nameen, " 
            + "namezh as namezh, " 
            + "algorithm as algorithm, " 
            + "algorithminfo as algorithminfo, " 
            + "descrption as descrption " 
            + "from " 
            + "ipm_alarm_algorithm "
            + "where "
            + "id = ${id} ")
    AlarmAlgorithmBean getAlarmAlgorithmById(@Param("id") String id);
    
    /**
     * 
     * @Title: updateAlarmAlgorithmById
     * @param alarmAlgorithmBean 算法对象
     * @Description: 根据id修改算法
     * @return int
     * @author chensq
     */    
    @Update("update ipm_alarm_algorithm set "
            + "`algorithminfo`=#{algorithminfo},"
            + "`descrption`=#{descrption}  "
            + "where id=#{id}")
    int updateAlarmAlgorithmById(AlarmAlgorithmBean alarmAlgorithmBean);
    
    
}
