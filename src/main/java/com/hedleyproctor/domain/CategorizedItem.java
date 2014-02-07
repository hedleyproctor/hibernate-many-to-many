package com.hedleyproctor.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class CategorizedItem {
	private String username;
	private Date dateAdded = new Date();
	private Item3 item;
	private Category3 category;
	public CategorizedItem(String username,
		Category3 category,
		Item3 item) {
		this.username = username;
		this.category = category;
		this.item = item;
	}
	
	public CategorizedItem() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CategorizedItem) {
			CategorizedItem that = (CategorizedItem)obj;
			return this.category.equals(that.getCategory()) && this.item.equals(that.getItem());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return item.hashCode() + category.hashCode();
	}

	@Column(name = "USERNAME", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "ADDED_ON", nullable = false, updatable = false)
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@ManyToOne
	@JoinColumn(name = "ITEM_ID", nullable = false, updatable = false)
	public Item3 getItem() {
		return item;
	}

	public void setItem(Item3 item) {
		this.item = item;
	}

	// you won't typically need to navigate back to the parent category, since this
	// categorized item is a component, you can't query for it and if you have access to it,
	// you should already have access to the parent, but I've included this annotation to show
	// how it works. I don't believe JPA has an equivalent mechanism.
	@org.hibernate.annotations.Parent
	public Category3 getCategory() {
		return category;
	}

	public void setCategory(Category3 category) {
		this.category = category;
	}
	
	
}
