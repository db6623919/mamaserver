package com.mama.server.main.dao;

import java.util.List;

import com.mama.server.main.dao.model.HouseCommentPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 房源评论DAO接口实现
 * @author majiafei
 *
 */
@MyBatisDao
public interface HouseCommentDao
{
	/**
	 * 插入房源评论
	 * 没有则插入，有则更新
	 * @param po
	 * @return
	 */
	int insertHouseComment(HouseCommentPo po);
 
	/**
	 * 更新房源评论
	 * @param po
	 * @return
	 */
	int updateHouseComment(HouseCommentPo po);
	
	/**
	 * 根据房源ID查询房源评论
	 * @param houseId
	 * @return
	 */
	List<HouseCommentPo> queryHouseComment(HouseCommentPo po);
}
