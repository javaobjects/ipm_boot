/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: JsonFileUtil.java
 *创建人: www    创建时间: 2017年9月15日
 */
package com.protocolsoft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * @ClassName: JsonFileUtil
 * @Description: JSON文件工具类
 * @author www
 *
 */
public class JsonFileUtil {
  
    /**
     * @Fields util :  实例
     */
    private static JsonFileUtil util;
    
    /**
     * @Fields path : JSON根目录
     */
    private static String path;
    
    /**
     * 
     * <p>Title: JsonFileUtil</p>
     * <p>Description: 构造方法</p>
     */
    private JsonFileUtil() {
        path = getClass().getResource("/").getPath() + "viewConf/";
    }
    
    /**
     * 
     * @Title: getInstance
     * @Description: 获取实例
     * @return JsonFileUtil
     * @author www
     */
    public synchronized static JsonFileUtil getInstance() {
        if (util == null) {
            util = new JsonFileUtil();
        }

        return util;
    }
    
    /**
     * 
     * @Title: getJsonContent
     * @Description: 获取配置内容
     * @param type 模块类型
     * @param busiId 业务ID
     * @return String 内容
     * @throws IOException String
     * @author www
     */
    public String getJsonContent(ModuleType type, int busiId) throws IOException {
        String content = null;
        String dirName = type.name;
        String name = path + dirName + "/" + dirName + "_" + busiId + ".json";
        File file = new File(name);
        if (file.exists()) {
            InputStream is = new FileInputStream(file);
            byte[] tmp = new byte[is.available()];
            is.read(tmp);
            content = new String(tmp, "UTF-8");
            is.close();
        }
        
        return content;
    }
    
    /**
     * 
     * @Title: addJsonFile
     * @Description: 添加业务配置
     * @param type 模块类型
     * @param busiId 业务ID
     * @return boolean 是否成功
     * @throws IOException boolean
     * @author www
     */
    public boolean addJsonFile(ModuleType type, int busiId) throws IOException {
        boolean bool = false;
        String dirName = type.name;
        String sourceFilePath = dirName + "/default/conf.json";
        String copyFilePath = dirName + "/" + dirName + "_" + busiId + ".json";
        if (type == ModuleType.VIEW) { // 驾驶舱无默认，直接复制文件
            bool = copyJsonFile(sourceFilePath, copyFilePath);
        } else {
            if (type.moduleId < 10) {
                dirName = "busi";
            }
            String addKey = dirName + "Id";
            File source = new File(path + sourceFilePath);
            InputStream is = new FileInputStream(source);
            byte[] tmp = new byte[(int) source.length()];
            is.read(tmp);
            is.close();
            String content = new String(tmp, "UTF-8");
            JSONObject obj = JSONObject.parseObject(content);
            JSONArray arr = JSONArray.parseArray(obj.get("graph").toString());
            for (int i = 0, len = arr.size(); i < len; i ++) {
                arr.getJSONObject(i).put(addKey, busiId);
            }
            obj.put("graph", arr);
            File file = new File(path + copyFilePath);
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            os.write(obj.toJSONString().getBytes());
            os.flush();
            os.close();
            bool = true;
        }
        
        
        return bool;
    }
    
