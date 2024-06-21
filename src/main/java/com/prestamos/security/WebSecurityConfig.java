package com.prestamos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.prestamos.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())

            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
            	.requestMatchers("/","/webfonts/**","/assets/**","/images/**","/js/**","/jspage/**","/css/**","/csspage/**","/fonts/**","/vendor/**","/login", "/registrarUser","/listar","/buscarUsuario","/guardar").permitAll()
           	 .requestMatchers("/nuevoJefe","/buscarJefe","/listaJefe","/actualizarJefe").hasAnyAuthority("Inversionista")
           	.requestMatchers("/home").hasAnyAuthority("Administrador","Inversionista","Jefe de Prestamista","Prestamista","Prestatario")
                .requestMatchers("/prestamista-list","/prestamista-crear").hasAnyAuthority("Jefe de Prestamista")
                   .requestMatchers("prestatario-crear","prestatario-actualizar","/prestatario-list","/prestatario-search","/solicitudes-prestamo","/cuotas-list-prestamista-pendiente").hasAnyAuthority("Prestamista")
                   .requestMatchers("/solicitar-prestamo","/historial-prestamo","/prestamos","/cuotas-list-prestatario-pendiente").hasAnyAuthority("Prestatario")  


                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true"))
            .logout(logout -> logout
            		
                	.invalidateHttpSession(true)
                	.clearAuthentication(true)
                	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))	
            	.logoutSuccessUrl("/login?logout")  // Cambia la URL de éxito al registro de usuario
            	.deleteCookies("JSESSIONID"));

        return http.build();
    }

}