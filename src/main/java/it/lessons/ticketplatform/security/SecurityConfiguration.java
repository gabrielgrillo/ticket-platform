package it.lessons.ticketplatform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    		http
            // Configura le autorizzazioni
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/tickets/create", "/ticket/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/tickets/**").hasAuthority("ADMIN")
                .requestMatchers("/notes", "/notes/**").hasAnyAuthority("ADMIN", "OPERATORE")
                .requestMatchers("/tickets", "/tickets/**").hasAnyAuthority("OPERATORE", "ADMIN")
                .anyRequest().permitAll() // Altri endpoint accessibili senza autenticazione
            )
            // Configura il login
            .formLogin(form -> form
                .permitAll()
            )
            // Configura il logout
            .logout(logout -> logout
                .permitAll()
            )
            // Gestione delle eccezioni
            .exceptionHandling(exception -> exception
                .accessDeniedPage(null) // Pagina personalizzata per accessi negati (opzionale)
            );
        
        return http.build(); // Costruisce il filtro di sicurezza 
        
   
      
          
        }

        
        
    
    
    @Bean
    DatabaseUserDetailsService userDetailsService() {
    	return new DatabaseUserDetailsService();
    	}
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider();
    	
    	autProvider.setUserDetailsService(userDetailsService());
    	autProvider.setPasswordEncoder(passwordEncoder());
    	
    	return autProvider;
    }
}
