package com.hovto.chepai.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.Remakes;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.DisuseMaterialServer;
import com.hovto.chepai.server.IndividualOutputServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.server.OperatorServer;
import com.hovto.chepai.server.RemakesServer;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.hovto.chepai.tool.FlowTypeUtils;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RemakesAction {
	@Resource
	private RemakesServer remakesServer;
	@Resource
	private NumberPlateServer numberPlateServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private IndividualOutputServer individualOutputServer;
	@Resource
	private OperatorServer operatorServer;
	@Resource
	private UserServer userServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private FlowTypeUtils flowTypeUtils;
	@Resource
	private DisuseMaterialServer disuseMaterialServer;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;
	
	private int[] numberPlateIds;//出错的牌
	private List<Remakes> remakesList;
	private int[] remakesIds;//补牌id
	private int isRemakes;//1补牌2合并
	private int smallnessBatchNumberId;
	private int flowTypeId;
	private int errorFlow;//重制错误流程
	private int marType;//废牌类型
	private int[] frontNumberIds;//出错前牌号
	private int[] offsideNumberIds;//出错后牌号
	Map<Integer,Integer> plates = new HashMap<Integer, Integer>();
	
	public String queryList(){
		remakesList=remakesServer.queryList();
		return "list";
	}
	
	
	/**
	 * 设置 补制的前牌,后牌 跟补制车牌的 对应关系
	 */
	public void setPlates() {
		if(frontNumberIds != null) {
			for(int i=0; i<frontNumberIds.length; i++)
				plates.put(frontNumberIds[i], 1);
		}
		if(offsideNumberIds != null) {
			for(int i=0; i<offsideNumberIds.length; i++) {
				if(plates.containsKey(offsideNumberIds[i])) {
					plates.remove(offsideNumberIds[i]);
					plates.put(offsideNumberIds[i], 3);
				} else {
					plates.put(offsideNumberIds[i], 2);
				}
			}
		}
		if(plates.keySet() != null && plates.keySet().size() > 0) {
			numberPlateIds = new int[plates.keySet().size()];
			int i=0;
			for(Integer id : plates.keySet()) {
				numberPlateIds[i] = id;
				i++;
			}
		}
		
	}
	
	//质检重置废牌
	public void saveFeeNumberPlate(){
		if(frontNumberIds == null && offsideNumberIds == null)
			throw new ChePaiException("重制车牌不允许为空!<a href='javascript:window.history.go(-1);'>返回操作</a>");
		this.setPlates();
		
		
		TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, errorFlow);
		
		List<SemifinishedProductType> frontSP=semifinishedProductTypeServer.findByType(2);
		Map<Integer,Integer> frontMap=new HashMap<Integer,Integer>();
		for(int i=0;i<frontSP.size();i++){
			for(int j=0;j<frontSP.get(i).getNumberPlateTypes().size();j++){
				frontMap.put(frontSP.get(i).getNumberPlateTypes().get(j).getId(), frontSP.get(i).getId());
			}
		}
		
		List<SemifinishedProductType> offsideSP=semifinishedProductTypeServer.findByType(1);
		Map<Integer,Integer> offsideMap=new HashMap<Integer,Integer>();
		for(int i=0;i<offsideSP.size();i++){
			for(int j=0;j<offsideSP.get(i).getNumberPlateTypes().size();j++){
				offsideMap.put(offsideSP.get(i).getNumberPlateTypes().get(j).getId(), offsideSP.get(i).getId());
			}
		}
		
		
		//添加反压前牌费牌记录
		this.addFor(frontNumberIds, frontMap,offsideMap,taskAllocation);
		//添加反压后牌费牌记录
		this.addFor(offsideNumberIds, offsideMap,frontMap,taskAllocation);
	}
	
	
	/**
	 * 记录 前后废牌
	 * @param numberIds
	 * @param map
	 * @param taskAllocation
	 */
	public void addFor(int[] numberIds,Map<Integer,Integer> map,Map<Integer,Integer> mapElse,TaskAllocation taskAllocation){
		if (numberIds == null) {
			return;
		}
		//记录错误牌
		for(int i=0;i<numberIds.length;i++){
			NumberPlate numberPlate=numberPlateServer.findById(numberIds[i]);
			DisuseMaterial disuseMaterial=new DisuseMaterial();
			disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
			disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
			disuseMaterial.setNumberPlate(numberPlate);
			disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
			disuseMaterial.setOperateTime(new Date());
			if(taskAllocation==null){ throw new ChePaiException("该车牌不经过此流程，请重新选择。<a href='Remakes!queryList.action'>返回</a>");}
			disuseMaterial.setFlowType(taskAllocation.getFlowType());
			disuseMaterial.setMarType(marType);
			disuseMaterial.setOperator(taskAllocation.getOperator());
			
			if(taskAllocation.getOperator() != null) {
				Users u = userServer.find(new Users(Integer.parseInt(taskAllocation.getOperator().split(",")[0])));
				disuseMaterial.setDayNight(u.getDayNight());
			}
			
			//判断 是否 包含 前牌或后牌
			if(map.containsKey(numberPlate.getNumberPlateType().getId())) {
				disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(numberPlate.getNumberPlateType().getId())));
			} else if(mapElse.containsKey(numberPlate.getNumberPlateType().getId())) {
				disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(mapElse.get(numberPlate.getNumberPlateType().getId())));
			}
			
			disuseMaterialServer.add(disuseMaterial);
		}
	}
	
	public void updateOperatorAndIndividualAndTask(int smallnessBatchNumberId){
		SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		
		WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
		//查询出小批号分配的未完成数据 (其实要找出来的就是 质检的任务)
		List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberId, workFlow.getCurrentFlow().getId(),0);
		if(taskList != null && taskList.size()>0){
			TaskAllocation taskAllocation=new TaskAllocation();	
			taskAllocation=taskList.get(0);
			
			//添加操作人
			Operator operator=new Operator();
			operator.setFlowType(workFlow.getCurrentFlow());
			operator.setOperater(taskAllocation.getOperator());
			operator.setSmallnessBatchNumber(smallnessBatchNumber);
			operator.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
			operatorServer.save(operator);//添加操作人
			
			
			//添加个人产量 根据小批号跟 流程 添加个人产量
			individualOutputServer.save(workFlow.getCurrentFlow(), smallnessBatchNumber);
			
			
			//修改任务分配的完成状态 将它设为补制
			taskAllocation.setStatus(1);
			taskAllocationServer.update(taskAllocation);
		}
	}
	/*//生成废牌记录
	public void addDisuseMaterial() {
		try {
			if(numberPlateIds.length>0){
				TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, errorFlow);
				for(int i=0;i<numberPlateIds.length;i++){
					NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
					DisuseMaterial disuseMaterial=new DisuseMaterial();
					disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
					disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
					disuseMaterial.setNumberPlate(numberPlate);
					disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
					disuseMaterial.setOperateTime(new Date());
					disuseMaterial.setFlowType(new FlowType(errorFlow));
					disuseMaterial.setOperator(taskAllocation.getOperator());
					disuseMaterial.setMarType(marType);
					disuseMaterialServer.add(disuseMaterial);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	/**
	 * 重制
	 */
	public String save(){
		try {
			if(marType == 6) {
				//添加废牌记录
				this.saveFeeNumberPlate();
			} else {
				
				this.saveFeeNumberPlate();
				if(numberPlateIds.length>0){
					
					//判断是否小批号不为补制
					if(isRemakes!=1){
						
						//添加补制车牌 修改车牌补制前后牌
						for(int i=0;i<numberPlateIds.length;i++){
							this.addRemakes(numberPlateIds[i]);
						}
						
						//设置小批号为 补制
						SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
						smallnessBatchNumber.setIsDistribute(3);
						smallnessBatchNumberServer.update(smallnessBatchNumber);
						
						/**
						WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
						//查询出小批号分配的未完成数据 (其实要找出来的就是 质检的任务)
						List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberId, workFlow.getCurrentFlow().getId(),0);
						if(taskList != null && taskList.size()>0){
							TaskAllocation taskAllocation=new TaskAllocation();	
							taskAllocation=taskList.get(0);
							
							//修改任务分配的完成状态 将它设为补制
							taskAllocation.setStatus(2);
							taskAllocationServer.update(taskAllocation);
						}
						**/
						
					}else{//如果小批号是 重新补制
						
						
						
						
						//设置所有补制车牌
						for(int i=0;i<numberPlateIds.length;i++){
							//这里首先的拿到车牌
							NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
							
							
							//修改车牌 名称, 如果还是继续补制前牌跟后牌,则不用修改,如果 补制整副,则 判断 是否有可能补制前牌或 后牌
							int index = numberPlate.getLicensePlateNumber().indexOf("_");
							String backStr = numberPlate.getLicensePlateNumber().substring(index+1,numberPlate.getLicensePlateNumber().length());
							if(backStr.equals("补制整副")) {
								if(plates.get(numberPlateIds[i]) == 1) {
									numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("整副", "前牌-"));
								} else if(plates.get(numberPlateIds[i]) == 2) {
									numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("整副", "后牌-"));
								}
							}
							
							//判断如果是重新补牌,则将补牌 状态 修改成 核审中
							Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
							remakes.setState(2);
							remakesServer.updateRemakes(remakes);
							//往补牌表 在添加一条重复补牌的 记录
							this.addRepeatRemakes(numberPlateIds[i]);
							
							/**
							 * 1.设置 补牌记录为完成
							 * 2.添加新的对应的补牌记录
							 * 	
							 * (1,2可以合成 setState为0)
							 * 
							 * 3.  判断当前补制批号内重制车牌是否全部处理List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
							 * 		如果全部处理掉, 找到流程,找到小批号, 清空箱子,添加操作人,添加个人产量						 * 
							 * */
							
							 
							/**
							 * 小旭开始
							 * 
							 * 
							//根据小批号 和 车牌号 查询记录
							Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
							//如果有该 补牌记录
							if(remakes!=null){
								remakes.setState(2);
								boolean str=remakesServer.updateRemakes(remakes);
								if(str){
									//this.updateOperatorAndIndividualAndTask();
									this.addRemakes(numberPlateIds[i]);
								}
							}
							*
							*
							*小旭结束
							***/
							
							
							
						}
						
						//这里员工 有可能先重制完成 在重制...或以还需   判断当前补制批号内重制车牌是否全部处理
						List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
						if(list.size()==0){
							WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
							if(workFlow!=null){
								SmallnessBatchNumber sBatchNumber=workFlow.getSmallnessBatchNumber();
								sBatchNumber.setBoxNumber(0);
								sBatchNumber.setBoxNumberType(0);
								smallnessBatchNumberServer.update(sBatchNumber);
								
								//查询出小批号分配的数据
								TaskAllocation taskAllocation=new TaskAllocation();	
								taskAllocation=taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, workFlow.getCurrentFlow().getId());
								
								//添加操作人
								Operator operator=new Operator();
								operator.setFlowType(workFlow.getCurrentFlow());
								operator.setOperater(taskAllocation.getOperator());
								operator.setSmallnessBatchNumber(new SmallnessBatchNumber(smallnessBatchNumberId));
								operatorServer.add(operator);//添加操作人
								
								individualOutputServer.save(workFlow.getCurrentFlow(), new SmallnessBatchNumber(smallnessBatchNumberId));//添加个人产量
								 	
								//修改任务分配的完成状态
								taskAllocation.setStatus(1);
								taskAllocationServer.update(taskAllocation);
								
								workFlow.setCurrentFlow(new FlowType(8));
								workFlow.setNextFlow(new FlowType(8));
								workFlowServer.update(workFlow);
								
							}
						}
					}
				}
			}
			
		} catch (ChePaiException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionContext.getContext().put("message", "成功加入待补区,请到待补区打包重制!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回</a>");
		return "message";
	}
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
	
	/**
	 * 重制完成方法
	 * @return
	 */
	public String remakesFinish(){
		//设置好 要补制的 前牌跟 后台
		this.setPlates();
		//定义一个改重制批号内    原来的小批号
		Map<Integer,SmallnessBatchNumber> map=new HashMap<Integer,SmallnessBatchNumber>();
		
		
		if(numberPlateIds.length>0){
			/**
			 *1.如果 是补制前牌跟后牌,直接下一步...
			 *2.如果 补制整副, 判断 是否全部选上
			 */
			
			//循环修改选择重制车牌并获取真实小批号
			for(int i=0;i<numberPlateIds.length;i++){
				
				//这里首先的拿到车牌
				NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
				
				
				//修改车牌 名称, 如果还是继续补制前牌跟后牌,则不用修改,如果 补制整副,则 判断 是否有可能补制前牌或 后牌
				int index = numberPlate.getLicensePlateNumber().indexOf("_");
				String backStr = numberPlate.getLicensePlateNumber().substring(index+1,numberPlate.getLicensePlateNumber().length());
				if(backStr.equals("补制整副")) {
					if(plates.get(numberPlateIds[i]) == 1) {
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("整副", "后牌"));
						//如果还需 再次补牌, 则修改补牌状态
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//往补牌表里在添加一条 重复补牌的记录
						this.addRepeatRemakes(numberPlateIds[i]);
					} else if(plates.get(numberPlateIds[i]) == 2) {
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("整副", "前牌"));
						//如果还需 再次补牌, 则修改补牌状态
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//往补牌表里在添加一条 重复补牌的记录
						this.addRepeatRemakes(numberPlateIds[i]);
					} else { //如果是 整副补牌成功
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().substring(0,index));
						//修改补牌状态,将其设置为完成
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//实际车牌的小批号
						boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber());
						//如果 没有该小批号, 设置 车牌号 跟 实习小批号
						if(!is)map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber());
					}
				} else { //如果是 补制前牌 跟补制后牌的 ...则说明 该车牌已经补制完成
					if(index!=-1)  
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().substring(0,index));
					//修改补牌状态,将其设置为完成
					Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
					remakes.setState(2);
					remakesServer.updateRemakes(remakes);
					//实际车牌的小批号
					boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber());
					//如果 没有该小批号, 设置 车牌号 跟 实习小批号
					if(!is)
						map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber());
				}
				
				
				/**
				 * 
				 * 小旭开始
				 *
				根据补牌的小批号 和 车牌号找到补牌记录 将其设置成为 补牌完成
				Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
				remakes.setState(2);
				boolean str=remakesServer.updateRemakes(remakes);
				if(str){
					//实际车牌的小批号
					boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber().getId());
					//如果 没有该小批号, 设置 车牌号 跟 实习小批号
					if(!is)map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber().getId());
				}
				*
				*小旭结束
				*
				**/
			}
			
			
			
			
			
			//查询出全部没有质检完的重制车牌
			List<Remakes> resultRemakes=remakesServer.findByState();
			//循环判断 重制完成 的车牌是否有质检全部通过小批号
			for(int i=0;i<numberPlateIds.length;i++){
				boolean pass=true;
				boolean is=map.containsKey(numberPlateIds[i]);
				if(is){
					//拿到真实小批号
					SmallnessBatchNumber sbatchNumberId=map.get(numberPlateIds[i]);
					//循环判断是否 补制车牌是否含有 真实小批号..
					for(int j=0;j<resultRemakes.size();j++){
						Remakes remakes=(Remakes)resultRemakes.get(j);
						if(remakes.getNumberPlate().getSmallnessBatchNumber().getId()==sbatchNumberId.getId()){
							pass=false;
						}
					}
					//判断改小批号的重制车牌是否全部质检完成, 更改流程状态,设置小批号任务状态为 未发
					if(pass){
						this.updateOperatorAndIndividualAndTask(sbatchNumberId.getId());
						boolean isSucceed= workFlowServer.updateBySbatchNumberId(sbatchNumberId.getId(),2);
						sbatchNumberId.setIsDistribute(1);
						smallnessBatchNumberServer.update(sbatchNumberId);
					}
				}
			}
			
			
			//判断当前补制批号内重制车牌是否全部处理 如果全部处理掉, 找到流程,找到小批号, 清空箱子,添加操作人,添加个人产量	
			List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
			if(list.size()==0){
				WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
				if(workFlow!=null){
					SmallnessBatchNumber sBatchNumber=workFlow.getSmallnessBatchNumber();
					sBatchNumber.setBoxNumber(0);
					sBatchNumber.setBoxNumberType(0);
					smallnessBatchNumberServer.update(sBatchNumber);
					
					//查询出小批号分配的数据
					TaskAllocation taskAllocation=new TaskAllocation();	
					taskAllocation=taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, workFlow.getCurrentFlow().getId());
					//添加操作人
					Operator operator=new Operator();
					operator.setFlowType(workFlow.getCurrentFlow());
					operator.setOperater(taskAllocation.getOperator());
					operator.setSmallnessBatchNumber(new SmallnessBatchNumber(smallnessBatchNumberId));
					operatorServer.add(operator);//添加操作人
					
					individualOutputServer.save(workFlow.getCurrentFlow(), new SmallnessBatchNumber(smallnessBatchNumberId));//添加个人产量
					//修改任务分配的完成状态
					taskAllocation.setStatus(1);
					taskAllocationServer.update(taskAllocation);
					
					workFlow.setCurrentFlow(new FlowType(8));
					workFlow.setNextFlow(new FlowType(8));
					workFlowServer.update(workFlow);
					
				}
			}
		}
		ActionContext.getContext().put("message", "补牌完成!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回</a>");
		return "message";
	}
	
	/**
	 * 添加一条 不是重复补牌数据
	 * @param numberPlateId
	 */
	public void addRemakes(int numberPlateId){
		NumberPlate numberPlate=numberPlateServer.findById(numberPlateId);
		
		
		//修改车牌的补制类型 状态
		if(plates.containsKey(numberPlateId)) {
			if(plates.get(numberPlateId) == 1) {
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_补制前牌");
			} else if(plates.get(numberPlateId) == 2) { 
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_补制后牌");
			} else { 
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_补制整副");
			}
		}
		numberPlateServer.update(numberPlate);
		
		
		Remakes remakes=new Remakes();
		remakes.setNumberPlate(numberPlate);
		remakes.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
		remakes.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
		remakesServer.add(remakes);
	}
	
	/**
	 * 添加一条 重复补牌数据
	 * @param numberPlateId
	 */
	public void addRepeatRemakes(int numberPlateId){
		NumberPlate numberPlate=numberPlateServer.findById(numberPlateId);
		Remakes remakes=new Remakes();
		remakes.setNumberPlate(numberPlate);
		remakes.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
		remakes.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
		remakesServer.add(remakes);
	}
	
	
	
	public String baleRemakes(){
		try {
			if(remakesIds.length>0){
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
				SmallnessBatchNumber smallnessBatchNumber=new SmallnessBatchNumber();
				smallnessBatchNumber.setOrderType(1);//订单类型1补制
				smallnessBatchNumber.setDateTime(new Date());
				smallnessBatchNumber.setPrecedence(1);//优先级别
				smallnessBatchNumber.setIsRemakes(1);//1补牌
				smallnessBatchNumber.setAmount(remakesIds.length);
				smallnessBatchNumber.setSmallnessBatchNumber("BP-"+fmt.format(new Date()));
				smallnessBatchNumber.setMakeStatus(1);//制作状态:0待制1制作中2完成
				smallnessBatchNumber.setIsDistribute(1);//任务状态1任务未发2任务已发3补制中4整批审核
				boolean isRight=smallnessBatchNumberServer.add(smallnessBatchNumber);
				if(isRight){
					//添加小批号反压流程
					WorkFlow workFlow = new WorkFlow();
					workFlow.setSmallnessBatchNumber(smallnessBatchNumber);
					workFlow.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
					workFlow.setCurrentFlow(new FlowType(1));
					workFlow.setNextFlow(new FlowType(2));
					workFlowDao.add(workFlow);
					
					for(int i=0;i<remakesIds.length;i++){
						Remakes remakes=remakesServer.findById(remakesIds[i]);
						remakes.setSmallnessBatchNumber(smallnessBatchNumber);
						remakes.setState(1);
						isRight=remakesServer.updateRemakes(remakes);
						if(!isRight) throw new ChePaiException("系统错误,生成批号时异常,请联系管理员!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flowTypeId = flowTypeUtils.getFlowType(flowTypeId);
		ActionContext.getContext().put("message", "打包成功,新生成小批号,转入反压流程!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回</a>");
		return "message";
	}
	
	public String backRemakes(){
		if(remakesIds != null && remakesIds.length>0){
			for(int i=0;i<remakesIds.length; i++) {
				Remakes remakes = remakesServer.findById(remakesIds[i]);
				NumberPlate n = remakes.getNumberPlate();
				SmallnessBatchNumber smallBatch = remakes.getSmallnessBatchNumber();
					
				List<Remakes> realRemakes = remakesServer.findByPlateId(n.getId());
				//如果是补制
				if(realRemakes.size() > 1) {
					Remakes back = realRemakes.get(1);
					NumberPlate backN = back.getNumberPlate();
					SmallnessBatchNumber backSmall = back.getSmallnessBatchNumber();
					
					//删除废牌
					if(backN.getLicensePlateNumber().indexOf("副") > 0) {
						List<DisuseMaterial> ds = disuseMaterialServer.findBySmallandPlate(smallBatch.getId(), n.getId());
						for(int j=0; j<2; j++) {
							DisuseMaterial d = ds.get(j);
							disuseMaterialServer.delete(d);
						}
					} else {
						List<DisuseMaterial> ds = disuseMaterialServer.findBySmallandPlate(smallBatch.getId(), n.getId());
						disuseMaterialServer.delete(ds.get(0));
					}
					//修改车牌名称
					if(backN.getLicensePlateNumber().indexOf("-") > 0) {
						backN.setLicensePlateNumber(backN.getLicensePlateNumber().substring(0,backN.getLicensePlateNumber().indexOf("_")) + "_补制整副");
						numberPlateServer.update(backN);
					}
					back.setState(1);
					remakesServer.updateRemakes(back);
					remakesServer.delete(realRemakes.get(0));
					
					WorkFlow backFlow = workFlowServer.findBySbatchNumberId(backSmall.getId());
					if(backFlow.getCurrentFlow().getId() == 8) {
						backFlow.setCurrentFlow(new FlowType(5));
						backFlow.setNextFlow(new FlowType(6));
						workFlowServer.update(backFlow);
						TaskAllocation backTask = taskAllocationServer.findBySBatchNumberAndFlowType(backSmall.getId(), 5);
						backTask.setStatus(0);
						taskAllocationServer.update(backTask);
						operatorServer.delBySmallAndFlow(backSmall.getId(), 5);
						individualOutputServer.delBySmallAndFlow(backSmall.getId(),5);
					}
					
					
				} else { //普通类型
					disuseMaterialServer.delBySmallandPlate(smallBatch.getId(), n.getId());
					n.setLicensePlateNumber(n.getLicensePlateNumber().substring(0,n.getLicensePlateNumber().indexOf("_")));
					numberPlateServer.update(n);
					remakesServer.delete(remakes);
					
					if(!remakesServer.findSmallIsRemakes(smallBatch.getId())) {
						smallBatch.setIsDistribute(2);
						smallnessBatchNumberServer.update(smallBatch);
					}
				}
				
			}
		}
		ActionContext.getContext().put("message", "回退成功!<br/><a href='Remakes!queryList.action'>返回操作</a>");
		return "message";
	}
	
	

	public int[] getNumberPlateIds() {
		return numberPlateIds;
	}

	public void setNumberPlateIds(int[] numberPlateIds) {
		this.numberPlateIds = numberPlateIds;
	}

	public List<Remakes> getRemakesList() {
		return remakesList;
	}

	public void setRemakesList(List<Remakes> remakesList) {
		this.remakesList = remakesList;
	}
	public int[] getRemakesIds() {
		return remakesIds;
	}
	public void setRemakesIds(int[] remakesIds) {
		this.remakesIds = remakesIds;
	}
	public int getIsRemakes() {
		return isRemakes;
	}
	public void setIsRemakes(int isRemakes) {
		this.isRemakes = isRemakes;
	}
	public int getSmallnessBatchNumberId() {
		return smallnessBatchNumberId;
	}
	public void setSmallnessBatchNumberId(int smallnessBatchNumberId) {
		this.smallnessBatchNumberId = smallnessBatchNumberId;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public int getErrorFlow() {
		return errorFlow;
	}
	public void setErrorFlow(int errorFlow) {
		this.errorFlow = errorFlow;
	}

	public int getMarType() {
		return marType;
	}

	public void setMarType(int marType) {
		this.marType = marType;
	}

	public int[] getFrontNumberIds() {
		return frontNumberIds;
	}

	public void setFrontNumberIds(int[] frontNumberIds) {
		this.frontNumberIds = frontNumberIds;
	}

	public int[] getOffsideNumberIds() {
		return offsideNumberIds;
	}

	public void setOffsideNumberIds(int[] offsideNumberIds) {
		this.offsideNumberIds = offsideNumberIds;
	}
}
