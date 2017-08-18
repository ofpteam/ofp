package com.webside.ofp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.exception.AjaxException;
import com.webside.exception.ServiceException;
import com.webside.ofp.common.config.OfpConfig;
import com.webside.ofp.common.util.OfpExportUtils;
import com.webside.ofp.common.util.StrUtil;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;
import com.webside.ofp.service.ProductService;
import com.webside.ofp.service.ProductTypeService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.util.PageUtil;

import jodd.util.ArraysUtil;

import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;

@Controller
@Scope("prototype")
@RequestMapping(value = "/product/")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;

	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try {
			PageUtil page = new PageUtil();
			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/ofp/product/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 打印商品页面
	 * 
	 * @return
	 */
	@RequestMapping("listForPrintUI.html")
	public String listForPrintUI() {
		return Common.BACKGROUND_PATH + "/ofp/product/listForPrintUI";
	}

	/**
	 * 显示所有的商品（打印页面）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAllProducts.html")
	@ResponseBody
	public Object getAllProducts(HttpServletRequest request) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("iTotalRecords", 0);
		jsonMap.put("iTotalDisplayRecords", 0);

		List<Map<String, Object>> datas = new ArrayList<>();
		datas = productService.selectByPage(jsonMap);
		jsonMap.put("iTotalRecords", datas.size());
		jsonMap.put("iTotalDisplayRecords", datas.size());
		jsonMap.put("aaData", datas);
		return jsonMap;
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dt
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters.size() < 0) {
			parameters.put("CN_NAME", null);
		}

		// 获取当前登录用户
		/*
		 * UserEntity user = ShiroAuthenticationManager.getUserEntity(); int
		 * roleId = user.getRole().getId().intValue(); parameters.put("roleId",
		 * roleId);
		 */

		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<Map<String, Object>> list = productService.selectByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Map<String, Object> map = new HashMap<>();
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), true);
			List<Map<String, Object>> list = productService.selectByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
			return parameters;
		}
	}

	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			List<ProductEntity> list = productService.queryListByPage(new HashMap<String, Object>());
			Map<String, Object> parameter = new HashMap<>();
			parameter.put("level", 2);
			// 查询一级目录
			List<ProductTypeEntity> productTypeList = productTypeService.queryListAll(parameter);
			if (!productTypeList.isEmpty()) {
				parameter.clear();
				model.addAttribute("productTypeList", productTypeList);
				parameter.put("parentId", productTypeList.get(0).getProductTypeId());
				List<ProductTypeEntity> productTypeChildrenList = productTypeService.queryListAll(parameter);
				if (!productTypeChildrenList.isEmpty()) {
					model.addAttribute("productTypeChildrenList", productTypeChildrenList);
				}
			}
			return Common.BACKGROUND_PATH + "/ofp/product/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}

	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(ProductEntityWithBLOBs productEntityWithBLOBs, ProductTypeEntity productTypeEntity,
			HttpServletRequest request) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			StringBuilder sb = this.validateSubmitVal(productEntityWithBLOBs, productTypeEntity);
			if (sb.length() == 0) {// 校验通过
				productEntityWithBLOBs.setProductType(productTypeEntity);
				productEntityWithBLOBs.setIsDelete(0);
				// cbm=(外箱长*宽*高)/1000000.
				double cbm = productEntityWithBLOBs.getHeight() * productEntityWithBLOBs.getWeight()
						* productEntityWithBLOBs.getLength() / 1000000;
				productEntityWithBLOBs.setCbm(cbm);
				productEntityWithBLOBs.setCreateTime(new Date());
				String fileUrl = OfpConfig.exportTempPath + File.separator + productEntityWithBLOBs.getHdMapUrl();
				productEntityWithBLOBs.setHdMapUrl(fileUrl);
				productEntityWithBLOBs.setCreateUser(ShiroAuthenticationManager.getUserId().intValue());
				String path = request.getSession().getServletContext().getRealPath("/");
				int result = productService.insertWithBlobs(productEntityWithBLOBs, path);
				if (result == 1) {
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "添加成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败");
				}
			} else {// 校验错误
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败" + sb.toString());
			}
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 校验提交的数据
	 * 
	 * @param productEntityWithBLOBs
	 * @param productTypeEntity
	 * @return
	 */
	private StringBuilder validateSubmitVal(ProductEntityWithBLOBs productEntityWithBLOBs,
			ProductTypeEntity productTypeEntity) {
		StringBuilder sb = new StringBuilder();
		if (productTypeEntity.getProductTypeId() == null) {
			sb.append("商品类型不能为空,");
		}
		if (StrUtil.noVal(productEntityWithBLOBs.getProductCode())) {
			sb.append("商品编码不能为空,");
		}
		if (StrUtil.noVal(productEntityWithBLOBs.getProductCode())) {
			sb.append("工厂编码不能为空,");
		}
		if (StrUtil.noVal(productEntityWithBLOBs.getUnit())) {
			sb.append("单位不能为空,");
		}
		if (productEntityWithBLOBs.getUsdPrice() == null || productEntityWithBLOBs.getUsdPrice() <= 0) {
			sb.append("美金单价不能为空或者美金单价小于0,");
		}
		if (StrUtil.noVal(productEntityWithBLOBs.getCnName())) {
			sb.append("中文名称不能为空,");
		}
		if (productEntityWithBLOBs.getVatRate() == null || productEntityWithBLOBs.getVatRate() <= 0) {
			sb.append("增值税率不能为空或者增值税率小于0,");
		}
		if (productEntityWithBLOBs.getBuyPrice() == null || productEntityWithBLOBs.getBuyPrice() <= 0) {
			sb.append("收购单价不能为空或者收购单价小于0,");
		}
		if (productEntityWithBLOBs.getWeight() == null || productEntityWithBLOBs.getWeight() <= 0) {
			sb.append("重量不能为空或者重量小于0,");
		}
		if (productEntityWithBLOBs.getVolume() == null || productEntityWithBLOBs.getVolume() <= 0) {
			sb.append("容量不能为空或者容量小于0,");
		}
		if (productEntityWithBLOBs.getTop() == null || productEntityWithBLOBs.getTop() <= 0) {
			sb.append("TOP不能为空或者TOP小于0,");
		}
		if (productEntityWithBLOBs.getBottom() == null || productEntityWithBLOBs.getBottom() <= 0) {
			sb.append("BOTTOM不能为空或者BOTTOM小于0,");
		}
		if (productEntityWithBLOBs.getHeight() == null || productEntityWithBLOBs.getHeight() <= 0) {
			sb.append("HEIGHT不能为空或者HEIGHT小于0,");
		}
		if (productEntityWithBLOBs.getLength() == null || productEntityWithBLOBs.getLength() <= 0) {
			sb.append("外包装长度不能为空或者外包装长度小于0,");
		}
		if (productEntityWithBLOBs.getWeight() == null || productEntityWithBLOBs.getWeight() <= 0) {
			sb.append("外包装宽度不能为空或者外包装宽度小于0,");
		}
		if (productEntityWithBLOBs.getPackHeight() == null || productEntityWithBLOBs.getPackHeight() <= 0) {
			sb.append("外包装高度不能为空或者外包装高度小于0,");
		}
		if (productEntityWithBLOBs.getPackingRate() == null || productEntityWithBLOBs.getPackingRate() <= 0) {
			sb.append("装箱率不能为空或者装箱率小于0,");
		}
		if (productEntityWithBLOBs.getTaxRebateRate() == null || productEntityWithBLOBs.getTaxRebateRate() <= 0) {
			sb.append("退税率不能为空或者退税率小于0,");
		}
		/*
		 * if (productEntityWithBLOBs.getCbm() == null ||
		 * productEntityWithBLOBs.getCbm() <= 0) {
		 * sb.append("CBM不能为空或者CBM小于0,"); }
		 */
		return sb;
	}

	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try {
			ProductEntityWithBLOBs productEntityWithBLOBs = productService.findByIdWithBLOBS(id);
			Map<String, Object> parameter = new HashMap<>();
			parameter.put("level", 2);
			// 查询一级目录
			List<ProductTypeEntity> productTypeList = productTypeService.queryListAll(parameter);
			if (!productTypeList.isEmpty()) {
				parameter.clear();
				model.addAttribute("productTypeList", productTypeList);
				parameter.put("parentId", productEntityWithBLOBs.getProductType().getParentId());
				List<ProductTypeEntity> productTypeChildrenList = productTypeService.queryListAll(parameter);
				if (!productTypeChildrenList.isEmpty()) {
					model.addAttribute("productTypeChildrenList", productTypeChildrenList);
				}
			}
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			model.addAttribute("page", page);
			model.addAttribute("productEntity", productEntityWithBLOBs);
			return Common.BACKGROUND_PATH + "/ofp/product/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("allList.html")
	@ResponseBody
	public Object allList() throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ProductEntity> list = productService.queryListAll(map);
			if (list != null && list.size() > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", list);
				map.put("message", "成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "没有获取到数据");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 一级目录选中后变动二级目录
	 * 
	 * @param productEntity
	 * @return
	 * @throws AjaxException
	 */
	@RequestMapping("getProductTypeChildrenList.html")
	@ResponseBody
	public Object getProductTypeChildrenList(ProductTypeEntity productTypeEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("parentId", productTypeEntity.getProductTypeId());
			List<ProductTypeEntity> productTypeChildrenList = productTypeService.queryListAll(map);
			if (productTypeChildrenList != null && productTypeChildrenList.size() > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", productTypeChildrenList);
				map.put("message", "二级目录获取成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "获取二级目录失败或者二级目录为空");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(ProductEntityWithBLOBs productEntityWithBLOBs, ProductTypeEntity productTypeEntity,
			HttpServletRequest request) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			StringBuilder sb = this.validateSubmitVal(productEntityWithBLOBs, productTypeEntity);
			if (sb.length() == 0) {// 校验通过
				productEntityWithBLOBs.setProductType(productTypeEntity);
				productEntityWithBLOBs.setModifyTime(new Date());
				if (productEntityWithBLOBs.getHdMapUrl() != null
						&& productEntityWithBLOBs.getHdMapUrl().indexOf(File.separator) == -1) {
					// 重新上传了一次附件
					String fileUrl = OfpConfig.exportTempPath + File.separator + productEntityWithBLOBs.getHdMapUrl();
					productEntityWithBLOBs.setHdMapUrl(fileUrl);
				}
				productEntityWithBLOBs.setModifyUser(ShiroAuthenticationManager.getUserId().intValue());
				String path = request.getSession().getServletContext().getRealPath("/");
				int result = productService.updateWithBlobs(productEntityWithBLOBs, path);
				if (result == 1) {
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "添加成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败");
				}
			} else {// 校验错误
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败" + sb.toString());
			}
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 附件上传
	 * 
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "uploadPicture.html")
	@ResponseBody
	public Object uploadPicture(HttpServletRequest request, ModelMap model) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			try {
				// String path = System.getProperty("catalina.home");
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 取得当前上传文件的文件名称
						String myFileName = UUID.randomUUID().toString() + file.getOriginalFilename();
						map.put("data", myFileName);
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						// 重命名上传后的文件名
						File downloadfile = new File(OfpConfig.exportTempPath);
						if (!downloadfile.exists()) {
							downloadfile.mkdirs();
						}
						downloadfile = new File(OfpConfig.exportTempPath + File.separator + myFileName);
						file.transferTo(downloadfile);
						// productService.insertWithBlobs(productEntityWithBLOBs,
						// basePath)
					}
				}
				map.put("success", Boolean.TRUE);
				map.put("message", "添加成功");
				// 记录上传该文件后的时间
			} catch (IllegalStateException | IOException e) {
				map.put("success", Boolean.FALSE);
				map.put("message", "添加是失败");
				e.printStackTrace();
			}
		} else

		{
			map.put("success", Boolean.FALSE);
			map.put("message", "添加是失败,没有需要上传的附件信息");
		}
		return map;

	}

	@RequestMapping(value = "loadQRCode.html", method = RequestMethod.GET)
	public void loadQRCode(HttpServletResponse response, @RequestParam("productId") int productId) {
		OutputStream os = null;
		try {
			response.setContentType("img/*");
			os = response.getOutputStream();
			ProductEntityWithBLOBs productEntityWithBLOBs = productService.findByIdWithBLOBS(productId);
			os.write(productEntityWithBLOBs.getQrCodePic());
			os.flush();
		} catch (Exception e) {
			logger.error("产品二维码显示异常：", e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("关闭输出流错误：", e);
			}
		}
	}

	@RequestMapping(value = "loadThumbnail.html", method = RequestMethod.GET)
	public void loadThumbnail(HttpServletResponse response, @RequestParam("productId") int productId) {
		OutputStream os = null;
		try {
			response.setContentType("img/*");
			os = response.getOutputStream();
			ProductEntityWithBLOBs productEntityWithBLOBs = productService.findByIdWithBLOBS(productId);
			os.write(productEntityWithBLOBs.getThumbnail());
			os.flush();
		} catch (Exception e) {
			logger.error("产品缩略图显示异常：", e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("关闭输出流错误：", e);
			}
		}
	}

	/**
	 * 导出报价单
	 * 
	 * @param response
	 * @param quotationSheet
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportQrCodeBatch.html")
	public void exportQrCodeBatch(HttpServletResponse response, HttpServletRequest request, String productIds) {
		String[] productIdArr = productIds.split(",");
		List<Integer> productIdList = new ArrayList<Integer>();
		for (String id : productIdArr) {
			if (id != null && !"".equals(id)) {
				productIdList.add(Integer.parseInt(id));
			}
		}
		try {
			List<ProductEntityWithBLOBs> productEntityWithBLOBs = productService.findByIdsWithBLOBS(productIdList);
			OfpExportUtils.exportQrCodeExcel(response, productEntityWithBLOBs);
		} catch (Exception e) {
			logger.error("导出二维码异常：", e);
		}
	}

	/**
	 * 文件下载
	 * 
	 * @Description:
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadfile.html")
	public String downloadFile(Long productId, HttpServletResponse response) {
		ProductEntity productEntity = productService.findById(productId);
		if (productEntity != null) {
			if (productEntity.getHdMapUrl() != null) {
				File file = new File(productEntity.getHdMapUrl());
				if (file.exists()) {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.addHeader("Content-Disposition", "attachment;fileName=" + productEntity.getHdMapUrl());// 设置文件名
					byte[] buffer = new byte[1024];
					FileInputStream fis = null;
					BufferedInputStream bis = null;
					try {
						fis = new FileInputStream(file);
						bis = new BufferedInputStream(fis);
						OutputStream os = response.getOutputStream();
						int i = bis.read(buffer);
						while (i != -1) {
							os.write(buffer, 0, i);
							i = bis.read(buffer);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						if (bis != null) {
							try {
								bis.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (fis != null) {
							try {
								fis.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			return null;
		} else {
			return null;
		}

	}
}
