package com.secutest3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        
        return super.authenticationManagerBean();
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);

		System.out.println("Fetching auth build config");
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http.csrf().disable().cors().disable()
			.authorizeHttpRequests()
			.antMatchers("/hi","/generate-token","/user/").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/items").hasAnyRole("ADMIN")
			.antMatchers("/items").hasAnyRole("ADMIN","USER")
			.anyRequest().authenticated()
		     .and()
             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			.antMatchers("/auth/**").permitAll()
//			.antMatchers("/admin").hasRole("ADMIN")
//			.antMatchers("/**").hasAnyRole("USER","ADMIN")
//			.anyRequest().authenticated()
//			.and()
//			.formLogin()
			;
		http.addFilterBefore(jwtAuthenticationFilter, 
				UsernamePasswordAuthenticationFilter.class);
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("Fetching Password encoder");
	    return NoOpPasswordEncoder.getInstance();
	}
	
//	
//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder
//			bCryptPasswordEncoder, UserDetailsService userDetailService) 
//	  throws Exception {
//	    return http.getSharedObject(AuthenticationManagerBuilder.class)
//	      .userDetailsService(myUserDetailsService)
////	      .passwordEncoder(bCryptPasswordEncoder)
//	      .and()
//	      .build();
//	}
}
