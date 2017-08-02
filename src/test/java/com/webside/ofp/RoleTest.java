package com.webside.ofp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.role.service.RoleService;

public class RoleTest extends BaseJunit {
	
	@Autowired
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	/*@Test
	public void TestInserRoleProduct(){
		roleService.addRoleProductType(2, 10);
	}*/
	
	@Test
	public void TestInserRoleProductBatch(){
		List<Integer> productTypeIds = new ArrayList<Integer>();
		productTypeIds.add(10);
		productTypeIds.add(11);
		productTypeIds.add(12);
		productTypeIds.add(13);
		roleService.addRoleProductTypeBatch(1, productTypeIds);
	}
}
