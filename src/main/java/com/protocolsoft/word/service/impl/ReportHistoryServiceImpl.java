/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.word.bean.ReportHistoryBean;
import com.protocolsoft.word.controller.HavingTimeProductNewWordController;
import com.protocolsoft.word.dao.ReportHistoryDao;
import com.protocolsoft.word.service.ReportHistoryService;

/**
 * @ClassName: ReportHistoryServiceImpl
 * @Description: 历史报表信息控制器
 * @author 刘斌
 *
 */
@Service
public class ReportHistoryServiceImpl implements ReportHistoryService {
    /**
     * @Fields dao : 报表历史Dao
     */
    @Autowired
    private ReportHistoryDao dao;
    
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;
    
    /**
     * @Fields systemUserService : 用户Service
     */
    @Autowired
    private SystemUserService systemUserService;

    /* (非 Javadoc)
     * <p>Title: insertReportHistory</p>
     * <p>Description: 插入历史报表信息</p>
     * @param bean
     * @return
     * @see com.protocolsoft.word.service.ReportHistoryService#insertReportHistory(com.protocolsoft.word.bean.ReportHistoryBean)
     */
    @Override
    @Transactional
    public Integer insertReportHistory(ReportHistoryBean bean) {
        try {
            return dao.insertReportHistory(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* (非 Javadoc)
     * <p>Title: deleteReportHistory</p>
     * <p>Description: 删除历史报表信息</p>
     * @param id
     * @param path
     * @param name
     * @return
     * @see com.protocolsoft.word.service.ReportHistoryService#deleteReportHistory(int, java.lang.String, java.lang.String)
     */
    @Override
    public boolean deleteReportHistory(int id, String path, String name, SystemUserBean bean) {
        name = name.replace(" ", "");
        String dir = id+"";
        try {
            File file = new File(path + File.separator + dir +File.separator + name + ".docx");
            File filePr = new File(path + File.separator + dir);
            if (file.exists()) {
                boolean df = file.delete();
                if(df){
                    boolean dd = filePr.delete();
                    if(!dd){
                        File fileDir = new File(path + File.separator + dir);
                        File[] files = fileDir.listFiles();
                        if(files.length>0){
                            for(File fileDelete : files){
                                fileDelete.delete();
                            }
                        }
                        fileDir.delete();
                    }
                }
            } 
            if (!file.exists()) {
                dao.deleteReportHistory(id);
            }
            
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(bean.getId());
            logsBean.setModuleId(19);
            logsBean.setMsg(bean.getRealName()+"删除历史报表信息，并删除与之对应的报表");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsDao.insertLogs(logsBean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /* (非 Javadoc)
     * <p>Title: searchReportHistory</p>
     * <p>Description: 通过id查找历史报表信息</p>
     * @param id
     * @return
     * @see com.protocolsoft.word.service.ReportHistoryService#searchReportHistory(int)
     */
    @Override
    public ReportHistoryBean searchReportHistory(int id) {
        return dao.selectReportHistory(id);
    }
    
    /* (非 Javadoc)
     * <p>Title: searchAllReportHistory</p>
     * <p>Description: 获取历史信息列表</p>
     * @param starttime
     * @param endtime
     * @return
     * @see com.protocolsoft.word.service.ReportHistoryService#searchAllReportHistory(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<ReportHistoryBean> searchAllReportHistory(Long starttime, Long endtime) {
        return dao.selectAllReportHistory(starttime, endtime);
    }

    /* (非 Javadoc)
     * <p>Title: downLoadBookFromMyServer</p>
     * <p>Description: 历史报表下载</p>
     * @param request
     * @param response
     * @param name
     * @see com.protocolsoft.word.service.ReportHistoryService#downLoadBookFromMyServer
     * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    @Override
    public void downLoadBookFromMyServer(HttpServletRequest request,
            HttpServletResponse response, String name) {
        String rootPath = this.getClass().getClassLoader().getResource("/")
                .getPath();
        File file = new File(rootPath + "/report/" + name + ".docx");
        String fileName = file.getName();
        String linuxName = null;
        try {
            linuxName = new String((fileName).getBytes("GBK"),
                    System.getProperty("sun.jnu.encoding"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName="
                    + linuxName);
            response.setCharacterEncoding("UTF-8");
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
                SystemUserBean  bean = systemUserService.getSessionUserBean(request);
                LogsBean logsBean = new LogsBean();
                logsBean.setUserId(bean.getId());
                logsBean.setModuleId(19);
                logsBean.setMsg(bean.getRealName()+"下载报表"+name);
                logsBean.setTime(DateUtils.getNowTimeSecond());
                logsDao.insertLogs(logsBean);
            }
        }
    }

    @Override
    public List<ReportHistoryBean> selectReportHistoryByUserId(Integer id) {
        return dao.selectReportHistoryByUserId(id);
    }

    @Override
    @Transactional
    public void deleteReportHistoryByCount() {
        String path = HavingTimeProductNewWordController.class.getClassLoader().getResource("report").getPath();
        Integer count = dao.selectReportHistoryCount();
        Integer counts = count > 100?count-100:0;
        if(counts!=0){
            List<Integer> list = dao.selectReportHistoryIds(counts);
            for(Integer id : list){
                File fileDir = new File(path + File.separator + id);
                File[] files = fileDir.listFiles();
                if(files.length>0){
                    for(File fileDelete : files){
                        fileDelete.delete();
                    }
                }
                fileDir.delete();
                dao.deleteReportHistory(id);
                
                LogsBean logsBean = new LogsBean();
                logsBean.setUserId(0);
                logsBean.setModuleId(19);
                logsBean.setMsg("系统定时"+"删除最近的"+ 100 +"条以外的历史报表信息，并删除与之对应的报表");
                logsBean.setTime(DateUtils.getNowTimeSecond());
                logsDao.insertLogs(logsBean);
            }
        }
    }

    @Override
    public int updateHistoryBeanBySend(Long sendTime, Integer id) {
        return dao.updateHistoryBeanBySend(sendTime, id);
    }
}
