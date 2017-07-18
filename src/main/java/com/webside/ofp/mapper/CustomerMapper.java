package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.CustomerEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<CustomerEntity, Long>{
    
}