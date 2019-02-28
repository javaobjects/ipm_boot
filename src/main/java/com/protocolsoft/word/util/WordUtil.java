/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * @author Administrator
 *2018年3月20日
 */
package com.protocolsoft.word.util;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @ClassName: WordUtil
 * @Description: word 报告首页生成文件
 * @author 刘斌
 *
 */
public class WordUtil {
    
    /**
     * @Title: generateWord
     * @Description: 依赖模板生成工具
     * @param param
     * @param template
     * @return CustomXWPFDocument 
     * @throws InvalidFormatException CustomXWPFDocument
     * @author 刘斌
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) throws InvalidFormatException{
        CustomXWPFDocument doc=null;
        try {
            OPCPackage pack=POIXMLDocument.openPackage(template);
            doc=new CustomXWPFDocument(pack);
            if(param!=null&&param.size()>0){
                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();   
                processParagraphs(paragraphList, param, doc); 
                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator(); 
                while(it.hasNext()){
                    XWPFTable table = it.next();  
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc); 
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    
    /**
     * @Title: processParagraphs
     * @Description: 处理段落
     * @param paragraphList
     * @param param
     * @param doc
     * @throws InvalidFormatException void
     * @author 刘斌
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList,
            Map<String, Object> param, CustomXWPFDocument doc) throws InvalidFormatException{  
        if(paragraphList!=null&&paragraphList.size()>0){
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs=paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text=run.getText(0);
                    if(text!=null){
                        boolean isSetText=false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key=entry.getKey();
                            if(text.indexOf(key)!=-1){
                                isSetText=true;
                                Object value=entry.getValue();
                                if(value instanceof String){//文本替换
                                    text=text.replace(key, value.toString());
                                }else if(value instanceof Map){//图片替换
                                }
                            }
                        }
                        if(isSetText){
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * @Title: getPictureType
     * @Description: 根据图片类型获取对应的图片类型代码
     * @param picType
     * @return int
     * @author 刘斌
     */
    public static int getPictureType(String picType){
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT; 
        if(picType!=null){
            if(picType.equalsIgnoreCase("png")){
                res=CustomXWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = CustomXWPFDocument.PICTURE_TYPE_EMF; 
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG; 
            }else if(picType.equalsIgnoreCase("wmf")){
                res = CustomXWPFDocument.PICTURE_TYPE_WMF; 
            }
        }
        return res;
    }
    
    /**
     * @Title: isContainChinese
     * @Description: 根据图片类型获取对应的图片类型代码
     * @param str
     * @return boolean
     * @author 刘斌
     */
    public static boolean isContainChinese(String str) {
        
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}