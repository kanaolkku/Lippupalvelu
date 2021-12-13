package com.lippujarjestelma.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventId;
	private String title;
	private String description;
	private double price;
	private String startTime;
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "Event_Category", joinColumns = { @JoinColumn(name = "event_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private List<Category> category;

	@ManyToOne
	@JoinColumn(name = "venueId")
	private Venue venue;

	private String startDate;

	private String startDateFormatted;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private List<Ticket> ticketlist;

	private int ticketLimit;

	public Event() {
	}

	public Event(String title, String description, double price, User user, List<Category> category, Venue venue,
			String startDateString, String endDateString, int ticketLimit, String startTime) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.user = user;
		this.category = category;
		this.venue = venue;
		this.startDateFormatted = startDateString;
		this.startDate = calcDate(startDateString);
		this.ticketLimit = ticketLimit;
		this.startTime = startTime;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDateString) {
		this.startDate = calcDate(startDateString);
		this.startDateFormatted = startDateString;
	}

	public String getStartDateFormatted() {
		return startDateFormatted;
	}

	public void setStartDateFormatted(String startDateFormatted) {
		this.startDateFormatted = startDateFormatted;
		this.startDate = calcDate(startDateFormatted);
	}

	public int getTicketLimit() {
		return ticketLimit;
	}

	public void setTicketLimit(int ticketLimit) {
		this.ticketLimit = ticketLimit;
	}

	public List<Ticket> getTicketlist() {
		return ticketlist;
	}

	public void setTicketlist(List<Ticket> ticketlist) {
		this.ticketlist = ticketlist;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int ticketCount() {
		if (ticketlist.size() < 1) {
			return 0;
		} else {
			return ticketlist.size();
		}

	}

	public boolean checkAvailability() {
		if (ticketCount() == ticketLimit) {
			return false;
		} else {
			return true;
		}
	}

	private String calcDate(String dateString) {
		String[] stringlist = dateString.split("-");

		String year = stringlist[0];
		String month = stringlist[1];
		String day = stringlist[2];

		String dateFormatted = day + "." + month + "." + year;

		return dateFormatted;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", user=" + user + ", category=" + category + ", avenue=" + venue + ", startDate=" + startDate
				+ ", endDate=" + ", ticketlist=" + ticketlist + ", ticketLimit=" + ticketLimit + "]";
	}

}
