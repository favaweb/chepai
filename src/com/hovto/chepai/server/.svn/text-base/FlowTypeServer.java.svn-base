package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.FlowTypeDao;
import com.hovto.chepai.model.FlowType;

@Component
@Transactional
public class FlowTypeServer {
	@Resource
	private FlowTypeDao flowTypeDao;
	
	public List<FlowType> findList(){
		return flowTypeDao.findList();
	}
}
