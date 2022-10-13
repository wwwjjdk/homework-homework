package com.example.lasthomework131022.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Configuration
public class MyConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.antMatchers("**/by-deletePath").hasRole("DELETE");
                    auth.antMatchers("**/by-writeOrDeletePath").hasAnyRole("WRITE", "DELETE");
                    auth.antMatchers("**/by-readPath").authenticated();
                    auth.antMatchers("**/").permitAll();
                }).formLogin();
    }
    //authorizeRequests(), мы говорим предоставить разрешения для следующих url.
    //доступ к эндпоинтам от меньшего к большему

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("name1")
                .password(passwordEncoder().encode("password1"))
                .roles("READ")
                .and()
                .withUser("name2")
                .password(passwordEncoder().encode("password2"))
                .roles("WRITE")
                .and()
                .withUser("name3")
                .password(passwordEncoder().encode("password3"))
                .roles("DELETE");
    }
}
