package com.synechron.user.security.config;

import com.synechron.user.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
   // private CustomUserDetailsService customUserDetailsService;
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
               /*     .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/api/v1/adminPage").hasAnyAuthority("ADMIN")
                .antMatchers("/api/v1/login/*").hasAnyAuthority("USER")
                .antMatchers("/api/v1/resetPwd").hasAuthority("USER")
                .anyRequest().authenticated();*/

               .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/adminPage").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login").hasAuthority("USER")
                //.antMatchers("/api/v1/user/details/*").hasAnyRole("ADMIN","USER")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/resetPwd").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

    }

}
