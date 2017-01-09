package com.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.console.entity.TSysResource;
import com.console.entity.TSysRole;
import com.console.entity.TSysUser;
import com.console.framework.dao.MyBatisDao;

@Service
public class LoginService extends BaseService{

	@Autowired
	private MyBatisDao myBatisDao;
	
	/**
	 *  @Description: 通过角色名查询资源信息
	 *  @param  user
	 *  @return TSysUser
	 *  @throws 
	 *  @author YY
	 *  @date   2013-8-12 下午6:23:42
	 */
	public List<TSysResource> getResourcesByRoleName(TSysRole role){
		List<TSysResource> resource = myBatisDao.getList("resourceMapper.getResourceByRoleName",role);
		return resource;
	}
	
	/**
	 *  @Description: 通过用户名查询角色信息
	 *  @param  user
	 *  @return TSysUser
	 *  @throws 
	 *  @author YY
	 *  @date   2013-8-12 下午6:23:42
	 */
	public List<TSysRole> getRolesByUserName(TSysUser user){
		List<TSysRole> role = myBatisDao.getList("roleMapper.getRoleByUserName",user);
		return role;
	}
	
	public static void main(String[] args){
		System.out.println("12345678".substring(3, 8));
//		System.out.println((int)(Math.random() * 5000));
	}
}
