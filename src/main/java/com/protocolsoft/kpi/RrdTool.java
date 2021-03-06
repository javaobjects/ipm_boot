/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: RrdTool.java
 *创建人: www    创建时间: 2017年8月22日
 */
package com.protocolsoft.kpi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.protocolsoft.kpi.bean.RetainTimeBean;
import com.protocolsoft.kpi.bean.RrdData;
import com.protocolsoft.utils.DateUtils;

import net.java.rrd.drive.FetchRrd;
import net.java.rrd.drive.FetchRrd.FetchData;

/**
 * @ClassName: RrdTool
 * @Description: Rrd工具类
 * @author www
 *
 */
public class RrdTool {
    
    /**
     * 路径分隔符
     */
    private static final String SC = File.separator;
    
    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(RrdTool.class);
    
    /**
     * 运行进程
     */
    private static Runtime run;
    
    /**
     * 
     * @Title: getInstance
     * @Description: 获取实例
     * @return RrdTool
     * @author www
     */
    public synchronized static RrdTool getInstance() {
        if (tool == null) {
            tool = new RrdTool();
            run = Runtime.getRuntime();
        }
        
        return tool;
    }
    
    /**
     * 
     * @Title: create
     * @Description: 创建rrd文件，最小粒度为1分钟
     * @param path rrd存放路径
     * @param rrdName rrd文件名称，带后缀名
     * @param initStep rrd初始粒度
     * @param columns rrd列
     * @param time 粒度保留时间
     * @return boolean 是否创建成功
     * @author www
     */
    public boolean create(String path, String rrdName, int initStep,
            List<String> columns, List<RetainTimeBean> time) {
        boolean bool = false;
        StringBuilder createCmd = new StringBuilder("rrdtool create ");
        createCmd.append(path);
        createCmd.append(SC);
        createCmd.append(rrdName);
        createCmd.append(" --start ");
        createCmd.append(DateUtils.getNowTimeSecond(60));
        createCmd.append(" --step ");
        createCmd.append(initStep);
        for (int i = 0, len = columns.size(); i < len; i++) {
            createCmd.append(" DS:");
            createCmd.append(columns.get(i));
            createCmd.append(":GAUGE:60:U:U");
        }
        RetainTimeBean bean = null;
        for (int i = 0, len = time.size(); i < len; i ++) {
            bean = time.get(i);
            createCmd.append(" RRA:AVERAGE:0.5:");
            createCmd.append(bean.getStep() / initStep);
            createCmd.append(":");
            createCmd.append(bean.getTime() / bean.getStep());
        }
        if (isLiunx) {
            Process pro = null;
            try {
                String[] cmd = new String[]{ "/bin/sh", "-c", createCmd.toString() };
                pro = run.exec(cmd);
                pro.waitFor();
                bool = true;
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (pro != null) {
                    pro.destroy();
                }
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: update
     * @Description: 更新rrd数据
     * @param updateTime 更新时间，秒级时间戳
     * @param path rrd存放路径
     * @param rrdName rrd文件名称，带后缀名
     * @param rowValues rrd更新数据
     * @return boolean 更新是否成功
     * @author www
     */
    public boolean update(long updateTime, String path, String rrdName, List<Number> rowValues) {
        boolean bool = false;
        StringBuilder updateCmd = new StringBuilder("rrdtool update ");
        
        updateCmd.append(path);
        updateCmd.append(SC);
        updateCmd.append(rrdName);
        updateCmd.append(" ");
        updateCmd.append(updateTime);
        for (int i = 0, len = rowValues.size(); i < len; i ++) {
            updateCmd.append(":");
            updateCmd.append(rowValues.get(i));
        }
        if (isLiunx) {
            Process pro = null;
            try {
                String[] cmd = new String[]{ "/bin/sh", "-c", updateCmd.toString() };
                pro = run.exec(cmd);
                pro.waitFor();
                bool = true;
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (pro != null) {
                    pro.destroy();
                }
            }
        }
        
        return bool;
    }
    
    /**
     * 
     * @Title: fetch
     * @Description: 获取rrd时间段内数据
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param path rrd存放路径
     * @param rrdName rrd文件名称，带后缀名
     * @param step rrd时间间隔
     * @return FetchData 
     * @author www
     */
    public RrdData fetch(long starttime, long endtime, String path, String rrdName, int step) {
        RrdData rrdData = null;
        StringBuilder fetchCmd = new StringBuilder("rrdtool fetch ");
        
        fetchCmd.append(path);
        fetchCmd.append(SC);
        fetchCmd.append(rrdName);
        fetchCmd.append(" AVERAGE --start ");
        fetchCmd.append(starttime);
        fetchCmd.append(" --end ");
        fetchCmd.append(endtime);
        
        fetchCmd.append(" -r ");
        fetchCmd.append(step);
        
        if (isLiunx) {
            String[] cmd = new String[]{ "/bin/sh", "-c", fetchCmd.toString() };
            try {
                FetchData data = new FetchRrd(cmd).getData();
                rrdData = new RrdData(data);
            } catch (NumberFormatException e) { }
        }
        
        return rrdData;
    }
    
    /**
     * 是否为linux
     */
    private boolean isLiunx;
    
    /**
     * 本单类实例
     */
    private static RrdTool tool;
    
    /**
     * 构造初始化 
     */
    private RrdTool() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (null != osName && osName.indexOf("linux") > -1) {
            this.isLiunx = true;
        } else {
            this.isLiunx = false;
        }
    }
}