    /**
     * 
     * @Title: updJsonFile
     * @Description: 更新业务配置
     * @param type 模块类型
     * @param busiId 业务ID
     * @param json 更新内容
     * @return boolean 是否成功
     * @throws IOException boolean
     * @author www
     */
    public boolean updJsonFile(ModuleType type, int busiId, String json) throws IOException {
        boolean bool = false;
        if (json != null) {
            String dirName = type.name;
            String name = path + dirName + "/" + dirName + "_" + busiId + ".json";
            File file = new File(name);
            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            os.append(json);
            os.flush();
            os.close();
            bool = true;
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: dleJsonFile
     * @Description: 删除业务配置
     * @param type 模块类型
     * @param busiId 业务ID
     * @return boolean 是否成功
     * @author www
     */
    public boolean dleJsonFile(ModuleType type, int busiId) {
        boolean bool = false;
        String dirName = type.name;
        String name = path + dirName + "/" + dirName + "_" + busiId + ".json";
        bool = new File(name).delete();
        
        return bool;
    }
    
    /**
     * 
     * @Title: copyJsonFile
     * @Description: 配置复制
     * @param sourceFilePath 源文件路径
     * @param copyFilePath 复制文件路径
     * @return boolean 是否成功
     * @throws IOException boolean
     * @author www
     */
    private boolean copyJsonFile(String sourceFilePath, String copyFilePath) throws IOException {
        boolean bool = false;
        File source = new File(path + sourceFilePath);
        InputStream is = new FileInputStream(source);
        File file = new File(path + copyFilePath);
        file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        byte[] tmp = new byte[(int) source.length()];
        is.read(tmp);
        os.write(tmp);
        os.flush();
        is.close();
        os.close();
        bool = true;
        
        return bool;
    }
    
    /**
     * 
     * @ClassName: ModuleType
     * @Description: 模块类型
     * @author www
     *
     */
    public enum ModuleType {
        
        /**
         * 驾驶舱
         */
        VIEW(0, "view"),
        
        /**
         * 应用可用性
         */
        NET(1, "usability"),
        
        /**
         * 系统资源消耗
         */
        SYSTEM(2, "system"),
        
        /**
         * 用户管理
         */
        CENTER(3, "center"),
        
        /**
         * HTTP应用
         */
        HTTP(4, "http"),
        
        /**
         * ORACLE应用
         */
        ORACLE(5, "oracle"),
        
        /**
         * MYSQL应用
         */
        MYSQL(6, "mysql"),
        
        /**
         * SQLSERVER应用
         */
        SQLSERVER(7, "sqlserver"),
        
        /**
         * URL交易
         */
        URL(8, "url"),
        
        /**
         * 报文交易
         */
        MESSAGE(9, "message"),
        
        /**
         * 观察点
         */
        WATCHPOINT(10, "watchpoint"),
        
        /**
         * 客户端
         */
        CLIENT(11, "client"),
        
        /**
         * 服务端
         */
        SERVER(12, "server"),
        
        /**
         * 网络
         */
        NETWORK(13, "network"),
        
        /**
         * 多观察点
         */
        MANYWATCHPOINT(14, "manyWatchpoint"),
        
        /**
         * 流量储存
         */
        FLOWSTORAGE(15, "flowStorage"),
        
        /**
         * 地图
         */
        MAP(16, "map"),
        
        /**
         * 拓扑图
         */
        TOPO(17, "topo"),
        
        /**
         * 通信队
         */
        TRAFFICPAIR(18, "trafficPair"),
        
        /**
         * iDigger
         */
        DIGGER(19, "digger");
        
        /**
         * 模版编号
         */
        private int moduleId;
        
        /**
         * 目录名称
         */
        private String name;
        
        /**
         * 
         * <p>Title: </p>
         * <p>Description: </p>
         * @param moduleId 模块ID 
         * @param name 路径
         */
        private ModuleType(int moduleId, String name) {
            this.moduleId = moduleId;
            this.name = name;
        }

        /**
         * <br />获取 <font color="red"><b>模版编号<b/></font>
         * @return moduleId 模版编号
         */
        public int getModuleId() {
            return moduleId;
        }
        
        /**
         * <br />获取 <font color="red"><b>目录名称<b/></font>
         * @return name 目录名称
         */
        public String getName() {
            return name;
        }

        /**
         * 
         * @Title: getModuleType
         * @Description: 获取模块类型
         * @param moduleId 模块编号
         * @return FileMoudleType 
         * @author www
         */
        public static ModuleType getModuleType(int moduleId) {
            ModuleType type = null;
            for (ModuleType t : ModuleType.values()) {
                if (t.getModuleId() == moduleId) {
                    type = t;
                    break;
                }
            }
            
            return type;
        }
        
        /**
         * 
         * @Title: getModuleType
         * @Description: 获取模块类型
         * @param moduleName 模块名称
         * @return FileMoudleType 
         * @author www
         */
        public static ModuleType getModuleType(String moduleName) {
            ModuleType type = null;
            for (ModuleType t : ModuleType.values()) {
                if (t.getName().toUpperCase().equals(moduleName.toUpperCase())) {
                    type = t;
                    break;
                }
            }
            
            return type;
        }
    }
}
