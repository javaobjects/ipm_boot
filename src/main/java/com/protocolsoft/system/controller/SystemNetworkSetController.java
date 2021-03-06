/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetController
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.service.LogsService;
import com.protocolsoft.system.bean.NetworkCardBean;
import com.protocolsoft.system.service.SystemNetworkSetService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;

/**
 * SystemNetworkSetController
 * 2017年7月18日 下午6:32:47
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/sysNetworkSet")
public class SystemNetworkSetController {
    
    /**systemNetworkSetService注入*/
    @Autowired(required = false)
    SystemNetworkSetService systemNetworkSetService;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * 日志管理业务对象
     */
    @Autowired
    private LogsService logsService;
    
    /**
     * 管理网口设置  // 获取管理网口的信息进行展示（linux-ubuntu）
     * 2017年7月19日 下午3:27:54
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/netConfig.do")
    @ResponseBody
    public  Map<String, String>  getManageNetworkInfoUbuntu(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> jsonMap = systemNetworkSetService.getManageNetworkInfoUbuntu();
        return jsonMap;
    }
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月19日 下午4:20:17
     * @param
     * @return List<Map<String,String>>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/netConfigList.do")
    @ResponseBody
    public List<Map<String, String>>  getNetworkInfoUbuntu(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> jsonList = systemNetworkSetService.getNetworkInfoUbuntu();
        return jsonList;
    }
    
    /**
     * 总带宽  // 对应sysset.properties中的total_band
     * 2017年7月20日 上午11:48:29
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/totalBand.do")
    @ResponseBody
    public  Map<String, String>  getTotalBandSet(HttpServletRequest request, HttpServletResponse response) {
        return systemNetworkSetService.getTotalBandSet();
    }
    
    /**
     * 总带宽  // 对应sysset.properties中的total_band
     * 2017年7月21日 下午4:33:38
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateTotalBandSet.do")
    @ResponseBody
    public Map<String, String> updateTotalBandSet(HttpServletRequest request, String totalBand) {
        return systemNetworkSetService.modifyTotalBandSet(totalBand);
    }
    
    /**
     * 管理网口设置  // 获取管理网口的信息进行展示（linux-ubuntu）
     * 2017年7月24日 上午11:19:51
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateManageNetworkInfoUbuntu.do")
    @ResponseBody
    public Map<String, String> updateManageNetworkInfoUbuntu(HttpServletRequest request, String ipAddress, String maskBits, String defaultDateway) {
        return systemNetworkSetService.modifyManageNetworkInfoUbuntu(request, ipAddress, maskBits, defaultDateway);
    }
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月25日 上午10:42:06
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/startStatistic.do")
    @ResponseBody
    public Boolean startStatistic(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String>map=systemNetworkSetService.startStatistic(request);
        String success=map.get("success");
        if ("0".equals(success)){
            return true;
        }
        return false;
        
    }
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月25日 上午10:42:36
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/stopStatistic.do")
    @ResponseBody
    public Map<String, String> stopStatistic(HttpServletRequest request, HttpServletResponse response) {
        return systemNetworkSetService.stopStatistic();
    }
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月25日 下午1:50:00
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/switchNetStatus.do")
    @ResponseBody
    public Map<String, String> switchNetStatus(HttpServletRequest request, String ifname, String promisc) {
        return systemNetworkSetService.switchNetStatus(ifname, promisc);
    }
    
    /**
     * 
     * @Title: getNetworkCardInfo
     * @Description: 获取网卡信息
     * @return Map<String,String>
     * @throws SigarException sigar
     * @author www
     */
    @RequestMapping(value = "/getNetworkCardInfo.do")
    @ResponseBody
    public Collection<NetworkCardBean> getNetworkCardInfo() throws SigarException {
        return systemNetworkSetService.getNetworkCardInfo();
    }
   
    /**
     * 清空网卡数据
     * 2018年5月18日 上午10:24:42
     * @param
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getDataEmptying.do")
    @ResponseBody
    public void rebootServer(HttpServletRequest request) {
        try {
            SystemUserBean systemUserBean=userService.getSessionUserBean(request);
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(11);
            logsBean.setMsg("清空网卡管理与设置数据");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsService.addLogs(logsBean);
            
            Collection<NetworkCardBean> listBean = systemNetworkSetService.getNetworkCardInfo();
            for (NetworkCardBean networkCardBean : listBean) {
                Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "ethtool -s " + networkCardBean.getName() + " msglvl 7"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: saveNetworkCardModel
     * @Description: 保存网卡工作模式
     * @param promiscNum 混杂模式网卡开启数量
     * @return Boolean 是否成功
     * @author www
     */
    @RequestMapping(value = "/saveNetworkCardModel.do")
    @ResponseBody
    public Boolean saveNetworkCardModel(int promiscNum) {
        return systemNetworkSetService.saveNetworkCardModel(promiscNum);
    }
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: 获取数据去重状态
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/getDataDedupState.do")
    @ResponseBody
    public Map<String, Integer> getDataDedupState(){
        
        return systemNetworkSetService.getDataDedupState();
    }
    
    /**
     * 
     * @Title: onOffDataDedup
     * @Description: 数据去重
     * @param state 状态
     * @return Map<String,String>
     * @author www
     */
    @RequestMapping(value = "/onOffDataDedup.do")
    @ResponseBody
    public Map<String, String> onOffDataDedup(HttpServletRequest request, int state) {
        
        return systemNetworkSetService.onOffDataDedup(request, state);
    }
    
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: HTTP负载段内容
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/getOnOffHttpLoad.do")
    @ResponseBody
    public Map<String, Integer> getOnOffHttpLoad(){
        
        return systemNetworkSetService.getOnOffHttpLoad();
    }
    
    /**
     * 
     * @Title: onOffHttpLoad
     * @Description: HTTP负载段内容
     * @param state 状态
     * @return Map<String,String>
     * @author www
     */
    @RequestMapping(value = "/onOffHttpLoad.do")
    @ResponseBody
    public Map<String, String> onOffHttpLoad(HttpServletRequest request, int state) {
        
        return systemNetworkSetService.onOffHttpLoad(request, state);
    }
    /**
     * 
     * @Title: getSamplingRatio
     * @Description: 获取抽样比例
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/getSamplingRatio.do")
    @ResponseBody
    public Map<String, Integer> getSamplingRatio() {
        
        return systemNetworkSetService.getSamplingRatio();
    }
    
    /**
     * 
     * @Title: getIntelligentBaseline
     * @Description: 获取智能基线状态
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getIntelligentBaseline.do")
    @ResponseBody
    public Map<String, Integer> getIntelligentBaseline(){
        
        return systemNetworkSetService.getIntelligentBaseline();
    }
    
    /**
     * 
     * @Title: setIntelligentBaseline
     * @Description: 修改智能基线状态
     * @param request 请求
     * @param state  状态
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/setIntelligentBaseline.do")
    @ResponseBody
    public Map<String, String> setIntelligentBaseline(HttpServletRequest request, int state) {
        
        return systemNetworkSetService.setIntelligentBaseline(request, state);
    }
    /**
     * 
     * @Title: updSamplingRatio
     * @Description: 修改
     * @param samplingRatio 比例
     * @return Map<String,String>
     * @author www
     */
    @RequestMapping(value = "/updSamplingRatio.do")
    @ResponseBody
    public Map<String, String> updSamplingRatio(HttpServletRequest request, int samplingRatio) {
        
        return systemNetworkSetService.updSamplingRatio(request, samplingRatio);
    }
    
    /**
     * 
     * @Title: intranetSegment
     * @Description: 获取内网网段设置
     * @param request 请求
     * @param response 响应
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/intranetSegment.do")
    @ResponseBody
    public  Map<String, String> intranetSegment(HttpServletRequest request, HttpServletResponse response) {
        return systemNetworkSetService.intranetSegment();
    }
    
    /**
     * 
     * @Title: updIntranetSegment
     * @Description: 修改内网网段设置
     * @param request 请求
     * @param ip   参数ip
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updIntranetSegment.do")
    @ResponseBody
    public  Map<String, String> updIntranetSegment(HttpServletRequest request, String ip){
        return systemNetworkSetService.updIntranetSegment(request, ip);
    }
    
    
    
    /**
     * 
     * @Title: xpmIps
     * @Description: 获取XPM服务器设置
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/xpmIps.do")
    @ResponseBody
    public Map<String, String> xpmIps(){
        return systemNetworkSetService.xpmIps();
    }
    
    /**
     * 
     * @Title: updxpmIps
     * @Description: 修改XPM服务器设置
     * @param request  请求
     * @param ip    IP地址
     * @param probeName 探针名称
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updxpmIps.do")
    @ResponseBody
    public Map<String, String> updxpmIps(HttpServletRequest request, String ip, String probeName){
        return systemNetworkSetService.updxpmIps(request, ip, probeName);
    }
}
