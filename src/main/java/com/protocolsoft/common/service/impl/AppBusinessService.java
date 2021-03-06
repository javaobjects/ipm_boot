/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppBusinessService.java
 *创建人: WWW    创建时间: 2018年9月25日
 */
package com.protocolsoft.common.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.SameIpPortParamBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.common.enumeration.ServiceRuleType;
import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.user.bean.SamllModuleBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.impl.AuthorizeModuleServer;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.IpUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;

/**
 * @ClassName: AppBusinessService
 * @Description: 业务
 * @author WWW
 *
 */
@Service
public class AppBusinessService {

    /**
     * 业务DAO
     */
    @Autowired
    private AppBusinessDao dao;
    
    /**
     * 告警阈值设置
     */
    @Autowired
    private AlarmSetService alarmSetService;
    
    /**
     * 模块
     */
    @Autowired
    private AuthorizeModuleServer authorizeService;
    
    /**
     * 权限
     */
    @Autowired
    private AuthorizeJurisService jurisService;
    
    /**
     * ipm_app_ip_port表Dao
     */
    @Autowired
    private AppIpPortDao appIpPortDao;
    
    /**
     * 日志
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 
     * @Title: appBusiCommonProcess
     * @Description: 应用业务通用业务流程(JSON配置、告警、日志、权限)
     * @param busiBean 业务信息
     * @param request 请求，用于获取用户信息
     * @return boolean 
     * @throws IOException IO错误
     * @author WWW
     */
    public boolean appBusiCommonProcess(AppBusinessBean busiBean, 
            HttpServletRequest request) throws IOException {
        SystemUserBean userBean = null;
        if (request == null) {
            userBean = systemUserService.getUserBeanById(1);
        } else {
            userBean = systemUserService.getSessionUserBean(request);
        }
        
        return this.appBusiCommonProcess(busiBean, userBean);
    }
    
