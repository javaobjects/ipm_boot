/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFlowNewService
 *创建人:chensq    创建时间:2018年03月19日
 */
package com.protocolsoft.commpair.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.bean.CommpairFlowReturnBean;
import com.protocolsoft.commpair.bean.CommpairServerFlowNameItemBean;
import com.protocolsoft.commpair.bean.CommpairServerFlowNameListBean;
import com.protocolsoft.commpair.util.CommpairFlowErrorUtil;
import com.protocolsoft.commpair.util.CommpairFolderNameUtil;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName： CommpairFlowNewService
 * @Description: 通信对流量service NEW
 * @author chensq
 * 
 */
@Service
public class CommpairFlowNewService {
     
    /**
     * @Fields logger : logger
     */
    static Logger logger = Logger.getLogger(CommpairFlowNewService.class);
    
    /**
     * @Fields watchpointDao : 观察点 DAO
     */
    @Autowired
    private WatchpointDao watchpointDao;
        
    /**
     * 
     * @Title: toHistoryExtract
     * @Description: 流量下载方法，control放入service
     * @param commpairBean
     * @param appBusinessDao
     * @param commpairFlowNewService
     * @return CommpairFlowReturnBean
     * @author chensq
     */
    public CommpairFlowReturnBean toHistoryExtract(
            CommpairBean commpairBean, 
            AppBusinessDao appBusinessDao,
            CommpairFlowNewService commpairFlowNewService){
        CommpairFlowReturnBean commpairFlowReturnBean=new CommpairFlowReturnBean();

        long starttime = commpairBean.getStarttime();
        long endtime = commpairBean.getEndtime();
        long moduleId =commpairBean.getModuleId();
        long businessId =commpairBean.getBusinessId();
        int historyGetFlow=commpairBean.getHistoryGetFlow(); //默认情况即   0为通信对单条下载   1为页面提取   2为有业务  3为会话列表下载 
        long lidu=commpairBean.getLidu();
        String protocolStr=commpairBean.getProtocol();
        
        String starttimeStr="";
        String endtimeStr="";
        //单条数据下载的情况
        if (0!=lidu && starttime==endtime){//一般通信对下载情况
            starttimeStr=DateUtils.getLongToStrDateTime((starttime*1000)-2000); // 向前推2s
            endtimeStr = DateUtils.getLongToStrDateTime((starttime+lidu)*1000+3000); // 向后推13s
        }else if(historyGetFlow==3){//一般为会话列表情况
            starttimeStr=DateUtils.getLongToStrDateTime((starttime*1000)); // 向前推0s
            endtimeStr = DateUtils.getLongToStrDateTime((endtime*1000)+1000); // 向后推1s
            historyGetFlow=0;
        }else {//一般为页面下载情况
            starttimeStr=DateUtils.getLongToStrDateTime((starttime*1000));
            endtimeStr=DateUtils.getLongToStrDateTime((endtime*1000));
        }
        
        //设置时间str
        commpairBean.setEndtimeStr(endtimeStr);
        commpairBean.setStarttimeStr(starttimeStr);

        //由于这三种形式差别很大，固采用放到map中
        Map<String, String> map=new LinkedHashMap<String, String>(); 
        //查询应用
        AppBusinessBean appBusinessBean =null;
        if(moduleId!=0 && businessId!=0){
            appBusinessBean =appBusinessDao.selectAppBusinessesByModuleIdAndBusId(moduleId, businessId);
        }
        //根据对应形式组装对应语句参数
        if(historyGetFlow==0){//log类型提取
            map.put("server-ip", commpairBean.getServerip()==null?"":commpairBean.getServerip());
            map.put("client-ip", commpairBean.getClientip()==null?"":commpairBean.getClientip());
            map.put("server-port", commpairBean.getServerport()==0?"":String.valueOf(commpairBean.getServerport()));
            map.put("client-port", commpairBean.getClientport()==null?"":String.valueOf(commpairBean.getClientport()));
            if(appBusinessBean!=null){//页面数据包提取
                historyGetFlow=2;
                //回填
                commpairBean.setHistoryGetFlow(historyGetFlow);
            }
        }
        
        if(historyGetFlow==2){//定义的实体提取
            map.put("entity", appBusinessBean.getDisplayIp());
        }
        
        if(historyGetFlow==1){//页面提取
            //判断协议情况  tcp udp icmp 
            if(StringUtils.isNotEmpty(protocolStr)){
                map.put("p", protocolStr);
            }
            //页面输入 serverIp clientIp 情况
            if(StringUtils.isNotEmpty(commpairBean.getServerip()) && StringUtils.isNotEmpty(commpairBean.getClientip())){
                int serindex=commpairBean.getServerip().indexOf(":");
                int cliindex=commpairBean.getClientip().indexOf(":");
                if(serindex>0){
                    String[] serArray= commpairBean.getServerip().split(":");
                    map.put("server-ip", serArray[0]);
                    map.put("server-port", serArray[1]);
                }else{
                    map.put("server-ip", commpairBean.getServerip()); 
                }
                if(cliindex>0){
                    String[] cliArray= commpairBean.getClientip().split(":");
                    map.put("client-ip", cliArray[0]);
                    map.put("client-port", cliArray[1]);
                }else{
                    map.put("client-ip", commpairBean.getClientip());
                }
            }else{//输入 ip port 情况
                map.put("ip", commpairBean.getIpStr()==null?"":commpairBean.getIpStr());
                map.put("port", commpairBean.getServerport()==0?"":String.valueOf(commpairBean.getServerport()));
            }
            if(appBusinessBean!=null){
                map.put("entity", appBusinessBean.getDisplayIp());
            }
        }
        
        try {
            commpairFlowReturnBean = commpairFlowNewService.historyExtract(commpairBean, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commpairFlowReturnBean;
    }
    
    /**
     * @Title: historyExtract
     * @Description: 流量下载
     * @param commpairBean
     * @param map
     * @return CommpairFlowReturnBean
     * @throws IOException 
     * @author chensq
     */
    public CommpairFlowReturnBean historyExtract(CommpairBean commpairBean, Map<String, String> map) throws IOException {
        //验证错误类型
        CommpairFlowErrorUtil commpairFlowErrorUtil=new CommpairFlowErrorUtil();
        
        //返回
        CommpairFlowReturnBean commpairFlowReturnBean=new CommpairFlowReturnBean();

        //通信对参数
        String starttimeStr = commpairBean.getStarttimeStr();
        String endtimeStr = commpairBean.getEndtimeStr();
        String watchpointIds = commpairBean.getWatchpointIds();

        String bpf = commpairBean.getBpf();
        
        //配置文件参数
        PropertiesUtil propertiesUtil=new PropertiesUtil("sysset.properties");
        String fileDictionary=propertiesUtil.readProperty("file_dictionary");
                
        StringBuffer sb = new StringBuffer("/usr/local/bin/extract");
       
        StringBuffer didStr=new StringBuffer();
        StringBuffer lidStr=new StringBuffer();
        StringBuffer vidStr=new StringBuffer();
        
        //观察点 (单个或者多选或者不选情况)
        
        List<WatchpointBean> watchpointBeanList=!"0".endsWith(watchpointIds)?watchpointDao.getFindByIds(watchpointIds):watchpointDao.getFindAll();
        if(watchpointBeanList!=null && watchpointBeanList.size()>0){
            for(int x=0; x<watchpointBeanList.size(); x++){
                //did
                didStr.append(watchpointBeanList.get(x).getDid().replaceAll(",", "+")); 
                if(x!=watchpointBeanList.size()-1){
                    didStr.append(",");
                }
                //lid
                lidStr.append(watchpointBeanList.get(x).getLid1().replaceAll(",", "+")); 
                if(x!=watchpointBeanList.size()-1){
                    lidStr.append(",");
                }
                //vid
                vidStr.append(watchpointBeanList.get(x).getVid().replaceAll(",", "+")); 
                if(x!=watchpointBeanList.size()-1){
                    vidStr.append(",");
                }
            }
        }
         
        sb.append(" --start \"" + starttimeStr + "\" --end \"" + endtimeStr + "\"");
        
        sb.append(" --did \""+didStr+"\" ");
        sb.append(" --lid \""+lidStr+"\" ");
        sb.append(" --vid \""+vidStr+"\" ");
              
        //迭代map
        if(map!=null && map.size()>0){
            Iterator<Entry<String, String>> it = map.entrySet().iterator();  
            while (it.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry<String, String>)  it.next();  
                
                if(e.getKey().equalsIgnoreCase("p")){
                    sb.append(" -"+e.getKey());
                    sb.append(" ");
                    sb.append(e.getValue());
                    sb.append(" ");  
                }else{
                    if(!"".equals(e.getValue())){
                        sb.append(" --"+e.getKey());
                        sb.append(" ");
                        sb.append(e.getValue());
                        sb.append(" ");                    
                    }
                }
             
            }
        }
        
            
        //默认情况即   0为通信对单条下载   1为页面提取   2为有业务
        //根据类型进行迭代
        switch (commpairBean.getHistoryGetFlow()) {
       // sb.append(" --proto tcp ");
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
        
        //文件名称
        Map<String, String> pathMap=CommpairFolderNameUtil.getFolderName(fileDictionary);

        //位置
        sb.append(" --output-path "+pathMap.get("fileDictionary") +System.getProperty("file.separator") +pathMap.get("folderName"));
        
        if ((bpf != null) && (!"".equals(bpf))) {
            sb.append(" -f \"" + bpf + "\"");
        } 
        
        //返回的类型
        String typeId="0";
        
        try {
            Process p = Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", sb.toString()});
            logger.error("---extract---sb str-: "+sb.toString());
            p.waitFor();
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            int ind = 0;
            String tmp="";
            while ((tmp = reader.readLine()) != null){
                logger.error("---extract---readLine-: "+tmp);
               
                typeId=commpairFlowErrorUtil.errorTypeId(tmp);

                if (tmp.startsWith("[OUT_FILE]")){
                    ind = 1;
                    break;
                }
            }
            if (ind == 0){
                typeId="2";
            }

            is.close();
            reader.close();
            p.destroy();
           
        } catch (Exception e) {
            e.printStackTrace();
            typeId="3";
        }
                
        //出现错误等情况，删除创建的目录
        if(!typeId.equalsIgnoreCase("0")){
            CommpairFolderNameUtil.delFolder(fileDictionary, pathMap.get("folderName"));
        }
        

        commpairFlowReturnBean.setTypeId(typeId);
        commpairFlowReturnBean.setFolderName(pathMap.get("folderName"));
        
        return commpairFlowReturnBean;
    }
 
    /**
     * @Title: toGetFolderNameList
     * @Description: 获取流量下载文件的方法，control放入service
     * @param folderName
     * @return CommpairServerFlowNameListBean
     * @author chensq
     */
    public CommpairServerFlowNameListBean toGetFolderNameList(String folderName){
      //最终返回的对象
        CommpairServerFlowNameListBean commpairGetServerFlowNameListBean=new CommpairServerFlowNameListBean();
        //名称集合
        List<CommpairServerFlowNameItemBean> returnNameList=new ArrayList<CommpairServerFlowNameItemBean>(); 
        //配置文件参数
        PropertiesUtil propertiesUtil=new PropertiesUtil("sysset.properties");
        String fileDictionary="";
        try {
            fileDictionary=propertiesUtil.readProperty("file_dictionary");
            
            List<String> nameList= CommpairFolderNameUtil.getFileNameList(fileDictionary, folderName);
            if(nameList!=null && nameList.size()>0){
                for(int x=0; x<nameList.size(); x++){
                    String fileNameItem=nameList.get(x);
                    //name to obj
                    CommpairServerFlowNameItemBean commpairServerFlowNameItemBean=new CommpairServerFlowNameItemBean();
                    commpairServerFlowNameItemBean.setId(x);
                    commpairServerFlowNameItemBean.setName(fileNameItem);
                    //文件名放入集合
                    returnNameList.add(commpairServerFlowNameItemBean);
                    //判断是否结束
                    if(fileNameItem.toUpperCase().indexOf("END")>0){
                        commpairGetServerFlowNameListBean.setFinishFlag(1);
                    }
                    //判断是否为无数据情况
                    if(fileNameItem.toUpperCase().indexOf("NO_DATA")>=0){
                        commpairGetServerFlowNameListBean.setNodataFlag(1);
                        commpairGetServerFlowNameListBean.setFinishFlag(1);
                    }
                }
                //文件名集合放入对象
                commpairGetServerFlowNameListBean.setFileName(returnNameList);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commpairGetServerFlowNameListBean;
    }
    
}
