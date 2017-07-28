package com.webside.ofp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.exception.AjaxException;
import com.webside.exception.ServiceException;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ProductService;
import com.webside.ofp.service.ProductTypeService;
import com.webside.util.PageUtil;
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
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<ProductEntity> list = productService.queryListByPage(parameters);
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
			parameter.put("level", 1);
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
	public Object add(ProductEntity productEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = 1;
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try {
			ProductEntity productEntity = productService.findById(id);
			Map<String, Object> parameter = new HashMap<>();
			parameter.put("level", 0);
			// 查询一级目录
			List<ProductTypeEntity> productTypeList = productTypeService.queryListAll(parameter);
			if (productTypeList != null && productTypeList.size() > 0) {
				model.addAttribute("productTypeList", productTypeList);
				parameter.clear();

			}
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			model.addAttribute("page", page);
			model.addAttribute("productEntity", productEntity);
			return Common.BACKGROUND_PATH + "/ofp/product/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
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
			map.clear();
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
	public Object update(ProductEntity productEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置创建者姓名
			int result = 1;
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 附件上传
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/upload.html", method = RequestMethod.POST)
    @ResponseBody
    public String uploadBackupFile(@RequestParam MultipartFile file) {
        //downloadUploadService.uploadBackupFile(serial, file);
        return "success";
    }
}
