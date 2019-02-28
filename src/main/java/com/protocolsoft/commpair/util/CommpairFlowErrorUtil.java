/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFlowErrorUtil
 *创建人:chensq    创建时间:2018年4月27日
 */
package com.protocolsoft.commpair.util;


/**
 * @ClassName: CommpairFlowErrorUtil
 * @Description: 流量下载错误情况
 * @author chensq
 *
 */
public class CommpairFlowErrorUtil {
    
    /**
     * @Fields errorinfoArray :  定义错误类型,历史数据提取的
     */
    String [] errorinfoArray =new String[]{
        "time error",
        "address error",
        "query time error", 
        "time must less than now", 
        "start time must less than end time",
        "did error",
        "server address error",
        "client address error",
        "entity is null"};
    
    /**
     * @Title: errorTypeId
     * @Description: 返回错误类型
     * @param errorInfo
     * @return String
     * @author chensq
     */
    public String errorTypeId(String errorInfo){
        String typeId="0";
        for(int x=0; x<errorinfoArray.length; x++){
            String tempStr= errorinfoArray[x];
            if (errorInfo.toUpperCase().indexOf(tempStr.toUpperCase()) > 0){
                typeId ="1";
            }
        }
        return typeId;
    }
    
}
