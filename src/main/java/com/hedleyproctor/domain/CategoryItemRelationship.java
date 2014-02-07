package com.hedleyproctor.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY_ITEM_RELATIONSHIP")
public class CategoryItemRelationship {
	
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
	private Category2 category;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
	private Item2 item;
	private Date dateAdded;

	@Embeddable
	public static class Id implements Serializable {
		@Column(name = "CATEGORY_ID")
		private Long categoryId;
		@Column(name = "ITEM_ID")
		private Long itemId;
		
		public Id() {}
		
		public Id(Long categoryId, Long itemId) {
			this.categoryId = categoryId;
			this.itemId = itemId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Id) {
				Id that = (Id)obj;
				return this.categoryId.equals(that.categoryId) && this.itemId.equals(that.itemId);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return itemId.hashCode() + categoryId.hashCode();
		}
		
		
	}
	
	public CategoryItemRelationship(Category2 category, Item2 item, Date dateAdded) {
		this.category = category;
		this.item = item;
		this.dateAdded = dateAdded;
		this.getId().categoryId = category.getId();
		this.getId().itemId = item.getId();
		category.getCategoryItemRelationships().add(this);
		item.getCategoryItemRelationships().add(this);
	}
	
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Category2 getCategory() {
		return category;
	}
	public void setCategory(Category2 category) {
		this.category = category;
	}
	public Item2 getItem() {
		return item;
	}
	public void setItem(Item2 item) {
		this.item = item;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	
	
	
	
	

}
