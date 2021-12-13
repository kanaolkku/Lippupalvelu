package com.lippujarjestelma.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {

	public String generateAuthUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return username;
	}

	public boolean checkAdminRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null
				&& authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return true;
		} else {
			return false;
		}
	}

}
