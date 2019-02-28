/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:PropertiesUtil
 *创建人:yan    创建时间:2017年7月19日
 */
package com.protocolsoft.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName: PropertiesUtil
 * @Description: 属性文件工具类
 * @author chensq
 *
 */
public class PropertiesUtil {
    
    /**
     * @Fields logger : 日志实例
     */
    Logger logger = Logger.getLogger(PropertiesUtil.class);
    
    /**
     * @Fields properiesName : 属性文件名称
     */
    private String properiesName = "";
    
    /**
     * <p>Title:PropertiesUtil </p>
     * <p>Description: 无参构造方法</p>
     */ 
    public PropertiesUtil() {

    }
    
    /**
     * <p>Title: PropertiesUtil</p>
     * <p>Description:有参构造方法 </p>
     * @param fileName
     */ 
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }
    
    /**
     * @Title: readProperty
     * @Description: 读取属性文件
     * @param key 键
     * @return String
     * @throws IOException 
     * @author chensq
     */
    public String readProperty(String key) throws IOException {
        String value = "";
        InputStreamReader is = null;
        is = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName),
                    "UTF-8");
        Properties p = new Properties();
        p.load(is);
        value = p.getProperty(key);
        is.close();
        return value;
    }
 
    /**
     * @Title: getProperties
     * @Description: 获取属性对象
     * @return Properties
     * @throws IOException 
     * @author chensq
     */
    public Properties getProperties() throws IOException {
        Properties p = new Properties();
        InputStreamReader is = null;
        is = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName),
                    "UTF-8");
        p.load(is);
        is.close();
        return p;
    }
    
    /**
     * @Title: writeProperty
     * @Description: 写入属性文件
     * @param key 键,值
     * @param value
     * @throws IOException void
     * @author chensq
     */
    public void writeProperty(String key, String value) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        is = new FileInputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
        p.load(is);
        os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
        p.setProperty(key, value);
        p.store(os, key);
        os.flush();
        os.close();
        if (null != is){
            is.close(); 
        }
        if (null != os){
            os.close(); 
        }
    }
    
    /**
     * @Title: writePropertyReturn
     * @Description: 写入属性文件
     * @param key
     * @param value
     * @return String
     * @author chensq
     */
    public String writePropertyReturn(String key, String value) {
        String returnStr="1";
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            returnStr="0";
            e.printStackTrace();
        } finally {
            try {
                if (null != is){
                    is.close(); 
                }
                if (null != os){
                    os.close(); 
                }
            } catch (IOException e) {
                returnStr="0";
                e.printStackTrace();
            }
        }
        return returnStr;
    }
}
