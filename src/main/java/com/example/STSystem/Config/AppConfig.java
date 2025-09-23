package com.example.STSystem.Config;

import com.example.STSystem.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class AppConfig {

    @Autowired
    private CustomUserDetailsService cuds;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> {
                    cors.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                            CorsConfiguration cfg = new CorsConfiguration();

                            cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                            cfg.setAllowedMethods(Collections.singletonList("*"));
                            cfg.setAllowCredentials(true);
                            cfg.setAllowedHeaders(Collections.singletonList("*"));
                            cfg.setExposedHeaders(Arrays.asList("Authorization"));

                            return cfg;
                        }
                    });
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/save", "/api/login").permitAll()
                        .requestMatchers("/stocks/**", "/lboard").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/{username}", "/api/*/deposit").hasRole("USER")
                        .requestMatchers("/api/*/withdraw", "/api/*/balance").hasRole("USER")
                        .requestMatchers("/portfolio/**").hasRole("USER")
                        .requestMatchers("/trade/**").hasRole("USER")
                        .requestMatchers("/transaction/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception{
        return cfg.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
