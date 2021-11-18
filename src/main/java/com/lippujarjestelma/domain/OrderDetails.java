package com.lippujarjestelma.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;

	@ManyToOne
	@JoinColumn(name = "id")
	private User user;

	// @ManyToMany(cascade = { CascadeType.ALL })

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "")
	private List<Ticket> tickets;

	private double totalPrice;
	private Date orderDate;

	public OrderDetails() {
	}

	public OrderDetails(long orderId, User user, List<Ticket> tickets, double totalPrice, Date orderDate) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.tickets = tickets;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", user=" + user + ", events=" + tickets + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + "]";
	}

}
