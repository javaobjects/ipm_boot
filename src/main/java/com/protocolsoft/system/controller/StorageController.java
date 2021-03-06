/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemNetworkSetController
 *创建人:long    创建时间:2017年7月18日
 */
package com.protocolsoft.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.system.bean.DataStorageBean;
import com.protocolsoft.system.bean.DownloadFileBean;
import com.protocolsoft.system.service.StorageService;

/**
 * StorageController
 * 2017年12月8日 下午4:59:09
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/storage")
public class StorageController {
    
    /**StorageService注入*/
    @Autowired(required = false)
    private StorageService storageService;
    
    /**
     * 添加存储
     * 2017年12月8日 下午5:00:33
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/addStorage.do")
    @ResponseBody
    public DataStorageBean addStorage(HttpServletRequest request, DataStorageBean dataStorageBean) {
        return storageService.addStorage(request, dataStorageBean);
    }
    /**
     * 删除存储
     * 2017年12月8日 下午5:01:00
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/delStorageById.do")
    @ResponseBody
    public Map<String, String> delStorageById(HttpServletRequest request, int id) {
        return storageService.delStorageById(request, id);
    }
    /**
     * 修改存储
     * 2017年12月8日 下午5:01:23
     * @param
     * @return DataStorageBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateStorageById.do")
    @ResponseBody
    public DataStorageBean updateStorageById(HttpServletRequest request, DataStorageBean dataStorageBean) {
        return storageService.updateStorageById(request, dataStorageBean);
    }
    /**
     * 查询存储
     * 2017年12月8日 下午5:01:36
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getStorage.do")
    @ResponseBody
    public List<DataStorageBean> getStorage() {
        return storageService.getStorage();
    }
    /**
     * 根据id查询存储
     * 2017年12月11日 下午4:23:16
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getStorageById.do")
    @ResponseBody
    public DataStorageBean getStorageById(int id) {
        return storageService.getStorageById(id);
    }
    /**
     * 查询高级存储
     * 2017年12月11日 下午4:24:31
     * @param
     * @return List<DataStorageBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getStorageHigh.do")
    @ResponseBody
    public List<DataStorageBean> getStorageHigh() {
        return storageService.getStorageHigh();
    }
    
    /**
     * 
     * @Title: getDownloadFileList
     * @Description: 获取下载数据包列表
     * @param dir 目录
     * @return List<DownloadFileBean>
     * @author www
     */
    @RequestMapping(value = "/getDownloadFileList.do")
    @ResponseBody
    public List<DownloadFileBean> getDownloadFileList(String dir) {
        
        return storageService.getDownloadFileList(dir);
    }
}
