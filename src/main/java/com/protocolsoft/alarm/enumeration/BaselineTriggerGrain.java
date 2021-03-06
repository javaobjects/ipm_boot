/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:BaselineTriggerGrain
 *创建人:chensq    创建时间:2017年11月3日
 */
package com.protocolsoft.alarm.enumeration;

/**
 * @ClassName: BaselineTriggerGrain
 * @Description: 与rrd的删除时间一致
 * @author chensq
 *
 */
public enum BaselineTriggerGrain {
 
    /**
     * @Fields _10S : 10秒----10s粒度保存4小时
     */
    _10S(10),
    /**
     * @Fields _60S :  60秒=1分钟----1分钟粒度保存一天
     */
    _60S(60),
    /**
     * @Fields _600S : 600秒=10分钟----10分钟粒度保存10天
     */
    _600S(600),    
    /**
     * @Fields _3600S : 3600秒=1小时----1小时粒度保存240天
     */
    _3600S(3600);
     
    /**
     * @Fields index : index
     */
    int index;
    
    /**
     * <p>Title: BaselineTriggerGrain </p>
     * <p>Description: </p>
     * @param index 
     */ 
    private BaselineTriggerGrain(int index) {
        this.index = index;
    }
    
    /**
     * 
     * 2017年11月3日 下午7:49:22
     * @param
     * @return int
     * @exception 
     * @see
     */
    public int toInt() {
        return index;
    }

}
