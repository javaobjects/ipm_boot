/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairController
 *创建人:chensq    创建时间:2017年10月20日
 */
package com.protocolsoft.commpair.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.commpair.bean.CommpairBean;
import com.protocolsoft.commpair.service.CommpairService;
import com.protocolsoft.protoplan.service.PublicProtoPlanService;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.UserConfigureDao;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;

/**
 * @ClassName: CommpairController
 * @Description: 通信对Controller
 * @author chensq
 *
 */
@Controller
@RequestMapping(value = "/commpair")
public class CommpairController {
         
    /**
     * @Fields commpairService : 服务端管理业务对象
     */
    @Autowired
    private CommpairService commpairService;
    
    /**
     * @Fields publicprotoPlanService : 协议业务对象
     */
    @Autowired
    private PublicProtoPlanService publicProtoPlanService;
        
    /**
     * @Fields systemUserService : userService注入
     */
    @Autowired(required = false)
    private SystemUserService systemUserService;
        
    /**
     * @Fields userListColumnService : 用户配置列表字段
     */
    @Autowired
    private UserListColumnService userListColumnService;
   
    /**
     * @Fields serverManagementService : 服务端管理业务对象
     */
    @Autowired
    private ServerManagementService serverManagementService;
    
    /**
     * @Fields userService : userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * @Fields userConfigureDao :  用户配置信息获取
     */
    @Autowired
    private UserConfigureDao userConfigureDao;
        
    /**
     * @Title: getCommpairMergeData
     * @Description: 获取按照条件聚合后的通信对(分发)
     * @param commpairBean
     * @return List<CommpairBean>
     * @author chensq
     */
    @RequestMapping(value="getCommpairMergeData.do")
    @ResponseBody 
    public List<CommpairBean> getCommpairMergeData(CommpairBean commpairBean){
        List<CommpairBean> commpairList =null;
        //将controller中的业务处理过程放入serive
        commpairList = commpairService.toCommpairMergeData(commpairBean, publicProtoPlanService, commpairService, serverManagementService);
        return  commpairList;
    }
    
    /**
     * @Title: getCommpairDetailData
     * @Description: 通信对列表详情
     * @param commpairBean
     * @return List<CommpairBean>
     * @author chensq
     */
    @RequestMapping(value="getCommpairDetailData.do")
    @ResponseBody 
    public List<CommpairBean> getCommpairDetailData(CommpairBean commpairBean){
        List<CommpairBean> commpairList =null;
        //将controller中的业务处理过程放入serive
        commpairList = commpairService.toCommpairDetailData(commpairBean, publicProtoPlanService, commpairService, serverManagementService);
        return  commpairList;
    }
        
    
    /**
     * @Title: getCommpairMergeCount
     * @Description: 通信对聚合总数 
     * @param commpairBean
     * @return long
     * @author chensq
     */
    @RequestMapping(value="getCommpairMergeCount.do")
    @ResponseBody 
    public long getCommpairMergeCount(CommpairBean commpairBean){
        long count =0;
        //将controller中的业务处理过程放入serive
        count = commpairService.toCommpairMergeCount(commpairBean, publicProtoPlanService, commpairService, serverManagementService);
        return  count;
    }
    
    /**
     * @Title: getCommpairListColumn
     * @Description: 获取通信对对应列表字段
     * @param request
     * @return List<ListColumnBean>
     * @author chensq
     */
    @RequestMapping(value="getCommpairListColumn.do")
    @ResponseBody 
    public List<ListColumnBean> getCommpairListColumn(HttpServletRequest request){
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        List<ListColumnBean> beans = userListColumnService.getUserListColumn(user.getId(), 13);
        return beans;
    }
        
    /**
     * @Title: getRemoteCommpairMergeData
     * @Description: 获取聚合后通信队信息 (center)
     * @param commpairBean
     * @return List<CommpairBean>
     * @author chensq
     */
    @RequestMapping(value = "/getRemoteCommpairMergeData.do")
    @ResponseBody 
    public List<CommpairBean> getRemoteCommpairMergeData(CommpairBean commpairBean) {
        String url = "/commpair/getCommpairMergeData.do";
        
        return commpairService.getRemoteCommpairData(commpairBean, url);
    }
    
    /**
     * @Title: getRemoteCommpairDetailData
     * @Description: 获取详细后通信队信息 (center)
     * @param commpairBean
     * @return List<CommpairBean>
     * @author www
     */
    @RequestMapping(value = "/getRemoteCommpairDetailData.do")
    @ResponseBody 
    public List<CommpairBean> getRemoteCommpairDetailData(CommpairBean commpairBean) {
        String url = "/commpair/getCommpairDetailData.do";
        
        return commpairService.getRemoteCommpairData(commpairBean, url);
    }
    
    /**
     * @Title: getRemoteCommpairMergeCountData
     * @Description: 获取聚合总数
     * @param commpairBean 参数
     * @return long
     * @author www
     */
    @RequestMapping(value = "/getRemoteCommpairMergeCountData.do")
    @ResponseBody 
    public long getRemoteCommpairMergeCountData(CommpairBean commpairBean) {
        String url = "/commpair/getCommpairMergeCount.do";
        
        return commpairService.getRemoteCommpairMergeCountData(commpairBean, url);
    }
    
}
