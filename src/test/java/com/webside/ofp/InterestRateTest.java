package com.webside.ofp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.ofp.model.InterestRateEntity;
import com.webside.ofp.service.InterestRateService;

public class InterestRateTest extends BaseJunit {
	
	@Autowired
	private InterestRateService interestRateService;
	
	public void setCustomerService(InterestRateService interestRateService) {
		this.interestRateService = interestRateService;
	}

	/*@Test
	public void TestInsert(){
		InterestRateEntity interestRateEntity = new InterestRateEntity();
		interestRateEntity.setRate(2l);
		interestRateService.insert(interestRateEntity);
	}*/
	
	@Test
	public void TestUpdate(){
		InterestRateEntity interestRateEntity = new InterestRateEntity();
		interestRateEntity.setInterestRateId(2);
		interestRateEntity.setRate((double) 3l);
		interestRateService.update(interestRateEntity);
	}
}
