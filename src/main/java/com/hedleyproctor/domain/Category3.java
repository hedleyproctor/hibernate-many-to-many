package com.hedleyproctor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Category3 {
	private Long id;
	private String name;
	private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();
	
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
	
	@ElementCollection
	@CollectionTable(name = "CATEGORIZED_ITEMS", joinColumns = @JoinColumn(name = "CATEGORY_ID"))
	public Set<CategorizedItem> getCategorizedItems() {
		return categorizedItems;
	}
	public void setCategorizedItems(Set<CategorizedItem> categorizedItems) {
		this.categorizedItems = categorizedItems;
	}
	
	
}
