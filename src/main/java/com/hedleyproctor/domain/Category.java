package com.hedleyproctor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Category {
	private Long id;
	private String name;
	private Set<Item> items = new HashSet<Item>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "CATEGORY_ITEM",
		joinColumns = { @JoinColumn(name = "CATEGORY_ID")},
		inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")}
		)
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	
	
}
