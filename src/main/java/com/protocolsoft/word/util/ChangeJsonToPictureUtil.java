/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ChangeJsonToPictureUtil
 * @Description: json转换图片器
 * @author 刘斌
 *
 */
public class ChangeJsonToPictureUtil {
    
    
    /**
     * @Title: translateJsonToPicture
     * @Description: 将json文件生成highcharts图表
     * @param resourceJson
     * @param tagPath
     * @return boolean
     * @author 刘斌
     */
    public static boolean translateJsonToPicture(String resourceJson, String tagPath){
        List<String> listStr = new ArrayList<>();
        try {
            File file = new File(resourceJson);
            if(!file.exists()){
                return false;
            }
            File[] files = file.listFiles();
            if(files.length==0){
                return false;
            }
            String resultString = "";
            int sre = 0;
            int et = 0;
            for(File file2 : files){
                String fileName = file2.getPath();
                String fileTagName = file2.getName();
                String prefix=fileName.substring(fileName.lastIndexOf("."));
                int num=prefix.length();
                String fileOtherName=fileTagName.substring(0, fileTagName.length()-num);
                String changString = fileName+"="+tagPath+File.separator+fileOtherName+".png";
                resultString += (changString+";");
                et ++;
                sre ++;
                if(60==et || sre == files.length){
                    String srt = resultString.substring(0, resultString.length() - 1);
                    resultString = "";
                    String der = srt;
                    listStr.add(der);
                    srt = "";
                    et = 0;
                }
            }
            for(String str : listStr){
                Runtime rt = Runtime.getRuntime();
                String[] dd = new String []{"/bin/sh", "-c", "highcharts-export-server -batch "+"\""+str+"\""};
                Process pt = rt.exec(dd);
                System.gc();
                pt.waitFor();
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}