/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetServiceTest
 *创建人:long    创建时间:2017年7月19日
 */
package com.protocolsoft.system;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

import com.protocolsoft.system.service.SystemNetworkSetService;

/**
 *
 * 2017年7月19日 上午10:35:14
 * @author long
 * @version
 * @see
 */
public class SystemNetworkSetServiceTest {
    
    /**
     * logger
     */
    Logger logger = Logger.getLogger(SystemNetworkSetServiceTest.class);
    
    /**
     * 测试systemNetworkSetService
     * 2017年7月19日 下午3:05:05
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testGetManageNetworkInfoUbuntu(){
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-servlet.xml");
        SystemNetworkSetService systemNetworkSetService = appContext.getBean(SystemNetworkSetService.class);
        Map<String, String>map = systemNetworkSetService.getManageNetworkInfoUbuntu();
        logger.error("ipAddress:"+map.get("ipAddress"));
        logger.error("maskBits:"+map.get("maskBits"));
        logger.error("defaultDateway:"+map.get("defaultDateway"));
        
    }
    
    /**
     * 测试systemNetworkSetService
     * 2017年7月19日 下午3:05:05
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testGetNetworkInfoUbuntu(){
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-servlet.xml");
        SystemNetworkSetService systemNetworkSetService = appContext.getBean(SystemNetworkSetService.class);
        List<Map<String, String>>list = systemNetworkSetService.getNetworkInfoUbuntu();
        for (Map<String, String>map:list){
            logger.error("name:"+map.get("name"));
            logger.error("workCond:"+map.get("workCond"));
            logger.error("rxBytes:"+map.get("rxBytes"));
            logger.error("rxPackets:"+map.get("rxPackets"));
            logger.error("ip:"+map.get("ip"));
        }
        
    }
    
    /**
     * 测试systemNetworkSetService
     * 2017年7月20日 上午11:48:04
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testGetTotalBandSet(){
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-servlet.xml");
        SystemNetworkSetService systemNetworkSetService = appContext.getBean(SystemNetworkSetService.class);
        Map<String, String>map = systemNetworkSetService.getTotalBandSet();
        logger.error("bandWidth:"+map.get("bandWidth"));
        
    }
    
    /**
     * 测试systemNetworkSetService
     * 2017年7月21日 下午5:07:21
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testModifyTotalBandSet(){
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-servlet.xml");
        SystemNetworkSetService systemNetworkSetService = appContext.getBean(SystemNetworkSetService.class);
        Map<String, String>map = systemNetworkSetService.modifyTotalBandSet("100");
        assertEquals("1", map.get("success"));
        
    }
    
    /**
     * 测试systemNetworkSetService
     * 2017年7月25日 下午5:20:30
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testModifyManageNetworkInfoUbuntu(HttpServletRequest request){
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-servlet.xml");
        SystemNetworkSetService systemNetworkSetService = appContext.getBean(SystemNetworkSetService.class);
        Map<String, String>map = systemNetworkSetService.modifyManageNetworkInfoUbuntu(request, "", "", "");
        assertEquals("0", map.get("success"));
        
    }
    
    

}
