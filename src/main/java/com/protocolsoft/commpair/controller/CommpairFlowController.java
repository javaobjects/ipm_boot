/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFlowController
 *创建人:chensq    创建时间:2017年12月4日
 */
package com.protocolsoft.commpair.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.bean.CommpairFlowReturnBean;
import com.protocolsoft.commpair.bean.CommpairServerFlowNameListBean;
import com.protocolsoft.commpair.service.CommpairFlowNewService;
import com.protocolsoft.commpair.service.CommpairFlowService;
import com.protocolsoft.log.annotation.Log;
import com.protocolsoft.utils.PropertiesUtil;

/**
 * @ClassName: CommpairFlowController
 * @Description: 通信对流量Controller
 * @author chensq
 *
 */
@Controller
@RequestMapping(value = "/commpairFlow")
public class CommpairFlowController {
    
    /**
     * @Fields appBusinessDao : app应用dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
   
    /**
     * @Fields commpairFlowService : 通信对flow流量service
     */
    @Autowired
    private CommpairFlowService commpairFlowService;
        
    /**
     * @Fields commpairFlowNewService : 通信对flow流量new service
     */
    @Autowired
    private CommpairFlowNewService commpairFlowNewService;
    
    /**
     * @Title: historyExtract
     * @Description: 通信对流量查询
     * @param commpairBean
     * @return CommpairFlowReturnBean
     * @author chensq
     */
    @RequestMapping(value="historyExtract.do")
    @ResponseBody 
    public CommpairFlowReturnBean historyExtract(CommpairBean commpairBean){
        CommpairFlowReturnBean commpairFlowReturnBean=new CommpairFlowReturnBean();
        commpairFlowReturnBean = commpairFlowNewService.toHistoryExtract(commpairBean, appBusinessDao, commpairFlowNewService);
        return commpairFlowReturnBean;
    }
    
    /**
     * @Title: getFolderNameList
     * @Description: 获取服务器指定文件夹下的文件名称
     * @param folderName
     * @return CommpairServerFlowNameListBean
     * @author chensq
     */
    @RequestMapping(value="historyNameList.do")
    @ResponseBody 
    public CommpairServerFlowNameListBean getFolderNameList(String folderName) {
        return commpairFlowNewService.toGetFolderNameList(folderName);
    }
        
    /**
     * @Title: listExtract
     * @Description: 通信对流量文件下载
     * @param commpairFlowReturnBean void
     * @author chensq
     */
    @Log(smallModuleId = 9, description = "流量下载")
    @RequestMapping(value="listExtract.do")
    @ResponseBody 
    public void listExtract(CommpairFlowReturnBean commpairFlowReturnBean){
        String filename = commpairFlowReturnBean.getFileName();
        String folderName =commpairFlowReturnBean.getFolderName();
        String filePath = null;
        
        ServletRequestAttributes servletRequestAttributes  = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        
       //配置文件参数
        PropertiesUtil propertiesUtil=new PropertiesUtil("sysset.properties");
        String fileDictionary="";
        try {
            fileDictionary=propertiesUtil.readProperty("file_dictionary");
        } catch (Exception e) {
            e.printStackTrace();            
        }
      
        //下载
        int len = fileDictionary.length();
        try {
            char c = fileDictionary.charAt(len - 1);
            if (c == '\\' || c == '/') {
                filePath = fileDictionary +System.getProperty("file.separator") + folderName +System.getProperty("file.separator") + filename;
            }else{
                filePath = fileDictionary + System.getProperty("file.separator") + folderName +System.getProperty("file.separator") + filename;
            }
            byte[] buff = new byte[10240];
            OutputStream bos = response.getOutputStream();
            File f = new File(filePath);
            FileInputStream bis = new FileInputStream(f);
            long filelength = f.length();
            String filesize = Long.toString(filelength);
            response.addHeader("Content-Length", filesize);
            response.addHeader("Content-disposition", "attachment;filename=" + filename);
            int readCount = 0;
            readCount = bis.read(buff);
            while (readCount != -1){
                bos.write(buff, 0, readCount);
                readCount = bis.read(buff);
            }
            bos.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
  
    
}
