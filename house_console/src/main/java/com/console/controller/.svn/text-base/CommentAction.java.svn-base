package com.console.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.OrderCommentVo;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.meidusa.fastjson.JSONObject;
/**
 * 评论管理
 * @author whl
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentAction extends BaseController{

	public static Logger logger = Logger.getLogger(CityAction.class);
	
	/**
	 * 评论列表翻页查询
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toList")
	public ModelAndView toCity(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /toList.");
		}
		Map<String, Object> postData = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
	    try {
	    	String status = "-1";
	    	if (null != request.getParameter("status") && !("").equals(request.getParameter("status"))) {
	    		status = request.getParameter("status");
	    	}
			pager.setCurrent_page(current_page);
			pager.setPage_size(MsgPropertiesUtils.getPageSize());
	        postData.put("currentPage", current_page);
	        postData.put("pageSize", MsgPropertiesUtils.getPageSize());
	        postData.put("status", status);
			JSONObject obj = HttpClientPostMethod.httpCustReqUrl(postData, "getOrderComment");
			net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(obj.get("data"));
			net.sf.json.JSONObject pageJson = jsonData.getJSONObject("page");
			List<OrderCommentVo> orderCommentList = new ArrayList<OrderCommentVo>();
			JSONArray resultJson = pageJson.getJSONArray("sourceList");
			for(int i=0 ; i<resultJson.size(); i++){
				net.sf.json.JSONObject jsonObject = resultJson.getJSONObject(i);
				OrderCommentVo orderComment = new OrderCommentVo();
				orderComment.setId(jsonObject.getString("id"));
				orderComment.setHouseId(jsonObject.getInt("houseId"));
				orderComment.setUid(jsonObject.getInt("uid"));
				orderComment.setUserPhone(jsonObject.getString("userPhone"));
				orderComment.setScore(jsonObject.getInt("score"));
				orderComment.setComments(jsonObject.getString("comments"));
				orderComment.setRecommendTime(jsonObject.getLong("recommendTime"));
				orderComment.setStatus(jsonObject.getInt("status"));
				orderComment.setRemoved(jsonObject.getInt("removed"));
				orderComment.setImageSize(jsonObject.getJSONArray("images").size());
				Date date = new Date(jsonObject.getLong("createTime"));
				orderComment.setCreateTime(sdf.format(date));
				orderCommentList.add(orderComment);
			}
			pager.paging(current_page, MsgPropertiesUtils.getPageSize(), pageJson.getInt("totalNum"));
			mv.addObject("orderCommentList", orderCommentList);
			mv.addObject("pager", pager);
			mv.addObject("status", status);
	    } catch (Exception e) {
	    	logger.error("评论数据查询异常.",e);
	    	e.printStackTrace();
	    }
		mv.setViewName("/comment/commentList");
		return mv;
	}
	
	/**
	 *评论置顶 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stickOrderCommnet")
	@ResponseBody
	public RestResponse stickOrderCommnet(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /stickOrderCommnet.");
		}
		RestResponse restP = new RestResponse();
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			String id = request.getParameter("id");
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			long recommendTime = Long.parseLong(request.getParameter("recommendTime"));
			postData.put("id", id);
			postData.put("recommendTime", recommendTime == 0 ? 0 : new Date().getTime());
			postData.put("houseId", houseId);
			JSONObject object = HttpClientPostMethod.httpCustReqUrl(postData, "stickOrderCommnet");
			String code = object.getString("code");
			restP.setCode(code);
		} catch (Exception e) {
			logger.error("评论数据删除异常.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
	/**
	 *评论审核 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doReview")
	@ResponseBody
	public RestResponse doReview(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /doReview.");
		}
		Map<String, Object> postData = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		String id = request.getParameter("id");
		int status = Integer.parseInt(request.getParameter("status"));
		int houseId = Integer.parseInt(request.getParameter("houseId"));
		postData.put("id", id);
		postData.put("status", status);
		postData.put("houseId", houseId);
		try {
			JSONObject object = HttpClientPostMethod.httpCustReqUrl(postData, "doReviewComment");
			String code = object.getString("code");
			restP.setCode(code);
		} catch (Exception e) {
			logger.error("评论数据删除异常.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
	/**
	 * 预览
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public ModelAndView show(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /show.");
		}
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			String id = request.getParameter("id");
			postData.put("id", id);
			JSONObject object = HttpClientPostMethod.httpCustReqUrl(postData, "getComment");
			net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(object.get("data"));
			net.sf.json.JSONObject ocvJson = jsonData.getJSONObject("orderCommnet");
			OrderCommentVo orderCommnet = new OrderCommentVo();
			orderCommnet.setUserPhone(ocvJson.getString("userPhone"));
			orderCommnet.setComments(ocvJson.getString("comments"));
			orderCommnet.setScore(ocvJson.getInt("score"));
			orderCommnet.setImages(ocvJson.getJSONArray("images"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(ocvJson.getLong("createTime"));
			orderCommnet.setCreateTime(sdf.format(date));
			mav.addObject("orderComment", orderCommnet);
			mav.setViewName("/comment/commentShow");
		} catch (Exception e) {
			logger.error("show：评论数据查询异常.",e);
		}
		return mav;
	}
	
}
