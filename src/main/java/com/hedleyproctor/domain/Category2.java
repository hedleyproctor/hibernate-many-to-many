package com.hedleyproctor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category2 {
	private Long id;
	private String name;
	private Set<CategoryItemRelationship> categoryItemRelationships = new HashSet<CategoryItemRelationship>();
	
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
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	public Set<CategoryItemRelationship> getCategoryItemRelationships() {
		return categoryItemRelationships;
	}

	public void setCategoryItemRelationships(Set<CategoryItemRelationship> categoryItemRelationships) {
		this.categoryItemRelationships = categoryItemRelationships;
	}

}
