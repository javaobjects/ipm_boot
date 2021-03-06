/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetService
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hyperic.sigar.SigarException;

import com.protocolsoft.system.bean.NetworkCardBean;

/**
 *
 * 2017年7月18日 下午6:34:31
 * @author long
 * @version
 * @see
 */
public interface SystemNetworkSetService {
    
    /**
     * 管理网口设置  // 获取管理网口的信息进行展示（linux-ubuntu）
     * 2017年7月19日 上午9:57:15
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    Map<String, String> getManageNetworkInfoUbuntu();
    
    /**
     * 总带宽  // 对应sysset.properties中的total_band
     * 2017年7月19日 上午9:59:02
     * @param
     * @return <Map<String, String>
     * @exception 
     * @see
     */
    Map<String, String> getTotalBandSet();
    
    /**
     * 总带宽  // 对应sysset.properties中的total_band
     * 2017年7月21日 下午4:21:27
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> modifyTotalBandSet(String totalBand);
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月19日 上午9:59:25
     * @param
     * @return List<Map<String, String>>
     * @exception 
     * @see
     */
    List<Map<String, String>> getNetworkInfoUbuntu();
    
    /**
     * 管理网口设置  // 获取管理网口的信息进行展示（linux-ubuntu）
     * 2017年7月21日 下午6:06:49
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> modifyManageNetworkInfoUbuntu(HttpServletRequest request, String ipAddress, String maskBits, String defaultDateway);
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年12月6日 下午4:30:14
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> startStatistic(HttpServletRequest request);
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月25日 上午10:23:11
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> stopStatistic();
    
    /**
     * 网卡管理与设置  // 获取服务器所有网卡信息（linux-ubuntu）
     * 2017年7月25日 下午1:52:39
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> switchNetStatus(String ifname, String promisc);
    
    /**
     * 
     * @Title: getNetworkCardInfo
     * @Description: 获取网卡信息
     * @return Collection<NetworkCardBean>
     * @throws SigarException sigar
     * @author www
     */
    Collection<NetworkCardBean> getNetworkCardInfo() throws SigarException;

    /**
     * @Title: saveNetworkCardModel
     * @Description: 保存网卡信息
     * @param promiscNum
     * @return Boolean
     * @author www
     */
    Boolean saveNetworkCardModel(int promiscNum);
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: 获取数据去重状态
     * @return Map<String,Integer>
     * @author www
     */
    Map<String, Integer> getDataDedupState();
    

    /**
     * 
     * @Title: getIntelligentBaseline
     * @Description: 获取智能基线状态
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, Integer> getIntelligentBaseline();
    
    /**
     * 
     * @Title: setIntelligentBaseline
     * @Description: 修改智能基线状态
     * @param request
     * @param state
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, String> setIntelligentBaseline(HttpServletRequest request, int state);
    
    /**
     * 
     * @Title: getDataDedupState
     * @Description: HTTP负载段内容
     * @return Map<String,Integer>
     * @author www
     */
    Map<String, Integer> getOnOffHttpLoad();
    
    /**
     * 
     * @Title: onOffDataDedup
     * @Description: 数据去重
     * @param state 是否开启 1开启 0关闭
     * @return Map<String,String>
     * @author www
     */
    Map<String, String> onOffDataDedup(HttpServletRequest request, int state);
    
    /**
     * 
     * @Title: onOffHttpLoad
     * @Description: HTTP负载段内容
     * @param state 是否开启 1开启 0关闭
     * @return Map<String,String>
     * @author www
     */
    Map<String, String> onOffHttpLoad(HttpServletRequest request, int state);
    
    /**
     * 
     * @Title: getSamplingRatio
     * @Description: 获取抽样比例
     * @return Map<String,Integer>
     * @author www
     */
    Map<String, Integer> getSamplingRatio();
    
    /**
     * 
     * @Title: updSamplingRatio
     * @Description: 修改抽样比例
     * @param num 比例
     * @return Map<String,String>
     * @author www
     */
    Map<String, String> updSamplingRatio(HttpServletRequest request, int num);
    
    /**
     * 
     * @Title: IntranetSegment
     * @Description: 获取内网网段设置
     * @return Map<String,Integer>
     * @author wangjianmin
     */
    Map<String, String> intranetSegment();
    
    /**
     * 
     * @Title: updIntranetSegment
     * @Description: 修改内网网段设置
     * @param request 请求
     * @param ip  ip/网段
     * @return Map<String,String>
     * @author wangjianmin
     */
    Map<String, String> updIntranetSegment(HttpServletRequest request, String ip);
    
    
    /**
     * 
     * @Title: xpmIps
     * @Description: 获取XPM服务器设置
     * @return Map<String,String>
     * @author wangjianmin
     */
    Map<String, String> xpmIps();
    
    /**
     * 
     * @Title: updxpmIps
     * @Description: 修改XPM服务器设置
     * @param request  请求
     * @param ip    IP地址
     * @param probeName   探针名称
     * @return Map<String,String>
     * @author wangjianmin
     */
    Map<String, String> updxpmIps(HttpServletRequest request, String ip, String probeName);
    
}
