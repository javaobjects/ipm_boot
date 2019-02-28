/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCheckCountBean
 *创建人:chensq    创建时间:2017年11月16日
 */
package com.protocolsoft.alarm.bean;

import java.util.Map;

/**
 * @ClassName: AlarmCheckCountBean
 * @Description: 告警产生检查数量bean
 * @author chensq
 *
 */
public class AlarmCheckCountBean {
     
    /**
     * @Fields starttime : 计算开始时间
     */
    private long starttime;
    
    /**
     * @Fields endtime : 计算结束时间
     */
    private long endtime;
   
    /**
     * @Fields endFlag : 是否结束标识 1：未结束 2：结束  3：产生
     */
    private int endFlag;
     
    /**
     * @Fields levelValueMap : 级别对应的次数，该map的顺序为级别的升序
     */
    private Map<String, Integer> levelValueMap=null;
 
    /**
     * @Fields finalAlarmSetId : 最终告警级别的告警设置id
     */
    private long finalAlarmSetId;
        
    /**
     * @Fields triggerflag : 告警产生信息
     */
    private long triggerflag;
   
    /**
     * @Fields firstCount : 第一次超过阈值的计数器
     */
    private int firstCount;

    /**
     * @return the starttime
     */
    public long getStarttime() {
        return starttime;
    }

    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    /**
     * @return the endtime
     */
    public long getEndtime() {
        return endtime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    /**
     * @return the endFlag
     */
    public int getEndFlag() {
        return endFlag;
    }

    /**
     * @param endFlag the endFlag to set
     */
    public void setEndFlag(int endFlag) {
        this.endFlag = endFlag;
    }

    /**
     * @return the levelValueMap
     */
    public Map<String, Integer> getLevelValueMap() {
        return levelValueMap;
    }

    /**
     * @param levelValueMap the levelValueMap to set
     */
    public void setLevelValueMap(Map<String, Integer> levelValueMap) {
        this.levelValueMap = levelValueMap;
    }

    /**
     * @return the finalAlarmSetId
     */
    public long getFinalAlarmSetId() {
        return finalAlarmSetId;
    }

    /**
     * @param finalAlarmSetId the finalAlarmSetId to set
     */
    public void setFinalAlarmSetId(long finalAlarmSetId) {
        this.finalAlarmSetId = finalAlarmSetId;
    }

    /**
     * @return the triggerflag
     */
    public long getTriggerflag() {
        return triggerflag;
    }

    /**
     * @param triggerflag the triggerflag to set
     */
    public void setTriggerflag(long triggerflag) {
        this.triggerflag = triggerflag;
    }

    /**
     * <br />获取 <font color="red"><b>firstCount<b/></font>
     * @return firstCount firstCount
     */
    public int getFirstCount() {
        return firstCount;
    }

    /**  
     * <br />设置 <font color='#333399'><b>firstCount</b></font>
     * @param firstCount firstCount  
     */
    public void setFirstCount(int firstCount) {
        this.firstCount = firstCount;
    }
    
    
}
