package com.lippujarjestelma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lippujarjestelma.domain.CategoryRepository;
import com.lippujarjestelma.domain.EventRepository;
import com.lippujarjestelma.domain.OrderRepository;
import com.lippujarjestelma.domain.TicketRepository;
import com.lippujarjestelma.domain.UserRepository;
import com.lippujarjestelma.domain.VenueRepository;

@SpringBootApplication
public class TicketApplication {

	private static final Logger log = LoggerFactory.getLogger(TicketApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(EventRepository erepository, CategoryRepository crepository,
			UserRepository urepository, TicketRepository trepository, VenueRepository vrepository,
			OrderRepository orepository) {
		return (args) -> {
			/*
			 * // delete existing entries erepository.deleteAll(); crepository.deleteAll();
			 * 
			 * log.info("adding categories"); // add categories crepository.save(new
			 * Category("Football")); crepository.save(new Category("Food"));
			 * crepository.save(new Category("Rock")); crepository.save(new
			 * Category("Metal")); crepository.save(new Category("Rap"));
			 * crepository.save(new Category("Kulttuuri")); crepository.save(new
			 * Category("Musical"));
			 * 
			 * // add avenues vrepository.save(new Venue("Venue 1",
			 * "Very nice place in Kamppi", "kampinjokukatu 3")); vrepository.save(new
			 * Venue("Venue 2", "Very nice place in Kamppi", "kampinjokukatu 4"));
			 * vrepository.save(new Venue("Venue 3", "Very nice place in Kamppi",
			 * "kampinjokukatu 5")); vrepository.save(new Venue("Venue 4",
			 * "Very nice place in Kamppi", "kampinjokukatu 6")); vrepository.save(new
			 * Venue("Venue 5", "Very nice place in Kamppi", "kampinjokukatu 7"));
			 * log.info("adding books"); // add books
			 * 
			 * User user1 = new User("user",
			 * "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER",
			 * "user1@mail.com"); User user2 = new User("admin",
			 * "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN",
			 * "admin@mail.com"); User user3 = new User("host",
			 * "$2a$10$xugaw2oi4QD6g1hF8.n9yupWduIeK68RfbDD4xd3QzDk5zCvp9nDS", "HOST",
			 * "host@mail.com"); User user4 = new User("ghost",
			 * "$2a$10$xugaw2oi4QD6g1hF8.n9yupWduIeK68RfbDD4xd3QzDk5zCvp9nDS", "HOST",
			 * "host@mail.com"); urepository.save(user1); urepository.save(user2);
			 * urepository.save(user3); urepository.save(user4);
			 * 
			 * log.info("Hello before event"); Event event1 = new Event();
			 * erepository.save(event1); event1.setTitle("Testieventti");
			 * event1.setUser(user2); event1.setCategory(
			 * Arrays.asList(crepository.findByName("Football").get(0),
			 * crepository.findByName("Metal").get(0)));
			 * event1.setDescription("testi tapahtuma"); event1.setTicketLimit(10);
			 * event1.setStartDate("2021-03-15"); event1.setStartTime("13:00");
			 * event1.setPrice(20);
			 * event1.setVenue(vrepository.findByName("Venue 3").get(0));
			 * 
			 * Event event2 = new Event(); erepository.save(event2);
			 * event2.setTitle("Testieventti 2"); event2.setUser(user3); event2.setCategory(
			 * Arrays.asList(crepository.findByName("Football").get(0),
			 * crepository.findByName("Metal").get(0)));
			 * event2.setDescription("testi tapahtuma 2"); event2.setTicketLimit(10);
			 * event2.setStartDate("2021-04-15"); event2.setStartTime("12:00");
			 * event2.setPrice(20);
			 * event2.setVenue(vrepository.findByName("Venue 2").get(0));
			 * 
			 * Event event3 = new Event(); erepository.save(event3);
			 * event3.setTitle("Testieventti 3"); event3.setUser(user4); event3.setCategory(
			 * Arrays.asList(crepository.findByName("Football").get(0),
			 * crepository.findByName("Metal").get(0)));
			 * event3.setDescription("testi tapahtuma 2"); event3.setTicketLimit(10);
			 * event3.setStartDate("2021-05-25"); event3.setStartTime("18:00");
			 * event3.setPrice(20);
			 * event3.setVenue(vrepository.findByName("Venue 1").get(0));
			 * 
			 * erepository.save(event1); erepository.save(event2); erepository.save(event3);
			 * 
			 * // add orders OrderDetails order1 = new OrderDetails();
			 * order1.setUser(user4); order1.setOrderDate(new Date());
			 * 
			 * OrderDetails order2 = new OrderDetails(); order2.setUser(user2);
			 * order2.setOrderDate(new Date());
			 * 
			 * OrderDetails order3 = new OrderDetails(); order3.setUser(user1);
			 * order3.setOrderDate(new Date());
			 * 
			 * orepository.save(order1); orepository.save(order2); orepository.save(order3);
			 * 
			 * // add tickets Ticket ticket1 = new Ticket(); ticket1.setEvent(event1);
			 * ticket1.setOrder(order1);
			 * 
			 * Ticket ticket2 = new Ticket(); ticket2.setEvent(event1);
			 * ticket2.setOrder(order1);
			 * 
			 * Ticket ticket3 = new Ticket(); ticket3.setEvent(event1);
			 * ticket3.setOrder(order2);
			 * 
			 * Ticket ticket4 = new Ticket(); ticket4.setEvent(event2);
			 * ticket4.setOrder(order2);
			 * 
			 * Ticket ticket5 = new Ticket(); ticket5.setEvent(event3);
			 * ticket5.setOrder(order3);
			 * 
			 * Ticket ticket6 = new Ticket(); ticket6.setEvent(event3);
			 * ticket6.setOrder(order3);
			 * 
			 * log.info("adding tickets"); trepository.save(ticket1);
			 * trepository.save(ticket2); trepository.save(ticket3);
			 * trepository.save(ticket4); trepository.save(ticket5);
			 * trepository.save(ticket6);
			 */
		};
	}

}
