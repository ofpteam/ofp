package com.webside.ofp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.mapper.CustomerMapper;
import com.webside.ofp.model.CustomerEntity;
import com.webside.ofp.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl extends AbstractService<CustomerEntity, Long> implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(customerMapper);
	}

}
