package app.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import app.spring.security.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	MyUserDetailService myUserDetailService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(registry -> {
					registry.requestMatchers("/", "/home", "/register/**", "/index", "/homepage").permitAll();
					registry.requestMatchers("/user/**").hasRole("USER");
					registry.requestMatchers("/admin/**").hasRole("ADMIN");
					registry.anyRequest().authenticated();
				}).formLogin(fl -> {
					fl.loginPage("/login")
					.successHandler(new AuthenticationSuccessHandler())
					.permitAll();
				}).build();
	}

// In Memory/tempory users	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.builder().username("Jeevan").password("$2a$12$nnMF4hF6ohP5qkcnTz90kOY447edgcbJO5/cIAGLfCP/uO/mZiv16").roles("USER").build();
//		UserDetails admin = User.builder().username("Ganesh").password("$2a$12$vt7f4/B55hssFxXGS2jGluU1EHaktQ.3rbl3mt46dUQT0zujh8LMe").roles("ADMIN", "USER").build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	public UserDetailsService userDetailsService() {
		return myUserDetailService;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(myUserDetailService);
		daoProvider.setPasswordEncoder(passwordEncoder());
		return daoProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}