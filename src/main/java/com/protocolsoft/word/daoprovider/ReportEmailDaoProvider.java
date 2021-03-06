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
 * @ClassName: ReportEmailDaoProvider
 * @Description: 
 * @author 刘斌
 *
 */
public class ReportEmailDaoProvider {

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
        sb.append("INSERT INTO ipm_report_email "); 
        sb.append("(plan_id,sender,reciver,email) ");  
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("( #'{'list[{0}].planId},"
                + "#'{'list[{0}].sender},#'{'list[{0}].recriver},#'{'list[{0}].email})");
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {  
                sb.append(",");
            }  
        }  
        return sb.toString();
    } 
}
