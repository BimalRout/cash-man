package com.bkr.atm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Configuring Security: HttpSecurity");
        http
                .authorizeRequests()
                // public resources and urls which do not require to be logged in yet
                .antMatchers("/", "/login", "/error", "/errorBlockedOrNotExist", "/enter_card", "/css/**", "/js/**").permitAll()
                // other requests require login (entered correct non-blocked credit card and pin)
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                // start 2-page login from home page with entering credit card number
                .loginPage("/")
                .usernameParameter("j_number")
                .passwordParameter("j_pin")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/operations")
                // if user enters incorrect pin, he will be thrown right to the second page of 2-page login, with the pin code input
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/?logout")
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Configuring Security:AuthenticationManagerBuilder");
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}