package com.lippujarjestelma.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long categoryid;

	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "category")
	private List<Event> events;

	public Category() {
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setBooks(List<Event> events) {
		this.events = events;
	}

	public long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(long id) {
		this.categoryid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", name=" + name + ", events=" + events + "]";
	}

}
