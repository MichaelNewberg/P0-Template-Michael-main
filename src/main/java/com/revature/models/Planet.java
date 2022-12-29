package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="planets")
public class Planet {
	@Id
	@Column(name="planet_id")
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="owner_Id")
	private int ownerId;

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

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", ownerId=" + ownerId + "]";
	}
}
