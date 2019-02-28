/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;


/**
 * @ClassName: RemoveLastUtil
 * @Description: 格式化文件名
 * @author 刘斌
 *
 */
public class RemoveLastUtil {
    /**
     * @Title: getFileNameNoEx
     * @Description: 去除文件后缀名
     * @param filename
     * @return String
     * @author 刘斌
     */
    public static String getFileNameNoEx(String filename) {
        try {
            if ((filename != null) && (filename.length() > 0)) {
                int dot = filename.lastIndexOf('.');
                if ((dot > -1) && (dot < (filename.length()))) {
                    return filename.substring(0, dot);
                }
            }
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
