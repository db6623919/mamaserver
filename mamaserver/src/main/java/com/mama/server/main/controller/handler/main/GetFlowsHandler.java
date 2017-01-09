package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Log;

@Component
public class GetFlowsHandler extends BaseHandler {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            String uid = null;
            if (param.get("uid") != null) {
                uid = (String)param.get("uid");
            }
            String startDate = null;
            if (param.get("startDate") != null) {
            	startDate = (String)param.get("startDate");
            }
            String endDate = null;
            if (param.get("endDate") != null) {
            	endDate = (String)param.get("endDate");
            }
            List<Integer> types = null;
            if (param.get("types") != null) {
                types = (List<Integer>)param.get("types");
            }
            List<String> orderIds = null;
            if (param.get("orderIds") != null) {
                orderIds = (List<String>)param.get("orderIds");
            }
            int sortByTime = -1;
            if (param.get("sortByTime") != null) {
                sortByTime = (Integer)param.get("sortByTime");
                if (sortByTime != 1 && sortByTime != 0) {
                    sortByTime = -1;
                }
            }
            
            int pageNum = -1;
            int pageCount = -1;
            if (param.get("pageNum") != null && param.get("pageCount") != null) {
                pageNum = (Integer)param.get("pageNum");
                pageCount = (Integer)param.get("pageCount");
                
                if (pageNum <= 0 || pageCount <= 0) {
                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
            }
            
            FlowPo flow = new FlowPo();
            if (uid != null) {
                flow.setUid(uid);
            }
            List<FlowPo> flowList = mainService.getFlowByAllParam(flow);
            if (flowList == null) {
                genErrOutputMapWithCode("fail to get flow", ReturnCode.GET_FLOW_ERROR);
                return outputMap;
            }
            
            Log.SERVICE.error("here");
            if (sortByTime == 0) {  // asc
                for (int i = 0; i < flowList.size(); ++i) {
                    for (int j = i + 1; j < flowList.size(); ++j) {
                        if (flowList.get(i).getOperTime().compareTo(flowList.get(j).getOperTime()) > 0) {
                            FlowPo tmpFlow = flowList.get(i);
                            flowList.set(i, flowList.get(j));
                            flowList.set(j, tmpFlow);
                        }
                    }
                }
            } else if (sortByTime == 1) {   // desc
                for (int i = 0; i < flowList.size(); ++i) {
                    for (int j = i + 1; j < flowList.size(); ++j) {
                        if (flowList.get(i).getOperTime().compareTo(flowList.get(j).getOperTime()) < 0) {
                            FlowPo tmpFlow = flowList.get(i);
                            flowList.set(i, flowList.get(j));
                            flowList.set(j, tmpFlow);
                        }
                    }
                }
            }
            Log.SERVICE.error("here");
            
            ArrayList<HashMap<String, Object>> flowMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < flowList.size(); ++i) {
                if (types != null && types.size() != 0 && !types.contains(flowList.get(i).getType())) {
                    continue;
                }
                if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && (flowList.get(i).getOperTime().compareTo(startDate) <0 || flowList.get(i).getOperTime().compareTo(endDate)>0)){
                	continue;
                }
                if (orderIds != null && orderIds.size() != 0) {
                    boolean orderIdFound = false;
                    for (int j = 0; j < orderIds.size(); ++j) {
                        if (orderIds.get(j).compareTo("") != 0 && 
                                orderIds.get(j).compareTo(flowList.get(i).getOrderId()) == 0) {
                            orderIdFound = true;
                            break;
                        }
                    }
                    if (!orderIdFound) {
                        continue;
                    }
                }
                if (flowList.get(i).getRemoved() == 1) {
                    continue;
                }
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("flowId", flowList.get(i).getFlowId());
                tmpMap.put("uid", flowList.get(i).getUid());
                tmpMap.put("operTime", flowList.get(i).getOperTime());
                tmpMap.put("amt", flowList.get(i).getAmt());
                tmpMap.put("type", flowList.get(i).getType());
                tmpMap.put("showDetail", flowList.get(i).getShowDetail());
                tmpMap.put("orderId", flowList.get(i).getOrderId());
                flowMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("flows", flowMapList);
                dataMap.put("num", flowMapList.size());
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > flowMapList.size()) {
                endNum = flowMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(flowMapList.get(i));
            }
            dataMap.put("flows", pageList);
            dataMap.put("num", pageList.size());
            
            dataMap.put("totalNum", flowMapList.size());
            
            genSuccOutputMap();
        } catch (Exception e) {
        	Log.SERVICE.error("查询流水异常",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
