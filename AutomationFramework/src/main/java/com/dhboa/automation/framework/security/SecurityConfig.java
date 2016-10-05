package com.dhboa.automation.framework.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
	
		http.httpBasic().and().authorizeRequests().antMatchers("/css/**", "/js/**", "/default/*").permitAll().
		antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER").and()
		.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
		.csrf().csrfTokenRepository(csrfTokenRepository()).and()
		.logout().logoutSuccessUrl("/login");
	}
	
	@Autowired
	protected void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("select username, password,enabled from af_users where upper(username)=upper(?)")
		.authoritiesByUsernameQuery("select username, role from af_user_roles where upper(username)=upper(?)");

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		// TODO Auto-generated method stub
		HttpSessionCsrfTokenRepository token = new HttpSessionCsrfTokenRepository();
		token.setHeaderName("X-XSRF-TOKEN");
		return token;
	};
}
