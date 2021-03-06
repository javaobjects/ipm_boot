/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemSetServiceImplTest
 *创建人:yan    创建时间:2017年7月19日
 */
package com.protocolsoft.system;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.protocolsoft.system.service.SystemSetService;
import com.protocolsoft.system.service.impl.SystemSetServiceImpl;

/**
 * 系统设置业务逻辑测试类
 * 2017年7月19日 下午2:53:14
 * @author yan
 * @version
 * @see
 */
public class SystemSetServiceImplTest {

    /**
     * logger
     */
    Logger logger = Logger.getLogger(SystemSetServiceImplTest.class);
    
    /**
     * 系统设置业务逻辑层对象
     */
    private SystemSetService systemSetService = new SystemSetServiceImpl();
    
    /**
     * Test method for {@link com.protocolsoft.system.service.impl.SystemSetServiceImpl#getDataTimeSet()}.
     */
    //@Test
    public void testGetDataTimeSet() {
        Map<String, String> resultMap = systemSetService.getDataTimeSet();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            logger.debug("key=====" + entry.getKey() + " and value=====" + entry.getValue());
        }
    }

    /**
     * Test method for {@link com.protocolsoft.system.service.impl.SystemSetServiceImpl#getProductLicensSet()}.
     */
    //@Test
    public void testGetProductLicensSet() {
        List<Map<String, String>> resultListMap = systemSetService.getProductLicensSet();
        for (Map<String, String> map : resultListMap) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                logger.debug("key=====" + entry.getKey() + " and value=====" + entry.getValue());
            }
        }
    }

    /**
     * Test method for {@link com.protocolsoft.system.service.impl.SystemSetServiceImpl#getDataStorageSet()}.
     */
    //@Test
    public void testGetDataStorageSet() {
        Map<String, String> resultMap = systemSetService.getDataStorageSet();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            logger.debug("key=====" + entry.getKey() + " and value=====" + entry.getValue());
        }
    }
    
    /**
     * Test method for {@link com.protocolsoft.system.service.impl.SystemSetServiceImpl#operateDateTimeSet()}.
     */
    @Test
    public void testOperateDateTimeSet(HttpServletRequest request){
        String result = systemSetService.operateDateTimeSet(request, "192.168.1.12", "2017-12-12");
        logger.debug("result====="+result);
    }
    
    /**
     * Test method for {@link com.protocolsoft.system.service.impl.SystemSetServiceImpl#clearTargetDir()}.
     */
    @Test
    public void testClearTargetDir(HttpServletRequest request){
        String result = systemSetService.clearTargetDir(request, "/ASD/CVB");
        logger.debug("result====="+result);
    }
}
