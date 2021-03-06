/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BeanUtils.java
 *创建人: www    创建时间: 2018年3月30日
 */
package com.protocolsoft.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BeanUtils
 * @Description: 工具
 * @author www
 *
 */
public class BeanUtils {

    /**
     * 
     * @Title: beanToMap
     * @Description: Bean 转 Map
     * @param bean 数据实体
     * @return Map<String,Object>
     * @author www
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        String name = null;
        Object val = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                name = pd.getName();
                if("ipmCenterId".equals(name)) {
                    continue;
                }
                if(!name.equals("class")) {
                    val = pd.getReadMethod().invoke(bean);
                    if (val != null) {
                        map.put(name, val);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        
        return map;
    }
}
