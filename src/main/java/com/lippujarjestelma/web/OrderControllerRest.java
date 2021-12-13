package com.lippujarjestelma.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lippujarjestelma.TicketApplication;
import com.lippujarjestelma.domain.EventRepository;
import com.lippujarjestelma.domain.OrderDetails;
import com.lippujarjestelma.domain.OrderRepository;
import com.lippujarjestelma.domain.Ticket;
import com.lippujarjestelma.domain.TicketRepository;
import com.lippujarjestelma.domain.UserRepository;
import com.lippujarjestelma.utils.AuthenticationHelper;

@RequestMapping("/api/orders")
@CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
@RestController
public class OrderControllerRest {

	private static final Logger log = LoggerFactory.getLogger(TicketApplication.class);

	@Autowired
	private TicketRepository trepository;

	@Autowired
	private OrderRepository orepository;

	@Autowired
	private UserRepository urepository;

	@Autowired
	private EventRepository erepository;

	AuthenticationHelper auth = new AuthenticationHelper();

	@GetMapping("/")
	public @ResponseBody List<Ticket> TicketListRest() {
		return (List<Ticket>) this.trepository.findAll();
	}

	@GetMapping("/order/{id}")
	public @ResponseBody ResponseEntity<?> findOrderRest(@PathVariable("id") Long orderId) {
		OrderDetails order = orepository.findById(orderId).get();
		String username = auth.generateAuthUsername();
		if (!username.equals(order.getUser().getUsername())) {
			return ResponseEntity.badRequest().body("You do not have access to this resource");
		} else {
			List<Ticket> tickets = order.getTickets();
			Map<String, Object> map = new HashMap<>();
			map.put("Order", order);
			map.put("Tickets", tickets);

			return ResponseEntity.ok().body(map);
		}
	}

	@GetMapping("/user")
	public @ResponseBody List<OrderDetails> UserOrderList() {
		String username = auth.generateAuthUsername();
		return urepository.findByUsername(username).getOrders();
	}

	@PostMapping("/save")
	public ResponseEntity<?> handleOrder(@RequestBody List<Long> eventIdList) {
		String username = auth.generateAuthUsername();
		OrderDetails order = new OrderDetails();
		order.setUser(urepository.findByUsername(username));
		order.setOrderDate(new Date());
		orepository.save(order);

		for (Long eventId : eventIdList) {
			Ticket ticket = new Ticket();
			ticket.setEvent(erepository.findById(eventId).get());
			ticket.setOrder(order);
			trepository.save(ticket);
		}

		return ResponseEntity.ok().body("Tickets ordered successfully");
	}
}
