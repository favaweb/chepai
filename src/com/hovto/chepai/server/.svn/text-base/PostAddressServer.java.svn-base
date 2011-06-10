package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.PostAddressDao;
import com.hovto.chepai.model.BusinessDepartment;
import com.hovto.chepai.model.PostAddress;

@Component
@Transactional
public class PostAddressServer {
	@Resource
	private PostAddressDao postAddressDao;
	
	public PostAddress find(int id){
		try {
			return postAddressDao.find(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Ìí¼Ó
	 */
	public boolean add(PostAddress postAddress){
		try {
			postAddressDao.add(postAddress);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean modify(PostAddress postAddress){
		try {
			postAddressDao.modify(postAddress);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<PostAddress> findAllPostAddress(){
		try {
			return postAddressDao.findAllPostAddress();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PostAddress> findPostAddressByDepartment(String department){
		try {
			return postAddressDao.findPostAddressbyDepartment(department);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PostAddress> findPostAddress(String place,String department){
		List<PostAddress> list;
		try {
			list = postAddressDao.findPostAddressbyDepartment(department);
			if(validate(list)){
				return list;
			}else{
				list = postAddressDao.findPostAddressbyPlace(place);
				if(validate(list)){
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean validate(List<PostAddress> list){
		if(list==null){
			return false;
		}
		else if(list.size()<=0){
			return false;
		}
		else{
			return true;
		}
	}
}
