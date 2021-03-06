/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MathUtlis
 *创建人:chensq    创建时间:2017年5月23日
 */
package com.protocolsoft.utils;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * 数字、数学处理公共工具类
 * 特别注意：如有可提取的公共方法，请联系相关人员添加，不可自行添加
 * 2017年5月23日 下午5:37:05
 * @author chensq
 * @version
 * @see
 * 
 */

public class MathUtlis {
   
    /**
     * 
     * @Title: getDefaultformatinteger
     * @Description:  整数格式化 long类型的整数格式化为数字分格格式
     * @param value int/long
     * @return String
     * @author chensq
     */
    public static String getDefaultformatinteger(long value){
        DecimalFormat df = new DecimalFormat("#,###");
        String m = df.format(value);
        return m;
    }

    /**
     * @Title: getDefaultformatdecimal2places
     * @Description: 含小数的默认取舍格式化 double类型格式化为数字分割格式，并且进行取舍
     * @param value int/long/float/double
     * @return String
     * @author chensq
     */
    public static String getDefaultformatdecimal2places(double value){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String m = df.format(value);
        return m;
    }
     
    /**
     * @Title: getDefaultformatpercent2places
     * @Description: 百分比默认取舍格式 double百分比类型格式化为数字分割格式，并且进行取舍
     * @param y int/long/float/double
     * @param z int/long/float/double
     * @return String
     * @author chensq
     */
    public static String getDefaultformatpercent2places(double y, double z) {
        String percent = ""; // 接受百分比的值
        double fen = y / z;
        DecimalFormat df1 = new DecimalFormat("##.00%");
        percent = df1.format(fen);
        return percent;
    }
    
    /**
     * 
     * @Title: getDoubleFormatStringPercent2Places
     * @Description: 保留两位小数
     * @param val 原始值
     * @return String 转换后的值
     * @author www
     */
    public static String getDoubleFormatStringPercent2Places(double val) {
        DecimalFormat df = new DecimalFormat("##.00");
        
        return df.format(val);
    }
    
    /**
     * 
     * @Title: getDoubleFormatPercent2Places
     * @Description: 保留两位小数
     * @param val 原始值
     * @return double 转换后的值
     * @author www
     */
    public static double getDoubleFormatPercent2Places(double val) {
        String valStr = getDoubleFormatStringPercent2Places(val);
        
        return Double.parseDouble(valStr);
    }
    
    /**
     * 
     * @Title: getMedian
     * @Description: 获取中位数
     * @param list 需要求中位数的数组
     * @return double 
     * @author chensq
     */
    public static double getMedian(List<Double> list){
        
        Collections.sort(list);

        int len=list.size(); //整体长度
      
        double mid=0; //计算出的中位数
      
        if(len%2==0){
            double a=Double.valueOf(list.get(len/2-1))==null?0:Double.valueOf(list.get(len/2-1));
            double b=Double.valueOf(list.get(len/2+1))==null?0:Double.valueOf(list.get(len/2+1));            
            mid = (a+b)/2;
        } else {
            double a=Double.valueOf(list.get(len/2))==null?0:Double.valueOf(list.get(len/2));
            mid =  a;
        }
        
        return mid;
    }
    
    /**
     * 
     * @Title: subTractionPercent
     * @Description: 计算减去百分比
     * @param basicValue
     * @param percentValue
     * @return double
     * @author chensq
     */
    public static double subTractionPercent(double basicValue, double percentValue){
        
        return basicValue-(basicValue*(percentValue/100));
    }
    
    /**
     * 
     * @Title: addTractionPercent
     * @Description: 计算加上百分比
     * @param basicValue
     * @param percentValue
     * @return double
     * @author chensq
     */
    public static double addTractionPercent(double basicValue, double percentValue){
        
        return basicValue+(basicValue*(percentValue/100));
    }
    
}
