package com.hovto.chepai.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * �Զ���������
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
		//���session���Ѿ���ֵ����˵���Ѿ���¼
		if(session != null && session.getAttribute("user") != null) {
//long currentTime = System.currentTimeMillis();
//System.out.println("--------currentTime :\t" + currentTime);
			String str = invocation.invoke();
//long endTime = System.currentTimeMillis();
//System.out.println("----------------------:intecetorEndTime:\t" + endTime);
//System.out.println("------------------------------inteceptor:\t" + (endTime - currentTime) + "\t-----------------");
			return str;
		//����ת�� ��¼ҳ��
		} else 
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		return null;
	}

}
