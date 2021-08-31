package com.ksp.config.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ksp.bo.client.auth.UserBasicBo;

@FeignClient(name = "ksp-auth-service", url = "${auth.service.url}")
public interface AuthRestClient {
	@PostMapping("/verify_token")
	UserBasicBo verifyToken(@RequestHeader("Authorization") String token);
}
