package com.webside.ofp.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.webside.dtgrid.model.Column;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.dtgrid.util.GridUtils;
import com.webside.enums.ExportType;
import com.webside.ofp.common.config.OfpConfig;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;
import com.webside.ofp.service.ProductTypeService;

import jxl.CellView;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PaperSize;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Component
public class OfpExportUtils extends ExportUtils{
	//报价单excel模板名称
	private static final String QUOTATION_SHEET_TEMPLATE_PATH = "\\quotation_sheet.xls";
	
	//老的报价单excel模板名称
	private static final String OLD_QUOTATION_SHEET_TEMPLATE_PATH = "\\quotation_sheet2.xls";
	
	public static Logger logger = LoggerFactory.getLogger(OfpExportUtils.class);
	
	private static ProductTypeService productTypeService;
	
	@Autowired(required = true)
	public void setProductTypeService(ProductTypeService productTypeService) {
		OfpExportUtils.productTypeService = productTypeService;
	}

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
			exportQuotationSheetExcel(response,QuotationSheet,basePath,1);
		}else if(ExportType.PDF.name().equalsIgnoreCase(exportType)){
			exportQuotationSheetPdf(response,QuotationSheet,basePath);
		}else if(ExportType.OLDEXCEL.name().equalsIgnoreCase(exportType)){
			exportQuotationSheetExcel(response,QuotationSheet,basePath,2);
		}
	}
	
	public static void exportQuotationSheetExcel(HttpServletResponse response,QuotationSheetEntity quotationSheet,String basePath,int excelType) throws Exception {
		
		// 设置响应头
		response.setContentType("application/vnd.ms-excel");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename="
				+ (quotationSheet.getQuotationSheetCode()+System.currentTimeMillis()) + ".xls");
		// 获取输出流
		OutputStream outputStream = response.getOutputStream();
		if(excelType == 1){
			exportQuotationSheetExcel(outputStream,quotationSheet,basePath);
		}else{
			exportOldQuotationSheetExcel(outputStream,quotationSheet,basePath);
		}
	}
	
	/**
	 * 批量导出二维码Excel
	 * @param response
	 * @param products 产品集合
	 */
	public static void exportQrCodeExcel(HttpServletResponse response,List<ProductEntityWithBLOBs> products) throws Exception {
		// 设置响应头
		response.setContentType("application/vnd.ms-excel");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename=productsQrCode"
				+ (System.currentTimeMillis()) + ".xls");
		// 获取输出流
		OutputStream outputStream = response.getOutputStream();
		exportQrCodeExcel(outputStream,products);
	}
	
	
	public static void exportQrCodeExcel(OutputStream outputStream,List<ProductEntityWithBLOBs> products)  throws Exception {
		logger.info("批量导出二维码excel 开始");
		// 定义Excel对象
		WritableWorkbook book = Workbook.createWorkbook(outputStream);
		// 创建Sheet页
		WritableSheet sheet = book.createSheet("二维码", 0);
		// 冻结表头
		SheetSettings settings = sheet.getSettings();
		settings.setVerticalFreeze(1);
		
		// 定义表头字体样式、表格字体样式
		WritableFont headerFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
		WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
		
		// 设置表头样式：加边框、背景颜色为淡灰、居中样式
//		headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//		headerCellFormat.setBackground(Colour.PALE_BLUE);
		headerCellFormat.setAlignment(Alignment.CENTRE);
		headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// 定义表头字体样式、表格字体样式
		WritableFont spaceFont = new WritableFont(
					WritableFont.createFont("Lucida Grande"), 3, WritableFont.NO_BOLD);
		WritableCellFormat spaceCellFormat = new WritableCellFormat(spaceFont);
		
		int startRow = 1;
		int startColumn = 1;
		for(int i=0;i<products.size();i++){
			//每行3个
			int column = i%3;
			if(column == 0 && i != 0){
				startColumn = 1;
				startRow += 3;
			}
			ProductEntityWithBLOBs product = products.get(i);
			sheet.setRowView(startRow, 1500, false); //设置行高
			sheet.setColumnView(startColumn, 14); //设置列宽
			if(product.getQrCodePic() != null){
				//缩略图
				WritableImage image = new WritableImage(startColumn+0.05,startRow+0.05,0.9,0.9,product.getQrCodePic());
				sheet.addImage(image);
				sheet.addCell(new Label(startColumn,startRow,"",headerCellFormat));
			}
			Label labelCustomer = new Label(startColumn,startRow+1,product.getProductCode(),headerCellFormat);
			sheet.addCell(labelCustomer);
			
			sheet.setColumnView(startColumn+1, 3); //设置列宽
			Label labelSpace = new Label(startColumn+1,startRow,"",spaceCellFormat);
			sheet.addCell(labelSpace);
			
			sheet.setRowView(startRow+2, 500, false); //设置行高
			startColumn += 2;
		}
		
		// 写入Excel工作表
		book.write();
		// 关闭Excel工作薄对象
		book.close();
		// 关闭流
		outputStream.flush();
		outputStream.close();
		outputStream = null;
		logger.info("批量导出二维码excel 结束");
	}
	
	
	public static void exportProductTags(OutputStream outputStream,List<ProductEntityWithBLOBs> products)  throws Exception {
		logger.info("批量导出产品标签excel 开始");
		// 定义Excel对象
		WritableWorkbook book = Workbook.createWorkbook(outputStream);
		// 创建Sheet页
		WritableSheet sheet = book.createSheet("标签", 0);
	
		// 定义表头字体样式、表格字体样式
		WritableFont headerFont = new WritableFont(
				WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
		WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
		
		// 设置表头样式：加边框、背景颜色为淡灰、居中样式
//		headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//		headerCellFormat.setBackground(Colour.PALE_BLUE);
		headerCellFormat.setAlignment(Alignment.CENTRE);
		headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// 定义表头字体样式、表格字体样式
		WritableFont spaceFont = new WritableFont(
					WritableFont.createFont("Lucida Grande"), 3, WritableFont.NO_BOLD);
		WritableCellFormat spaceCellFormat = new WritableCellFormat(spaceFont);
		
		int startRow = 1;
		int startColumn = 1;
		for(int i=0;i<products.size();i++){
			//每行3个
			int column = i%3;
			if(column == 0 && i != 0){
				startColumn = 1;
				startRow += 3;
			}
			ProductEntityWithBLOBs product = products.get(i);
			sheet.setRowView(startRow, 1500, false); //设置行高
			sheet.setColumnView(startColumn, 14); //设置列宽
			if(product.getQrCodePic() != null){
				//缩略图
				WritableImage image = new WritableImage(startColumn,startRow,1,1,product.getQrCodePic());
				sheet.addImage(image);
			}
			Label labelCustomer = new Label(startColumn,startRow+1,product.getProductCode(),headerCellFormat);
			sheet.addCell(labelCustomer);
			
			sheet.setColumnView(startColumn+1, 3); //设置列宽
			Label labelSpace = new Label(startColumn+1,startRow,"",spaceCellFormat);
			sheet.addCell(labelSpace);
			
			sheet.setRowView(startRow+2, 500, false); //设置行高
			startColumn += 2;
		}
		
		// 写入Excel工作表
		book.write();
		// 关闭Excel工作薄对象
		book.close();
		// 关闭流
		outputStream.flush();
		outputStream.close();
		outputStream = null;
		logger.info("批量导出产品标签excel 结束");
	}
	
	
	/**
	 * 导出老的报价单格式
	 * @param outputStream
	 * @param quotationSheet
	 * @param basePath
	 * @throws Exception
	 */
	public static void exportOldQuotationSheetExcel(OutputStream outputStream,QuotationSheetEntity quotationSheet,String basePath) throws Exception {
		logger.info("导出老的excel 形式报价单开始");
		// 定义Excel对象
		Workbook book = Workbook.getWorkbook(new File(basePath+OLD_QUOTATION_SHEET_TEMPLATE_PATH));
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(quotationSheet.getQuotationDate() != null){
			String date = sdf.format(quotationSheet.getQuotationDate());
			Label labelDate = new Label(2,2,date,headerCellFormat2);
			sheet.addCell(labelDate);
		}
		
		if(quotationSheet.getCustomer() != null){
			Label labelCustomer = new Label(2,3,quotationSheet.getCustomer().getCustomerName(),headerCellFormat2);
			sheet.addCell(labelCustomer);
		}
		
		if(quotationSheet.getPriceTerms() != null){
			Label labelPriceTerms = new Label(6,6,quotationSheet.getPriceTerms(),headerCellFormat2);
			sheet.addCell(labelPriceTerms);
		}
		
		if(quotationSheet.getResource() != null){
			Label labelResource = new Label(6,7,quotationSheet.getResource(),headerCellFormat2);
			sheet.addCell(labelResource);
		}
		
		if(quotationSheet.getExpirationDate() != 0){
			Label labelExpirationDate = new Label(11,6,quotationSheet.getExpirationDate()+" DAYS",headerCellFormat2);
			sheet.addCell(labelExpirationDate);
		}
		
		if(quotationSheet.getDeliveryDate() != 0){
			Label labelDeliveryDate = new Label(11,7,quotationSheet.getDeliveryDate()+" DAYS",headerCellFormat2);
			sheet.addCell(labelDeliveryDate);
		}
		
		List<QuotationSubSheetEntity> subList = quotationSheet.getSubSheetList();
		int subStartRow = 12;
		for(int i=0;i<subList.size();i++){
			QuotationSubSheetEntity subSheet = subList.get(i);
			int currentRow = subStartRow + i;
			
		    sheet.setRowView(currentRow, 1800, false); //设置行高
		    
			//产品编号
			Label itemNoLabel = new Label(0,currentRow,subSheet.getProduct().getProductCode(),bodyCellFormat);
			sheet.addCell(itemNoLabel);
			
			//缩略图
			//x.y是图片相对于当前cell的位置，width和height是图片高宽
			WritableImage image = new WritableImage(1+0.05,currentRow+0.05,0.9,0.9,subSheet.getProduct().getThumbnail());
			sheet.addImage(image);
			sheet.addCell(new Label(1,currentRow,"",bodyCellFormat));
			
			//货描
			/*Label descHLabel = new Label(2,subStartRow,"Description",headerCellFormat);
			sheet.addCell(descHLabel);
			Label descLabel = new Label(2,subStartRow+1,subSheet.getPacking(),bodyCellFormat);
			sheet.addCell(descLabel);*/
			
			//美金单价
			jxl.write.Number priceLabel = new jxl.write.Number(2,currentRow,subSheet.getUsdPrice(),bodyCellFormat);
			sheet.addCell(priceLabel);
			
			//单位
			Label unitLabel = new Label(3,currentRow,subSheet.getUnit(),bodyCellFormat);
			sheet.addCell(unitLabel);
			
			//top
			jxl.write.Number topLabel = new jxl.write.Number(4,currentRow,subSheet.getTop(),bodyCellFormat);
			sheet.addCell(topLabel);
			
			//bottom
			jxl.write.Number bottomLabel = new jxl.write.Number(5,currentRow,subSheet.getBottom(),bodyCellFormat);
			sheet.addCell(bottomLabel);
			
			//height
			jxl.write.Number heightLabel = new jxl.write.Number(6,currentRow,subSheet.getHeight(),bodyCellFormat);
			sheet.addCell(heightLabel);
			
			//weight
			jxl.write.Number weightLabel = new jxl.write.Number(7,currentRow,subSheet.getWeight(),bodyCellFormat);
			sheet.addCell(weightLabel);
			
			//volume
			jxl.write.Number volumeLabel = new jxl.write.Number(8,currentRow,subSheet.getVolume(),bodyCellFormat);
			sheet.addCell(volumeLabel);
			
			//packing
			Label packingLabel = new Label(9,currentRow,subSheet.getPacking(),bodyCellFormat);
			sheet.addCell(packingLabel);
			
			//packing rate
			jxl.write.Number packingRateLabel = new jxl.write.Number(10,currentRow,subSheet.getPackingRate(),bodyCellFormat);
			sheet.addCell(packingRateLabel);
			
			//number
			jxl.write.Number numberLabel = new jxl.write.Number(11,currentRow,subSheet.getNumber(),bodyCellFormat);
			sheet.addCell(numberLabel);
			
			//pack number
			jxl.write.Number packNumberLabel = new jxl.write.Number(12,currentRow,subSheet.getPackNum(),bodyCellFormat);
			sheet.addCell(packNumberLabel);
			
			//cbm
			jxl.write.Number cbmLabel = new jxl.write.Number(13,currentRow,subSheet.getProduct().getCbm(),bodyCellFormat);
			sheet.addCell(cbmLabel);
			
			//total cbm
			jxl.write.Number totalCbmLabel = new jxl.write.Number(14,currentRow,subSheet.getTotalcbm(),bodyCellFormat);
			sheet.addCell(totalCbmLabel);
			
			//gw
			jxl.write.Number gwLabel = new jxl.write.Number(15,currentRow,subSheet.getGw(),bodyCellFormat);
			sheet.addCell(gwLabel);
			
			//total gw
			jxl.write.Number totalGwLabel = new jxl.write.Number(16,currentRow,subSheet.getTotalGw(),bodyCellFormat);
			sheet.addCell(totalGwLabel);
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
		logger.info("导出老的excel 形式报价单结束");
	}
	
	/**
	 * 批量导出产品Excel
	 * @param response
	 * @param products 产品集合
	 */
	public static void exportProductExcel(HttpServletResponse response,List<ProductEntityWithBLOBs> products) throws Exception {
		// 设置响应头
		response.setContentType("application/vnd.ms-excel");
		// 执行文件写入
		response.setHeader("Content-Disposition", "attachment;filename=products"
				+ (System.currentTimeMillis()) + ".xls");
		// 获取输出流
		OutputStream outputStream = response.getOutputStream();
		String[] exportColumns = new String[]{"商品编码","工厂编码","商品大类（一级）","商品大类（二级）","英文名称","中文名称","海关编码","单位","美金单价",
				"收购单价","增值税率","退税率","Top(mm)","Bottom(mm)","Height(mm)","G.W.","外箱长","外箱宽","外箱高","CBM","容量(ml)",
				"单品重量(g)","装箱率","Description"};
		exportProductExcel(outputStream,products,exportColumns);
	}
	
	/**
	 * 批量导出产品excel
	 * @param outputStream
	 * @param quotationSheet
	 * @param basePath
	 * @throws Exception
	 */
	public static void exportProductExcel(OutputStream outputStream,List<ProductEntityWithBLOBs> products,String[] exportColumns) throws Exception {
		logger.info("导出产品excel开始");
		// 定义Excel对象
		WritableWorkbook book = Workbook.createWorkbook(outputStream);
		// 创建Sheet页
		WritableSheet sheet = book.createSheet("数据", 0);
		sheet.getSettings().setSelected(true);
		
		// 冻结表头
		SheetSettings settings = sheet.getSettings();
		settings.setVerticalFreeze(1);
		
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
		
		if(exportColumns != null){
			int seq = 0;
			for (String column : exportColumns) {
				sheet.addCell(new Label(seq, 0, column,
						headerCellFormat));
				seq++;
			}
			for(int i = 0; i < products.size(); i++){
				ProductEntityWithBLOBs product = products.get(i);
				sheet.addCell(new Label(0, i + 1, product.getProductCode(),bodyCellFormat));
				sheet.addCell(new Label(1, i + 1, product.getFactoryCode(),bodyCellFormat));
				if(product.getProductType().getParentId() != null){
					ProductTypeEntity parentType = productTypeService.findById((long)product.getProductType().getParentId());
					sheet.addCell(new Label(2, i + 1, parentType.getCnName(),bodyCellFormat));
				}
				sheet.addCell(new Label(3, i + 1, product.getProductType().getCnName()+"",bodyCellFormat));
				sheet.addCell(new Label(4, i + 1, product.getEnName(),bodyCellFormat));
				sheet.addCell(new Label(5, i + 1, product.getCnName(),bodyCellFormat));
				sheet.addCell(new Label(6, i + 1, product.getCustomsCode(),bodyCellFormat));
				sheet.addCell(new Label(7, i + 1, product.getUnit(),bodyCellFormat));
				sheet.addCell(new Label(8, i + 1, product.getUsdPrice()+"",bodyCellFormat));
				sheet.addCell(new Label(9, i + 1, product.getBuyPrice()+"",bodyCellFormat));
				sheet.addCell(new Label(10, i + 1, product.getVatRate()+"",bodyCellFormat));
				sheet.addCell(new Label(11, i + 1, product.getTaxRebateRate()+"",bodyCellFormat));
				sheet.addCell(new Label(12, i + 1, product.getTop()+"",bodyCellFormat));
				sheet.addCell(new Label(13, i + 1, product.getBottom()+"",bodyCellFormat));
				sheet.addCell(new Label(14, i + 1, product.getHeight()+"",bodyCellFormat));
				sheet.addCell(new Label(15, i + 1, product.getGw()+"",bodyCellFormat));
				sheet.addCell(new Label(16, i + 1, product.getLength()+"",bodyCellFormat));
				sheet.addCell(new Label(17, i + 1, product.getWidth()+"",bodyCellFormat));
				sheet.addCell(new Label(18, i + 1, product.getPackHeight()+"",bodyCellFormat));
				sheet.addCell(new Label(19, i + 1, product.getCbm()+"",bodyCellFormat));
				sheet.addCell(new Label(20, i + 1, product.getVolume()+"",bodyCellFormat));
				sheet.addCell(new Label(21, i + 1, product.getWeight()+"",bodyCellFormat));
				sheet.addCell(new Label(22, i + 1, product.getPackingRate()+"",bodyCellFormat));
				if(product.getDescription() != null){
					sheet.addCell(new Label(23, i + 1, product.getDescription()+"",bodyCellFormat));
				}
			}
		}
		
		// 写入Excel工作表
		book.write();
		// 关闭Excel工作薄对象
		book.close();
		// 关闭流
		outputStream.flush();
		outputStream.close();
		outputStream = null;
		logger.info("导出产品excel结束");
	}
	
	
	/**
	 * 导出新的报价单格式
	 * @param outputStream
	 * @param quotationSheet
	 * @param basePath
	 * @throws Exception
	 */
	public static void exportQuotationSheetExcel(OutputStream outputStream,QuotationSheetEntity quotationSheet,String basePath) throws Exception {
		logger.info("导出excel 形式报价单开始");
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(quotationSheet.getQuotationDate() != null){
			String date = sdf.format(quotationSheet.getQuotationDate());
			Label labelDate = new Label(1,2,date,headerCellFormat2);
			sheet.addCell(labelDate);
		}
		
		if(quotationSheet.getCustomer() != null){
			Label labelCustomer = new Label(1,3,quotationSheet.getCustomer().getCustomerName(),headerCellFormat2);
			sheet.addCell(labelCustomer);
		}
		
		if(quotationSheet.getExpirationDate() != 0){
			Label labelExpirationDate = new Label(5,2,quotationSheet.getExpirationDate()+" DAYS",headerCellFormat2);
			sheet.addCell(labelExpirationDate);
		}
		
		if(quotationSheet.getDeliveryDate() != 0){
			Label labelDeliveryDate = new Label(5,5,quotationSheet.getDeliveryDate()+" DAYS",headerCellFormat2);
			sheet.addCell(labelDeliveryDate);
		}
		
		if(quotationSheet.getPayMode() != null){
			Label labelPayMode = new Label(5,4,quotationSheet.getPayMode(),headerCellFormat2);
			sheet.addCell(labelPayMode);
		}
		
		
		List<QuotationSubSheetEntity> subList = quotationSheet.getSubSheetList();
		int subStartRow = 8;
		for(int i=0;i<subList.size();i++){
			QuotationSubSheetEntity subSheet = subList.get(i);
			
		    sheet.setRowView(subStartRow+1, 1800, false); //设置行高
		    
			//产品编号
			Label itemNoHLabel = new Label(0,subStartRow,"Item No.",headerCellFormat);
			sheet.addCell(itemNoHLabel);
			Label itemNoLabel = new Label(0,subStartRow+1,subSheet.getProduct().getProductCode(),bodyCellFormat);
			sheet.addCell(itemNoLabel);
			
			//缩略图
			Label photoHLabel = new Label(1,subStartRow,"Photo",headerCellFormat);
			sheet.addCell(photoHLabel);
			WritableImage image = new WritableImage(1+0.05,subStartRow+1+0.05,0.9,0.9,subSheet.getProduct().getThumbnail());
			sheet.addImage(image);
			sheet.addCell(new Label(1,subStartRow+1,"",bodyCellFormat));
			
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
		logger.info("导出excel 形式报价单结束");
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
		OutputStream outputStreamResponse = response.getOutputStream();
		exportQuotationSheetPdf(outputStreamResponse,quotationSheet,basePath,fileName);
	}
	
	/**
	 * 导出pdf，这里先导出excel，然后使用jacob 把excel转为pdf
	 * @param response
	 * @param quotationSheet
	 * @param basePath
	 * @throws Exception
	 */
	public static void exportQuotationSheetPdf(OutputStream outputStream,QuotationSheetEntity quotationSheet,String basePath,String fileName) throws Exception {
		logger.info("导出pdf形式报价单开始");
		String tempPath = OfpConfig.exportTempPath;
		logger.info("tempPath:" + tempPath);
		File file = new File(tempPath);
		if(!file.exists()){
			file.mkdirs();
		}
		String excelPath = tempPath+"\\"+fileName+".xls";
		String pdfPath = tempPath+"\\"+fileName+".pdf";
		//导出excel到指定路径
		OutputStream os = new FileOutputStream(new File(excelPath));
		exportQuotationSheetExcel(os,quotationSheet,basePath);
		
		excel2Pdf(excelPath,pdfPath);
		
		FileInputStream fis = new FileInputStream(new File(pdfPath));
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buffer = new byte[1024];
		int i = bis.read(buffer);
		while (i != -1) {
			outputStream.write(buffer, 0, i);
			i = bis.read(buffer);
		}
		
		bis.close();
		fis.close();
		os.close();
		outputStream.close();
		logger.info("导出pdf形式报价单结束");
	}
	
	public static void excel2Pdf(String excelPath,String pdfPath){
		logger.info("excel转换为pdf开始");
        ActiveXComponent ax = new ActiveXComponent("Excel.Application");
        logger.info("ax:" + ax.toString());
        try {
//        	ax.setProperty("ActivePrinter", new Variant("Microsoft XPS Document Writer"));
            ax.setProperty("Visible",new Variant(false));
            ax.setProperty("AutomationSecurity", new Variant(3)); //禁用宏  
            logger.info("2");
            Dispatch excels=ax.getProperty("Workbooks").toDispatch();
            logger.info("excels:"+excels.toString());
            Dispatch excel=Dispatch.invoke(excels,"Open",Dispatch.Method,new Object[]{  
            	excelPath,
                new Variant(false),  
                new Variant(false)
            },  
            new int[9]).toDispatch();
            logger.info("Open:"+excel.toString());
          /*  
            Dispatch currentSheet = Dispatch.get((Dispatch) excel,
                    "ActiveSheet").toDispatch();


            Dispatch pageSetup = Dispatch.get(currentSheet, "PageSetup")
                    .toDispatch();
            Dispatch.put(pageSetup, "Orientation", new Variant(2));
            Dispatch.put(pageSetup, "Zoom", false); // 值为100或false
//            Dispatch.put(pageSetup, "PaperSize",new Variant("xlPaperA4"));
            Dispatch.put(pageSetup, "FitToPagesWide", 1); // 所有列为一页(1或false)
*/           
            //转换格式  
            Dispatch.invoke(excel,"ExportAsFixedFormat",Dispatch.Method,new Object[]{  
                new Variant(0), //PDF格式=0  
                pdfPath,  
                new Variant(0)  //0=标准 (生成的PDF图片不会变模糊) 1=最小文件 (生成的PDF图片糊的一塌糊涂)  
            },new int[1]);
            logger.info("ExportAsFixedFormat:"+excel.toString());
            Dispatch.call(excel, "Close",new Variant(false));  
            logger.info("Close:"+excel.toString());
            if(ax!=null){  
                ax.invoke("Quit",new Variant[]{});  
                ax=null;  
            }
            logger.info("quit");
            ComThread.Release();  
        } catch (Exception e) {
            logger.error("excel转pdf时异常,",e);    
        }finally {
        	logger.info("excel转换为pdf结束");
            if (ax != null){    
                ax.invoke("Quit", new Variant[] {});
            }    
        }
	}
	
	
	public static void main(String[] args){
		System.out.println("d:" + 6%5);
	}
}
