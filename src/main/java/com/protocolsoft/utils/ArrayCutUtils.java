/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ArrayCutUtils
 *创建人:chensq    创建时间:2017年12月11日
 */
package com.protocolsoft.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ArrayCutUtils
 * @Description: 对数组进行制定份数的切分
 * @author chensq
 *
 */
public class ArrayCutUtils {
         
    /**
     * @Title: splitAry
     * @Description: 对数组进行切分
     * @param ary
     * @param subSize
     * @return Object[]
     * @author chensq
     */
    public static Object[] splitAry(String [] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize: ary.length / subSize + 1;  
        
        List<List<String>> subAryList = new ArrayList<List<String>>();  
        
        for (int i = 0; i < count; i++) {  
            int index = i * subSize;  
           
            List<String> list = new ArrayList<String>();  
            int j = 0;  
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);  
                j++;  
            }  
        
            subAryList.add(list);  
        }  
          
        Object[] subAry = new Object[subAryList.size()];  
          
        for (int i = 0; i < subAryList.size(); i++) {  
            List<String> subList = subAryList.get(i);  
           
            String[] subAryItem = new String[subList.size()];  
            for (int j = 0; j < subList.size(); j++) {  
                subAryItem[j] = subList.get(j);  
            }  
           
            subAry[i] = subAryItem;  
        }  
          
        return subAry;  
    }
    
    
}
