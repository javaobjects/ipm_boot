/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.io.File;


/**
 * @ClassName: CleanAllTextUtil
 * @Description: 清楚文件夹工具
 * @author 刘斌
 *
 */
public class CleanAllTextUtil {
    
    /**
     * @Title: cleanFile
     * @Description: 递归的删除文件夹及内部文件
     * @param delpath void
     * @author 刘斌
     */
    public static void cleanFile(String delpath) {

        File file = new File(delpath);
        if(!file.exists()){
            return;
        }
        if (!file.isDirectory()) {
            System.gc();
            file.delete();
        } else if (file.isDirectory()) {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                if (!filelist[i].isDirectory()) {
                    System.gc(); 
                    filelist[i].delete();
                } else if (filelist[i].isDirectory()) {
                    cleanFile(delpath + File.separator + filelist[i]);
                }
            }
            file.delete();
            if (!file.toString().equals(delpath)) {
                System.gc(); 
                file.delete();
            }
        }
        if(file.exists()){
            File[] filelist = file.listFiles();
            if(filelist.length>0){
                cleanFile(delpath);
            }
        }
    }

}
