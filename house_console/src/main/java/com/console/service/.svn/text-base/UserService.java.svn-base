package com.console.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.console.entity.TSysUser;
import com.console.framework.dao.MyBatisDao;
import com.console.framework.dao.Pagination;
import com.console.util.MD5;
import com.console.util.MD5Encoder;

@Service
public class UserService {

	@Autowired
	private MyBatisDao myBatisDao;

	@Value("${defaultPwd}")
	private String defaultPasswd;

	/**
	 * 添加新用户
	 * 
	 * @param user
	 *            用户基本信息
	 * @param role_id
	 *            用户角色id
	 * @throws Exception
	 */

	public void addUser(TSysUser user, Integer role_id) throws Exception {
		TSysUser hasUser = myBatisDao.getOne("userMapper.getUserByUserName",
				user.getUsername());
		if (hasUser != null)
			throw new Exception("用户已存在！ username:" + user.getUsername());
//		user.setPassword(this.defaultPasswd);
		
		myBatisDao.save("userMapper.saveUser", user);
		TSysUser n_user = myBatisDao.getOne("userMapper.getUserByUserName",
				user.getUsername());
		Map<String, Integer> roleuser = new HashMap<String, Integer>();
		roleuser.put("user_id", n_user.getId());
		roleuser.put("role_id", role_id);
		myBatisDao.save("userMapper.saveUserRole", roleuser);
	}

	/**
	 * 根据登录用户名和姓名查询用户信息列表
	 * 
	 * @param paraMap
	 * @return
	 */
	public Pagination queryUsers(Map paraMap) {
		Pagination pager = myBatisDao.getPagination(
				"userMapper.getUserByUserNameAndName", paraMap, 0,
				Integer.MAX_VALUE);
		return pager;
	}

	public TSysUser getUserByUserId(Integer user_id) {
		TSysUser user = myBatisDao
				.getOne("userMapper.getUserByUserId", user_id);
		return user;
	}

	public void updateUser(TSysUser user, Integer role_id) {
		myBatisDao.update("userMapper.updateUser", user);
		Map<String, Integer> paraMap = new HashMap<String, Integer>();
		paraMap.put("user_id", user.getId());
		paraMap.put("role_id", role_id);
		myBatisDao.update("userMapper.updateUserRole", paraMap);
	}

	public void deleteUser(Integer user_id) {
		myBatisDao.delete("userMapper.deleteUserRole", user_id);
		myBatisDao.delete("userMapper.deleteUser", user_id);
	}

	public void updatePwd(String name, String oldPwd, String newPwd)
			throws Exception {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("username", name);
		TSysUser user = myBatisDao.getOne("userMapper.getUserByUserName", paraMap);
		if (user.getPassword().equals(oldPwd)) {
			user.setPassword(newPwd);
			myBatisDao.update("userMapper.updatePwd", user);
		} else {
			throw new Exception("密码错误");
		}
	}

}
