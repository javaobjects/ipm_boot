/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:WatchpointServer
 *创建人:wjm    创建时间:2017年9月1日
 */
package com.protocolsoft.watchpoint.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.app.bean.ParamBean;
import com.protocolsoft.app.bean.PlotSimpleBean;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.enumeration.ServiceRuleType;
import com.protocolsoft.common.service.impl.AppBusinessService;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.PlotOptionBean;
import com.protocolsoft.kpi.bean.PlotTypeBean;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.service.ProtoPlanService;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.bean.UserConfigureBean;
import com.protocolsoft.user.dao.AuthorizeJurisDao;
import com.protocolsoft.user.dao.UserConfigureDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.NetworkBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;
import com.protocolsoft.word.service.TimerReportDetailService;

/**
 * 
 * @ClassName: WatchpointService
 * @Description: 观察点server层
 * @author wangjianmin
 *
 */
@Service
public class WatchpointService {
    
    /**
     * WatchpointDao 对象
     */
    @Autowired
    private WatchpointDao dao;
    
    /**
     * AuthorizeJurisDao
     */
    @Autowired
    private AuthorizeJurisDao authorizeJurisDao;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 注入
     */
    @Autowired
    private UserConfigureDao userConfigureDao;
    
    /**
     *  注入
     */
    @Autowired
    private PlotService plotService;
    
    /**
     * 告警阈值设置  service
     */
    @Autowired
    private AlarmSetService alarmSetService;
   
    /**
     * 告警阈值设置业务逻辑层对象
     */
    @Autowired
    private AlarmLogService alarmLogService;
    
    /**
     * 业务信息表
     */
    @Autowired
    private AppBusinessService appBusinessService;
    
    /**
     * appBusinessDao注入
     */
    @Autowired(required=false)
    private AppBusinessDao appBusinessDao;
    
    /**
     * 协议表
     */
    @Autowired(required = false)
    private ProtoPlanService protoPlanService;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 权限
     */
    @Autowired
    private AuthorizeJurisService jurisService;
    
    /**
     * 报表
     */
    @Autowired(required = false)
    private TimerReportDetailService reportService;

   
    /**
     * 
     * @Title: getFindAll
     * @Description: 查询所有观察点业务
     * @param request  请求session信息
     * @return List<Map<String,String>>
     * @author wangjianmin
     */
    public List<Map<String, Object>> getFindAll(HttpServletRequest request){
        //获取所有观察点业务信息
        List<WatchpointBean> list = this.getWatchpointInfo(request);
        //保存网卡名称，防止网卡名称重复
        Set<String> didSet = new HashSet<String>();
        //拼接数据格式
        Map<String, Object> m = new HashMap<String, Object>();
        //最终数据格式
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        //网卡信息对象
        List<NetworkBean> listNetwork=null;
        for (WatchpointBean bean : list) {
            m = new HashMap<String, Object>();
            didSet.clear();                             //每次先清空保存的网卡名称
            m.put("id", bean.getId());                  //业务编号
            m.put("name", bean.getName());              //业务名称
            m.put("checked", bean.getChecked());        //是否选中
            m.put("bandwidth", bean.getBandwidth());    //带宽
            m.put("vid", bean.getVid());
            m.put("vxid", bean.getVxid());
            //处理网卡名称（多个）
            String[] didArr = bean.getDid().split(",");
            //循环得到网卡的id
            for (int i = 0; i < didArr.length; i++){
                listNetwork = dao.getNetworkById(didArr[i]);  //根据id得到网卡信息
                //循环得到网卡名称
                for (NetworkBean netbean : listNetwork) { 
                    didSet.add(netbean.getName()); //保存网卡名称
                }
            }
            //前后各截取一个
            String didStr=didSet.toString().substring(1, didSet.toString().length()-1);
            m.put("did", didStr);                         //网卡名称
            m.put("lid1", bean.getLid1());                //MPLS LABLE 1 
            m.put("lid2", bean.getLid2());                //MPLS LABLE 2 
            m.put("lid3", bean.getLid3());                //MPLS LABLE 3 
            m.put("lid4", bean.getLid4());                //MPLS LABLE 4 
            m.put("lid5", bean.getLid5());                //MPLS LABLE 5 
            m.put("ip", bean.getIp());                    //租户IP
            m.put("port", bean.getPort());                //租户端口
            m.put("contacts", bean.getContacts());        //联系人
            m.put("telephone", bean.getTelephone());      //电话
            m.put("email", bean.getEmail());              //邮箱
            m.put("validterm", bean.getValidterm());      //服务时限（就是有效限期）
            m.put("isLocal", bean.isLocal());             //是否为本地观察点
            //保存拼接好的数据格式
            dataList.add(m);

        }        
        return dataList;
    }

