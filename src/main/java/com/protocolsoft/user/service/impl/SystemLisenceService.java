/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLisenceService.java
 *创建人: WWW    创建时间: 2018年9月17日
 */
package com.protocolsoft.user.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.system.service.impl.SystemSetServiceImpl;
import com.protocolsoft.user.bean.AuthorizeModuleBean;
import com.protocolsoft.user.bean.LicenseInfoBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * @ClassName: SystemLisenceService
 * @Description: 系统授权
 * @author WWW
 *
 */
@Service
public class SystemLisenceService {
    
    /**
     * 授权信息文件名称
     */
    private final static String FILE_NAME = "AUTHORIZE.INFO";
    
    /**
     * 授权信息加密文件名称
     */
    private final static String ENCODE_NAME = "AUTHORIZE.EINFO";
    
    /**
     * 上传文件名称
     */
    private final static String UPLOAD_NAME = "license.tar.gz";
    
    /**
     * 换行字符
     */
    private final static String LINE_CHAR = "\r\n";
    
    /**
     * 系统设置
     */
    @Autowired(required = false)
    private SystemSetServiceImpl systemSetService;
    
    /**
     * 用户信息
     */
    @Autowired
    private SystemUserService systemUserService;
    
    /**
     * 授权
     */
    @Autowired
    private AuthorizeModuleServer authorizeModuleServer;
    
    /**
     * 观察点
     */
    @Autowired
    private WatchpointService watchpointService;
    
    /**
     * LOG
     */
    @Autowired
    private LogsDao logsDao;
    
