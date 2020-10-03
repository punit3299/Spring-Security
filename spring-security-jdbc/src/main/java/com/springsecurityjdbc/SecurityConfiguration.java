package com.springsecurityjdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
//				If we have our own defined users and authorities table, then we can 
//				override them here with these two functions
//				Since, users and authorities are by-default tables for spring security
				.usersByUsernameQuery(" select username,password,enabled "
						+ " from users "
						+ " where username = ? ")
				.authoritiesByUsernameQuery(" select username,authority "
						+ " from authorities "
						+ " where username = ? ");
		
//				.withDefaultSchema()
//				.withUser(
//						User.withUsername("user")
//						.password("pass")
//						.roles("USER")
//				)
//				.withUser(
//						User.withUsername("admin")
//						.password("pass")
//						.roles("ADMIN")
//				);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user").hasAnyRole("USER","ADMIN")
				.antMatchers("/").permitAll()
				.and()
				.formLogin();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
