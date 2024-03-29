package com.deltamate.demo.config;

import com.deltamate.demo.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**","/","/view/home").permitAll()
                .antMatchers("/users","/users/**").hasRole("ADMIN")
                .antMatchers("/workStandard","/workStandard/**").hasRole("OFFICE")
                .antMatchers("/workStandard").hasRole("USER")
                //.anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("userID")
                .successForwardUrl("/workStandard")
                .failureForwardUrl("/login?error")
                .and()
                .logout()
                .and()
                .rememberMe()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
