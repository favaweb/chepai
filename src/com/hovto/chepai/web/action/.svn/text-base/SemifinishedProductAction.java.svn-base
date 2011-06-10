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
	 * 添加半成品类型
	 */
	public String addType(){
		semifinishedProductTypeServer.add(semifinishedProductType);
		return "addType";
	}
	
	/**
	 * 查找所有半成品类型
	 */
	public String findTypeList(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		return "listType";
	}
	
	/**
	 * 添加导入数据
	 */
	public String add(){
		//验证非空
		if(inputRegister.getCreateDate()==null||inputRegister.getReductionAmount()==0
				|| inputRegister.getReductionNumber().trim().equals("")||inputRegister.getType()==0){
			throw new ChePaiException("请填写必要信息，并确保信息准确！<a href='javascript:history.go(-1);'>返回</a>");
		}
		//新建temp对象，并对它赋值获取的类型名
		SemifinishedProductType temp = new SemifinishedProductType();
		temp.setId(inputRegister.getSemifinishedProductType().getId());
		//查找规格
		temp = semifinishedProductTypeServer.find(temp);		
		inputRegister.setStandard(temp.getStandard());
		if(inputRegisterServer.add(inputRegister))
			ActionContext.getContext().put(
					"message", 
					"添加成功<br><a href='SemifinishedProduct!findInputList.action'>查看出入库记录</a>"
					+"<br><a href='SemifinishedProduct!operateInput.action?inputRegister.type=1'>入库</a>"
					+"<br><a href='SemifinishedProduct!operateInput.action?inputRegister.type=2'>出库</a>"
			);
		else{throw new ChePaiException("添加失败！<a href='javascript:history.go(-1);'>返回</a>");}
		temp = null;
		return "message";
	}
	
	/**
	 * 导入数据页面
	 */
	public String operateInput(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		if(inputRegister.getType()==1) return "inInput";
		if(inputRegister.getType()==2) return "outInput";
		throw new ChePaiException("错误链接！<a href='javascript:history.go(-1);'>返回</a>");
	}
	
	/**
	 * 查找出入库记录页面
	 */
	public String findInputList(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		page = new Page();
		return "listInput";
	}
	
	/**
	 * 条件查询出入库记录
	 * @return
	 */
	public String findInputListbyCondition(){
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		if(page==null) page = new Page();
		inputRegisters = inputRegisterServer.findList(inputRegister, startDate, endDate,page);
		return "listInput";
	}
	
	
	
	/**
	 * 删除导入记录
	 * @return
	 */
	public String deleteInput(){
		inputRegister = inputRegisterServer.find(inputRegister);
		if (inputRegisterServer.delete(inputRegister))
			ActionContext.getContext().put("message", "删除成功！<a href='javascript:history.go(-1);'>返回</a>");
		else 
			throw new ChePaiException("删除失败！<a href='javascript:history.go(-1);'>返回</a>");
		return "message";
	}
	
	/**
	 * 单个数据导入详情页面
	 * @return
	 */
	public String detailInput(){
		reserves = reserveServer.findList();
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		inputRegister = inputRegisterServer.find(inputRegister);
		return "detailInput";
	}
	
	/**
	 * 修改出入库记录
	 * @return
	 */
	public String update(){
		InputRegister temp = inputRegisterServer.find(inputRegister);
		temp.setStandard(semifinishedProductTypeServer.find(temp.getSemifinishedProductType()).getStandard());  
		if(inputRegisterServer.update(inputRegister, temp)) {
			if(temp.getType()==1)
				ActionContext.getContext().put("message", "修改成功！<a href='SemifinishedProduct!findInputList.action'>查看出入库记录</a>");
			else if(temp.getType() ==2)
				ActionContext.getContext().put("message", "修改成功！<a href='SemifinishedProduct!findInputList.action'>查看出入库记录</a>");
		}
		else
			throw new ChePaiException("修改失败！<a href='javascript:history.go(-1);'>返回</a>");
		return "message";
	}
	
	/**
	 * 库存剩余页面
	 * @return
	 */
	public String reserveList(){
		reserves = reserveServer.findList();
		return "reserveList";
	}
	
	
	
	
	
	/* 以下为get,set方法 */
	
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
