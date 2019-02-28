/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFlowService
 *创建人:chensq    创建时间:2017年12月4日
 */
package com.protocolsoft.commpair.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.servers.bean.AppIpPortBean;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.watchpoint.bean.NetworkBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;


/**
 * @ClassName： CommpairFlowService
 * @Description: 通信对流量service
 * @author chensq
 * 
 */
@Service
public class CommpairFlowService {
    
    /**
     * @Fields watchpointDao : 观察点 DAO
     */
    @Autowired
    private WatchpointDao watchpointDao;
    
    /**
     * @Title: historyExtract
     * @Description: 流量下载
     * @param commpairBean
     * @param ipList
     * @return String
     * @throws IOException String
     * @author chensq
     */
    public String historyExtract(CommpairBean commpairBean, List<AppIpPortBean> ipList) throws IOException {

        //定义变量
        String  vid=null;
        
        //通信对参数
        String starttimeStr = commpairBean.getStarttimeStr();
        String endtimeStr = commpairBean.getEndtimeStr();
        int watchpointId = commpairBean.getWatchpointId();
        String serverIpStr = commpairBean.getServerip();
        String clientIpStr = commpairBean.getClientip();
        long serverport = commpairBean.getServerport();
        String serCliUnknow= commpairBean.getSerCliUnknow();
        String bpf = commpairBean.getBpf();
        int historyGetFlow = commpairBean.getHistoryGetFlow();
        
        //配置文件参数
        PropertiesUtil propertiesUtil=new PropertiesUtil("sysset.properties");
        String fileDictionary=propertiesUtil.readProperty("file_dictionary");
        String rootDictionary=propertiesUtil.readProperty("root_dictionary");
        
        //拼接
        StringBuffer strFilter = new StringBuffer();
        StringBuffer sb = new StringBuffer("/usr/local/bin/dsas_extract");
       
        //观察点  必要
        if (watchpointId!=0) {//观察点存在的情况
            //网卡集合
            List<NetworkBean> networkBeanList = null;
            
            WatchpointBean watchpointBean=watchpointDao.getWatchpointById(watchpointId);
            String did = watchpointBean.getDid();
            vid = watchpointBean.getVid();
            if (did == null) {
                return "3";
            }
            String [] strs = did.split(",");
            networkBeanList = new ArrayList<NetworkBean>();
            //通过观察点的网卡id查询网卡详情
            for (int i = 0; i < strs.length; i++) {
                networkBeanList.addAll(watchpointDao.getNetworkById(strs[i]));
            }
            if (networkBeanList.size() == 0) {
                return "3";
            }
            
            //迭代网卡
            String tmp="";
            NetworkBean nicBean =null;
            String  nicNames=null;
            
            for (int i = 0; i < networkBeanList.size(); i++) {
                nicBean = networkBeanList.get(i);
                if (i == 0) {
                    tmp = nicBean.getName();
                    continue;
                }
                tmp = tmp + "," + nicBean.getName();
            }
            nicNames = tmp;
            
            sb.append(" -i " + nicNames + " -s \"" + starttimeStr + "\" -e \"" + endtimeStr + "\"");
        } else {//观察点不存在的情况
            return "3";
        }

        if (historyGetFlow==1){//历史数据提取情况，流量不区分方向
            if (StringUtils.isNotEmpty(serverIpStr)){
                strFilter.append(" host "+serverIpStr);
                if (serverport!=0) {
                    strFilter.append(" and port "+serverport);
                }
            }            
        } else {//通信对下载情况，流量区分方向
            
            //如果服务端、客户端 ip端口
            int ipCount=0;
            //判断服务端、客户端ip的有无
            if (StringUtils.isNotEmpty(serverIpStr)){
                ipCount++;
            }
            if (StringUtils.isNotEmpty(clientIpStr)){
                ipCount++;
            }
            
            //拼接
            if (StringUtils.isEmpty(serCliUnknow)) {//正常流量情况
                //服务端ip  客户端ip
                if (ipCount==1) {//服务端 客户端 其中一个有
                    strFilter.append(" ( ");
                    strFilter.append("(src host " 
                            + ((serverIpStr==null || "".equals(serverIpStr))?clientIpStr:serverIpStr) + ")");
                    strFilter.append(" or ");
                    strFilter.append("(dst host " 
                            + ((serverIpStr==null || "".equals(serverIpStr))?clientIpStr:serverIpStr) + ")");
                    strFilter.append(" ) ");
                } else if (ipCount==2) {//服务端 客户端 两者都有
                    strFilter.append(" ( ");
                    strFilter.append("(src host " + serverIpStr + " and " +" dst host "+clientIpStr+")");
                    strFilter.append(" or ");
                    strFilter.append("(src host " + clientIpStr + " and " +" dst host "+serverIpStr+")");
                    strFilter.append(" ) ");
                }
                if (serverport!=0){
                    if (ipCount>=1) {
                        strFilter.append(" and ");
                    }
                    strFilter.append("(");
                    strFilter.append("(src port " + serverport + ")");
                    strFilter.append(" or ");
                    strFilter.append("(dst port " + serverport + ")");
                    strFilter.append(")");
                }
            }
        }
        
        if ((bpf != null) && (!"".equals(bpf))) {
            sb.append(" -f \"" + bpf + "\"");
        } else {
            if ((vid != null) && (!"".equals(vid)) && (Integer.parseInt(vid) > 0)){
                sb.append(" -f \"vlan " + vid);
                if (!"".equals(strFilter.toString())){
                    sb.append(" and (" + strFilter.toString() + " )");
                }
                sb.append('"');
            } else {
                if (!"".equals(strFilter.toString())) {
                    sb.append(" -f \"(" + strFilter.toString() + " ) "
                        + "or"
                        + " (vlan and (" + strFilter.toString() + " ))\"");
                }
            }
        }
        sb.append(" -r " + rootDictionary);
        sb.append(" -o " + fileDictionary);

        String filename="";
        try {
            int ch = '\\';
            Process p = Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", sb.toString()});
            p.waitFor();
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            int ind = 0;
            String tmp="";
            while ((tmp = reader.readLine()) != null){
                if (tmp.indexOf("query time error") > 0){
                    return "1";
                }
                if (tmp.startsWith("[OUT_FILE]")){
                    ind = 1;
                    break;
                }
            }
            if (ind == 0){
                return "2";
            }
            tmp = tmp.substring(10);
            is.close();
            reader.close();
            p.destroy();
            ind = tmp.lastIndexOf(ch);
            if (ind < 0) {
                ch = '/';
                ind = tmp.lastIndexOf(ch);
            }
            filename = tmp.substring(ind + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return filename;
    }

    
}
