package com.hovto.chepai.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.UserDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.privilege.Privilege;
import com.hovto.chepai.privilege.Roler;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class UserServer {

	@Resource
	private UserDao userDao;
	
	public void add(Users user) {
		if(user == null) throw new ChePaiException("用户资料不允许为空<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		Users u = userDao.findAccount(user.getAccount());
		if(u != null) throw new ChePaiException("该账户已经存在!<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		else userDao.add(user);
	}
	
	public void modify(Users user) {
		if(user != null) {
			Users u = userDao.find(user);
			u.setDayNight(user.getDayNight());
			u.setIsvalid(user.getIsvalid());
			u.setName(user.getName());
			u.setRoler(user.getRoler());
			u.setSex(user.isSex());
			userDao.modify(u);
		}
	}
	
	public void modifyRoler(Users user, int[] rolers) {
		user = userDao.find(user);
		List<Roler> currentRolers = null;
		if(rolers != null) {
			currentRolers = new ArrayList<Roler>();
			for(int i=0; i<rolers.length; i++) {
				currentRolers.add(new Roler(rolers[i]));
			}
		}
		user.setRolers(currentRolers);
		userDao.modify(user);
	}
	public List<Users> findAll(){
		return userDao.findAll();
	}
	public Users find(Users user) {
		return userDao.find(user);
	}
	
	public List<Users> findList(Page page,int roler,int daynight,String username) {
		if(roler == -1 && daynight ==-1 && username.equals(""))
			return userDao.findList(page);
		else
			return userDao.findListByCondition(page, roler, daynight, username);
	}
	
	
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void login(Users user, Map<String, Object> session) {
		if(user != null) {
			Users realAdmin = userDao.find(user.getAccount());
			if(realAdmin == null)
				throw new ChePaiException("没有该用戶!<br/><a href='javascript:window.history.go(-1);'></a>");
			else if(!realAdmin.getPassword().equals(user.getPassword()))
				throw new ChePaiException("密码错误!<br/><a href='javascript:window.history.go(-1);'></a>");
			for(Roler roler : realAdmin.getRolers()) {
				for(Privilege privilege : roler.getPrivileges()) {
					privilege.getModel();
				}
			}
			session.put("user", realAdmin);
		}
	}

	public void getUserPrivileges(Map<String, Object> session,
			List<Privilege> onePrivileges, List<Privilege> twoPrivileges, List<Privilege> threePrivileges, List<Privilege> fourPrivileges, List<Privilege> fivePrivileges) {
		Users user = (Users) session.get("user");
		if(user != null) {
			for(Roler roler : user.getRolers()) {
				for(Privilege privilege : roler.getPrivileges()) {
					
					if(privilege.getCode() == 1) 
						if(!onePrivileges.contains(privilege))
							onePrivileges.add(privilege);
					
					if(privilege.getCode() == 2) 
						if(!twoPrivileges.contains(privilege))
							twoPrivileges.add(privilege);
					
					if(privilege.getCode() == 3) 
						if(!threePrivileges.contains(privilege))
							threePrivileges.add(privilege);
					
					if(privilege.getCode() == 4) 
						if(!fourPrivileges.contains(privilege))
							fourPrivileges.add(privilege);
					
					if(privilege.getCode() == 5) 
						if(!fivePrivileges.contains(privilege))
							fivePrivileges.add(privilege);
					
				}
			}
		} else 
			throw new ChePaiException("您还没有登陆,没有权限查看,请点击下面链接登陆<br/><a href='index.jsp' target='_parent'>登陆</a>");
	}

	public void modify(Users user, String nowPassword, String modifyPassword,
			String confirmPassword, Map<String, Object> session) {
		if(modifyPassword == null || !modifyPassword.trim().equals(confirmPassword.trim()))
			throw new ChePaiException("俩次密码输入不一致!<br/><a href='javascript:window.history.go(-1);'></a>");
		user = userDao.find(user);
		if(!user.getPassword().equals(nowPassword))
			throw new ChePaiException("现在的密码不对,请重新输入!<br/><a href='javascript:window.history.go(-1);'></a>");
		user.setPassword(modifyPassword);
		userDao.modify(user);
		session.remove("user");
	}

	/**
	 * 根据 流程查询 所有员工
	 * @param flowTypeId 流程id
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Users> findByFlowType(int flowTypeId) {
		return userDao.findByPrivilege(this.findPrivilege(flowTypeId));
	}

	private String findPrivilege(int flowTypeId) {
		switch (flowTypeId) {
			case 1:
				return "反压";
			case 2:
				return "洗牌";
			case 3:
				return "滚油";
			case 4:
				return "正压";
			case 5:
				return "质检";
			case 6:
				return "打码";
			case 7:
				return "总质检";
			default:
				throw new ChePaiException("无法识别的流程类型!<br/><a href='javascript:window.history.go(-1);'></a>");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
