package com.webside.ofp.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

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
public class ProductServiceImpl extends AbstractService<ProductEntity, Long>implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	public static final int THUMBNAIL_DEFAULT_HEIGHT = 100;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(productMapper);
	}

	@Override
	public List<ProductEntity> findByTypeId(String id) {
		return productMapper.findByTypeId(id);
	}

	public ProductEntityWithBLOBs findByIdWithBLOBS(long id) {
		return productMapper.findByIdWithBLOBS(id);
	}

	@Override
	public int insertWithBlobs(ProductEntityWithBLOBs productEntity, String basePath) {
		ByteOutputStream output = new ByteOutputStream();
		FileImageInputStream input = null;
		ByteOutputStream output2 = null;
		try {
			// 二维码
			String content = "ART.NO: " + productEntity.getProductCode() + "%0A NAME: " + productEntity.getCnName() 
					+ "%0A TOP(mm): " + productEntity.getTop() + "%0A BOTTOM(mm): " + productEntity.getBottom() +"%0A HEIGHT(mm): "
					+ productEntity.getHeight() + "%0A WEIGHT(g): " + productEntity.getWeight() + "%0A VOLUME(ml): " + productEntity.getVolume() 
					+ "%0A 长：" + productEntity.getLength() + "%0A 宽：" + productEntity.getWidth() + "%0A 高：" + productEntity.getPackHeight() + 
					"%0A G.W.(kgs)" + productEntity.getGw() + "%0A QTY/CTN： " + productEntity.getPackingRate() + "%0A CBM： " + productEntity.getCbm();
			String url = "http://rd.wechat.com/qrcode/confirm?block_type=101&content=" + content;
			QRCodeUtil.encode(url, output);
			productEntity.setQrCodePic(output.getBytes());

			// 缩略图
			String hdMapUrl = productEntity.getHdMapUrl();
			if (hdMapUrl != null && hdMapUrl.indexOf(".") != -1) {
				String prefix = hdMapUrl.substring(0, hdMapUrl.lastIndexOf("."));
				String endfix = hdMapUrl.substring(hdMapUrl.lastIndexOf(".") + 1);
				String thumbnailUrl = prefix + "_thumbnail." + endfix;
				// 生成固定高度缩略图，默认为100
				String waterUrl = basePath + "\\resources\\images\\ofplogo.png";
				ImageUtils.scaleWithHeightAndWaterMark(productEntity.getHdMapUrl(), thumbnailUrl,
						THUMBNAIL_DEFAULT_HEIGHT, waterUrl);
				File file = new File(thumbnailUrl);
				input = new FileImageInputStream(file);
				output2 = new ByteOutputStream();
				byte[] buf = new byte[1024];
				int numBytesRead = 0;
				while ((numBytesRead = input.read(buf)) != -1) {
					output2.write(buf, 0, numBytesRead);
				}
				byte[] data = output2.getBytes();
				productEntity.setThumbnail(data);
			}

			int size = this.insert(productEntity);
			return size;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output2 != null) {
					output2.close();
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet) {
		// TODO Auto-generated method stub
		return productMapper.selectByPage(paramet);
	}

	@Override
	public int updateWithBlobs(ProductEntityWithBLOBs productEntity, String basePath) {
		ByteOutputStream output = new ByteOutputStream();
		FileImageInputStream input = null;
		ByteOutputStream output2 = null;
		try {
			// 二维码
			/*String content = productEntity.getProductCode() + "|" + productEntity.getTop() + "|"
					+ productEntity.getBottom() + productEntity.getWeight() + "|" + productEntity.getVolume() + "|"
					+ productEntity.getPackingRate() + "|" + productEntity.getCbm();*/
			String content = "ART.NO: " + productEntity.getProductCode() + "%0A NAME: " + productEntity.getCnName() 
			+ "%0A TOP(mm): " + productEntity.getTop() + "%0A BOTTOM(mm): " + productEntity.getBottom() +"%0A HEIGHT(mm): "
			+ productEntity.getHeight() + "%0A WEIGHT(g): " + productEntity.getWeight() + "%0A VOLUME(ml): " + productEntity.getVolume() 
			+ "%0A 长：" + productEntity.getLength() + "%0A 宽：" + productEntity.getWidth() + "%0A 高：" + productEntity.getPackHeight() + 
			"%0A G.W.(kgs)" + productEntity.getGw() + "%0A QTY/CTN： " + productEntity.getPackingRate() + "%0A CBM： " + productEntity.getCbm();
			String url = "http://rd.wechat.com/qrcode/confirm?block_type=101&content=" + content;
			QRCodeUtil.encode(url, output);
			productEntity.setQrCodePic(output.getBytes());

			// 缩略图
			String hdMapUrl = productEntity.getHdMapUrl();
			if (hdMapUrl != null && hdMapUrl.indexOf(".") != -1) {
				String prefix = hdMapUrl.substring(0, hdMapUrl.lastIndexOf("."));
				String endfix = hdMapUrl.substring(hdMapUrl.lastIndexOf(".") + 1);
				String thumbnailUrl = prefix + "_thumbnail." + endfix;
				// 生成固定高度缩略图，默认为100
				String waterUrl = basePath + "\\resources\\images\\ofplogo.png";
				ImageUtils.scaleWithHeightAndWaterMark(productEntity.getHdMapUrl(), thumbnailUrl,
						THUMBNAIL_DEFAULT_HEIGHT, waterUrl);
				File file = new File(thumbnailUrl);
				input = new FileImageInputStream(file);
				output2 = new ByteOutputStream();
				byte[] buf = new byte[1024];
				int numBytesRead = 0;
				while ((numBytesRead = input.read(buf)) != -1) {
					output2.write(buf, 0, numBytesRead);
				}
				byte[] data = output2.getBytes();
				productEntity.setThumbnail(data);
			}

			int size = this.update(productEntity);
			return size;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output2 != null) {
					output2.close();
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ProductEntityWithBLOBs> findByIdsWithBLOBS(List<Integer> productIds) {
		return productMapper.findByIdsWithBLOBS(productIds);
	}

}
