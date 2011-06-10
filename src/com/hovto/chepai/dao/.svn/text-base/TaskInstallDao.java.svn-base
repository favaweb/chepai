package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.TaskInstall;

@Component
public class TaskInstallDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void add(TaskInstall taskInstall) {
		sessionFactory.getCurrentSession().save(taskInstall);
	}

	public void modify(TaskInstall taskInstall) {
		sessionFactory.getCurrentSession().update(taskInstall);
	}

	public TaskInstall find(TaskInstall taskInstall) {
		return (TaskInstall) sessionFactory.getCurrentSession().get(
				TaskInstall.class, taskInstall.getId());
	}

	@SuppressWarnings("unchecked")
	public List<TaskInstall> findList() {
		return sessionFactory.getCurrentSession().createQuery(
				"select u from TaskInstall u").list();
	}

	public TaskInstall find(int flowType) {
		return (TaskInstall) sessionFactory.getCurrentSession().createQuery(
				"select t from TaskInstall t where t.flowType.id = :id")
				.setParameter("id", flowType).uniqueResult();
	}

}
