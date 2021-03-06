/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSystemInfo
 *创建人:chensq    创建时间:2017年11月8日
 */
package com.protocolsoft.alarm.util;

import java.util.HashMap;
import java.util.Map;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @ClassName: AlarmNeedSystemInfo
 * @Description: 告警需要得系统信息
 * @author chensq
 *
 */
public class AlarmNeedSystemInfo {
    /**
     * @Title: getPerformanceInfoUbuntu
     * @Description: 获取关系到服务器性能的相关参数
     * @return Map<String,Long>
     * @author chensq
     */
    public static Map<String, Long> getPerformanceInfoUbuntu() {
        Sigar sigar = null ;
        Map<String, Long> map = new HashMap<String, Long>();
        try {
            sigar = new Sigar(); 
           
            CpuInfo []infos = sigar.getCpuInfoList();
 
            long totalCpuNum=infos.length; //总核数
            long totalCpuMhz=0; //总赫兹数
            
            for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
                CpuInfo info = infos[i];
                totalCpuMhz+=info.getMhz();
            }
            map.put("success", Long.parseLong("1"));
            map.put("totalCpuNum", totalCpuNum);
            map.put("totalCpuMhz", totalCpuMhz);

        } catch (SigarException e) {
            map.put("success", Long.parseLong("0"));
            e.printStackTrace();
        } finally {
            if (sigar != null){
                try {
                    sigar.close();
                    sigar = null;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sigar = null;
                }
            }
        }
        return map;
    }
  
}
