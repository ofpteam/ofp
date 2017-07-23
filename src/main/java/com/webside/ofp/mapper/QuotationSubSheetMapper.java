package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.QuotationSubSheetEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationSubSheetMapper extends BaseMapper<QuotationSubSheetEntity, Long>{
	public List<QuotationSubSheetEntity> findBySheetId(@Param("sheetId")int sheetId);
	public int deleteBySheetId(@Param("sheetId")int sheetId);
	public int deleteBySheetIdPhysical(@Param("sheetId")int sheetId);
}