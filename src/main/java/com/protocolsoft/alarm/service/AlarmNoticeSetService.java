package com.protocolsoft.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmEmailSendBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmNoticeSetBean;
import com.protocolsoft.alarm.dao.AlarmNoticeSetDao;
import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.service.EmailService;
import com.protocolsoft.sendemail.MailSendService;
import com.protocolsoft.sendemail.bean.AlarmCheckEmailBean;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;

@Service
public class AlarmNoticeSetService {
	
	private static Logger logger = LoggerFactory.getLogger(AlarmNoticeSetService.class);

	@Autowired
	private AlarmNoticeSetDao dao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MailSendService emailSendService;
	
	@Autowired
	private AuthorizeJurisService jurisService;

    @Autowired
    private AlarmLogService alarmLogService;
	
	private int add(SystemUserBean userBean) {
		List<AlarmNoticeSetBean> list = new ArrayList<AlarmNoticeSetBean>();
		AlarmNoticeSetBean bean = null;
		// 普通告警
		bean = new AlarmNoticeSetBean();
		bean.setUserId(userBean.getId());
		bean.setName("普通告警");
		bean.setType(2);
		bean.setState(false);
		bean.setRestrain(60);
		list.add(bean);
		
		// 重要告警
		bean = new AlarmNoticeSetBean();
		bean.setUserId(userBean.getId());
		bean.setName("重要告警");
		bean.setType(3);
		bean.setState(false);
		bean.setRestrain(60);
		list.add(bean);
		
		// 紧急告警
		bean = new AlarmNoticeSetBean();
		bean.setUserId(userBean.getId());
		bean.setName("紧急告警");
		bean.setType(4);
		bean.setState(false);
		bean.setRestrain(60);
		list.add(bean);
		
		return dao.add(list);
	}
	
	public int updateById(AlarmNoticeSetBean bean) {
		
		return dao.updateById(bean);
	}
	
	public List<AlarmNoticeSetBean> selectByUserId(SystemUserBean userBean) {
		List<AlarmNoticeSetBean> list = dao.selectByUserId(userBean.getId());
		if (list.size() == 0) {
			this.add(userBean);
			list = dao.selectByUserId(userBean.getId());
		}
		
		return list;
	}
	
	public Map<String, Object> checkEmail(String emails) {
        AlarmCheckEmailBean checkBean = new AlarmCheckEmailBean();
        checkBean.setTitle("自检邮件");
        checkBean.setContext("此邮件为自检邮件，请勿回复！");
        
        return this.sendEmail(emails, checkBean);
	}
	
