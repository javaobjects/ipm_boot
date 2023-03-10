package com.protocolsoft.alarm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.alarm.bean.AlarmNoticeSetBean;
import com.protocolsoft.alarm.service.AlarmNoticeSetService;
import com.protocolsoft.user.bean.SystemUserBean;

@Controller
@RequestMapping("/alarmNoticeSet")
public class AlarmNoticeSetController {

	@Autowired
	private AlarmNoticeSetService service;
	
	@RequestMapping("/selectByUserId.do")
	public @ResponseBody List<AlarmNoticeSetBean> selectByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SystemUserBean userBean=(SystemUserBean) session.getAttribute("userBean");
        
        return service.selectByUserId(userBean);
	}
	
	@RequestMapping("/updateById.do")
	public @ResponseBody Map<String, Object> update(AlarmNoticeSetBean bean) {
		int num = service.updateById(bean);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", num);
		
		return map;
	}
	
	@RequestMapping("/checkEmail.do")
	public @ResponseBody Map<String, Object> checkEmail(String emails) {
		
		return service.checkEmail(emails);
	}
}
