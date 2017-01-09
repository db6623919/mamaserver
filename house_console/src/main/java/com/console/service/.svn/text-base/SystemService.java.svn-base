//package com.console.service;
//
//import java.util.List;
//
//import net.miugo.core.BaseService;
//import net.miugo.core.msg.Q0016;
//import net.miugo.dao.Paginater;
//import net.miugo.dao.QueryHelper;
//import net.miugo.entity.TInfMchnt;
//import net.miugo.entity.TblSysOperlog;
//import net.miugo.entity.TblSysResources;
//import net.miugo.entity.TblSysRoles;
//import net.miugo.entity.TblSysRolesResources;
//import net.miugo.entity.TblSysRolesResourcesId;
//import net.miugo.entity.TblSysUsers;
//import net.miugo.entity.TblSysUsersRoles;
//import net.miugo.entity.TblSysUsersRolesId;
//import net.miugo.entity.vo.ResultMessageVO;
//import net.miugo.util.Constant;
//import net.miugo.util.MD5;
//import net.miugo.util.MD5Encoder;
//import net.miugo.util.RSAUtil;
//import net.miugo.util.ResultConstant;
//import net.miugo.util.StringUtil;
//
//import org.springframework.stereotype.Service;
//
///**
// * 系统设置
// * 
// * @author yangy
// * @param
// * @date 2012-09-10
// */
//
//@Service
//public class SystemService extends BaseService {
//
//	/**
//	 * 获取所有资源菜单
//	 * 
//	 * @return
//	 */
//	public List<TblSysResources> getAllResource() {
//
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysResources order by orderno");
//		List<TblSysResources> list = null;
//		try {
//			list = (List<TblSysResources>) baseDao.getList(qh);
//		} catch (Exception e) {
//			logger.error("获取所有资源菜单error", e);
//		}
//		return list;
//	}
//
//	/**
//	 * 查询人员信息
//	 * @param current_page
//	 * @param page_size
//	 * @param user
//	 * @return
//	 */
//	public Paginater getUser(int current_page, int page_size, TblSysUsers user) {
//
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysUsers where 1=1");
//		if (!StringUtil.isEmpty(user.getUsername())) {
//			qh.append(" and username = ?", user.getUsername());
//		}
//		if (!StringUtil.isEmpty(user.getName())) {
//			qh.append(" and name like ?", "%" + user.getName() + "%");
//		}
//		qh.append(" order by username");
//		return baseDao.getPaginater(qh, current_page, page_size);
//	}
//
//	/**
//	 * 保存人员信息
//	 * @param user
//	 * @param role
//	 * @param userName
//	 * @throws Exception
//	 */
//	public void saveUser(TblSysUsers user, String role, String userName) throws Exception {
//		// 增加人员
//		baseDao.save(user);
//		// 增加人员权限关系
//		TblSysUsersRoles ur = new TblSysUsersRoles();
//		TblSysUsersRolesId urid = new TblSysUsersRolesId();
//		urid.setRoleId(role);
//		urid.setUserId(user.getId());
//		ur.setId(urid);
//		baseDao.save(ur);
//	}
//
//	/**
//	 * 保存人员信息
//	 * 
//	 * @author 荣琪
//	 * @createDate 2012年10月18日11:22:12
//	 * @param tblSysUsers
//	 * @throws Exception
//	 */
//	public void saveUser(TblSysUsers tblSysUsers) throws Exception {
//		baseDao.save(tblSysUsers);
//	}
//
//	/**
//	 * 修改人员信息
//	 * @param user
//	 * @param role
//	 * @throws Exception
//	 */
//	public void updateUser(TblSysUsers user, String role) throws Exception {
//		// 删除人员权限关系
//		String sql = "delete tbl_sys_users_roles where user_id = '"
//				+ user.getId() + "'";
//		baseDao.getJdbcTemplate().execute(sql);
//		// 修改人员
//		baseDao.update(user);
//		// 增加人员权限关系
//		TblSysUsersRoles ur = new TblSysUsersRoles();
//		TblSysUsersRolesId urid = new TblSysUsersRolesId();
//		urid.setRoleId(role);
//		urid.setUserId(user.getId());
//		ur.setId(urid);
//		baseDao.save(ur);
//	}
//
//	/**
//	 * 修改TblSysUsers的信息
//	 * 
//	 * @author 荣琪
//	 * @createDate 2012年9月28日13:05:29
//	 * @param user
//	 * @throws Exception
//	 */
//	public void updateTblSysUsers(TblSysUsers user, String userName) throws Exception {
//		baseDao.update(user);
//	}
//
//	/**
//	 * 删除人员信息
//	 * @param id
//	 * @throws Exception
//	 */
//	public void deleteUser(String id) throws Exception {
//		deleteTblSysUsersRoles(id);
//		baseDao.deleteById(TblSysUsers.class, id);
//	}
//
//	/**
//	 * 删除人员角色信息
//	 * 
//	 * @param username
//	 * @return
//	 * @throws Exception
//	 */
//	public void deleteTblSysUsersRoles(String userId) throws Exception {
//		String sql = " delete from tbl_sys_users_roles t where t.user_id = ? ";
//		baseDao.getJdbcTemplate().update(sql, new Object[] { userId });
//	}
//
//	/**
//	 * 根据用户名查询用户
//	 * 
//	 * @param username
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean getUserByUsername(String username) throws Exception {
//		boolean result = true;
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysUsers where username=?", username);
//		if (baseDao.getOne(qh) == null) {
//			result = false;
//		}
//		return result;
//	}
//
//	/**
//	 * 根据用户id查询用户
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public TblSysUsers getUserById(String id) throws Exception {
//		return (TblSysUsers) baseDao.findById(TblSysUsers.class, id);
//	}
//
//	/**
//	 * 查询角色信息
//	 * @return
//	 * @throws Exception
//	 */
//	public List<TblSysRoles> getRole() throws Exception {
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysRoles");
//		qh.append(" order by id");
//		return (List<TblSysRoles>) baseDao.getList(qh);
//	}
//
//	/**
//	 * 查询用户拥有的角色信息
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public List<TblSysUsersRoles> getRoleByUser(String id) throws Exception {
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysUsersRoles where id.userId = ?", id);
//		return (List<TblSysUsersRoles>) baseDao.getList(qh);
//	}
//
//	/**
//	 * 根据用户名查询角色信息
//	 * 
//	 * @author 荣琪
//	 * @createDate 2012年10月25日16:59:16
//	 * @param userName
//	 * @return
//	 * @throws Exception
//	 */
//	public List<TblSysUsersRoles> getRoleByUserName(String userName) throws Exception {
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysUsersRoles where tblSysUsers.username = ?",
//				userName);
//		return (List<TblSysUsersRoles>) baseDao.getList(qh);
//	}
//
//	/**
//	 * 查询商户信息（带分页）
//	 * 
//	 * @param current_page
//	 * @param page_size
//	 * @param mchnt
//	 * @return
//	 */
//	public Paginater getMchnt(int current_page, int page_size, TInfMchnt mchnt) {
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TInfMchnt where 1=1");
//		if (!StringUtil.isEmpty(mchnt.getMchntId())) {
//			qh.append(" and mchntId = ?", mchnt.getMchntId());
//		}
//		if (!StringUtil.isEmpty(mchnt.getChndes())) {
//			qh.append(" and chndes like ?", "%" + mchnt.getChndes() + "%");
//		}
//		qh.append(" order by mchntId");
//		return baseDao.getPaginater(qh, current_page, page_size);
//	}
//
//	/**
//	 * 查询商户信息（无分页）
//	 * 
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public TInfMchnt getMchnt(String mchntId) throws Exception {
//		return (TInfMchnt) baseDao.findById(TInfMchnt.class, mchntId);
//	}
//
//	/**
//	 * 根据ID查询角色
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public TblSysRoles getRoleById(String id) throws Exception {
//		boolean result = true;
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysRoles where id=?", id);
//		return (TblSysRoles) baseDao.getOne(qh);
//	}
//
//	/**
//	 * 根据role查询资源
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public List<TblSysRolesResources> getResourceByRole(String id) throws Exception {
//		boolean result = true;
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysRolesResources where id.roleId=?", id);
//		return (List<TblSysRolesResources>) baseDao.getList(qh);
//	}
//
//	/**
//	 * 修改角色的资源信息
//	 * 
//	 * @param role
//	 * @param resourceids
//	 * @throws Exception
//	 */
//	public void updateRole(TblSysRoles role, String resourceids)
//			throws Exception {
//		// 删除角色资源关系
//		String sql = "delete tbl_sys_roles_resources where role_id = '"
//				+ role.getId() + "'";
//		baseDao.getJdbcTemplate().execute(sql);
//		// 修改角色
//		// baseDao.update(role);
//		// 增加角色资源关系
//		String[] resourceid = resourceids.split(",");
//		for (String string : resourceid) {
//			if (string != null && !string.equals("")) {
//				TblSysRolesResources rr = new TblSysRolesResources();
//				TblSysRolesResourcesId rrid = new TblSysRolesResourcesId();
//				rrid.setRoleId(role.getId());
//				rrid.setResourceId(string);
//				rr.setId(rrid);
//				baseDao.save(rr);
//			}
//		}
//	}
//
//	/**
//	 * 根据id查询资源
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public TblSysResources getResourceById(String id) throws Exception {
//		boolean result = true;
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysResources where id=?", id);
//		return (TblSysResources) baseDao.getOne(qh);
//	}
//
//	/**
//	 * 根据pid查询资源
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean getResourceByPid(String id) throws Exception {
//		boolean result = true;
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append("from TblSysResources where pid=?", id);
//		if (baseDao.getOne(qh) == null) {
//			result = false;
//		}
//		return result;
//	}
//
//	/**
//	 * 保存子资源信息
//	 * @param resource
//	 * @throws Exception
//	 */
//	public void saveResource(TblSysResources resource) throws Exception {
//		// 增加子资源
//		baseDao.save(resource);
//	}
//
//	/**
//	 * 修改资源信息
//	 * @param resource
//	 * @throws Exception
//	 */
//	public void updateResource(TblSysResources resource) throws Exception {
//		// 修改子资源
//		baseDao.update(resource);
//	}
//
//	/**
//	 * 删除资源信息
//	 * @param id
//	 * @throws Exception
//	 */
//	public void deleteResource(String id) throws Exception {
//		baseDao.deleteById(TblSysResources.class, id);
//	}
//
//	/**
//	 * 增加日志
//	 * @param log
//	 * @throws Exception
//	 */
//	public void saveOperatorLog(TblSysOperlog log) throws Exception {
//		baseDao.save(log);
//	}
//
//	/**
//	 * 修改密码
//	 * 
//	 * @author 荣琪
//	 * @date 2012年12月11日14:34:46
//	 * @param userName
//	 * @param oldPwd
//	 * @param newPwd
//	 * @return
//	 * @throws Exception
//	 */
//	public ResultMessageVO updatePwd(String userName, String oldPwd,
//			String newPwd) throws Exception {
//		String resultNo = ResultConstant.R0000;
//		String ResultDescription = ResultConstant.R0000MSG;
//		MD5Encoder encoderMd5 = new MD5Encoder(userName, "MD5");
//		String encodePwd = encoderMd5.encode(MD5.getEncodeString(RSAUtil
//				.decryptJS(oldPwd)));
//
//		QueryHelper qh = QueryHelper.getHelper();
//		qh.append(" from TblSysUsers t where 1=1 and t.username = ?", userName);
//		qh.append(" and t.password = ?", encodePwd);
//		try {
//			TblSysUsers user = (TblSysUsers) baseDao.getOne(qh);
//			if (user != null && user.getUsername() != null
//					&& !"".equals(user.getUsername())) {
//				user.setPassword(encoderMd5.encode(MD5.getEncodeString(RSAUtil
//						.decryptJS(newPwd))));
//				this.baseDao.update(user);
//			} else {
//				resultNo = ResultConstant.R0003;
//				ResultDescription = ResultConstant.R0003MSG;
//			}
//		} catch (Exception e) {
//			resultNo = ResultConstant.R0002;
//			ResultDescription = ResultConstant.R0002MSG;
//			e.printStackTrace();
//		}
//
//		ResultMessageVO resultMessageVO = new ResultMessageVO();
//		resultMessageVO.setFlag(resultNo);
//		resultMessageVO.setReturnDes(ResultDescription);
//		logger.info("结果码：" + resultNo);
//		logger.info("结果描述：" + ResultDescription);
//		return resultMessageVO;
//	}
//
//	/**
//	 * 发送短信
//	 * 
//	 * @author 荣琪
//	 * @createDate 2012年10月30日19:04:25
//	 * @param phone
//	 * @param details
//	 * @return
//	 * @throws Exception
//	 */
//	public Q0016 sendMsg(String phone, String details){
//		Q0016 q0016 = new Q0016();
//		q0016.setPhone(phone);
//		q0016.setDetails(details);
//		try {
//			q0016.execute();
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("发送短信失败：" + e.getMessage());
//		}
//		return q0016;
//	}
//
//}
