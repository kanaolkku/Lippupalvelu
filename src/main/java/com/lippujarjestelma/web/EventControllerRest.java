package com.lippujarjestelma.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lippujarjestelma.domain.Event;
import com.lippujarjestelma.domain.EventRepository;

@RequestMapping("/api/events")
@CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
@RestController
public class EventControllerRest {
	@Autowired
	private EventRepository eventrepository;

	@GetMapping("/")
	public @ResponseBody List<Event> eventListRest() {
		return (List<Event>) this.eventrepository.findAll();
	}

	@GetMapping("/{id}")
	public @ResponseBody Optional<Event> findEventRest(@PathVariable("id") Long eventId) {
		return eventrepository.findById(eventId);
	}

}
