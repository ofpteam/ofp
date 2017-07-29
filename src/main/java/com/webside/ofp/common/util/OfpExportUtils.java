package com.webside.ofp.common.util;

import java.io.File;
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

import jxl.SheetSettings;
import jxl.Workbook;
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
	private static final String QUOTATION_SHEET_TEMPLATE_PATH = "/template/quotation_sheet.xls";
	
	
	/**
	 * 导出
	 * 
	 * @param response
	 *            响应对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void exportQuotationSheet(HttpServletResponse response,QuotationSheetEntity QuotationSheet,String exportType)
			throws Exception {
		if(ExportType.EXCEL.name().equalsIgnoreCase(exportType)){
			exportQuotationSheetExcel(response,QuotationSheet);
		}else if(ExportType.PDF.name().equalsIgnoreCase(exportType)){
			
		}
	}
	
	public static void exportQuotationSheetExcel(HttpServletResponse response,QuotationSheetEntity quotationSheet) throws Exception {
		// 设置响应头
		response.setContentType("application/vnd.ms-excel");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename="
				+ quotationSheet.getQuotationSheetCode() + ".xls");
		// 获取输出流
		OutputStream outputStream = response.getOutputStream();
		// 定义Excel对象
		Workbook book = Workbook.getWorkbook(new File(QUOTATION_SHEET_TEMPLATE_PATH));
		
		// 定义Excel对象
		WritableWorkbook wwb = Workbook.createWorkbook(outputStream,book);
		// 获取Sheet页
		WritableSheet sheet = wwb.getSheet(0);
		
		// 定义表头字体样式、表格字体样式
		WritableFont headerFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
		WritableFont bodyFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9,
				WritableFont.NO_BOLD);
		WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
		WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
		// 设置表头样式：加边框、背景颜色为淡灰、居中样式
		headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		headerCellFormat.setBackground(Colour.PALE_BLUE);
		headerCellFormat.setAlignment(Alignment.CENTRE);
		headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		// 设置表格体样式：加边框、居中
		bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyCellFormat.setAlignment(Alignment.CENTRE);
		bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		//日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if(quotationSheet.getQuotationDate() != null){
			String date = sdf.format(quotationSheet.getQuotationDate());
			Label labelDate = new Label(1,2,date);
			sheet.addCell(labelDate);
		}
		
		if(quotationSheet.getCustomer() != null){
			Label labelCustomer = new Label(1,3,quotationSheet.getCustomer().getCustomerName());
			sheet.addCell(labelCustomer);
		}
		
		if(quotationSheet.getPayMode() != null){
			Label labelPayMode = new Label(4,4,quotationSheet.getPayMode());
			sheet.addCell(labelPayMode);
		}
		
		
		List<QuotationSubSheetEntity> subList = quotationSheet.getSubSheetList();
		int subStartRow = 8;
		for(int i=0;i<subList.size();i++){
			QuotationSubSheetEntity subSheet = subList.get(i);
			//产品编号
			Label itemNoHLabel = new Label(i,subStartRow,"Item No.",headerCellFormat);
			sheet.addCell(itemNoHLabel);
			Label itemNoLabel = new Label(i,subStartRow+1,subSheet.getProductId().toString(),bodyCellFormat);
			sheet.addCell(itemNoLabel);
			
			//缩略图
			Label photoHLabel = new Label(i,subStartRow,"Photo",headerCellFormat);
			sheet.addCell(photoHLabel);
			Label photoLabel = new Label(i,subStartRow,"Photo",headerCellFormat);
			sheet.addCell(photoLabel);
			
//			WritableImage image = new WritableImage(i,subStartRow+1,1,1);
		}
	}
	
	public static void exportQuotationSheetPdf(HttpServletResponse response,QuotationSheetEntity QuotationSheet) throws Exception {
		
	}
}
