/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CenterIpController.java
 *创建人: www    创建时间: 2018年3月26日
 */
package com.protocolsoft.word.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.word.bean.ReportHistoryBean;
import com.protocolsoft.word.service.ReportHistoryService;

/**
 * @ClassName: DownLoadController
 * @Description: 报表下载的共用接口
 * @author 刘斌
 *
 */
@Controller
@RequestMapping(value = "/downLoadController")
public class DownLoadController {
    
    /**
     * @Fields reportHistoryService : reportHistoryService对象
     */
    @Autowired
    private ReportHistoryService reportHistoryService;
    
    /**
     * @Title: getAllTimerReportDetail
     * @Description: 报表下载的公用接口
     * @param request
     * @param response
     * @param name
     * @param id
     * @throws UnsupportedEncodingException void
     * @author 刘斌
     */
    @RequestMapping(value = "downLoadWordByName.do")
    @ResponseBody
    public void getAllTimerReportDetail(HttpServletRequest request,
            HttpServletResponse response, String name, int id) throws UnsupportedEncodingException {
        ReportHistoryBean historyBean = reportHistoryService.searchReportHistory(id);
        response.setContentType("multipart/form-data");
        name = name.replace(" ", "");
        String dir = id + "";
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath();
        File file = new File(rootPath + "/report/" + dir + File.separator + name + ".docx");
        File fileSecond = new File(rootPath + "/report/" + dir);
        String fileName = historyBean.getName() + ".docx";
        String linuxName = null;
        try {
            if("ISO-8859-1".equals(System.getProperty("sun.jnu.encoding"))){
                linuxName = fileName;
                //new String((fileName).getBytes("utf-8"),
                    //System.getProperty("sun.jnu.encoding"));
                linuxName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }else{
                linuxName = new String(fileName.getBytes(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            response.setHeader("Content-disposition",
                    String.format("attachment; filename=\"%s\"", linuxName));
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/force-download");
//            response.addHeader("Content-Disposition", "attachment;fileName=" + linuxName);
//            response.setCharacterEncoding("UTF-8");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            File fileNew;
            try {
                if (fileSecond.exists() && fileSecond.isDirectory()) {
                    File[] files = fileSecond.listFiles();
                    if(files.length==0){
                        return;
                    }
                    String fileNewName = files[0].getName();
                    fileNew = new File(rootPath + "/report/" + dir + File.separator + fileNewName);
                    
                    if("ISO-8859-1".equals(System.getProperty("sun.jnu.encoding"))){
                        linuxName = fileName;
                        //new String((fileName).getBytes("utf-8"),
                            //System.getProperty("sun.jnu.encoding"));
                        linuxName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                    }else{
                        linuxName = new String(fileName.getBytes(), "utf-8");
                    }
                    
                    if (fileNew.exists()) {
                        response.setHeader("Content-disposition",
                                String.format("attachment; filename=\"%s\"", linuxName));
                        response.setContentType("application/vnd.ms-excel;charset=utf-8");
                        response.setCharacterEncoding("UTF-8");
                        
                        byte[] buffer = new byte[1024];
                        FileInputStream fis = null;
                        BufferedInputStream bis = null;
                        try {
                            fis = new FileInputStream(fileNew);
                            bis = new BufferedInputStream(fis);
                            OutputStream os = response.getOutputStream();
                            int i = bis.read(buffer);
                            while (i != -1) {
                                os.write(buffer, 0, i);
                                i = bis.read(buffer);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}