    /**
     * 
     * @Title: appBusiCommonProcess
     * @Description: 应用业务通用业务流程(JSON配置、告警、日志、权限)
     * @param busiBean 业务信息
     * @param userBean 用户信息
     * @return boolean 是否成功
     * @throws IOException IO错误
     * @author WWW
     */
    public boolean appBusiCommonProcess(AppBusinessBean busiBean, 
            SystemUserBean userBean) throws IOException {
        boolean bool = true;
        
        int moduleId = busiBean.getModuleId();
        int busiId = busiBean.getId(); 

        //添加JSON文件配置
        ModuleType type = ModuleType.getModuleType(moduleId);
        bool = JsonFileUtil.getInstance().addJsonFile(type, busiId);
        
        if (bool) {
            // 添加响应告警信息
            bool = alarmSetService.addAppBusinessAfter(0, moduleId, busiId);
    
            if (bool) {
                // 添加用户权限（超级管理员不需要）
                if (userBean.getRoleId() != 1) {
                    jurisService.addUserAuthorize(userBean.getId(), busiId, moduleId);
                }
                bool = this.appBusiLogs(busiBean, userBean, ServiceRuleType.INSERT);
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: isExistSameIpPort
     * @Description: 是否有相同的IP端口
     * @param bean 参数
     * @return boolean
     * @author WWW
     */
    private boolean isExistSameIpPort(SameIpPortParamBean bean) {
        boolean bool = dao.isExistSameIpPort(bean);
        
        return bool;
    }
    
    /**
     * 
     * @Title: addBusiIpPort
     * @Description: 添加业务IP端口
     * @param bean 业务信息
     * @param isUpd 是否为修改
     * @return int 1 成功   0 失败    2 ip重复
     * @author WWW
     */
    public int addBusiIpPort(AppBusinessBean bean, boolean isUpd) {
        int state = 0;
        List<AppIpPortBean> appIpPortBeans = this.analysisAppIpPort(bean);

        SameIpPortParamBean paramBean = new SameIpPortParamBean();
        paramBean.setList(appIpPortBeans);
        paramBean.setUpd(isUpd);
        paramBean.setBusiId(bean.getId());
        if (this.isExistSameIpPort(paramBean)) {
            state = 2;
        } else {
            appIpPortDao.batchInsertAppIpPort(appIpPortBeans);
            state = 1;
        }
        
        return state;
    }
    
    /**
     * 
     * @Title: analysisAppIpPort
     * @Description: 解析应用IP端口
     * @param bean 业务信息
     * @return List<AppIpPortBean>
     * @author WWW
     */
    public List<AppIpPortBean> analysisAppIpPort(AppBusinessBean bean) {
        List<AppIpPortBean> list = null;
        String displayIp = bean.getDisplayIp();
        int busiId = bean.getId();
        if (displayIp.contains(".")) {
            list = this.analysisAppIpPort(displayIp, busiId);
        } else {
            list = this.analysisAppPort(displayIp, busiId);
        }
        
        return list;
    }
    
    /**
     * 
     * @Title: appBusiLogs
     * @Description: 添加业务日志
     * @param busiBean 业务信息
     * @param request 请求，用于获取用户信息
     * @param type 业务类型
     * @return boolean
     * @author WWW
     */
    public boolean appBusiLogs(AppBusinessBean busiBean, 
            HttpServletRequest request, ServiceRuleType type) {
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        return this.appBusiLogs(busiBean, userBean, type);
    }
    
    /**
     * 
     * @Title: analysisAppIpPort
     * @Description: 解析服务端ip和端口
     * @param displayIp 输入IP端口
     * @param busiId 业务编号
     * @return List<AppIpPortBean>
     * @author WWW
     */
    private List<AppIpPortBean> analysisAppIpPort(String displayIp, int busiId) {
        List<AppIpPortBean> appIpPortBeans = new ArrayList<AppIpPortBean>();
        
        String[] displayIpArr = displayIp.split(",");
        AppIpPortBean appIpPortBean = null;
        //多个用逗号分隔,故先按照逗号分隔
        for (String string : displayIpArr) {
            //按冒号分隔,下标0为ip,下标1为端口
            String [] temp = string.split(":");
            String ips = temp[0];
            String [] ipArr = ips.split("-");
            if (temp.length == 1) { // 只输入ip，无端口的情况
                if (ipArr.length == 1) { // 单个IP
                    if (ips.contains("/")) {
                        List<Long> list = IpUtils.getMaxMinIpByIpnet(ips);
                        long minIp = list.get(0);
                        long maxIp = list.get(1);
                        for(long i = minIp; i <= maxIp; i ++) {
                            appIpPortBean = new AppIpPortBean();
                            appIpPortBean.setAppId(busiId);
                            appIpPortBean.setIp(String.valueOf(i));
                            appIpPortBeans.add(appIpPortBean);
                        }
                    } else {
                        appIpPortBean = new AppIpPortBean();
                        appIpPortBean.setAppId(busiId);
                        appIpPortBean.setIp(Long.toString(IpUtils.ipFromStringToLong(ips)));
                        appIpPortBeans.add(appIpPortBean);
                    }
                } else {
                    String[] sip = ipArr[0].split("\\.");
                    String[] eip = ipArr[1].split("\\.");
                    int minIp = Integer.parseInt(sip[3]);
                    int maxIp = Integer.parseInt(eip[3]);
                    sip[3] = "";
                    String startIp = String.join(".", sip);
                    for (; minIp <= maxIp; minIp++) {
                        appIpPortBean = new AppIpPortBean();
                        appIpPortBean.setAppId(busiId);
                        appIpPortBean.setIp(Long.toString(IpUtils.ipFromStringToLong(startIp + minIp)));
                        appIpPortBeans.add(appIpPortBean);
                    }
                }
            } else {
                // 按横杠分隔,得出ip或者端口的范围
                String ports = temp[1];
                String[] portArr = ports.split("-");

                if (ipArr.length > 1 && portArr.length > 1) {
                    // 最小端口
                    int minPort = Integer.parseInt(portArr[0]);
                    // 最大端口
                    int maxPort = Integer.parseInt(portArr[1]);
                    // 最小ip
                    int minIp = Integer.parseInt(ipArr[0].substring(ipArr[0].lastIndexOf(".") + 1));
                    // 最大ip
                    int maxIp = Integer.parseInt(ipArr[1].substring(ipArr[1].lastIndexOf(".") + 1));
                    for (int j = minPort; j <= maxPort; j++) {
                        for (int i = minIp; i <= maxIp; i++) {
                            // 根据解析ip端口 创建对象
                            appIpPortBean = new AppIpPortBean();
                            appIpPortBean.setAppId(busiId);
                            // ip拼接对象
                            StringBuffer ipSb = new StringBuffer();
                            // ip的前三位
                            ipSb.append(ipArr[0].substring(0, ipArr[0].lastIndexOf(".") + 1));
                            // i代表最后一位
                            ipSb.append(i);

                            appIpPortBean.setIp(Long.toString(IpUtils.ipFromStringToLong(ipSb.toString())));
                            appIpPortBean.setPort(j);
                            appIpPortBeans.add(appIpPortBean);
                        }
                    }
                } else if (ipArr.length > 1 && portArr.length == 1) {
                    // 最小ip
                    int minIp = Integer.parseInt(ipArr[0].substring(ipArr[0].lastIndexOf(".") + 1));
                    // 最大ip
                    int maxIp = Integer.parseInt(ipArr[1].substring(ipArr[1].lastIndexOf(".") + 1));
                    for (int i = minIp; i <= maxIp; i++) {
                        // 根据解析ip端口 创建对象
                        appIpPortBean = new AppIpPortBean();
                        appIpPortBean.setAppId(busiId);
                        // ip拼接对象
                        StringBuffer ipSb = new StringBuffer();
                        // ip的前三位
                        ipSb.append(ipArr[0].substring(0, ipArr[0].lastIndexOf(".") + 1));
                        // i代表最后一位
                        ipSb.append(i);

                        appIpPortBean.setIp(Long.toString(IpUtils
                                .ipFromStringToLong(ipSb.toString())));
                        appIpPortBean.setPort(Integer.parseInt(portArr[0]));
                        appIpPortBeans.add(appIpPortBean);
                    }
                } else if (ipArr.length == 1 && portArr.length > 1) {
                    // 最小端口
                    int minPort = Integer.parseInt(portArr[0]);
                    // 最大端口
                    int maxPort = Integer.parseInt(portArr[1]);
                    for (int j = minPort; j <= maxPort; j++) {
                        // 根据解析ip端口 创建对象
                        appIpPortBean = new AppIpPortBean();
                        appIpPortBean.setAppId(busiId);
                        // ip
                        appIpPortBean.setIp(Long.toString(IpUtils.ipFromStringToLong(ipArr[0])));
                        // 端口
                        appIpPortBean.setPort(j);
                        appIpPortBeans.add(appIpPortBean);
                    }
                } else {
                    // 根据解析ip端口 创建对象
                    appIpPortBean = new AppIpPortBean();
                    appIpPortBean.setAppId(busiId);
                    // ip
                    appIpPortBean.setIp(Long.toString(IpUtils.ipFromStringToLong(ipArr[0])));
                    // port
                    appIpPortBean.setPort(Integer.parseInt(portArr[0]));
                    appIpPortBeans.add(appIpPortBean);

                }
            }
        }
        
        return appIpPortBeans;
    }
    
    /**
     * 
     * @Title: analysisAppPort
     * @Description: 解析只端口的情况
     * @param displayIp 信息
     * @param busiId 业务编号
     * @return List<AppIpPortBean>
     * @author www
     */
    private List<AppIpPortBean> analysisAppPort(String displayIp, int busiId) {
        List<AppIpPortBean> appIpPortBeans = new ArrayList<AppIpPortBean>();
        
        String[] displayIpArr = displayIp.split(",");
        String[] portArr = null;
        int portStart = 0;
        int portEnd = 0;
        //多个用逗号分隔,故先按照逗号分隔
        for (String string : displayIpArr) {
            portArr = string.split("-");
            // 单端口情况
            if (portArr.length == 1) {
                AppIpPortBean appIpPortBean = new AppIpPortBean();
                appIpPortBean.setAppId(busiId);
                appIpPortBean.setPort(Integer.parseInt(string));
                appIpPortBeans.add(appIpPortBean);
            } else {
                portStart = Integer.parseInt(portArr[0]);
                portEnd = Integer.parseInt(portArr[1]);
                for (; portStart <= portEnd; portStart ++) {
                    AppIpPortBean appIpPortBean = new AppIpPortBean();
                    appIpPortBean.setAppId(busiId);
                    appIpPortBean.setPort(portStart);
                    appIpPortBeans.add(appIpPortBean);
                }
            }
        }
        
        return appIpPortBeans;
    }
    
    /**
     * 
     * @Title: appBusiLogs
     * @Description: 添加业务日志
     * @param busiBean 业务信息
     * @param userBean 用户信息
     * @param type 业务类型
     * @return boolean
     * @author WWW
     */
    private boolean appBusiLogs(AppBusinessBean busiBean, 
            SystemUserBean userBean, ServiceRuleType type) {
        boolean bool = false;
        
        String prefix = null;
        switch (type) {
            case INSERT:
                prefix = "添加";
                break;
            case DELETE:
                prefix = "删除";
                break;
            case UPDATE:
                prefix = "修改";
                break;
            default:
                break;
        }
        
        int moduleId = busiBean.getModuleId();
        SamllModuleBean samllBean = authorizeService.getSamllModuleByModuleId(moduleId);
        
        //添加log日志参数
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(samllBean.getId());
        logsBean.setMsg(prefix + " " + busiBean.getName() + " " + samllBean.getName());
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsDao.insertLogs(logsBean);
        
        bool = true;
        
        return bool;
    }
}
