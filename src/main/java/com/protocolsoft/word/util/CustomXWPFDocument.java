/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * @author Administrator
 *      2018年3月20日
 */
package com.protocolsoft.word.util;

import java.io.IOException;  

import java.io.InputStream;  

import org.apache.poi.openxml4j.opc.OPCPackage;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;  
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;  
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;  
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;  
   

/**
 * @ClassName: CustomXWPFDocument
 * @Description: 向word文档插入图片的工具类
 * @author 刘斌
 *
 */
public class CustomXWPFDocument extends XWPFDocument {
	
	public TOC toc;
    /**
     * @throws IOException 
     * <p>Title: </p>
     * <p>Description: 有参的构造方法</p>
     * @param in
     */ 
    public CustomXWPFDocument(InputStream in) throws IOException{    
        super(in);    
    }    

        
    /**
     * <p>Title: </p>
     * <p>Description: 无参的构造方法</p>
     */ 
    public CustomXWPFDocument() {    
        super();  
    }    

    /**
     * @throws IOException
     * <p>Title: </p>
     * <p>Description: Document构造方法</p>
     * @param pkg
     */ 
    public CustomXWPFDocument(OPCPackage pkg) throws IOException { 
        super(pkg);    
    }
    
    /**
     * @Title: createPicture
     * @Description: 创建图片边框 镶入图片
     * @param paragraph
     * @param id
     * @param width
     * @param height
     * @param picAttch void
     * @author 刘斌
     */
    public void createPicture(XWPFParagraph paragraph, int id, int width, int height, String picAttch) {    
        final int emu = 9525;    
        try {
            width *= emu;
            height *= emu;
            String blipId = getAllPictures().get(id).getPackageRelationship().getId();
            CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
            if(null!=picAttch){
                paragraph.createRun().setText(picAttch);
            }
            String picXml = ""
                    + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                    + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                    + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                    + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
                    + id
                    + "\" name=\"Generated\"/>"
                    + "            <pic:cNvPicPr/>"
                    + "         </pic:nvPicPr>"
                    + "         <pic:blipFill>"
                    + "            <a:blip r:embed=\""
                    + blipId
                    + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                    + "            <a:stretch>"
                    + "               <a:fillRect/>"
                    + "            </a:stretch>"
                    + "         </pic:blipFill>"
                    + "         <pic:spPr>"
                    + "            <a:xfrm>"
                    + "               <a:off x=\"0\" y=\"0\"/>"
                    + "               <a:ext cx=\""
                    + width
                    + "\" cy=\""
                    + height
                    + "\"/>"
                    + "            </a:xfrm>"
                    + "            <a:prstGeom prst=\"rect\">"
                    + "               <a:avLst/>"
                    + "            </a:prstGeom>"
                    + "         </pic:spPr>"
                    + "      </pic:pic>"
                    + "   </a:graphicData>" + "</a:graphic>";
            inline.addNewGraphic().addNewGraphicData();
            XmlToken xmlToken = null;
            try {
                xmlToken = XmlToken.Factory.parse(picXml);
            } catch (XmlException xe) {
                xe.printStackTrace();
            }
            inline.set(xmlToken);
            inline.setDistT(0);
            inline.setDistB(0);
            inline.setDistL(0);
            inline.setDistR(0);
            CTPositiveSize2D extent = inline.addNewExtent();
            extent.setCx(width);
            extent.setCy(height);
            CTNonVisualDrawingProps docPr = inline.addNewDocPr();
            docPr.setId(id);
            docPr.setName("图片" + id);
            docPr.setDescr("");
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
    
    /**
     * 
     */
    public void createTOC() {
        CTSdtBlock block = this.getDocument().getBody().addNewSdt();
        TOC toc = new TOC(block);
        this.toc =toc;
        for (XWPFParagraph par: paragraphs ) {
            String parStyle = par.getStyle();
            if (parStyle != null && parStyle.startsWith("Heading")) {
                try {
                    int level = Integer.valueOf(parStyle.substring("Heading".length()));
                    toc.addRow(level, par.getText(), 1, "112723803");
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
  
	public TOC getToc() {
		return toc;
	}


	public void setToc(TOC toc) {
		this.toc = toc;
	}
    
    
}