package com.hovto.chepai.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BatchRefashion;
import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Remakes;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.BatchNumberMergeServer;
import com.hovto.chepai.server.BatchRefashionServer;
import com.hovto.chepai.server.DisuseMaterialServer;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.server.RemakesServer;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class BatchRefashionAction {
	@Resource
	private BatchRefashionServer batchRefashionServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private NumberPlateServer numberPlateServer;
	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private BatchNumberMergeServer batchNumberMergeServer;
	@Resource
	private DisuseMaterialServer disuseMaterialServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	@Resource
	private RemakesServer remakesServer;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;
	@Resource
	private UserServer userServer;
	
	
	public SmallnessBatchNumber smallnessBatchNumber;
	public int[] smallnessBatchNumberIds;
	public int[] batchRefashionIds;
	public List<BatchRefashion> batchRefashionList;
	private int flowTypeId;
	public List<FlowType> flowTypes;//����������
	private int errorFlow;//���ƴ�������
	private int marType;//��������
	
	public String save(){
		if(smallnessBatchNumberIds!=null){
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				BatchRefashion batchRefashion=new BatchRefashion();
				SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberIds[i]);
				batchRefashion.setSmallnessBatchNumber(smallnessBatchNumber);
				batchRefashion.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
				batchRefashion.setStatus(1);
				batchRefashionServer.save(batchRefashion);//���һ������������Ϣ
				
				
				//smallnessBatchNumber.setIsValid(1);
				//smallnessBatchNumber.setBoxNumber(0);
				smallnessBatchNumber.setIsDistribute(4);//�����
				smallnessBatchNumberServer.update(smallnessBatchNumber);//����Ҫ���Ƶ������ű�Ϊ��������
				List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberId(smallnessBatchNumber.getId());
				for(int j=0;j<taskList.size();j++){
					TaskAllocation taskAllocation=taskList.get(j);
					taskAllocation.setStatus(2);
					taskAllocationServer.update(taskAllocation);
				}
			}
			ActionContext.getContext().put("message", "�������Ƴɹ�,ת�������!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
			return "message";
		} else
			throw new ChePaiException("��ѡ��Ҫ�����������Ƶ�С����!");
	}
	/**
	 * ͣ��
	 * @return
	 */
	public String stopfor(){
		if(smallnessBatchNumberIds!=null){
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberIds[i]);
				smallnessBatchNumber.setIsDistribute(5); //ͣ����
				smallnessBatchNumberServer.update(smallnessBatchNumber);
			}
			
			ActionContext.getContext().put("message", "ͣ�Ƴɹ�!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
			return "message";
		}else{
			throw new ChePaiException("��ѡ��Ҫ�����������Ƶ�С����!<a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
		}
	}
	
	/**
	 * ȡ��ͣ��
	 * @return
	 */
	public String celstopfor(){
		if(smallnessBatchNumber!=null){
			smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumber.getId());
			smallnessBatchNumber.setIsDistribute(2); //�ָ��ѷ�״̬
			smallnessBatchNumberServer.update(smallnessBatchNumber);
			ActionContext.getContext().put("message", "�����ɹ�!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
			return "message";
		}else{
			throw new ChePaiException("����ʧ��!<a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
		}
	}
	
	public String back(){
		batchRefashionServer.back(batchRefashionIds);
		ActionContext.getContext().put("message", "���˳ɹ�!<br/><a href='BatchRefashion!findByStatus.action'>���ز���</a>");
		return "message";
	}
	
	//���ɷ��Ƽ�¼
	public void addDisuseMaterial(SmallnessBatchNumber sb) {
		try {
			List<SemifinishedProductType> sp= semifinishedProductTypeServer.findList();
			Map<Integer,int[]> map=new HashMap<Integer,int[]>();
			for(int i=0;i<sp.size();i++){
				for(int j=0;j<sp.get(i).getNumberPlateTypes().size();j++){
					if(map.get(sp.get(i).getNumberPlateTypes().get(j).getId()) == null)
						map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{sp.get(i).getId()});
					else
						map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{map.get(sp.get(i).getNumberPlateTypes().get(j).getId())[0],sp.get(i).getId()});
				}
			}
			
			List<NumberPlate> numberPlateList=numberPlateServer.findBySmallnessBatchId(sb.getId());
			TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(sb.getId(), errorFlow);
			for(int j=0;j<numberPlateList.size();j++){
				NumberPlate numberPlate=numberPlateList.get(j);
				
				int index = 0;
				int typeId = numberPlate.getNumberPlateType().getId();
				for(int i=0;i<numberPlate.getNumberPlateType().getNumber();i++) {
					DisuseMaterial disuseMaterial=new DisuseMaterial();
					disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
					disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
					disuseMaterial.setNumberPlate(numberPlate);
					disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
					disuseMaterial.setOperateTime(new Date());
					disuseMaterial.setFlowType(new FlowType(errorFlow));
					disuseMaterial.setMarType(marType);
					disuseMaterial.setOperator(taskAllocation.getOperator());
					if(taskAllocation.getOperator() != null) {
						Users u = userServer.find(new Users(Integer.parseInt(taskAllocation.getOperator().split(",")[0])));
						disuseMaterial.setDayNight(u.getDayNight());
					}
					
					
					if(map.containsKey(typeId)) {
						if(map.get(typeId).length > 1) {
							disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(typeId)[index]));
							index ++;
						} else {
							disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(typeId)[index]));
						}
					}
					
					disuseMaterialServer.add(disuseMaterial);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String findByStatus(){
		batchRefashionList=batchRefashionServer.findByStatus(1);
		return "list";
	}
	
	
	public void updateworkFlow(SmallnessBatchNumber smallnessBatchNumber){
		smallnessBatchNumber.setIsValid(1);//���ǰ������
		smallnessBatchNumber.setBoxNumber(0);//������
		smallnessBatchNumber.setBoxNumberType(0);
		smallnessBatchNumber.setIsDistribute(4);//������
		smallnessBatchNumberServer.update(smallnessBatchNumber);//����Ҫ���Ƶ������ű�Ϊ��������
		WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumber.getId());
		workFlow.setCurrentFlow(new FlowType(8,null));
		workFlow.setNextFlow(new FlowType(8,null));
		workFlowServer.update(workFlow);//�ı�����״̬
	}
	
	public String update(){
		try {
			if(batchRefashionIds!=null){
				for(int i=0;i<batchRefashionIds.length;i++){
					/*//��ѯ��С���ŷ��������
					List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberId(smallnessBatchNumberIds[i]);
					if(taskAllocationList.size()>0){
						//�޸������������״̬
						TaskAllocation taskAllocation=taskAllocationList.get(0);
						taskAllocation.setStatus(1);
						taskAllocationServer.update(taskAllocation);//�޸�����������
					}*/
					
					BatchRefashion batchRefashion=batchRefashionServer.findById(batchRefashionIds[i]);
					SmallnessBatchNumber sb=batchRefashion.getSmallnessBatchNumber();
					
					List<NumberPlate> numberPlateList=numberPlateServer.findBySmallnessBatchId(sb.getId());
					SmallnessBatchNumber refashion=new SmallnessBatchNumber(sb.getBigBatchNumber(),sb.getSmallnessBatchNumber(),sb.getOrderType(),sb.getPlace(),sb.getOtherName(),sb.getAmount(),sb.getDateTime(),1,sb.getIsRemakes(),0,1,1,0,0,sb.getNumberPlateType());
					
					smallnessBatchNumberServer.add(refashion);//�������һ��������
					
					//���С���ŷ�ѹ����
					WorkFlow workFlow = new WorkFlow();
					workFlow.setSmallnessBatchNumber(refashion);
					workFlow.setBigBatchNumber(refashion.getBigBatchNumber());
					workFlow.setCurrentFlow(new FlowType(1));
					workFlow.setNextFlow(new FlowType(2));
					workFlowDao.add(workFlow);
					
					this.addDisuseMaterial(sb);//���ɷ��Ƽ�¼
					this.updateworkFlow(sb);//�ı�ԭС����״̬
					for(int j=0;j<numberPlateList.size();j++){
						NumberPlate numberPlate=(NumberPlate)numberPlateList.get(j);
						numberPlate.setSmallnessBatchNumber(refashion);
						numberPlateServer.update(numberPlate);//�ı䳵�ƶ�Ӧ��С���� 
					}

					if(sb.getIsRemakes()==2){//�ж��Ƿ�ϲ�
						List<BatchNumberMerge> batchList=batchNumberMergeServer.findMergerBySmallId(sb.getId());
						for(int j=0;j<batchList.size();j++){
							BatchNumberMerge batchNumberMerge=new BatchNumberMerge(refashion,batchList.get(j).getBigBatchNumber());
							batchNumberMergeServer.add(batchNumberMerge);
						}
					}else if(sb.getIsRemakes()==1){
						List<Remakes> remakesList=remakesServer.findResultRemakes(sb.getId());
							for(int j=0;j<remakesList.size();j++){
								Remakes remakes=remakesList.get(j);
								remakes.setSmallnessBatchNumber(refashion);
								remakesServer.updateRemakes(remakes);
							}
					}
					batchRefashion.setRefashionSmallnessBatchNumber(refashion);//���������ر��ж�Ӧ
					batchRefashion.setStatus(2);
					batchRefashionServer.update(batchRefashion);
				}
				ActionContext.getContext().put("message", "���Ƴɹ�,ת�������,�������·�����!<br/><a href='BatchRefashion!findByStatus.action'>���ز���</a>");
				return "message";
			} else
				throw new ChePaiException("��ѡ��Ҫ���Ƶ�С����");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int[] getSmallnessBatchNumberIds() {
		return smallnessBatchNumberIds;
	}

	public void setSmallnessBatchNumberIds(int[] smallnessBatchNumberIds) {
		this.smallnessBatchNumberIds = smallnessBatchNumberIds;
	}
	public int[] getBatchRefashionIds() {
		return batchRefashionIds;
	}
	public void setBatchRefashionIds(int[] batchRefashionIds) {
		this.batchRefashionIds = batchRefashionIds;
	}
	public List<BatchRefashion> getBatchRefashionList() {
		return batchRefashionList;
	}
	public void setBatchRefashionList(List<BatchRefashion> batchRefashionList) {
		this.batchRefashionList = batchRefashionList;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public List<FlowType> getFlowTypes() {
		return flowTypeServer.findList();
	}
	public void setFlowTypes(List<FlowType> flowTypes) {
		this.flowTypes = flowTypes;
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
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}

}
