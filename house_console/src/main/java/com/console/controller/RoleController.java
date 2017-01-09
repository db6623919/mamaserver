package com.console.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.console.entity.TSysResource;
import com.console.entity.TSysRole;
import com.console.entity.TSysRoleResource;
import com.console.framework.dao.MyBatisDao;
import com.console.service.ResourceService;
import com.console.service.RoleService;
import com.console.util.StringUtil;
import com.console.vo.MenuVO;
import com.console.vo.ZTreeMenuVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/user")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("/toRoleList")
	public ModelAndView toRoleList(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<TSysRole> list = roleService.getAllRoles();
		for (TSysRole role:list) {
			role.setName(HtmlUtils.htmlEscape(role.getName()));
			role.setDescription(HtmlUtils.htmlEscape(role.getDescription()));
		}

		mv.addObject("list", list);

		String successMessage = request.getParameter("successMessage");
		if (successMessage != null && !"".equals(successMessage) && !"null".equals(successMessage)) {
			mv.addObject("successMessage", successMessage);
		}
		String errorMessage = request.getParameter("errorMessage");
		if (errorMessage != null && !"".equals(errorMessage) && !"null".equals(errorMessage)) {
			mv.addObject("successMessage", successMessage);
		}
		mv.setViewName("/user/roleList_new");
		return mv;
	}

	@RequestMapping("/to_updateRole")
	public ModelAndView to_updateRole(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		Integer role_id = Integer.parseInt(request.getParameter("id"));
		TSysRole role = roleService.getRoleById(role_id);
		role.setName(HtmlUtils.htmlEscape(role.getName()));
		role.setDescription(HtmlUtils.htmlEscape(role.getDescription()));
		
		mv.addObject("role", role);

		List<TSysResource> haslist = roleService.getResourceByRoleName(role);
		StringBuffer sbid = new StringBuffer();
		StringBuffer sbname = new StringBuffer();

		for (TSysResource resource : haslist) {
			sbid.append(resource.getId()).append(",");
			sbname.append(resource.getName()).append(",");
		}

		mv.addObject("hasResourceId", sbid.toString());
		mv.addObject("hasResourceName", sbname.toString());
		mv.addObject("flag", "update");
		mv.setViewName("/user/roleAddUpdate_new");
		return mv;
	}

	@RequestMapping("/to_addRole")
	public ModelAndView to_addRole(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("hasResourceId", "");
		mv.addObject("hasResourceName", "");
		mv.addObject("flag", "add");
		mv.setViewName("/user/roleAddUpdate_new");
		return mv;
	}

	@RequestMapping("/addRole")
	public ModelAndView addRole(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		TSysRole p_role = new TSysRole();
		try {
			p_role.setName(request.getParameter("name"));
			p_role.setDescription(request.getParameter("description"));
			p_role.setStatus(request.getParameter("status"));
			
			String resourceids = request.getParameter("resourceids");
			roleService.addRole(p_role, resourceids);
			mv.setViewName("/common/success_new");
			mv.addObject("message", "新增角色成功！");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "新增角色角色失败 ！ 错误信息：" + e.getMessage());
		}
		return mv;
	}

	@RequestMapping("/to_getResourceMenu")
	public ModelAndView to_getResourceMenu(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Integer role_id = Integer.parseInt(request.getParameter("id"));
		TSysRole role = roleService.getRoleById(role_id);
		List<TSysResource> haslist = roleService.getResourceByRoleName(role);
		List<TSysResource> alllist = resourceService.getAllResource();

		List<ZTreeMenuVO> list = new ArrayList<ZTreeMenuVO>();
		boolean isCheck = false;
		for (TSysResource resource : alllist) {
			isCheck = false;
			for (TSysResource roleresource : haslist) {
				if (resource.getId().equals(roleresource.getId())) {
					isCheck = true;
					continue;
				}
			}
			ZTreeMenuVO menuVO = new ZTreeMenuVO();
			menuVO.setId(resource.getId());
			menuVO.setpId(resource.getParent_id());
			menuVO.setName(resource.getName());
			menuVO.setOpen(true);
			if (resource.getParent_id().equals("0")) {
				menuVO.setOpen(true);
			}
			if (isCheck) {
				 menuVO.setChecked(isCheck);
			}
			list.add(menuVO);
		}
		Gson gson = new Gson();
		Type type = new TypeToken<List<MenuVO>>() {
		}.getType();

		mv.addObject("menuStr", gson.toJson(list, type));
		mv.setViewName("/user/menuList");

		return mv;
	}

	@RequestMapping("/updateRole")
	public ModelAndView updateRole(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysRole p_role = new TSysRole();
		try {

			p_role.setId(Integer.parseInt(request.getParameter("id")));
			p_role.setName(request.getParameter("name"));
			p_role.setDescription(request.getParameter("description"));
			p_role.setStatus(request.getParameter("status"));

			List<TSysRoleResource> list = new ArrayList<TSysRoleResource>();
			String resourceids = request.getParameter("resourceids");

			String[] srids = resourceids.split(",");
			for (String rid : srids) {
				if (StringUtils.isNotEmpty(rid) && StringUtils.isNotBlank(rid)) {
					TSysRoleResource rs = new TSysRoleResource();
					rs.setRole_id(p_role.getId());
					rs.setResource_id(Integer.parseInt(rid));
					list.add(rs);
				}
			}

			roleService.updateRole(p_role, list);

			mv.setViewName("/common/success_new");
			mv.addObject("message", "角色信息更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "角色 信息更新失败! 错误信息："+e.getMessage());
		}
		return mv;
	}

	@RequestMapping("/deleteRole")
	public ModelAndView deleteRole(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Integer role_id = Integer.parseInt(request.getParameter("id"));
			if(roleService.getUserByRoleid(role_id)>0){
				mv.setViewName("/common/error_new");
				mv.addObject("message", "删除角色失败！  错误信息:角色已被用户关联使用");
			}else{
				roleService.deleteRole(role_id);
				mv.setViewName("/common/success_new");
				mv.addObject("message", "删除角色成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/common/error");
			mv.addObject("message", "删除角色失败！  错误信息" + e.getMessage());
		}
		return mv;
	}

}
