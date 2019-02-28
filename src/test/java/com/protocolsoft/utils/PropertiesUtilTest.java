/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:PropertiesUtilTest
 *创建人:yan    创建时间:2017年7月19日
 */
package com.protocolsoft.utils;


import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 属性文件工具类测试类
 * 2017年7月19日 上午11:48:35
 * @author yan
 * @version
 * @see
 */
public class PropertiesUtilTest {

    /**
     * logger
     */
    Logger logger = Logger.getLogger(PropertiesUtilTest.class);
    
    /**
     * 测试PropertiesUtil
     * 2017年7月19日 上午10:09:28
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testPropertiesUtil(){
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String result = "";
        try {
            result = propertiesUtil.readProperty("time_server");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("读取sysset.properties文件中key为time_server的value====="+result);
        
        Properties properties = null;
        try {
            properties = propertiesUtil.getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("获取sysset.properties对应的properties对象====="+properties);
        
        try {
            propertiesUtil.writeProperty("test", "test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
