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
	
	private int[] numberPlateIds;//�������
	private List<Remakes> remakesList;
	private int[] remakesIds;//����id
	private int isRemakes;//1����2�ϲ�
	private int smallnessBatchNumberId;
	private int flowTypeId;
	private int errorFlow;//���ƴ�������
	private int marType;//��������
	private int[] frontNumberIds;//����ǰ�ƺ�
	private int[] offsideNumberIds;//������ƺ�
	Map<Integer,Integer> plates = new HashMap<Integer, Integer>();
	
	public String queryList(){
		remakesList=remakesServer.queryList();
		return "list";
	}
	
	
	/**
	 * ���� ���Ƶ�ǰ��,���� �����Ƴ��Ƶ� ��Ӧ��ϵ
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
	
	//�ʼ����÷���
	public void saveFeeNumberPlate(){
		if(frontNumberIds == null && offsideNumberIds == null)
			throw new ChePaiException("���Ƴ��Ʋ�����Ϊ��!<a href='javascript:window.history.go(-1);'>���ز���</a>");
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
		
		
		//��ӷ�ѹǰ�Ʒ��Ƽ�¼
		this.addFor(frontNumberIds, frontMap,offsideMap,taskAllocation);
		//��ӷ�ѹ���Ʒ��Ƽ�¼
		this.addFor(offsideNumberIds, offsideMap,frontMap,taskAllocation);
	}
	
	
	/**
	 * ��¼ ǰ�����
	 * @param numberIds
	 * @param map
	 * @param taskAllocation
	 */
	public void addFor(int[] numberIds,Map<Integer,Integer> map,Map<Integer,Integer> mapElse,TaskAllocation taskAllocation){
		if (numberIds == null) {
			return;
		}
		//��¼������
		for(int i=0;i<numberIds.length;i++){
			NumberPlate numberPlate=numberPlateServer.findById(numberIds[i]);
			DisuseMaterial disuseMaterial=new DisuseMaterial();
			disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
			disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
			disuseMaterial.setNumberPlate(numberPlate);
			disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
			disuseMaterial.setOperateTime(new Date());
			if(taskAllocation==null){ throw new ChePaiException("�ó��Ʋ����������̣�������ѡ��<a href='Remakes!queryList.action'>����</a>");}
			disuseMaterial.setFlowType(taskAllocation.getFlowType());
			disuseMaterial.setMarType(marType);
			disuseMaterial.setOperator(taskAllocation.getOperator());
			
			if(taskAllocation.getOperator() != null) {
				Users u = userServer.find(new Users(Integer.parseInt(taskAllocation.getOperator().split(",")[0])));
				disuseMaterial.setDayNight(u.getDayNight());
			}
			
			//�ж� �Ƿ� ���� ǰ�ƻ����
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
		//��ѯ��С���ŷ����δ������� (��ʵҪ�ҳ����ľ��� �ʼ������)
		List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberId, workFlow.getCurrentFlow().getId(),0);
		if(taskList != null && taskList.size()>0){
			TaskAllocation taskAllocation=new TaskAllocation();	
			taskAllocation=taskList.get(0);
			
			//��Ӳ�����
			Operator operator=new Operator();
			operator.setFlowType(workFlow.getCurrentFlow());
			operator.setOperater(taskAllocation.getOperator());
			operator.setSmallnessBatchNumber(smallnessBatchNumber);
			operator.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
			operatorServer.save(operator);//��Ӳ�����
			
			
			//��Ӹ��˲��� ����С���Ÿ� ���� ��Ӹ��˲���
			individualOutputServer.save(workFlow.getCurrentFlow(), smallnessBatchNumber);
			
			
			//�޸������������״̬ ������Ϊ����
			taskAllocation.setStatus(1);
			taskAllocationServer.update(taskAllocation);
		}
	}
	/*//���ɷ��Ƽ�¼
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
	 * ����
	 */
	public String save(){
		try {
			if(marType == 6) {
				//��ӷ��Ƽ�¼
				this.saveFeeNumberPlate();
			} else {
				
				this.saveFeeNumberPlate();
				if(numberPlateIds.length>0){
					
					//�ж��Ƿ�С���Ų�Ϊ����
					if(isRemakes!=1){
						
						//��Ӳ��Ƴ��� �޸ĳ��Ʋ���ǰ����
						for(int i=0;i<numberPlateIds.length;i++){
							this.addRemakes(numberPlateIds[i]);
						}
						
						//����С����Ϊ ����
						SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
						smallnessBatchNumber.setIsDistribute(3);
						smallnessBatchNumberServer.update(smallnessBatchNumber);
						
						/**
						WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
						//��ѯ��С���ŷ����δ������� (��ʵҪ�ҳ����ľ��� �ʼ������)
						List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberId, workFlow.getCurrentFlow().getId(),0);
						if(taskList != null && taskList.size()>0){
							TaskAllocation taskAllocation=new TaskAllocation();	
							taskAllocation=taskList.get(0);
							
							//�޸������������״̬ ������Ϊ����
							taskAllocation.setStatus(2);
							taskAllocationServer.update(taskAllocation);
						}
						**/
						
					}else{//���С������ ���²���
						
						
						
						
						//�������в��Ƴ���
						for(int i=0;i<numberPlateIds.length;i++){
							//�������ȵ��õ�����
							NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
							
							
							//�޸ĳ��� ����, ������Ǽ�������ǰ�Ƹ�����,�����޸�,��� ��������,�� �ж� �Ƿ��п��ܲ���ǰ�ƻ� ����
							int index = numberPlate.getLicensePlateNumber().indexOf("_");
							String backStr = numberPlate.getLicensePlateNumber().substring(index+1,numberPlate.getLicensePlateNumber().length());
							if(backStr.equals("��������")) {
								if(plates.get(numberPlateIds[i]) == 1) {
									numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("����", "ǰ��-"));
								} else if(plates.get(numberPlateIds[i]) == 2) {
									numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("����", "����-"));
								}
							}
							
							//�ж���������²���,�򽫲��� ״̬ �޸ĳ� ������
							Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
							remakes.setState(2);
							remakesServer.updateRemakes(remakes);
							//�����Ʊ� �����һ���ظ����Ƶ� ��¼
							this.addRepeatRemakes(numberPlateIds[i]);
							
							/**
							 * 1.���� ���Ƽ�¼Ϊ���
							 * 2.����µĶ�Ӧ�Ĳ��Ƽ�¼
							 * 	
							 * (1,2���Ժϳ� setStateΪ0)
							 * 
							 * 3.  �жϵ�ǰ�������������Ƴ����Ƿ�ȫ������List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
							 * 		���ȫ�������, �ҵ�����,�ҵ�С����, �������,��Ӳ�����,��Ӹ��˲���						 * 
							 * */
							
							 
							/**
							 * С��ʼ
							 * 
							 * 
							//����С���� �� ���ƺ� ��ѯ��¼
							Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
							//����и� ���Ƽ�¼
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
							*С�����
							***/
							
							
							
						}
						
						//����Ա�� �п������������ ������...���Ի���   �жϵ�ǰ�������������Ƴ����Ƿ�ȫ������
						List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
						if(list.size()==0){
							WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
							if(workFlow!=null){
								SmallnessBatchNumber sBatchNumber=workFlow.getSmallnessBatchNumber();
								sBatchNumber.setBoxNumber(0);
								sBatchNumber.setBoxNumberType(0);
								smallnessBatchNumberServer.update(sBatchNumber);
								
								//��ѯ��С���ŷ��������
								TaskAllocation taskAllocation=new TaskAllocation();	
								taskAllocation=taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, workFlow.getCurrentFlow().getId());
								
								//��Ӳ�����
								Operator operator=new Operator();
								operator.setFlowType(workFlow.getCurrentFlow());
								operator.setOperater(taskAllocation.getOperator());
								operator.setSmallnessBatchNumber(new SmallnessBatchNumber(smallnessBatchNumberId));
								operatorServer.add(operator);//��Ӳ�����
								
								individualOutputServer.save(workFlow.getCurrentFlow(), new SmallnessBatchNumber(smallnessBatchNumberId));//��Ӹ��˲���
								 	
								//�޸������������״̬
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
		ActionContext.getContext().put("message", "�ɹ����������,�뵽�������������!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>����</a>");
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
	 * ������ɷ���
	 * @return
	 */
	public String remakesFinish(){
		//���ú� Ҫ���Ƶ� ǰ�Ƹ� ��̨
		this.setPlates();
		//����һ��������������    ԭ����С����
		Map<Integer,SmallnessBatchNumber> map=new HashMap<Integer,SmallnessBatchNumber>();
		
		
		if(numberPlateIds.length>0){
			/**
			 *1.��� �ǲ���ǰ�Ƹ�����,ֱ����һ��...
			 *2.��� ��������, �ж� �Ƿ�ȫ��ѡ��
			 */
			
			//ѭ���޸�ѡ�����Ƴ��Ʋ���ȡ��ʵС����
			for(int i=0;i<numberPlateIds.length;i++){
				
				//�������ȵ��õ�����
				NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
				
				
				//�޸ĳ��� ����, ������Ǽ�������ǰ�Ƹ�����,�����޸�,��� ��������,�� �ж� �Ƿ��п��ܲ���ǰ�ƻ� ����
				int index = numberPlate.getLicensePlateNumber().indexOf("_");
				String backStr = numberPlate.getLicensePlateNumber().substring(index+1,numberPlate.getLicensePlateNumber().length());
				if(backStr.equals("��������")) {
					if(plates.get(numberPlateIds[i]) == 1) {
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("����", "����"));
						//������� �ٴβ���, ���޸Ĳ���״̬
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//�����Ʊ��������һ�� �ظ����Ƶļ�¼
						this.addRepeatRemakes(numberPlateIds[i]);
					} else if(plates.get(numberPlateIds[i]) == 2) {
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().replace("����", "ǰ��"));
						//������� �ٴβ���, ���޸Ĳ���״̬
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//�����Ʊ��������һ�� �ظ����Ƶļ�¼
						this.addRepeatRemakes(numberPlateIds[i]);
					} else { //����� �������Ƴɹ�
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().substring(0,index));
						//�޸Ĳ���״̬,��������Ϊ���
						Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
						remakes.setState(2);
						remakesServer.updateRemakes(remakes);
						//ʵ�ʳ��Ƶ�С����
						boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber());
						//��� û�и�С����, ���� ���ƺ� �� ʵϰС����
						if(!is)map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber());
					}
				} else { //����� ����ǰ�� �����ƺ��Ƶ� ...��˵�� �ó����Ѿ��������
					if(index!=-1)  
						numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber().substring(0,index));
					//�޸Ĳ���״̬,��������Ϊ���
					Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
					remakes.setState(2);
					remakesServer.updateRemakes(remakes);
					//ʵ�ʳ��Ƶ�С����
					boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber());
					//��� û�и�С����, ���� ���ƺ� �� ʵϰС����
					if(!is)
						map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber());
				}
				
				
				/**
				 * 
				 * С��ʼ
				 *
				���ݲ��Ƶ�С���� �� ���ƺ��ҵ����Ƽ�¼ �������ó�Ϊ �������
				Remakes remakes=remakesServer.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, numberPlateIds[i]);
				remakes.setState(2);
				boolean str=remakesServer.updateRemakes(remakes);
				if(str){
					//ʵ�ʳ��Ƶ�С����
					boolean is=map.containsValue(remakes.getNumberPlate().getSmallnessBatchNumber().getId());
					//��� û�и�С����, ���� ���ƺ� �� ʵϰС����
					if(!is)map.put(numberPlateIds[i], remakes.getNumberPlate().getSmallnessBatchNumber().getId());
				}
				*
				*С�����
				*
				**/
			}
			
			
			
			
			
			//��ѯ��ȫ��û���ʼ�������Ƴ���
			List<Remakes> resultRemakes=remakesServer.findByState();
			//ѭ���ж� ������� �ĳ����Ƿ����ʼ�ȫ��ͨ��С����
			for(int i=0;i<numberPlateIds.length;i++){
				boolean pass=true;
				boolean is=map.containsKey(numberPlateIds[i]);
				if(is){
					//�õ���ʵС����
					SmallnessBatchNumber sbatchNumberId=map.get(numberPlateIds[i]);
					//ѭ���ж��Ƿ� ���Ƴ����Ƿ��� ��ʵС����..
					for(int j=0;j<resultRemakes.size();j++){
						Remakes remakes=(Remakes)resultRemakes.get(j);
						if(remakes.getNumberPlate().getSmallnessBatchNumber().getId()==sbatchNumberId.getId()){
							pass=false;
						}
					}
					//�жϸ�С���ŵ����Ƴ����Ƿ�ȫ���ʼ����, ��������״̬,����С��������״̬Ϊ δ��
					if(pass){
						this.updateOperatorAndIndividualAndTask(sbatchNumberId.getId());
						boolean isSucceed= workFlowServer.updateBySbatchNumberId(sbatchNumberId.getId(),2);
						sbatchNumberId.setIsDistribute(1);
						smallnessBatchNumberServer.update(sbatchNumberId);
					}
				}
			}
			
			
			//�жϵ�ǰ�������������Ƴ����Ƿ�ȫ������ ���ȫ�������, �ҵ�����,�ҵ�С����, �������,��Ӳ�����,��Ӹ��˲���	
			List<Remakes> list= remakesServer.findResultRemakes(smallnessBatchNumberId);
			if(list.size()==0){
				WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberId);
				if(workFlow!=null){
					SmallnessBatchNumber sBatchNumber=workFlow.getSmallnessBatchNumber();
					sBatchNumber.setBoxNumber(0);
					sBatchNumber.setBoxNumberType(0);
					smallnessBatchNumberServer.update(sBatchNumber);
					
					//��ѯ��С���ŷ��������
					TaskAllocation taskAllocation=new TaskAllocation();	
					taskAllocation=taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, workFlow.getCurrentFlow().getId());
					//��Ӳ�����
					Operator operator=new Operator();
					operator.setFlowType(workFlow.getCurrentFlow());
					operator.setOperater(taskAllocation.getOperator());
					operator.setSmallnessBatchNumber(new SmallnessBatchNumber(smallnessBatchNumberId));
					operatorServer.add(operator);//��Ӳ�����
					
					individualOutputServer.save(workFlow.getCurrentFlow(), new SmallnessBatchNumber(smallnessBatchNumberId));//��Ӹ��˲���
					//�޸������������״̬
					taskAllocation.setStatus(1);
					taskAllocationServer.update(taskAllocation);
					
					workFlow.setCurrentFlow(new FlowType(8));
					workFlow.setNextFlow(new FlowType(8));
					workFlowServer.update(workFlow);
					
				}
			}
		}
		ActionContext.getContext().put("message", "�������!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>����</a>");
		return "message";
	}
	
	/**
	 * ���һ�� �����ظ���������
	 * @param numberPlateId
	 */
	public void addRemakes(int numberPlateId){
		NumberPlate numberPlate=numberPlateServer.findById(numberPlateId);
		
		
		//�޸ĳ��ƵĲ������� ״̬
		if(plates.containsKey(numberPlateId)) {
			if(plates.get(numberPlateId) == 1) {
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_����ǰ��");
			} else if(plates.get(numberPlateId) == 2) { 
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_���ƺ���");
			} else { 
				numberPlate.setLicensePlateNumber(numberPlate.getLicensePlateNumber() + "_��������");
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
	 * ���һ�� �ظ���������
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
				smallnessBatchNumber.setOrderType(1);//��������1����
				smallnessBatchNumber.setDateTime(new Date());
				smallnessBatchNumber.setPrecedence(1);//���ȼ���
				smallnessBatchNumber.setIsRemakes(1);//1����
				smallnessBatchNumber.setAmount(remakesIds.length);
				smallnessBatchNumber.setSmallnessBatchNumber("BP-"+fmt.format(new Date()));
				smallnessBatchNumber.setMakeStatus(1);//����״̬:0����1������2���
				smallnessBatchNumber.setIsDistribute(1);//����״̬1����δ��2�����ѷ�3������4�������
				boolean isRight=smallnessBatchNumberServer.add(smallnessBatchNumber);
				if(isRight){
					//���С���ŷ�ѹ����
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
						if(!isRight) throw new ChePaiException("ϵͳ����,��������ʱ�쳣,����ϵ����Ա!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flowTypeId = flowTypeUtils.getFlowType(flowTypeId);
		ActionContext.getContext().put("message", "����ɹ�,������С����,ת�뷴ѹ����!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>����</a>");
		return "message";
	}
	
	public String backRemakes(){
		if(remakesIds != null && remakesIds.length>0){
			for(int i=0;i<remakesIds.length; i++) {
				Remakes remakes = remakesServer.findById(remakesIds[i]);
				NumberPlate n = remakes.getNumberPlate();
				SmallnessBatchNumber smallBatch = remakes.getSmallnessBatchNumber();
					
				List<Remakes> realRemakes = remakesServer.findByPlateId(n.getId());
				//����ǲ���
				if(realRemakes.size() > 1) {
					Remakes back = realRemakes.get(1);
					NumberPlate backN = back.getNumberPlate();
					SmallnessBatchNumber backSmall = back.getSmallnessBatchNumber();
					
					//ɾ������
					if(backN.getLicensePlateNumber().indexOf("��") > 0) {
						List<DisuseMaterial> ds = disuseMaterialServer.findBySmallandPlate(smallBatch.getId(), n.getId());
						for(int j=0; j<2; j++) {
							DisuseMaterial d = ds.get(j);
							disuseMaterialServer.delete(d);
						}
					} else {
						List<DisuseMaterial> ds = disuseMaterialServer.findBySmallandPlate(smallBatch.getId(), n.getId());
						disuseMaterialServer.delete(ds.get(0));
					}
					//�޸ĳ�������
					if(backN.getLicensePlateNumber().indexOf("-") > 0) {
						backN.setLicensePlateNumber(backN.getLicensePlateNumber().substring(0,backN.getLicensePlateNumber().indexOf("_")) + "_��������");
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
					
					
				} else { //��ͨ����
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
		ActionContext.getContext().put("message", "���˳ɹ�!<br/><a href='Remakes!queryList.action'>���ز���</a>");
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
