/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.word.service.impl;

import org.springframework.stereotype.Service;

import com.protocolsoft.word.service.ChangeJsonToPictureService;
import com.protocolsoft.word.util.ChangeJsonToPictureUtil;

/**
 * @ClassName: ChangeJsonToPictureServiceImpl
 * @Description: 将json信息通过插件生成图表
 * @author 刘斌
 *
 */
@Service
public class ChangeJsonToPictureServiceImpl implements
    ChangeJsonToPictureService {

    @Override
    public boolean getThePictureForWord(String resourceJson, String tagPath) {
        try {
            return ChangeJsonToPictureUtil.translateJsonToPicture(resourceJson,
                 tagPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
