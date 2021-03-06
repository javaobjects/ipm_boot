/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: RrdData.java
 *创建人: www    创建时间: 2017年8月23日
 */
package com.protocolsoft.kpi.bean;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.rrd.drive.FetchRrd.FetchData;

/**
 * @ClassName: RrdData
 * @Description: rrd
 * @author www
 *
 */
public class RrdData {

    /**
     * rrd源数据
     */
    private FetchData sourceData;
    
    /**
     * rrd中所有列
     */
    private List<String> columns;
    
    /**
     * rrd列对应下标
     */
    private Map<String, Integer> columnIndex;
    
    /**
     * RrdData 有参，不能为NULL 
     * @param sourceData rrd数据源
     */
    public RrdData(FetchData sourceData) {
        this.sourceData = sourceData;
        init();
    }
    
    /**
     * 
     * @Title: init
     * @Description: 初始化
     * @author www
     */
    private void init() {
        columns = Arrays.asList(sourceData.getDsNames());
        columnIndex = new HashMap<String, Integer>();
        for (int i = 0; i < columns.size(); i ++) {
            columnIndex.put(columns.get(i), i);
        }
    }
    
    /**
     * 
     * @Title: getLastRrdDataByColumnName
     * @Description: 获取最后一个点数据
     * @param columnName 列名称
     * @return SimpleEntry<Long,Double>
     *          Long -> 时间戳
     *          Double -> 对于数据
     * @author www
     */
    public SimpleEntry<Long, Double> getLastRrdDataByColumnName(String columnName) {
        SimpleEntry<Long, Double> data = null;
        if (columnIndex.containsKey(columnName)) {
            int row = sourceData.getRowCount();
            if (row > 0) {
                int index = columnIndex.get(columnName);
                row = row - 1;
                data = new SimpleEntry<Long, Double>(
                        sourceData.getTimestamp(row), 
                        sourceData.getValue(row, index));
            }
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getRrdDataByColumnName
     * @Description: 获取某一列对于数据
     * @param columnName 列名称
     * @return List<SimpleEntry<Long,Double>>
     *          Long -> 时间戳
     *          Double -> 对于数据
     * @author www
     */
    public List<SimpleEntry<Long, Double>> getRrdDataByColumnName(String columnName) {
        List<SimpleEntry<Long, Double>> list = null;
        if (columnIndex.containsKey(columnName)) {
            int index = columnIndex.get(columnName);
            int row = sourceData.getRowCount();
            list = new ArrayList<SimpleEntry<Long, Double>>();
            SimpleEntry<Long, Double> entry = null;
            for (int j = 0; j < row; j++) {
                entry = new SimpleEntry<Long, Double>(
                        sourceData.getTimestamp(j), 
                        sourceData.getValue(j, index));
                list.add(entry);
            }
        }

        return list;
    }
    
    /**
     * 
     * @Title: getRrdDataByColumnName
     * @Description: 获取某一列对于数据
     * @param columnName 列名称
     * @param step 间隔
     * @return List<SimpleEntry<Long,Double>>
     *          Long -> 时间戳
     *          Double -> 对于数据
     * @author www
     */
    public List<SimpleEntry<Long, Double>> getRrdDataByColumnName(String columnName, int step) {
        List<SimpleEntry<Long, Double>> list = null;
        if (columnIndex.containsKey(columnName)) {
            int index = columnIndex.get(columnName);
            int row = sourceData.getRowCount();
            list = new ArrayList<SimpleEntry<Long, Double>>();
            SimpleEntry<Long, Double> entry = null;
            for (int j = 0; j < row; j++) {
                entry = new SimpleEntry<Long, Double>(
                        sourceData.getTimestamp(j), 
                        sourceData.getValue(j, index) * step);
                list.add(entry);
            }
        }

        return list;
    }
    
    /**
     * 
     * @Title: getRrdDataAllColumnsValues
     * @Description: 所有数据
     * @return Map<String,List<SimpleEntry<Long,Double>>>
     *          String -> 列名称
     *          Long -> 时间戳
     *          Double -> 对于数据
     * @author www
     */
    public Map<String, List<SimpleEntry<Long, Double>>> getRrdDataAllColumnsValues() {
        Map<String, List<SimpleEntry<Long, Double>>> data = null;
        data = new HashMap<String, List<SimpleEntry<Long, Double>>>();
        List<SimpleEntry<Long, Double>> list = null;
        SimpleEntry<Long, Double> entry = null;
        int col = sourceData.getColCount();
        int row = sourceData.getRowCount();
        for (int i = 0; i < col; i++) {
            list = new ArrayList<SimpleEntry<Long, Double>>();
            for (int j = 0; j < row; j++) {
                entry = new SimpleEntry<Long, Double>(
                        sourceData.getTimestamp(j), 
                        sourceData.getValue(j, i));
                list.add(entry);
            }
            data.put(sourceData.getColName(i), list);
        }

        return data;
    }

    /**
     * <br />获取 <font color="red"><b>rrd中所有列<b/></font>
     * @return columns rrd中所有列
     */
    public List<String> getColumns() {
        return columns;
    }
}