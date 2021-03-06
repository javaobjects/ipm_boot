/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairService.java
 *创建人:chensq    创建时间:2017年10月17日
 */
package com.protocolsoft.commpair.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.CenterIpBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.commpair.bean.CommpairAreaDictBean;
import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.bean.CommpairFindParamBean;
import com.protocolsoft.commpair.dao.CommpairAreaDictDao;
import com.protocolsoft.commpair.dao.CommpairDao;
import com.protocolsoft.commpair.provider.CommpairProvider;
import com.protocolsoft.commpair.util.CommpairIpLocationUtil;
import com.protocolsoft.commpair.util.CommpairProtocolUtil;
import com.protocolsoft.commpair.util.CommpairTableNameUtil;
import com.protocolsoft.commpair.util.CommpairWithOutKpiUtil;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.dao.PublicProtoPlanDao;
import com.protocolsoft.protoplan.service.PublicProtoPlanService;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.utils.BeanUtils;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.IpUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName： CommpairService
 * @Description: 通信对service
 * @author chensq
 * 
 */
@Service
public class CommpairService {

    /**
     * @Fields MAX_COMMPAIR_LOG_NUM : 定义常量
     */
    public static final int MAX_COMMPAIR_LOG_NUM=1000;
    
    /**
     * @Fields commpairDao : 通信对DAO
     */
    @Autowired
    private CommpairDao commpairDao;
    
    /**
     * @Fields publicProtoPlanDao : public协议DAO
     */
    @Autowired
    private PublicProtoPlanDao publicProtoPlanDao;
    
    /**
     * @Fields watchpointDao : 观察点DAO
     */
    @Autowired
    WatchpointDao watchpointDao;
    
    /**
     * @Fields centerService : Center
     */
    @Autowired
    private CenterIpService centerService;
    
    /**
     * @Fields commpairAreaDictDao : commpair AreaDict 
     */
    @Autowired
    private CommpairAreaDictDao commpairAreaDictDao;
    
