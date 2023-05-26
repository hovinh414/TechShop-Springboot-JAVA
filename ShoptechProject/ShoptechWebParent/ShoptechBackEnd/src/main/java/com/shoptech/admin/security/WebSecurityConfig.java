package com.shoptech.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new ShoptechUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

<<<<<<< HEAD
    //NOTE: Đóng từ dòng 39 đến 87  [đang lỗi chưa bik fix]
    /*@Bean
=======

    @Bean
>>>>>>> main
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
                .requestMatchers("/products/**", "/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")

                .requestMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")

                .requestMatchers("/products/edit/**", "/products/save", "/products/check_unique")
                .hasAnyAuthority("Admin", "Editor", "Salesperson")

                .requestMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")

                .requestMatchers("/customers/**", "/shipping/**", "/report/**").hasAnyAuthority("Admin", "Salesperson")
                .requestMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
                .requestMatchers("/articles/**", "/menus/**").hasAnyAuthority("Admin", "Editor")
                .requestMatchers("/assets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and().logout().permitAll();
                *//*.and().rememberMe().key("AbcDefgHijKlmnOpqrs_1234567890")
                .tokenValiditySeconds(7 * 24 * 60 * 60);  // remember me cookie is valid for one week*//*

<<<<<<< HEAD
        *//*
         * set a fix key used for the md5 hash algorithm to encrypt cookie content each
         * time the application is restarted, a new key will be generated; this key will
         * be used to encrypt the cookie content so, in order for the cookie to be
         * permanent; a fix key is needed.
         *//*

=======
>>>>>>> main
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**");
    }*/


<<<<<<< HEAD
    //NOTE: Mở Cái này là vô được admin

    @Bean
=======
    /*@Bean
>>>>>>> main
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.authorizeRequests().anyRequest().permitAll();
        return http.build();
    }



}