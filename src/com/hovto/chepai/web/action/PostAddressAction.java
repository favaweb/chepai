package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BusinessDepartment;
import com.hovto.chepai.model.PostAddress;
import com.hovto.chepai.server.BusinessDepartmentServer;
import com.hovto.chepai.server.PostAddressServer;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class PostAddressAction {
	@Resource
	private PostAddressServer postAddressServer;
	@Resource
	private BusinessDepartmentServer bDepatmentServer;
	
	private PostAddress postAddress;
	private List<PostAddress> postAddressList;
	private List<BusinessDepartment> bDepartments;
	
	public String add(){
		validate();
		postAddressServer.add(postAddress);
		ActionContext.getContext().put("message", "添加发货地址成功!<a href='PostAddress!list.action'>返回</a>");
		return "message";
	}
	
	public String addInput(){
		bDepartments = bDepatmentServer.findList();
		return "addInput";
	}
	
	public String modify(){
		validate();
		postAddressServer.modify(postAddress);
		ActionContext.getContext().put("message", "修改发货地址成功!<a href='PostAddress!list.action'>返回</a>");
		return "message";
	}
	
	public String list(){
		postAddressList = postAddressServer.findAllPostAddress();
		return "list";
	}
	
	public String detail(){
		if(postAddress.getId() == 0) 
			throw new ChePaiException("请提交资料。");
		postAddress = postAddressServer.find(postAddress.getId());
		bDepartments = bDepatmentServer.findList();
		return "detail";
	}
	
	
	
	
	
	
	
	
	/**
	 * 验证
	 */
	private void validate(){
		if(postAddress== null){
			throw new ChePaiException("请提交资料。");
		}
		if(postAddress.getDepartment()==null){
			throw new ChePaiException("业务部门不可为空。");
		}
		if(postAddress.getPlace()==null || postAddress.getPlace().trim().equals("") ){
			throw new ChePaiException("地区不可为空。");
		}
		if(postAddress.getTelephone() == null || postAddress.getTelephone().trim().equals("") ){
			throw new ChePaiException("联系电话不可为空。");
		}
		if(postAddress.getReceiver() == null || postAddress.getReceiver().trim().equals("")){
			throw new ChePaiException("联系人不可为空。");
		}
		if(postAddress.getAddress() == null || postAddress.getAddress().trim().equals("")){
			throw new ChePaiException("地址不可为空。");
		}
	}
	
	public PostAddress getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(PostAddress postAddress) {
		this.postAddress = postAddress;
	}
	public List<PostAddress> getPostAddressList() {
		return postAddressList;
	}
	public void setPostAddressList(List<PostAddress> postAddressList) {
		this.postAddressList = postAddressList;
	}
	public List<BusinessDepartment> getBDepartments() {
		return bDepartments;
	}
	public void setBDepartments(List<BusinessDepartment> departments) {
		bDepartments = departments;
	}
}
