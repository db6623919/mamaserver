package com.console.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.console.entity.TSysRole;
import com.console.entity.TSysUser;
import com.console.framework.dao.MyBatisDao;
import com.console.framework.dao.Pagination;
import com.console.service.RoleService;
import com.console.service.UserService;
import com.console.util.Constant;
import com.console.util.MD5;
import com.console.util.MD5Encoder;
import com.console.util.RSAUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	public static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUserList")
	public ModelAndView toUserList(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Pagination pager = userService.queryUsers(null);
		List<TSysUser> sysUserList = new ArrayList<TSysUser>();
		sysUserList = (List<TSysUser>) pager.getList();
		for (TSysUser sysUser:sysUserList) {
			sysUser.setUsername(HtmlUtils.htmlEscape(sysUser.getUsername()));
			sysUser.setStatus(HtmlUtils.htmlEscape(sysUser.getUsername().replace("\"", "&quot;").replace("\'", "&apos;")));
			sysUser.setName(HtmlUtils.htmlEscape(sysUser.getName()));
		}
		pager.setList(sysUserList);
		mv.addObject("pager", pager);
		mv.setViewName("/user/userList_new");
		return mv;
	}

	/**
	 * 根据用户名和用户姓名查询用户信息
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/to_user")
	public ModelAndView toUser(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Map<String, String> paraMap = new HashMap();
		paraMap.put("username", request.getParameter("s_username"));
		paraMap.put("name", request.getParameter("s_name"));
		Pagination pager = userService.queryUsers(paraMap);
		mv.addObject("pager", pager);
		mv.setViewName("/user/userList_new");
		return mv;
	}

	/**
	 * 跳转到增加用户页面
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/to_addUser")
	public ModelAndView toAddUser(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<TSysRole> allRoles = roleService.getAllRoles();
		mv.addObject("allRoleList", allRoles);
		mv.addObject("flag", "add");
		mv.setViewName("/user/userAddUpdate_new");
		return mv;
	}

	/**
	 * 保存用户
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveUser")
	public ModelAndView saveUser(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		TSysUser n_user = new TSysUser();
		n_user.setName(request.getParameter("name"));
		String pwd = MD5Encoder.encode(MD5.md5(RSAUtil.decryptJS( request.getParameter("user_password") )));
		n_user.setPassword(pwd);
		n_user.setUsername(request.getParameter("username"));
		n_user.setDescription(request.getParameter("description"));
		n_user.setStatus(request.getParameter("status"));

		Integer role_id = Integer.parseInt(request.getParameter("role"));

		try {
			userService.addUser(n_user, role_id);
			mv.setViewName("/common/success_new");
			mv.addObject("message", "新增用户成功");
		} catch (Exception e) {
			mv.setViewName("/common/error_new");
			mv.addObject("message", e.getMessage());
		}
		return mv;
	}

	@RequestMapping("/to_updateUser")
	public ModelAndView to_updateUser(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysUser p_user = new TSysUser();
		p_user.setId(Integer.parseInt(request.getParameter("id")));
		p_user.setUsername(request.getParameter("username"));

		TSysUser user = userService.getUserByUserId(p_user.getId());
		user.setUsername(HtmlUtils.htmlEscape(user.getUsername()));
		user.setName(HtmlUtils.htmlEscape(user.getName()));
		
		TSysRole role = roleService.getRole(p_user);

		List<TSysRole> hasRoleList = new ArrayList<TSysRole>();
		hasRoleList.add(role);
		List<TSysRole> allRoleList = roleService.getAllRoles();

		mv.addObject("flag", "update");
		mv.addObject("user", user);
		mv.addObject("allRoleList", allRoleList);
		mv.addObject("hasRoleList", hasRoleList);
		mv.setViewName("/user/userAddUpdate_new");
		return mv;
	}

	/**
	 * 更新用户信息，以及用户的角色
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUser")
	public ModelAndView updateUser(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysUser p_user = null;
		try {
			p_user = new TSysUser();
			p_user.setId(Integer.parseInt(request.getParameter("id")));
			p_user.setUsername(request.getParameter("username"));
			p_user.setName(request.getParameter("name"));
			p_user.setDescription(request.getParameter("description"));
			p_user.setStatus(request.getParameter("status"));

			int role_id = Integer.parseInt(request.getParameter("role"));
			userService.updateUser(p_user, role_id);
			mv.setViewName("/common/success_new");
			mv.addObject("message", "更新用户信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			mv.setViewName("/common/error_new");
			mv.addObject("message", "更新用户信息失败");
		}

		return mv;
	}

	@RequestMapping("/deleteUser")
	public ModelAndView deleteUser(HttpSession session, HttpServletRequest request) {
		int user_id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		try {
			userService.deleteUser(user_id);
			mv.setViewName("/common/success_new");
			mv.addObject("message", "用户 " + username + " 已删除");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			mv.setViewName("/common/error_new");
			mv.addObject("message", "删除用户 " + username + " 失败");
		}
		return mv;
	}

	@RequestMapping("/to_changePwd")
	public ModelAndView to_changePwd(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String successMessage = request.getParameter("successMessage");
		if (successMessage != null && !"".equals(successMessage) && !"null".equals(successMessage)) {
			mv.addObject("successMessage", successMessage);
		} else {
			mv.addObject("successMessage", "");
		}
		String errorMessage = request.getParameter("errorMessage");
		if (errorMessage != null && !"".equals(errorMessage) && !"null".equals(errorMessage)) {
			mv.addObject("errorMessage", errorMessage);
		} else {
			mv.addObject("errorMessage", "");
		}
		mv.setViewName("/user/updatePwd_new");
		return mv;
	}

	@RequestMapping("/changePwd")
	public ModelAndView changePwd(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) request.getSession().getAttribute(Constant.SESSION_USERINFO);
		// TODO :　需要修改密码加密方式
		// String oldPwd =
		// StringUtil.transcoding(request.getParameter("oldPwd"));
		// String newPwd =
		// StringUtil.transcoding(request.getParameter("newPwd"));
		String oldPwd = MD5Encoder.encode(MD5.md5(RSAUtil.decryptJS( request.getParameter("oldPwd") )));
		String newPwd = MD5Encoder.encode(MD5.md5(RSAUtil.decryptJS( request.getParameter("newPwd"))));
		try {
			userService.updatePwd(user.getUsername(), oldPwd, newPwd);
			mv.setViewName("/common/success_new");
			mv.addObject("message", "修改密码成功 ！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			mv.setViewName("/common/error_new");
			mv.addObject("message", "修改密码失败！");
		}
		return mv;
	}

}