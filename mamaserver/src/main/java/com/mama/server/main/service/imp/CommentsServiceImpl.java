package com.mama.server.main.service.imp;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mama.server.main.dao.HouseCommentDao;
import com.mama.server.main.dao.model.HouseCommentPo;
import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.dao.mongodb.IOrderCommentDao;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.vo.HouseCommentVo;
import com.mama.server.main.vo.OrderCommentVo;

/**
 * 评分实现类
 * @author majiafei
 *
 */
@Service
public class CommentsServiceImpl implements CommentsService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);

	@Resource
	private HouseCommentDao houseCommentDao;
	
	@Resource
	private IOrderCommentDao orderCommentDao;
	
	@Override
	public HouseCommentVo getHouseComments(int houseId)
	{
		if (LOGGER.isInfoEnabled())
		{
			LOGGER.info("start to call getHouseComment(), houseId = {}.", houseId);
		}
		
		HouseCommentPo po = new HouseCommentPo();
		po.setHouseId(houseId);
		List<HouseCommentPo> resultPo = houseCommentDao.queryHouseComment(po);
		if (CollectionUtils.isNotEmpty(resultPo))
		{
			return convertHouseCommentPo2Vo(resultPo.get(0));
		}
		
		return null;
	}

	private HouseCommentVo convertHouseCommentPo2Vo(HouseCommentPo resultPo)
	{
		if (resultPo == null)
		{
			return null;
		}
		
		HouseCommentVo vo = new HouseCommentVo();
		vo.setHouseId(resultPo.getHouseId());
		vo.setTotalCommentsNum(resultPo.getTotalCommentsNum());
		vo.setAverageScore(resultPo.getAverageScore());
		vo.setSummary(resultPo.getSummary());
		vo.setUpdateTime(resultPo.getUpdateTime());
		
		return vo;
	}

	@Override
	public QueryResultVo<OrderCommentPo> getOrderComments(OrderCommentPo orderComment, int sort,int currentPage,int pageSize)
	{
		return orderCommentDao.queryOrderComments(orderComment, sort,currentPage,pageSize);
	}

	@Override
	public void addOrderComments(OrderCommentVo vo)
	{
		if (LOGGER.isInfoEnabled()) 
		{
			LOGGER.info("start to call addOrderComments(), param is {}.", vo);
		}
				
		//获取评论总数和总评分
		boolean isRecommend = false;//是否已有精华的评论
		double totalScore = vo.getScore();
		int totalCommetsNum = 1;
		List<OrderCommentPo> pos = orderCommentDao.queryByHouseId(vo.getHouseId());
		if (CollectionUtils.isNotEmpty(pos)) 
		{
			totalCommetsNum += pos.size();
			for(OrderCommentPo po2 : pos)
			{
				totalScore += po2.getScore();
				
				if (po2.getRecommendTime() > 0) {
					isRecommend = true;
				}
			}
		}
		
		//添加评论
		OrderCommentPo po = convertOrderCommentVo2Po(vo);
		orderCommentDao.addOrderComment(po);
				
		//计算平均评分
		double averageScore = (totalCommetsNum == 0) ? 0 : (totalScore / (double)totalCommetsNum);
		DecimalFormat format = new DecimalFormat("0.0");
		String tmpAvrgScore = format.format(averageScore);
		
		//更新房源评论的总评论及平均部分
		HouseCommentPo houseCommentPo = new HouseCommentPo();
		houseCommentPo.setHouseId(vo.getHouseId());
		List<HouseCommentPo> hCommentPos = houseCommentDao.queryHouseComment(houseCommentPo);
		if (CollectionUtils.isEmpty(hCommentPos) || !isRecommend) {
			houseCommentPo.setSummary(vo.getComments());
		}
		houseCommentPo.setRemoved(0);
		houseCommentPo.setAverageScore(Double.valueOf(tmpAvrgScore));
		houseCommentPo.setTotalCommentsNum(totalCommetsNum);
		
				
		houseCommentDao.insertHouseComment(houseCommentPo);
	}

	private OrderCommentPo convertOrderCommentVo2Po(OrderCommentVo vo)
	{
		if (vo == null)
		{
			return null;
		}
		
		OrderCommentPo po = new OrderCommentPo();
		po.setHouseId(vo.getHouseId());
		po.setUid(vo.getUid());
		po.setUserPhone(vo.getUserPhone());
		po.setScore(vo.getScore());
		po.setComments(vo.getComments());
		po.setImages(vo.getImages());
		po.setRecommendTime(vo.getRecommendTime());
		po.setStatus(vo.getStatus());
		po.setRemoved(vo.getRemoved());
		po.setCreateTime(vo.getCreateTime());
		po.setOrderId(vo.getOrderId());
		
		return po;
	}

	@Override
	public void modifyHouseComment(OrderCommentPo po)
	{
		//精华
		orderCommentDao.updateOrderComment(po);
		//查询最新一条置顶信息
		OrderCommentPo orderComment = orderCommentDao.queryOrderComment(po);
		HouseCommentPo houseComment = new HouseCommentPo();
		houseComment.setHouseId(po.getHouseId());
		String comment = "";
		if(orderComment != null) {
			//修改mysql房源信息
			comment = orderComment.getComments();
		} 
		houseComment.setSummary(comment);
		houseCommentDao.updateHouseComment(houseComment);
	}

	@Override
	public void reviewOrderComment(OrderCommentPo orderCommnet) {
		try {
			orderCommentDao.updateOrderComment(orderCommnet);
			
			//更新评论个数
			HouseCommentPo po = new HouseCommentPo();
			po.setHouseId(orderCommnet.getHouseId());
			if(orderCommnet.getStatus() == 2) { //审核不通过
				List<HouseCommentPo> pos = houseCommentDao.queryHouseComment(po);
				//if (CollectionUtils.isNotEmpty(pos)) {
					//int totalCommentsNum = pos.get(0).getTotalCommentsNum();
					po.setTotalCommentsNum(pos.get(0).getTotalCommentsNum() - 1);
					OrderCommentPo orderComment = orderCommentDao.queryOrderComment(orderCommnet);
					if(orderComment != null) {
						po.setSummary(orderComment.getComments());
					} else {
						po.setSummary("");
					}
					houseCommentDao.updateHouseComment(po);
				//}
			}
			
			
		} catch (Exception e) {
			LOGGER.error("reviewOrderComment:评论审核失败.",e);
		}
	}
	
	@Override
	public OrderCommentPo findOrderCommnetById(String id) {
		try {
			return orderCommentDao.findOrderCommentById(id);
		} catch (Exception e) {
			LOGGER.error("findOrderCommnetById：详情查询失败.",e);
		}
		return null;
	}

	@Override
	public void delOrderCommentByOrderId(String orderId)
	{
		OrderCommentPo po = new OrderCommentPo();
		po.setRemoved(1);
		po.setOrderId(orderId);
		
		orderCommentDao.updateOrderComment(po);
		
	}
	
	@Override
	public OrderCommentPo queryOrderComment(OrderCommentPo po) {
		return orderCommentDao.queryOrderComment(po);
	}
	
}
