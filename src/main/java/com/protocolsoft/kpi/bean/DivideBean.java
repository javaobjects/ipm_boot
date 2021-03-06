/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: DivideBean.java
 *创建人: WWW    创建时间: 2018年7月2日
 */
package com.protocolsoft.kpi.bean;

/**
 * @ClassName: DivideBean
 * @Description: 除法
 * @author WWW
 *
 */
public class DivideBean {

    /**
     * 被除数
     */
    private String dividend;
    
    /**
     * 除数
     */
    private String divisor;

    /**
     * <br />获取 <font color="red"><b>被除数<b/></font>
     * @return dividend 被除数
     */
    public String getDividend() {
        return dividend;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>被除数</b></font>
     * @param dividend 被除数  
     */
    public void setDividend(String dividend) {
        this.dividend = dividend;
    }
    

    /**
     * <br />获取 <font color="red"><b>除数<b/></font>
     * @return divisor 除数
     */
    public String getDivisor() {
        return divisor;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>除数</b></font>
     * @param divisor 除数  
     */
    public void setDivisor(String divisor) {
        this.divisor = divisor;
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param dividend
     * @param divisor
     */ 
    public DivideBean(String dividend, String divisor) {
        super();
        this.dividend = dividend;
        this.divisor = divisor;
    }
}
