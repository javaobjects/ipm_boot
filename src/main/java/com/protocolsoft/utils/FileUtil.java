package com.protocolsoft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * 文件Util
 * 2017年11月28日 下午4:45:40
 * @author yan
 * @version
 * @see
 */
public class FileUtil {
    
    /**
     * @Title: createFile
     * @Description: 写入文件
     * @param pathName
     * @param nodeJson void
     * @author chensq
     */
    public static void createFile(String pathName, String nodeJson) {
        try {
            File f = new File(pathName);
            if (f.exists()) {
                f.delete();
            } else {
                f.createNewFile();
            }
            PrintStream ps = new PrintStream(new FileOutputStream(f), false, "UTF-8");
            ps.println(nodeJson);
            ps.close();
        } catch (Exception e) {
        }
    }
    
    /**
     * @Title: getFile
     * @Description: 读取文件
     * @param pathName 路径
     * @return String
     * @author chensq
     */
    public static String getFile(String pathName) {
        StringBuffer sb = new StringBuffer();
        try {
            File f = new File(pathName);
            if (!f.exists()) {
                return "";
            }
            InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "UTF-8");
            int ch = 0;
            while ((ch = isr.read())!=-1) {  
                sb.append((char)ch); 
            }  
            isr.close();
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
