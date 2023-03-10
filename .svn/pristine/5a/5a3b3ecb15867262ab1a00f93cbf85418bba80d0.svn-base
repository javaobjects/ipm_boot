/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.email.service.impl;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.email.bean.QywxBean;
import com.protocolsoft.email.service.QywxService;
import com.protocolsoft.utils.PropertiesUtil;


/**
 * 
 * @ClassName: QywxServiceImpl
 * @author 刘斌
 *
 */
@Service
public class QywxServiceImpl implements QywxService{
    
    /**
     * @return int
     */
    @Override
    public int updateQywx(QywxBean bean) {
      String retstr = "";
      PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties"); 
      retstr += propertiesUtil.writePropertyReturn("qywxId", bean.getQywxId());
      retstr += propertiesUtil.writePropertyReturn("qywxAppAgentId", bean.getQywxAppAgentId());
      retstr += propertiesUtil.writePropertyReturn("qywxAppSecret", bean.getQywxAppSecret());
      retstr += propertiesUtil.writePropertyReturn("qywxDepId", bean.getQywxDepId());
      retstr += propertiesUtil.writePropertyReturn("qywxUsers", bean.getQywxUsers());

      if ("11111".equals(retstr)) {
        return 1;
      } 
      return 0;
    }

    /**
     * @return QywxBean
     */
    @Override
    public QywxBean getQywx() {
      QywxBean qywxBean = new QywxBean();
      PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
      try {
        qywxBean.setQywxId(propertiesUtil.readProperty("qywxId"));
        qywxBean.setQywxAppAgentId(propertiesUtil.readProperty("qywxAppAgentId"));
        qywxBean.setQywxAppSecret(propertiesUtil.readProperty("qywxAppSecret"));
        qywxBean.setQywxDepId(propertiesUtil.readProperty("qywxDepId"));
        qywxBean.setQywxUsers(propertiesUtil.readProperty("qywxUsers"));
        return qywxBean;
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
}
