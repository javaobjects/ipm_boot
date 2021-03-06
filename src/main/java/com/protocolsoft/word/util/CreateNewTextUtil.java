/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @ClassName: CreateNewTextUtil
 * @Description: 将字符串信息写入指定路径的文本工具类
 * @author 刘斌
 *
 */
public class CreateNewTextUtil {
    
    /**
     * @Title: createJsonFileByBeanOther
     * @Description: 创建横轴图的解释文件
     * @param id
     * @param newsPath
     * @param news void
     * @author 刘斌
     */
    public static void createJsonFileByBeanOther(String id, String newsPath,
            String news){
        String tagAumont = newsPath + File.separator + "x!"+ id + ".txt";
        try {
            String fullPath = new String((tagAumont).getBytes("GBK"),
                    System.getProperty("sun.jnu.encoding"));
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            Writer write = new OutputStreamWriter(new FileOutputStream(file),
                    "UTF-8");
            write.write(news);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }
    
    /**
     * @Title: readNewsFromTxt
     * @Description: 读取解释文件
     * @param newsPath
     * @param fileName
     * @return String
     * @author 刘斌
     */
    public static String readNewsFromTxt(String newsPath , String fileName){
        File file;
        if(!fileName.endsWith(".json")){
            file = new File(newsPath + File.separator + fileName+ ".txt");
        }else{
            file = new File(newsPath + File.separator + fileName);
        }
        if(file.exists()){
            Long filelength = file.length();  
            byte[] filecontent = new byte[filelength.intValue()];  
            try {  
                FileInputStream in = new FileInputStream(file);  
                in.read(filecontent);  
                in.close();  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                return new String(filecontent, "utf-8");  
            } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
                return null;  
            }  
        }
        return "未发现已经注释文件！";
    }
    
    /**
     * @Title: createJsonFileByBeanOther
     * @Description: 创建横轴图的解释文件
     * @param id
     * @param newsPath
     * @param news void
     * @author 刘斌
     */
    public static void createJsonOfTable(String newsPath, String fielName,
            String news){
        String tagAumont = newsPath + File.separator + fielName+".json";
        try {
            String fullPath = new String((tagAumont).getBytes("GBK"),
                    System.getProperty("sun.jnu.encoding"));
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            Writer write = new OutputStreamWriter(new FileOutputStream(file),
                    "UTF-8");
            write.write(news);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }
}
