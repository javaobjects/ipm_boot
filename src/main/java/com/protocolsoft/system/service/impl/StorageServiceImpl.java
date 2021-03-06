/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetServiceImpl
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.system.bean.DataStorageBean;
import com.protocolsoft.system.bean.DownloadFileBean;
import com.protocolsoft.system.dao.StorageDao;
import com.protocolsoft.system.service.StorageService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;

/**
 * StorageServiceImpl
 * 2017年12月11日 上午9:24:49
 * @author long
 * @version
 * @see
 */
@Service
public class StorageServiceImpl implements StorageService {
    /**
     * storageDao注入
     */
    @Autowired(required=false)
    private StorageDao storageDao;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    @Override
    public DataStorageBean addStorage(HttpServletRequest request, DataStorageBean dataStorageBean) {
        int isSuccess=storageDao.addStorage(dataStorageBean);
        //得到当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        if (isSuccess>0){
            dataStorageBean.setSuccess("0");
        } else {
            dataStorageBean.setSuccess("1");
        }
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(3);
        logsBean.setMsg("添加"+dataStorageBean.getName()+"高级存储");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        return dataStorageBean;
    }

    @Override
    public Map<String, String> delStorageById(HttpServletRequest request, int id) {
        //根据id得到预删除的高级存储，用于日志
        DataStorageBean bean = storageDao.getStorageById(id);
        
        //得到当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        boolean isSuccess=storageDao.delStorageById(id);
        Map<String, String> map=new HashMap<String, String>();
        if (isSuccess){
            map.put("success", "0");
        } else {
            map.put("success", "1");
        }
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(3);
        logsBean.setMsg("删除"+bean.getName()+"高级存储");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        return map;
    }

    @Override
    public DataStorageBean updateStorageById(HttpServletRequest request, DataStorageBean dataStorageBean) {
        int isSuccess=storageDao.updateStorageById(dataStorageBean);
        //得到当前用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        if (isSuccess>0){
            dataStorageBean.setSuccess("0");
        } else {
            dataStorageBean.setSuccess("1");
        }
        //添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(3);
        if(!dataStorageBean.getName().equals("标准存储方案")){
            logsBean.setMsg("更新"+dataStorageBean.getName()+"高级存储");
        }else{
            logsBean.setMsg("更新标准存储");
        }
        
        logsBean.setTime(DateUtils.getNowTimeSecond());
        
        //添加系统日志
        logsDao.insertLogs(logsBean);
        
        return dataStorageBean;
    }

    @Override
    public List<DataStorageBean> getStorage() {
        return storageDao.getStorage();
    }

    @Override
    public DataStorageBean getStorageById(int id) {
        return storageDao.getStorageById(id);
    }

    @Override
    public List<DataStorageBean> getStorageHigh() {
        return storageDao.getStorageHigh();
    }

    /** (非 Javadoc)
     * <p>Title: getDownloadFileList</p>
     * <p>Description: 获取下载数据包列表</p>
     * @param dir 目录
     * @return List<DownloadFileBean>
     * @see com.protocolsoft.system.service.StorageService#getDownloadFileList()
     */
    public List<DownloadFileBean> getDownloadFileList(String dir) {
        List<DownloadFileBean> list = new ArrayList<DownloadFileBean>();
        String path = null;
        try {
            path = new PropertiesUtil("sysset.properties").readProperty("file_dictionary");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String osName = System.getProperty("os.name").toLowerCase();
        if (path != null && osName.indexOf("linux") > -1) {
            DownloadFileBean bean = null;
            if (dir != null) {
                path += File.separator + dir;
            }
            File[] files = new File(path).listFiles();
            if (files != null) {
                File file = null;
                File[] tmpFiles = null;
                long fileLen = 0;
                for (int i = 0, len = files.length; i < len; i ++) {
                    file = files[i];
                    bean = new DownloadFileBean();
                    bean.setName(file.getName());
                    bean.setType(file.isDirectory() ? 1 : 2);
                    if (bean.getType() == 1) {
                        fileLen = 0;
                        tmpFiles = file.listFiles();
                        for (int j = 0, jlen = tmpFiles.length; j < jlen; j ++) {
                            fileLen += tmpFiles[j].length();
                        }
                        bean.setSize(fileLen);
                    } else {
                        bean.setSize(file.length());
                    }
                    bean.setTime(file.lastModified());
                    list.add(bean);
                }
            }
        }
        
        //按照时间倒叙
        if (list!=null && list.size()>0) {
            listSort(list);
        }
        
        return list;
    }
    /**
     * 将流量历史提取对象的集合按照时间倒叙
     * 2017年12月28日 下午7:01:38
     * @param list 流量历史提取对象
     * @exception 
     * @see
     */
    private static void listSort(List<DownloadFileBean> list) {
        Collections.sort(list, new Comparator<DownloadFileBean>() {
            @Override
            public int compare(DownloadFileBean o1, DownloadFileBean o2) {
                try {
                    if (o1.getTime() < o2.getTime()) {
                        return 1;
                    } else if (o1.getTime() > o2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    

}
