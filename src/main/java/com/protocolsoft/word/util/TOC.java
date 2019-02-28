package com.protocolsoft.word.util;


import java.math.BigInteger;

import org.apache.poi.util.Internal;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtEndPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabTlc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme;

public class TOC {

	CTSdtBlock block;

	public TOC() {
		this(CTSdtBlock.Factory.newInstance());
	}

	public TOC(CTSdtBlock block) {
		this.block = block;
		CTSdtPr sdtPr = block.addNewSdtPr();
		CTDecimalNumber id = sdtPr.addNewId();
		id.setVal(new BigInteger("4844945"));
		sdtPr.addNewDocPartObj().addNewDocPartGallery().setVal("Table of contents");
		CTSdtEndPr sdtEndPr = block.addNewSdtEndPr(); CTRPr rPr = sdtEndPr.addNewRPr();
		CTFonts fonts = rPr.addNewRFonts(); fonts.setAsciiTheme(STTheme.MINOR_H_ANSI);
		fonts.setEastAsiaTheme(STTheme.MINOR_H_ANSI); fonts.setHAnsiTheme(STTheme.MINOR_H_ANSI);
		fonts.setCstheme(STTheme.MINOR_BIDI); rPr.addNewB().setVal(STOnOff.OFF);
		rPr.addNewBCs().setVal(STOnOff.OFF); rPr.addNewColor().setVal("auto");
		rPr.addNewSz().setVal(new BigInteger("24"));
		rPr.addNewSzCs().setVal(new BigInteger("24"));
		CTSdtContentBlock content = block.addNewSdtContent();
		CTP p = content.addNewP();
//		p.setRsidR("00EF7E24".getBytes("utf-8"));
//		p.setRsidRDefault("00EF7E24".getBytes("utf-8"));
		p.addNewPPr().addNewPStyle().setVal("TOCHeading");
		p.addNewR().addNewT().setStringValue("目 录");
		//源码中为"Table of contents" 
		//设置段落对齐方式，即将“目录”二字居中 
		CTPPr pr = p.getPPr(); CTJc jc = pr.isSetJc() ? pr.getJc() : pr.addNewJc();
		STJc.Enum en = STJc.Enum.forInt(ParagraphAlignment.CENTER.getValue());
		jc.setVal(en);
		//"目录"二字的字体 
		CTRPr pRpr = p.getRArray(0).addNewRPr();
		fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii("Times New Roman");
		fonts.setEastAsia("华文中宋");
		fonts.setHAnsi("华文中宋"); 
		//"目录"二字加粗 
		CTOnOff bold = pRpr.isSetB() ? pRpr.getB() : pRpr.addNewB(); bold.setVal(STOnOff.TRUE);
		// 设置“目录”二字字体大小为24号 
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz(); sz.setVal(new BigInteger("36"));
	}

    @Internal
    public CTSdtBlock getBlock() {
		return this.block;
	}

