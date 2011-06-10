package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.OperatorDao;
import com.hovto.chepai.dao.SmallnessBatchNumberDao;
import com.hovto.chepai.dao.UserDao;
import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;

@Component
@Transactional
public class OperatorServer {
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private SmallnessBatchNumberDao smallnessBatchNumberDao;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private UserDao userDao;
	
	
	public boolean add(Operator operator){
		try {
			operatorDao.add(operator);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断选择用户是否还有任务未完成
	 * @param operater
	 * @param flowType
	 * @return
	 */
	public boolean findByOperater(String operater,int flowTypeId){
		List<Operator> operatorList=operatorDao.findByOperater(operater);
		for(int i=0;i<operatorList.size();i++){
			Operator operator=(Operator)operatorList.get(i);
			if(operator.getFlowType().getId()==flowTypeId){
				return false;
			}
		}
		return true;
	}
	/**
	 * 添加操作人
	 * @param userId 操作人id
	 * @param smallnessBatchNumber 子批号对象
	 * @return
	 */
	public boolean addOperator(String userId,SmallnessBatchNumber smallnessBatchNumber){
		try {
			Operator operator=new Operator();
			operator.setOperater(userId);
			operator.setSmallnessBatchNumber(smallnessBatchNumber);
			if(smallnessBatchNumber.getBigBatchNumber()!=null){
				operator.setBigBatchNumber(new BigBatchNumber(smallnessBatchNumber.getBigBatchNumber().getId()));
			}
			List<WorkFlow> list=workFlowDao.findBySbatchNumberId(smallnessBatchNumber.getId());
			if(list.size()>0){
				operator.setFlowType(new FlowType(((WorkFlow)list.get(0)).getCurrentFlow().getId(),null));
				operatorDao.add(operator);//添加操作人
				smallnessBatchNumber.setIsDistribute(2);
				smallnessBatchNumberDao.modify(smallnessBatchNumber);
				//修改半成品数量materialServer.findByNumberPlateType(smallnessBatchNumber.getId);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean save(Operator operator){
		try {
			operatorDao.add(operator);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 该方法没有用到
	 * 给对象分配任务
	 * @param userId 操作人
	 * @param sum 可分配批数
	 * @return
	 public boolean save(String userId,int sum,int workFlowId,String boxNumber){
		try {
			String[] boxNumbers=null;
			if(boxNumber!=null&&!"".equals(boxNumber)){
				boxNumbers=boxNumber.split(",");
			}
			
			//List<SmallnessBatchNumber> result=smallnessBatchNumberDao.findByIsDistribute();
			List<WorkFlow> resultFlow=workFlowDao.findByIsDistribute(workFlowId);//查询出该流程下的待发车牌
			int resultLength=resultFlow.size();
			if(resultLength>sum){//判断待批结果是否大于待分人数*
				resultLength=sum;
			}
			for(int i=0;i<resultLength;i++){
				SmallnessBatchNumber sBatchNumber=(SmallnessBatchNumber)resultFlow.get(i).getSmallnessBatchNumber();
				if(boxNumbers!=null&&!"".equals(boxNumbers)){
					sBatchNumber.setBoxNumber(Integer.parseInt(boxNumbers[i]));
				}
				this.addOperator(userId, sBatchNumber);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
	
	/**
	 * 根据小批号 查询操作人
	 * @param smallBatchId
	 * @return
	 */
	public List<Operator> findBySmallBatchId(int smallBatchId) {
		List<Operator> operators =  operatorDao.findBySmallBatchId(smallBatchId);
		if(operators != null && operators.size() != 0) {
			for(Operator o : operators) {
				String[] sOperators = o.getOperater().split(",");
				for(String s : sOperators) {
					Users user = userDao.find(new Users(Integer.parseInt(s)));
					o.getUsers().add(user);
				}
			}
		}
		return operators;
	}
	public void delBySmallAndFlow(int smallId, int flow) {
		operatorDao.delBySmallAndFlow(smallId,flow);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
