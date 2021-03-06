/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:InitServlet
 *创建人:long    创建时间:2017年7月20日
 */
package com.protocolsoft.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: InitServlet
 * @Description: 初始化sigar的服务文件
 * @author longyy
 */
@Component
public class InitServlet implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String path = InitServlet.class.getResource("/").getPath().toString();
            String sigar = null;
            if (null != osName && -1 < osName.indexOf("linux")) {
                if (System.getProperty("os.arch").indexOf("64") != -1) {
                    sigar = "libsigar-amd64-linux.so";
                } else {
                    sigar = "libsigar-x86-linux.so";
                }
            } else {
                if (System.getenv("ProgramFiles(x86)") != null) {
                    sigar = "sigar-amd64-winnt.dll";
                } else {
                    sigar = "sigar-x86-winnt.dll";
                }
            }
            System.load(path + sigar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
