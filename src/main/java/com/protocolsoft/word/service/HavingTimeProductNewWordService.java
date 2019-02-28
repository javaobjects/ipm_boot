package com.protocolsoft.word.service;

import org.springframework.stereotype.Service;


/**
 * @ClassName: HavingTimeProductNewWordService
 * @Description: 定时报表Service接口
 * @author 刘斌
 */
@Service
public interface HavingTimeProductNewWordService {
    
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