    /**
     * @Fields plotService : plot Service
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * @Fields appBusinessDao : appBusiness Dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    //--------有地址库的情况-----------
  
    /**
     * 
     * @Title: toCommpairMergeData
     * @Description: 通信对聚合的service方法
     * @param commpairBean 通信对参数对象
     * @param publicProtoPlanService 协议业务对象
     * @param commpairService 通信对业务对象
     * @param serverManagementService 服务端管理业务对象
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> toCommpairMergeData(
            CommpairBean commpairBean,
            PublicProtoPlanService publicProtoPlanService,
            CommpairService commpairService,
            ServerManagementService serverManagementService){
        //返回结果
        List<CommpairBean> commpairList =null;
        long start=0;
        long end=0;
        if (commpairBean.getStarttime()== 0) {
            end=System.currentTimeMillis() / DateAppsUtils.RADIX1000;
            start=end-DateAppsUtils.DEFAULTINTERVAL_TENMINUTES;
            commpairBean.setStarttime(start);
            commpairBean.setEndtime(end);
        }

        //ipStr to ipLong

        if (commpairBean.getProtocolType()==0) {//默认正常情况
            //处理协议饼图情况
            if (commpairBean.getProtoPlanId()>0){//有选择协议
                ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                protoPlanBean.setId(commpairBean.getProtoPlanId());
                protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                if (protoPlanBean.getPort()==0) {//前台请求的是未识别协议
                    commpairBean.setServerport(0);
                    String serverportNotInSql=commpairService.portNotInFullSql();
                    if (StringUtils.isNotEmpty(serverportNotInSql)){
                        commpairBean.setServerportNotInSql(serverportNotInSql);
                    }
                } else {//普通协议
                    int portNum=protoPlanBean.getPort();
                    commpairBean.setServerport(portNum);
                }
            }
        } else if (commpairBean.getProtocolType()==1) {//公有协议分布情况
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            String []protocolInfoArray= null;
            if (StringUtils.isNotEmpty(protocolInfo)){
                protocolInfoArray= protocolInfo.split(",");
            }
            //判断具体情况
            if (commpairBean.getProtocolInfo()!=null && "11106".equals(commpairBean.getProtocolInfo())) {//未识别情况
                commpairBean.setServerport(0);
                String serverportNotInSql=commpairService.portNotInFullSql();
                if (StringUtils.isNotEmpty(serverportNotInSql)){
                    commpairBean.setServerportNotInSql(serverportNotInSql);
                }
            } else {//单独情况   或   其他情况            
                if (protocolInfoArray!=null){
                    if (protocolInfoArray.length==1) {//单独情况
                        ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                        protoPlanBean.setId(Integer.parseInt(commpairBean.getProtocolInfo()));
                        protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                        int portNum=protoPlanBean.getPort();
                        commpairBean.setServerport(portNum);
                    } else if (protocolInfoArray.length>1) {//其他情况                       
                        commpairBean.setServerport(0);
                        String serverportNotInSql=commpairService.portNotInByProtoIds(protocolInfo);
                        if (StringUtils.isNotEmpty(serverportNotInSql)){
                            commpairBean.setServerportNotInSql(serverportNotInSql);
                        }
                    }
                }
            }
        } else if (commpairBean.getProtocolType()==2) {//自定义用户协议
            //ipPort Buf 
            StringBuffer ipPortBuf=new StringBuffer();
            //ip port集合
            List<AppIpPortBean> appIpPortBeanList=null;
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            if (StringUtils.isNotEmpty(protocolInfo) && !"0".endsWith(protocolInfo)) {//单独情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(Integer.parseInt(protocolInfo));
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip="+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport="+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" or ");
                        }
                    }
                }
            } else {//未识别情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(0);
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip <>"+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport <> "+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" and ");
                        }
                    }
                }                
            }            
            commpairBean.setServerIpPortSql(ipPortBuf.toString());
        }
      
        //根据流量排序功能
        KpisBean kpisBean=null;
        if(commpairBean.getKpiId()==0){
            kpisBean=plotService.getKpisByEthernetTraffic();
        }else{
            kpisBean=plotService.getKpisById(commpairBean.getKpiId());
        }
        commpairBean.setKpiName(kpisBean.getName());
        //隐式的传递了-triggerflag
        
        //计算limit参数信息
        commpairBean.setStartNum(commpairBean.getServerPageNumber()==1?0:((commpairBean.getServerPageNumber()-1) * MAX_COMMPAIR_LOG_NUM));
        commpairBean.setStepNum(MAX_COMMPAIR_LOG_NUM);
        //聚合结果
        commpairList=this.getCommpairs(commpairBean);
 
        //当list为null时,处理下
        if (commpairList==null) {
            commpairList=new ArrayList<CommpairBean>();
        }
        
        return commpairList;
    }
    
    /**
     * @Title: getCommpairs
     * @Description: 聚合通信对的列表方法
     * @param commpair
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getCommpairs(CommpairBean commpair) {
        long lidu=DateAppsUtils.getCommpairNewIntervalAuto(commpair.getStarttime(), commpair.getEndtime());
        commpair.setLidu(lidu);
        List<CommpairBean> list=null;
        if(commpair.getOpenIpLocationFlag()==0){// 不开启地址库
            list=this.getCommpairMergeList(commpair, commpair.getWatchpointId());
        }else{// 开启地址库            
            //地图请求通信对的情况
            if(commpair.getAreaDictId()>0){
                //根据地址id查询地址中文信息
                //城市
                CommpairAreaDictBean commpairAreaDictBean3=new CommpairAreaDictBean();
                commpairAreaDictBean3=commpairAreaDictDao.getCommpairAreaDictById(commpair.getAreaDictId());
                //省份
                CommpairAreaDictBean commpairAreaDictBean2=new CommpairAreaDictBean();
                commpairAreaDictBean2=commpairAreaDictDao.getCommpairAreaDictById(commpairAreaDictBean3.getParentId());
                //国家
                CommpairAreaDictBean commpairAreaDictBean1=new CommpairAreaDictBean();
                commpairAreaDictBean1=commpairAreaDictDao.getCommpairAreaDictById(commpairAreaDictBean2.getParentId());
                //设置入通信对对象
                commpair.setCountryCn(commpairAreaDictBean1.getNameCn()); //国家
                commpair.setRegionCn(commpairAreaDictBean2.getNameCn()); //省份
                commpair.setCityCn(commpairAreaDictBean3.getNameCn()); //城市
            }
            list=this.getCommpairLocMergeList(commpair, commpair.getWatchpointId());
        }
        return this.packageCommpair(list, lidu, 1, commpair);
    }
    
    /**
     * 
     * @Title: toCommpairDetailData
     * @Description: 通信对详情的service方法
     * @param commpairBean
     * @param protoPlanService
     * @param commpairService
     * @param serverManagementService
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> toCommpairDetailData(
            CommpairBean commpairBean,
            PublicProtoPlanService publicProtoPlanService,
            CommpairService commpairService,
            ServerManagementService serverManagementService){
        //返回结果
        List<CommpairBean> commpairList =null;
        long start=0;
        long end=0;
        if (commpairBean.getStarttime()== 0) {
            end=System.currentTimeMillis() / DateAppsUtils.RADIX1000;
            start=end-DateAppsUtils.DEFAULTINTERVAL_TENMINUTES;
            commpairBean.setStarttime(start);
            commpairBean.setEndtime(end);
        }
        
        if (commpairBean.getProtocolType()==0) {//默认正常情况
            //处理协议饼图情况
            if (commpairBean.getProtoPlanId()>0){//有选择协议
                ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                protoPlanBean.setId(commpairBean.getProtoPlanId());
                protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                if (protoPlanBean.getPort()==0) {
                    if (commpairBean.getServerport()==0) {//前台请求的是未识别
                        commpairBean.setServerport(0);
                        String serverportNotInSql=commpairService.portNotInFullSql();
                        commpairBean.setServerportNotInSql(StringUtils.isNotEmpty(serverportNotInSql)
                                ?" and "+serverportNotInSql:"");
                    } else {//点击一行的情况(协议为未识别，服务端端口为具体数值)
                        
                    }
                } else {//普通协议
                    int portNum=protoPlanBean.getPort();
                    commpairBean.setServerport(portNum);
                }
            }
        } else if (commpairBean.getProtocolType()==1) {//公有协议分布情况
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            String []protocolInfoArray= null;
            if (StringUtils.isNotEmpty(protocolInfo)){
                protocolInfoArray= protocolInfo.split(",");
            }
            //判断具体情况
            if (commpairBean.getProtocolInfo()!=null && "11106".equals(commpairBean.getProtocolInfo())) {//未识别情况
                commpairBean.setServerport(0);
                String serverportNotInSql=commpairService.portNotInFullSql();
                if (StringUtils.isNotEmpty(serverportNotInSql)){
                    commpairBean.setServerportNotInSql(" and "+serverportNotInSql);
                }
            } else {//单独情况   或   其他情况            
                if (protocolInfoArray!=null){
                    if (protocolInfoArray.length==1) {//单独情况
                        ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                        protoPlanBean.setId(Integer.parseInt(commpairBean.getProtocolInfo()));
                        protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                        int portNum=protoPlanBean.getPort();
                        commpairBean.setServerport(portNum);
                    } else if (protocolInfoArray.length>1) {//其他情况                       
                        commpairBean.setServerport(0);
                        String serverportNotInSql=commpairService.portNotInByProtoIds(protocolInfo);
                        if (StringUtils.isNotEmpty(serverportNotInSql)){
                            commpairBean.setServerportNotInSql(" and "+serverportNotInSql);
                        }
                    }
                }
            }
        } else if (commpairBean.getProtocolType()==2) {//自定义用户协议
            //ipPort Buf 
            StringBuffer ipPortBuf=new StringBuffer();
            //ip port集合
            List<AppIpPortBean> appIpPortBeanList=null;
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            if (StringUtils.isNotEmpty(protocolInfo) && !"0".endsWith(protocolInfo)) {//单独情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(Integer.parseInt(protocolInfo));
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    ipPortBuf.append(" and ");
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip="+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport="+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" or ");
                        }
                    }
                }
            } else {//未识别情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(0);
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    ipPortBuf.append(" and ");
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip <>"+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport <> "+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" and ");
                        }
                    }
                }                
            }            
            commpairBean.setServerIpPortSql(ipPortBuf.toString());
        }
        
        //根据流量排序功能
        KpisBean kpisBean=null;
        if(commpairBean.getKpiId()==0){
            kpisBean=plotService.getKpisByEthernetTraffic();
        }else{
            kpisBean=plotService.getKpisById(commpairBean.getKpiId());
        }
        commpairBean.setKpiName(kpisBean.getName());
        //隐式的传递了-triggerflag
        
        commpairList = this.getCommpairsDetail(commpairBean, commpairBean.getWatchpointId());

        return  commpairList;
    }
    
    /**
     * 
     * @Title: getCommpairsDetail
     * @Description: 查询通信对详情（有无观察点/开启地址库与否都使用该方法）
     * @param commpair
     * @param watchPointId
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getCommpairsDetail(CommpairBean commpair, int watchPointId) {
        long lidu=commpair.getLidu();
        List<CommpairBean> list=this.getCommpairDetailList(commpair, watchPointId);
        return this.packageCommpair(list, lidu, 2, commpair);
    }
    
    /**
     * 
     * @Title: toCommpairMergeCount
     * @Description: 查询聚合的告警总数
     * @param commpairBean
     * @param publicProtoPlanService
     * @param commpairService
     * @param serverManagementService
     * @return long
     * @author chensq
     */
    public long toCommpairMergeCount(
            CommpairBean commpairBean,
            PublicProtoPlanService publicProtoPlanService,
            CommpairService commpairService,
            ServerManagementService serverManagementService){
        //获取总数
        long mergeCount=0;
        
        //获取与处理时间
        long start=0;
        long end=0;
        if (commpairBean.getStarttime()== 0) {
            end=System.currentTimeMillis() / DateAppsUtils.RADIX1000;
            start=end-DateAppsUtils.DEFAULTINTERVAL_TENMINUTES;
            commpairBean.setStarttime(start);
            commpairBean.setEndtime(end);
        }

        //ipStr to ipLong
        if (commpairBean.getProtocolType()==0) {//默认正常情况
            //处理协议饼图情况
            if (commpairBean.getProtoPlanId()>0){//有选择协议
                ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                protoPlanBean.setId(commpairBean.getProtoPlanId());
                protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                if (protoPlanBean.getPort()==0) {//前台请求的是未识别协议
                    commpairBean.setServerport(0);
                    String serverportNotInSql=commpairService.portNotInFullSql();
                    if (StringUtils.isNotEmpty(serverportNotInSql)){
                        commpairBean.setServerportNotInSql(serverportNotInSql);
                    }
                } else {//普通协议
                    int portNum=protoPlanBean.getPort();
                    commpairBean.setServerport(portNum);
                }
            }
        } else if (commpairBean.getProtocolType()==1) {//公有协议分布情况
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            String []protocolInfoArray= null;
            if (StringUtils.isNotEmpty(protocolInfo)){
                protocolInfoArray= protocolInfo.split(",");
            }
            //判断具体情况
            if (commpairBean.getProtocolInfo()!=null && "11106".equals(commpairBean.getProtocolInfo())) {//未识别情况
                commpairBean.setServerport(0);
                String serverportNotInSql=commpairService.portNotInFullSql();
                if (StringUtils.isNotEmpty(serverportNotInSql)){
                    commpairBean.setServerportNotInSql(serverportNotInSql);
                }
            } else {//单独情况   或   其他情况            
                if (protocolInfoArray!=null){
                    if (protocolInfoArray.length==1) {//单独情况
                        ProtoPlanBean protoPlanBean =new ProtoPlanBean();
                        protoPlanBean.setId(Integer.parseInt(commpairBean.getProtocolInfo()));
                        protoPlanBean=publicProtoPlanService.getProtoPlanBeanById(protoPlanBean);
                        int portNum=protoPlanBean.getPort();
                        commpairBean.setServerport(portNum);
                    } else if (protocolInfoArray.length>1) {//其他情况                       
                        commpairBean.setServerport(0);
                        String serverportNotInSql=commpairService.portNotInByProtoIds(protocolInfo);
                        if (StringUtils.isNotEmpty(serverportNotInSql)){
                            commpairBean.setServerportNotInSql(serverportNotInSql);
                        }
                    }
                }
            }
        } else if (commpairBean.getProtocolType()==2) {//自定义用户协议
            //ipPort Buf 
            StringBuffer ipPortBuf=new StringBuffer();
            //ip port集合
            List<AppIpPortBean> appIpPortBeanList=null;
            //公有协议参数内容
            String protocolInfo=commpairBean.getProtocolInfo();
            if (StringUtils.isNotEmpty(protocolInfo) && !"0".endsWith(protocolInfo)) {//单独情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(Integer.parseInt(protocolInfo));
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip="+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport="+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" or ");
                        }
                    }
                }
            } else {//未识别情况
                appIpPortBeanList=serverManagementService.getAppIpPortByAppId(0);
                if (appIpPortBeanList!=null && appIpPortBeanList.size()>0) {
                    for (int x=0; x<appIpPortBeanList.size(); x++) {
                        String ip=appIpPortBeanList.get(x).getIp();
                        Integer port=appIpPortBeanList.get(x).getPort();
                        ipPortBuf.append(" ( ");
                        ipPortBuf.append(" l.serverip <>"+ip);
                        if (port!=0 && port!=null) {
                            ipPortBuf.append(" and ");
                            ipPortBuf.append(" l.serverport <> "+port);
                        }
                        ipPortBuf.append(" ) ");
                        if (x!=appIpPortBeanList.size()-1) {
                            ipPortBuf.append(" and ");
                        }
                    }
                }                
            }            
            commpairBean.setServerIpPortSql(ipPortBuf.toString());
        }
      
        //根据流量排序功能
        KpisBean kpisBean=null;
        if(commpairBean.getKpiId()==0){
            kpisBean=plotService.getKpisByEthernetTraffic();
        }else{
            kpisBean=plotService.getKpisById(commpairBean.getKpiId());
        }
        commpairBean.setKpiName(kpisBean.getName());
        //隐式的传递了-triggerflag
    
        //聚合结果
        mergeCount=this.getCommpairsMergeCount(commpairBean);
 
        return mergeCount;
    }
    
    /**
     * @Title: getCommpairs
     * @Description: 聚合通信对的列表方法
     * @param commpair
     * @return List<CommpairBean>
     * @author chensq
     */
    public long getCommpairsMergeCount(CommpairBean commpair) {
        long lidu=DateAppsUtils.getCommpairNewIntervalAuto(commpair.getStarttime(), commpair.getEndtime());
        commpair.setLidu(lidu);
        long mergeCount=0;
        if(commpair.getOpenIpLocationFlag()==0){// 不开启地址库
            mergeCount=this.getCommpairMergeCount(commpair, commpair.getWatchpointId());
        }else{// 开启地址库            
            //地图请求通信对的情况
            if(commpair.getAreaDictId()>0){
                //根据地址id查询地址中文信息
                //城市
                CommpairAreaDictBean commpairAreaDictBean3=new CommpairAreaDictBean();
                commpairAreaDictBean3=commpairAreaDictDao.getCommpairAreaDictById(commpair.getAreaDictId());
                //省份
                CommpairAreaDictBean commpairAreaDictBean2=new CommpairAreaDictBean();
                commpairAreaDictBean2=commpairAreaDictDao.getCommpairAreaDictById(commpairAreaDictBean3.getParentId());
                //国家
                CommpairAreaDictBean commpairAreaDictBean1=new CommpairAreaDictBean();
                commpairAreaDictBean1=commpairAreaDictDao.getCommpairAreaDictById(commpairAreaDictBean2.getParentId());
                //设置入通信对对象
                commpair.setCountryCn(commpairAreaDictBean1.getNameCn()); //国家
                commpair.setRegionCn(commpairAreaDictBean2.getNameCn()); //省份
                commpair.setCityCn(commpairAreaDictBean3.getNameCn()); //城市
            }
            mergeCount=this.getCommpairLocMergeCount(commpair, commpair.getWatchpointId());
        }
        return mergeCount;
    }
    
    /**
     * 
     * @Title: getCommpairMergeCount
     * @Description: 查询无地址库的聚合通信对总数
     * @param commpair
     * @param watchpointId
     * @return long
     * @author chensq
     */
    public long getCommpairMergeCount(CommpairBean commpair, int watchpointId){
        //返回
        long mergeCount=0;
        
        //多个观察点对应的表明集合
        Map<String, String> tableNameMap=null;

        //迭代观察点，获取表明
        List <WatchpointBean>  probeList=null;
        if (commpair.getWatchpointId()==0) {
            probeList=watchpointDao.getFindAll();
        }else{
            probeList=watchpointDao.getFindByIds(String.valueOf(watchpointId));
        }
        
        //获取表与观察点map
        if (probeList!=null && probeList.size()>0) {
            tableNameMap =new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                int tempProbeId= probeList.get(x).getId();
                //所有符合条件的表名的后缀
                List<String> commpairTables=CommpairTableNameUtil.commpairTables4WP(commpair, tempProbeId, commpairDao);
                //<tableName,WatchPoint>
                if (commpairTables!=null && commpairTables.size()>0) {
                    for (int y=0; y<commpairTables.size(); y++) {
                        tableNameMap.put(commpairTables.get(y), String.valueOf(tempProbeId));
                    }
                }
            }
        }
        
        //--------------------主sql使用start---------------------
        //聚合类型(主sql用)
        String unionGroupBySql="";
        if (commpair.getGroupType()>=0) {
            switch (commpair.getGroupType()){
                case 1:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ";
                    break;
                case 2:
                    unionGroupBySql=" GROUP BY watchpointId, clientip ";
                    break;
                case 3:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
                case 4:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,serverport ";
                    break;
                default:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
            }
        }
             
        //--------------------主sql使用end---------------------

        //参数bean
        CommpairFindParamBean commpairFindParamBean=new CommpairFindParamBean();
        
        //迭代拼接子sql
        StringBuffer unionSqlBuf=new StringBuffer();
        
        if (tableNameMap!=null && tableNameMap.size() >0) {
            int index=0;
            for (Map.Entry<String, String > entry : tableNameMap.entrySet()) {
                String key = entry.getKey();
                String watchPointIdValue = entry.getValue();
                String tableSuffix=key;
                //单个sql
                String itemSql=CommpairProvider.commpairMergeCountProviderSql(commpair, Integer.parseInt(watchPointIdValue), tableSuffix);
                
                unionSqlBuf.append(itemSql);
                
                if (index!=tableNameMap.size()-1) {
                    unionSqlBuf.append(" UNION ALL  ");
                }
                
                index++;
            }
            
            commpairFindParamBean.setWatchpointSql(" watchpointId  AS watchpointId, ");
            commpairFindParamBean.setUnionAllSql(unionSqlBuf.toString());
            commpairFindParamBean.setGroupBySql(unionGroupBySql);
            mergeCount=commpairDao.getCommpairUnionAllMergeLogsCount(commpairFindParamBean);
        }

        return mergeCount;

    }
    
    /**
     * 
     * @Title: getCommpairLocMergeCount
     * @Description: 有地址库的通信对聚合数量查询
     * @param commpair
     * @param watchpointId
     * @return long
     * @author chensq
     */
    public long getCommpairLocMergeCount(CommpairBean commpair, int watchpointId) {
        //返回总数
        long mergeCount=0;
        
        //多个观察点对应的表明集合
        Map<String, String> tableNameMap=null;

        //迭代观察点，获取表明
        List <WatchpointBean>  probeList=null;
        if (commpair.getWatchpointId()==0) {
            probeList=watchpointDao.getFindAll();
        }else{
            probeList=watchpointDao.getFindByIds(String.valueOf(watchpointId));
        }
        
        if (probeList!=null && probeList.size()>0) {
            tableNameMap =new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                int tempProbeId= probeList.get(x).getId();
                //所有符合条件的表名的后缀
                List<String> commpairTables=CommpairTableNameUtil.commpairTables4WP(commpair, tempProbeId, commpairDao);
                //<tableName,WatchPoint>
                if (commpairTables!=null && commpairTables.size()>0) {
                    for (int y=0; y<commpairTables.size(); y++) {
                        tableNameMap.put(commpairTables.get(y), String.valueOf(tempProbeId));
                    }
                }
            }
        }

        //--------------------主sql使用start---------------------
        //聚合类型(主sql用)
        String unionGroupBySql="";
        if (commpair.getGroupType()>=0){
            switch (commpair.getGroupType()) {
                case 1:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ";
                    break;
                case 2:
                    unionGroupBySql=" GROUP BY watchpointId, clientip ";
                    break;
                case 3:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
                case 4:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,serverport ";
                    break;
                default:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
            }
        }

        //--------------------主sql使用end---------------------
        
        //参数bean
        CommpairFindParamBean commpairFindParamBean=new CommpairFindParamBean();
        //地址库开关
        commpairFindParamBean.setOpenIpLocationFlagColumnSql(CommpairIpLocationUtil.getMainMergeIpLocationColumn(commpair));
        
        //迭代拼接子sql
        StringBuffer unionSqlBuf=new StringBuffer();
    
        //迭代拼接union sql
        if (tableNameMap!=null && tableNameMap.size()>0){
            int index=0;
            for (Map.Entry<String, String > entry : tableNameMap.entrySet()){
                String key = entry.getKey();
                String watchPointIdValue = entry.getValue();
                String tableSuffix=key;
                //单个sql
                String itemSql=CommpairProvider.commpairMergeCountLocProviderSql(commpair, Integer.parseInt(watchPointIdValue), tableSuffix);
                
                unionSqlBuf.append(itemSql);
                if (index!=tableNameMap.size()-1) {
                    unionSqlBuf.append(" UNION ALL  ");
                }
                index++;                
            }

            commpairFindParamBean.setWatchpointSql(" watchpointId  AS watchpointId, ");
            commpairFindParamBean.setUnionAllSql(unionSqlBuf.toString());
            commpairFindParamBean.setGroupBySql(unionGroupBySql);
            mergeCount=commpairDao.getCommpairUnionAllMergeLogsCount(commpairFindParamBean);
        }

        return mergeCount;

    }
    
    /**
     * 
     * @Title: getCommpairLocMergeList
     * @Description: 有地址库的通信对聚合查询
     * @param commpair
     * @param watchpointId
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getCommpairLocMergeList(CommpairBean commpair, int watchpointId) {
        
        //多个观察点对应的表明集合
        Map<String, String> tableNameMap=null;

        //迭代观察点，获取表明
        List <WatchpointBean>  probeList=null;
        if (commpair.getWatchpointId()==0) {
            probeList=watchpointDao.getFindAll();
        }else{
            probeList=watchpointDao.getFindByIds(String.valueOf(watchpointId));
        }
        
        if (probeList!=null && probeList.size()>0) {
            tableNameMap =new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                int tempProbeId= probeList.get(x).getId();
                //所有符合条件的表名的后缀
                List<String> commpairTables=CommpairTableNameUtil.commpairTables4WP(commpair, tempProbeId, commpairDao);
                //<tableName,WatchPoint>
                if (commpairTables!=null && commpairTables.size()>0) {
                    for (int y=0; y<commpairTables.size(); y++) {
                        tableNameMap.put(commpairTables.get(y), String.valueOf(tempProbeId));
                    }
                }
            }
        }

        //--------------------主sql使用start---------------------
        //聚合类型(主sql用)
        String unionGroupBySql="";
        if (commpair.getGroupType()>=0){
            switch (commpair.getGroupType()) {
                case 1:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ";
                    break;
                case 2:
                    unionGroupBySql=" GROUP BY watchpointId, clientip ";
                    break;
                case 3:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
                case 4:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,serverport ";
                    break;
                default:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
            }
        }

        //排序(main)
        CommpairWithOutKpiUtil withOutkpi=new CommpairWithOutKpiUtil();
        StringBuffer orderBySqlBuf= new StringBuffer();
        orderBySqlBuf.append(" order by ");
        //字段
        if(StringUtils.isNotEmpty(commpair.getKpiName())){
            int indexInfo=withOutkpi.withOutKpiTypeId(commpair.getKpiName());
            if(indexInfo>0){
                orderBySqlBuf.append(" snaptime ");
            }else{
                orderBySqlBuf.append(commpair.getKpiName());
            }
        }else{
            orderBySqlBuf.append(" snaptime ");
        }
        //排序
        if(commpair.getTriggerflag()==0){
            orderBySqlBuf.append(" desc ");
        }else{
            orderBySqlBuf.append(" asc ");
        }
        //--------------------主sql使用end---------------------
        
        //参数bean
        CommpairFindParamBean commpairFindParamBean=new CommpairFindParamBean();
        //地址库开关
        commpairFindParamBean.setOpenIpLocationFlagColumnSql(CommpairIpLocationUtil.getMainMergeIpLocationColumn(commpair));
        
        //迭代拼接子sql
        StringBuffer unionSqlBuf=new StringBuffer();
        
        //关联地址库(sub left join)
        List<CommpairBean> tempList=null;
        
        //迭代拼接union sql
        if (tableNameMap!=null && tableNameMap.size()>0){
            int index=0;
            for (Map.Entry<String, String > entry : tableNameMap.entrySet()){
                String key = entry.getKey();
                String watchPointIdValue = entry.getValue();
                String tableSuffix=key;
                //单个sql
                String itemSql=CommpairProvider.commpairMergeListLocProviderSql(commpair, Integer.parseInt(watchPointIdValue), tableSuffix);
                
                unionSqlBuf.append(itemSql);
                if (index!=tableNameMap.size()-1) {
                    unionSqlBuf.append(" UNION ALL  ");
                }
                index++;                
            }

            commpairFindParamBean.setWatchpointSql(" watchpointId  AS watchpointId, ");
            commpairFindParamBean.setUnionAllSql(unionSqlBuf.toString());
            commpairFindParamBean.setGroupBySql(unionGroupBySql);
            commpairFindParamBean.setOrderBySql(orderBySqlBuf.toString());
            if(commpair.getPropelling()==1){
                commpairFindParamBean.setPageSql("");
            }else{
                commpairFindParamBean.setPageSql("limit "+commpair.getStartNum()+","+commpair.getStepNum());
            }
            tempList=commpairDao.getCommpairUnionAllMergeLocationLogs(commpairFindParamBean);
        }

        return tempList;

    }
   
    /**
     * @Title: getCommpairMergeList
     * @Description:  无地址库的通信对聚合查询
     * @param commpair
     * @param watchpointId
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getCommpairMergeList(CommpairBean commpair, int watchpointId){

        //多个观察点对应的表明集合
        Map<String, String> tableNameMap=null;

        //迭代观察点，获取表明
        List <WatchpointBean>  probeList=null;
        if (commpair.getWatchpointId()==0) {
            probeList=watchpointDao.getFindAll();
        }else{
            probeList=watchpointDao.getFindByIds(String.valueOf(watchpointId));
        }
        
        //获取表与观察点map
        if (probeList!=null && probeList.size()>0) {
            tableNameMap =new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                int tempProbeId= probeList.get(x).getId();
                //所有符合条件的表名的后缀
                List<String> commpairTables=CommpairTableNameUtil.commpairTables4WP(commpair, tempProbeId, commpairDao);
                //<tableName,WatchPoint>
                if (commpairTables!=null && commpairTables.size()>0) {
                    for (int y=0; y<commpairTables.size(); y++) {
                        tableNameMap.put(commpairTables.get(y), String.valueOf(tempProbeId));
                    }
                }
            }
        }
        
        //--------------------主sql使用start---------------------
        //聚合类型(主sql用)
        String unionGroupBySql="";
        if (commpair.getGroupType()>=0) {
            switch (commpair.getGroupType()){
                case 1:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ";
                    break;
                case 2:
                    unionGroupBySql=" GROUP BY watchpointId, clientip ";
                    break;
                case 3:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
                case 4:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,serverport ";
                    break;
                default:
                    unionGroupBySql=" GROUP BY watchpointId, serverip ,clientip ";
                    break;
            }
        }
             
        //排序(main)
        CommpairWithOutKpiUtil withOutkpi=new CommpairWithOutKpiUtil();
        StringBuffer orderBySqlBuf= new StringBuffer();
        orderBySqlBuf.append(" order by ");
        //字段
        if(StringUtils.isNotEmpty(commpair.getKpiName())){
            int indexInfo=withOutkpi.withOutKpiTypeId(commpair.getKpiName());
            if(indexInfo>0){
                orderBySqlBuf.append(" snaptime ");
            }else{
                orderBySqlBuf.append(commpair.getKpiName());
            }
        }else{
            orderBySqlBuf.append(" snaptime ");
        }
        //排序
        if(commpair.getTriggerflag()==0){
            orderBySqlBuf.append(" desc ");
        }else{
            orderBySqlBuf.append(" asc ");
        }
        //--------------------主sql使用end---------------------

        //参数bean
        CommpairFindParamBean commpairFindParamBean=new CommpairFindParamBean();
        
        //迭代拼接子sql
        StringBuffer unionSqlBuf=new StringBuffer();
        
        List<CommpairBean> tempList=null;

        if (tableNameMap!=null && tableNameMap.size() >0) {
            int index=0;
            for (Map.Entry<String, String > entry : tableNameMap.entrySet()) {
                String key = entry.getKey();
                String watchPointIdValue = entry.getValue();
                String tableSuffix=key;
                //单个sql
                String itemSql=CommpairProvider.commpairMergeListProviderSql(commpair, Integer.parseInt(watchPointIdValue), tableSuffix);
                
                unionSqlBuf.append(itemSql);
                
                if (index!=tableNameMap.size()-1) {
                    unionSqlBuf.append(" UNION ALL  ");
                }
                
                index++;
            }
            
            commpairFindParamBean.setWatchpointSql(" watchpointId  AS watchpointId, ");
            commpairFindParamBean.setUnionAllSql(unionSqlBuf.toString());
            commpairFindParamBean.setGroupBySql(unionGroupBySql);
            commpairFindParamBean.setOrderBySql(orderBySqlBuf.toString());
            if(commpair.getPropelling()==1){
                commpairFindParamBean.setPageSql("");
            }else{
                commpairFindParamBean.setPageSql("limit "+commpair.getStartNum()+","+commpair.getStepNum());
            }
            tempList=commpairDao.getCommpairUnionAllMergeLogs(commpairFindParamBean);
        }

        return tempList;

    }

    
    /**
     * 
     * @Title: getCommpairDetailList
     * @Description: 通信对详情(有无地址库 有无观察点)
     * @param commpair
     * @param watchPointId
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getCommpairDetailList(CommpairBean commpair, int watchPointId){

        //多个观察点对应的表明集合
        Map<String, String> tableNameMap=null;

        //迭代观察点，获取表明
        List <WatchpointBean>  probeList=null;
        if (commpair.getWatchpointId()==0) {
            probeList=watchpointDao.getFindAll();
        }else{
            probeList=watchpointDao.getFindByIds(String.valueOf(watchPointId));
        }
        
        if (probeList!=null && probeList.size()>0) {
            tableNameMap =new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                int tempProbeId= probeList.get(x).getId();
                //所有符合条件的表名的后缀
                List<String> commpairTables=CommpairTableNameUtil.commpairTables4WP(commpair, tempProbeId, commpairDao);
                //<tableName,WatchPoint>
                if (commpairTables!=null && commpairTables.size()>0){
                    for (int y=0; y<commpairTables.size(); y++) {
                        tableNameMap.put(commpairTables.get(y), String.valueOf(tempProbeId));
                    }
                }
            }
        }

        //排序(main)
        CommpairWithOutKpiUtil withOutkpi=new CommpairWithOutKpiUtil();
        StringBuffer orderBySqlBuf= new StringBuffer();
        orderBySqlBuf.append(" order by ");
        //字段
        if(StringUtils.isNotEmpty(commpair.getKpiName())){
            int indexInfo=withOutkpi.withOutKpiTypeId(commpair.getKpiName());
            if(indexInfo>0){
                orderBySqlBuf.append(" snaptime ");
            }else{
                orderBySqlBuf.append(commpair.getKpiName());
            }
        }else{
            orderBySqlBuf.append(" snaptime ");
        }
        //排序
        if(commpair.getTriggerflag()==0){
            orderBySqlBuf.append(" desc ");
        }else{
            orderBySqlBuf.append(" asc ");
        }
        
        //具体查询

        //子网/
        String clientUideSql="";
        if (commpair.getClientId()>0) {
            clientUideSql=" AND l.clientUideId REGEXP  '\\\\."+commpair.getClientId()+"\\\\.' ";
        }
        //应用/服务端
        String serverUideSql="";
        if (commpair.getServerId()>0) {
            serverUideSql=" AND l.serverUideId REGEXP  '\\\\."+commpair.getServerId()+"\\\\.' ";
        }
        //端口
        String serverportSql="";
        if (commpair.getServerport()>0) {
            serverportSql=" AND l.serverport = "+commpair.getServerport()+" ";
        }
        //服务端ip
        String serveripSql="";
        if (StringUtils.isNotEmpty(commpair.getServerip())) {
            serveripSql=" AND l.serverip = inet_aton('"+commpair.getServerip()+"')";
        }
        //客户端ip
        String clientipSql="";
        if (StringUtils.isNotEmpty(commpair.getClientip())) {
            clientipSql=" AND l.clientip = inet_aton('"+commpair.getClientip()+"')";
        }

        //聚合类型(主sql用)
        String unionGroupBySql="";
        unionGroupBySql=serveripSql+clientipSql+serverportSql;

        //多个子sql
        StringBuffer unionSqlBuf=new StringBuffer();

        //子sql
        StringBuffer subSqlBuf=new StringBuffer();
        subSqlBuf.append("SELECT ");
        subSqlBuf.append("replaceWatchPointId AS watchpointId, ");
        subSqlBuf.append("l.clientUideId AS clientUideIds, ");
        subSqlBuf.append("l.serverUideId AS serverUideIds, ");
        subSqlBuf.append("l.snaptime AS snaptime, ");
        subSqlBuf.append("INET_NTOA(l.serverip) AS serverip, ");
        subSqlBuf.append("INET_NTOA(l.clientip) AS clientip, ");
        subSqlBuf.append("l.serverport AS serverport, ");
        // 具体指标
        subSqlBuf.append("l.ethernetTraffic AS ethernetTraffic, ");
        subSqlBuf.append("l.synPkts AS synPkts, "); // SYN包数
        subSqlBuf.append("l.synAckPkts AS synAckPkts, "); // 连接应答数量
        subSqlBuf.append("l.serverConDelay+l.responseDelay+l.loadDelay AS qos, ");
        subSqlBuf.append("l.serverDurDelay AS serverDurDelay, ");
        subSqlBuf.append("l.clientDurDelay AS clientDurDelay, ");

        subSqlBuf.append("l.rtt AS rtt, ");
        subSqlBuf.append("l.serverConDelay AS serverConDelay, ");
        subSqlBuf.append("l.clientConDelay AS clientConDelay, ");
        subSqlBuf.append("l.responseDelay AS responseDelay, ");
        subSqlBuf.append("l.loadDelay AS loadDelay, ");

        subSqlBuf.append("l.serverRetransDelay AS serverRetransDelay, ");
        subSqlBuf.append("l.clientRetransDelay AS clientRetransDelay, ");
        subSqlBuf.append("l.tcpTraffic AS tcpTraffic, ");
        subSqlBuf.append("l.udpTraffic AS udpTraffic, ");
        subSqlBuf.append("l.inTraffic AS inTraffic, "); // 入流量
        subSqlBuf.append("l.outTraffic AS outTraffic, "); // 出流量
        subSqlBuf.append("l.rstPkts AS rstPkts, "); // rst包数
        
        subSqlBuf.append("l.finPkts AS finPkts, "); // fin包数
        subSqlBuf.append("l.fin1Pkts AS fin1Pkts, "); // 主动关闭包数
        subSqlBuf.append("l.fin2Pkts AS fin2Pkts, "); // 被动关闭包数
        subSqlBuf.append("l.ethernetPkts AS ethernetPkts, "); // 数据包个数(与rrd名称不一致)
        //--需要计算s--
        subSqlBuf.append("(l.netPktLost/l.ethernetPkts)*100 AS netPktLostRatio, "); // 网络丢包率
        subSqlBuf.append("(l.serverPktLost/l.serverPkt)*100 AS serverPktLostRatio, "); // 服务端丢包率
        subSqlBuf.append("(l.clientPktLost/l.clientPkt)*100 AS clientPktLostRatio, "); // 客户端丢包率
        //--需要计算e--
        subSqlBuf.append("l.tinyPkts AS tinyPkts, "); // 小包个数(与rrd名称不一致)
        //--需要计算s--
        subSqlBuf.append("(l.tinyPkts/l.ethernetPkts)*100 AS tinyPktsRatio, "); // 小包比率 (tinyPkts/ethernetPkts)
        //--需要计算e--
          
        //地址库开关
        subSqlBuf.append(CommpairIpLocationUtil.getSubDetailIpLocationColumn(commpair));
        
        subSqlBuf.append("l.ethernetTraffic/8/l.ethernetPkts AS avgPktsLen, "); // 平均包长
        subSqlBuf.append("l.zeroWinCount AS zeroWinCount "); // 零窗口包数
        
        subSqlBuf.append("FROM  ");
        subSqlBuf.append("replaceTable l ");
        
        //地址库开关
        subSqlBuf.append(CommpairIpLocationUtil.getMainDetailIpLocationLeftJoin(commpair, "l."));
        
        subSqlBuf.append("WHERE ");
        subSqlBuf.append(" 1=1 ");

        //根据合并类型判断sql
        subSqlBuf.append(unionGroupBySql);
        
        subSqlBuf.append("and l.snaptime>= "+commpair.getStarttime()+" ");
        subSqlBuf.append("and l.snaptime<= "+commpair.getEndtime()+" ");
        if (StringUtils.isNotEmpty(commpair.getServerportNotInSql())) {
            subSqlBuf.append(commpair.getServerportNotInSql());
        }
        if (StringUtils.isNotEmpty(commpair.getServerIpPortSql())) {
            subSqlBuf.append(commpair.getServerIpPortSql());
        }
        subSqlBuf.append(serverUideSql);
        subSqlBuf.append(clientUideSql);

        List<CommpairBean> tempList=null;

        if (tableNameMap!=null && tableNameMap.size() >0) {
            int index=0;
            for (Map.Entry<String, String > entry : tableNameMap.entrySet()) {
                String key = entry.getKey();
                String watchPointIdValue = entry.getValue();
                String tableSuffix=key;
                String singleSQL=  subSqlBuf.toString().replace("replaceTable", tableSuffix).
                        replace("replaceWatchPointId", watchPointIdValue);
                unionSqlBuf.append(singleSQL);
                if (index!=tableNameMap.size()-1) {
                    unionSqlBuf.append(" UNION ALL ");
                }
                index++;
            }
            unionSqlBuf.append(orderBySqlBuf.toString());
            tempList=commpairDao.getCommpairUnionAllDetailLogs(unionSqlBuf.toString());
        }

        return tempList;

    }
    
    
    /**
     * @Title: packageCommpair
     * @Description: 对集合进行相应处理
     * @param list 
     * @param lidu 
     * @param type 
     * @param commpair 
     * @return List<CommpairBean>
     */
    public List<CommpairBean> packageCommpair(List<CommpairBean> list, long lidu, int type, CommpairBean commpair){
        //最终返回集合(不分区观察点)
        List<CommpairBean> finalList=null;

        //获取所有观察点map
        Map<String, String> watchPointMap =this.getWatchPointMap();
        
        //获取所有协议map
        Map<String, String> protocolMap =this.getProtocolMap();
        
        //获取所有协议对象map
        Map<String, ProtoPlanBean> protocolObjMap =this.getProtocolObjMap();        

        if (list !=null && list.size()>0){
            finalList=new ArrayList<CommpairBean>();
        }

        //迭代通信对列表
        CommpairBean tempObj=null;
        if (list!=null && list.size()>0) {
            for (int x=0; x<list.size(); x++) {
                tempObj= list.get(x);
                //id 
                tempObj.setId(x);

                //粒度
                tempObj.setLidu(lidu);

                //观察点名称
                tempObj.setWatchpointName(watchPointMap.get(String.valueOf(tempObj.getWatchpointId())));

                //排序使用(聚合或者详情)
                if (type==1) {//聚合情况
                    if (tempObj.getStarttime() == tempObj.getEndtime()) {//未能继续聚合的情况
                        tempObj.setSortTime(tempObj.getStarttime());
                    } else {//聚合多条的情况
                        tempObj.setSortTime(tempObj.getStarttime());
                    }
                } else {//列表情况
                    tempObj.setSortTime(tempObj.getSnaptime());
                }

                //获取服务端客户端名称
                Map<String, String> business11Map=getAppBusinessByModuleId(11); // 客户端
                String clientName=getServiceOrClientName(tempObj.getClientUideIds(), business11Map);
                
                Map<String, String> business12Map=getAppBusinessByModuleId(12); // 服务端 
                String serverName=getServiceOrClientName(tempObj.getServerUideIds(), business12Map);
                
                tempObj.setClientName(clientName);
                tempObj.setServerName(serverName);        
                
                //聚合时间段范围
                String startEndStr="";
                if (type==1) {//聚合情况
                    if (tempObj.getStarttime() == tempObj.getEndtime()) {//未能继续聚合的情况
                        startEndStr=String.valueOf(tempObj.getSnaptime());
                    } else {//聚合多条的情况
                        startEndStr= String.valueOf(tempObj.getStarttime())+"-"
                                    +String.valueOf(tempObj.getEndtime());
                    }
                } else {//列表情况
                    startEndStr=String.valueOf(tempObj.getSnaptime());
                }
                tempObj.setStartEndstr(startEndStr);
              
                //通信协议  icpm是否为65536，其他两个照旧
                if(tempObj.getServerport()==65536){
                    tempObj.setCommunicationProtocol("ICMP");
                }else{
                    tempObj.setCommunicationProtocol(tempObj.getTcpTraffic()>0?"TCP":"UDP");
                }
                
                //根据聚合条件设置服务端ip 客户端ip 端口  协议
                if (type==1){
                    if (commpair.getGroupType()>=0){
                        tempObj=this.toPerfectTempObj(protocolObjMap, commpair.getGroupType(), tempObj, commpair);
                    }
                } else {//列表情况
                    String serverportprotocol=protocolMap.get(String.valueOf(tempObj.getServerport()));
                    if (serverportprotocol!=null) {
                        tempObj.setProtocol(serverportprotocol);
                    } else {
                        tempObj.setProtocol("未识别协议");
                    }
                }
                
                finalList.add(tempObj);

            }

        }
        return finalList;

    }
    
    
    /**
     * @Title: toPerfectTempObj
     * @Description: 完善对象(根据聚合方式与数据返回情况完善对象)
     * @param protocolObjMap
     * @param groupType
     * @param tempObj
     * @param commpair
     * @return CommpairBean
     * @author chensq
     */
    public CommpairBean toPerfectTempObj(
            Map<String, ProtoPlanBean> protocolObjMap,
            int groupType,
            CommpairBean tempObj,
            CommpairBean commpair){
        
        long serverIp=0;
        if(StringUtils.isNotEmpty(tempObj.getServerip())){
            serverIp=IpUtils.ipFromStringToLong(tempObj.getServerip());
        }
        long clientIp=0;
        if(StringUtils.isNotEmpty(tempObj.getClientip())){
            clientIp=IpUtils.ipFromStringToLong(tempObj.getClientip());
        }                
        long serverIpColumn=tempObj.getServerIpColumn();           
        long clientIpColumn=tempObj.getClientIpColumn();
         
        long clientLocId=tempObj.getClientLocId();
        long clientLocIdColumn=tempObj.getClientLocIdColumn();
        
        switch (groupType) {
     
            case 1://服务端ip
                if (clientIp!=clientIpColumn) {
                    tempObj.setClientip("");
                }
                tempObj=this.setServerPortAndProtocol(protocolObjMap, tempObj, commpair);
                break;
            case 2://客户端ip
                if (serverIp!=serverIpColumn) {
                    tempObj.setServerip("");
                }
                tempObj=this.setServerPortAndProtocol(protocolObjMap, tempObj, commpair);
                break;
            case 3://服务端ip 客户端ip            
                tempObj=this.setServerPortAndProtocol(protocolObjMap, tempObj, commpair);
                break;
            case 4://服务端ip 端口                
                if (clientIp!=clientIpColumn) {
                    tempObj.setClientip("");
                }
                tempObj=this.setServerPortAndProtocol(protocolObjMap, tempObj, commpair);
                break;
            default ://服务端ip 客户端ip
                tempObj=this.setServerPortAndProtocol(protocolObjMap, tempObj, commpair);
                break; 
        }
        
        if (commpair.getOpenIpLocationFlag()!=0) {
          //判断ip地址库信息显示与否
            if (clientLocId!=clientLocIdColumn) {
                tempObj.setCountryCn("");
                tempObj.setRegionCn("");
                tempObj.setCityCn("");
                tempObj.setIspCn("");
            }
        }
        
        return tempObj;
    }
   
    
    /**
     * @Title: setServerPortAndProtocol
     * @Description: 设置端口，协议的信息
     * @param protocolObjMap
     * @param tempObj
     * @param commpair
     * @return CommpairBean
     * @author chensq
     */
    public CommpairBean setServerPortAndProtocol(Map<String, ProtoPlanBean> protocolObjMap,
            CommpairBean tempObj,
            CommpairBean commpair){
        //协议(区分未识别)
        ProtoPlanBean tempProtoPlanBean=protocolObjMap.get(String.valueOf(tempObj.getServerport()));
        String serverportprotocol=tempProtoPlanBean==null?null:tempProtoPlanBean.getName();
        //迭代对象
        long serverport=tempObj.getServerport();
        long serverPortColumn=tempObj.getServerPortColumn();
        String serverportConcat=tempObj.getServerportConcat();
        
        //在协议map中存在 && 内部数据一致 
        if (serverportprotocol!=null && serverport==serverPortColumn) {
            tempObj.setProtoPlanId(tempProtoPlanBean.getId());
            tempObj.setServerport(serverport);
            tempObj.setProtocol(serverportprotocol);
        } else {
            if (serverportprotocol==null  && serverport!=serverPortColumn) {//协议在map中找不到 && 内部数据不一致 
                String []serverportConcatArray=serverportConcat.split(",");
                boolean unknownProtocolFlag=false;
                for (int x=0; x<serverportConcatArray.length; x++) {
                    ProtoPlanBean tempProtoPlanitemBean=protocolObjMap.get(serverportConcatArray[x]);
                    if (tempProtoPlanitemBean!=null) {
                        unknownProtocolFlag=true;
                    }
                }
                if (unknownProtocolFlag){//内部不全是未识别(虽然端口不一样)
                    tempObj.setProtoPlanId(commpair.getProtoPlanId());
                    tempObj.setServerport(commpair.getServerport());
                    tempObj.setProtocol("");
                } else {//内部全是未识别(虽然端口不一样)
                    tempObj.setProtoPlanId(11106);
                    tempObj.setServerport(0);
                    tempObj.setProtocol("未识别协议");
                }
            } else {
                if (serverportprotocol==null) {//协议在map中找不到
                    tempObj.setProtoPlanId(11106);
                    tempObj.setServerport(serverport==serverPortColumn?serverport:0);
                    tempObj.setProtocol("未识别协议");
                }
                if (serverport!=serverPortColumn) {//内部数据不一致 
                    tempObj.setProtoPlanId(commpair.getProtoPlanId());
                    tempObj.setServerport(commpair.getServerport());
                    tempObj.setProtocol("");
                }
            }
        }
         
        return tempObj;
    }
     
    
    /**
     * 
     * @Title: getAppBusinessByModuleId
     * @Description: 根据模块id查询其下的所有应用
     * @param moduleId
     * @return Map<String,String> id,name
     * @author chensq
     */
    public Map<String, String> getAppBusinessByModuleId(Integer moduleId){
        Map<String, String> map=new HashMap<String, String>();
        List<AppBusinessBean> appBusinessList=appBusinessDao.selectAppBusinessesByModuleId(moduleId);
        if(appBusinessList!=null && appBusinessList.size()>0){
            for(int x=0; x<appBusinessList.size(); x++){
                map.put(String.valueOf(appBusinessList.get(x).getId()), appBusinessList.get(x).getName());
            }
        }
        return map;
    }
    
    
    /**
     * @Title: getServiceOrClientName
     * @Description: 根据对象bean，以及所有业务信息，查询名称
     * @param busName
     * @param businessMap
     * @return String serverName/clientName:具体名称信息
     * @author chensq
     */
    public String getServiceOrClientName(String busName, Map<String, String> businessMap){
        String firstBusName="";
        String []busIdArray= busName.split("\\.");
        for(int x=0; x<busIdArray.length; x++){
            String tempStr=busIdArray[x];
            if(!"0".equalsIgnoreCase(tempStr) && StringUtils.isNotEmpty(tempStr) && "".endsWith(firstBusName)){
                firstBusName=businessMap.get(tempStr);
            }
        }
        return firstBusName;
    }
    
    /**
     * @Title: portNotInFullSql
     * @Description: 获取全部协议 not in sql
     * @return String
     * @author chensq
     */
    public String portNotInFullSql(){
        CommpairProtocolUtil cpUtil=new CommpairProtocolUtil();
        List<ProtoPlanBean> protoPlanList= publicProtoPlanDao.getProtoPlanList();
        return cpUtil.portNotInFullSql(publicProtoPlanDao, protoPlanList);
    }
     
    
    /**
     * @Title: portNotInByProtoIds
     * @Description: 获取部分协议not in sql
     * @param ids
     * @return String
     * @author chensq
     */
    public String portNotInByProtoIds(String ids){
        CommpairProtocolUtil cpUtil=new CommpairProtocolUtil();
        List<ProtoPlanBean> protoPlanNotInList =publicProtoPlanDao.getProtoPlanNotInList(ids);
        return cpUtil.portNotInFullSql(publicProtoPlanDao, protoPlanNotInList);
    }
   
    
    /**
     * @Title: getWatchPointMap
     * @Description: 获取观察点为map集合
     * @return Map<String,String>
     * @author chensq
     */
    public Map<String, String> getWatchPointMap(){
        List<WatchpointBean> probeList= watchpointDao.getFindAll();
        Map<String, String> map=null;
        if (probeList!=null && probeList.size()>0) {
            map=new HashMap<String, String>();
            for (int x=0; x<probeList.size(); x++) {
                map.put(String.valueOf(probeList.get(x).getId()), probeList.get(x).getName());
            }
        }
        return map;
    }
    
    
    /**
     * @Title: getProtocolMap
     * @Description: 获取协议为map集合
     * @return Map<String,String>
     * @author chensq
     */
    public Map<String, String> getProtocolMap(){
        List<ProtoPlanBean> protoPlanList= publicProtoPlanDao.getProtoPlanList();
        Map<String, String> map=null;
        if (protoPlanList!=null && protoPlanList.size()>0){
            map=new HashMap<String, String>();
            for (int x=0; x<protoPlanList.size(); x++) {
                map.put(String.valueOf(protoPlanList.get(x).getPort()), protoPlanList.get(x).getName());
            }
        }
        return map;
    }
    
    
    /**
     * @Title: getProtocolObjMap
     * @Description: 获取协议为map集合
     * @return Map<String,ProtoPlanBean>
     * @author chensq
     */
    public Map<String, ProtoPlanBean> getProtocolObjMap(){
        List<ProtoPlanBean> protoPlanList= publicProtoPlanDao.getProtoPlanList();
        Map<String, ProtoPlanBean> map=null;
        if (protoPlanList!=null && protoPlanList.size()>0){
            map=new HashMap<String, ProtoPlanBean>();
            for (int x=0; x<protoPlanList.size(); x++) {
                map.put(String.valueOf(protoPlanList.get(x).getPort()), protoPlanList.get(x));
            }
        }
        return map;
    }    
    
    
    /**
     * @Title: getRemoteCommpairData
     * @Description: 获取通信队信息 (center)
     * @param commpairBean
     * @param url
     * @return List<CommpairBean>
     * @author chensq
     */
    public List<CommpairBean> getRemoteCommpairData(CommpairBean commpairBean, String url) {
        List<CommpairBean> list = null;
        Map<String, Object> params = BeanUtils.beanToMap(commpairBean);
        String data = null;
        Integer centerId = commpairBean.getIpmCenterId();
        CenterIpBean bean = null;
        if (centerId != null) {
            bean = centerService.getCenterById(centerId);
            data = centerService.getRemoteData(params, centerId, url);
            if (data == null) {
                list = new ArrayList<CommpairBean>();
            } else {
                list = this.commStrToList(data, bean);
            }
        } else {
            list = new ArrayList<CommpairBean>();
            List<CenterIpBean> center = centerService.getAllAccessInfo();
            List<CommpairBean> tmpList = null;
            for (int i = 0, len = center.size(); i < len; i ++) {
                bean = center.get(i);
                data = centerService.getRemoteData(params, bean.getId(), url);
                if (data != null) {
                    tmpList = this.commStrToList(data, bean);
                    list.addAll(tmpList);
                }
            }
        }
        
        return list;
    }
    
    /**
     * @Title: getRemoteCommpairMergeCountData
     * @Description: 聚合总数 (center)
     * @param commpairBean 参数
     * @param url URL
     * @return long  总数
     * @author WWW
     */
    public long getRemoteCommpairMergeCountData(CommpairBean commpairBean, String url) {
        long count = 0;
        Map<String, Object> params = BeanUtils.beanToMap(commpairBean);
        String data = null;
        Integer centerId = commpairBean.getIpmCenterId();
        CenterIpBean bean = null;
        if (centerId != null) {
            bean = centerService.getCenterById(centerId);
            data = centerService.getRemoteData(params, centerId, url);
            if (data != null) {
                count = Long.parseLong(data);
            }
        } else {
            List<CenterIpBean> center = centerService.getAllAccessInfo();
            for (int i = 0, len = center.size(); i < len; i ++) {
                bean = center.get(i);
                data = centerService.getRemoteData(params, bean.getId(), url);
                if (data != null) {
                    count += Long.parseLong(data);
                }
            }
        }
        
        return count;
    }
    
    
    /**
     * @Title: commStrToList
     * @Description: 字符串转为List
     * @param data JSON字符串
     * @param ciBean 
     * @return List<CommpairBean>
     * @author www
     */
    private List<CommpairBean> commStrToList(String data, CenterIpBean ciBean) {
        List<CommpairBean> list = null;
        JSONArray arr = JSONArray.parseArray(data);
        CommpairBean bean = null;
        JSONObject obj = null;
        if (arr != null) {
            list = new ArrayList<CommpairBean>();
            for (int i = 0, len = arr.size(); i < len; i ++) {
                obj = arr.getJSONObject(i);
                bean = JSONObject.toJavaObject(obj, CommpairBean.class);
                bean.setIpmCenterId(ciBean.getId());
                bean.setIpmCenterName(ciBean.getName());
                list.add(bean);
            }
        }
        
        return list;
    }

}
