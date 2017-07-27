package com.webside.ofp.service.impl;

import java.io.File;

import javax.imageio.stream.FileImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.common.util.ImageUtils;
import com.webside.ofp.common.util.QRCodeUtil;
import com.webside.ofp.mapper.ProductMapper;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.service.ProductService;

@Service("productService")
public class ProductServiceImpl extends AbstractService<ProductEntity, Long> implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	public static final int THUMBNAIL_DEFAULT_HEIGHT = 100;
	
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(productMapper);
	}

	@Override
	public ProductEntity findByTypeId(String id) {
		return productMapper.findByTypeId(id);
	}
	
	public ProductEntityWithBLOBs findByIdWithBLOBS(String id){
		return productMapper.findByIdWithBLOBS(id);
	}

	@Override
	public int insertWithBlobs(ProductEntityWithBLOBs productEntity) {
		ByteOutputStream output = new ByteOutputStream();
		FileImageInputStream input = null;
		ByteOutputStream output2 = null;
		try {
			//二维码
			String content = productEntity.getProductCode() + "|" + productEntity.getTop() + "|" + productEntity.getBottom() + 
					productEntity.getWeight() + "|" + productEntity.getVolume() + "|" + productEntity.getPackingRate() + "|" + 
					productEntity.getCbm();
			QRCodeUtil.encode(content, output);
			productEntity.setQrCodePic(output.getBytes());
			
			//缩略图
			String hdMapUrl = productEntity.getHdMapUrl();
			String prefix = hdMapUrl.substring(0,hdMapUrl.lastIndexOf("."));
			String endfix = hdMapUrl.substring(hdMapUrl.lastIndexOf(".") + 1);
			String thumbnailUrl = prefix + "_thumbnail." + endfix;
			//生成固定高度缩略图，默认为100
			ImageUtils.scaleWithHeight(productEntity.getHdMapUrl(), thumbnailUrl, THUMBNAIL_DEFAULT_HEIGHT);
			input = new FileImageInputStream(new File(thumbnailUrl));
		    output2 = new ByteOutputStream();
	    	byte[] buf = new byte[1024];
	      	int numBytesRead = 0;
	      	while ((numBytesRead = input.read(buf)) != -1) {
	      		output2.write(buf, 0, numBytesRead);
	      	}
		    byte[] data =  output2.getBytes();
		    productEntity.setThumbnail(data);
			
			int size = this.insert(productEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(input != null){
					input.close();
				}
				if(output2 != null){
					output2.close();
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}
