package com.webside.ofp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.ofp.model.ProductEntity;
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
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductType(productTypeEntity);
		productEntity.setProductCode("20170720002");
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
		int size = productService.insert(productEntity);
		System.out.println("插入数据id：" + productEntity.getProductId());
    }
	
	@Test
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
	public void testFindById(){
		Long id = 1l;
		ProductTypeEntity productTypeEntity = productTypeService.findById(id);
		System.out.println("id为1的产品为：" + productTypeEntity.getCnName());
	}
	
	
	@Test
	public void testDeleteById(){
		Long id = 1l;
		int size = productTypeService.deleteById(id);
		System.out.println("删除id为1的产品：" + size);
	}*/
	
	@Test
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
	}
}
