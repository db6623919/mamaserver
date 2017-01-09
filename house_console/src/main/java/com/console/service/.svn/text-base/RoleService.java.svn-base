package com.console.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.console.entity.TSysResource;
import com.console.entity.TSysRole;
import com.console.entity.TSysRoleResource;
import com.console.entity.TSysUser;
import com.console.entity.TSysUserRole;
import com.console.framework.dao.MyBatisDao;


@Service
public class RoleService {
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	/**
	 * 获得所有的角色信息
	 * @return
	 */
	public List<TSysRole> getAllRoles()
	{
		List<TSysRole> allRoles =myBatisDao.getList("roleMapper.getAllRoles");
		return allRoles;
	}
	
	/**
	 * 获取用户的角色信息
	 * @param user
	 * @return
	 */
	public TSysRole getRole(TSysUser user)
	{
		TSysRole role=myBatisDao.getOne("roleMapper.getRoleByUserName", user);
		return role;
	}
	
	public TSysRole getRoleById(Integer id)
	{
		TSysRole role=myBatisDao.getOne("roleMapper.getRoleById", id);
		return role;
	}
	
	
	public List<TSysResource> getResourceByRoleName(TSysRole role)
	{
		List<TSysResource> list=myBatisDao.getList("resourceMapper.getResourceByRoleName", role);
		return list;
	}
	
	public void updateRole(TSysRole role,  List<TSysRoleResource> list )
	{
		myBatisDao.update("roleMapper.updateRole", role);
		myBatisDao.delete("roleMapper.deleteRoleResource", role.getId());
		for(TSysRoleResource roleresouce: list)
		{
			myBatisDao.save("roleMapper.insertRoleResource", roleresouce);
		}
	}
	
	
	public void deleteRole(Integer id)
	{
		myBatisDao.delete("roleMapper.deleteRole", id);
	}
	
	
	public int getUserByRoleid(Integer role_id){
		return myBatisDao.getOne("roleMapper.getUserByRoleid", role_id);
	}
	
	public void addRole(TSysRole role, String rids) throws Exception
	{
		TSysRole hasRole=myBatisDao.getOne("roleMapper.findRole", role);
		if(hasRole!=null)
			throw new Exception("角色已存在 "+role.getName());
		myBatisDao.save("roleMapper.insertRole", role);
		TSysRole n_role=myBatisDao.getOne("roleMapper.findRole", role);
		
		List<TSysRoleResource> list=new ArrayList<TSysRoleResource>();
		String[] srids=rids.split(",");
		for(String rid : srids){
			if( StringUtils.isNotEmpty(rid) && StringUtils.isNotBlank(rid)){
				TSysRoleResource rs=new TSysRoleResource();
				rs.setRole_id(n_role.getId());
				rs.setResource_id(Integer.parseInt(rid));
				list.add(rs);
			}
		}
		for(TSysRoleResource roleresouce: list){
			myBatisDao.save("roleMapper.insertRoleResource", roleresouce);
		}
	}
	
	
}
