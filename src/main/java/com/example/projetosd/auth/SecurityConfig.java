package com.example.projetosd.auth;

import com.example.projetosd.logic.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin")    .hasRole("ADMIN")
                        .requestMatchers("/admin/**") .hasRole("ADMIN")
                        .requestMatchers("/perfil")   .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/historico").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/carrinho") .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/loja")     .hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
            )
            .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .usernameParameter("email")
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/"); // Redirect if NOT authenticated
                })
                // Redirect forbidden (logged in but no permission) to "/"
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/"); // Redirect if authenticated but forbidden
                })
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}