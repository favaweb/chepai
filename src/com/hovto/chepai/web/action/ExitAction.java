package com.hovto.chepai.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class ExitAction extends ActionSupport{
	public String exit(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.getSession().invalidate();
		return "login";
	}
}
