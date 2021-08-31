package com.ksp.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ksp.bo.client.auth.UserBasicBo;

public class RequestUtils {
	private RequestUtils() {

	}

	public static String loggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserBasicBo) {
			return ((UserBasicBo) principal).getId();
		}
		return Constant.NOT_LOGGED_IN;

	}
}
