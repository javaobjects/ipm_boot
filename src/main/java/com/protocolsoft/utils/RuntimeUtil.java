/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:RuntimeUtil
 *创建人:yan    创建时间:2017年7月21日
 */
package com.protocolsoft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @ClassName: RuntimeUtil
 * @Description:  Runtime工具类
 * @author yan
 *
 */
public class RuntimeUtil {
    
    /**
     * @Fields BR_INPUT : 标准输出
     */
    public static final String BR_INPUT = "brInput";
        
    /**
     * @Fields BR_ERROR : 标准错误
     */
    public static final String BR_ERROR = "brError";
     
    /**
     * @Fields logger : 日志
     */
    static Logger logger = Logger.getLogger(PropertiesUtil.class);
    
    /**
     * @Title: exec
     * @Description 执行命令
     * @param String[]
     * @return Map<String,String>
     * @throws IOException 
     * @throws InterruptedException 
     * @exception 
     * @see
     */
    public static Map<String, String> exec(String[] cmd) throws IOException, InterruptedException{
        Map<String, String> map = new HashMap<String, String>();
        Process process = null;  
        process = Runtime.getRuntime().exec(cmd);  
        //标准输出
        BufferedReader brInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //标准错误
        BufferedReader brError = new BufferedReader(new InputStreamReader(process.getErrorStream()));  
        process.waitFor();
        //接收命令行返回信息
        String lineTemp = "";
        if ((lineTemp = brInput.readLine()) != null) {
            map.put(BR_INPUT, lineTemp);
            logger.debug("标准输出====="+lineTemp);
            lineTemp  = "";
        }
        if ((lineTemp = brError.readLine()) != null) {
            map.put(BR_ERROR, lineTemp);
            logger.debug("标准错误====="+lineTemp);
        }
        brInput.close();  
        brError.close();  
        process.destroy();  
        return map;
    }
}
