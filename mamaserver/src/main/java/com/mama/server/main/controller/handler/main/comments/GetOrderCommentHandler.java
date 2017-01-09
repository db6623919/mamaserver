package com.mama.server.main.controller.handler.main.comments;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.service.imp.CommentsServiceImpl;

/**
 * 评论记录查询
 * @author whl
 *
 */
@Component
public class GetOrderCommentHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

	@Autowired
    protected CommentsService commentsService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled()) {
			logger.info("run to start GetOrderCommentHandler.");
		}
		try {
			
			OrderCommentPo houseComment = new OrderCommentPo();
			int currentPage = 0;
			if(param.get("currentPage") != null)
			{
				currentPage = (Integer)param.get("currentPage");
				//houseComment.setCurrent_page(currentPage);
			}
			
			int pageSize = 5;
			if (param.get("pageSize") != null) {
				pageSize = (Integer)param.get("pageSize");
				//houseComment.setPage_size(pageSize);
			}
				
            if(null != param.get("status") && !"".equals(param.get("status"))) {
            	 houseComment.setStatus(Integer.parseInt((String)param.get("status")));
            }
            
            if (param.get("houseId") != null) {
				int houseId = (Integer) param.get("houseId");
				houseComment.setHouseId(houseId);
			}
            
            int sort = 0;
            if (param.get("sort") != null) {
				sort = (Integer) param.get("sort");
			}
            QueryResultVo<OrderCommentPo> page = commentsService.getOrderComments(houseComment, sort,currentPage,pageSize);
            dataMap.put("page", page);
            
            dataMap.put("sourceList", page.getSourceList());
            dataMap.put("totalNum", page.getTotalNum());
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("评论记录查询异常",e);
            genErrOutputMap("interface error");
        }
		
		if (logger.isInfoEnabled()) {
			logger.info("run to end GetOrderCommentHandler.");
		}
		
        return outputMap;
	}

}
