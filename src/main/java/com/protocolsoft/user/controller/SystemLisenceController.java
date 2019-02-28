/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLisenceController.java
 *创建人: WWW    创建时间: 2018年9月17日
 */
package com.protocolsoft.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.protocolsoft.user.bean.LicenseInfoBean;
import com.protocolsoft.user.service.impl.SystemLisenceService;

/**
 * @ClassName: SystemLisenceController
 * @Description: 系统授权
 * @author WWW
 *
 */
@Controller
@RequestMapping(value = "/lisence")
public class SystemLisenceController {
    
    /**
     * 系统授权
     */
    @Autowired
    private SystemLisenceService service;
    
    /**
     * 
     * @Title: generationAuthorize
     * @Description: 生成授权信息
     * @param request 请求
     * @param bean 授权信息
     * @return Map<String,Boolean>
     * @author WWW
     */
    @RequestMapping(value = "/generationAuthorize.do")
    @ResponseBody
    public Map<String, Boolean> generationAuthorize(HttpServletRequest request, LicenseInfoBean bean) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        boolean bool = service.generationAuthorize(request, bean);
        map.put("state", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: generationEncodeFile
     * @Description: 获取加密授权文件
     * @param request 请求
     * @return ResponseEntity<byte[]>
     * @author WWW
     */
    @RequestMapping(value = "/generationEncodeFile.do")
    public ResponseEntity<byte[]> generationEncodeFile(HttpServletRequest request) {
        
        return service.generationEncodeFile(request);
    }
    
    /**
     * 
     * @Title: uploadAuthorizeFile
     * @Description: 上传授权文件
     * @param request 请求信息
     * @param file 文件信息
     * @return Map<String,Object>
     * @author WWW
     */
    @RequestMapping(value = "/uploadAuthorizeFile.do")
    @ResponseBody
    public Map<String, Object> uploadAuthorizeFile(
            HttpServletRequest request, MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean bool = service.uploadAuthorizeFile(request, file);
        map.put("state", bool);
        
        return map;
    }
}
