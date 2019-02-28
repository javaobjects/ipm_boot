/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:IpUtils
 *创建人:chensq    创建时间:2017年5月23日
 */
package com.protocolsoft.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;

/**
 * @ClassName: IpUtils
 * @Description: Ip转换公共工具类
 * @author chensq
 *
 */
public class IpUtils {
   
    /**
     * @Title: ipFromLongToString
     * @Description: ip long to String
     * @param longIP
     * @return String
     * @author chensq
     */
    public static String ipFromLongToString(Long longIP){
        StringBuffer sb = new StringBuffer();
        long v = longIP.longValue();
        int v1 = 0;
        int v2 = 0;
        int v3 = 0;
        int v4 = 0;
        int t = 255;
        v1 = (int)(v >> 24);
        v2 = (int)((v >> 16) - (v1 * 256));
        v3 = (int)((v >> 8) - (v2 * 256) - (v1 * 256 * 256));
        v4 = (int)(v & t);
        sb.append(v1);
        sb.append('.');
        sb.append(v2);
        sb.append('.');
        sb.append(v3);
        sb.append('.');
        sb.append(v4);
        return sb.toString();
    }    
    
    /**
     * @Title: ipFromStringToLong
     * @Description: ip String to long
     * @param strIP
     * @return long 类型的ip
     * @author chensq
     */
    public static long ipFromStringToLong(String strIP){
        String [] strs = strIP.split("\\.");
        int v1 = Integer.parseInt(strs[0]);
        int v2 = Integer.parseInt(strs[1]);
        int v3 = Integer.parseInt(strs[2]);
        int v4 = Integer.parseInt(strs[3]);
        long v = 0;
        v = v + v4;
        v = v + v3 * 256;
        v = v + v2 * 256 * 256;
        v = v + (long)v1 * 256 * 256 * 256;
        return v;
    }
    
    /**
     * @Title: isBelongIpnet
     * @Description: 验证某一ip是否在某一网段内
     * @param ipnet 子网
     * @param ip 需要验证的ip
     * @return boolean true:在其中 false:不在其中
     * @author chensq
     */
    public boolean isBelongIpnet(String ipnet, long ip) {
        boolean flag = false;
        List<Long> ipsList = getMaxMinIpByIpnet(ipnet);
        if (ip >= ipsList.get(0) && ip <= ipsList.get(1)) {
            flag = true;
        }
        return flag;
    }
 
    /**
     * @Title: fromSubNetStringGetIpArray
     * @Description: 通过子网获取ip集合数组 (需要工具jar支持)
     * @param strSubNet 子网,例如：192.168.1.0/24
     * @param isIncludeStartStop 是否包含起始ip true:包含,false:不包含
     * @return String[] 子网内的ip数组
     * @author chensq
     */
    public static String[] fromSubNetStringGetIpArray(String strSubNet, boolean isIncludeStartStop){
        SubnetUtils utils = new SubnetUtils(strSubNet);
        utils.setInclusiveHostCount(isIncludeStartStop);
        String[] allIps = utils.getInfo().getAllAddresses();
        return allIps;
    }

    /**
     * @Title: getMaxMinIpByIpnet
     * @Description: get maxIP minIP from subnet (此方法一般不直接使用)  举例192.168.1.0/24 获取网段最大与最小ip，用于判断一个ip是否在某网段内
     * @param ipnet 子网
     * @return List<Long> 返回类型
     * @author chensq
     */
    public static List<Long> getMaxMinIpByIpnet(String ipnet) {
        List<Long> list = new ArrayList<Long>();
        int ind = ipnet.indexOf('/');
        String ip = ipnet.substring(0, ind);
        long b = ipFromStringToLong(ip);
        int sub = 32 - Integer.parseInt(ipnet.substring(ind + 1));
        int v = 1;
        for (int i = 0; i < sub; i++) {
            v = v * 2;
        }
        long subV = b % v;
        long minIP = b - subV;
        long maxIP = minIP + v - 1;
        list.add(minIP);
        list.add(maxIP);
        return list;
    }
    
}
