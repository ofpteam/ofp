package com.webside.ofp;

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
		customerEntity.setCustomerName("tom");
		customerEntity.setTelephone("138212312354");
		customerEntity.setCountry("china");
		customerEntity.setCreateUser(4);
		customerEntity.setDescription("测试插入3");
		int size = customerService.insert(customerEntity);
		System.out.println("插入数据id：" + customerEntity.getCustomerId());
    }
	
	@Test
	public void testUpdate(){
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(3);
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
		Long id = 3l;
		CustomerEntity customerEntity = customerService.findById(id);
		System.out.println("id为3的用户为：" + customerEntity.getCustomerName());
	}
	*/
	
	@Test
	public void testDeleteById(){
		Long id = 3l;
		int size = customerService.deleteById(id);
		System.out.println("删除id为3的用户数：" + size);
	}
}
