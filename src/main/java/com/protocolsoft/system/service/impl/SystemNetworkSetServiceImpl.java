/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetServiceImpl
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.system.bean.NetworkCardBean;
import com.protocolsoft.system.service.SystemNetworkSetService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;

/**
 * 管理网口设置 // 获取管理网口的信息进行展示（linux-ubuntu） 2017年7月18日 下午6:35:19 
 * @author long
 * @version
 * @see
 */
@Service
public class SystemNetworkSetServiceImpl implements SystemNetworkSetService {
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;

    @Override
    public Map<String, String> getManageNetworkInfoUbuntu() {
        Sigar sigar = null ;
        Map<String, String> map = new HashMap<String, String>();
        try {
            sigar = new Sigar(); 
            NetInfo netInfo = sigar.getNetInfo(); 
            NetInterfaceConfig netInterfaceConfig = sigar.getNetInterfaceConfig(null);
            String ipAddress = netInterfaceConfig.getAddress(); 
            String maskBits = netInterfaceConfig.getNetmask(); 
            String defaultDateway = netInfo.getDefaultGateway(); 
            map.put("ipAddress", ipAddress); 
            map.put("maskBits", maskBits); 
            map.put("defaultDateway", defaultDateway); 
            map.put("success", "1");
        } catch (SigarException e) {
            map.put("success", "0");
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

    @Override
    public Map<String, String> getTotalBandSet() {
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        try {
            map.put("bandWidth", propertiesUtil.readProperty("total_band")); 
            map.put("success", "1");
        } catch (IOException e) { 
            map.put("success", "0");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map<String, String>> getNetworkInfoUbuntu() {
        Sigar sigar = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        try {
            sigar = new Sigar();
            String[] names = sigar.getNetInterfaceList();
            for (int i= 0; i<names.length; i++) {
                NetInterfaceConfig netInterfaceConfig = sigar.getNetInterfaceConfig(names[i]); 
                NetInterfaceStat netInterfaceStat = sigar.getNetInterfaceStat(names[i]); 
                long flags = netInterfaceConfig.getFlags();
                map = new HashMap<String, String>(); 
                map.put("name", names[i]); 
                map.put("workCond", flags == 0 ? flags + "" : flags / flags+ "");
                map.put("rxBytes", netInterfaceStat.getRxBytes()+""); 
                map.put("rxPackets", netInterfaceStat.getRxPackets()+""); 
                map.put("ip", netInterfaceConfig.getAddress()); 
                list.add(map); 
            }
        } catch (SigarException e) {
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
        return list;
    }

    @Override
    public Map<String, String> modifyTotalBandSet(String totalBand) {
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        String success = propertiesUtil.writePropertyReturn("total_band", totalBand); 
        map.put("success", success);
        return map;
    }

    @Override
    public Map<String, String> modifyManageNetworkInfoUbuntu(HttpServletRequest request, String ipAddress, String maskBits, String defaultDateway) {
        Map<String, String> map =new HashMap<String, String>(); 
        String success = "1"; 
        String path = "/etc/network/interfaces"; 
        FileInputStream fis = null; 
        BufferedReader reader = null; 
        FileOutputStream fos = null; 
        PrintWriter pw = null; 
        try {
            String net = null;
            String tmp = null;
            List<String> strs = new ArrayList<String>();
            fis = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(fis));
            while ((tmp = reader.readLine()) != null) {
                if (tmp.contains("address")) {
                    tmp = "address " + ipAddress;
                } else if (tmp.contains("netmask")) {
                    tmp = "netmask " + maskBits;
                } else if (tmp.contains("gateway")) {
                    tmp = "gateway " + defaultDateway;
                } else if (tmp.contains("inet static")) {
                    net = tmp.split(" +")[1];
                }
                strs.add(tmp);
            }
            fos = new FileOutputStream(path);
            pw = new PrintWriter(new OutputStreamWriter(fos));
            for (int i = 0, len = strs.size(); i < len; i++){
                pw.println(strs.get(i));
            }
            Runtime.getRuntime().exec("ifconfig " + net + " down");
            Thread.sleep(5000);
            Runtime.getRuntime().exec("ifconfig " + net + " up");
        } catch (Exception e) {
            success = "0";
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (pw != null) {
                    pw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("success", success);
        
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("更新管理网口设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        
        return map;
    }

    @Override
    public Map<String, String> startStatistic(HttpServletRequest request) {
        
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("重新分析网卡管理与设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        String[] execStr = new String []{"/bin/sh", "-c", "killall -9 ipmcoll"};
        Map<String, String> map=new HashMap<String, String>();
        try {
            Runtime.getRuntime().exec(execStr);
            map.put("success", "0");
        } catch (IOException e) {
            map.put("success", "1");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, String> stopStatistic() {
        return null;
    }

    @Override
    public Map<String, String> switchNetStatus(String ifname, String promisc) {
        return null;
    }

    /**
     * (非 Javadoc)
     * <p>Title: getNetworkCardInfo</p>
     * <p>Description: </p>
     * @return Collection<NetworkCardBean>
     * @throws SigarException sigar
     * @see com.protocolsoft.system.service.SystemNetworkSetService#getNetworkCardInfo()
     */
    public Collection<NetworkCardBean> getNetworkCardInfo() throws SigarException {
        Map<String, NetworkCardBean> data = new HashMap<String, NetworkCardBean>();
        NetworkCardBean bean = null;
        Sigar sigar = new Sigar();
        String[] ifNames = sigar.getNetInterfaceList();
        NetInterfaceConfig ifconfig = null;
        NetInterfaceStat ifstat = null;
        for (int i = 0; i < ifNames.length; i++) {
            bean = new NetworkCardBean();
            bean.setName(ifNames[i]);
            ifconfig = sigar.getNetInterfaceConfig(bean.getName());
            ifstat = sigar.getNetInterfaceStat(bean.getName());
            bean.setIp(ifconfig.getAddress());
            bean.setRxPackets(ifstat.getRxPackets());
            bean.setRxBytes(ifstat.getRxBytes());
            data.put(bean.getName(), bean);
        }
        sigar.close();
        
        return data.values();
    }

    /** (非 Javadoc)
     * <p>Title: saveNetworkCardModel</p>
     * <p>Description: </p>
     * @return boolean 是否成功
     * @see com.protocolsoft.system.service.SystemNetworkSetService#saveNetworkCardModel(java.util.Map)
     */
    public Boolean saveNetworkCardModel(int promiscNum) {
        boolean bool = true;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("linux") > -1) {
            bool = this.setCConfig(promiscNum);
            String[] cmdsh = new String []{"/bin/sh", "-c", "/opt/apache-tomcat-8.0.45/bin/shutdown.sh"};
            try {
                Runtime.getRuntime().exec(cmdsh).waitFor();
                cmdsh[2] = "reboot";
                Runtime.getRuntime().exec(cmdsh);
            } catch (InterruptedException e) {
                bool = false;
                e.printStackTrace();
            } catch (IOException e) {
                bool = false;
                e.printStackTrace();
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: restartCApp
     * @Description: 设置C端参数
     * @param promiscNum 混杂模式网卡数
     * @return boolean
     * @author www
     */
    private boolean setCConfig(int promiscNum) {
        Map<Integer, Integer> memorySize = new HashMap<Integer, Integer>();
        memorySize.put(1, 1500);
        memorySize.put(2, 2300);
        memorySize.put(3, 4096);
        boolean bool = true;
        StringBuilder cmd =  new StringBuilder();
        cmd.append("sed -e 's/^HUGEPAGES=..*$/HUGEPAGES=");
        cmd.append(memorySize.get(promiscNum));
        cmd.append("/g' /usr/local/driver/loaddriver.sh");
        String[] cmdsh = new String []{"/bin/sh", "-c", cmd.toString()};
        try {
            Runtime.getRuntime().exec(cmdsh).waitFor();
        } catch (InterruptedException e) {
            bool = false;
            e.printStackTrace();
        } catch (IOException e) {
            bool = false;
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: 获取数据去重状态
     * @return Map<String,Integer>
     * @author www
     */
    public Map<String, Integer> getDataDedupState() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String val = "1";
        try {
            val = propertiesUtil.readProperty("disableRepeat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("state", Integer.parseInt(val));
        
        return map;
    }
    
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: HTTP负载段内容
     * @return Map<String,Integer>
     * @author www
     */
    public Map<String, Integer> getOnOffHttpLoad() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String val = "1";
        try {
            val = propertiesUtil.readProperty("httpload");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("state", Integer.parseInt(val));
        
        return map;
    }
    /**
     * 
     * @Title: onOffDataDedup
     * @Description: 数据去重
     * @param state 是否开启 1开启 0关闭
     * @return Map<String,String>
     * @author www
     */
    public Map<String, String> onOffDataDedup(HttpServletRequest request, int state) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("更新数据包去重");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        String success = propertiesUtil.writePropertyReturn("disableRepeat", String.valueOf(state)); 
        map.put("success", success);
        
        return map;
    }
    
    /**
     * 
     * @Title: getSamplingRatio
     * @Description: 获取抽样比例
     * @return Map<String,Integer>
     * @author www
     */
    public Map<String, Integer> getSamplingRatio() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String val = "1";
        try {
            val = propertiesUtil.readProperty("samplingRatio");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("samplingRatio", Integer.parseInt(val));
        
        return map;
    }
    
    /**
     * 
     * @Title: updSamplingRatio
     * @Description: 修改抽样比例
     * @param num 比例数(1-32)
     * @return Map<String,String>
     * @author www
     */
    public Map<String, String> updSamplingRatio(HttpServletRequest request, int num) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("更新抽样比例");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        if (num >= 1 && num <= 32) {
            PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
            String success = propertiesUtil.writePropertyReturn("samplingRatio", String.valueOf(num)); 
            map.put("success", success);
        } else {
            map.put("success", "0");
        }
        
        return map;
    }


    @Override
    public Map<String, String> onOffHttpLoad(HttpServletRequest request, int state) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(15);
        logsBean.setMsg("更新负载入库");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        String success = propertiesUtil.writePropertyReturn("httpload", String.valueOf(state)); 
        map.put("success", success);
        return map;
    }

    @Override
    public Map<String, Integer> getIntelligentBaseline() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String val = "1";
        try {
            val = propertiesUtil.readProperty("alarm_baseline_show");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("state", Integer.parseInt(val));
        return map;
    }

    @Override
    public Map<String, String> setIntelligentBaseline(HttpServletRequest request, int state) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("修改智能基线状态");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        if(state == 1){
            PlotService.alarmBaselineShow  = true; //开启
        }else{
            PlotService.alarmBaselineShow  = false; //关闭
        }
        String success = propertiesUtil.writePropertyReturn("alarm_baseline_show", String.valueOf(state)); 
        map.put("success", success);
        return map;
    }

    @Override
    public Map<String, String> intranetSegment() {
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String val = null;
        try {
            val = propertiesUtil.readProperty("monitor_net");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("state", val);
        return map;
    }

    @Override
    public Map<String, String> updIntranetSegment(HttpServletRequest request, String ip) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("更新内网网段设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        String success = propertiesUtil.writePropertyReturn("monitor_net", ip); 
        map.put("success", success);
        
        return map;
    }

    @Override
    public Map<String, String> xpmIps() {
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        String xpmips = null;
        String probename = null;
        try {
            xpmips = propertiesUtil.readProperty("xpm_ips");
            probename =  propertiesUtil.readProperty("probe_name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("xpm_ips", xpmips);
        map.put("probe_name", probename);
        return map;
    }

    @Override
    public Map<String, String> updxpmIps(HttpServletRequest request, String ip, String probeName) {
        //当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(11);
        logsBean.setMsg("更XPM服务器设置");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        Map<String, String> map = new HashMap<String, String>();
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
        String success = propertiesUtil.writePropertyReturn("xpm_ips", ip); 
        if("1".equals(success)){
            map.put("success", success);
        }else{
            map.put("success", "0");
        }
        return map;
    }
}
