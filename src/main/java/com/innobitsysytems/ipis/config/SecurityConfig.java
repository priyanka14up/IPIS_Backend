/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Security Configuration
 */

package com.innobitsysytems.ipis.config;

import com.innobitsysytems.ipis.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login")
				.permitAll().antMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/getTvDisplay").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/getFile").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1//displayFiles").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/setup/get-train-status-color/{status}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/setup/get-ivd-screen-color-config/{1}").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/setup/add-status-color").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/setup/add-ivd-screen-color-config").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/setup/send-color-packet").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/setup/send-video-packet").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/v1/setup/update-train-status-color").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/v1/setup//update-ivd-screen-config").permitAll()
				.antMatchers("/ws/**", "/user/**", "/tv/**", "/chatroom/**", "/app/**").permitAll()
				.antMatchers("/trainlogin").hasAnyRole("SUPER ADMIN", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/v1/setup/**").hasAnyAuthority("ROLE_Setup")
				.antMatchers(HttpMethod.PUT, "/api/v1/setup/**").hasAnyAuthority("ROLE_Setup")
				.antMatchers(HttpMethod.DELETE, "/api/v1/setup/**").hasAnyAuthority("ROLE_Setup")
				.antMatchers(HttpMethod.GET, "/api/v1/setup/**")
				.hasAnyRole("SUPER ADMIN", "ADMIN", "STATION MASTER", "OPERATOR")
				.antMatchers(HttpMethod.POST, "/api/v1/interface/**").hasAnyAuthority("ROLE_Interface")
				.antMatchers(HttpMethod.PUT, "/api/v1/interface/**").hasAnyAuthority("ROLE_Interface")
				.antMatchers(HttpMethod.DELETE, "/api/v1/interface/**").hasAnyAuthority("ROLE_Interface")
				.antMatchers(HttpMethod.GET, "/api/v1/interface/**")
				.hasAnyRole("SUPER ADMIN", "ADMIN", "STATION MASTER", "OPERATOR").antMatchers("/api/v1/reports/**")
				.hasAnyRole("SUPER ADMIN", "ADMIN", "STATION MASTER", "OPERATOR").anyRequest().authenticated();

		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/**").allowedOrigins("*");
			}
		};
	}
}