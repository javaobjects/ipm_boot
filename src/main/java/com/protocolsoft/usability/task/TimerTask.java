/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:TimerTask
 *创建人:wjm    创建时间:2018年3月19日
 */
package com.protocolsoft.usability.task;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.usability.bean.UsabilityBean;
import com.protocolsoft.usability.dao.UsabilityDao;
import com.protocolsoft.usability.service.UsabilityService;

/**
 * 
 * @ClassName: TimerTask
 * @Description: 定时检查应用可用性业务
 * @author wangjianmin
 *
 */
@Component
public class TimerTask {
    /**
     * UsabilityService 注入
     */
    @Autowired(required = false)
    private UsabilityService usabilityService;
    
    
    /**
     * UsabilityDao 注入
     */
    @Autowired(required = false)
    private UsabilityDao usabilityDao;
    
    /**
     * AlarmLogService 注入
     */
    @Autowired(required = false)
    private AlarmLogService alarmLogService ;
    
    /**
     * 定时验证应用可用性
     */
    @Scheduled(cron = "0 */1 * * * ?")//每隔1分钟执行一次 
    public void runListener() {
        List<UsabilityBean> list = usabilityService.getUsabilityAll();
        AlarmLogBean alarmLogBean = new AlarmLogBean();
        long current = System.currentTimeMillis() / 1000;
        boolean bool = false;
        long start=0;
        long end=0;
        for (UsabilityBean usabilityBean : list) {
            if("Y".equals(usabilityBean.getStatus())){
                long time = current - Integer.parseInt(usabilityBean.getLastExecTime());
                start = Long.parseLong(usabilityBean.getLastExecTime());  //告警的开始时间
                end= Long.parseLong(usabilityBean.getLastExecTime());    //告警的结束时间
                if(time >= Integer.parseInt(usabilityBean.getInterval()) * 60){
                    bool = isPortComm(usabilityBean);
                    usabilityBean.setLastExecTime(current+"");
                    usabilityDao.updateTime(usabilityBean);
                    if(!bool){  //判断如果IP端口不通添加告警
                        AlarmLogBean bean=alarmLogService.getMaxUsabilityAlarmLongEndTime(usabilityBean.getIp(), usabilityBean.getPort());
                        if(bean != null && bean.getEndtime() != 0){ //判断告警是不是存在，存在,返回告警时间
                            alarmLogBean = new AlarmLogBean();
                            alarmLogBean.setBusinessId(usabilityBean.getAppId());
                            alarmLogBean.setStarttime(start);
                            alarmLogBean.setEndtime(end);
                            if(current - bean.getEndtime()  >= Integer.parseInt(usabilityBean.getInterval()) * 60){
                                // 判断告警时间是否相邻，如果相邻更新时间
                                alarmLogService.updateUsabilityAlarmLogEndTimeById(alarmLogBean.getEndtime(), bean.getId());  
                            }else{
                                // 如果告警时间不相邻就添加告警
                                alarmLogService.insertUsabilityAlarmlog(alarmLogBean); 
                            } 
                        }else{
                            alarmLogBean = new AlarmLogBean();
                            alarmLogBean.setBusinessId(usabilityBean.getAppId());
                            alarmLogBean.setStarttime(start);
                            alarmLogBean.setEndtime(end);
                            alarmLogService.insertUsabilityAlarmlog(alarmLogBean);
                        }
                    }
                }                                      
            }
        }
    }
    /**
     * 验证IP，端口是否能通
     * 2018年3月27日 下午2:10:37
     * @param beans 
     * @return boolean
     * @exception 
     * @see
     */
    private boolean isPortComm(UsabilityBean beans){
        boolean flag=true;
        Socket socket=null;
        try{
            socket = new Socket(); // 客户端给出IP和端口号
            SocketAddress socketAddress = new InetSocketAddress(beans.getIp(), Integer.parseInt(beans.getPort()));
            socket.connect(socketAddress, 10000);
        }catch (Exception e){
            flag=false;
        }finally {
            try{
                socket.close();
            }catch (Exception e){

            }
        }
        return flag;
    }
}
