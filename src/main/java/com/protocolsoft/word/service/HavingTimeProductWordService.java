/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.word.service;


/**
 * @ClassName: HavingTimeProductWordService
 * @Description: 定时报表生成接口
 * @author 刘斌
 *
 */
public interface HavingTimeProductWordService {
    /**
     * @Title: productWordByDate
     * @Description: 生成日报表
     * @author 刘斌
     */
    void productWordByDate();
    
    /**
     * @Title: productWordByWeek
     * @Description: 生成周报表
     * @author 刘斌
     */
    void productWordByWeek();
    
    /**
     * @Title: productWordByMonth
     * @Description: 生成月报表
     * @author 刘斌
     */
    void productWordByMonth();
}
