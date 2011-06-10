package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.server.OperatorServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.UserServer;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class OperatorAction {
	@Resource
	private OperatorServer operatorServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private UserServer userServer;
	
	private String[] select;
	private int sum;
	private int flowTypeId;//流程id
	private String boxNumber;//箱号
	private int smallBatchId;
	private List<Operator> operators;

	
	/**
	 * 
	 *
	 *没有调用的方法,不知道有什么作用 
	public String save(){
		String message="";
		if(flowTypeId==1){
			String result=this.boxNumberIsExist();//判断箱号是否以用
			if(result!=null&&!"".equals(result)){
				String[] results=result.split(",");
				if(results.length>0){
					for(int i=0;i<results.length;i++){
						message+=results[i]+"号箱";
					}
					message+="重复.";
				}
			}
		}
		
//		判断是否有用户还有车牌未完成
//		for(int i=0;i<select.length;i++){
//			boolean isExist=operatorServer.findByOperater(select[i], flowTypeId);
//			if(!isExist){
//				message+=this.loadUserName(Integer.parseInt(select[i]))+"还有未完成任务";
//			}
//		}
		
		if(message!=null&&!"".equals(message)){
			ActionContext.getContext().put("message", message);
			return "message";
		}
		
		String operater="";
		for(int i=0;i<select.length;i++){
			if(!select[i].equals("0")){
				if(operater.equals("")||operater==null){
					operater=select[i];
				}else{
					operater+=","+select[i];
				}
			}
		}
		operatorServer.save(operater, sum,flowTypeId,boxNumber);
		ActionContext.getContext().put("message", "任务分发成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回</a>");
		return "message";
	}
	 */
	
	
	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	public String loadUserName(int id){
		return userServer.find(new Users(id)).getName();
	}
	
	
	/**
	 * 判断箱号是否重复
	 * @return
	 
	public String boxNumberIsExist(){
		String[] boxNumbers=boxNumber.split(",");
		String result="";
			for(int i=0;i<boxNumbers.length;i++){
				List<SmallnessBatchNumber> sBatchNumberList=smallnessBatchNumberServer.findByBoxNumber(Integer.parseInt(boxNumbers[i]));
				if(sBatchNumberList.size()>0){
					SmallnessBatchNumber smallnessBatchNumber=(SmallnessBatchNumber)sBatchNumberList.get(i);
					result+=smallnessBatchNumber.getBoxNumber()+",";
				}
			}
		return result;
	}
	*/
	
	public String loadFlow(){
		if(flowTypeId==1){
			return "Counterpressure";
		}else if(flowTypeId==2){
			return "Shuffle";
		}else if(flowTypeId==3){
			return "Stitchingoil";
		}else if(flowTypeId==4){
			return "Barotropy";
		}else if(flowTypeId==5){
			return "Llnite";
		}else if(flowTypeId==6){
			return "Imprinter";
		}else if(flowTypeId==7){
			return "AlwaysLlnite";
		}else{
			return null;
		}
	} 
	
	public String findBySmallBatchId() {
		operators = operatorServer.findBySmallBatchId(smallBatchId);
		return "findBySmallBatchId";
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}
	public int getSum() {
		return sum;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public int getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public List<Operator> getOperators() {
		return operators;
	}
	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}
	public int getSmallBatchId() {
		return smallBatchId;
	}
	public void setSmallBatchId(int smallBatchId) {
		this.smallBatchId = smallBatchId;
	}
}
