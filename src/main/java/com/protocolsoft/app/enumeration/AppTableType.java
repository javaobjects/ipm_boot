/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTableEnum.java
 *创建人: www    创建时间: 2018年1月12日
 */
package com.protocolsoft.app.enumeration;

/**
 * @ClassName: AppTableEnum
 * @Description: 应用对应表名
 * @author www
 *
 */
public enum AppTableType {
    
    /**
     * HTTP服务
     */
    HTTP(4, "http_log_tables", "http_log_"),
    
    /**
     * ORACLE服务
     */
    ORACLE(5, "oracle_log_tables", "oracle_log_"),
    
    /**
     * MYSQL服务
     */
    MYSQL(6, "mysql_log_tables", "mysql_log_"),
    
    /**
     * SQLSERVER服务
     */
    SQLSERVER(7, "sqlserver_log_tables", "sqlserver_log_"),
    
    /**
     * 报文服务
     */
    MSG(9, "msg_log_tables", "msg_log_");
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 前缀
     */
    private String tablePrefix;


    /**
     * <br />获取 <font color="red"><b>表名<b/></font>
     * @return tableName 表名
     */
    public String getTableName() {
        return tableName;
    }


    /**
     * <br />获取 <font color="red"><b>前缀<b/></font>
     * @return tablePrefix 前缀
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**  
     * <br />设置 <font color='#333399'><b>表名</b></font>
     * @param tableName 表名  
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    /**  
     * <br />设置 <font color='#333399'><b>前缀</b></font>
     * @param tablePrefix 前缀  
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param moduleId 模块
     * @param tableName 表名
     * @param tablePrefix 前缀
     */
    private AppTableType(int moduleId, String tableName, String tablePrefix) {
        this.moduleId = moduleId;
        this.tableName = tableName;
        this.tablePrefix = tablePrefix;
    }
    
    /**
     * 
     * @Title: getTypeByModuleId
     * @Description: 根据模块编号获取标信息
     * @param moduleId 模块编号
     * @return AppTableType 表信息
     * @author www
     */
    public static AppTableType getTypeByModuleId(int moduleId) {
        AppTableType type = null;
        for (AppTableType t : AppTableType.values()) {
            if (t.moduleId == moduleId) {
                type = t;
                break;
            }
        }
        
        return type;
    }

}
