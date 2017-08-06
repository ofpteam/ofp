package com.webside.ofp.service;

import java.util.List;
import java.util.Map;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;

public interface ProductService extends BaseService<ProductEntity, Long> {
	/**
	 * 根据产品类型查找产品
	 * 
	 * @param id
	 *            产品类型id
	 * @return
	 */
	public List<ProductEntity> findByTypeId(String id);

	public ProductEntityWithBLOBs findByIdWithBLOBS(long id);

	public int insertWithBlobs(ProductEntityWithBLOBs productEntityWithBLOBs, String basePath);

	/**
	 * 报价单查询页面
	 */
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);

}
