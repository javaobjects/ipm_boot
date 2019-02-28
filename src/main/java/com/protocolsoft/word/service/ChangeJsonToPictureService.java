/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.word.service;


/**
 * @ClassName: ChangeJsonToPictureService
 * @Description: json文件转换成图片接口
 * @author 刘斌
 *
 */
public interface ChangeJsonToPictureService {
    
    /**
     * @Title: getThePictureForWord
     * @Description: 将json文件转换成图表放入指定文件夹
     * @param resourceJson
     * @param tagPath
     * @return boolean
     * @author 刘斌
     */
    boolean getThePictureForWord(String resourceJson, String tagPath);
}
