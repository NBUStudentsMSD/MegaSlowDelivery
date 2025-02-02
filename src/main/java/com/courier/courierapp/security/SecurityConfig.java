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
                .cors(cors -> {
                    cors.configurationSource(request -> {
                        var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                        corsConfig.setAllowedOrigins(java.util.List.of("*"));
                        corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        corsConfig.setAllowedHeaders(java.util.List.of("*"));
                        return corsConfig;
                    });
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/api/auth/register").permitAll();
                    auth.requestMatchers("/api/auth/login").permitAll();
                    auth.requestMatchers("/api/auth/logout").permitAll();// Публичен достъп до фирмите (примерно за display):
                    auth.requestMatchers(HttpMethod.GET, "/api/companies/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/api/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/api/packages/guest/**").permitAll();



                    // ---- Само EMPLOYEE за компании (POST/PUT/DELETE):
                    auth.requestMatchers(HttpMethod.POST, "/api/companies/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT,  "/api/companies/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/companies/**").hasRole("ADMIN");


                    // Permit all access to the specified endpoints
                    auth.requestMatchers(HttpMethod.GET, "/api/packages/recipient/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/packages/sender/**").permitAll();

                    // ---- REPORTS (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET,  "/api/reports/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- OFFICES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/offices/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/offices/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT,  "/api/offices/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/offices/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- EMPLOYEES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST,"/api/employees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/employees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- USERS (само EMPLOYEE):
                    auth.requestMatchers("/api/users/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- CLIENTS:
                    auth.requestMatchers(HttpMethod.GET, "/api/clients").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.PUT, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.DELETE, "/api/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST,"/api/clients/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- PACKAGES:
                    //   EMPLOYEE -> POST,PUT,DELETE
                    auth.requestMatchers(HttpMethod.POST,"/api/packages/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/packages/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/packages/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    //   CLIENT -> GET (само своите), но .authenticated() + контролерна логика
                    auth.requestMatchers(HttpMethod.GET,"/api/packages").authenticated();
                    auth.requestMatchers(HttpMethod.GET,"/api/packages/**").authenticated();

                    // ---- DELIVERYFEES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/deliveryfees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST,"/api/deliveryfees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/deliveryfees/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/deliveryfees/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- REVENUES (само EMPLOYEE):
                    auth.requestMatchers(HttpMethod.GET, "/api/revenues/**").hasAnyRole("EMPLOYEE" , "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/revenues/**").hasAnyRole("EMPLOYEE", "ADMIN");

                    // ---- Всичко останало -> authenticated
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
