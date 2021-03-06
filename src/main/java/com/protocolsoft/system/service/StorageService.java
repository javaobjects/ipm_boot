/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetService
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.system.bean.DataStorageBean;
import com.protocolsoft.system.bean.DownloadFileBean;

/**
 * StorageService
 * 2017年12月8日 下午4:53:55
 * @author long
 * @version
 * @see
 */
public interface StorageService {
    /**
     * 添加存储
     * 2017年12月8日 下午4:55:56
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    DataStorageBean addStorage(HttpServletRequest request, DataStorageBean dataStorageBean);
    /**
     * 删除存储
     * 2017年12月11日 上午9:30:21
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> delStorageById(HttpServletRequest request, int id);
    /**
     * 更新存储
     * 2017年12月8日 下午4:56:09
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    DataStorageBean updateStorageById(HttpServletRequest request, DataStorageBean dataStorageBean);
    /**
     * 查询存储
     * 2017年12月8日 下午4:56:13
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    List<DataStorageBean> getStorage();
    /**
     * 根据id查询存储
     * 2017年12月11日 下午4:20:52
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    DataStorageBean getStorageById(int id);
    /**
     * 查询高级存储
     * 2017年12月11日 下午4:21:08
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    List<DataStorageBean> getStorageHigh();
    
    /**
     * 
     * @Title: getDownloadFileList
     * @Description: 获取下载数据包列表
     * @param dir 目录
     * @return List<DownloadFileBean>
     * @author www
     */
    List<DownloadFileBean> getDownloadFileList(String dir);
}
