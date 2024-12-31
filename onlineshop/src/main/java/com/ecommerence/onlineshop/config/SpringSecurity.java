package com.ecommerence.onlineshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                		.requestMatchers("/mainsys/**").permitAll()
                		.requestMatchers("/order/**").permitAll()
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/delisys/**").permitAll()
                        .requestMatchers("/delidetailsys/**").permitAll()
                        .requestMatchers("/customer/**").permitAll()
                        .requestMatchers("/shop/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/css/**", "/images/**","/js/**","/chartist/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/css/**", "/images/**","/js/**","/chartist/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ass/**", "/ass/images/**","/js/**","/home/**","/signup/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/ass/**", "/ass/images/**","/js/**","/home/**","/signup/**").permitAll()
                        .anyRequest().authenticated()
                ) .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();

    }
}