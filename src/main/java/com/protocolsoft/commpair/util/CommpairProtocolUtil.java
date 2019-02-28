/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairProtocolUtil
 *创建人:chensq    创建时间:2018年1月4日
 */
package com.protocolsoft.commpair.util;

import java.util.ArrayList;
import java.util.List;

import com.protocolsoft.protoplan.bean.ProtoPlanBean;
import com.protocolsoft.protoplan.dao.PublicProtoPlanDao;
import com.protocolsoft.utils.ArrayCutUtils;

/**
 * @ClassName: CommpairProtocolUtil
 * @Description: Commpair通信对列表工具类
 * @author chensq
 *
 */
public class CommpairProtocolUtil {
   
    /**
     * @Title: portNotInFullSql
     * @Description: 获取完整的serverport not in sql
     * @param protoPlanDao
     * @param protoPlanList
     * @return String
     * @author chensq
     */
    public String portNotInFullSql(PublicProtoPlanDao publicProtoPlanDao, List<ProtoPlanBean> protoPlanList){
        //拼接完整in sql
        StringBuffer fullBuf=new StringBuffer();
        List<String> notinStrList=this.getAllProtoPortArraySql(publicProtoPlanDao, protoPlanList);
        if (notinStrList.size()>0) {
            for (int x=0; x<notinStrList.size(); x++) {
                fullBuf.append(" l.serverport ");
                fullBuf.append(notinStrList.get(x));
                if (x!=notinStrList.size()-1) {
                    fullBuf.append(" and ");
                }
            }
        }
        return fullBuf.toString();
    }
     
    /**
     * @Title: getAllProtoPortArraySql
     * @Description: 拼接协议端口，not in
     * @param protoPlanDao
     * @param protoPlanList
     * @return List<String>
     * @author chensq
     */
    public List<String> getAllProtoPortArraySql(PublicProtoPlanDao publicProtoPlanDao, List<ProtoPlanBean> protoPlanList){
       //notin String list
        List<String> notinStrList=new ArrayList<String>();
        //对数组进行切分
        String []portsArray=this.getAllProtoPort(publicProtoPlanDao, protoPlanList).split(",");
        //获取切分后的数组
        Object[] subAry =ArrayCutUtils.splitAry(portsArray, 5);
        //array to list
        for (Object obj: subAry) {
            String[] aryItem = (String[]) obj;  
            StringBuffer buf=new StringBuffer();
            buf.append(" not in (");
            for (int i = 0; i < aryItem.length; i++) {
                buf.append(aryItem[i]);
                if (i!=aryItem.length-1) {
                    buf.append(",");
                }
            }  
            buf.append(") ");
            notinStrList.add(buf.toString());
        }
        return notinStrList;
    }
      
    /**
     * @Title: getAllProtoPort
     * @Description: 查询所有协议的端口，并以","连接
     * @param protoPlanDao
     * @param protoPlanList
     * @return String
     * @author chensq
     */
    public String getAllProtoPort(PublicProtoPlanDao publicProtoPlanDao, List<ProtoPlanBean> protoPlanList){
        StringBuffer buf=new StringBuffer();
        if (protoPlanList!=null && protoPlanList.size()>0) {
            for (int x=0; x<protoPlanList.size(); x++) {
                buf.append(protoPlanList.get(x).getPort());
                if (x!=protoPlanList.size()-1){
                    buf.append(",");
                }
            }
        }
        return buf.toString();
    }
    
}
