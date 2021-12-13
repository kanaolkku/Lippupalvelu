package com.lippujarjestelma.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lippujarjestelma.TicketApplication;
import com.lippujarjestelma.domain.User;
import com.lippujarjestelma.domain.UserRepository;

@RequestMapping("/api/users/")
@CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
@RestController
public class UserControllerRest {

	private static final Logger log = LoggerFactory.getLogger(TicketApplication.class);
	@Autowired
	private UserRepository urepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping(value = { "/", "" })
	public ResponseEntity<Iterable<User>> getUsers() {
		return ResponseEntity.ok().body(urepository.findAll());
	}

	@GetMapping("/user/{id}")
	public @ResponseBody Optional<User> findEventRest(@PathVariable("id") Long id) {
		return urepository.findById(id);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		if (urepository.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.badRequest()
					.body("USERNAME ERROR: User with the username " + user.getUsername() + " already exists!");
		}
		user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
		return ResponseEntity.ok().body(urepository.save(user));
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring(7);
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // env secret later
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = urepository.findByUsername(username);
				List<String> roleList = new ArrayList<String>();
				roleList.add(user.getRole());

				String access_token = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", roleList.stream().collect(Collectors.toList())).sign(algorithm);

				// response.setHeader("access_token", access_token);
				// response.setHeader("refresh_token", refresh_token);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				// response.sendError(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);

			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

	@GetMapping(value = "/current")
	public ResponseEntity<User> thisUser() {
		String username = generateAuthUsername();
		return ResponseEntity.ok().body(urepository.findByUsername(username));
	}

	public String generateAuthUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return username;
	}

}
