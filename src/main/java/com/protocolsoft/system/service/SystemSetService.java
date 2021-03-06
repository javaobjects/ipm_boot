/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:SystemSetService
 *创建人:yan    创建时间:2017年7月18日
 */
package com.protocolsoft.system.service;

import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统设置业务逻辑层接口
 * 2017年7月18日 下午5:55:02
 * @author yan
 * @version
 * @see
 */
public interface SystemSetService {

    /**
     * 获取日期时间设置
     * 2017年7月18日 下午6:10:13
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    Map<String, String> getDataTimeSet();
    
    /**
     * 操作日期和时间设置
     * 2017年7月20日 下午5:33:42
     * @param sameServer 时间同步服务器
     * @param nowTime 当前系统时间
     * @return String
     * @exception 
     * @see
     */
    String operateDateTimeSet(HttpServletRequest request, String sameServer, String nowTime);
    
    /**
     * 获取产品授权设置
     * 2017年7月18日 下午6:10:29
     * @param
     * @return <Map<String, String>>
     * @exception 
     * @see
     */
    List<Map<String, String>> getProductLicensSet();
    
    /**
     * 获取数据存储设置
     * 2017年7月18日 下午6:10:40
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    Map<String, String> getDataStorageSet();
    
    /**
     * 清空目标目录
     * 2017年7月24日 上午10:27:47
     * @param targetDir 目标目录
     * @return String
     * @exception 
     * @see
     */
    String clearTargetDir(HttpServletRequest request, String targetDir);
    
    /**
     * 操作数据存储设置
     * 2017年7月24日 上午11:20:40
     * @param fileSize 文件块存储大小
     * @param diskUsage 磁盘利用率百分比上限
     * @return String
     * @exception 
     * @see
     */
    String operationDataStorageSet(HttpServletRequest request, String fileSize, String diskUsage);
    
    /**
     * 
     * @Title: systemConfigExport
     * @Description: 配置导出
     * @return Byte[]
     * @author www
     */
    ResponseEntity<byte[]> systemConfigExport(HttpServletRequest request);
    
    /**
     * 
     * @Title: systemConfigImport
     * @Description: 配置导入
     * @param name 名称
     * @param file 配置文件
     * @return SimpleEntry<String,Integer>
     * @author www
     */
    SimpleEntry<String, Integer> systemConfigImport(HttpServletRequest request, String name, MultipartFile file);

    /**
     * @Title: appConfigImport
     * @Description: 应用配置导入
     * @param name 名称
     * @param file 文件
     * @return String
     * @author www
     */
    String appConfigImport(HttpServletRequest request, String name, MultipartFile file);

    /**
     * @Title: appConfigExport
     * @Description: 应用配置导出
     * @return ResponseEntity<byte []>
     * @author www
     */
    ResponseEntity<byte[]> appConfigExport(HttpServletRequest request);
    
    /**
     * 
     * @Title: selectTablesData
     * @Description: 查询粒度表
     * @return List<Map<String,Object>>
     * @author wangjianmin
     */
    List<Map<String, Object>> selectTablesData();
    
    /**
     * 
     * @Title: getValidterm
     * @Description: 获取有效期
     * @return String
     * @author wangjianmin
     */
    String getValidterm();
}