	public Map<String, Object> sendEmail(String emails, AlarmCheckEmailBean checkBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		
        EmailBean emailBean = emailService.getEmail();
        
        String[] emailArr = emails.split(",");
        for (int i = 0, len = emailArr.length; i < len; i ++) {
        	try {
        		checkBean.setToEmail(emailArr[i]);
				emailSendService.send(emailBean, checkBean);
			} catch (MessagingException e) {
				msg += emailArr[i] + "发送失败.";
				e.printStackTrace();
			}
        }
        
        if ("".equals(msg)) {
        	map.put("msg", "发送成功");
        } else {
        	map.put("msg", msg);
        }
        map.put("code", 0);
        
        return map;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////        告警邮箱发送                       //////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
	
	private static long lastSendTime;
	
	private static String alarmWebUrl = null;
	
	static {
		lastSendTime = DateUtils.getNowTimeSecond(60) - 600;
		PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
		try {
			alarmWebUrl = propertiesUtil.readProperty("alarm_web_url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// <userId, <moduleId, <appId, <levelId, <kpiId, sendInfo>>>>>
	private static Map<Integer, Map<Integer, Map<Integer, Map<Integer, Map<Integer, AlarmSendInfo>>>>> alarmSendMap =
			new HashMap<Integer, Map<Integer,Map<Integer,Map<Integer,Map<Integer, AlarmSendInfo>>>>>();
		
	
	@Scheduled(fixedRate = 60000)
	public void alarmEmailSend() {
		long starttime = lastSendTime;
		long endtime = lastSendTime + 60;
		logger.info("告警邮件发送线程开始：" + DateUtils.getLongToStrDateTime(starttime * 1000));
		lastSendTime = endtime;
		Map<Integer, Map<Integer, AlarmNoticeSetBean>> alarmNoticeSetMap = this.getOpenStateSet();
		if (alarmNoticeSetMap != null) {
			Map<Integer, Map<Integer, Map<Integer, AuthorizeJurisBean>>> userJurisMap = this.getModuleJuris();
			if (userJurisMap != null) {
				Map<Integer, Map<Integer, Map<Integer, AlarmLogBean>>> alarmLogMap = this.getAlarmLog(starttime, endtime);
				
				if (alarmLogMap != null) {
					// <alarmLevelId, bean>
					Map<Integer, AlarmNoticeSetBean> levelUserMap = null;
					// <moduleId, <appId, bean>>
					Map<Integer, Map<Integer, AuthorizeJurisBean>> jurisUserMap = null;
					// <moduleId, <appId, bean>>
					Map<Integer, Map<Integer, AlarmLogBean>> alarmLevelMap = null;
					for (Integer userId : alarmNoticeSetMap.keySet()) {
						if (userJurisMap.containsKey(userId)) {
							levelUserMap = alarmNoticeSetMap.get(userId);
							jurisUserMap = userJurisMap.get(userId);
							for (Integer levelId : levelUserMap.keySet()) {
								alarmLevelMap = alarmLogMap.get(levelId);
								this.userLevelDataHandle(endtime, levelUserMap.get(levelId), jurisUserMap, alarmLevelMap);
							}
						}
					}
				}
			}
		}
	}
	
	private Map<String, Object> sendEmail(String emails, List<AlarmEmailSendBean> list) {
		String context = this.alarmEmailContext(list);
		
        AlarmCheckEmailBean checkBean = new AlarmCheckEmailBean();
        checkBean.setTitle("XPM告警通知");
        checkBean.setContext(context);
        
        return this.sendEmail(emails, checkBean);
	}
	
	private void userLevelDataHandle(long time, AlarmNoticeSetBean noticeSetBean,
			// <moduleId, <appId, bean>>
			Map<Integer, Map<Integer, AuthorizeJurisBean>> jurisUserMap,
			// <moduleId, <appId, bean>>
			Map<Integer, Map<Integer, AlarmLogBean>> alarmLevelMap) {
		
		List<AlarmEmailSendBean> list = null;
		
		if (jurisUserMap != null && alarmLevelMap != null) {
			list = new ArrayList<AlarmEmailSendBean>();
			Map<Integer, AuthorizeJurisBean> jurisAppMap = null;
			Map<Integer, AlarmLogBean> alarmAppMap = null;
			AuthorizeJurisBean jurisBean = null;
			AlarmLogBean alarmLogBean = null;
			AlarmEmailSendBean sendBean = null;
			for (Integer moduleId : jurisUserMap.keySet()) {
				jurisAppMap = jurisUserMap.get(moduleId);
				alarmAppMap = alarmLevelMap.get(moduleId);
				if (jurisAppMap != null && alarmAppMap != null) {
					for (Integer appId : jurisAppMap.keySet()) {
						jurisBean = jurisAppMap.get(appId);
						alarmLogBean = alarmAppMap.get(appId);
						if (jurisBean != null && alarmLogBean != null) {
							sendBean = this.initSendEmailInfo(time, noticeSetBean, jurisBean, alarmLogBean);
							if (sendBean != null) {
								list.add(sendBean);
							}
						}
					}
				}
			}
		}
		
		// 发送邮件
		if (list != null && list.size() > 0) {
			this.sendEmail(noticeSetBean.getEmail(), list);
		}
	}
	
	private AlarmEmailSendBean initSendEmailInfo(long time, AlarmNoticeSetBean noticeSetBean,
			AuthorizeJurisBean jurisBean, AlarmLogBean alarmLogBean) {
		
		// <moduleId, <appId, <levelId, <kpiId, sendTime>>>>
		Map<Integer, Map<Integer, Map<Integer, Map<Integer, AlarmSendInfo>>>> moduleMap = null;
		Map<Integer, Map<Integer, Map<Integer, AlarmSendInfo>>> appMap = null;
		Map<Integer, Map<Integer, AlarmSendInfo>> levelMap = null;
		Map<Integer, AlarmSendInfo> kpiMap = null;
		if (alarmSendMap.containsKey(noticeSetBean.getUserId())) {
			moduleMap = alarmSendMap.get(noticeSetBean.getUserId());
		} else {
			moduleMap = new HashMap<Integer, Map<Integer,Map<Integer,Map<Integer,AlarmSendInfo>>>>();
			alarmSendMap.put(noticeSetBean.getUserId(), moduleMap);
		}
		
		if (moduleMap.containsKey(jurisBean.getModuleId())) {
			appMap = moduleMap.get(jurisBean.getModuleId());
		} else {
			appMap = new HashMap<Integer, Map<Integer,Map<Integer,AlarmSendInfo>>>();
			moduleMap.put(jurisBean.getModuleId(), appMap);
		}
		
		if (appMap.containsKey(jurisBean.getAppId())) {
			levelMap = appMap.get(jurisBean.getAppId());
		} else {
			levelMap = new HashMap<Integer, Map<Integer,AlarmSendInfo>>();
			appMap.put(jurisBean.getAppId(), levelMap);
		}
		
		if (levelMap.containsKey(noticeSetBean.getType())) {
			kpiMap = levelMap.get(noticeSetBean.getType());
		} else {
			kpiMap = new HashMap<Integer, AlarmSendInfo>();
			levelMap.put(noticeSetBean.getType(), kpiMap);
		}
		
		boolean isSend = false;
		int kpiId = (int) alarmLogBean.getKpisId();
		if (kpiMap.containsKey(kpiId)) {
			long sendtime = kpiMap.get(kpiId).getSendTime();
			if ((sendtime + noticeSetBean.getRestrain() * 60) <= time) {
				isSend = true;
				kpiMap.get(kpiId).restrainNumReset().setSendTime(time);
			} else {
				kpiMap.get(kpiId).restrainNumAdd();
			}
		} else {
			kpiMap.put(kpiId, new AlarmSendInfo(time));
			isSend = true;
		}
		
		AlarmEmailSendBean bean = null; 
		if (isSend) {
			bean = new AlarmEmailSendBean();
			bean.setTime(DateUtils.getLongToStrDateTime(alarmLogBean.getStarttime() * 1000));
			bean.setNum(kpiMap.get(kpiId).getRestrainNum());
			bean.setModuleName(alarmLogBean.getModuleName());
			bean.setLevel(noticeSetBean.getName());
			bean.setKpiName(alarmLogBean.getKpisDisplayName());
			bean.setAppName(alarmLogBean.getBusinessName());
			bean.setAlarmType(alarmLogBean.getTriggerflagStr());
		}
		
		return bean;
	}
	
	private Map<Integer, Map<Integer, Map<Integer, AlarmLogBean>>> getAlarmLog(long starttime, long endtime) {
		// <levelId, <moduleId, <appId, bean>>>
		Map<Integer, Map<Integer, Map<Integer, AlarmLogBean>>> alarmLogMap = null;
		
		AlarmLogBean alarmLogBean = new AlarmLogBean();
		alarmLogBean.setStarttime(starttime);
		alarmLogBean.setEndtime(endtime);
        List<AlarmLogBean> list = alarmLogService.getAlarmLogData(alarmLogBean);
        if (list != null && list.size() > 0) {
        	alarmLogMap = new HashMap<Integer, Map<Integer,Map<Integer,AlarmLogBean>>>();
        	Map<Integer,Map<Integer,AlarmLogBean>> moduleMap = null;
        	Map<Integer, AlarmLogBean> appMap = null;
        	AlarmLogBean bean = null;
        	for (int i = 0, len = list.size(); i < len; i ++) {
        		bean = list.get(i);
        		int levelId = (int) bean.getAlarmLevelId();
        		int moduleId = (int) bean.getModuleId();
        		int appId = (int) bean.getBusinessId();
        		if (alarmLogMap.containsKey(levelId)) {
        			moduleMap = alarmLogMap.get(levelId);
        		} else {
        			moduleMap = new HashMap<Integer, Map<Integer,AlarmLogBean>>();
        			alarmLogMap.put(levelId, moduleMap);
        		}
        		
        		if (moduleMap.containsKey(moduleId)) {
        			appMap = moduleMap.get(moduleId);
        		} else {
        			appMap = new HashMap<Integer, AlarmLogBean>();
        			moduleMap.put(moduleId, appMap);
        		}
        		appMap.put(appId, bean);
        	}
        }
        
        return alarmLogMap;
	}
	
	private Map<Integer, Map<Integer, Map<Integer, AuthorizeJurisBean>>> getModuleJuris() {
		// <userId, <moduleId, <appId, bean>>>
		Map<Integer, Map<Integer, Map<Integer, AuthorizeJurisBean>>> userJurisMap = null;
		
		List<AuthorizeJurisBean> moduleJuris = jurisService.getModuleJuris();
		if (moduleJuris != null && moduleJuris.size() > 0) {
			userJurisMap = new HashMap<Integer, Map<Integer,Map<Integer,AuthorizeJurisBean>>>();
			Map<Integer, Map<Integer, AuthorizeJurisBean>> moduleMap = null;
			Map<Integer, AuthorizeJurisBean> appMap = null;
			AuthorizeJurisBean bean = null;
			for (int i = 0, len = moduleJuris.size(); i < len; i ++) {
				bean = moduleJuris.get(i);
				if (userJurisMap.containsKey(bean.getUserId())) {
					moduleMap = userJurisMap.get(bean.getUserId());
				} else {
					moduleMap = new HashMap<Integer, Map<Integer,AuthorizeJurisBean>>();
					userJurisMap.put(bean.getUserId(), moduleMap);
				}
				
				if (moduleMap.containsKey(bean.getModuleId())) {
					appMap = moduleMap.get(bean.getModuleId());
				} else {
					appMap = new HashMap<Integer, AuthorizeJurisBean>();
					moduleMap.put(bean.getModuleId(), appMap);
				}
				
				appMap.put(bean.getAppId(), bean);
			}
		}
		
		return userJurisMap;
	}
	
	private Map<Integer, Map<Integer, AlarmNoticeSetBean>> getOpenStateSet() {
		// <UserId, <alarmLevelId, bean>>
		Map<Integer, Map<Integer, AlarmNoticeSetBean>> alarmNoticeSetMap = null;
		
		List<AlarmNoticeSetBean> list = dao.selectAllByOpenState();
		if (list != null && list.size() > 0) {
			alarmNoticeSetMap = new HashMap<Integer, Map<Integer,AlarmNoticeSetBean>>();
			Map<Integer, AlarmNoticeSetBean> alarmMap = null;
			AlarmNoticeSetBean bean = null;
			for (int i = 0, len = list.size(); i < len; i ++) {
				bean = list.get(i);
				if (alarmNoticeSetMap.containsKey(bean.getUserId())) {
					alarmMap = alarmNoticeSetMap.get(bean.getUserId());
				} else {
					alarmMap = new HashMap<Integer, AlarmNoticeSetBean>();
					alarmNoticeSetMap.put(bean.getUserId(), alarmMap);
				}
				alarmMap.put(bean.getType(), bean);
			}
		}
		
		return alarmNoticeSetMap;
	}
	
	private String alarmEmailContext(List<AlarmEmailSendBean> list) {
		StringBuilder context = new StringBuilder();
		context.append("尊敬的XPM用户，<br />");
		context.append("以下信息为已经触发告警通知条件的告警内容。<br />");
		context.append("<table border=1 style='text-align: center;'>");
		context.append("<tr style='font-weight: bold;'>");
		context.append("<td>告警时间</td>");
		context.append("<td>抑制数量</td>");
		context.append("<td>告警级别</td>");
		context.append("<td>模块名称</td>");
		context.append("<td>业务名称</td>");
		context.append("<td>KPI名称</td>");
		context.append("<td>告警类型</td>");
		context.append("</tr>");
		
		AlarmEmailSendBean bean = null;
		for (int i = 0, len = list.size(); i < len; i ++) {
			bean = list.get(i);
			context.append("<tr>");
			context.append("<td>" + bean.getTime() + "</td>");
			context.append("<td>" + bean.getNum() + "</td>");
			context.append("<td>" + bean.getLevel() + "</td>");
			context.append("<td>" + bean.getModuleName() + "</td>");
			context.append("<td>" + bean.getAppName() + "</td>");
			context.append("<td>" + bean.getKpiName() + "</td>");
			context.append("<td>" + bean.getAlarmType() + "</td>");
			context.append("</tr>");
		}
		context.append("</table>");
		context.append("如需查看更详细的告警信息，请访问：");
		context.append(alarmWebUrl);
		context.append("<br />");
		context.append("如果该告警通知较多，请在“告警阈值设置”功能里，及时修改相关指标的告警阈值，或在系统设置的“告警通知设置”功能里增加告警通知抑制的时间。");
		
		return context.toString();
	}
	
	/**
	 * 发送告警信息类
	 */
	private class AlarmSendInfo {
		
		/**
		 * 发送时间
		 */
		private Long sendTime;
		
		/**
		 * 抑制数量
		 */
		private int restrainNum;
		
		/**
		 * @param sendTime 发送时间
		 */
		public AlarmSendInfo(Long sendTime) {
			this.sendTime = sendTime;
			this.restrainNum = 0;
		}
		
		public void restrainNumAdd() {
			this.restrainNum ++;
		}
		
		public AlarmSendInfo restrainNumReset() {
			this.restrainNum = 0;
			
			return this;
		}

		public int getRestrainNum() {
			return restrainNum;
		}

		public Long getSendTime() {
			return sendTime;
		}

		public void setSendTime(Long sendTime) {
			this.sendTime = sendTime;
		}
	}
}
