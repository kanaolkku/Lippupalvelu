package com.lippujarjestelma;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lippujarjestelma.web.CategoryController;
import com.lippujarjestelma.web.EventController;
import com.lippujarjestelma.web.EventControllerRest;
import com.lippujarjestelma.web.UserController;
import com.lippujarjestelma.web.UserControllerRest;
import com.lippujarjestelma.web.VenueController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TicketApplicationTests {
	@Autowired
	private EventController econtroller;
	@Autowired
	private CategoryController catcontroller;
	@Autowired
	private VenueController vcontroller;
	@Autowired
	private UserController ucontroller;
	@Autowired
	private EventControllerRest erestcontroller;
	@Autowired
	private UserControllerRest urestcontroller;

	@Test
	void contextLoads() {
		assertThat(econtroller).isNotNull();
		assertThat(catcontroller).isNotNull();
		assertThat(vcontroller).isNotNull();
		assertThat(ucontroller).isNotNull();
		assertThat(erestcontroller).isNotNull();
		assertThat(urestcontroller).isNotNull();
	}

}
