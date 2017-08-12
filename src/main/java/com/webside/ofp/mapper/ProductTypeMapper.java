package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.ProductTypeEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeMapper extends BaseMapper<ProductTypeEntity, Long>{
	/**
	 * 根据父产品类型id查询父类下子产品
	 * @param parentId
	 * @return
	 */
	public List<ProductTypeEntity> findByParentId(int parentId);
}