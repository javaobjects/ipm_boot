/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * @author Administrator
 *2018年3月20日
 */
package com.protocolsoft.word.service;

import com.protocolsoft.word.bean.WordBean;



/**
 * @ClassName: ExportWordService
 * @Description: 导出WORD文档
 * @author 刘斌
 *
 */
public interface ExportWordService {

    /**
     * @Title: getTheWarningWord
     * @Description: 将图片和报表属性组合成word文档
     * @param picturePath
     * @param bean
     * @param modelPath
     * @param tagName void
     * @author 刘斌
     */
    void getTheWarningWord(String picturePath, WordBean bean, String modelPath, String tagName);
}
