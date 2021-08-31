package com.ksp.config;

import static com.ksp.utils.Constant.ROLE_ADMIN;
import static com.ksp.utils.Constant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private EntryPointHandler unauthorizedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().formLogin().disable().exceptionHandling()
				.authenticationEntryPoint(this.unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		http.authorizeRequests().antMatchers("/**").hasAnyRole(ROLE_USER, ROLE_ADMIN);
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationFilter authenticationTokenFilter() {
		return new AuthenticationFilter();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
		web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
				"/webjars/**");
	}
}
