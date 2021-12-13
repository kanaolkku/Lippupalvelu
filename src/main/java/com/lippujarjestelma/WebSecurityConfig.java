package com.lippujarjestelma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lippujarjestelma.filter.CustomAuthenticationFilter;
import com.lippujarjestelma.filter.CustomAuthorizationFilter;
import com.lippujarjestelma.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Configuration
	@Order(1)
	public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private UserDetailServiceImpl userDetailService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
					authenticationManagerBean());
			customAuthenticationFilter.setFilterProcessesUrl("/api/login");

			http.csrf().disable();
			http.cors().disable();
			http.antMatcher("/api/**").authorizeRequests();
			http.authorizeRequests()
					.antMatchers("/api/login", "/api/users/token/refresh", "/api/users/save", "/api/events/**")
					.permitAll();
			http.authorizeRequests().antMatchers("/api/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_HOST");
			// http.authorizeRequests().anyRequest().authenticated();
			http.addFilter(customAuthenticationFilter);
			http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
		}
	}

	@Configuration
	@Order(2)
	public static class HttpSessionSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().antMatcher("/**").authorizeRequests()
					.antMatchers("/login", "/", "/signup", "/saveuser").permitAll()
					.antMatchers("/resources/**", "/css/**").permitAll().antMatchers("/**")
					.hasAnyAuthority("ROLE_ADMIN", "ROLE_HOST").and().formLogin().loginPage("/login")
					.defaultSuccessUrl("/eventlist", true).permitAll().and().logout().permitAll();
			http.headers().frameOptions().disable();
		}

		@Autowired
		private UserDetailServiceImpl userDetailService;

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
		}
	}

}