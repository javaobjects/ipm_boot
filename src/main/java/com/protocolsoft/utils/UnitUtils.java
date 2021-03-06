/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UnitUtils
 *创建人:chensq    创建时间:2017年5月23日
 */
package com.protocolsoft.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

/**
 * @ClassName: UnitUtils
 * @Description: 单位处理公共工具类
 * @author chensq
 *
 */
public class UnitUtils {
        
    /**
     * @Title: numFormat
     * @Description: 数据初始化
     * @param value 数值
     * @param unit 单位
     * @return SimpleEntry<Double, String> 
     * @author www
     */
    public static SimpleEntry<Double, String> numFormat(double value, String unit) {
        switch (unit) {
            case "num":
            case "pps":
                if(value >= 100000000) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 100000000);
                    unit = "亿个";
                } else if(value >= 10000) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 10000);
                    unit = "万个";
                } else {
                    unit = "个";
                }
                break;
            case "ms":
                value = MathUtlis.getDoubleFormatPercent2Places(value);
                unit = "ms";
                break;
            case "flow":
                if (value < 1000) {
                    unit = "b";
                } else if (value >= 1000 && value < 1000000) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 1000);
                    unit = "Kb";
                } else if (value >= 1000000 && value < 1000000000) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 1000000);
                    unit = "Mb";
                } else if (value >= 1000000000 && value < 1000000000000F) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 1000000000);
                    unit = "Gb";
                } else if (value >= 1000000000000F) {
                    value = MathUtlis.getDoubleFormatPercent2Places(value / 1000000000000F);
                    unit = "Tb";
                }
                break;
            case "flowB":
                unit = "B";
                break;
            case "ratio":
                unit = "%";
                break;
            default:
                break;
        }
        
        return new SimpleEntry<Double, String>(value, unit);
    }
        
    /**
     * @Title: changeKpiUnitMap
     * @Description: 对kpis的单位进行转换
     * @return Map<String,String>
     * @author chensq
     */
    public static Map<String, String> changeKpiUnitMap(){
        Map<String, String> unitMap=new HashMap<String, String> ();
        unitMap.put("flow", "b");
        unitMap.put("ms", "ms");
        unitMap.put("num", "个");
        unitMap.put("pps", "pps");
        unitMap.put("ratio", "%");
        unitMap.put("flowB", "B");
        return unitMap;        
    }
    
}
