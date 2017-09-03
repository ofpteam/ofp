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

import javax.swing.JOptionPane;

import com.webside.ofp.common.bean.PrintProductTagBean;

public class PrintUtil implements Printable{
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
		this.startX = 10;
		this.startY = 3;
		this.pages = this.sourcelist == null ? 0 : this.sourcelist.size();

		pcoord.put("smallLogoX", 40F);
		pcoord.put("smallLogoY", 3F);

		pcoord.put("artNoX", 10F);
		pcoord.put("artNoY", 22F);

		pcoord.put("facNoX", 10F);
		pcoord.put("facNoY", 30F);

		pcoord.put("tbhX", 10F);
		pcoord.put("tbhY", 38F);

		pcoord.put("weightAndVolX", 10F);
		pcoord.put("weightAndVolY", 46F);

		pcoord.put("measX", 10F);
		pcoord.put("measY", 54F);

		pcoord.put("gwX", 10F);
		pcoord.put("gwY", 62F);

		pcoord.put("qcAndCbmX", 10F);
		pcoord.put("qcAndCbmY", 70F);
	}
	
	/**
	 * @param Graphic指明打印的图形环境
	 * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
	 * @param pageIndex指明页号
	 * 
	 **/
   public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
	  Component c = null;
      //转换成Graphics2D
      Graphics2D g2 = (Graphics2D) gra;
      //设置打印颜色为黑色
      g2.setColor(Color.black);
      

      switch(pageIndex){
         case 0:
           Font font = new Font("新宋体", Font.PLAIN, 5);
           g2.setFont(font);//设置字体
           //BasicStroke   bs_3=new   BasicStroke(0.5f);   
           float[] dash1 = {2.0f};
           //设置打印线的属性。   
           //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量  
           g2.setStroke(new BasicStroke(0.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,2.0f,dash1,0.0f));
           
           if(obj.getSmallLogo() != null && !"".equals(obj.getSmallLogo())){
        	   Image img = Toolkit.getDefaultToolkit().getImage(obj.getSmallLogo());  
//               g2.drawImage(src,pcoord.get("smallLogoX"),pcoord.get("smallLogoY"),c);
               g2.drawImage(img,pcoord.get("smallLogoX").intValue(), pcoord.get("smallLogoY").intValue(), 30, 15, c);
           }
           
           g2.drawString(obj.getArtNo() == null ? "" : obj.getArtNo(), pcoord.get("artNoX"), pcoord.get("artNoY")); 
           g2.drawString(obj.getFacNo() == null ? "" : obj.getFacNo(), pcoord.get("facNoX"), pcoord.get("facNoY"));  
           g2.drawString(obj.getTbh() == null ? "" : obj.getTbh(), pcoord.get("tbhX"), pcoord.get("tbhY"));
           g2.drawString(obj.getWeightAndVol() == null ? "" : obj.getWeightAndVol(), pcoord.get("weightAndVolX"), pcoord.get("weightAndVolY"));
         
           g2.drawString(obj.getMeas() == null ? "" : obj.getMeas(), pcoord.get("measX"), pcoord.get("measY"));            
           g2.drawString(obj.getGw() == null ? "" : obj.getGw(), pcoord.get("gwX"), pcoord.get("gwY"));
           g2.drawString(obj.getQcAndCbm() == null ? "" : obj.getQcAndCbm(), pcoord.get("qcAndCbmX"), pcoord.get("qcAndCbmY"));
           
         return PAGE_EXISTS;
         default:
         return NO_SUCH_PAGE;
      }
      
   }

	// 打印内容到指定位置
	public void printContent() {
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

			// 获取打印服务对象
			PrinterJob job = PrinterJob.getPrinterJob();
			// 设置打印类
			job.setPageable(book);
			try {
				// 直接打印
				for (PrintProductTagBean obj : sourcelist) {
					this.obj = obj;
					job.print();
				}
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		} else {
			// 如果打印内容为空时，提示用户打印将取消
			JOptionPane.showConfirmDialog(null, "对不起, 打印内容为空, 打印取消!", "提示", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}
}