/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.io.File;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.protocolsoft.app.bean.PlotDataBean;

/**
 * @ClassName: JsonStringOutputDocUtil
 * @Description: 分析获取信息生成规定类型的json
 * @author 刘斌
 *
 */
@Component
public class JsonStringOutputDocUtil {

    /**
     * @Title: createJsonFile
     * @Description: 分析获取信息生成规定类型的json
     * @param path
     * @param id
     * @param fileName
     * @param map
     * @param x void
     * @return String
     * @author 刘斌
     */
    public static String createJsonFile(String path, String id, String fileName,
            Map<String, Object> map, int x) {
        Map<String, Object> newMap = null;
        String jsonString = "";
        if ("yes".equals(map.get("exeption"))) {
            newMap = CreateJsonModleErrorUtil.getJsonModel(fileName);
        } else {
            if (x == 1) {
                newMap = CreateJsonModleLineUtil.getJsonModel(fileName, map);
            } else if (x == 2) {
            } else if (x == 3) {
                newMap = CreateJsonModlePieUtil.getJsonModel(fileName, map);
            } else if (x == 4) {
            }
        }
        if(null!=newMap){
            jsonString = newMap.get("1").toString();
        }
        String tagAumont = path + File.separator + id + ".json";

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
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(null!=newMap && null!=newMap.get("0")){
            return newMap.get("0").toString();
        }else{
            return "";
        }
    }
    
    /**
     * @Title: createJsonFileByBean
     * @Description: 可用PlotDataBean 取数据生成json文件的接口
     * @param path
     * @param id
     * @param fileName
     * @param plotDataBean void
     * @return String
     * @author 刘斌
     */
    public static String createJsonFileByBean(String path, String id, String fileName,
            PlotDataBean plotDataBean){
        Map<String, Object> newMap = null;
        String jsonString = "";
        if(null!=plotDataBean.getType()){
            if("line".equals(plotDataBean.getType())){
                newMap = CreateJsonModleLineUtil.getJsonModelByBean(fileName, plotDataBean);
            }else if("bar".equals(plotDataBean.getType())){
                newMap = CreateJsonModleColumnUtil.getJsonModel(fileName, plotDataBean, "bar");
            }else if("pie".equals(plotDataBean.getType())){
                newMap = CreateJsonModlePieUtil.getJsonModelByBean(fileName, plotDataBean);
            }else if("column".equals(plotDataBean.getType())){
                newMap = CreateJsonModleColumnUtil.getJsonModel(fileName, plotDataBean, "column");
            }
        }
        
        if(null!=newMap){
            jsonString = newMap.get("1").toString();
        }
        
        String tagAumont = path + File.separator + id + ".json";

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
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null!=newMap.get("0")){
            return newMap.get("0").toString();
        }else{
            return "";
        }
    }
    
    /**
     * @Title: createJsonFileByBeanOther
     * @Description: 生成横柱图的json文件接口
     * @param path
     * @param id
     * @param fileName
     * @param plotDataBean
     * @param newsPath void
     * @return String
     * @author 刘斌
     */
    public static String createJsonFileByBeanOther(String path, String id, String fileName,
            PlotDataBean plotDataBean , String newsPath){
        Map<String, Object> newMap = null;
        String jsonString = "";
        newMap = CreateJsonModleColumnUtil.getJsonModelX(id, fileName, plotDataBean, "bar", newsPath);
        String tagAumont = path + File.separator + "x!"+ id + ".json";
        if(null!=newMap){
            jsonString = newMap.get("1").toString();
        }
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
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(null!=newMap.get("0")){
            return newMap.get("0").toString();
        }else{
            return "";
        }
    }
    
}
