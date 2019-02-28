/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SaasuserCenterTask.java
 *创建人: wangjianmin    创建时间: 2018年9月4日
 */
package com.protocolsoft.common.task;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.protocolsoft.common.bean.SaasUserBean;
import com.protocolsoft.common.dao.SaasUserDao;
import com.protocolsoft.common.service.impl.SaasUserService;

/**
 * @ClassName: SaasuserCenterTask
 * @Description: 定时检查Center ip类
 * @author wangjianmin
 *
 */
@Component
public class SaasuserCenterTask {
    
    /**
     * 用户业务
     */
    @Autowired
    private SaasUserService service;
    
    /**
     * 用户DAO
     */
    @Autowired
    private SaasUserDao dao;
    
    /**
     * 
     * @Title: run
     * @Description: 定时检查Center ip，回填时延
     * @author wangjianmin
     */
    @Scheduled(cron = "0 */1 * * * ?")//每隔1分钟执行一次 
    public void run() {
        //获取所有Center ip设置
        List<SaasUserBean>  list = service.getAllUser();
        //循环得到每条Center ip设置
        for (SaasUserBean saasUserBean : list) {
            //排除本机
            if(saasUserBean.getId() != 1){
                long startTime = System.currentTimeMillis(); //开始时间
                long endTime = isCenterIp(saasUserBean);     //检查结束时间
                long  result = (endTime - startTime); //相差时间秒
                if(result < 1000){  //是否小于1000
                    dao.updateCenterDelay(saasUserBean.getId(), 0);
                } else{
                    dao.updateCenterDelay(saasUserBean.getId(), result);
                }  
            }
        }
    }
    
    /**
     * 
     * @Title: isCenterIp
     * @Description: 验证设置Center的ip端口，来返回时延
     * @param bean  接收Center参数
     * @return long
     * @author wangjianmin
     */
    public long isCenterIp(SaasUserBean bean){
        long flag=System.currentTimeMillis();
        Socket socket=null;
        try{
            socket = new Socket(); // 客户端给出IP和端口号
            SocketAddress socketAddress = new InetSocketAddress(bean.getIp(), bean.getPort());
            socket.connect(socketAddress, 10000);
        }catch (Exception e){
            flag=System.currentTimeMillis();
        }finally {
            try{
                socket.close();
            }catch (Exception e){

            }
        }
        return flag;
    }

}
