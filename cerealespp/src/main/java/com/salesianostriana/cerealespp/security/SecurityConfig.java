/**
 * 
 */
package com.salesianostriana.cerealespp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author luismi
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
    CustomSuccessHandler customSuccessHandler;
    /**
     * Constructor del objeto SecurityConfig. 
     * @param userDetailsService Objeto que carga información sobre el usuario.
     * @param customSuccessHandler Objeto que configura la autenticación satisfactoria.
     */
	public SecurityConfig(UserDetailsService userDetailsService, CustomSuccessHandler customSuccessHandler) {
		this.userDetailsService = userDetailsService;
		this.customSuccessHandler = customSuccessHandler;
	}
	/**
	 * Método que crea un objeto BCryptPasswordEncoder para encriptar la contraseña de un usuario.
	 * @return Objeto BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/**
	 * {@inheritDoc}
	 * Método que encripta la contraseña ingresada en el logIn.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}
	/**
	 * {@inheritDoc}
	 * Método que configura qué urls son públicas, o privadas para ciertos roles. También especifica la página de logIn y si es pública 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/css/**","/js/**","/webjars/**", "/h2-console/**","/img/**","/files/**","/*", "/signup/**","/images/**","contactUs/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/")
				.permitAll()
				.successHandler(customSuccessHandler)
				.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/");
		
		// Añadimos esto para poder seguir accediendo a la consola de H2
		// con Spring Security habilitado.
		http.csrf().disable();
        http.headers().frameOptions().disable();
		
	}

	
	
	
}
