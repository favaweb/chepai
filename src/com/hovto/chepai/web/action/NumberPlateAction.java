package com.hovto.chepai.web.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.NumberPlateType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TypeStats;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.BatchNumberMergeServer;
import com.hovto.chepai.server.BigBatchNumberServer;
import com.hovto.chepai.server.BusinessDepartmentServer;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.server.NumberPlateTypeServer;
import com.hovto.chepai.server.RemakesServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.hovto.chepai.tool.DelDir;
import com.hovto.chepai.tool.parseExcel;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class NumberPlateAction {
	@Resource
	private NumberPlateServer numberPlateServer;
	@Resource
	private NumberPlateTypeServer numberPlateTypeServer;
	@Resource
	private BusinessDepartmentServer bDepartmentServer;
	@Resource
	private BigBatchNumberServer bigBatchNumberServer;
	@Resource
	private ExcelUtils excelUtils;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private RemakesServer remakesServer;
	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	@Resource
	private BatchNumberMergeServer batchNumberMergeServer;
	
	
	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private int flowTypeId;
	private List<NumberPlate> numberPlates;
	private NumberPlate numberPlate;
	private int smallnessBatchNumberId;
	private int isDistribute;
	private List<WorkFlow> workFlows;
	private List<Users> users;
	private List<FlowType> flowTypes;
	private List<List<NumberPlate>> nums;
	private int[] numberPlateIds;
	private int isRemakes;//1����2�ϲ�
	private String boxNumber;
	private String boxType;
	private SmallnessBatchNumber smallnessBatchNumber;
	private int[] smallnessBatchNumberIds;
	private List<TypeStats> typeStatses; 
	private String dateTime;
	private int print;
	private File file;
	private String remoteIp;
	private String[] orderTypes;
	private int plateType;
	private String operator;
	private List<BatchNumberMerge> batchNumberMerges;
	private List<SmallnessBatchNumber> smallnessBatchNumbers;
	
	public String importInput() {
		return "importInput";
	}
	
	public String importFiles() throws IOException {
		
		//��ȡҵ��������
		Map bDepartmentList=bDepartmentServer.queryAll();
		//��ȡ������������
		Map numberTypeList=numberPlateTypeServer.queryAll();
		
		String message = numberPlateServer.importFiles(orderTypes,remoteIp,bDepartmentList,numberTypeList);
		if(message == null)
			message = "���ݵ���ɹ�!";
		
		ActionContext.getContext().put("message", message + "<a href='NumberPlate!importInput.action'>���ز���</a>");
		return "message";
	}
	
	
	
	//����Excel����
	@SuppressWarnings("deprecation")
	public String save(){
		try {
			//��ȡҵ��������
			Map bDepartmentList=bDepartmentServer.queryAll();
			//��ȡ������������
			Map numberTypeList=numberPlateTypeServer.queryAll();
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request=ServletActionContext.getRequest();
			
			response.setContentType("utf-8");
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String fileUrl=request.getParameter("fileUrl");//��ȡ�����ļ�·��
			String filename = fileUrl.substring(fileUrl.lastIndexOf("\\")+1);
			String tofilepath = ServletActionContext.getRequest().getRealPath("\\excel\\Import\\parse")+"\\" +filename ;
			String filepath = ServletActionContext.getRequest().getRealPath("\\excel\\Import")+"\\" +filename ;
			file = parseExcel.parse(file, filepath, tofilepath);
			//�����ļ�, ·��, ��������, ҵ���� �����ļ�
			List result=excelUtils.readExcel(file,tofilepath,bDepartmentList,numberTypeList);//���������ļ�
			DelDir.delDir(tofilepath);
			
			if(result==null){
				ActionContext.getContext().put("message", "�ظ�����<a href='javascript:window.history.go(-1);'>���ز���</a>");
				return "message";
			}
			
			
			BigBatchNumber bigBatchNumber=new BigBatchNumber();//����������bean
			//��ȡ����
			bigBatchNumber.setBigBattchNumber(fileUrl.substring(fileUrl.lastIndexOf("\\")+1, fileUrl.lastIndexOf(".")));
			bigBatchNumber.setBeginSegmentNumber(((Map)result.get(0)).get("number").toString());//��ʼ�Ŷ�
			bigBatchNumber.setEndSegmentNumber(((Map)result.get(result.size()-1)).get("number").toString());//�����Ŷ�)
			bigBatchNumber.setAmount(result.size());//����;
			String[] path=fileUrl.split("\\\\");
			bigBatchNumber.setPlace(path[path.length-2]);//��ַ
			String orderType=path[path.length-3];//����
			bigBatchNumber.setOrderType(orderTypeConvert(orderType));//���ͣ�3�ŶΣ�2��ѡ��1����
			bigBatchNumber.setOtherName(((Map)result.get(0)).get("otherName").toString());//��������
			int precedence=2;
			if(orderType.equals("�Ŷ�")){
				precedence=3;//�Ŷ�Ĭ��
				bigBatchNumber.setNumberPlateType(new NumberPlateType(Integer.parseInt(((Map)result.get(0)).get("licensePlateType").toString())));
				excelUtils.setSame(true);
			} else if(excelUtils.isSame()) {
				bigBatchNumber.setNumberPlateType(new NumberPlateType(Integer.parseInt(((Map)result.get(0)).get("licensePlateType").toString())));
				excelUtils.setSame(true);
			} else {
				bigBatchNumber.setNumberPlateType(new NumberPlateType(18));
				excelUtils.setSame(true);
			}
			bigBatchNumber.setPrecedence(precedence);
			bigBatchNumber.setCreateTime(new Date());//����ʱ��
			boolean is=bigBatchNumberServer.add(bigBatchNumber);
			if(is){
				numberPlateServer.save(result, bigBatchNumber);
			}
		} catch(ChePaiException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionContext.getContext().put("message", "���ݵ���ɹ�!<a href='NumberPlate!importInput.action'>���ز���</a>");
		return "message";
	}
	public int orderTypeConvert(String orderType){
		if(orderType.equals("����")){
			return 1;
		}else if(orderType.equals("��ѡ")){
			return 2;
		}else if(orderType.equals("�Ŷ�")){
			return 3;
		}
		throw new ChePaiException("�޷�ʶ��������");
	}
	
	
	/**
	 * �ж�����Ƿ��ظ�
	 * @return
	 */
	public void boxNumberIsExist(){
		if(boxNumber!=null&&!"".equals(boxNumber)&&boxType != null && !"".equals(boxType)){
			int bnumber = 0;
			int bType = 0;
			try {
				bnumber = Integer.parseInt(boxNumber);
				bType = Integer.parseInt(boxType);
			} catch (Exception e) {
				throw new ChePaiException("���������Ÿ�ʽ����,����������,����������<a href='javascript:window.history.go(-1);'>���ز���</a>");
			}
			if(bnumber <= 0) throw new ChePaiException("��ű������0");
			long isExitBox = smallnessBatchNumberServer.findByBoxNumber(bnumber,bType);
			if(isExitBox > 0) throw new ChePaiException("����ظ�!������ѡ��!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
		} else
			throw new ChePaiException("��Ÿ�������ɫ������Ϊ��!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
	}
	
	
	public String shuffleUpdate(){
		try {
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				workFlowServer.shuffleAndBarotropyUpdate(smallnessBatchNumberIds[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionContext.getContext().put("message", "�����ύ�ɹ�,�����¸�����!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
		return "message";
	}
	
	
	/**
	 * �ı�����������״̬
	 * @return
	 * @throws IOException 
	 */
	public String updateByStuts() throws IOException{
		/*workFlowServer.updateBySbatchNumberId(smallnessBatchNumberId);*/
		
/*		//���ʼ��ύ���Զ�����excel����ӡ
		if(flowTypeId ==7 && print==1 ){
			List<NumberPlate> numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
			ExcelReport.export2(numberPlates, numberPlates.get(0).getBigBatchNumber().getBigBattchNumber(), ServletActionContext.getRequest().getRealPath("\\excel\\"));
			String path = ServletActionContext.getRequest().getRealPath("\\excel\\")+"\\"+numberPlates.get(0).getBigBatchNumber().getBigBattchNumber()+".xls";
			JacobPrint.print(path,null,1);
		}*/
		
		//�ڷ�ѹ��ʱ���ж�����Ƿ��ظ�
		if (flowTypeId==1) {
			this.boxNumberIsExist();
		}
		
		if(flowTypeId==1||flowTypeId==3){
			//��ѹ�͹����ύ��һ���̷���
			workFlowServer.counterpressureUpdate(smallnessBatchNumberId, boxNumber,boxType,isRemakes);
		}else if(flowTypeId==2||flowTypeId==4){
			//ϴ�ƺ���ѹ�ύ��һ���̷���
			workFlowServer.shuffleAndBarotropyUpdate(smallnessBatchNumberId);
		}else{
			workFlowServer.updateBySbatchNumberId(smallnessBatchNumberId,0);
		}
		
		String url = "�����ύ�ɹ�,�����¸�����!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId;
		if(plateType != 0) 
			url += "&plateType=" + plateType;
		url += "'>���ز���</a>";
		ActionContext.getContext().put("message", url);
		return "message";
	}
	
	public String goNextFlow() {
		
		if(smallnessBatchNumberIds != null) {
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				if(flowTypeId==3){
					//��ѹ�͹����ύ��һ���̷���
					workFlowServer.counterpressureUpdate(smallnessBatchNumberIds[i], boxNumber,boxType,isRemakes);
				}else if(flowTypeId==4){
					//ϴ�ƺ���ѹ�ύ��һ���̷���
					workFlowServer.shuffleAndBarotropyUpdate(smallnessBatchNumberIds[i]);
				}else{
					workFlowServer.updateBySbatchNumberId(smallnessBatchNumberIds[i],0);
				}
			}
		}
		
		ActionContext.getContext().put("message", "�����ύ�ɹ�,�����¸�����!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>���ز���</a>");
		return "message";
	}
	
	public String findByOrder() {
		flowTypes=flowTypeServer.findList();
		SmallnessBatchNumber smallnessBatchNumber=new SmallnessBatchNumber();
		smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		if(smallnessBatchNumber.getIsRemakes()!=1){
			numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
		}else{
			numberPlates=remakesServer.findBySmallnessBatchNumber(smallnessBatchNumberId);
		}
		if(numberPlates == null || numberPlates.size() == 0) {
			numberPlateServer.findByHBSmallnessBatchId(smallnessBatchNumberId, numberPlates);
		}
		return "findByOrder";
	}
	
	/**
	 * 
	 * @return
	 */
	public String findBySmallnessBatchId() {
		flowTypes=flowTypeServer.findList();
		SmallnessBatchNumber smallnessBatchNumber=new SmallnessBatchNumber();
		smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		if(smallnessBatchNumber.getIsRemakes()!=1){
			numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
		}else{
			numberPlates=remakesServer.findBySmallnessBatchNumber(smallnessBatchNumberId);
		}
		if(numberPlates == null || numberPlates.size() == 0) {
			numberPlateServer.findByHBSmallnessBatchId(smallnessBatchNumberId, numberPlates);
		}
		return this.loadFlow();
	}
	
	public String makeYazhiDan() throws UnsupportedEncodingException{
		operator = new String(operator.getBytes("utf-8"),"ISO-8859-1");
		operator = new String(operator.getBytes("ISO-8859-1"),"utf-8");
		flowTypes=flowTypeServer.findList();
		smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		if(smallnessBatchNumber.getIsRemakes()!=1){
			numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
		}else{
			numberPlates=remakesServer.findBySmallnessBatchNumber(smallnessBatchNumberId);
		}
		if(numberPlates == null || numberPlates.size() == 0) {
			numberPlateServer.findByHBSmallnessBatchId(smallnessBatchNumberId, numberPlates);
		}
		return "makeYazhiDan";
	}
	
	public String makeBaozhiDan() {
		flowTypes=flowTypeServer.findList();
		smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		if(smallnessBatchNumber.getIsRemakes()!=1){
			numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
		}else{
			numberPlates=remakesServer.findBySmallnessBatchNumber(smallnessBatchNumberId);
		}
		if(numberPlates == null || numberPlates.size() == 0) {
			numberPlateServer.findByHBSmallnessBatchId(smallnessBatchNumberId, numberPlates);
		}
		return "makeBaozhiDan";
	}

	public String packPrint(){
		smallnessBatchNumber = smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		numberPlates = numberPlateServer.findBySmallnessBatchId(smallnessBatchNumberId);
		return "packPrint";
	}
	
	public String mergepackPrint(){
		batchNumberMerges = batchNumberMergeServer.findMergerBySmallId(smallnessBatchNumberId);
		smallnessBatchNumbers = new ArrayList<SmallnessBatchNumber>();
		for(BatchNumberMerge merge:batchNumberMerges){
			smallnessBatchNumbers.add(
					smallnessBatchNumberServer.findSmallnessBatchNumberbyBid(
							merge.getBigBatchNumber().getId()));
		}
		nums = new ArrayList<List<NumberPlate>>();
		for(SmallnessBatchNumber small:smallnessBatchNumbers){
			numberPlates = numberPlateServer.findBySmallnessBatchId(small.getId());
			nums.add(numberPlates);
		}
		
		return "mergepackPrint";
	}
	
	public String typeStats() {
		typeStatses = numberPlateServer.typeStats(dateTime);
		return "typeStats";
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
	public List<NumberPlate> getNumberPlates() {
		return numberPlates;
	}
	public void setNumberPlates(List<NumberPlate> numberPlates) {
		this.numberPlates = numberPlates;
	}
	public NumberPlate getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(NumberPlate numberPlate) {
		this.numberPlate = numberPlate;
	}
	public int getSmallnessBatchNumberId() {
		return smallnessBatchNumberId;
	}
	public void setSmallnessBatchNumberId(int smallnessBatchNumberId) {
		this.smallnessBatchNumberId = smallnessBatchNumberId;
	}
	public int[] getNumberPlateIds() {
		return numberPlateIds;
	}
	public void setNumberPlateIds(int[] numberPlateIds) {
		this.numberPlateIds = numberPlateIds;
	}
	public int getIsDistribute() {
		return isDistribute;
	}
	public void setIsDistribute(int isDistribute) {
		this.isDistribute = isDistribute;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public int getIsRemakes() {
		return isRemakes;
	}
	public void setIsRemakes(int isRemakes) {
		this.isRemakes = isRemakes;
	}
	public List<WorkFlow> getWorkFlows() {
		return workFlows;
	}
	public void setWorkFlows(List<WorkFlow> workFlows) {
		this.workFlows = workFlows;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public List<FlowType> getFlowTypes() {
		return flowTypes;
	}
	public void setFlowTypes(List<FlowType> flowTypes) {
		this.flowTypes = flowTypes;
	}
	public String getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}
	public int[] getSmallnessBatchNumberIds() {
		return smallnessBatchNumberIds;
	}
	public void setSmallnessBatchNumberIds(int[] smallnessBatchNumberIds) {
		this.smallnessBatchNumberIds = smallnessBatchNumberIds;
	}
	public List<TypeStats> getTypeStatses() {
		return typeStatses;
	}
	public void setTypeStatses(List<TypeStats> typeStatses) {
		this.typeStatses = typeStatses;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getPrint() {
		return print;
	}
	public void setPrint(int print) {
		this.print = print;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String[] getOrderTypes() {
		return orderTypes;
	}

	public void setOrderTypes(String[] orderTypes) {
		this.orderTypes = orderTypes;
	}

	public int getPlateType() {
		return plateType;
	}

	public void setPlateType(int plateType) {
		this.plateType = plateType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<BatchNumberMerge> getBatchNumberMerges() {
		return batchNumberMerges;
	}

	public void setBatchNumberMerges(List<BatchNumberMerge> batchNumberMerges) {
		this.batchNumberMerges = batchNumberMerges;
	}


	public List<SmallnessBatchNumber> getSmallnessBatchNumbers() {
		return smallnessBatchNumbers;
	}

	public void setSmallnessBatchNumbers(
			List<SmallnessBatchNumber> smallnessBatchNumbers) {
		this.smallnessBatchNumbers = smallnessBatchNumbers;
	}

	public List<List<NumberPlate>> getNums() {
		return nums;
	}

	public void setNums(List<List<NumberPlate>> nums) {
		this.nums = nums;
	}
	
	
	
	
	
}