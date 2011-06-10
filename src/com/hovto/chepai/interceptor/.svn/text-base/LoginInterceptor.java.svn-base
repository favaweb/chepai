package com.hovto.chepai.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 自定义拦截器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginInterceptor implements Interceptor{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		//如果session中已经有值，则说明已经登录
		if(session != null && session.getAttribute("user") != null) {
//long currentTime = System.currentTimeMillis();
//System.out.println("--------currentTime :\t" + currentTime);
			String str = invocation.invoke();
//long endTime = System.currentTimeMillis();
//System.out.println("----------------------:intecetorEndTime:\t" + endTime);
//System.out.println("------------------------------inteceptor:\t" + (endTime - currentTime) + "\t-----------------");
			return str;
		//否则转到 登录页面
		} else 
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		return null;
	}

}
