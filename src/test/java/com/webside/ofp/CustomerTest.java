package com.webside.ofp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.ofp.model.CustomerEntity;
import com.webside.ofp.service.CustomerService;

public class CustomerTest extends BaseJunit {
	
	@Autowired
	private CustomerService customerService;
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/*@Test
	public void testInsert() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerName("amey");
		customerEntity.setTelephone("1283212122");
		customerEntity.setCountry("china");
		customerEntity.setCreateUser(4);
		customerEntity.setDescription("测试插入3");
		int size = customerService.insert(customerEntity);
		System.out.println("插入数据id：" + customerEntity.getCustomerId());
    }
	
	@Test
	public void testUpdate(){
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(1);
		customerEntity.setCustomerName("tom");
		customerEntity.setTelephone("13821231254");
		customerEntity.setCountry("USA");
		customerEntity.setModifyUser(22);
		customerEntity.setDescription("测试插入3");
		int size = customerService.update(customerEntity);
		System.out.println("修改客户信息成功！");
	}
	
	@Test
	public void testFindById(){
		Long id = 1l;
		CustomerEntity customerEntity = customerService.findById(id);
		System.out.println("id为1的用户为：" + customerEntity.getCustomerName());
	}
	
	
	@Test
	public void testDeleteById(){
		Long id = 1l;
		int size = customerService.deleteById(id);
		System.out.println("删除id为3的用户数：" + size);
	}*/
	
	@Test
	public void testQueryListByPage(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("start",1);
		parameters.put("pageNumber",20);
		parameters.put("customerName","ja");
		List<CustomerEntity> list = customerService.queryListByPage(parameters);
		for(CustomerEntity customerEntity:list){
			System.out.println("分页查询返回对象：" + customerEntity.toString());
		}
	}
	
	@Test
	public void testQueryListAll(){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("customerName","ame");
		List<CustomerEntity> list = customerService.queryListAll(parameters);
		for(CustomerEntity customerEntity:list){
			System.out.println("查询返回对象：" + customerEntity.toString());
		}
	}
}
