/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.daoprovider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.word.bean.ReportBusinessBean;

/**
 * 
 * @ClassName: ReportBussinessDaoProvider
 * @Description: 
 * @author 刘斌
 *
 */
public class ReportBussinessDaoProvider {

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
        List<ReportBusinessBean> users = (List<ReportBusinessBean>) map.get("list");
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO ipm_report_business ");  
        sb.append("(plan_id,module_id,business_id) "); 
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("( #'{'list[{0}].planId}, #'{'list[{0}].modulId}"
                + ",#'{'list[{0}].bussinessId})");
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {  
                sb.append(",");  
            }  
        }  
        return sb.toString();
    }  
}
