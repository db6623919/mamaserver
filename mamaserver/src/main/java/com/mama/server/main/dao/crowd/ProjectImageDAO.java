package com.mama.server.main.dao.crowd;

import java.util.List;

import com.mama.server.main.dao.model.crowd.ProjectImagePo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 众筹项目图片
 * @author majiafei
 *
 */
//@MyBatisDao
public interface ProjectImageDAO
{
	/**
	 * 插入众筹项目图片
	 * @param po
	 * @return
	 */
	int insertProjectImage(ProjectImagePo po);
	
	/**
	 * 更新众筹项目图片
	 * @param po
	 * @return
	 */
	int updateProjectImage(ProjectImagePo po);
	
	/**
	 *  删除众筹项目图片
	 * @param po
	 * @return
	 */
	int deleteProjectImage(ProjectImagePo po);
	
	/**
	 * 查询众筹项目图片
	 * @param po
	 * @return
	 */
	List<ProjectImagePo> selectProjectImage(ProjectImagePo po);

}
