package com.console.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.console.entity.TSysResource;
import com.console.service.ResourceService;
import com.console.vo.MenuVO;
import com.console.vo.ZTreeMenuVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/user")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/toResourceList")
	public ModelAndView toResouceList(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		List<TSysResource> alllist = resourceService.getAllResource();
		List<ZTreeMenuVO> list = new ArrayList<ZTreeMenuVO>();

		for (TSysResource resource : alllist) {
			ZTreeMenuVO menuVO = new ZTreeMenuVO();
			menuVO.setId(resource.getId());
			menuVO.setpId(resource.getParent_id());
			menuVO.setName(resource.getName());
			menuVO.setOpen(true);
			if (resource.getParent_id().equals("0")) {
				menuVO.setOpen(true);
			}
			list.add(menuVO);
		}
		Gson gson = new Gson();
		Type type = new TypeToken<List<MenuVO>>() {
		}.getType();

		
		mv.addObject("menuStr", gson.toJson(list, type));
		mv.setViewName("/user/resourceList_new");
		return mv;
	}

	@RequestMapping("/to_includeResource")
	public ModelAndView to_includeResource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysResource resource = resourceService.getResourceById(Integer.parseInt(request
				.getParameter("id")));
		mv.addObject("resource", resource);

		if (resource.getParent_id() != 0) {
			TSysResource p_resource = resourceService.getResourceById(resource.getParent_id());
			mv.addObject("parName", p_resource.getName());
		} else {
			mv.addObject("parName", "");
		}
		mv.setViewName("/user/resourceInclude_new");
		return mv;
	}

	@RequestMapping("/to_addResource")
	public ModelAndView to_addResource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		
		String pid = request.getParameter("pid");
		TSysResource resource = resourceService.getResourceById(Integer.parseInt(pid));

		if ("0".equals(type)) {
			resource.setId(0);
			resource.setName("");
		}
		mv.addObject("parResource", resource);
		mv.addObject("flag", "add");
		mv.setViewName("/user/resourceAddUpdate_new");
		return mv;
	}

	@RequestMapping("/addResource")
	public ModelAndView addResource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			TSysResource resource = new TSysResource();
			resource.setName(request.getParameter("name"));
			resource.setDescription(request.getParameter("description"));
			resource.setParent_id(Integer.parseInt(request.getParameter("pid")));
			resource.setSort(Integer.parseInt(request.getParameter("sort")));
			resource.setStatus(request.getParameter("status"));
			resource.setType("1");
			resource.setPrivilege(request.getParameter("privilege"));
			resource.setBz1(resource.getName());
			resource.setBz2(resource.getBz1());
			resource.setBz3(resource.getBz1());
			resource.setBz4(resource.getBz1());
			resourceService.addResource(resource);
			mv.setViewName("/common/success");
			mv.addObject("message", "新增资源成功");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "新增资源失败");
		}
		return mv;
	}

	@RequestMapping("/to_updateResource")
	public ModelAndView to_updateResource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysResource resource = resourceService.getResourceById(Integer.parseInt(request
				.getParameter("id")));
		TSysResource parResource = null;
		if (resource.getParent_id() != 0) {
			parResource = resourceService.getResourceById(resource.getParent_id());
		} else {
			parResource = new TSysResource();
			parResource.setId(0);
			parResource.setName("");
		}
		mv.addObject("flag", "update");
		mv.addObject("resource", resource);
		mv.addObject("parResource", parResource);
		mv.setViewName("/user/resourceAddUpdate_new");
		return mv;
	}

	@RequestMapping("/updateResource")
	public ModelAndView updateResource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			TSysResource resource = new TSysResource();
			resource.setName(request.getParameter("name"));
			resource.setDescription(request.getParameter("description"));
			resource.setId(Integer.parseInt(request.getParameter("id")));
			resource.setSort(Integer.parseInt(request.getParameter("sort")));
			resource.setStatus(request.getParameter("status"));
			resource.setType("1");
			resource.setPrivilege(request.getParameter("privilege"));
			resource.setBz1(resource.getName());
			resource.setBz2(resource.getBz1());
			resource.setBz3(resource.getBz1());
			resource.setBz4(resource.getBz1());
			resource.setBz5(resource.getBz1());
			resourceService.updateResource(resource);
			mv.setViewName("/common/success");
			mv.addObject("message", "资源更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "资源更新失败！");
		}
		return mv;
	}

	@RequestMapping("/deleteResource")
	public ModelAndView deleteResource(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Integer resource_id = Integer.parseInt(request.getParameter("id"));
			resourceService.deleteResource(resource_id);
			mv.setViewName("/common/success");
			mv.addObject("message", "资源删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "删除失败！");
		}
		return mv;
	}

}
