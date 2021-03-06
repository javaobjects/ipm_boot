/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.daoprovider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.word.bean.ReportEmailBean;
/**
 * 
 * @ClassName: ReportModalTableAndWraningProvider
 * @Description: 
 * @author 刘斌
 *
 */
public class ReportModalTableAndWraningProvider {

    /**
     * 
     * @Title: insertAll
     * @Description: 
     * @param map
     * @return String
     * @author 刘斌
     */
    public String insertAll(Map<String, Object> map) {
        @SuppressWarnings("unchecked")
        List<ReportEmailBean> users = (List<ReportEmailBean>) map.get("list");
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO ipm_modal_tableandalarm "); 
        sb.append("(modal_id,module_id,table_having,warning_having) ");
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("( #'{'list[{0}].modalId},"
                + "#'{'list[{0}].modultId},#'{'list[{0}].tableHaving},#'{'list[{0}].warningHaving})");
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {  
                sb.append(",");
            }  
        }  
        return sb.toString();
    } 
}
