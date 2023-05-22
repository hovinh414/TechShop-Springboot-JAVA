package com.shoptech.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.authorizeRequests().anyRequest().permitAll();
        return http.build();
    }

    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //permit access to the assets in the following directories
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }*/
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // any request need to be authenticated (require login)
        // custom login page at "/login" - handler method in MainController class
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
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and().logout().permitAll()
                .and().rememberMe().key("AbcDefgHijKlmnOpqrs_1234567890")
                .tokenValiditySeconds(7 * 24 * 60 * 60);  // remember me cookie is valid for one week

        *//*
         * set a fix key used for the md5 hash algorithm to encrypt cookie content each
         * time the application is restarted, a new key will be generated; this key will
         * be used to encrypt the cookie content so, in order for the cookie to be
         * permanent; a fix key is needed.
         *//*

        http.authenticationProvider(authenticationProvider());


        return http.build();
    }*/
}