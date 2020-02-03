package com.unicon.server.api.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * basic auth spring security
 * https://www.baeldung.com/spring-security-basic-authentication
 * https://www.baeldung.com/spring-cloud-securing-services
 * https://howtodoinjava.com/spring-boot2/security-rest-basic-auth-example/
 * https://stackoverflow.com/questions/49258719/adding-spring-security-to-zuul-service-in-spring-boot-micro-service?rq=1
 * https://memorynotfound.com/spring-security-in-memory-authentication-example/
 * https://memorynotfound.com/spring-security-basic-authentication-configuration-example/
 * https://www.baeldung.com/spring-security-session
 * spring oauth2 token disable expire
 * https://stackoverflow.com/questions/28343367/spring-security-oauth2-how-to-disable-access-token-expiry
 *
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
                .formLogin().disable()
                .logout().disable()
            .authorizeRequests()
                .antMatchers("/rest/security/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("12345678"))
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
