package com.lippujarjestelma.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lippujarjestelma.domain.Venue;
import com.lippujarjestelma.domain.VenueRepository;

@Controller
public class VenueController {
	@Autowired
	private VenueRepository venuerepository;

	@GetMapping(value = "/venuelist")
	public String venuelist(Model model) {
		model.addAttribute("venues", venuerepository.findAll());
		return "venuelist";
	}

	@GetMapping(value = "/addvenue")
	public String addVenue(Model model) {
		model.addAttribute("venue", new Venue());
		return "addvenue";
	}

	@PostMapping(value = "/savevenue")
	public String saveVenue(Venue venue) {
		venuerepository.save(venue);
		return "venuesaved";
	}
}
