package com.webside.ofp.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.enums.ExportType;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;

import jxl.CellView;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class OfpExportUtils extends ExportUtils{
	//报价单excel模板名称
	private static final String QUOTATION_SHEET_TEMPLATE_PATH = "\\template\\quotation_sheet.xls";
	
	
	/**
	 * 导出
	 * 
	 * @param response
	 *            响应对象
	 * @throws Exceptiono
	 */
	@SuppressWarnings("unchecked")
	public static void exportQuotationSheet(HttpServletResponse response,QuotationSheetEntity QuotationSheet,String exportType,String basePath)
			throws Exception {
		if(ExportType.EXCEL.name().equalsIgnoreCase(exportType)){
			exportQuotationSheetExcel(response,QuotationSheet,basePath);
		}else if(ExportType.PDF.name().equalsIgnoreCase(exportType)){
			exportQuotationSheetPdf(response,QuotationSheet,basePath);
		}
	}
	
	public static void exportQuotationSheetExcel(HttpServletResponse response,QuotationSheetEntity quotationSheet,String basePath) throws Exception {
		
		// 设置响应头
		response.setContentType("application/vnd.ms-excel");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename="
				+ (quotationSheet.getQuotationSheetCode()+System.currentTimeMillis()) + ".xls");
		// 获取输出流
		OutputStream outputStream = response.getOutputStream();
		exportQuotationSheetExcel(outputStream,quotationSheet,basePath);
	}
	 
	/**
	 * 导出pdf，这里先导出excel，然后使用jacob 把excel转为pdf
	 * @param response
	 * @param quotationSheet
	 * @param basePath
	 * @throws Exception
	 */
	public static void exportQuotationSheetPdf(HttpServletResponse response,QuotationSheetEntity quotationSheet,String basePath) throws Exception {
		String fileName = quotationSheet.getQuotationSheetCode()+System.currentTimeMillis();
		// 设置响应头
		response.setContentType("application/pdf");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName + ".pdf");
		// 获取输出流
