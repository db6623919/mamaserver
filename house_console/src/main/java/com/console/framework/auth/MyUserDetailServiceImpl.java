package com.console.framework.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.console.entity.TSysRole;
import com.console.entity.TSysUser;
import com.console.framework.dao.MyBatisDao;


/**
 *  查询用户的权限(3)
 *	@ClassName:   MyAccessDecisionManager
 *	@Description: TODO
 *	@author YY
 *	@date   2013-9-28 上午11:40:05
 */

@SuppressWarnings("deprecation")
public class MyUserDetailServiceImpl implements UserDetailsService {
	public static Logger logger = Logger.getLogger(MyUserDetailServiceImpl.class);

	private MyBatisDao myBatisDao;
	public MyBatisDao getMyBatisDao() {
		return myBatisDao;
	}
	public void setMyBatisDao(MyBatisDao myBatisDao) {
		this.myBatisDao = myBatisDao;
	}
	
	//登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//logger.info("********************查询用户的权限 组装User********************");

		TSysUser users = myBatisDao.getOne("userMapper.getUserByUserName",username);
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);

		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		//封装成spring security的user
		User userdetail = new User(users.getUsername(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		return userdetail;
	}
	
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(TSysUser user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		//查询用户的角色
		List<TSysRole> usersRoles = myBatisDao.getList("roleMapper.getRolesByUserid",user.getId());
		for (TSysRole role : usersRoles) {
			authSet.add(new GrantedAuthorityImpl(role.getName()));
		}
		return authSet;
	}
}
