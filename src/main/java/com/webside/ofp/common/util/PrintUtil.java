package com.webside.ofp.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webside.ofp.common.bean.PrintProductTagBean;

public class PrintUtil implements Printable{
	public static Logger logger = LoggerFactory.getLogger(PrintUtil.class);
	
	private static final String PRINTER_NAME = "Xprinter XP-350B";
	
	private static final int SUCCESS = 0;
	private static final int ERROR = 1;
	/**
	 * 纸张尺寸
	 */
	private double width;
	private double height;

	/**
	 * 打印起始坐标
	 */
	private double startX;
	private double startY;

	// 打印页数
	private int pages;

	// 打印数据坐标
	private Map<String, Float> pcoord;

	// 打印数据源
	private List<PrintProductTagBean> sourcelist;
	// private int PAGES = 0;

	private PrintProductTagBean obj;

	public PrintUtil() {
	}

	public PrintUtil(List<PrintProductTagBean> sourcelist) {
		this.pcoord = this.pcoord == null ? new HashMap<String, Float>() : this.pcoord;
		this.pcoord.clear();
		this.sourcelist = sourcelist;

		this.width = 100; //(39.5/25.4)*72
		this.height = 80; //(29.2/25.4)*72
		this.startX = 5;
		this.startY = 3;
		this.pages = this.sourcelist == null ? 0 : this.sourcelist.size();

		pcoord.put("smallLogoX", 35F);
		pcoord.put("smallLogoY", 3F);

		pcoord.put("artNoX", 5F);
		pcoord.put("artNoY", 24.5F);

		pcoord.put("facNoX", 5F);
		pcoord.put("facNoY", 33.5F);

		pcoord.put("tbhX", 5F);
		pcoord.put("tbhY", 43F);

		pcoord.put("weightAndVolX", 5F);
		pcoord.put("weightAndVolY", 52.5F);

		pcoord.put("measX", 5F);
		pcoord.put("measY", 62F);

		pcoord.put("gwX", 5F);
		pcoord.put("gwY", 71.5F);

		pcoord.put("qcAndCbmX", 5F);
		pcoord.put("qcAndCbmY", 81F);
	}
	
	/**
	 * @param Graphic指明打印的图形环境
	 * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
	 * @param pageIndex指明页号
	 * 
	 **/
   public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
	  logger.info("开始调用java后台打印程序打印,产品编号：" + obj.getArtNo() + "===page:" + pageIndex);
	  Component c = null;
      //转换成Graphics2D
      Graphics2D g2 = (Graphics2D) gra;
      //设置打印颜色为黑色
      g2.setColor(Color.black);
      
      switch(pageIndex){
         case 0:
           Font font = new Font("Arial", Font.PLAIN, 8);
//           Font fontBlack = new Font("Bell MT", Font.PLAIN, 8);
//           g2.setFont(fontBlack);
           g2.setFont(font);//设置字体
           //BasicStroke   bs_3=new   BasicStroke(0.5f);   
           float[] dash1 = {2.0f};
           //设置打印线的属性。   
           //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量  
           g2.setStroke(new BasicStroke(0.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,2.0f,dash1,0.0f));
           logger.info("开始打印logo");
           if(obj.getSmallLogo() != null && !"".equals(obj.getSmallLogo())){
        	   Image img = Toolkit.getDefaultToolkit().getImage(obj.getSmallLogo());
               g2.drawImage(img,pcoord.get("smallLogoX").intValue(), pcoord.get("smallLogoY").intValue(), 41, 15, c);
           }
//           g2.drawString("ALIC",pcoord.get("smallLogoX"), pcoord.get("smallLogoY"));
           logger.info("开始打印产品编码");
           g2.drawString(obj.getArtNo() == null ? "" : obj.getArtNo(), pcoord.get("artNoX"), pcoord.get("artNoY"));
           logger.info("开始打印厂商编码");
           g2.drawString(obj.getFacNo() == null ? "" : obj.getFacNo(), pcoord.get("facNoX"), pcoord.get("facNoY"));
           logger.info("开始打印TBH");
           g2.drawString(obj.getTbh() == null ? "" : obj.getTbh(), pcoord.get("tbhX"), pcoord.get("tbhY"));
           logger.info("开始打印WeightAndVol");
           g2.drawString(obj.getWeightAndVol() == null ? "" : obj.getWeightAndVol(), pcoord.get("weightAndVolX"), pcoord.get("weightAndVolY"));
           logger.info("开始打印meas");
           g2.drawString(obj.getMeas() == null ? "" : obj.getMeas(), pcoord.get("measX"), pcoord.get("measY"));            
           logger.info("开始打印GW");
           g2.drawString(obj.getGw() == null ? "" : obj.getGw(), pcoord.get("gwX"), pcoord.get("gwY"));
           logger.info("开始打印Qc");
           g2.drawString(obj.getQcAndCbm() == null ? "" : obj.getQcAndCbm(), pcoord.get("qcAndCbmX"), pcoord.get("qcAndCbmY"));
           
         return PAGE_EXISTS;
         default:
        	logger.info("打印完成,退出");
         return NO_SUCH_PAGE;
      }
      
   }

	// 打印内容到指定位置
	public int printContent() {
		logger.info("打印产品标签 开始");
		if (sourcelist != null && sourcelist.size() > 0) // 当打印内容不为空时
		{
			// PAGES = printpaper.getSourcelist().size(); // 获取打印总页数
			// 书、文档
			Book book = new Book();
			// 设置成竖打
			PageFormat pf = new PageFormat();
			pf.setOrientation(PageFormat.PORTRAIT);
			// 通过Paper设置页面的空白边距和可打印区域。

			Paper p = new Paper();
			p.setSize(this.width, this.height);// 纸张大小
			p.setImageableArea(this.startX, this.startY, this.width, this.height);// 设置打印区域

			pf.setPaper(p);
			// 把 PageFormat 和 Printable 添加到书中，组成一个页面
			book.append(this, pf);

//			PrinterResolution printerResolution = new PrinterResolution(300, 300, PrinterResolution.DPI);
			PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
//			attr.add(printerResolution);
			attr.add(PrintQuality.HIGH);
			
			/*PrintService PS = PrintServiceLookup.lookupDefaultPrintService();
			System.out.println("当前打印机名称：" + PS.getName());*/
			
			PrintService myPrintService = null;
			
			//可用的打印机列表(字符串数组)
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
			PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
			for(int i=0;i<printService.length;i++){
				System.out.println("打印机名称：" + printService[i].getName());
				if(PRINTER_NAME.equals(printService[i].getName())){
					myPrintService = printService[i];
				}
			}
			
			if(myPrintService == null){
				logger.error("没有找到打印机服务：" + PRINTER_NAME);
			}
			
			// 获取打印服务对象
			PrinterJob job = PrinterJob.getPrinterJob();
			// 设置打印类
			job.setPageable(book);
			try {
				// 直接打印
				for (PrintProductTagBean obj : sourcelist) {
					this.obj = obj;
					job.setPrintService(myPrintService);
//					job.printDialog();
					logger.info("开始打印产品标签,产品编号：" + obj.getArtNo());
					job.print(attr);
				}
				return SUCCESS;
			} catch (PrinterException e) {
				logger.error("打印产品标签异常：",e);
			}
		} else {
			// 如果打印内容为空时，提示用户打印将取消
			JOptionPane.showConfirmDialog(null, "对不起, 打印内容为空, 打印取消!", "提示", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
		logger.info("打印产品标签 结束");
		return ERROR;
	}
}