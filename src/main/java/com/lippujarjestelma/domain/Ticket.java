package com.lippujarjestelma.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ticketId;

	@ManyToOne
	@JoinColumn(name = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "eventId")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "orderId")
	private OrderDetails order;

	private Boolean redeemed = false;

	public Ticket() {
	}

	public Ticket(User user, Event event, OrderDetails order, String avenueDesc, Boolean redeemed) {
		super();
		this.user = user;
		this.event = event;
		this.redeemed = redeemed;
		this.order = order;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Boolean getRedeemed() {
		return redeemed;
	}

	public void setRedeemed(Boolean redeemed) {
		this.redeemed = redeemed;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", userId=" + user + ", event=" + event + ", avenueDesc="
				+ ", redeemed=" + redeemed + "]";
	}

}
