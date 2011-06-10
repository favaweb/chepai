package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.TaskInstall;
import com.hovto.chepai.server.TaskInstallServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class TaskInstallAction extends ActionSupport {

	@Resource
	private TaskInstallServer taskInstallServer;
	
	private List<TaskInstall> taskInstalls;
	private TaskInstall taskInstall;
	
	public String modifyInput() {
		taskInstall = taskInstallServer.find(taskInstall);
		return "modifyInput";
	}
	
	public String modify() {
		taskInstallServer.modify(taskInstall);
		ActionContext.getContext().put("message", "设置成功!<br/><a href='TaskInstall!list.action'>返回操作</a>");
		return "message";
	}
	
	public String list() {
		taskInstalls = taskInstallServer.findList();
		return "list";
	}

	public List<TaskInstall> getTaskInstalls() {
		return taskInstalls;
	}

	public void setTaskInstalls(List<TaskInstall> taskInstalls) {
		this.taskInstalls = taskInstalls;
	}

	public TaskInstall getTaskInstall() {
		return taskInstall;
	}

	public void setTaskInstall(TaskInstall taskInstall) {
		this.taskInstall = taskInstall;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
