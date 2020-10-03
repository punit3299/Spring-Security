package com.springbootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//It is a web security configuration class
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	Used for Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// setting type of configuration
		auth.inMemoryAuthentication()
				.withUser("user")
				.password("pass")
				.roles("USER")
				.and()
				.withUser("admin")
				.password("pass")
				.roles("ADMIN");
	}

//	To encode our password as hash
	@Bean
	public PasswordEncoder getPasswordEncoder() {

		// NoOpPasswordEncoder - doesn't encode password, just keep it as clear text -
		// not secure
		return NoOpPasswordEncoder.getInstance();
	}

//	Used for Authorization
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//				.antMatchers("/**").hasAnyRole("ADMIN","USER")
//				.antMatchers("/**").hasRole("ADMIN")
				.antMatchers("/admin").hasRole("ADMIN")
//				.antMatchers("/user").hasRole("USER")
				.antMatchers("/user").hasAnyRole("USER","ADMIN")
				.antMatchers("/").permitAll().and().formLogin();
	}
}
