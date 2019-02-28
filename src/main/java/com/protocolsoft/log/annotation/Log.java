/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:Log
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Log注解用于记录日志
 * 2017年9月4日 上午10:31:50
 * @author yan
 * @version
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Log {
    
    /**
     * 日志描述对应的小模块Id
     * 2017年9月18日 上午10:18:36
     * @param
     * @exception 
     * @see
     */
    int smallModuleId() default -1;
    
    /**
     * 日志描述
     * 2017年9月4日 上午10:36:23
     * @param
     * @exception 
     * @see
     */
    String description() default "";
}
