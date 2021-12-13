package com.lippujarjestelma.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lippujarjestelma.domain.OrderDetails;
import com.lippujarjestelma.domain.Ticket;
import com.lippujarjestelma.domain.TicketRepository;
import com.lippujarjestelma.domain.UserRepository;
import com.lippujarjestelma.utils.AuthenticationHelper;

@RequestMapping("/api/tickets")
@CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
@RestController
public class TicketControllerRest {
	@Autowired
	private TicketRepository trepository;

	@Autowired
	private UserRepository urepository;

	AuthenticationHelper auth = new AuthenticationHelper();

	@GetMapping("/")
	public @ResponseBody List<Ticket> TicketListRest() {
		return (List<Ticket>) this.trepository.findAll();
	}

	@GetMapping("/ticket/{id}")
	public @ResponseBody Optional<Ticket> findEventRest(@PathVariable("id") Long ticketId) {
		return trepository.findById(ticketId);
	}

	@GetMapping("/user")
	public @ResponseBody List<Ticket> UserTicketList() {
		String username = auth.generateAuthUsername();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (OrderDetails order : urepository.findByUsername(username).getOrders()) {
			for (Ticket ticket : order.getTickets()) {
				ticketList.add(ticket);
			}
		}
		return ticketList;
	}

}
