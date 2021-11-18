package com.lippujarjestelma.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Venue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long venueId;
	private String name;
	private String address; // address class later
	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
	private List<Event> events;

	public Venue() {
	}

	public Venue(String name, String description, String address) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}

	public long getVenueId() {
		return venueId;
	}

	public void setVenueId(long venueId) {
		this.venueId = venueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Avenue [venueId=" + venueId + ", name=" + name + ", address=" + address + ", description=" + description
				+ "]";
	}

}
