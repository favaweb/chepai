package com.hovto.chepai.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hovto.chepai.dao.FlowTypeDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.FlowType;

@Component
public class FlowTypeUtils {
	
	@Resource
	private FlowTypeDao flowTypeDao;
	
	public static Map<String, Integer> flowType = new HashMap<String, Integer>(0);
	
	public int getFlowType(int id) {
		if (flowType.size() == 0) {
			for(Iterator<FlowType> i = flowTypeDao.findList().iterator(); i.hasNext();) {
				FlowType f = i.next();
				flowType.put(f.getTypeName(), f.getId());
			}
		}
		int type = 0;
		switch (id) {
			case 1:
				type = flowType.get("反压");
				break;
			case 2:
				type = flowType.get("洗牌");
				break;
			case 3:
				type = flowType.get("滚油");
				break;
			case 4:
				type = flowType.get("正压");
				break;
			case 5:
				type = flowType.get("质检");
				break;
			case 6:
				type = flowType.get("打码");
				break;
			case 7:
				type = flowType.get("总质检");
				break;
			default:
				throw new ChePaiException("无法识别的流程类型.");
		}
		return type;
	}
	
	
	
	
}
