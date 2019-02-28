/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.word.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: WordCopyUtil
 * @Description: 文件复制工具类
 * @author 刘斌
 *
 */
public class WordCopyUtil {

    /**
     * @Title: copyFile
     * @Description: 从word 模板复制文件
     * @param fromFile
     * @param toFile
     * @return boolean
     * @author 刘斌
     */
    public static boolean copyFile(File fromFile, File toFile){

        FileInputStream ins = null;
        FileOutputStream out = null;
        try {
            ins = new FileInputStream(fromFile);
            out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
