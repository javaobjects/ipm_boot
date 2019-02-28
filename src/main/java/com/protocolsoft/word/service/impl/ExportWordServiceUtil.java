/**<p>Copyright © 北京协软科技有限公司版权所有。</p>

 * @author Administrator
 *      2018年3月20日
 */
package com.protocolsoft.word.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.protocolsoft.common.bean.ReportListBean;
import com.protocolsoft.kpi.service.PlotService;
import com.protocolsoft.utils.UnitUtilsNew;
import com.protocolsoft.word.bean.WordBean;
import com.protocolsoft.word.util.CustomXWPFDocument;
import com.protocolsoft.word.util.RemoveLastUtil;
import com.protocolsoft.word.util.TOC;
import com.protocolsoft.word.util.WordCopyUtil;
import com.protocolsoft.word.util.WordUtil;

/**
 * @ClassName: ExportWordServiceUtil
 * @Description: 生成word文档的类
 * @author 刘斌
 *
 */
@Service
public class ExportWordServiceUtil {

    /**
     * 
     * @Title: setCellText
     * @param cell
     * @param text
     * @param width
     * @param isShd
     * @param shdValue
     * @param shdColor void
     * @author 刘斌
     */
    public static void setCellText(XWPFTableCell cell, String text, int width,
            boolean isShd, int shdValue, String shdColor) {
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        CTShd ctshd = ctPr.isSetShd() ? ctPr.getShd() : ctPr.addNewShd();
        ctPr.addNewTcW().setW(BigInteger.valueOf(width));
        if (isShd) {
            if (shdValue > 0 && shdValue <= 38) {
                ctshd.setVal(STShd.Enum.forInt(shdValue));
            }
            if (shdColor != null) {
                ctshd.setFill(shdColor);
                ctshd.setColor("auto");
                ctshd.setColor(shdColor);
            }
        }

        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }
    
