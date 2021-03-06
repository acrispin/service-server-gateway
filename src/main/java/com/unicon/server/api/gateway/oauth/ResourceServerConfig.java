package com.unicon.server.api.gateway.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Value("${config.security.oauth.jwt.key}")
    private String jwtKey;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/rest/security/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/rest/cliente/**", "/rest/items/listar", "/rest/usuarios/usuarios").permitAll()
//                .antMatchers(HttpMethod.GET, "/rest/productos/ver/{id}",
//                        "/rest/items/ver/{id}/cantidad/{cantidad}",
//                        "/rest/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/rest/productos/**", "/rest/items/**", "/rest/usuarios/**").hasRole("ADMIN")
//                .anyRequest().authenticated();

        http.authorizeRequests().antMatchers("/rest/security/**").permitAll()
                .antMatchers(
                        "/rest/clientes/cliente/**",
                        "/rest/obras/obra/**",
                        "/rest/plantas/planta/**",
                        "/rest/sunat/empresa/**"
                ).hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(jwtKey);
        return tokenConverter;
    }

}