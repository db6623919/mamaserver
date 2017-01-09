package com.console.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.console.entity.TSysResource;
import com.console.entity.TSysRole;
import com.console.entity.TSysUser;
import com.console.service.LoginService;
import com.console.util.Constant;
import com.console.util.IpUtil;
import com.console.util.StringUtil;
import com.console.vo.MenuVO;


@Controller
@RequestMapping("/")
public class LoginController extends BaseController{
	@Autowired
	private LoginService loginService;
	
	/**
	 * 进入系统登录页
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/init")
	public ModelAndView init(HttpSession session, HttpServletRequest request) {
		//默认中文繁體
		String language = "";
		String country  = "";
		//主动选择语言时
		String locale = request.getParameter("locale");
		String locale_session = String.valueOf(session.getAttribute(Constant.SESSION_LOCALVAL));
		if(!StringUtil.isEmpty(locale)){
			if(locale.equals("en_US")){
				language = "en";
				country  = "US";
			}else if(locale.equals("pt_PT")){
				language = "pt";
				country  = "PT";
			}else if(locale.equals("zh_TW")){
				language = "zh";
				country  = "TW";
			}else{
				language = "zh";
				country  = "CN";
			}
		}else if(locale_session!=null && !locale_session.equals("") && !locale_session.equals("null")){
			if(locale_session.equals("en_US")){
				language = "en";
				country  = "US";
			}else if(locale_session.equals("pt_PT")){
				language = "pt";
				country  = "PT";
			}else if(locale_session.equals("zh_TW")){
				language = "zh";
				country  = "TW";
			}else{
				language = "zh";
				country  = "CN";
			}
		}else{
			//获取浏览器语言
			String navigator_locale=request.getLocale().toString();
			if(navigator_locale.startsWith("en")){
				language = "en";
				country  = "US";
			}else if(navigator_locale.equals("pt_PT")){
				language = "pt";
				country  = "PT";
			}else if(navigator_locale.equals("zh_TW")){
				language = "zh";
				country  = "TW";
			}else{
				language = "zh";
				country  = "CN";
			}
		}
		Locale currentLocale = new Locale(language, country);
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,currentLocale);
		session.setAttribute(Constant.SESSION_LOCALVAL, currentLocale);
		
		session.removeAttribute(Constant.SESSION_MENUINFO);
		session.removeAttribute(Constant.SESSION_USERINFO);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject(Constant.SESSION_LOCALVAL, currentLocale);
		mv.setViewName("/login_new");
		return mv;
	}
	
	/**
	 * @Description: 登录成功进入系统
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/main")
	public ModelAndView main(HttpSession session, HttpServletRequest request) {
		session.setAttribute(Constant.SESSION_IPADDRESS, IpUtil.getIpAddress(request));
		Locale currentLocale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		ModelAndView mv = new ModelAndView();
		List<MenuVO> menuList = null;
		if(session.getAttribute(Constant.SESSION_MENUINFO)!=null){
			menuList = (List<MenuVO>) session.getAttribute(Constant.SESSION_MENUINFO);
		}else{
			// 查询菜单信息
			List<TSysResource> resourceList = new ArrayList<TSysResource>();
			TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
			Set<String> roleNames = getRoleNameByUserName(user.getId());
			for (String roleName : roleNames) {
				// 通过角色查询到资源
				TSysRole r = new TSysRole();
				r.setName(roleName);
				List<TSysResource> resources = loginService.getResourcesByRoleName(r);
				for (TSysResource TSysResource : resources) {
					resourceList.add(TSysResource);
				}
			}
			// 组装menu
			menuList = getMenu(resourceList,session);
		}
		session.setAttribute(Constant.SESSION_MENUINFO, menuList);
		Map<String,String> map = new HashMap<String, String>();
		mv.setViewName("main_new");
		mv.addObject(Constant.SESSION_LOCALVAL, currentLocale);
		return mv;
	}
	
	@RequestMapping("/toLogin")
	public ModelAndView toLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/login_new");
		
		
		
		return mv;
	}
	
	@RequestMapping("/timeoutLogout")
	public String timeoutLogout(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			session.removeAttribute(Constant.SESSION_USERINFO);
			session.removeAttribute(Constant.SESSION_MENUINFO);
		} catch (Exception e) {
			throw e;
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>parent.window.location='"+request.getContextPath()+"/toLogin.shtml';</script>");
		return null;
	}
	
	/**
	 * @Description: 查询角色集合
	 */
	public Set<String> getRoleNameByUserName(Integer userId) {
		Set<String> roless = new HashSet<String>();
		TSysUser u = new TSysUser();
		u.setId(userId);
		List<TSysRole> roles = loginService.getRolesByUserName(u);
		for (TSysRole TSysRole : roles) {
			roless.add(TSysRole.getName());
		}
		return roless;
	}
	
	/**
	 * @Description: 查询菜单集合
	 */
	public List<MenuVO> getMenu(List<TSysResource> resourceList,HttpSession session) {
		Locale currentLocale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		
		List<MenuVO> menuList = new ArrayList<MenuVO>();
		for (TSysResource TSysResource : resourceList) {
			MenuVO menuvo = new MenuVO();
			if (TSysResource.getParent_id() == 0) {
				menuvo.setId(TSysResource.getId());
				
				//设置菜单语言
				if(null!=currentLocale){
					if(currentLocale.toString().equals("zh_TW")){
						menuvo.setName(TSysResource.getBz1());
					}else if(currentLocale.toString().equals("en_US")){
						menuvo.setName(TSysResource.getBz2());
					}else if(currentLocale.toString().equals("pt_PT")){
						menuvo.setName(TSysResource.getBz3());
					}else{
						menuvo.setName(TSysResource.getName());
					}
				}else{
					menuvo.setName(TSysResource.getName());
				}
				
				menuvo.setDescription(TSysResource.getDescription());
				menuvo.setParent_id(TSysResource.getParent_id());
				menuvo.setPrivilege(TSysResource.getPrivilege());
				menuvo.setSort(TSysResource.getSort());
				menuvo.setStatus(TSysResource.getStatus());
				menuvo.setType(TSysResource.getType());
				menuList.add(menuvo);
			}
		}
		List<MenuVO> menulist = new ArrayList<MenuVO>();
		for (MenuVO menuVO : menuList) {
			List<TSysResource> resList = new ArrayList<TSysResource>();
			for (TSysResource TSysResource : resourceList) {
				if (TSysResource.getParent_id() == menuVO.getId()) {
					
					//设置菜单语言
					if(null!=currentLocale){
						if(currentLocale.toString().equals("zh_TW")){
							TSysResource.setName(TSysResource.getBz1());
						}else if(currentLocale.toString().equals("en_US")){
							TSysResource.setName(TSysResource.getBz2());
						}else if(currentLocale.toString().equals("pt_PT")){
							TSysResource.setName(TSysResource.getBz3());
						}else{
							TSysResource.setName(TSysResource.getName());
						}
					}else{
						TSysResource.setName(TSysResource.getName());
					}
					
					resList.add(TSysResource);
				}
			}
			menuVO.setSubMenu(resList);
			menulist.add(menuVO);
		}
		return menulist;
	}
	
	public static void main(String[] args){
		
	}
}