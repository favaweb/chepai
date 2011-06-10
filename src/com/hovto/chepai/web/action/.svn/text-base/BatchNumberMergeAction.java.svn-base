package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.server.BatchNumberMergeServer;
import com.hovto.chepai.server.BigBatchNumberServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.tool.ExcelReport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class BatchNumberMergeAction {
	
	@Resource
	private BatchNumberMergeServer batchNumberMergeServer;
	
	@Resource
	private NumberPlateServer numberPlateServer;
	
	private int smallBatchId;
	private List<BatchNumberMerge> batchNumberMerges;
	
	public String findMergerBySmallId() {
		batchNumberMerges = batchNumberMergeServer.findMergerBySmallId(smallBatchId);
		return "findMergerBySmallId";
	}

	public int getSmallBatchId() {
		return smallBatchId;
	}

	public void setSmallBatchId(int smallBatchId) {
		this.smallBatchId = smallBatchId;
	}

	public List<BatchNumberMerge> getBatchNumberMerges() {
		return batchNumberMerges;
	}

	public void setBatchNumberMerges(List<BatchNumberMerge> batchNumberMerges) {
		this.batchNumberMerges = batchNumberMerges;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