    /**
     * 
     * @Title: generationAuthorize
     * @Description: 生成授权信息
     * @param request 请求
     * @param bean 授权信息
     * @return boolean
     * @author WWW
     */
    public boolean generationAuthorize(HttpServletRequest request, LicenseInfoBean bean) {
        boolean bool = false;
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path + FILE_NAME);
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            bool = file.createNewFile();
            if (bool) {
                os = new FileOutputStream(file);
                osw = new OutputStreamWriter(os, "UTF-8");
                bw = new BufferedWriter(osw);
                // 写入系统版本号
                PropertiesUtil propertiesUtil = new PropertiesUtil("sysinfo.properties");
                bean.setProductNo(propertiesUtil.readProperty("productNo"));
                // 写入基础信息
                this.writeBasics(bean, osw);
                // 写入网卡MAC信息
                this.writeNetcardMacId(osw);
                // 写入CPU信息
                this.writeCpuId(osw);
                osw.flush();
                
                String filePath = path + ENCODE_NAME;
                File encodeFile = new File(filePath);
                if (encodeFile.exists()) {
                    encodeFile.delete();
                }
                String shell = "/usr/local/bin/mksysinfo -i " + path + FILE_NAME + " -o " + filePath;
                String info  = this.getShellData(shell).get(0);
                if (info != null && info.startsWith("[OUT_FILE]")) {
                    bool = true;
                    this.addLogs(request, 11, "生成授权信息加密文件成功");
                } else {
                    bool = false;
                    this.addLogs(request, 11, "生成授权信息加密文件失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SigarException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: generationEncodeFile
     * @Description: 获取加密文件
     * @param request 请求
     * @return ResponseEntity<byte[]>
     * @author WWW
     */
    public ResponseEntity<byte[]> generationEncodeFile(HttpServletRequest request) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        ResponseEntity<byte[]> entity = systemSetService.fileDowload(ENCODE_NAME, path);
        this.addLogs(request, 11, "下载授权信息加密文件");
        
        return entity;
    }
    
    /**
     * 
     * @Title: uploadAuthorizeFile
     * @Description: 上传授权文件
     * @param request 请求
     * @param uploadFile 授权信息
     * @return boolean
     * @author WWW
     */
    public boolean uploadAuthorizeFile(HttpServletRequest request, MultipartFile uploadFile) {
        boolean bool = false;
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path + UPLOAD_NAME);
        if (file.exists()) {
            file.delete();
        }
        try {
            uploadFile.transferTo(file);
            String shell = "gunzip -f " + path + UPLOAD_NAME + ";tar -xvf " + path + "license.tar -C /home";
            this.getShellData(shell);
            file = new File("/home");
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                String name = null;
                File tmp = null;
                for (int i = 0, len = files.length; i < len; i ++) {
                    tmp = files[i];
                    name = tmp.getName();
                    if("key".equals(name)) {
                        tmp.renameTo(new File("/etc/ipmkey"));
                    } else if ("sysinfo.properties".equals(name)) {
                        tmp.renameTo(new File(path + "sysinfo.properties"));
                    }
                }
                bool = this.updateAuthorizeModule();
                if (bool) {
                    bool = this.chkLicense();
                }
            }
            Runtime.getRuntime().exec(new String []{"/bin/sh", "-c", "/usr/local/var/chkxpm.sh kill"});
            
            // 修改日期
            PropertiesUtil util = new PropertiesUtil("sysinfo.properties");
            String time = util.readProperty("validterm");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long validterm = sdf.parse(time).getTime();
            if (validterm != 0) {
                watchpointService.updateValidterm((int) (validterm / 1000));
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) { 
            e.printStackTrace();
        } catch (ParseException e) { 
            e.printStackTrace();
        }
        this.addLogs(request, 11, "上传授权文件");
        
        return bool;
    }
    
    /**
     * 
     * @Title: chkLicense
     * @Description: 检测授权文件
     * @return boolean
     * @author WWW
     */
    public boolean chkLicense() {
        boolean bool = false;
        String shell = "/usr/local/bin/chklicense";
        List<String> info = this.getShellData(shell);
        for (int i = 0, len = info.size(); i < len; i ++) {
            if ("[GOOD LICENSE]".equals(info.get(i))) {
                bool = true;
                break;
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: addLogs
     * @Description: 添加日志
     * @param request 请求
     * @param moduleId 模块
     * @param msg 信息 
     * @author WWW
     */
    public void addLogs(HttpServletRequest request, int moduleId, String msg) {
        // 用户信息
        SystemUserBean userBean = systemUserService.getSessionUserBean(request);
        
        this.addLogs(userBean, moduleId, msg);
    }
    
    /**
     * 
     * @Title: addLogs
     * @Description: 添加日志
     * @param userBean 用户信息
     * @param moduleId 模块
     * @param msg void 信息
     * @author WWW
     */
    public void addLogs(SystemUserBean userBean, int moduleId, String msg) {
        // 添加log日志参数bean
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(userBean.getId());
        logsBean.setModuleId(moduleId);
        logsBean.setMsg(msg);
        logsBean.setTime(DateUtils.getNowTimeSecond());

        // 添加系统日志
        logsDao.insertLogs(logsBean);
    }
    
    /**
     * 
     * @Title: updateAuthorizeModule
     * @Description: 更新模块授权
     * @return boolean
     * @author WWW
     */
    private boolean updateAuthorizeModule() {
        boolean bool = false;
        PropertiesUtil util = new PropertiesUtil("sysinfo.properties");
        try {
            Properties proper = util.getProperties();
            List<AuthorizeModuleBean> list = authorizeModuleServer.selectAllModule();
            AuthorizeModuleBean bean = null;
            ModuleType type = null;
            String value = null;
            boolean isopen = false;
            for (int i = 0, len = list.size(); i < len; i ++) {
                bean = list.get(i);
                type = ModuleType.getModuleType(bean.getNameen());
                value = proper.getProperty(type.getName(), null);
                if (value != null) {
                    if ("1".equals(value)) {
                        isopen = true;
                    } else {
                        isopen = false;
                    }
                    authorizeModuleServer.updateByIdModule(type.getModuleId(), isopen);
                }
            }
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: writeBasics
     * @Description: 写入基础信息
     * @param bean 基础信息
     * @param osw 输入流
     * @throws IOException IO
     * @throws IllegalAccessException Illegal
     * @author WWW
     */
    private void writeBasics(LicenseInfoBean bean, OutputStreamWriter osw)
        throws IOException, IllegalAccessException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        Class<?> fieldType = null;
        for (Field f : fields) {
            sb.setLength(0);
            f.setAccessible(true);
            fieldType = f.getType();
            sb.append(f.getName());
            sb.append("=");
            if (String.class.isAssignableFrom(fieldType)) {
                if (f.get(bean) != null) {
                    sb.append(f.get(bean));
                }
            } else if (boolean.class.isAssignableFrom(fieldType)) {
                if (Boolean.valueOf(f.get(bean).toString())) {
                    sb.append(1);
                } else {
                    sb.append(0);
                }
            } else {
                sb.append(f.get(bean));
            }
            sb.append(LINE_CHAR);
            osw.write(sb.toString());
        }
    }
    
    /**
     * 
     * @Title: writeNetcardMacId
     * @Description: 写入网卡信息
     * @param osw 输入流
     * @throws IOException IO
     * @throws SigarException Sigar
     * @author WWW
     */
    private void writeNetcardMacId(OutputStreamWriter osw) 
        throws IOException, SigarException {
        Sigar sigar = new Sigar();
        String[] netcards = sigar.getNetInterfaceList();
        NetInterfaceConfig config = null;
        for (String s: netcards) {
            config = sigar.getNetInterfaceConfig(s);
            if ("lo".equals(config.getName())) {
                continue;
            }
            osw.write("mac." + config.getName() + "=" + config.getHwaddr() + LINE_CHAR);
        }
    }
  
    /**
     * 
     * @Title: writeCpuId
     * @Description: 写入CPU信息
     * @param osw 输入流
     * @throws IOException void
     * @author WWW
     */
    private void writeCpuId(OutputStreamWriter osw) throws IOException {
        String shell = "/usr/sbin/dmidecode -t processor | grep ID | awk -F: '{print $2}'";
        List<String> info = this.getShellData(shell);
        for (int i = 0, len = info.size(); i < len; i ++) {
            osw.write("cpuid" + (i + 1) + "=" + info.get(i).trim() + LINE_CHAR);
        }
    }
    
    /**
     * 
     * @Title: getShellData
     * @Description: 获取SHELL数据
     * @param shell 执行的命名
     * @return List<String>
     * @author WWW
     */
    private List<String> getShellData(String shell) {
        List<String> info = new ArrayList<String>();
        Process p = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", shell});
            p.waitFor();
            is = p.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                info.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (p != null) {
                    p.destroy();
                }
            }
        }
        
        return info;
    }
}
