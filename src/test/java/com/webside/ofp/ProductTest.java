package com.webside.ofp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.webside.base.BaseJunit;
import com.webside.ofp.common.util.ImageUtils;
import com.webside.ofp.common.util.QRCodeUtil;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ProductService;
import com.webside.ofp.service.ProductTypeService;

public class ProductTest extends BaseJunit {
	@Autowired
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	/*@Test
	public void testInsert() {
		
		ProductTypeEntity productTypeEntity = new ProductTypeEntity();
		productTypeEntity.setProductTypeId(10);
		ProductEntityWithBLOBs productEntity = new ProductEntityWithBLOBs();
		productEntity.setProductType(productTypeEntity);
		productEntity.setProductCode("20170727002");
		productEntity.setUnit("SET");
		productEntity.setCustomsCode("70133700");
		productEntity.setUsdPrice(0.65);
		productEntity.setCnName("9按压杯");
		productEntity.setEnName("GLASS CUP");
		productEntity.setVatRate(17.21d);
		productEntity.setBuyPrice(4.38);
		productEntity.setWeight(250);
		productEntity.setVolume(255);
		productEntity.setTop(65l);
		productEntity.setBottom(53l);
		productEntity.setHeight(137l);
		productEntity.setLength(47.3);
		productEntity.setWidth(26d);
		productEntity.setPackHeight(43.5);
		productEntity.setGw(19.5);
		productEntity.setPackingRate(12.3d);
		productEntity.setTaxRebateRate(13.2d);
		productEntity.setCbm(0.053);
		productEntity.setPacking("6 pos window box,12 sets/ctn");
		productEntity.setCreateUser(4);
		productEntity.setHdMapUrl("D:\\IMG_20141012_130853.jpg");;
		ByteOutputStream output = new ByteOutputStream();
		FileImageInputStream input = null;
		ByteOutputStream output2 = null;
		try {
			//二维码
			String content = "code:" + productEntity.getProductCode() + "| top:" + productEntity.getTop() + "| bottom:" + 
					productEntity.getBottom() + "| weight:" + productEntity.getWeight() + "| volume:" + productEntity.getVolume() + 
					"| packing:" + productEntity.getPackingRate() + "| cbm:" + productEntity.getCbm();
			QRCodeUtil.encode(content, output);
//			byte[] rqCodeByteStr = new byte[40960];
			productEntity.setQrCodePic(output.getBytes());
			
			//缩略图
			String hdMapUrl = productEntity.getHdMapUrl();
			String prefix = hdMapUrl.substring(0,hdMapUrl.lastIndexOf("."));
			String endfix = hdMapUrl.substring(hdMapUrl.lastIndexOf(".") + 1);
			String thumbnailUrl = prefix + "_thumbnail." + endfix;
			ImageUtils.scaleWithHeight(productEntity.getHdMapUrl(), thumbnailUrl, 100);
			input = new FileImageInputStream(new File(thumbnailUrl));
		    output2 = new ByteOutputStream();
	    	byte[] buf = new byte[1024];
	      	int numBytesRead = 0;
	      	while ((numBytesRead = input.read(buf)) != -1) {
	      		output2.write(buf, 0, numBytesRead);
	      	}
		    byte[] data =  output2.getBytes();
		    productEntity.setThumbnail(data);
		    int size = productService.insert(productEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(input != null){
					input.close();
				}
				if(output2 != null){
					output2.close();
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("插入数据id：" + productEntity.getProductId());
    }*/
	
	/*@Test
	public void testInsertWithBlobs() {
		ProductTypeEntity productTypeEntity = new ProductTypeEntity();
		productTypeEntity.setProductTypeId(10);
		ProductEntityWithBLOBs productEntity = new ProductEntityWithBLOBs();
		productEntity.setProductType(productTypeEntity);
		productEntity.setProductCode("20170817007");
		productEntity.setUnit("SET");
		productEntity.setCustomsCode("70133700");
		productEntity.setUsdPrice(0.65);
		productEntity.setCnName("21按压杯");
		productEntity.setEnName("GLASS CUP");
		productEntity.setVatRate(17.2123d);
		productEntity.setBuyPrice(4.38);
		productEntity.setWeight(250);
		productEntity.setVolume(255);
		productEntity.setTop(65l);
		productEntity.setBottom(53l);
		productEntity.setHeight(137l);
		productEntity.setLength(47.3);
		productEntity.setWidth(26d);
		productEntity.setPackHeight(43.5);
		productEntity.setGw(19.5);
		productEntity.setPackingRate(12.3d);
		productEntity.setTaxRebateRate(13.2d);
		productEntity.setCbm(0.053);
		productEntity.setPacking("6 pos window box,12 sets/ctn");
		productEntity.setCreateUser(4);
		productEntity.setHdMapUrl("D:\\IMG_20141012_130853.jpg");;
		String basePath = "C:\\Users\\Administrator\\git\\ofp\\src\\main\\webapp";
		productService.insertWithBlobs(productEntity, basePath);
		System.out.println("插入数据id：" + productEntity.getProductId());
    }*/
	
	/*@Test
	public void testUpdate(){
		ProductTypeEntity productTypeEntity = new ProductTypeEntity();
		productTypeEntity.setProductTypeId(1);
		productTypeEntity.setCnName("杯子");
		productTypeEntity.setEnName("glass");
		productTypeEntity.setParentId(0);
		productTypeEntity.setLevel(1);
		productTypeEntity.setOrderby(2);
		productTypeEntity.setModifyUser(4);
		int size = productTypeService.update(productTypeEntity);
		System.out.println("修改产品类型信息成功！");
	}
	
	@Test
	public void testDeleteById(){
		Long id = 1l;
		int size = productTypeService.deleteById(id);
		System.out.println("删除id为1的产品：" + size);
	}*/
	
	@Test
	public void testFindByIdWithBlobs(){
		Integer id = 34;
		ProductEntityWithBLOBs productEntity  = productService.findByIdWithBLOBS(id);
		System.out.println("分页查询返回对象：" + productEntity.toString());
		if(productEntity.getQrCodePic() != null){
			byte[] data = productEntity.getQrCodePic();
//			byte[] data = productEntity.getThumbnail();
			if(data != null){
				InputStream in = new ByteArrayInputStream(data);
//				String targetPath = "D:/" + productEntity.getProductCode() + "_thumbnail.jpg";
				String targetPath = "D:/" + productEntity.getProductCode() + "_qrcode.jpg";
				File file = new File(targetPath);
				String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
				if(!file.exists()){
					new File(path).mkdir();
				}
				OutputStream fos = null;
				try {
					fos = new FileOutputStream(file);
					int len = 0;
					byte[] buf = new byte[1024];
					while ((len = in.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					fos.flush();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				} finally{
					try {
						in.close();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	/*@Test
	public void testQueryListByPage(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("start",0);
		parameters.put("pageNumber",20);
		parameters.put("cnName","9按压");
		List<ProductEntity> list = productService.queryListByPage(parameters);
		for(ProductEntity productEntity:list){
			System.out.println("分页查询返回对象：" + productEntity.toString());
		}
	}
	
	@Test
	public void testQueryListAll(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("enName","glass");
		List<ProductEntity> list = productService.queryListAll(parameters);
		for(ProductEntity productEntity:list){
			System.out.println("查询返回对象：" + productEntity.toString());
		}
	}*/
}
