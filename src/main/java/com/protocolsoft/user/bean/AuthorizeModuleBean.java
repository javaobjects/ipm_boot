/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AuthorizeModuleBean
 *创建人:wjm    创建时间:2017年9月8日
 */
package com.protocolsoft.user.bean;

/**
 * 
 * @ClassName: AuthorizeModuleBean
 * @Description: ipm_authorize_module表 实体层
 * @author wangjianmin
 *
 */
public class AuthorizeModuleBean {
    /**
     * id
     */
    private int id;
    
    /**
     * 业务英文名称
     */
    private String nameen;
    
    /**
     * 业务中文名称
     */
    private String namezh;
    
    /**
     * 是否开启
     */
    private  int isopen; 
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the nameen
     */
    public String getNameen() {
        return nameen;
    }
    /**
     * @param nameen the nameen to set
     */
    public void setNameen(String nameen) {
        this.nameen = nameen;
    }
    /**
     * @return the namezh
     */
    public String getNamezh() {
        return namezh;
    }
    /**
     * @param namezh the namezh to set
     */
    public void setNamezh(String namezh) {
        this.namezh = namezh;
    }
    /**
     * <br />获取 <font color="red"><b>isopen<b/></font>
     * @return isopen isopen
     */
    public int getIsopen() {
        return isopen;
    }
    /**  
     * <br />设置 <font color='#333399'><b>isopen</b></font>
     * @param isopen isopen  
     */
    public void setIsopen(int isopen) {
        this.isopen = isopen;
    }

}
