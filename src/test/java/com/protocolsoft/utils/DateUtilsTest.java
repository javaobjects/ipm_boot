/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DateUtilsTest
 *创建人:yan    创建时间:2017年7月19日
 */
package com.protocolsoft.utils;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 日期工具类测试类
 * 2017年7月19日 下午2:25:07
 * @author yan
 * @version
 * @see
 */
public class DateUtilsTest {

    /**
     * logger
     */
    Logger logger = Logger.getLogger(PropertiesUtilTest.class);
    
    /**
     * 测试DateUtils
     * 2017年7月19日 下午2:25:44
     * @param
     * @exception 
     * @see
     */
    @Test
    public void testGetServerTimeUtil() {
        String serverTime = DateUtils.getServerTime();
        logger.debug("当前服务器时间====="+serverTime);
    }
}
