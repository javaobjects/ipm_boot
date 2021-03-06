/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFolderNameUtil
 *创建人:chensq    创建时间:2018年4月26日
 */
package com.protocolsoft.commpair.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * @ClassName: CommpairFolderNameUtil
 * @Description: 生成文件夹名称(通过时间戳),以及获取指定的文件下的文件列表
 * @author chensq
 *
 */
public class CommpairFolderNameUtil {
    
    /**
     * @Title: getFolderName
     * @Description: 生成文件夹名称,如果不存在的话创建文件夹
     * @param fileDictionary
     * @return Map<String,String>
     * @throws IOException 
     * @author chensq
     */
    public static Map<String, String> getFolderName(String fileDictionary) throws IOException{
        //设置文件夹名称
        long folderName = System.currentTimeMillis() / 1000;
        //拼接路径        
        File dirFile = new File(fileDictionary+System.getProperty("file.separator")+folderName);
        //判断存在与否
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        Map<String, String> map=new HashMap<String, String>();
        map.put("fileDictionary", fileDictionary);
        map.put("folderName", String.valueOf(folderName));
        
        return map;
    }
    
    /**
     * @Title: getFileNameList
     * @Description: 根据路径与文件夹名称获取其下的文件名称集合
     * @param fileDictionary
     * @param folderName
     * @return List<String>
     * @throws IOException List<String>
     * @author chensq
     */
    public static List<String> getFileNameList(String fileDictionary, String folderName) throws IOException{
        List<String> list=null;
        File file = new File(fileDictionary+System.getProperty("file.separator")+folderName);   
        // 获得该文件夹内的所有文件   
        File[] array = file.listFiles();   

        if(array!=null && array.length>0){
            for(int i=0; i<array.length; i++){
                if(list ==null){
                    list=new ArrayList<String>();
                }
                list.add(array[i].getName());
            }  
        }
        
        return list;
    }
     
    /**
     * @Title: delFolder
     * @Description: 删除指定路径下的指定文件夹
     * @param fileDictionary
     * @param folderName void
     * @author chensq
     */
    public static void delFolder(String fileDictionary, String folderName){
        File file = new File(fileDictionary);   
        // 获得该文件夹内的所有文件   
        File[] array = file.listFiles();   

        if(array!=null && array.length>0){
            for(int i=0; i<array.length; i++){
                String tempFileName=array[i].getName();
                if(tempFileName.equalsIgnoreCase(folderName)){
                    array[i].delete();
                }
            }
        }
            
    }
    
}
