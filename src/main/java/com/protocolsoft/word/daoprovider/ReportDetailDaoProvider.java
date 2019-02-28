/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.daoprovider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.protocolsoft.word.bean.ReportTimerDetailBean;
/**
 * 
 * @ClassName: ReportDetailDaoProvider
 * @Description: 
 * @author 刘斌
 *
 */
public class ReportDetailDaoProvider {

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
        List<ReportTimerDetailBean> users = (List<ReportTimerDetailBean>) map.get("list");
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO ipm_timer_report_detail ");  
        sb.append("(report_id,module_id,watchpoint_id,app_id,plot_id,plot_type_id) ");  
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("( #'{'list[{0}].reportId}, #'{'list[{0}].moduleId},#'{'list[{0}].watchpointId}"
                + ",#'{'list[{0}].appId},#'{'list[{0}].plotId},#'{'list[{0}].plotTypeId})");  
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {
                sb.append(",");  
            }  
        }  
        return sb.toString();
    }  
}
