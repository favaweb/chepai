package com.hovto.chepai.privilege;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.hovto.chepai.model.Users;

@Entity
public class Roler {

	private int id;
	private String name;
	private List<Privilege> privileges;
	private List<Users> users;
	
	public Roler() {}
	
	public Roler(int id) {
		super();
		this.id = id;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Roler other = (Roler) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@ManyToMany
	@JoinTable(
			name="roler_privilege",
			joinColumns={@JoinColumn(name="rolerId")},
			inverseJoinColumns={@JoinColumn(name="privilegeId")}
	)
	public List<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	@ManyToMany(mappedBy="rolers")
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
	
	
}
