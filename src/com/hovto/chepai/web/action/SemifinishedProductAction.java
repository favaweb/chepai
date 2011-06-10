package com.hovto.chepai.web.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.Reserve;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.server.InputRegisterServer;
import com.hovto.chepai.server.ReserveServer;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.tool.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class SemifinishedProductAction extends ActionSupport {

	@Resource
	private InputRegisterServer inputRegisterServer;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;
	@Resource
	private ReserveServer reserveServer;
	
	private SemifinishedProductType semifinishedProductType;
	private InputRegister inputRegister;
	private List<SemifinishedProductType> semifinishedProductTypes;
	private List<InputRegister> inputRegisters;
	private List<Reserve> reserves;
	private int type;
	private Date startDate;
	private Date endDate;
	private Page page;
	



	/**
	 * ��Ӱ��Ʒ����
	 */
	public String addType(){
		semifinishedProductTypeServer.add(semifinishedProductType);
		return "addType";
	}
	
	/**
	 * �������а��Ʒ����
	 */
	public String findTypeList(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		return "listType";
	}
	
	/**
	 * ��ӵ�������
	 */
	public String add(){
		//��֤�ǿ�
		if(inputRegister.getCreateDate()==null||inputRegister.getReductionAmount()==0
				|| inputRegister.getReductionNumber().trim().equals("")||inputRegister.getType()==0){
			throw new ChePaiException("����д��Ҫ��Ϣ����ȷ����Ϣ׼ȷ��<a href='javascript:history.go(-1);'>����</a>");
		}
		//�½�temp���󣬲�������ֵ��ȡ��������
		SemifinishedProductType temp = new SemifinishedProductType();
		temp.setId(inputRegister.getSemifinishedProductType().getId());
		//���ҹ��
		temp = semifinishedProductTypeServer.find(temp);		
		inputRegister.setStandard(temp.getStandard());
		if(inputRegisterServer.add(inputRegister))
			ActionContext.getContext().put(
					"message", 
					"��ӳɹ�<br><a href='SemifinishedProduct!findInputList.action'>�鿴������¼</a>"
					+"<br><a href='SemifinishedProduct!operateInput.action?inputRegister.type=1'>���</a>"
					+"<br><a href='SemifinishedProduct!operateInput.action?inputRegister.type=2'>����</a>"
			);
		else{throw new ChePaiException("���ʧ�ܣ�<a href='javascript:history.go(-1);'>����</a>");}
		temp = null;
		return "message";
	}
	
	/**
	 * ��������ҳ��
	 */
	public String operateInput(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		if(inputRegister.getType()==1) return "inInput";
		if(inputRegister.getType()==2) return "outInput";
		throw new ChePaiException("�������ӣ�<a href='javascript:history.go(-1);'>����</a>");
	}
	
	/**
	 * ���ҳ�����¼ҳ��
	 */
	public String findInputList(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		page = new Page();
		return "listInput";
	}
	
	/**
	 * ������ѯ������¼
	 * @return
	 */
	public String findInputListbyCondition(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		if(page==null) page = new Page();
		inputRegisters = inputRegisterServer.findList(inputRegister, startDate, endDate,page);
		return "listInput";
	}
	
	
	
	/**
	 * ɾ�������¼
	 * @return
	 */
	public String deleteInput(){
		inputRegister = inputRegisterServer.find(inputRegister);
		if (inputRegisterServer.delete(inputRegister))
			ActionContext.getContext().put("message", "ɾ���ɹ���<a href='javascript:history.go(-1);'>����</a>");
		else 
			throw new ChePaiException("ɾ��ʧ�ܣ�<a href='javascript:history.go(-1);'>����</a>");
		return "message";
	}
	
	/**
	 * �������ݵ�������ҳ��
	 * @return
	 */
	public String detailInput(){
		reserves = reserveServer.findList();
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		inputRegister = inputRegisterServer.find(inputRegister);
		return "detailInput";
	}
	
	/**
	 * �޸ĳ�����¼
	 * @return
	 */
	public String update(){
		InputRegister temp = inputRegisterServer.find(inputRegister);
		temp.setStandard(semifinishedProductTypeServer.find(temp.getSemifinishedProductType()).getStandard());  
		if(inputRegisterServer.update(inputRegister, temp)) {
			if(temp.getType()==1)
				ActionContext.getContext().put("message", "�޸ĳɹ���<a href='SemifinishedProduct!findInputList.action'>�鿴������¼</a>");
			else if(temp.getType() ==2)
				ActionContext.getContext().put("message", "�޸ĳɹ���<a href='SemifinishedProduct!findInputList.action'>�鿴������¼</a>");
		}
		else
			throw new ChePaiException("�޸�ʧ�ܣ�<a href='javascript:history.go(-1);'>����</a>");
		return "message";
	}
	
	/**
	 * ���ʣ��ҳ��
	 * @return
	 */
	public String reserveList(){
		reserves = reserveServer.findList();
		return "reserveList";
	}
	
	
	
	
	
	/* ����Ϊget,set���� */
	
	public List<SemifinishedProductType> getSemifinishedProductTypes() {
		return semifinishedProductTypes;
	}

	public void setSemifinishedProductTypes(
			List<SemifinishedProductType> semifinishedProductTypes) {
		this.semifinishedProductTypes = semifinishedProductTypes;
	}

	public SemifinishedProductType getSemifinishedProductType() {
		return semifinishedProductType;
	}

	public void setSemifinishedProductType(
			SemifinishedProductType semifinishedProductType) {
		this.semifinishedProductType = semifinishedProductType;
	}

	public InputRegister getInputRegister() {
		return inputRegister;
	}

	public void setInputRegister(InputRegister inputRegister) {
		this.inputRegister = inputRegister;
	}
	
	public List<InputRegister> getInputRegisters() {
		return inputRegisters;
	}

	public void setInputRegisters(List<InputRegister> inputRegisters) {
		this.inputRegisters = inputRegisters;
	}

	public List<Reserve> getReserves() {
		return reserves;
	}

	public void setReserves(List<Reserve> reserves) {
		this.reserves = reserves;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
