package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmNoticeSetBean;

@Repository
public interface AlarmNoticeSetDao {

	@Insert("<script>"
			+ "INSERT INTO `ipm_alarm_notice_set` (`user_id`, `name`, `type`, `state`, `restrain`, `email`, `message`, `im`, `desp`) VALUES "
			+ "<foreach collection='list' item='bean' separator=','> "
			+ "(#{bean.userId}, #{bean.name}, #{bean.type}, #{bean.state}, #{bean.restrain}, #{bean.email}, #{bean.message}, #{bean.im}, #{bean.desp})"
			+ "</foreach>"
			+ "</script>")
	int add(List<AlarmNoticeSetBean> list);
	
	@Update("UPDATE `ipm_alarm_notice_set` SET `state`=#{state}, `restrain`=#{restrain}, "
			+ "`email`=#{email}, `message`=#{message}, `im`=#{im}, `desp`=#{desp} WHERE id = #{id}")
	int updateById(AlarmNoticeSetBean bean);
	
	@Select("select `id`, `user_id` userId, `name`, `type`, `state`, `restrain`, `email`, `message`, `im`, `desp` from `ipm_alarm_notice_set` "
			+ "where user_id = #{userId}")
	List<AlarmNoticeSetBean> selectByUserId(@Param("userId") int userId);
	
	@Select("select `id`, `user_id` userId, `name`, `type`, `state`, `restrain`, `email`, `message`, `im`, `desp` from `ipm_alarm_notice_set` "
			+ "where state = true")
	List<AlarmNoticeSetBean> selectAllByOpenState();
}
