package com.courier.courierapp.security;

import com.courier.courierapp.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/api/auth/register").permitAll();
                    auth.requestMatchers("/api/auth/login").permitAll();
                    auth.requestMatchers("/api/auth/logout").permitAll();

                    // Публичен достъп до фирмите (примерно за display):
                    auth.requestMatchers(HttpMethod.GET, "/api/companies/**").permitAll();

                    // ---- Само EMPLOYEE за компании (POST/PUT/DELETE):
                    auth.requestMatchers(HttpMethod.POST, "/api/companies/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT,  "/api/companies/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/companies/**").hasRole("EMPLOYEE");

                    // ---- REPORTS (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET,  "/api/reports/**").hasRole("EMPLOYEE");

                    // ---- OFFICES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/offices/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.POST, "/api/offices/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT,  "/api/offices/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/offices/**").hasRole("EMPLOYEE");

                    // ---- EMPLOYEES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.POST,"/api/employees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("EMPLOYEE");

                    // ---- USERS (само EMPLOYEE):
                    auth.requestMatchers("/api/users/**").hasRole("EMPLOYEE");

                    // ---- CLIENTS:
                    auth.requestMatchers(HttpMethod.GET, "/api/clients").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.GET, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.PUT, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.DELETE, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST,"/api/clients/**").hasRole("EMPLOYEE");

                    // ---- PACKAGES:
                    //   EMPLOYEE -> POST,PUT,DELETE
                    auth.requestMatchers(HttpMethod.POST,"/api/packages/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT, "/api/packages/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/packages/**").hasRole("EMPLOYEE");
                    //   CLIENT -> GET (само своите), но .authenticated() + контролерна логика
                    auth.requestMatchers(HttpMethod.GET,"/api/packages").authenticated();
                    auth.requestMatchers(HttpMethod.GET,"/api/packages/**").authenticated();

                    // ---- DELIVERYFEES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/deliveryfees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.POST,"/api/deliveryfees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT, "/api/deliveryfees/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/deliveryfees/**").hasRole("EMPLOYEE");

                    // ---- REVENUES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/revenues/**").hasRole("EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/revenues/**").hasRole("EMPLOYEE");

                    // ---- Всичко останало -> authenticated
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