    /**
     * 
     * @Title: getWatchpointInfo
     * @Description: 获取观察点信息
     * @param request 请求session信息
     * @return List<WatchpointBean>
     * @author www
     */
    public List<WatchpointBean> getWatchpointInfo(HttpServletRequest request) {
        List<WatchpointBean> list = null;
        String ids = null;
        String remoteId = request.getParameter("remoteId");
        if (remoteId == null) { // 本地请求
            SystemUserBean userBean = systemUserService.getSessionUserBean(request);
            if (userBean.getRoleId() == 1) {
                list = dao.getFindAll();
            } else {
                ids = jurisService.selectModuleRole(userBean.getId(), 10, 1);
                list = dao.getFindByIds(ids);
            }
        } else if(CenterIpService.REMOTEID.equals(remoteId)) {
            ids = request.getParameter("remoteBusiIds"); 
            if (ids == null || ids.equals("")) {
                list = dao.getFindAll();
            } else {
                list = dao.getFindByIds(ids);
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getWatchpointInfo
     * @Description: 获取观察点信息
     * @param centerId 接入IP编号
     * @param userId 用户编号
     * @return List<WatchpointBean>
     * @author www
     */
    public List<WatchpointBean> getWatchpointInfo(int centerId, int userId) {
        List<WatchpointBean> list = null;
        if (centerId != 0 && userId != 0) {
            SystemUserBean userBean = systemUserService.getSessionUserBean(null);
            if (userBean.getRoleId() == 1) {
                list = dao.getFindAll();
            } else {
                String ids = jurisService.selectModuleRole(userBean.getId(), 10, centerId);
                list = dao.getFindByIds(ids);
            }
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getWatchpointInfo
     * @Description: 获取观察点信息
     * @return List<WatchpointBean>
     * @author www
     */
    public List<WatchpointBean> getWatchpointInfo() {
        List<WatchpointBean> list = dao.getFindAll();
        
        return list;
    }
    
    /**
     * 
     * @Title: getWatchpointInfo
     * @Description: 获取观察点信息
     * @param ids 编号
     * @return List<WatchpointBean>
     * @author WWW
     */
    public List<WatchpointBean> getWatchpointInfo(String ids) {
        List<WatchpointBean> list = null;
        if (ids != null && !ids.equals("")) {
            list = dao.getFindByIds(ids);
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: getNetworkAll
     * @Description: 获取所有网卡信息
     * @return Map<String,Object>
     * @author wangjianmin
     */
    public Map<String, Object> getNetworkAll(){
        //获取所有网卡信息
        List<NetworkBean> list = dao.getNetworkAll();
        //存放拼接数据格式
        Map<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> vidMap = new HashMap<String, Object>();
        Map<String, Object> vxidMap = new HashMap<String, Object>();
        Map<String, Object> lid1Map = new HashMap<String, Object>();
        Map<String, Object> lid2Map = new HashMap<String, Object>();
        Map<String, Object> lid3Map = new HashMap<String, Object>();
        Map<String, Object> lid4Map = new HashMap<String, Object>();
        Map<String, Object> lid5Map = new HashMap<String, Object>();
        //存放拼接的数据格式
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> vidList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> vxidList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lid1List = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lid2List = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lid3List = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lid4List = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lid5List = new ArrayList<Map<String, Object>>();
        
        Set<String> vidSet=new HashSet<String>();  //存放分割好的 v VLAN
        Set<String> vxidSet=new HashSet<String>(); //存放分割好的 vx VLAN
        Set<String> lid1Set=new HashSet<String>(); //存放分割好的  MPLS LABLE 1
        Set<String> lid2Set=new HashSet<String>(); //存放分割好的 MPLS LABLE 2
        Set<String> lid3Set=new HashSet<String>(); //存放分割好的  MPLS LABLE 3
        Set<String> lid4Set=new HashSet<String>(); //存放分割好的  MPLS LABLE 4
        Set<String> lid5Set=new HashSet<String>(); //存放分割好的  MPLS LABLE 5 
        //最终的数据格式
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            m = new HashMap<String, Object>();
            m.put("id", list.get(i).getId());     //网卡id
            m.put("name", list.get(i).getName()); //网卡名称
            dataList.add(m);
            
            String [] vid = list.get(i).getVid().split(",");   //v VLAN
            String [] vxid = list.get(i).getVxid().split(","); //vx VLAN
            String [] lid1 = list.get(i).getLid1().split(","); //MPLS LABLE 1
            String [] lid2 = list.get(i).getLid2().split(","); //MPLS LABLE 2
            String [] lid3 = list.get(i).getLid3().split(","); //MPLS LABLE 3
            String [] lid4 = list.get(i).getLid4().split(","); //MPLS LABLE 4
            String [] lid5 = list.get(i).getLid5().split(","); //MPLS LABLE 5
            
            //v VLAN
            if (StringUtils.isNotEmpty(list.get(i).getVid())){
                for (int j = 0; j < vid.length; j++){
                    if (!vidSet.contains(vid[j])){
                        vidSet.add(vid[j]);
                    }
                }
            }
            //vx VLAN
            if (StringUtils.isNotEmpty(list.get(i).getVxid())){
                for (int j = 0; j < vxid.length; j++){
                    if (!vxidSet.contains(vxid[j])){
                        vxidSet.add(vxid[j]);
                    }
                }           
            }
            //MPLS LABLE 1
            if (StringUtils.isNotEmpty(list.get(i).getLid1())){
                for (int j = 0; j < lid1.length; j++){
                    if (!lid1Set.contains(lid1[j])){
                        lid1Set.add(lid1[j]);
                    }
                }
            }
            //MPLS LABLE 2
            if (StringUtils.isNotEmpty(list.get(i).getLid2())){
                for (int j = 0; j < lid2.length; j++){
                    if (!lid2Set.contains(lid2[j])){
                        lid2Set.add(lid2[j]);
                    }
                }
            }
            //MPLS LABLE 3
            if (StringUtils.isNotEmpty(list.get(i).getLid3())){
                for (int j = 0; j < lid3.length; j++){
                    if (!lid3Set.contains(lid3[j])){
                        lid3Set.add(lid3[j]);
                    }
                }
            }
            //MPLS LABLE 4
            if (StringUtils.isNotEmpty(list.get(i).getLid4())){
                for (int j = 0; j < lid4.length; j++){
                    if (!lid4Set.contains(lid4[j])){
                        lid4Set.add(lid4[j]);
                    }
                }
            }                      
            //MPLS LABLE 5
            if (StringUtils.isNotEmpty(list.get(i).getLid5())){
                for (int j = 0; j < lid5.length; j++){
                    if (!lid5Set.contains(lid5[j])){
                        lid5Set.add(lid5[j]);
                    }
                }
            }
            
        }
        //前后各减一
        String  vidStr=vidSet.toString().substring(1, vidSet.toString().length()-1);
        vidMap.put("vid", vidStr);
        vidList.add(vidMap);
       
        String  vxidStr = vxidSet.toString().substring(1, vxidSet.toString().length()-1);
        vxidMap.put("vxid", vxidStr);
        vxidList.add(vxidMap);
      
        String  lid1Str = lid1Set.toString().substring(1, lid1Set.toString().length()-1);
        lid1Map.put("lid1", lid1Str);
        lid1List.add(lid1Map);
        
        String  lid2Str = lid2Set.toString().substring(1, lid2Set.toString().length()-1);
        lid2Map.put("lid2", lid2Str);
        lid2List.add(lid2Map);
     
        String  lid3Str = lid3Set.toString().substring(1, lid3Set.toString().length()-1);
        lid3Map.put("lid3", lid3Str);
        lid3List.add(lid3Map);
       
        String  lid4Str = lid4Set.toString().substring(1, lid4Set.toString().length()-1);
        lid4Map.put("lid4", lid4Str);
        lid4List.add(lid4Map);
       
        String  lid5Str = lid5Set.toString().substring(1, lid5Set.toString().length()-1);
        lid5Map.put("lid5", lid5Str);
        lid5List.add(lid5Map);
        
        //保存拼接的数据格式
        jsonMap.put("name", dataList);
        jsonMap.put("vid", vidList);
        jsonMap.put("vxid", vxidList);
        jsonMap.put("lid1", lid1List);
        jsonMap.put("lid2", lid2List);
        jsonMap.put("lid3", lid3List);
        jsonMap.put("lid4", lid4List);
        jsonMap.put("lid5", lid5List);
        
        return jsonMap;
    }
    
    /**
     * 
     * @Title: getNetworkById
     * @Description: 查询一个网卡
     * @param id  观察点业务ID
     * @return List<NetworkBean>
     * @author wangjianmin
     */
    public List<NetworkBean> getNetworkById(String id){
        List<NetworkBean> ids = dao.getNetworkById(id);
        return ids;
    }
    
    /**
     * 
     * @Title: delWatchpoin
     * @Description:  删除一条观察点
     * @param request 请求
     * @param id   观察点业务ID
     * @return int
     * @author wangjianmin
     * @throws IOException 
     */
    public int delWatchpoin(HttpServletRequest request, int id) throws IOException{
        //根据id查询查询观察点
        WatchpointBean bean = dao.getWatchpointById(id);
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(5);
        logsBean.setMsg("删除"+bean.getName()+"观察点");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        //根据id删除观察点
        int ids = dao.delWatchpoin(id);
        Runtime.getRuntime().exec("/usr/local/var/cleanwp.sh " + id);
        //删除权限中这个id的观察点信息
        AuthorizeJurisBean userAuthorizeBean = new AuthorizeJurisBean();
        userAuthorizeBean.setAppId(id);
        authorizeJurisDao.deleteUserAuthorize(userAuthorizeBean);
        
        //删除观察点设置
        JsonFileUtil jfu = JsonFileUtil.getInstance();
        try {
            jfu.dleJsonFile(ModuleType.WATCHPOINT, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除告警中这个id的观察点信息
        alarmSetService.delAppBusinessAfter(0, 10, id);
        
        //删除报表中这个id的观察点信息
        reportService.deleteTimerDetailByWatchPointId(id);
        
        return ids;
    }
    
    /**
     * 
     * @Title: addWatchpoint
     * @Description: 添加观察点业务
     * @param bean   观察点参数实体
     * @param request 请求
     * @return int
     * @author wangjianmin
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addWatchpoint(WatchpointBean bean, HttpServletRequest request) {
    	
    	bean.setIp("127.0.0.1"); //租户IP
    	bean.setPort(80); //租户端口
    	bean.setLocal(true);
    	PropertiesUtil propertiesUtil = new PropertiesUtil("sysinfo.properties");
    	//有效期
    	String validterm = null;
        try {
			validterm = propertiesUtil.readProperty("validterm");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        if(!validterm.equals("")) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	try {
				Date  da =sdf.parse(validterm);
				int time = (int) (da.getTime() /1000);
				bean.setValidterm(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        //添加观察点
        int watchpoint = dao.addWatchpoint(bean);
        
        // 通用业务Bean
        AppBusinessBean busiBean = new AppBusinessBean();
        busiBean.setId(bean.getId());
        busiBean.setModuleId(10);
        busiBean.setName(bean.getName());
        
        try {
            boolean bool = appBusinessService.appBusiCommonProcess(busiBean, request);
            if (!bool) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (IOException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return watchpoint;
    }
    
    /**
     * 
     * @Title: updWatchpoint
     * @Description: 修改一条观察点
     * @param request 请求
     * @param bean 接收修改观察点参数
     * @return int
     * @author wangjianmin
     */
    public int updWatchpoint(HttpServletRequest request, WatchpointBean bean){
    	bean.setIp("127.0.0.1"); //租户IP
    	bean.setPort(80); //租户端口
    	PropertiesUtil propertiesUtil = new PropertiesUtil("sysinfo.properties");
    	//有效期
    	String validterm = null;
        try {
			validterm = propertiesUtil.readProperty("validterm");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        if(!validterm.equals("")) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	try {
				Date  da =sdf.parse(validterm);
				int time = (int) (da.getTime() /1000);
				bean.setValidterm(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        //修改观察点
        int updWatchpoint = dao.updWatchpoint(bean);
        
        // 通用业务Bean
        AppBusinessBean busiBean = new AppBusinessBean();
        busiBean.setId(bean.getId());
        busiBean.setModuleId(10);
        busiBean.setName(bean.getName());
        // 添加日志
        appBusinessService.appBusiLogs(busiBean, request, ServiceRuleType.UPDATE);
        
        return updWatchpoint;
    }
   
    /**
     * 
     * @Title: getUserConfigureBean
     * @Description: 查询用户配置
     * @param userId 用户ID
     * @return List<UserConfigureBean>
     * @author wangjianmin
     */
    public List<UserConfigureBean> getUserConfigureBean(int userId){
        List<UserConfigureBean> list = userConfigureDao.getUserConfigureBean(userId);
        return list;
    }

    /**
     * 
     * @Title: updateUserConfigureByKey
     * @Description: 修改用户配置 背景
     * @param request 请求
     * @param userConfigureBean 用户背景参数信息
     * @return int
     * @author wangjianmin
     */
    public int updateUserConfigureByKey(HttpServletRequest request, UserConfigureBean userConfigureBean){
        //修改当前用户背景色
        int ids = userConfigureDao.updateUserConfigureByKey(userConfigureBean);
        
        //得到当前用户信息
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
       
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(6);
        if(userConfigureBean.getKey().equals("name")){
            logsBean.setMsg("修改"+systemUserBean.getUserName()+"用户背景色");
        }else if(userConfigureBean.getKey().equals("dataRefreshTime")){
            logsBean.setMsg("修改"+systemUserBean.getUserName()+"用户地址库开关");
        }else if(userConfigureBean.getKey().equals("openIpLocationFlag")){
            logsBean.setMsg("修改"+systemUserBean.getUserName()+"用户刷新粒度");
        }else if(userConfigureBean.getKey().equals("cockpitConfig")){
            logsBean.setMsg("修改"+systemUserBean.getUserName()+"主驾驶舱默认配置");
        }
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志表
        logsDao.insertLogs(logsBean);
        
        return ids;
    }
    
    /**
     * 
     * @Title: addConfigure
     * @Description: 添加背景
     * @param id 用户ID
     * @param key 键
     * @param value 值
     * @return int
     * @author wangjianmin
     */
    public int addConfigure(int id , String key, String value){
        int ids = dao.addConfigure(id, key, value);
        return ids;
    }
    
    /**
     * 
     * @Title: getWatchpointById
     * @Description: 获取信息
     * @param id 编号
     * @return WatchpointBean
     * @author www
     */
    public WatchpointBean getWatchpointById(int id) {
        
        return dao.getWatchpointById(id);
    }
    
    /**
     * 
     * @Title: getWatchpointGraphical
     * @Description: 观察点绘图
     * @param drawingOptionsBean 观察点绘图实体
     * @return Map<String,Object>
     * @author wangjianmin
     */
    public Map<String, Object> getWatchpointGraphical(DrawingOptionsBean drawingOptionsBean){
        Map<String, Object> mapList = new HashMap<String, Object>();
        long start = 0;
        long end = 0;
        int plotId = drawingOptionsBean.getPlotId();
        if (drawingOptionsBean.getStarttime() == null) {
            TimeDefaultBean time = DateAppsUtils.getGraphDefaultTime();
            end = time.getEndtime();
            if (plotId == 308) { // 健康图默认一个小时
                start = end - 3600;
            } else {
                start = time.getStarttime();
            }
        } else {
            start = drawingOptionsBean.getStarttime();
            end = drawingOptionsBean.getEndtime();
        }
        int watchpointId = drawingOptionsBean.getWatchpointId();
        WatchpointBean bean = this.getWatchpointById(watchpointId);
        PlotOptionBean plotBean = plotService.getPlotOptionById(plotId);
        KpisBean kpisBean = plotService.getKpisById(plotBean.getKpiId());
        PlotTypeBean plotTypeBean = plotService.getPlotTypeById(drawingOptionsBean.getPlotTypeId());
        BusiKpiService kpiServer = new BusiKpiService(watchpointId);
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = null;
        if (plotId == 321 || plotId == 317) {
            List<SimpleEntry<Long, Double>> tmp = null;
            KpisBean kpi = null;
            int[] qos = null;
            if (plotId == 321) { // QoS + 会话数量图表
                qos = new int[]{ 14, 2 };
            } else if (plotId == 317) { // QoS详细图表
                qos = new int[]{ 14, 7, 36, 16, 6 };
            }
            for (int i = 0, len = qos.length; i < len; i ++) {
                kpi = plotService.getKpisById(qos[i]);
                dataMap = new HashMap<String, Object>();
                tmp = kpiServer.getRrdDataByName(start, end, 0, kpi.getName(), RrdAlgorithm.AVG);
                dataMap.put("value", tmp);
                dataMap.put("name", kpi.getDisplayName());
                dataMap.put("unit", kpi.getUnit());
                if (qos[i] == 6) { // 链路时延为虚线
                    dataMap.put("type", "spline");
                }
                listMap.add(dataMap);
            }
        } else if (plotId == 31) { // 公有协议分布
            List<SimpleEntry<String, Double>> list = kpiServer.getPublicProtoRrdData(start, end, kpisBean.getName());
            SimpleEntry<String, Double> entry = null;
            ProtoPlanBean protoPlanBean = new ProtoPlanBean();
            int protoPlanId = 0;
            for (int i = 0, j = list.size(); i < j; i++) {
                entry = list.get(i);
                protoPlanBean.setName(entry.getKey());
                protoPlanId = protoPlanService.getProtoPlanBeanByName(protoPlanBean).getId();
                dataMap = new HashMap<String, Object>();
                dataMap.put("id", protoPlanId);
                dataMap.put("name", entry.getKey());
                dataMap.put("value", entry.getValue());
                listMap.add(dataMap);
            }
            listMap = sortList(listMap);
            
            // 未识别共有协议
            int unknowId = 11106;
            int unknowKpiId = 34;
            kpisBean = plotService.getKpisById(unknowKpiId);
            double unknowPublic = kpiServer.getRrdDataPointByName(start, 
                    end, kpisBean.getName(), RrdAlgorithm.SUM).getValue();
            dataMap = new HashMap<String, Object>();
            dataMap.put("id", unknowId);
            dataMap.put("name", kpisBean.getDisplayName());
            dataMap.put("value", unknowPublic);
            listMap.add(dataMap);
        } else if (plotId == 30) { // 用户协议分布(所有定义的服务端)
            List<AppBusinessBean> list = appBusinessDao.selectAppBusinessesByModuleId(12);
            SimpleEntry<String, Double> entry = null;
            for (AppBusinessBean serverBean : list) {
                entry = kpiServer.getUserProtoRrdData(start, end, 
                    "USER" + serverBean.getId(), kpisBean.getName());
                if (entry != null) {
                    dataMap = new HashMap<String, Object>();
                    dataMap.put("id", serverBean.getId());
                    dataMap.put("name", serverBean.getName() + "流量");
                    dataMap.put("value", entry.getValue());
                    listMap.add(dataMap);
                }
            }
            
            // 未识别用户协议
            int unknowId = 0;
            int unknowKpiId = 33;
            kpisBean = plotService.getKpisById(unknowKpiId);
            double unknowUser = kpiServer.getRrdDataPointByName(start, 
                    end, kpisBean.getName(), RrdAlgorithm.SUM).getValue();
            dataMap = new HashMap<String, Object>();
            dataMap.put("id", unknowId);
            dataMap.put("name", kpisBean.getDisplayName());
            dataMap.put("value", unknowUser);
            listMap.add(dataMap);
        } else if (plotId == 301) { // 包大小分布
            Map<String, String> namezh = PlotService.pktsNamezh;
            List<SimpleEntry<String, Double>> list = kpiServer.getPktsSizeDistri(start, end, kpisBean.getName());
            SimpleEntry<String, Double> entry = null;
            ProtoPlanBean protoPlanBean = new ProtoPlanBean();
            for (int i = 0, j = list.size(); i < j; i++) {
                entry = list.get(i);
                protoPlanBean.setName(entry.getKey());
                dataMap = new HashMap<String, Object>();
                dataMap.put("type", entry.getKey());
                dataMap.put("name", namezh.get(entry.getKey()));
                dataMap.put("value", entry.getValue());
                listMap.add(dataMap);
            }
        } else if (plotId == 308) { // 健康图
            kpisBean = new KpisBean();
            plotService.getHeatmap(start, end, 10, watchpointId, 0, mapList, kpisBean);
        } else {
            dataMap = new HashMap<String, Object>();
            List<SimpleEntry<Long, Double>> avg = kpiServer.getRrdDataByName(
                    start, end, 0, kpisBean.getName(), RrdAlgorithm.AVG);
            dataMap.put("value", avg);
            dataMap.put("name", kpisBean.getDisplayName());
            listMap.add(dataMap);
        }
        mapList.put("starttime", start);
        mapList.put("endtime", end);
        mapList.put("plotName", bean.getName() + "-" + plotBean.getName());
        mapList.put("type", plotTypeBean.getName());
        mapList.put("unit", kpisBean.getUnit());
        if (!mapList.containsKey("data")) {
            mapList.put("data", listMap);
        }
        
        if (plotId == 317) { // QoS
            mapList.put("kpiName", kpisBean.getName());
        }
        
        // 线图展示基线
        if (plotId != 321 && "line".equals(plotTypeBean.getName())) { 
            ParamBean paramBean = new ParamBean();
            paramBean.setStarttime(start);
            paramBean.setEndtime(end);
            paramBean.setModuleId(10);
            paramBean.setBusiId(watchpointId);
            paramBean.setWatchpointId(0);

            PlotSimpleBean simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.HIGH);
            if (simpleBean != null) {
                dataMap = new HashMap<String, Object>();
                dataMap.put("value", simpleBean.getValue());
                dataMap.put("name", simpleBean.getName());
                dataMap.put("type", simpleBean.getType());
                listMap.add(dataMap);
            }
                
            simpleBean = plotService.getAlarmBaseLine(paramBean, kpisBean, AlarmBaseType.LOW);
            if (simpleBean != null) {
                dataMap = new HashMap<String, Object>();
                dataMap.put("value", simpleBean.getValue());
                dataMap.put("name", simpleBean.getName());
                dataMap.put("type", simpleBean.getType());
                listMap.add(dataMap);
            }
        }
        
        return mapList;
    }
    
    /**
     * 
     * @Title: sortList
     * @Description: 排序
     * @param dataList 数据集合
     * @return List<Map<String,Object>>
     * @author wangjianmin
     */
    public List<Map<String, Object>> sortList(List<Map<String, Object>> dataList) {
        Collections.sort(dataList, new Comparator<Map<String, Object>>() {  
            @Override  
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
                int i = (int) (Double.parseDouble(o2.get("value").toString())
                        - Double.parseDouble(o1.get("value").toString()));  
                return i;
            }  
        });
        int dataSize = dataList.size() > 10 ? 10 : dataList.size();
        List<Map<String, Object>> data = dataList.subList(0, dataSize); 
        // 其他
        if (dataList.size() > 10) {
            StringBuilder ids = new StringBuilder(dataList.get(0).get("id").toString());
            double val = 0;
            for (int i = 1, len = dataList.size(); i < len; i ++) {
                if (i < 10) {
                    ids.append(",");
                    ids.append(dataList.get(i).get("id").toString());
                } else {
                    val += Double.parseDouble(dataList.get(i).get("value").toString());
                }
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("moduleId", 10);
            dataMap.put("id", ids.toString());
            dataMap.put("name", "其他");
            dataMap.put("value", val);
            data.add(dataMap);
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getCrossGridData
     * @Description: 十字格数据
     * @param drawingOptionsBean 十字格数据参数
     * @return Map<String,Object>
     * @author wangjianmin
     */
    public Map<String, Object>  getCrossGridData(DrawingOptionsBean drawingOptionsBean){
        Map<String, Object> mapList = new HashMap<String, Object>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        long start = 0;
        long end = 0;
        if (drawingOptionsBean.getStarttime() == null) {
            TimeDefaultBean timeBe = DateAppsUtils.getRrdDefaultTime();
            end = timeBe.getEndtime();
            start = timeBe.getStarttime();
        } else {
            start = drawingOptionsBean.getStarttime();
            end = drawingOptionsBean.getEndtime();
        }
        String[] plotIds = drawingOptionsBean.getPlotIds().split(",");
        Map<String, Object> dataMap = null;
        SimpleEntry<Long, Double> avg = null;
        AlarmLogBean alarmLogBean = null;
        BusiKpiService kpiServer = null;
        PlotOptionBean plotBean = null;
        KpisBean kpisBean = null;
        Integer plotId = 0;
        for (int i = 0; i < plotIds.length; i++) {
            dataMap = new HashMap<String, Object>();
            plotId = Integer.parseInt(plotIds[i]);
            drawingOptionsBean.setPlotId(plotId);
            alarmLogBean = new AlarmLogBean();
            plotBean = plotService.getPlotOptionById(drawingOptionsBean.getPlotId());
            kpisBean = plotService.getKpisById(plotBean.getKpiId());
            kpiServer = new BusiKpiService(drawingOptionsBean.getWatchpointId());
            if (drawingOptionsBean.getStarttime() == null) {
                if (plotId==86){
                    alarmLogBean.setWatchpointId(drawingOptionsBean.getWatchpointId());
                    alarmLogBean.setBusinessId(drawingOptionsBean.getWatchpointId());
                    alarmLogBean.setModuleId(10);
                    alarmLogBean.setHandledflag("N");
                    Map<String, Long> alarmLogCount = alarmLogService.getAlarmLogCount(alarmLogBean);
                    dataMap.put("value", alarmLogCount.get("count"));
                    dataMap.put("starttime", alarmLogCount.get("starttime"));
                    dataMap.put("endtime", alarmLogCount.get("endtime"));
                    dataMap.put("alarmLevelId", alarmLogCount.get("alarmLevelId"));
                    dataMap.put("unit", "num");
                    dataMap.put("plotName", plotBean.getName());
                } else {
                    avg = kpiServer.getRrdDataPointByName(start, end, kpisBean.getName(), RrdAlgorithm.AVG);
                    dataMap.put("plotName", plotBean.getName());
                    dataMap.put("unit", kpisBean.getUnit());
                    dataMap.put("value", avg.getValue()); 
                }
                listMap.add(dataMap);
            } else {
                RrdAlgorithm rrdAlgorithm = RrdAlgorithm.SUM;
                if (plotId==86){
                    alarmLogBean.setWatchpointId(drawingOptionsBean.getWatchpointId());
                    alarmLogBean.setBusinessId(drawingOptionsBean.getWatchpointId());
                    alarmLogBean.setModuleId(10);
                    alarmLogBean.setHandledflag("N");
                    alarmLogBean.setStarttime(start);
                    alarmLogBean.setEndtime(end);
                    Map<String, Long> alarmLogCount = alarmLogService.getAlarmLogCount(alarmLogBean);
                    dataMap.put("value", alarmLogCount.get("count"));
                    dataMap.put("starttime", alarmLogCount.get("starttime"));
                    dataMap.put("endtime", alarmLogCount.get("endtime"));
                    dataMap.put("alarmLevelId", alarmLogCount.get("alarmLevelId"));
                    dataMap.put("unit", "num");
                    dataMap.put("plotName", plotBean.getName());
                } else {
                    String calcul=plotBean.getCalcul();
                    if (calcul !=null && !"".equals(calcul.trim()) && "AVG".equals(calcul)) {
                        rrdAlgorithm = RrdAlgorithm.AVG;
                    }
                    avg = kpiServer.getRrdDataPointByName(start, end, kpisBean.getName(), rrdAlgorithm);
                    dataMap.put("plotName", plotBean.getName());
                    dataMap.put("unit", kpisBean.getUnit());
                    dataMap.put("value", avg.getValue());
                }
                listMap.add(dataMap);
            }
        }
        mapList.put("starttime", start);
        mapList.put("endtime", end);
        mapList.put("data", listMap);
        mapList.put("watchPointId", drawingOptionsBean.getWatchpointId());
        
        return mapList;
    }
    
    
    
    
    /**
     * 
     * @Title: addWatchpoint
     * @Description: 添加观察点业务与杨佳楠对接
     * @param bean   观察点参数实体
     * @param request 请求
     * @return int
     * @author wangjianmin
     */
    public Integer addWatchpointY(WatchpointBean bean, HttpServletRequest request) {
        //验证名称是否重复添加
        boolean byName = dao.getWatchpointByName(bean.getName());
        if(byName){
            return -1;
        } else {
            //添加观察点
            dao.addWatchpoint(bean);
            
            // 通用业务Bean
            AppBusinessBean busiBean = new AppBusinessBean();
            busiBean.setId(bean.getId());
            busiBean.setModuleId(10);
            busiBean.setName(bean.getName());
            
            //获取用户信息
            SystemUserBean systemUserBean = userService.getUserBeanById(1);
            try {
                appBusinessService.appBusiCommonProcess(busiBean, systemUserBean);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return bean.getId();
        }
    }
    
    /**
     * 
     * @Title: updateByName
     * @Description: 修改观察点名称
     * @param name  名称
     * @param id  编号
     * @return boolean
     * @author wangjianmin
     */
    public boolean updateByName(String name, int id){
        //验证名称是否重复添加
        boolean byName = dao.getWatchpointByName(name);
        if(byName){
            return false;
        } else {
            boolean flag = dao.updateByName(name, id);
            //根据id查询查询观察点
            WatchpointBean upbean =  dao.getWatchpointById(id);
            //获取当前用户信息
            SystemUserBean systemUserBean = userService.getUserBeanById(1);
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(5);
            logsBean.setMsg("修改"+upbean.getName()+"观察点");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            //添加系统日志表
            logsDao.insertLogs(logsBean);
            return flag;
        }
    }
    
    /**
     * 
     * @Title: updateWatchpoint
     * @Description: 修改一条观察点
     * @param request 请求
     * @param bean 接收修改观察点参数
     * @return int
     * @author wangjianmin
     */
    public Integer updateWatchpoint(HttpServletRequest request, WatchpointBean bean){
    	//验证名称是否重复添加
        boolean byName = dao.getWatchpointByName(bean.getName());
        if(byName){
            return -1;
        } else {
        	 //修改观察点
            dao.updWatchpoint(bean);
            
            // 通用业务Bean
            AppBusinessBean busiBean = new AppBusinessBean();
            busiBean.setId(bean.getId());
            busiBean.setModuleId(10);
            busiBean.setName(bean.getName());
            
            //根据id查询查询观察点
            WatchpointBean upbean =  dao.getWatchpointById(bean.getId());
            //获取当前用户信息
            SystemUserBean systemUserBean = userService.getUserBeanById(1);
            //添加log日志参数bean
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(5);
            logsBean.setMsg("修改"+upbean.getName()+"观察点");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            //添加系统日志表
            logsDao.insertLogs(logsBean);
        	
        }
        return bean.getId();
     }
    
    /**
     * 
     * @Title: updateValidterm
     * @Description: 修改有效期
     * @param validterm   有效期
     * @param request 请求
     * @return void
     * @author wangjianmin
     */
    public void updateValidterm(Integer validterm) {
    	//获取所有本地观察点
    	List<WatchpointBean> list = dao.selectValidterm();
    	for (WatchpointBean watchpointBean : list) {
    		dao.updateValidterm(validterm, watchpointBean.getId());
		}
    }
}
