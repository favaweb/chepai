package com.hovto.chepai.server;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.TaskInstallDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.TaskInstall;

@Component
@Transactional
public class TaskInstallServer {

	@Resource
	private TaskInstallDao taskInstallDao;
	
	public void add(TaskInstall taskInstall) {
		taskInstallDao.add(taskInstall);
	}
	
	public void modify(TaskInstall taskInstall) {
		if(taskInstall == null) throw new ChePaiException("修改内容不允许为空!");
		
		taskInstall.setInstallDate(new Date());
		taskInstallDao.modify(taskInstall);
	}
	
	public TaskInstall find(TaskInstall taskInstall) {
		return taskInstallDao.find(taskInstall);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskInstall> findList() {
		return taskInstallDao.findList();
	}
	
	
	
	
}
