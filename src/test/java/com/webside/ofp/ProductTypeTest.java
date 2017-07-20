package com.webside.ofp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ProductTypeService;

public class ProductTypeTest extends BaseJunit {
	@Autowired
	private ProductTypeService productTypeService;
	
	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	@Test
	public void testInsert() {
		ProductTypeEntity productTypeEntity = new ProductTypeEntity();
		productTypeEntity.setCnName("吹杯");
		productTypeEntity.setEnName("GLASS");
		productTypeEntity.setParentId(6);
		productTypeEntity.setLevel(0);
		productTypeEntity.setOrderby(1);
		productTypeEntity.setCreateUser(4);
		productTypeEntity.setDescription("吹杯");
		int size = productTypeService.insert(productTypeEntity);
		System.out.println("插入数据id：" + productTypeEntity.getProductTypeId());
    }
	
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
	}
	
	@Test
	public void testQueryListByPage(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("start",1);
		parameters.put("pageNumber",20);
		parameters.put("cnName","杯子");
		List<ProductTypeEntity> list = productTypeService.queryListByPage(parameters);
		for(ProductTypeEntity productTypeEntity:list){
			System.out.println("分页查询返回对象：" + productTypeEntity.toString());
		}
	}
	
	@Test
	public void testQueryListAll(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("enName","glass");
		List<ProductTypeEntity> list = productTypeService.queryListAll(parameters);
		for(ProductTypeEntity productTypeEntity:list){
			System.out.println("查询返回对象：" + productTypeEntity.toString());
		}
	}*/
}