    public void addRow(int level, String title, int page, String bookmarkRef) {
    	CTSdtContentBlock contentBlock = this.block.getSdtContent();
    	CTP p = contentBlock.addNewP();
//    	p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//    	p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
    	CTPPr pPr = p.addNewPPr(); pPr.addNewPStyle().setVal("TOC" + level);
    	CTTabs tabs = pPr.addNewTabs();
    	//Set of Custom Tab Stops自定义制表符集合
    	CTTabStop tab = tabs.addNewTab();
    	//Custom Tab Stop自定义制表符 
    	tab.setVal(STTabJc.RIGHT);
    	tab.setLeader(STTabTlc.DOT);
    	tab.setPos(new BigInteger("9100"));
    	//默认为8290，因为调整过页边距，所有需要调整，手动设置找出最佳值
    	pPr.addNewRPr().addNewNoProof();
    	//不检查语法
    	CTR run = p.addNewR();
    	run.addNewRPr().addNewNoProof();
    	run.addNewT().setStringValue(title);
    	//添加标题文字 //设置标题字体 
    	CTRPr pRpr = run.getRPr();
    	CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
    	fonts.setAscii("Times New Roman");
    	fonts.setEastAsia("楷体"); fonts.setHAnsi("楷体");
    	// 设置标题字体大小
    	CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
    	sz.setVal(new BigInteger("21"));
    	CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
    	szCs.setVal(new BigInteger("21"));
    	//添加制表符
    	run = p.addNewR();
    	run.addNewRPr().addNewNoProof();
    	run.addNewTab();
    	//添加页码左括号
    	p.addNewR().addNewT().setStringValue("(");
    	//STFldCharType.BEGIN标识与结尾处STFldCharType.END相对应 
    	run = p.addNewR();
    	run.addNewRPr().addNewNoProof();
    	run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
    	//Field Character Type // pageref run 
    	run = p.addNewR();
    	run.addNewRPr().addNewNoProof();
    	CTText text = run.addNewInstrText();
    	//Field Code 添加域代码文本控件 
    	text.setSpace(SpaceAttribute.Space.PRESERVE);
    	// bookmark reference //源码的域名为" PAGEREF _Toc","\h"含义为在目录内建立目录项与页码的超链接 
    	text.setStringValue(" PAGEREF "+bookmarkRef + " \\h ");
    	p.addNewR().addNewRPr().addNewNoProof();
    	run = p.addNewR();
    	run.addNewRPr().addNewNoProof();
    	run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE); 
    	// page number run 
    	run = p.addNewR(); run.addNewRPr().addNewNoProof();
    	run.addNewT().setStringValue("第" + page + "部分");
    	run = p.addNewR(); run.addNewRPr().addNewNoProof();
    	//STFldCharType.END标识与上面STFldCharType.BEGIN相对应
    	run.addNewFldChar().setFldCharType(STFldCharType.END); 
    	//添加页码右括号
    	p.addNewR().addNewT().setStringValue(")");
    	//设置行间距
    	CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing(): pPr.addNewSpacing();
    	pSpacing.setLineRule(STLineSpacingRule.AUTO);
    	//行间距类型：多倍 
    	pSpacing.setLine(new BigInteger("360"));
    	//此处1.5倍行间距 }
    }

    public void addRowOnlyTitle(int level, String title) {
    	CTSdtContentBlock contentBlock = this.block.getSdtContent(); 
    	CTP p = contentBlock.addNewP();
//    	p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//    	p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
    	CTPPr pPr = p.addNewPPr();
    	pPr.addNewPStyle().setVal("TOC" + level);
    	CTTabs tabs = pPr.addNewTabs();
    	//Set of Custom Tab Stops自定义制表符集合
    	CTTabStop tab = tabs.addNewTab();
    	//Custom Tab Stop自定义制表符
    	tab.setVal(STTabJc.RIGHT);
    	tab.setLeader(STTabTlc.DOT);
    	tab.setPos(new BigInteger("9190"));
    	//默认为8290，因为调整过页边距，所有需要调整，手动设置找出最佳值
    	pPr.addNewRPr().addNewNoProof();
    	//不检查语法 
    	CTR run = p.addNewR(); 
    	run.addNewRPr().addNewNoProof();
    	run.addNewT().setStringValue(title);
    	//设置行间距
    	CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing(): pPr.addNewSpacing();
    	pSpacing.setLineRule(STLineSpacingRule.AUTO);
    	//行间距类型：多倍 
    	pSpacing.setLine(new BigInteger("360"));
    	//此处1.5倍行间距 
    	pSpacing.setBeforeLines(new BigInteger("20"));
    	//段前0.2 
    	pSpacing.setAfterLines(new BigInteger("10"));
    	//段后0.1 //设置字体 
    	CTRPr pRpr = run.getRPr();
    	CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
    	fonts.setAscii("Times New Roman"); fonts.setEastAsia("黑体"); 
    	fonts.setHAnsi("黑体");
    	// 设置字体大小
    	CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
    	sz.setVal(new BigInteger("24"));
    	CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs(); 
    	szCs.setVal(new BigInteger("24")); 
    	}
}

