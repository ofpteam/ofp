package com.webside.ofp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

	public int updateWithBlobs(ProductEntityWithBLOBs productEntity, String basePath);

	/**
	 * 报价单查询页面
	 */
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);

	public List<ProductEntityWithBLOBs> findByIdsWithBLOBS(List<Integer> productIds);
	
	/**
	 * 删除附件
	 */
	public void deleteAttachmentsByProductId(Integer productId);

	/**
	 * 批量添加附件（步骤：先删除，后批量插入）
	 * 
	 * @param fileName
	 * @return
	 */
	public int insertAttachments(Map<String, Object> parameter);
}
