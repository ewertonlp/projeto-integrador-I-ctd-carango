package com.backendprojetointegrador.lajeDev.common.security;

import com.backendprojetointegrador.lajeDev.api.security.FilterToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private FilterToken filterToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .cors(Customizer.withDefaults())

                    .csrf()
                        .disable()

                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                        .authorizeHttpRequests()
                            .requestMatchers(HttpMethod.POST, "/usuarios/**")
                                .permitAll()

                            .requestMatchers(HttpMethod.DELETE, "/usuarios/**")
                                .hasAnyRole("ADMIN")

                            .requestMatchers(HttpMethod.PATCH, "/usuarios/**")
                                .hasAnyRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/usuarios/**")
                                .hasAnyRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/login")
                                .permitAll()

                            .requestMatchers(HttpMethod.POST, "/produtos/**")
                                .hasAnyRole("ADMIN", "USER")

                            .requestMatchers(HttpMethod.POST, "/upload/**")
                                .hasAnyRole("ADMIN", "USER")

                            .requestMatchers(HttpMethod.POST ,"/reservas/**")
                                .hasAnyRole("CLIENT")

                            .requestMatchers(HttpMethod.GET, "/**")
                                .permitAll()

                            .anyRequest()
                                .authenticated()
                    .and()
                        .addFilterBefore(filterToken, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://127.0.0.1:5173",
                "http://ec2-15-228-148-249.sa-east-1.compute.amazonaws.com/"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
