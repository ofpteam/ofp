package com.webside.ofp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OfpConfig {
	public static String exportTempPath;

	
	@Value("${export.temporary.path}")
	public void setExportTempPath(String exportTempPath) {
		OfpConfig.exportTempPath = exportTempPath;
	}
}