    /**
     * 
     * @Title: getTheWarningWord
     * @Description: 
     * @param bean
     * @param tagName
     * @param plotService
     * @param mapPath
     * @param map
     * @param listListMap void
     * @author 刘斌
     */
    public static void getTheWarningWord(WordBean bean,
            String tagName, PlotService plotService, Map<String, String> mapPath,
            Map<String, String> map, Map<String, List<List<ReportListBean>>> listListMap) {
        CustomXWPFDocument docq = null;
        FileOutputStream fos = null;
        try {
            
            if (null != tagName && !"".equals(tagName)) {
                File dir = new File(mapPath.get("pathpp"));
                File filet = new File(mapPath.get("pathpp") + File.separator
                        + tagName + ".docx");
                dir.mkdir();
                if (!filet.exists()) {
                    WordCopyUtil.copyFile(new File(mapPath.get("modelPath")),
                            filet);
                }
                fos = new FileOutputStream(filet);
            } else if (null == tagName) {
                return;
            }
            
            Map<String, Object> param = new HashMap<String, Object>();
            if (null != bean.getName()) {
                param.put("${name}", bean.getName() + "(" + bean.getIDCard()
                        + ")");
            } else {
                param.put("${name}", "报告");
            }
            if (null != bean.getExamNum()) {
                param.put("${examNum}", bean.getExamNum());
            } else {
                param.put("${examNum}", "");
            }
            if (null != bean.getIDCard()) {
                param.put("${IDCard}", bean.getIDCard());
            } else {
                param.put("${IDCard}", "");
            }
            if (null != bean.getCarModel()) {
                param.put("${carModel}", bean.getCarModel());
            } else {
                param.put("${carModel}", "");
            }
            if (null != bean.getTimeStart()) {
                param.put("${timeStart}", bean.getTimeStart());
            } else {
                param.put("${timeStart}", "");
            }
            if (null != bean.getTimeEnd()) {
                param.put("${timeEnd}", bean.getTimeEnd());
            } else {
                param.put("${timeEnd}", "");
            }
            if (null != bean.getTimeDepartment()) {
                param.put("${timeDepartment}", bean.getTimeDepartment());
            } else {
                param.put("${timeDepartment}", "");
            }
            if (null != mapPath.get("modelPath") && !"".equals(mapPath.get("modelPath"))) {
                docq = WordUtil.generateWord(param, mapPath.get("modelPath"));
            } 
            File filess = new File(mapPath.get("picturePath"));

            File[] files = filess.listFiles();
            Comparator<File> com = new Comparator<File>() {
                    public int compare(File file1, File file2) {
                        if((file1.getName().split("!"))[0].equals((file2.getName().split("!"))[0])){
                            if((file1.getName().split("!"))[1].equals((file2.getName().split("!"))[1])){
                                if((file1.getName().split("!"))[3].equals((file2.getName().split("!"))[3])){
                                    return (file1.getName().split("!"))[2].compareTo((file2.getName().split("!"))[2]);
                                }else{
                                    return (file1.getName().split("!"))[3].compareTo((file2.getName().split("!"))[3]);
                                }
                            }else{
                                return (file1.getName().split("!"))[1].compareTo((file2.getName().split("!"))[1]);
                            }
                        }else{
                            return (file1.getName().split("!"))[0].compareTo((file2.getName().split("!"))[0]);
                        }
                    }
                };
            TreeSet<File> ts1 = new TreeSet<>(com);
            TreeSet<File> ts2 = new TreeSet<>(com);
            TreeSet<File> ts3 = new TreeSet<>(com);
            if(null!=files&&files.length>0){
                for (File file2 : files) {
                    String fileName = file2.getName();
                    fileName = RemoveLastUtil.getFileNameNoEx(fileName);
                    String[] dfe = fileName.split("!");
                    if ("12".equals(dfe[0])) {
                        ts3.add(file2);
                    } else if("10".equals(dfe[0])){
                        ts1.add(file2);
                    } else if("11".equals(dfe[0])){
                        ts2.add(file2);
                    }

                }
            }
            
            docq.createTOC();
            int pageNum =1;
            List<SimpleEntry<Integer, String>> queue = new ArrayList<>();
            TOC toc = docq.getToc();
            
            String[] colors = new String[] { "CCA6EF", "C0C0C0", "A9A9A9", "DD999D",
                "4FCEF0", "7A7A7A", "F3C917", "FFA932",
                "C7B571", "535354", "5FD2F1", "A6DBFF",
                "FEF8B6" };
            if(null!=bean.getJsonObjects()){
                XWPFParagraph paramtsAline = docq.createParagraph();
                paramtsAline.setPageBreak(true);
                XWPFParagraph paramts2 = docq.createParagraph();
                XWPFRun rwarn = paramts2.createRun();
                paramts2.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts2.setAlignment(ParagraphAlignment.LEFT);
                rwarn.setFontSize(12);
                SimpleEntry<Integer, String> simpleEntry = new SimpleEntry<>(pageNum ++,"告警数量统计列表");
                queue.add(simpleEntry);
                
                XWPFParagraph paramts = docq.createParagraph();
                XWPFRun r2 = paramts.createRun();
                paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts.setAlignment(ParagraphAlignment.CENTER);
                
                for(int s =0; s<2; s++){
                    r2.addBreak();
                }
                r2.setText("告警");
                r2.addCarriageReturn();
                r2.setFontSize(20);
                    
                XWPFTable table = docq.createTable(1, 6);
                CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                CTTblPr tablePr = table.getCTTbl().addNewTblPr();
                tablePr.addNewJc().setVal(STJc.CENTER);
                    
                    
                infoTableWidth.setType(STTblWidth.DXA);
                infoTableWidth.setW(BigInteger.valueOf(8900));
                XWPFTableRow tableRow;
                tableRow = table.getRow(0);
                tableRow.setHeight(400);
                XWPFTableCell firstCell = null;
                int firstCellIndex = 0;
                for(int sfe =firstCellIndex; sfe<6; sfe++){
                    firstCell = tableRow.getCell(sfe);
                    setCellText(firstCell, "", 1600, true, 3, colors[2]);
                }
                tableRow.getCell(0).setText("名称");
                tableRow.getCell(1).setText("告警总数");
                tableRow.getCell(2).setText("流量告警");
                tableRow.getCell(3).setText("会话告警");
                tableRow.getCell(4).setText("服务质量告警");
                tableRow.getCell(5).setText("其他");
                for(JSONObject listMap : bean.getJsonObjects()){
                    XWPFTableRow infoTableRowTwo = table.createRow();
                    infoTableRowTwo.getCell(0).setText(listMap.getString("name"));
                    infoTableRowTwo.getCell(1).setText(listMap.getString("0"));
                    infoTableRowTwo.getCell(2).setText(listMap.getString("1"));
                    infoTableRowTwo.getCell(3).setText(listMap.getString("2"));
                    infoTableRowTwo.getCell(4).setText(listMap.getString("14"));
                    infoTableRowTwo.getCell(5).setText((Integer.parseInt(listMap.getString("0"))-
                            (Integer.parseInt(listMap.getString("1"))+Integer.parseInt(listMap.getString("2"))
                                    +Integer.parseInt(listMap.getString("14"))))+"");
                    for(int e =0; e<6; e++){
                        CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                        CTTcPr ctPr = cttc.addNewTcPr();
                        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                    }
                    for(int i =0; i<6; i++){
                        CTTc cttc = tableRow.getCell(i).getCTTc();
                        CTTcPr ctPr = cttc.addNewTcPr();
                        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                    }
                }
            }
            
            
            int addReturnPage = 0;
            if(null!= listListMap&&null!=listListMap.get("10")&&listListMap.get("10").size()>0&&listListMap.get("10").get(0).size()>0){
                if(null!=bean.getJsonObjects()){
                    XWPFParagraph paramtsAline4 = docq.createParagraph();
                    paramtsAline4.setPageBreak(true);
                }
                XWPFParagraph paramts3 = docq.createParagraph();
                XWPFRun rwarn3 = paramts3.createRun();
                paramts3.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts3.setAlignment(ParagraphAlignment.LEFT);
                rwarn3.setFontSize(12);
                rwarn3.setText("观察点常用KPI统计列表：");
                SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,"观察点常用KPI统计列表");
                queue.add(simpleEntryline);
                
                addReturnPage = 1;
                XWPFParagraph paramts = docq.createParagraph();
                XWPFRun r2 = paramts.createRun();
                paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts.setAlignment(ParagraphAlignment.CENTER);
                
                for(int s =0; s<2; s++){
                    r2.addBreak();
                }
                r2.setText("观察点");
                r2.addCarriageReturn();
                r2.setFontSize(20);
                XWPFTable table = docq.createTable(1, 7);
                CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                    
                CTTblPr tablePr = table.getCTTbl().addNewTblPr();
                tablePr.addNewJc().setVal(STJc.CENTER);
                    
                    
                infoTableWidth.setType(STTblWidth.DXA);
                infoTableWidth.setW(BigInteger.valueOf(8900));
                XWPFTableRow tableRow;
                tableRow = table.getRow(0);
                tableRow.setHeight(400);
                XWPFTableCell firstCell = null;
                int firstCellIndex = 0;
                for(int sfe =firstCellIndex; sfe<7; sfe++){
                    firstCell = tableRow.getCell(sfe);
                    setCellText(firstCell, "", 1600, true, 3, colors[2]);
                }
                tableRow.getCell(0).setText("名称");
                tableRow.getCell(1).setText("告警数量");
                tableRow.getCell(2).setText("流量");
                tableRow.getCell(3).setText("最大会话数量");
                tableRow.getCell(4).setText("服务质量");
                tableRow.getCell(5).setText("网络丢包率");
                tableRow.getCell(6).setText("带宽占用率");
                
                for(List<ReportListBean> listMap : listListMap.get("10")){
                    if(0!=listMap.size()){
                        if(listMap.size()==1){
                            ReportListBean map10 = listMap.get(0);
                            XWPFTableRow infoTableRowTwo = table.createRow();
                            infoTableRowTwo.getCell(0).setText(map10.getName());
                            SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                            infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                            SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                            infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                            SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                            infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                            SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                            infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                            SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                            infoTableRowTwo.getCell(5).setText(simpleEntry3.getKey()+simpleEntry3.getValue());
                            SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                            infoTableRowTwo.getCell(6).setText(simpleEntry4.getKey()+simpleEntry4.getValue());
                            for(int e =0; e<7; e++){
                                CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                            }
                        }else if(listMap.size()==2){
                            ReportListBean compare1 = listMap.get(0);
                            ReportListBean compare2 = listMap.get(1);
                            ReportListBean compareresult = new ReportListBean();
                            compareresult.setName("环比");
                            compareresult.setAlarmNum(compare1.getAlarmNum()-compare2.getAlarmNum());
                            compareresult.setEthernetTraffic(compare1.getEthernetTraffic()-compare2.getEthernetTraffic());
                            compareresult.setSynPkts(compare1.getSynPkts()-compare2.getSynPkts());
                            compareresult.setQosOrDelay(compare1.getQosOrDelay()-compare2.getQosOrDelay());
                            compareresult.setNetPktLostRatio(compare1.getNetPktLostRatio()-compare2.getNetPktLostRatio());
                            compareresult.setBandWidthRatio(compare1.getBandWidthRatio()-compare2.getBandWidthRatio());
                            listMap.add(compareresult);
                         
                            for(ReportListBean map10 : listMap){
                                XWPFTableRow infoTableRowTwo = table.createRow();
                                infoTableRowTwo.getCell(0).setText(map10.getName());
                                SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                                infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                                SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                                infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                                SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                                infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                                SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                                infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                                SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                                infoTableRowTwo.getCell(5).setText(formatDouble1(simpleEntry3.getKey())+simpleEntry3.getValue());
                                SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                                infoTableRowTwo.getCell(6).setText(formatDouble1(simpleEntry4.getKey())+simpleEntry4.getValue());
                                for(int e =0; e<7; e++){
                                    CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                    CTTcPr ctPr = cttc.addNewTcPr();
                                    ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                    cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                                }  
                            }
                        }
                        for(int i =0; i<7; i++){
                            CTTc cttc = tableRow.getCell(i).getCTTc();
                            CTTcPr ctPr = cttc.addNewTcPr();
                            ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                        }
                    }
                }
            }
            byte d = 0;
            
            int serilaze = 1;
            String ts1Names = "^";
            if (ts1.size() > 0) {
                XWPFParagraph paramtll = docq.createParagraph();
                paramtll.setPageBreak(true);
                for (File file2 : ts1) {
                    XWPFParagraph paramts = docq.createParagraph();
                    String fileName = file2.getName();
                    if(!ts1Names.contains("^" + fileName + "^")){
                        ts1Names += fileName + "^";
                        fileName = RemoveLastUtil.getFileNameNoEx(fileName);
                        XWPFRun r2 = paramts.createRun();
                        paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                        paramts.setAlignment(ParagraphAlignment.LEFT);
                        r2.setText(serilaze + "." + map.get(file2.getName()));
                        SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,map.get(file2.getName()));
                        queue.add(simpleEntryline);
                        r2.setFontSize(12);
                        if (null != map.get(file2.getName() + "l")
                                && !(map.get(file2.getName() + "l")).equals("")) {
                            r2.addCarriageReturn();
                            r2.setText(map.get(file2.getName() + "l"));
                            if(null!=map.get(file2.getName() + "f")){
                                r2.addCarriageReturn();
                                r2.setText(map.get(file2.getName() + "f"));
                                d =0;
                            }else{
                                d =1;
                            }
                        } else{
                            d =2;
                        }
                        File file = new File(mapPath.get("picturePath") + File.separator
                                + file2.getName());
                        InputStream inputStream = new FileInputStream(file);
                        docq.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG);
                        XWPFParagraph paramt = docq.createParagraph();
                        docq.createPicture(paramt, docq.getAllPictures().size() - 1, 650, 320, null);
                        XWPFRun r3 = paramt.createRun();
                        r3.setFontSize(12);
                        r3.setText("[注：]");
                        r3.addCarriageReturn();
                        if(serilaze!=ts1.size()){
                            r3.addCarriageReturn();
                        }
                        for(byte i = d; i>0; i--){
                            r3.addCarriageReturn();
                        }
                        serilaze++;
                    }
                }
                if(serilaze %2 == 0){
                    addReturnPage = 3;
                }else{
                    addReturnPage = 2;
                }
            }
            
            if(null!= listListMap&&null!=listListMap.get("11")&&listListMap.get("11").size()>0&&listListMap.get("11").get(0).size()>0){
                XWPFParagraph paramtszhingdongB = docq.createParagraph();
                if(addReturnPage == 1 || addReturnPage == 3){
                    paramtszhingdongB.setPageBreak(true);
                }
                addReturnPage = 1;
                
                XWPFParagraph paramts4 = docq.createParagraph();
                XWPFRun rwarn4 = paramts4.createRun();
                paramts4.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts4.setAlignment(ParagraphAlignment.LEFT);
                rwarn4.setFontSize(12);
                rwarn4.setText("客户端常用KPI统计列表：");
                SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,"客户端常用KPI统计列表");
                queue.add(simpleEntryline);
                
                XWPFParagraph paramts = docq.createParagraph();
                XWPFRun r2 = paramts.createRun();
                paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts.setAlignment(ParagraphAlignment.CENTER);
                
                for(int s =0; s<2; s++){
                    r2.addBreak();
                }
                r2.setText("客户端");
                r2.addCarriageReturn();
                r2.setFontSize(20);
                
                XWPFTable table = docq.createTable(1, 7);
                CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                
                CTTblPr tablePr = table.getCTTbl().addNewTblPr();
                tablePr.addNewJc().setVal(STJc.CENTER);
                infoTableWidth.setType(STTblWidth.DXA);
                infoTableWidth.setW(BigInteger.valueOf(8900));
                XWPFTableRow tableRow;
                tableRow = table.getRow(0);
                tableRow.setHeight(400);
                XWPFTableCell firstCell = null;
                int firstCellIndex = 0;
                for(int sfe =firstCellIndex; sfe<7; sfe++){
                    firstCell = tableRow.getCell(sfe);
                    setCellText(firstCell, "", 1600, true, 3, colors[2]);
                }
                tableRow.getCell(0).setText("名称");
                tableRow.getCell(1).setText("告警数量");
                tableRow.getCell(2).setText("流量");
                tableRow.getCell(3).setText("最大会话数量");
                tableRow.getCell(4).setText("服务质量");
                tableRow.getCell(5).setText("网络丢包率");
                tableRow.getCell(6).setText("带宽占用率");
                 
                for(List<ReportListBean> listMap : listListMap.get("11")){
                    if(0!=listMap.size()){
                        if(listMap.size()==1){
                            ReportListBean map10 = listMap.get(0);
                            XWPFTableRow infoTableRowTwo = table.createRow();
                            infoTableRowTwo.getCell(0).setText(map10.getName());
                            SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                            infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                            SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                            infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                            SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                            infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                            SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                            infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                            SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                            infoTableRowTwo.getCell(5).setText(simpleEntry3.getKey()+simpleEntry3.getValue());
                            SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                            infoTableRowTwo.getCell(6).setText(simpleEntry4.getKey()+simpleEntry4.getValue());
                            for(int e =0; e<7; e++){
                                CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                            }
                        }else if(listMap.size()==2){
                            ReportListBean compare1 = listMap.get(0);
                            ReportListBean compare2 = listMap.get(1);
                            ReportListBean compareresult = new ReportListBean();
                            compareresult.setName("环比");
                            compareresult.setAlarmNum(compare1.getAlarmNum()-compare2.getAlarmNum());
                            compareresult.setEthernetTraffic(compare1.getEthernetTraffic()-compare2.getEthernetTraffic());
                            compareresult.setSynPkts(compare1.getSynPkts()-compare2.getSynPkts());
                            compareresult.setQosOrDelay(compare1.getQosOrDelay()-compare2.getQosOrDelay());
                            compareresult.setNetPktLostRatio(compare1.getNetPktLostRatio()-compare2.getNetPktLostRatio());
                            compareresult.setBandWidthRatio(compare1.getBandWidthRatio()-compare2.getBandWidthRatio());
                            listMap.add(compareresult);
                            for(ReportListBean map10 : listMap){
                                XWPFTableRow infoTableRowTwo = table.createRow();
                                infoTableRowTwo.getCell(0).setText(map10.getName());
                                SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                                infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                                SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                                infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                                SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                                infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                                SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                                infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                                SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                                infoTableRowTwo.getCell(5).setText(formatDouble1(simpleEntry3.getKey())+simpleEntry3.getValue());
                                SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                                infoTableRowTwo.getCell(6).setText(formatDouble1(simpleEntry4.getKey())+simpleEntry4.getValue());
                                for(int e =0; e<7; e++){
                                    CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                    CTTcPr ctPr = cttc.addNewTcPr();
                                    ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                    cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                                }  
                            }
                        }
                    }
                    for(int i =0; i<7; i++){
                        CTTc cttc = tableRow.getCell(i).getCTTc();
                        CTTcPr ctPr = cttc.addNewTcPr();
                        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                    }
                }
            }
            int serilaze2 = 1;
            String ts2StrNames = "^";
            if (ts2.size() > 0) {
                d =0;
                XWPFParagraph paramtll = docq.createParagraph();
                if(addReturnPage==1||addReturnPage==0){
                    paramtll.setPageBreak(true);
                }
                for (File file2 : ts2) {
                    XWPFParagraph paramts = docq.createParagraph();
                    String fileName = file2.getName();
                    if(!ts2StrNames.contains("^"+fileName+"^")){
                        ts2StrNames+= fileName+"^"; 
                        fileName = RemoveLastUtil.getFileNameNoEx(fileName);
                        XWPFRun r2 = paramts.createRun();
                        if(serilaze2==1){
                            r2.removeBreak();
                            r2.removeCarriageReturn();
                            r2.removeBreak();
                            r2.removeCarriageReturn();
                            r2.removeBreak();
                        }
                        paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                        paramts.setAlignment(ParagraphAlignment.LEFT);
                        r2.setText(serilaze2 + "." + map.get(file2.getName()));
                        SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,map.get(file2.getName()));
                        queue.add(simpleEntryline);
                        r2.setFontSize(12);
                        if(addReturnPage==2||addReturnPage==3){
                            addReturnPage =4;
                        }else{
                        }
                        if (null != map.get(file2.getName() + "l")
                                && !(map.get(file2.getName() + "l")).equals("")) {
                            r2.addCarriageReturn();
                            r2.setText(map.get(file2.getName() + "l"));
                            if(null!=map.get(file2.getName() + "f")){
                                r2.addCarriageReturn();
                                r2.setText(map.get(file2.getName() + "f"));
                                d =0;
                            }else{
                                d =1;
                            }
                        } else{
                            d =2;
                        }
                        File file = new File(mapPath.get("picturePath") + File.separator + file2.getName());
                        InputStream inputStream = new FileInputStream(file);
                        docq.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG);
                        XWPFParagraph paramt = docq.createParagraph();
                        docq.createPicture(paramt, docq.getAllPictures().size() - 1, 650, 320, null);
                        XWPFRun r3 = paramt.createRun();
                        r3.setFontSize(12);
                        r3.setText("[注：]");
                        r3.addCarriageReturn();
                        if(serilaze2!=ts2.size()){
                            r3.addCarriageReturn();
                        }
                        for(byte i = d; i>0; i--){
                            r3.addCarriageReturn();
                        }
                        serilaze2++;
                    }
                }
                if(serilaze2%2==0){
                    addReturnPage = 3;
                }else{
                    addReturnPage = 2;
                }
            }
            
            if(null!= listListMap&&null!=listListMap.get("12")&&listListMap.get("12").size()>0&&listListMap.get("12").get(0).size()>0){
                XWPFParagraph paramtszhouA = docq.createParagraph();
                if(addReturnPage==1 || addReturnPage == 3 || (serilaze %2 == 0 && !(null!= listListMap&&null!=listListMap.get("11") 
                        && listListMap.get("11").size()>0&&listListMap.get("11").get(0).size()>0) && serilaze2 %2 != 0 && serilaze2 != 1)){
                    paramtszhouA.setPageBreak(true);
                }
                addReturnPage = 1;
                
                XWPFParagraph paramts5 = docq.createParagraph();
                XWPFRun rwarn5 = paramts5.createRun();
                paramts5.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts5.setAlignment(ParagraphAlignment.LEFT);
                rwarn5.setFontSize(12);
                rwarn5.setText("服务端常用KPI统计列表：");
                SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,"服务端常用KPI统计列表");
                queue.add(simpleEntryline);
                
                XWPFParagraph paramts = docq.createParagraph();
                
                XWPFRun r2 = paramts.createRun();
                paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                paramts.setAlignment(ParagraphAlignment.CENTER);
                for(int s =0; s<2; s++){
                    r2.addBreak();
                }
                r2.setText("服务端");
                r2.addCarriageReturn();
                r2.setFontSize(20);
                
                XWPFTable table = docq.createTable(1, 7);
                
                CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                
                CTTblPr tablePr = table.getCTTbl().addNewTblPr();
                tablePr.addNewJc().setVal(STJc.CENTER);
                
                infoTableWidth.setType(STTblWidth.DXA);
                infoTableWidth.setW(BigInteger.valueOf(8900));
                XWPFTableRow tableRow;
                tableRow = table.getRow(0);
                tableRow.setHeight(400);
                XWPFTableCell firstCell = null;
                int firstCellIndex = 0;
                for(int sfe =firstCellIndex; sfe<7; sfe++){
                    firstCell = tableRow.getCell(sfe);
                    setCellText(firstCell, "", 1600, true, 3, colors[2]);
                }
                tableRow.getCell(0).setText("名称");
                tableRow.getCell(1).setText("告警数量");
                tableRow.getCell(2).setText("流量");
                tableRow.getCell(3).setText("最大会话数量");
                tableRow.getCell(4).setText("服务质量");
                tableRow.getCell(5).setText("网络丢包率");
                tableRow.getCell(6).setText("带宽占用率");
                for(List<ReportListBean> listMap : listListMap.get("12")){
                    if(0!=listMap.size()){
                        if(listMap.size()==1){
                            ReportListBean map10 = listMap.get(0);
                            XWPFTableRow infoTableRowTwo = table.createRow();
                            infoTableRowTwo.getCell(0).setText(map10.getName());
                            SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                            infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                            SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                            infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                            SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                            infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                            SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                            infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                            SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                            infoTableRowTwo.getCell(5).setText(simpleEntry3.getKey()+simpleEntry3.getValue());
                            SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                            infoTableRowTwo.getCell(6).setText(simpleEntry4.getKey()+simpleEntry4.getValue());
                            for(int e =0; e<7; e++){
                                CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                            }       
                        }else if(listMap.size()==2){
                            ReportListBean compare1 = listMap.get(0);
                            ReportListBean compare2 = listMap.get(1);
                            ReportListBean compareresult = new ReportListBean();
                            compareresult.setName("环比");
                            compareresult.setAlarmNum(compare1.getAlarmNum()-compare2.getAlarmNum());
                            compareresult.setEthernetTraffic(compare1.getEthernetTraffic()-compare2.getEthernetTraffic());
                            compareresult.setSynPkts(compare1.getSynPkts()-compare2.getSynPkts());
                            compareresult.setQosOrDelay(compare1.getQosOrDelay()-compare2.getQosOrDelay());
                            compareresult.setNetPktLostRatio(compare1.getNetPktLostRatio()-compare2.getNetPktLostRatio());
                            compareresult.setBandWidthRatio(compare1.getBandWidthRatio()-compare2.getBandWidthRatio());
                            listMap.add(compareresult);
                            for(ReportListBean map10 : listMap){
                                XWPFTableRow infoTableRowTwo = table.createRow();
                                infoTableRowTwo.getCell(0).setText(map10.getName());
                                SimpleEntry<Double, String> simpleEntry5 = UnitUtilsNew.numFormat(map10.getAlarmNum(), "pps");
                                infoTableRowTwo.getCell(1).setText(simpleEntry5.getKey()+simpleEntry5.getValue());
                                SimpleEntry<Double, String> simpleEntry = UnitUtilsNew.numFormat(map10.getEthernetTraffic(), "flow");
                                infoTableRowTwo.getCell(2).setText(simpleEntry.getKey()+simpleEntry.getValue());
                                SimpleEntry<Double, String> simpleEntry6 = UnitUtilsNew.numFormat(map10.getSynPkts(), "pps");
                                infoTableRowTwo.getCell(3).setText(simpleEntry6.getKey()+simpleEntry6.getValue());
                                SimpleEntry<Double, String> simpleEntry2 = UnitUtilsNew.numFormat(map10.getQosOrDelay(), "ms");
                                infoTableRowTwo.getCell(4).setText(simpleEntry2.getKey()+simpleEntry2.getValue());
                                SimpleEntry<Double, String> simpleEntry3 = UnitUtilsNew.numFormat(map10.getNetPktLostRatio(), "ratio");
                                infoTableRowTwo.getCell(5).setText(formatDouble1(simpleEntry3.getKey())+simpleEntry3.getValue());
                                SimpleEntry<Double, String> simpleEntry4 = UnitUtilsNew.numFormat(map10.getBandWidthRatio(), "ratio");
                                infoTableRowTwo.getCell(6).setText(formatDouble1(simpleEntry4.getKey())+simpleEntry4.getValue());
                                for(int e =0; e<7; e++){
                                    CTTc cttc = infoTableRowTwo.getCell(e).getCTTc();
                                    CTTcPr ctPr = cttc.addNewTcPr();
                                    ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                                    cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                                }  
                            }
                        }
                    }
                    for(int i =0; i<7; i++){
                        CTTc cttc = tableRow.getCell(i).getCTTc();
                        CTTcPr ctPr = cttc.addNewTcPr();
                        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                    }
                }
            }
            int serilaze3 = 1;
            String ts3Names = "^";
            if (ts3.size() > 0) {
                d = 0;
                XWPFParagraph paramtl = docq.createParagraph();
                if(addReturnPage==1||addReturnPage==0){
                    paramtl.setPageBreak(true);
                }
                for (File file2 : ts3) {
                    XWPFParagraph paramts = docq.createParagraph();
                    String fileName = file2.getName();
                    if(!ts3Names.contains("^" + fileName + "^")){
                        ts3Names += fileName + "^";
                        fileName = RemoveLastUtil.getFileNameNoEx(fileName);
                        XWPFRun r2 = paramts.createRun();
                        if(serilaze3==1){
                            r2.removeBreak();
                            r2.removeCarriageReturn();
                            r2.removeBreak();
                            r2.removeCarriageReturn();
                            r2.removeBreak();
                        }
                        paramts.setSpacingLineRule(LineSpacingRule.AUTO);
                        paramts.setAlignment(ParagraphAlignment.LEFT);
                        r2.setText(serilaze3 + "." + map.get(file2.getName()));
                        SimpleEntry<Integer, String> simpleEntryline = new SimpleEntry<>(pageNum ++,map.get(file2.getName()));
                        queue.add(simpleEntryline);
                        r2.setFontSize(12);
                        if(addReturnPage==2||addReturnPage==3){
                            addReturnPage = 4;
                        }else{
                        }
                        if (null != map.get(file2.getName() + "l")
                                && !(map.get(file2.getName() + "l")).equals("")) {
                            r2.addCarriageReturn();
                            r2.setText(map.get(file2.getName() + "l"));
                            if(null!=map.get(file2.getName() + "f")){
                                r2.addCarriageReturn();
                                r2.setText(map.get(file2.getName() + "f"));
                                d =0;
                            }else{
                                d =1;
                            }
                        } else{
                            d =2;
                        }
                        File file = new File(mapPath.get("picturePath") + File.separator + file2.getName());
                        InputStream inputStream = new FileInputStream(file);
                        docq.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG);
                        XWPFParagraph paramt = docq.createParagraph();
                        docq.createPicture(paramt, docq.getAllPictures().size() - 1, 650, 320, null);
                        XWPFRun r3 = paramt.createRun();
                        r3.setFontSize(12);
                        r3.setText("[注：]");
                        r3.addCarriageReturn();
                        if(serilaze3!=ts3.size()){
                            r3.addCarriageReturn();
                        }
                        for(byte i = d; i>0; i--){
                            r3.addCarriageReturn();
                        }
                        serilaze3++;
                    }
                }
            }
            
            List<XWPFHeader> pageHeaders = docq.getHeaderList();
            for (int i = 0; i < pageHeaders.size(); i++) {
                List<XWPFParagraph> headerPara = pageHeaders.get(i).getParagraphs();
                for (int j = 0; j < headerPara.size(); j++) {
                    XWPFRun runHeader = headerPara.get(j).createRun();
                    runHeader.setFontSize(10);
//                    CTRPr rpr = runHeader.getCTR().isSetRPr() ? runHeader.getCTR().getRPr() : runHeader.getCTR().addNewRPr();
//                    CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
//                    fonts.setAscii("楷体");
//                    fonts.setEastAsia("楷体");
//                    fonts.setHAnsi("楷体");
                    runHeader.setText(bean.getName() + "(" + bean.getIDCard() + ")");
                    headerPara.get(j).setStyle("page-header");
                    headerPara.get(j).setAlignment(ParagraphAlignment.RIGHT);
                }
            }
            
            if(null!=queue && queue.size()!=0) {
            	for(SimpleEntry<Integer, String> entryQueue : queue) {
            		toc.addRow(2, entryQueue.getValue(),entryQueue.getKey() ,"----------");
            	}
            }
            
            
            docq.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    /**
     * 保留两位小数，四舍五入的一个老土的方法
     * @param d
     * @return double
     */
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }

    public void createTOC(CustomXWPFDocument doc) {
    	CTSdtBlock block = doc.getDocument().getBody().addNewSdt();
    	doc.createTOC();
    	doc.toc = new TOC(block);
//    	wordSetting.setCustomHeadocdingStyle(doc, "Heading1", 1);
//    	wordSetting.setCustomHeadingStyle(doc, "Heading2", 2); 
    	}
    
    
}