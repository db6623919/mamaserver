//package com.console.controller;
//import java.io.PrintWriter;
//import java.lang.reflect.Type;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import net.miugo.core.BaseAction;
//import net.miugo.core.UserOperateLog;
//import net.miugo.core.msg.M1034;
//import net.miugo.core.msg.Q0016;
//import net.miugo.core.msg.vo.M1034VO;
//import net.miugo.dao.Paginater;
//import net.miugo.entity.TInfMchnt;
//import net.miugo.entity.TblSysResources;
//import net.miugo.entity.TblSysRoles;
//import net.miugo.entity.TblSysRolesResources;
//import net.miugo.entity.TblSysUsers;
//import net.miugo.entity.TblSysUsersRoles;
//import net.miugo.entity.vo.LoginMenuVO;
//import net.miugo.entity.vo.LoginResourceVO;
//import net.miugo.entity.vo.MenuVO;
//import net.miugo.entity.vo.ResultMessageVO;
//import net.miugo.service.system.SystemService;
//import net.miugo.util.Constant;
//import net.miugo.util.ExcelUtil;
//import net.miugo.util.IpUtil;
//import net.miugo.util.MD5;
//import net.miugo.util.MD5Encoder;
//import net.miugo.util.ResultConstant;
//import net.miugo.util.StringUtil;
//
//import org.apache.commons.lang.RandomStringUtils;
//import org.springframework.stereotype.Controller;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import demo.MerchantVO;
//import demo.ShopVO;
//
///**
// * 系统设置
// * 
// * @author yangy
// * @param
// * @date 2012-09-10
// */
//
//@Controller
//public class SystemAction extends BaseAction {
//
//	private static final long serialVersionUID = 127353712540636145L;
//	private String successMessage = "";
//	private String errorMessage = "";
//	@Resource
//	private SystemService systemService;
//	
//	/**
//	 * 跳转到妙购一卡通后台登陆页
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@UserOperateLog(value="",type=0,key="system.login",sysType="1")
//    public String login() throws Exception {
//		request.setAttribute("flag", "index");
//		return "to_login";
//	}
//
//	/**
//	 * 进入妙购一卡通后台首页
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@UserOperateLog(value="",type=0,key="system.index",sysType="1") 
//	public String index() throws Exception {
//		session.setAttribute(Constant.SESSION_IPADDRESS, IpUtil.getIpAddress(request));
//		//查询登录者拥有的角色		
//		List<TblSysUsersRoles> urlist = systemService.getRoleByUser(user.getId());
//		List<TblSysRolesResources> rrlist  = new ArrayList<TblSysRolesResources>();
//		for (TblSysUsersRoles tblSysUsersRoles : urlist) {
//			//查询角色拥有的资源(人员角色1对1关系,可以这样取)
//			rrlist = systemService.getResourceByRole(tblSysUsersRoles.getTblSysRoles().getId());
//		}
//		
//		//排序
//		Collections.sort(rrlist,new Comparator<TblSysRolesResources>(){   
//			public int compare(TblSysRolesResources o1, TblSysRolesResources o2) {      
//					return o1.getTblSysResources().getOrderno().intValue()-o2.getTblSysResources().getOrderno().intValue(); 
//				}
//			}
//		);
//		
//		//设置2级菜单
//		List<LoginMenuVO> lmList = new ArrayList<LoginMenuVO>();
//		for(TblSysRolesResources t:rrlist){
//			if(t.getTblSysResources().getPid().equals("0")){
//				//如果上级节点是根节点
//				LoginMenuVO lmvo = new LoginMenuVO();
//				//将TblSysResources对象转化成ResourceVO对象
//				LoginResourceVO rvo = tranFromTSResToResVO(t.getTblSysResources());
//				//设置当前菜单
//				lmvo.setcMenu(rvo);
//				//设置下级菜单
//				lmvo.setSubMenuList(getSubMenuList(rvo.getId(),rrlist));
//				lmList.add(lmvo);
//			}
//		}
//		session.setAttribute("menus", lmList);// 2级菜单信息
//		return "to_index";
//	}
//	
//	
//	
//	/**
//	 * 获取下级菜单
//	 * @author 荣琪
//	 * @createDate 2012年10月9日17:20:31
//	 * @param pid
//	 * @param list
//	 * @return
//	 * @throws Exception 
//	 */
//   public List<LoginResourceVO> getSubMenuList(String pid,List<TblSysRolesResources> list) throws Exception{
//		List<LoginResourceVO> l=new ArrayList<LoginResourceVO>();
//		for (TblSysRolesResources t : list) {
//			if(t.getTblSysResources().getPid().equals(pid)){
//				//将TblSysResources对象转化成ResourceVO对象
//				LoginResourceVO rvo = tranFromTSResToResVO(t.getTblSysResources());
//				l.add(rvo);
//			}
//		}
//		return l;	
//	}
//	
//	/**
//	 * 将TblSysResources对象转化成LoginResourceVO对象
//	 * @author 荣琪
//	 * @createDate 2012年10月9日17:13:16
//	 * @param tblSysResources
//	 * @return
//	 */
//    public LoginResourceVO tranFromTSResToResVO(TblSysResources tblSysResources) throws Exception{
//		LoginResourceVO rvo = new LoginResourceVO();
//		rvo.setId(tblSysResources.getId());
//		rvo.setName(tblSysResources.getName());
//		rvo.setOrderno(tblSysResources.getOrderno().longValue());
//		rvo.setPid(tblSysResources.getPid());
//		rvo.setRemark(tblSysResources.getRemark());
//		rvo.setStatus(tblSysResources.getStatus());
//		rvo.setUrl(tblSysResources.getUrl());
//		return rvo;
//	}
//	
//	/**
//	 * 退出妙购一卡通后台
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@UserOperateLog(value="",type=0,key="system.logout",sysType="1") 
//	public String logout() throws Exception {
//		try {
//			session.removeAttribute(Constant.SESSION_USERINFO);
//			session.removeAttribute(Constant.SESSION_LOGINUSERINFO);
//		} catch (Exception e) {
//			throw e;
//		}
//		return "to_logout";
//	}
//	
//	/**
//	 * session超时退出妙购一卡通后台
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@UserOperateLog(value="",type=0,key="system.timeoutLogout",sysType="1") 
//	public String timeoutLogout() throws Exception {
//		try {
//			session.removeAttribute(Constant.SESSION_USERINFO);
//			session.removeAttribute(Constant.SESSION_LOGINUSERINFO);
//		} catch (Exception e) {
//			throw e;
//		}
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();
//		out.print("<script>parent.window.location='"+request.getContextPath()+"/system!logout.shtml';</script>");
//		return null;
//	}
//	
//	/**
//	 * 查询妙购一卡通后台用户
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_user() throws Exception {
//		//判断page_flag是否变化，若page_flag为空或不为Y的时候将current_page重置为1
//		if(request.getParameter("page_flag") == null || !request.getParameter("page_flag").equals("Y")) {
//			current_page = 1;
//		}
//		//查询条件
//		String s_username = request.getParameter("s_username");
//		String s_name = request.getParameter("s_name");
//		request.setAttribute("s_username", s_username);
//		request.setAttribute("s_name", s_name);
//		
//		//分页查询
//		TblSysUsers tuser = new TblSysUsers();
//		tuser.setUsername(s_username);
//		tuser.setName(s_name);	
//        Paginater paginater = systemService.getUser(current_page,Constant.PAGE_SIZE,tuser);
//        request.setAttribute("pager", paginater);
//   
//        String successMessage = request.getParameter("successMessage");
//		if(successMessage != null && !"".equals(successMessage) && !"null".equals(successMessage)){
//			this.setSuccessMessage(successMessage);
//		}else{
//			this.setSuccessMessage("");
//		}
//		String errorMessage = request.getParameter("errorMessage");
//		if(errorMessage != null && !"".equals(errorMessage) && !"null".equals(errorMessage)){
//			this.setErrorMessage(errorMessage);
//		}else{
//			this.setErrorMessage("");
//		}
//        return "to_user";
//	}
//
//	/**
//	 * 跳转到增加妙购一卡通后台用户
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_addUser() throws Exception {
//		//查询所有角色
//		List<TblSysRoles> rolelist = systemService.getRole();
//		request.setAttribute("allRoleList", rolelist);
//		//增加标志
//		request.setAttribute("flag", "add");
//        return "to_user_addupdate";
//	}
//	
//	/**
//	 * 跳转到修改妙购一卡通后台用户
//	 * 
//	 * @return 
//	 * @throws Exception
//	 */
//    public String to_updateUser() throws Exception {
//		//查询条件
//		String id = request.getParameter("id");
//		//查询人员
//		TblSysUsers tuser = systemService.getUserById(id);
//        request.setAttribute("user", tuser);
//        
//        String mchntId = tuser.getMchntId();
//        String mchntChnDes = "";
//		if(mchntId != null && !mchntId.equals("") && !mchntId.equals("null")){
//			TInfMchnt tInfMchnt = systemService.getMchnt(mchntId);
//			mchntChnDes = tInfMchnt.getChndes();
//			request.setAttribute("mchntChnDes", mchntChnDes);
//		}
//		
//        //查询角色
//  		List<TblSysRoles> allRoleList = systemService.getRole();
//  		request.setAttribute("allRoleList", allRoleList);
//  		
//  		//查询用户拥有的角色
//  		List<TblSysRoles> hasRoleList = new ArrayList<TblSysRoles>();
//  		for (TblSysUsersRoles tblSysUsersRoles : systemService.getRoleByUser(id)) {
//  			TblSysRoles role = tblSysUsersRoles.getTblSysRoles();
//  			hasRoleList.add(role);
//		}
//  		request.setAttribute("hasRoleList", hasRoleList);
//  		
//      	//修改标志	
//        request.setAttribute("flag", "update");
//        return "to_user_addupdate";
//	}
//	
//	
//	/**
//	 * 删除妙购一卡通后台用户
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String deleteUser() throws Exception {
//		// 获取参数
//		String id = request.getParameter("id");
//		String username = request.getParameter("username");
//		try {
//			systemService.deleteUser(id);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("删除用户"+username+"失败", e);
//			this.setErrorMessage("删除人员"+username+"失败");
//			this.setSuccessMessage("");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_2, "删除妙购一卡通后台用户【"+username+"】失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "to_userlist";
//		}
//		this.setErrorMessage("");
//		this.setSuccessMessage("删除人员"+username+"成功");
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_2, "删除妙购一卡通后台用户【"+username+"】成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_userlist";
//	}
//	
//	
//	/**
//	 * 增加妙购一卡通后台用户
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String saveUser() throws Exception {
//		// 获取参数
//		String username = StringUtil.transcoding(request.getParameter("username"));
//		String name = StringUtil.transcoding(request.getParameter("name"));
//		String status = StringUtil.transcoding(request.getParameter("state"));
//		String remark = StringUtil.transcoding(request.getParameter("remark"));
//		String mchntId = StringUtil.transcoding(request.getParameter("mchntId"));//商户
//		String role = StringUtil.transcoding(request.getParameter("role"));      //权限
//
//		TblSysUsers tuser = new TblSysUsers();
//		tuser.setId(StringUtil.createSerial18());
//		tuser.setName(name);
//		tuser.setUsername(username);
//		tuser.setStatus(status);
//		tuser.setRemark(remark);
//		tuser.setCreatetime(new Date());
//		//为商户权限时，才设置所属商户
//		if(Constant.USER_ROLE_SH.equals(role) || Constant.USER_ROLE_QY.equals(role)){
//			tuser.setMchntId(mchntId);
//		}
//		MD5Encoder encoderMd5 = new MD5Encoder(username, "MD5");
//		tuser.setPassword(encoderMd5.encode(MD5.getEncodeString(Constant.PASSWORD)));
//		try {
//			//增加人员
//			systemService.saveUser(tuser,role,((TblSysUsers) session
//					.getAttribute(Constant.SESSION_USERINFO)).getUsername());
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("增加人员"+username+"失败", e);
//			this.setErrorMessage("增加人员"+username+"失败");
//			this.setSuccessMessage("");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_1, "增加妙购一卡通后台用户【"+username+"】失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "to_userlist";
//		}
//		this.setErrorMessage("");
//		this.setSuccessMessage("增加人员"+username+"成功");
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_1, "增加妙购一卡通后台用户【"+username+"】成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_userlist";
//	}
//	
//	/**
//	 * 修改妙购一卡通后台用户
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String updateUser() throws Exception {
//		// 获取参数
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		String name = StringUtil.transcoding(request.getParameter("name"));
//		String status = StringUtil.transcoding(request.getParameter("state"));
//		String remark = StringUtil.transcoding(request.getParameter("remark"));
//		String mchntId = StringUtil.transcoding(request.getParameter("mchntId"));//商户
//		String role = StringUtil.transcoding(request.getParameter("role"));      //权限
//		String username = StringUtil.transcoding(request.getParameter("username"));//用户名
//		
//		//查询出原信息
//		TblSysUsers tuser = systemService.getUserById(id);
//		tuser.setName(name);
//		tuser.setStatus(status);
//		tuser.setRemark(remark);
//		tuser.setCreatetime(new Date());
//		//为商户权限时，才设置所属商户
//		if(Constant.USER_ROLE_SH.equals(role) || Constant.USER_ROLE_QY.equals(role)){
//			tuser.setMchntId(mchntId);
//		}else{
//			TInfMchnt tinfmchnt =new TInfMchnt();
//			tinfmchnt.setMchntId("");
//			tuser.setMchntId(mchntId);
//		}
//
//		try {
//			systemService.updateUser(tuser,role);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("修改人员"+username+"失败", e);
//			this.setErrorMessage("修改人员"+username+"失败");
//			this.setSuccessMessage("");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改妙购一卡通后台用户【"+username+"】失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "to_userlist";
//		}
//		this.setErrorMessage("");
//		this.setSuccessMessage("修改人员"+username+"成功");
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改妙购一卡通后台用户【"+username+"】成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_userlist";
//	}
//	
//	/**
//	 * 判断用户名是否存在
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String getUserByUsername() throws Exception {
//		String username = request.getParameter("param");
//		String result = "";
//		if(systemService.getUserByUsername(username)){
//			result = "已存在此用户名!";
//		}else{
//			result = "y";
//		}
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 根据用户名判断该用户名是否存在
//	 * 入参有两个属性：
//	 * 	 param：input中输入的值
//	 *   name：input的name属性名
//	 * 如果存在就返回：该用户名已存在
//	 * 否则返回y
//	 * @author 荣琪
//	 * @createDate 2012年10月29日17:46:25
//	 * @return
//	 * @throws Exception
//	 */
//    public String checkUserByUsername() throws Exception {
//		String username = StringUtil.transcoding(request.getParameter("param"));
//		String result = "";
//		if(systemService.getUserByUsername(username)){
//			result = "该用户名已存在";
//		}else{
//			result = "y";
//		}
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 查询商户信息
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_mchnt() throws Exception {
//		//判断page_flag是否变化，若page_flag为空或不为Y的时候将current_page重置为1
//		if(request.getParameter("page_flag") == null || !request.getParameter("page_flag").equals("Y")) {
//			current_page = 1;
//		}
//		//查询条件
//		String s_mchntid = StringUtil.transcoding(request.getParameter("s_mchntid"));//商户号
//		String s_chndes = StringUtil.transcoding(request.getParameter("s_chndes"));   //商户名称
//		request.setAttribute("s_mchntid", s_mchntid);
//		request.setAttribute("s_chndes", s_chndes);
//		
//		//分页查询
//		TInfMchnt mchnt = new TInfMchnt();
//		mchnt.setMchntId(s_mchntid);
//		mchnt.setChndes(s_chndes);
//        Paginater paginater = systemService.getMchnt(current_page,Constant.PAGE_SIZE_8,mchnt);
//        request.setAttribute("pager", paginater);
//        request.setAttribute("flag", request.getParameter("flag"));
//   
//        return "to_mchnt";
//	}
//	
//	/**
//	 * 查询角色信息
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_role() throws Exception {
//		List<TblSysRoles> list = systemService.getRole();
//		request.setAttribute("list", list);
//		String successMessage = request.getParameter("successMessage");
//		if(successMessage != null && !"".equals(successMessage) && !"null".equals(successMessage)){
//			successMessage = StringUtil.transcoding(successMessage);
//			this.setSuccessMessage(successMessage);
//		}else{
//			this.setSuccessMessage("");
//		}
//		String errorMessage = request.getParameter("errorMessage");
//		if(errorMessage != null && !"".equals(errorMessage) && !"null".equals(errorMessage)){
//			errorMessage = StringUtil.transcoding(errorMessage);
//			this.setErrorMessage(errorMessage);
//		}else{
//			this.setErrorMessage("");
//		}
//		return "to_role";
//	}
//	
//	/**
//	 * 根据ID查询角色
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String getRoleById() throws Exception {
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		//查询角色
//		TblSysRoles role = systemService.getRoleById(id);
//		request.setAttribute("role", role);
//		//查询拥有的资源
//		List<TblSysRolesResources> haslist = systemService.getResourceByRole(id);
//		StringBuffer sbid = new StringBuffer();
//		StringBuffer sbname = new StringBuffer();
//		for (TblSysRolesResources tblSysRolesResources : haslist) {
//			sbid.append(tblSysRolesResources.getTblSysResources().getId()).append(",");
//			sbname.append(tblSysRolesResources.getTblSysResources().getName()).append(",");
//		}
//		request.setAttribute("hasResourceId", sbid.toString());
//		request.setAttribute("hasResourceName", sbname.toString());
//		return "to_role_addupdate";
//	}	
//	
//	/**
//	 * 根据role查询资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String getResourceByRole() throws Exception {
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		//查询拥有的资源
//		List<TblSysRolesResources> haslist = systemService.getResourceByRole(id);
//		//查询所有的资源
//		List<TblSysResources> alllist = systemService.getAllResource();
//		//循环组装jstree需要的json
//		List<MenuVO> list = new ArrayList<MenuVO>();
//		boolean isCheck = false;
//		for (TblSysResources resource : alllist) {
//			isCheck = false;
//			for (TblSysRolesResources roleresource : haslist) {
//				if(resource.getId().equals(roleresource.getTblSysResources().getId())){
//					isCheck = true;
//					continue;
//				}
//			}
//			MenuVO menuVO = new MenuVO();
//			menuVO.setId(resource.getId());
//			menuVO.setpId(resource.getPid());
//			menuVO.setName(resource.getName());
//			if (resource.getPid().equals("0")) {
//				menuVO.setOpen(true);
//			}
//			if(isCheck){
//				menuVO.setChecked(true);
//			}
//			list.add(menuVO);
//		}
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<MenuVO>>(){}.getType();
//		request.setAttribute("menuStr", gson.toJson(list, type));
//		return "to_menu";
//	}
//	
//	/**
//	 * 修改角色的资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String updateRole() throws Exception {
//		// 获取参数
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		String roleName = StringUtil.transcoding(request.getParameter("roleName"));
//		String resourceids = StringUtil.transcoding(request.getParameter("resourceids"));
//		TblSysRoles role = new TblSysRoles();
//		role.setId(id);
//		try {
//			systemService.updateRole(role,resourceids);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("修改角色失败", e);
//			this.setErrorMessage("修改"+roleName+"的资源失败");
//			this.setSuccessMessage("");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改"+roleName+"的资源失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "to_rolelist";
//		}
//		this.setSuccessMessage("修改"+roleName+"的资源成功");
//		this.setErrorMessage("");
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改"+roleName+"的资源成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_rolelist";
//	}
//	
//	/**
//	 * 查询妙购一卡通后台资源信息
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_resource() throws Exception {
//		// 查询资源，组装成json串
//		List<TblSysResources> listresource = systemService.getAllResource();
//		List<MenuVO> list = new ArrayList<MenuVO>();
//		for (TblSysResources tblSysResources : listresource) {
//			MenuVO menuVO = new MenuVO();
//			menuVO.setId(tblSysResources.getId());
//			menuVO.setpId(tblSysResources.getPid());
//			menuVO.setName(tblSysResources.getName());
//			if (tblSysResources.getPid().equals("0")) {
//				menuVO.setOpen(true);
//			}
//			list.add(menuVO);
//		}
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<MenuVO>>(){}.getType();
//		request.setAttribute("menuStr", gson.toJson(list, type));
//		return "to_resource";
//	}
//
//	/**
//	 * 根据资源id查询资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String getResourceById() throws Exception {
//		//查询资源
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		TblSysResources resource = systemService.getResourceById(id);
//		request.setAttribute("resource", resource);
//		
//		//查询pid的名称
//		if(!"0".equals(resource.getPid())){
//			TblSysResources p_resource = systemService.getResourceById(resource.getPid());
//			request.setAttribute("parName", p_resource.getName());
//		}else{
//			request.setAttribute("parName", "");
//		}		
//		return "to_include";
//	}
//	
//	/**
//	 * 跳转到增加资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_addResource() throws Exception {
//		String type = request.getParameter("type");
//		
//		//type 0 增加一级菜单
//		//type 1 增加下级菜单
//		String pid = StringUtil.transcoding(request.getParameter("pid"));
//		TblSysResources resource = systemService.getResourceById(pid);
//		if("0".equals(type)){
//			resource.setId("0");
//			resource.setName("");
//		}
//		request.setAttribute("parResource", resource);
//		
//		//修改标志	
//        request.setAttribute("flag", "add");
//        return "to_resource_addupdate";
//	}
//	
//	/**
//	 * 跳转到修改资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_updateResource() throws Exception {
//		//查询条件
//		String id = request.getParameter("id");
//		TblSysResources resource = systemService.getResourceById(id);
//		//查询父资源
//		TblSysResources p_resource = systemService.getResourceById(resource.getPid());
//		
//		request.setAttribute("parResource", p_resource);
//		request.setAttribute("resource", resource);
//  		
//      	//修改标志	
//        request.setAttribute("flag", "update");
//        return "to_resource_addupdate";
//	}
//	
//	/**
//	 * 查询此资源是否有子资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String getResourceByPid() throws Exception {
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		String result = "";
//		if(systemService.getResourceByPid(id)){
//			result = "1";
//		}else{
//			result = "0";
//		}
//		//System.err.println(result);
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 增加子资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String saveResource() throws Exception {
//		// 获取参数
//		String pid = StringUtil.transcoding(request.getParameter("pid"));//父id
//		String name = StringUtil.transcoding(request.getParameter("name"));
//		String url = StringUtil.transcoding(request.getParameter("url"));
//		String status = StringUtil.transcoding(request.getParameter("state"));
//		String orderno = StringUtil.transcoding(request.getParameter("orderno"));//商户
//		if(StringUtil.isEmpty(orderno)){
//			orderno = "100";
//		}
//		String remark = StringUtil.transcoding(request.getParameter("remark"));      //权限
//		
//		TblSysResources resource = new TblSysResources();
//		resource.setId(StringUtil.createSerial18());
//		resource.setName(name);
//		resource.setUrl(url);
//		resource.setPid(pid);
//		resource.setOrderno(new BigDecimal(orderno));
//		resource.setRemark(remark);
//		resource.setStatus(status);	
//		try {
//			//增加子资源
//			systemService.saveResource(resource);
//		} catch (Exception e) {
//			logger.error("增加资源失败", e);
//			request.setAttribute("message", "增加资源失败!");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_1, "增加资源失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "error";
//		}
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_1, "增加资源成功", Constant.SYSTEM_TYPE_HGGLXT);		
//		return "to_jumpframe";	
//	}
//	
//	/**
//	 * 修改资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String updateResource() throws Exception {
//		// 获取参数
//		String id = StringUtil.transcoding(request.getParameter("id"));//id
//		String name = StringUtil.transcoding(request.getParameter("name"));
//		String url = StringUtil.transcoding(request.getParameter("url"));
//		String status = StringUtil.transcoding(request.getParameter("state"));
//		String orderno = StringUtil.transcoding(request.getParameter("orderno"));//商户
//		if(StringUtil.isEmpty(orderno)){
//			orderno = "100";
//		}
//		String remark = StringUtil.transcoding(request.getParameter("remark")); //权限
//		
//		//修改
//		TblSysResources resource = systemService.getResourceById(id);
//		resource.setName(name);
//		resource.setUrl(url);
//		resource.setOrderno(new BigDecimal(orderno));
//		resource.setRemark(remark);
//		resource.setStatus(status);	
//		try {
//			//增加子资源
//			systemService.updateResource(resource);
//		} catch (Exception e) {
//			logger.error("修改资源失败", e);
//			request.setAttribute("message", "修改资源失败!");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改资源失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "error";
//		}
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改资源成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_jumpframe";	
//	}
//	
//	/**
//	 * 删除资源
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String deleteResource() throws Exception {
//		String id = StringUtil.transcoding(request.getParameter("id"));
//		//删除菜单
//		try {
//			//增加子资源
//			systemService.deleteResource(id);	
//		} catch (Exception e) {
//			logger.error("删除资源失败", e);
//			request.setAttribute("message", "删除资源失败!");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_2, "删除资源失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "error";
//		}
//		systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_2, "删除资源成功", Constant.SYSTEM_TYPE_HGGLXT);
//		return "to_resourcelist";		
//	}
//
//	/**
//	 * 生成EXCEL并下载
//	 * @return
//	 */
//     public String downExcel() {
//		List<MerchantVO> resultList = new ArrayList<MerchantVO>();
//		MerchantVO merchantVO1 = new MerchantVO();
//		MerchantVO merchantVO2 = new MerchantVO();
//
//		ShopVO shopVO1 = new ShopVO();
//		ShopVO shopVO2 = new ShopVO();
//		ShopVO shopVO3 = new ShopVO();
//		ShopVO shopVO4 = new ShopVO();
//
//		shopVO1.setShopCode("门店代码1");
//		shopVO1.setShopName("门店名称1");
//		shopVO1.setCount1(1);
//		shopVO1.setAmount1(1.00);
//		shopVO1.setAmount2(1.00);
//		shopVO1.setAmount3(1.00);
//		shopVO1.setAmount4(1.00);
//
//		shopVO2.setShopCode("门店代码2");
//		shopVO2.setShopName("门店名称2");
//		shopVO2.setCount1(2);
//		shopVO2.setAmount1(2.00);
//		shopVO2.setAmount2(2.00);
//		shopVO2.setAmount3(2.00);
//		shopVO2.setAmount4(2.00);
//
//		shopVO3.setShopCode("门店代码3");
//		shopVO3.setShopName("门店名称3");
//		shopVO3.setCount1(3);
//		shopVO3.setAmount1(3.00);
//		shopVO3.setAmount2(3.00);
//		shopVO3.setAmount3(3.00);
//		shopVO3.setAmount4(3.00);
//
//		shopVO4.setShopCode("门店代码4");
//		shopVO4.setShopName("门店名称4");
//		shopVO4.setCount1(4);
//		shopVO4.setAmount1(4.00);
//		shopVO4.setAmount2(4.00);
//		shopVO4.setAmount3(4.00);
//		shopVO4.setAmount4(4.00);
//
//		List<ShopVO> shopList1 = new ArrayList<ShopVO>();
//		shopList1.add(shopVO1);
//		shopList1.add(shopVO2);
//		List<ShopVO> shopList2 = new ArrayList<ShopVO>();
//		shopList2.add(shopVO3);
//		shopList2.add(shopVO4);
//
//		merchantVO1.setMerchantName("商户1");
//		merchantVO1.setShopList(shopList1);
//		merchantVO2.setMerchantName("商户2");
//		merchantVO2.setShopList(shopList2);
//
//		resultList.add(merchantVO1);
//		resultList.add(merchantVO2);
//
//		// 生成并下载EXcel
//		Map<String, Object> beans = new HashMap<String, Object>();
//		beans.put("resultList", resultList);
//		beans.put("createName", "老杨");
//		beans.put("createTime", "2012-12-12");
//		ExcelUtil.doExcel("wdcztjmb.xls", "网点充值统计.xls", beans, this.response);
//		return null;
//	}
//
//	/**
//	 * 进入修改密码页面
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String to_changePwd() throws Exception {
//		String successMessage = request.getParameter("successMessage");
//		if(successMessage != null && !"".equals(successMessage) && !"null".equals(successMessage)){
//			this.setSuccessMessage(successMessage);
//		}else{
//			this.setSuccessMessage("");
//		}
//		String errorMessage = request.getParameter("errorMessage");
//		if(errorMessage != null && !"".equals(errorMessage) && !"null".equals(errorMessage)){
//			this.setErrorMessage(errorMessage);
//		}else{
//			this.setErrorMessage("");
//		}
//		return "to_changePwd";
//	}
//
//	/**
//	 * 妙购一卡通修改登录密码
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//    public String updatePwdForm() throws Exception {
//		// 登录用户信息
//		TblSysUsers users = (TblSysUsers) request.getSession().getAttribute(Constant.SESSION_USERINFO);
//		String oldPwd = StringUtil.transcoding(request.getParameter("oldPwd"));
//		String newPwd = StringUtil.transcoding(request.getParameter("newPwd"));
//		ResultMessageVO vo = new ResultMessageVO();
//		try {
//			vo = systemService.updatePwd(users.getUsername(), oldPwd, newPwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("修改"+user.getUsername()+"密码失败："+e.getMessage());
//			this.setErrorMessage("修改密码失败");
//			this.setSuccessMessage("");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改"+user.getUsername()+"密码失败", Constant.SYSTEM_TYPE_HGGLXT);
//			return "to_changePwd";
//		}
//		if (ResultConstant.R0000.equals(vo.getFlag())) {
//			this.setErrorMessage("");
//			this.setSuccessMessage("修改密码成功");
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改"+user.getUsername()+"密码成功", Constant.SYSTEM_TYPE_HGGLXT);
//		} else {
//			this.setErrorMessage("");
//			this.setSuccessMessage("修改密码失败："+vo.getReturnDes());
//			systemService.saveRecordOperLog(user.getUsername(), Constant.OPERLOG_TYPE_3, "修改"+user.getUsername()+"密码失败", Constant.SYSTEM_TYPE_HGGLXT);
//		}
//		return "to_changePwd";
//	}
//	
//	/**
//	 * 发送短信
//	 * @author 荣琪
//	 * @throws Exception 
//	 * @createDate 2012年10月30日18:59:47
//	 */
//    public String sendMsg() throws Exception{
//		String result = "";
//		String phone = request.getParameter("phone");
//		String details = RandomStringUtils.randomNumeric(6);
//		String sendDetails = StringUtil.getSMSStr("sms_enterprise_reg", details);
//		System.out.println(sendDetails);
//		logger.info("发送手机号:"+phone);
//		logger.info("发送手机内容:"+sendDetails);
//		session.setAttribute("phone", phone);
//		session.setAttribute("randomcode", details+"|"+System.currentTimeMillis());
//		Q0016 q0016 = new Q0016();
//		try {
//			q0016 = systemService.sendMsg(phone, sendDetails);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("发送短信失败："+e.getMessage());
//			request.setAttribute("message", "发送短信失败："+e.getMessage());
//		}
//		if(q0016.getResult_no().equals(ResultConstant.R0000)){
//			result = "1";
//		}else{
//			result = "发送短信失败";
//		}
//		logger.info("发送短信结果:"+result);
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 清空session的所有信息
//	 * @author 荣琪
//	 * @createDate 2012年11月1日11:34:26
//	 */
//    public String clearAllSession() throws Exception{
//		String result = "1";
//		session.invalidate();
//		logger.info("清空session的所有信息");
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 清空短信验证的session中的信息
//	 * @author 荣琪
//	 * @createDate 2012年11月1日11:36:15
//	 */
//    public String clearMobileVerifySession() throws Exception{
//		String result = "1";
//		session.removeAttribute("phone");//清空手机号
//		session.removeAttribute("randomcode");//清空验证码
//		logger.info("清空短信验证的session中的信息");
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 校验短信验证码是否正确
//	 * @author 荣琪
//	 * @createDate 2012年11月1日12:48:33
//	 */
//     public String verifyShortMsgCode() throws Exception{
//		String result = "1";
//		//获取用户输入的手机号和短信验证码
//		String cusMobile = request.getParameter("mobile");
//		String cusMobileCheckCode = request.getParameter("mobileCheckCode");
//		long cusTime = Long.valueOf(System.currentTimeMillis());
//		logger.info("用户输入的手机号是："+cusMobile);
//		logger.info("用户输入的短信验证码是："+cusMobileCheckCode);
//		//获取session中的手机号和短信验证码
//		String sysMobile = String.valueOf(session.getAttribute("phone"));
//		if(sysMobile != null && !sysMobile.equals("") && !sysMobile.equals("null")){
//			String randomcodeStr = String.valueOf(session.getAttribute("randomcode"));
//			if(randomcodeStr != null && !randomcodeStr.equals("") && !randomcodeStr.equals("null")){
//				String[] arrys = StringUtil.split(randomcodeStr, "|");
//				logger.info("session中的手机号是："+sysMobile);
//				logger.info("session中的短信验证码是："+arrys[0]);
//				String sysMobileCheckCode= arrys[0];
//				long sysTime = Long.valueOf(arrys[1]);
//				long subSecond = (cusTime - sysTime)/1000;
//				logger.info("当前时间为："+cusTime+"  发送短信的时间为："+sysTime+"  两者的时间差为："+subSecond);
//				if(!cusMobile.equals(sysMobile)){
//					result = "手机号码不正确或者页面超时，请重新发送短信校验码";
//				}else{
//					if(subSecond<0 || subSecond>60){
//						result = "您在60秒内没有输入短信验证码，请重新发送短信验证码";
//					}else{
//						if(!cusMobileCheckCode.equals(sysMobileCheckCode)){
//							result = "您输入的短信验证码错误，请重新输入";
//						}else{
//							result = "1";
//						}
//					}
//				}
//			}else{
//				result = "短信验证码失效，请点击重发短信验证码";
//			}
//		}else{
//			result = "短信验证码失效，请点击重发短信验证码";
//		}
//		logger.info("校验短信验证码的结果是："+result);
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 验证验证码-用于登录验证
//	 * @author 荣琪
//	 * @date 2012年11月9日15:31:34
//	 */
//    public String checkValiCode() throws Exception {
//		String code = request.getParameter("code").toLowerCase();
//		String sessionValidateCode = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//		String result = "";
//		// 验证码相等
//		if (code.equals(sessionValidateCode.toLowerCase())) {
//			result = "1";
//		} else {
//			result = "0";
//		}
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(result);
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	/**
//	 * 获取短信验证码-用于登录验证
//	 * @author 荣琪
//	 * @date 2012年11月9日15:50:36
//	 */
//    public String getMobileCode() throws Exception {
//		// 发送短信
//		String token = "";// 短信token
//		String mobile = request.getParameter("mobile");
//		M1034 m1034 = new M1034();
//		m1034.setPhoneNo(mobile);
//		try {
//			m1034.execute();
//			if ("0000".equals(m1034.getResult_no())) {
//				List<M1034VO> list = m1034.getReturnList();
//				M1034VO m1034vo = list.get(0);
//				token = m1034vo.getToken();
//				System.err.println(m1034vo.getValidNo());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.printf(token);
//		out.flush();
//		out.close();
//		return null;
//	}
//
//	public String getSuccessMessage() {
//		return successMessage;
//	}
//
//	public void setSuccessMessage(String successMessage) {
//		this.successMessage = successMessage;
//	}
//
//	public String getErrorMessage() {
//		return errorMessage;
//	}
//
//	public void setErrorMessage(String errorMessage) {
//		this.errorMessage = errorMessage;
//	}
//	
//}
