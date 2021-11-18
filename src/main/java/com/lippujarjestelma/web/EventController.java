package com.lippujarjestelma.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lippujarjestelma.TicketApplication;
import com.lippujarjestelma.domain.CategoryRepository;
import com.lippujarjestelma.domain.Event;
import com.lippujarjestelma.domain.EventRepository;
import com.lippujarjestelma.domain.UserRepository;
import com.lippujarjestelma.domain.VenueRepository;
import com.lippujarjestelma.utils.AuthenticationHelper;
import com.lippujarjestelma.utils.ErrorMessage;

@Controller
public class EventController {
	private static final Logger log = LoggerFactory.getLogger(TicketApplication.class);
	@Autowired
	private EventRepository erepository;
	@Autowired
	private CategoryRepository categoryrepository;
	@Autowired
	private UserRepository urepository;
	@Autowired
	private VenueRepository vrepository;

	private AuthenticationHelper authHelper = new AuthenticationHelper();

	@GetMapping("/")
	public String index() {
		if (authHelper.generateAuthUsername() == "anonymousUser") {
			return "login";
		} else {
			return "redirect:/eventlist";
		}
	}

	@GetMapping("/eventlist")
	public String Eventlist(Model model) {
		if (authHelper.checkAdminRole() == false) {
			model.addAttribute("events", urepository.findByUsername(authHelper.generateAuthUsername()).getEvents());
		} else {
			model.addAttribute("events", erepository.findAll());
		}
		return "eventlist";
	}

	@GetMapping("/event/{id}")
	public String viewEvent(@PathVariable("id") Long eventId, Model model) {
		String username = erepository.findById(eventId).get().getUser().getUsername();
		log.info(username + "bleep");
		if (username.equals(authHelper.generateAuthUsername()) || authHelper.checkAdminRole() == true) {
			model.addAttribute("event", erepository.findById(eventId).get());
			return "eventdetails";
		} else {
			model.addAttribute("error", new ErrorMessage("Access to this resource denied!"));
			return "errormessage";
		}
	}

	@GetMapping(value = "/addevent")
	public String addEvent(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("categories", categoryrepository.findAll());
		model.addAttribute("venues", vrepository.findAll());
		return "addevent";
	}

	@PostMapping(value = "/saveevent")
	public String saveEvent(Event event) {
		// run if event exists and has a host
		if (erepository.existsById(event.getEventId())) {
			String eventUsername = erepository.findById(event.getEventId()).get().getUser().getUsername();
			String authenticatedUser = authHelper.generateAuthUsername();

			if (eventUsername.equals(authenticatedUser) || authHelper.checkAdminRole() == true) {
				event.setCategory(erepository.findById(event.getEventId()).get().getCategory());
				event.setUser(erepository.findById(event.getEventId()).get().getUser());
			}

		} else {
			event.setUser(urepository.findByUsername(authHelper.generateAuthUsername()));
		}
		erepository.save(event);
		return "redirect:/eventlist";
	}

	@GetMapping(value = "/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_HOST')")
	public String deleteEvent(@PathVariable("id") Long eventId, Model model) {
		if (authHelper.checkAdminRole() == true) {
			erepository.deleteById(eventId);
		} else {
			String authUsername = authHelper.generateAuthUsername();
			String requestUsername = erepository.findById(eventId).get().getUser().getUsername();
			log.info(authUsername, requestUsername, "please");
			if (authUsername.equals(requestUsername)) {
				erepository.deleteById(eventId);
			} else {
				return "redirect:/eventlist";
			}
		}
		return "redirect:../eventlist";
	}

	@GetMapping(value = "/edit/{id}")
	public String editEvent(@PathVariable("id") Long EventId, Model model) {
		model.addAttribute("event", erepository.findById(EventId));
		model.addAttribute("categories", categoryrepository.findAll());
		model.addAttribute("venues", vrepository.findAll());
		return "editevent";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

}
