/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmRrdService.java
 *创建人: www    创建时间: 2018年5月18日
 */
package com.protocolsoft.kpi.service;

import java.io.File;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.protocolsoft.kpi.bean.RetainTimeBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;

/**
 * @ClassName: AlarmRrdService
 * @Description: 告警基线RRD
 * @author WWW
 *
 */
public class AlarmRrdService {
    
    /**
     * URL RRD根
     */
    private final static String PREFIX = "ALARM";
    
    /**
     * RRD
     */
    private RRDService service;
    
    /**
     * 模块编号
     */
    private long moduleId;
    
    /**
     * 业务编号
     */
    private long busiId;
    
    /**
     * 观察点编号
     */
    private long watchpointId;
    
    /**
     * 基线类型
     */
    private AlarmBaseType type;
    
    /**
     * 是否为最终基线
     *    0: 否
     *    1: 是
     */
    private int isFinBase;

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param moduleId 模块编号
     *        模块为观察点是，busiId为观察点编号，watchpointId为0.
     * @param busiId 业务编号
     * @param watchpointId 观察点编号
     * @param type 基线类型
     * @param isFinBase 是否为最终基线 true:不是最终基线  false：最终基线
     *        
     */
    public AlarmRrdService(long moduleId, long busiId, 
            long watchpointId, AlarmBaseType type, boolean isFinBase) {
        service = new RRDService();
        this.moduleId = moduleId;
        this.busiId = busiId;
        this.watchpointId = watchpointId;
        this.type = type;
        this.isFinBase = isFinBase ? 1 : 0;
    }
    
    /**
     * 
     * @Title: create
     * @Description: 创建RRD
     * @param step 初始粒度
     * @param columns 列名
     * @param time 保留时间
     * @return boolean
     * @author WWW
     */
    public boolean create(int step, List<String> columns, List<RetainTimeBean> time) {
        boolean bool = false;
        try {
            char ch = File.separatorChar;
            String path = PREFIX + ch + moduleId + ch + busiId + ch 
                    + watchpointId + ch + type.ordinal() + ch + isFinBase;
            bool = service.createDir(path, step, columns, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: update
     * @Description: 更新
     * @param time 时间
     * @param values 数据
     * @return boolean 是否成功
     * @author www
     */
    public boolean update(long time, List<Number> values) {
        char ch = File.separatorChar;
        String path = PREFIX + ch + moduleId + ch + busiId + ch 
                + watchpointId + ch + type.ordinal() + ch + isFinBase;
        
        return service.updateRrd(path, time, values);
    }
    
    /**
     * 
     * @Title: deleteFile
     * @Description: 删除RRD文件
     * @return boolean
     * @author www
     */
    public boolean deleteFile() {
        char ch = File.separatorChar;
        String path = PREFIX + ch + moduleId + ch + busiId;
        service.dleDir(path);
        
        return true;
    }
    
    /**
     * 
     * @Title: getBaseLineRrd
     * @Description: 取智能告警的单个点rrd信息
     * @param starttime
     * @param endtime
     * @param kpiName
     * @return SimpleEntry<Long,Double>
     * @author chensq
     */
    public SimpleEntry<Long, Double> getBaseLineRrd(long starttime, long endtime, String kpiName, int lidu){
        BusiKpiService busiKpiService=new BusiKpiService((int)moduleId, (int)busiId, (int)watchpointId,  type, false);
        //rrd point
        SimpleEntry<Long, Double> point=busiKpiService.getRrdDataPointByName(//rrd取值
                starttime,
                endtime,                     
                lidu,
                kpiName,
                RrdAlgorithm.AVG);
        
        return point;
    }
     
}