//		OutputStream outputStream = response.getOutputStream();
		
		//导出excel到指定路径
		OutputStream outputStream = new FileOutputStream(new File("D:\\"+fileName+".xls"));
		exportQuotationSheetExcel(outputStream,quotationSheet,basePath);
		
		FileInputStream fis = new FileInputStream(new File("D:\\"+fileName+".xls"));
		
	}
	
	public static void exportQuotationSheetExcel(OutputStream outputStream,QuotationSheetEntity quotationSheet,String basePath) throws Exception {
		// 定义Excel对象
		Workbook book = Workbook.getWorkbook(new File(basePath+QUOTATION_SHEET_TEMPLATE_PATH));
		WorkbookSettings settings = new WorkbookSettings();  
		settings.setWriteAccess(null); 
		// 定义Excel对象
		WritableWorkbook wwb = Workbook.createWorkbook(outputStream,book,settings);
		// 获取Sheet页
		WritableSheet sheet = wwb.getSheet(0);
		sheet.getSettings().setSelected(true);
		
		// 定义表头字体样式、表格字体样式
		WritableFont headerFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
		WritableFont bodyFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9,
				WritableFont.NO_BOLD);
		WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
		WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
		
		WritableCellFormat headerCellFormat2 = new WritableCellFormat(bodyFont);
		
		// 设置表头样式：加边框、背景颜色为淡灰、居中样式
		headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		headerCellFormat.setBackground(Colour.PALE_BLUE);
		headerCellFormat.setAlignment(Alignment.CENTRE);
		headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// 设置表头样式：加边框、居中样式
		headerCellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
		headerCellFormat2.setAlignment(Alignment.CENTRE);
		headerCellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// 设置表格体样式：加边框、居中
		bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyCellFormat.setAlignment(Alignment.CENTRE);
		bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		//日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if(quotationSheet.getQuotationDate() != null){
			String date = sdf.format(quotationSheet.getQuotationDate());
			Label labelDate = new Label(1,2,date,headerCellFormat2);
			sheet.addCell(labelDate);
		}
		
		if(quotationSheet.getCustomer() != null){
			Label labelCustomer = new Label(1,3,quotationSheet.getCustomer().getCustomerName(),headerCellFormat2);
			sheet.addCell(labelCustomer);
		}
		
		if(quotationSheet.getPayMode() != null){
			Label labelPayMode = new Label(4,4,quotationSheet.getPayMode(),headerCellFormat2);
			sheet.addCell(labelPayMode);
		}
		
		
		List<QuotationSubSheetEntity> subList = quotationSheet.getSubSheetList();
		int subStartRow = 8;
		for(int i=0;i<subList.size();i++){
			QuotationSubSheetEntity subSheet = subList.get(i);
			
		    sheet.setRowView(subStartRow+1, 1600, false); //设置行高
		    
			//产品编号
			Label itemNoHLabel = new Label(0,subStartRow,"Item No.",headerCellFormat);
			sheet.addCell(itemNoHLabel);
			Label itemNoLabel = new Label(0,subStartRow+1,subSheet.getProduct().getProductCode(),bodyCellFormat);
			sheet.addCell(itemNoLabel);
			
			//缩略图
			Label photoHLabel = new Label(1,subStartRow,"Photo",headerCellFormat);
			sheet.addCell(photoHLabel);
			WritableImage image = new WritableImage(1,subStartRow+1,1,1,subSheet.getProduct().getThumbnail());
			sheet.addImage(image);
			
			//货描
			Label descHLabel = new Label(2,subStartRow,"Description",headerCellFormat);
			sheet.addCell(descHLabel);
			Label descLabel = new Label(2,subStartRow+1,subSheet.getPacking(),bodyCellFormat);
			sheet.addCell(descLabel);
			
			//美金单价
			Label priceHLabel = new Label(3,subStartRow,"Price($)",headerCellFormat);
			sheet.addCell(priceHLabel);
			jxl.write.Number priceLabel = new jxl.write.Number(3,subStartRow+1,subSheet.getUsdPrice(),bodyCellFormat);
			sheet.addCell(priceLabel);
			
			//港口
			Label portHLabel = new Label(4,subStartRow,"Port",headerCellFormat);
			sheet.addCell(portHLabel);
			Label portLabel = new Label(4,subStartRow+1,quotationSheet.getResource(),bodyCellFormat);
			sheet.addCell(portLabel);
			
			//数量
			Label moqHLabel = new Label(5,subStartRow,"MOQ",headerCellFormat);
			sheet.addCell(moqHLabel);
			jxl.write.Number moqLabel = new jxl.write.Number(5,subStartRow+1,subSheet.getNumber(),bodyCellFormat);
			sheet.addCell(moqLabel);
			
			//装箱率
			Label packingRateHLabel = new Label(0,subStartRow+2,"Inner/Outer Qty",headerCellFormat);
			sheet.addCell(packingRateHLabel);
			jxl.write.Number packingRateLabel = new jxl.write.Number(1,subStartRow+2,subSheet.getPackingRate(),bodyCellFormat);
			sheet.addCell(packingRateLabel);
			
			//产品口底高
			Label sizeHLabel = new Label(2,subStartRow+2,"Size of Item(mm)",headerCellFormat);
			sheet.addCell(sizeHLabel);
			sheet.mergeCells(3, subStartRow+2, 5, subStartRow+2);
			Label sizeLabel = new Label(3,subStartRow+2,subSheet.getTop()+"*"+subSheet.getBottom()+"*"+subSheet.getHeight(),bodyCellFormat);
			sheet.addCell(sizeLabel);
			
			//外箱长宽高
			Label cartonHLabel = new Label(0,subStartRow+3,"Carton Details(cm)",headerCellFormat);
			sheet.addCell(cartonHLabel);
			sheet.mergeCells(1, subStartRow+3, 3, subStartRow+3);
			Label cartonLabel = new Label(1,subStartRow+3,subSheet.getProduct().getLength()+"*"+subSheet.getProduct().getWidth()+"*"+subSheet.getProduct().getPackHeight(),bodyCellFormat);
			sheet.addCell(cartonLabel);
			
			
			//cbm
			Label cbmHLabel = new Label(4,subStartRow+3,"CBM",headerCellFormat);
			sheet.addCell(cbmHLabel);
			jxl.write.Number cbmLabel = new jxl.write.Number(5,subStartRow+3,subSheet.getProduct().getCbm(),bodyCellFormat);
			sheet.addCell(cbmLabel);
			
			subStartRow += 5;
		}
		
		// 写入Excel工作表
		wwb.write();
		// 关闭Excel工作薄对象
		book.close();
		wwb.close();
		// 关闭流
		outputStream.flush();
		outputStream.close();
		outputStream = null;
	}
}
