package com.mama.server.main.dao.crowd;

import java.util.List;

import com.mama.server.main.dao.model.crowd.ProjectImagePo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 项目收益
 * @author majiafei
 *
 */
//@MyBatisDao
public interface ProjectRevenueDAO
{
	/**
	 * 插入项目收益
	 * @param po
	 * @return
	 */
	int insertProjectRevenue(ProjectImagePo po);
	
	/**
	 * 更新项目收益
	 * @param po
	 * @return
	 */
	int updateProjectRevenue(ProjectImagePo po);
	
	/**
	 * 删除项目收益
	 * @param po
	 * @return
	 */
	int deleteProjectRevenue(ProjectImagePo po);
	
	/**
	 * 查询项目收益
	 * @param po
	 * @return
	 */
	List<ProjectImagePo> selectProjectRevenue(ProjectImagePo po);

}
